
package com.smallhospital.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <b>Description: 对账单数据模型</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName StatementBillVo.java
 * <br><b>Date: 2018年8月31日 下午3:57:06 </b> 
 *
 */
public class StatementBillVo implements Serializable
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1750927596184738506L;

	/**
	 * 客户ID
	 */
	private Integer traderId;
	
	/**
	 * 客户名称
	 */
	private String traderName;
	
	/**
	 * 公司ID
	 */
	private int companyId;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 对账日期
	 * yyyy年MM月dd日
	 */
	private String statementDate;
	
	/**
	 * 订单维度信息列表
	 */
	private List<OrderStatementInfo> orderList;
	

	/**
	 * 请求类型
	 * 1--订单明细
	 * 2--产品明细
	 */
	private Integer reqType;
	
	/**
	 * 合计
	 * 订单总金额
	 * 
	 */
	private BigDecimal totalActualAmount;

	/**
	 * 展示--订单总金额
	 */
	private String totalActualAmountString;

	/**
	 * 合计
	 * 订单收款金额
	 */
	private BigDecimal totalCollectionAmount;
	
	/**
	 * 展示 -- 合计订单收款金额
	 */
	private String totalCollectionAmountString;

	/**
	 * 合计
	 * 未还款 账期金额
	 */
	private BigDecimal totalAccountAmount;

	/**
	 * 展示--合计未回款账期金额
	 */
	private String totalAccountAmountString;
	
	/**
	 * 合计
	 * 商品数量
	 */
	private Integer totalGoodNum;
	
	/**
	 * 合计
	 * 商品总金额
	 */
	private BigDecimal totalGoodAmount;

	/**
	 * 展示--合计商品总金额
	 */
	private String totalGoodAmountString;
	
	/**
	 * 导出时间
	 */
	private Date searchDate;

	public Integer getTraderId()
	{
		return traderId;
	}

	public void setTraderId(Integer traderId)
	{
		this.traderId = traderId;
	}

	public String getTraderName()
	{
		return traderName;
	}

	public void setTraderName(String traderName)
	{
		this.traderName = traderName;
	}

	public int getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(int companyId)
	{
		this.companyId = companyId;
	}

	public String getCompanyName()
	{
		return companyName;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public String getStatementDate()
	{
		return statementDate;
	}

	public void setStatementDate(String statementDate)
	{
		this.statementDate = statementDate;
	}

	public List<OrderStatementInfo> getOrderList()
	{
		return orderList;
	}

	public void setOrderList(List<OrderStatementInfo> orderList)
	{
		this.orderList = orderList;
	}

	public Integer getReqType()
	{
		return reqType;
	}

	public void setReqType(Integer reqType)
	{
		this.reqType = reqType;
	}

	public BigDecimal getTotalActualAmount()
	{
		return totalActualAmount;
	}

	public void setTotalActualAmount(BigDecimal totalActualAmount)
	{
		this.totalActualAmount = totalActualAmount;
	}

	public String getTotalActualAmountString()
	{
		return totalActualAmountString;
	}

	public void setTotalActualAmountString(String totalActualAmountString)
	{
		this.totalActualAmountString = totalActualAmountString;
	}

	public BigDecimal getTotalCollectionAmount()
	{
		return totalCollectionAmount;
	}

	public void setTotalCollectionAmount(BigDecimal totalCollectionAmount)
	{
		this.totalCollectionAmount = totalCollectionAmount;
	}

	public String getTotalCollectionAmountString()
	{
		return totalCollectionAmountString;
	}

	public void setTotalCollectionAmountString(String totalCollectionAmountString)
	{
		this.totalCollectionAmountString = totalCollectionAmountString;
	}

	public BigDecimal getTotalAccountAmount()
	{
		return totalAccountAmount;
	}

	public void setTotalAccountAmount(BigDecimal totalAccountAmount)
	{
		this.totalAccountAmount = totalAccountAmount;
	}

	public String getTotalAccountAmountString()
	{
		return totalAccountAmountString;
	}

	public void setTotalAccountAmountString(String totalAccountAmountString)
	{
		this.totalAccountAmountString = totalAccountAmountString;
	}

	public Integer getTotalGoodNum()
	{
		return totalGoodNum;
	}

	public void setTotalGoodNum(Integer totalGoodNum)
	{
		this.totalGoodNum = totalGoodNum;
	}

	public BigDecimal getTotalGoodAmount()
	{
		return totalGoodAmount;
	}

	public void setTotalGoodAmount(BigDecimal totalGoodAmount)
	{
		this.totalGoodAmount = totalGoodAmount;
	}

	public String getTotalGoodAmountString()
	{
		return totalGoodAmountString;
	}

	public void setTotalGoodAmountString(String totalGoodAmountString)
	{
		this.totalGoodAmountString = totalGoodAmountString;
	}

	public Date getSearchDate()
	{
		return searchDate;
	}

	public void setSearchDate(Date searchDate)
	{
		this.searchDate = searchDate;
	}
	
	
}

