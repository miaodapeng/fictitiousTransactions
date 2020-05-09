package com.vedeng.saleperformance.model;

import java.math.BigDecimal;

public class SalesPerformanceGoalMonth {
    private Integer salesPerformanceGoalMonthId;

    private Integer salesPerformanceGoalYearId;

    private Integer companyId;

    private Integer month;

    private BigDecimal goalMonth;

    private Integer orgId;

    private Integer userId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getSalesPerformanceGoalMonthId() {
        return salesPerformanceGoalMonthId;
    }

    public void setSalesPerformanceGoalMonthId(Integer salesPerformanceGoalMonthId) {
        this.salesPerformanceGoalMonthId = salesPerformanceGoalMonthId;
    }

    public Integer getSalesPerformanceGoalYearId() {
        return salesPerformanceGoalYearId;
    }

    public void setSalesPerformanceGoalYearId(Integer salesPerformanceGoalYearId) {
        this.salesPerformanceGoalYearId = salesPerformanceGoalYearId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getGoalMonth() {
        return goalMonth;
    }

    public void setGoalMonth(BigDecimal goalMonth) {
        this.goalMonth = goalMonth;
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