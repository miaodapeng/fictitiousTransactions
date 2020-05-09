package com.vedeng.finance.model.vo;

import java.math.BigDecimal;

import com.vedeng.finance.model.PayApply;

public class PayApplyVo extends PayApply {
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer payApplyDetailId;

    private Integer detailgoodsId;

    private BigDecimal price;

    private BigDecimal num;

    private BigDecimal totalAmount;
    
    private Integer traderType;
    
    private Integer isUseBalance;//1：使用余额；2：不使用
    
    private Integer traderFinanceId;//供应商财务信息
    
    private Integer traderSupplierId;//供应商id
    
    private Integer afterSalesInstallstionId;//售后按调id
    
    private Integer engineerId;//工程师id
    
	public Integer getAfterSalesInstallstionId() {
		return afterSalesInstallstionId;
	}

	public void setAfterSalesInstallstionId(Integer afterSalesInstallstionId) {
		this.afterSalesInstallstionId = afterSalesInstallstionId;
	}

	public Integer getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(Integer engineerId) {
		this.engineerId = engineerId;
	}

	public Integer getPayApplyDetailId() {
		return payApplyDetailId;
	}

	public void setPayApplyDetailId(Integer payApplyDetailId) {
		this.payApplyDetailId = payApplyDetailId;
	}

	public Integer getDetailgoodsId() {
		return detailgoodsId;
	}

	public void setDetailgoodsId(Integer detailgoodsId) {
		this.detailgoodsId = detailgoodsId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public Integer getIsUseBalance() {
		return isUseBalance;
	}

	public void setIsUseBalance(Integer isUseBalance) {
		this.isUseBalance = isUseBalance;
	}

	public Integer getTraderFinanceId() {
		return traderFinanceId;
	}

	public void setTraderFinanceId(Integer traderFinanceId) {
		this.traderFinanceId = traderFinanceId;
	}

	public Integer getTraderSupplierId() {
		return traderSupplierId;
	}

	public void setTraderSupplierId(Integer traderSupplierId) {
		this.traderSupplierId = traderSupplierId;
	}

}
