package com.vedeng.logistics.model;

import java.io.Serializable;

public class WarehousePickingDetail implements Serializable{
    private Integer warehousePickingDetailId;

    private Integer warehousePickingId;

    private Integer warehouseGoodsOperateLogId;

    private Integer goodsId;

    private Integer num;
    
    private Long expirationDate;
    
    private Long addTime;
    
    private String batchNumber;
    
    private String warehouseArea;//存储位置
    
    private String comments;
    
    private Integer isEnable;
    
    private Integer deliveryNum;
    
    private Integer deliveryStatus;
    
    private Integer relatedType;

    private Integer relatedId;
    
    public Integer getRelatedType() {
		return relatedType;
	}

	public void setRelatedType(Integer relatedType) {
		this.relatedType = relatedType;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
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

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Long getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Long expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getWarehouseArea() {
		return warehouseArea;
	}

	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    public Integer getWarehousePickingDetailId() {
        return warehousePickingDetailId;
    }

    public void setWarehousePickingDetailId(Integer warehousePickingDetailId) {
        this.warehousePickingDetailId = warehousePickingDetailId;
    }

    public Integer getWarehousePickingId() {
        return warehousePickingId;
    }

    public void setWarehousePickingId(Integer warehousePickingId) {
        this.warehousePickingId = warehousePickingId;
    }

    public Integer getWarehouseGoodsOperateLogId() {
        return warehouseGoodsOperateLogId;
    }

    public void setWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId) {
        this.warehouseGoodsOperateLogId = warehouseGoodsOperateLogId;
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
}