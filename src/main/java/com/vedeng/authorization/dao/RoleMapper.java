package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.vo.RRoleActionGroupVo;
import com.vedeng.authorization.model.vo.RRoleActionVo;
import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.Actiongroup;
import com.vedeng.authorization.model.RRoleAction;
import com.vedeng.authorization.model.RRoleActiongroup;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;

public interface RoleMapper {
    
	/**
	 * @Title: querylistPage 
	 * @Description: 角色列表查询  
	 * @param @param map
	 * @param @return
	 * @return List<Role>
	 * @throws
	 */
    List<Role> querylistPage(Map<String, Object> map);

    Role selectByPrimaryKey(Integer roleId);
    
    /**
     * 
     * @Title: getRoleByKey 
     * @Description: 根据主键查询节点详细信息 
     * @param @param record
     * @param @return
     * @return Role
     * @throws
     */
    Role getRoleByKey(Role record);
    
    /**
     * 
     * @Title: insert 
     * @Description: 保存角色信息 
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    int insert(Role record);
    
    /**
     * 
     * @Title: update 
     * @Description: 修改角色信息 
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    int update(Role record);
	
	/**
     * 
     * @Title: getAllRoles 
     * @Description: 获取所有角色  
     * @param @return
     * @return List<Role>
     * @throws
     */
    List<Role> getAllRoles(Role role);
    
    /**
     * 
     * @Title: getUserRoles 
     * @Description: 查询用户角色 
     * @param @param userId
     * @param @return
     * @return List<Role>
     * @throws
     */
    List<Role> getUserRoles(@Param("userId") Integer userId);
    
    /**
     * 
     * @Title: getUserRoles 
     * @Description: 查询用户角色 
     * @param @param userName
     * @param @return
     * @return List<Role>
     * @throws
     */
    List<Role> getUserRolesByUserName(User user);
    
    /*
     * 
     * @Title: getUserRoles 
     * @Description: 查询用户角色 
     * @param @param userName
     * @param @return
     * @return List<Role>
     * @throws
     */
    List<Role> getAdminUserRoles();
    
    List<Actiongroup> queryMenuList();
    
    /**
     * <b>Description:</b><br> 删除角色
     * @param roleId 角色ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 下午1:37:35
     */
    Integer delete(Integer roleId);
    
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
    
    int insertRoleAction(@Param("roleId") Integer roleId,@Param("actionIdList") List<String> actionIdList,@Param("platformId") Integer platformId);
    
    int insertRoleGroup(@Param("roleId") Integer roleId,@Param("groupIdList") List<String> groupIdList,@Param("platformId") Integer platformId);
    
    List<RRoleAction> getRoleAction(@Param("role") Role role,@Param("platformId") Integer platformId);
    
    List<RRoleActiongroup> getRoleGroup(@Param("role") Role role, @Param("platformId") Integer platformId);

    List<RRoleActionVo> getChoosedRoleAction(@Param("role") Role role,@Param("platformId") Integer platformId);

    List<RRoleActionGroupVo> getChoosedRoleGroup(@Param("role") Role role,@Param("platformId") Integer platformId);

    int deleteAction(@Param("roleId") Integer roleId, @Param("platformId") Integer platformId);
    
    int deleteGroup(@Param("roleId") Integer roleId, @Param("platformId") Integer platformId);
    
    Integer vailRole(Role role);
    
    List<String> getUserNameByRoleName(@Param("roleName") String roleName, @Param("companyId") Integer companyId);
    
    List<Integer> getUserIdByRoleName(@Param("roleName") String roleName, @Param("companyId") Integer companyId);

    /**
      * 分页查询符合条件的角色
      * @author wayne.liu
      * @date  2019/6/6 14:48
      * @param 
      * @return 
      */
    List<Role> queryListPage(Map<String, Object> map);
}