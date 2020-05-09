package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class orderInfo implements Serializable{
	
	private Integer invoiceDetailId;
	
	private Integer detailgoodsId;
	
	private Integer invoiceId;
	
	private Integer buyorderId;
	
    private String buyorderNo;//
    
    private Integer arrivalNum;
    
    private Long validTime;
    
    private Integer userId;
    
    private BigDecimal price;
    
    private String userName;

	public orderInfo() {
		super();
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(Integer invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Integer getDetailgoodsId() {
		return detailgoodsId;
	}

	public void setDetailgoodsId(Integer detailgoodsId) {
		this.detailgoodsId = detailgoodsId;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public Integer getArrivalNum() {
		return arrivalNum;
	}

	public void setArrivalNum(Integer arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}