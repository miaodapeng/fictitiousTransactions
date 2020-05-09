package com.vedeng.system.service;

import com.vedeng.authorization.model.User;

public interface ReadService {
	
	/**
	 * <b>Description:</b><br> 新增产品检索new标志
	 * @param read
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月3日 上午11:00:59
	 */
	Integer saveRead(User user);
	
	/**
	 * <b>Description:</b><br> 查询当前用户是否已点过产品检索
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月3日 上午11:05:48
	 */
	Integer getReadByUserId(User user);
	
	/**
	 * <b>Description:</b><br> 上架新品后删除所有已读标志
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月3日 上午11:30:13
	 */
	Integer delAllReadByCompanyId(Integer companyId);

}
