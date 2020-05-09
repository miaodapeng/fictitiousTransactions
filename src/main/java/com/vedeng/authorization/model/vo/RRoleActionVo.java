package com.vedeng.authorization.model.vo;

import java.io.Serializable;

/**
 * 角色功能点Vo
 * @author wayne.liu
 * @description 
 * @date 2019/6/17 11:39
 */
public class RRoleActionVo implements Serializable {

    private static final long serialVersionUID = -7710702227066105699L;

    private Integer roleActionId;

    private Integer actiongroupId;

    private Integer roleId;

    private Integer actionId;

    private String actionName;

    public Integer getActiongroupId() {
        return actiongroupId;
    }

    public void setActiongroupId(Integer actiongroupId) {
        this.actiongroupId = actiongroupId;
    }

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

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public String toString() {
        return "RRoleActionVo{" +
                "roleActionId=" + roleActionId +
                ", actiongroupId=" + actiongroupId +
                ", roleId=" + roleId +
                ", actionId=" + actionId +
                ", actionName='" + actionName + '\'' +
                '}';
    }
}
