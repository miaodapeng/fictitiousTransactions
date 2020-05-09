package com.vedeng.order.model.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.Invoice;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.model.Buyorder;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.VerifiesRecord;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.vo.TraderAddressVo;

/**
 * <b>Description:</b><br> 采购订单vo
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.order.model.vo
 * <br><b>ClassName:</b> BuyorderVo
 * <br><b>Date:</b> 2017年7月11日 上午9:22:22
 */
public class BuyorderVo extends Buyorder implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
    private static final long serialVersionUID = 1L;
	
    private String createName;//创建人
    
    private String homePurchasing;//归属采购：供应商归属
	
    private String buyDepartmentName;//采购部门
	
    private String buyPerson;//采购员
	
    private String goodsName;//产品名称

    private String brandName;//品牌名称
    
    private String model;//商品型号
    
    private String sku;//订货号
    
    private String saleorderNo;//销售单号
    
    private Integer saleorderId;//此字段只有直发的时候用到
    
    private Integer isRemindTicket;//需催票：0-否；1-是
    
    private Integer isRemindMoney;//需催款：0-否；1-是
    
    private Integer isRemindGoods;//需催货：0-否；1-是
    
    private Integer searchDateType;//时间类型：1-创建时间，2-生效时间，3-付款时间，4-发货时间，5-收货时间，6-收票时间，7-审核时间
    
    private Long searchBegintime;//搜索开始时间
    
    private Long searchEndtime;//搜索结束时间
    
    private Integer reviewStatus;//审核状态
    
    private List<Integer> userIds;
    
    private Integer proOrgtId;//产品部门id
    
    private Integer proUserId;//产品负责人id

    private String proOrgName;//产品部门名称
    
    private String proUserName;//产品负责人名称
    
    private String saleorderGoodsIds;//销售订单产品的ids集合字符串
    
    private String goodsIds;//提交采购订单时，产品id集合字符串，多个逗号隔开

    private List<BuyorderGoodsVo> buyorderGoodsVoList;
    
    private List<Integer> saleGoodsIdList;//采购订单列表页按销售单号查询时，查询出所有关联的销售产品的id
    
    private List<VerifiesRecord> verifiesRecordList;//订单的审核记录列表
    
    private List<Attachment> attachmentList;//采购订单回传列表
    
    private List<WarehouseGoodsOperateLogVo> warehouseGoodsOperateLogVoList;//入库记录
    
    private List<Express> expressList;//快递单列表
    
    private List<CapitalBill> capitalBillList; //交易记录 
    
    private String logisticsName;//物流公司名称
    
    private String freightDes;//运费说明
    
    private String invoiceTypeStr;//收票种类
    
    private String materialCode;//物料编码
    
    private Integer buyorderSum;//订单中商品的总数
    
    private BigDecimal buyorderAmount;//订单的总额
    
    private Integer isIgnore;//生成采购订单时传，0代表待采购订单生成，1表示忽略列表生成
    
    private Integer allNum;//采购订单产品总数
    
    private Integer allArrivalNum;//采购订单总的到货数量
    
    private Integer isView;//列表页是否能查看详情：1-是；0-否
    
    private BigDecimal periodBalance;//供应商账期余额
    
    private Integer onWayNum;//在途数量
    
//    private String estimateArrivalTime;//预计到货时间
    
    private BigDecimal searchBeginAmount;//开始金额
    
    private BigDecimal searchEndAmount;//结束金额
    
    private List<BuyorderGoodsVo> bgvList;//售后订单查询使用
    
    private List<TraderAddress> tavList;//新增售后订单获取收货地址
    
    private List<TraderAddressVo> traderAddressVoList;//收货地址dto
    
    private BigDecimal paid;//已付款金额
    
    private String payee;//收款方名称
    
    private String bank;//开户银行
    
    private String bankCode;//开户行支付联行号

    private String bankAccount;//银行帐号
    
    private List<Invoice> invoiceList;//发票列表
    
    private List<TraderContact> tcList;//售后订单查询使用
    
    private BigDecimal paymentAmount;//已付款金额
    
    private BigDecimal periodAmount;//已付款金额
    
    private BigDecimal realAmount;//订单实际金额
    
    private BigDecimal lackAccountPeriodAmount;//应付账期额
    
    private BigDecimal invoiceAmount;//已收票总额
    
    private Integer isBuyorderTotal = 0;//采购订单是否统计相关总额
    
    private Integer times;//平均到货时间信息次数
    
    private BigDecimal totalDays;//平均到货时间信息总天数
    
    private Integer goodsId;
    
    private Integer periodDay;//帐期天数
    
    private BigDecimal periodSurplusAmount;//帐期剩余额度
    
    private String name;//联系人姓名
    
    private Integer sex;//性别
    
    private String fax;//传真

    private String mobile;//手机号
    
    private String email;//邮件
    
    private String shName;//采购单审核人
    
    private String taxNum;//税号
    
    private Integer isOverSettlementPrice;//是否超过结算价/1.02
    
	private String verifyUsername;//当前审核人
    
	private Integer verifyStatus;//审核状态
	
	private String paymentVerifyUsername;//付款当前审核人
    
	private Integer paymentVerifyStatus;//付款审核状态
	
	private List<Integer> categoryIdList;//
	
	private String sTakeTraderContactName;

    private String sTakeTraderContactMobile;

    private String sTakeTraderContactTelephone;

    private Integer sTakeTraderAddressId;

    private String sTakeTraderAddress;
    
    private String sTakeTraderArea;
    
    private Integer aftersaleCanClose;//采购发生售后后是否能关闭采购单；0-不可以；1-可以
    
    private Integer lackPeriod;//帐期未还0-否；1-是
    
    private Integer invoiceVerifyStatus;//采购路票申请审核状态
    
    private String userName;//当前采购单归属人
    
    private Integer bussinessId;//业务id（换货订单、样品外借订单）
    
    private Integer bussinessType=0;//业务类型：1销售入库 2销售出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库
    
    private Integer orderId;//售后单关联的销售/采购单id
    
    private String bussinessNo;//售后单号
    
    private BigDecimal paymentTotalAmount;//已付款总额（总计）
    
    private BigDecimal invoiceTotalAmount;//收票总额（总计）
    
    private List<Integer> buyorderIdList;//查询条件 
    
    private Integer buyorderGoodsCount;//采购详情中产品的条数（注：标序号的数量）
    
	private Integer searchUserId;//当前查询人的主键id,此字段只用于待采购的查询且没有查询意义，只是作为dbcenter数据缓存map的key值，查询完成就删除
	
	private Integer currentCount;//当前是第几次查询，默认从2开始
	
	private Integer isUpdateDeliveryDirect;//是否能修改直发：0-能；1-不能（采购关联多个销售单）；2-采购单关联的销售商品含普发
	
    private Integer saleTakeTraderId;

    private String saleTakeTraderName;

    private Integer saleTakeTraderContactId;

    private String saleTakeTraderContactName;

    private String saleTakeTraderContactMobile;

    private String saleTakeTraderContactTelephone;

    private Integer saleTakeTraderAddressId;

    private String saleTakeTraderAddress;
    
    private String saleTakeTraderArea;
    
    private BigDecimal occupyAmount;//余额占用
    
    private Long periodTime;
    
    private Long tkTime;
    
    private Long hxTime;

    // 当前操作人
    private String currentOperateUser;

	public void setCurrentOperateUser(String currentOperateUser) {
		this.currentOperateUser = currentOperateUser;
	}

	public String getCurrentOperateUser() {
		return currentOperateUser;
	}

	public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public BigDecimal getOccupyAmount() {
		return occupyAmount;
	}

	public void setOccupyAmount(BigDecimal occupyAmount) {
		this.occupyAmount = occupyAmount;
	}

	public Integer getIsUpdateDeliveryDirect() {
		return isUpdateDeliveryDirect;
	}

	public void setIsUpdateDeliveryDirect(Integer isUpdateDeliveryDirect) {
		this.isUpdateDeliveryDirect = isUpdateDeliveryDirect;
	}

	public Integer getSaleTakeTraderId() {
		return saleTakeTraderId;
	}

	public void setSaleTakeTraderId(Integer saleTakeTraderId) {
		this.saleTakeTraderId = saleTakeTraderId;
	}

	public String getSaleTakeTraderName() {
		return saleTakeTraderName;
	}

	public void setSaleTakeTraderName(String saleTakeTraderName) {
		this.saleTakeTraderName = saleTakeTraderName;
	}

	public Integer getSaleTakeTraderContactId() {
		return saleTakeTraderContactId;
	}

	public void setSaleTakeTraderContactId(Integer saleTakeTraderContactId) {
		this.saleTakeTraderContactId = saleTakeTraderContactId;
	}

	public String getSaleTakeTraderContactName() {
		return saleTakeTraderContactName;
	}

	public void setSaleTakeTraderContactName(String saleTakeTraderContactName) {
		this.saleTakeTraderContactName = saleTakeTraderContactName;
	}

	public String getSaleTakeTraderContactMobile() {
		return saleTakeTraderContactMobile;
	}

	public void setSaleTakeTraderContactMobile(String saleTakeTraderContactMobile) {
		this.saleTakeTraderContactMobile = saleTakeTraderContactMobile;
	}

	public String getSaleTakeTraderContactTelephone() {
		return saleTakeTraderContactTelephone;
	}

	public void setSaleTakeTraderContactTelephone(String saleTakeTraderContactTelephone) {
		this.saleTakeTraderContactTelephone = saleTakeTraderContactTelephone;
	}

	public Integer getSaleTakeTraderAddressId() {
		return saleTakeTraderAddressId;
	}

	public void setSaleTakeTraderAddressId(Integer saleTakeTraderAddressId) {
		this.saleTakeTraderAddressId = saleTakeTraderAddressId;
	}

	public String getSaleTakeTraderAddress() {
		return saleTakeTraderAddress;
	}

	public void setSaleTakeTraderAddress(String saleTakeTraderAddress) {
		this.saleTakeTraderAddress = saleTakeTraderAddress;
	}

	public String getSaleTakeTraderArea() {
		return saleTakeTraderArea;
	}

	public void setSaleTakeTraderArea(String saleTakeTraderArea) {
		this.saleTakeTraderArea = saleTakeTraderArea;
	}

	public Integer getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(Integer searchUserId) {
		this.searchUserId = searchUserId;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Integer getBuyorderGoodsCount() {
		return buyorderGoodsCount;
	}

	public void setBuyorderGoodsCount(Integer buyorderGoodsCount) {
		this.buyorderGoodsCount = buyorderGoodsCount;
	}

	public Integer getBussinessId()
	{
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId)
	{
		this.bussinessId = bussinessId;
	}

	public Integer getBussinessType()
	{
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType)
	{
		this.bussinessType = bussinessType;
	}

	public Integer getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}

	public String getBussinessNo()
	{
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo)
	{
		this.bussinessNo = bussinessNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getInvoiceVerifyStatus() {
		return invoiceVerifyStatus;
	}

	public void setInvoiceVerifyStatus(Integer invoiceVerifyStatus) {
		this.invoiceVerifyStatus = invoiceVerifyStatus;
	}

	public String getsTakeTraderContactName() {
		return sTakeTraderContactName;
	}

	public void setsTakeTraderContactName(String sTakeTraderContactName) {
		this.sTakeTraderContactName = sTakeTraderContactName;
	}

	public String getsTakeTraderContactMobile() {
		return sTakeTraderContactMobile;
	}

	public void setsTakeTraderContactMobile(String sTakeTraderContactMobile) {
		this.sTakeTraderContactMobile = sTakeTraderContactMobile;
	}

	public String getsTakeTraderContactTelephone() {
		return sTakeTraderContactTelephone;
	}

	public void setsTakeTraderContactTelephone(String sTakeTraderContactTelephone) {
		this.sTakeTraderContactTelephone = sTakeTraderContactTelephone;
	}

	public Integer getsTakeTraderAddressId() {
		return sTakeTraderAddressId;
	}

	public void setsTakeTraderAddressId(Integer sTakeTraderAddressId) {
		this.sTakeTraderAddressId = sTakeTraderAddressId;
	}

	public String getsTakeTraderAddress() {
		return sTakeTraderAddress;
	}

	public void setsTakeTraderAddress(String sTakeTraderAddress) {
		this.sTakeTraderAddress = sTakeTraderAddress;
	}

	public String getsTakeTraderArea() {
		return sTakeTraderArea;
	}

	public void setsTakeTraderArea(String sTakeTraderArea) {
		this.sTakeTraderArea = sTakeTraderArea;
	}
    
	public String getShName() {
		return shName;
	}

	public String getTaxNum() {
		return taxNum;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public Integer getOnWayNum() {
		return onWayNum;
	}

	public void setOnWayNum(Integer onWayNum) {
		this.onWayNum = onWayNum;
	}

//	public String getEstimateArrivalTime() {
//		return estimateArrivalTime;
//	}
//
//	public void setEstimateArrivalTime(String estimateArrivalTime) {
//		this.estimateArrivalTime = estimateArrivalTime;
//	}

	public List<Integer> getSaleGoodsIdList() {
		return saleGoodsIdList;
	}

	public void setSaleGoodsIdList(List<Integer> saleGoodsIdList) {
		this.saleGoodsIdList = saleGoodsIdList;
	}

	public String getBuyDepartmentName() {
		return buyDepartmentName;
	}

	public void setBuyDepartmentName(String buyDepartmentName) {
		this.buyDepartmentName = buyDepartmentName;
	}

	public String getBuyPerson() {
		return buyPerson;
	}

	public void setBuyPerson(String buyPerson) {
		this.buyPerson = buyPerson;
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

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getIsRemindTicket() {
		return isRemindTicket;
	}

	public void setIsRemindTicket(Integer isRemindTicket) {
		this.isRemindTicket = isRemindTicket;
	}

	public Integer getIsRemindMoney() {
		return isRemindMoney;
	}

	public void setIsRemindMoney(Integer isRemindMoney) {
		this.isRemindMoney = isRemindMoney;
	}

	public Integer getIsRemindGoods() {
		return isRemindGoods;
	}

	public void setIsRemindGoods(Integer isRemindGoods) {
		this.isRemindGoods = isRemindGoods;
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

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public Integer getSearchDateType() {
		return searchDateType;
	}

	public void setSearchDateType(Integer searchDateType) {
		this.searchDateType = searchDateType;
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

	public String getProOrgName() {
		return proOrgName;
	}

	public void setProOrgName(String proOrgName) {
		this.proOrgName = proOrgName;
	}

	public String getProUserName() {
		return proUserName;
	}

	public void setProUserName(String proUserName) {
		this.proUserName = proUserName;
	}

	public String getSaleorderGoodsIds() {
		return saleorderGoodsIds;
	}

	public void setSaleorderGoodsIds(String saleorderGoodsIds) {
		this.saleorderGoodsIds = saleorderGoodsIds;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public List<BuyorderGoodsVo> getBuyorderGoodsVoList() {
		return buyorderGoodsVoList;
	}

	public void setBuyorderGoodsVoList(List<BuyorderGoodsVo> buyorderGoodsVoList) {
		this.buyorderGoodsVoList = buyorderGoodsVoList;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public List<VerifiesRecord> getVerifiesRecordList() {
		return verifiesRecordList;
	}

	public void setVerifiesRecordList(List<VerifiesRecord> verifiesRecordList) {
		this.verifiesRecordList = verifiesRecordList;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getFreightDes() {
		return freightDes;
	}

	public void setFreightDes(String freightDes) {
		this.freightDes = freightDes;
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
	}


	public String getMaterialCode() {
	    return materialCode;
	}

	public void setMaterialCode(String materialCode) {
	    this.materialCode = materialCode;
	}

	public Integer getBuyorderSum() {
		return buyorderSum;
	}

	public void setBuyorderSum(Integer buyorderSum) {
		this.buyorderSum = buyorderSum;
	}

	public BigDecimal getBuyorderAmount() {
		return buyorderAmount;
	}

	public void setBuyorderAmount(BigDecimal buyorderAmount) {
		this.buyorderAmount = buyorderAmount;
	}

	public Integer getIsIgnore() {
		return isIgnore;
	}

	public void setIsIgnore(Integer isIgnore) {
		this.isIgnore = isIgnore;
	}

	public String getHomePurchasing() {
		return homePurchasing;
	}

	public void setHomePurchasing(String homePurchasing) {
		this.homePurchasing = homePurchasing;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public Integer getAllNum() {
	    return allNum;
	}

	public void setAllNum(Integer allNum) {
	    this.allNum = allNum;
	}

	public Integer getAllArrivalNum() {
	    return allArrivalNum;
	}

	public void setAllArrivalNum(Integer allArrivalNum) {
	    this.allArrivalNum = allArrivalNum;
	}
	
	public List<WarehouseGoodsOperateLogVo> getWarehouseGoodsOperateLogVoList() {
		return warehouseGoodsOperateLogVoList;
	}

	public void setWarehouseGoodsOperateLogVoList(List<WarehouseGoodsOperateLogVo> warehouseGoodsOperateLogVoList) {
		this.warehouseGoodsOperateLogVoList = warehouseGoodsOperateLogVoList;
	}

	public List<Express> getExpressList() {
		return expressList;
	}

	public void setExpressList(List<Express> expressList) {
		this.expressList = expressList;
	}

	public BigDecimal getPeriodBalance() {
		return periodBalance;
	}

	public void setPeriodBalance(BigDecimal periodBalance) {
		this.periodBalance = periodBalance;
	}

	public BigDecimal getSearchBeginAmount() {
		return searchBeginAmount;
	}

	public void setSearchBeginAmount(BigDecimal searchBeginAmount) {
		this.searchBeginAmount = searchBeginAmount;
	}

	public BigDecimal getSearchEndAmount() {
		return searchEndAmount;
	}

	public void setSearchEndAmount(BigDecimal searchEndAmount) {
		this.searchEndAmount = searchEndAmount;
	}

	public List<TraderAddress> getTavList() {
		return tavList;
	}

	public void setTavList(List<TraderAddress> tavList) {
		this.tavList = tavList;
	}

	public List<TraderAddressVo> getTraderAddressVoList() {
		return traderAddressVoList;
	}

	public void setTraderAddressVoList(List<TraderAddressVo> traderAddressVoList) {
		this.traderAddressVoList = traderAddressVoList;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
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

	public List<BuyorderGoodsVo> getBgvList() {
		return bgvList;
	}

	public void setBgvList(List<BuyorderGoodsVo> bgvList) {
		this.bgvList = bgvList;
	}

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<TraderContact> getTcList() {
		return tcList;
	}

	public void setTcList(List<TraderContact> tcList) {
		this.tcList = tcList;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getLackAccountPeriodAmount() {
		return lackAccountPeriodAmount;
	}

	public void setLackAccountPeriodAmount(BigDecimal lackAccountPeriodAmount) {
		this.lackAccountPeriodAmount = lackAccountPeriodAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Integer getIsBuyorderTotal() {
		return isBuyorderTotal;
	}

	public void setIsBuyorderTotal(Integer isBuyorderTotal) {
		this.isBuyorderTotal = isBuyorderTotal;
	}

	public List<CapitalBill> getCapitalBillList() {
		return capitalBillList;
	}

	public void setCapitalBillList(List<CapitalBill> capitalBillList) {
		this.capitalBillList = capitalBillList;
	}

	public BigDecimal getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(BigDecimal totalDays) {
		this.totalDays = totalDays;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getPeriodDay() {
		return periodDay;
	}

	public void setPeriodDay(Integer periodDay) {
		this.periodDay = periodDay;
	}

	public BigDecimal getPeriodSurplusAmount() {
		return periodSurplusAmount;
	}

	public void setPeriodSurplusAmount(BigDecimal periodSurplusAmount) {
		this.periodSurplusAmount = periodSurplusAmount;
	}

	public Integer getIsOverSettlementPrice() {
	    return isOverSettlementPrice;
	}

	public void setIsOverSettlementPrice(Integer isOverSettlementPrice) {
	    this.isOverSettlementPrice = isOverSettlementPrice;
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

	public String getPaymentVerifyUsername() {
		return paymentVerifyUsername;
	}

	public void setPaymentVerifyUsername(String paymentVerifyUsername) {
		this.paymentVerifyUsername = paymentVerifyUsername;
	}

	public Integer getPaymentVerifyStatus() {
		return paymentVerifyStatus;
	}

	public void setPaymentVerifyStatus(Integer paymentVerifyStatus) {
		this.paymentVerifyStatus = paymentVerifyStatus;
	}

	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	public Integer getAftersaleCanClose() {
		return aftersaleCanClose;
	}

	public void setAftersaleCanClose(Integer aftersaleCanClose) {
		this.aftersaleCanClose = aftersaleCanClose;
	}

	public Integer getLackPeriod() {
		return lackPeriod;
	}

	public void setLackPeriod(Integer lackPeriod) {
		this.lackPeriod = lackPeriod;
	}

	public BigDecimal getPaymentTotalAmount() {
		return paymentTotalAmount;
	}

	public void setPaymentTotalAmount(BigDecimal paymentTotalAmount) {
		this.paymentTotalAmount = paymentTotalAmount;
	}

	public BigDecimal getInvoiceTotalAmount() {
		return invoiceTotalAmount;
	}

	public void setInvoiceTotalAmount(BigDecimal invoiceTotalAmount) {
		this.invoiceTotalAmount = invoiceTotalAmount;
	}

	public List<Integer> getBuyorderIdList() {
		return buyorderIdList;
	}

	public void setBuyorderIdList(List<Integer> buyorderIdList) {
		this.buyorderIdList = buyorderIdList;
	}

	public Long getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(Long periodTime) {
		this.periodTime = periodTime;
	}

	public Long getTkTime() {
		return tkTime;
	}

	public void setTkTime(Long tkTime) {
		this.tkTime = tkTime;
	}

	public Long getHxTime() {
		return hxTime;
	}

	public void setHxTime(Long hxTime) {
		this.hxTime = hxTime;
	}

	
}
