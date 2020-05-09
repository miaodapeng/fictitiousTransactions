package com.vedeng.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.authorization.model.User;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.LendOut;
import com.vedeng.order.model.vo.OrderGoodsData;

public class Saleorder implements Serializable {
    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long serialVersionUID = 1L;
    private Boolean onlineFlag; //是否是线上订单列表
    
    private Integer saleorderId;

    private String elSaleordreNo;

    private Integer quoteorderId;

    private Integer parentId;

    private String saleorderNo;

    private String msaleorderNo;

    /**
     * 产品归属的用户id
     */
    private Integer productBelongUserId;

    /**
     * 0--销售订单 1-BD订单/2--备货订单/3--订货订单/4--经销商订单/5--耗材商城
     */
    private Integer orderType;

    private Integer isCustomerArrival;
    
    private Integer source;

    private Integer orgId;

    private String orgName;

    private Integer userId;

    private Integer validStatus;

    private Long validTime;

    private Integer status;

    private Integer purchaseStatus;

    private Integer lockedStatus;

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

    private Integer invoiceMethod;

    private Integer freightDescription;

    private Integer deliveryType;

    private Integer logisticsId;

    private Integer paymentType;

    private BigDecimal prepaidAmount;

    private BigDecimal accountPeriodAmount;

    private Integer periodDay;

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

    private Integer statusComments;

    private Integer syncStatus;

    private Long addTime;

    private Integer creator;

    private Long satisfyInvoiceTime;

    private Long satisfyDeliveryTime;

    private String creatorName;// 创建人员

    private Long modTime;

    private Integer updater;

    private String traderArea;

    private String takeTraderArea;

    private String invoiceTraderArea;

    private List<Integer> keyIds;// 主键

    private String traderContact;// 联系人信息

    private String salesName;// 销售人员

    private String salesDeptId;// 销售部门ID

    private String salesDeptName;// 销售部门

    private List<String> salesDeptUser;// 销售部门人员

    private Integer communicateNum;// 沟通次数

    private String sku;// 订货号

    private String goodsName;// 产品名称

    private Integer goodsId;// 产品id

    private String brandName;// 品牌

    private String model;// 型号

    private Integer searchDateType;// 搜索日期类型 1：创建时间 2：生效时间 3：付款时间 4：发货时间 5：收货时间
    // 6：开票时间

    private Long searchBegintime;// 搜索开始时间

    private Long searchEndtime;// 搜索结束时间

    private String quoteorderNo;// 对应的报价单号

    private Integer bussinessChanceId;// 对应的商机ID

    private String bussinessChanceNo;// 对应的商机单号

    private String customerTypeStr;// 客户类型

    private String customerNatureStr;// 客户性质

    private String customerLevelStr;// 客户等级

    private String terminalTraderTypeStr;// 终端类型

    private List<User> saleUserList;// 对应销售人员列表

    private Integer isContractReturn;// 是否订单合同回传

    private Integer isDeliveryOrderReturn;// 是否订单送货单回传

    private List<Integer> traderIdList;// 客户Id列表

    private Integer optUserId;// 归属销售人员Id

    private String optUserName;// 归属销售人员名称

    private List<SaleorderGoods> goodsList;// 订单下的商品列表
    
    private String materialCode;// 物料编码

    private String optor;// 操作人名称

    private String optType;// 操作类型

    private String flag = "0"; // 是否查询全部订单：0查询所有，1查询状态正常

    private String show;// 订单状态：0：正常，1：非正常

    private String pickNums;// 订单的拣货数+每个批次拣货数

    private Long addTimejh;// 拣货清单创建时间

    private Integer creatorjh;// 拣货单创建人

    private Long modTimejh;// 最新一次拣货时间

    private Integer updaterjh;// 最近一次操作人

    private Integer companyId;// 创建人所属公司

    private String companyName;// 创建人所属公司名称

    private String idCnt;// 扫码出库值

    private String isSM = "0";// 是否是扫码出库1:是0：否

    private String isOut = "0";// 是否关联出库日志表1；是0否

    private int isSearchCount = 0;// 是否查询记录总计及总金额；0否，1是

    private BigDecimal allTotalAmount;// 全部记录总金额

    private Integer allNum;// 订单全部产品数量

    private Integer allDeliveryNum;// 订单全部发货产品数量

    private BigDecimal receivedAmount;// 订单已结款金额（包含账期金额）

    private Integer searchType;// 1.对公收款，2.对私收款

    private BigDecimal startAmount;// 订单总额-起始金额

    private BigDecimal endAmount;// 订单总额-结束金额

    private BigDecimal residueAmount;// 剩余订单账期金额（同一订单信用支付-信用还款）

    private String eFlag = "0"; // 是否是查询快递出库的订单下的商品0：否，1：是

    private String outGoodsTime;// 商品出库时间

    private String search;// 单个搜索框搜索关键词

    private Integer isPayment;// 是否全部结款0未全部结款 1全部结款(含账期结款、尾款)

    private Integer isWeiXin;// 联系人是否绑定微信0未绑定 1绑定

    private BigDecimal paymentAmount;// 订单已收款金额(不含账期)

    private Integer bussinessId;// 业务id（换货订单、样品外借订单）

    private Integer bussinessType = 0;// 业务类型：1销售入库
				      // 2销售出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库

    private Integer orderId;// 售后单关联的销售/采购单id/快递

    private String bussinessNo;// 售后单号

    private List<AfterSalesGoodsVo> afterSalesGoodsList;// 售后订单下的商品列表

    private BigDecimal accountPayable;// 账期付款金额

    private Integer isOpenInvoice;// 是否允许开票:1允许2不允许;;;;是否允许提前开票:3允许，4不允许----5已申请开票

    private Integer shType;// 售后类型

    private Integer isOverSettlementPrice;// 是否超过结算价/1.02

    private Integer overLimit;// 价格超出核价百分比

    private Integer verifiesType;// 审核类型

    private String verifyUsername;// 当前审核人

    private Integer verifyStatus;// 审核状态

    private Integer contractStatus;// 合同审核状态

    private List<String> verifyUsernameList;// 当前审核人

    private String addTimeStr, validTimeStr, invoiceTypeStr;

    private Integer purchase;// 提前采购：1-- 已到款额（不分交易方式）<预付款；订单商品数量>已采购数量 &&
			     // 订单商品数量>已发货数量，必要条件；

    private Integer advancePurchaseStatus;

    private String advancePurchaseComments;

    private Long advancePurchaseTime;

    private List<Integer> invoiceId;

    private BigDecimal realAmount;// 订单实际金额（订单总额-订单退款金额）

    private Integer saleorderModifyApplyId;

    private String saleorderModifyApplyNo;

    private String extraType;// 销售订单详情补充类型

    private String regAddress;

    private Integer isAftersale = 0;// 0:没售后1：产生售后

    private String creatorOrgName;// 创建人部门

    private Integer creatorOrgId;// 创建人部门ID

    private Integer validOrgId;// 生效时归属人部门ID

    private String validOrgName;// 生效时归属人部门

    private Integer validUserId;// 生效时归属人

    private Integer accountPeriod;// 账期未还

    private Long endTime;

    private Integer isSaleOut = 0;// 1：商品出库0：其它业务

    private Integer openInvoiceApply;// 销售单开票

    private Integer isCloseSale;// 是否能关闭（销售订单发生售后，全部退货，全部退款，全部退票）0-否；1-是

    private Integer goodsType;// 订单中产品类型（0未维护,1 只有设备,2 只有试剂,3 又有试剂又有设备）

    private Integer isDelayInvoice;

    private Integer isHaveInvoiceApply;// 是否有开票申请 0无 1有

    private String lockedReason;// 锁定原因

    private Integer isSalesPerformance;// 是否参与业绩计算0否1是

    private Long salesPerformanceTime;// 参与业绩计算时间

    private Integer isLocked; // 是否含有锁定产品

    private Integer hasReturnMoney;// 是否有待确认或进行中的退款单
    
    private Long salesPerformanceModTime;// 参与业绩计算更新时间
    /**
     * 五行
     */
    private BigDecimal fiveTotalAmount;
   
    /**
     * 请求类型
     */
    private Integer reqType;

    private String logisticsName;// 快递公司名称

    private String logisticsNo;// 快递单号

    private String phoneNo;// 短信接收的手机号

    private String costUserIds;// 添加产品成本人员ID集合

    private List<String> costUserIdsList;// 添加产品成本人员ID集合

    private Integer isReferenceCostPrice;// 是否填写成本 0已填写 1 未填写

    /**
     * INVOICE_EMAIL 收票邮箱 add by Franlin for[耗材商城]
     */
    private String invoiceEmail;

    /**
     * 订单归属 OWNER_USER_ID
     */
    private Integer ownerUserId;

    /**
     * 订单归属人名称
     * 
     */
    private String ownerUserName;

    /**
     * 耗材商城的 优惠金额
     */
    private BigDecimal couponAmount;

    /**
     * COUPON_TYPE 优惠类型:1优惠券
     */
    private Integer couponType;

    /**
     * 支付方式 1支付宝、2微信、3银行
     */
    private Integer payType;

    /**
     * 支付形式 0线上、1线下
     */
    private Integer paymentMode;
    
    /**
     * 客户地区最小级ID
     * TRADER_AREA_ID
     */
    private Integer traderAreaId;
    
    /**
     * 收货地区最小级ID
     * TAKE_TRADER_AREA_ID
     */
    private Integer takeTraderAreaId;
    
    /**
     * 收票地区最小级ID
     * INVOICE_TRADER_AREA_ID
     */
    private Integer invoiceTraderAreaId;
    
    private Integer isApplyInvoice;

    private Long applyInvoiceTime;

    /**
     * 认证状态：0 未审核 1 待审核 2 审核通过 3 审核不通过
     * 耗材客户
     */
    private Integer traderStatus;

    private List<String> createMobileList; //手机号集合


    private Integer logisticsApiSync;//是否由物流接口刷新收货状态0否1是

    private Integer logisticsWxsendSync;//是否发送签收微信消息0否1是（如果联系人尚未在贝登注册或未查询到WXOPENID也会标记为1）
   
    private String createMobile; //BD订单创建人手机号
    
    private String bdtraderComments;//BD订单客户备注
    
    private String closeComments;//订单关闭原因
    
    private Long bdMobileTime;//BD订单审核待用户确认状态时间
    
    private Long webTakeDeliveryTime;//前台用户确认收货时间

    private List<Integer> orgIdList;
    
    private List<Goods> goodslist;
    
    private LendOut lendout;//外借单

    /**
     * 是否使用优惠券  0否  1是
     */
    private Integer isCoupons;

    private Integer actionId;//活动Id

    private Integer discountTypeId; //优惠类型，-1全部，1限时活动，2满减券
    private String userName;
    private BigDecimal couponMoney;//优惠金额

    private BigDecimal originalAmount;//订单原金额

    private Integer type; //平台标识（1、贝登）
    //物流单id
    private Integer expressId;

    //是否打印出库单  0不打印  1打印带价格出库单  2打印不带价格出库单
    private Integer isPrintout;

    private Integer outIsFlag;//是否可以出库


    private Integer bhVerifyStatus=null;

    /**
     * 付款的搜索开始时间
     */
    private  Long searchPaymentBeginTime;

    /**
     * 付款的搜索结束时间
     */
    private  Long searchPaymentEndTime;

    /**
     * 登陆人是否归属于产品经理 1:是 0:否
     */
    private Integer loginUserBelongToProductManager = 0;

    public Integer getDiscountTypeId() {
        return discountTypeId;
    }

    public void setDiscountTypeId(Integer discountTypeId) {
        this.discountTypeId = discountTypeId;
    }

    public Integer getIsCoupons() {
        return isCoupons;
    }

    public void setIsCoupons(Integer isCoupons) {
        this.isCoupons = isCoupons;
    }


    private  Integer operateType;//业务类型

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Long getWebTakeDeliveryTime() {
		return webTakeDeliveryTime;
	}

	public void setWebTakeDeliveryTime(Long webTakeDeliveryTime) {
		this.webTakeDeliveryTime = webTakeDeliveryTime;
	}

    private Integer lendOutId;//外借单主键

    private Integer webAccountId;//前台用户id

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    // add by Tomcat.Hui 2019/11/25 11:30 .Desc:  VDERP-1325 订单款项是否满足开票条件. start
    private Integer meetInvoiceConditions;

    /**
     * 订单下的所有的ID集合
     */
    private String saleorderGoodsIds;

    public Integer getMeetInvoiceConditions() {
        return meetInvoiceConditions;
    }

    public void setMeetInvoiceConditions(Integer meetInvoiceConditions) {
        this.meetInvoiceConditions = meetInvoiceConditions;
    }

    // add by Tomcat.Hui 2019/11/25 11:30 .Desc:  VDERP-1325 订单款项是否满足开票条件. end


    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    // add by Tomcat.Hui 2020/1/2 15:31 .Desc: VDERP-1039 票货同行. start
    private Boolean phtxFlag;

    public Boolean getPhtxFlag() {
        return phtxFlag;
    }

    public void setPhtxFlag(Boolean phtxFlag) {
        this.phtxFlag = phtxFlag;
    }
    // add by Tomcat.Hui 2020/1/2 15:31 .Desc: VDERP-1039 票货同行. end


    public Integer getLendOutId() {
		return lendOutId;
	}

	public void setLendOutId(Integer lendOutId) {
		this.lendOutId = lendOutId;
	}

	public LendOut getLendout() {
		return lendout;
	}

	public void setLendout(LendOut lendout) {
		this.lendout = lendout;
	}

	public List<Goods> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<Goods> goodslist) {
		this.goodslist = goodslist;
	}

	public Boolean getOnlineFlag() {
		return onlineFlag;
	}

	public void setOnlineFlag(Boolean onlineFlag) {
		this.onlineFlag = onlineFlag;
	}

	public String getCloseComments() {
		return closeComments;
	}

	public void setCloseComments(String closeComments) {
		this.closeComments = closeComments;
	}

	public String getBdtraderComments() {
		return bdtraderComments;
	}

	public void setBdtraderComments(String bdtraderComments) {
		this.bdtraderComments = bdtraderComments;
	}

	public String getCreateMobile() {
		return createMobile;
	}

	public void setCreateMobile(String createMobile) {
		this.createMobile = createMobile;
	}

	public Long getSalesPerformanceModTime() {
		return salesPerformanceModTime;
	}

	public void setSalesPerformanceModTime(Long salesPerformanceModTime) {
		this.salesPerformanceModTime = salesPerformanceModTime;
	}

	public String getMsaleorderNo() {
		return msaleorderNo;
	}

	public void setMsaleorderNo(String msaleorderNo) {
		this.msaleorderNo = msaleorderNo;
	}

	public Integer getLogisticsWxsendSync() {
        return logisticsWxsendSync;
    }

    public void setLogisticsWxsendSync(Integer logisticsWxsendSync) {
        this.logisticsWxsendSync = logisticsWxsendSync;
    }

    public Integer getLogisticsApiSync() {
        return logisticsApiSync;
    }

    public void setLogisticsApiSync(Integer logisticsApiSync) {
        this.logisticsApiSync = logisticsApiSync;
    }


    public Integer getIsApplyInvoice() {
		return isApplyInvoice;
	}

	public void setIsApplyInvoice(Integer isApplyInvoice) {
		this.isApplyInvoice = isApplyInvoice;
	}

	public Long getApplyInvoiceTime() {
		return applyInvoiceTime;
	}

	public void setApplyInvoiceTime(Long applyInvoiceTime) {
		this.applyInvoiceTime = applyInvoiceTime;
	}

	public Integer getIsLocked() {
	return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
	this.isLocked = isLocked;
    }

    public Integer getHasReturnMoney() {
	return hasReturnMoney;
    }

    public void setHasReturnMoney(Integer hasReturnMoney) {
	this.hasReturnMoney = hasReturnMoney;
    }

    public String getPhoneNo() {
	return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
    }

    public String getLogisticsName() {
	return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
	this.logisticsName = logisticsName;
    }

    public String getLogisticsNo() {
	return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
	this.logisticsNo = logisticsNo;
    }

    public Integer getIsCloseSale() {
	return isCloseSale;
    }

    public void setIsCloseSale(Integer isCloseSale) {
	this.isCloseSale = isCloseSale;
    }

    public Integer getOpenInvoiceApply() {
	return openInvoiceApply;
    }

    public void setOpenInvoiceApply(Integer openInvoiceApply) {
	this.openInvoiceApply = openInvoiceApply;
    }

    public Integer getIsSaleOut() {
	return isSaleOut;
    }

    public void setIsSaleOut(Integer isSaleOut) {
	this.isSaleOut = isSaleOut;
    }

    public Integer getAccountPeriod() {
	return accountPeriod;
    }

    public void setAccountPeriod(Integer accountPeriod) {
	this.accountPeriod = accountPeriod;
    }

    public Long getBdMobileTime() {
		return bdMobileTime;
	}

	public void setBdMobileTime(Long bdMobileTime) {
		this.bdMobileTime = bdMobileTime;
	}

	public String getCreatorOrgName() {
	return creatorOrgName;
    }

    public void setCreatorOrgName(String creatorOrgName) {
	this.creatorOrgName = creatorOrgName;
    }

    public Integer getCreatorOrgId() {
	return creatorOrgId;
    }

    public void setCreatorOrgId(Integer creatorOrgId) {
	this.creatorOrgId = creatorOrgId;
    }

    public Integer getValidOrgId() {
	return validOrgId;
    }

    public void setValidOrgId(Integer validOrgId) {
	this.validOrgId = validOrgId;
    }

    public String getValidOrgName() {
	return validOrgName;
    }

    public void setValidOrgName(String validOrgName) {
	this.validOrgName = validOrgName;
    }

    public Integer getValidUserId() {
	return validUserId;
    }

    public void setValidUserId(Integer validUserId) {
	this.validUserId = validUserId;
    }

    public Integer getIsAftersale() {
	return isAftersale;
    }

    public void setIsAftersale(Integer isAftersale) {
	this.isAftersale = isAftersale;
    }

    public String getRegAddress() {
	return regAddress;
    }

    public void setRegAddress(String regAddress) {
	this.regAddress = regAddress;
    }

    public String getCustomerLevelStr() {
	return customerLevelStr;
    }

    public void setCustomerLevelStr(String customerLevelStr) {
	this.customerLevelStr = customerLevelStr;
    }

    public String getInvoiceTypeStr() {
	return invoiceTypeStr;
    }

    public void setInvoiceTypeStr(String invoiceTypeStr) {
	this.invoiceTypeStr = invoiceTypeStr;
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

    public Integer getShType() {
	return shType;
    }

    public void setShType(Integer shType) {
	this.shType = shType;
    }

    public Integer getOrderId() {
	return orderId;
    }

    public void setOrderId(Integer orderId) {
	this.orderId = orderId;
    }

    public String getBussinessNo() {
	return bussinessNo;
    }

    public void setBussinessNo(String bussinessNo) {
	this.bussinessNo = bussinessNo;
    }

    public Integer getIsOpenInvoice() {
	return isOpenInvoice;
    }

    public void setIsOpenInvoice(Integer isOpenInvoice) {
	this.isOpenInvoice = isOpenInvoice;
    }

    public String getOptType() {
	return optType;
    }

    public void setOptType(String optType) {
	this.optType = optType;
    }

    public BigDecimal getAccountPayable() {
	return accountPayable;
    }

    public void setAccountPayable(BigDecimal accountPayable) {
	this.accountPayable = accountPayable;
    }

    public Integer getGoodsId() {
	return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
	this.goodsId = goodsId;
    }

    public List<AfterSalesGoodsVo> getAfterSalesGoodsList() {
	return afterSalesGoodsList;
    }

    public void setAfterSalesGoodsList(List<AfterSalesGoodsVo> afterSalesGoodsList) {
	this.afterSalesGoodsList = afterSalesGoodsList;
    }

    public Integer getBussinessType() {
	return bussinessType;
    }

    public void setBussinessType(Integer bussinessType) {
	this.bussinessType = bussinessType;
    }

    public Integer getBussinessId() {
	return bussinessId;
    }

    public void setBussinessId(Integer bussinessId) {
	this.bussinessId = bussinessId;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
	this.paymentAmount = paymentAmount;
    }

    public BigDecimal getPaymentAmount() {
	return paymentAmount;
    }

    public BigDecimal getStartAmount() {
	return startAmount;
    }

    public void setStartAmount(BigDecimal startAmount) {
	this.startAmount = startAmount;
    }

    public BigDecimal getEndAmount() {
	return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
	this.endAmount = endAmount;
    }

    public BigDecimal getAllTotalAmount() {
	return allTotalAmount;
    }

    public void setAllTotalAmount(BigDecimal allTotalAmount) {
	this.allTotalAmount = allTotalAmount;
    }

    public int getIsSearchCount() {
	return isSearchCount;
    }

    public void setIsSearchCount(int isSearchCount) {
	this.isSearchCount = isSearchCount;
    }

    public String getIsOut() {
	return isOut;
    }

    public void setIsOut(String isOut) {
	this.isOut = isOut;
    }

    public String getIsSM() {
	return isSM;
    }

    public void setIsSM(String isSM) {
	this.isSM = isSM;
    }

    public String getIdCnt() {
	return idCnt;
    }

    public void setIdCnt(String idCnt) {
	this.idCnt = idCnt;
    }

    public Integer getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
    }

    public Long getAddTimejh() {
	return addTimejh;
    }

    public void setAddTimejh(Long addTimejh) {
	this.addTimejh = addTimejh;
    }

    public Integer getCreatorjh() {
	return creatorjh;
    }

    public void setCreatorjh(Integer creatorjh) {
	this.creatorjh = creatorjh;
    }

    public Long getModTimejh() {
	return modTimejh;
    }

    public void setModTimejh(Long modTimejh) {
	this.modTimejh = modTimejh;
    }

    public Integer getUpdaterjh() {
	return updaterjh;
    }

    public void setUpdaterjh(Integer updaterjh) {
	this.updaterjh = updaterjh;
    }

    public String getPickNums() {
	return pickNums;
    }

    public void setPickNums(String pickNums) {
	this.pickNums = pickNums;
    }

    public String getShow() {
	return show;
    }

    public void setShow(String show) {
	this.show = show;
    }

    public String getFlag() {
	return flag;
    }

    public void setFlag(String flag) {
	this.flag = flag;
    }

    public String getOptor() {
	return optor;
    }

    public void setOptor(String optor) {
	this.optor = optor;
    }

    public String getMaterialCode() {
	return materialCode;
    }

    public void setMaterialCode(String materialCode) {
	this.materialCode = materialCode;
    }

    public List<SaleorderGoods> getGoodsList() {
	return goodsList;
    }

    public void setGoodsList(List<SaleorderGoods> goodsList) {
	this.goodsList = goodsList;
    }

    public Integer getSaleorderId() {
	return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
	this.saleorderId = saleorderId;
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

    public String getSaleorderNo() {
	return saleorderNo;
    }

    public void setSaleorderNo(String saleorderNo) {
	this.saleorderNo = saleorderNo == null ? null : saleorderNo.trim();
    }

    public Integer getOrderType() {
	return orderType;
    }

    public void setOrderType(Integer orderType) {
	this.orderType = orderType;
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

    public Integer getLockedStatus() {
	return lockedStatus;
    }

    public void setLockedStatus(Integer lockedStatus) {
	this.lockedStatus = lockedStatus;
    }

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
	this.traderContactName = traderContactName == null ? null : traderContactName.trim();
    }

    public String getTraderContactMobile() {
	return traderContactMobile;
    }

    public void setTraderContactMobile(String traderContactMobile) {
	this.traderContactMobile = traderContactMobile == null ? null : traderContactMobile.trim();
    }

    public String getTraderContactTelephone() {
	return traderContactTelephone;
    }

    public void setTraderContactTelephone(String traderContactTelephone) {
	this.traderContactTelephone = traderContactTelephone == null ? null : traderContactTelephone.trim();
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
	this.traderAddress = traderAddress == null ? null : traderAddress.trim();
    }

    public String getTraderComments() {
	return traderComments;
    }

    public void setTraderComments(String traderComments) {
	this.traderComments = traderComments == null ? null : traderComments.trim();
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
	this.takeTraderContactName = takeTraderContactName == null ? null : takeTraderContactName.trim();
    }

    public String getTakeTraderContactMobile() {
	return takeTraderContactMobile;
    }

    public void setTakeTraderContactMobile(String takeTraderContactMobile) {
	this.takeTraderContactMobile = takeTraderContactMobile == null ? null : takeTraderContactMobile.trim();
    }

    public String getTakeTraderContactTelephone() {
	return takeTraderContactTelephone;
    }

    public void setTakeTraderContactTelephone(String takeTraderContactTelephone) {
	this.takeTraderContactTelephone = takeTraderContactTelephone == null ? null : takeTraderContactTelephone.trim();
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
	this.takeTraderAddress = takeTraderAddress == null ? null : takeTraderAddress.trim();
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
	this.invoiceTraderContactName = invoiceTraderContactName == null ? null : invoiceTraderContactName.trim();
    }

    public String getInvoiceTraderContactMobile() {
	return invoiceTraderContactMobile;
    }

    public void setInvoiceTraderContactMobile(String invoiceTraderContactMobile) {
	this.invoiceTraderContactMobile = invoiceTraderContactMobile == null ? null : invoiceTraderContactMobile.trim();
    }

    public String getInvoiceTraderContactTelephone() {
	return invoiceTraderContactTelephone;
    }

    public void setInvoiceTraderContactTelephone(String invoiceTraderContactTelephone) {
	this.invoiceTraderContactTelephone = invoiceTraderContactTelephone == null ? null
		: invoiceTraderContactTelephone.trim();
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
	this.invoiceTraderAddress = invoiceTraderAddress == null ? null : invoiceTraderAddress.trim();
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
	this.salesArea = salesArea == null ? null : salesArea.trim();
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

    public String getAdditionalClause() {
	return additionalClause;
    }

    public void setAdditionalClause(String additionalClause) {
	this.additionalClause = additionalClause == null ? null : additionalClause.trim();
    }

    public String getLogisticsComments() {
	return logisticsComments;
    }

    public void setLogisticsComments(String logisticsComments) {
	this.logisticsComments = logisticsComments == null ? null : logisticsComments.trim();
    }

    public String getFinanceComments() {
	return financeComments;
    }

    public void setFinanceComments(String financeComments) {
	this.financeComments = financeComments == null ? null : financeComments.trim();
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments == null ? null : comments.trim();
    }

    public String getInvoiceComments() {
	return invoiceComments;
    }

    public void setInvoiceComments(String invoiceComments) {
	this.invoiceComments = invoiceComments == null ? null : invoiceComments.trim();
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
	this.supplierClause = supplierClause == null ? null : supplierClause.trim();
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

    public Integer getSyncStatus() {
	return syncStatus;
    }

    public void setSyncStatus(Integer syncStatus) {
	this.syncStatus = syncStatus;
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

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public Integer getHaveCommunicate() {
	return haveCommunicate;
    }

    public void setHaveCommunicate(Integer haveCommunicate) {
	this.haveCommunicate = haveCommunicate;
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

    public String getSalesName() {
	return salesName;
    }

    public void setSalesName(String salesName) {
	this.salesName = salesName;
    }

    public String getSalesDeptName() {
	return salesDeptName;
    }

    public void setSalesDeptName(String salesDeptName) {
	this.salesDeptName = salesDeptName;
    }

    public List<String> getSalesDeptUser() {
	return salesDeptUser;
    }

    public void setSalesDeptUser(List<String> salesDeptUser) {
	this.salesDeptUser = salesDeptUser;
    }

    public Integer getCommunicateNum() {
	return communicateNum;
    }

    public void setCommunicateNum(Integer communicateNum) {
	this.communicateNum = communicateNum;
    }

    public String getSku() {
	return sku;
    }

    public void setSku(String sku) {
	this.sku = sku;
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

    public Integer getIsCustomerArrival() {
		return isCustomerArrival;
	}

	public void setIsCustomerArrival(Integer isCustomerArrival) {
		this.isCustomerArrival = isCustomerArrival;
	}

	public String getSalesDeptId() {
	return salesDeptId;
    }

    public void setSalesDeptId(String salesDeptId) {
	this.salesDeptId = salesDeptId;
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

    public List<Integer> getKeyIds() {
	return keyIds;
    }

    public void setKeyIds(List<Integer> keyIds) {
	this.keyIds = keyIds;
    }

    public String getTraderContact() {
	return traderContact;
    }

    public void setTraderContact(String traderContact) {
	this.traderContact = traderContact;
    }

    public Integer getOrgId() {
	return orgId;
    }

    public void setOrgId(Integer orgId) {
	this.orgId = orgId;
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

    public String getQuoteorderNo() {
	return quoteorderNo;
    }

    public void setQuoteorderNo(String quoteorderNo) {
	this.quoteorderNo = quoteorderNo;
    }

    public Integer getBussinessChanceId() {
	return bussinessChanceId;
    }

    public void setBussinessChanceId(Integer bussinessChanceId) {
	this.bussinessChanceId = bussinessChanceId;
    }

    public String getBussinessChanceNo() {
	return bussinessChanceNo;
    }

    public void setBussinessChanceNo(String bussinessChanceNo) {
	this.bussinessChanceNo = bussinessChanceNo;
    }

    public String getCreatorName() {
	return creatorName;
    }

    public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
    }

    public String getCustomerTypeStr() {
	return customerTypeStr;
    }

    public void setCustomerTypeStr(String customerTypeStr) {
	this.customerTypeStr = customerTypeStr;
    }

    public String getCustomerNatureStr() {
	return customerNatureStr;
    }

    public void setCustomerNatureStr(String customerNatureStr) {
	this.customerNatureStr = customerNatureStr;
    }

    public String getTerminalTraderTypeStr() {
	return terminalTraderTypeStr;
    }

    public void setTerminalTraderTypeStr(String terminalTraderTypeStr) {
	this.terminalTraderTypeStr = terminalTraderTypeStr;
    }

    public String getPaymentComments() {
	return paymentComments;
    }

    public void setPaymentComments(String paymentComments) {
	this.paymentComments = paymentComments;
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

    public List<User> getSaleUserList() {
	return saleUserList;
    }

    public void setSaleUserList(List<User> saleUserList) {
	this.saleUserList = saleUserList;
    }

    public Integer getIsContractReturn() {
	return isContractReturn;
    }

    public void setIsContractReturn(Integer isContractReturn) {
	this.isContractReturn = isContractReturn;
    }

    public Integer getIsDeliveryOrderReturn() {
	return isDeliveryOrderReturn;
    }

    public void setIsDeliveryOrderReturn(Integer isDeliveryOrderReturn) {
	this.isDeliveryOrderReturn = isDeliveryOrderReturn;
    }

    public List<Integer> getTraderIdList() {
	return traderIdList;
    }

    public void setTraderIdList(List<Integer> traderIdList) {
	this.traderIdList = traderIdList;
    }

    public Integer getOptUserId() {
	return optUserId;
    }

    public void setOptUserId(Integer optUserId) {
	this.optUserId = optUserId;
    }

    public String getOptUserName() {
	return optUserName;
    }

    public void setOptUserName(String optUserName) {
	this.optUserName = optUserName;
    }

    public Integer getAllNum() {
	return allNum;
    }

    public void setAllNum(Integer allNum) {
	this.allNum = allNum;
    }

    public Integer getAllDeliveryNum() {
	return allDeliveryNum;
    }

    public void setAllDeliveryNum(Integer allDeliveryNum) {
	this.allDeliveryNum = allDeliveryNum;
    }

    public BigDecimal getReceivedAmount() {
	return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
	this.receivedAmount = receivedAmount;
    }

    public Integer getSearchType() {
	return searchType;
    }

    public void setSearchType(Integer searchType) {
	this.searchType = searchType;
    }

    public BigDecimal getResidueAmount() {
	return residueAmount;
    }

    public void setResidueAmount(BigDecimal residueAmount) {
	this.residueAmount = residueAmount;
    }

    public String getSearch() {
	return search;
    }

    public void setSearch(String search) {
	this.search = search;
    }

    public String geteFlag() {
	return eFlag;
    }

    public void seteFlag(String eFlag) {
	this.eFlag = eFlag;
    }

    public String getOutGoodsTime() {
	return outGoodsTime;
    }

    public void setOutGoodsTime(String outGoodsTime) {
	this.outGoodsTime = outGoodsTime;
    }

    public Integer getIsPayment() {
	return isPayment;
    }

    public void setIsPayment(Integer isPayment) {
	this.isPayment = isPayment;
    }

    public Integer getIsWeiXin() {
	return isWeiXin;
    }

    public void setIsWeiXin(Integer isWeiXin) {
	this.isWeiXin = isWeiXin;
    }

    public String getCompanyName() {
	return companyName;
    }

    public void setCompanyName(String companyName) {
	this.companyName = companyName == null ? null : companyName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getPeriodDay() {
	return periodDay;
    }

    public void setPeriodDay(Integer periodDay) {
	this.periodDay = periodDay;
    }

    public Integer getStatusComments() {
	return statusComments;
    }

    public void setStatusComments(Integer statusComments) {
	this.statusComments = statusComments;
    }

    public Integer getIsOverSettlementPrice() {
	return isOverSettlementPrice;
    }

    public void setIsOverSettlementPrice(Integer isOverSettlementPrice) {
	this.isOverSettlementPrice = isOverSettlementPrice;
    }

    public Integer getOverLimit() {
	return overLimit;
    }

    public void setOverLimit(Integer overLimit) {
	this.overLimit = overLimit;
    }

    public Integer getVerifiesType() {
	return verifiesType;
    }

    public void setVerifiesType(Integer verifiesType) {
	this.verifiesType = verifiesType;
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

    public Integer getPurchase() {
	return purchase;
    }

    public void setPurchase(Integer purchase) {
	this.purchase = purchase;
    }

    public Integer getAdvancePurchaseStatus() {
	return advancePurchaseStatus;
    }

    public void setAdvancePurchaseStatus(Integer advancePurchaseStatus) {
	this.advancePurchaseStatus = advancePurchaseStatus;
    }

    public String getAdvancePurchaseComments() {
	return advancePurchaseComments;
    }

    public void setAdvancePurchaseComments(String advancePurchaseComments) {
	this.advancePurchaseComments = advancePurchaseComments;
    }

    public Long getAdvancePurchaseTime() {
	return advancePurchaseTime;
    }

    public void setAdvancePurchaseTime(Long advancePurchaseTime) {
	this.advancePurchaseTime = advancePurchaseTime;
    }

    public List<Integer> getInvoiceId() {
	return invoiceId;
    }

    public void setInvoiceId(List<Integer> invoiceId) {
	this.invoiceId = invoiceId;
    }

    public Integer getSaleorderModifyApplyId() {
	return saleorderModifyApplyId;
    }

    public void setSaleorderModifyApplyId(Integer saleorderModifyApplyId) {
	this.saleorderModifyApplyId = saleorderModifyApplyId;
    }

    public String getSaleorderModifyApplyNo() {
	return saleorderModifyApplyNo;
    }

    public void setSaleorderModifyApplyNo(String saleorderModifyApplyNo) {
	this.saleorderModifyApplyNo = saleorderModifyApplyNo;
    }

    public Long getSatisfyInvoiceTime() {
	return satisfyInvoiceTime;
    }

    public void setSatisfyInvoiceTime(Long satisfyInvoiceTime) {
	this.satisfyInvoiceTime = satisfyInvoiceTime;
    }

    public Long getSatisfyDeliveryTime() {
	return satisfyDeliveryTime;
    }

    public void setSatisfyDeliveryTime(Long satisfyDeliveryTime) {
	this.satisfyDeliveryTime = satisfyDeliveryTime;
    }

    public BigDecimal getRealAmount() {
	return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
	this.realAmount = realAmount;
    }

    public String getExtraType() {
	return extraType;
    }

    public void setExtraType(String extraType) {
	this.extraType = extraType;
    }

    public Integer getSource() {
	return source;
    }

    public void setSource(Integer source) {
	this.source = source;
    }

    public String getOrgName() {
	return orgName;
    }

    public void setOrgName(String orgName) {
	this.orgName = orgName;
    }

    public Long getEndTime() {
	return endTime;
    }

    public void setEndTime(Long endTime) {
	this.endTime = endTime;
    }

    public Integer getGoodsType() {
	return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
	this.goodsType = goodsType;
    }

    public Integer getIsDelayInvoice() {
	return isDelayInvoice;
    }

    public void setIsDelayInvoice(Integer isDelayInvoice) {
	this.isDelayInvoice = isDelayInvoice;
    }

    public Integer getIsHaveInvoiceApply() {
	return isHaveInvoiceApply;
    }

    public void setIsHaveInvoiceApply(Integer isHaveInvoiceApply) {
	this.isHaveInvoiceApply = isHaveInvoiceApply;
    }

    public Integer getInvoiceMethod() {
	return invoiceMethod;
    }

    public void setInvoiceMethod(Integer invoiceMethod) {
	this.invoiceMethod = invoiceMethod;
    }

    public String getLockedReason() {
	return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
	this.lockedReason = lockedReason;
    }

    public Integer getIsSalesPerformance() {
	return isSalesPerformance;
    }

    public void setIsSalesPerformance(Integer isSalesPerformance) {
	this.isSalesPerformance = isSalesPerformance;
    }

    public Long getSalesPerformanceTime() {
	return salesPerformanceTime;
    }

    public void setSalesPerformanceTime(Long salesPerformanceTime) {
	this.salesPerformanceTime = salesPerformanceTime;
    }

    public BigDecimal getFiveTotalAmount() {
	return fiveTotalAmount;
    }

    public void setFiveTotalAmount(BigDecimal fiveTotalAmount) {
	this.fiveTotalAmount = fiveTotalAmount;
    }

    public Integer getReqType() {
	return reqType;
    }

    public void setReqType(Integer reqType) {
	this.reqType = reqType;
    }

    public String getCostUserIds() {
	return costUserIds;
    }

    public void setCostUserIds(String costUserIds) {
	this.costUserIds = costUserIds;
    }

    public List<String> getCostUserIdsList() {
	return costUserIdsList;
    }

    public void setCostUserIdsList(List<String> costUserIdsList) {
	this.costUserIdsList = costUserIdsList;
    }

    public Integer getIsReferenceCostPrice() {
	return isReferenceCostPrice;
    }

    public void setIsReferenceCostPrice(Integer isReferenceCostPrice) {
	this.isReferenceCostPrice = isReferenceCostPrice;
    }

    public Integer getContractStatus() {
	return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
	this.contractStatus = contractStatus;
    }

    public String getInvoiceEmail() {
	return invoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
	this.invoiceEmail = invoiceEmail;
    }

    public Integer getOwnerUserId() {
	return ownerUserId;
    }

    public void setOwnerUserId(Integer ownerUserId) {
	this.ownerUserId = ownerUserId;
    }

    public String getOwnerUserName() {
	return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
	this.ownerUserName = ownerUserName;
    }

    public BigDecimal getCouponAmount() {
	return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
	this.couponAmount = couponAmount;
    }

    public Integer getCouponType() {
	return couponType;
    }

    public void setCouponType(Integer couponType) {
	this.couponType = couponType;
    }

    public Integer getPayType() {
	return payType;
    }

    public void setPayType(Integer payType) {
	this.payType = payType;
    }

    public Integer getPaymentMode() {
	return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
	this.paymentMode = paymentMode;
    }

	public Integer getTraderAreaId()
	{
		return traderAreaId;
	}

	public void setTraderAreaId(Integer traderAreaId)
	{
		this.traderAreaId = traderAreaId;
	}

	public Integer getTakeTraderAreaId()
	{
		return takeTraderAreaId;
	}

	public void setTakeTraderAreaId(Integer takeTraderAreaId)
	{
		this.takeTraderAreaId = takeTraderAreaId;
	}

	public Integer getInvoiceTraderAreaId()
	{
		return invoiceTraderAreaId;
	}

	public void setInvoiceTraderAreaId(Integer invoiceTraderAreaId)
	{
		this.invoiceTraderAreaId = invoiceTraderAreaId;
	}

	public Integer getTraderStatus()
	{
		return traderStatus;
	}

	public void setTraderStatus(Integer traderStatus)
	{
		this.traderStatus = traderStatus;
	}

    public List<Integer> getOrgIdList() {
        return orgIdList;
    }

    public void setOrgIdList(List<Integer> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public List<String> getCreateMobileList() {
        return createMobileList;
    }

    public void setCreateMobileList(List<String> createMobileList) {
        this.createMobileList = createMobileList;
    }

    public String getElSaleordreNo() {
        return elSaleordreNo;
    }

    public void setElSaleordreNo(String elSaleordreNo) {
        this.elSaleordreNo = elSaleordreNo;
    }

    public Integer getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Integer webAccountId) {
        this.webAccountId = webAccountId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSaleorderGoodsIds() {
        return saleorderGoodsIds;
    }

    public void setSaleorderGoodsIds(String saleorderGoodsIds) {
        this.saleorderGoodsIds = saleorderGoodsIds;
    }

    public Integer getProductBelongUserId() {
        return productBelongUserId;
    }

    public void setProductBelongUserId(Integer productBelongUserId) {
        this.productBelongUserId = productBelongUserId;
    }

    public Integer getIsPrintout() {
        return isPrintout;
    }

    public void setIsPrintout(Integer isPrintout) {
        this.isPrintout = isPrintout;
    }

    public Integer getBhVerifyStatus() {
        return bhVerifyStatus;
    }

    public void setBhVerifyStatus(Integer bhVerifyStatus) {
        this.bhVerifyStatus = bhVerifyStatus;
    }

    public Long getSearchPaymentBeginTime() {
        return searchPaymentBeginTime;
    }

    public void setSearchPaymentBeginTime(Long searchPaymentBeginTime) {
        this.searchPaymentBeginTime = searchPaymentBeginTime;
    }

    public Long getSearchPaymentEndTime() {
        return searchPaymentEndTime;
    }

    public void setSearchPaymentEndTime(Long searchPaymentEndTime) {
        this.searchPaymentEndTime = searchPaymentEndTime;
    }


    public Integer getOutIsFlag() {
        return outIsFlag;
    }

    public void setOutIsFlag(Integer outIsFlag) {
        this.outIsFlag = outIsFlag;
    }

    public Integer getLoginUserBelongToProductManager() {
        return loginUserBelongToProductManager;
    }

    public void setLoginUserBelongToProductManager(Integer loginUserBelongToProductManager) {
        this.loginUserBelongToProductManager = loginUserBelongToProductManager;
    }
}
