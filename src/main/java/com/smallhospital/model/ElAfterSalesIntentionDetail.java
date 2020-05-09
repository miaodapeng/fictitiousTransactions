package com.smallhospital.model;

/**
 * @author Daniel
 * @date created in 2020/1/21 11:37
 */
public class ElAfterSalesIntentionDetail {

    private Integer elAfterSalesIntentionDetailId;

    private Integer elAfterSalesIntentionId;

    private String saleorderNo;

    private String skuNo;

    private String skuName;

    /**
     * 退货数量
     */
    private Integer afterSaleCount;


    public ElAfterSalesIntentionDetail() {
    }

    public ElAfterSalesIntentionDetail(Integer elAfterSalesIntentionId, String skuNo, String skuName, Integer afterSaleCount) {
        this.elAfterSalesIntentionId = elAfterSalesIntentionId;
        this.skuNo = skuNo;
        this.skuName = skuName;
        this.afterSaleCount = afterSaleCount;
    }

    public ElAfterSalesIntentionDetail(String saleorderNo, String skuNo, String skuName, Integer afterSaleCount) {
        this.saleorderNo = saleorderNo;
        this.skuNo = skuNo;
        this.skuName = skuName;
        this.afterSaleCount = afterSaleCount;
    }

    public ElAfterSalesIntentionDetail(String skuNo, String skuName, Integer afterSaleCount) {
        this.skuNo = skuNo;
        this.skuName = skuName;
        this.afterSaleCount = afterSaleCount;
    }

    public ElAfterSalesIntentionDetail(Integer elAfterSalesIntentionId, String saleorderNo, String skuNo, String skuName, Integer afterSaleCount) {
        this.elAfterSalesIntentionId = elAfterSalesIntentionId;
        this.saleorderNo = saleorderNo;
        this.skuNo = skuNo;
        this.skuName = skuName;
        this.afterSaleCount = afterSaleCount;
    }

    public Integer getElAfterSalesIntentionDetailId() {
        return elAfterSalesIntentionDetailId;
    }

    public void setElAfterSalesIntentionDetailId(Integer elAfterSalesIntentionDetailId) {
        this.elAfterSalesIntentionDetailId = elAfterSalesIntentionDetailId;
    }

    public Integer getElAfterSalesIntentionId() {
        return elAfterSalesIntentionId;
    }

    public void setElAfterSalesIntentionId(Integer elAfterSalesIntentionId) {
        this.elAfterSalesIntentionId = elAfterSalesIntentionId;
    }

    public String getSaleorderNo() {
        return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getAfterSaleCount() {
        return afterSaleCount;
    }

    public void setAfterSaleCount(Integer afterSaleCount) {
        this.afterSaleCount = afterSaleCount;
    }

    @Override
    public String toString() {
        return "ElAfterSalesIntentionDetail{" +
                "elAfterSalesIntentionDetailId=" + elAfterSalesIntentionDetailId +
                ", elAfterSalesIntentionId=" + elAfterSalesIntentionId +
                ", skuNo='" + skuNo + '\'' +
                ", skuName='" + skuName + '\'' +
                ", afterSaleCount=" + afterSaleCount +
                '}';
    }
}
