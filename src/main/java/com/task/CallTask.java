package com.task;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.task.service.CallTaskService;
import com.vedeng.common.model.ResultInfo;

/**
 * <b>Description:</b><br> 呼叫中心定时任务
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.task
 * <br><b>ClassName:</b> Call
 * <br><b>Date:</b> 2017年7月20日 上午9:10:06
 */
public class CallTask extends BaseTaskController{
	public static Logger logger = LoggerFactory.getLogger(CallTask.class);

	@Autowired
	@Qualifier("callTaskService")
	private CallTaskService callTaskService;
	
	/**
	 * <b>Description:</b><br> 客服中心转座席失败详单信息同步
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 上午9:11:16
	 */
	public void failedRecordSync(){
		try {
			Integer i = 0;
			Integer code = -1;
			ResultInfo resultInfo = callTaskService.failedRecordSync();
//			code = resultInfo.getCode();
//			do{
//				Thread.sleep(60000);//休眠60s后再次执行
//				ResultInfo recordSync = callTaskService.failedRecordSync();
//				code = recordSync.getCode();
//			}while(i<=10 && code.equals("-1"));
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 通话记录客户信息更新(一周之内、无客户信息的通话记录)
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 上午10:38:59
	 */
	public void callRecordTraderModify(){
		try {
			Integer i = 0;
			Integer code = -1;
			ResultInfo resultInfo = callTaskService.callRecordTraderModify();
			code = resultInfo.getCode();
			do{
				Thread.sleep(60000);//休眠60s后再次执行
				ResultInfo recordSync = callTaskService.callRecordTraderModify();
				code = recordSync.getCode();
			}while(i<=10 && code.equals("-1"));
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 呼叫中心通话信息补充（通话时长、录音地址)
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 上午10:41:22
	 */
	public void callRecordInfoSync(){
		try {
			Integer i = 0;
			Integer code = -1;
			ResultInfo resultInfo = callTaskService.callRecordInfoSync();
			code = resultInfo.getCode();
			do{
				Thread.sleep(60000);//休眠60s后再次执行
				ResultInfo recordSync = callTaskService.callRecordInfoSync();
				code = recordSync.getCode();
			}while(i<=10 && code.equals("-1"));
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 当天未同步的记录同步(呼叫中心服务器往erp同步)
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 上午10:43:00
	 */
	public void callRecordUnSync(){
		try {
			Integer i = 0;
			Integer code = -1;
			ResultInfo resultInfo = callTaskService.callRecordUnSync();
			code = resultInfo.getCode();
			do{
				Thread.sleep(60000);//休眠60s后再次执行
				ResultInfo recordSync = callTaskService.callRecordUnSync();
				code = recordSync.getCode();
			}while(i<=10 && code.equals("-1"));
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
}
