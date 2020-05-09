package com.vedeng.saleperformance.model;

import java.util.Date;

public class SalesPerformanceRate {
    private Integer salesPerformanceRateId;

    private Integer companyId;

    private Date yearMonth;
    
    private String yearMonthStr;

    private Integer userId;

    private Long addTime;

    public Integer getSalesPerformanceRateId() {
        return salesPerformanceRateId;
    }

    public void setSalesPerformanceRateId(Integer salesPerformanceRateId) {
        this.salesPerformanceRateId = salesPerformanceRateId;
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

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

	public String getYearMonthStr() {
		return yearMonthStr;
	}

	public void setYearMonthStr(String yearMonthStr) {
		this.yearMonthStr = yearMonthStr;
	}
}