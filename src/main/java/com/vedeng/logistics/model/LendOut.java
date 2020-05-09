package com.vedeng.logistics.model;

import java.util.List;

import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsVo;

public class LendOut {
    /** 外借主键id  LEND_OUT_ID **/
    private Integer lendOutId;

    /** 外借单号  LEND_OUT_NO **/
    private String lendOutNo;

    /** 客户ID  TRADER_ID **/
    private Integer traderId;

    /** 1:经销商（包含终端）2:供应商 3沟通  TRADER_TYPE **/
    private Integer traderType;

    /** 客户名称  TRADER_NAME **/
    private String traderName;

    /** 商品id  GOODS_ID **/
    private Integer goodsId;

    /** 商品名称  GOODS_NAME **/
    private String goodsName;

    /** 订货号  SKU **/
    private String sku;

    /** 0 进行中  1 已完结  2 已关闭  LEND_OUT_STATUS **/
    private Integer lendOutStatus;

    /** 1 样品外借  2 售后垫货  LEND_OUT_TYPE **/
    private Integer lendOutType;

    /** 售后订单号  AFTER_SALES_NO **/
    private String afterSalesNo;

    /** 售后订单ID  AFTER_SALES_ID **/
    private Integer afterSalesId;

    /** 借用数量  LEND_OUT_NUM **/
    private Integer lendOutNum;

    /** 已发数量  DELIVER_NUM 根据快递发出数量决定**/
    private Integer deliverNum;

    /** 归还数量  RETURN_NUM **/
    private Integer returnNum;

    /** 收货地区id  TAKE_TRADER_AREA_ID **/
    private Integer takeTraderAreaId;

    /** 收货地址id  TAKE_TRADER_ADDRESS_ID **/
    private Integer takeTraderAddressId;

    /** 收获联系人id  TAKE_TRADER_CONTACT_ID **/
    private Integer takeTraderContactId;

    /** 是否可用 0是 1否  IS_ENABLE **/
    private Boolean isEnable;

    /** 预计归还时间  RETURN_TIME **/
    private Long returnTime;

    /** 创建人  CREATOR **/
    private Integer creator;

    /** 创建时间  ADD_TIME **/
    private Long addTime;

    /** 更新时间  MOD_TIME **/
    private Long modTime;
    
    private Integer logisticsId;//物流公司id LOGISTICS_ID
    
    private Integer freightDescription;//运费说明 字典表  FREIGHT_DESCRIPTION
    
    private String logisticsComments;//物流备注   LOGISTICS_COMMENTS
    
    private String pickNums;// 订单的拣货数+每个批次拣货数
    
    private Integer businessType;//业务类型
    
    private String creatorName;//创建者名称
    
    private String idCnt;
    
    private String searchStr;//搜索关键字
    
    private Integer overdue;//逾期搜索关键字
    
    private Long overdueTime;//逾期时间
    
    private String returnTimeStr;//返回时间
    
    private Integer barcodeNum;//条码数量
    
    private Integer flag;//判断条件
    
    private Integer deliveystatus;//发货状态  1 未发货  2 部分发货   3全部发货
    
    private Integer num;
    
    public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	private List<Goods> goodslist; 
    
    private List<WarehouseGoodsOperateLog> wlist;
    
    public Integer getDeliveystatus() {
		return deliveystatus;
	}

	public void setDeliveystatus(Integer deliveystatus) {
		this.deliveystatus = deliveystatus;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getBarcodeNum() {
		return barcodeNum;
	}

	public void setBarcodeNum(Integer barcodeNum) {
		this.barcodeNum = barcodeNum;
	}

	public String getReturnTimeStr() {
		return returnTimeStr;
	}

	public void setReturnTimeStr(String returnTimeStr) {
		this.returnTimeStr = returnTimeStr;
	}

	public Integer getOverdue() {
		return overdue;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public Long getOverdueTime() {
		return overdueTime;
	}

	public void setOverdueTime(Long overdueTime) {
		this.overdueTime = overdueTime;
	}

	public static LendOut getinstance() {
    	return new LendOut();
    }
    
	public List<WarehouseGoodsOperateLog> getWlist() {
		return wlist;
	}

	public void setWlist(List<WarehouseGoodsOperateLog> wlist) {
		this.wlist = wlist;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Integer getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Integer logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getPickNums() {
		return pickNums;
	}

	public void setPickNums(String pickNums) {
		this.pickNums = pickNums;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public List<Goods> getGoodslist() {
		return goodslist;
	}

	public String getIdCnt() {
		return idCnt;
	}

	public void setIdCnt(String idCnt) {
		this.idCnt = idCnt;
	}

	public void setGoodslist(List<Goods> goodslist) {
		this.goodslist = goodslist;
	}

	public Integer getFreightDescription() {
		return freightDescription;
	}

	public void setFreightDescription(Integer freightDescription) {
		this.freightDescription = freightDescription;
	}

	public String getLogisticsComments() {
		return logisticsComments;
	}

	public void setLogisticsComments(String logisticsComments) {
		this.logisticsComments = logisticsComments;
	}

	/**   外借主键id  LEND_OUT_ID   **/
    public Integer getLendOutId() {
        return lendOutId;
    }

    /**   外借主键id  LEND_OUT_ID   **/
    public void setLendOutId(Integer lendOutId) {
        this.lendOutId = lendOutId;
    }

    /**   外借单号  LEND_OUT_NO   **/
    public String getLendOutNo() {
        return lendOutNo;
    }

    public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	/**   外借单号  LEND_OUT_NO   **/
    public void setLendOutNo(String lendOutNo) {
        this.lendOutNo = lendOutNo == null ? null : lendOutNo.trim();
    }


    /**   1:经销商（包含终端）2:供应商 3沟通  TRADER_TYPE   **/
    public Integer getTraderType() {
        return traderType;
    }

    /**   1:经销商（包含终端）2:供应商 3沟通  TRADER_TYPE   **/
    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    /**   客户名称  TRADER_NAME   **/
    public String getTraderName() {
        return traderName;
    }

    /**   客户名称  TRADER_NAME   **/
    public void setTraderName(String traderName) {
        this.traderName = traderName == null ? null : traderName.trim();
    }

    /**   商品id  GOODS_ID   **/
    public Integer getGoodsId() {
        return goodsId;
    }

    /**   商品id  GOODS_ID   **/
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**   商品名称  GOODS_NAME   **/
    public String getGoodsName() {
        return goodsName;
    }

    /**   商品名称  GOODS_NAME   **/
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**   订货号  SKU   **/
    public String getSku() {
        return sku;
    }

    /**   订货号  SKU   **/
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**   0 进行中  1 已完结  2 已关闭  LEND_OUT_STATUS   **/
    public Integer getLendOutStatus() {
        return lendOutStatus;
    }

    /**   0 进行中  1 已完结  2 已关闭  LEND_OUT_STATUS   **/
    public void setLendOutStatus(Integer lendOutStatus) {
        this.lendOutStatus = lendOutStatus;
    }

    /**   1 样品外借  2 售后垫货  LEND_OUT_TYPE   **/
    public Integer getLendOutType() {
        return lendOutType;
    }

    /**   1 样品外借  2 售后垫货  LEND_OUT_TYPE   **/
    public void setLendOutType(Integer lendOutType) {
        this.lendOutType = lendOutType;
    }

    /**   售后订单号  AFTER_SALES_NO   **/
    public String getAfterSalesNo() {
        return afterSalesNo;
    }

    /**   售后订单号  AFTER_SALES_NO   **/
    public void setAfterSalesNo(String afterSalesNo) {
        this.afterSalesNo = afterSalesNo == null ? null : afterSalesNo.trim();
    }

    /**   售后订单ID  AFTER_SALES_ID   **/
    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    /**   售后订单ID  AFTER_SALES_ID   **/
    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    /**   借用数量  LEND_OUT_NUM   **/
    public Integer getLendOutNum() {
        return lendOutNum;
    }

    /**   借用数量  LEND_OUT_NUM   **/
    public void setLendOutNum(Integer lendOutNum) {
        this.lendOutNum = lendOutNum;
    }

    /**   已发数量  DELIVER_NUM   **/
    public Integer getDeliverNum() {
        return deliverNum;
    }

    /**   已发数量  DELIVER_NUM   **/
    public void setDeliverNum(Integer deliverNum) {
        this.deliverNum = deliverNum;
    }

    /**   归还数量  RETURN_NUM   **/
    public Integer getReturnNum() {
        return returnNum;
    }

    /**   归还数量  RETURN_NUM   **/
    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    /**   收货地区id  TAKE_TRADER_AREA_ID   **/
    public Integer getTakeTraderAreaId() {
        return takeTraderAreaId;
    }

    /**   收货地区id  TAKE_TRADER_AREA_ID   **/
    public void setTakeTraderAreaId(Integer takeTraderAreaId) {
        this.takeTraderAreaId = takeTraderAreaId;
    }

    /**   收货地址id  TAKE_TRADER_ADDRESS_ID   **/
    public Integer getTakeTraderAddressId() {
        return takeTraderAddressId;
    }

    /**   收货地址id  TAKE_TRADER_ADDRESS_ID   **/
    public void setTakeTraderAddressId(Integer takeTraderAddressId) {
        this.takeTraderAddressId = takeTraderAddressId;
    }

    /**   收获联系人id  TAKE_TRADER_CONTACT_ID   **/
    public Integer getTakeTraderContactId() {
        return takeTraderContactId;
    }

    /**   收获联系人id  TAKE_TRADER_CONTACT_ID   **/
    public void setTakeTraderContactId(Integer takeTraderContactId) {
        this.takeTraderContactId = takeTraderContactId;
    }

    /**   是否可用 0是 1否  IS_ENABLE   **/
    public Boolean getIsEnable() {
        return isEnable;
    }

    /**   是否可用 0是 1否  IS_ENABLE   **/
    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**   预计归还时间  RETURN_TIME   **/
    public Long getReturnTime() {
        return returnTime;
    }

    /**   预计归还时间  RETURN_TIME   **/
    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }

    /**   创建人  CREATOR   **/
    public Integer getCreator() {
        return creator;
    }

    /**   创建人  CREATOR   **/
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**   创建时间  ADD_TIME   **/
    public Long getAddTime() {
        return addTime;
    }

    /**   创建时间  ADD_TIME   **/
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**   更新时间  MOD_TIME   **/
    public Long getModTime() {
        return modTime;
    }

    /**   更新时间  MOD_TIME   **/
    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }
}