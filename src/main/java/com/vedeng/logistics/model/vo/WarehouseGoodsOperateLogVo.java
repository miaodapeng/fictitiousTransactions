package com.vedeng.logistics.model.vo;

import com.vedeng.logistics.model.WarehouseGoodsOperateLog;

public class WarehouseGoodsOperateLogVo extends WarehouseGoodsOperateLog {
	
	private String operaterName;//操作人名称
	
	private String goodsName;//
	
	private String sku;
	
	private String materialCode;//物料编码
	
	private String brandName;//品牌
	
	private String model;//型号
	
	private String unitName;//单位

	private Integer isProblem;//是否有问题

	public String getOperaterName() {
		return operaterName;
	}

	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getIsProblem() {
		return isProblem;
	}

	public void setIsProblem(Integer isProblem) {
		this.isProblem = isProblem;
	}

}
