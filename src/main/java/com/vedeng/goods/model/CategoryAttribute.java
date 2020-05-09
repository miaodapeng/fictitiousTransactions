package com.vedeng.goods.model;

import java.io.Serializable;
import java.util.List;


public class CategoryAttribute implements Serializable{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CategoryAttributeValue> categoryAttributeValue;

	private Integer categoryAttributeId;

    private Integer categoryId;

    private Integer parentId;

    private String categoryAttributeName;

    private Integer inputType;
    
    private Integer isSelected;

    private Integer isFilter;

    private Integer sort;
    
    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private Category category;
    
    private List<Integer> categoryIdList;
    
    private Integer companyId;
    
    public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public Category getCategory() {
		return category;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getCategoryAttributeId() {
        return categoryAttributeId;
    }

    public void setCategoryAttributeId(Integer categoryAttributeId) {
        this.categoryAttributeId = categoryAttributeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategoryAttributeName() {
        return categoryAttributeName;
    }

    public void setCategoryAttributeName(String categoryAttributeName) {
        this.categoryAttributeName = categoryAttributeName == null ? null : categoryAttributeName.trim();
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public Integer getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(Integer isFilter) {
        this.isFilter = isFilter;
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

	public Integer getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}

	public List<CategoryAttributeValue> getCategoryAttributeValue() {
		return categoryAttributeValue;
	}

	public void setCategoryAttributeValue(List<CategoryAttributeValue> categoryAttributeValue) {
		this.categoryAttributeValue = categoryAttributeValue;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}