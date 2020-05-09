package com.smallhospital.model;

/**
 * 小医院订单收货情况
 */
public class ElSaleOrderDelivery {

    private Integer elSaleOrderDeliveryId;

    /**
     * 物流单id
     */
    private Integer expressId;

    /**
     * 物流详情id
     */
    private Integer expressDetailId;

    /**
     * sku数量
     */
    private Integer num;

    public Integer getElSaleOrderDeliveryId() {
        return elSaleOrderDeliveryId;
    }

    public void setElSaleOrderDeliveryId(Integer elSaleOrderDeliveryId) {
        this.elSaleOrderDeliveryId = elSaleOrderDeliveryId;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public Integer getExpressDetailId() {
        return expressDetailId;
    }

    public void setExpressDetailId(Integer expressDetailId) {
        this.expressDetailId = expressDetailId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public ElSaleOrderDelivery(Integer expressId, Integer expressDetailId, Integer num) {
        this.expressId = expressId;
        this.expressDetailId = expressDetailId;
        this.num = num;
    }

    public ElSaleOrderDelivery() {
    }

    @Override
    public String toString() {
        return "ElSaleOrderDelivery{" +
                "elSaleOrderDeliveryId=" + elSaleOrderDeliveryId +
                ", expressId=" + expressId +
                ", expressDetailId=" + expressDetailId +
                ", num=" + num +
                '}';
    }
}
