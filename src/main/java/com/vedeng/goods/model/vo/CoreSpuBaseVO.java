package com.vedeng.goods.model.vo;

import com.beust.jcommander.internal.Lists;
import com.vedeng.common.constant.goods.GoodsCheckStatusEnum;
import com.vedeng.common.constant.goods.SpuLevelEnum;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.dto.CoreSpuBaseDTO;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.List;

public class CoreSpuBaseVO extends CoreSpuBaseDTO {

	private String registrationNumber;
	private String spuTypeShow;
	private String spuLevelShow;// 0:其他产品,1:核心产品、2:临时产品、
	private String checkStatusShow;
	private String modTimeShow;
	private String operateInfoIdShow;
	private Integer skuTotalSize = 0;
	private String skuIdsInSpu;
	private List<CoreSkuBaseVO> coreSkuBaseVOList = Lists.newArrayList();

	public List<CoreSkuBaseVO> getCoreSkuBaseVOList() {
		return coreSkuBaseVOList;
	}

	public void setCoreSkuBaseVOList(List<CoreSkuBaseVO> coreSkuBaseVOList) {
		this.coreSkuBaseVOList = coreSkuBaseVOList;
	}

	public String getSpuTypeShow() {
		return spuTypeShow;
	}

	public void setSpuTypeShow(String spuTypeShow) {


		this.spuTypeShow = spuTypeShow;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getSpuLevelShow() {
		spuLevelShow = SpuLevelEnum.levelName(getSpuLevel());
		return spuLevelShow;
	}

	public void setSpuLevelShow(String spuLevelShow) {
		this.spuLevelShow = spuLevelShow;
	}

	public String getCheckStatusShow() {
		checkStatusShow = GoodsCheckStatusEnum.statusName(getCheckStatus());
		return checkStatusShow;
	}

	public void setCheckStatusShow(String checkStatusShow) {
		this.checkStatusShow = checkStatusShow;
	}

	public String getModTimeShow() {
		if (getModTime() != null) {
			modTimeShow = DateFormatUtils.format(getModTime(), DateUtil.TIME_FORMAT);
		}
		return modTimeShow;
	}

	public void setModTimeShow(String modTimeShow) {
		this.modTimeShow = modTimeShow;
	}

	public String getOperateInfoIdShow() {
		operateInfoIdShow = NumberUtils.toInt(getOperateInfoId() + "") > 0 ? "已添加" : "未添加";
		return operateInfoIdShow;
	}

	public void setOperateInfoIdShow(String operateInfoIdShow) {
		this.operateInfoIdShow = operateInfoIdShow;
	}

	public Integer getSkuTotalSize() {
		return skuTotalSize;
	}

	public void setSkuTotalSize(Integer skuTotalSize) {
		this.skuTotalSize = skuTotalSize;
	}

	public String getSkuIdsInSpu() {
		return skuIdsInSpu;
	}

	public void setSkuIdsInSpu(String skuIdsInSpu) {
		this.skuIdsInSpu = skuIdsInSpu;
	}

}
