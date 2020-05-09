package com.vedeng.logistics.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.order.model.vo.BuyorderGoodsVo;

public class Barcode implements Serializable
{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5787583877530177156L;

	private Integer barcodeId;

    private String barcode;

    private Integer type;

    private Integer detailGoodsId;

    private Integer goodsId;

    private Integer sequence;

    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String ftpPath;//ftp保存地址
    
    private BuyorderGoodsVo buyorderGood;//产品详情
    
    private String creatorName;//添加人名称
    
    private String domain;//域名
    
    private Integer buyorderId;//采购订单ID
    
    private List<Integer> buyorderIds;//采购/售后产品ID列表
    
    private String comment;//货仓备注
    
    private Integer companyId;//公司id
    
    private Integer isIn;//是否已经入库
    
    private Long warehouseInTime;//入库时间
    
    /**
     * STORAGEADDRESS
     * 仓库位置
     */
    private String storageAddress;
    
    private Long searchBegintime;//搜索开始时间
    
    private Long searchEndtime;//搜索结束时间
    
	public Long getSearchBegintime() {
        return searchBegintime;
    }

    public void setSearchBegintime(Long searchBegintime) {
        this.searchBegintime = searchBegintime;
    }

    public Long getSearchEndtime() {
        return searchEndtime;
    }

    public void setSearchEndtime(Long searchEndtime) {
        this.searchEndtime = searchEndtime;
    }

    public Integer getIsIn() {
		return isIn;
	}

	public void setIsIn(Integer isIn) {
		this.isIn = isIn;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
    
    public Integer getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(Integer barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public String getFtpPath() {
	return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
	this.ftpPath = ftpPath;
    }

    public BuyorderGoodsVo getBuyorderGood() {
	return buyorderGood;
    }

    public void setBuyorderGood(BuyorderGoodsVo buyorderGood) {
	this.buyorderGood = buyorderGood;
    }

    public String getCreatorName() {
	return creatorName;
    }

    public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
    }

    public String getDomain() {
	return domain;
    }

    public void setDomain(String domain) {
	this.domain = domain;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDetailGoodsId() {
		return detailGoodsId;
	}

	public void setDetailGoodsId(Integer detailGoodsId) {
		this.detailGoodsId = detailGoodsId;
	}

	public Integer getBuyorderId() {
	    return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
	    this.buyorderId = buyorderId;
	}

	public Long getWarehouseInTime() {
	    return warehouseInTime;
	}

	public void setWarehouseInTime(Long warehouseInTime) {
	    this.warehouseInTime = warehouseInTime;
	}

	public List<Integer> getBuyorderIds() {
	    return buyorderIds;
	}

	public void setBuyorderIds(List<Integer> buyorderIds) {
	    this.buyorderIds = buyorderIds;
	}

	public String getStorageAddress()
	{
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress)
	{
		this.storageAddress = storageAddress;
	}
	
}