package com.vedeng.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.trader.model.vo.TraderFinanceVo;

public class Quoteorder implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
    private static final long serialVersionUID = 1L;

    private Integer quoteorderId;

    private Integer bussinessChanceId;

    private String quoteorderNo;
    
    private Integer companyId;
    
    private Integer orgId;//销售部门
    
    private Integer userId;//销售人员ID

    private Integer traderId;

    private String traderName;

    private String area;

    private Integer customerType;//客户类型

    private Integer customerNature;//客户性质

    private Integer isNewCustomer;

    private String customerLevel;

    private Integer traderContactId;

    private String traderContactName;

    private String mobile;

    private String telephone;

    private Integer traderAddressId;

    private String address;

    private Integer isPolicymaker;

    private Integer purchasingType;

    private Integer paymentTerm;

    private Integer purchasingTime;

    private String projectProgress;

    private Integer followOrderStatus;

    private String followOrderStatusComments;

    private Long followOrderTime;

    private Integer salesAreaId;

    private String salesArea;

    private Integer terminalTraderId;

    private String terminalTraderName;

    private Integer terminalTraderType;

    private Integer paymentType;

    private BigDecimal prepaidAmount;

    private BigDecimal accountPeriodAmount;
    
    private Integer periodDay;

    private Integer logisticsCollection;

    private BigDecimal retainageAmount;

    private Integer retainageAmountMonth;

    private Integer validStatus;

    private Long validTime;

    private BigDecimal totalAmount;

    private Integer period;

    private Integer invoiceType;

    private Integer freightDescription;

    private String additionalClause;

    private String comments;

    private Integer isSend;

    private Long sendTime;

    private Integer isReplay;

    private Long replayTime;

    private Integer replayUserId;
    
    private Integer haveCommunicate;//是否有沟通记录0无：1有
    
    private Integer consultStatus;//咨询答复状态0无 1未处理 2处理中 3已处理

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    //-----------------------------------------------------------------------------
    private QuoteorderGoods quoteGoods;
    
    private String addTimeStr;
    
    private List<Integer> keyIds;//主键
    
    private String traderContact;//联系人信息
    
    private String salesName;//销售人员
    
    private String salesDeptName;//销售部门
    
    private Integer communicateNum;//沟通次数
    
    private Integer source;//商机来源
    
    private String sku;//订货号

    private String goodsName;//产品名称
    
    private String brandName;//品牌

    private String model;//型号
    
    private Integer timeType;//时间类型1：生效时间；2：沟通时间；3：创建时间；4:成单时间；5：失单时间
    
    private Long beginDate,endDate;//起始时间，结束时间
    
    private Long receiveTime;//商机时间
    
    private String creatorName;//创建人
    
    private String customerTypeStr;//客户类型

    private String customerNatureStr;//客户性质
    
    private String purchasingTypeStr;//采购类型
    
    private String paymentTermStr;//付款条件

    private String purchasingTimeStr;//采购时间
    
    private String terminalTraderTypeStr;//终端类型

    private String paymentTypeStr;//付款方式
    
    private String bussinessChanceNo;//商機編號
    
    private List<User> saleUserList;//
    
    private Integer bussinessStatus;//商机状态
    
    private List<Integer> traderIdList;//客户Id列表
    
    private Integer optUserId;//归属销售人员Id
    
    private String optUserName;//归属销售人员名称
    
    private String saleorderId;//銷售订单id
    
    private String saleorderNo;//銷售订单号 
    
    private Integer verifyStatus;//审核状态
    
    private Long verifyTime;//审核时间
    
    private String submitVerifyTimeStr;//提交审核时间
	
	private String verifyTimeStr;//审核时间
    
    private List<Integer> quoteorderIdList;//审核状态筛选报价单ID
    
    private Integer verifiesType;//审核类型
    
    private String followOrderTimeStr;//成单-失单时间
    
    private String verifyUsername;//当前审核人
    
    private List<String> verifyUsernameList;//当前审核人
    
    private Integer closeVerifyStatus;//关闭报价审核状态
    
    private Long closeVerifyTime;//关闭报价审核时间
    
    private List<QuoteorderGoods> quoteorderGoods;
    
    private String invoiceTypeName;//发票类型名称
    
    private String invoiceTypeRate;//发票利率
    
    private String freightDescriptionName;//运费说明
    
    private Integer totalNum;//产品总数
    
    private TraderFinanceVo traderFinanceVo;
    
    private Integer goodsType;//订单中产品类型（0未维护,1 只有设备,2 只有试剂,3 又有试剂又有设备）

	private String sourceQuae;//报价订单（存的是VS  BD）

	private String taderIdName;//客户表
    
	public Long getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Long verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getFollowOrderTimeStr() {
		return followOrderTimeStr;
	}

	public void setFollowOrderTimeStr(String followOrderTimeStr) {
		this.followOrderTimeStr = followOrderTimeStr;
	}

	public String getSubmitVerifyTimeStr() {
		return submitVerifyTimeStr;
	}

	public void setSubmitVerifyTimeStr(String submitVerifyTimeStr) {
		this.submitVerifyTimeStr = submitVerifyTimeStr;
	}

	public String getVerifyTimeStr() {
		return verifyTimeStr;
	}

	public void setVerifyTimeStr(String verifyTimeStr) {
		this.verifyTimeStr = verifyTimeStr;
	}

	public QuoteorderGoods getQuoteGoods() {
		return quoteGoods;
	}

	public void setQuoteGoods(QuoteorderGoods quoteGoods) {
		this.quoteGoods = quoteGoods;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public String getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(String saleorderId) {
		this.saleorderId = saleorderId;
	}

	public String getOptUserName() {
		return optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}

	public Integer getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(Integer optUserId) {
		this.optUserId = optUserId;
	}

	public List<Integer> getTraderIdList() {
		return traderIdList;
	}

	public void setTraderIdList(List<Integer> traderIdList) {
		this.traderIdList = traderIdList;
	}

	public Integer getBussinessStatus() {
		return bussinessStatus;
	}

	public void setBussinessStatus(Integer bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}

	public List<User> getSaleUserList() {
		return saleUserList;
	}

	public void setSaleUserList(List<User> saleUserList) {
		this.saleUserList = saleUserList;
	}

	public String getBussinessChanceNo() {
		return bussinessChanceNo;
	}

	public void setBussinessChanceNo(String bussinessChanceNo) {
		this.bussinessChanceNo = bussinessChanceNo;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getTerminalTraderTypeStr() {
		return terminalTraderTypeStr;
	}

	public void setTerminalTraderTypeStr(String terminalTraderTypeStr) {
		this.terminalTraderTypeStr = terminalTraderTypeStr;
	}

	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}

	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}

	public String getPaymentTermStr() {
		return paymentTermStr;
	}

	public void setPaymentTermStr(String paymentTermStr) {
		this.paymentTermStr = paymentTermStr;
	}

	public String getPurchasingTimeStr() {
		return purchasingTimeStr;
	}

	public void setPurchasingTimeStr(String purchasingTimeStr) {
		this.purchasingTimeStr = purchasingTimeStr;
	}

	public String getPurchasingTypeStr() {
		return purchasingTypeStr;
	}

	public void setPurchasingTypeStr(String purchasingTypeStr) {
		this.purchasingTypeStr = purchasingTypeStr;
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

	public Quoteorder() {
		super();
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Long receiveTime) {
		this.receiveTime = receiveTime;
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

	public Integer getCommunicateNum() {
		return communicateNum;
	}

	public void setCommunicateNum(Integer communicateNum) {
		this.communicateNum = communicateNum;
	}

	public List<Integer> getKeyIds() {
		return keyIds;
	}

	public void setKeyIds(List<Integer> keyIds) {
		this.keyIds = keyIds;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Long getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Long beginDate) {
		this.beginDate = beginDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
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

	public Integer getConsultStatus() {
		return consultStatus;
	}

	public void setConsultStatus(Integer consultStatus) {
		this.consultStatus = consultStatus;
	}

	public String getTraderContact() {
		return traderContact;
	}

	public void setTraderContact(String traderContact) {
		this.traderContact = traderContact;
	}

	public Integer getQuoteorderId() {
        return quoteorderId;
    }

    public void setQuoteorderId(Integer quoteorderId) {
        this.quoteorderId = quoteorderId;
    }

    public Integer getBussinessChanceId() {
        return bussinessChanceId;
    }

    public void setBussinessChanceId(Integer bussinessChanceId) {
        this.bussinessChanceId = bussinessChanceId;
    }

    public String getQuoteorderNo() {
        return quoteorderNo;
    }

    public void setQuoteorderNo(String quoteorderNo) {
        this.quoteorderNo = quoteorderNo == null ? null : quoteorderNo.trim();
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
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

	public Integer getIsNewCustomer() {
        return isNewCustomer;
    }

    public void setIsNewCustomer(Integer isNewCustomer) {
        this.isNewCustomer = isNewCustomer;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel == null ? null : customerLevel.trim();
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getTraderAddressId() {
        return traderAddressId;
    }

    public void setTraderAddressId(Integer traderAddressId) {
        this.traderAddressId = traderAddressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getIsPolicymaker() {
        return isPolicymaker;
    }

    public void setIsPolicymaker(Integer isPolicymaker) {
        this.isPolicymaker = isPolicymaker;
    }

    public Integer getPurchasingType() {
        return purchasingType;
    }

    public void setPurchasingType(Integer purchasingType) {
        this.purchasingType = purchasingType;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Integer getPurchasingTime() {
        return purchasingTime;
    }

    public void setPurchasingTime(Integer purchasingTime) {
        this.purchasingTime = purchasingTime;
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress == null ? null : projectProgress.trim();
    }

    public Integer getFollowOrderStatus() {
        return followOrderStatus;
    }

    public void setFollowOrderStatus(Integer followOrderStatus) {
        this.followOrderStatus = followOrderStatus;
    }

    public String getFollowOrderStatusComments() {
        return followOrderStatusComments;
    }

    public void setFollowOrderStatusComments(String followOrderStatusComments) {
        this.followOrderStatusComments = followOrderStatusComments == null ? null : followOrderStatusComments.trim();
    }

    public Long getFollowOrderTime() {
        return followOrderTime;
    }

    public void setFollowOrderTime(Long followOrderTime) {
        this.followOrderTime = followOrderTime;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public String getAdditionalClause() {
        return additionalClause;
    }

    public void setAdditionalClause(String additionalClause) {
        this.additionalClause = additionalClause == null ? null : additionalClause.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getIsReplay() {
        return isReplay;
    }

    public void setIsReplay(Integer isReplay) {
        this.isReplay = isReplay;
    }

    public Long getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(Long replayTime) {
        this.replayTime = replayTime;
    }

    public Integer getReplayUserId() {
        return replayUserId;
    }

    public void setReplayUserId(Integer replayUserId) {
        this.replayUserId = replayUserId;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<Integer> getQuoteorderIdList() {
	    return quoteorderIdList;
	}

	public void setQuoteorderIdList(List<Integer> quoteorderIdList) {
	    this.quoteorderIdList = quoteorderIdList;
	}

	public Integer getVerifyStatus() {
	    return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
	    this.verifyStatus = verifyStatus;
	}


	public Integer getPeriodDay() {
		return periodDay;
	}

	public void setPeriodDay(Integer periodDay) {
		this.periodDay = periodDay;
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

	public List<String> getVerifyUsernameList() {
	    return verifyUsernameList;
	}

	public void setVerifyUsernameList(List<String> verifyUsernameList) {
	    this.verifyUsernameList = verifyUsernameList;
	}

	public Integer getCloseVerifyStatus() {
	    return closeVerifyStatus;
	}

	public void setCloseVerifyStatus(Integer closeVerifyStatus) {
	    this.closeVerifyStatus = closeVerifyStatus;
	}

	public Long getCloseVerifyTime() {
	    return closeVerifyTime;
	}

	public void setCloseVerifyTime(Long closeVerifyTime) {
	    this.closeVerifyTime = closeVerifyTime;
	}

	public List<QuoteorderGoods> getQuoteorderGoods() {
		return quoteorderGoods;
	}

	public void setQuoteorderGoods(List<QuoteorderGoods> quoteorderGoods) {
		this.quoteorderGoods = quoteorderGoods;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public String getInvoiceTypeRate() {
		return invoiceTypeRate;
	}

	public void setInvoiceTypeRate(String invoiceTypeRate) {
		this.invoiceTypeRate = invoiceTypeRate;
	}

	public String getFreightDescriptionName() {
		return freightDescriptionName;
	}

	public void setFreightDescriptionName(String freightDescriptionName) {
		this.freightDescriptionName = freightDescriptionName;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public TraderFinanceVo getTraderFinanceVo() {
		return traderFinanceVo;
	}

	public void setTraderFinanceVo(TraderFinanceVo traderFinanceVo) {
		this.traderFinanceVo = traderFinanceVo;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getSourceQuae() {
		return sourceQuae;
	}

	public void setSourceQuae(String sourceQuae) {
		this.sourceQuae = sourceQuae;
	}

	public String getTaderIdName() {
		return taderIdName;
	}

	public void setTaderIdName(String taderIdName) {
		this.taderIdName = taderIdName;
	}
}