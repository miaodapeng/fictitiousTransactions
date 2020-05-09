package com.vedeng.order.model;

import java.io.Serializable;

public class BuyorderGoodsCode implements Serializable{
    private Integer buyorderGoodsCodeId;
    
    private Integer buyorderId;

    private Integer buyorderGoodsId;

    private Integer sequence;

    private Integer goodsId;

    private String code;
    
    private Long addTime;

    private Integer creator;

    public Integer getBuyorderGoodsCodeId() {
        return buyorderGoodsCodeId;
    }

    public void setBuyorderGoodsCodeId(Integer buyorderGoodsCodeId) {
        this.buyorderGoodsCodeId = buyorderGoodsCodeId;
    }

    public Integer getBuyorderGoodsId() {
        return buyorderGoodsId;
    }

    public void setBuyorderGoodsId(Integer buyorderGoodsId) {
        this.buyorderGoodsId = buyorderGoodsId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
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
}