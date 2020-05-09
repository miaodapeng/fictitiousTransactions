package com.vedeng.common.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.UserOperationLog;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.UserOperationLogService;

import net.sf.json.JSONObject;

@Aspect
@Component
public class SystemLogAspect {
	public static Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	@Resource
	private UserOperationLogService userOperationLogService;
	@Resource
	private ActionService actionService;

	//本地异常日志记录对象    
    //private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);  
    
    //Controller层切点    
    //@Pointcut("execution (* com.vedeng.*.controller..*.*(..)) && !execution (* com.vedeng.common.controller..*.*(..))")
    @Pointcut("@annotation(com.vedeng.common.annotation.SystemControllerLog)")
    public  void controllerAspect() {} 
    
    //service层切点   
//    @Pointcut("@annotation(com.vedeng.common.annotation.SystemServiceLog)")
//    public  void serviceAspect() {} 
    
    @Around("controllerAspect()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
    	//访问目标方法的参数：
        Object[] args = point.getArgs();
        if (args != null && args.length > 0 && args[0].getClass() == String.class) {
            
        }
        //用改变后的参数执行目标方法
        Object returnValue = point.proceed(args);
    	return returnValue ;
    }
 
    /**
     * <b>Description:</b><br> 后置通知
     * @param point
     * @param returnValue
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年9月6日 下午1:06:34
     */
    @AfterReturning(value="controllerAspect()", returning="returnValue")
    public void doAfterReturn(JoinPoint joinPoint, Object returnValue){
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
    	ServletRequestAttributes sra = (ServletRequestAttributes) ra;  
    	HttpServletRequest request = sra.getRequest();   
        HttpSession session = request.getSession();    
        //读取session中的用户    
        User user = (User) session.getAttribute(ErpConst.CURR_USER);    
        //请求功能点url
        String url = request.getServletPath();   
        try {
	        Integer optType = 0;
	        String desc = "";
	        Integer type  = 0;
	        Map<String, Object> map = getServiceMethodMsg(joinPoint,url);
	        if(map != null){
	        	if(map.containsKey("type")){
	        		type = Integer.valueOf(map.get("type").toString());
	        	}
	        	if(map.containsKey("desc")){
	        		desc = map.get("desc").toString();
	        	}
	        	if(map.containsKey("opt")){
	        		optType = Integer.valueOf(map.get("opt").toString());
	        	}
	        }
	        Integer actionId = getActionId(url);
	        if(actionId !=null && actionId != 0 && optType !=5){
    			//*========数据库日志=========*//  
                UserOperationLog userOperationLog = new UserOperationLog();
                userOperationLog.setAccessTime(DateUtil.sysTimeMillis());
                userOperationLog.setUserId(user.getUserId());
                userOperationLog.setUsername(user.getUsername());
                userOperationLog.setAccessIp(getIpAddress(request));
                userOperationLog.setActionId(actionId);
                userOperationLog.setType(type);
                userOperationLog.setDesc(desc);
                userOperationLog.setOperationType(optType);
                if(optType !=3 && optType !=4){//导入导出不做前后记录
                	Object [] methods = joinPoint.getArgs();
                    StringBuffer inParams = new StringBuffer();
                    for (Object obj : methods) {
    					if(obj != null && !obj.toString().contains("servlet")&&!obj.toString().contains("beforeParams") && !obj.toString().contains("BeanPropertyBindingResult")){
    						inParams.append(JsonUtils.translateToJson(obj)).append(";");
    					}
    					if(optType != 0 && obj != null && obj.toString().contains("beforeParams")){
    						String key = obj.toString();
    						String beforeParams = JedisUtils.get(key);
    						userOperationLog.setBeforeEntity(beforeParams);
    						JedisUtils.del(key);
    	                }
    				}
                    userOperationLog.setAfterEntity(inParams.toString());
                }
                 if(returnValue !=null && returnValue.toString().contains("ResultInfo")){
                	 JSONObject json = JSONObject.fromObject(returnValue); 
                	 ResultInfo<?> res = (ResultInfo<?>) JSONObject.toBean(json, ResultInfo.class);
                	 if(res==null || res.getCode()!=0){
                		 userOperationLog.setResultStatus(-1);
                	 }else{
                		 userOperationLog.setResultStatus(0);
                	 }
                	 //业务主体id
                	 if(res != null && res.getData() != null && res.getData() instanceof Integer){
                		 userOperationLog.setRelatedId((Integer)res.getData()); 
                	 }
                 }else if(returnValue !=null && !returnValue.toString().contains("404") && !returnValue.toString().contains("fail")
                		 &&(returnValue.toString().contains("redirect")||returnValue.toString().contains(".do") || returnValue.toString().contains("success"))){
                	 userOperationLog.setResultStatus(0);
                 }else{
                	 userOperationLog.setResultStatus(-1);
                 }
                 logger.info("ASPECTLOG1"+JSON.toJSONString(userOperationLog));
                //userOperationLogService.saveUserOperationLog(userOperationLog);
        		} 
        }  catch (Exception e) {    
            //记录本地异常日志    
//            logger.error("==后置通知异常==");    
//            logger.error("异常信息:{}", e.getMessage());  
        	logger.error(Contant.ERROR_MSG, e);
        } 
        
            
    }
    
    /**  
     * 异常通知 用于拦截Controller层记录异常日志  
     *  
     * @param joinPoint  
     * @param e  
     */    
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")    
     public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
        HttpSession session = request.getSession();    
        //读取session中的用户    
        User user = (User) session.getAttribute(ErpConst.CURR_USER); 
        //请求功能点url
        String url = request.getServletPath();
    	try {
    		Integer optType = 0;
	        String desc = "";
	        Integer type = 0;
	        Map<String, Object> map = getServiceMethodMsg(joinPoint,url);
	        if(map != null){
	        	if(map.containsKey("type")){
	        		type = Integer.valueOf(map.get("type").toString());
	        	}
	        	if(map.containsKey("desc")){
	        		desc = map.get("desc").toString();
	        	}
	        	if(map.containsKey("opt")){
	        		optType = Integer.valueOf(map.get("opt").toString());
	        	}
	        }
	        Integer actionId = getActionId(url);
	        if(actionId !=null && actionId != 0 && optType !=5){
        	
        		//Integer optType = getControllerMethodOperationType(joinPoint);
        		//if(optType !=5){//查询暂不做记录
    			//*========数据库日志=========*//  
                UserOperationLog userOperationLog = new UserOperationLog();
                userOperationLog.setAccessTime(DateUtil.sysTimeMillis());
                userOperationLog.setUserId(user.getUserId());
                userOperationLog.setUsername(user.getUsername());
                userOperationLog.setAccessIp(getIpAddress(request));
                userOperationLog.setType(type);
                userOperationLog.setAfterEntity(e.getMessage());
                userOperationLog.setActionId(actionId);
                userOperationLog.setResultStatus(-1);
                userOperationLog.setDesc(desc);
                userOperationLog.setOperationType(optType);
                if(optType != 0){
                	//操作前数据原型
                	userOperationLog.setBeforeEntity(null); 
                }
				logger.info("ASPECTLOG2"+JSON.toJSONString(userOperationLog));
              //  userOperationLogService.saveUserOperationLog(userOperationLog);
	        }   
        }  catch (Exception ex) {    
            //记录本地异常日志    
//            logger.error("==异常通知异常==");    
//            logger.error("异常信息:{}", ex.getMessage());  
        	logger.error(Contant.ERROR_MSG, e);
        } 
            
             

    }       
    
    /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     @SuppressWarnings("rawtypes")
	public  static Map<String,Object> getServiceMethodMsg(JoinPoint joinPoint,String url)  throws Exception {    
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        Map<String, Object> map =new HashMap<>();
         for (Method method : methods) { 
             if (method.getName().equals(methodName)) {    
                Class[] clazzs = method.getParameterTypes();    
                 if (clazzs.length == arguments.length) { 
                	 int type = method.getAnnotation(SystemControllerLog. class).type(); 
                	 map.put("type", type);
                	 String description = method.getAnnotation(SystemControllerLog. class).desc(); 
                	 map.put("desc", url+"  "+description);
                	 String operation = method.getAnnotation(SystemControllerLog. class).operationType(); 
                	 if(operation != null && "select".equals(operation)){
                		 map.put("opt", 5);
                	 }else if(operation != null && "add".equals(operation)){
                		 map.put("opt", 0);
                	 }else if(operation != null && "edit".equals(operation)){
                		 map.put("opt", 1);
                	 }else if(operation != null && "delete".equals(operation)){
                		 map.put("opt", 2);
                	 }else if(operation != null && "export".equals(operation)){
                		 map.put("opt", 3);
                	 }else if(operation != null && "import".equals(operation)){
                		 map.put("opt", 4);
                	 }
                     break;    
                }    
            }    
        }    
         return map;    
    }    
     
     /**  
      * 获取注解中对方法的操作类型 用于Controller层注解  
      *  
      * @param joinPoint 切点  
      * @return 方法描述  
     * @throws ClassNotFoundException 
      * @throws Exception  
      */    
      @SuppressWarnings("rawtypes")
      public static Integer getControllerMethodOperationType(JoinPoint joinPoint) throws ClassNotFoundException {    
         String targetName = joinPoint.getTarget().getClass().getName();    
         String methodName = joinPoint.getSignature().getName();    
         Object[] arguments = joinPoint.getArgs();    
         Class targetClass = Class.forName(targetName);    
         Method[] methods = targetClass.getMethods();    
         String operation = "";    
          for (Method method : methods) {    
              if (method.getName().equals(methodName)) {    
                 Class[] clazzs = method.getParameterTypes();    
                  if (clazzs.length == arguments.length) {    
                	  operation = method.getAnnotation(SystemControllerLog. class).operationType();    
                      break;    
                 }    
             }    
         }
         if(operation!=null && !"".equals(operation)){
        	 if("select".equals(operation)){
        		 return 5;
        	 }else if("add".equals(operation)){
        		 return 0;
        	 }else if("edit".equals(operation)){
        		 return 1;
        	 }else if("delete".equals(operation)){
        		 return 2;
        	 }else if("export".equals(operation)){
        		 return 3;
        	 }else if("import".equals(operation)){
        		 return 4;
        	 }
         }
		return -1;    
     }
      
     /**
     * <b>Description:</b><br> 根据请求的url查询actionid
     * @param url
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年9月6日 下午1:40:47
     */
    private Integer getActionId(String url){
    	String [] urls = url.split("/");
    	 if(urls.length>3){
    		 String modelName=urls[1];
    		 String controllerName=urls[2];
    		 String actionName=urls[3].substring(0, urls[3].indexOf("."));
    		 Integer actionId  = actionService.getActionId(modelName, controllerName, actionName);
//    		 if(actionId == null || actionId == 0){
//    			 Action action =new Action();
//    			 action.setActionName(actionName);
//    			 action.setModuleName(modelName);
//    			 action.setControllerName(controllerName);
//    			 action.setIsMenu(0);
//    			 action.setSort(1);
//    			 actionService.insert(action);
//    			 actionId  = action.getActionId();
//    		 }
    		 return actionId;
    	 }
    	 return 0;
     }
   
	
	/** 
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
	 * 参考文章： http://developer.51cto.com/art/201111/305181.htm 
	 *  
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
	 *  
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
	 * 192.168.1.100 
	 *  
	 * 用户真实IP为： 192.168.1.110 
	 *  
	 * @param request 
	 * @return 
	 */  
	private String getIpAddress(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}	
} 

