
package com.smallhospital.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Description: 对账单--订单维度信息</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName StatementBillVo.java
 * <br><b>Date: 2018年8月31日 下午3:57:06 </b> 
 *
 */
public class OrderStatementInfo implements Serializable
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4458175080862636123L;
	
	/**
	 * 序号
	 */
	private Integer no;

	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 订单ID
	 */
	private Integer orderId;
	
	/**
	 * 订单类型
	 */
	private Integer orderType;
	
	/**
	 * 生效时间
	 */
	private String effectiveDate;
	
	/**
	 * 订单实际金额
	 */
	private BigDecimal actualAmount;

	/**
	 * 展示--订单实际金额
	 */
	private String actualAmountString;
	
	/**
	 * 订单收款金额
	 * 不包含账期
	 */
	private BigDecimal collectionAmount;

	/**
	 * 展示 订单收款金额 (不包含账期)
	 */
	private String collectionAmountString;
	
	/**
	 * 未还款 账期金额
	 */
	private BigDecimal accountAmount;

	/**
	 * 展示 -- 未回款 账期金额
	 */
	private String accountAmountString;
	
	/**
	 * 开票时间
	 * 最后一次开票时间
	 */
	private String billDate;
	
	/**
	 * 发票号
	 * 只展示蓝字有效&&没有对应作废的发票号
	 */
	private String ticketNo;
	
	/**
	 * 还款日期 
	 * 为开票时间加30天
	 */
	private String repaymentDate;
	
	/**
	 * 还款日期是否小于制单日期
	 * 1--小于
	 * 0--大于
	 */
	private Integer repaymentDateStatus;

	public Integer getNo()
	{
		return no;
	}

	public void setNo(Integer no)
	{
		this.no = no;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public Integer getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}

	public Integer getOrderType()
	{
		return orderType;
	}

	public void setOrderType(Integer orderType)
	{
		this.orderType = orderType;
	}

	public String getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getActualAmount()
	{
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount)
	{
		this.actualAmount = actualAmount;
	}

	public String getActualAmountString()
	{
		return actualAmountString;
	}

	public void setActualAmountString(String actualAmountString)
	{
		this.actualAmountString = actualAmountString;
	}

	public BigDecimal getCollectionAmount()
	{
		return collectionAmount;
	}

	public void setCollectionAmount(BigDecimal collectionAmount)
	{
		this.collectionAmount = collectionAmount;
	}

	public String getCollectionAmountString()
	{
		return collectionAmountString;
	}

	public void setCollectionAmountString(String collectionAmountString)
	{
		this.collectionAmountString = collectionAmountString;
	}

	public BigDecimal getAccountAmount()
	{
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount)
	{
		this.accountAmount = accountAmount;
	}

	public String getAccountAmountString()
	{
		return accountAmountString;
	}

	public void setAccountAmountString(String accountAmountString)
	{
		this.accountAmountString = accountAmountString;
	}

	public String getBillDate()
	{
		return billDate;
	}

	public void setBillDate(String billDate)
	{
		this.billDate = billDate;
	}

	public String getTicketNo()
	{
		return ticketNo == null ? "" : ticketNo.trim();
	}

	public void setTicketNo(String ticketNo)
	{
		this.ticketNo = ticketNo;
	}

	public String getRepaymentDate()
	{
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate)
	{
		this.repaymentDate = repaymentDate;
	}

	public Integer getRepaymentDateStatus()
	{
		return repaymentDateStatus;
	}

	public void setRepaymentDateStatus(Integer repaymentDateStatus)
	{
		this.repaymentDateStatus = repaymentDateStatus;
	}
	
	
}

