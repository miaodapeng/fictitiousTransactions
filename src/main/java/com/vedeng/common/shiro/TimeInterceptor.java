package com.vedeng.common.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <b>Description:</b><br>
 * 请求方法拦截，计算执行时间
 * 
 * @author duke
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.shiro <br>
 *       <b>ClassName:</b> TimeInteceptor <br>
 *       <b>Date:</b> 2017年4月28日 上午9:49:18
 */
public class TimeInterceptor extends HandlerInterceptorAdapter {

	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

	private static final Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);

	/**
	 * 在Controller处理之前进行调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		 * if (logger.isDebugEnabled()){ long beginTime =
		 * System.currentTimeMillis();//1、开始时间 startTimeThreadLocal.set(beginTime);
		 * //线程绑定变量（该数据只有当前请求的线程可见） logger.debug("------开始计时: {"+new
		 * SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS").format(beginTime)
		 * +"}  --------------------------URI: {"+request.getRequestURI()+"}"); }
		 */
		return true;
	}

	/**
	 * 当前这个Interceptor的preHandle方法返回值为true的时候才会执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {

		/*
		 * if (logger.isDebugEnabled()){ long beginTime =
		 * startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间） long endTime =
		 * System.currentTimeMillis(); //2、结束时间
		 * 
		 * logger.debug("------计时结束：{"+new
		 * SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS").format(endTime)+"}  ------耗时：{"+
		 * (Double.valueOf(endTime) -
		 * Double.valueOf(beginTime))/1000+"}s  ------URI: {"+request.getRequestURI()+
		 * "}"); }
		 */
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3)
			throws Exception {
	}
}
