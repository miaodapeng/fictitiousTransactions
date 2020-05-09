package com.vedeng.goods.command;

import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.controller.BaseCommand;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.system.model.SysOptionDefinition;

import java.util.Date;
import java.util.List;

public class SpuSearchCommand extends BaseCommand {

	private List<SysOptionDefinition> spuTypeList;



	private Integer tabCheckStatus;

	private FirstEngage firstEngage;

	private String searchType;// 多维搜索的方式
	private String searchValue;// 多维搜索的value

	private List<String> errors;

	private Integer spuId;
	private String spuIds;// 导出

	private Integer categoryId;
	private Integer spuLevel;// 0:其他产品,1:核心产品、2:临时产品、
	private Integer brandId;
	private Integer spuType;// 默认为器械
	private Integer firstEngageId;
	private String spuName;
	private String showName;
	private String wikiHref;

	private String spuStatus;

	private String registrationIcon;// 注册商标

	private Integer skuId;
	private String skuIds;// 批量设置备货

	private String hospitalDeptTags;

	private Integer operateInfoId;
	private Integer operateInfoFlag;
	private GoodsCheckStatusEnum[] checkStatus = GoodsCheckStatusEnum.values();

	private Integer manageCategoryLevel;// 管理类别
	private Integer spuCheckStatus;// spu审核状态

	private Integer skuCheckStatus;// sku审核状态
	private Integer newStandardCategoryId;// 新国标

	private Integer hasBackupMachine;// 0未设置备货

	private Integer productCompanyId;// 厂家

	private Integer pushStatus;// 推送的状态

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public Integer getIsStockup() {
		return isStockup;
	}

	public void setIsStockup(Integer isStockup) {
		this.isStockup = isStockup;
	}

	private Integer isStockup;
	public String getProductCompanyName() {
		return productCompanyName;
	}

	public void setProductCompanyName(String productCompanyName) {
		this.productCompanyName = productCompanyName;
	}

	private String productCompanyName;// 厂家


	private Integer departmentId;// 科室

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	private String departmentName;// 科室名称

	private Date modTimeStart;
	private Date modTimeEnd;


    private Integer spuIdSearch;

	public Integer getSpuIdSearch() {
		return spuIdSearch;
	}

	public void setSpuIdSearch(Integer spuIdSearch) {
		this.spuIdSearch = spuIdSearch;
	}

	private String deleteReason;



	private Integer assignmentManagerId;




	private Integer assignmentAssistantId;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private String brandName;



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

	public Integer getTabCheckStatus() {
		return tabCheckStatus;
	}

	public void setTabCheckStatus(Integer tabCheckStatus) {
		this.tabCheckStatus = tabCheckStatus;
	}
	public Integer getManageCategoryLevel() {
		return manageCategoryLevel;
	}

	public void setManageCategoryLevel(Integer manageCategoryLevel) {
		this.manageCategoryLevel = manageCategoryLevel;
	}

	public Integer getSpuCheckStatus() {
		return spuCheckStatus;
	}

	public void setSpuCheckStatus(Integer spuCheckStatus) {
		this.spuCheckStatus = spuCheckStatus;
	}

	public Integer getSkuCheckStatus() {
		return skuCheckStatus;
	}

	public void setSkuCheckStatus(Integer skuCheckStatus) {
		this.skuCheckStatus = skuCheckStatus;
	}

	public Integer getNewStandardCategoryId() {
		return newStandardCategoryId;
	}

	public void setNewStandardCategoryId(Integer newStandardCategoryId) {
		this.newStandardCategoryId = newStandardCategoryId;
	}

	public Integer getHasBackupMachine() {
		return hasBackupMachine;
	}

	public void setHasBackupMachine(Integer hasBackupMachine) {
		this.hasBackupMachine = hasBackupMachine;
	}

	public void setCheckStatus(GoodsCheckStatusEnum[] checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getSpuLevel() {
		return spuLevel;
	}

	public void setSpuLevel(Integer spuLevel) {
		this.spuLevel = spuLevel;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getFirstEngageId() {
		return firstEngageId;
	}

	public void setFirstEngageId(Integer firstEngageId) {
		this.firstEngageId = firstEngageId;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public String getWikiHref() {
		return wikiHref;
	}

	public void setWikiHref(String wikiHref) {
		this.wikiHref = wikiHref;
	}

	public String getSpuStatus() {
		return spuStatus;
	}

	public void setSpuStatus(String spuStatus) {
		this.spuStatus = spuStatus;
	}

	// public String[] getSpuCheckFiles() {
	// return spuCheckFiles;
	// }
	//
	// public void setSpuCheckFiles(String[] spuCheckFiles) {
	// this.spuCheckFiles = spuCheckFiles;
	// }
	//
	// public String[] getSpuPatentFiles() {
	// return spuPatentFiles;
	// }
	//
	// public void setSpuPatentFiles(String[] spuPatentFiles) {
	// this.spuPatentFiles = spuPatentFiles;
	// }
	//
	// public String[] getSpuPicFiles() {
	// return spuPicFiles;
	// }
	//
	// public void setSpuPicFiles(String[] spuPicFiles) {
	// this.spuPicFiles = spuPicFiles;
	// }

	public Integer getSpuType() {
		return spuType;
	}

	public void setSpuType(Integer spuType) {
		this.spuType = spuType;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<SysOptionDefinition> getSpuTypeList() {
		return spuTypeList;
	}

	public void setSpuTypeList(List<SysOptionDefinition> spuTypeList) {
		this.spuTypeList = spuTypeList;
	}

	public String getRegistrationIcon() {
		return registrationIcon;
	}

	public void setRegistrationIcon(String registrationIcon) {
		this.registrationIcon = registrationIcon;
	}

	public GoodsCheckStatusEnum[] getCheckStatus() {
		return checkStatus;
	}

	public Integer getOperateInfoId() {
		return operateInfoId;
	}

	public void setOperateInfoId(Integer operateInfoId) {
		this.operateInfoId = operateInfoId;
	}

	public Integer getSpuId() {
		return spuId;
	}

	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
	}

	public Integer getOperateInfoFlag() {
		return operateInfoFlag;
	}

	public void setOperateInfoFlag(Integer operateInfoFlag) {
		this.operateInfoFlag = operateInfoFlag;
	}

	// public CoreSpuGenerate getGenerate() {
	// return generate;
	// }
	//
	// public void setGenerate(CoreSpuGenerate generate) {
	// this.generate = generate;
	// }
	//
	public FirstEngage getFirstEngage() {
		return firstEngage;
	}

	public void setFirstEngage(FirstEngage firstEngage) {
		this.firstEngage = firstEngage;
	}

	public String getHospitalDeptTags() {
		return hospitalDeptTags;
	}

	public void setHospitalDeptTags(String hospitalDeptTags) {
		this.hospitalDeptTags = hospitalDeptTags;
	}

	// public Integer[] getDepartmentIds() {
	// return departmentIds;
	// }
	//
	// public void setDepartmentIds(Integer[] departmentIds) {
	// this.departmentIds = departmentIds;
	// }

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getProductCompanyId() {
		return productCompanyId;
	}

	public void setProductCompanyId(Integer productCompanyId) {
		this.productCompanyId = productCompanyId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Date getModTimeStart() {
		return modTimeStart;
	}

	public void setModTimeStart(Date modTimeStart) {
		this.modTimeStart = modTimeStart;
	}

	public Date getModTimeEnd() {
		return modTimeEnd;
	}

	public void setModTimeEnd(Date modTimeEnd) {
		this.modTimeEnd = modTimeEnd;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(String skuIds) {
		this.skuIds = skuIds;
	}

	public String getSpuIds() {
		return spuIds;
	}

	public void setSpuIds(String spuIds) {
		this.spuIds = spuIds;
	}


}
