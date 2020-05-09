package com.vedeng.common.util;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordComapnyIdToken extends UsernamePasswordToken {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public UsernamePasswordComapnyIdToken(String username, String password, Integer companyId) {
		super(username, password);
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	private Integer companyId;

	public boolean isNeedPassword() {
		return needPassword;
	}

	public void setNeedPassword(boolean needPassword) {
		this.needPassword = needPassword;
	}

	private boolean needPassword=true;
}
