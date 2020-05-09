package com.vedeng.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsChannelPriceExtend implements Serializable{
    private Integer goodsChannelPriceExtendId;

    private Integer goodsChannelPriceId;

    private Integer priceType;

    private Integer conditionType;

    private Long startTime;

    private Long endTime;

    private Integer minNum;

    private Integer maxNum;

    private BigDecimal batchPrice;

    public Integer getGoodsChannelPriceExtendId() {
        return goodsChannelPriceExtendId;
    }

    public void setGoodsChannelPriceExtendId(Integer goodsChannelPriceExtendId) {
        this.goodsChannelPriceExtendId = goodsChannelPriceExtendId;
    }

    public Integer getGoodsChannelPriceId() {
        return goodsChannelPriceId;
    }

    public void setGoodsChannelPriceId(Integer goodsChannelPriceId) {
        this.goodsChannelPriceId = goodsChannelPriceId;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getConditionType() {
        return conditionType;
    }

    public void setConditionType(Integer conditionType) {
        this.conditionType = conditionType;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getBatchPrice() {
        return batchPrice;
    }

    public void setBatchPrice(BigDecimal batchPrice) {
        this.batchPrice = batchPrice;
    }
}