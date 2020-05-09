package com.vedeng.ordergoods.model.vo;

import java.util.List;

import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;

public class OrdergoodsStoreAccountVo extends OrdergoodsStoreAccount {
	private String traderName;
	
	private String mobile;
	
	private String area;
	
	private String owner;
	
	private String contact;
	
	private List<Integer> traderContactIds;
	
	private Integer areaId;

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<Integer> getTraderContactIds() {
		return traderContactIds;
	}

	public void setTraderContactIds(List<Integer> traderContactIds) {
		this.traderContactIds = traderContactIds;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
}
