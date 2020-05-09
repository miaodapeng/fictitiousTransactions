package com.vedeng.finance.model.vo;

import java.math.BigDecimal;

import com.vedeng.finance.model.InvoiceDetail;

public class InvoiceDetailVo extends InvoiceDetail {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer invoiceId;

    private Integer companyId;
    
    private Integer type;//开票申请类型 字典库

    private Integer relatedId;//关联表ID

    private String invoiceNo;//发票号码

    private Integer invoiceType;//发票类型 字典库

    private BigDecimal ratio;//发票税率

    private Integer colorType;//红蓝字 1红2蓝

    private BigDecimal amount;//发票金额

    private Integer isEnable;//是否有效 0否 1是

    private Integer validStatus;//审核 0待审核 1通过 不通过

    private Long validTime;//最后一次审核时间

    private String validComments;//审核备注

    private Integer invoicePrintStatus;//发票打印状态 0：未打印 1：已打印

    private Integer invoiceCancelStatus;//发票作废状态 0：未作废 1：已作废
    
    private Integer expressId;//快递表主键

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
		this.invoiceNo = invoiceNo;
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
		this.validComments = validComments;
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

	public Integer getExpressId() {
		return expressId;
	}

	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
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

}
