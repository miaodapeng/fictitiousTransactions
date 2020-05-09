package com.vedeng.system.model;

import java.io.Serializable;
import java.util.Date;

public class ActProcinst implements Serializable{

	private String id_,proc_def_id_,task_def_key_,proc_inst_id_,execution_id_,name_,parent_task_id_;
	
	private Date start_time_,claim_time_,end_time_,due_date_;
	
	private Long duration_;
	
	private String description_,owner_,assignee_,delete_reason_,form_key_,category_,tenant_id_;
	
	private Integer priority_;
	
	private String business_key_,end_time_str;

	
	
	public String getBusiness_key_() {
		return business_key_;
	}

	public void setBusiness_key_(String business_key_) {
		this.business_key_ = business_key_;
	}

	public String getId_() {
		return id_;
	}

	public void setId_(String id_) {
		this.id_ = id_;
	}

	public String getProc_def_id_() {
		return proc_def_id_;
	}

	public void setProc_def_id_(String proc_def_id_) {
		this.proc_def_id_ = proc_def_id_;
	}

	public String getTask_def_key_() {
		return task_def_key_;
	}

	public void setTask_def_key_(String task_def_key_) {
		this.task_def_key_ = task_def_key_;
	}

	public String getProc_inst_id_() {
		return proc_inst_id_;
	}

	public void setProc_inst_id_(String proc_inst_id_) {
		this.proc_inst_id_ = proc_inst_id_;
	}

	public String getExecution_id_() {
		return execution_id_;
	}

	public void setExecution_id_(String execution_id_) {
		this.execution_id_ = execution_id_;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getParent_task_id_() {
		return parent_task_id_;
	}

	public void setParent_task_id_(String parent_task_id_) {
		this.parent_task_id_ = parent_task_id_;
	}

	public Date getStart_time_() {
		return start_time_;
	}

	public void setStart_time_(Date start_time_) {
		this.start_time_ = start_time_;
	}

	public Date getClaim_time_() {
		return claim_time_;
	}

	public void setClaim_time_(Date claim_time_) {
		this.claim_time_ = claim_time_;
	}

	public Date getEnd_time_() {
		return end_time_;
	}

	public void setEnd_time_(Date end_time_) {
		this.end_time_ = end_time_;
	}

	public Date getDue_date_() {
		return due_date_;
	}

	public void setDue_date_(Date due_date_) {
		this.due_date_ = due_date_;
	}

	public Long getDuration_() {
		return duration_;
	}

	public void setDuration_(Long duration_) {
		this.duration_ = duration_;
	}

	public String getDescription_() {
		return description_;
	}

	public void setDescription_(String description_) {
		this.description_ = description_;
	}

	public String getOwner_() {
		return owner_;
	}

	public void setOwner_(String owner_) {
		this.owner_ = owner_;
	}

	public String getAssignee_() {
		return assignee_;
	}

	public void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}

	public String getDelete_reason_() {
		return delete_reason_;
	}

	public void setDelete_reason_(String delete_reason_) {
		this.delete_reason_ = delete_reason_;
	}

	public String getForm_key_() {
		return form_key_;
	}

	public void setForm_key_(String form_key_) {
		this.form_key_ = form_key_;
	}

	public String getCategory_() {
		return category_;
	}

	public void setCategory_(String category_) {
		this.category_ = category_;
	}

	public String getTenant_id_() {
		return tenant_id_;
	}

	public void setTenant_id_(String tenant_id_) {
		this.tenant_id_ = tenant_id_;
	}

	public Integer getPriority_() {
		return priority_;
	}

	public void setPriority_(Integer priority_) {
		this.priority_ = priority_;
	}

	public String getEnd_time_str() {
		return end_time_str;
	}

	public void setEnd_time_str(String end_time_str) {
		this.end_time_str = end_time_str;
	}
	
}
