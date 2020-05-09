package com.vedeng.goods.model;

import java.io.Serializable;

public class GoodsAttribute implements Serializable{
    private Integer goodsAttributeId;

    private Integer goodsId;

    private Integer categoryAttributeId;

    private Integer categoryAttrValueId;

    private Integer sort;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String attrValue;
    
    private String categoryAttributeName;
    
    private Integer inputType;

    public Integer getGoodsAttributeId() {
        return goodsAttributeId;
    }

    public void setGoodsAttributeId(Integer goodsAttributeId) {
        this.goodsAttributeId = goodsAttributeId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCategoryAttributeId() {
        return categoryAttributeId;
    }

    public void setCategoryAttributeId(Integer categoryAttributeId) {
        this.categoryAttributeId = categoryAttributeId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

	public Integer getCategoryAttrValueId() {
		return categoryAttrValueId;
	}

	public void setCategoryAttrValueId(Integer categoryAttrValueId) {
		this.categoryAttrValueId = categoryAttrValueId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getCategoryAttributeName() {
		return categoryAttributeName;
	}

	public void setCategoryAttributeName(String categoryAttributeName) {
		this.categoryAttributeName = categoryAttributeName;
	}

	public Integer getInputType() {
		return inputType;
	}

	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}
}