package com.vedeng.authorization.model.vo;

import java.util.List;

/**
 * @Author wayne.liu
 * @Description 角色管理Vo
 * @Date 2019/6/6 10:27
 */
public class RoleVo {

	/**
	 * 角色Id
	 */
	private Integer roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 应用平台Id
	 */
	private List<Integer> platformId;
	/**
	 * 公司Id
	 */
	private Integer companyId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Integer> getPlatformId() {
		return platformId;
	}

	public void setPlatformId(List<Integer> platformId) {
		this.platformId = platformId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "RoleVo{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				", platformId=" + platformId +
				", companyId=" + companyId +
				'}';
	}
}
