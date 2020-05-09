package com.smallhospital.dto;

import java.util.Arrays;

/**
 * 小医院订单终止传输类
 */
public class ElSaleOrderTerminateDTO {

    /**
     * 订单明细id集合
     */
    private String[] orderListId;

    public String[] getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(String[] orderListId) {
        this.orderListId = orderListId;
    }

    @Override
    public String toString() {
        return "ElSaleOrderTerminateDTO{" +
                "orderListId=" + Arrays.toString(orderListId) +
                '}';
    }
}
