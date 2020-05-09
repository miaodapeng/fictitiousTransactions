package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TraderAccountPeriodApply implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderAccountPeriodApplyId;// 账期申请主键

	private Integer traderId;// 交易者ID

	private Integer traderType;// 所属类型 1::经销商（包含终端）2:供应商

	private BigDecimal accountPeriodNow;// 当前账期总额度

	private BigDecimal accountPeriodApply;// 账期额度（申请）

	private BigDecimal accountPeriodLeft;// 剩余额度（申请时的）

	private Integer accountPeriodDaysNow;// 账期天数（申请时现有的）

	private Integer accountPeriodDaysApply;// 申请的天数

	private Integer usedTimes;// 使用次数

	private Integer overdueTimes;// 逾期次数

	private BigDecimal overdueAmount;// 逾期金额

	private Integer saleorderId;// 销售订单ID

	private BigDecimal predictProfit;// 预期利润

	private Integer status;// 审核状态0未审核 1审核通过 2审核不通过

	private String comments;// 备注

	private Long addTime;

	private String addTimeStr;

	private Integer creator;

	private String creatorNm;

	private Long modTime;

	private Integer updater;

	private String saleorderNo;// 销售单号

	private String traderName;// 交易者名称
	
	private String customerNature;// 交易者性质

	private String customerNatureStr;// 交易者性质

	private Long startDate, endDate;// 开始结束日期

	private String auditReason;// 审核原因

	private Integer verifiesType;//审核类型
	    
	private String verifyUsername;//当前审核人
	
	private List<String> verifyUsernameList;//当前审核人
	    
	private Integer verifyStatus;//当前审核人
	
	private String verifyTimeStr;//审核时间
	
	private Integer companyId;
	
	private List<Integer> userList;
	
	private String validUserName;//审核人名称
	
	
	
	public List<Integer> getUserList() {
		return userList;
	}

	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}

	public String getVerifyTimeStr() {
		return verifyTimeStr;
	}

	public void setVerifyTimeStr(String verifyTimeStr) {
		this.verifyTimeStr = verifyTimeStr;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public TraderAccountPeriodApply() {
		super();
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getCustomerNatureStr() {
		return customerNatureStr;
	}

	public void setCustomerNatureStr(String customerNatureStr) {
		this.customerNatureStr = customerNatureStr;
	}

	public Integer getTraderAccountPeriodApplyId() {
		return traderAccountPeriodApplyId;
	}

	public void setTraderAccountPeriodApplyId(Integer traderAccountPeriodApplyId) {
		this.traderAccountPeriodApplyId = traderAccountPeriodApplyId;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public BigDecimal getAccountPeriodNow() {
		return accountPeriodNow;
	}

	public void setAccountPeriodNow(BigDecimal accountPeriodNow) {
		this.accountPeriodNow = accountPeriodNow;
	}

	public BigDecimal getAccountPeriodApply() {
		return accountPeriodApply;
	}

	public void setAccountPeriodApply(BigDecimal accountPeriodApply) {
		this.accountPeriodApply = accountPeriodApply;
	}

	public BigDecimal getAccountPeriodLeft() {
		return accountPeriodLeft;
	}

	public void setAccountPeriodLeft(BigDecimal accountPeriodLeft) {
		this.accountPeriodLeft = accountPeriodLeft;
	}

	public Integer getAccountPeriodDaysNow() {
		return accountPeriodDaysNow;
	}

	public void setAccountPeriodDaysNow(Integer accountPeriodDaysNow) {
		this.accountPeriodDaysNow = accountPeriodDaysNow;
	}

	public Integer getAccountPeriodDaysApply() {
		return accountPeriodDaysApply;
	}

	public void setAccountPeriodDaysApply(Integer accountPeriodDaysApply) {
		this.accountPeriodDaysApply = accountPeriodDaysApply;
	}

	public Integer getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Integer getOverdueTimes() {
		return overdueTimes;
	}

	public void setOverdueTimes(Integer overdueTimes) {
		this.overdueTimes = overdueTimes;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public BigDecimal getPredictProfit() {
		return predictProfit;
	}

	public void setPredictProfit(BigDecimal predictProfit) {
		this.predictProfit = predictProfit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments == null ? null : comments.trim();
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

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getVerifiesType() {
	    return verifiesType;
	}

	public void setVerifiesType(Integer verifiesType) {
	    this.verifiesType = verifiesType;
	}

	public String getVerifyUsername() {
	    return verifyUsername;
	}

	public void setVerifyUsername(String verifyUsername) {
	    this.verifyUsername = verifyUsername;
	}

	public Integer getVerifyStatus() {
	    return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
	    this.verifyStatus = verifyStatus;
	}

	public List<String> getVerifyUsernameList() {
	    return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
	    this.verifyUsernameList = verifyUsernameList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getValidUserName() {
	    return validUserName;
	}

	public void setValidUserName(String validUserName) {
	    this.validUserName = validUserName;
	}
}
