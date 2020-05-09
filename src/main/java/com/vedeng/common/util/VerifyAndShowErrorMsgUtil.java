
package com.vedeng.common.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.exception.ShowErrorMsgException;

/**
 * <b>Description: 用于校验并提示错误信息的工具类</b><br>
 * <b>Author: Franlin</b>
 * 
 * @fileName ShowErrorMsgUtil.java <br>
 *           <b>Date: 2018年5月11日 上午11:17:20 </b>
 *
 */
public class VerifyAndShowErrorMsgUtil {
	/**
	 * 
	 * <b>Description: 将String转换为BigDecimal,错误则提示</b><br>
	 * 
	 * @param value
	 *            数字
	 * @param errorMsg
	 *            错误提示信息
	 * @return
	 * @throws ShowErrorMsgException
	 *             <b>Author: Franlin</b> <br>
	 *             <b>Date: 2018年5月11日 上午11:21:16 </b>
	 */
	public static BigDecimal verifyBigDecimalByString(String value, String errorMsg) throws ShowErrorMsgException {
		if (StringUtil.isBlank(value))
			throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, errorMsg);
		BigDecimal bd = null;
		try {
			bd = new BigDecimal(value.trim());
		} catch (Exception e) {
			throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, errorMsg);
		}
		return bd;
	}

	/**
	 * 
	 * <b>Description: 将String转换为Integer,错误则提示</b><br>
	 * 
	 * @param value
	 * @param errorMsg
	 *            错误提示信息
	 * @return
	 * @throws ShowErrorMsgException
	 *             <b>Author: Franlin</b> <br>
	 *             <b>Date: 2018年5月11日 上午11:24:03 </b>
	 */
	public static Integer verifyIntegerByString(String value, String errorMsg) throws ShowErrorMsgException {
		if (StringUtil.isBlank(value))
			throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, errorMsg);
		Integer ter = null;
		try {
			ter = Integer.parseInt(value.trim());
		} catch (Exception e) {
			throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, errorMsg);
		}
		return ter;
	}

	/**
	 * 
	 * <b>Description: 是否是闰年</b><br>
	 * 
	 * @param year
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年5月9日 上午9:15:08 </b>
	 */
	public static boolean isLeapYear(int year) {
		if (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <b>Description: 将String转换为Date,错误则提示</b><br>
	 * 
	 * @param value
	 *            时间
	 * @param format
	 *            时间格式:默认 yyyy-MM-dd
	 * @param errorMsg
	 *            错误信息
	 * @return
	 * @throws ShowErrorMsgException
	 *             <br>
	 *             时间为空, errorCode:0001; 时间格式错误, errorCode: 0002; 时间填写错误, errorCode
	 *             0003 <b>Author: Franlin</b> <br>
	 *             <b>Date: 2018年5月11日 上午11:27:09 </b>
	 */
	public static Date verifyDateByString(String value, String format, String errorMsg) throws ShowErrorMsgException {
		if (StringUtil.isBlank(value))
			throw new ShowErrorMsgException("0001", errorMsg);
		if (StringUtil.isBlank(format)) {
			format = "yyyy-MM-dd";
		}
		if (StringUtil.isBlank(errorMsg)) {
			errorMsg = "";
		}
		value = value.trim();
		format = format.trim();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (Exception e) {
			throw new ShowErrorMsgException("0002", errorMsg);
		}
		// 日历对象
		Calendar calendar = Calendar.getInstance();
		// 设置当前日期
		calendar.setTime(date);
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 获取日
		int day = calendar.get(Calendar.DATE);

		// System.out.println("year = " + year);
		// System.out.println("month = " + month);
		// System.out.println("day = " + day);

		// 将日期重新拼接成字符串
		String new_value = StringUtil.repaceAll(format, "yyyy", year + "");
		new_value = StringUtil.repaceAll(new_value, "MM", month + "");
		new_value = StringUtil.repaceAll(new_value, "dd", day + "");

		System.out.println("新日期 = " + new_value);

		// 时间字符串不相等
		if (!value.equalsIgnoreCase(new_value)) {
			throw new ShowErrorMsgException("0003", errorMsg);
		}

		// // 校验月份
		// if(value.indexOf(year + "") == -1 || month > 12 || month < 1)
		// {
		// throw new ShowErrorMsgException("0002", errorMsg);
		// }
		//
		// if(month == 2)
		// {
		// // 闰年 29
		// if(isLeapYear(year))
		// {
		// if(day < 0 || day > 29)
		// throw new ShowErrorMsgException("0003");
		// }
		// if(day < 0 || day > 28)
		// throw new ShowErrorMsgException("0004");
		// }
		// // 30d
		// else if(month == 4 || month == 6 || month == 9 || month == 11)
		// {
		// if(day < 0 || day > 30)
		// throw new ShowErrorMsgException("0005");
		// }
		// // 31 : 1 3 5 7 8 10 12
		// else
		// {
		// if(day < 0 || day > 31)
		// throw new ShowErrorMsgException("0006");
		// }

		return date;
	}

}
