package com.vedeng.saleperformance.model;

public class RSalesPerformanceGroupJUser {
    private Integer rSalesPerformanceGroupJUserId;

    private Integer salesPerformanceGroupId;

    private Integer userId;
    
    private String userIdsStr;
    
    private Integer weight;

    public Integer getrSalesPerformanceGroupJUserId() {
        return rSalesPerformanceGroupJUserId;
    }

    public void setrSalesPerformanceGroupJUserId(Integer rSalesPerformanceGroupJUserId) {
        this.rSalesPerformanceGroupJUserId = rSalesPerformanceGroupJUserId;
    }

    public Integer getSalesPerformanceGroupId() {
        return salesPerformanceGroupId;
    }

    public void setSalesPerformanceGroupId(Integer salesPerformanceGroupId) {
        this.salesPerformanceGroupId = salesPerformanceGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

	public String getUserIdsStr() {
		return userIdsStr;
	}

	public void setUserIdsStr(String userIdsStr) {
		this.userIdsStr = userIdsStr;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}