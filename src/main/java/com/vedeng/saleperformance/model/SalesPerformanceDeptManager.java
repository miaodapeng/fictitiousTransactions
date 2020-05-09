package com.vedeng.saleperformance.model;

import java.io.Serializable;

/**
 * 功能描述
 * className
 *  小组关系负责人
 * @author Bert
 * @date 2019/2/17 18:25
 * @Description //TODO
 * @version: 1.0
 */
public class SalesPerformanceDeptManager implements Serializable {
	
	private static final long serialVersionUID = -7408981733188656134L;
	/**
	 * PK
	 */
	private Integer salesPerformanceManagerId;
	/**
	 * 小组ID
	 */
	private Integer salesPerformanceDeptId;
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	public Integer getSalesPerformanceManagerId() {
		return salesPerformanceManagerId;
	}
	
	public void setSalesPerformanceManagerId(Integer salesPerformanceManagerId) {
		this.salesPerformanceManagerId = salesPerformanceManagerId;
	}
	
	public Integer getSalesPerformanceDeptId() {
		return salesPerformanceDeptId;
	}
	
	public void setSalesPerformanceDeptId(Integer salesPerformanceDeptId) {
		this.salesPerformanceDeptId = salesPerformanceDeptId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
