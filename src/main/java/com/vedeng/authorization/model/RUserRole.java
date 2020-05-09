package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 员工角色关系bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> RUserRole
 * <br><b>Date:</b> 2017年4月25日 上午11:07:45
 */
public class RUserRole implements Serializable{
    private Integer userRoleId;

    private Integer roleId;

    private Integer userId;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}