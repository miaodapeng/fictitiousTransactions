package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceApply implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer invoiceApplyId;
    
    private Integer companyId;

    private Integer type;//开票申请类型 字典库
    
//    private Integer invoiceProperty;//1纸质发票2电子发票

    private Integer relatedId;//关联表ID

    private Integer isAuto;//开票型式1手动2自动3电子发票

    private Integer isAdvance;//是否提前申请开票 0:否1：是
    
    private Integer advanceValidStatus;//提前申请审核 0待审核 1通过 不通过
    
    private Integer advanceValidUserid;//提前申请审核人ID
    
    private Long advanceValidTime;//提前申请最后一次审核时间
    
    private String advanceValidComments;//提前申请审核备注
    
    private Integer yyValidStatus,yyValidUserid;
    
    private Long yyValidTime;
    
    private String yyValidComments;

    private Integer validStatus;//审核 0待审核 1通过 不通过
    
    private Integer validUserId;//

    private Long validTime;//最后一次审核时间

    private String validComments;//审核备注

    private Integer invoiceStStatus;//待开发票状态 1：待开 2：已经获取

    private Integer invoicePrintStatus;//发票打印状态 0：未打印 1：已打印
    
    private String comments;//备注(提前开票申请备注)

	private Integer isSign;// 是否标记开票0否 1是

    private Long addTime;

    private Integer creator;
    
    private Long modTime;

    private Integer updater;
    
    private String saleorderNo,afterSalesNo;//订单号
    
    private Integer traderId;//客户ID
    
    private String traderName;//客户名称
    
    private List<Integer> traderIdList;
    
    private Integer orgId;//销售部门
    
    private Integer invoiceType;//发票类型
    
    private String invoiceTypeStr;//发票类型
    
    private BigDecimal totalAmount;//订单总额
    
    private Integer paymentStatus;//付款状态
    
    private Integer deliveryStatus;//发货状态
    
    private Integer arrivalStatus;//收货状态

	private Long arrivalTime;//收货时间
    
    private Integer serviceStatus;//售后状态
    
    private Integer traderUserId;//归属销售人员
    
    private BigDecimal totalMoney;//总金额
    
    private Integer dateType;//日期类型
    
    private Long startDate;//开始时间
    
    private Long endDate;//结束时间
    
    private List<Integer> invoiceApplyIdList;
    
    private String userName,optType;//操作人
    
    private String creatorNm,advanceValidUserNm,addTimeStr,orgNm,advanceValidTimeStr;

    private Integer countNum,verifyStatus,isCollect;//总数，审核状态，是否集中开票
    
    private String verifyUsername,customerLevel;// 审核人，客户等级

	// add by Tomcat.Hui 2019/9/23 11:16 .Desc:VDERP-1214 开票申请界面优化 增加订单的 INVOICE_COMMENTS字段 . start
	private String invoiceComments;

	public String getInvoiceComments() {
		return invoiceComments;
	}

	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}

	// add by Tomcat.Hui 2019/9/23 11:16 .Desc: VDERP-1214 开票申请界面优化 增加订单的 INVOICE_COMMENTS字段. end

	// add by Tomcat.Hui 2019/11/15 13:43 .Desc:VDERP-1325 分批开票 . start

	private List<InvoiceApplyDetail> invoiceApplyDetails;

	public List<InvoiceApplyDetail> getInvoiceApplyDetails() {
		return invoiceApplyDetails;
	}

	public void setInvoiceApplyDetails(List<InvoiceApplyDetail> invoiceApplyDetails) {
		this.invoiceApplyDetails = invoiceApplyDetails;
	}

	private BigDecimal applyAmount;

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	/**
	 * 申请方式
	 */
	private Integer applyMethod;

	public Integer getApplyMethod() {
		return applyMethod;
	}

	public void setApplyMethod(Integer applyMethod) {
		this.applyMethod = applyMethod;
	}
	// add by Tomcat.Hui 2019/11/15 13:43 .Desc:VDERP-1325 分批开票 . end

	// add by Tomcat.Hui 2020/1/3 17:32 .Desc: VDERP-1039 票货同行. start
	private Integer expressId;

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	// add by Tomcat.Hui 2020/1/3 17:32 .Desc: VDERP-1039 票货同行. end


	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyUsername() {
		return verifyUsername;
	}

	public void setVerifyUsername(String verifyUsername) {
		this.verifyUsername = verifyUsername;
	}

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public Integer getYyValidStatus() {
		return yyValidStatus;
	}

	public void setYyValidStatus(Integer yyValidStatus) {
		this.yyValidStatus = yyValidStatus;
	}

	public Integer getYyValidUserid() {
		return yyValidUserid;
	}

	public void setYyValidUserid(Integer yyValidUserid) {
		this.yyValidUserid = yyValidUserid;
	}

	public Long getYyValidTime() {
		return yyValidTime;
	}

	public void setYyValidTime(Long yyValidTime) {
		this.yyValidTime = yyValidTime;
	}

	public String getYyValidComments() {
		return yyValidComments;
	}

	public void setYyValidComments(String yyValidComments) {
		this.yyValidComments = yyValidComments;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public Integer getValidUserId() {
		return validUserId;
	}

	public void setValidUserId(Integer validUserId) {
		this.validUserId = validUserId;
	}

	public String getAdvanceValidTimeStr() {
		return advanceValidTimeStr;
	}

	public void setAdvanceValidTimeStr(String advanceValidTimeStr) {
		this.advanceValidTimeStr = advanceValidTimeStr;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getAdvanceValidUserNm() {
		return advanceValidUserNm;
	}

	public void setAdvanceValidUserNm(String advanceValidUserNm) {
		this.advanceValidUserNm = advanceValidUserNm;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Integer getAdvanceValidUserid() {
		return advanceValidUserid;
	}

	public void setAdvanceValidUserid(Integer advanceValidUserid) {
		this.advanceValidUserid = advanceValidUserid;
	}

	public Integer getAdvanceValidStatus() {
		return advanceValidStatus;
	}

	public void setAdvanceValidStatus(Integer advanceValidStatus) {
		this.advanceValidStatus = advanceValidStatus;
	}

	public Long getAdvanceValidTime() {
		return advanceValidTime;
	}

	public void setAdvanceValidTime(Long advanceValidTime) {
		this.advanceValidTime = advanceValidTime;
	}

	public String getAdvanceValidComments() {
		return advanceValidComments;
	}

	public void setAdvanceValidComments(String advanceValidComments) {
		this.advanceValidComments = advanceValidComments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Integer> getInvoiceApplyIdList() {
		return invoiceApplyIdList;
	}

	public void setInvoiceApplyIdList(List<Integer> invoiceApplyIdList) {
		this.invoiceApplyIdList = invoiceApplyIdList;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getTraderIdList() {
		return traderIdList;
	}

	public void setTraderIdList(List<Integer> traderIdList) {
		this.traderIdList = traderIdList;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getTraderUserId() {
		return traderUserId;
	}

	public void setTraderUserId(Integer traderUserId) {
		this.traderUserId = traderUserId;
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
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

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public Integer getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getInvoiceApplyId() {
        return invoiceApplyId;
    }

    public void setInvoiceApplyId(Integer invoiceApplyId) {
        this.invoiceApplyId = invoiceApplyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Integer getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    public Integer getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(Integer isAdvance) {
        this.isAdvance = isAdvance;
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

    public String getValidComments() {
        return validComments;
    }

    public void setValidComments(String validComments) {
        this.validComments = validComments == null ? null : validComments.trim();
    }

    public Integer getInvoiceStStatus() {
        return invoiceStStatus;
    }

    public void setInvoiceStStatus(Integer invoiceStStatus) {
        this.invoiceStStatus = invoiceStStatus;
    }

    public Integer getInvoicePrintStatus() {
        return invoicePrintStatus;
    }

    public void setInvoicePrintStatus(Integer invoicePrintStatus) {
        this.invoicePrintStatus = invoicePrintStatus;
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

	
}