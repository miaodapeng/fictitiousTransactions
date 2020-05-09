package com.vedeng.saleperformance.model;

import java.util.List;


public class SalesPerformanceConfig {
    private Integer salesPerformanceConfigId;

    private Integer companyId;

    private String item;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    /** 权重  WEIGHT **/
    private Integer weight;
    
    /**
     * 关系表主键
     */
    private Integer rSalesPerformanceGroupJConfigId;
    
    public Integer getrSalesPerformanceGroupJConfigId() {
        return rSalesPerformanceGroupJConfigId;
    }
    
    public void setrSalesPerformanceGroupJConfigId(Integer rSalesPerformanceGroupJConfigId) {
        this.rSalesPerformanceGroupJConfigId = rSalesPerformanceGroupJConfigId;
    }
    
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    //private List<SalesPerformanceGroup> groupList;

    public Integer getSalesPerformanceConfigId() {
        return salesPerformanceConfigId;
    }

    public void setSalesPerformanceConfigId(Integer salesPerformanceConfigId) {
        this.salesPerformanceConfigId = salesPerformanceConfigId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
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

/*	public List<SalesPerformanceGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<SalesPerformanceGroup> groupList) {
		this.groupList = groupList;
	}*/
    
    
}