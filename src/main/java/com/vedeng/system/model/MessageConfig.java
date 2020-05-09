package com.vedeng.system.model;

import java.io.Serializable;

public class MessageConfig implements Serializable{
    private Integer messageConfigId;

    private Integer actionId;

    private Integer conditionType;

    private Integer conditionFunctionId;

    private String userIds;

    private String title;

    private String content;

    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getMessageConfigId() {
        return messageConfigId;
    }

    public void setMessageConfigId(Integer messageConfigId) {
        this.messageConfigId = messageConfigId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    public Integer getConditionFunctionId() {
        return conditionFunctionId;
    }

    public void setConditionFunctionId(Integer conditionFunctionId) {
        this.conditionFunctionId = conditionFunctionId;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds == null ? null : userIds.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}