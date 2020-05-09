package com.vedeng.trader.model;

import java.io.Serializable;

import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.Goods;

public class TraderOrderGoods implements Serializable{
    private Integer traderOrderGoodsId;

    private Integer traderId;

    private Integer traderType;

    private Integer goodsId;

    private Integer brandId;

    private Integer categoryId;
    
    private Integer areaId;
    
    private String categoryName; //品类
    
    private String brandName;//品牌
    
    private String area;//区域
    
    private String areaName;//区域名称
    
    private String goodsName;//产品
    
    public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getTraderOrderGoodsId() {
        return traderOrderGoodsId;
    }

    public void setTraderOrderGoodsId(Integer traderOrderGoodsId) {
        this.traderOrderGoodsId = traderOrderGoodsId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}