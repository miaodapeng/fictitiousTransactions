package com.vedeng.logistics.model;

import java.io.Serializable;

public class WarehouseGoodsSet implements Serializable{
    public Integer  warehouseGoodsSetId;
	
	public Integer companyId;
	
	public Integer goodsId;
	
	public Integer wareHouseId;
	
	public Integer storageroomId;
	
	public Integer storageAreaId;
	
	public Integer storageRackId;
	
	public Integer storageLocationId;
	
	public String comments;
	
    public Long addTime;
    
    public Integer creator ;
    
    public Long modTime;
    
    public Integer updater;
    
    public String goodsName;//商品名称
    
    public String brandName;//品牌
    
    public String model;//型号
    
    public String materialCode;//物料编码
    
    public String unitName;//单位
    
    public String wareHouseName;//仓库
    
    public String storageroomName;//库房
    
    public String storageAreaName;//货区
    
    public String storageRackName;//货架
    
    public String storageLocationName;//库位
    
    public Integer num;//产品数量
    
    public Integer isDiscard;//产品状态
    
    public String sku;//订货号
    
    public String levelAndId;//所属的五级仓库的级别和id
    
    public String values;//批量删除的值
    
    public Integer buyorderId;//采购订单
    
	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getLevelAndId() {
		return levelAndId;
	}

	public void setLevelAndId(String levelAndId) {
		this.levelAndId = levelAndId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getIsDiscard() {
		return isDiscard;
	}

	public void setIsDiscard(Integer isDiscard) {
		this.isDiscard = isDiscard;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public Integer getWarehouseGoodsSetId() {
		return warehouseGoodsSetId;
	}

	public void setWarehouseGoodsSetId(Integer warehouseGoodsSetId) {
		this.warehouseGoodsSetId = warehouseGoodsSetId;
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

	public Integer getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public Integer getStorageroomId() {
		return storageroomId;
	}

	public void setStorageroomId(Integer storageroomId) {
		this.storageroomId = storageroomId;
	}

	public Integer getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(Integer storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public Integer getStorageRackId() {
		return storageRackId;
	}

	public void setStorageRackId(Integer storageRackId) {
		this.storageRackId = storageRackId;
	}

	public Integer getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(Integer storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Integer getBuyorderId() {
	    return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
	    this.buyorderId = buyorderId;
	}
}