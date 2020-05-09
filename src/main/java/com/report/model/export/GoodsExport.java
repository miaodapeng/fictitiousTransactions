package com.report.model.export;

import java.math.BigDecimal;
import java.util.List;

public class GoodsExport {

	private Integer companyId,goodsId;
	
	private String sku,goodsName,brandName,model,materialCode,unitName,categoryName,manageCategoryName,manageCategoryLevelStr,goodsTypeName,goodsLevelName;
	
	private BigDecimal goodsLength,goodsWidth,goodsHeight,packageLength,packageWidth,packageHeight;
	
	private Integer isDiscard,verifyStatus;
	
	private String packingList,addTimeStr,modTimeStr,verifyTimeStr,categoryNameArr;
	
	private Integer categoryId,goodsType,goodsLevel,isOnSale;
	
	private Integer saleNum365,saleNum180,saleNum90,buyNum365,buyNum180,buyNum90;
	
	private Integer goodsUserId;
	
	private String optUserName;
	
	private String categoryNameOne, categoryNameTwo, categoryNameThree, standardCategoryNameOne, standardCategoryNameTwo, standardCategoryNameThree;
	
	private List<Integer> categoryIdList;// 产品归属对应的分类Id列表
	
	//搜索日期类型 1：创建时间 2：更新时间 3：审核时间
    private Integer searchDateType;
    
    //搜索开始时间--搜索结束时间
    private Long searchBegintime,searchEndtime;
    
    
    
	public String getCategoryNameArr() {
		return categoryNameArr;
	}
	public void setCategoryNameArr(String categoryNameArr) {
		this.categoryNameArr = categoryNameArr;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getGoodsUserId() {
		return goodsUserId;
	}
	public void setGoodsUserId(Integer goodsUserId) {
		this.goodsUserId = goodsUserId;
	}
	public String getOptUserName() {
		return optUserName;
	}
	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}
	public Integer getSaleNum365() {
		return saleNum365;
	}
	public void setSaleNum365(Integer saleNum365) {
		this.saleNum365 = saleNum365;
	}
	public Integer getSaleNum180() {
		return saleNum180;
	}
	public void setSaleNum180(Integer saleNum180) {
		this.saleNum180 = saleNum180;
	}
	public Integer getSaleNum90() {
		return saleNum90;
	}
	public void setSaleNum90(Integer saleNum90) {
		this.saleNum90 = saleNum90;
	}
	public Integer getBuyNum365() {
		return buyNum365;
	}
	public void setBuyNum365(Integer buyNum365) {
		this.buyNum365 = buyNum365;
	}
	public Integer getBuyNum180() {
		return buyNum180;
	}
	public void setBuyNum180(Integer buyNum180) {
		this.buyNum180 = buyNum180;
	}
	public Integer getBuyNum90() {
		return buyNum90;
	}
	public void setBuyNum90(Integer buyNum90) {
		this.buyNum90 = buyNum90;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getManageCategoryName() {
		return manageCategoryName;
	}
	public void setManageCategoryName(String manageCategoryName) {
		this.manageCategoryName = manageCategoryName;
	}
	public String getManageCategoryLevelStr() {
		return manageCategoryLevelStr;
	}
	public void setManageCategoryLevelStr(String manageCategoryLevelStr) {
		this.manageCategoryLevelStr = manageCategoryLevelStr;
	}
	public String getGoodsTypeName() {
		return goodsTypeName;
	}
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}
	public String getGoodsLevelName() {
		return goodsLevelName;
	}
	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}
	public BigDecimal getGoodsLength() {
		return goodsLength;
	}
	public void setGoodsLength(BigDecimal goodsLength) {
		this.goodsLength = goodsLength;
	}
	public BigDecimal getGoodsWidth() {
		return goodsWidth;
	}
	public void setGoodsWidth(BigDecimal goodsWidth) {
		this.goodsWidth = goodsWidth;
	}
	public BigDecimal getGoodsHeight() {
		return goodsHeight;
	}
	public void setGoodsHeight(BigDecimal goodsHeight) {
		this.goodsHeight = goodsHeight;
	}
	public BigDecimal getPackageLength() {
		return packageLength;
	}
	public void setPackageLength(BigDecimal packageLength) {
		this.packageLength = packageLength;
	}
	public BigDecimal getPackageWidth() {
		return packageWidth;
	}
	public void setPackageWidth(BigDecimal packageWidth) {
		this.packageWidth = packageWidth;
	}
	public BigDecimal getPackageHeight() {
		return packageHeight;
	}
	public void setPackageHeight(BigDecimal packageHeight) {
		this.packageHeight = packageHeight;
	}
	public Integer getIsDiscard() {
		return isDiscard;
	}
	public void setIsDiscard(Integer isDiscard) {
		this.isDiscard = isDiscard;
	}
	public Integer getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getPackingList() {
		return packingList;
	}
	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}
	public String getAddTimeStr() {
		return addTimeStr;
	}
	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
	public String getModTimeStr() {
		return modTimeStr;
	}
	public void setModTimeStr(String modTimeStr) {
		this.modTimeStr = modTimeStr;
	}
	public String getVerifyTimeStr() {
		return verifyTimeStr;
	}
	public void setVerifyTimeStr(String verifyTimeStr) {
		this.verifyTimeStr = verifyTimeStr;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public Integer getGoodsLevel() {
		return goodsLevel;
	}
	public void setGoodsLevel(Integer goodsLevel) {
		this.goodsLevel = goodsLevel;
	}
	public Integer getIsOnSale() {
		return isOnSale;
	}
	public void setIsOnSale(Integer isOnSale) {
		this.isOnSale = isOnSale;
	}
	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}
	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}
	public Integer getSearchDateType() {
		return searchDateType;
	}
	public void setSearchDateType(Integer searchDateType) {
		this.searchDateType = searchDateType;
	}
	public Long getSearchBegintime() {
		return searchBegintime;
	}
	public void setSearchBegintime(Long searchBegintime) {
		this.searchBegintime = searchBegintime;
	}
	public Long getSearchEndtime() {
		return searchEndtime;
	}
	public void setSearchEndtime(Long searchEndtime) {
		this.searchEndtime = searchEndtime;
	}
	public String getCategoryNameOne() {
		return categoryNameOne;
	}
	public void setCategoryNameOne(String categoryNameOne) {
		this.categoryNameOne = categoryNameOne;
	}
	public String getCategoryNameTwo() {
		return categoryNameTwo;
	}
	public void setCategoryNameTwo(String categoryNameTwo) {
		this.categoryNameTwo = categoryNameTwo;
	}
	public String getCategoryNameThree() {
		return categoryNameThree;
	}
	public void setCategoryNameThree(String categoryNameThree) {
		this.categoryNameThree = categoryNameThree;
	}
	public String getStandardCategoryNameOne() {
		return standardCategoryNameOne;
	}
	public void setStandardCategoryNameOne(String standardCategoryNameOne) {
		this.standardCategoryNameOne = standardCategoryNameOne;
	}
	public String getStandardCategoryNameTwo() {
		return standardCategoryNameTwo;
	}
	public void setStandardCategoryNameTwo(String standardCategoryNameTwo) {
		this.standardCategoryNameTwo = standardCategoryNameTwo;
	}
	public String getStandardCategoryNameThree() {
		return standardCategoryNameThree;
	}
	public void setStandardCategoryNameThree(String standardCategoryNameThree) {
		this.standardCategoryNameThree = standardCategoryNameThree;
	}
    
}
