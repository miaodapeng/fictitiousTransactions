package com.vedeng.authorization.model;

import com.vedeng.common.page.PageSort;
import org.apache.commons.lang3.StringUtils;

/**
 * <b>Description:</b><br> 功能bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> Action
 * <br><b>Date:</b> 2017年4月25日 上午11:10:05
 */
public class Action extends PageSort{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Integer actionId;//功能點ID

    private Integer actiongroupId;//功能分組ID

    private String controllerName;//控制器名稱

    private String actionName;//功能名稱

    private String moduleName;//模塊名稱

    private String actionDesc;//功能描述

    private Integer isMenu;//是否為菜單
    
    private Integer sort;

    private Long addTime;

    private Integer creator;

    private Long modTime;//編輯時間

    private Integer updater;
    
    private String groupName;//分組名稱

    private Integer platformId;
    private String platformName;

    public String getFinalUrl() {
        if(StringUtils.startsWith(moduleName,"http://")){
            return moduleName;
        }
        return "/"+moduleName+"/"+controllerName+"/"+actionName+".do";
    }

    public void setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
    }
    /**
     *
     */
    private String finalUrl;


    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getActiongroupId() {
        return actiongroupId;
    }

    public void setActiongroupId(Integer actiongroupId) {
        this.actiongroupId = actiongroupId;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName == null ? null : controllerName.trim();
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc == null ? null : actionDesc.trim();
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
 
}