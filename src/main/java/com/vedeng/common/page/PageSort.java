package com.vedeng.common.page;

import java.io.Serializable;

public class PageSort implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 排序字段，排序方式
	 */
	private String sortField,order;

	public PageSort() {
		super();
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
}
