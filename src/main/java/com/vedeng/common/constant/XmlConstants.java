package com.vedeng.common.constant;


/**
 * <P>Description：(定义常量)</P>
 * @version 1.0
 * @author：CreateDate：2011-8-29
 */
public class XmlConstants {
	public static final String SUCCESS = "00: 解析xml成功";
	public static final String NODE_ERROR = "01: 传入参数为空或格式错误，请检查各节点是否为有效XML节点";
	public static final String BUILD_ERROR = "02: 读取失败";
	public static final String CONNECT_ERROR = "03: 通讯中断";
	public static final String CONNECTION_ERROR = "04: 连接异常";
	public static final String CODE_ERROR = "05：编码格式错误,不支持的字符编码";
	public static final String DECODE_ERROR = "06:参数解码失败";
	public static final String ARG_ERROR_OR_NO_ROWS_SELECTED = "07:参数错误或记录为空";
	public static final String PWD_OR_CONS_NO_ERROR = "08:用户账号或密码错误";
	
	public static final String OTHERE_RROR = "99: 其他错误";
	public static final String UTF = "UTF-8";
	public static final int PARAMETER_CLIENT = 1; // 读取95598库的jdbc连接配置
	public static final int PARAMETER_SERVICE = 2; // 读取运行管理系统库的jdbc连接配置

	public static final String FAIL = "0";
	public static final String OK = "1";
	public static final String MSG = "msg";
	public static final String hibernateDao = "hibernateDao";
	public static final String GBK = "GBK";
	//充换电单查询业务    
	public static final String ROOT_NODE = "DATA";//根节点
	public static final String CHARGE_TYPE = "01";//充电业务  
	public static final String REPLACE_TYPE = "02";//换电业务
	public static final String TIME_FORMAT="YYYY-MM-DD HH24:MI:SS"; //时间格式
	public static final String DATE_FORMAT="YYYY-MM-DD";//日期格式
	// end
	public static final String RESULT_STR = "RESULT";
	public static final String ROOT_NODE_STR = "DATA";
	public static final String ERROR_STR = "ERROR";
	public static final String CONS_STR = "CONS";
	public static final String CAR_LIST_STR = "CAR_LIST";
	public static final String BATTERY_LIST_STR = "BATTERY_LIST";
	public static final String CAR_NO_STR = "CAR_NO";
	public static final String WKST_LIST_STR = "WKST_LIST";
	public static final String WKST_STR = "WKST";
	public static final String DATE_BEGIN_STR = "DATE_BEGIN";
	public static final String DATE_END_STR = "DATE_END";
	public static final String APP_NO_STR = "APP_NO";
	public static final String QUERY_PWD_STR = "QUERY_PWD";

	public static final String elmentXml="DATA";   //英大xml 跟节点定义
	
}
