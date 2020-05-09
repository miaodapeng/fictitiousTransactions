package com.vedeng.goods.model;

import java.io.Serializable;

public class GoodsSysOptionAttribute implements Serializable{
    private Integer goodsSysOptionAttributeId;

    private Integer goodsId;
    
    private Integer attributeType;

    private Integer attributeId;

    private String attributeOther;
    
    private String title;

    public Integer getGoodsSysOptionAttributeId() {
        return goodsSysOptionAttributeId;
    }

    public void setGoodsSysOptionAttributeId(Integer goodsSysOptionAttributeId) {
        this.goodsSysOptionAttributeId = goodsSysOptionAttributeId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeOther() {
        return attributeOther;
    }

    public void setAttributeOther(String attributeOther) {
        this.attributeOther = attributeOther == null ? null : attributeOther.trim();
    }

	public Integer getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(Integer attributeType) {
		this.attributeType = attributeType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}