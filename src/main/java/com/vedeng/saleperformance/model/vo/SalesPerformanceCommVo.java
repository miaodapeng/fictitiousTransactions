package com.vedeng.saleperformance.model.vo;

import java.math.BigDecimal;

import com.vedeng.saleperformance.model.SalesPerformanceComm;

public class SalesPerformanceCommVo extends SalesPerformanceComm {
	private Integer inNum;//呼入数量
	
	private Integer inLength;//呼入时长
	
	private Integer outNum;//呼出数量
	
	private Integer outLength;//呼出时长
	
	private Integer commLength;//通话总时长
	
	private Integer groupId;//分组ID
	
	private Integer salesPerformanceGoalMonthId;//月份配置主键
	
	private BigDecimal score;//得分
	
	private BigDecimal avgScore;//均分

	public Integer getInNum() {
		return inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}

	public Integer getInLength() {
		return inLength;
	}

	public void setInLength(Integer inLength) {
		this.inLength = inLength;
	}

	public Integer getOutNum() {
		return outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}

	public Integer getOutLength() {
		return outLength;
	}

	public void setOutLength(Integer outLength) {
		this.outLength = outLength;
	}

	public Integer getCommLength() {
		return commLength;
	}

	public void setCommLength(Integer commLength) {
		this.commLength = commLength;
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
