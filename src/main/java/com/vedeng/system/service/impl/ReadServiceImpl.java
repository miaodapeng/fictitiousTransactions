package com.vedeng.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.dao.ReadMapper;
import com.vedeng.system.model.Read;
import com.vedeng.system.service.ReadService;

@Service("readService")
public class ReadServiceImpl extends BaseServiceimpl implements ReadService {
	
	@Resource
	private ReadMapper readMapper;

	@Override
	public Integer saveRead(User user) {
		Integer res = getReadByUserId(user);
		int r = 0;
		if(res == 0){
			Read read = new Read();
			read.setCompanyId(user.getCompanyId());
			read.setUserId(user.getUserId());
			r = readMapper.insertSelective(read);
		}
		return r;
	}

	/**
	 * <b>Description:</b><br> 查询当前用户是否已点过产品检索
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月3日 上午11:05:48
	 */
	@Override
	public Integer getReadByUserId(User user) {
		return readMapper.getReadByUserId(user);
	}

	@Override
	public Integer delAllReadByCompanyId(Integer companyId) {
		return readMapper.delAllReadByCompanyId(companyId);
	}

}
