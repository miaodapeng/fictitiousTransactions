package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.vedeng.logistics.model.Express;
import com.vedeng.order.model.BuyorderGoods;
import com.vedeng.order.model.vo.SaleorderGoodsVo;

public class Invoice implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer invoiceId;
    
    private Integer companyId;

    private Integer type;//开票申请类型 字典库
    
    private Integer tag;//录票/开票 1开票 2录票

    private Integer relatedId,afterSalesId;//关联表ID

    private String invoiceNo;//发票号码

    private Integer invoiceType;//发票类型 字典库

    private BigDecimal ratio;//发票税率

    private Integer colorType;//红蓝字 1红2蓝

    private BigDecimal amount;//发票金额
    
    private BigDecimal ratioAmount;//税额
    
    private BigDecimal noRatioAmount;//不含税金额

    private Integer isEnable;//是否有效 0否 1是

    private Integer validStatus;//审核 0待审核 1通过 不通过
    
    private Integer validUserId;//审核人

    private Long validTime;//最后一次审核时间

    private String validComments;//审核备注

    private Integer invoicePrintStatus;//发票打印状态 0：未打印 1：已打印

    private Integer invoiceCancelStatus;//发票作废状态 0：未作废 1：已作废
    
    private Integer expressId;//快递表主键

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String validUserName;//审核人

    private List<InvoiceDetail> invoiceDetailList;//发票详情列表
    
    private List<Integer> invoiceIdList;//主键集合
    
    private List<Integer> relatedIdList;//外键集合
    
    private List<BigDecimal> invoiceNumList;//录票数量集合
    
    private List<BigDecimal> invoicePriceList;//录票单价集合
    
    private List<BigDecimal> invoiceTotleAmountList;//录票总价集合
    
    private List<Integer> detailGoodsIdList;//销售产品表ID
    
    private Integer traderId;//客户ID
    
    private String traderName;//客户名称
    
    private String invoiceTypeStr;//发票类型
    
    private String creatorName;//创建人员
    
    private String validUserNm;//审核人员
    
    private String updaterName;//修改人员
    
    private String buyorderNo;//采购单号
    
    private String saleorderNo;//销售单号
    
    private String userName;//销售人员
    
    private Integer isSendInvoice;//发票是否寄送
    
    
    private BigDecimal startAmount;//起始发票金额
    private BigDecimal endAmount;//截止发票金额
    
    private Integer dateType;//日期类型
    private Long startDate;//开始时间
    private Long endDate;//结束时间
    
    private Integer invoiceCount;//全部记录条数
    private BigDecimal amountCount;//发票总金额
    
    private BuyorderGoods buyorderGoods;//采购单
    
    private Express express;//快递信息
    
    private Integer sendUserId;//寄送人
    
    private String comments;//备注
    
    private Integer afterId;//售后单号
    
    private Integer afterSubjectType;//售后主体类型
    
    private Integer afterType;//售后类型
    
    private Integer atferStatus;//售后状态
    
    private Integer lzyxNum;//蓝字有效数量
    
    private Integer lzzfNum;//蓝字作废数量
    
    private Integer hzyxNum;//红字有效数量

    private BigDecimal lzyxAmount,lzyxTaxAmount,lzyxTaxFreeAmount;//蓝字有效金额-税额-不含税额

    private BigDecimal lzzfAmount,lzzfTaxAmount,lzzfTaxFreeAmount;//蓝字作废金额-税额-不含税额

    private BigDecimal hzyxAmount,hzyxTaxAmount,hzyxTaxFreeAmount;//红字有效金额-税额-不含税额

    private List<Integer> typeList;//开票类型（销售和售后，采购和售后）
    
    private List<SaleorderGoodsVo> sgvList;
    
    private String addTimeStr,validTimeStr;
    
    private Integer invoiceNum;//数量
    private BigDecimal invoicePrice;//单价
    
    private String jsInvoiceNo;
    
    private Integer orgId,userId;//部门、归属人员（售后-归属售后人员、销售-客户负责人）
    private String orgNm,userNm,sendUserNm;
    private String invoiceTraderContactName;
    
    private String invoiceArea,invoiceAddress;//发票收票地区、地址
    
    private String invoiceTraderContactMobile,invoiceTraderContactTelephone;//收票人联系手机、电话
    
    private String financeVoucherNo;//金蝶凭证号
    private Integer financeVoucherNoId;//金蝶凭证号id
    
    private Integer isAuth;//是否已认证
    
    private String amountStatus;//凭证号
    private Integer isMonthAuth;
    
    /////////////////////电子发票////////////////////////////////
    private Integer invoiceProperty;
    private String invoiceHref;//下载地址
    private String invoiceFlow;//发票流水号
    private String invoiceCode;//发票代码

    private Integer sendResult;//发送结果
	
	/****************************************************************************/
	// bert as 20191119 开票记录(修改页面的备注信息[改成开票收票信息的开票备注])
	private String invoiceComments;

	private String logisticsNo;//快递单号

	private Integer traderContactId;//销售订单联系人ID

	/**
	 *
	 * 订单下客户联系手机号
	 */
	private String traderContactMobile;

	/**
	 * 订单类型
	 */
	private Integer orderType;

	/**
	 * 物流公司名称
	 */
	private String logisticsName;

    // add by Tomcat.Hui 2019/11/21 19:28 .Desc:  VDERP-1325 分批开票 增加发票表开票申请ID字段. start
    private Integer invoiceApplyId;

    public Integer getInvoiceApplyId() {
        return invoiceApplyId;
    }

    public void setInvoiceApplyId(Integer invoiceApplyId) {
        this.invoiceApplyId = invoiceApplyId;
    }

    // add by Tomcat.Hui 2019/11/21 19:28 .Desc:  VDERP-1325 分批开票 增加发票表开票申请ID字段. end

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getInvoiceComments() {
		return invoiceComments;
	}
	
	public void setInvoiceComments(String invoiceComments) {
		this.invoiceComments = invoiceComments;
	}


    public BigDecimal getLzzfTaxAmount() {
        return lzzfTaxAmount;
    }

    public void setLzzfTaxAmount(BigDecimal lzzfTaxAmount) {
        this.lzzfTaxAmount = lzzfTaxAmount;
    }

    public BigDecimal getLzzfTaxFreeAmount() {
        return lzzfTaxFreeAmount;
    }

    public void setLzzfTaxFreeAmount(BigDecimal lzzfTaxFreeAmount) {
        this.lzzfTaxFreeAmount = lzzfTaxFreeAmount;
    }

    public BigDecimal getHzyxTaxAmount() {
        return hzyxTaxAmount;
    }

    public void setHzyxTaxAmount(BigDecimal hzyxTaxAmount) {
        this.hzyxTaxAmount = hzyxTaxAmount;
    }

    public BigDecimal getHzyxTaxFreeAmount() {
        return hzyxTaxFreeAmount;
    }

    public void setHzyxTaxFreeAmount(BigDecimal hzyxTaxFreeAmount) {
        this.hzyxTaxFreeAmount = hzyxTaxFreeAmount;
    }

    public BigDecimal getLzyxTaxAmount() {
        return lzyxTaxAmount;
    }

    public void setLzyxTaxAmount(BigDecimal lzyxTaxAmount) {
        this.lzyxTaxAmount = lzyxTaxAmount;
    }

    public BigDecimal getLzyxTaxFreeAmount() {
        return lzyxTaxFreeAmount;
    }

    public void setLzyxTaxFreeAmount(BigDecimal lzyxTaxFreeAmount) {
        this.lzyxTaxFreeAmount = lzyxTaxFreeAmount;
    }

    /****************************************************************************/
	
	
	public Integer getSendResult() {
		return sendResult;
	}

	public BigDecimal getLzyxAmount() {
		return lzyxAmount;
	}

	public void setLzyxAmount(BigDecimal lzyxAmount) {
		this.lzyxAmount = lzyxAmount;
	}

	public BigDecimal getLzzfAmount() {
		return lzzfAmount;
	}

	public void setLzzfAmount(BigDecimal lzzfAmount) {
		this.lzzfAmount = lzzfAmount;
	}

	public BigDecimal getHzyxAmount() {
		return hzyxAmount;
	}

	public void setHzyxAmount(BigDecimal hzyxAmount) {
		this.hzyxAmount = hzyxAmount;
	}

	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}

	public Integer getFinanceVoucherNoId() {
		return financeVoucherNoId;
	}

	public void setFinanceVoucherNoId(Integer financeVoucherNoId) {
		this.financeVoucherNoId = financeVoucherNoId;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceFlow() {
		return invoiceFlow;
	}

	public void setInvoiceFlow(String invoiceFlow) {
		this.invoiceFlow = invoiceFlow;
	}

	public Integer getInvoiceProperty() {
		return invoiceProperty;
	}

	public void setInvoiceProperty(Integer invoiceProperty) {
		this.invoiceProperty = invoiceProperty;
	}

	public String getInvoiceHref() {
		return invoiceHref;
	}

	public void setInvoiceHref(String invoiceHref) {
		this.invoiceHref = invoiceHref;
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

	public String getJsInvoiceNo() {
		return jsInvoiceNo;
	}

	public void setJsInvoiceNo(String jsInvoiceNo) {
		this.jsInvoiceNo = jsInvoiceNo;
	}

	public String getAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(String amountStatus) {
		this.amountStatus = amountStatus;
	}

	public BigDecimal getRatioAmount() {
		return ratioAmount;
	}

	public void setRatioAmount(BigDecimal ratioAmount) {
		this.ratioAmount = ratioAmount;
	}

	public BigDecimal getNoRatioAmount() {
		return noRatioAmount;
	}

	public void setNoRatioAmount(BigDecimal noRatioAmount) {
		this.noRatioAmount = noRatioAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFinanceVoucherNo() {
		return financeVoucherNo;
	}

	public void setFinanceVoucherNo(String financeVoucherNo) {
		this.financeVoucherNo = financeVoucherNo;
	}

    private Long authTime;
    
	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public Integer getIsMonthAuth() {
		return isMonthAuth;
	}

	public void setIsMonthAuth(Integer isMonthAuth) {
		this.isMonthAuth = isMonthAuth;
	}

	public Long getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Long authTime) {
		this.authTime = authTime;
	}

	public Integer getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(Integer afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getValidUserName() {
		return validUserName;
	}

	public void setValidUserName(String validUserName) {
		this.validUserName = validUserName;
	}

	public String getInvoiceArea() {
		return invoiceArea;
	}

	public void setInvoiceArea(String invoiceArea) {
		this.invoiceArea = invoiceArea;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
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

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getSendUserNm() {
		return sendUserNm;
	}

	public void setSendUserNm(String sendUserNm) {
		this.sendUserNm = sendUserNm;
	}

	public Integer getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(Integer invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public BigDecimal getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(BigDecimal invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public BuyorderGoods getBuyorderGoods() {
		return buyorderGoods;
	}

	public void setBuyorderGoods(BuyorderGoods buyorderGoods) {
		this.buyorderGoods = buyorderGoods;
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
    
	public String getValidUserNm() {
		return validUserNm;
	}

	public void setValidUserNm(String validUserNm) {
		this.validUserNm = validUserNm;
	}

	public Integer getValidUserId() {
		return validUserId;
	}

	public void setValidUserId(Integer validUserId) {
		this.validUserId = validUserId;
	}

	public List<Integer> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Integer> typeList) {
		this.typeList = typeList;
	}

	public List<SaleorderGoodsVo> getSgvList() {
		return sgvList;
	}

	public void setSgvList(List<SaleorderGoodsVo> sgvList) {
		this.sgvList = sgvList;
	}

	public Integer getAtferStatus() {
		return atferStatus;
	}

	public void setAtferStatus(Integer atferStatus) {
		this.atferStatus = atferStatus;
	}

	public Integer getAfterSubjectType() {
		return afterSubjectType;
	}

	public void setAfterSubjectType(Integer afterSubjectType) {
		this.afterSubjectType = afterSubjectType;
	}

	public Integer getLzyxNum() {
		return lzyxNum;
	}

	public void setLzyxNum(Integer lzyxNum) {
		this.lzyxNum = lzyxNum;
	}

	public Integer getLzzfNum() {
		return lzzfNum;
	}

	public void setLzzfNum(Integer lzzfNum) {
		this.lzzfNum = lzzfNum;
	}

	public Integer getHzyxNum() {
		return hzyxNum;
	}

	public void setHzyxNum(Integer hzyxNum) {
		this.hzyxNum = hzyxNum;
	}

	public Integer getAfterType() {
		return afterType;
	}

	public void setAfterType(Integer afterType) {
		this.afterType = afterType;
	}
	
	public List<InvoiceDetail> getInvoiceDetailList() {
		return invoiceDetailList;
	}

	public void setInvoiceDetailList(List<InvoiceDetail> invoiceDetailList) {
		this.invoiceDetailList = invoiceDetailList;
	}

	public Integer getAfterId() {
		return afterId;
	}

	public void setAfterId(Integer afterId) {
		this.afterId = afterId;
	}

	public List<Integer> getInvoiceIdList() {
		return invoiceIdList;
	}

	public void setInvoiceIdList(List<Integer> invoiceIdList) {
		this.invoiceIdList = invoiceIdList;
	}

	public Integer getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Express getExpress() {
		return express;
	}

	public void setExpress(Express express) {
		this.express = express;
	}

	public List<Integer> getDetailGoodsIdList() {
		return detailGoodsIdList;
	}

	public void setDetailGoodsIdList(List<Integer> detailGoodsIdList) {
		this.detailGoodsIdList = detailGoodsIdList;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
    public Integer getInvoiceCount() {
		return invoiceCount;
	}

	public void setInvoiceCount(Integer invoiceCount) {
		this.invoiceCount = invoiceCount;
	}

	public BigDecimal getAmountCount() {
		return amountCount;
	}

	public void setAmountCount(BigDecimal amountCount) {
		this.amountCount = amountCount;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
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
	
    public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}
    
    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
    
    public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
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

	public List<BigDecimal> getInvoiceTotleAmountList() {
		return invoiceTotleAmountList;
	}

	public void setInvoiceTotleAmountList(List<BigDecimal> invoiceTotleAmountList) {
		this.invoiceTotleAmountList = invoiceTotleAmountList;
	}

	public List<BigDecimal> getInvoiceNumList() {
		return invoiceNumList;
	}

	public void setInvoiceNumList(List<BigDecimal> invoiceNumList) {
		this.invoiceNumList = invoiceNumList;
	}

	public List<BigDecimal> getInvoicePriceList() {
		return invoicePriceList;
	}

	public void setInvoicePriceList(List<BigDecimal> invoicePriceList) {
		this.invoicePriceList = invoicePriceList;
	}

	public List<Integer> getRelatedIdList() {
		return relatedIdList;
	}

	public void setRelatedIdList(List<Integer> relatedIdList) {
		this.relatedIdList = relatedIdList;
	}

	public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public Integer getColorType() {
        return colorType;
    }

    public void setColorType(Integer colorType) {
        this.colorType = colorType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public Integer getInvoicePrintStatus() {
        return invoicePrintStatus;
    }

    public void setInvoicePrintStatus(Integer invoicePrintStatus) {
        this.invoicePrintStatus = invoicePrintStatus;
    }

    public Integer getInvoiceCancelStatus() {
        return invoiceCancelStatus;
    }

    public void setInvoiceCancelStatus(Integer invoiceCancelStatus) {
        this.invoiceCancelStatus = invoiceCancelStatus;
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

	public String getInvoiceTraderContactName() {
		return invoiceTraderContactName;
	}

	public void setInvoiceTraderContactName(String invoiceTraderContactName) {
		this.invoiceTraderContactName = invoiceTraderContactName;
	}

	public String getTraderContactMobile() {
		return traderContactMobile;
	}

	public void setTraderContactMobile(String traderContactMobile) {
		this.traderContactMobile = traderContactMobile;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
}
