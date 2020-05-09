package com.vedeng.system.service.impl;

import com.vedeng.authorization.dao.PlatformMapper;
import com.vedeng.authorization.model.*;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("platformService")
public class PlatformServiceImpl extends BaseServiceimpl implements PlatformService {

	@Autowired
	@Qualifier("platformMapper")
	private PlatformMapper platformMapper;
	
	@Override
	public List<Platform> queryList() {
		return platformMapper.queryPlatformList();
	}

	@Override
	public Platform queryById(Integer id) {
		return platformMapper.queryPlatformById(id);
	}
}
