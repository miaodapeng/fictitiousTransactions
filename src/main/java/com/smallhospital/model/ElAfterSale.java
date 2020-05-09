package com.smallhospital.model;


import java.util.Date;

/**
 * 小医院发送过来的售后申请类
 * 保存小医院的售后申请id
 */
public class ElAfterSale {

    /**
     * 主键
     */
    private Integer elAfterSaleId;

    /**
     * erp里的销售订单id
     */
    private Integer erpAfterSaleId;

    /**
     * 小医院系统的售后单id
     */
    private String elReturnId;

    /**
     * 审批结果，0：关闭订单，1：同意
     */
    private Integer status;

    private Date addTime;

    private Date updateTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getElAfterSaleId() {
        return elAfterSaleId;
    }

    public void setElAfterSaleId(Integer elAfterSaleId) {
        this.elAfterSaleId = elAfterSaleId;
    }

    public Integer getErpAfterSaleId() {
        return erpAfterSaleId;
    }

    public void setErpAfterSaleId(Integer erpAfterSaleId) {
        this.erpAfterSaleId = erpAfterSaleId;
    }

    public String getElReturnId() {
        return elReturnId;
    }

    public void setElReturnId(String elReturnId) {
        this.elReturnId = elReturnId;
    }

    public ElAfterSale(Integer erpAfterSaleId, String elReturnId, Date addTime) {
        this.erpAfterSaleId = erpAfterSaleId;
        this.elReturnId = elReturnId;
        this.addTime = addTime;
    }

    public ElAfterSale() {
    }

    public ElAfterSale(Integer erpAfterSaleId, Integer status) {
        this.erpAfterSaleId = erpAfterSaleId;
        this.status = status;
    }

    public ElAfterSale(Integer elAfterSaleId, Integer erpAfterSaleId, String elReturnId, Integer status) {
        this.elAfterSaleId = elAfterSaleId;
        this.erpAfterSaleId = erpAfterSaleId;
        this.elReturnId = elReturnId;
        this.status = status;
    }
}
