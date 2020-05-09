package com.vedeng.saleperformance.model.vo;

import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.saleperformance.model.SalesPerformanceConfig;
import com.vedeng.saleperformance.model.SalesPerformanceGroup;

public class SalesPerformanceGroupVo extends SalesPerformanceGroup {
	
	private Integer userId;
	
	private String userName;
	
	private String orgName;
	
	private String goalYear;
	
	private String goalMonth;
	
	private Integer salesPerformanceGoalYearId;
	
	private Integer salesPerformanceGoalMonthId;
	
	private Integer rSalesPerformanceGroupJUserId;
	
	private Integer orgId;
	
	private List<RSalesPerformanceGroupJConfigVo> configVoList;
	/**
	 * 年份
	 */
	private Integer year;
	
	/**
	 * 每月的目标值
	 */
	private String[] monthGoals;
	
	/**
	 * 月份
	 */
	private Integer[] months;
	
	/**
	 *用户ID团队负责人
	 * @return
	 */
	private List<Integer> userIds;
	
	/**
	 * 用户的IDs
	 */
	private Integer[] ids;
	
	
	
	/**
	 * 用户一对多的使用
	 */
	private List<User> userList;
	
	/**
	 *  五行的列表
	 * @return
	 */
	private List<SalesPerformanceConfig> configList;
	
	/**
	 * 是否禁用
	 */
	private  Integer isDisabled;
	
	
	public List<User> getUserList() {
		return userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	public List<SalesPerformanceConfig> getConfigList() {
		return configList;
	}
	
	public void setConfigList(List<SalesPerformanceConfig> configList) {
		this.configList = configList;
	}
	
	public Integer getIsDisabled() {
		return isDisabled;
	}
	
	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}
	
	public List<Integer> getUserIds() {
		return userIds;
	}
	
	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getGoalYear() {
		return goalYear;
	}

	public void setGoalYear(String goalYear) {
		this.goalYear = goalYear;
	}

	public String getGoalMonth() {
		return goalMonth;
	}

	public void setGoalMonth(String goalMonth) {
		this.goalMonth = goalMonth;
	}

	public List<RSalesPerformanceGroupJConfigVo> getConfigVoList() {
		return configVoList;
	}

	public void setConfigVoList(List<RSalesPerformanceGroupJConfigVo> configVoList) {
		this.configVoList = configVoList;
	}


	public Integer getSalesPerformanceGoalYearId() {
		return salesPerformanceGoalYearId;
	}

	public void setSalesPerformanceGoalYearId(Integer salesPerformanceGoalYearId) {
		this.salesPerformanceGoalYearId = salesPerformanceGoalYearId;
	}

	public Integer getSalesPerformanceGoalMonthId() {
		return salesPerformanceGoalMonthId;
	}

	public void setSalesPerformanceGoalMonthId(Integer salesPerformanceGoalMonthId) {
		this.salesPerformanceGoalMonthId = salesPerformanceGoalMonthId;
	}

	public Integer getrSalesPerformanceGroupJUserId() {
		return rSalesPerformanceGroupJUserId;
	}

	public void setrSalesPerformanceGroupJUserId(Integer rSalesPerformanceGroupJUserId) {
		this.rSalesPerformanceGroupJUserId = rSalesPerformanceGroupJUserId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer integer) {
		this.orgId = integer;
	}

	public Integer getYear()
	{
		return year;
	}

	public void setYear(Integer year)
	{
		this.year = year;
	}

	public String[] getMonthGoals()
	{
		return monthGoals;
	}

	public void setMonthGoals(String[] monthGoals)
	{
		this.monthGoals = monthGoals;
	}

	public Integer[] getMonths()
	{
		return months;
	}

	public void setMonths(Integer[] months)
	{
		this.months = months;
	}
	
	
	public Integer[] getIds() {
		return ids;
	}
	
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
}
