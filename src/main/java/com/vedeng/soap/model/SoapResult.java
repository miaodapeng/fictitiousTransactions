package com.vedeng.soap.model;

import javax.xml.bind.annotation.XmlRootElement;  

@XmlRootElement(name = "data")  
 
public class SoapResult<T> {
	private Integer code = -1;
	
	private String message = "操作失败";
	
	private String data;
	
	private Integer key_id;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getKey_id() {
		return key_id;
	}

	public void setKey_id(Integer key_id) {
		this.key_id = key_id;
	}

}
