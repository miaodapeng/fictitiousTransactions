package com.vedeng.phoneticWriting.model;

public class ModificationRecord {
    /** 修改记录id  MODIFICATION_RECORD_ID **/
    private Integer modificationRecordId;

    /** 争议内容  CONTROVERSIAL_CONTENT **/
    private String controversialContent;

    /** 修改内容  MODIFY_CONTENT **/
    private String modifyContent;

    /** 处理状态，0：未处理；1：处理中；2：已处理  STATE **/
    private Integer state;

    /** 关联沟通记录id  RELATED_ID **/
    private Integer relatedId;

    /** 是否删除  IS_DEL **/
    private Integer isDel;

    /** 创建时间  ADD_TIME **/
    private Long addTime;

    /** 创建人  CREATOR **/
    private Integer creator;

    /** 操作类型  TYPE **/
    private Integer type;

    /** 更新时间  MOD_TIME **/
    private Long modTime;

    /** 更新人  UPDATER **/
    private Integer updater;

    // 录音文档id
    private Integer phoneticWritingId;

    public Integer getPhoneticWritingId() {
        return phoneticWritingId;
    }

    public void setPhoneticWritingId(Integer phoneticWritingId) {
        this.phoneticWritingId = phoneticWritingId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**   修改记录id  MODIFICATION_RECORD_ID   **/
    public Integer getModificationRecordId() {
        return modificationRecordId;
    }

    /**   修改记录id  MODIFICATION_RECORD_ID   **/
    public void setModificationRecordId(Integer modificationRecordId) {
        this.modificationRecordId = modificationRecordId;
    }

    /**   争议内容  CONTROVERSIAL_CONTENT   **/
    public String getControversialContent() {
        return controversialContent;
    }

    /**   争议内容  CONTROVERSIAL_CONTENT   **/
    public void setControversialContent(String controversialContent) {
        this.controversialContent = controversialContent == null ? null : controversialContent.trim();
    }

    /**   修改内容  MODIFY_CONTENT   **/
    public String getModifyContent() {
        return modifyContent;
    }

    /**   修改内容  MODIFY_CONTENT   **/
    public void setModifyContent(String modifyContent) {
        this.modifyContent = modifyContent == null ? null : modifyContent.trim();
    }

    /**   处理状态，0：未处理；1：处理中；2：已处理  STATE   **/
    public Integer getState() {
        return state;
    }

    /**   处理状态，0：未处理；1：处理中；2：已处理  STATE   **/
    public void setState(Integer state) {
        this.state = state;
    }

    /**   关联沟通记录id  RELATED_ID   **/
    public Integer getRelatedId() {
        return relatedId;
    }

    /**   关联沟通记录id  RELATED_ID   **/
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
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