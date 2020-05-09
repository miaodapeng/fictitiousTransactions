package com.vedeng.logistics.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class StorageRoom implements Serializable{
public Integer storageroomId;
	
	public Integer warehouseId;
	
	public String storageRoomName;
	
	public Integer companyId;
	
	public Integer isEnable;
	
	public BigDecimal  temperatureMin;  
	
	public BigDecimal  temperatureMax;  
	
	public BigDecimal  humidityMin;  
	
	public BigDecimal  humidityMax;
	
	public String comments;
	
    public Long addTime;
    
    public Integer creator ;
    
    public Long modTime;
    
    public Integer updater;
    
    public Long enabletime;
    
    public String enableComment;
    
    public String warehouseName;//所属仓库
    
    public String creatorName;//创建人姓名
    
    private String wareIsEnable;//上级仓库是否被禁用
    
    private String flag="0";//区分查询是否禁用的货区的标志
    
    private Integer cnt;// 库房中商品数量
	
    public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
    
    public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
    
    public String getWareIsEnable() {
		return wareIsEnable;
	}

	public void setWareIsEnable(String wareIsEnable) {
		this.wareIsEnable = wareIsEnable;
	}
    
    public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
    
    public Long getEnabletime() {
		return enabletime;
	}

	public void setEnabletime(Long enabletime) {
		this.enabletime = enabletime;
	}

	public String getEnableComment() {
		return enableComment;
	}

	public void setEnableComment(String enableComment) {
		this.enableComment = enableComment;
	}
    
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getStorageroomId() {
		return storageroomId;
	}

	public void setStorageroomId(Integer storageroomId) {
		this.storageroomId = storageroomId;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getStorageRoomName() {
		return storageRoomName;
	}

	public void setStorageRoomName(String storageRoomName) {
		this.storageRoomName = storageRoomName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public BigDecimal getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(BigDecimal temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public BigDecimal getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(BigDecimal temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public BigDecimal getHumidityMin() {
		return humidityMin;
	}

	public void setHumidityMin(BigDecimal humidityMin) {
		this.humidityMin = humidityMin;
	}

	public BigDecimal getHumidityMax() {
		return humidityMax;
	}

	public void setHumidityMax(BigDecimal humidityMax) {
		this.humidityMax = humidityMax;
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
}