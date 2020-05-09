package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 角色功能关系bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> RRoleAction
 * <br><b>Date:</b> 2017年4月25日 上午11:08:36
 */
public class RRoleAction implements Serializable{
    private Integer roleActionId;

    private Integer roleId;

    private Integer actionId;

    public Integer getRoleActionId() {
        return roleActionId;
    }

    public void setRoleActionId(Integer roleActionId) {
        this.roleActionId = roleActionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }
}