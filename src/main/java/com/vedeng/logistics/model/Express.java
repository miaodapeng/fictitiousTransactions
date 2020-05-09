package com.vedeng.logistics.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.logistics.model.ExpressDetail;

public class Express implements Serializable{
    private Integer expressId;
    
    private Integer companyId;

    private Integer logisticsId;

    private String logisticsNo;

    private Long deliveryTime;

    private Integer arrivalStatus;

    private Long arrivalTime;

    private Integer deliveryFrom;

    private String logisticsComments;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Integer isEnable;
    
    private Integer sentSms;
    
    private Integer paymentType;
    
    private String cardnumber;
    
    private Integer business_Type;
    
    private BigDecimal realWeight;
    
    private Integer j_num;
    
    private BigDecimal amountWeight;
    
    private String mailGoods;
    
    private Integer mailGoodsNum;
    
    private Integer isProtectPrice;
    
    private BigDecimal protectPrice;
    
    private Integer isReceipt;
    
    private String mailCommtents;

    private List<ExpressDetail> expressDetail;//快递单详细
    /**
     * T_EXPRESS_DETAIL表中的
     * 
     */
    private Integer expressDetailId;
    /**
     * T_EXPRESS_DETAIL表中的
     * 业务类型 字典表
     */
    private Integer businessType;
    /**
     * T_EXPRESS_DETAIL表中的
     * 关联主表字段ID
     */
    private Integer relatedId;
    /**
     * T_EXPRESS_DETAIL表中的
     * 数量
     */
    private Integer num;
    /**
     * T_EXPRESS_DETAIL表中的
     * 金额
     */
    private BigDecimal amount;
    /**
     * T_EXPRESS_DETAIL表中的
     * 快递名称
     */
    private String logisticsName;
    
    private String logisticsCompanyName;//快递公司名称
    
    private String goodsName;//商品名称
    
    private Long beginTime; //开始时间
    
    private Long endTime; //开始时间
    
    private Long fhTime;//发货时间
    
    private String sjName;//收件名称
    
    private String xsNo;//销售单号
    private String fpNo;//发票号
    
    private List<Integer> relatedIds;//附表关联id的list 
    
    private Integer cnt;//未签收的订单数
    
    private Integer ywId;//业务id
    
    private Integer verifiesType;//审核类型
    
    private String verifyUsername;//当前审核人
    
    private Integer verifyStatus;//审核状态
    
    private Integer  ywType;//售后的具体类型
    
    private Integer saleorderId;//销售订单id
    
    private Integer buyorderId;//采购订单id
    
    private List<Integer> expressIds;
    
    private Integer currentCount;//当前是第几次查询，默认从2开始
    
    private String msgCommtents;//发送短信
    
    private String content;//查询快递后的内容
    
    private String contentNew;//快递单的最新信息
    
    private String code;//快递公司编码
    
    private BigDecimal invoiceAmount;//关联的发票金额
    
    private String invoiceNo;//关联的发票号
    
    private Integer fnum;
    
    private Integer allnum;
	//判断是否开据发票字段
	/*private Integer validStatus;

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}*/

	//是否开据发票
	public Integer getIsInvoicing() {
		return isInvoicing;
	}

	public void setIsInvoicing(Integer isInvoicing) {
		this.isInvoicing = isInvoicing;
	}

	private Integer isInvoicing;


		//是否票货同行1代表同行0为不同行
    private Integer travelingByTicket;

	public Integer getTravelingByTicket() {
		return travelingByTicket;
	}

	public void setTravelingByTicket(Integer travelingByTicket) {
		this.travelingByTicket = travelingByTicket;
	}

	// add by Tomcat.Hui 2019/11/11 11:33 .Desc: VDERP-1325 分批开票 查询已收货数量.. start
	private Integer orderGoodsId;//订单goodsID

	public Integer getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(Integer orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}
	// add by Tomcat.Hui 2019/11/11 11:33 .Desc: VDERP-1325 分批开票 查询已收货数量.. end

	// add by Tomcat.Hui 2020/1/3 17:29 .Desc: VDERP-1039 票货同行. start
	private InvoiceApply invoiceApply;

	public InvoiceApply getInvoiceApply() {
		return invoiceApply;
	}

	public void setInvoiceApply(InvoiceApply invoiceApply) {
		this.invoiceApply = invoiceApply;
	}

	private List<Invoice> invoiceList;

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	// add by Tomcat.Hui 2020/1/3 17:29 .Desc: VDERP-1039 票货同行. end

    
	public Integer getFnum() {
		return fnum;
	}

	public void setFnum(Integer fnum) {
		this.fnum = fnum;
	}

	public Integer getAllnum() {
		return allnum;
	}

	public void setAllnum(Integer allnum) {
		this.allnum = allnum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContentNew() {
		return contentNew;
	}

	public void setContentNew(String contentNew) {
		this.contentNew = contentNew;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgCommtents() {
		return msgCommtents;
	}

	public void setMsgCommtents(String msgCommtents) {
		this.msgCommtents = msgCommtents;
	}

	public Integer getSentSms() {
		return sentSms;
	}

	public void setSentSms(Integer sentSms) {
		this.sentSms = sentSms;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public Integer getBusiness_Type() {
		return business_Type;
	}

	public void setBusiness_Type(Integer business_Type) {
		this.business_Type = business_Type;
	}

	public BigDecimal getRealWeight() {
		return realWeight;
	}

	public void setRealWeight(BigDecimal realWeight) {
		this.realWeight = realWeight;
	}

	public Integer getJ_num() {
		return j_num;
	}

	public void setJ_num(Integer j_num) {
		this.j_num = j_num;
	}

	public BigDecimal getAmountWeight() {
		return amountWeight;
	}

	public void setAmountWeight(BigDecimal amountWeight) {
		this.amountWeight = amountWeight;
	}

	public String getMailGoods() {
		return mailGoods;
	}

	public void setMailGoods(String mailGoods) {
		this.mailGoods = mailGoods;
	}

	public Integer getMailGoodsNum() {
		return mailGoodsNum;
	}

	public void setMailGoodsNum(Integer mailGoodsNum) {
		this.mailGoodsNum = mailGoodsNum;
	}

	public Integer getIsProtectPrice() {
		return isProtectPrice;
	}

	public void setIsProtectPrice(Integer isProtectPrice) {
		this.isProtectPrice = isProtectPrice;
	}

	public BigDecimal getProtectPrice() {
		return protectPrice;
	}

	public void setProtectPrice(BigDecimal protectPrice) {
		this.protectPrice = protectPrice;
	}

	public Integer getIsReceipt() {
		return isReceipt;
	}

	public void setIsReceipt(Integer isReceipt) {
		this.isReceipt = isReceipt;
	}

	public String getMailCommtents() {
		return mailCommtents;
	}

	public void setMailCommtents(String mailCommtents) {
		this.mailCommtents = mailCommtents;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public String getFpNo() {
		return fpNo;
	}

	public void setFpNo(String fpNo) {
		this.fpNo = fpNo;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getYwType() {
		return ywType;
	}

	public void setYwType(Integer ywType) {
		this.ywType = ywType;
	}
    
	public Integer getYwId() {
		return ywId;
	}

	public void setYwId(Integer ywId) {
		this.ywId = ywId;
	}
    
	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	/**
     * 操作人名称
     */
    private String updaterUsername;
    
    private String creatName;
    
    private String fIds;//筛选出的文件id
    
    private Integer csArrivalStatus;//超时状态 0：未超时，1：超时
    
    private Integer  isovertime;//是否超时
    
    private String fhTimeStr;//
    
    private String deliveryArea;//
    
    private String deliveryAddress;//
    
    private String arrivalTimeStr;//
    
    public Integer getIsovertime() {
		return isovertime;
	}

	public void setIsovertime(Integer isovertime) {
		this.isovertime = isovertime;
	}
    
    public Integer getCsArrivalStatus() {
		return csArrivalStatus;
	}

	public void setCsArrivalStatus(Integer csArrivalStatus) {
		this.csArrivalStatus = csArrivalStatus;
	}

	public String getfIds() {
		return fIds;
	}

	public void setfIds(String fIds) {
		this.fIds = fIds;
	}
    
    public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getCreatName() {
		return creatName;
	}

	public void setCreatName(String creatName) {
		this.creatName = creatName;
	}

	public String getXsNo() {
		return xsNo;
	}

	public void setXsNo(String xsNo) {
		this.xsNo = xsNo;
	}
    
    public Long getFhTime() {
		return fhTime;
	}

	public void setFhTime(Long fhTime) {
		this.fhTime = fhTime;
	}

	public String getSjName() {
		return sjName;
	}

	public void setSjName(String sjName) {
		this.sjName = sjName;
	}
    
    public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
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

    public Integer getDeliveryFrom() {
        return deliveryFrom;
    }

    public void setDeliveryFrom(Integer deliveryFrom) {
        this.deliveryFrom = deliveryFrom;
    }

    public String getLogisticsComments() {
        return logisticsComments;
    }

    public void setLogisticsComments(String logisticsComments) {
        this.logisticsComments = logisticsComments == null ? null : logisticsComments.trim();
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

    public Integer getExpressDetailId() {
	return expressDetailId;
    }

    public void setExpressDetailId(Integer expressDetailId) {
	this.expressDetailId = expressDetailId;
    }

    public Integer getBusinessType() {
	return businessType;
    }

    public void setBusinessType(Integer businessType) {
	this.businessType = businessType;
    }

    public Integer getRelatedId() {
	return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
	this.relatedId = relatedId;
    }

    public Integer getNum() {
	return num;
    }

    public void setNum(Integer num) {
	this.num = num;
    }

    public BigDecimal getAmount() {
	return amount;
    }

    public void setAmount(BigDecimal amount) {
	this.amount = amount;
    }

    public String getLogisticsName() {
	return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
	this.logisticsName = logisticsName;
    }

    public List<ExpressDetail> getExpressDetail() {
	return expressDetail;
    }

    public void setExpressDetail(List<ExpressDetail> expressDetail) {
	this.expressDetail = expressDetail;
    }

    public List<Integer> getRelatedIds() {
	return relatedIds;
    }

    public void setRelatedIds(List<Integer> relatedIds) {
	this.relatedIds = relatedIds;
    }

    public String getUpdaterUsername() {
	return updaterUsername;
    }

    public void setUpdaterUsername(String updaterUsername) {
	this.updaterUsername = updaterUsername;
    }

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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

	public String getFhTimeStr() {
		return fhTimeStr;
	}

	public void setFhTimeStr(String fhTimeStr) {
		this.fhTimeStr = fhTimeStr;
	}

	public String getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getArrivalTimeStr() {
		return arrivalTimeStr;
	}

	public void setArrivalTimeStr(String arrivalTimeStr) {
		this.arrivalTimeStr = arrivalTimeStr;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public List<Integer> getExpressIds() {
		return expressIds;
	}

	public void setExpressIds(List<Integer> expressIds) {
		this.expressIds = expressIds;
	}

	public BigDecimal getInvoiceAmount() {
	    return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
	    this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceNo() {
	    return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
	    this.invoiceNo = invoiceNo;
	}

	
}