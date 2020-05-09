package com.vedeng.common.model;

import com.vedeng.common.page.Page;

import java.util.List;

/**
 * <b>Description:</b><br> json 数据模型
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.common.model
 * <br><b>ClassName:</b> ResultInfo
 * <br><b>Date:</b> 2017年4月25日 上午11:13:17
 */

public class ResultInfo<T> {

	private Integer code = -1;//0成功，-1失败
	
	private String message = "操作失败";
	
	private Object param;
	
	private T data;
	
	private Page page;
	
	private List<T> listData;//备用字段
	
	private Integer status = 0;
	
	public ResultInfo() {
		super();
	}
	
	public ResultInfo(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResultInfo(Integer code, String message, Integer status, T data) {
		super();
		this.code = code;
		this.message = message;
		this.status = status;
		this.data = data;
	}

	public ResultInfo(Integer code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ResultInfo(Integer code, String message, List<T> listData) {
		super();
		this.code = code;
		this.message = message;
		this.listData = listData;
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "ResultInfo [code=" + code + ", message=" + message + ", param=" + param + ", data=" + data + ", page="
				+ page + ", listData=" + listData + "]";
	}

	public String toSimpleString() {
		return "ResultInfo [code=" + code + ", message=" + message + "]";
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
