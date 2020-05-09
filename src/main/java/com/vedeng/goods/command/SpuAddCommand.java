package com.vedeng.goods.command;

import com.beust.jcommander.internal.Lists;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.controller.BaseCommand;
import com.vedeng.common.model.FileInfo;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.CoreSpuDetailVO;
import com.vedeng.goods.model.vo.DepartmentsHospitalGenerateVO;
import com.vedeng.system.model.SysOptionDefinition;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class SpuAddCommand extends BaseCommand {
	//
	// 数据库数据
	//
	private List<SysOptionDefinition> spuTypeList;
	private CoreSpuDetailVO coreSpuDetailVO;
	private FirstEngage firstEngage;
	private List<DepartmentsHospitalGenerateVO> departmentsHospitalList;
    private Integer assignmentManagerId;
    private Integer assignmentAssistantId;

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

	public String getSpuCheckFileJson() {
		return spuCheckFileJson;
	}

	public void setSpuCheckFileJson(String spuCheckFileJson) {
		this.spuCheckFileJson = spuCheckFileJson;
	}

	private String spuCheckFileJson;//用于回显

	public String getSpuPatentFilesJson() {
		return spuPatentFilesJson;
	}

	public void setSpuPatentFilesJson(String spuPatentFilesJson) {
		this.spuPatentFilesJson = spuPatentFilesJson;
	}

	private String spuPatentFilesJson;//用于回显




	public String getSpuCheckFiles() {
		return spuCheckFiles;
	}

	public void setSpuCheckFiles(String spuCheckFiles) {
		this.spuCheckFiles = spuCheckFiles;
	}

	private String  spuCheckFiles;// 检测

	public String getSpuPatentFiles() {
		return spuPatentFiles;
	}

	public void setSpuPatentFiles(String spuPatentFiles) {
		this.spuPatentFiles = spuPatentFiles;
	}

	private String  spuPatentFiles;// 专利

	public String getHospitalTags() {
		return hospitalTags;
	}

	public void setHospitalTags(String hospitalTags) {
		this.hospitalTags = hospitalTags;
	}

	private String hospitalTags;// 功能
	private String hospitalTagsShow;

	public String getHospitalTagsShow(){
		return StringUtils.replace(hospitalTags,"@_@"," 、");
	}
	private GoodsCheckStatusEnum[] checkStatusEnums = GoodsCheckStatusEnum.values();
	//
	// 页面数据
	//
	private Integer spuId;

	public Integer getTempSpuId() {
		return tempSpuId;
	}

	public void setTempSpuId(Integer tempSpuId) {
		this.tempSpuId = tempSpuId;
	}

	private Integer tempSpuId;

	private Integer categoryId;

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	private Integer categoryType;//1:医疗器械；2:非医疗器械
	private Integer spuLevel;// 0:其他产品,1:核心产品、2:临时产品、
	private Integer brandId;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	private String brandName;
	private Integer spuType = SysOptionConstant.ID_316;// 默认为器械
	private Integer firstEngageId;
	private String spuName;
	private String showName;
	private String wikiHref;

	public Integer getOperateInfoId() {
		return operateInfoId;
	}

	public void setOperateInfoId(Integer operateInfoId) {
		this.operateInfoId = operateInfoId;
	}

	private Integer operateInfoId;

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	private String tips;

	public GoodsCheckStatusEnum[] getCheckStatusEnums() {
		return checkStatusEnums;
	}

	public void setCheckStatusEnums(GoodsCheckStatusEnum[] checkStatusEnums) {
		this.checkStatusEnums = checkStatusEnums;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	private Integer checkStatus;
	private String registrationIcon;// 注册商标
	private Integer operateInfoFlag;
	private Integer spuCheckStatus;// spu审核状态
	private Integer[] departmentIds;// 科室


	private Integer[] baseAttributeIds;//已经选择的属性id

	private String lastCheckReason;

	private String showType;// 用于区分显示的字段

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public List<FileInfo> getSkuCheck() {
		return skuCheck;
	}

	public void setSkuCheck(List<FileInfo> skuCheck) {
		this.skuCheck = skuCheck;
	}

	public List<FileInfo> getSkuPatent() {
		return skuPatent;
	}

	public void setSkuPatent(List<FileInfo> skuPatent) {
		this.skuPatent = skuPatent;
	}

	private List<FileInfo> skuCheck;//回显
	private List<FileInfo> skuPatent;//回显


	//sku
	private String skuInfo;

	public Integer getSkuType() {
		return skuType;
	}

	public void setSkuType(Integer skuType) {
		this.skuType = skuType;
	}

	private Integer skuType;//1器械  2 耗材


	public String getSkuInfo() {
		return skuInfo;
	}

	public void setSkuInfo(String skuInfo) {
		this.skuInfo = skuInfo;
	}

	public Integer[] getBaseAttributeIds() {
		return baseAttributeIds;
	}

	public void setBaseAttributeIds(Integer[] baseAttributeIds) {
		this.baseAttributeIds = baseAttributeIds;
	}
	private List<BaseAttributeVo> baseAttributes = Lists.newArrayList();

	public Integer getOperateInfoFlag() {
		return operateInfoFlag;
	}

	public void setOperateInfoFlag(Integer operateInfoFlag) {
		this.operateInfoFlag = operateInfoFlag;
	}

	public List<SysOptionDefinition> getSpuTypeList() {
		return spuTypeList;
	}

	public void setSpuTypeList(List<SysOptionDefinition> spuTypeList) {
		this.spuTypeList = spuTypeList;
	}

	public FirstEngage getFirstEngage() {
		return firstEngage;
	}

	public void setFirstEngage(FirstEngage firstEngage) {
		this.firstEngage = firstEngage;
	}

	public List<DepartmentsHospitalGenerateVO> getDepartmentsHospitalList() {
		return departmentsHospitalList;
	}

	public void setDepartmentsHospitalList(List<DepartmentsHospitalGenerateVO> departmentsHospitalList) {
		this.departmentsHospitalList = departmentsHospitalList;
	}




	public Integer getSpuId() {
		return spuId;
	}

	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
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

	public Integer getSpuType() {
		return spuType;
	}

	public void setSpuType(Integer spuType) {
		this.spuType = spuType;
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

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getWikiHref() {
		return wikiHref;
	}

	public void setWikiHref(String wikiHref) {
		this.wikiHref = wikiHref;
	}


	public String getRegistrationIcon() {
		return registrationIcon;
	}

	public void setRegistrationIcon(String registrationIcon) {
		this.registrationIcon = registrationIcon;
	}

	public Integer getSpuCheckStatus() {
		return spuCheckStatus;
	}

	public void setSpuCheckStatus(Integer spuCheckStatus) {
		this.spuCheckStatus = spuCheckStatus;
	}

	public Integer[] getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(Integer[] departmentIds) {
		this.departmentIds = departmentIds;
	}


	public List<BaseAttributeVo> getBaseAttributes() {
		return baseAttributes;
	}

	public void setBaseAttributes(List<BaseAttributeVo> baseAttributes) {
		this.baseAttributes = baseAttributes;
	}

	public CoreSpuDetailVO getCoreSpuDetailVO() {
		return coreSpuDetailVO;
	}

	public void setCoreSpuDetailVO(CoreSpuDetailVO coreSpuDetailVO) {
		this.coreSpuDetailVO = coreSpuDetailVO;
	}

	public String getLastCheckReason() {
		return lastCheckReason;
	}

	public void setLastCheckReason(String lastCheckReason) {
		this.lastCheckReason = lastCheckReason;
	}


}
