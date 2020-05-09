package com.vedeng.activiti.model;
/**
 * 历史任务搜索项
 * @author John
 *
 */
public class HistorySearchForm extends TaskSearchForm {
	
	private int complete;
	private String tenantId;
	private String businessKey;

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
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
