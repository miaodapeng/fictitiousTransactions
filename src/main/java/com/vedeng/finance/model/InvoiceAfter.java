package com.vedeng.finance.model;

import java.io.Serializable;
import java.util.List;

public class InvoiceAfter implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer afterSalesId;//售后表主键

    private String afterSalesNo;//售后单号

    private Integer subjectType;//售后主体类型：535销售、536采购、537第三方

    private Integer type;//售后类型

    private Integer orderId;//订单ID

    private String orderNo;//订单号

    private Integer serviceUserId;//归属售后人员

    private Integer validStatus;//是否生效 0否 1是

    private Long validTime;//生效时间

    private Integer status;//售后订单审核状态：0待确认（默认）、1审核中、2审核通过、3审核不通过

    private Integer atferSalesStatus;//售后订单状态：0待确认（默认）、1进行中、2已完结、3已关闭
    
    private Integer refundAmountStatus;//退款状态
    
    private Integer serviceAmountStatus;//收款状态
    
    private Integer isRefundInvoice;//是否需要退票
    
    private Integer refundInvoiceStatus;//退票状态
    
    private Integer openInvoiceStatus;//开票状态

    private Long addTime;

    private Integer creator;

    private Long modTime;//最近一次编辑时间

    private Integer updater;
    
    private List<Integer> afterTypeList;//查询售后业务类型
    
    private String typeName;//售后类型名称
    
    private Integer timeType;//日期查询类型
    
    private Long startTime;//起始时间
    
    private Long endTime;//结束时间
    
    private String traderName;//合同客户

    private String traderId;//交易者ID

    private Integer companyId;
    private Integer receivePaymentStatus;//收款状态
    
    
    
	public Integer getReceivePaymentStatus() {
		return receivePaymentStatus;
	}

	public void setReceivePaymentStatus(Integer receivePaymentStatus) {
		this.receivePaymentStatus = receivePaymentStatus;
	}

	public String getTraderId() {
		return traderId;
	}

	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getOpenInvoiceStatus() {
		return openInvoiceStatus;
	}

	public void setOpenInvoiceStatus(Integer openInvoiceStatus) {
		this.openInvoiceStatus = openInvoiceStatus;
	}

	public Integer getRefundAmountStatus() {
		return refundAmountStatus;
	}

	public void setRefundAmountStatus(Integer refundAmountStatus) {
		this.refundAmountStatus = refundAmountStatus;
	}

	public Integer getServiceAmountStatus() {
		return serviceAmountStatus;
	}

	public void setServiceAmountStatus(Integer serviceAmountStatus) {
		this.serviceAmountStatus = serviceAmountStatus;
	}

	public Integer getIsRefundInvoice() {
		return isRefundInvoice;
	}

	public void setIsRefundInvoice(Integer isRefundInvoice) {
		this.isRefundInvoice = isRefundInvoice;
	}

	public Integer getRefundInvoiceStatus() {
		return refundInvoiceStatus;
	}

	public void setRefundInvoiceStatus(Integer refundInvoiceStatus) {
		this.refundInvoiceStatus = refundInvoiceStatus;
	}

	public InvoiceAfter() {
		super();
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Integer> getAfterTypeList() {
		return afterTypeList;
	}

	public void setAfterTypeList(List<Integer> afterTypeList) {
		this.afterTypeList = afterTypeList;
	}

	public Integer getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(Integer afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getServiceUserId() {
		return serviceUserId;
	}

	public void setServiceUserId(Integer serviceUserId) {
		this.serviceUserId = serviceUserId;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAtferSalesStatus() {
		return atferSalesStatus;
	}

	public void setAtferSalesStatus(Integer atferSalesStatus) {
		this.atferSalesStatus = atferSalesStatus;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
    
}
