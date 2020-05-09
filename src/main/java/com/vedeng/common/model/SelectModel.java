package com.vedeng.common.model;

import java.util.List;

public class SelectModel {
	
	private String id;

	private String text;
	
	private List<?> list;
	
	private String level;
	
	private String str;

	public SelectModel() {
	}
	public SelectModel(String id,String text) {
		this.id = id;
		this.text = text;
	}
	
	public java.util.List<SelectModel> getChildren() {
		return children;
	}

	public void setChildren(java.util.List<SelectModel> children) {
		this.children = children;
	}

	private java.util.List<SelectModel> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
