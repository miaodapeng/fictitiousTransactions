package com.vedeng.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class GoodsPackage implements Serializable{
    private Integer goodsPackageId;

    private Integer parentGoodsId;

    private Integer goodsId;

    private BigDecimal packagePrice;

    private Integer isStandard;

    private Long addTime;

    private Integer creator;
    
    private List<String> goodsIdArr;

    public Integer getGoodsPackageId() {
        return goodsPackageId;
    }


	public List<String> getGoodsIdArr() {
		return goodsIdArr;
	}

	public void setGoodsIdArr(List<String> goodsIdArr) {
		this.goodsIdArr = goodsIdArr;
	}

	public void setGoodsPackageId(Integer goodsPackageId) {
        this.goodsPackageId = goodsPackageId;
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

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Integer getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Integer isStandard) {
        this.isStandard = isStandard;
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