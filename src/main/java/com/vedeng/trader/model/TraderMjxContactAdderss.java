package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderMjxContactAdderss implements Serializable{
	private String phone;//用户手机号
	private String deliveryUserName;//收货人名称
	private Integer deliveryAddressId;//收货地址ID
	private String deliveryUserAddress;//收件人地址
	private String deliveryUserPhone;//收件人手机
	private String deliveryLevel1Id;//省Id
	private String deliveryLevel2Id;//市id
	private String deliveryLevel3Id;//区id
	private String deliveryAreaIds;//省市区id
	private Integer isEnable;//是否有效 0否 1是
	/** 是否默认收货地址 0否 1是  IS_DELIVERY_DEFAULT **/
    private Integer isDeliveryDefault;

    /** 是否默认收票地址 0否 1是  IS_INVOICE_DEFAULT **/
    private Integer isInvoiceDefault;
    
    private String titleName;//标签名称
    
    private Integer mjxContactAdderssId; //MJX主键
	
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public Integer getMjxContactAdderssId() {
		return mjxContactAdderssId;
	}
	public void setMjxContactAdderssId(Integer mjxContactAdderssId) {
		this.mjxContactAdderssId = mjxContactAdderssId;
	}
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	public Integer getIsDeliveryDefault() {
		return isDeliveryDefault;
	}
	public void setIsDeliveryDefault(Integer isDeliveryDefault) {
		this.isDeliveryDefault = isDeliveryDefault;
	}
	public Integer getIsInvoiceDefault() {
		return isInvoiceDefault;
	}
	public void setIsInvoiceDefault(Integer isInvoiceDefault) {
		this.isInvoiceDefault = isInvoiceDefault;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeliveryUserName() {
		return deliveryUserName;
	}
	public void setDeliveryUserName(String deliveryUserName) {
		this.deliveryUserName = deliveryUserName;
	}
	public Integer getDeliveryAddressId() {
		return deliveryAddressId;
	}
	public void setDeliveryAddressId(Integer deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}
	public String getDeliveryUserAddress() {
		return deliveryUserAddress;
	}
	public void setDeliveryUserAddress(String deliveryUserAddress) {
		this.deliveryUserAddress = deliveryUserAddress;
	}
	public String getDeliveryUserPhone() {
		return deliveryUserPhone;
	}
	public void setDeliveryUserPhone(String deliveryUserPhone) {
		this.deliveryUserPhone = deliveryUserPhone;
	}
	public String getDeliveryLevel1Id() {
		return deliveryLevel1Id;
	}
	public void setDeliveryLevel1Id(String deliveryLevel1Id) {
		this.deliveryLevel1Id = deliveryLevel1Id;
	}
	public String getDeliveryLevel2Id() {
		return deliveryLevel2Id;
	}
	public void setDeliveryLevel2Id(String deliveryLevel2Id) {
		this.deliveryLevel2Id = deliveryLevel2Id;
	}
	public String getDeliveryLevel3Id() {
		return deliveryLevel3Id;
	}
	public void setDeliveryLevel3Id(String deliveryLevel3Id) {
		this.deliveryLevel3Id = deliveryLevel3Id;
	}
	public String getDeliveryAreaIds() {
		return deliveryAreaIds;
	}
	public void setDeliveryAreaIds(String deliveryAreaIds) {
		this.deliveryAreaIds = deliveryAreaIds;
	}
	
}
