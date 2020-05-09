package com.vedeng.common.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <b>Description:</b><br> 验证入参集合或数组是否为空
 * @author duke.li
 * @Note
 * <b>ProjectName:</b> api.vedeng.com
 * <br><b>PackageName:</b> com.vedeng.common.tool.util
 * <br><b>ClassName:</b> EmptyUtils
 * <br><b>Date:</b> 2018年10月25日 下午7:03:15
 */
public class EmptyUtils {

	/**
	 * 	String字符串使用org.apache.commons.lang3.StringUtils包中
	 * 	isEmpty、isNotEmpty：验证null和空字符串（不包含带空格的字符串）
	 * 	StringUtils.isEmpty(" ") = false;
	 * 	StringUtils.isNotEmpty(" ") = true;
	 * 
	 * 	isBlank、isNotBlank：验证null和空字符串（包括带空格的字符串）
	 *  StringUtils.isBlank(" ") = true;
	 *  StringUtils.isNotBlank(" ") = false;
	 */
	
	
	/**
	 * <b>Description:</b> 字符串判断为空（不包含空格）
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午7:07:53
	 */
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * <b>Description:</b> 字符串判断非空（不包含空格）
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午7:07:37
	 */
	public static boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}
	
	/**
	 * <b>Description:</b> 字符串判断为空（包含空格）
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午7:07:18
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}
	
	/**
	 * <b>Description:</b> 字符串判断非空（包含空格）
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午7:06:47
	 */
	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}
	
	/**
	 * <b>Description:</b> 集合验证空
	 * @param list
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:53:40
	 */
	public static boolean isEmpty(List<?> list) {
		return CollectionUtils.isEmpty(list);
	}

	/**
	 * <b>Description:</b> 集合验证非空
	 * @param list
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:54:13
	 */
	public static boolean isNotEmpty(List<?> list) {
		return CollectionUtils.isNotEmpty(list);
	}

	/**
	 * <b>Description:</b> 集合验证空
	 * @param list
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:53:40
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * <b>Description:</b> 集合验证非空
	 * @param list
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:54:13
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		if (map != null && !map.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * <b>Description:</b> 验证数组为空
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:56:51
	 */
	public static boolean isEmpty(String[] str) {
		if (str == null || str.length == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * <b>Description:</b> 验证数组非空
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:57:06
	 */
	public static boolean isNotEmpty(String[] str) {
		if (str != null && str.length > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * <b>Description:</b> 验证数组为空
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:56:51
	 */
	public static boolean isEmpty(Integer[] it) {
		if (it == null || it.length == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * <b>Description:</b> 验证数组非空
	 * @param str
	 * @return boolean
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年10月25日 下午6:57:06
	 */
	public static boolean isNotEmpty(Integer[] it) {
		if (it != null && it.length > 0) {
			return true;
		}
		return false;
	}
}
