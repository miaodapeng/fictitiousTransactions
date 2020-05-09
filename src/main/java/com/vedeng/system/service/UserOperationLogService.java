package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.UserOperationLog;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

/**
 * <b>Description:</b><br> 用户操作日志功能
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> UserOperationLogService
 * <br><b>Date:</b> 2017年4月25日 上午11:43:42
 */
public interface UserOperationLogService extends BaseService {

	/**
	 * <b>Description:</b><br> 操作日志列表
	 * @param userOperationLog
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月25日 上午10:44:36
	 */
	public List<UserOperationLog> querylistPage(UserOperationLog userOperationLog,Page page);
	
	/**
	 * <b>Description:</b><br> 查询
	 * @param userOperationLogId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月31日 下午1:06:32
	 */
	public UserOperationLog getUserOperationLogByKey(Integer userOperationLogId);
	
	/**
	 * <b>Description:</b><br> 保存
	 * @param userOperationLog
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月31日 下午1:07:41
	 */
	Integer saveUserOperationLog(UserOperationLog userOperationLog);
}
