package com.vedeng.order.model;

import java.io.Serializable;

public class RBuyorderSaleorder implements Serializable{
    private Integer rBuyorderJSaleorderId;

    private Integer buyorderGoodsId;

    private Integer saleorderGoodsId;
    
    private Integer num;//销售产品中采购数量

    public Integer getrBuyorderJSaleorderId() {
        return rBuyorderJSaleorderId;
    }

    public void setrBuyorderJSaleorderId(Integer rBuyorderJSaleorderId) {
        this.rBuyorderJSaleorderId = rBuyorderJSaleorderId;
    }

    public Integer getBuyorderGoodsId() {
        return buyorderGoodsId;
    }

    public void setBuyorderGoodsId(Integer buyorderGoodsId) {
        this.buyorderGoodsId = buyorderGoodsId;
    }

    public Integer getSaleorderGoodsId() {
        return saleorderGoodsId;
    }

    public void setSaleorderGoodsId(Integer saleorderGoodsId) {
        this.saleorderGoodsId = saleorderGoodsId;
    }

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}