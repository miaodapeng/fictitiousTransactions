package com.vedeng.logistics.model;

/**
 * @program: erp
 * @description: 发货提醒参数
 * @author: addis
 * @create: 2019-09-25 11:33
 **/
public class ShipmentToRemind {

    private String logIsTicsNo; //快递单号
    private String goodsName;//商品名称
    private Integer afterSalesNum;//售后数量
    private Integer goodsNum;//产品数量
    private Integer DeliveryNum;//发货数量
    private Long validTime;//订单生效时间
    private String taaderContactName;//订单联系人
    private String traderContactMobile;//订单手机号
    private String ligisticsName;//快递公司名称
    private String SaleOrderNo;//业务单据

    public String getLogIsTicsNo() {
        return logIsTicsNo;
    }

    public void setLogIsTicsNo(String logIsTicsNo) {
        this.logIsTicsNo = logIsTicsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getAfterSalesNum() {
        return afterSalesNum;
    }

    public void setAfterSalesNum(Integer afterSalesNum) {
        this.afterSalesNum = afterSalesNum;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getDeliveryNum() {
        return DeliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        DeliveryNum = deliveryNum;
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
    }

    public String getTaaderContactName() {
        return taaderContactName;
    }

    public void setTaaderContactName(String taaderContactName) {
        this.taaderContactName = taaderContactName;
    }

    public String getTraderContactMobile() {
        return traderContactMobile;
    }

    public void setTraderContactMobile(String traderContactMobile) {
        this.traderContactMobile = traderContactMobile;
    }

    public String getLigisticsName() {
        return ligisticsName;
    }

    public void setLigisticsName(String ligisticsName) {
        this.ligisticsName = ligisticsName;
    }

    public String getSaleOrderNo() {
        return SaleOrderNo;
    }

    public void setSaleOrderNo(String saleOrderNo) {
        SaleOrderNo = saleOrderNo;
    }
}
