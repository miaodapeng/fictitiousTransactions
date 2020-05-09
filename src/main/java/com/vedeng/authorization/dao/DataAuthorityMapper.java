package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.RRoleDataAuthority;

import javax.inject.Named;
import java.util.List;

/**
 * @Author wayne.liu
 * @Description
 * @Date 2019/6/6 9:45 
 */
@Named("dataAuthorityMapper")
public interface DataAuthorityMapper {

    /**
     * 保存角色平台关系
     * @author wayne.liu
     * @date  2019/6/6 10:49
     * @param list
     * @return int
     */
    int insertDataAuthorityBatch(List<RRoleDataAuthority> list);


    /**
     * 查询角色平台关系列表
     * @author wayne.liu
     * @date  2019/6/6 9:46
     * @param roleId 角色Id
     * @return
     */
    List<RRoleDataAuthority> queryList(Integer roleId);

    /**
     * 删除角色的数据权限
     * @author wayne.liu
     * @date  2019/6/6 9:46
     * @param roleId 角色Id
     * @return
     */
    void delete(Integer roleId);

}