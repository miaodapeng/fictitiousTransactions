package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.RolePlatform;
import com.vedeng.authorization.model.vo.RolePlatformVo;

import javax.inject.Named;
import java.util.List;

/**
 * @Author wayne.liu
 * @Description
 * @Date 2019/6/6 9:45 
 */
@Named("rolePlatformMapper")
public interface RolePlatformMapper {
    
    /**
      * 保存角色平台关系
      * @author wayne.liu
      * @date  2019/6/6 10:49
      * @param list
      * @return int
      */
    int insertBatch(List<RolePlatform> list);


    /**
     * 查询角色平台关系列表
     * @author wayne.liu
     * @date  2019/6/6 9:46
     * @param roleId 角色Id
     * @return
     */
    List<RolePlatform> queryList(Integer roleId);

    /**
      * 根据角色Id删除角色平台关系
      * @author wayne.liu
      * @date  2019/6/6 13:02
      * @param roleId
      * @return
      */
    void deleteByRoleId(Integer roleId);

    /**
     * 查询角色平台关系列表
     * @author wayne.liu
     * @date  2019/6/6 9:46
     * @param list 角色Id
     * @return
     */
    List<RolePlatformVo> queryListByIds(List<Integer> list);

    /**
     * 查询角色平台关系列表
     * @author wayne.liu
     * @date  2019/6/6 9:46
     * @param roleId 角色Id
     * @return
     */
    List<RolePlatformVo> queryListByRoleId(Integer roleId);
}