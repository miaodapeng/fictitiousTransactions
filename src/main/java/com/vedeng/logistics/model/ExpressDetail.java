package com.vedeng.logistics.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExpressDetail implements Serializable{
    // add by Tomcat.Hui 2019/12/6 15:35 .Desc:  VDERP-1325 分批开票. start
    //商品表ID
    private Integer saleOrderGoodsId;

    //已收货数量
    private Integer arriveNum;

    //已发货数量
    private Integer sendNum;

    //售后数量
    private Integer afterSaleNum;

    //商品数量
    private Integer allNum;

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getAfterSaleNum() {
        return afterSaleNum;
    }

    public void setAfterSaleNum(Integer afterSaleNum) {
        this.afterSaleNum = afterSaleNum;
    }

    public Integer getSaleOrderGoodsId() {
        return saleOrderGoodsId;
    }

    public void setSaleOrderGoodsId(Integer saleOrderGoodsId) {
        this.saleOrderGoodsId = saleOrderGoodsId;
    }

    public Integer getArriveNum() {
        return arriveNum;
    }

    public void setArriveNum(Integer arriveNum) {
        this.arriveNum = arriveNum;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    // add by Tomcat.Hui 2019/12/6 15:35 .Desc:  VDERP-1325 分批开票. end


    private Integer expressDetailId;

    private Integer expressId;

    private Integer businessType;

    private Integer relatedId;

    private Integer num;

    private BigDecimal amount;
    
    private String goodName;//产品名称
    
    private String unitName;//单位名称
    
    private String relatedNo;//其他编号
    
    private Integer goodsId;

    
    public String getRelatedNo() {
		return relatedNo;
	}

	public void setRelatedNo(String relatedNo) {
		this.relatedNo = relatedNo;
	}

	public Integer getExpressDetailId() {
        return expressDetailId;
    }

    public void setExpressDetailId(Integer expressDetailId) {
        this.expressDetailId = expressDetailId;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getGoodName() {
	return goodName;
    }

    public void setGoodName(String goodName) {
	this.goodName = goodName;
    }

    public String getUnitName() {
	return unitName;
    }

    public void setUnitName(String unitName) {
	this.unitName = unitName;
    }

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
}