package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CapitalBillDetailGoods implements Serializable{
    private Integer capitalBillDetailgoodsId;

    private Integer capitalBillDetailId;

    private Integer detailgoodsId;

    private BigDecimal price;

    private BigDecimal num;

    private BigDecimal totalAmount;

    public Integer getCapitalBillDetailgoodsId() {
        return capitalBillDetailgoodsId;
    }

    public void setCapitalBillDetailgoodsId(Integer capitalBillDetailgoodsId) {
        this.capitalBillDetailgoodsId = capitalBillDetailgoodsId;
    }

    public Integer getCapitalBillDetailId() {
        return capitalBillDetailId;
    }

    public void setCapitalBillDetailId(Integer capitalBillDetailId) {
        this.capitalBillDetailId = capitalBillDetailId;
    }

    public Integer getDetailgoodsId() {
        return detailgoodsId;
    }

    public void setDetailgoodsId(Integer detailgoodsId) {
        this.detailgoodsId = detailgoodsId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}