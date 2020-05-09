package com.vedeng.authorization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vedeng.authorization.model.RRoleAction;
@Repository("rRoleActionMapper")
public interface RRoleActionMapper {
    int deleteByPrimaryKey(Integer roleActionId);

    int insert(RRoleAction record);

    int insertSelective(RRoleAction record);

    RRoleAction selectByPrimaryKey(Integer roleActionId);

    int updateByPrimaryKeySelective(RRoleAction record);

    int updateByPrimaryKey(RRoleAction record);
    
    /**
     * <b>Description:</b><br> 根据actionid获取角色的集合
     * @param actionId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月10日 上午11:04:29
     */
    List<RRoleAction> getRRoleActionListByActionId(@Param("actionId")Integer actionId);

    /**
     * 根据角色Id和应用系统删除绑定关系
     * @author wayne.liu
     * @date  2019/6/19 13:06
     * @param
     * @return
     */
    Integer deleteByRoleIdAndPlatformId(@Param("roleId")Integer roleId,@Param("ids") List<Integer> ids);
}