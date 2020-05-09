package com.vedeng.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.authorization.model.User;

public class Goods implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer goodsId;

	private Integer companyId;

	private Integer categoryId;

	private Integer brandId;

	private String sku;

	private String goodsName;

	private String aliasName;// 销售聚合页-别名-2018-6-19

	private String model;

	private String materialCode;

	private Integer baseUnitId;

	private Integer changeNum;

	private Integer unitId;

	private BigDecimal grossWeight;

	private BigDecimal netWeight;

	private BigDecimal goodsLength;

	private BigDecimal goodsWidth;

	private BigDecimal goodsHeight;

	private BigDecimal packageLength;

	private BigDecimal packageWidth;

	private BigDecimal packageHeight;

	private Integer goodsType;

	private Integer goodsLevel;

	private Integer manageCategory;

	private Integer manageCategoryLevel;

	private String purchaseRemind;

	private String licenseNumber;

	private String recordNumber;

	private String registrationNumber;
	
	private String registrationNumberName;

	private Long begintime;

	private Long endtime;

	private String authorizationCertificateUrl;

	private String otherQualificationUrl;

	private Integer source;// 商品来源 0-ERP | 1-耗材商城

    private String colorPageUrl;

    private String technicalParameterUrl;

    private String instructionsUrl;

    private String biddingDataUrl;

    private String packingList;

    private String tos;

    private Integer isRecommed;

    private Integer isNoReasonReturn;
    
    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    private String technicalParameter;
    
    private String performanceParameter;
    
    private String specParameter;
    
    private Integer isOnSale;
    
    private Integer isDiscard;
    
    private String taxCategoryNo;
    
    private String manufacturer;
    
    private String productionLicense;
    
    private Long discardTime;
    
    private String discardReason;
    
    private String brandName;
    
    private String unitName;
    
    private String categoryName;
    
    private String standardCategoryName;
    
    private String goodsLevelName;
    
    /**
     * wiki链接
     */
    private String href;
    
    private List<GoodsSysOptionAttribute> goodsSysOptionAttributes;
    
    private List<GoodsAttribute> goodsAttributes;
    
    private List<GoodsAttachment> goodsAttachments;
    
    //搜索日期类型 1：创建时间 2：更新时间 3：审核时间
    private Integer searchDateType;
    //搜索开始时间
    private Long searchBegintime;
    //搜索结束时间
    private Long searchEndtime;
    
    private String searchContent;//组合搜索
    
    private Integer orderOccupy;//订单占用
    
    private Integer stockNum;//库存
    
    private Integer adjustableNum;//可调剂
    
    private List<String> goodsIdArr;//批量操作产品ID列表
    
    private Integer changeCategoryId;//修改的分类ID
    
    private String startMon;//开始月份
    
    private String endMon;//开始月份
    
    private BigDecimal averagePrice;//近半年销售均价
    
    private double averageDeliveryCycle;//近半年货期均值
    
    private List<User> userList;//产品负责人
    
    private Integer saleNum90;//前90天销量
    
    private Integer saleNum30;//前30天销量
    
    private Integer quoteNum90;//前90天报价量
    
    private Integer quoteNum30;//前30天报价量
    
    private Integer onWayNum;//在途
    
    private Integer saleNum365;//近1年销量
    
    private Integer goodsUserId;//搜索产品归属ID
    
    private List<Integer> categoryIdList;// 产品归属对应的分类Id列表
    
    private String lastVerifyUsermae;//最后审核人
    
    private Integer verifiesType;//审核类型
    
    private String verifyUsername;//当前审核人
	    
    private Integer verifyStatus;//审核状态
	
    private List<String> verifyUsernameList;//当前审核人
    
    private String supplierName;//产品所属供应商
    
    private Long goodsLastInTime;//产品最近一次入库时间
    
    private Long goodsLastOutTime;//产品最近一次出库时间
    
    private BigDecimal channelPrice;//核算价
    
    private String supplyModel;
    
    private Integer standardCategoryId;
    
    private Integer standardCategoryLevel;
    
    private String spec;
    
    private String productAddress;
    
    private Integer aftersaleContentType;//售后内容类型
    
    private String goodsIdsList;//要出库的产品
    
    private Integer showCompanyType;//展示哪家公司产品 -1（总公司和当前公司） 1（总公司）2（当前登陆公司）
    
    private Integer currentCount;//当前是第几次查询，默认从2开始
    
    private Integer parentId;//从哪个产品复制而来的
    
    private Integer one;//产品分类一级
    private Integer two;//产品分类二级
    private Integer three;//产品分类三级
    
    private Integer isChannelPrice;//是否核价

	private BigDecimal jxMarketPrice;//贝登精品市场价

	private BigDecimal jxSalePrice;//贝登精品销售价

	private Integer isProblem;
	
	private Integer deliveryNum;//已发数量
	
	private Integer num;//总数

	private Integer pickCnt;//拣货数量
	
	private Integer totalNum;//可拣货库存
	
	private Integer pCountAll=0;//自动分配拣货数量总数
	
	private Integer nowNum;
	
	private Integer eNum=0;//快递发货数
	
	private Integer price=0;//外借单商品单价
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer geteNum() {
		return eNum;
	}

	public void seteNum(Integer eNum) {
		this.eNum = eNum;
	}

	public Integer getNowNum() {
		return nowNum;
	}

	public void setNowNum(Integer nowNum) {
		this.nowNum = nowNum;
	}

	public Integer getpCountAll() {
		return pCountAll;
	}

	public void setpCountAll(Integer pCountAll) {
		this.pCountAll = pCountAll;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPickCnt() {
		return pickCnt;
	}

	public void setPickCnt(Integer pickCnt) {
		this.pickCnt = pickCnt;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public void setJxMarketPrice(BigDecimal jxMarketPrice) {
		this.jxMarketPrice = jxMarketPrice;
	}

	public void setJxSalePrice(BigDecimal jxSalePrice) {
		this.jxSalePrice = jxSalePrice;
	}

	public Integer getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Integer isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

	public BigDecimal getJxMarketPrice() {
		return jxMarketPrice;
	}

	public void setJpMarketPrice(BigDecimal jxMarketPrice) {
		this.jxMarketPrice = jxMarketPrice;
	}

	public BigDecimal getJxSalePrice() {
		return jxSalePrice;
	}

	public void setJpSalePrice(BigDecimal jxSalePrice) {
		this.jxSalePrice = jxSalePrice;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRegistrationNumberName() {
		return registrationNumberName;
	}

	public void setRegistrationNumberName(String registrationNumberName) {
		this.registrationNumberName = registrationNumberName;
	}

	private List<String> channelPriceGoodsArr;//核价产品列表
    
	public List<String> getChannelPriceGoodsArr() {
		return channelPriceGoodsArr;
	}

	public void setChannelPriceGoodsArr(List<String> channelPriceGoodsArr) {
		this.channelPriceGoodsArr = channelPriceGoodsArr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsChannelPrice() {
		return isChannelPrice;
	}

	public void setIsChannelPrice(Integer isChannelPrice) {
		this.isChannelPrice = isChannelPrice;
	}

	public Integer getIsRecommed() {
		return isRecommed;
	}

	public void setIsRecommed(Integer isRecommed) {
		this.isRecommed = isRecommed;
	}

	public Integer getOne() {
		return one;
	}

	public void setOne(Integer one) {
		this.one = one;
	}

	public Integer getTwo() {
		return two;
	}

	public void setTwo(Integer two) {
		this.two = two;
	}

	public Integer getThree() {
		return three;
	}

	public void setThree(Integer three) {
		this.three = three;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public String getGoodsIdsList() {
		return goodsIdsList;
	}

	public void setGoodsIdsList(String goodsIdsList) {
		this.goodsIdsList = goodsIdsList;
	}

	public Integer getAftersaleContentType() {
		return aftersaleContentType;
	}

	public void setAftersaleContentType(Integer aftersaleContentType) {
		this.aftersaleContentType = aftersaleContentType;
	}

	public String getSupplyModel() {
		return supplyModel;
	}

	public void setSupplyModel(String supplyModel) {
		this.supplyModel = supplyModel;
	}

	public Integer getStandardCategoryId() {
		return standardCategoryId;
	}

	public void setStandardCategoryId(Integer standardCategoryId) {
		this.standardCategoryId = standardCategoryId;
	}

	public Integer getStandardCategoryLevel() {
		return standardCategoryLevel;
	}

	public void setStandardCategoryLevel(Integer standardCategoryLevel) {
		this.standardCategoryLevel = standardCategoryLevel;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getProductAddress() {
		return productAddress;
	}

	public void setProductAddress(String productAddress) {
		this.productAddress = productAddress;
	}

	public BigDecimal getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(BigDecimal channelPrice) {
		this.channelPrice = channelPrice;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName == null ? null : supplierName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getStartMon() {
		return startMon;
	}

	public void setStartMon(String startMon) {
		this.startMon = startMon;
	}

	public String getEndMon() {
		return endMon;
	}

	public void setEndMon(String endMon) {
		this.endMon = endMon;
	}

	public Integer getOrderOccupy() {
		return orderOccupy;
	}

	public void setOrderOccupy(Integer orderOccupy) {
		this.orderOccupy = orderOccupy;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getAdjustableNum() {
		return adjustableNum;
	}

	public void setAdjustableNum(Integer adjustableNum) {
		this.adjustableNum = adjustableNum;
	}

	public String getGoodsLevelName() {
		return goodsLevelName;
	}

	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model == null ? null : model.trim();
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode == null ? null : materialCode.trim();
	}

	public Integer getBaseUnitId() {
		return baseUnitId;
	}

	public void setBaseUnitId(Integer baseUnitId) {
		this.baseUnitId = baseUnitId;
	}

	public Integer getChangeNum() {
		return changeNum;
	}

	public void setChangeNum(Integer changeNum) {
		this.changeNum = changeNum;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
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

	public Integer getManageCategory() {
		return manageCategory;
	}

	public void setManageCategory(Integer manageCategory) {
		this.manageCategory = manageCategory;
	}

	public Integer getManageCategoryLevel() {
		return manageCategoryLevel;
	}

	public void setManageCategoryLevel(Integer manageCategoryLevel) {
		this.manageCategoryLevel = manageCategoryLevel;
	}

	public String getPurchaseRemind() {
		return purchaseRemind;
	}

	public void setPurchaseRemind(String purchaseRemind) {
		this.purchaseRemind = purchaseRemind == null ? null : purchaseRemind.trim();
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber == null ? null : registrationNumber.trim();
	}

	public Long getBegintime() {
		return begintime;
	}

	public void setBegintime(Long begintime) {
		this.begintime = begintime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public String getAuthorizationCertificateUrl() {
		return authorizationCertificateUrl;
	}

	public void setAuthorizationCertificateUrl(String authorizationCertificateUrl) {
		this.authorizationCertificateUrl = authorizationCertificateUrl == null ? null
				: authorizationCertificateUrl.trim();
	}

	public String getOtherQualificationUrl() {
		return otherQualificationUrl;
	}

	public void setOtherQualificationUrl(String otherQualificationUrl) {
		this.otherQualificationUrl = otherQualificationUrl == null ? null : otherQualificationUrl.trim();
	}

	public String getColorPageUrl() {
		return colorPageUrl;
	}

	public void setColorPageUrl(String colorPageUrl) {
		this.colorPageUrl = colorPageUrl == null ? null : colorPageUrl.trim();
	}

	public String getTechnicalParameterUrl() {
		return technicalParameterUrl;
	}

	public void setTechnicalParameterUrl(String technicalParameterUrl) {
		this.technicalParameterUrl = technicalParameterUrl == null ? null : technicalParameterUrl.trim();
	}

	public String getInstructionsUrl() {
		return instructionsUrl;
	}

	public void setInstructionsUrl(String instructionsUrl) {
		this.instructionsUrl = instructionsUrl == null ? null : instructionsUrl.trim();
	}

	public String getBiddingDataUrl() {
		return biddingDataUrl;
	}

	public void setBiddingDataUrl(String biddingDataUrl) {
		this.biddingDataUrl = biddingDataUrl == null ? null : biddingDataUrl.trim();
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList == null ? null : packingList.trim();
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos == null ? null : tos.trim();
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Long getModTime() {
		return modTime;
	}

	public void setModTime(Long modTime) {
		this.modTime = modTime;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public String getTechnicalParameter() {
		return technicalParameter;
	}

	public void setTechnicalParameter(String technicalParameter) {
		this.technicalParameter = technicalParameter == null ? null : technicalParameter.trim();
	}

	public Integer getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Integer isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Integer getIsDiscard() {
		return isDiscard;
	}

	public void setIsDiscard(Integer isDiscard) {
		this.isDiscard = isDiscard;
	}

	public String getDiscardReason() {
		return discardReason;
	}

	public void setDiscardReason(String discardReason) {
		this.discardReason = discardReason == null ? null : discardReason.trim();
	}

	public List<GoodsSysOptionAttribute> getGoodsSysOptionAttributes() {
		return goodsSysOptionAttributes;
	}

	public void setGoodsSysOptionAttributes(List<GoodsSysOptionAttribute> goodsSysOptionAttributes) {
		this.goodsSysOptionAttributes = goodsSysOptionAttributes;
	}

	public List<GoodsAttribute> getGoodsAttributes() {
		return goodsAttributes;
	}

	public void setGoodsAttributes(List<GoodsAttribute> goodsAttributes) {
		this.goodsAttributes = goodsAttributes;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public String getTaxCategoryNo() {
		return taxCategoryNo;
	}

	public void setTaxCategoryNo(String taxCategoryNo) {
		this.taxCategoryNo = taxCategoryNo;
	}

	public Long getDiscardTime() {
		return discardTime;
	}

	public void setDiscardTime(Long discardTime) {
		this.discardTime = discardTime;
	}

	public List<GoodsAttachment> getGoodsAttachments() {
		return goodsAttachments;
	}

	public void setGoodsAttachments(List<GoodsAttachment> goodsAttachments) {
		this.goodsAttachments = goodsAttachments;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer == null ? null : manufacturer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getProductionLicense() {
		return productionLicense;
	}

	public void setProductionLicense(String productionLicense) {
		this.productionLicense = productionLicense;
	}

	public List<String> getGoodsIdArr() {
		return goodsIdArr;
	}

	public void setGoodsIdArr(List<String> goodsIdArr) {
		this.goodsIdArr = goodsIdArr;
	}

	public Integer getChangeCategoryId() {
		return changeCategoryId;
	}

	public void setChangeCategoryId(Integer changeCategoryId) {
		this.changeCategoryId = changeCategoryId;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public double getAverageDeliveryCycle() {
		return averageDeliveryCycle;
	}

	public void setAverageDeliveryCycle(double averageDeliveryCycle) {
		this.averageDeliveryCycle = averageDeliveryCycle;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getSaleNum90() {
		return saleNum90;
	}

	public void setSaleNum90(Integer saleNum90) {
		this.saleNum90 = saleNum90;
	}

	public Integer getSaleNum30() {
		return saleNum30;
	}

	public void setSaleNum30(Integer saleNum30) {
		this.saleNum30 = saleNum30;
	}

	public Integer getQuoteNum90() {
		return quoteNum90;
	}

	public void setQuoteNum90(Integer quoteNum90) {
		this.quoteNum90 = quoteNum90;
	}

	public Integer getQuoteNum30() {
		return quoteNum30;
	}

	public void setQuoteNum30(Integer quoteNum30) {
		this.quoteNum30 = quoteNum30;
	}

	public Integer getSaleNum365() {
		return saleNum365;
	}

	public void setSaleNum365(Integer saleNum365) {
		this.saleNum365 = saleNum365;
	}

	public Integer getOnWayNum() {
		return onWayNum;
	}

	public void setOnWayNum(Integer onWayNum) {
		this.onWayNum = onWayNum;
	}

	public Integer getGoodsUserId() {
		return goodsUserId;
	}

	public void setGoodsUserId(Integer goodsUserId) {
		this.goodsUserId = goodsUserId;
	}

	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public String getLastVerifyUsermae() {
		return lastVerifyUsermae;
	}

	public void setLastVerifyUsermae(String lastVerifyUsermae) {
		this.lastVerifyUsermae = lastVerifyUsermae;
	}

	public String getVerifyUsername() {
		return verifyUsername;
	}

	public void setVerifyUsername(String verifyUsername) {
		this.verifyUsername = verifyUsername;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public List<String> getVerifyUsernameList() {
		return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
		this.verifyUsernameList = verifyUsernameList;
	}

	public Long getGoodsLastInTime() {
		return goodsLastInTime;
	}

	public void setGoodsLastInTime(Long goodsLastInTime) {
		this.goodsLastInTime = goodsLastInTime;
	}

	public Long getGoodsLastOutTime() {
		return goodsLastOutTime;
	}

	public void setGoodsLastOutTime(Long goodsLastOutTime) {
		this.goodsLastOutTime = goodsLastOutTime;
	}

	public Integer getVerifiesType() {
		return verifiesType;
	}

	public void setVerifiesType(Integer verifiesType) {
		this.verifiesType = verifiesType;
	}

	public String getPerformanceParameter() {
		return performanceParameter;
	}

	public void setPerformanceParameter(String performanceParameter) {
		this.performanceParameter = performanceParameter;
	}

	public String getSpecParameter() {
		return specParameter;
	}

	public void setSpecParameter(String specParameter) {
		this.specParameter = specParameter;
	}

	public String getStandardCategoryName() {
		return standardCategoryName;
	}

	public void setStandardCategoryName(String standardCategoryName) {
		this.standardCategoryName = standardCategoryName;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Integer getShowCompanyType() {
		return showCompanyType;
	}

	public void setShowCompanyType(Integer showCompanyType) {
		this.showCompanyType = showCompanyType;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

    public Integer getIsProblem() {
        return isProblem;
    }

    public void setIsProblem(Integer isProblem) {
        this.isProblem = isProblem;
    }
}
