package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AccountPeriod implements Serializable{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer traderId;//客户-供应商：ID
	
	private String traderName;//客户-供应商：名称

	private Integer relatedId;//销售或采购：订单ID
	
	private Long validTime;// 订单生效日期
	
	private BigDecimal totalAmount;// 订单总额

	private BigDecimal accountPeriodAmount;// 账期支付金额

	private Long accountEndTime;// 账期截止日期

	private Long deliveryTime;// 第一次发货日期-账期开始日期

	private BigDecimal amount;// 资金流水金额
	
	private Long traderTime;// 资金到账交易时间
	
	private Integer periodDay;//账期期限
	
	private Long startDate, endDate;// 开始结束日期
	
	private String saleorderNo;//销售订单号
	
	private String buyorderNo;//采购订单号
	
	private Integer overdueState;//逾期状态
	
	private Integer traderUserId;// 客户归属用户ID

	private List<Integer> traderIdList;// 归属销售
	
	private String traderUserNm;//交易者归属销售/采购人员

	private Integer orderNum;// 订单数
	
	private Integer companyId;

	private String validTimeStr,deliveryTimeStr,accountEndTimeStr,tradertimeStr;
	
	private List<Integer> userIdList;
	
	
	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public String getDeliveryTimeStr() {
		return deliveryTimeStr;
	}

	public void setDeliveryTimeStr(String deliveryTimeStr) {
		this.deliveryTimeStr = deliveryTimeStr;
	}

	public String getAccountEndTimeStr() {
		return accountEndTimeStr;
	}

	public void setAccountEndTimeStr(String accountEndTimeStr) {
		this.accountEndTimeStr = accountEndTimeStr;
	}

	public String getTradertimeStr() {
		return tradertimeStr;
	}

	public void setTradertimeStr(String tradertimeStr) {
		this.tradertimeStr = tradertimeStr;
	}

	public String getTraderUserNm() {
		return traderUserNm;
	}

	public void setTraderUserNm(String traderUserNm) {
		this.traderUserNm = traderUserNm;
	}

	public AccountPeriod() {
		super();
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public Integer getOverdueState() {
		return overdueState;
	}

	public void setOverdueState(Integer overdueState) {
		this.overdueState = overdueState;
	}

	public Integer getPeriodDay() {
		return periodDay;
	}

	public void setPeriodDay(Integer periodDay) {
		this.periodDay = periodDay;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
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

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAccountPeriodAmount() {
		return accountPeriodAmount;
	}

	public void setAccountPeriodAmount(BigDecimal accountPeriodAmount) {
		this.accountPeriodAmount = accountPeriodAmount;
	}

	public Long getAccountEndTime() {
		return accountEndTime;
	}

	public void setAccountEndTime(Long accountEndTime) {
		this.accountEndTime = accountEndTime;
	}

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getTraderUserId() {
		return traderUserId;
	}

	public void setTraderUserId(Integer traderUserId) {
		this.traderUserId = traderUserId;
	}

	public List<Integer> getTraderIdList() {
		return traderIdList;
	}

	public void setTraderIdList(List<Integer> traderIdList) {
		this.traderIdList = traderIdList;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getTraderTime() {
		return traderTime;
	}

	public void setTraderTime(Long traderTime) {
		this.traderTime = traderTime;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
