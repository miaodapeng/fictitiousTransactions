package com.vedeng.aftersales.model;

import java.io.Serializable;

public class AfterSalesInvoice implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer afterSalesInvoiceId;

    private Integer afterSalesId;

    private Integer invoiceId;

    private Integer isRefundInvoice;

    private Integer status;
    
    private Integer isSendInvoice;

    public Integer getAfterSalesInvoiceId() {
        return afterSalesInvoiceId;
    }

    public void setAfterSalesInvoiceId(Integer afterSalesInvoiceId) {
        this.afterSalesInvoiceId = afterSalesInvoiceId;
    }

    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getIsRefundInvoice() {
        return isRefundInvoice;
    }

    public void setIsRefundInvoice(Integer isRefundInvoice) {
        this.isRefundInvoice = isRefundInvoice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}

}