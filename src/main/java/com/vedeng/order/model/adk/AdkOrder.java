package com.vedeng.order.model.adk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdkOrder {
	private String orderNo;
	private String traderName;
	private BigDecimal totalAmount;
	private String takeTraderContactName;

	private String takeTraderContactTelephone;
	private String takeTraderContactMobile;
	private String invoiceTraderContactName;

	private String invoiceTraderContactTelephone;
	private String invoiceTraderContactMobile;
	private String comments;
	private List<AdkOrderGoods> orderGoods = new ArrayList<AdkOrderGoods>();

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTakeTraderContactName() {
		return takeTraderContactName;
	}

	public void setTakeTraderContactName(String takeTraderContactName) {
		this.takeTraderContactName = takeTraderContactName;
	}

	public String getTakeTraderContactTelephone() {
		return takeTraderContactTelephone;
	}

	public void setTakeTraderContactTelephone(String takeTraderContactTelephone) {
		this.takeTraderContactTelephone = takeTraderContactTelephone;
	}

	public String getInvoiceTraderContactName() {
		return invoiceTraderContactName;
	}

	public void setInvoiceTraderContactName(String invoiceTraderContactName) {
		this.invoiceTraderContactName = invoiceTraderContactName;
	}

	public String getInvoiceTraderContactTelephone() {
		return invoiceTraderContactTelephone;
	}

	public void setInvoiceTraderContactTelephone(String invoiceTraderContactTelephone) {
		this.invoiceTraderContactTelephone = invoiceTraderContactTelephone;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<AdkOrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<AdkOrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public String getInvoiceTraderContactMobile() {
		return invoiceTraderContactMobile;
	}

	public void setInvoiceTraderContactMobile(String invoiceTraderContactMobile) {
		this.invoiceTraderContactMobile = invoiceTraderContactMobile;
	}

	public String getTakeTraderContactMobile() {
		return takeTraderContactMobile;
	}

	public void setTakeTraderContactMobile(String takeTraderContactMobile) {
		this.takeTraderContactMobile = takeTraderContactMobile;
	}

}
