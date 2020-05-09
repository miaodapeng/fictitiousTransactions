package com.vedeng.aftersales.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AfterSalesDetail implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer afterSalesDetailId;

    private Integer afterSalesId;
    
    private String traderName;

    private Integer reason;

	private String comments;
    
    private Integer traderId;

    private Integer traderContactId;

    private String traderContactName;

    private String traderContactMobile;

    private String traderContactTelephone;

    private Integer refund;

    private Integer areaId;

    private Integer addressId;

    private String area;

    private String address;

    private BigDecimal refundAmount;

    private BigDecimal refundFee;

    private BigDecimal realRefundAmount;

    private Integer refundAmountStatus;

    private Integer traderSubject;
    
    private Integer traderMode;//新增字段-交易方式2018-1-31

    private String payee;

    private String bank;

    private String bankCode;

    private String bankAccount;
    
    private BigDecimal serviceAmount;
    
    private Integer serviceAmountStatus;

    private Integer invoiceType;

    private Integer isSendInvoice;
    
    private BigDecimal payPeriodAmount;//偿还帐期金额2018-4-11
    private BigDecimal paymentAmount;//2018-06-04已付款金额不含帐期，排除当前售后订单
    /******2018-4-16 新增*******/
    private Integer invoiceTraderId;

    private String invoiceTraderName;

    private Integer invoiceTraderContactId;

    private String invoiceTraderContactName;

    private String invoiceTraderContactMobile;

    private String invoiceTraderContactTelephone;

    private Integer invoiceTraderAddressId;
    
    private String invoiceTraderArea;

    private String invoiceTraderAddress;
    
    private String invoiceComments;
    /******2018-9-4 新增******/
    private Integer receiveInvoiceStatus;
    
    private Long receiveInvoiceTime;
    /******2018-9-4 新增 END******/
    /******2018-4-16 新增**结束*****/
    
    private String bussinessNo;
    
    private Integer receivePaymentStatus;//收款状态
    
    private Long receivePaymentTime;//收款时间
    
    private Integer paymentStatus;//付款状态
    
    private Long paymentTime;//付款时间


    private Integer refundTwo;
    
    public Integer getReceiveInvoiceStatus() {
		return receiveInvoiceStatus;
	}

	public void setReceiveInvoiceStatus(Integer receiveInvoiceStatus) {
		this.receiveInvoiceStatus = receiveInvoiceStatus;
	}

	public Long getReceiveInvoiceTime() {
		return receiveInvoiceTime;
	}

	public void setReceiveInvoiceTime(Long receiveInvoiceTime) {
		this.receiveInvoiceTime = receiveInvoiceTime;
	}

	public Integer getReceivePaymentStatus() {
		return receivePaymentStatus;
	}

	public void setReceivePaymentStatus(Integer receivePaymentStatus) {
		this.receivePaymentStatus = receivePaymentStatus;
	}

	public Long getReceivePaymentTime() {
		return receivePaymentTime;
	}

	public void setReceivePaymentTime(Long receivePaymentTime) {
		this.receivePaymentTime = receivePaymentTime;
	}

	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}
    
    public BigDecimal getPayPeriodAmount() {
		return payPeriodAmount;
	}

	public void setPayPeriodAmount(BigDecimal payPeriodAmount) {
		this.payPeriodAmount = payPeriodAmount;
	}

	public Integer getTraderMode() {
		return traderMode;
	}

	public void setTraderMode(Integer traderMode) {
		this.traderMode = traderMode;
	}

	public Integer getServiceAmountStatus() {
		return serviceAmountStatus;
	}

	public void setServiceAmountStatus(Integer serviceAmountStatus) {
		this.serviceAmountStatus = serviceAmountStatus;
	}

	public Integer getAfterSalesDetailId() {
        return afterSalesDetailId;
    }

    public void setAfterSalesDetailId(Integer afterSalesDetailId) {
        this.afterSalesDetailId = afterSalesDetailId;
    }

    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getTraderContactId() {
        return traderContactId;
    }

    public void setTraderContactId(Integer traderContactId) {
        this.traderContactId = traderContactId;
    }

    public String getTraderContactName() {
        return traderContactName;
    }

    public void setTraderContactName(String traderContactName) {
        this.traderContactName = traderContactName == null ? null : traderContactName.trim();
    }

    public String getTraderContactMobile() {
        return traderContactMobile;
    }

    public void setTraderContactMobile(String traderContactMobile) {
        this.traderContactMobile = traderContactMobile == null ? null : traderContactMobile.trim();
    }

    public String getTraderContactTelephone() {
        return traderContactTelephone;
    }

    public void setTraderContactTelephone(String traderContactTelephone) {
        this.traderContactTelephone = traderContactTelephone == null ? null : traderContactTelephone.trim();
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public BigDecimal getRealRefundAmount() {
        return realRefundAmount;
    }

    public void setRealRefundAmount(BigDecimal realRefundAmount) {
        this.realRefundAmount = realRefundAmount;
    }

    public Integer getRefundAmountStatus() {
        return refundAmountStatus;
    }

    public void setRefundAmountStatus(Integer refundAmountStatus) {
        this.refundAmountStatus = refundAmountStatus;
    }

    public Integer getTraderSubject() {
        return traderSubject;
    }

    public void setTraderSubject(Integer traderSubject) {
        this.traderSubject = traderSubject;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getInvoiceTraderId() {
		return invoiceTraderId;
	}

	public void setInvoiceTraderId(Integer invoiceTraderId) {
		this.invoiceTraderId = invoiceTraderId;
	}

	public String getInvoiceTraderName() {
		return invoiceTraderName;
	}

	public void setInvoiceTraderName(String invoiceTraderName) {
		this.invoiceTraderName = invoiceTraderName==null ? null :invoiceTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getInvoiceTraderContactId() {
		return invoiceTraderContactId;
	}

	public void setInvoiceTraderContactId(Integer invoiceTraderContactId) {
		this.invoiceTraderContactId = invoiceTraderContactId;
	}

	public String getInvoiceTraderContactName() {
		return invoiceTraderContactName;
	}

	public void setInvoiceTraderContactName(String invoiceTraderContactName) {
		this.invoiceTraderContactName = invoiceTraderContactName;
	}

	public String getInvoiceTraderContactMobile() {
		return invoiceTraderContactMobile;
	}

	public void setInvoiceTraderContactMobile(String invoiceTraderContactMobile) {
		this.invoiceTraderContactMobile = invoiceTraderContactMobile;
	}

	public String getInvoiceTraderContactTelephone() {
		return invoiceTraderContactTelephone;
	}

	public void setInvoiceTraderContactTelephone(String invoiceTraderContactTelephone) {
		this.invoiceTraderContactTelephone = invoiceTraderContactTelephone;
	}

	public Integer getInvoiceTraderAddressId() {
		return invoiceTraderAddressId;
	}

	public void setInvoiceTraderAddressId(Integer invoiceTraderAddressId) {
		this.invoiceTraderAddressId = invoiceTraderAddressId;
	}

	public String getInvoiceTraderArea() {
		return invoiceTraderArea;
	}

	public void setInvoiceTraderArea(String invoiceTraderArea) {
		this.invoiceTraderArea = invoiceTraderArea;
	}

	public String getInvoiceTraderAddress() {
		return invoiceTraderAddress;
	}

	public void setInvoiceTraderAddress(String invoiceTraderAddress) {
		this.invoiceTraderAddress = invoiceTraderAddress;
	}

	public String getInvoiceComments() {
		return invoiceComments;
	}

	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

    public Integer getRefundTwo() {
        return refundTwo;
    }

    public void setRefundTwo(Integer refundTwo) {
        this.refundTwo = refundTwo;
    }
}