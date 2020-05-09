package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * @Author wayne.liu
 * @Description 角色平台关系model
 * @Date 2019/6/6 9:36
 */
public class RolePlatform implements Serializable{

    private static final long serialVersionUID = -1849353732579763067L;
    /**
     * 主键
     */
    private Integer rolePlatformId;
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 应用平台Id
     */
    private Integer platformId;

    public Integer getRolePlatformId() {
        return rolePlatformId;
    }

    public void setRolePlatformId(Integer rolePlatformId) {
        this.rolePlatformId = rolePlatformId;
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
        return "RolePlatform{" +
                "rolePlatformId=" + rolePlatformId +
                ", roleId=" + roleId +
                ", platformId=" + platformId +
                '}';
    }
}