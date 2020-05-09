package com.vedeng.order.model;

import java.io.Serializable;

public class SaleorderInvoiceApply implements Serializable{
    private Integer saleorderInvoiceApplyId;

    private Integer saleorderId;

    private Integer status;

    private Integer verifyUserId;

    private Long verifyTime;

    private String comments;

    private Long addTime;

    private Integer creator;

    private Integer isAuto;

    private Integer invoiceStStatus;

    private Integer invoicePrintStatus;

    private Integer isAdvance;

    public Integer getSaleorderInvoiceApplyId() {
        return saleorderInvoiceApplyId;
    }

    public void setSaleorderInvoiceApplyId(Integer saleorderInvoiceApplyId) {
        this.saleorderInvoiceApplyId = saleorderInvoiceApplyId;
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVerifyUserId() {
        return verifyUserId;
    }

    public void setVerifyUserId(Integer verifyUserId) {
        this.verifyUserId = verifyUserId;
    }

    public Long getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Long verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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

    public Integer getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    public Integer getInvoiceStStatus() {
        return invoiceStStatus;
    }

    public void setInvoiceStStatus(Integer invoiceStStatus) {
        this.invoiceStStatus = invoiceStStatus;
    }

    public Integer getInvoicePrintStatus() {
        return invoicePrintStatus;
    }

    public void setInvoicePrintStatus(Integer invoicePrintStatus) {
        this.invoicePrintStatus = invoicePrintStatus;
    }

    public Integer getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(Integer isAdvance) {
        this.isAdvance = isAdvance;
    }
}