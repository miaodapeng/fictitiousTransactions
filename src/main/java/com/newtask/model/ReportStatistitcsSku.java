package com.newtask.model;

public class ReportStatistitcsSku {
    /** 主键id  STATISTITCS_SKU_ID **/
    private String statistitcsSkuId;

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

    /** 销售数量  SELL_NUM **/
    private Integer sellNum;

    /** 30天销量  TOTAL_SELL_NUM_THIRTY_DAYS **/
    private Integer totalSellNumThirtyDays;

    /** 90天销量  TOTAL_SELL_NUM_NINETY_DAYS **/
    private Integer totalSellNumNinetyDays;

    /** 非销售出库数量   LEND_OUT_NUM **/
    private Integer lendOutNum;

    /** 30天非销售出库数量   TOTAL_LEND_OUT_NUM_THIRTY_DAYS **/
    private Integer totalLendOutNumThirtyDays;

    /** 90天非销售出库数量   TOTAL_LEND_OUT_NUM_NINETY_DAYS **/
    private Integer totalLendOutNumNinetyDays;

    /** 订单最早交易时间  EARLIEST_PAY_TIME **/
    private String earliestPayTime;

    /** 添加时间  ADD_TIME **/
    private String addTime;

    /** 修改时间  MOD_TIME **/
    private String modTime;

    /**   主键id  STATISTITCS_SKU_ID   **/
    public String getStatistitcsSkuId() {
        return statistitcsSkuId;
    }

    /**   主键id  STATISTITCS_SKU_ID   **/
    public void setStatistitcsSkuId(String statistitcsSkuId) {
        this.statistitcsSkuId = statistitcsSkuId == null ? null : statistitcsSkuId.trim();
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

    /** 订单号  SELLORDER_NO **/
    public String getSellorderNo() {
        return sellorderNo;
    }

    /** 订单号  SELLORDER_NO **/
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

    /**   销售数量  SELL_NUM   **/
    public Integer getSellNum() {
        return sellNum;
    }

    /**   销售数量  SELL_NUM   **/
    public void setSellNum(Integer sellNum) {
        this.sellNum = sellNum;
    }

    /**   30天销量  TOTAL_SELL_NUM_THIRTY_DAYS   **/
    public Integer getTotalSellNumThirtyDays() {
        return totalSellNumThirtyDays;
    }

    /**   30天销量  TOTAL_SELL_NUM_THIRTY_DAYS   **/
    public void setTotalSellNumThirtyDays(Integer totalSellNumThirtyDays) {
        this.totalSellNumThirtyDays = totalSellNumThirtyDays;
    }

    /**   90天销量  TOTAL_SELL_NUM_NINETY_DAYS   **/
    public Integer getTotalSellNumNinetyDays() {
        return totalSellNumNinetyDays;
    }

    /**   90天销量  TOTAL_SELL_NUM_NINETY_DAYS   **/
    public void setTotalSellNumNinetyDays(Integer totalSellNumNinetyDays) {
        this.totalSellNumNinetyDays = totalSellNumNinetyDays;
    }

    /**   非销售出库数量   LEND_OUT_NUM   **/
    public Integer getLendOutNum() {
        return lendOutNum;
    }

    /**   非销售出库数量   LEND_OUT_NUM   **/
    public void setLendOutNum(Integer lendOutNum) {
        this.lendOutNum = lendOutNum;
    }

    /**   30天非销售出库数量   TOTAL_LEND_OUT_NUM_THIRTY_DAYS   **/
    public Integer getTotalLendOutNumThirtyDays() {
        return totalLendOutNumThirtyDays;
    }

    /**   30天非销售出库数量   TOTAL_LEND_OUT_NUM_THIRTY_DAYS   **/
    public void setTotalLendOutNumThirtyDays(Integer totalLendOutNumThirtyDays) {
        this.totalLendOutNumThirtyDays = totalLendOutNumThirtyDays;
    }

    /**   90天非销售出库数量   TOTAL_LEND_OUT_NUM_NINETY_DAYS   **/
    public Integer getTotalLendOutNumNinetyDays() {
        return totalLendOutNumNinetyDays;
    }

    /**   90天非销售出库数量   TOTAL_LEND_OUT_NUM_NINETY_DAYS   **/
    public void setTotalLendOutNumNinetyDays(Integer totalLendOutNumNinetyDays) {
        this.totalLendOutNumNinetyDays = totalLendOutNumNinetyDays;
    }

    /**   订单最早交易时间  EARLIEST_PAY_TIME   **/
    public String getEarliestPayTime() {
        return earliestPayTime;
    }

    /**   订单最早交易时间  EARLIEST_PAY_TIME   **/
    public void setEarliestPayTime(String earliestPayTime) {
        this.earliestPayTime = earliestPayTime == null ? null : earliestPayTime.trim();
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