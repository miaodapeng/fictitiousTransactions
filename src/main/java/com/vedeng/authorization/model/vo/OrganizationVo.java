package com.vedeng.authorization.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.authorization.model.Organization;
import com.vedeng.homepage.model.SalesGoalSetting;

/**
 * <b>Description:</b><br> DTO
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model.vo
 * <br><b>ClassName:</b> OrganizationVo
 * <br><b>Date:</b> 2017年11月22日 下午2:57:24
 */
public class OrganizationVo extends Organization {
	
	private Integer orgLeaderId;//部门负责人id
	
	private String orgLeader;//部门负责人
	
	private List<SalesGoalSetting> salesGoalSettingList;//全年目标值

	private List<Organization> erpClassify;

	public String getOrgLeader() {
		return orgLeader;
	}

	public void setOrgLeader(String orgLeader) {
		this.orgLeader = orgLeader;
	}

	public List<SalesGoalSetting> getSalesGoalSettingList() {
		return salesGoalSettingList;
	}

	public void setSalesGoalSettingList(List<SalesGoalSetting> salesGoalSettingList) {
		this.salesGoalSettingList = salesGoalSettingList;
	}

	public Integer getOrgLeaderId() {
		return orgLeaderId;
	}

	public void setOrgLeaderId(Integer orgLeaderId) {
		this.orgLeaderId = orgLeaderId;
	}

	public List<Organization> getErpClassify() {
		return erpClassify;
	}

	public void setErpClassify(List<Organization> erpClassify) {
		this.erpClassify = erpClassify;
	}
}
