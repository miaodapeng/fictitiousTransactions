package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 角色分组关系bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> RRoleActiongroup
 * <br><b>Date:</b> 2017年4月25日 上午11:08:14
 */
public class RRoleActiongroup implements Serializable{
    private Integer roleActiongroupId;

    private Integer roleId;

    private Integer actiongroupId;

    private Integer platformId;

    public Integer getRoleActiongroupId() {
        return roleActiongroupId;
    }

    public void setRoleActiongroupId(Integer roleActiongroupId) {
        this.roleActiongroupId = roleActiongroupId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getActiongroupId() {
        return actiongroupId;
    }

    public void setActiongroupId(Integer actiongroupId) {
        this.actiongroupId = actiongroupId;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }
}