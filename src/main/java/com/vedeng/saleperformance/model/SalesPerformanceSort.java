package com.vedeng.saleperformance.model;

import java.math.BigDecimal;
import java.util.Date;

public class SalesPerformanceSort {
    private Integer salesPerformanceSortId;

    private Integer salesPerformanceGoalMonthId;

    private Integer sortNature;

    private Integer sortType;

    private Date date;
    
    private String dateStr;

    private BigDecimal score;

    private Integer sort;
    
    private Integer userId;
    
    private Integer orgId;
    
    private Integer salesPerformanceGroupId;

    public Integer getSalesPerformanceSortId() {
        return salesPerformanceSortId;
    }

    public void setSalesPerformanceSortId(Integer salesPerformanceSortId) {
        this.salesPerformanceSortId = salesPerformanceSortId;
    }

    public Integer getSalesPerformanceGoalMonthId() {
        return salesPerformanceGoalMonthId;
    }

    public void setSalesPerformanceGoalMonthId(Integer salesPerformanceGoalMonthId) {
        this.salesPerformanceGoalMonthId = salesPerformanceGoalMonthId;
    }

    public Integer getSortNature() {
        return sortNature;
    }

    public void setSortNature(Integer sortNature) {
        this.sortNature = sortNature;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getSalesPerformanceGroupId() {
		return salesPerformanceGroupId;
	}

	public void setSalesPerformanceGroupId(Integer salesPerformanceGroupId) {
		this.salesPerformanceGroupId = salesPerformanceGroupId;
	}
	
	
}