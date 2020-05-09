package com.vedeng.saleperformance.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 功能描述
 * className
 *
 * @author Bert
 * @date 2019/2/19 0:45
 * @Description //TODO 小组内人员信息
 * @version: 1.0
 */
public class SalesPerformanceDeptUser implements Serializable {
	private static final long serialVersionUID = 1975425935392985329L;
	/**   SALES_PERFORMANCE_DEPT_USER_ID **/
	private Integer salesPerformanceDeptUserId;
	
	/** 小组ID  SALES_PERFORMANCE_DEPT_ID **/
	private Integer salesPerformanceDeptId;
	
	/** 用户ID  USER_ID **/
	private Integer userId;
	
	/** 目标额(万元)  GOAL **/
	private BigDecimal goal;
	
	/** 创建时间  ADD_TIME **/
	private Long addTime;
	
	/** 创建人  CREATOR **/
	private Integer creator;
	
	/** 更新时间  MOD_TIME **/
	private Long modTime;
	
	/** 更新人  UPDATER **/
	private Integer updater;
	
	/**     SALES_PERFORMANCE_DEPT_USER_ID   **/
	public Integer getSalesPerformanceDeptUserId() {
		return salesPerformanceDeptUserId;
	}
	
	/**     SALES_PERFORMANCE_DEPT_USER_ID   **/
	public void setSalesPerformanceDeptUserId(Integer salesPerformanceDeptUserId) {
		this.salesPerformanceDeptUserId = salesPerformanceDeptUserId;
	}
	
	/**   小组ID  SALES_PERFORMANCE_DEPT_ID   **/
	public Integer getSalesPerformanceDeptId() {
		return salesPerformanceDeptId;
	}
	
	/**   小组ID  SALES_PERFORMANCE_DEPT_ID   **/
	public void setSalesPerformanceDeptId(Integer salesPerformanceDeptId) {
		this.salesPerformanceDeptId = salesPerformanceDeptId;
	}
	
	/**   用户ID  USER_ID   **/
	public Integer getUserId() {
		return userId;
	}
	
	/**   用户ID  USER_ID   **/
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	/**   目标额(万元)  GOAL   **/
	public BigDecimal getGoal() {
		return goal;
	}
	
	/**   目标额(万元)  GOAL   **/
	public void setGoal(BigDecimal goal) {
		this.goal = goal;
	}
	
	/**   创建时间  ADD_TIME   **/
	public Long getAddTime() {
		return addTime;
	}
	
	/**   创建时间  ADD_TIME   **/
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
	
	/**   创建人  CREATOR   **/
	public Integer getCreator() {
		return creator;
	}
	
	/**   创建人  CREATOR   **/
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
	/**   更新时间  MOD_TIME   **/
	public Long getModTime() {
		return modTime;
	}
	
	/**   更新时间  MOD_TIME   **/
	public void setModTime(Long modTime) {
		this.modTime = modTime;
	}
	
	/**   更新人  UPDATER   **/
	public Integer getUpdater() {
		return updater;
	}
	
	/**   更新人  UPDATER   **/
	public void setUpdater(Integer updater) {
		this.updater = updater;
	}
}
