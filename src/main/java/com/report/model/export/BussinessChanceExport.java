package com.report.model.export;

import java.math.BigDecimal;

public class BussinessChanceExport {
	
	private Integer bussinessChanceId;
	
	private Integer type,source,communication,orgId,userId,status,areaId,goodsCategory,saleOrderStatus,statusComments,creator;

	private String bussinessChanceNo,receiveTimeStr,addTimeStr,assignTimeStr,traderName;
	
	private String traderContactName,mobile,telephone,otherContact,content,goodsBrand,goodsName;
	
	private String quoteOrderNo,quoteOrderAddTimeStr,saleOrderNo,saleOrderAddTimeStr,firstViewTimeStr,closedComments,categoryNameArr;
	
	private BigDecimal quoteOrderTotalAmount,saleOrderTotalAmount;
	
	private Long firstViewTime,assignTime;
	
	private String saleDeptName,saleUser,typeName,sourceName,communicationName,closeReason,respondTime,creatorName,areaName;

	
	public String getCategoryNameArr() {
		return categoryNameArr;
	}

	public void setCategoryNameArr(String categoryNameArr) {
		this.categoryNameArr = categoryNameArr;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getRespondTime() {
		return respondTime;
	}

	public void setRespondTime(String respondTime) {
		this.respondTime = respondTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getCommunicationName() {
		return communicationName;
	}

	public void setCommunicationName(String communicationName) {
		this.communicationName = communicationName;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getSaleUser() {
		return saleUser;
	}

	public void setSaleUser(String saleUser) {
		this.saleUser = saleUser;
	}

	public String getSaleDeptName() {
		return saleDeptName;
	}

	public void setSaleDeptName(String saleDeptName) {
		this.saleDeptName = saleDeptName;
	}

	public Integer getBussinessChanceId() {
		return bussinessChanceId;
	}

	public void setBussinessChanceId(Integer bussinessChanceId) {
		this.bussinessChanceId = bussinessChanceId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getCommunication() {
		return communication;
	}

	public void setCommunication(Integer communication) {
		this.communication = communication;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(Integer goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getBussinessChanceNo() {
		return bussinessChanceNo;
	}

	public void setBussinessChanceNo(String bussinessChanceNo) {
		this.bussinessChanceNo = bussinessChanceNo;
	}

	public String getReceiveTimeStr() {
		return receiveTimeStr;
	}

	public void setReceiveTimeStr(String receiveTimeStr) {
		this.receiveTimeStr = receiveTimeStr;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getAssignTimeStr() {
		return assignTimeStr;
	}

	public void setAssignTimeStr(String assignTimeStr) {
		this.assignTimeStr = assignTimeStr;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getTraderContactName() {
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName) {
		this.traderContactName = traderContactName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOtherContact() {
		return otherContact;
	}

	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGoodsBrand() {
		return goodsBrand;
	}

	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getSaleOrderStatus() {
		return saleOrderStatus;
	}

	public void setSaleOrderStatus(Integer saleOrderStatus) {
		this.saleOrderStatus = saleOrderStatus;
	}

	public String getQuoteOrderNo() {
		return quoteOrderNo;
	}

	public void setQuoteOrderNo(String quoteOrderNo) {
		this.quoteOrderNo = quoteOrderNo;
	}

	public String getQuoteOrderAddTimeStr() {
		return quoteOrderAddTimeStr;
	}

	public void setQuoteOrderAddTimeStr(String quoteOrderAddTimeStr) {
		this.quoteOrderAddTimeStr = quoteOrderAddTimeStr;
	}

	public String getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getSaleOrderAddTimeStr() {
		return saleOrderAddTimeStr;
	}

	public void setSaleOrderAddTimeStr(String saleOrderAddTimeStr) {
		this.saleOrderAddTimeStr = saleOrderAddTimeStr;
	}

	public String getFirstViewTimeStr() {
		return firstViewTimeStr;
	}

	public void setFirstViewTimeStr(String firstViewTimeStr) {
		this.firstViewTimeStr = firstViewTimeStr;
	}

	public Integer getStatusComments() {
		return statusComments;
	}

	public void setStatusComments(Integer statusComments) {
		this.statusComments = statusComments;
	}

	public String getClosedComments() {
		return closedComments;
	}

	public void setClosedComments(String closedComments) {
		this.closedComments = closedComments;
	}

	public BigDecimal getQuoteOrderTotalAmount() {
		return quoteOrderTotalAmount;
	}

	public void setQuoteOrderTotalAmount(BigDecimal quoteOrderTotalAmount) {
		this.quoteOrderTotalAmount = quoteOrderTotalAmount;
	}

	public BigDecimal getSaleOrderTotalAmount() {
		return saleOrderTotalAmount;
	}

	public void setSaleOrderTotalAmount(BigDecimal saleOrderTotalAmount) {
		this.saleOrderTotalAmount = saleOrderTotalAmount;
	}

	public Long getFirstViewTime() {
		return firstViewTime;
	}

	public void setFirstViewTime(Long firstViewTime) {
		this.firstViewTime = firstViewTime;
	}

	public Long getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Long assignTime) {
		this.assignTime = assignTime;
	}
	
}
