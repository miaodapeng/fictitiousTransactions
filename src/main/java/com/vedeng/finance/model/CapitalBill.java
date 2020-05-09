package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Saleorder;

public class CapitalBill implements Serializable{
    private Integer capitalBillId;

    private Integer bankBillId;

    private String capitalBillNo;
    
    private Integer companyId;

    private Long traderTime;

    private Integer traderSubject;

    private Integer traderType;

    private Integer traderMode;

    private BigDecimal amount;

    private Integer currencyUnitId;
    
    private String payer;
    
    private String payee;

    private String comments;

    private Long addTime;

    private Integer creator;
    
    private String tranFlow;
    
    private String payerBankAccount;
    
    private String payeeBankAccount;
    
    private String payerBankName;
    
    private String payeeBankName;
    
    //销售订单
    private Saleorder saleorder;
    
    private String creatorName;//创建者
    
    private String traderModeStr;//交易方式名称
    
    private Long searchBegintime;//搜索开始时间
    
    private Long searchEndtime;//搜索结束时间
    
    private BigDecimal searchBeginAmount;//开始金额
    
    private BigDecimal searchEndAmount;//结束金额
    
    private List<CapitalBillDetail> capitalBillDetails;
    
    private CapitalBillDetail capitalBillDetail;
    
    private Integer traderId;//交易者ID
    
    private String traderName;//交易者名称
    
    private String orderNo;//订单号
    
    private Integer bussinessType;//业务类型
    
    private Integer orderType;//订单类型
    
    private Integer relatedId;//关联表ID
    
    private String salesDeptId;//销售部门ID
    
    private String salesDeptName;//销售部门
    
    private Integer optUserId;//归属销售人员Id
    
    private String optUserName;//归属销售人员名称
    
    private Integer deliveryStatus;//发货状态
    
    private Long deliveryTime;//发货时间
    
    private Integer arrivalStatus;//到货状态
    
    private Long arrivalTime;//到货时间
    
    private Buyorder buyorder;
    
    private Integer isCapitalBillTotal = 0;//查询交易流水收入总额,支出总额
    
    private Integer isPaymentRecordTotal = 0;//查询付款记录订单付款总额，本次付款总额
    
    private Integer isCollectionRecordTotal = 0;//查询收款记录订单收款总额，本次收款总额
    
    private BigDecimal capitalBillCollectionAmount;//收入总额
    
    private BigDecimal capitalBillPaymentAmount;//支出总额
    
    private BigDecimal orderPaymentTotalAmount;//订单已付款总额
    
    private BigDecimal thisPaymentTotalAmount;//本次付款总额
    
    private BigDecimal totalAmount,realAmount;//订单总额
    
    private Integer orgId;//部门ID
    
    private List<Integer> traderIdList;// 客户Id列表
    
    private BigDecimal buyAmount;//采购总额
    
    private Integer payApplyId;//付款申请ID
    
    private Integer userId;
    
    private String addTimeStr,traderTimeStr,deliveryTimeStr,arrivalTimeStr;

    private Integer paymentType;//付款途径
    
    private Integer afterSalesPaymentType;//售后订单付款类型 1：收款 2/null：付款
    
    private String operationType;//操作类型
    
    private Integer relatedOrderId;
    private String relatedOrderNo;

    private Boolean bdPayStatusFlag = true;//bd订单推送付款状态flag

	public Boolean getBdPayStatusFlag() {
		return bdPayStatusFlag;
	}

	public void setBdPayStatusFlag(Boolean bdPayStatusFlag) {
		this.bdPayStatusFlag = bdPayStatusFlag;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRelatedOrderId() {
		return relatedOrderId;
	}

	public void setRelatedOrderId(Integer relatedOrderId) {
		this.relatedOrderId = relatedOrderId;
	}

	public String getRelatedOrderNo() {
		return relatedOrderNo;
	}

	public void setRelatedOrderNo(String relatedOrderNo) {
		this.relatedOrderNo = relatedOrderNo;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getArrivalTimeStr() {
		return arrivalTimeStr;
	}

	public void setArrivalTimeStr(String arrivalTimeStr) {
		this.arrivalTimeStr = arrivalTimeStr;
	}

	public String getTraderTimeStr() {
		return traderTimeStr;
	}

	public void setTraderTimeStr(String traderTimeStr) {
		this.traderTimeStr = traderTimeStr;
	}

	public String getDeliveryTimeStr() {
		return deliveryTimeStr;
	}

	public void setDeliveryTimeStr(String deliveryTimeStr) {
		this.deliveryTimeStr = deliveryTimeStr;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

    public Integer getPayApplyId() {
		return payApplyId;
	}

	public void setPayApplyId(Integer payApplyId) {
		this.payApplyId = payApplyId;
	}
    
    public Integer getCapitalBillId() {
        return capitalBillId;
    }

    public void setCapitalBillId(Integer capitalBillId) {
        this.capitalBillId = capitalBillId;
    }

    public Integer getBankBillId() {
        return bankBillId;
    }

    public void setBankBillId(Integer bankBillId) {
        this.bankBillId = bankBillId;
    }

    public String getCapitalBillNo() {
        return capitalBillNo;
    }

    public void setCapitalBillNo(String capitalBillNo) {
        this.capitalBillNo = capitalBillNo == null ? null : capitalBillNo.trim();
    }

    public Long getTraderTime() {
        return traderTime;
    }

    public void setTraderTime(Long traderTime) {
        this.traderTime = traderTime;
    }

    public Integer getTraderSubject() {
        return traderSubject;
    }

    public void setTraderSubject(Integer traderSubject) {
        this.traderSubject = traderSubject;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public Integer getTraderMode() {
        return traderMode;
    }

    public void setTraderMode(Integer traderMode) {
        this.traderMode = traderMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCurrencyUnitId() {
        return currencyUnitId;
    }

    public void setCurrencyUnitId(Integer currencyUnitId) {
        this.currencyUnitId = currencyUnitId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
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

    public Saleorder getSaleorder() {
	return saleorder;
    }

    public void setSaleorder(Saleorder saleorder) {
	this.saleorder = saleorder;
    }


	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer == null ? null : payer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee == null ? null : payee.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public List<CapitalBillDetail> getCapitalBillDetails() {
		return capitalBillDetails;
	}

	public void setCapitalBillDetails(List<CapitalBillDetail> capitalBillDetails) {
		this.capitalBillDetails = capitalBillDetails;
	}

	public CapitalBillDetail getCapitalBillDetail() {
		return capitalBillDetail;
	}

	public void setCapitalBillDetail(CapitalBillDetail capitalBillDetail) {
		this.capitalBillDetail = capitalBillDetail;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getTraderModeStr() {
		return traderModeStr;
	}

	public void setTraderModeStr(String traderModeStr) {
		this.traderModeStr = traderModeStr;
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

	public String getTranFlow() {
		return tranFlow;
	}

	public void setTranFlow(String tranFlow) {
		this.tranFlow = tranFlow;
	}

	public String getPayerBankAccount() {
		return payerBankAccount;
	}

	public void setPayerBankAccount(String payerBankAccount) {
		this.payerBankAccount = payerBankAccount;
	}

	public String getPayeeBankAccount() {
		return payeeBankAccount;
	}

	public void setPayeeBankAccount(String payeeBankAccount) {
		this.payeeBankAccount = payeeBankAccount;
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

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Buyorder getBuyorder() {
		return buyorder;
	}

	public void setBuyorder(Buyorder buyorder) {
		this.buyorder = buyorder;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public String getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(String salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
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

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	public Integer getIsCapitalBillTotal() {
		return isCapitalBillTotal;
	}

	public void setIsCapitalBillTotal(Integer isCapitalBillTotal) {
		this.isCapitalBillTotal = isCapitalBillTotal;
	}

	public Integer getIsPaymentRecordTotal() {
		return isPaymentRecordTotal;
	}

	public void setIsPaymentRecordTotal(Integer isPaymentRecordTotal) {
		this.isPaymentRecordTotal = isPaymentRecordTotal;
	}

	public Integer getIsCollectionRecordTotal() {
		return isCollectionRecordTotal;
	}

	public void setIsCollectionRecordTotal(Integer isCollectionRecordTotal) {
		this.isCollectionRecordTotal = isCollectionRecordTotal;
	}

	public BigDecimal getCapitalBillCollectionAmount() {
		return capitalBillCollectionAmount;
	}

	public void setCapitalBillCollectionAmount(BigDecimal capitalBillCollectionAmount) {
		this.capitalBillCollectionAmount = capitalBillCollectionAmount;
	}

	public BigDecimal getCapitalBillPaymentAmount() {
		return capitalBillPaymentAmount;
	}

	public void setCapitalBillPaymentAmount(BigDecimal capitalBillPaymentAmount) {
		this.capitalBillPaymentAmount = capitalBillPaymentAmount;
	}

	public BigDecimal getOrderPaymentTotalAmount() {
		return orderPaymentTotalAmount;
	}

	public void setOrderPaymentTotalAmount(BigDecimal orderPaymentTotalAmount) {
		this.orderPaymentTotalAmount = orderPaymentTotalAmount;
	}

	public BigDecimal getThisPaymentTotalAmount() {
		return thisPaymentTotalAmount;
	}

	public void setThisPaymentTotalAmount(BigDecimal thisPaymentTotalAmount) {
		this.thisPaymentTotalAmount = thisPaymentTotalAmount;
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

	public List<Integer> getTraderIdList() {
		return traderIdList;
	}

	public void setTraderIdList(List<Integer> traderIdList) {
		this.traderIdList = traderIdList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Integer getPaymentType() {
	    return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
	    this.paymentType = paymentType;
	}

	public Integer getAfterSalesPaymentType() {
		return afterSalesPaymentType;
	}

	public void setAfterSalesPaymentType(Integer afterSalesPaymentType) {
		this.afterSalesPaymentType = afterSalesPaymentType;
	}

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}
}