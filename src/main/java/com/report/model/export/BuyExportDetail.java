package com.report.model.export;

import java.math.BigDecimal;

public class BuyExportDetail {
	
	private Integer companyId,buyorderId;
	
	private String buyorderNo,traderName,validTimeStr,paymentTypeStr,payTimeStr;
	
	private Integer userId,orgId,traderId,deliveryDirect,invoiceStatus,arrivalStatus,paymentStatus,serviceStatus;
	
	private String lastTimeEndStr,firstInWarehouseTimeStr,lastInWarehouseTimeStr,invoiceTypeStr;
	
	private BigDecimal totalAmount,buyPayAmount,lackAccountPeriodAmount;
	
	private String userName,orgNm,traderUserNm,traderUserOrgNm;
	
	private String auditUserNm_1,auditTimeStr_1,auditStatus_1,auditUserNm_2,auditTimeStr_2,auditStatus_2,auditUserNm_3,auditTimeStr_3,auditStatus_3;
	
	
	
	public String getAuditUserNm_1() {
		return auditUserNm_1;
	}

	public void setAuditUserNm_1(String auditUserNm_1) {
		this.auditUserNm_1 = auditUserNm_1;
	}

	public String getAuditTimeStr_1() {
		return auditTimeStr_1;
	}

	public void setAuditTimeStr_1(String auditTimeStr_1) {
		this.auditTimeStr_1 = auditTimeStr_1;
	}

	public String getAuditStatus_1() {
		return auditStatus_1;
	}

	public void setAuditStatus_1(String auditStatus_1) {
		this.auditStatus_1 = auditStatus_1;
	}

	public String getAuditUserNm_2() {
		return auditUserNm_2;
	}

	public void setAuditUserNm_2(String auditUserNm_2) {
		this.auditUserNm_2 = auditUserNm_2;
	}

	public String getAuditTimeStr_2() {
		return auditTimeStr_2;
	}

	public void setAuditTimeStr_2(String auditTimeStr_2) {
		this.auditTimeStr_2 = auditTimeStr_2;
	}

	public String getAuditStatus_2() {
		return auditStatus_2;
	}

	public void setAuditStatus_2(String auditStatus_2) {
		this.auditStatus_2 = auditStatus_2;
	}

	public String getAuditUserNm_3() {
		return auditUserNm_3;
	}

	public void setAuditUserNm_3(String auditUserNm_3) {
		this.auditUserNm_3 = auditUserNm_3;
	}

	public String getAuditTimeStr_3() {
		return auditTimeStr_3;
	}

	public void setAuditTimeStr_3(String auditTimeStr_3) {
		this.auditTimeStr_3 = auditTimeStr_3;
	}

	public String getAuditStatus_3() {
		return auditStatus_3;
	}

	public void setAuditStatus_3(String auditStatus_3) {
		this.auditStatus_3 = auditStatus_3;
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
	}

	public String getTraderUserNm() {
		return traderUserNm;
	}

	public void setTraderUserNm(String traderUserNm) {
		this.traderUserNm = traderUserNm;
	}

	public String getTraderUserOrgNm() {
		return traderUserOrgNm;
	}

	public void setTraderUserOrgNm(String traderUserOrgNm) {
		this.traderUserOrgNm = traderUserOrgNm;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}

	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getLastTimeEndStr() {
		return lastTimeEndStr;
	}

	public void setLastTimeEndStr(String lastTimeEndStr) {
		this.lastTimeEndStr = lastTimeEndStr;
	}

	public String getFirstInWarehouseTimeStr() {
		return firstInWarehouseTimeStr;
	}

	public void setFirstInWarehouseTimeStr(String firstInWarehouseTimeStr) {
		this.firstInWarehouseTimeStr = firstInWarehouseTimeStr;
	}

	public String getLastInWarehouseTimeStr() {
		return lastInWarehouseTimeStr;
	}

	public void setLastInWarehouseTimeStr(String lastInWarehouseTimeStr) {
		this.lastInWarehouseTimeStr = lastInWarehouseTimeStr;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getBuyPayAmount() {
		return buyPayAmount;
	}

	public void setBuyPayAmount(BigDecimal buyPayAmount) {
		this.buyPayAmount = buyPayAmount;
	}

	public BigDecimal getLackAccountPeriodAmount() {
		return lackAccountPeriodAmount;
	}

	public void setLackAccountPeriodAmount(BigDecimal lackAccountPeriodAmount) {
		this.lackAccountPeriodAmount = lackAccountPeriodAmount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public String getPayTimeStr() {
		return payTimeStr;
	}

	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}

}
