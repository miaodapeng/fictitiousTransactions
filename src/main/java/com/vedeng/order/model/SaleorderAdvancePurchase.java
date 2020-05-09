package com.vedeng.order.model;

import java.io.Serializable;

public class SaleorderAdvancePurchase implements Serializable{
    private Integer saleorderAdvancePurchaseId;

    private Integer saleorderId;

    private Integer status;

    private Integer verifyUserId;

    private Long verifyTime;

    private String content;

    private String comments;

    private Long addTime;

    private Integer creator;

    public Integer getSaleorderAdvancePurchaseId() {
        return saleorderAdvancePurchaseId;
    }

    public void setSaleorderAdvancePurchaseId(Integer saleorderAdvancePurchaseId) {
        this.saleorderAdvancePurchaseId = saleorderAdvancePurchaseId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
}