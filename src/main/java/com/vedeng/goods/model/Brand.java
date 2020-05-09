package com.vedeng.goods.model;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.vedeng.common.validation.Interface.First;
import com.vedeng.common.validation.Interface.Second;

//通过@GroupSequence指定验证顺序：先验证First分组，如果有错误立即返回而不会验证Second分组
@GroupSequence({ First.class, Second.class, Brand.class })
public class Brand implements Serializable {

	private Integer brandId;

	private Integer companyId;

	private Integer brandNature;// 品牌性质 1国产 2进口

	@NotBlank(message = "名称不能为空", groups = { First.class }) // 优先验证
	@Size(min = 1, max = 32, message = "名称长度应为1-32位字符", groups = { Second.class })
	private String brandName;

	@Size(min = 1, max = 32, message = "链接长度应为1-32位字符", groups = { First.class }) // 优先验证
	@Pattern(regexp = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$", message = "链接地址不符合要求，请验证", groups = {
			First.class, Second.class })
	private String brandWebsite;

	@Pattern(regexp = "^[a-z]+$|^$", message = "测试", groups = { Second.class })
	private String owner;

	private String logoDomain;

	private String logoUri;

	private Integer sort;

	private String initialCn;

	private String initialEn;

	private String description;

	private Long addTime;

	private Integer creator;

	private Long modTime;

	private Integer updater;

	private String creatorNm;// 创建人

	private String updaterNm;// 修改人

	private String addTimeStr;// 添加时间

	private String modTimeStr;// 修改时间

	private Integer goodsNum;// 产品数量

	private String logoUriName;

	private Integer source;// 品牌来源 0-ERP | 1-耗材商城 默认0

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getUpdaterNm() {
		return updaterNm;
	}

	public void setUpdaterNm(String updaterNm) {
		this.updaterNm = updaterNm;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getModTimeStr() {
		return modTimeStr;
	}

	public void setModTimeStr(String modTimeStr) {
		this.modTimeStr = modTimeStr;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public String getBrandWebsite() {
		return brandWebsite;
	}

	public void setBrandWebsite(String brandWebsite) {
		this.brandWebsite = brandWebsite == null ? null : brandWebsite.trim();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner == null ? null : owner.trim();
	}

	public String getLogoDomain() {
		return logoDomain;
	}

	public void setLogoDomain(String logoDomain) {
		this.logoDomain = logoDomain == null ? null : logoDomain.trim();
	}

	public String getLogoUri() {
		return logoUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri == null ? null : logoUri.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getInitialCn() {
		return initialCn;
	}

	public void setInitialCn(String initialCn) {
		this.initialCn = initialCn == null ? null : initialCn.trim();
	}

	public String getInitialEn() {
		return initialEn;
	}

	public void setInitialEn(String initialEn) {
		this.initialEn = initialEn == null ? null : initialEn.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Long getAddTime() {
		return addTime;
	}

	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Long getModTime() {
		return modTime;
	}

	public void setModTime(Long modTime) {
		this.modTime = modTime;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public Integer getBrandNature() {
		return brandNature;
	}

	public void setBrandNature(Integer brandNature) {
		this.brandNature = brandNature;
	}

	public String getLogoUriName() {
		return logoUriName;
	}

	public void setLogoUriName(String logoUriName) {
		this.logoUriName = logoUriName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

}