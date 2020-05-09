package com.newtask.model;

public class ReportNotSellSku {
    /** 主键id  NOTSELL_SKU_ID **/
    private String notsellSkuId;

    /** SKU订货号  SKU_NO **/
    private String skuNo;

    /** 订单归属部门ID   ORG_ID **/
    private Integer orgId;

    /** 订单归属部门名称  ORG_NAME **/
    private String orgName;

    /** 订单号  SELLORDER_NO **/
    private String sellorderNo;

    /** 订单类型,1 - HC | 2 - BD |  3 - VS  |4 - DH | 5 -  ADK | 6 - JX | 7 - EL  SELLORDER_NO_TYPE **/
    private Integer sellorderNoType;

    /** 非销售出库数量   NOT_SELL_NUM **/
    private Integer notSellNum;

    /** 30天非销售出库数量   TOTAL_NOT_SELL_NUM_THIRTY_DAYS **/
    private Integer totalNotSellNumThirtyDays;

    /** 90天非销售出库数量   TOTAL_NOT_SELL_NUM_NINETY_DAYS **/
    private Integer totalNotSellNumNinetyDays;

    /** 订单创建时间  SELLORDER_ADD_TIME **/
    private String sellorderAddTime;

    /** SKU_NO,ORG_ID,SELLORDER_ADD_TIME拼接后的字段*/
    private String associationFiled;

    /** 添加时间  ADD_TIME **/
    private String addTime;

    /** 修改时间  MOD_TIME **/
    private String modTime;

    /**   主键id  NOTSELL_SKU_ID   **/
    public String getNotsellSkuId() {
        return notsellSkuId;
    }

    /**   主键id  NOTSELL_SKU_ID   **/
    public void setNotsellSkuId(String notsellSkuId) {
        this.notsellSkuId = notsellSkuId == null ? null : notsellSkuId.trim();
    }

    /**   SKU订货号  SKU_NO   **/
    public String getSkuNo() {
        return skuNo;
    }

    /**   SKU订货号  SKU_NO   **/
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo == null ? null : skuNo.trim();
    }

    /**   订单归属部门ID   ORG_ID   **/
    public Integer getOrgId() {
        return orgId;
    }

    /**   订单归属部门ID   ORG_ID   **/
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**   订单归属部门名称  ORG_NAME   **/
    public String getOrgName() {
        return orgName;
    }

    /**   订单归属部门名称  ORG_NAME   **/
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getSellorderNo() {
        return sellorderNo;
    }

    public void setSellorderNo(String sellorderNo) {
        this.sellorderNo = sellorderNo;
    }

    /**   订单类型,1 - HC | 2 - BD |  3 - VS  |4 - DH | 5 -  ADK | 6 - JX | 7 - EL  SELLORDER_NO_TYPE   **/
    public Integer getSellorderNoType() {
        return sellorderNoType;
    }

    /**   订单类型,1 - HC | 2 - BD |  3 - VS  |4 - DH | 5 -  ADK | 6 - JX | 7 - EL  SELLORDER_NO_TYPE   **/
    public void setSellorderNoType(Integer sellorderNoType) {
        this.sellorderNoType = sellorderNoType;
    }

    /**   非销售出库数量   NOT_SELL_NUM   **/
    public Integer getNotSellNum() {
        return notSellNum;
    }

    /**   非销售出库数量   NOT_SELL_NUM   **/
    public void setNotSellNum(Integer notSellNum) {
        this.notSellNum = notSellNum;
    }

    /**   30天非销售出库数量   TOTAL_NOT_SELL_NUM_THIRTY_DAYS   **/
    public Integer getTotalNotSellNumThirtyDays() {
        return totalNotSellNumThirtyDays;
    }

    /**   30天非销售出库数量   TOTAL_NOT_SELL_NUM_THIRTY_DAYS   **/
    public void setTotalNotSellNumThirtyDays(Integer totalNotSellNumThirtyDays) {
        this.totalNotSellNumThirtyDays = totalNotSellNumThirtyDays;
    }

    /**   90天非销售出库数量   TOTAL_NOT_SELL_NUM_NINETY_DAYS   **/
    public Integer getTotalNotSellNumNinetyDays() {
        return totalNotSellNumNinetyDays;
    }

    /**   90天非销售出库数量   TOTAL_NOT_SELL_NUM_NINETY_DAYS   **/
    public void setTotalNotSellNumNinetyDays(Integer totalNotSellNumNinetyDays) {
        this.totalNotSellNumNinetyDays = totalNotSellNumNinetyDays;
    }

    /**   订单创建时间  SELLORDER_ADD_TIME   **/
    public String getSellorderAddTime() {
        return sellorderAddTime;
    }

    /**   订单创建时间  SELLORDER_ADD_TIME   **/
    public void setSellorderAddTime(String sellorderAddTime) {
        this.sellorderAddTime = sellorderAddTime == null ? null : sellorderAddTime.trim();
    }

    public String getAssociationFiled() {
        return associationFiled;
    }

    public void setAssociationFiled(String associationFiled) {
        this.associationFiled = associationFiled;
    }

    /**   添加时间  ADD_TIME   **/
    public String getAddTime() {
        return addTime;
    }

    /**   添加时间  ADD_TIME   **/
    public void setAddTime(String addTime) {
        this.addTime = addTime == null ? null : addTime.trim();
    }

    /**   修改时间  MOD_TIME   **/
    public String getModTime() {
        return modTime;
    }

    /**   修改时间  MOD_TIME   **/
    public void setModTime(String modTime) {
        this.modTime = modTime == null ? null : modTime.trim();
    }
}