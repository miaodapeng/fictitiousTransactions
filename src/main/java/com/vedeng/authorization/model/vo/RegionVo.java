package com.vedeng.authorization.model.vo;

import java.io.Serializable;

import com.vedeng.authorization.model.Region;

public class RegionVo extends Region implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer province;//省
	
	private Integer city;//市
	
	private Integer zoneId;
	
	private String provinceName;//
	
	private String cityName;//城市名称
	
	private String zone;//区
	
	private Integer cityStart;//市
	private Integer cityEnd;//市
	
	public Integer getCityStart() {
		return cityStart;
	}
	
	public void setCityStart(Integer cityStart) {
		this.cityStart = cityStart;
	}
	
	public Integer getCityEnd() {
		return cityEnd;
	}
	
	public void setCityEnd(Integer cityEnd) {
		this.cityEnd = cityEnd;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

}
