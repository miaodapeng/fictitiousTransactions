package com.vedeng.goods.model;

import java.io.Serializable;
import java.util.List;

public class StandardCategory implements Serializable{
    private Integer standardCategoryId;

    private Integer parentId;

    private String categoryName;

    private String treenodes;

    private Integer status;

    private Integer level;

    private Integer sort;

    private String iconDomain;

    private String iconUri;

    private String description;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    /**
     * 新国标分类
     */
    private List<StandardCategory> standardCategoryList;
    
    /**
     * 接口类型
     * 默认0
     * 新国标用于查询时全部分类名称展示时 interfaceType = 1;
     */
    private Integer interfaceType = 0;

    public List<StandardCategory> getStandardCategoryList() {
		return standardCategoryList;
	}

	public void setStandardCategoryList(List<StandardCategory> standardCategoryList) {
		this.standardCategoryList = standardCategoryList;
	}

	public Integer getStandardCategoryId() {
        return standardCategoryId;
    }

    public void setStandardCategoryId(Integer standardCategoryId) {
        this.standardCategoryId = standardCategoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getTreenodes() {
        return treenodes;
    }

    public void setTreenodes(String treenodes) {
        this.treenodes = treenodes == null ? null : treenodes.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIconDomain() {
        return iconDomain;
    }

    public void setIconDomain(String iconDomain) {
        this.iconDomain = iconDomain == null ? null : iconDomain.trim();
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri == null ? null : iconUri.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

	public Integer getInterfaceType()
	{
		return interfaceType;
	}

	public void setInterfaceType(Integer interfaceType)
	{
		this.interfaceType = interfaceType;
	}
    
    
}