package com.vedeng.saleperformance.model.vo;

import java.math.BigDecimal;

import com.vedeng.saleperformance.model.SalesPerformanceTrader;

public class SalesPerformanceTraderVo extends SalesPerformanceTrader {
	private Integer ordersNum;
	
	private Long lastOrderTime;
	
	private Long lastCommTime;
	
	private BigDecimal ordersAmount;
	
	private String isOld;
	
	private Integer traderNum;//客户数
	
	private Integer groupId;//分组ID
	
	private Integer salesPerformanceGoalMonthId;//月份配置主键
	
	private BigDecimal score;//得分
	
	private BigDecimal avgScore;//均分

	public Integer getOrdersNum() {
		return ordersNum;
	}

	public void setOrdersNum(Integer ordersNum) {
		this.ordersNum = ordersNum;
	}

	public Long getLastOrderTime() {
		return lastOrderTime;
	}

	public void setLastOrderTime(Long lastOrderTime) {
		this.lastOrderTime = lastOrderTime;
	}

	public Long getLastCommTime() {
		return lastCommTime;
	}

	public void setLastCommTime(Long lastCommTime) {
		this.lastCommTime = lastCommTime;
	}

	public BigDecimal getOrdersAmount() {
		return ordersAmount;
	}

	public void setOrdersAmount(BigDecimal ordersAmount) {
		this.ordersAmount = ordersAmount;
	}

	public String getIsOld() {
		return isOld;
	}

	public void setIsOld(String isOld) {
		this.isOld = isOld;
	}

	public Integer getTraderNum() {
		return traderNum;
	}

	public void setTraderNum(Integer traderNum) {
		this.traderNum = traderNum;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getSalesPerformanceGoalMonthId() {
		return salesPerformanceGoalMonthId;
	}

	public void setSalesPerformanceGoalMonthId(Integer salesPerformanceGoalMonthId) {
		this.salesPerformanceGoalMonthId = salesPerformanceGoalMonthId;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(BigDecimal avgScore) {
		this.avgScore = avgScore;
	}

}
