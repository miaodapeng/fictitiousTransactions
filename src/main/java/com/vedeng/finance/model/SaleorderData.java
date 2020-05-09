package com.vedeng.finance.model;

import com.vedeng.common.util.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Description:</b><br> 销售订单
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.model.common
 * <br><b>ClassName:</b> SaleorderData
 * <br><b>Date:</b> 2017年10月16日 上午10:13:39
 */
public class SaleorderData implements Serializable{
	
	private Integer saleorderId;//订单ID
	
	private BigDecimal paymentAmount;//订单已收款金额(不含账期)
	
	private BigDecimal periodAmount;//订单已收款账期金额
	
	private BigDecimal lackAccountPeriodAmount;//剩余账期未还金额
	
	private Integer arrivalNum;//客户收货数量（订单详情）
	
	private BigDecimal realAmount;//订单金额（总额-退款金额）

	// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. start
	private BigDecimal refundBalanceAmount;

	public BigDecimal getRefundBalanceAmount() {
		return refundBalanceAmount;
	}

	public void setRefundBalanceAmount(BigDecimal refundBalanceAmount) {
		this.refundBalanceAmount = refundBalanceAmount;
	}
	// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. end

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	public BigDecimal getLackAccountPeriodAmount() {
		return lackAccountPeriodAmount;
	}

	public void setLackAccountPeriodAmount(BigDecimal lackAccountPeriodAmount) {
		this.lackAccountPeriodAmount = lackAccountPeriodAmount;
	}

	public Integer getArrivalNum() {
		return arrivalNum;
	}

	public void setArrivalNum(Integer arrivalNum) {
		this.arrivalNum = arrivalNum;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

}
