package com.vedeng.saleperformance.model;

import java.util.Date;

public class SalesPerformanceTrader {
    private Integer salesPerformanceTraderId;

    private Integer companyId;

    private Integer termType;

    private Date yearMonth;
    
    private String yearMonthStr;

    private Integer userId;

    private Integer traderId;

    private String traderName;

    private Long addTime;

    public Integer getSalesPerformanceTraderId() {
        return salesPerformanceTraderId;
    }

    public void setSalesPerformanceTraderId(Integer salesPerformanceTraderId) {
        this.salesPerformanceTraderId = salesPerformanceTraderId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getTermType() {
        return termType;
    }

    public void setTermType(Integer termType) {
        this.termType = termType;
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

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
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