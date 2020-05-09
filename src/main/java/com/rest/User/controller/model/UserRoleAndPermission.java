package com.rest.User.controller.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户权限信息
 * @author wayne.liu
 * @description 
 * @date 2019/6/14 17:23
 */
public class UserRoleAndPermission implements Serializable {


    private static final long serialVersionUID = -7700397297238132861L;
    /**
     * 角色名
     */
    private Set<String> roleSet;

    /**
     * 权限名称
     */
    private Set<String> permissionSet;

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }

    public Set<String> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<String> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
