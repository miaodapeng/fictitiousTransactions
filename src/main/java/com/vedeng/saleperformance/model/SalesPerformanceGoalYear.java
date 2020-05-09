package com.vedeng.saleperformance.model;

import java.math.BigDecimal;
import java.util.Date;

public class SalesPerformanceGoalYear {
    private Integer salesPerformanceGoalYearId;

    private Integer rSalesPerformanceGroupJUserId;

    private Integer companyId;

    private Integer year;

    private BigDecimal goalYear;

    private Integer orgId;

    private Integer userId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getSalesPerformanceGoalYearId() {
        return salesPerformanceGoalYearId;
    }

    public void setSalesPerformanceGoalYearId(Integer salesPerformanceGoalYearId) {
        this.salesPerformanceGoalYearId = salesPerformanceGoalYearId;
    }

    public Integer getrSalesPerformanceGroupJUserId() {
        return rSalesPerformanceGroupJUserId;
    }

    public void setrSalesPerformanceGroupJUserId(Integer rSalesPerformanceGroupJUserId) {
        this.rSalesPerformanceGroupJUserId = rSalesPerformanceGroupJUserId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear()
	{
		return year;
	}

	public void setYear(Integer year)
	{
		this.year = year;
	}

	public BigDecimal getGoalYear() {
        return goalYear;
    }

    public void setGoalYear(BigDecimal goalYear) {
        this.goalYear = goalYear;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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