package com.vedeng.phoneticWriting.model;

public class PhoneticWriting {
    /** 语音转文字的信息主键  PHONETIC WRITING_ID **/
    private Integer phoneticWritingId;

    /** 对应沟通记录主键  RELATED_ID **/
    private Integer relatedId;

    private String originalContent;

    private String updatedContent;

    /** 状态：0未处理；1：处理中；2：已完成  STATE **/
    private Integer state;

    /** 第三方接口返回的任务ID  TASK_ID **/
    private String taskId;

    /** 创建时间  ADD_TIME **/
    private Long addTime;

    /** 创建人  CREATOR **/
    private Integer creator;

    /** 更新时间  MOD_TIME **/
    private Long modTime;

    /** 更新人  UPDATER **/
    private Integer updater;

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getUpdatedContent() {
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    /**   语音转文字的信息主键  PHONETIC WRITING_ID   **/
    public Integer getPhoneticWritingId() {
        return phoneticWritingId;
    }

    /**   语音转文字的信息主键  PHONETIC WRITING_ID   **/
    public void setPhoneticWritingId(Integer phoneticWritingId) {
        this.phoneticWritingId = phoneticWritingId;
    }

    /**   对应沟通记录主键  RELATED_ID   **/
    public Integer getRelatedId() {
        return relatedId;
    }

    /**   对应沟通记录主键  RELATED_ID   **/
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    /**   状态：0未处理；1：处理中；2：已完成  STATE   **/
    public Integer getState() {
        return state;
    }

    /**   状态：0未处理；1：处理中；2：已完成  STATE   **/
    public void setState(Integer state) {
        this.state = state;
    }

    /**   第三方接口返回的任务ID  TASK_ID   **/
    public String getTaskId() {
        return taskId;
    }

    /**   第三方接口返回的任务ID  TASK_ID   **/
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**   创建时间  ADD_TIME   **/
    public Long getAddTime() {
        return addTime;
    }

    /**   创建时间  ADD_TIME   **/
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**   创建人  CREATOR   **/
    public Integer getCreator() {
        return creator;
    }

    /**   创建人  CREATOR   **/
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**   更新时间  MOD_TIME   **/
    public Long getModTime() {
        return modTime;
    }

    /**   更新时间  MOD_TIME   **/
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    /**   更新人  UPDATER   **/
    public Integer getUpdater() {
        return updater;
    }

    /**   更新人  UPDATER   **/
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}