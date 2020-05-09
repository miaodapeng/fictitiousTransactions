package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.CommunicateRecord;

public class CommunicateRecordVo extends CommunicateRecord{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer afterSalesId;

    private String afterSalesNo;
    
    private String typeName;//售后类型名称
	
    private String orderNo;
    
    private Integer afterTraderId;//售后对象中的traderId.....
    
    private Integer realTraderType;//售后对象的真正类型（客户or供应商）
    
    private String traderName;
    
    private String name;
    
    private String mobile;

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAfterTraderId() {
		return afterTraderId;
	}

	public void setAfterTraderId(Integer afterTraderId) {
		this.afterTraderId = afterTraderId;
	}

	public Integer getRealTraderType() {
		return realTraderType;
	}

	public void setRealTraderType(Integer realTraderType) {
		this.realTraderType = realTraderType;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
