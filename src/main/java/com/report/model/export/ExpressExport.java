package com.report.model.export;

import java.math.BigDecimal;

public class ExpressExport {

	private Integer expressId;
	
	private String logisticsNo,logisticsComments,expressName,xsNo,sjName;
	
	private String deliveryTimeStr,arrivalTimeStr,addTimeStr;
	
	private Integer arrivalStatus,creator,businessType,ywId,ywType;
	
	private BigDecimal amount;

	private String deliveryArea,deliveryAddress,creatName;
	
	
	
	public String getCreatName() {
		return creatName;
	}

	public void setCreatName(String creatName) {
		this.creatName = creatName;
	}

	public String getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Integer getYwType() {
		return ywType;
	}

	public void setYwType(Integer ywType) {
		this.ywType = ywType;
	}

	public String getXsNo() {
		return xsNo;
	}

	public void setXsNo(String xsNo) {
		this.xsNo = xsNo;
	}

	public String getSjName() {
		return sjName;
	}

	public void setSjName(String sjName) {
		this.sjName = sjName;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getYwId() {
		return ywId;
	}

	public void setYwId(Integer ywId) {
		this.ywId = ywId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsComments() {
		return logisticsComments;
	}

	public void setLogisticsComments(String logisticsComments) {
		this.logisticsComments = logisticsComments;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getDeliveryTimeStr() {
		return deliveryTimeStr;
	}

	public void setDeliveryTimeStr(String deliveryTimeStr) {
		this.deliveryTimeStr = deliveryTimeStr;
	}

	public String getArrivalTimeStr() {
		return arrivalTimeStr;
	}

	public void setArrivalTimeStr(String arrivalTimeStr) {
		this.arrivalTimeStr = arrivalTimeStr;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
}
