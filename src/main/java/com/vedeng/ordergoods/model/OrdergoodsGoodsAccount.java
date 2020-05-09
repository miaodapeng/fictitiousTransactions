package com.vedeng.ordergoods.model;

import java.math.BigDecimal;

public class OrdergoodsGoodsAccount {
    private Integer ordergoodsGoodsAccountId;

    private Integer ordergoodsStoreId;

    private Integer webAccountId;

    private Integer goodsId;

    private BigDecimal price;
    
    private Integer minQuantity1;
    
    private BigDecimal price1;
    
    private Integer minQuantity2;
    
    private BigDecimal price2;
  
    private Integer minQuantity3;
    
    private BigDecimal price3;
    
    private Integer minQuantity4;
    
    private BigDecimal price4;

    private Long addTime;

    private Integer creator;

    public Integer getOrdergoodsGoodsAccountId() {
        return ordergoodsGoodsAccountId;
    }

    public void setOrdergoodsGoodsAccountId(Integer ordergoodsGoodsAccountId) {
        this.ordergoodsGoodsAccountId = ordergoodsGoodsAccountId;
    }

    public Integer getOrdergoodsStoreId() {
        return ordergoodsStoreId;
    }

    public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
        this.ordergoodsStoreId = ordergoodsStoreId;
    }

    public Integer getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Integer webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

	public Integer getMinQuantity1() {
		return minQuantity1;
	}

	public void setMinQuantity1(Integer minQuantity1) {
		this.minQuantity1 = minQuantity1;
	}

	public BigDecimal getPrice1() {
		return price1;
	}

	public void setPrice1(BigDecimal price1) {
		this.price1 = price1;
	}

	public Integer getMinQuantity2() {
		return minQuantity2;
	}

	public void setMinQuantity2(Integer minQuantity2) {
		this.minQuantity2 = minQuantity2;
	}

	public BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}

	public Integer getMinQuantity3() {
		return minQuantity3;
	}

	public void setMinQuantity3(Integer minQuantity3) {
		this.minQuantity3 = minQuantity3;
	}

	public BigDecimal getPrice3() {
		return price3;
	}

	public void setPrice3(BigDecimal price3) {
		this.price3 = price3;
	}

	public Integer getMinQuantity4() {
		return minQuantity4;
	}

	public void setMinQuantity4(Integer minQuantity4) {
		this.minQuantity4 = minQuantity4;
	}

	public BigDecimal getPrice4() {
		return price4;
	}

	public void setPrice4(BigDecimal price4) {
		this.price4 = price4;
	}
	
}