package com.vedeng.system.service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Notice;

public interface NoticeUserService extends BaseService {
	/**
	 * 查看是否有该用户查看最新公告的查看日志
	 * <b>Description:</b><br> 
	 * @param notice
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年9月3日 上午11:06:55
	 */
	Integer getNoticeUserCount(Notice notice,User user);  
}
