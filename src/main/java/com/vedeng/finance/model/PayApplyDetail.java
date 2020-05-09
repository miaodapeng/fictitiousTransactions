package com.vedeng.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayApplyDetail implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer payApplyDetailId;

    private Integer payApplyId;

    private Integer detailgoodsId;

    private BigDecimal price;

    private BigDecimal num;

    private BigDecimal totalAmount;
    
    private String saleOrderNo,orderNo,traderName,goodsName,sku,brandName,model,unitName;
    
    
    public String getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getPayApplyDetailId() {
        return payApplyDetailId;
    }

    public void setPayApplyDetailId(Integer payApplyDetailId) {
        this.payApplyDetailId = payApplyDetailId;
    }

    public Integer getPayApplyId() {
        return payApplyId;
    }

    public void setPayApplyId(Integer payApplyId) {
        this.payApplyId = payApplyId;
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
}