package com.vedeng.order.model.vo;

import java.math.BigDecimal;

public class OrderGoodsData {

    private String skuNo;
    private Integer productNum;//商品数量
    private BigDecimal jxSalePrice;//销售价
    private BigDecimal marketMomey;//市场价
    private String storeRemarks;//商家备注
    
	public String getStoreRemarks() {
		return storeRemarks;
	}

	public void setStoreRemarks(String storeRemarks) {
		this.storeRemarks = storeRemarks;
	}

	public BigDecimal getMarketMomey() {
		return marketMomey;
	}

	public void setMarketMomey(BigDecimal marketMomey) {
		this.marketMomey = marketMomey;
	}

	public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getJxSalePrice() {
        return jxSalePrice;
    }

    public void setJxSalePrice(BigDecimal jxSalePrice) {
        this.jxSalePrice = jxSalePrice;
    }

    @Override
	public String toString() {
		return "OrderGoodsData [skuNo=" + skuNo + ", productNum=" + productNum + "]";
	}
    
}
