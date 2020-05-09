package com.vedeng.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.common.constants.Contant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	public static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	// 短日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATE_FORMAT_P = "yyyy.MM.dd";
	public static String DATE_FORMAT_NO = "yyyyMMdd";

	// 长日期格式
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String TIME_FORMAT_T = "HH:mm:ss";

	/**
	 * <b>Description:</b><br>
	 * 获取当前系统时间
	 * 
	 * @param str
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年9月28日 下午2:23:54
	 */
	public static String getNowDate(String str) {
		SimpleDateFormat df = null;
		if (StringUtils.isBlank(str)) {
			df = new SimpleDateFormat(DATE_FORMAT);// 设置日期格式
		} else {
			df = new SimpleDateFormat(str);// 设置日期格式
		}
		return df.format(new Date());// new Date()为获取当前系统时间
	}

	/**
	 * <b>Description:</b><br>
	 * 获取今年
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年1月11日 下午1:17:41
	 */
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	/**
	 * <b>Description:</b><br>
	 * 获取当前系统时间
	 * 
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年9月28日 下午2:24:12
	 */
	public static long gainNowDate() {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
			return sf.parse(sf.format(sysTimeMillis())).getTime();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return 0l;
	}

	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convertLong(String date, String format) {
		try {
			if (StringUtils.isNotBlank(date)) {
				if (StringUtils.isBlank(format))
					format = DateUtil.TIME_FORMAT;
				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return 0l;
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convertString(long time, String format) {
		if (time > 0L) {
			if (StringUtils.isBlank(format)) {
				format = DateUtil.TIME_FORMAT;
			}
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 获取当前系统的日期
	 * 
	 * @return
	 */
	public static long sysTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * @Title: longToDate @Description: 时间字符串转日期格式 @param @param time @param @param
	 *         format @param @return @return String @throws
	 */
	public static Date StringToDate(String time, String format) {
		Date res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			res = simpleDateFormat.parse(time);
			return res;
		} catch (ParseException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 日期转long
	 * 
	 * @param date
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月8日 下午2:35:52
	 */
	public static long DateToLong(Date date) {
		if (date != null) {
			return date.getTime();
		} else {
			return 0l;
		}
	}

	/**
	 * 
	 * @Title: longToDate @Description: 时间字符串转日期格式 @param @param time @param @param
	 *         format @param @return @return String @throws
	 */
	public static Date StringToDate(String time) {
		Date res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			res = simpleDateFormat.parse(time);
			return res;
		} catch (ParseException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	public static String getStringDateNow() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT_NO);
		return now.format(format);
	}

	/**
	 * <b>Description:</b><br>
	 * 日期转时间字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月26日 下午3:45:36
	 */
	public static String DateToString(Date date, String format) {
		if (format == null) {
			return new SimpleDateFormat(DATE_FORMAT).format(date);
		}
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * <b>Description:</b><br>
	 * 获取几个月前的日期 YYYY-MM-DD
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月29日 下午5:18:05
	 */
	public static String DatePreMonth(Date date, Integer i, String format) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, i);
		Date m = c.getTime();
		if (format == null) {
			return new SimpleDateFormat(DATE_FORMAT).format(m);
		}
		return new SimpleDateFormat(format).format(m);
	}

	/**
	 * <b>Description:</b><br>
	 * 获取几天前的时间戳
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年7月25日 下午1:30:47
	 */
	public static long getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);

		Date time = now.getTime();
		return time.getTime();
	}

	/**
	 * 获取几天后的时间戳
	 * <p>
	 * Title: getDateAfter
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param d
	 * @param day
	 * @return
	 * @author Bill
	 * @date 2019年4月8日
	 */
	public static long getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);

		Date time = now.getTime();
		return time.getTime();
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}

	/**
	 * 两个时间相差距离多少天
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param endTime
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static long getDistanceTimeDays(String startTime, String endTime) {
		if(StringUtils.isBlank(startTime)||StringUtils.isBlank(endTime)){
			return 0;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try {
			long time1 = df.parse(startTime).getTime();
			long time2 = df.parse(endTime).getTime();
			long diff=Math.abs(time1 - time2);
			day = diff / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return day;
	}

	/**
	 * 将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
	 *
	 * @param date
	 *            日期字符串
	 * @return 返回格式化的日期
	 * @throws ParseException
	 *             分析时意外地出现了错误异常
	 */
	public static String strToDateFormat(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		formatter.setLenient(false);
		Date newDate = formatter.parse(date);
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(newDate);
	}

	/**
	 * 将字符串格式yyyy-MM-dd的字符串转为日期，格式"yyyyMMdd"
	 *
	 * @param date
	 *            日期字符串
	 * @return 返回格式化的日期
	 * @throws ParseException
	 *             分析时意外地出现了错误异常
	 */
	public static String strDateToFormat(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		Date newDate = formatter.parse(date);
		formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(newDate);
	}

	/**
	 * <b>Description:</b><br>
	 * 获取当前日，前x个月前的当前日期
	 * 
	 * @param i
	 *            当前月加i个月
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年10月17日 上午10:41:01
	 */
	public static String getDayOfMonth(int i) {
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, i);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(cal_1.getTime());
	}

	/**
	 * @description 获取当前是当月的第几天
	 * @author bill
	 * @param
	 * @date 2019/4/22
	 */
	public static int getFirstDayOfMonth() {
		// 获取当前日期
		Calendar cal_1 = Calendar.getInstance();
		return cal_1.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <b>Description:</b><br>
	 * 获取当月第一天
	 * 
	 * @param i
	 *            当前月加i个月
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年9月28日 下午2:19:18
	 */
	public static String getFirstDayOfMonth(int i) {
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, i);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(cal_1.getTime());
	}

	public static String getLastDayOfMonth(int j) {
		// 获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, (j + 1));
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为0号,当前日期上个月最后一天
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return format.format(cale.getTime());
	}

	/**
	 * 获取最近12个月
	 */
	public static String[] getLast12Months() {
		String[] last12Months = new String[12];
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)); // 要先+1,才能把本月的算进去
		for (int i = 0; i < 12; i++) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); // 逐次往前推1个月
			String m = (cal.get(Calendar.MONTH) + 1) + "";
			if (m.length() == 1) {
				m = "0" + m;
			}
			last12Months[11 - i] = cal.get(Calendar.YEAR) + "." + m;
		}
		return last12Months;
	}

	/**
	 * 获取最近12个月包含本月 时间格式: yyyy.MM 年.月 升序
	 */
	public static String[] getLast12MonthsWithThisMonth() {
		String[] last12Months = new String[12];

		// 获取当前时间
		Date now = new Date();
		// 获取
		for (int i = 11; i >= 0; i--) {
			Date lastDay = DateUtil.getThisMonthLastDayByDateTime(DateUtil.subDateByMonths(now, -i));
			last12Months[last12Months.length - 1 - i] = parseString(lastDay, "yyyy.MM");
		}
		return last12Months;
	}

	/**
	 * 
	 * <b>Description: 获取当前时间的当前月份的最后一天</b><br>
	 * 
	 * @param date
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月11日 下午4:21:09 </b>
	 */
	public static Date getThisMonthLastDayByDateTime(Date date) {
		if (null == date)
			return null;
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.set(Calendar.DATE, cd.getActualMaximum(5));
		return cd.getTime();
	}

	/**
	 * 
	 * <b>Description: 将Date时间的月份减去或者加上num</b><br>
	 * 
	 * @param date
	 * @param num
	 *            负--减/正--加
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月7日 上午11:20:50 </b>
	 */
	public static Date subDateByMonths(Date date, int num) {
		if (null == date)
			return null;
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.MONTH, num);
		return cd.getTime();
	}

	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	/**
	 * 获取指定日期当月的第一天
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static String getFirstDayOfGivenMonth(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 0);
			return sdf.format(calendar.getTime());
		} catch (ParseException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	/**
	 * 
	 * <b>Description: 指定时间加或减天数后的时间</b><br>
	 * 
	 * @param date
	 * @param num
	 *            正数--加/负数--减
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月7日 上午11:01:31 </b>
	 */
	public static Date subDateByDays(Date date, int num) {
		if (null == date)
			return null;
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, num);
		return cd.getTime();
	}

	/**
	 * 
	 * <b>Description: 获取Date的上一天时间</b><br>
	 * 
	 * @param date
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月6日 下午12:03:45 </b>
	 */
	public static Date getPreviousDayByDateTime(Date date) {
		return subDateByDays(date, -1);
	}

	/**
	 * 
	 * <b>Description: 获取Date的第几天</b><br>
	 * 
	 * @param date
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月6日 上午10:11:16 </b>
	 */
	public static Integer getMonthOfDayByDateTime(Date date) {
		if (null == date)
			return null;
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);

		return cd.get(Calendar.DATE);
	}

	/**
	 * 
	 * <b>Description:获取Date的当天时间的第几个小时</b><br>
	 * 
	 * @param date
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月14日 上午10:20:26 </b>
	 */
	public static Integer getHourByDateTime(Date date) {
		if (null == date)
			return null;
		Calendar cd = Calendar.getInstance();

		cd.setTime(date);

		return cd.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 获取传入日期下一个月的1号
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年8月8日 下午1:33:03
	 */
	public static String getNextMonthFirst(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, 1);
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow.getMinimum(Calendar.DATE));
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 
	 * <b>Description: 将Date转换为String</b><br>
	 * 
	 * @param date
	 * @param format
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年8月9日 下午4:13:25 </b>
	 */
	public static String parseString(Date date, String format) {
		if (null == date)
			return null;

		if (null == format || format.trim().length() == 0)
			format = DATE_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);
	}

	/**
	 * 
	 * <b>Description: 将以秒为单位的时间转换为 h时m分s秒</b><br>
	 * 
	 * @param seconds
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月12日 下午4:02:33 </b>
	 */
	public static String customShowTime(Integer seconds) {
		if (null == seconds)
			return null;
		String timeStr = seconds + "秒";
		if (seconds > 60) {
			Integer second = seconds % 60;
			Integer min = seconds / 60;
			timeStr = min + "分" + second + "秒";
			if (min > 60) {
				min = (seconds / 60) % 60;
				Integer hour = (seconds / 60) / 60;
				timeStr = hour + "时" + min + "分" + second + "秒";
				// if (hour > 24)
				// {
				// hour = ((seconds / 60) / 60) % 24;
				// long day = (((seconds / 60) / 60) / 24);
				// timeStr = day + "天" + hour + "时" + min + "分" + second
				// + "秒";
				// }
			}
		}
		return timeStr;
	}

	public static String getCalender(String dateStr) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(dateStr)); // 判断方法
		int dayForWeek = 0;
		String weekStr = "";
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		switch ((dayForWeek - 1)) {
		case 0:
			weekStr = "星期一";
			break;
		case 1:
			weekStr = "星期二";
			break;
		case 2:
			weekStr = "星期三";
			break;
		case 3:
			weekStr = "星期四";
			break;
		case 4:
			weekStr = "星期五";
			break;
		case 5:
			weekStr = "星期六";
			break;
		case 6:
			weekStr = "星期日";
			break;
		}
		return weekStr;
	}

	// 判断某个时间是否是今天
	public static boolean isToday(String str){
		// 判空
		if(EmptyUtils.isBlank(str)){
			return false;
		}

		// 当前时间
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
		//获取今天的日期
		String nowDay = sf.format(now);

		// 判断是否是今天
		if(nowDay.equals(str)){
			return true;
		}
		return false;
	}

	/**
	* @Description: 计算当前时间推迟多少天后的时间
	* @Param: []
	* @return: void
	* @Author: addis
	* @Date: 2019/9/26
	*/
    public static String datePostponeTime(Integer time){
		Date date=new Date();
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(s.format(date));
		String str1=s.format(date);//当前的时间

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, time);//计算多少天后的时间
		String str2=s.format(c.getTime());
		return str2;
	}

	/**
	 * 获取当前时间yyyy-MM-dd毫秒数
	 * @return
	 */
	public static long getNowDayMillisecond (){
		SimpleDateFormat df =  new SimpleDateFormat(DATE_FORMAT);
		String nowDay = df.format(new Date());
		try {
			return df.parse(nowDay).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * <b>Description:</b>获取当前日期的时间戳<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/2
	 */
	public static long getNowByDateFormat(){
		try {
			SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
			return sf.parse(sf.format(sysTimeMillis())).getTime();
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return 0l;
	}
}
