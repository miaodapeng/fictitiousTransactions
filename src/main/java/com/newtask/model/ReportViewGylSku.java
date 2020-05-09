package com.newtask.model;

public class ReportViewGylSku {
    /** 部门名称  VIEW_GYL_ID **/
    private String viewGylId;

    /** 订单归属部门ID   ORG_ID **/
    private Integer orgId;

    /** 部门名称  ORG_NAME **/
    private String orgName;

    /** SKU订货号  SKU_NO **/
    private String skuNo;

    /** 商品名称  SKU_NAME **/
    private String skuName;

    /** 商品类型ID   SKU_TYPE_ID **/
    private Integer skuTypeId;

    /** 商品类型名称  SKU_TYPE_NAME **/
    private String skuTypeName;

    /** 商品等级ID  SKU_LEVEL_ID **/
    private Integer skuLevelId;

    /** 商品等级名称  SKU_LEVEL_NAME **/
    private String skuLevelName;

    /** 品牌ID  BRAND_ID **/
    private Integer brandId;

    /** 品牌名称  BRAND_NAME **/
    private String brandName;

    /** 一级分类ID   TOP_CATEGORY_ID **/
    private Integer topCategoryId;

    /** 一级分类名称  TOP_CATEGORY_NAME **/
    private String topCategoryName;

    /** 二级分类ID   SECOND_CATEGORY_ID **/
    private Integer secondCategoryId;

    /** 二级分类名称  SECOND_CATEGORY_NAME **/
    private String secondCategoryName;

    /** 三级分类ID   THIRD_CATEHGORY_ID **/
    private Integer thirdCatehgoryId;

    /** 三级分类名称  THIRD_CATEHGORY_NAME **/
    private String thirdCatehgoryName;

    /** 规格  MODEL **/
    private String model;

    /** SKU商品单位ID   BASE_UNIT_ID **/
    private Integer baseUnitId;

    /** SKU商品单位名称  BASE_UNIT_NAME **/
    private String baseUnitName;

    /** 成本价  COST_PRICE **/
    private String costPrice;

    /** 上下架状态  IS_ON_SALE **/
    private String isOnSale;

    /** 库存数量  STOCK_NUM **/
    private Integer stockNum;

    /** 占用数量  OCCUPY_NUM **/
    private Integer occupyNum;

    /** 活动锁定库存数量  ACTION_LOCK_COUNT **/
    private Integer actionLockCount;

    /** 活动占用库存数量  ACTION_OCCUPY_COUNT **/
    private Integer actionOccupyCount;

    /** 采购在途数量  PURCHASE_ON_ORDER_NUM **/
    private Integer purchaseOnOrderNum;

    /** 总非销售出库销量  TOTAL_LEND_OUT_NUM **/
    private Integer totalLendOutNum;

    /** 30日非销售出库销量  TOTAL_LEND_OUT_NUM_THIRTY_DAYS **/
    private Integer totalLendOutNumThirtyDays;

    /** 90日非销售出库销量  TOTAL_LEND_OUT_NUM_NINETY_DAYS **/
    private Integer totalLendOutNumNinetyDays;

    /** HC订单总销量  TOTAL_SELL_NUM_HC **/
    private Integer totalSellNumHc;

    /** BD订单总销量  TOTAL_SELL_NUM_BD **/
    private Integer totalSellNumBd;

    /** VS订单总销量  TOTAL_SELL_NUM_VS **/
    private Integer totalSellNumVs;

    /** DH订单总销量  TOTAL_SELL_NUM_DH **/
    private Integer totalSellNumDh;

    /** ADK订单总销量  TOTAL_SELL_NUM_ADK **/
    private Integer totalSellNumAdk;

    /** JX订单总销量  TOTAL_SELL_NUM_JX **/
    private Integer totalSellNumJx;

    /** EL订单总销量  TOTAL_SELL_NUM_EL **/
    private Integer totalSellNumEl;

    /** HC订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_HC **/
    private Integer totalSellNumThirtyDaysHc;

    /** BD订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_BD **/
    private Integer totalSellNumThirtyDaysBd;

    /** VS订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_VS **/
    private Integer totalSellNumThirtyDaysVs;

    /** DH订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_DH **/
    private Integer totalSellNumThirtyDaysDh;

    /** ADK订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_ADK **/
    private Integer totalSellNumThirtyDaysAdk;

    /** JX订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_JX **/
    private Integer totalSellNumThirtyDaysJx;

    /** EL订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_EL **/
    private Integer totalSellNumThirtyDaysEl;

    /** HC订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_HC **/
    private Integer totalSellNumNinetyDaysHc;

    /** BD订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_BD **/
    private Integer totalSellNumNinetyDaysBd;

    /** VS订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_VS **/
    private Integer totalSellNumNinetyDaysVs;

    /** DH订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_DH **/
    private Integer totalSellNumNinetyDaysDh;

    /** ADK订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_ADK **/
    private Integer totalSellNumNinetyDaysAdk;

    /** JX订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_JX **/
    private Integer totalSellNumNinetyDaysJx;

    /** EL订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_EL **/
    private Integer totalSellNumNinetyDaysEl;

    /** 订单最早支付时间 年-月-日  EARLIEST_PAY_TIME **/
    private String earliestPayTime;

    /** 添加时间  ADD_TIME **/
    private String addTime;

    /** 修改时间  MOD_TIME **/
    private String modTime;

    /**   部门名称  VIEW_GYL_ID   **/
    public String getViewGylId() {
        return viewGylId;
    }

    /**   部门名称  VIEW_GYL_ID   **/
    public void setViewGylId(String viewGylId) {
        this.viewGylId = viewGylId == null ? null : viewGylId.trim();
    }

    /**   订单归属部门ID   ORG_ID   **/
    public Integer getOrgId() {
        return orgId;
    }

    /**   订单归属部门ID   ORG_ID   **/
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**   部门名称  ORG_NAME   **/
    public String getOrgName() {
        return orgName;
    }

    /**   部门名称  ORG_NAME   **/
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /**   SKU订货号  SKU_NO   **/
    public String getSkuNo() {
        return skuNo;
    }

    /**   SKU订货号  SKU_NO   **/
    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo == null ? null : skuNo.trim();
    }

    /**   商品名称  SKU_NAME   **/
    public String getSkuName() {
        return skuName;
    }

    /**   商品名称  SKU_NAME   **/
    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    /**   商品类型ID   SKU_TYPE_ID   **/
    public Integer getSkuTypeId() {
        return skuTypeId;
    }

    /**   商品类型ID   SKU_TYPE_ID   **/
    public void setSkuTypeId(Integer skuTypeId) {
        this.skuTypeId = skuTypeId;
    }

    /**   商品类型名称  SKU_TYPE_NAME   **/
    public String getSkuTypeName() {
        return skuTypeName;
    }

    /**   商品类型名称  SKU_TYPE_NAME   **/
    public void setSkuTypeName(String skuTypeName) {
        this.skuTypeName = skuTypeName == null ? null : skuTypeName.trim();
    }

    /**   商品等级ID  SKU_LEVEL_ID   **/
    public Integer getSkuLevelId() {
        return skuLevelId;
    }

    /**   商品等级ID  SKU_LEVEL_ID   **/
    public void setSkuLevelId(Integer skuLevelId) {
        this.skuLevelId = skuLevelId;
    }

    /**   商品等级名称  SKU_LEVEL_NAME   **/
    public String getSkuLevelName() {
        return skuLevelName;
    }

    /**   商品等级名称  SKU_LEVEL_NAME   **/
    public void setSkuLevelName(String skuLevelName) {
        this.skuLevelName = skuLevelName == null ? null : skuLevelName.trim();
    }

    /**   品牌ID  BRAND_ID   **/
    public Integer getBrandId() {
        return brandId;
    }

    /**   品牌ID  BRAND_ID   **/
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**   品牌名称  BRAND_NAME   **/
    public String getBrandName() {
        return brandName;
    }

    /**   品牌名称  BRAND_NAME   **/
    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    /**   一级分类ID   TOP_CATEGORY_ID   **/
    public Integer getTopCategoryId() {
        return topCategoryId;
    }

    /**   一级分类ID   TOP_CATEGORY_ID   **/
    public void setTopCategoryId(Integer topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    /**   一级分类名称  TOP_CATEGORY_NAME   **/
    public String getTopCategoryName() {
        return topCategoryName;
    }

    /**   一级分类名称  TOP_CATEGORY_NAME   **/
    public void setTopCategoryName(String topCategoryName) {
        this.topCategoryName = topCategoryName == null ? null : topCategoryName.trim();
    }

    /**   二级分类ID   SECOND_CATEGORY_ID   **/
    public Integer getSecondCategoryId() {
        return secondCategoryId;
    }

    /**   二级分类ID   SECOND_CATEGORY_ID   **/
    public void setSecondCategoryId(Integer secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    /**   二级分类名称  SECOND_CATEGORY_NAME   **/
    public String getSecondCategoryName() {
        return secondCategoryName;
    }

    /**   二级分类名称  SECOND_CATEGORY_NAME   **/
    public void setSecondCategoryName(String secondCategoryName) {
        this.secondCategoryName = secondCategoryName == null ? null : secondCategoryName.trim();
    }

    /**   三级分类ID   THIRD_CATEHGORY_ID   **/
    public Integer getThirdCatehgoryId() {
        return thirdCatehgoryId;
    }

    /**   三级分类ID   THIRD_CATEHGORY_ID   **/
    public void setThirdCatehgoryId(Integer thirdCatehgoryId) {
        this.thirdCatehgoryId = thirdCatehgoryId;
    }

    /**   三级分类名称  THIRD_CATEHGORY_NAME   **/
    public String getThirdCatehgoryName() {
        return thirdCatehgoryName;
    }

    /**   三级分类名称  THIRD_CATEHGORY_NAME   **/
    public void setThirdCatehgoryName(String thirdCatehgoryName) {
        this.thirdCatehgoryName = thirdCatehgoryName == null ? null : thirdCatehgoryName.trim();
    }

    /**   规格  MODEL   **/
    public String getModel() {
        return model;
    }

    /**   规格  MODEL   **/
    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    /**   SKU商品单位ID   BASE_UNIT_ID   **/
    public Integer getBaseUnitId() {
        return baseUnitId;
    }

    /**   SKU商品单位ID   BASE_UNIT_ID   **/
    public void setBaseUnitId(Integer baseUnitId) {
        this.baseUnitId = baseUnitId;
    }

    /**   SKU商品单位名称  BASE_UNIT_NAME   **/
    public String getBaseUnitName() {
        return baseUnitName;
    }

    /**   SKU商品单位名称  BASE_UNIT_NAME   **/
    public void setBaseUnitName(String baseUnitName) {
        this.baseUnitName = baseUnitName == null ? null : baseUnitName.trim();
    }

    /**   成本价  COST_PRICE   **/
    public String getCostPrice() {
        return costPrice;
    }

    /**   成本价  COST_PRICE   **/
    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice == null ? null : costPrice.trim();
    }

    /**   上下架状态  IS_ON_SALE   **/
    public String getIsOnSale() {
        return isOnSale;
    }

    /**   上下架状态  IS_ON_SALE   **/
    public void setIsOnSale(String isOnSale) {
        this.isOnSale = isOnSale == null ? null : isOnSale.trim();
    }

    /**   库存数量  STOCK_NUM   **/
    public Integer getStockNum() {
        return stockNum;
    }

    /**   库存数量  STOCK_NUM   **/
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    /**   占用数量  OCCUPY_NUM   **/
    public Integer getOccupyNum() {
        return occupyNum;
    }

    /**   占用数量  OCCUPY_NUM   **/
    public void setOccupyNum(Integer occupyNum) {
        this.occupyNum = occupyNum;
    }

    /**   活动锁定库存数量  ACTION_LOCK_COUNT   **/
    public Integer getActionLockCount() {
        return actionLockCount;
    }

    /**   活动锁定库存数量  ACTION_LOCK_COUNT   **/
    public void setActionLockCount(Integer actionLockCount) {
        this.actionLockCount = actionLockCount;
    }

    /**   活动占用库存数量  ACTION_OCCUPY_COUNT   **/
    public Integer getActionOccupyCount() {
        return actionOccupyCount;
    }

    /**   活动占用库存数量  ACTION_OCCUPY_COUNT   **/
    public void setActionOccupyCount(Integer actionOccupyCount) {
        this.actionOccupyCount = actionOccupyCount;
    }

    /**   采购在途数量  PURCHASE_ON_ORDER_NUM   **/
    public Integer getPurchaseOnOrderNum() {
        return purchaseOnOrderNum;
    }

    /**   采购在途数量  PURCHASE_ON_ORDER_NUM   **/
    public void setPurchaseOnOrderNum(Integer purchaseOnOrderNum) {
        this.purchaseOnOrderNum = purchaseOnOrderNum;
    }

    /**   总非销售出库销量  TOTAL_LEND_OUT_NUM   **/
    public Integer getTotalLendOutNum() {
        return totalLendOutNum;
    }

    /**   总非销售出库销量  TOTAL_LEND_OUT_NUM   **/
    public void setTotalLendOutNum(Integer totalLendOutNum) {
        this.totalLendOutNum = totalLendOutNum;
    }

    /**   30日非销售出库销量  TOTAL_LEND_OUT_NUM_THIRTY_DAYS   **/
    public Integer getTotalLendOutNumThirtyDays() {
        return totalLendOutNumThirtyDays;
    }

    /**   30日非销售出库销量  TOTAL_LEND_OUT_NUM_THIRTY_DAYS   **/
    public void setTotalLendOutNumThirtyDays(Integer totalLendOutNumThirtyDays) {
        this.totalLendOutNumThirtyDays = totalLendOutNumThirtyDays;
    }

    /**   90日非销售出库销量  TOTAL_LEND_OUT_NUM_NINETY_DAYS   **/
    public Integer getTotalLendOutNumNinetyDays() {
        return totalLendOutNumNinetyDays;
    }

    /**   90日非销售出库销量  TOTAL_LEND_OUT_NUM_NINETY_DAYS   **/
    public void setTotalLendOutNumNinetyDays(Integer totalLendOutNumNinetyDays) {
        this.totalLendOutNumNinetyDays = totalLendOutNumNinetyDays;
    }

    /**   HC订单总销量  TOTAL_SELL_NUM_HC   **/
    public Integer getTotalSellNumHc() {
        return totalSellNumHc;
    }

    /**   HC订单总销量  TOTAL_SELL_NUM_HC   **/
    public void setTotalSellNumHc(Integer totalSellNumHc) {
        this.totalSellNumHc = totalSellNumHc;
    }

    /**   BD订单总销量  TOTAL_SELL_NUM_BD   **/
    public Integer getTotalSellNumBd() {
        return totalSellNumBd;
    }

    /**   BD订单总销量  TOTAL_SELL_NUM_BD   **/
    public void setTotalSellNumBd(Integer totalSellNumBd) {
        this.totalSellNumBd = totalSellNumBd;
    }

    /**   VS订单总销量  TOTAL_SELL_NUM_VS   **/
    public Integer getTotalSellNumVs() {
        return totalSellNumVs;
    }

    /**   VS订单总销量  TOTAL_SELL_NUM_VS   **/
    public void setTotalSellNumVs(Integer totalSellNumVs) {
        this.totalSellNumVs = totalSellNumVs;
    }

    /**   DH订单总销量  TOTAL_SELL_NUM_DH   **/
    public Integer getTotalSellNumDh() {
        return totalSellNumDh;
    }

    /**   DH订单总销量  TOTAL_SELL_NUM_DH   **/
    public void setTotalSellNumDh(Integer totalSellNumDh) {
        this.totalSellNumDh = totalSellNumDh;
    }

    /**   ADK订单总销量  TOTAL_SELL_NUM_ADK   **/
    public Integer getTotalSellNumAdk() {
        return totalSellNumAdk;
    }

    /**   ADK订单总销量  TOTAL_SELL_NUM_ADK   **/
    public void setTotalSellNumAdk(Integer totalSellNumAdk) {
        this.totalSellNumAdk = totalSellNumAdk;
    }

    /**   JX订单总销量  TOTAL_SELL_NUM_JX   **/
    public Integer getTotalSellNumJx() {
        return totalSellNumJx;
    }

    /**   JX订单总销量  TOTAL_SELL_NUM_JX   **/
    public void setTotalSellNumJx(Integer totalSellNumJx) {
        this.totalSellNumJx = totalSellNumJx;
    }

    /**   EL订单总销量  TOTAL_SELL_NUM_EL   **/
    public Integer getTotalSellNumEl() {
        return totalSellNumEl;
    }

    /**   EL订单总销量  TOTAL_SELL_NUM_EL   **/
    public void setTotalSellNumEl(Integer totalSellNumEl) {
        this.totalSellNumEl = totalSellNumEl;
    }

    /**   HC订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_HC   **/
    public Integer getTotalSellNumThirtyDaysHc() {
        return totalSellNumThirtyDaysHc;
    }

    /**   HC订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_HC   **/
    public void setTotalSellNumThirtyDaysHc(Integer totalSellNumThirtyDaysHc) {
        this.totalSellNumThirtyDaysHc = totalSellNumThirtyDaysHc;
    }

    /**   BD订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_BD   **/
    public Integer getTotalSellNumThirtyDaysBd() {
        return totalSellNumThirtyDaysBd;
    }

    /**   BD订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_BD   **/
    public void setTotalSellNumThirtyDaysBd(Integer totalSellNumThirtyDaysBd) {
        this.totalSellNumThirtyDaysBd = totalSellNumThirtyDaysBd;
    }

    /**   VS订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_VS   **/
    public Integer getTotalSellNumThirtyDaysVs() {
        return totalSellNumThirtyDaysVs;
    }

    /**   VS订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_VS   **/
    public void setTotalSellNumThirtyDaysVs(Integer totalSellNumThirtyDaysVs) {
        this.totalSellNumThirtyDaysVs = totalSellNumThirtyDaysVs;
    }

    /**   DH订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_DH   **/
    public Integer getTotalSellNumThirtyDaysDh() {
        return totalSellNumThirtyDaysDh;
    }

    /**   DH订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_DH   **/
    public void setTotalSellNumThirtyDaysDh(Integer totalSellNumThirtyDaysDh) {
        this.totalSellNumThirtyDaysDh = totalSellNumThirtyDaysDh;
    }

    /**   ADK订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_ADK   **/
    public Integer getTotalSellNumThirtyDaysAdk() {
        return totalSellNumThirtyDaysAdk;
    }

    /**   ADK订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_ADK   **/
    public void setTotalSellNumThirtyDaysAdk(Integer totalSellNumThirtyDaysAdk) {
        this.totalSellNumThirtyDaysAdk = totalSellNumThirtyDaysAdk;
    }

    /**   JX订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_JX   **/
    public Integer getTotalSellNumThirtyDaysJx() {
        return totalSellNumThirtyDaysJx;
    }

    /**   JX订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_JX   **/
    public void setTotalSellNumThirtyDaysJx(Integer totalSellNumThirtyDaysJx) {
        this.totalSellNumThirtyDaysJx = totalSellNumThirtyDaysJx;
    }

    /**   EL订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_EL   **/
    public Integer getTotalSellNumThirtyDaysEl() {
        return totalSellNumThirtyDaysEl;
    }

    /**   EL订单30日总销量  TOTAL_SELL_NUM_THIRTY_DAYS_EL   **/
    public void setTotalSellNumThirtyDaysEl(Integer totalSellNumThirtyDaysEl) {
        this.totalSellNumThirtyDaysEl = totalSellNumThirtyDaysEl;
    }

    /**   HC订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_HC   **/
    public Integer getTotalSellNumNinetyDaysHc() {
        return totalSellNumNinetyDaysHc;
    }

    /**   HC订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_HC   **/
    public void setTotalSellNumNinetyDaysHc(Integer totalSellNumNinetyDaysHc) {
        this.totalSellNumNinetyDaysHc = totalSellNumNinetyDaysHc;
    }

    /**   BD订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_BD   **/
    public Integer getTotalSellNumNinetyDaysBd() {
        return totalSellNumNinetyDaysBd;
    }

    /**   BD订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_BD   **/
    public void setTotalSellNumNinetyDaysBd(Integer totalSellNumNinetyDaysBd) {
        this.totalSellNumNinetyDaysBd = totalSellNumNinetyDaysBd;
    }

    /**   VS订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_VS   **/
    public Integer getTotalSellNumNinetyDaysVs() {
        return totalSellNumNinetyDaysVs;
    }

    /**   VS订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_VS   **/
    public void setTotalSellNumNinetyDaysVs(Integer totalSellNumNinetyDaysVs) {
        this.totalSellNumNinetyDaysVs = totalSellNumNinetyDaysVs;
    }

    /**   DH订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_DH   **/
    public Integer getTotalSellNumNinetyDaysDh() {
        return totalSellNumNinetyDaysDh;
    }

    /**   DH订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_DH   **/
    public void setTotalSellNumNinetyDaysDh(Integer totalSellNumNinetyDaysDh) {
        this.totalSellNumNinetyDaysDh = totalSellNumNinetyDaysDh;
    }

    /**   ADK订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_ADK   **/
    public Integer getTotalSellNumNinetyDaysAdk() {
        return totalSellNumNinetyDaysAdk;
    }

    /**   ADK订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_ADK   **/
    public void setTotalSellNumNinetyDaysAdk(Integer totalSellNumNinetyDaysAdk) {
        this.totalSellNumNinetyDaysAdk = totalSellNumNinetyDaysAdk;
    }

    /**   JX订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_JX   **/
    public Integer getTotalSellNumNinetyDaysJx() {
        return totalSellNumNinetyDaysJx;
    }

    /**   JX订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_JX   **/
    public void setTotalSellNumNinetyDaysJx(Integer totalSellNumNinetyDaysJx) {
        this.totalSellNumNinetyDaysJx = totalSellNumNinetyDaysJx;
    }

    /**   EL订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_EL   **/
    public Integer getTotalSellNumNinetyDaysEl() {
        return totalSellNumNinetyDaysEl;
    }

    /**   EL订单90日总销量  TOTAL_SELL_NUM_NINETY_DAYS_EL   **/
    public void setTotalSellNumNinetyDaysEl(Integer totalSellNumNinetyDaysEl) {
        this.totalSellNumNinetyDaysEl = totalSellNumNinetyDaysEl;
    }

    /**   订单最早支付时间 年-月-日  EARLIEST_PAY_TIME   **/
    public String getEarliestPayTime() {
        return earliestPayTime;
    }

    /**   订单最早支付时间 年-月-日  EARLIEST_PAY_TIME   **/
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