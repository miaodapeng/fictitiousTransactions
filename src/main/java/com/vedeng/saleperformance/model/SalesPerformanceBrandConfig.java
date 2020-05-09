package com.vedeng.saleperformance.model;

public class SalesPerformanceBrandConfig {
    private Integer salesPerformanceBrandConfigId;

    private Integer salesPerformanceGroupId;

    private Integer companyId;

    private Integer brandId;

    private String brandName;

    private Integer goodsNum;

    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private SalesPerformanceBrand salesPerformanceBrand;
    
    public Integer getSalesPerformanceBrandConfigId() {
        return salesPerformanceBrandConfigId;
    }

    public void setSalesPerformanceBrandConfigId(Integer salesPerformanceBrandConfigId) {
        this.salesPerformanceBrandConfigId = salesPerformanceBrandConfigId;
    }

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
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

	public SalesPerformanceBrand getSalesPerformanceBrand() {
		return salesPerformanceBrand;
	}

	public void setSalesPerformanceBrand(SalesPerformanceBrand salesPerformanceBrand) {
		this.salesPerformanceBrand = salesPerformanceBrand;
	}
    
}