package com.vedeng.goods.command;

import com.vedeng.common.controller.BaseCommand;
import com.vedeng.common.model.FileInfo;
import com.vedeng.goods.model.vo.CoreSpuBaseVO;

import java.util.List;

public class SkuAddCommand extends BaseCommand {
	private CoreSpuBaseVO coreSpuBaseVO;
	private Integer spuId;
	private Integer skuId;

	private String skuCheckFiles;// 检测
	private String skuPatentFiles;// 专利

	public Integer[] getBaseAttributeValueId() {
		return baseAttributeValueId;
	}

	public void setBaseAttributeValueId(Integer[] baseAttributeValueId) {
		this.baseAttributeValueId = baseAttributeValueId;
	}

	private Integer[] baseAttributeValueId;


	public String getSkuInfo() {
		return skuInfo;
	}

	public void setSkuInfo(String skuInfo) {
		this.skuInfo = skuInfo;
	}

	private String skuInfo;//临时商品 ， model spec


	private String corePartPriceFile;// 核心零部件价格

	private String skuCheckFilesJson;// 检测回显
	private String skuPatentFilesJson;// 专利回显
	private String corePartPriceFileJson;// 核心零部件价格回显



	private List<FileInfo> skuCheck;//回显
	private List<FileInfo> skuPatent;//回显
	private List<FileInfo> skuPart;//回显

	private String[] paramsName1;
	private String[] paramsName2;
	private String[] paramsName3;

	private String[] paramsValue1;
	private String[] paramsValue2;
	private String[] paramsValue3;

	public String getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(String skuIds) {
		this.skuIds = skuIds;
	}

	private String skuIds;//批量设置备货

	public String getHasBackupMachine() {
		return hasBackupMachine;
	}

	public void setHasBackupMachine(String hasBackupMachine) {
		this.hasBackupMachine = hasBackupMachine;
	}

	private String hasBackupMachine;//批量设置备货



	//一些数字的验证回显
	private String changeNumStr;
	private String effectiveDaysStr;
	private String goodsHeightStr;
	private String goodsLengthStr;
	private String goodsWidthStr;
	private String grossWeightStr;
	private String minOrderStr;
	private String netWeightStr;
	private String packageHeightStr;
	private String packageLengthStr;
	private String packageWidthStr;
	private String qaOutPriceStr;
	private String qaResponseTimeStr;
	private String supplierExtendsGuaranteePriceStr;

	private String showType;//显示类型（区分显示字段）


	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getChangeNumStr() {
		return changeNumStr;
	}

	public void setChangeNumStr(String changeNumStr) {
		this.changeNumStr = changeNumStr;
	}

	public String getEffectiveDaysStr() {
		return effectiveDaysStr;
	}

	public void setEffectiveDaysStr(String effectiveDaysStr) {
		this.effectiveDaysStr = effectiveDaysStr;
	}

	public String getGoodsHeightStr() {
		return goodsHeightStr;
	}

	public void setGoodsHeightStr(String goodsHeightStr) {
		this.goodsHeightStr = goodsHeightStr;
	}

	public String getGoodsLengthStr() {
		return goodsLengthStr;
	}

	public void setGoodsLengthStr(String goodsLengthStr) {
		this.goodsLengthStr = goodsLengthStr;
	}

	public String getGoodsWidthStr() {
		return goodsWidthStr;
	}

	public void setGoodsWidthStr(String goodsWidthStr) {
		this.goodsWidthStr = goodsWidthStr;
	}

	public String getGrossWeightStr() {
		return grossWeightStr;
	}

	public void setGrossWeightStr(String grossWeightStr) {
		this.grossWeightStr = grossWeightStr;
	}

	public String getMinOrderStr() {
		return minOrderStr;
	}

	public void setMinOrderStr(String minOrderStr) {
		this.minOrderStr = minOrderStr;
	}

	public String getNetWeightStr() {
		return netWeightStr;
	}

	public void setNetWeightStr(String netWeightStr) {
		this.netWeightStr = netWeightStr;
	}

	public String getPackageHeightStr() {
		return packageHeightStr;
	}

	public void setPackageHeightStr(String packageHeightStr) {
		this.packageHeightStr = packageHeightStr;
	}

	public String getPackageLengthStr() {
		return packageLengthStr;
	}

	public void setPackageLengthStr(String packageLengthStr) {
		this.packageLengthStr = packageLengthStr;
	}

	public String getPackageWidthStr() {
		return packageWidthStr;
	}

	public void setPackageWidthStr(String packageWidthStr) {
		this.packageWidthStr = packageWidthStr;
	}

	public String getQaOutPriceStr() {
		return qaOutPriceStr;
	}

	public void setQaOutPriceStr(String qaOutPriceStr) {
		this.qaOutPriceStr = qaOutPriceStr;
	}

	public String getQaResponseTimeStr() {
		return qaResponseTimeStr;
	}

	public void setQaResponseTimeStr(String qaResponseTimeStr) {
		this.qaResponseTimeStr = qaResponseTimeStr;
	}

	public String getSupplierExtendsGuaranteePriceStr() {
		return supplierExtendsGuaranteePriceStr;
	}

	public void setSupplierExtendsGuaranteePriceStr(String supplierExtendsGuaranteePriceStr) {
		this.supplierExtendsGuaranteePriceStr = supplierExtendsGuaranteePriceStr;
	}

	public String[] getParamsName1() {
		return paramsName1;
	}

	public void setParamsName1(String[] paramsName1) {
		this.paramsName1 = paramsName1;
	}

	public String[] getParamsName2() {
		return paramsName2;
	}

	public void setParamsName2(String[] paramsName2) {
		this.paramsName2 = paramsName2;
	}

	public String[] getParamsName3() {
		return paramsName3;
	}

	public void setParamsName3(String[] paramsName3) {
		this.paramsName3 = paramsName3;
	}

	public String[] getParamsValue1() {
		return paramsValue1;
	}

	public void setParamsValue1(String[] paramsValue1) {
		this.paramsValue1 = paramsValue1;
	}

	public String[] getParamsValue2() {
		return paramsValue2;
	}

	public void setParamsValue2(String[] paramsValue2) {
		this.paramsValue2 = paramsValue2;
	}

	public String[] getParamsValue3() {
		return paramsValue3;
	}

	public void setParamsValue3(String[] paramsValue3) {
		this.paramsValue3 = paramsValue3;
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

	public List<FileInfo> getSkuPart() {
		return skuPart;
	}

	public void setSkuPart(List<FileInfo> skuPart) {
		this.skuPart = skuPart;
	}

	private Integer operateInfoId;

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	private Integer checkStatus;

	public String getLastCheckReason() {
		return lastCheckReason;
	}

	public void setLastCheckReason(String lastCheckReason) {
		this.lastCheckReason = lastCheckReason;
	}

	private String lastCheckReason;





	private Integer skuType;//1器械  2 耗材

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	private String tips;



	public Integer getSkuType() {
		return skuType;
	}

	public void setSkuType(Integer skuType) {
		this.skuType = skuType;
	}

	public String getCorePartPriceFile() {
		return corePartPriceFile;
	}

	public void setCorePartPriceFile(String corePartPriceFile) {
		this.corePartPriceFile = corePartPriceFile;
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



	public CoreSpuBaseVO getCoreSpuBaseVO() {
		return coreSpuBaseVO;
	}

	public void setCoreSpuBaseVO(CoreSpuBaseVO coreSpuBaseVO) {
		this.coreSpuBaseVO = coreSpuBaseVO;
	}



	public String getSkuCheckFiles() {
		return skuCheckFiles;
	}

	public void setSkuCheckFiles(String skuCheckFiles) {
		this.skuCheckFiles = skuCheckFiles;
	}

	public String getSkuPatentFiles() {
		return skuPatentFiles;
	}

	public void setSkuPatentFiles(String skuPatentFiles) {
		this.skuPatentFiles = skuPatentFiles;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSkuCheckFilesJson() {
		return skuCheckFilesJson;
	}

	public void setSkuCheckFilesJson(String skuCheckFilesJson) {
		this.skuCheckFilesJson = skuCheckFilesJson;
	}

	public String getSkuPatentFilesJson() {
		return skuPatentFilesJson;
	}

	public void setSkuPatentFilesJson(String skuPatentFilesJson) {
		this.skuPatentFilesJson = skuPatentFilesJson;
	}

	public String getCorePartPriceFileJson() {
		return corePartPriceFileJson;
	}

	public void setCorePartPriceFileJson(String corePartPriceFileJson) {
		this.corePartPriceFileJson = corePartPriceFileJson;
	}
}
