package com.vedeng.firstengage.model;

import com.vedeng.common.controller.BaseCommand;
import com.vedeng.system.model.Attachment;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

//通过@GroupSequence指定验证顺序：先验证First分组，如果有错误立即返回而不会验证Second分组
public class FirstengageBrand extends BaseCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8593245333378295900L;

	private Integer brandId;

	private Integer companyId;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private String companyName;

	private Integer brandNature;// 品牌性质 1国产 2进口

	private String brandName;

	private String brandNameEn;// 英文名

	private String brandWebsite;

	private String owner;

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
	
	private String logoUri;//品牌LOGO

	private Integer source;// 品牌来源 0-ERP | 1-耗材商城 默认0
	
	private String keyWords;//查询关键字
	
	private String effectStartDate;// 查询起始时间

	private String effectEndDate;// 查询结束时间

	private Integer searchStatus;// 查询类型  1关键词  2 品牌名称 3 品牌ID
	
	private Integer timeSort;// 更新时间 1 由近到远 2 由远到近
	
	private List<Attachment> proof;//授权证明附件

	private String logoDomain;//LOGO DOMAIN
	
	private String comment;//删除描述信息

	private String manufacturer;// 生产企业

	private Blob logoObj;

	public Blob getLogoObj() {
		return logoObj;
	}

	public void setLogoObj(Blob logoObj) {
		this.logoObj = logoObj;
	}

	public String getBrandNameEn() {
        return brandNameEn;
    }

    public void setBrandNameEn(String brandNameEn) {
        this.brandNameEn = brandNameEn;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLogoDomain() {
		return logoDomain;
	}

	public void setLogoDomain(String logoDomain) {
		this.logoDomain = logoDomain;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}

	public List<Attachment> getProof() {
		return proof;
	}

	public void setProof(List<Attachment> proof) {
		this.proof = proof;
	}

	public String getCreatorNm() {
		return creatorNm;
	}
	
	public String getKeyWords() {
		return keyWords;
	}

	public Integer getTimeSort() {
		return timeSort;
	}


	public void setTimeSort(Integer timeSort) {
		this.timeSort = timeSort;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getEffectStartDate() {
		return effectStartDate;
	}

	public void setEffectStartDate(String effectStartDate) {
		this.effectStartDate = effectStartDate;
	}

	public String getEffectEndDate() {
		return effectEndDate;
	}

	public void setEffectEndDate(String effectEndDate) {
		this.effectEndDate = effectEndDate;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public Integer getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(Integer searchStatus) {
		this.searchStatus = searchStatus;
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

	class File{     
		private Integer attachmentFunction;
		
		private String uri;

		public Integer getAttachmentFunction() {
			return attachmentFunction;
		}

		public void setAttachmentFunction(Integer attachmentFunction) {
			this.attachmentFunction = attachmentFunction;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}
		
		
       
    }
	
}