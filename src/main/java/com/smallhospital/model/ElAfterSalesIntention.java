package com.smallhospital.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ElAfterSalesIntention {

    private Integer elAfterSaleIntentionId;

    private Integer saleorderId;

    private String saleorderNo;

    private String traderName;

    /**
     * 售后类型，0退货单请求，1订单终止请求
     */
    private Integer type;

    /**
     * 医流网对应的售后请求id
     */
    private String elAfterSaleId;

    /**
     * 0：待处理，1：同意，-1：不同意
     */
    private Integer status;

    private Long addTime;

    private Long updateTime;

    public ElAfterSalesIntention() {
    }

    public ElAfterSalesIntention(Integer saleorderId, String saleorderNo, String traderName, Integer type, String elAfterSaleId, Integer status, Long addTime) {
        this.saleorderId = saleorderId;
        this.saleorderNo = saleorderNo;
        this.traderName = traderName;
        this.type = type;
        this.elAfterSaleId = elAfterSaleId;
        this.status = status;
        this.addTime = addTime;
    }

    public Integer getElAfterSaleIntentionId() {
        return elAfterSaleIntentionId;
    }

    public void setElAfterSaleIntentionId(Integer elAfterSaleIntentionId) {
        this.elAfterSaleIntentionId = elAfterSaleIntentionId;
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

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getSaleorderNo() {
        return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getElAfterSaleId() {
        return elAfterSaleId;
    }

    public void setElAfterSaleId(String elAfterSaleId) {
        this.elAfterSaleId = elAfterSaleId;
    }

    @Override
    public String toString() {
        return "ElAfterSalesIntention{" +
                "elAfterSaleIntentionId=" + elAfterSaleIntentionId +
                ", saleorderId=" + saleorderId +
                ", saleorderNo='" + saleorderNo + '\'' +
                ", traderName='" + traderName + '\'' +
                ", type=" + type +
                ", elAfterSaleId='" + elAfterSaleId + '\'' +
                ", status=" + status +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
