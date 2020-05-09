package com.vedeng.goods.model;

import java.io.Serializable;

public class GoodsStockDetail implements Serializable{
    private Integer goodsStockDetailId;

    private Integer goodsId;

    private Integer stockType;

    private Integer num;

    private String comments;

    private Long addTime;

    public Integer getGoodsStockDetailId() {
        return goodsStockDetailId;
    }

    public void setGoodsStockDetailId(Integer goodsStockDetailId) {
        this.goodsStockDetailId = goodsStockDetailId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
}