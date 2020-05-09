package com.vedeng.saleperformance.model;

import java.util.Date;

public class SalesPerformanceBrand {
    private Integer salesPerformanceBrandId;

    private Integer companyId;

    private Date yearMonth;

    private Integer userId;

    private Integer brandId;

    private String brandName;

    private Long addTime;

    public Integer getSalesPerformanceBrandId() {
        return salesPerformanceBrandId;
    }

    public void setSalesPerformanceBrandId(Integer salesPerformanceBrandId) {
        this.salesPerformanceBrandId = salesPerformanceBrandId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}