package com.vedeng.authorization.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 角色功能组Vo
 * @author wayne.liu
 * @description 
 * @date 2019/6/17 11:39
 */
public class RRoleActionGroupVo implements Serializable {
    
    private static final long serialVersionUID = -172835180503108261L;

    private Integer roleActiongroupId;

    private Integer roleId;

    private Integer actiongroupId;

    private Integer platformId;

    private Integer parentId;

    private String groupName;

    private List<RRoleActionVo> actionList;

    private List<RRoleActionGroupVo> childNode;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<RRoleActionVo> getActionList() {
        return actionList;
    }

    public void setActionList(List<RRoleActionVo> actionList) {
        this.actionList = actionList;
    }

    public List<RRoleActionGroupVo> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<RRoleActionGroupVo> childNode) {
        this.childNode = childNode;
    }

    @Override
    public String toString() {
        return "RRoleActionGroupVo{" +
                "roleActiongroupId=" + roleActiongroupId +
                ", roleId=" + roleId +
                ", actiongroupId=" + actiongroupId +
                ", platformId=" + platformId +
                ", parentId=" + parentId +
                ", groupName='" + groupName + '\'' +
                ", actionList=" + actionList +
                ", childNode=" + childNode +
                '}';
    }
}
