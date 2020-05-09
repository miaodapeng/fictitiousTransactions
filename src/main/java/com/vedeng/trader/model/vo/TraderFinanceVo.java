package com.vedeng.trader.model.vo;

import java.math.BigDecimal;

import com.vedeng.trader.model.TraderFinance;

/**
 * <b>Description:</b><br> DTO
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.model.vo
 * <br><b>ClassName:</b> TraderFinanceVo
 * <br><b>Date:</b> 2017年9月22日 上午9:50:56
 */
public class TraderFinanceVo extends TraderFinance {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal amount;//账户余额
	private BigDecimal periodAmount;//账期额度
	private Integer periodDay;//账期天数
	private BigDecimal balanceAccount;//账期余额
	
	private Integer canApplyPeriod;//是否可以申请帐期；0-否；1-是
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}
	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}
	public Integer getPeriodDay() {
		return periodDay;
	}
	public void setPeriodDay(Integer periodDay) {
		this.periodDay = periodDay;
	}
	public BigDecimal getBalanceAccount() {
		return balanceAccount;
	}
	public void setBalanceAccount(BigDecimal balanceAccount) {
		this.balanceAccount = balanceAccount;
	}
	public Integer getCanApplyPeriod() {
		return canApplyPeriod;
	}
	public void setCanApplyPeriod(Integer canApplyPeriod) {
		this.canApplyPeriod = canApplyPeriod;
	}
}
