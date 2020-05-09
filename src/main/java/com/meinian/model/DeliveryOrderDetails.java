package com.meinian.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import java.util.Date;

/**
 * 功能描述
 * className 这个类是用来推送消息的
 *
 * @author Bert
 * @date 2018/11/9 18:19
 * @Description //TODO
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryOrderDetails implements Serializable {
    /**
     * orderNumber物流单号
     */
    private String orderNumber;
    /**
     * 供方货号/订货平（系统的编码）
     */
    private String supplierNumber;
    /**
     * 产品编码（供应链系统的编码）
     */
    private String productCode;

    /**
     * 供货数量
     */
    private Long supplyNumber;
    /**
     * 批次
     */
    private String batchNumber;
    /**
     *单价(元)
     */
    private Double price;
    /**
     * 批次效期
     */
    private String batchExpiryTime;
    /**
     * 备注
     */
    private String remark;
    //设备类型
    private String serialNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getSupplyNumber() {
        return supplyNumber;
    }

    public void setSupplyNumber(Long supplyNumber) {
        this.supplyNumber = supplyNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBatchExpiryTime() {
        return batchExpiryTime;
    }

    public void setBatchExpiryTime(String batchExpiryTime) {
        this.batchExpiryTime = batchExpiryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
