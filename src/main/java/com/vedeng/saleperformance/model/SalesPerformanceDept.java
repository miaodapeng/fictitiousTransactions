package com.vedeng.saleperformance.model;

import com.vedeng.authorization.model.User;

import java.util.List;

/**
 * 小组信息
 * <b>Description:</b><br>
 * @author bill.bo
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.model.saleperformance
 * <br><b>ClassName:</b> SalesPerformanceDept
 * <br><b>Date:</b> 2019年2月15日 下午5:53:55
 */
public class SalesPerformanceDept {

	/**
	 * 小组信息ID
	 */
	private Integer salesPerformanceDeptId;
	
	/**
	 * 团队ID
	 */
	private Integer salesPerformanceGroupId;
	
	/**
	 * 小组名称
	 */
	private String deptName;
	
	/**
	 * 是否删除0否 1是
	 */
	private Integer isDelete;
	
	/**
	 * 创建时间
	 */
    private Long addTime;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 更新时间
     */
    private Long modTime;

    /**
     * 更新人
     */
    private Integer updater;
	
	/**
	 * 负责人的主键
	 */
	private Integer[] ids;
	
	/**
	 * 用户列表
	 */
	private List<User> userList;
	
	/**
	 * 公司的ID
	 */
	private Integer companyId;
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
	public List<User> getUserList() {
		return userList;
	}
	
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	
	public Integer[] getIds() {
		return ids;
	}
	
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	
	public Integer getSalesPerformanceDeptId() {
		return salesPerformanceDeptId;
	}

	public void setSalesPerformanceDeptId(Integer salesPerformanceDeptId) {
		this.salesPerformanceDeptId = salesPerformanceDeptId;
	}

	public Integer getSalesPerformanceGroupId() {
		return salesPerformanceGroupId;
	}

	public void setSalesPerformanceGroupId(Integer salesPerformanceGroupId) {
		this.salesPerformanceGroupId = salesPerformanceGroupId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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
