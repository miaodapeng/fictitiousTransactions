package com.vedeng.system.model.vo;

import com.vedeng.system.model.ParamsConfig;

/**
 * <b>Description:</b><br> 参数设置DTO
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.model.vo
 * <br><b>ClassName:</b> ParamsConfigVo
 * <br><b>Date:</b> 2017年12月4日 上午10:14:12
 */
public class ParamsConfigVo extends ParamsConfig {
	
    private Integer paramsConfigValueId;

    private Integer companyId;

    private String paramsValue;
    
    private String logistics;//快递超时天数
    private String quote;//报价有效期
    private String sale;//订单有效期
    private String quoteorder;//报价单有效期
    private Integer [] paramsConfigIds;//主键集合
    
    private String defaultCharge;//默认的负责人
    
    private String [] orgCharges;//部门售后负责人

	public Integer getParamsConfigValueId() {
		return paramsConfigValueId;
	}

	public void setParamsConfigValueId(Integer paramsConfigValueId) {
		this.paramsConfigValueId = paramsConfigValueId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public String getQuoteorder() {
		return quoteorder;
	}

	public void setQuoteorder(String quoteorder) {
		this.quoteorder = quoteorder;
	}

	public Integer[] getParamsConfigIds() {
		return paramsConfigIds;
	}

	public void setParamsConfigIds(Integer[] paramsConfigIds) {
		this.paramsConfigIds = paramsConfigIds;
	}

	public String getDefaultCharge() {
		return defaultCharge;
	}

	public void setDefaultCharge(String defaultCharge) {
		this.defaultCharge = defaultCharge;
	}

	public String[] getOrgCharges() {
		return orgCharges;
	}

	public void setOrgCharges(String[] orgCharges) {
		this.orgCharges = orgCharges;
	}

}
