package com.vedeng.activiti.model;

import java.io.Serializable;

/**
 * 
 * @author John
 *
 */
public class MyProcessDefinitionInfo implements Serializable{
	
	private String name;//流程定义名称
	private String key;//流程定义唯一码
	private int version;//流程定义版本号
	private String description;//流程定义说明
	private String tenantId;
	private String businessKey;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
