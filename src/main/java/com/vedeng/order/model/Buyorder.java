package com.vedeng.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Description:</b><br> 采购订单
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.order.model
 * <br><b>ClassName:</b> Buyorder
 * <br><b>Date:</b> 2017年7月11日 上午9:05:19
 */
public class Buyorder implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyName;//非数据库字段，只是用于采购订单生效时，传付款方的名称

	private Integer buyorderId;

    private String buyorderNo;

    private Integer orderType;
    
    private Integer companyId;

    private Integer orgId;

    private Integer userId;

    private Integer validStatus;

    private Long validTime;

    private Integer status;

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

    private Integer deliveryDirect;

    private BigDecimal totalAmount;

    private Integer traderId;

    private String traderName;

    private Integer traderContactId;

    private String traderContactName;

    private String traderContactMobile;

    private String traderContactTelephone;

    private Integer traderAddressId;

    private String traderAddress;
    
    private String traderArea;

    private String traderComments;

    private Integer takeTraderId;

    private String takeTraderName;

    private Integer takeTraderContactId;

    private String takeTraderContactName;

    private String takeTraderContactMobile;

    private String takeTraderContactTelephone;

    private Integer takeTraderAddressId;

    private String takeTraderAddress;
    
    private String takeTraderArea;

    private Integer paymentType;

    private BigDecimal prepaidAmount;

    private BigDecimal accountPeriodAmount;
    
    private Integer periodDay;

    private BigDecimal retainageAmount;

    private Integer retainageAmountMonth;

    private Integer logisticsId;

    private Integer invoiceType;

    private Integer freightDescription;

    private String paymentComments;

    private String logisticsComments;

    private String invoiceComments;

    private String comments;

    private String additionalClause;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String flag;
    
    private List<BuyorderGoods> goodsList;// 订单下的商品列表
    
    private Long satisfyDeliveryTime;
    
    private String bussinessNo;
    
    private String estimateArrivalTime;//预计到货时间
    
    private String delBuyGoodsIds;//删除的buyorderGoodsId集合字符串，逗号间隔
    
    private Integer isContractReturn;//是否合同回传
    
    private Integer isFinanceAlready;//流程节点是否已到财务,付款申请已到财务
    
    public Integer getIsFinanceAlready() {
		return isFinanceAlready;
	}

	public void setIsFinanceAlready(Integer isFinanceAlready) {
		this.isFinanceAlready = isFinanceAlready;
	}

	public Integer getIsContractReturn() {
		return isContractReturn;
	}

	public void setIsContractReturn(Integer isContractReturn) {
		this.isContractReturn = isContractReturn;
	}
    
    public String getDelBuyGoodsIds() {
		return delBuyGoodsIds;
	}

	public void setDelBuyGoodsIds(String delBuyGoodsIds) {
		this.delBuyGoodsIds = delBuyGoodsIds;
	}

	public String getEstimateArrivalTime() {
		return estimateArrivalTime;
	}

	public void setEstimateArrivalTime(String estimateArrivalTime) {
		this.estimateArrivalTime = estimateArrivalTime;
	}

	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
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
        this.buyorderNo = buyorderNo == null ? null : buyorderNo.trim();
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

    public Integer getDeliveryDirect() {
        return deliveryDirect;
    }

    public void setDeliveryDirect(Integer deliveryDirect) {
        this.deliveryDirect = deliveryDirect;
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

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
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

    public String getPaymentComments() {
        return paymentComments;
    }

    public void setPaymentComments(String paymentComments) {
        this.paymentComments = paymentComments == null ? null : paymentComments.trim();
    }

    public String getLogisticsComments() {
        return logisticsComments;
    }

    public void setLogisticsComments(String logisticsComments) {
        this.logisticsComments = logisticsComments == null ? null : logisticsComments.trim();
    }

    public String getInvoiceComments() {
        return invoiceComments;
    }

    public void setInvoiceComments(String invoiceComments) {
        this.invoiceComments = invoiceComments == null ? null : invoiceComments.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getAdditionalClause() {
        return additionalClause;
    }

    public void setAdditionalClause(String additionalClause) {
        this.additionalClause = additionalClause == null ? null : additionalClause.trim();
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

	public String getTakeTraderArea() {
		return takeTraderArea;
	}

	public void setTakeTraderArea(String takeTraderArea) {
		this.takeTraderArea = takeTraderArea;
	}

	public String getTraderArea() {
		return traderArea;
	}

	public void setTraderArea(String traderArea) {
		this.traderArea = traderArea;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public List<BuyorderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<BuyorderGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public Long getSatisfyDeliveryTime() {
		return satisfyDeliveryTime;
	}

	public void setSatisfyDeliveryTime(Long satisfyDeliveryTime) {
		this.satisfyDeliveryTime = satisfyDeliveryTime;
	}
	
}