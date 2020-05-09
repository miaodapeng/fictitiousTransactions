package com.vedeng.approval.model;

import java.io.Serializable;

public class ApprovalRecordEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    
    //子表记录主键
    private Integer recordId;
    
    //主表主键
    private Integer approvalId;
    
    //创建人Id
    private Integer creator;
    
    //创建人名称
    private String createName;
    
    //创建时间 
    private String addTime;
    
    //修改人Id
    private Integer updater;
    
    //修改人名称
    private String updateName;
    
    //修改时间
    private String modTime;
    
    //记录内容
    private String recordContent;
    
    //删除标记(0:否、1：是)
    private Integer isDelete;
    
    //是否有权限(0：否、1：是)
    private Integer isLimit;

    
    //是否展示分页(0：否、1：是)
    private Integer isPage;
    

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getModTime() {
        return modTime;
    }

    public void setModTime(String modTime) {
        this.modTime = modTime;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }

    public Integer getIsPage() {
        return isPage;
    }

    public void setIsPage(Integer isPage) {
        this.isPage = isPage;
    }

    
   }


