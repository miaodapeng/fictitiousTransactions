package com.vedeng.system.service;

import java.util.List;


import com.vedeng.authorization.model.UserLoginLog;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

/**
 * 
 * @ClassName: LoginLogService 
 * @Description: 用户登录日志功能
 * @author leo.yang
 * @date 2017年4月24日 下午1:41:07 
 *
 */
public interface LoginLogService extends BaseService {
	
	/**
	 * 
	 * @Title: querylistPage 
	 * @Description: 查询登录日志列表数据 
	 * @param @param userLoginLog
	 * @param @param page
	 * @param @return
	 * @return List<Action>
	 * @throws
	 */
	public List<UserLoginLog> querylistPage(UserLoginLog userLoginLog,Page page);
	
	/**
	 * <b>Description:</b><br> 保存或更新
	 * @param userLoginLog
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月22日 上午10:26:04
	 */
	int saveOrUpdate(UserLoginLog userLoginLog);
}
