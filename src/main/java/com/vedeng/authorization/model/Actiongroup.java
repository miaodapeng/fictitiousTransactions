package com.vedeng.authorization.model;

import java.util.List;

import com.vedeng.common.page.PageSort;
import com.vedeng.authorization.model.Action;

/**
 * <b>Description:</b><br> 功能分组bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> Actiongroup
 * <br><b>Date:</b> 2017年4月25日 上午11:09:47
 */
public class Actiongroup extends PageSort{
	
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer actiongroupId;

    private Integer parentId;

    private Integer level;

    private String name;

    private Integer orderNo;
    
    private String iconStyle;
    
    private List<Action> actionList;

    private List<Actiongroup> childNode;

    private String nameArr;

    private Integer platformId;

    private String platformName;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getIconStyle() {
		return iconStyle;
	}

	public void setIconStyle(String iconStyle) {
		this.iconStyle = iconStyle;
	}

	public String getNameArr() {
		return nameArr;
	}

	public void setNameArr(String nameArr) {
		this.nameArr = nameArr;
	}

	public List<Action> getActionList() {
		return actionList;
	}

	public void setActionList(List<Action> actionList) {
		this.actionList = actionList;
	}

	public Integer getActiongroupId() {
        return actiongroupId;
    }

    public void setActiongroupId(Integer actiongroupId) {
        this.actiongroupId = actiongroupId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public List<Actiongroup> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<Actiongroup> childNode) {
        this.childNode = childNode;
    }
}