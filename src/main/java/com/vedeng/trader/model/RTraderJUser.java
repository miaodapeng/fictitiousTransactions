package com.vedeng.trader.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.authorization.model.User;

public class RTraderJUser implements Serializable{
	private Integer rTraderJUserId;

	private Integer traderType;

	private Integer userId;

	private Integer traderId;

	private String traderName;

	private Integer areaId;
	
	private String areaIds;
	
	private String areaStr;

	private String ownerUser;

	private Integer changeTimes;

	private String level;
	
	private Integer companyId;
	
	private List<Integer> traderIds;

	private Integer source;

	private String userName;


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getrTraderJUserId() {
		return rTraderJUserId;
	}

	public void setrTraderJUserId(Integer rTraderJUserId) {
		this.rTraderJUserId = rTraderJUserId;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getChangeTimes() {
		return changeTimes;
	}

	public void setChangeTimes(Integer changeTimes) {
		this.changeTimes = changeTimes;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getAreaStr() {
		return areaStr;
	}

	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}

	public String getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public List<Integer> getTraderIds() {
		return traderIds;
	}

	public void setTraderIds(List<Integer> traderIds) {
		this.traderIds = traderIds;
	}

	public String getUserName() {
		return userName;
	}
}