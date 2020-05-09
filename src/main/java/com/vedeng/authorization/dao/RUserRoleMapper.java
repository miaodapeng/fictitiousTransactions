package com.vedeng.authorization.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.RUserRole;
import com.vedeng.authorization.model.User;

/**
 * <b>Description:</b><br> 员工角色Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> RUserRoleMapper
 * <br><b>Date:</b> 2017年4月25日 上午10:00:57
 */
@Named("rUserRoleMapper")
public interface RUserRoleMapper {
	
    /**
     * <b>Description:</b><br>删除员工角色 
     * @param userId 用户ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:01:41
     */
    int deleteByUserId(Integer userId);
    
    /**
     * <b>Description:</b><br>添加员工角色 
     * @param rUserRole 员工角色关系bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:04:13
     */
    int insert(RUserRole rUserRole);
	/**
	 * <b>Description:</b><br> 根据userid获取角色
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月10日 下午2:04:29
	 */
    List<RUserRole> getRUserRoleListByUserId(@Param("userId")Integer userId);
	/**
	 * <b>Description:</b><br> 根据userid获取角色
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月10日 下午2:04:29
	 */
    List<Integer> getRoleIdListByUserId(@Param("userId")Integer userId);
    /**
     * <b>Description:</b><br> 根据角色的集合查询关联用户的id集合
     * @param roleIdList
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月2日 下午1:25:22
     */
    List<Integer> getUserIdList(@Param("roleIdList")List<Integer> roleIdList);
}