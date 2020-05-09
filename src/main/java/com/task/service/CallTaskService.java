package com.task.service;

import com.vedeng.common.model.ResultInfo;

public interface CallTaskService {

	/**
	 * <b>Description:</b><br> 客服中心转座席失败详单信息同步
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 上午9:17:06
	 */
	ResultInfo failedRecordSync() throws Exception;

	/**
	 * <b>Description:</b><br> 通话记录客户信息更新(一周之内、无客户信息的通话记录)
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 上午10:59:01
	 */
	ResultInfo callRecordTraderModify()  throws Exception;

	/**
	 * <b>Description:</b><br> 通话信息补充（通话时长、录音地址）
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午2:31:30
	 */
	ResultInfo callRecordInfoSync() throws Exception;

	/**
	 * <b>Description:</b><br> 未同步的记录同步(呼叫中心服务器往erp同步)
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午3:29:51
	 */
	ResultInfo callRecordUnSync() throws Exception;

}
