package com.vedeng.authorization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vedeng.authorization.model.RRoleActiongroup;
@Repository("rRoleActiongroupMapper")
public interface RRoleActiongroupMapper {
    int deleteByPrimaryKey(Integer roleActiongroupId);

    int insert(RRoleActiongroup record);

    int insertSelective(RRoleActiongroup record);

    RRoleActiongroup selectByPrimaryKey(Integer roleActiongroupId);

    int updateByPrimaryKeySelective(RRoleActiongroup record);

    int updateByPrimaryKey(RRoleActiongroup record);
    /**
     * <b>Description:</b><br> 根据actiongroupid获取角色的集合
     * @param actionId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月10日 上午11:04:29
     */
    List<RRoleActiongroup> getRRoleActiongroupListByActiongroupId(@Param("actiongroupId")Integer actiongroupId);

    /**
      * 根据角色Id和应用系统删除绑定关系
      * @author wayne.liu
      * @date  2019/6/19 13:06
      * @param
      * @return
      */
    Integer deleteByRoleIdAndPlatformId(@Param("roleId")Integer roleId,@Param("ids") List<Integer> ids);
}