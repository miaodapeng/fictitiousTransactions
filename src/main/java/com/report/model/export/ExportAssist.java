package com.report.model.export;

import java.math.BigDecimal;

public class ExportAssist {

	private Integer relatedId;
	
	private Integer relatedData;
	
	private String relatedStr;
	
	private BigDecimal relatedDecimal;

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getRelatedData() {
		return relatedData;
	}

	public void setRelatedData(Integer relatedData) {
		this.relatedData = relatedData;
	}

	public String getRelatedStr() {
		return relatedStr;
	}

	public void setRelatedStr(String relatedStr) {
		this.relatedStr = relatedStr;
	}

	public BigDecimal getRelatedDecimal() {
		return relatedDecimal;
	}

	public void setRelatedDecimal(BigDecimal relatedDecimal) {
		this.relatedDecimal = relatedDecimal;
	}
	
}
