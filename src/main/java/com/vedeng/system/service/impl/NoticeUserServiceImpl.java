package com.vedeng.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.dao.NoticeUserMapper;
import com.vedeng.system.model.Notice;
import com.vedeng.system.service.NoticeUserService;

@Service("noticeUserService")
public class NoticeUserServiceImpl extends BaseServiceimpl implements NoticeUserService {

	@Autowired
	@Qualifier("noticeUserMapper")
	private NoticeUserMapper noticeUserMapper;
	
	@Override
	public Integer getNoticeUserCount(Notice notice, User user) {
		// TODO Auto-generated method stub
		return noticeUserMapper.getNoticeUserCount(notice, user);
	}
}
