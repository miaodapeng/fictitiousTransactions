package com.vedeng.logistics.model;

import java.io.Serializable;

public class StorageRack implements Serializable{
public Integer storageRackId;
	
	public Integer companyId;
	
	public Integer storageAreaId;
	
	public String storageRackName;
	
	public Integer isEnable;
	
	public Long enabletime;
    
	public String enableComment;
	
    public String comments;
	
    public Long addTime;
    
    public Integer creator ;
    
    public Long modTime;
    
    public Integer updater;
    
    public String creatorName;
    
    public String storageAreaName;//货区名称
    
    public Integer storageroomId;//库房id
    
    public String storageRoomName;//所属库房
    
    public Integer wareHouseId;//所属仓库
    
    public String wareHouseName;//所属仓库名称
    
    private String sa_is_enable;//上级货区是否禁用
    
    private String flag="0";//是否只查询非禁用库位
    
    private Integer cnt;// 货区中商品数量
	
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
    
	public String getSa_is_enable() {
		return sa_is_enable;
	}

	public void setSa_is_enable(String sa_is_enable) {
		this.sa_is_enable = sa_is_enable;
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

	public String getStorageRoomName() {
		return storageRoomName;
	}

	public void setStorageRoomName(String storageRoomName) {
		this.storageRoomName = storageRoomName;
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

	public Integer getStorageRackId() {
		return storageRackId;
	}

	public void setStorageRackId(Integer storageRackId) {
		this.storageRackId = storageRackId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(Integer storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public String getStorageRackName() {
		return storageRackName;
	}

	public void setStorageRackName(String storageRackName) {
		this.storageRackName = storageRackName;
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

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}