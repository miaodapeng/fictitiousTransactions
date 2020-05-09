package com.vedeng.goods.model.dto;

public class SpuSkuIdForListDTO {
	private Integer spuId;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	private Integer brandId;

	private String skuIds;

	public Integer getSpuLevel() {
		return spuLevel;
	}

	public void setSpuLevel(Integer spuLevel) {
		this.spuLevel = spuLevel;
	}

	private Integer spuLevel;

	public Integer getSpuId() {
		return spuId;
	}

	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
	}

	public String getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(String skuIds) {
		this.skuIds = skuIds;
	}


}
