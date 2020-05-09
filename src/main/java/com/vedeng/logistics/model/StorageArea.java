package com.vedeng.logistics.model;

import java.io.Serializable;

public class StorageArea implements Serializable{
public Integer storageAreaId;
	
	public Integer storageRoomId;
	
	public String storageAreaName;
	
	public Integer isEnable;
	
	public Long enabletime;
	    
	public String enableComment;
	
    public String comments;
	
    public Long addTime;
    
    public Integer creator ;
    
    public Long modTime;
    
    public Integer updater;
    
    public String creatorName;
    
    public Integer companyId;
    
    public String storageRoomName;//所属库房
    
    public Integer wareHouseId;//所属仓库
    
    public String wareHouseName;//所属仓库名称
    
    private String st_is_enable;//上级库房是否禁用
    
    private String flag="0";//是否只查询非禁用货架
    
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
    
   	public String getSt_is_enable() {
   		return st_is_enable;
   	}

   	public void setSt_is_enable(String st_is_enable) {
   		this.st_is_enable = st_is_enable;
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

	public Integer getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(Integer storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public Integer getStorageRoomId() {
		return storageRoomId;
	}

	public void setStorageRoomId(Integer storageRoomId) {
		this.storageRoomId = storageRoomId;
	}

	public String getStorageAreaName() {
		return storageAreaName;
	}

	public void setStorageAreaName(String storageAreaName) {
		this.storageAreaName = storageAreaName;
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