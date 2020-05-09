package com.vedeng.saleperformance.model.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.User;

public class GroupMemberDetailsVo {
	
	private List<User> users;
	
	private Integer userId;
	
	private String username;
	
	private Integer companyId;//公司Id
	
	private String orgName;//部门名称
	
	private Integer year;//年
	
	private Integer month;//月
	
	private Integer day;//日
	
	private String date;//时间
	
	private String dateBySort;//用于查询排名的日期
	
	private List<String> monthYear;
	
	private Integer selectOrgId;//用来查询该组历史人数的小组部门Id
	
	private List<Integer> groupIds;//用来查询该组历史人数的分组Id
	
	private Integer type;//查询类型
	
	private Integer rateType;//转化率类型
	
	private Integer sortNature;//1：个人2：小组
	
	private Integer sortType;//类型：1业绩2品牌3客户4话务5转化率

	private BigDecimal goalMonth;//月度目标
	
	private BigDecimal orderDetails;//当前绩效
	
	private String completionDegree;//完成度
	
	private Integer orderSort;//绩效排名
	
	private Float brandCount;//品牌数
	
	private Integer brandSort;//品牌排名
	
	private Float traderCount;//合作客户数
	
	private Integer traderSort;//合作客户数排名
	
	private String commCount;//通话时长
	
	private Integer commSort;//通话排名
	
	private String rate;//转化率
	
	private Integer rateSort;//转化率排名
	
	private Integer totalSort;//综合排名

	private List<String> yearAndMonth;//最近12个月
	
	private Map<String, List<User>> areaMenbers;//区域人员列表
	
	private Integer salesPerformanceGroupId;//五行剑法系统下小组Id
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSalesPerformanceGroupId() {
		return salesPerformanceGroupId;
	}

	public void setSalesPerformanceGroupId(Integer salesPerformanceGroupId) {
		this.salesPerformanceGroupId = salesPerformanceGroupId;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public Integer getSelectOrgId() {
		return selectOrgId;
	}

	public void setSelectOrgId(Integer selectOrgId) {
		this.selectOrgId = selectOrgId;
	}

	public Map<String, List<User>> getAreaMenbers() {
		return areaMenbers;
	}

	public void setAreaMenbers(Map<String, List<User>> areaMenbers) {
		this.areaMenbers = areaMenbers;
	}

	public Integer getRateType() {
		return rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	public List<String> getYearAndMonth() {
		return yearAndMonth;
	}

	public void setYearAndMonth(List<String> yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getDateBySort() {
		return dateBySort;
	}

	public void setDateBySort(String dateBySort) {
		this.dateBySort = dateBySort;
	}

	public List<String> getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(List<String> monthYear) {
		this.monthYear = monthYear;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getGoalMonth() {
		return goalMonth;
	}

	public void setGoalMonth(BigDecimal goalMonth) {
		this.goalMonth = goalMonth;
	}

	public BigDecimal getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(BigDecimal orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getCompletionDegree() {
		return completionDegree;
	}

	public void setCompletionDegree(String completionDegree) {
		this.completionDegree = completionDegree;
	}

	public Integer getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(Integer orderSort) {
		this.orderSort = orderSort;
	}

	public Float getBrandCount() {
		return brandCount;
	}

	public void setBrandCount(Float brandCount) {
		this.brandCount = brandCount;
	}

	public Integer getBrandSort() {
		return brandSort;
	}

	public void setBrandSort(Integer brandSort) {
		this.brandSort = brandSort;
	}

	public Float getTraderCount() {
		return traderCount;
	}

	public void setTraderCount(Float traderCount) {
		this.traderCount = traderCount;
	}

	public Integer getTraderSort() {
		return traderSort;
	}

	public void setTraderSort(Integer traderSort) {
		this.traderSort = traderSort;
	}

	public String getCommCount() {
		return commCount;
	}

	public void setCommCount(String commCount) {
		this.commCount = commCount;
	}

	public Integer getCommSort() {
		return commSort;
	}

	public void setCommSort(Integer commSort) {
		this.commSort = commSort;
	}



	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Integer getRateSort() {
		return rateSort;
	}

	public void setRateSort(Integer rateSort) {
		this.rateSort = rateSort;
	}

	public Integer getTotalSort() {
		return totalSort;
	}

	public void setTotalSort(Integer totalSort) {
		this.totalSort = totalSort;
	}


	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	
	
	
}
