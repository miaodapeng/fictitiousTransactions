package com.vedeng.saleperformance.model.vo;


import com.vedeng.saleperformance.model.SalesPerformanceDeptUser;

import java.util.List;

/**
 * 功能描述
 * className
 *
 * @author Bert
 * @date 2019/2/19 9:08
 * @Description //TODO
 * @version: 1.0
 */
public class SalesPerformanceDeptUserVo extends SalesPerformanceDeptUser {
	/**
	 * 是否删除 0 未删除 1 已删除
	 */
	private Integer isDelete;
	
	/**
	 *  0 未禁 1 已禁
	 */
	private Integer isDisabled;
	
	
	
	/**
	 * 公司ID
	 */
	private Integer companyId;
	/**
	 * 地区名字
	 */
	private String orgName;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户的ID集合(用来传参)
	 */
	private String userIds;
	
	/**
	 * 用来保存
	 */
	public List<Integer> listUserId;
	
	public List<Integer> getListUserId() {
		return listUserId;
	}
	
	public void setListUserId(List<Integer> listUserId) {
		this.listUserId = listUserId;
	}
	
	public String getUserIds() {
		return userIds;
	}
	
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getIsDelete() {
		return isDelete;
	}
	
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public Integer getIsDisabled() {
		return isDisabled;
	}
	
	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}
}
	
