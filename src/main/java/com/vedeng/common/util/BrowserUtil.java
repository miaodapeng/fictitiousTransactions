package com.vedeng.common.util;

/**
 * <b>Description:</b><br>
 * 浏览器
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 * 		<b>PackageName:</b> com.vedeng.common.util <br>
 * 		<b>ClassName:</b> BrowserUtil <br>
 * 		<b>Date:</b> 2017年7月20日 下午2:18:44
 */
public class BrowserUtil {
	/**
	 * <b>Description:</b><br> 获取浏览器版本信息
	 * @param agent String agent=request.getHeader("User-Agent").toLowerCase();
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 下午2:19:18
	 */
	public String getBrowserName(String agent) {
		if (agent.indexOf("msie 7") > 0) {
			return "ie7";
		} else if (agent.indexOf("msie 8") > 0) {
			return "ie8";
		} else if (agent.indexOf("msie 9") > 0) {
			return "ie9";
		} else if (agent.indexOf("msie 10") > 0) {
			return "ie10";
		} else if (agent.indexOf("msie") > 0) {
			return "ie";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("opera") > 0) {
			return "opera";
		} else if (agent.indexOf("firefox") > 0) {
			return "firefox";
		} else if (agent.indexOf("webkit") > 0) {
			return "webkit";
		} else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
			return "ie11";
		} else {
			return "Others";
		}
	}
}
