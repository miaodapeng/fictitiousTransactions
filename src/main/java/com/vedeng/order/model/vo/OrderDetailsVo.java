package com.vedeng.order.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Description: 用于五行展示订单详情</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName OrderDetailsVo.java
 * <br><b>Date: 2018年7月23日 下午4:36:09 </b> 
 *
 */
public class OrderDetailsVo implements Serializable
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4196771853891412336L;
	
	/**
	 * SALEORDER_ID
	 */
	private Integer saleorderId;
	
	/**
	 * SALEORDER_NO
	 */
	private String saleorderNo;
	
	/**
	 * TRADER_ID
	 */
	
	private Integer traderId;
	
	/**
	 * TRADER_NAME
	 */
	private String traderName;
	
	/**
	 * STATUS
	 * 0待确认（默认）、1进行中、2已完结、3已关闭'
	 */
	private Integer orderStatus;
	
	/**
	 * VALID_TIME
	 */
	private String validTimeStr;
	
	/**
	 * PAYMENT_STATUS
	 */
	private Integer paymentStatus;
	
	/**
	 * DELIVERY_STATUS
	 * 订单发货状态：0未发货 1部分发货 2全部发货
	 */
	private Integer orderDeliveryStatus;
	
	/**
	 * ARRIVAL_STATUS
	 * 订单收货状态：0未收货 1部分收货 2全部收货
	 */
	private Integer orderArrivalStatus;
	
	/**
	 * TOTAL_AMOUNT
	 * 订单原始金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 订单成本
	 */
	private BigDecimal costAmount;
	
	/**
	 * 订单到款金额
	 */
	private BigDecimal actualPayAmount;
	
	/**
	 * 订单实际金额
	 */
	private BigDecimal actualOrderAmount;
	
	/**
	 * REFERENCE_COST_PRICE
	 * 产品部手填成本价
	 */
	private BigDecimal peferenceCostPrice;
	
	/**
	 * DELIVERY_STATUS
	 * 产品发货状态：0未发货 1部分发货 2全部发货
	 */
	private Integer  productDeliveryStatus;
	
	/**
	 * ARRIVAL_STATUS '产品收货状态：0未收货 1部分收货 2全部收货'
	 */
	private Integer productArrivalStatus;
	
	/**
	 * VALID_USER_ID
	 * 生效时订单归属人
	 */
	private Integer orderUserId;
	
	private Integer companyId;
	
	/**
	 * VALID_STATUS
	 */
	private Integer validStatus;
	
	/**
	 * 生效时间
	 */
	private Long validTime;
	
	/**
	 * 是否计入业绩
	 */
	private Integer perfStatus;
	
	private Integer satisfyDeliveryTime;
	
	public Integer getSatisfyDeliveryTime() {
		return satisfyDeliveryTime;
	}

	public void setSatisfyDeliveryTime(Integer satisfyDeliveryTime) {
		this.satisfyDeliveryTime = satisfyDeliveryTime;
	}

	public Integer getSaleorderId()
	{
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId)
	{
		this.saleorderId = saleorderId;
	}

	public String getSaleorderNo()
	{
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo)
	{
		this.saleorderNo = saleorderNo;
	}

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
		this.traderName = traderName.replaceAll("\\(","（").replaceAll("\\)","）");
	}

	public Integer getOrderStatus()
	{
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	public String getValidTimeStr()
	{
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr)
	{
		this.validTimeStr = validTimeStr;
	}

	public Integer getPaymentStatus()
	{
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus)
	{
		this.paymentStatus = paymentStatus;
	}

	public Integer getOrderDeliveryStatus()
	{
		return orderDeliveryStatus;
	}

	public void setOrderDeliveryStatus(Integer orderDeliveryStatus)
	{
		this.orderDeliveryStatus = orderDeliveryStatus;
	}

	public Integer getOrderArrivalStatus()
	{
		return orderArrivalStatus;
	}

	public void setOrderArrivalStatus(Integer orderArrivalStatus)
	{
		this.orderArrivalStatus = orderArrivalStatus;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCostAmount()
	{
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount)
	{
		this.costAmount = costAmount;
	}

	public BigDecimal getActualPayAmount()
	{
		return actualPayAmount;
	}

	public void setActualPayAmount(BigDecimal actualPayAmount)
	{
		this.actualPayAmount = actualPayAmount;
	}

	public BigDecimal getActualOrderAmount()
	{
		return actualOrderAmount;
	}

	public void setActualOrderAmount(BigDecimal actualOrderAmount)
	{
		this.actualOrderAmount = actualOrderAmount;
	}

	public BigDecimal getPeferenceCostPrice()
	{
		return peferenceCostPrice;
	}

	public void setPeferenceCostPrice(BigDecimal peferenceCostPrice)
	{
		this.peferenceCostPrice = peferenceCostPrice;
	}

	public Integer getProductDeliveryStatus()
	{
		return productDeliveryStatus;
	}

	public void setProductDeliveryStatus(Integer productDeliveryStatus)
	{
		this.productDeliveryStatus = productDeliveryStatus;
	}

	public Integer getProductArrivalStatus()
	{
		return productArrivalStatus;
	}

	public void setProductArrivalStatus(Integer productArrivalStatus)
	{
		this.productArrivalStatus = productArrivalStatus;
	}
	public Integer getOrderUserId()
	{
		return orderUserId;
	}

	public void setOrderUserId(Integer orderUserId)
	{
		this.orderUserId = orderUserId;
	}

	public Integer getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(Integer companyId)
	{
		this.companyId = companyId;
	}

	public Integer getValidStatus()
	{
		return validStatus;
	}

	public void setValidStatus(Integer validStatus)
	{
		this.validStatus = validStatus;
	}

	public Long getValidTime()
	{
		return validTime;
	}

	public void setValidTime(Long validTime)
	{
		this.validTime = validTime;
	}

	public Integer getPerfStatus()
	{
		return perfStatus;
	}

	public void setPerfStatus(Integer perfStatus)
	{
		this.perfStatus = perfStatus;
	}
	
}

