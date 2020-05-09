package com.vedeng.saleperformance.model.vo;

import java.util.List;

import com.vedeng.saleperformance.model.SalesPerformanceSort;

public class SalesPerformanceSortVo extends SalesPerformanceSort {
	private String yearStr;//年
	
	private String monthStr;//月
	
	private String dateStr;//年-月-日

	private Integer userId;
	
	private List<Integer> userIds;
	
	private Integer orgId2;//二级部
	
	private Integer orgId3;//三级部
	
	public String getYearStr() {
		return yearStr;
	}

	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
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

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public Integer getOrgId2() {
		return orgId2;
	}

	public void setOrgId2(Integer orgId2) {
		this.orgId2 = orgId2;
	}

	public Integer getOrgId3() {
		return orgId3;
	}

	public void setOrgId3(Integer orgId3) {
		this.orgId3 = orgId3;
	}
	
	
}
