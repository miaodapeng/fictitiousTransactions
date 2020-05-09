package com.vedeng.aftersales.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class AfterSalesGoods implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer afterSalesGoodsId;
	
	private Integer companyId;//归属公司id

    private Integer afterSalesId;

    private Integer orderDetailId;

    private Integer goodsId;

    private Integer num;

    private BigDecimal price;

    private Integer deliveryDirect;

    private Integer arrivalNum;

    private Long arrivalTime;

    private Integer arrivalUserId;

    private Integer arrivalStatus;
    
    private Integer goodsType;
    
    private Integer deliveryNum;
    
    private Integer deliveryStatus;
    
    private Long deliveryTime;
    
    private Integer creator;
    
    private Long addTime;
    
    private Integer operateType;
    /**是否为活动商品   0否  1是*/
    private Integer isActionGoods;

    /**
     * 当前sku退款金额
     */
    private BigDecimal skuRefundAmount;

    /**
     * 商品的saleorderGoodsId
     */
    private Integer saleorderGoodsId;

    private BigDecimal skuOldRefundAmount;//如果退款金额存在差异，就存之前的退款金额

    public Integer getIsActionGoods() {
        return isActionGoods;
    }

    public void setIsActionGoods(Integer isActionGoods) {
        this.isActionGoods = isActionGoods;
    }

    public BigDecimal getSkuRefundAmount() {
        return skuRefundAmount;
    }

    public void setSkuRefundAmount(BigDecimal skuRefundAmount) {
        this.skuRefundAmount = skuRefundAmount;
    }

    public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getAfterSalesGoodsId() {
        return afterSalesGoodsId;
    }

    public void setAfterSalesGoodsId(Integer afterSalesGoodsId) {
        this.afterSalesGoodsId = afterSalesGoodsId;
    }

    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDeliveryDirect() {
        return deliveryDirect;
    }

    public void setDeliveryDirect(Integer deliveryDirect) {
        this.deliveryDirect = deliveryDirect;
    }

    public Integer getArrivalNum() {
        return arrivalNum;
    }

    public void setArrivalNum(Integer arrivalNum) {
        this.arrivalNum = arrivalNum;
    }

    public Long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getArrivalUserId() {
        return arrivalUserId;
    }

    public void setArrivalUserId(Integer arrivalUserId) {
        this.arrivalUserId = arrivalUserId;
    }

    public Integer getArrivalStatus() {
        return arrivalStatus;
    }

    public void setArrivalStatus(Integer arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

    public BigDecimal getSkuOldRefundAmount() {
        return skuOldRefundAmount;
    }

    public void setSkuOldRefundAmount(BigDecimal skuOldRefundAmount) {
        this.skuOldRefundAmount = skuOldRefundAmount;
    }

    public Integer getSaleorderGoodsId() {
        return saleorderGoodsId;
    }

    public void setSaleorderGoodsId(Integer saleorderGoodsId) {
        this.saleorderGoodsId = saleorderGoodsId;
    }
}