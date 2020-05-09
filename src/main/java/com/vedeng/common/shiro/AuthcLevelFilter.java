package com.vedeng.common.shiro;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class AuthcLevelFilter extends FormAuthenticationFilter  {
	private static final Logger log = LoggerFactory.getLogger(AuthcLevelFilter.class);

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		// if (this.isLoginRequest(request, response)) {
		// return true;
		// } else {
		// HttpServletRequest httpRequest = (HttpServletRequest) request;
		// this.saveRequest(request);
		// String via = httpRequest.getHeader("Via");
		// String host = request.getRemoteHost();
		// System.out.println("host----------------" + host);
		// String remoteAddr = request.getRemoteAddr();
		// System.out.println("RemoteAddr----------------" + remoteAddr);
		// String localAddr = request.getLocalAddr();
		// System.out.println("localAddr----------------" + localAddr);
		// String serverName = request.getServerName();
		// System.out.println("serverName----------------" + serverName);
		// if (via != null && "nginx".equals(via.toLowerCase())) {//
		// // 如果是nginx，就不需要contextRelative上下文关系
		// System.out.println("========nginx=============");
		// System.out.println("==========denglu==========" +
		// this.getLoginUrl());
		// //setLoginUrl(request.getServerName()+"/login.do");
		//// this.saveRequest(request);
		// //this.saveRequestAndRedirectToLogin(request, response);
		//
		// WebUtils.issueRedirect(request, response, this.getLoginUrl(), null,
		// false);
		// } else {
		// WebUtils.issueRedirect(request, response, this.getLoginUrl());
		// }
		// return false;
		// }

		if (isLoginRequest(request, response)) {

			return true;

		} else {
			this.saveRequest(request);
			String via = ((HttpServletRequest) request).getHeader("Via");
			//String serverName = request.getServerName();
			
			 String host = request.getRemoteHost();
			 System.out.println("host----------------" + host);
			 String remoteAddr = request.getRemoteAddr();
			 System.out.println("RemoteAddr----------------" + remoteAddr);
			 String localAddr = request.getLocalAddr();
			 System.out.println("localAddr----------------" + localAddr);
			 String serverName = request.getServerName();
			 System.out.println("serverName----------------" + serverName);
			if (via != null && "nginx".equals(via.toLowerCase())) {
//				HttpServletRequest httpRequest = WebUtils.toHttp(request);
//				// 组装URL
//				StringBuilder returnUrl = new StringBuilder(request.getServerName());
//				returnUrl.append(httpRequest.getRequestURI());
//				Enumeration enumeration = httpRequest.getParameterNames();
//				if (enumeration != null) {
//					boolean first = true;
//					while (enumeration.hasMoreElements()) {
//						Object param = enumeration.nextElement();
//						if (param != null && httpRequest.getParameter(param.toString()) != null) {
//							if (first) {
//								returnUrl.append("?");
//							}
//							returnUrl.append(param).append("=").append(httpRequest.getParameter(param.toString()))
//									.append("&");
//							first = false;
//						}
//					}
//				}
//				String encodedReturnUrl = returnUrl.toString();
//				if (encodedReturnUrl.endsWith("&")) {
//					encodedReturnUrl = encodedReturnUrl.substring(0, encodedReturnUrl.length() - 1);
//				}
//				encodedReturnUrl = URLEncoder.encode(encodedReturnUrl, "UTF-8");
//				StringBuilder builder = new StringBuilder("/login.do");
//				builder.append(encodedReturnUrl);
//				setLoginUrl(builder.toString());
//				//returnUrl.append("/login.do");
//				//setLoginUrl(request.getServerName()+httpRequest.getRequestURI()+"/login.do");
//				redirectToLogin(request, response);
				WebUtils.issueRedirect(request, response, this.getLoginUrl(), null,false);
//				System.out.println("------------------"+getLoginUrl());
			}else{
				WebUtils.issueRedirect(request, response, this.getLoginUrl());
			}
			return false;
		}
	}


	@Override
	public void postHandle(ServletRequest request, ServletResponse response) throws Exception {
		
		
	}
	
	
}
