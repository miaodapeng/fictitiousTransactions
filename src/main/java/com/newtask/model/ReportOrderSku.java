package com.newtask.model;

public class ReportOrderSku {
    /** 主键id  ORDER_SKU_ID **/
    private String orderSkuId;

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

    /** 换算单位ID   UNIT_ID **/
    private Integer unitId;

    /** 换算单位名称  UNIT_NAME **/
    private String unitName;

    /** SKU商品单位ID   BASE_UNIT_ID **/
    private Integer baseUnitId;

    /** SKU商品单位名称  BASE_UNIT_NAME **/
    private String baseUnitName;

    /** 换算数量   CHANGE_NUM **/
    private Integer changeNum;

    /** 成本价  COST_PRICE **/
    private String costPrice;

    /** 上下架状态 1-上架 |0-下架   IS_ON_SALE **/
    private Integer isOnSale;

    /** 采购在途数量   PURCHASE_ON_ORDER_NUM **/
    private Integer purchaseOnOrderNum;

    /** 库存数量  STOCK_NUM **/
    private Integer stockNum;

    /** 占用库存数量  OCCUPY_NUM **/
    private Integer occupyNum;

    /** 活动锁定库存数量   ACTION_LOCK_COUNT **/
    private Integer actionLockCount;

    /** 活动占用库存数量   ACTION_OCCUPY_COUNT **/
    private Integer actionOccupyCount;

    /** 添加时间  ADD_TIME **/
    private String addTime;

    /** 修改时间  MOD_TIME **/
    private String modTime;

    /**   主键id  ORDER_SKU_ID   **/
    public String getOrderSkuId() {
        return orderSkuId;
    }

    /**   主键id  ORDER_SKU_ID   **/
    public void setOrderSkuId(String orderSkuId) {
        this.orderSkuId = orderSkuId == null ? null : orderSkuId.trim();
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

    /**   换算单位ID   UNIT_ID   **/
    public Integer getUnitId() {
        return unitId;
    }

    /**   换算单位ID   UNIT_ID   **/
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    /**   换算单位名称  UNIT_NAME   **/
    public String getUnitName() {
        return unitName;
    }

    /**   换算单位名称  UNIT_NAME   **/
    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
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

    /**   换算数量   CHANGE_NUM   **/
    public Integer getChangeNum() {
        return changeNum;
    }

    /**   换算数量   CHANGE_NUM   **/
    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }

    /**   成本价  COST_PRICE   **/
    public String getCostPrice() {
        return costPrice;
    }

    /**   成本价  COST_PRICE   **/
    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice == null ? null : costPrice.trim();
    }

    /**   上下架状态 1-上架 |0-下架   IS_ON_SALE   **/
    public Integer getIsOnSale() {
        return isOnSale;
    }

    /**   上下架状态 1-上架 |0-下架   IS_ON_SALE   **/
    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    /**   采购在途数量   PURCHASE_ON_ORDER_NUM   **/
    public Integer getPurchaseOnOrderNum() {
        return purchaseOnOrderNum;
    }

    /**   采购在途数量   PURCHASE_ON_ORDER_NUM   **/
    public void setPurchaseOnOrderNum(Integer purchaseOnOrderNum) {
        this.purchaseOnOrderNum = purchaseOnOrderNum;
    }

    /**   库存数量  STOCK_NUM   **/
    public Integer getStockNum() {
        return stockNum;
    }

    /**   库存数量  STOCK_NUM   **/
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    /**   占用库存数量  OCCUPY_NUM   **/
    public Integer getOccupyNum() {
        return occupyNum;
    }

    /**   占用库存数量  OCCUPY_NUM   **/
    public void setOccupyNum(Integer occupyNum) {
        this.occupyNum = occupyNum;
    }

    /**   活动锁定库存数量   ACTION_LOCK_COUNT   **/
    public Integer getActionLockCount() {
        return actionLockCount;
    }

    /**   活动锁定库存数量   ACTION_LOCK_COUNT   **/
    public void setActionLockCount(Integer actionLockCount) {
        this.actionLockCount = actionLockCount;
    }

    /**   活动占用库存数量   ACTION_OCCUPY_COUNT   **/
    public Integer getActionOccupyCount() {
        return actionOccupyCount;
    }

    /**   活动占用库存数量   ACTION_OCCUPY_COUNT   **/
    public void setActionOccupyCount(Integer actionOccupyCount) {
        this.actionOccupyCount = actionOccupyCount;
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