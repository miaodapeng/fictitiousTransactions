package com.meinian.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能描述
 * className
 * 向美年发送订单消息的包装类
 * @author Bert
 * @date 2018/11/12 16:23
 * @Description //TODO
 * @version: 1.0
 */
@Data
public class SendData  implements Serializable {
    //批次
    private String batchNumber;

    //批次的时间
    private Long batchExpiryTime;

    //备注
    private String remark;
    //产品的id
    private String productCode;

    //价格
    private BigDecimal price;

    //订单号
    private String orderNumber;
    //供货数量
    private Integer supplyNumber;
    //供方货号/供货平台产品ID
    private String supplierNumber;
    //
    private String serialNumber;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Long getBatchExpiryTime() {
        return batchExpiryTime;
    }

    public void setBatchExpiryTime(Long batchExpiryTime) {
        this.batchExpiryTime = batchExpiryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getSupplyNumber() {
        return supplyNumber;
    }

    public void setSupplyNumber(Integer supplyNumber) {
        this.supplyNumber = supplyNumber;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
