package com.vedeng.common.aop;

import com.common.constants.Contant;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.order.model.Saleorder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 防止重复提交AOP.
 * @note: 防止多人对一个订单的重复提交(非单人重复提交token机制)
 * @version: 1.0.
 * @date: 2019/8/27 16:54.
 * @author: Tomcat.Hui.
 */
@Aspect
@Component
public class AntiDuplicationAspect {

    private static final Logger log =  LoggerFactory.getLogger(AntiDuplicationAspect.class);

    private static final String REDIS_KEY = "ORDER_NO_";

    @Autowired
    private RedisUtils redisUtils;

    @Pointcut("@annotation(com.vedeng.common.annotation.AntiOrderRepeat)")
    public  void controller() {}

    /** @description: 环绕消息.
     * @notes: 在订单校验接口增加环绕处理：如果并发提交，则判断是否2min内有审核流程.
     * @author: Tomcat.Hui.
     * @date: 2019/8/28 11:48.
     * @param pjp
     * @return: com.vedeng.common.model.ResultInfo<?>.
     * @throws: .
     */
    @Around("controller()")
    public ResultInfo<?>  around(ProceedingJoinPoint pjp){

        Object[] args = pjp.getArgs();
        if(args.length >= 2 && args[1].getClass() == Saleorder.class){
            Saleorder order = (Saleorder) args[1];
            if(StringUtil.isNotBlank(order.getSaleorderId().toString())){
                log.info("订单: {} 已被拦截,正在校验是否重复提交",order.getSaleorderId());
                //该订单是否已加入缓存
                if(redisUtils.exists(REDIS_KEY + order.getSaleorderId())){
                    log.info("订单: {} 2分钟内多次提交",order.getSaleorderId());
                    return new  ResultInfo(-1,"请勿在2分钟内重复审核同一订单");
                } else {
                    log.info("订单: {} 2分钟内未进行过提交,审核流程继续",order.getSaleorderId());
                    String key = REDIS_KEY + order.getSaleorderId();
                    redisUtils.setex(key,"1",120);
                    log.info("设置Redis缓存 : {} redis缓存时间: {}",key,redisUtils.ttl(key) );
                    //流程继续
                    try {
                        Object o =  pjp.proceed(args);
                        return (ResultInfo<?>)o;
                    } catch (Throwable throwable) {
                        log.error(Contant.ERROR_MSG, throwable);
                    }
                }
            } else {
                return new  ResultInfo(-1,"订单号为空");
            }
        }
        return new  ResultInfo(-1,"参数错误");
    }

}
