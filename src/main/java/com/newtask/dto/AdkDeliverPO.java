package com.newtask.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class AdkDeliverPO {
	private String fDate = "";
	private String fCom = "";
	private String sign = "";
	private String fNote = "";
	private String orderNo = "";
	private List<AdkDeliverProductPO> orderGoods = Lists.newArrayList();

	public void add(AdkDeliverProductPO p) {
		orderGoods.add(p);
	}

	public String getfDate() {
		return fDate;
	}

	public void setfDate(String fDate) {
		this.fDate = fDate;
	}

	public String getfCom() {
		return fCom;
	}

	public void setfCom(String fCom) {
		this.fCom = fCom;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getfNote() {
		return fNote;
	}

	public void setfNote(String fNote) {
		this.fNote = fNote;
	}

	public List<AdkDeliverProductPO> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<AdkDeliverProductPO> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}
