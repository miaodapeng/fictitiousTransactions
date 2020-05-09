package com.vedeng.common.validator;

import com.vedeng.common.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

public class FormTokenInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FormTokenInterceptor.class);

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {//判断是否是拦截的方法（配置文件中指定）
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			FormToken annotation = method.getAnnotation(FormToken.class);
			if (annotation != null) {//方法中FormToken注释不为空
				//LOGGER.info("当前页面url："+request.getHeader("Referer"));
				boolean needSaveSession = annotation.save();
				if (needSaveSession) {//需要保存Token
					String uuid = UUID.randomUUID().toString();
					request.setAttribute("formToken", uuid);
					//request.getSession(false).setAttribute("formToken", uuid);
					redisUtils.set("formToken:" + uuid, uuid, 24 * 60 * 60);// 24小时过期
				}
				boolean needRemoveSession = annotation.remove();
				if (needRemoveSession) {//需要移除Token
					String formToken = request.getParameter("formToken");
					// 获取redis中的formtoken
					String redisFormToken = redisUtils.get("formToken:" + formToken);
					//表单以及redis中的formtoken不能为空，缓存中不存在：1、已请求被清除；2、令牌错误；
					if (StringUtils.isBlank(formToken) || StringUtils.isBlank(redisFormToken)) {
						writeMessage(request, response, "操作失败，请重新尝试");
						return false;
					}
					// 验证是否重复提交
					// 传入令牌和缓存中不相等，重复提交
					if (!formToken.equals(redisFormToken)){
						writeMessage(request, response, "请勿重复提交");
						return false;
					}
					redisUtils.del("formToken:" + formToken);
					/*if (isRepeatSubmit(request)) {
						return false;
					}
					*/
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute("formToken");
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter("formToken");
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}

	/**
	 * <b>Description:</b> 错误信息写入response结果中
	 * @param response
	 * @param message
	 * @Note
	 * <b>Author：</b> cooper.xu
	 * <b>Date:</b> 2018年11月1日 下午1:29:45
	 */
	private void writeMessage(HttpServletRequest request,HttpServletResponse response, String message) {
		try {
			LOGGER.warn("TOKEN拦截失败"+request.getRequestURI());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			//response.sendRedirect(request.getHeader("Referer"));
			// 返回信息
			//String json = JSONObject.fromObject(new ResultInfo<>(CommonConstants.FAIL_CODE, message)).toString();
			response.getWriter().write("<html><head>" +
					"<meta charset=\"UTF-8\">" +
					"<title>FORMTOKEN操作失败</title>" +
					"<link rel=\"stylesheet\" href=\"/static/css/content.css?test&rnd=<%=Math.random()%>\" />" +
					"<link rel=\"stylesheet\" href=\"/static/css/general.css?rnd=<%=Math.random()%>\" />" +
					"<link rel=\"stylesheet\" href=\"/static/css/manage.css?rnd=<%=Math.random()%>\" />" +
					"</head><body><div class=\"operate\">" +
					"<div class=\"false\">" + message + "<br/>" +
					"<span class=\"jump\">如果浏览器没有跳转，请点击<a href=\""+request.getHeader("Referer")+"\">跳转链接</a></span></div>" +
					"<div class=\"false-img\"><img src=\"/static/images/operatefalse.jpg\" /></div>" +
					"</div></body></html>");
			response.getWriter().close();
		} catch (Exception e) {
			LOGGER.error("erp拦截器中写入response信息时发生异常", e);
		}
	}

}
