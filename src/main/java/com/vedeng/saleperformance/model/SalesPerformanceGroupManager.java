package com.vedeng.saleperformance.model;

import java.io.Serializable;

/**
 * 团队负责人
 * <b>Description:</b><br>
 * @author Bert
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.model.saleperformance
 * <br><b>ClassName:</b> SalesPerformanceGroupManager
 * <br><b>Date:</b> 2019年2月19日 下午4:01:53
 */
public class SalesPerformanceGroupManager implements Serializable {
	
	
	private static final long serialVersionUID = -6663185571047732300L;
	/**
	 * 主键id
	 */
	private Integer salesPerformanceGroupManagerId;
	
	/**
	 * 团队主键
	 */
	private Integer salesPerformanceGroupId;
	
	/**
	 * 用户ID
	 */
	private Integer userId;

	public Integer getSalesPerformanceGroupManagerId() {
		return salesPerformanceGroupManagerId;
	}

	public void setSalesPerformanceGroupManagerId(Integer salesPerformanceGroupManagerId) {
		this.salesPerformanceGroupManagerId = salesPerformanceGroupManagerId;
	}

	public Integer getSalesPerformanceGroupId() {
		return salesPerformanceGroupId;
	}

	public void setSalesPerformanceGroupId(Integer salesPerformanceGroupId) {
		this.salesPerformanceGroupId = salesPerformanceGroupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
