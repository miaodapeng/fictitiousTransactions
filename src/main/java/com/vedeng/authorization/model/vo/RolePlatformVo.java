package com.vedeng.authorization.model.vo;

/**
 * @Author wayne.liu
 * @Description 角色平台Vo
 * @Date 2019/6/6 10:27
 */
public class RolePlatformVo{
	/**
	 * 角色Id
	 */
	private Integer roleId;

	/**
	 * 应用平台Id
	 */
	private Integer platformId;

	/**
	 * 应用平台名称
	 */
	private String platformName;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	@Override
	public String toString() {
		return "RolePlatformVo{" +
				"roleId=" + roleId +
				", platformId=" + platformId +
				", platformName=" + platformName +
				'}';
	}
}
