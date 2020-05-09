package com.vedeng.system.service.impl;

import com.vedeng.authorization.dao.RolePlatformMapper;
import com.vedeng.authorization.model.RolePlatform;
import com.vedeng.authorization.model.vo.RolePlatformVo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.RolePlatformService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 角色应用平台实现类
 * @author wayne.liu
 * @description 
 * @date 2019/6/6 11:32
 */
@Service("rolePlatformService")
public class RolePlatformServiceImpl extends BaseServiceimpl implements RolePlatformService {

	@Autowired
	@Qualifier("rolePlatformMapper")
	private RolePlatformMapper rolePlatformMapper;

	@Override
	public List<RolePlatform> queryList(Integer roleId) {
		return rolePlatformMapper.queryList(roleId);
	}

	@Override
	public List<RolePlatformVo> queryListByIds(List<Integer> roleIds) {
		if(CollectionUtils.isNotEmpty(roleIds)){
			return rolePlatformMapper.queryListByIds(roleIds);
		}
		return Collections.emptyList();
	}

	@Override
	public List<RolePlatformVo> queryListByRoleId(Integer roleId) {
		return rolePlatformMapper.queryListByRoleId(roleId);
	}
}
