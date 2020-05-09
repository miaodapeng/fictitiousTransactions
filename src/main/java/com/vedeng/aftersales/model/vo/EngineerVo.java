package com.vedeng.aftersales.model.vo;

import java.util.List;

import com.vedeng.aftersales.model.Engineer;

public class EngineerVo extends Engineer {
	private Integer province;// 省

	private Integer city;// 市

	private Integer zone;// 区
	
	private String areaStr;//地区
	
	private Integer afterSalesId;//
	
	private Integer serviceTimes;//服务次数
	
	private Integer areaId;//
	
	private String addTimeStr;//
	
	private List<AfterSalesInstallstionVo> afterSalesInstallstions;//安调记录

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

	public String getAreaStr() {
		return areaStr;
	}

	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}

	public Integer getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(Integer serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

	public List<AfterSalesInstallstionVo> getAfterSalesInstallstions() {
		return afterSalesInstallstions;
	}

	public void setAfterSalesInstallstions(List<AfterSalesInstallstionVo> afterSalesInstallstions) {
		this.afterSalesInstallstions = afterSalesInstallstions;
	}

	public Integer getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(Integer afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	
}
