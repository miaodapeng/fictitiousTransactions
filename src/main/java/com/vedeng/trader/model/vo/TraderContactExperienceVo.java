package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.TraderContactExperience;

public class TraderContactExperienceVo extends TraderContactExperience {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String brands;//经营品牌
	
	private String areas;//经营区域,区域最小id字符串合集
	
	private String address;//经营区域名称

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getAreas() {
		return areas;
	}

	public void setAreas(String areas) {
		this.areas = areas;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
