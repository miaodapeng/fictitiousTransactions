package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.*;
import com.vedeng.authorization.model.vo.RRoleActionGroupVo;
import com.vedeng.authorization.model.vo.RRoleActionVo;
import com.vedeng.authorization.model.vo.RoleVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SelectModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;

/**
 * <b>Description:</b><br> 角色操作接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> RoleService
 * <br><b>Date:</b> 2017年4月25日 下午3:38:52
 */
public interface RoleService extends BaseService {
	
	/**
	 * <b>Description: </b><br> 角色列表查询
	 * @param role
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:59:28
	 */
	List<Role> querylistPage(Role role, Page page);
	
	/**
	 * <b>Description:</b><br> 根据主键查询详情信息
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:59:39
	 */
	Role getRoleByKey(Role role);
	
	/**
	 * <b>Description: </b><br> 保存角色信息
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:59:47
	 */
	ResultInfo<?> insert(Role role);
	
	/**
	 * <b>Description: </b><br> 修改角色信息
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:59:59
	 */
	ResultInfo<?> update(Role role);
	
	/**
	 * <b>Description: </b><br> 获取所有角色
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午4:00:09
	 */
	List<Role> getAllRoles(Role role);
	
	/**
	 * <b>Description:</b><br> 查询用户角色 
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午4:00:18
	 */
	List<Role> getUserRoles(Integer userId);
	/**
	 * <b>Description:</b><br> 查询用户角色 
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午4:00:18
	 */
	List<Role> getUserRoles(User user);
	
	/**
	 * <b>Description:</b><br> 查询全部分组及对应的功能（即节点）
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午4:00:32
	 */
	List<SelectModel> queryMenuList();

	/**
	  * 根据平台Id查询对应全部分组和对应的功能点
	  * @author wayne.liu
	  * @date  2019/6/11 11:21
	  * @param platformId
	  * @return
	  */
	List<Actiongroup> queryMenuListByPlatformId(Integer platformId);

	/**
	 * <b>Description:</b><br> 保存角色对应的功能信息
	 * @param roleId
	 * @param groupIdArr
	 * @param actionIdArr
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午4:00:45
	 */
	ResultInfo<?> saveMenu(Integer roleId,List<String> groupIdArr,List<String> actionIdArr,Integer platformId);
	
	
	/**
	 * <b>Description:</b><br> 获取角色对应平台功能点
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午4:00:56
	 */
	List<RRoleAction> getRoleAction(Role role,Integer platformId);
	
	/**
	 * <b>Description:</b><br> 获取角色对应平台功能分组
	 * @param role
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午3:55:28
	 */
	List<RRoleActiongroup> getRoleGroup(Role role, Integer platformId);


	/**
	  * 获取角色全部功能点
	  * @author wayne.liu
	  * @date  2019/6/17 13:12
	  * @param
	  * @return
	  */
	List<RRoleActionVo> getRoleAllAction(Role role,Integer platformId);

	/**
	  * 获取角色全部功能组
	  * @author wayne.liu
	  * @date  2019/6/17 13:13
	  * @param
	  * @return
	  */
	List<RRoleActionGroupVo> getRoleAllGroup(Role role,Integer platformId);

	/**
	 * <b>Description:</b><br> 删除角色
	 * @param role 角色bean
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:39:07
	 */
	Integer delete(Role role);
	
	/**
     * <b>Description:</b><br> 查询功能分组下的角色
     * @param actiongroupId 功能分组ID
     * @return List<Role>
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 下午3:14:46
     */
    List<Role> getRoleByActiongroupId(Integer actiongroupId);
    
    /**
	 * <b>Description:</b><br> 查询功能下的角色
	 * @param actionId 功能ID
	 * @return List<Role>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:12:49
	 */
    List<Role> getRoleByActionId(Integer actionId);
    /**
     * 
     * <b>Description:</b><br> 根据有角色名获取对应的USER的ID
     * @param roleName
     * @param companyId
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年7月13日 上午11:01:08
     */
    List<Integer> getUserIdByRoleName(String roleName, Integer companyId);

	/**
	  * 添加角色数据
	  * @author wayne.liu
	  * @date  2019/6/6 11:54
	  * @param roleVo
	  * @return
	  */
	ResultInfo<?> insertRole(RoleVo roleVo);

	/**
	  * 更新角色数据
	  * @author wayne.liu
	  * @date  2019/6/6 11:54
	  * @param roleVo
	  * @return 
	  */
	ResultInfo<?> updateRole(RoleVo roleVo);


	/**
	  * 分页搜索角色列表
	  * @author wayne.liu
	  * @date  2019/6/6 14:39
	  * @param roleVo 角色名称/应用平台
	  * @param page 分页参数
	  * @return list
	  */
	List<Role> queryListPage(RoleVo roleVo, Page page);
}
