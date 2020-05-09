package com.vedeng.logistics.model;

import java.util.List;

/**
 * @program: erp
 * @description:
 * @author: addis
 * @create: 2019-11-08 11:13
 **/
public class LogisticsOrderData {

    private Integer accountId;//账户id
    private String orderNo;//订单号
    private String addLogisticsNo;//物流单号
    private Integer logisticsType;//快件类型 1、普发 2、直发
    private String logisticsCode;//物流公司编码
    private Integer type;//1、新增 2、修改 3、删除
    private String  delLogisticsNo;//修改之前的单号
    private List<LogisticsOrderGoodsData> orderGoodsLogisticsDataList;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<LogisticsOrderGoodsData> getOrderGoodsLogisticsDataList() {
        return orderGoodsLogisticsDataList;
    }

    public void setOrderGoodsLogisticsDataList(List<LogisticsOrderGoodsData> orderGoodsLogisticsDataList) {
        this.orderGoodsLogisticsDataList = orderGoodsLogisticsDataList;
    }

    public String getAddLogisticsNo() {
        return addLogisticsNo;
    }

    public void setAddLogisticsNo(String addLogisticsNo) {
        this.addLogisticsNo = addLogisticsNo;
    }

    public String getDelLogisticsNo() {
        return delLogisticsNo;
    }

    public void setDelLogisticsNo(String delLogisticsNo) {
        this.delLogisticsNo = delLogisticsNo;
    }
}
