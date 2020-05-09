package com.vedeng.logistics.model;

import java.io.Serializable;

public class StorageLocation implements Serializable{
public Integer storageLocationId;
	
	public Integer storageRackId;
	
	public String storageLocationName;
	
	public Integer isEnable;
	
	public Long enabletime;
	    
	public String enableComment;
	
    public String comments;
	
    public Long addTime;
    
    public Integer creator ;
    
    public Long modTime;
    
    public Integer updater;
    
    public Integer companyId;
    
    public String creatorName; //创建人名称
    
    public String storageRackName;//货架名称
    
    public Integer storageAreaId;//货区id
    
    public String storageAreaName;//货区名称
    
    public Integer storageroomId;//库房id
    
    public String storageRoomName;//所属库房
    
    public Integer wareHouseId;//所属仓库id
    
    public String wareHouseName;//所属仓库名称
    
    private String scIsEnable;//上级货区是否禁用
    
    private Integer cnt;// 库位中商品数量
	
    public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
    
    public String getScIsEnable() {
		return scIsEnable;
	}

	public void setScIsEnable(String scIsEnable) {
		this.scIsEnable = scIsEnable;
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

	public String getStorageLocationName() {
		return storageLocationName;
	}

	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}

	public String getStorageRackName() {
		return storageRackName;
	}

	public void setStorageRackName(String storageRackName) {
		this.storageRackName = storageRackName;
	}

	public Integer getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(Integer storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public String getStorageAreaName() {
		return storageAreaName;
	}

	public void setStorageAreaName(String storageAreaName) {
		this.storageAreaName = storageAreaName;
	}

	public Integer getStorageroomId() {
		return storageroomId;
	}

	public void setStorageroomId(Integer storageroomId) {
		this.storageroomId = storageroomId;
	}

	public Integer getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public String getStorageRoomName() {
		return storageRoomName;
	}

	public void setStorageRoomName(String storageRoomName) {
		this.storageRoomName = storageRoomName;
	}
    
    public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
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