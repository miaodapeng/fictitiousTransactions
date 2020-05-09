package com.vedeng.system.model.vo;

import com.vedeng.system.model.Address;

public class AddressVo extends Address {
	
	private String companyName;//公司名称
	
	private String areas;//省市区中文
	
	private Integer province;
	
	private Integer city;
	
	private Integer zone;
	
	private Integer paramsConfigValueId;//
	
	private Integer isDefault;//是否默认--0：否；1：是

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getZone() {
		return zone;
	}

	public void setZone(Integer zone) {
		this.zone = zone;
	}

	public Integer getParamsConfigValueId() {
		return paramsConfigValueId;
	}

	public void setParamsConfigValueId(Integer paramsConfigValueId) {
		this.paramsConfigValueId = paramsConfigValueId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	
}
