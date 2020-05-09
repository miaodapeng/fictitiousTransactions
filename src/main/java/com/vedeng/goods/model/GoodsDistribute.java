/**   
 * Copyright © 2019 公司名. All rights reserved.
 * 
 * @Title: GoodsDistribute.java 
 * @Prject: erp.vedeng.com
 * @Package: com.vedeng.goods.model 
 * @Description: TODO
 * @author: vedeng   
 * @date: 2019年6月4日 下午2:09:48 
 * @version: V1.0   
 */
package com.vedeng.goods.model;

import java.io.Serializable;

/** 
 * @ClassName: GoodsDistribute 
 * @Description: TODO
 * @author: vedeng
 * @date: 2019年6月4日 下午2:09:48  
 */
public class GoodsDistribute implements Serializable {

	
	private static final long serialVersionUID = 1L;
	//SPUID 
	private Integer spuId;
	
	//分配归属人专用
	private String spuIds;
	 //商品名称
	 private String spuName;
	 //spu编号
	 private String spuNo;
	 //归属产品经理
	 private String managerUsername;
	 //归属产品助理
	 private String assUsername;
	 //0:其他产品,1:核心产品、2:临时产品、
	 private Integer spuLevel;
	//搜索时用到的字段
	 private Integer brandNatureSearch;
	 //品牌分类
	 private String brandNature;
	 //品牌ID
	 private Integer brandId;
	 //品牌名称
	 private String brandName;
	 // 分类ID
	 private Integer categoryId;
	 //一级分类名称
	 private String categoryName;
	 //是否分配产品经理
	 private String hasM;
	 //是否分配助理
	 private String hasA;
	 //归属经理编号
	 private Integer assignmentManagerId;
	 //归属助理编号
	 private Integer assignmentAssistantId;

	 //已经分配归属经理编号
	 private Integer assignmentManagerIdOld;
	 //已经分配归属助理编号
	 private Integer assignmentAssistantIdOld;
	 
	 
	 
	 //待分配归属经理编号
	 private Integer assignmentManagerIdNew;
	 
	 //待分配助理编号
	 
	 private Integer assignmentAssistantIdNew;

	 //一级分类id
	 private Integer categoryLv1Name;
     //二级分类id
	 private Integer categoryLv2Name;
     //三级分类id
	 private Integer categoryLv3Name;
	 //分类的等级
	 private int categoryLevel;

	public Integer getCategoryLv1Name() {
		return categoryLv1Name;
	}

	public void setCategoryLv1Name(Integer categoryLv1Name) {
		this.categoryLv1Name = categoryLv1Name;
	}

	public Integer getCategoryLv2Name() {
		return categoryLv2Name;
	}

	public void setCategoryLv2Name(Integer categoryLv2Name) {
		this.categoryLv2Name = categoryLv2Name;
	}

	public Integer getCategoryLv3Name() {
		return categoryLv3Name;
	}

	public void setCategoryLv3Name(Integer categoryLv3Name) {
		this.categoryLv3Name = categoryLv3Name;
	}

	public int getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(int categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public Integer getSpuId() {
		return spuId;
	}
	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
	}
	public String getSpuName() {
		return spuName;
	}
	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}
	public String getManagerUsername() {
		return managerUsername;
	}
	public void setManagerUsername(String managerUsername) {
		this.managerUsername = managerUsername;
	}
	public String getAssUsername() {
		return assUsername;
	}
	public void setAssUsername(String assUsername) {
		this.assUsername = assUsername;
	}
	public Integer getSpuLevel() {
		return spuLevel;
	}
	public void setSpuLevel(Integer spuLevel) {
		this.spuLevel = spuLevel;
	}
	public String getBrandNature() {
		return brandNature;
	}
	public void setBrandNature(String brandNature) {
		this.brandNature = brandNature;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getHasM() {
		return hasM;
	}
	public void setHasM(String hasM) {
		this.hasM = hasM;
	}
	public String getHasA() {
		return hasA;
	}
	public void setHasA(String hasA) {
		this.hasA = hasA;
	}
	public Integer getAssignmentManagerId() {
		return assignmentManagerId;
	}
	public void setAssignmentManagerId(Integer assignmentManagerId) {
		this.assignmentManagerId = assignmentManagerId;
	}
	public Integer getAssignmentAssistantId() {
		return assignmentAssistantId;
	}
	public void setAssignmentAssistantId(Integer assignmentAssistantId) {
		this.assignmentAssistantId = assignmentAssistantId;
	}
	public String getSpuNo() {
		return spuNo;
	}
	public void setSpuNo(String spuNo) {
		this.spuNo = spuNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSpuIds() {
		return spuIds;
	}
	public void setSpuIds(String spuIds) {
		this.spuIds = spuIds;
	}
	public Integer getAssignmentManagerIdNew() {
		return assignmentManagerIdNew;
	}
	public void setAssignmentManagerIdNew(Integer assignmentManagerIdNew) {
		this.assignmentManagerIdNew = assignmentManagerIdNew;
	}
	public Integer getAssignmentAssistantIdNew() {
		return assignmentAssistantIdNew;
	}
	public void setAssignmentAssistantIdNew(Integer assignmentAssistantIdNew) {
		this.assignmentAssistantIdNew = assignmentAssistantIdNew;
	}
	public Integer getAssignmentManagerIdOld() {
		return assignmentManagerIdOld;
	}
	public void setAssignmentManagerIdOld(Integer assignmentManagerIdOld) {
		this.assignmentManagerIdOld = assignmentManagerIdOld;
	}
	public Integer getAssignmentAssistantIdOld() {
		return assignmentAssistantIdOld;
	}
	public void setAssignmentAssistantIdOld(Integer assignmentAssistantIdOld) {
		this.assignmentAssistantIdOld = assignmentAssistantIdOld;
	}

	public Integer getBrandNatureSearch() {
		return brandNatureSearch;
	}

	public void setBrandNatureSearch(Integer brandNatureSearch) {
		this.brandNatureSearch = brandNatureSearch;
	}
	
	
	

	 
}
