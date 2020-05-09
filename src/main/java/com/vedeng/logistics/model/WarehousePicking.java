package com.vedeng.logistics.model;

import java.io.Serializable;
import java.util.List;

public class WarehousePicking implements Serializable{
    private Integer warehousePickingId;

    private Integer companyId;

    private Integer orderType;

    private Integer orderId;

    private Integer isEnable;

    private Integer pickingStatus;

    private Long pickingStatusTime;

    private String comments;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String goodsName;//产品名称
    
    private String model;//型号
    
    private String brandName;//品牌
    
    private String materialCode;//物料编码
    
    private String unitName;//单位
    
    private Integer num;//当次拣货数量
    
    private Long pickTime;//拣货时间
    
    private String operator;//操作人
    
    private String sku;//批次号
    
    private Integer goodsId;//产品id
    
    private  List<WarehousePickingDetail> pdList;//拣货单下的商品
    
    private String warehouseArea;//产品存储位置
    
    private Integer warehousePickingDetailId;//拣货单下的商品id
    
    private Integer cnt;//出库数
    
    private Integer barcodeId;//条码id
    
    private Integer lockedStatus=0;//锁定状态0未锁定 1已锁定

    private  Integer warehouseGoodsOperateLogId;

    private Long expirationDate;//效期时间

    private String batchNumber;//批次号

	public Integer getLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(Integer lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}
    
   	public Integer getCnt() {
   		return cnt;
   	}

   	public void setCnt(Integer cnt) {
   		this.cnt = cnt;
   	}
    
	public Integer getWarehousePickingDetailId() {
		return warehousePickingDetailId;
	}

	public void setWarehousePickingDetailId(Integer warehousePickingDetailId) {
		this.warehousePickingDetailId = warehousePickingDetailId;
	}
    
	public String getWarehouseArea() {
		return warehouseArea;
	}

	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}
    
	public List<WarehousePickingDetail> getPdList() {
		return pdList;
	}

	public void setPdList(List<WarehousePickingDetail> pdList) {
		this.pdList = pdList;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
    
    public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getPickTime() {
		return pickTime;
	}

	public void setPickTime(Long pickTime) {
		this.pickTime = pickTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getWarehousePickingId() {
        return warehousePickingId;
    }

    public void setWarehousePickingId(Integer warehousePickingId) {
        this.warehousePickingId = warehousePickingId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getPickingStatus() {
        return pickingStatus;
    }

    public void setPickingStatus(Integer pickingStatus) {
        this.pickingStatus = pickingStatus;
    }

    public Long getPickingStatusTime() {
        return pickingStatusTime;
    }

    public void setPickingStatusTime(Long pickingStatusTime) {
        this.pickingStatusTime = pickingStatusTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Integer getWarehouseGoodsOperateLogId() {
        return warehouseGoodsOperateLogId;
    }

    public void setWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId) {
        this.warehouseGoodsOperateLogId = warehouseGoodsOperateLogId;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}