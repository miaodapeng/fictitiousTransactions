package com.vedeng.logistics.model;

import java.io.Serializable;
import java.util.List;

public class WarehouseGoodsStatus implements Serializable{
    private Integer warehouseGoodsStatusId;

    private Integer companyId;

    private Integer goodsId;

    private Integer warehouseId=0;

    private Integer storageRoomId=0;

    private Integer storageAreaId=0;

    private Integer storageLocationId=0;

    private Integer storageRackId=0;

    private Integer num;

    private Integer isParentProblem;

    public Integer getWarehouseGoodsStatusId;

   private Integer saleorderGoodsId;//销售订单id

    private Integer buyorderId;//采购订单Id

    private Integer afterSalesId;//售后订单Id

    private String wareHouseName;//仓库

    private String storageroomName;//库房

    private String storageAreaName;//货区

    private String storageRackName;//货架

    private String storageLocationName;//库位

    private List<Integer> goodsList;//产品ID集合
    //是否比较过 校验WarehouseGoodsStatus库存数量使用字段
    private Boolean sameFalg = false;

    public Boolean getSameFalg() {
        return sameFalg;
    }

    public void setSameFalg(Boolean sameFalg) {
        this.sameFalg = sameFalg;
    }

    public Integer getSaleorderGoodsId() {
		return saleorderGoodsId;
	}

	public void setSaleorderGoodsId(Integer saleorderGoodsId) {
		this.saleorderGoodsId = saleorderGoodsId;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public Integer getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(Integer afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getStorageroomName() {
		return storageroomName;
	}

	public void setStorageroomName(String storageroomName) {
		this.storageroomName = storageroomName;
	}

	public String getStorageAreaName() {
		return storageAreaName;
	}

	public void setStorageAreaName(String storageAreaName) {
		this.storageAreaName = storageAreaName;
	}

	public String getStorageRackName() {
		return storageRackName;
	}

	public void setStorageRackName(String storageRackName) {
		this.storageRackName = storageRackName;
	}

	public String getStorageLocationName() {
		return storageLocationName;
	}

	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}

	public List<Integer> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Integer> goodsList) {
		this.goodsList = goodsList;
	}

	public Integer getWarehouseGoodsStatusId() {
        return warehouseGoodsStatusId;
    }

    public void setWarehouseGoodsStatusId(Integer warehouseGoodsStatusId) {
        this.warehouseGoodsStatusId = warehouseGoodsStatusId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getStorageRoomId() {
        return storageRoomId;
    }

    public void setStorageRoomId(Integer storageRoomId) {
        this.storageRoomId = storageRoomId;
    }

    public Integer getStorageAreaId() {
        return storageAreaId;
    }

    public void setStorageAreaId(Integer storageAreaId) {
        this.storageAreaId = storageAreaId;
    }

    public Integer getStorageLocationId() {
        return storageLocationId;
    }

    public void setStorageLocationId(Integer storageLocationId) {
        this.storageLocationId = storageLocationId;
    }

    public Integer getStorageRackId() {
        return storageRackId;
    }

    public void setStorageRackId(Integer storageRackId) {
        this.storageRackId = storageRackId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getIsParentProblem(){
        return isParentProblem;
    }

    public void setIsParentProblem(Integer isParentProblem) {
        this.isParentProblem = isParentProblem;
    }

    public Integer getGetWarehouseGoodsStatusId() {
        return getWarehouseGoodsStatusId;
    }

    public void setGetWarehouseGoodsStatusId(Integer getWarehouseGoodsStatusId) {
        this.getWarehouseGoodsStatusId = getWarehouseGoodsStatusId;
    }

    @Override
    public String toString() {
        return "WarehouseGoodsStatus{" +
                "warehouseGoodsStatusId=" + warehouseGoodsStatusId +
                ", companyId=" + companyId +
                ", goodsId=" + goodsId +
                ", warehouseId=" + warehouseId +
                ", storageRoomId=" + storageRoomId +
                ", storageAreaId=" + storageAreaId +
                ", storageLocationId=" + storageLocationId +
                ", storageRackId=" + storageRackId +
                ", num=" + num +
                ", sameFalg=" + sameFalg +
                '}';
    }
}