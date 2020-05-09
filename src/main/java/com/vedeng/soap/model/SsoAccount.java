package com.vedeng.soap.model;

import java.io.Serializable;

public class SsoAccount implements Serializable{
	private Integer sso_account_id;
	
	private String account;
	
	private String email;
	
	private String mobile;
	
	private Integer email_validate;
	
	private Integer mobile_validate;
	
	private String nickname;
	
	private Integer status;
	
	private Integer source;
	
	private Integer add_time;
	
	private Integer mod_time;
	
	private Integer updater;
	
	private String uuid;
	
	private String corporation;
	
	private String weixin_openid;
	
	private Integer from;
	
	private String verifyCode;
	
	private String smsVerifyCode;
	
	private String regtype;
	
	private Integer isNew;

	public Integer getSso_account_id() {
		return sso_account_id;
	}

	public void setSso_account_id(Integer sso_account_id) {
		this.sso_account_id = sso_account_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getEmail_validate() {
		return email_validate;
	}

	public void setEmail_validate(Integer email_validate) {
		this.email_validate = email_validate;
	}

	public Integer getMobile_validate() {
		return mobile_validate;
	}

	public void setMobile_validate(Integer mobile_validate) {
		this.mobile_validate = mobile_validate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

	public Integer getMod_time() {
		return mod_time;
	}

	public void setMod_time(Integer mod_time) {
		this.mod_time = mod_time;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getWeixin_openid() {
		return weixin_openid;
	}

	public void setWeixin_openid(String weixin_openid) {
		this.weixin_openid = weixin_openid;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getSmsVerifyCode() {
		return smsVerifyCode;
	}

	public void setSmsVerifyCode(String smsVerifyCode) {
		this.smsVerifyCode = smsVerifyCode;
	}

	public String getRegtype() {
		return regtype;
	}

	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	
}
