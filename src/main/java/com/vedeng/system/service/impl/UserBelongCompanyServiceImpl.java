package com.vedeng.system.service.impl;

import com.vedeng.authorization.dao.UserBelongCompanyMapper;
import com.vedeng.authorization.model.UserBelongCompany;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.UserBelongCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userBelongCompanyService")
public class UserBelongCompanyServiceImpl extends BaseServiceimpl implements UserBelongCompanyService {

	@Autowired
	@Qualifier("userBelongCompanyMapper")
	private UserBelongCompanyMapper userBelongCompanyMapper;

	@Override
	public UserBelongCompany getUserCompanyById(Integer id) {
		return userBelongCompanyMapper.getBelongCompanyById(id);
	}

	@Override
	public List<UserBelongCompany> queryAll() {
		return userBelongCompanyMapper.queryAll();
	}

	@Override
	public UserBelongCompany getUserCompanyByName(String name) {
		return userBelongCompanyMapper.getBelongCompany(name);
	}
}
