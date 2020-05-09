package com.vedeng.call.model;

import java.io.Serializable;

public class CallOut implements Serializable{
	private String phone;//被叫电话
	
	private Integer callType;//呼出类型 1商机2销售订单3报价4售后5采购订单
	
	private Integer traderId;//交易者id
	
	private Integer traderType;//交易者类型 1客户 2供应商
	
	private Integer orderId;//对应类型订单ID
	
	private Integer traderContactId;//联系人ID
	
	private String coid;//电话录音唯一编码
	
	private Integer callFrom;//0呼入 1呼出 2内呼

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCallType() {
		return callType;
	}

	public void setCallType(Integer callType) {
		this.callType = callType;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public String getCoid() {
		return coid;
	}

	public void setCoid(String coid) {
		this.coid = coid;
	}

	public Integer getCallFrom() {
		return callFrom;
	}

	public void setCallFrom(Integer callFrom) {
		this.callFrom = callFrom;
	}
	
	
}
