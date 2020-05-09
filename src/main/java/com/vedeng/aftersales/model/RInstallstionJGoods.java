package com.vedeng.aftersales.model;

import java.io.Serializable;

public class RInstallstionJGoods implements Serializable{
    private Integer rInstallstionJGoodsId;

    private Integer afterSalesInstallstionId;

    private Integer afterSalesGoodsId;
    
    private Integer num;

    public Integer getrInstallstionJGoodsId() {
        return rInstallstionJGoodsId;
    }

    public void setrInstallstionJGoodsId(Integer rInstallstionJGoodsId) {
        this.rInstallstionJGoodsId = rInstallstionJGoodsId;
    }

    public Integer getAfterSalesInstallstionId() {
        return afterSalesInstallstionId;
    }

    public void setAfterSalesInstallstionId(Integer afterSalesInstallstionId) {
        this.afterSalesInstallstionId = afterSalesInstallstionId;
    }

    public Integer getAfterSalesGoodsId() {
        return afterSalesGoodsId;
    }

    public void setAfterSalesGoodsId(Integer afterSalesGoodsId) {
        this.afterSalesGoodsId = afterSalesGoodsId;
    }

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
    
}