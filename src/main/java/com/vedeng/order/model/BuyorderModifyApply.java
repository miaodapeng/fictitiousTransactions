package com.vedeng.order.model;

import java.io.Serializable;

public class BuyorderModifyApply implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer buyorderModifyApplyId;

    private String buyorderModifyApplyNo;

    private Integer companyId;

    private Integer buyorderId;
    
    private Integer validStatus;

    private Long validTime;

    private Integer deliveryDirect;

    private Integer takeTraderId;

    private String takeTraderName;

    private Integer takeTraderContactId;

    private String takeTraderContactName;

    private String takeTraderContactMobile;

    private String takeTraderContactTelephone;

    private Integer takeTraderAddressId;

    private String takeTraderArea;

    private String takeTraderAddress;

    private String logisticsComments;

    private Integer invoiceType;

    private String invoiceComments;

    private Integer oldDeliveryDirect;

    private Integer oldTakeTraderId;

    private String oldTakeTraderName;

    private Integer oldTakeTraderContactId;

    private String oldTakeTraderContactName;

    private String oldTakeTraderContactMobile;

    private String oldTakeTraderContactTelephone;

    private Integer oldTakeTraderAddressId;

    private String oldTakeTraderArea;

    private String oldTakeTraderAddress;

    private String oldLogisticsComments;

    private Integer oldInvoiceType;

    private String oldInvoiceComments;

    private Long addTime;

    private Integer creator;
    
    private String creatorName;//创建人
    
    private Integer verifiesType;//审核类型
    
    private String verifyUsername;//当前审核人
    
    private Integer verifyStatus;//审核状态

    public Integer getBuyorderModifyApplyId() {
        return buyorderModifyApplyId;
    }

    public void setBuyorderModifyApplyId(Integer buyorderModifyApplyId) {
        this.buyorderModifyApplyId = buyorderModifyApplyId;
    }

    public String getBuyorderModifyApplyNo() {
        return buyorderModifyApplyNo;
    }

    public void setBuyorderModifyApplyNo(String buyorderModifyApplyNo) {
        this.buyorderModifyApplyNo = buyorderModifyApplyNo == null ? null : buyorderModifyApplyNo.trim();
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

    public Integer getDeliveryDirect() {
        return deliveryDirect;
    }

    public void setDeliveryDirect(Integer deliveryDirect) {
        this.deliveryDirect = deliveryDirect;
    }

    public Integer getTakeTraderId() {
        return takeTraderId;
    }

    public void setTakeTraderId(Integer takeTraderId) {
        this.takeTraderId = takeTraderId;
    }

    public String getTakeTraderName() {
        return takeTraderName;
    }

    public void setTakeTraderName(String takeTraderName) {
        this.takeTraderName = takeTraderName==null ? null :takeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getTakeTraderContactId() {
        return takeTraderContactId;
    }

    public void setTakeTraderContactId(Integer takeTraderContactId) {
        this.takeTraderContactId = takeTraderContactId;
    }

    public String getTakeTraderContactName() {
        return takeTraderContactName;
    }

    public void setTakeTraderContactName(String takeTraderContactName) {
        this.takeTraderContactName = takeTraderContactName == null ? null : takeTraderContactName.trim();
    }

    public String getTakeTraderContactMobile() {
        return takeTraderContactMobile;
    }

    public void setTakeTraderContactMobile(String takeTraderContactMobile) {
        this.takeTraderContactMobile = takeTraderContactMobile == null ? null : takeTraderContactMobile.trim();
    }

    public String getTakeTraderContactTelephone() {
        return takeTraderContactTelephone;
    }

    public void setTakeTraderContactTelephone(String takeTraderContactTelephone) {
        this.takeTraderContactTelephone = takeTraderContactTelephone == null ? null : takeTraderContactTelephone.trim();
    }

    public Integer getTakeTraderAddressId() {
        return takeTraderAddressId;
    }

    public void setTakeTraderAddressId(Integer takeTraderAddressId) {
        this.takeTraderAddressId = takeTraderAddressId;
    }

    public String getTakeTraderArea() {
        return takeTraderArea;
    }

    public void setTakeTraderArea(String takeTraderArea) {
        this.takeTraderArea = takeTraderArea == null ? null : takeTraderArea.trim();
    }

    public String getTakeTraderAddress() {
        return takeTraderAddress;
    }

    public void setTakeTraderAddress(String takeTraderAddress) {
        this.takeTraderAddress = takeTraderAddress == null ? null : takeTraderAddress.trim();
    }

    public String getLogisticsComments() {
        return logisticsComments;
    }

    public void setLogisticsComments(String logisticsComments) {
        this.logisticsComments = logisticsComments == null ? null : logisticsComments.trim();
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceComments() {
        return invoiceComments;
    }

    public void setInvoiceComments(String invoiceComments) {
        this.invoiceComments = invoiceComments == null ? null : invoiceComments.trim();
    }

    public Integer getOldDeliveryDirect() {
        return oldDeliveryDirect;
    }

    public void setOldDeliveryDirect(Integer oldDeliveryDirect) {
        this.oldDeliveryDirect = oldDeliveryDirect;
    }

    public Integer getOldTakeTraderId() {
        return oldTakeTraderId;
    }

    public void setOldTakeTraderId(Integer oldTakeTraderId) {
        this.oldTakeTraderId = oldTakeTraderId;
    }

    public String getOldTakeTraderName() {
        return oldTakeTraderName;
    }

    public void setOldTakeTraderName(String oldTakeTraderName) {
        this.oldTakeTraderName = oldTakeTraderName == null ? null : oldTakeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getOldTakeTraderContactId() {
        return oldTakeTraderContactId;
    }

    public void setOldTakeTraderContactId(Integer oldTakeTraderContactId) {
        this.oldTakeTraderContactId = oldTakeTraderContactId;
    }

    public String getOldTakeTraderContactName() {
        return oldTakeTraderContactName;
    }

    public void setOldTakeTraderContactName(String oldTakeTraderContactName) {
        this.oldTakeTraderContactName = oldTakeTraderContactName == null ? null : oldTakeTraderContactName.trim();
    }

    public String getOldTakeTraderContactMobile() {
        return oldTakeTraderContactMobile;
    }

    public void setOldTakeTraderContactMobile(String oldTakeTraderContactMobile) {
        this.oldTakeTraderContactMobile = oldTakeTraderContactMobile == null ? null : oldTakeTraderContactMobile.trim();
    }

    public String getOldTakeTraderContactTelephone() {
        return oldTakeTraderContactTelephone;
    }

    public void setOldTakeTraderContactTelephone(String oldTakeTraderContactTelephone) {
        this.oldTakeTraderContactTelephone = oldTakeTraderContactTelephone == null ? null : oldTakeTraderContactTelephone.trim();
    }

    public Integer getOldTakeTraderAddressId() {
        return oldTakeTraderAddressId;
    }

    public void setOldTakeTraderAddressId(Integer oldTakeTraderAddressId) {
        this.oldTakeTraderAddressId = oldTakeTraderAddressId;
    }

    public String getOldTakeTraderArea() {
        return oldTakeTraderArea;
    }

    public void setOldTakeTraderArea(String oldTakeTraderArea) {
        this.oldTakeTraderArea = oldTakeTraderArea == null ? null : oldTakeTraderArea.trim();
    }

    public String getOldTakeTraderAddress() {
        return oldTakeTraderAddress;
    }

    public void setOldTakeTraderAddress(String oldTakeTraderAddress) {
        this.oldTakeTraderAddress = oldTakeTraderAddress == null ? null : oldTakeTraderAddress.trim();
    }

    public String getOldLogisticsComments() {
        return oldLogisticsComments;
    }

    public void setOldLogisticsComments(String oldLogisticsComments) {
        this.oldLogisticsComments = oldLogisticsComments == null ? null : oldLogisticsComments.trim();
    }

    public Integer getOldInvoiceType() {
        return oldInvoiceType;
    }

    public void setOldInvoiceType(Integer oldInvoiceType) {
        this.oldInvoiceType = oldInvoiceType;
    }

    public String getOldInvoiceComments() {
        return oldInvoiceComments;
    }

    public void setOldInvoiceComments(String oldInvoiceComments) {
        this.oldInvoiceComments = oldInvoiceComments == null ? null : oldInvoiceComments.trim();
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

    public String getCreatorName() {
	return creatorName;
    }

    public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
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
}