package com.vedeng.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Description:</b><br> 正则验证工具类
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.util
 * <br><b>ClassName:</b> RegexUtil
 * <br><b>Date:</b> 2017年11月28日 下午2:10:33
 */
public class RegexUtil {
	/**
	 * <b>Description:</b><br> 
	 * @param regex 正则表达式
	 * @param str
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月28日 下午2:10:43
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
