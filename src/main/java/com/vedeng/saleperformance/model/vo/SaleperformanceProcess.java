package com.vedeng.saleperformance.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleperformanceProcess implements Serializable
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3883343060475982397L;
	
	private String username;// 人员;
	private String orgName2;// 二级部门;
	private String orgName3;// 三级部门;
	private BigDecimal goalMonth;// 本月目标;
	private BigDecimal amount;// 业绩额;
	private String amountRate;// 完成度;
	private String deptAmountRate;// 部门完成度;                //
	private BigDecimal score;// 业绩得分;
	private Integer sort;// 业绩排名;
	private Integer customerNum;// 成交客户数（90天内成交客户数）;
	private BigDecimal deptAvgCustomerNum;// 部门平均成交客户数;       // 
	private BigDecimal customerScore;// 客户得分;
	private Integer customerSort;// 客户排行;
	private Integer brandNum;// 合作品牌数（90天）;
	private BigDecimal deptAvgBrandNum;// 部门平均合作品牌数;      // 
	private BigDecimal brandScore;// 品牌得分;
	private Integer brandSort;// 品牌排名;
	private String commLength;// 通话时长;
	private String deptAvgCommLength;// 部门平均通话时长;         //
	private BigDecimal commScore;// 通话得分;
	private Integer commSort;// 通话排名;
	private Integer bussinessChanceNum;// 新成交询价数;
	private Integer assignBussinessChanceNum;// 分配总询价数;
	private String bussinessChanceRate;// 转化率;
	private String deptAvgBussinessChanceRate;// 部门平均转化率;  //
	private BigDecimal bussinessChanceRateScore;// 转化率得分;
	private Integer bussinessChanceRateSort;// 转化率排名;
	private BigDecimal bdScore;
	private Integer bdSort;
	private BigDecimal totalScore;// 总得分;
	private Integer totalSort;// 总排名;


	private Integer userId;
	private Integer orgId2;
	private Integer orgId3;
	private Integer companyId;
	private Integer configOrgId; // 配置团队ID 如平台、科研等
	private Integer callTime;
	private Integer yesterDayTotalSort;
	private BigDecimal yesterDayTotalScore;
	private BigDecimal preMonthLastDayTotalScore;
	private Integer preMonthLastDayTotalSort;
	private Integer reqType;
	private String timeStr;
	/**
	 * 团队Id
	 */
	private Integer groupId;
	/**
	 * 团队名称
	 */
	private String groupName;

	public BigDecimal getBdScore() {
		return bdScore;
	}

	public void setBdScore(BigDecimal bdScore) {
		this.bdScore = bdScore;
	}

	public Integer getBdSort() {
		return bdSort;
	}

	public void setBdSort(Integer bdSort) {
		this.bdSort = bdSort;
	}

	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getOrgName2()
	{
		return orgName2;
	}
	public void setOrgName2(String orgName2)
	{
		this.orgName2 = orgName2;
	}
	public String getOrgName3()
	{
		return orgName3;
	}
	public void setOrgName3(String orgName3)
	{
		this.orgName3 = orgName3;
	}
	public BigDecimal getGoalMonth()
	{
		return goalMonth;
	}
	public void setGoalMonth(BigDecimal goalMonth)
	{
		this.goalMonth = goalMonth;
	}
	public BigDecimal getAmount()
	{
		return amount;
	}
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
	public String getAmountRate()
	{
		return amountRate;
	}
	public void setAmountRate(String amountRate)
	{
		this.amountRate = amountRate;
	}
	public String getDeptAmountRate()
	{
		return deptAmountRate;
	}
	public void setDeptAmountRate(String deptAmountRate)
	{
		this.deptAmountRate = deptAmountRate;
	}
	public BigDecimal getScore()
	{
		return score;
	}
	public void setScore(BigDecimal score)
	{
		this.score = score;
	}
	public Integer getSort()
	{
		return sort;
	}
	public void setSort(Integer sort)
	{
		this.sort = sort;
	}
	public Integer getCustomerNum()
	{
		return customerNum;
	}
	public void setCustomerNum(Integer customerNum)
	{
		this.customerNum = customerNum;
	}
	public BigDecimal getDeptAvgCustomerNum()
	{
		return deptAvgCustomerNum;
	}
	public void setDeptAvgCustomerNum(BigDecimal deptAvgCustomerNum)
	{
		this.deptAvgCustomerNum = deptAvgCustomerNum;
	}
	public BigDecimal getCustomerScore()
	{
		return customerScore;
	}
	public void setCustomerScore(BigDecimal customerScore)
	{
		this.customerScore = customerScore;
	}
	public Integer getCustomerSort()
	{
		return customerSort;
	}
	public void setCustomerSort(Integer customerSort)
	{
		this.customerSort = customerSort;
	}
	public Integer getBrandNum()
	{
		return brandNum;
	}
	public void setBrandNum(Integer brandNum)
	{
		this.brandNum = brandNum;
	}
	public BigDecimal getDeptAvgBrandNum()
	{
		return deptAvgBrandNum;
	}
	public void setDeptAvgBrandNum(BigDecimal deptAvgBrandNum)
	{
		this.deptAvgBrandNum = deptAvgBrandNum;
	}
	public BigDecimal getBrandScore()
	{
		return brandScore;
	}
	public void setBrandScore(BigDecimal brandScore)
	{
		this.brandScore = brandScore;
	}
	public Integer getBrandSort()
	{
		return brandSort;
	}
	public void setBrandSort(Integer brandSort)
	{
		this.brandSort = brandSort;
	}
	public String getCommLength()
	{
		return commLength;
	}
	public void setCommLength(String commLength)
	{
		this.commLength = commLength;
	}
	public String getDeptAvgCommLength()
	{
		return deptAvgCommLength;
	}
	public void setDeptAvgCommLength(String deptAvgCommLength)
	{
		this.deptAvgCommLength = deptAvgCommLength;
	}
	public BigDecimal getCommScore()
	{
		return commScore;
	}
	public void setCommScore(BigDecimal commScore)
	{
		this.commScore = commScore;
	}
	public Integer getCommSort()
	{
		return commSort;
	}
	public void setCommSort(Integer commSort)
	{
		this.commSort = commSort;
	}
	public Integer getBussinessChanceNum()
	{
		return bussinessChanceNum;
	}
	public void setBussinessChanceNum(Integer bussinessChanceNum)
	{
		this.bussinessChanceNum = bussinessChanceNum;
	}
	public Integer getAssignBussinessChanceNum()
	{
		return assignBussinessChanceNum;
	}
	public void setAssignBussinessChanceNum(Integer assignBussinessChanceNum)
	{
		this.assignBussinessChanceNum = assignBussinessChanceNum;
	}
	public String getBussinessChanceRate()
	{
		return bussinessChanceRate;
	}
	public void setBussinessChanceRate(String bussinessChanceRate)
	{
		this.bussinessChanceRate = bussinessChanceRate;
	}
	public String getDeptAvgBussinessChanceRate()
	{
		return deptAvgBussinessChanceRate;
	}
	public void setDeptAvgBussinessChanceRate(String deptAvgBussinessChanceRate)
	{
		this.deptAvgBussinessChanceRate = deptAvgBussinessChanceRate;
	}
	public BigDecimal getBussinessChanceRateScore()
	{
		return bussinessChanceRateScore;
	}
	public void setBussinessChanceRateScore(BigDecimal bussinessChanceRateScore)
	{
		this.bussinessChanceRateScore = bussinessChanceRateScore;
	}
	public Integer getBussinessChanceRateSort()
	{
		return bussinessChanceRateSort;
	}
	public void setBussinessChanceRateSort(Integer bussinessChanceRateSort)
	{
		this.bussinessChanceRateSort = bussinessChanceRateSort;
	}
	public BigDecimal getTotalScore()
	{
		return totalScore;
	}
	public void setTotalScore(BigDecimal totalScore)
	{
		this.totalScore = totalScore;
	}
	public Integer getTotalSort()
	{
		return totalSort;
	}
	public void setTotalSort(Integer totalSort)
	{
		this.totalSort = totalSort;
	}
	public Integer getUserId()
	{
		return userId;
	}
	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}
	public Integer getOrgId2()
	{
		return orgId2;
	}
	public void setOrgId2(Integer orgId2)
	{
		this.orgId2 = orgId2;
	}
	public Integer getOrgId3()
	{
		return orgId3;
	}
	public void setOrgId3(Integer orgId3)
	{
		this.orgId3 = orgId3;
	}
	public Integer getCompanyId()
	{
		return companyId;
	}
	public void setCompanyId(Integer companyId)
	{
		this.companyId = companyId;
	}
	public Integer getConfigOrgId()
	{
		return configOrgId;
	}
	public void setConfigOrgId(Integer configOrgId)
	{
		this.configOrgId = configOrgId;
	}
	public Integer getCallTime()
	{
		return callTime;
	}
	public void setCallTime(Integer callTime)
	{
		this.callTime = callTime;
	}
	public Integer getYesterDayTotalSort()
	{
		return yesterDayTotalSort;
	}
	public void setYesterDayTotalSort(Integer yesterDayTotalSort)
	{
		this.yesterDayTotalSort = yesterDayTotalSort;
	}
	public BigDecimal getYesterDayTotalScore()
	{
		return yesterDayTotalScore;
	}
	public void setYesterDayTotalScore(BigDecimal yesterDayTotalScore)
	{
		this.yesterDayTotalScore = yesterDayTotalScore;
	}
	public BigDecimal getPreMonthLastDayTotalScore()
	{
		return preMonthLastDayTotalScore;
	}
	public void setPreMonthLastDayTotalScore(BigDecimal preMonthLastDayTotalScore)
	{
		this.preMonthLastDayTotalScore = preMonthLastDayTotalScore;
	}
	public Integer getPreMonthLastDayTotalSort()
	{
		return preMonthLastDayTotalSort;
	}
	public void setPreMonthLastDayTotalSort(Integer preMonthLastDayTotalSort)
	{
		this.preMonthLastDayTotalSort = preMonthLastDayTotalSort;
	}
	public Integer getReqType() {
		return reqType;
	}
	public void setReqType(Integer reqType) {
		this.reqType = reqType;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	
	

}
