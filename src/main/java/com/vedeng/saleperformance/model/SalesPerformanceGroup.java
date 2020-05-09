package com.vedeng.saleperformance.model;


public class SalesPerformanceGroup {
    private Integer salesPerformanceGroupId;

    private Integer companyId;

    private String groupName;

    private Integer isStatistics;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getSalesPerformanceGroupId() {
        return salesPerformanceGroupId;
    }

    public void setSalesPerformanceGroupId(Integer salesPerformanceGroupId) {
        this.salesPerformanceGroupId = salesPerformanceGroupId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getIsStatistics() {
        return isStatistics;
    }

    public void setIsStatistics(Integer isStatistics) {
        this.isStatistics = isStatistics;
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