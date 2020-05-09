package com.vedeng.logistics.model;

import java.io.Serializable;

public class Warehouse implements Serializable{
    private Integer warehouseId;

    private Integer companyId;

    private Integer isEnable;

    private String warehouseName;

    private Integer areaId;

    private String areaIds;
 
    private String address;

    private String comments;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Integer isTemp;//临时仓库标识
    
    public Long enabletime;
    
    public String enableComment;
    
    public String areaName;//地区
    
    public String creatorName;//创建人
    
	public String companyName;   //公司名称
    
    private Integer province;// 省

	private Integer city;// 市

	private Integer zone;// 区
	
	private Integer cnt;// 仓库中商品数量
	
    public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getZone() {
		return zone;
	}

	public void setZone(Integer zone) {
		this.zone = zone;
	}

	public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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

    public Integer getIsTemp() {
	return isTemp;
    }

    public void setIsTemp(Integer isTemp) {
	this.isTemp = isTemp;
    }
}