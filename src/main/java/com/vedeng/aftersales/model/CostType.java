package com.vedeng.aftersales.model;

public class CostType {

	private int sysOptionDefinitionId;
	
	private String comments;
	
	private int parentId;
	
	private int status;
	
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSysOptionDefinitionId() {
		return sysOptionDefinitionId;
	}

	public void setSysOptionDefinitionId(int sysOptionDefinitionId) {
		this.sysOptionDefinitionId = sysOptionDefinitionId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}