package com.vedeng.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <b>Description:</b><br> 全局获取request
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> RequestUtils
 * <br><b>Date:</b> 2017年5月3日 下午4:46:22
 */
public class RequestUtils {
	
	/**
	 * <b>Description:</b><br> 获取request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月3日 下午3:35:20
	 */
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		return request;
	}

}
