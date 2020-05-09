package com.smallhospital.dto;

import java.math.BigDecimal;

public class ELOrderItemDto {

    private String orderListId;
    private String orderId;
    private Integer productId;
    private Integer orderQuantity;
    private String remark;
    private String contractListId;
    private BigDecimal purPrice;

    public String getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(String orderListId) {
        this.orderListId = orderListId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContractListId() {
        return contractListId;
    }

    public void setContractListId(String contractListId) {
        this.contractListId = contractListId;
    }

    public BigDecimal getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(BigDecimal purPrice) {
        this.purPrice = purPrice;
    }
}
