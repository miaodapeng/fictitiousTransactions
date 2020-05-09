package com.vedeng.system.service.impl;

import com.vedeng.authorization.dao.DataAuthorityMapper;
import com.vedeng.authorization.model.RRoleDataAuthority;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.service.DataAuthorityService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("roleDataAuthorityService")
public class RoleDataAuthorityServiceImpl extends BaseServiceimpl implements DataAuthorityService {

	@Autowired
	@Qualifier("dataAuthorityMapper")
	private DataAuthorityMapper dataAuthorityMapper;

	@Override
	public void saveDataAuthority(String data,Integer roleId) {
		List<RRoleDataAuthority> addList = new ArrayList<>();

		dataAuthorityMapper.delete(roleId);
		if(StringUtils.isEmpty(data)){

			return;
		}

		List<String> list = Arrays.asList(data.split(","));;

		for(String s : list){
			RRoleDataAuthority add = new RRoleDataAuthority();
			add.setRoleId(roleId);
			add.setPlatformId(Integer.parseInt(s));
			addList.add(add);
		}
		dataAuthorityMapper.insertDataAuthorityBatch(addList);
	}

	@Override
	public List<RRoleDataAuthority> queryListByRoleId(Integer roleId) {
		return dataAuthorityMapper.queryList(roleId);
	}
}
