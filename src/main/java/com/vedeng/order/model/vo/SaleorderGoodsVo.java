package com.vedeng.order.model.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.logistics.model.vo.WarehouseGoodsStatusVo;
import com.vedeng.order.model.SaleorderGoods;

/**
 * <b>Description:</b><br>
 * 此类用于展示待采购列表中产品信息
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.model.vo <br>
 *       <b>ClassName:</b> SaleorderGoodsVo <br>
 *       <b>Date:</b> 2017年7月13日 下午4:34:20
 */
public class SaleorderGoodsVo extends SaleorderGoods {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer applicantId;// 申请人
	
	private String applicantName;// 申请人名称

	private Integer proOrgtId;// 产品部门

	private Integer proUserId;// 产品负责人
	
	private String goodsLevelName;//产品级别
	
	private Integer goodsStock;//库存数量
	
	private Integer canUseGoodsStock;//可用库存数量
	
	private List<WarehouseGoodsStatusVo> goodsStockList;//商品库存列表

	private String materialCode;//物料编码
	
	private String ignoreName;//忽略人
	
	private String optUserName;//忽略人
	
	//private Integer buyNum;//已采购数量
	
	private Integer saleBuyNum;//采购订单页面每个销售产品填写的数量
	
	private List<Integer> userIds;
	
	private Integer buyorderGoodsId;//关系表修改数量使用
	
	private Integer receiveNum;//已收货数量
	
	private BigDecimal invoiceNum;//已开票数量
	
	private Integer afterSaleUpLimitNum;//售后数量上限
	
	private String saleName; //归属销售
	
	// 以下字段为Saleorder中字段
	private Integer quoteorderId;

	private Integer parentId;

	private String saleorderNo;

	private Integer orderType;

	private Integer orgId;

	private Integer userId;

	private Integer validStatus;

	private Long validTime;

	private Integer status;

	private Integer purchaseStatus;

	//private Integer lockedStatus;

	private Integer invoiceStatus;

	private Long invoiceTime;

	private Integer paymentStatus;

	private Long paymentTime;

	private Integer deliveryStatus;

	private Long deliveryTime;

	private Integer arrivalStatus;

	private Long arrivalTime;

	private Integer serviceStatus;

	private Integer haveAccountPeriod;

	private BigDecimal totalAmount;

	private Integer traderId;

	private Integer customerType;

	private Integer customerNature;

	private String traderName;

	private Integer traderContactId;

	private String traderContactName;

	private String traderContactMobile;

	private String traderContactTelephone;

	private Integer traderAddressId;

	private String traderAddress;

	private String traderComments;

	private Integer takeTraderId;

	private String takeTraderName;

	private Integer takeTraderContactId;

	private String takeTraderContactName;

	private String takeTraderContactMobile;

	private String takeTraderContactTelephone;

	private Integer takeTraderAddressId;

	private String takeTraderAddress;

	private Integer isSendInvoice;

	private Integer invoiceTraderId;

	private String invoiceTraderName;

	private Integer invoiceTraderContactId;

	private String invoiceTraderContactName;

	private String invoiceTraderContactMobile;

	private String invoiceTraderContactTelephone;

	private Integer invoiceTraderAddressId;

	private String invoiceTraderAddress;

	private Integer salesAreaId;

	private String salesArea;

	private Integer terminalTraderId;

	private String terminalTraderName;

	private Integer terminalTraderType;

	private Integer invoiceType;

	private Integer freightDescription;

	private Integer deliveryType;

	private Integer logisticsId;

	private Integer paymentType;

	private BigDecimal prepaidAmount;

	private BigDecimal accountPeriodAmount;

	private Integer logisticsCollection;

	private BigDecimal retainageAmount;

	private Integer retainageAmountMonth;

	private String paymentComments;

	private String additionalClause;

	private String logisticsComments;

	private String financeComments;

	private String comments;

	private String invoiceComments;

	private Integer deliveryDirect;

	private String supplierClause;

	private Integer haveAdvancePurchase;

	private Integer isUrgent;

	private BigDecimal urgentAmount;

	private Integer haveCommunicate;

	private String prepareComments;

	private String marketingPlan;

	private Integer syncStatus;

	private String traderArea;

	private String takeTraderArea;

	private String invoiceTraderArea;

	private List<SaleorderVo> saleorderVoList = new ArrayList<>();
	
    private Integer needCnt;//订单所需商品（不包含已发货和直发部分）
    
	private Integer goodsNum;//产品数量
	
	private Integer skuNum;//sku数量
	
	private Integer brandNum;//品牌数量
	
	private String starttime;// 开始时间

	private String endtime;// 结束时间
	
	private Long starttimeLong;//

	private Long endtimeLong;//
	
	private Integer saleAfterNum;//销售退货数量
	
	private Integer buyAfterNum;//采购退货数量
	
	private Integer needBuyNum;//需采数量
	
	private Integer onWayNum;//在途数量

	public String getAssignmentManagerId() {
		return assignmentManagerId;
	}

	public void setAssignmentManagerId(String assignmentManagerId) {
		this.assignmentManagerId = assignmentManagerId;
	}

	public String getAssignmentAssistantId() {
		return assignmentAssistantId;
	}

	public void setAssignmentAssistantId(String assignmentAssistantId) {
		this.assignmentAssistantId = assignmentAssistantId;
	}

	//归属人
	private String assignmentManagerId;
	private  String assignmentAssistantId;




	
	public Integer getOnWayNum() {
		return onWayNum;
	}

	public void setOnWayNum(Integer onWayNum) {
		this.onWayNum = onWayNum;
	}
	
	public Integer getNeedBuyNum() {
		return needBuyNum;
	}

	public void setNeedBuyNum(Integer needBuyNum) {
		this.needBuyNum = needBuyNum;
	}

	public Integer getSaleAfterNum() {
		return saleAfterNum;
	}

	public void setSaleAfterNum(Integer saleAfterNum) {
		this.saleAfterNum = saleAfterNum;
	}

	public Integer getBuyAfterNum() {
		return buyAfterNum;
	}

	public void setBuyAfterNum(Integer buyAfterNum) {
		this.buyAfterNum = buyAfterNum;
	}

	private List<User> userList;
    
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getCanUseGoodsStock() {
		return canUseGoodsStock;
	}

	public void setCanUseGoodsStock(Integer canUseGoodsStock) {
		this.canUseGoodsStock = canUseGoodsStock;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getOptUserName() {
		return optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}

	public Integer getNeedCnt() {
		return needCnt;
	}

	public void setNeedCnt(Integer needCnt) {
		this.needCnt = needCnt;
	}

	public List<SaleorderVo> getSaleorderVoList() {
		return saleorderVoList;
	}

	public void setSaleorderVoList(List<SaleorderVo> saleorderVoList) {
		this.saleorderVoList = saleorderVoList;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}

	public Integer getProOrgtId() {
		return proOrgtId;
	}

	public void setProOrgtId(Integer proOrgtId) {
		this.proOrgtId = proOrgtId;
	}

	public Integer getProUserId() {
		return proUserId;
	}

	public void setProUserId(Integer proUserId) {
		this.proUserId = proUserId;
	}

	public Integer getQuoteorderId() {
		return quoteorderId;
	}

	public void setQuoteorderId(Integer quoteorderId) {
		this.quoteorderId = quoteorderId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(Integer purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

//	public Integer getLockedStatus() {
//		return lockedStatus;
//	}
//
//	public void setLockedStatus(Integer lockedStatus) {
//		this.lockedStatus = lockedStatus;
//	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Long getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Long invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
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

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Integer getHaveAccountPeriod() {
		return haveAccountPeriod;
	}

	public void setHaveAccountPeriod(Integer haveAccountPeriod) {
		this.haveAccountPeriod = haveAccountPeriod;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public String getTraderContactName() {
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName) {
		this.traderContactName = traderContactName;
	}

	public String getTraderContactMobile() {
		return traderContactMobile;
	}

	public void setTraderContactMobile(String traderContactMobile) {
		this.traderContactMobile = traderContactMobile;
	}

	public String getTraderContactTelephone() {
		return traderContactTelephone;
	}

	public void setTraderContactTelephone(String traderContactTelephone) {
		this.traderContactTelephone = traderContactTelephone;
	}

	public Integer getTraderAddressId() {
		return traderAddressId;
	}

	public void setTraderAddressId(Integer traderAddressId) {
		this.traderAddressId = traderAddressId;
	}

	public String getTraderAddress() {
		return traderAddress;
	}

	public void setTraderAddress(String traderAddress) {
		this.traderAddress = traderAddress;
	}

	public String getTraderComments() {
		return traderComments;
	}

	public void setTraderComments(String traderComments) {
		this.traderComments = traderComments;
	}

	public Integer getTakeTraderId() {
		return takeTraderId;
	}

	public void setTakeTraderId(Integer takeTraderId) {
		this.takeTraderId = takeTraderId;
	}

	public String getTakeTraderName() {
		return takeTraderName;
	}

	public void setTakeTraderName(String takeTraderName) {
		this.takeTraderName = takeTraderName==null ? null :takeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getTakeTraderContactId() {
		return takeTraderContactId;
	}

	public void setTakeTraderContactId(Integer takeTraderContactId) {
		this.takeTraderContactId = takeTraderContactId;
	}

	public String getTakeTraderContactName() {
		return takeTraderContactName;
	}

	public void setTakeTraderContactName(String takeTraderContactName) {
		this.takeTraderContactName = takeTraderContactName;
	}

	public String getTakeTraderContactMobile() {
		return takeTraderContactMobile;
	}

	public void setTakeTraderContactMobile(String takeTraderContactMobile) {
		this.takeTraderContactMobile = takeTraderContactMobile;
	}

	public String getTakeTraderContactTelephone() {
		return takeTraderContactTelephone;
	}

	public void setTakeTraderContactTelephone(String takeTraderContactTelephone) {
		this.takeTraderContactTelephone = takeTraderContactTelephone;
	}

	public Integer getTakeTraderAddressId() {
		return takeTraderAddressId;
	}

	public void setTakeTraderAddressId(Integer takeTraderAddressId) {
		this.takeTraderAddressId = takeTraderAddressId;
	}

	public String getTakeTraderAddress() {
		return takeTraderAddress;
	}

	public void setTakeTraderAddress(String takeTraderAddress) {
		this.takeTraderAddress = takeTraderAddress;
	}

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}

	public Integer getInvoiceTraderId() {
		return invoiceTraderId;
	}

	public void setInvoiceTraderId(Integer invoiceTraderId) {
		this.invoiceTraderId = invoiceTraderId;
	}

	public String getInvoiceTraderName() {
		return invoiceTraderName;
	}

	public void setInvoiceTraderName(String invoiceTraderName) {
		this.invoiceTraderName = invoiceTraderName==null ? null :invoiceTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getInvoiceTraderContactId() {
		return invoiceTraderContactId;
	}

	public void setInvoiceTraderContactId(Integer invoiceTraderContactId) {
		this.invoiceTraderContactId = invoiceTraderContactId;
	}

	public String getInvoiceTraderContactName() {
		return invoiceTraderContactName;
	}

	public void setInvoiceTraderContactName(String invoiceTraderContactName) {
		this.invoiceTraderContactName = invoiceTraderContactName;
	}

	public String getInvoiceTraderContactMobile() {
		return invoiceTraderContactMobile;
	}

	public void setInvoiceTraderContactMobile(String invoiceTraderContactMobile) {
		this.invoiceTraderContactMobile = invoiceTraderContactMobile;
	}

	public String getInvoiceTraderContactTelephone() {
		return invoiceTraderContactTelephone;
	}

	public void setInvoiceTraderContactTelephone(String invoiceTraderContactTelephone) {
		this.invoiceTraderContactTelephone = invoiceTraderContactTelephone;
	}

	public Integer getInvoiceTraderAddressId() {
		return invoiceTraderAddressId;
	}

	public void setInvoiceTraderAddressId(Integer invoiceTraderAddressId) {
		this.invoiceTraderAddressId = invoiceTraderAddressId;
	}

	public String getInvoiceTraderAddress() {
		return invoiceTraderAddress;
	}

	public void setInvoiceTraderAddress(String invoiceTraderAddress) {
		this.invoiceTraderAddress = invoiceTraderAddress;
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

	public Integer getTerminalTraderId() {
		return terminalTraderId;
	}

	public void setTerminalTraderId(Integer terminalTraderId) {
		this.terminalTraderId = terminalTraderId;
	}

	public String getTerminalTraderName() {
		return terminalTraderName;
	}

	public void setTerminalTraderName(String terminalTraderName) {
		this.terminalTraderName = terminalTraderName == null ? null : terminalTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getTerminalTraderType() {
		return terminalTraderType;
	}

	public void setTerminalTraderType(Integer terminalTraderType) {
		this.terminalTraderType = terminalTraderType;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getFreightDescription() {
		return freightDescription;
	}

	public void setFreightDescription(Integer freightDescription) {
		this.freightDescription = freightDescription;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getPrepaidAmount() {
		return prepaidAmount;
	}

	public void setPrepaidAmount(BigDecimal prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}

	public BigDecimal getAccountPeriodAmount() {
		return accountPeriodAmount;
	}

	public void setAccountPeriodAmount(BigDecimal accountPeriodAmount) {
		this.accountPeriodAmount = accountPeriodAmount;
	}

	public Integer getLogisticsCollection() {
		return logisticsCollection;
	}

	public void setLogisticsCollection(Integer logisticsCollection) {
		this.logisticsCollection = logisticsCollection;
	}

	public BigDecimal getRetainageAmount() {
		return retainageAmount;
	}

	public void setRetainageAmount(BigDecimal retainageAmount) {
		this.retainageAmount = retainageAmount;
	}

	public Integer getRetainageAmountMonth() {
		return retainageAmountMonth;
	}

	public void setRetainageAmountMonth(Integer retainageAmountMonth) {
		this.retainageAmountMonth = retainageAmountMonth;
	}

	public String getPaymentComments() {
		return paymentComments;
	}

	public void setPaymentComments(String paymentComments) {
		this.paymentComments = paymentComments;
	}

	public String getAdditionalClause() {
		return additionalClause;
	}

	public void setAdditionalClause(String additionalClause) {
		this.additionalClause = additionalClause;
	}

	public String getLogisticsComments() {
		return logisticsComments;
	}

	public void setLogisticsComments(String logisticsComments) {
		this.logisticsComments = logisticsComments;
	}

	public String getFinanceComments() {
		return financeComments;
	}

	public void setFinanceComments(String financeComments) {
		this.financeComments = financeComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getInvoiceComments() {
		return invoiceComments;
	}

	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public String getSupplierClause() {
		return supplierClause;
	}

	public void setSupplierClause(String supplierClause) {
		this.supplierClause = supplierClause;
	}

	public Integer getHaveAdvancePurchase() {
		return haveAdvancePurchase;
	}

	public void setHaveAdvancePurchase(Integer haveAdvancePurchase) {
		this.haveAdvancePurchase = haveAdvancePurchase;
	}

	public Integer getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(Integer isUrgent) {
		this.isUrgent = isUrgent;
	}

	public BigDecimal getUrgentAmount() {
		return urgentAmount;
	}

	public void setUrgentAmount(BigDecimal urgentAmount) {
		this.urgentAmount = urgentAmount;
	}

	public Integer getHaveCommunicate() {
		return haveCommunicate;
	}

	public void setHaveCommunicate(Integer haveCommunicate) {
		this.haveCommunicate = haveCommunicate;
	}

	public String getPrepareComments() {
		return prepareComments;
	}

	public void setPrepareComments(String prepareComments) {
		this.prepareComments = prepareComments;
	}

	public String getMarketingPlan() {
		return marketingPlan;
	}

	public void setMarketingPlan(String marketingPlan) {
		this.marketingPlan = marketingPlan;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getTraderArea() {
		return traderArea;
	}

	public void setTraderArea(String traderArea) {
		this.traderArea = traderArea;
	}

	public String getTakeTraderArea() {
		return takeTraderArea;
	}

	public void setTakeTraderArea(String takeTraderArea) {
		this.takeTraderArea = takeTraderArea;
	}

	public String getInvoiceTraderArea() {
		return invoiceTraderArea;
	}

	public void setInvoiceTraderArea(String invoiceTraderArea) {
		this.invoiceTraderArea = invoiceTraderArea;
	}


	public String getGoodsLevelName() {
		return goodsLevelName;
	}

	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public List<WarehouseGoodsStatusVo> getGoodsStockList() {
		return goodsStockList;
	}

	public void setGoodsStockList(List<WarehouseGoodsStatusVo> goodsStockList) {
		this.goodsStockList = goodsStockList;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getIgnoreName() {
		return ignoreName;
	}

	public void setIgnoreName(String ignoreName) {
		this.ignoreName = ignoreName;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

//	public Integer getBuyNum() {
//		return buyNum;
//	}
//
//	public void setBuyNum(Integer buyNum) {
//		this.buyNum = buyNum;
//	}

	public Integer getBuyorderGoodsId() {
		return buyorderGoodsId;
	}

	public void setBuyorderGoodsId(Integer buyorderGoodsId) {
		this.buyorderGoodsId = buyorderGoodsId;
	}

	public Integer getSaleBuyNum() {
		return saleBuyNum;
	}

	public void setSaleBuyNum(Integer saleBuyNum) {
		this.saleBuyNum = saleBuyNum;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

	public BigDecimal getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(BigDecimal invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public Integer getAfterSaleUpLimitNum() {
		return afterSaleUpLimitNum;
	}

	public void setAfterSaleUpLimitNum(Integer afterSaleUpLimitNum) {
		this.afterSaleUpLimitNum = afterSaleUpLimitNum;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public Integer getBrandNum() {
		return brandNum;
	}

	public void setBrandNum(Integer brandNum) {
		this.brandNum = brandNum;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Long getStarttimeLong() {
		return starttimeLong;
	}

	public void setStarttimeLong(Long starttimeLong) {
		this.starttimeLong = starttimeLong;
	}

	public Long getEndtimeLong() {
		return endtimeLong;
	}

	public void setEndtimeLong(Long endtimeLong) {
		this.endtimeLong = endtimeLong;
	}

}
