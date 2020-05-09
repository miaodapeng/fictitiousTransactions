package com.vedeng.goods.model;

import java.io.Serializable;

public class RGoodsJTraderSupplier implements Serializable{
    private Integer rGoodsJTraderSupplier;

    private Integer goodsId;

    private Integer traderSupplierId;

    public Integer getrGoodsJTraderSupplier() {
        return rGoodsJTraderSupplier;
    }

    public void setrGoodsJTraderSupplier(Integer rGoodsJTraderSupplier) {
        this.rGoodsJTraderSupplier = rGoodsJTraderSupplier;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getTraderSupplierId() {
        return traderSupplierId;
    }

    public void setTraderSupplierId(Integer traderSupplierId) {
        this.traderSupplierId = traderSupplierId;
    }
}