package com.vedeng.saleperformance.model;

import java.util.Date;

public class SalesPerformanceBussinessChance {
    private Integer salesPerformanceBussinessChanceId;

    private Integer companyId;

    private Date yearMonth;

    private Integer userId;

    private Integer bussinessChanceId;

    private String bussinessChanceNo;

    private Integer saleorderId;

    private String saleorderNo;

    private Long assignTime;

    private Long addTime;
    
    private String yearMonthStr;

    public Integer getSalesPerformanceBussinessChanceId() {
        return salesPerformanceBussinessChanceId;
    }

    public void setSalesPerformanceBussinessChanceId(Integer salesPerformanceBussinessChanceId) {
        this.salesPerformanceBussinessChanceId = salesPerformanceBussinessChanceId;
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

    public Integer getBussinessChanceId() {
        return bussinessChanceId;
    }

    public void setBussinessChanceId(Integer bussinessChanceId) {
        this.bussinessChanceId = bussinessChanceId;
    }

    public String getBussinessChanceNo() {
        return bussinessChanceNo;
    }

    public void setBussinessChanceNo(String bussinessChanceNo) {
        this.bussinessChanceNo = bussinessChanceNo == null ? null : bussinessChanceNo.trim();
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    public String getSaleorderNo() {
        return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo == null ? null : saleorderNo.trim();
    }

    public Long getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Long assignTime) {
        this.assignTime = assignTime;
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