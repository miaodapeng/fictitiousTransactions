package com.vedeng.common.shiro;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.model.ResultJSON;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.ez.utils.StringUtil;
import com.vedeng.system.service.UserService;
import net.sf.json.JSONArray;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/**
 * <b>Description:</b><br>
 * 权限拦截器
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.shiro <br>
 *       <b>ClassName:</b> SecurityInterceptor <br>
 *       <b>Date:</b> 2017年8月31日 下午1:02:18
 */


public class SecurityInterceptor implements HandlerInterceptor {

	private UserService userService;
	private String exclude = ".*(\\.(js|css|jpg|png|txt|gif|jpeg|ico|bmp|json));.*(/static/).*";
	private List<Pattern> excludePattern = new ArrayList<Pattern>(1);

	public void init(){
		excludePattern.addAll(StringUtil.loadPattern(exclude).keySet());
	}
	private boolean isStaticUrl(String originatingUrl) {
		return StringUtil.match(excludePattern, originatingUrl);
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originatingUrl = urlPathHelper.getOriginatingRequestUri(request);
		//给op用 或者静态资源
		if(request.getRequestURI().contains("getgoodslistextrainfo")||isStaticUrl(originatingUrl)){
			return true;
		}
		//
		if (user != null && user.getIsAdmin() != 2) {
			String requestType = (String) request.getHeader("X-Requested-With");
			String url = request.getContextPath() + request.getServletPath();
			JSONArray jsonArray = JSONArray.fromObject(JedisUtils.get(userService.getRedisDbType()
					+ ErpConst.KEY_PREFIX_USER_PERMISSIONS + user.getUsername().toLowerCase() + user.getCompanyId()));
			@SuppressWarnings("unchecked")
			List<String> permissionList = (List<String>) JSONArray.toCollection(jsonArray, String.class);
			if (url.contains("erp")) {// 本地
				if (!permissionList.contains(url.substring(4))) {
					/** 判断是否是ajax提交方式 */
					if (requestType != null && requestType.equals("XMLHttpRequest")) {
						// 向http头添加 状态 sessionstatus
						 response.setHeader("permissionstatus", "nopermission");// 没有权限
						 response.setStatus(1001);// 没有权限的状态码
						// 向http头添加登录的url
						 response.getWriter().write(JsonUtils.convertObjectToJsonStr(ResultJSON.failed().message("nopermission")));
						 return false;
					} else {
						String pop = request.getQueryString();
						if (pop != null && pop.contains("pop")) {
							request.getRequestDispatcher("/WEB-INF/jsp/common/nopower_popup.jsp").forward(request,
									response);
						} else {
							request.getRequestDispatcher("/WEB-INF/jsp/common/nopower.jsp").forward(request, response);
						}
						return false;
					}
				}
//				return true;
			} else {// 线上
				if (!permissionList.contains(url)) {
					/** 判断是否是ajax提交方式 */
					if (requestType != null && requestType.equals("XMLHttpRequest")) {
						// 向http头添加 状态 sessionstatus
						response.setHeader("permissionstatus", "nopermission");// 没有权限
						response.setStatus(1001);// 没有权限的状态码
						// 向http头添加登录的url
						response.getWriter().write(JsonUtils.convertObjectToJsonStr(ResultJSON.failed().message("nopermission")));
						return false;
					} else {
						String pop = request.getQueryString();
						if (pop != null && pop.contains("pop")) {
							request.getRequestDispatcher("/WEB-INF/jsp/common/nopower_popup.jsp").forward(request,
									response);
						} else {
							request.getRequestDispatcher("/WEB-INF/jsp/common/nopower.jsp").forward(request, response);
						}
						return false;
					}
				}
			}

		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
