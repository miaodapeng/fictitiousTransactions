package com.vedeng.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;

public class SaleorderGoods implements Serializable {

	// add by Tomcat.Hui 2019/11/12 13:32 .Desc: VDERP-1325 分批开票. start
	private BigDecimal appliedNum;

	private BigDecimal invoicedNum;

	private Integer canInvoicedNum;

	private BigDecimal appliedAmount;

	private BigDecimal invoicedAmount;

	public BigDecimal getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(BigDecimal appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}

	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	public Integer getCanInvoicedNum() {
		return canInvoicedNum;
	}

	public void setCanInvoicedNum(Integer canInvoicedNum) {
		this.canInvoicedNum = canInvoicedNum;
	}

	public BigDecimal getAppliedNum() {
		return appliedNum;
	}

	public void setAppliedNum(BigDecimal appliedNum) {
		this.appliedNum = appliedNum;
	}

	public BigDecimal getInvoicedNum() {
		return invoicedNum;
	}

	public void setInvoicedNum(BigDecimal invoicedNum) {
		this.invoicedNum = invoicedNum;
	}

	// add by Tomcat.Hui 2019/11/12 13:32 .Desc: VDERP-1325 分批开票. end

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7609000365294092689L;

	private Integer saleorderGoodsId;

	private Integer saleorderId;

	private String msaleorderNo;

	private Integer elOrderlistId;

	private Integer goodsId;

	private String sku;

	private String goodsName;

	private String brandName;

	private String model;

	private String unitName;

	/**
	 * `PRICE` decimal(10,2) DEFAULT '0.00' COMMENT '单价 在耗材商城订单中为优惠后单价' 订单中真实单价
	 */
	private BigDecimal price;

	/**
	 * `REAL_PRICE` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '实际单价 耗材商城实际单价',
	 * 即订单的原来的单价
	 */
	private BigDecimal realPrice;

	/**
	 * sku小计金额
	 */
	private BigDecimal maxSkuRefundAmount;
	/*
	 * V_CORE_SKU表贝登精选市场价
	 * JX_MARKET_PRICE
	 */
	private BigDecimal jxMarketPrice;
	/*
	 * V_CORE_SKU表贝登精销售价
	 * JX_SALE_PRICE
	 */
	private BigDecimal jxSalePrice;
	
	private Integer currencyUnitId;

	private Integer num;

	private Integer dbBuyNum;// 数据库中对应已采购数量字段

	private String deliveryCycle;

	private Integer deliveryDirect;

	private String deliveryDirectComments;

	private String registrationNumber;

	private String supplierName;

	private BigDecimal referenceCostPrice;

	private BigDecimal referencePrice;

	private String referenceDeliveryCycle;

	private Integer reportStatus;

	private String reportComments;

	private Integer haveInstallation;

	private String goodsComments;

	private String insideComments;

	private Integer arrivalUserId;

	private Integer arrivalStatus;

	private Long arrivalTime;

	private Integer isDelete;

	private Long addTime;

	private Integer creator;

	private Long modTime;

	private Integer updater;

	private Integer deliveryNum;

	private Integer deliveryStatus;

	private Long deliveryTime;

	private Integer isIgnore;

	private Long ignoreTime;// 忽略时间

	private Integer ignoreUserId;// 忽略人

	private String purchasingPrice;

	private String goodsLevelName;// 产品级别

	private Goods goods;// 产品信息

	private String manageCategoryName;// 管理类别名称

	private String materialCode;// 物料编码

	private String storageAddress;// 存储位置

	private Integer noOutNum;// 拣货未发出产品数

	private Integer nowNum;// 本次拣货产品数

	private String allPrice;// 商品总价

	private String prices;// 单价

	private List<WarehouseGoodsOperateLog> wlist;// 商品入库详情列表

	private Integer pickCnt;// 拣货总数

	private Integer totalNum;// 库存总数

	private String isOut;// 是否是出库1是0否

	private List<String> whList;// 仓库名称列表

	private Integer eNum = 0;// 快递发货数

	private Integer buyNum;// 采购数量

	private Integer warehouseNum;// 入库数量

	private BigDecimal averagePrice;// 近半年销售均价

	private BigDecimal averageDeliveryCycle;// 近半年货期均值

	private Integer bussinessId;// 业务id（换货订单、样品外借订单）

	private Integer bussinessType = 0;// 业务类型：1销售入库 2销售出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库

	private Integer companyId;

	private BigDecimal cgPrice;// 采购单价

	private Integer categoryId;// 分类Id

	private BigDecimal settlementPrice;// 结算价

	private Integer areaControl = 0;// 是否区域控制0：否；；1：是

	private BigDecimal channelPrice;// 核算价格

	private String channelDeliveryCycle, delivery;// 核价货期

	private String buyOrderNo;// 采购单号

	private Integer expressNum;// 快递已发送数量

	private BigDecimal expressPrice;// 快递已发送产品价格均价

	private String serviceNo;// 录保卡好
	private Integer pCountAll = 0;// 自动分配拣货数量总数
	private String creatorNm;

	private Saleorder saleorder;

	private BigDecimal settlePrice;// 产品结算价格
	private Integer barcodeId;// 二维码id

	private BigDecimal costPrice;// 成本价

	private List<Buyorder> buyorderList;// 对应采购订单列表

	private Long satisfyDeliveryTime;// 可发货时间

	private Integer buyorderStatus;// 当前销售产品的采购状态(0-未采购；1-部分采购；2-全部采购)

	private Integer afterReturnNum;
	private BigDecimal afterReturnAmount;

	private Integer warehouseReturnNum;

	private Integer terminalTraderId, terminalTraderType;// 终端客户ID和类型

	private String terminalTraderName;

	private Integer salesAreaId;// 报价区域Id

	private String salesArea;// H报价区域

	private Integer customerNature;// 报价客户性质

	private String goodsUserIdStr;// 产品负责人

	private List<Integer> saleorderGoodsIdList;

	private Integer lockedStatus = 0;// 锁定状态0未锁定 1已锁定

	private  Integer occupyNum;//占用数量

	private Integer isActionGoods;//是否为活动商品   0否  1是

	private Integer actionOccupyNum;//活动占用库存数量

	private Integer actionLockCount;//活动锁定库存

	private Integer isCoupons;//是否使用优惠券

	private List<Integer> goodsIdList = new ArrayList<>();
	/*商品流切换*/
	private String checkStatus;
	private String assis;
	private String manager;
	private String skuNew,goodsNameNew,brandNameNew,modelNew,unitNameNew;

	public String getSkuNew() {
		return skuNew;
	}

	public void setSkuNew(String skuNew) {
		this.skuNew = skuNew;
	}

	public String getGoodsNameNew() {
		return goodsNameNew;
	}

	public void setGoodsNameNew(String goodsNameNew) {
		this.goodsNameNew = goodsNameNew;
	}

	public String getBrandNameNew() {
		return brandNameNew;
	}

	public void setBrandNameNew(String brandNameNew) {
		this.brandNameNew = brandNameNew;
	}

	public String getModelNew() {
		return modelNew;
	}

	public void setModelNew(String modelNew) {
		this.modelNew = modelNew;
	}

	public String getUnitNameNew() {
		return unitNameNew;
	}

	public void setUnitNameNew(String unitNameNew) {
		this.unitNameNew = unitNameNew;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getAssis() {
		return assis;
	}

	public void setAssis(String assis) {
		this.assis = assis;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Integer getIsActionGoods() {
		return isActionGoods;
	}

	public void setIsActionGoods(Integer isActionGoods) {
		this.isActionGoods = isActionGoods;
	}

	public Integer getActionOccupyNum() {
		return actionOccupyNum;
	}

	public void setActionOccupyNum(Integer actionOccupyNum) {
		this.actionOccupyNum = actionOccupyNum;
	}

	public Integer getOccupyNum() {
		return occupyNum;
	}

	public void setOccupyNum(Integer occupyNum) {
		this.occupyNum = occupyNum;
	}

	public BigDecimal getJxMarketPrice() {
		return jxMarketPrice;
	}

	public void setJxMarketPrice(BigDecimal jxMarketPrice) {
		this.jxMarketPrice = jxMarketPrice;
	}

	public BigDecimal getJxSalePrice() {
		return jxSalePrice;
	}

	public void setJxSalePrice(BigDecimal jxSalePrice) {
		this.jxSalePrice = jxSalePrice;
	}

	public BigDecimal getAfterReturnAmount() {
		return afterReturnAmount;
	}

	public void setAfterReturnAmount(BigDecimal afterReturnAmount) {
		this.afterReturnAmount = afterReturnAmount;
	}
	public String getMsaleorderNo() {
		return msaleorderNo;
	}

	public void setMsaleorderNo(String msaleorderNo) {
		this.msaleorderNo = msaleorderNo;
	}
	public List<Integer> getGoodsIdList() {
		return goodsIdList;
	}

	public void setGoodsIdList(List<Integer> goodsIdList) {
		this.goodsIdList = goodsIdList;
	}

	public Integer getLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(Integer lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	public List<Integer> getSaleorderGoodsIdList() {
		return saleorderGoodsIdList;
	}

	public void setSaleorderGoodsIdList(List<Integer> saleorderGoodsIdList) {
		this.saleorderGoodsIdList = saleorderGoodsIdList;
	}

	public String getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(String allPrice) {
		this.allPrice = allPrice;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Integer getTerminalTraderId() {
		return terminalTraderId;
	}

	public void setTerminalTraderId(Integer terminalTraderId) {
		this.terminalTraderId = terminalTraderId;
	}

	public Integer getTerminalTraderType() {
		return terminalTraderType;
	}

	public void setTerminalTraderType(Integer terminalTraderType) {
		this.terminalTraderType = terminalTraderType;
	}

	public String getTerminalTraderName() {
		return terminalTraderName;
	}

	public void setTerminalTraderName(String terminalTraderName) {
		this.terminalTraderName = terminalTraderName == null ? null : terminalTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getSalesAreaId() {
		return salesAreaId;
	}

	public void setSalesAreaId(Integer salesAreaId) {
		this.salesAreaId = salesAreaId;
	}

	public String getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getAfterReturnNum() {
		return afterReturnNum;
	}

	public void setAfterReturnNum(Integer afterReturnNum) {
		this.afterReturnNum = afterReturnNum;
	}

	public Integer getBuyorderStatus() {
		return buyorderStatus;
	}

	public void setBuyorderStatus(Integer buyorderStatus) {
		this.buyorderStatus = buyorderStatus;
	}

	public Long getSatisfyDeliveryTime() {
		return satisfyDeliveryTime;
	}

	public void setSatisfyDeliveryTime(Long satisfyDeliveryTime) {
		this.satisfyDeliveryTime = satisfyDeliveryTime;
	}

	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	public Saleorder getSaleorder() {
		return saleorder;
	}

	public void setSaleorder(Saleorder saleorder) {
		this.saleorder = saleorder;
	}

	public Integer getpCountAll() {
		return pCountAll;
	}

	public void setpCountAll(Integer pCountAll) {
		this.pCountAll = pCountAll;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	public String getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(String buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}

	public Integer getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(Integer expressNum) {
		this.expressNum = expressNum;
	}

	public BigDecimal getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(BigDecimal expressPrice) {
		this.expressPrice = expressPrice;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getChannelDeliveryCycle() {
		return channelDeliveryCycle;
	}

	public void setChannelDeliveryCycle(String channelDeliveryCycle) {
		this.channelDeliveryCycle = channelDeliveryCycle;
	}

	public Integer getAreaControl() {
		return areaControl;
	}

	public void setAreaControl(Integer areaControl) {
		this.areaControl = areaControl;
	}

	public BigDecimal getChannelPrice() {
		return channelPrice;
	}

	public void setChannelPrice(BigDecimal channelPrice) {
		this.channelPrice = channelPrice;
	}

	public BigDecimal getCgPrice() {
		return cgPrice;
	}

	public void setCgPrice(BigDecimal cgPrice) {
		this.cgPrice = cgPrice;
	}

	private BigDecimal lastOrderPrice;// 上次购买价格

	private String goodsUserNm;// 产品负责人

	private Integer tNum;// 退货数

	public Integer gettNum() {
		return tNum;
	}

	public void settNum(Integer tNum) {
		this.tNum = tNum;
	}

	public String getGoodsUserNm() {
		return goodsUserNm;
	}

	public void setGoodsUserNm(String goodsUserNm) {
		this.goodsUserNm = goodsUserNm;
	}

	public BigDecimal getLastOrderPrice() {
		return lastOrderPrice;
	}

	public void setLastOrderPrice(BigDecimal lastOrderPrice) {
		this.lastOrderPrice = lastOrderPrice;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId) {
		this.bussinessId = bussinessId;
	}

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getWarehouseNum() {
		return warehouseNum;
	}

	public void setWarehouseNum(Integer warehouseNum) {
		this.warehouseNum = warehouseNum;
	}

	public Integer geteNum() {
		return eNum;
	}

	public void seteNum(Integer eNum) {
		this.eNum = eNum;
	}

	public List<String> getWhList() {
		return whList;
	}

	public void setWhList(List<String> whList) {
		this.whList = whList;
	}

	public String getIsOut() {
		return isOut;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getPickCnt() {
		return pickCnt;
	}

	public void setPickCnt(Integer pickCnt) {
		this.pickCnt = pickCnt;
	}

	public List<WarehouseGoodsOperateLog> getWlist() {
		return wlist;
	}

	public void setWlist(List<WarehouseGoodsOperateLog> wlist) {
		this.wlist = wlist;
	}

	public Integer getNoOutNum() {
		return noOutNum;
	}

	public void setNoOutNum(Integer noOutNum) {
		this.noOutNum = noOutNum;
	}

	public Integer getNowNum() {
		return nowNum;
	}

	public void setNowNum(Integer nowNum) {
		this.nowNum = nowNum;
	}

	public String getStorageAddress() {
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}

	private Integer LineNum;

	public Integer getLineNum() {
		return LineNum;
	}

	public void setLineNum(Integer lineNum) {
		LineNum = lineNum;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public Integer getSaleorderGoodsId() {
		return saleorderGoodsId;
	}

	public void setSaleorderGoodsId(Integer saleorderGoodsId) {
		this.saleorderGoodsId = saleorderGoodsId;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
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
		this.sku = sku == null ? null : sku.trim();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model == null ? null : model.trim();
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName == null ? null : unitName.trim();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCurrencyUnitId() {
		return currencyUnitId;
	}

	public void setCurrencyUnitId(Integer currencyUnitId) {
		this.currencyUnitId = currencyUnitId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDeliveryCycle() {
		return deliveryCycle;
	}

	public void setDeliveryCycle(String deliveryCycle) {
		this.deliveryCycle = deliveryCycle == null ? null : deliveryCycle.trim();
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public String getDeliveryDirectComments() {
		return deliveryDirectComments;
	}

	public void setDeliveryDirectComments(String deliveryDirectComments) {
		this.deliveryDirectComments = deliveryDirectComments == null ? null : deliveryDirectComments.trim();
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber == null ? null : registrationNumber.trim();
	}

	public String getSupplierName() {
		return supplierName;
	}

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

	public BigDecimal getReferenceCostPrice() {
		return referenceCostPrice;
	}

	public void setReferenceCostPrice(BigDecimal referenceCostPrice) {
		this.referenceCostPrice = referenceCostPrice;
	}

	public BigDecimal getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(BigDecimal referencePrice) {
		this.referencePrice = referencePrice;
	}

	public String getReferenceDeliveryCycle() {
		return referenceDeliveryCycle;
	}

	public void setReferenceDeliveryCycle(String referenceDeliveryCycle) {
		this.referenceDeliveryCycle = referenceDeliveryCycle == null ? null : referenceDeliveryCycle.trim();
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getReportComments() {
		return reportComments;
	}

	public void setReportComments(String reportComments) {
		this.reportComments = reportComments == null ? null : reportComments.trim();
	}

	public Integer getHaveInstallation() {
		return haveInstallation;
	}

	public void setHaveInstallation(Integer haveInstallation) {
		this.haveInstallation = haveInstallation;
	}

	public String getGoodsComments() {
		return goodsComments;
	}

	public void setGoodsComments(String goodsComments) {
		this.goodsComments = goodsComments == null ? null : goodsComments.trim();
	}

	public String getInsideComments() {
		return insideComments;
	}

	public void setInsideComments(String insideComments) {
		this.insideComments = insideComments == null ? null : insideComments.trim();
	}

	public Integer getArrivalUserId() {
		return arrivalUserId;
	}

	public void setArrivalUserId(Integer arrivalUserId) {
		this.arrivalUserId = arrivalUserId;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getIsIgnore() {
		return isIgnore;
	}

	public void setIsIgnore(Integer isIgnore) {
		this.isIgnore = isIgnore;
	}

	public String getPurchasingPrice() {
		return purchasingPrice;
	}

	public void setPurchasingPrice(String purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}

	public String getGoodsLevelName() {
		return goodsLevelName;
	}

	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getManageCategoryName() {
		return manageCategoryName;
	}

	public void setManageCategoryName(String manageCategoryName) {
		this.manageCategoryName = manageCategoryName;
	}

	public Long getIgnoreTime() {
		return ignoreTime;
	}

	public void setIgnoreTime(Long ignoreTime) {
		this.ignoreTime = ignoreTime;
	}

	public Integer getIgnoreUserId() {
		return ignoreUserId;
	}

	public void setIgnoreUserId(Integer ignoreUserId) {
		this.ignoreUserId = ignoreUserId;
	}

	public Integer getDbBuyNum() {
		return dbBuyNum;
	}

	public void setDbBuyNum(Integer dbBuyNum) {
		this.dbBuyNum = dbBuyNum;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getAverageDeliveryCycle() {
		return averageDeliveryCycle;
	}

	public void setAverageDeliveryCycle(BigDecimal averageDeliveryCycle) {
		this.averageDeliveryCycle = averageDeliveryCycle;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public BigDecimal getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(BigDecimal settlePrice) {
		this.settlePrice = settlePrice;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public List<Buyorder> getBuyorderList() {
		return buyorderList;
	}

	public void setBuyorderList(List<Buyorder> buyorderList) {
		this.buyorderList = buyorderList;
	}

	public String getGoodsUserIdStr() {
		return goodsUserIdStr;
	}

	public void setGoodsUserIdStr(String goodsUserIdStr) {
		this.goodsUserIdStr = goodsUserIdStr;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public BigDecimal getMaxSkuRefundAmount() {
		return maxSkuRefundAmount;
	}

	public void setMaxSkuRefundAmount(BigDecimal maxSkuRefundAmount) {
		this.maxSkuRefundAmount = maxSkuRefundAmount;
	}

	public Integer getWarehouseReturnNum() {
		return warehouseReturnNum;
	}

	public void setWarehouseReturnNum(Integer warehouseReturnNum) {
		this.warehouseReturnNum = warehouseReturnNum;
	}

	public Integer getActionLockCount() {
		return actionLockCount;
	}

	public void setActionLockCount(Integer actionLockCount) {
		this.actionLockCount = actionLockCount;
	}

	public Integer getIsCoupons() {
		return isCoupons;
	}

	public void setIsCoupons(Integer isCoupons) {
		this.isCoupons = isCoupons;
	}

	@Override
	public String toString() {
		return "SaleorderGoods{" +
				"saleorderGoodsId=" + saleorderGoodsId +
				", saleorderId=" + saleorderId +
				", msaleorderNo='" + msaleorderNo + '\'' +
				", goodsId=" + goodsId +
				", sku='" + sku + '\'' +
				", goodsName='" + goodsName + '\'' +
				", brandName='" + brandName + '\'' +
				", model='" + model + '\'' +
				", unitName='" + unitName + '\'' +
				", price=" + price +
				", realPrice=" + realPrice +
				", maxSkuRefundAmount=" + maxSkuRefundAmount +
				", jxMarketPrice=" + jxMarketPrice +
				", jxSalePrice=" + jxSalePrice +
				", currencyUnitId=" + currencyUnitId +
				", num=" + num +
				", dbBuyNum=" + dbBuyNum +
				", deliveryCycle='" + deliveryCycle + '\'' +
				", deliveryDirect=" + deliveryDirect +
				", deliveryDirectComments='" + deliveryDirectComments + '\'' +
				", registrationNumber='" + registrationNumber + '\'' +
				", supplierName='" + supplierName + '\'' +
				", referenceCostPrice=" + referenceCostPrice +
				", referencePrice=" + referencePrice +
				", referenceDeliveryCycle='" + referenceDeliveryCycle + '\'' +
				", reportStatus=" + reportStatus +
				", reportComments='" + reportComments + '\'' +
				", haveInstallation=" + haveInstallation +
				", goodsComments='" + goodsComments + '\'' +
				", insideComments='" + insideComments + '\'' +
				", arrivalUserId=" + arrivalUserId +
				", arrivalStatus=" + arrivalStatus +
				", arrivalTime=" + arrivalTime +
				", isDelete=" + isDelete +
				", addTime=" + addTime +
				", creator=" + creator +
				", modTime=" + modTime +
				", updater=" + updater +
				", deliveryNum=" + deliveryNum +
				", deliveryStatus=" + deliveryStatus +
				", deliveryTime=" + deliveryTime +
				", isIgnore=" + isIgnore +
				", ignoreTime=" + ignoreTime +
				", ignoreUserId=" + ignoreUserId +
				", purchasingPrice='" + purchasingPrice + '\'' +
				", goodsLevelName='" + goodsLevelName + '\'' +
				", goods=" + goods +
				", manageCategoryName='" + manageCategoryName + '\'' +
				", materialCode='" + materialCode + '\'' +
				", storageAddress='" + storageAddress + '\'' +
				", noOutNum=" + noOutNum +
				", nowNum=" + nowNum +
				", allPrice='" + allPrice + '\'' +
				", prices='" + prices + '\'' +
				", wlist=" + wlist +
				", pickCnt=" + pickCnt +
				", totalNum=" + totalNum +
				", isOut='" + isOut + '\'' +
				", whList=" + whList +
				", eNum=" + eNum +
				", buyNum=" + buyNum +
				", warehouseNum=" + warehouseNum +
				", averagePrice=" + averagePrice +
				", averageDeliveryCycle=" + averageDeliveryCycle +
				", bussinessId=" + bussinessId +
				", bussinessType=" + bussinessType +
				", companyId=" + companyId +
				", cgPrice=" + cgPrice +
				", categoryId=" + categoryId +
				", settlementPrice=" + settlementPrice +
				", areaControl=" + areaControl +
				", channelPrice=" + channelPrice +
				", channelDeliveryCycle='" + channelDeliveryCycle + '\'' +
				", delivery='" + delivery + '\'' +
				", buyOrderNo='" + buyOrderNo + '\'' +
				", expressNum=" + expressNum +
				", expressPrice=" + expressPrice +
				", serviceNo='" + serviceNo + '\'' +
				", pCountAll=" + pCountAll +
				", creatorNm='" + creatorNm + '\'' +
				", saleorder=" + saleorder +
				", settlePrice=" + settlePrice +
				", barcodeId=" + barcodeId +
				", costPrice=" + costPrice +
				", buyorderList=" + buyorderList +
				", satisfyDeliveryTime=" + satisfyDeliveryTime +
				", buyorderStatus=" + buyorderStatus +
				", afterReturnNum=" + afterReturnNum +
				", afterReturnAmount=" + afterReturnAmount +
				", warehouseReturnNum=" + warehouseReturnNum +
				", terminalTraderId=" + terminalTraderId +
				", terminalTraderType=" + terminalTraderType +
				", terminalTraderName='" + terminalTraderName + '\'' +
				", salesAreaId=" + salesAreaId +
				", salesArea='" + salesArea + '\'' +
				", customerNature=" + customerNature +
				", goodsUserIdStr='" + goodsUserIdStr + '\'' +
				", saleorderGoodsIdList=" + saleorderGoodsIdList +
				", lockedStatus=" + lockedStatus +
				", occupyNum=" + occupyNum +
				", isActionGoods=" + isActionGoods +
				", actionOccupyNum=" + actionOccupyNum +
				", actionLockCount=" + actionLockCount +
				", goodsIdList=" + goodsIdList +
				", lastOrderPrice=" + lastOrderPrice +
				", goodsUserNm='" + goodsUserNm + '\'' +
				", tNum=" + tNum +
				", LineNum=" + LineNum +
				'}';
	}
	public Integer getElOrderlistId() {
		return elOrderlistId;
	}

	public void setElOrderlistId(Integer elOrderlistId) {
		this.elOrderlistId = elOrderlistId;
	}
}
