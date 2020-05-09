package com.vedeng.logistics.model;

/**
 * @program: erp
 * @description:
 * @author: addis
 * @create: 2019-11-08 11:14
 **/
public class LogisticsOrderGoodsData {

    private String skuNo;//订货号
    private Integer num;//数量

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
