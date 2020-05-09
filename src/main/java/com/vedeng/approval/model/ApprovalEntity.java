package com.vedeng.approval.model;

import java.io.Serializable;
import java.util.List;

public class ApprovalEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    
    //批准公示Id
    private Integer approvalId;
    
    //标题
    private String title;
    
    //发布时间
    private String releaseTime;
    
    //链接地址
    private String linkAddress;
    
    //机构名称
    private String organizeName;
    
    //机构类别
    private String organizeType;
    
    //机构类别
    private String organizeSubject;
    
    //跟进人员 
    private Integer followUserId;
    
    //跟进状态(1：未跟进、2：跟进中、3：已完结、4：纳入商机)
    private Integer followState;
    
    //关注状态(1：未关注、2：已关注)
    private Integer focusState;
    
    //联系地址
    private String contactAddress;
    
    //联系电话
    private String contactPhone;
    
    //创建人Id
    private Integer creator;
    
    //创建人姓名
    private String createName;
    
    //创建时间 (发布时间)
    private String addTime;
    
    //创建时间 (额外字段)
    private String endTime;
    
    //修改人Id
    private Integer updater;
    
    //修改人名称
    private String updateName;
    
    //修改时间
    private String modTime;
    
    //是否收录(1:否、2：是)
    private Integer isInclude;
    
    //删除标记(0:否、1：是)
    private Integer isDelete;
    
    //使用该字段判断不再收录时，批量不再收录是否可以点击
    private Integer isLimit;
    
    //使用该字段判断数据不满10条，不显示分页器
    private Integer isPage;
    
    //子表
    private List<ApprovalRecordEntity> approvalRecordList;

    
    
    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getOrganizeType() {
        return organizeType;
    }

    public void setOrganizeType(String organizeType) {
        this.organizeType = organizeType;
    }

    public String getOrganizeSubject() {
        return organizeSubject;
    }

    public void setOrganizeSubject(String organizeSubject) {
        this.organizeSubject = organizeSubject;
    }

    public Integer getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Integer followUserId) {
        this.followUserId = followUserId;
    }

    public Integer getFollowState() {
        return followState;
    }

    public void setFollowState(Integer followState) {
        this.followState = followState;
    }

    public Integer getFocusState() {
        return focusState;
    }

    public void setFocusState(Integer focusState) {
        this.focusState = focusState;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public Integer getIsInclude() {
        return isInclude;
    }

    public void setIsInclude(Integer isInclude) {
        this.isInclude = isInclude;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public List<ApprovalRecordEntity> getApprovalRecordList() {
        return approvalRecordList;
    }

    public void setApprovalRecordList(List<ApprovalRecordEntity> approvalRecordList) {
        this.approvalRecordList = approvalRecordList;
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
