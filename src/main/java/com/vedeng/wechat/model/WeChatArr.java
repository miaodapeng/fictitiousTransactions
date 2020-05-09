package com.vedeng.wechat.model;

import java.util.Date;

public class WeChatArr {
    /** 字段ID  ARR_ID **/
    private Integer arrId;

    /** 订单类型0销售订单2备货订单3订货订单5耗材商城订单  ORDER_TYPE **/
    private Integer orderType;

    /** 交易者ID  TRADER_ID **/
    private Integer traderId;

    /** 交易者名称  TRADER_NAME **/
    private String traderName;

    /** 联系人  TRADER_CONTACT_NAME **/
    private String traderContactName;

    /** 手机  TRADER_CONTACT_MOBILE **/
    private String traderContactMobile;

    /** 联系详细地址(含省市区)  TRADER_ADDRESS **/
    private String traderAddress;

    /** 订单ID  SALEORDER_ID **/
    private Integer saleorderId;

    /**
     * 订单号
     */
    private String saleorderNo;

    /**
     * 公司ID
     */
    private Integer companyId;

    /** 创建时间  ADD_TIME **/
    private Date addTime;
    /** 
    * @Description: 平台id 
    * @Param:
    * @return:  
    * @Author: addis
    * @Date: 2019/9/29 
    */ 
    private Integer platfromId;

    /**   字段ID  ARR_ID   **/
    public Integer getArrId() {
        return arrId;
    }

    /**   字段ID  ARR_ID   **/
    public void setArrId(Integer arrId) {
        this.arrId = arrId;
    }

    /**   订单类型0销售订单2备货订单3订货订单5耗材商城订单  ORDER_TYPE   **/
    public Integer getOrderType() {
        return orderType;
    }

    /**   订单类型0销售订单2备货订单3订货订单5耗材商城订单  ORDER_TYPE   **/
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**   交易者ID  TRADER_ID   **/
    public Integer getTraderId() {
        return traderId;
    }

    /**   交易者ID  TRADER_ID   **/
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    /**   交易者名称  TRADER_NAME   **/
    public String getTraderName() {
        return traderName;
    }

    /**   交易者名称  TRADER_NAME   **/
    public void setTraderName(String traderName) {
        this.traderName = traderName == null ? null : traderName.trim();
    }

    /**   联系人  TRADER_CONTACT_NAME   **/
    public String getTraderContactName() {
        return traderContactName;
    }

    /**   联系人  TRADER_CONTACT_NAME   **/
    public void setTraderContactName(String traderContactName) {
        this.traderContactName = traderContactName == null ? null : traderContactName.trim();
    }

    /**   手机  TRADER_CONTACT_MOBILE   **/
    public String getTraderContactMobile() {
        return traderContactMobile;
    }

    /**   手机  TRADER_CONTACT_MOBILE   **/
    public void setTraderContactMobile(String traderContactMobile) {
        this.traderContactMobile = traderContactMobile == null ? null : traderContactMobile.trim();
    }

    /**   联系详细地址(含省市区)  TRADER_ADDRESS   **/
    public String getTraderAddress() {
        return traderAddress;
    }

    /**   联系详细地址(含省市区)  TRADER_ADDRESS   **/
    public void setTraderAddress(String traderAddress) {
        this.traderAddress = traderAddress == null ? null : traderAddress.trim();
    }

    /**   订单ID  SALEORDER_ID   **/
    public Integer getSaleorderId() {
        return saleorderId;
    }

    /**   订单ID  SALEORDER_ID   **/
    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    public String getSaleorderNo() {
        return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**   创建时间  ADD_TIME   **/
    public Date getAddTime() {
        return addTime;
    }

    /**   创建时间  ADD_TIME   **/
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getPlatfromId() {
        return platfromId;
    }

    public void setPlatfromId(Integer platfromId) {
        this.platfromId = platfromId;
    }
}