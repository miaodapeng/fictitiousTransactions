package com.vedeng.common.aop;

import com.vedeng.common.annotation.MethodLock;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.common.util.StringUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

/**
 *方法锁切面
 * @author strange
 * @date $
 */
@Aspect
@Component
public class MethodLockAspect {

    private static final Logger log =  LoggerFactory.getLogger(MethodLockAspect.class);

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
            put("java.lang.String",String.class);
        }
    };

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("@annotation(com.vedeng.common.annotation.MethodLock)")
    public void method(){}

    /**
     *获取key 设置redis锁
     * @Author:strange
     * @Date:19:57 2019-12-29
     */
    @Around("method()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ResultInfo<T> resultInfo = new ResultInfo<>();

        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        //获取全路径明作为前半段key
        StringBuffer rediskey = getRediskey(joinPoint);

        String key = "";
        try {
            MethodLock declaredAnnotation = getDeclaredAnnotation(joinPoint);
            //获取注解指定时间
            Integer time = declaredAnnotation.time();
            //获取注解指定类
            Class aClass = declaredAnnotation.className();
            if(aClass == null){
                throw new Exception("MethodLockAspect 未指定参数类型 方法为:"+rediskey.toString());
            }
            //获取注解指定字段
            String field = declaredAnnotation.field();

            boolean annotationflag = false;

            //查找MethodLockParam注解并获取改注解下标值
            Integer paramsIndex = getparamsInedx(joinPoint);
            if(paramsIndex != null){
                annotationflag = true;
            }

            //如果有MethodLockParam注解且入参是基本数据类型或其包装类则直接取值
            key = getkey(params, rediskey, key, aClass, field, annotationflag, paramsIndex);
            if(StringUtil.isBlank(key)){
                throw new Exception("MethodLockAspect 入参为空 方法为:"+rediskey.toString());
            }
            //获取最终rediskey
            rediskey =  rediskey.append(key);
            //设置redis锁
            boolean flag = redisUtils.tryGetDistributedLock(rediskey.toString(), UUID.randomUUID().toString(), time);
            if(flag) {
                log.info("MethodLockAspect rediskey:{}",rediskey.toString());
                return joinPoint.proceed();
            }
        }catch (Exception e){
            log.error("MethodLockAspect  rediskey:{} ,error:{}",rediskey.toString(),e);
        }finally {
            String s = redisUtils.get(rediskey.toString());
            redisUtils.releaseDistributedLock(rediskey.toString(), s);
        }
        resultInfo.setMessage("请刷新页面后,重试!");
        return resultInfo;
    }

    private String getkey(Object[] params, StringBuffer rediskey, String key, Class aClass, String field, boolean annotationflag, Integer paramsIndex) throws Exception {
        if(annotationflag && map.containsKey(aClass.getName())){
            if(params[paramsIndex] != null) {
                key = params[paramsIndex].toString();
            }
        }else if(annotationflag && !map.containsKey(aClass.getName())){
            //有MethodLockParam注解但为对象
            field = captureName(field,rediskey.toString());
            Object param = params[paramsIndex];
            if(param != null) {
                //反射获取key
                key = declaredMethodInvoke(param, field);
            }
        } else{
            field = captureName(field,rediskey.toString());
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param != null && param.getClass().getName().equals(aClass.getName())) {
                        //获取指定类中指定字段入参
                        key = declaredMethodInvoke(param,field);
                        break;
                    }
                }
            }
        }
        return key;
    }

    /**
    *获取rediskey
    * @Author:strange
    * @Date:17:36 2020-01-07
    */
    private StringBuffer getRediskey(ProceedingJoinPoint joinPoint) {
        StringBuffer rediskey = new StringBuffer();
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        //方法路径名
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        rediskey = rediskey.append(ErpConst.METHODLOCK_REDISKEY).append(declaringTypeName).append(".").append(methodName).append(":");
        return rediskey;
    }
    /**
    *获取有@MethodLockParam注解参数下标值
    * @Author:strange
    * @Date:17:36 2020-01-07
    */
    private Integer getparamsInedx(ProceedingJoinPoint joinPoint) {
        Integer paramsIndex = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            int i = ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation : parameterAnnotation) {
                if(annotation instanceof com.vedeng.common.annotation.MethodLockParam){
//                  Object param = params[i];
                    paramsIndex = i;
                }
            }
        }
        return paramsIndex;
    }
    /**
    * 反射获取对象参数
    * @Author:strange
    * @Date:17:36 2020-01-07
    */
    private static String declaredMethodInvoke(Object param, String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = param.getClass().getDeclaredMethod(field);
       return declaredMethod.invoke(param, null).toString();
    }

    /**
     * 获取方法中声明的注解
     * @author strange
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public MethodLock getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        MethodLock annotation = objMethod.getDeclaredAnnotation(MethodLock.class);
        // 返回
        return annotation;
    }
    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    private static String captureName(String str,String rediskey) throws Exception {
        //多个相同对象则入参为有注解对象
        if(StringUtil.isBlank(str)){
            throw new Exception("MethodLockAspect 入参对象字段为空 方法为:"+rediskey);
        }
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return "get"+String.valueOf(cs);
    }

}