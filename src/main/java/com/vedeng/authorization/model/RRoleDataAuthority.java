package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * @Author wayne.liu
 * @Description 角色数据权限关系model
 * @Date 2019/6/6 9:36
 */
public class RRoleDataAuthority implements Serializable{


    private static final long serialVersionUID = 2147790607764869771L;
    /**
     * 主键
     */
    private Integer roleDataAuthorityId;
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 应用平台Id
     */
    private Integer platformId;

    public Integer getRoleDataAuthorityId() {
        return roleDataAuthorityId;
    }

    public void setRoleDataAuthorityId(Integer roleDataAuthorityId) {
        this.roleDataAuthorityId = roleDataAuthorityId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @Override
    public String toString() {
        return "RRoleDataAuthority{" +
                "roleDataAuthorityId=" + roleDataAuthorityId +
                ", roleId=" + roleId +
                ", platformId=" + platformId +
                '}';
    }
}