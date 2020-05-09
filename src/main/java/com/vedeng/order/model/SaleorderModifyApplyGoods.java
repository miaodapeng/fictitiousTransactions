package com.vedeng.order.model;

import java.io.Serializable;

public class SaleorderModifyApplyGoods implements Serializable{
    private Integer saleorderModifyApplyGoodsId;

    private Integer saleorderModifyApplyId;

    private Integer saleorderGoodsId;

    private Integer deliveryDirect;

    private String deliveryDirectComments;

    private Integer oldDeliveryDirect;

    private String oldDeliveryDirectComments;
    
    private String GoodsComments;
    
    private String oldGoodsComments;

    public Integer getSaleorderModifyApplyGoodsId() {
        return saleorderModifyApplyGoodsId;
    }

    public void setSaleorderModifyApplyGoodsId(Integer saleorderModifyApplyGoodsId) {
        this.saleorderModifyApplyGoodsId = saleorderModifyApplyGoodsId;
    }

    public Integer getSaleorderModifyApplyId() {
        return saleorderModifyApplyId;
    }

    public void setSaleorderModifyApplyId(Integer saleorderModifyApplyId) {
        this.saleorderModifyApplyId = saleorderModifyApplyId;
    }

    public Integer getSaleorderGoodsId() {
        return saleorderGoodsId;
    }

    public void setSaleorderGoodsId(Integer saleorderGoodsId) {
        this.saleorderGoodsId = saleorderGoodsId;
    }

    public Integer getDeliveryDirect() {
        return deliveryDirect;
    }

    public void setDeliveryDirect(Integer deliveryDirect) {
        this.deliveryDirect = deliveryDirect;
    }

    public String getDeliveryDirectComments() {
        return deliveryDirectComments;
    }

    public void setDeliveryDirectComments(String deliveryDirectComments) {
        this.deliveryDirectComments = deliveryDirectComments == null ? null : deliveryDirectComments.trim();
    }

    public Integer getOldDeliveryDirect() {
        return oldDeliveryDirect;
    }

    public void setOldDeliveryDirect(Integer oldDeliveryDirect) {
        this.oldDeliveryDirect = oldDeliveryDirect;
    }

    public String getOldDeliveryDirectComments() {
        return oldDeliveryDirectComments;
    }

    public void setOldDeliveryDirectComments(String oldDeliveryDirectComments) {
        this.oldDeliveryDirectComments = oldDeliveryDirectComments == null ? null : oldDeliveryDirectComments.trim();
    }

	public String getGoodsComments() {
		return GoodsComments;
	}

	public void setGoodsComments(String goodsComments) {
		GoodsComments = goodsComments;
	}

	public String getOldGoodsComments() {
		return oldGoodsComments;
	}

	public void setOldGoodsComments(String oldGoodsComments) {
		this.oldGoodsComments = oldGoodsComments;
	}
}