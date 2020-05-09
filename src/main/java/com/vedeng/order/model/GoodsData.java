package com.vedeng.order.model;

import java.math.BigDecimal;

/**
 * <b>Description:</b><br> 产品数量信息状态
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.model.common
 * <br><b>ClassName:</b> GoodsData
 * <br><b>Date:</b> 2017年9月4日 下午2:11:37
 */
public class GoodsData {
	
	private Integer goodsId;//产品ID
	
	private Integer companyId;//公司ID

    private Integer stockNum;//库存数量
    
    private Integer occupyNum;//占用数量
    
    private Integer onWayNum;//在途数量
    
    private Integer needNum;//需求数量
    
    private Long lastOutTime;//近期出库时间
    
    private Long lastInTime;//近期入库时间
    
    private Integer saleNum90;//前90天销量
    
    private Integer saleNum30;//前30天销量
    
    private Integer quoteNum90;//前90天报价量
    
    private Integer quoteNum30;//前30天报价量
    
    private BigDecimal averagePrice;//近半年销售均价
    
    private double averageDeliveryCycle;//近半年货期均值
    
    private BigDecimal occupyAmount;//资金占用
    
    private BigDecimal occupyStockAmount;//库存资金占用

	private String sku;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getOccupyNum() {
		return occupyNum;
	}

	public void setOccupyNum(Integer occupyNum) {
		this.occupyNum = occupyNum;
	}

	public Integer getOnWayNum() {
		return onWayNum;
	}

	public void setOnWayNum(Integer onWayNum) {
		this.onWayNum = onWayNum;
	}

	public Long getLastOutTime() {
		return lastOutTime;
	}

	public void setLastOutTime(Long lastOutTime) {
		this.lastOutTime = lastOutTime;
	}

	public Long getLastInTime() {
		return lastInTime;
	}

	public void setLastInTime(Long lastInTime) {
		this.lastInTime = lastInTime;
	}

	public Integer getNeedNum() {
		return needNum;
	}

	public void setNeedNum(Integer needNum) {
		this.needNum = needNum;
	}

	public Integer getSaleNum90() {
		return saleNum90;
	}

	public void setSaleNum90(Integer saleNum90) {
		this.saleNum90 = saleNum90;
	}

	public Integer getSaleNum30() {
		return saleNum30;
	}

	public void setSaleNum30(Integer saleNum30) {
		this.saleNum30 = saleNum30;
	}

	public Integer getQuoteNum90() {
		return quoteNum90;
	}

	public void setQuoteNum90(Integer quoteNum90) {
		this.quoteNum90 = quoteNum90;
	}

	public Integer getQuoteNum30() {
		return quoteNum30;
	}

	public void setQuoteNum30(Integer quoteNum30) {
		this.quoteNum30 = quoteNum30;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public double getAverageDeliveryCycle() {
		return averageDeliveryCycle;
	}

	public void setAverageDeliveryCycle(double averageDeliveryCycle) {
		this.averageDeliveryCycle = averageDeliveryCycle;
	}

	public BigDecimal getOccupyAmount() {
		return occupyAmount;
	}

	public void setOccupyAmount(BigDecimal occupyAmount) {
		this.occupyAmount = occupyAmount;
	}

	public BigDecimal getOccupyStockAmount() {
		return occupyStockAmount;
	}

	public void setOccupyStockAmount(BigDecimal occupyStockAmount) {
		this.occupyStockAmount = occupyStockAmount;
	}
    
    
}
