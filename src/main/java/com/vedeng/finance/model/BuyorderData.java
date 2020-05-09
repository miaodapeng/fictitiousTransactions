package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Description:</b><br> 采购订单
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.finance.model
 * <br><b>ClassName:</b> BuyorderData
 * <br><b>Date:</b> 2017年10月23日 下午3:20:11
 */
public class BuyorderData implements Serializable{
	
	private Integer buyorderId;//采购订单ID
	
	private BigDecimal paymentAmount;//已付款金额
	
	private BigDecimal lackAccountPeriodAmount;//应付账期额
	
	private BigDecimal invoiceAmount;//已收票总额
	
	private Integer deliveryNum;//订单产品发货数量

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getLackAccountPeriodAmount() {
		return lackAccountPeriodAmount;
	}

	public void setLackAccountPeriodAmount(BigDecimal lackAccountPeriodAmount) {
		this.lackAccountPeriodAmount = lackAccountPeriodAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

}
