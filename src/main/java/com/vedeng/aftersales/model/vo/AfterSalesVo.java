package com.vedeng.aftersales.model.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.authorization.model.User;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.PayApply;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.model.TraderContact;


/**
 * <b>Description:</b><br> 售后订单数据传输类
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.aftersales.model.vo
 * <br><b>ClassName:</b> AfterSalesVo
 * <br><b>Date:</b> 2017年10月9日 上午11:21:00
 */
public class AfterSalesVo extends AfterSales{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer companyId;//归属公司id

	private String typeName;// 业务类型名称

	private Integer timeType;// 时间类型：1-申请时间；2-生效时间

	private Long searchStartTime;// 搜索开始时间

	private Long searchEndTime;// 搜索结束时间
	
	private String starttime;// 搜索开始时间

	private String endtime;// 搜索结束时间
	
	private List<Integer> serviceUserIdList;//归属售后人员集合
	
	private String creatorName;//创建人
	
	private String serviceUserName;//售后人员
	
	private Integer isView;//列表页是否能查看详情：1-是；0-否
	
	private Integer reason;//售后原因
	
	private String comments;//详情说明
	
	private Integer traderContactId;//联系人id
	
	private Integer refund;//款项退还
	
	private String [] afterSalesNum;//退货数量拼接字符串（saleorderGoodsId+num+deliveryDirect）逗号隔开
	
	private String [] attachName;//附件名称，逗号隔开
	
	private String [] attachUri;//附件uri,逗号隔开
	
	private String domain;//域名
	
	private String takeMsg;//收货信息（traderAddressI|areaId|tav.area|address）
	
	private Integer province;//省
	
	private Integer city;//市
	
	private Integer zone;//区
	
	private String address;//详细地址
	
	private String area;//地区
	
	private String [] invoiceIds;//退票id集合
	
	private String reasonName;//售后原因名称
    
    //以下为售后详情类---开始
    private Integer afterSalesDetailId;
    
    private String traderName;//客户名称
    
    private String goodsName;//产品名称
    
    private String model;//型号
    
    private String brandName,unitName;// 品牌名称
    
    private String materialCode;//物料编码
    
    private BigDecimal serviceAmount;
    
    private BigDecimal paymentAmount;
    
    private Integer purchaseStatus;

    private Integer invoiceType;

    private Integer isSendInvoice;
    
    private List<AfterSalesGoodsVo> afterSalesGoodsList;// 订单下的商品列表
    
    private List<AfterSalesGoods> thhGoodsList;//退换货产品信息
    
    private List<AfterSalesDetailVo> afterSalesDetailVoList;//退款信息
    
    private List<AfterSalesRecordVo> afterSalesRecordVoList;//售后内容
    
    private List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList;//出库记录
    
    private List<AfterSalesGoodsVo> afterReturnGoodsInStorageList;//退换货入库记录

    private List<AfterSalesGoodsVo> afterReturnGoodsOutStorageList;//退换货库记录
    
    private List<WarehouseGoodsOperateLog> afterReturnInstockList;//售后退换货入库记录
    
    private List<WarehouseGoodsOperateLog> afterReturnOutstockList;//售后换货出库记录
    
    private List<Express> expresseList;//物流信息
    
    private List<AfterSalesInvoiceVo> afterSalesInvoiceVoList;//退票信息
    
    private List<Invoice> afterReturnInvoiceVoList;//已退票信息
    
    private List<AfterSalesInvoiceVo> afterOpenInvoiceList;//售后开票记录-发票记录
    
    private List<AfterSalesInvoiceVo> afterInInvoiceList;//售后录票记录-发票记录
    
    private List<CapitalBill> afterCapitalBillList;//售后资金-交易记录
    
    private List<Attachment> afterInvoiceAttachmentList;//售后附件信息记录
    
    private List<Attachment> afterContractAttachmentList;//售后合同回传附件
    
    private List<AfterSalesInstallstionVo> afterSalesInstallstionVoList;//售后关联的工程师
    
    private List<AfterSalesInstallstion> safetyList;//安调信息
    
    private List<EngineerVo> engineerVoList;//工程师列表
    
    private List<PayApply> afterPayApplyList;//付款申请（财务-售后-安调）
    
    private List<TraderContact> traderContactList;//第三方关联客户的联系人列表
    
    
    private Integer thGoodsId;//商品库中退换货商品的id

    private String traderContactName;

    private String traderContactMobile;

    private String traderContactTelephone;

    private Integer areaId;

    private Integer addressId;

    private BigDecimal refundAmount;

    private BigDecimal refundFee;

    private BigDecimal realRefundAmount;

    private Integer refundAmountStatus;
    
    private BigDecimal periodAmount;//订单的账期金额
    
    private BigDecimal haveRefundAmount;//已退款金额

    private Integer traderSubject;
    
    private Integer traderMode;//新增字段-交易方式2018-1-31

    private String payee;

    private String bank;

    private String bankCode;

    private String bankAccount;
    
    /******2018-4-16 新增*******/
    private Integer invoiceTraderId;

    private String invoiceTraderName;

    private Integer invoiceTraderContactId;

    private String invoiceTraderContactName;

    private String invoiceTraderContactMobile;

    private String invoiceTraderContactTelephone;

    private Integer invoiceTraderAddressId;
    
    private String invoiceTraderArea;

    private String invoiceTraderAddress;
    
    private String invoiceComments;
    /******2018-4-16 新增**结束*****/
    //售后详情类---结束
    private Integer paymentStatus;//付款状态
    
    private Integer deliveryStatus;//发货状态
    
  //  private Integer invoiceStatus;//开票状态
    
    private Integer arrivalStatus;//收货状态
    
    private BigDecimal totalAmount;//订单总额
    
    private Integer orgId;//部门
    
    private String orgName;//部门名称

    private Integer userId;//归属销售
    
    private String userName;//归属销售名称
    
    private Integer saleorderStatus;//销售订单状态
    
    private Long saleorderValidTime;//销售订单生效时间
    
    private Integer traderId;
    
    private Integer customerNature;//客户性质
    
    private Integer customerLevel;// 客户等级
    
	private Integer orderCount;// 交易次数
	
	private BigDecimal orderTotalAmount;// 交易金额
	
	private Long lastOrderTime;//上次交易时间
	
	private List<Attachment> attachmentList;//关联的附件列表
	
	private List<User> userList;//用户信息列表
	
	private Integer afterSalesGoodsId;//编辑退换货手续费用
	
	private BigDecimal payAmount;//订单已付款金额
	
	private String name;//工程师的名称
	
	private String searchName;//搜索工程师名称
	
    private String sku;
    
    private String sku2;
    
    private Integer saleorderId;
	
	private String takeTraderName;
	
	private String takeTraderAddress;
	
    private Integer deliveryType;
    
    private String takeTraderArea;
    
    private Integer logisticsId;
    
    private Integer freightDescription;
    
    private String logisticsComments;
    
    private Integer takeTraderContactId;
    
    private String takeTraderContactName;
    
    private String takeTraderContactMobile;
    
    private String isOut = "0";// 是否关联出库日志表1；是0否
    
    private BigDecimal amount;//交易者账户余额
    
    private String eFlag = "0"; // 是否是查询快递出库的订单下的商品0：否，1：是
    
    private Integer isIn = 0; //判断是否是售后入库
    
    private Integer businessType;//业务类型
    
    private Integer goodsId;//商品id
    
    private BigDecimal capitalTotalAmount;//售后流水：销售使用余额已经支付金额
    
    private BigDecimal traderAmount;//客户余额
    
    private String payer;//付款方
    
    private Integer verifiesType;//审核类型
    
    private List<String> verifyUsernameList;//当前审核人
    
    private String addTimeStr;//
    
    private String validTimeStr;//
    
    private BigDecimal payPeriodAmount;//偿还帐期
    
    private Integer isNormal = 0;//是否筛选正常出库产品
    
    private Integer orderNum;//售后订单数量
    
    private BigDecimal orderPrice;
    
    private Integer closeStatus;//售后订单的关闭状态：-货款票状态未改变---1-可以关闭；2-不可以
    
    private Integer isCanApplyInvoice;//是否能申请开票0-不可以；1-可以；
    
    private Integer isModifyServiceAmount;//是否能修改售后服务费0-不可以；1-可以
    private BigDecimal PreAnnealing;//订单的预退金额
  //售后税务
    private String regAddress;//注册地址
    
    private String regTel;//注册电话
    
    private String taxNum;//税务登记号
    
    private Integer goodsType;//订单中产品类型（0未维护，1 只有设备,2 只有试剂,3 又有试剂又有设备）
    
    private Integer verifyType;//审核类型（0 关闭 1完成）
    
    private Integer receivePaymentStatus;//收款状态
    
    private Long receivePaymentTime;//收款时间
    
    private Integer exchangeReturnNum;//已退回数量
    
    private Integer exchangeDeliverNum;//已重发数量
    
    private Integer canUseGoodsStock;//可用库存
    
    private Integer afterGoodsNum;//换货数量
    
    private Integer goodsStockNum;//库存量
	private Integer orderType; //订单类型
	private String currentAuditName;//当前审核人


	private Integer afterSalesType;//业务类型
	private Integer optUserId;


	private Date afterSalesWixTime;//售后单完结时间

	private String searchStartDateTime;//查询完结时间字段
	private String searchEndDateTime;

	private String mobileNumber;//手机号
    private List<Integer> userIdList;//用户id



	public Integer getGoodsStockNum() {
		return goodsStockNum;
	}

	public void setGoodsStockNum(Integer goodsStockNum) {
		this.goodsStockNum = goodsStockNum;
	}

	public Integer getAfterGoodsNum() {
		return afterGoodsNum;
	}

	public void setAfterGoodsNum(Integer afterGoodsNum) {
		this.afterGoodsNum = afterGoodsNum;
	}

	public Integer getCanUseGoodsStock() {
		return canUseGoodsStock;
	}

	public void setCanUseGoodsStock(Integer canUseGoodsStock) {
		this.canUseGoodsStock = canUseGoodsStock;
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

	public String getSku2() {
		return sku2;
	}

	public void setSku2(String sku2) {
		this.sku2 = sku2;
	}

	public Integer getReceivePaymentStatus() {
		return receivePaymentStatus;
	}

	public void setReceivePaymentStatus(Integer receivePaymentStatus) {
		this.receivePaymentStatus = receivePaymentStatus;
	}

	public Long getReceivePaymentTime() {
		return receivePaymentTime;
	}

	public void setReceivePaymentTime(Long receivePaymentTime) {
		this.receivePaymentTime = receivePaymentTime;
	}

	public List<AfterSalesInvoiceVo> getAfterInInvoiceList() {
		return afterInInvoiceList;
	}

	public void setAfterInInvoiceList(List<AfterSalesInvoiceVo> afterInInvoiceList) {
		this.afterInInvoiceList = afterInInvoiceList;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getRegTel() {
		return regTel;
	}

	public void setRegTel(String regTel) {
		this.regTel = regTel;
	}

	public String getTaxNum() {
		return taxNum;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public List<Invoice> getAfterReturnInvoiceVoList() {
		return afterReturnInvoiceVoList;
	}

	public void setAfterReturnInvoiceVoList(List<Invoice> afterReturnInvoiceVoList) {
		this.afterReturnInvoiceVoList = afterReturnInvoiceVoList;
	}

	public BigDecimal getPreAnnealing() {
		return PreAnnealing;
	}

	public void setPreAnnealing(BigDecimal preAnnealing) {
		PreAnnealing = preAnnealing;
	}
    
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getIsNormal() {
		return isNormal;
	}

	public void setIsNormal(Integer isNormal) {
		this.isNormal = isNormal;
	}

	public BigDecimal getPayPeriodAmount() {
		return payPeriodAmount;
	}

	public void setPayPeriodAmount(BigDecimal payPeriodAmount) {
		this.payPeriodAmount = payPeriodAmount;
	}
    
	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer == null ? null : payer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public BigDecimal getTraderAmount() {
		return traderAmount;
	}

	public void setTraderAmount(BigDecimal traderAmount) {
		this.traderAmount = traderAmount;
	}
    
	public BigDecimal getCapitalTotalAmount() {
		return capitalTotalAmount;
	}

	public void setCapitalTotalAmount(BigDecimal capitalTotalAmount) {
		this.capitalTotalAmount = capitalTotalAmount;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getIsIn() {
		return isIn;
	}

	public void setIsIn(Integer isIn) {
		this.isIn = isIn;
	}

	public String getIsOut() {
		return isOut;
	}
    
	public String geteFlag() {
		return eFlag;
	}

	public void seteFlag(String eFlag) {
		this.eFlag = eFlag;
	}

	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}

	public String getTakeTraderContactTelephone() {
		return takeTraderContactTelephone;
	}

	public void setTakeTraderContactTelephone(String takeTraderContactTelephone) {
		this.takeTraderContactTelephone = takeTraderContactTelephone;
	}

	private String takeTraderContactTelephone;
    
	public String getTakeTraderContactMobile() {
		return takeTraderContactMobile;
	}

	public void setTakeTraderContactMobile(String takeTraderContactMobile) {
		this.takeTraderContactMobile = takeTraderContactMobile;
	}
    
	public String getTakeTraderContactName() {
		return takeTraderContactName;
	}

	public void setTakeTraderContactName(String takeTraderContactName) {
		this.takeTraderContactName = takeTraderContactName;
	}

	public Integer getTakeTraderContactId() {
		return takeTraderContactId;
	}

	public void setTakeTraderContactId(Integer takeTraderContactId) {
		this.takeTraderContactId = takeTraderContactId;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public String getTakeTraderName() {
		return takeTraderName;
	}

	public void setTakeTraderName(String takeTraderName) {
		this.takeTraderName = takeTraderName==null ? null :takeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getTakeTraderAddress() {
		return takeTraderAddress;
	}

	public void setTakeTraderAddress(String takeTraderAddress) {
		this.takeTraderAddress = takeTraderAddress;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getTakeTraderArea() {
		return takeTraderArea;
	}

	public void setTakeTraderArea(String takeTraderArea) {
		this.takeTraderArea = takeTraderArea;
	}

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	public Integer getFreightDescription() {
		return freightDescription;
	}

	public void setFreightDescription(Integer freightDescription) {
		this.freightDescription = freightDescription;
	}

	public String getLogisticsComments() {
		return logisticsComments;
	}

	public void setLogisticsComments(String logisticsComments) {
		this.logisticsComments = logisticsComments;
	}
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	private Integer grade;//供应商级别

    private Integer buyorderStatus;//采购订单状态
    
    private Long buyorderValidTime;//采购订单生效时间
	
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(Integer purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public List<PayApply> getAfterPayApplyList() {
		return afterPayApplyList;
	}

	public void setAfterPayApplyList(List<PayApply> afterPayApplyList) {
		this.afterPayApplyList = afterPayApplyList;
	}

	public List<Attachment> getAfterContractAttachmentList() {
		return afterContractAttachmentList;
	}

	public void setAfterContractAttachmentList(List<Attachment> afterContractAttachmentList) {
		this.afterContractAttachmentList = afterContractAttachmentList;
	}

	public List<Attachment> getAfterInvoiceAttachmentList() {
		return afterInvoiceAttachmentList;
	}

	public void setAfterInvoiceAttachmentList(List<Attachment> afterInvoiceAttachmentList) {
		this.afterInvoiceAttachmentList = afterInvoiceAttachmentList;
	}

	public List<AfterSalesInvoiceVo> getAfterOpenInvoiceList() {
		return afterOpenInvoiceList;
	}

	public void setAfterOpenInvoiceList(List<AfterSalesInvoiceVo> afterOpenInvoiceList) {
		this.afterOpenInvoiceList = afterOpenInvoiceList;
	}

	public List<AfterSalesGoodsVo> getAfterReturnGoodsInStorageList() {
		return afterReturnGoodsInStorageList;
	}

	public void setAfterReturnGoodsInStorageList(List<AfterSalesGoodsVo> afterReturnGoodsInStorageList) {
		this.afterReturnGoodsInStorageList = afterReturnGoodsInStorageList;
	}

	public List<AfterSalesGoodsVo> getAfterReturnGoodsOutStorageList() {
		return afterReturnGoodsOutStorageList;
	}

	public void setAfterReturnGoodsOutStorageList(List<AfterSalesGoodsVo> afterReturnGoodsOutStorageList) {
		this.afterReturnGoodsOutStorageList = afterReturnGoodsOutStorageList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	private Integer traderType;//交易者类型 1客户 2供应商
	
	public List<AfterSalesGoodsVo> getAfterSalesGoodsList() {
		return afterSalesGoodsList;
	}

	public void setAfterSalesGoodsList(List<AfterSalesGoodsVo> afterSalesGoodsList) {
		this.afterSalesGoodsList = afterSalesGoodsList;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Long getSearchStartTime() {
		return searchStartTime;
	}

	public void setSearchStartTime(Long searchStartTime) {
		this.searchStartTime = searchStartTime;
	}

	public Long getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(Long searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	public List<Integer> getServiceUserIdList() {
		return serviceUserIdList;
	}

	public void setServiceUserIdList(List<Integer> serviceUserIdList) {
		this.serviceUserIdList = serviceUserIdList;
	}

	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public Integer getRefund() {
		return refund;
	}

	public void setRefund(Integer refund) {
		this.refund = refund;
	}

	public String[] getAfterSalesNum() {
		return afterSalesNum;
	}

	public void setAfterSalesNum(String[] afterSalesNum) {
		this.afterSalesNum = afterSalesNum;
	}

	public String[] getAttachName() {
		return attachName;
	}

	public void setAttachName(String[] attachName) {
		this.attachName = attachName;
	}

	public String[] getAttachUri() {
		return attachUri;
	}

	public void setAttachUri(String[] attachUri) {
		this.attachUri = attachUri;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTakeMsg() {
		return takeMsg;
	}

	public void setTakeMsg(String takeMsg) {
		this.takeMsg = takeMsg;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getZone() {
		return zone;
	}

	public void setZone(Integer zone) {
		this.zone = zone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String[] getInvoiceIds() {
		return invoiceIds;
	}

	public void setInvoiceIds(String[] invoiceIds) {
		this.invoiceIds = invoiceIds;
	}

	public Integer getTraderSubject() {
		return traderSubject;
	}

	public void setTraderSubject(Integer traderSubject) {
		this.traderSubject = traderSubject;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee == null ? null : payee.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public Integer getAfterSalesDetailId() {
		return afterSalesDetailId;
	}

	public void setAfterSalesDetailId(Integer afterSalesDetailId) {
		this.afterSalesDetailId = afterSalesDetailId;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public BigDecimal getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}

	public BigDecimal getRealRefundAmount() {
		return realRefundAmount;
	}

	public void setRealRefundAmount(BigDecimal realRefundAmount) {
		this.realRefundAmount = realRefundAmount;
	}

	public Integer getRefundAmountStatus() {
		return refundAmountStatus;
	}

	public void setRefundAmountStatus(Integer refundAmountStatus) {
		this.refundAmountStatus = refundAmountStatus;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

//	public Integer getInvoiceStatus() {
//		return invoiceStatus;
//	}
//
//	public void setInvoiceStatus(Integer invoiceStatus) {
//		this.invoiceStatus = invoiceStatus;
//	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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
	
	public Integer getSaleorderStatus() {
		return saleorderStatus;
	}

	public void setSaleorderStatus(Integer saleorderStatus) {
		this.saleorderStatus = saleorderStatus;
	}

	public Long getSaleorderValidTime() {
		return saleorderValidTime;
	}

	public void setSaleorderValidTime(Long saleorderValidTime) {
		this.saleorderValidTime = saleorderValidTime;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(Integer customerLevel) {
		this.customerLevel = customerLevel;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public Long getLastOrderTime() {
		return lastOrderTime;
	}

	public void setLastOrderTime(Long lastOrderTime) {
		this.lastOrderTime = lastOrderTime;
	}

	public Integer getThGoodsId() {
		return thGoodsId;
	}

	public void setThGoodsId(Integer thGoodsId) {
		this.thGoodsId = thGoodsId;
	}

	public List<AfterSalesGoods> getThhGoodsList() {
		return thhGoodsList;
	}

	public void setThhGoodsList(List<AfterSalesGoods> thhGoodsList) {
		this.thhGoodsList = thhGoodsList;
	}

	public List<AfterSalesInvoiceVo> getAfterSalesInvoiceVoList() {
		return afterSalesInvoiceVoList;
	}

	public void setAfterSalesInvoiceVoList(List<AfterSalesInvoiceVo> afterSalesInvoiceVoList) {
		this.afterSalesInvoiceVoList = afterSalesInvoiceVoList;
	}

	public List<AfterSalesDetailVo> getAfterSalesDetailVoList() {
		return afterSalesDetailVoList;
	}

	public void setAfterSalesDetailVoList(List<AfterSalesDetailVo> afterSalesDetailVoList) {
		this.afterSalesDetailVoList = afterSalesDetailVoList;
	}

	public List<AfterSalesRecordVo> getAfterSalesRecordVoList() {
		return afterSalesRecordVoList;
	}

	public void setAfterSalesRecordVoList(List<AfterSalesRecordVo> afterSalesRecordVoList) {
		this.afterSalesRecordVoList = afterSalesRecordVoList;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getServiceUserName() {
		return serviceUserName;
	}

	public void setServiceUserName(String serviceUserName) {
		this.serviceUserName = serviceUserName;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<WarehouseGoodsOperateLog> getWarehouseGoodsOperateLogList() {
		return warehouseGoodsOperateLogList;
	}

	public void setWarehouseGoodsOperateLogList(List<WarehouseGoodsOperateLog> warehouseGoodsOperateLogList) {
		this.warehouseGoodsOperateLogList = warehouseGoodsOperateLogList;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public Integer getAfterSalesGoodsId() {
		return afterSalesGoodsId;
	}

	public void setAfterSalesGoodsId(Integer afterSalesGoodsId) {
		this.afterSalesGoodsId = afterSalesGoodsId;
	}

	public List<CapitalBill> getAfterCapitalBillList() {
		return afterCapitalBillList;
	}

	public void setAfterCapitalBillList(List<CapitalBill> afterCapitalBillList) {
		this.afterCapitalBillList = afterCapitalBillList;
	}

	public List<AfterSalesInstallstionVo> getAfterSalesInstallstionVoList() {
		return afterSalesInstallstionVoList;
	}

	public void setAfterSalesInstallstionVoList(List<AfterSalesInstallstionVo> afterSalesInstallstionVoList) {
		this.afterSalesInstallstionVoList = afterSalesInstallstionVoList;
	}

	public List<AfterSalesInstallstion> getSafetyList() {
		return safetyList;
	}

	public void setSafetyList(List<AfterSalesInstallstion> safetyList) {
		this.safetyList = safetyList;
	}

	public BigDecimal getServiceAmount() {
		return serviceAmount;
	}

	public void setServiceAmount(BigDecimal serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EngineerVo> getEngineerVoList() {
		return engineerVoList;
	}

	public void setEngineerVoList(List<EngineerVo> engineerVoList) {
		this.engineerVoList = engineerVoList;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getBuyorderStatus() {
		return buyorderStatus;
	}

	public void setBuyorderStatus(Integer buyorderStatus) {
		this.buyorderStatus = buyorderStatus;
	}

	public Long getBuyorderValidTime() {
		return buyorderValidTime;
	}

	public void setBuyorderValidTime(Long buyorderValidTime) {
		this.buyorderValidTime = buyorderValidTime;
	}

	public List<TraderContact> getTraderContactList() {
		return traderContactList;
	}

	public void setTraderContactList(List<TraderContact> traderContactList) {
		this.traderContactList = traderContactList;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	public BigDecimal getHaveRefundAmount() {
		return haveRefundAmount;
	}

	public void setHaveRefundAmount(BigDecimal haveRefundAmount) {
		this.haveRefundAmount = haveRefundAmount;
	}

	public List<WarehouseGoodsOperateLog> getAfterReturnInstockList() {
		return afterReturnInstockList;
	}

	public void setAfterReturnInstockList(List<WarehouseGoodsOperateLog> afterReturnInstockList) {
		this.afterReturnInstockList = afterReturnInstockList;
	}

	public List<WarehouseGoodsOperateLog> getAfterReturnOutstockList() {
		return afterReturnOutstockList;
	}

	public void setAfterReturnOutstockList(List<WarehouseGoodsOperateLog> afterReturnOutstockList) {
		this.afterReturnOutstockList = afterReturnOutstockList;
	}

	public List<Express> getExpresseList() {
		return expresseList;
	}

	public void setExpresseList(List<Express> expresseList) {
		this.expresseList = expresseList;
	}
	
	public Integer getVerifiesType() {
	    return verifiesType;
	}

	public void setVerifiesType(Integer verifiesType) {
	    this.verifiesType = verifiesType;
	}

	
	public List<String> getVerifyUsernameList() {
	    return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
	    this.verifyUsernameList = verifyUsernameList;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getTraderMode() {
		return traderMode;
	}

	public void setTraderMode(Integer traderMode) {
		this.traderMode = traderMode;
	}

	public Integer getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(Integer closeStatus) {
		this.closeStatus = closeStatus;
	}

	public Integer getIsCanApplyInvoice() {
		return isCanApplyInvoice;
	}

	public void setIsCanApplyInvoice(Integer isCanApplyInvoice) {
		this.isCanApplyInvoice = isCanApplyInvoice;
	}

	public Integer getIsModifyServiceAmount() {
		return isModifyServiceAmount;
	}

	public void setIsModifyServiceAmount(Integer isModifyServiceAmount) {
		this.isModifyServiceAmount = isModifyServiceAmount;
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

	public String getInvoiceTraderArea() {
		return invoiceTraderArea;
	}

	public void setInvoiceTraderArea(String invoiceTraderArea) {
		this.invoiceTraderArea = invoiceTraderArea;
	}

	public String getInvoiceTraderAddress() {
		return invoiceTraderAddress;
	}

	public void setInvoiceTraderAddress(String invoiceTraderAddress) {
		this.invoiceTraderAddress = invoiceTraderAddress;
	}

	public String getInvoiceComments() {
		return invoiceComments;
	}

	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getVerifyType() {
	    return verifyType;
	}

	public void setVerifyType(Integer verifyType) {
	    this.verifyType = verifyType;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getCurrentAuditName() {
		return currentAuditName;
	}

	public void setCurrentAuditName(String currentAuditName) {
		this.currentAuditName = currentAuditName;
	}

	public Integer getAfterSalesType() {
		return afterSalesType;
	}

	public void setAfterSalesType(Integer afterSalesType) {
		this.afterSalesType = afterSalesType;
	}

	public Date getAfterSalesWixTime() {
		return afterSalesWixTime;
	}

	public void setAfterSalesWixTime(Date afterSalesWixTime) {
		this.afterSalesWixTime = afterSalesWixTime;
	}

	public Integer getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(Integer optUserId) {
		this.optUserId = optUserId;
	}

	public String getSearchStartDateTime() {
		return searchStartDateTime;
	}

	public void setSearchStartDateTime(String searchStartDateTime) {
		this.searchStartDateTime = searchStartDateTime;
	}

	public String getSearchEndDateTime() {
		return searchEndDateTime;
	}

	public void setSearchEndDateTime(String searchEndDateTime) {
		this.searchEndDateTime = searchEndDateTime;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}


}
