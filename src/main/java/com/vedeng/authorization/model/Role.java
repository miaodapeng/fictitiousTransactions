package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 角色bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> Role
 * <br><b>Date:</b> 2017年4月25日 上午11:09:12
 */
public class Role implements Serializable{
    /**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1777031787189067994L;

	private Integer roleId;

    private Integer companyId;

    private String roleName;
    
    private String patentId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

	public String getPatentId() {
		return patentId;
	}

	public void setPatentId(String patentId) {
		this.patentId = patentId;
	}
    
}