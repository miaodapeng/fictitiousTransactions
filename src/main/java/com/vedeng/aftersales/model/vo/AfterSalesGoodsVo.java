package com.vedeng.aftersales.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.authorization.model.User;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.order.model.Buyorder;

public class AfterSalesGoodsVo extends AfterSalesGoods{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Integer> afterSalesIdList;//售后订单主键集合
	
	private Integer goodsStock;//库存数量  
	
	private Integer manageCategory;//管理类别级别
	
	private String materialCode;//物料编码
	
    private String goodsName;//产品名称
	
    private String traderName;//客户名称
    
    private String model;//型号
    
    private String brandName;// 品牌名称
    
    private String unitName;// 单位
    
    private Integer incnt;//入库数量
    
    private Integer outcnt;//出库数量
    
    private String sku;
    
    private Integer goodsHeadId;//产品负责人
    
    private String registrationNumber;//注册证号
    
    private String manageCategoryName;//管理类别名称
    
    private String purchaseRemind;//采购提醒
    
    private String packingList;//包装清单
    
    private String tos;//服务条款
    
    private Integer orderOccupy;//订单占用
    
    private Integer adjustableNum;//可调剂
    
    private BigDecimal saleorderPrice;//单价
    
    private Integer saleorderNum;
    
    private Integer nowNum;
    
    private Integer saleorderDeliveryDirect;
    
    private BigDecimal buyorderPrice;//单价
    
    private Integer buyorderNum;
    
    private Integer buyorderDeliveryDirect;
    
    private Integer deliveryNum;//已发货数量
    
    private Integer buyNum;//已采购数量
    
    private Integer receiveNum;//已收货数量
    
    //-----------退货入库记录------------------------------
    private Integer barcodeId;//公司条码ID
    
    private Integer barcode;//公司条码
    
    private String barcodeFactory;//产商条码
    
    private Long checkStatusTime;//入库效验时间
    
    private Integer checkStatusUser;//入库效验人员
    //------------------------------------------------------
    
    private BigDecimal orderPrice;//订单产品单价
    
    private BigDecimal orderNum;//订单产品数量
    
    private BigDecimal invoiceNum;//发票产品数量
    
    private BigDecimal invoiceAmount;//发票金额
    
    private Integer invoiceType;//发票票种
    
    private List<String> whList;//仓库名称列表
    
    private Integer pickCnt;//商品拣货个数
    
    private Integer totalNum;//可拣货商品总数

    private BigDecimal payApplyTotalAmount;//申请付款的已申请总额
    
    private BigDecimal payApplySum;//申请付款的已申请数量
    
    private List<WarehouseGoodsOperateLog> wlist;//商品入库详情列表
    
    private String isOut;//是否是出库1是0否
    
    private Integer eNum=0;//快递发货数
    
    private Integer type;//类型
    
    private Integer saleorderId;
    
    private String goodsHeader;//产品负责人
    
    private Integer buyorderId;

    private String buyorderNo;
    
    private Integer exchangeReturnNum;//换货已退回数量
    
    private Integer exchangeDeliverNum;//换货已重发数量
    
	private Integer categoryId;
	
	private List<User> userList;
	
    private Integer pCountAll=0;//自动分配拣货数量总数
    
    private Integer nowSalesNum;//当前销售产品的数量（减去已完结的售后数量）
    
    private Integer rknum;//销售售后要入库数
    
    private List<Buyorder> buyorderNos;//关联的采购单号，可能会关联到多个
    
    private Integer verifyStatus;
    
    private Integer goodsGoodType;//产品表中的产品类型

	private Integer isActionGoods;//是否为活动商品

	private Integer actionLockCount;//活动锁定库存

	private BigDecimal skuRefundAmount;//当前sku退款金额
	private BigDecimal OldSkuRefundAmount;//当前sku退款金额(在执行退款时，重新计算退款金额，如果有差异，则把值赋值到skuRefundAmount，这个用于存有变更的老值)
	private Integer fag;//用于区别退款金额的差异的标识
	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getGoodsGoodType() {
		return goodsGoodType;
	}

	public void setGoodsGoodType(Integer goodsGoodType) {
		this.goodsGoodType = goodsGoodType;
	}
    

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public List<Buyorder> getBuyorderNos() {
		return buyorderNos;
	}

	public void setBuyorderNos(List<Buyorder> buyorderNos) {
		this.buyorderNos = buyorderNos;
	}

	public Integer getRknum() {
		return rknum;
	}

	public void setRknum(Integer rknum) {
		this.rknum = rknum;
	}
    
	public Integer getOutcnt() {
		return outcnt;
	}

	public void setOutcnt(Integer outcnt) {
		this.outcnt = outcnt;
	}

	public Integer getpCountAll() {
		return pCountAll;
	}

	public void setpCountAll(Integer pCountAll) {
		this.pCountAll = pCountAll;
	}
    
    public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public Integer getNowNum() {
		return nowNum;
	}

	public void setNowNum(Integer nowNum) {
		this.nowNum = nowNum;
	}

	public String getGoodsHeader() {
		return goodsHeader;
	}

	public void setGoodsHeader(String goodsHeader) {
		this.goodsHeader = goodsHeader;
	}

	public Integer getBarcode() {
		return barcode;
	}

	public void setBarcode(Integer barcode) {
		this.barcode = barcode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    
	public Integer geteNum() {
		return eNum;
	}

	public void seteNum(Integer eNum) {
		this.eNum = eNum;
	}
    
    public String getIsOut() {
		return isOut;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}

	public List<WarehouseGoodsOperateLog> getWlist() {
		return wlist;
	}

	public void setWlist(List<WarehouseGoodsOperateLog> wlist) {
		this.wlist = wlist;
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

	public BigDecimal getBuyorderPrice() {
		return buyorderPrice;
	}

	public void setBuyorderPrice(BigDecimal buyorderPrice) {
		this.buyorderPrice = buyorderPrice;
	}

	public Integer getBuyorderNum() {
		return buyorderNum;
	}

	public void setBuyorderNum(Integer buyorderNum) {
		this.buyorderNum = buyorderNum;
	}

	public Integer getBuyorderDeliveryDirect() {
		return buyorderDeliveryDirect;
	}

	public void setBuyorderDeliveryDirect(Integer buyorderDeliveryDirect) {
		this.buyorderDeliveryDirect = buyorderDeliveryDirect;
	}

    private String storageAddress;//商品在库位置
    
	public String getStorageAddress() {
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}

	public List<String> getWhList() {
		return whList;
	}

	public void setWhList(List<String> whList) {
		this.whList = whList;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(BigDecimal orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(BigDecimal invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getTraderName() {
		return traderName;
	}

	public Integer getManageCategory() {
		return manageCategory;
	}

	public void setManageCategory(Integer manageCategory) {
		this.manageCategory = manageCategory;
	}

	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	public String getBarcodeFactory() {
		return barcodeFactory;
	}

	public void setBarcodeFactory(String barcodeFactory) {
		this.barcodeFactory = barcodeFactory;
	}

	public Long getCheckStatusTime() {
		return checkStatusTime;
	}

	public void setCheckStatusTime(Long checkStatusTime) {
		this.checkStatusTime = checkStatusTime;
	}

	public Integer getCheckStatusUser() {
		return checkStatusUser;
	}

	public void setCheckStatusUser(Integer checkStatusUser) {
		this.checkStatusUser = checkStatusUser;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getIncnt() {
		return incnt;
	}

	public void setIncnt(Integer incnt) {
		this.incnt = incnt;
	}


	public List<Integer> getAfterSalesIdList() {
		return afterSalesIdList;
	}

	public void setAfterSalesIdList(List<Integer> afterSalesIdList) {
		this.afterSalesIdList = afterSalesIdList;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getManageCategoryName() {
		return manageCategoryName;
	}

	public void setManageCategoryName(String manageCategoryName) {
		this.manageCategoryName = manageCategoryName;
	}

	public String getPurchaseRemind() {
		return purchaseRemind;
	}

	public void setPurchaseRemind(String purchaseRemind) {
		this.purchaseRemind = purchaseRemind;
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public Integer getOrderOccupy() {
		return orderOccupy;
	}

	public void setOrderOccupy(Integer orderOccupy) {
		this.orderOccupy = orderOccupy;
	}

	public Integer getAdjustableNum() {
		return adjustableNum;
	}

	public void setAdjustableNum(Integer adjustableNum) {
		this.adjustableNum = adjustableNum;
	}

	public BigDecimal getSaleorderPrice() {
		return saleorderPrice;
	}

	public void setSaleorderPrice(BigDecimal saleorderPrice) {
		this.saleorderPrice = saleorderPrice;
	}

	public Integer getSaleorderNum() {
		return saleorderNum;
	}

	public void setSaleorderNum(Integer saleorderNum) {
		this.saleorderNum = saleorderNum;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getSaleorderDeliveryDirect() {
		return saleorderDeliveryDirect;
	}

	public void setSaleorderDeliveryDirect(Integer saleorderDeliveryDirect) {
		this.saleorderDeliveryDirect = saleorderDeliveryDirect;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Integer getGoodsHeadId() {
		return goodsHeadId;
	}

	public void setGoodsHeadId(Integer goodsHeadId) {
		this.goodsHeadId = goodsHeadId;
	}

	public BigDecimal getPayApplyTotalAmount() {
		return payApplyTotalAmount;
	}

	public void setPayApplyTotalAmount(BigDecimal payApplyTotalAmount) {
		this.payApplyTotalAmount = payApplyTotalAmount;
	}

	public BigDecimal getPayApplySum() {
		return payApplySum;
	}

	public void setPayApplySum(BigDecimal payApplySum) {
		this.payApplySum = payApplySum;
	}

	public Integer getExchangeReturnNum() {
		return exchangeReturnNum;
	}

	public void setExchangeReturnNum(Integer exchangeReturnNum) {
		this.exchangeReturnNum = exchangeReturnNum;
	}

	public Integer getExchangeDeliverNum() {
		return exchangeDeliverNum;
	}

	public void setExchangeDeliverNum(Integer exchangeDeliverNum) {
		this.exchangeDeliverNum = exchangeDeliverNum;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getNowSalesNum() {
		return nowSalesNum;
	}

	public void setNowSalesNum(Integer nowSalesNum) {
		this.nowSalesNum = nowSalesNum;
	}

	public Integer getIsActionGoods() {
		return isActionGoods;
	}

	public void setIsActionGoods(Integer isActionGoods) {
		this.isActionGoods = isActionGoods;
	}
	@Override
	public BigDecimal getSkuRefundAmount() {
		return skuRefundAmount;
	}

	@Override
	public void setSkuRefundAmount(BigDecimal skuRefundAmount) {
		this.skuRefundAmount = skuRefundAmount;
	}

	public BigDecimal getOldSkuRefundAmount() {
		return OldSkuRefundAmount;
	}

	public void setOldSkuRefundAmount(BigDecimal oldSkuRefundAmount) {
		OldSkuRefundAmount = oldSkuRefundAmount;
	}

	public Integer getFag() {
		return fag;
	}

	public void setFag(Integer fag) {
		this.fag = fag;
	}

	public Integer getActionLockCount() {
		return actionLockCount;
	}

	public void setActionLockCount(Integer actionLockCount) {
		this.actionLockCount = actionLockCount;
	}
}