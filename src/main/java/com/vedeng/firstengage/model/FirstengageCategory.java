package com.vedeng.firstengage.model;

import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.common.page.PageSort;

public class FirstengageCategory extends PageSort{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer categoryId;

    private Integer companyId;

    private Integer parentId;

    private String categoryName;//
    
    private String categoryNm;
    
    private String categoryThreeNm;//三级分类名称

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
    
    private String addTimeStr;
    
    private String modTimeStr;
    
    private String creatorNm;
    
    private String updaterNm;
    
    private String categoryNameArr;
    
    private String categoryIdArr;
    
    private Integer goodsNum;
    
    private List<User> userList;//产品类别归属
    
    private Integer userId;//搜索归属人
    
    private List<Integer> categoryIds;//分类ID集合
    
    /**
     * 接口类型
     * 默认0
     * 商品运营分类 用于查询时全部分类名称展示时 interfaceType = 1;
     */
    private Integer interfaceType = 0;
    
    
    
    public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getModTimeStr() {
		return modTimeStr;
	}

	public void setModTimeStr(String modTimeStr) {
		this.modTimeStr = modTimeStr;
	}

	public String getCategoryThreeNm() {
		return categoryThreeNm;
	}

	public void setCategoryThreeNm(String categoryThreeNm) {
		this.categoryThreeNm = categoryThreeNm;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getUpdaterNm() {
		return updaterNm;
	}

	public void setUpdaterNm(String updaterNm) {
		this.updaterNm = updaterNm;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public String getCategoryIdArr() {
		return categoryIdArr;
	}

	public void setCategoryIdArr(String categoryIdArr) {
		this.categoryIdArr = categoryIdArr;
	}

	public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

	public String getCategoryNameArr() {
		return categoryNameArr;
	}

	public void setCategoryNameArr(String categoryNameArr) {
		this.categoryNameArr = categoryNameArr;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
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