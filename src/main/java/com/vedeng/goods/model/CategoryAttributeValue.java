package com.vedeng.goods.model;

import java.io.Serializable;
import java.util.Map;

public class CategoryAttributeValue implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer categoryAttributeValueId;

    private Integer categoryAttributeId;
    
    private String categoryAttributeName;

    private String attrValue;

    private Integer sort;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Integer isEnable;
    
    private Map<String,CategoryAttributeValue> insertAttrMap;
    
    private Map<String,CategoryAttributeValue> updateAttrMap;
    
    private String dataType;
    
    
    
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getCategoryAttributeName() {
		return categoryAttributeName;
	}

	public void setCategoryAttributeName(String categoryAttributeName) {
		this.categoryAttributeName = categoryAttributeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Map<String, CategoryAttributeValue> getInsertAttrMap() {
		return insertAttrMap;
	}

	public void setInsertAttrMap(Map<String, CategoryAttributeValue> insertAttrMap) {
		this.insertAttrMap = insertAttrMap;
	}

	public Map<String, CategoryAttributeValue> getUpdateAttrMap() {
		return updateAttrMap;
	}

	public void setUpdateAttrMap(Map<String, CategoryAttributeValue> updateAttrMap) {
		this.updateAttrMap = updateAttrMap;
	}

	public Integer getCategoryAttributeValueId() {
        return categoryAttributeValueId;
    }

    public void setCategoryAttributeValueId(Integer categoryAttributeValueId) {
        this.categoryAttributeValueId = categoryAttributeValueId;
    }

    public Integer getCategoryAttributeId() {
        return categoryAttributeId;
    }

    public void setCategoryAttributeId(Integer categoryAttributeId) {
        this.categoryAttributeId = categoryAttributeId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue == null ? null : attrValue.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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