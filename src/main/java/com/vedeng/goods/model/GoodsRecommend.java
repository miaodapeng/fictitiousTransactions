package com.vedeng.goods.model;

import java.io.Serializable;
import java.util.List;

public class GoodsRecommend implements Serializable{
	
    private Integer goodsRecommendId;

    private Integer parentGoodsId;

    private Integer goodsId;

    private Long addTime;

    private Integer creator;
    
    private List<String> goodsIdArr;

    
    public GoodsRecommend() {
		super();
	}

	public List<String> getGoodsIdArr() {
		return goodsIdArr;
	}

	public void setGoodsIdArr(List<String> goodsIdArr) {
		this.goodsIdArr = goodsIdArr;
	}

	public Integer getGoodsRecommendId() {
        return goodsRecommendId;
    }

    public void setGoodsRecommendId(Integer goodsRecommendId) {
        this.goodsRecommendId = goodsRecommendId;
    }

    public Integer getParentGoodsId() {
        return parentGoodsId;
    }

    public void setParentGoodsId(Integer parentGoodsId) {
        this.parentGoodsId = parentGoodsId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
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