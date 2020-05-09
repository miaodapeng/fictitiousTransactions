package com.vedeng.order.model;

import java.io.Serializable;

public class RBuyorderGoodsCodeJBracode implements Serializable{
    private Integer rBuyorderGoodsCodeJBarcodeJ;

    private Integer buyorderGoodsCodeId;

    private Integer barcodeId;

    public Integer getrBuyorderGoodsCodeJBarcodeJ() {
        return rBuyorderGoodsCodeJBarcodeJ;
    }

    public void setrBuyorderGoodsCodeJBarcodeJ(Integer rBuyorderGoodsCodeJBarcodeJ) {
        this.rBuyorderGoodsCodeJBarcodeJ = rBuyorderGoodsCodeJBarcodeJ;
    }

    public Integer getBuyorderGoodsCodeId() {
        return buyorderGoodsCodeId;
    }

    public void setBuyorderGoodsCodeId(Integer buyorderGoodsCodeId) {
        this.buyorderGoodsCodeId = buyorderGoodsCodeId;
    }

    public Integer getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(Integer barcodeId) {
        this.barcodeId = barcodeId;
    }
}