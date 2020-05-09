package com.vedeng.system.service;

import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.DataSyncStatus;

/**
 * <b>Description:</b><br> 接口同步记录
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> DataSyncStatusService
 * <br><b>Date:</b> 2018年1月5日 下午2:57:00
 */
public interface DataSyncStatusService extends BaseService {
	/**
	 * <b>Description:</b><br> 新增同步记录
	 * @param dataSyncStatus
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午2:56:52
	 */
	void addDataSyncStatus(DataSyncStatus dataSyncStatus);
}
