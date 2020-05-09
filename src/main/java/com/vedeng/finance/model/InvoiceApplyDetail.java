package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * T_INVOICE_APPLY_DETAIL
 * @author 
 */
public class InvoiceApplyDetail implements Serializable {
    /**
     * 主键
     */
    private Integer invoiceApplyDetailId;

    /**
     * 申请表关联主键
     */
    private Integer invoiceApplyId;

    /**
     * 订单商品ID
     */
    private Integer detailgoodsId;

    /**
     * 开票单价
     */
    private BigDecimal price;

    /**
     * 开票数量
     */
    private BigDecimal num;

    /**
     * 开票总额
     */
    private BigDecimal totalAmount;

    /**
     * 修改后的商品名称
     */
    private String changedGoodsName;

    private static final long serialVersionUID = 1L;


    // add by Tomcat.Hui 2020/1/6 15:07 .Desc: VDERP-1039 票货同行. start
    private String sku;

    private String goodsName;

    private String unitName;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    // add by Tomcat.Hui 2020/1/6 15:07 .Desc: VDERP-1039 票货同行. end

    public Integer getInvoiceApplyDetailId() {
        return invoiceApplyDetailId;
    }

    public void setInvoiceApplyDetailId(Integer invoiceApplyDetailId) {
        this.invoiceApplyDetailId = invoiceApplyDetailId;
    }

    public Integer getInvoiceApplyId() {
        return invoiceApplyId;
    }

    public void setInvoiceApplyId(Integer invoiceApplyId) {
        this.invoiceApplyId = invoiceApplyId;
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

    public String getChangedGoodsName() {
        return changedGoodsName;
    }

    public void setChangedGoodsName(String changedGoodsName) {
        this.changedGoodsName = changedGoodsName;
    }

    @Override
    public String toString() {
        return "InvoiceApplyDetail{" +
                "invoiceApplyDetailId=" + invoiceApplyDetailId +
                ", invoiceApplyId=" + invoiceApplyId +
                ", detailgoodsId=" + detailgoodsId +
                ", price=" + price +
                ", num=" + num +
                ", totalAmount=" + totalAmount +
                ", changedGoodsName='" + changedGoodsName + '\'' +
                ", sku='" + sku + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", unitName='" + unitName + '\'' +
                '}';
    }
}