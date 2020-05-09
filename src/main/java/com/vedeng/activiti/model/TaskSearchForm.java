package com.vedeng.activiti.model;

import java.io.Serializable;

/**
 * 流程定义表搜索对象
 * @author John
 * 
 */
public class TaskSearchForm implements Serializable{
	private String id;
	private String assignee;
	private String name;
	private String tenantId;
	private String businessKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	
	
}
