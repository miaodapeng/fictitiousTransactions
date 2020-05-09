package com.vedeng.aftersales.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.aftersales.model.AfterSalesInvoice;

public class AfterSalesInvoiceVo extends AfterSalesInvoice {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String invoiceNo;//发票号码
	
	private String invoiceCode;//发票代码
	
	private BigDecimal amount;//发票金额
	
	private Integer invoiceType;//发票类型 字典库
	
	private Integer invoiceTag;//录票/开票 1开票 2录票
	
	private String invoiceTypeName;//发票类型名称 字典库
	
	private Integer expressId;//是否寄送（非0-已寄送）
	
	private Integer colorType;//发票颜色
	
	private Integer isEnable;//是否有效
	
	private Integer creator;//创建人
	
	private Long addTime;//创建时间
	
	private String logisticsName;//物流名称
	
	private String logisticsNo;//物流单号
	
	private Long deliveryTime;//寄送时间
	
	private Integer arrivalStatus;//收货状态
	
	private String logisticsComments;//物流备注
	
	private String creatorName;//开票人
	
	private Integer subjectType;//售后主体类型:535销售，536采购，537第三方
	
	private Integer currentMonthInvoice;//当前月发票：1是：0否

	private List<AfterSalesGoodsVo> afterGoodsList;//售后产品列表
	
    private BigDecimal ratio;//发票税率
    
    private Long validTime;//发票审核时间
    
    private Integer validStatus;//审核 0待审核 1通过 不通过

    private Integer validUserId;//审核人
    
    private String validUsername;//审核人名称

    private Integer isSendInvoice;//售后发票是否寄送

    private Integer afterType;//售后类型 
    
    private Integer orderId;
    
    private Integer invoiceId;//发票Id

    private Integer invoiceProperty;//1纸质发票2电子发票    
    
    private BigDecimal amountCount;//发票总金额
    
    
    
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public BigDecimal getAmountCount() {
		return amountCount;
	}

	public void setAmountCount(BigDecimal amountCount) {
		this.amountCount = amountCount;
	}

	public Integer getInvoiceProperty() {
		return invoiceProperty;
	}

	public void setInvoiceProperty(Integer invoiceProperty) {
		this.invoiceProperty = invoiceProperty;
	}
    
	public Integer getInvoiceTag() {
		return invoiceTag;
	}

	public void setInvoiceTag(Integer invoiceTag) {
		this.invoiceTag = invoiceTag;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
    
	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getAfterType() {
		return afterType;
	}

	public void setAfterType(Integer afterType) {
		this.afterType = afterType;
	}

	public Integer getIsSendInvoice() {
		return isSendInvoice;
	}

	public void setIsSendInvoice(Integer isSendInvoice) {
		this.isSendInvoice = isSendInvoice;
	}
    
	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}
    
	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	
	public List<AfterSalesGoodsVo> getAfterGoodsList() {
		return afterGoodsList;
	}

	public void setAfterGoodsList(List<AfterSalesGoodsVo> afterGoodsList) {
		this.afterGoodsList = afterGoodsList;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Integer getCurrentMonthInvoice() {
		return currentMonthInvoice;
	}

	public void setCurrentMonthInvoice(Integer currentMonthInvoice) {
		this.currentMonthInvoice = currentMonthInvoice;
	}

	public Integer getColorType() {
		return colorType;
	}

	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
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

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public String getLogisticsComments() {
		return logisticsComments;
	}

	public void setLogisticsComments(String logisticsComments) {
		this.logisticsComments = logisticsComments;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public Integer getValidUserId() {
		return validUserId;
	}

	public void setValidUserId(Integer validUserId) {
		this.validUserId = validUserId;
	}

	public String getValidUsername() {
		return validUsername;
	}

	public void setValidUsername(String validUsername) {
		this.validUsername = validUsername;
	}

}
