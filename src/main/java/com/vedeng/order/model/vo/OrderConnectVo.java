
package com.vedeng.order.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>Description: 订单与订单关系数据模型</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName OrderConnectVo.java
 * <br><b>Date: 2018年8月23日 下午3:01:43 </b> 
 *
 */
public class OrderConnectVo implements Serializable
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 253542094132081309L;

	/**
	 * 关联单号
	 */
	private String orderConnectNo;
	
	/**
	 * 申请人即创建人
	 */
	private String creatorName;
	/**
	 * 采购数据
	 */
	private Integer bugNum;
	
	/**
	 * 该订单商品数量
	 * 可以为销售数量
	 */
	private Integer orderNum;
	
	/**
	 * 该订单商品单价
	 * 根据订单类型:销售单价/采购单价等
	 */
	private BigDecimal orderPrice; 
	
	/**
	 * DELIVERY_CYCLE
	 * 销售期货
	 */
	private String deliveryCycle;
	
	/**
	 * 该订单内部备注
	 */
	private String orderComments;
	
	/**
	 * 该订单的产品备注
	 */
	private String goodComments;
	
	/**
	 * 客户名称:终端客户名称
	 * TERMINAL_TRADER_NAME
	 */
	private String traderName;
	
	/**
	 * 关联单号ID
	 */
	private Integer orderConnectId;
	
	/**
	 * 采购订单号
	 * BUYORDER_NO
	 */
	private String buyorderNo;
	
	/**
	 *  采购订单ID
	 */
	private Integer buyorderId;
	
	
	/**
	 * 申请人ID
	 */
	private Integer creatorId;
	
	/**
	 * 客户ID
	 */
	private Integer traderId;
	
	/**
	 * BUYORDER_GOODS_ID
	 */
	private Integer buyorderGoodsId;
	
	/**
	 * SALEORDER_GOODS_ID
	 */
	private Integer saleorderGoodsId;
	
	/**
	 * SKU
	 */
	private String sku;
	
	/**
	 * 查询订单号:
	 * 可以为销售订单号
	 */
	private String searchOrderNo;
	
	/**
	 * 查询订单Id
	 * 
	 */
	private int searchOrderId;

	public String getOrderConnectNo()
	{
		return orderConnectNo;
	}

	public void setOrderConnectNo(String orderConnectNo)
	{
		this.orderConnectNo = orderConnectNo;
	}

	public String getCreatorName()
	{
		return creatorName;
	}

	public void setCreatorName(String creatorName)
	{
		this.creatorName = creatorName;
	}

	public Integer getBugNum()
	{
		return bugNum;
	}

	public void setBugNum(Integer bugNum)
	{
		this.bugNum = bugNum;
	}

	public Integer getOrderNum()
	{
		return orderNum;
	}

	public void setOrderNum(Integer orderNum)
	{
		this.orderNum = orderNum;
	}

	public BigDecimal getOrderPrice()
	{
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice)
	{
		this.orderPrice = orderPrice;
	}

	public String getDeliveryCycle()
	{
		return deliveryCycle;
	}

	public void setDeliveryCycle(String deliveryCycle)
	{
		this.deliveryCycle = deliveryCycle;
	}

	public String getOrderComments()
	{
		return orderComments;
	}

	public void setOrderComments(String orderComments)
	{
		this.orderComments = orderComments;
	}

	public String getGoodComments()
	{
		return goodComments;
	}

	public void setGoodComments(String goodComments)
	{
		this.goodComments = goodComments;
	}

	public String getTraderName()
	{
		return traderName;
	}

	public void setTraderName(String traderName)
	{
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getOrderConnectId()
	{
		return orderConnectId;
	}

	public void setOrderConnectId(Integer orderConnectId)
	{
		this.orderConnectId = orderConnectId;
	}

	public String getBuyorderNo()
	{
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo)
	{
		this.buyorderNo = buyorderNo;
	}

	public Integer getBuyorderId()
	{
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId)
	{
		this.buyorderId = buyorderId;
	}

	public Integer getCreatorId()
	{
		return creatorId;
	}

	public void setCreatorId(Integer creatorId)
	{
		this.creatorId = creatorId;
	}

	public Integer getTraderId()
	{
		return traderId;
	}

	public void setTraderId(Integer traderId)
	{
		this.traderId = traderId;
	}

	public Integer getBuyorderGoodsId()
	{
		return buyorderGoodsId;
	}

	public void setBuyorderGoodsId(Integer buyorderGoodsId)
	{
		this.buyorderGoodsId = buyorderGoodsId;
	}

	public Integer getSaleorderGoodsId()
	{
		return saleorderGoodsId;
	}

	public void setSaleorderGoodsId(Integer saleorderGoodsId)
	{
		this.saleorderGoodsId = saleorderGoodsId;
	}

	public String getSku()
	{
		return sku;
	}

	public void setSku(String sku)
	{
		this.sku = sku;
	}

	public String getSearchOrderNo()
	{
		return searchOrderNo;
	}

	public void setSearchOrderNo(String searchOrderNo)
	{
		this.searchOrderNo = searchOrderNo;
	}

	public int getSearchOrderId()
	{
		return searchOrderId;
	}

	public void setSearchOrderId(int searchOrderId)
	{
		this.searchOrderId = searchOrderId;
	}
	
	
	
	
}

