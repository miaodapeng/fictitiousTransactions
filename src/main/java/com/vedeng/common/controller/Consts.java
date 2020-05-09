package com.vedeng.common.controller;

public class Consts {

	
	public static final String SESSION_USER = "curr_user";
	
	public static final int EXP_SEARCH_SIZE = 10000;//导出列表每次查询多少条信息
	
	public static final int EXP_PRE_NUM = 50000;//每个工作簿显示多少条信息
	
	public static final int EXP_TEM_NUM = 5000;//内存中保留 x 条数据，以免内存溢出，其余写入 硬盘
	
}
