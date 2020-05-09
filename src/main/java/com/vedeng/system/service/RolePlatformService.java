package com.vedeng.system.service;

import com.vedeng.authorization.model.RolePlatform;
import com.vedeng.authorization.model.vo.RolePlatformVo;
import com.vedeng.common.service.BaseService;

import java.util.List;

/**
 * @author wayne.liu
 * @description 平台类
 * @date 2019/6/6 9:32
 */
public interface RolePlatformService extends BaseService {

	/**
	  * 查询角色与平台关系列表
	  * @author wayne.liu
	  * @date  2019/6/6 11:23
	  * @param roleId 角色Id
	  * @return list
	  */
	List<RolePlatform> queryList(Integer roleId);

	/**
	 * 查询角色与平台关系列表
	 * @author wayne.liu
	 * @date  2019/6/6 11:23
	 * @param roleId 角色Id
	 * @return list
	 */
	List<RolePlatformVo> queryListByRoleId(Integer roleId);

	/**
	 * 查询角色与平台关系列表
	 * @author wayne.liu
	 * @date  2019/6/6 11:23
	 * @param roleIds 角色Id集合
	 * @return list
	 */
	List<RolePlatformVo> queryListByIds(List<Integer> roleIds);
}
