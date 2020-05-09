package com.smallhospital.dto;

import com.smallhospital.model.ElAfterSalesIntentionDetail;

import java.util.List;

/**
 * @author Daniel
 * @date created in 2020/1/21 14:04
 */
public class ElAfterSaleIntentionDto {

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

    private List<ElAfterSalesIntentionDetail> details;

    public ElAfterSaleIntentionDto() {
    }

    public ElAfterSaleIntentionDto(Integer saleorderId, String saleorderNo, String traderName, String elAfterSaleId) {
        this.saleorderId = saleorderId;
        this.saleorderNo = saleorderNo;
        this.traderName = traderName;
        this.elAfterSaleId = elAfterSaleId;
    }

    public ElAfterSaleIntentionDto(Integer saleorderId, String saleorderNo, String traderName) {
        this.saleorderId = saleorderId;
        this.saleorderNo = saleorderNo;
        this.traderName = traderName;
    }

    public ElAfterSaleIntentionDto(Integer saleorderId, String saleorderNo, String traderName, String elAfterSaleId, List<ElAfterSalesIntentionDetail> details) {
        this.saleorderId = saleorderId;
        this.saleorderNo = saleorderNo;
        this.traderName = traderName;
        this.elAfterSaleId = elAfterSaleId;
        this.details = details;
    }

    public Integer getElAfterSaleIntentionId() {
        return elAfterSaleIntentionId;
    }

    public void setElAfterSaleIntentionId(Integer elAfterSaleIntentionId) {
        this.elAfterSaleIntentionId = elAfterSaleIntentionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
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

    public String getElAfterSaleId() {
        return elAfterSaleId;
    }

    public void setElAfterSaleId(String elAfterSaleId) {
        this.elAfterSaleId = elAfterSaleId;
    }

    public List<ElAfterSalesIntentionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ElAfterSalesIntentionDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ElAfterSaleIntentionDto{" +
                "saleorderId=" + saleorderId +
                ", saleorderNo='" + saleorderNo + '\'' +
                ", traderName='" + traderName + '\'' +
                ", elAfterSaleId='" + elAfterSaleId + '\'' +
                ", details=" + details +
                '}';
    }
}
