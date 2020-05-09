package com.vedeng.common.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.authorization.model.User;

public class SearchModel implements Serializable{
	private Long startDateLong;
	
	private Long startDateLongTwo;
	
	private Long endDateLong;
	
	private Integer companyId;
	
	private Integer relateData;
	
	private String relateStr;
	
	private Integer relateDataTwo;
	
	private List<Integer> relateDataList;
	
	private List<User> userList;
	
	private Integer relateDataThree;
	
	private Integer relateDataFour;
	
	private String relateStrTwo;

	public Long getStartDateLong() {
		return startDateLong;
	}

	public void setStartDateLong(Long startDateLong) {
		this.startDateLong = startDateLong;
	}

	public Long getEndDateLong() {
		return endDateLong;
	}

	public void setEndDateLong(Long endDateLong) {
		this.endDateLong = endDateLong;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getRelateData() {
		return relateData;
	}

	public void setRelateData(Integer relateData) {
		this.relateData = relateData;
	}

	public String getRelateStr() {
		return relateStr;
	}

	public void setRelateStr(String relateStr) {
		this.relateStr = relateStr;
	}

	public Integer getRelateDataTwo() {
		return relateDataTwo;
	}

	public void setRelateDataTwo(Integer relateDataTwo) {
		this.relateDataTwo = relateDataTwo;
	}

	public Long getStartDateLongTwo() {
		return startDateLongTwo;
	}

	public void setStartDateLongTwo(Long startDateLongTwo) {
		this.startDateLongTwo = startDateLongTwo;
	}

	public List<Integer> getRelateDataList() {
		return relateDataList;
	}

	public void setRelateDataList(List<Integer> relateDataList) {
		this.relateDataList = relateDataList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getRelateDataThree() {
		return relateDataThree;
	}

	public void setRelateDataThree(Integer relateDataThree) {
		this.relateDataThree = relateDataThree;
	}

	public Integer getRelateDataFour() {
		return relateDataFour;
	}

	public void setRelateDataFour(Integer relateDataFour) {
		this.relateDataFour = relateDataFour;
	}

	public String getRelateStrTwo() {
		return relateStrTwo;
	}

	public void setRelateStrTwo(String relateStrTwo) {
		this.relateStrTwo = relateStrTwo;
	}
	
	
}
