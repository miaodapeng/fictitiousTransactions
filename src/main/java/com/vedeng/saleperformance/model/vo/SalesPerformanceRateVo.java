package com.vedeng.saleperformance.model.vo;

import java.math.BigDecimal;

import com.vedeng.saleperformance.model.SalesPerformanceRate;

public class SalesPerformanceRateVo extends SalesPerformanceRate {
	private Integer businessNum;//个人商机
	
	private Integer totalNum; //总的市场分配给自己商机数
	
	private BigDecimal rate;
	
	private Integer groupId;//分组ID
	
	private Integer salesPerformanceGoalMonthId;//月份配置主键
	
	private BigDecimal score;//得分
	
	private String bussinessIds;//商机ID集合
	private String bussinessNos;//商机单号集合
	private String assignTimes;//商机分配时间集合
	private String saleorderIds;//订单ID集合
	private String saleorderNos;//订单单号集合

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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

	public String getBussinessIds() {
		return bussinessIds;
	}

	public void setBussinessIds(String bussinessIds) {
		this.bussinessIds = bussinessIds;
	}

	public String getBussinessNos() {
		return bussinessNos;
	}

	public void setBussinessNos(String bussinessNos) {
		this.bussinessNos = bussinessNos;
	}

	public String getAssignTimes() {
		return assignTimes;
	}

	public void setAssignTimes(String assignTimes) {
		this.assignTimes = assignTimes;
	}

	public String getSaleorderIds() {
		return saleorderIds;
	}

	public void setSaleorderIds(String saleorderIds) {
		this.saleorderIds = saleorderIds;
	}

	public String getSaleorderNos() {
		return saleorderNos;
	}

	public void setSaleorderNos(String saleorderNos) {
		this.saleorderNos = saleorderNos;
	}
	
	
}
