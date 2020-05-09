package com.vedeng.logistics.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.authorization.model.User;

public class WarehouseGoodsOperateLog implements Serializable
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2822391713822945210L;

	private Integer warehouseGoodsOperateLogId;

	private Integer barcodeId;

	private Integer companyId;

	private Integer operateType;

	private Integer relatedId;

	private Integer goodsId;

	private String barcodeFactory;

	private Integer num;

	private Integer warehouseId;

	private Integer storageRoomId;

	private Integer storageAreaId;

	private Integer storageLocationId;

	private Integer storageRackId;

	private String batchNumber;

	private Long productDate;

	private Long expirationDate;

	private Integer checkStatus;

	private Integer checkStatusUser;

	private Long checkStatusTime;

	private Integer recheckStatus;

	private Integer recheckStatusUser;

	private Long recheckStatusTime;

	private String comments;

	private Long addTime;

	private Integer creator;

	private Long modTime;

	private Integer updater;

	private Integer isEnable;

	private Integer warehousePickingDetailId;

	private String goodsName;//产品名称

	private String model;//型号

	private String brandName;//品牌

	private String materialCode;//物料编码

	private String unitName;//单位

	private String sku;//订货号

	private Long outTime;//出库时间

	private String operator;//入库操作人

	private String checkUserName; //入库验收人

	private String recheckUserName; //出库复核人

	private String warehouseArea;//存储位置

	private String buyorderNo;//采购单号

	private Integer buyorderId;//采购ID

	private String saleorderNo;//销售单号

	private Integer saleorderId;//销售ID

	private Integer afterSalesId;//售后id

	private String afterSalesNo;//售后单号
	private Integer lendOutId;  //外借Id
	private Integer cnt;//每个效期的产品数

	private Integer storeNum;//库存数量（该入库记录数量减去出库记录数量）

	private Integer storeTotalNum;//库存总数量（根据批次号和产品ID分组 该入库记录数量减去出库记录数量）

	private BigDecimal price;//单价

	private Integer pickCnt=0;//需要拣货的商品个数

	private String pCtn;//当前商品拣货个数

	private BigDecimal xsprice;//销售单价

	private BigDecimal cgprice;//采购单价

	private String buytraderName; //发货方

	private String saletraderName; //收货方

	private String barcode; //二维码

	private Integer isBarcode=0;//是否是查询已存在二维码0：否，1：是

	private Long beginTime; //开始时间

	private Long endTime; //开始时间

	private Integer outGoodsCnt;//出库个数（ 按入库日期、批次号、效期截止日期、关联采购单四个维度来区分）

	private String expirationDateStr;//效期

	private Integer expirationDateStatus;//效期状态（1，存在，2不存在，3全部）

	private String opName;//出库人

	private String traderName;//供应商

	private Integer zkCnt;//在库数量

	private Integer rowNum;//所在行数

	private String addtimes;//出入库统计时间

	private Integer nums;//出入库统计数

	private Integer ywType;//业务类型

	private Integer orderId;//业务订单id

	private String orderNo;//业务订单No 

	private Integer pickdId;//拣货商品id

	private Integer pCount=0;//自动分配拣货数量

	private String creatorNm;

	private Integer zkN;//在库

	private Integer bussinessType=0;//1退换货0销售出库 

	private String flag;//订货号

	private String warehouseName;//仓库名称

	private String storageRoomName;//库房名称

	private String storageAreaName;//货区名称

	private String storageRackName;//货架名称

	private String storageLocationName;//库位名称

	private String add_time_str;//入库时间

	private String expiration_date_str;//效期

	private String productDateStr;//生产日期

	private Integer expirationDateWarnStatus;//效期预警状态（1，有效，2 临期，3 失效，4 全部）

	private String problemRemark;  //问题原因

	// begin add by Franlin for [需求:打印广州分公司的出库入库单] at 2018-05-22
	/**
	 * 生产厂家
	 * 对应表T_GOODS中MANUFACTURER
	 */
	private String manufacturer;

	/**
	 * 生产许可证号
	 * 对应表T_GOODS中PRODUCTION_LICENSE
	 */
	private String productionLicenseNumber;
	/*
	 * 生产企业许可证号或备案凭证编号
	 * 对应表 T_PRODUCT_COMPANY 中  PRODUCT_COMPANY_LICENCE
	 */
	private String productCompanyLicence;
	/**
	 * 注册证号
	 * 对应表T_GOODS中REGISTRATION_NUMBER
	 */
	private String registrationNumber;
	/**
	 * 商品备案编号
	 * 对应表T_GOODS中RECORD_NUMBER
	 */
	private String recordNumber;
	
	/**
	 *存储温度
	 *对应表 T_FIRST_ENGAGE 中TEMPERATURE
	 */
	private String temperaTure;
	/*
	 * 存储条件
	 * 对应表V_CORE_SKU 中的 STORAGE_CONDITION_ONE
	 * 1常温、2冷冻、3冷藏
	 */
	private Integer storageConditionOne;
	/**
	 * 字典表
	 * 对应表 T_FIRST_ENGAGE 中CONDITION_ONE
	 */
	private Integer conditionOne; 
	/**
	 * 商品类级
	 * 对应表 T_SYS_OPTION_DEFINITION 中 TITLE
	 */
	private String title;
	/**
	 * 商品产地
	 * 对应表T_GOODS中PRODUCT_ADDRESS
	 */
	private String productAddress;

	

	// end add by Franlin for [需求:打印广州分公司的出库入库单] at 2018-05-22

	private List<Integer> idList;

	private Integer lockedStatus=0;//锁定状态0未锁定 1已锁定

	private String timeStr;//出库单时间

	private Integer goodsUserId;//商品归属

	private List<Integer> categoryIdList;// 产品归属对应的分类Id列表

	private Integer categoryId;//商品分类id

	private List<User> userList;//产品负责人
	/*
	 * 商品真实单价
	 * 对应表T_SALEORDER_GOODS表REAL_PRICE
	 */
	private  BigDecimal realPrice; //
	/*
	 * 当前订单商品实际金额
	 * 对应T_SALEORDER_GOODS表MAX_SKU_REFUND_AMOUNT
	 */
	private BigDecimal maxSkuRefundAmount;
	/*
	 * 订单类型
	 * 对应T_SALEORDER表中的ORDER_TYPE
	 */
	private Integer orderType;
	
	List<Long> dates = new ArrayList<>();
	
	List<Integer> creators = new ArrayList<>();

	private String addTimeStr;

	private Integer isActionGoods = 0;//活动商品   0否 1是

    private Integer operatorfalg;//业务flag

	private BigDecimal amount;//总价

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getIsActionGoods() {
		return isActionGoods;
	}

	public void setIsActionGoods(Integer isActionGoods) {
		this.isActionGoods = isActionGoods;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getMaxSkuRefundAmount() {
		return maxSkuRefundAmount;
	}

	public void setMaxSkuRefundAmount(BigDecimal maxSkuRefundAmount) {
		this.maxSkuRefundAmount = maxSkuRefundAmount;
	}

	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}

	public Integer getStorageConditionOne() {
		return storageConditionOne;
	}

	public void setStorageConditionOne(Integer storageConditionOne) {
		this.storageConditionOne = storageConditionOne;
	}

	public String getProductCompanyLicence() {
		return productCompanyLicence;
	}

	public void setProductCompanyLicence(String productCompanyLicence) {
		this.productCompanyLicence = productCompanyLicence;
	}

	public String getTemperaTure() {
		return temperaTure;
	}

	public void setTemperaTure(String temperaTure) {
		this.temperaTure = temperaTure;
	}

	public Integer getConditionOne() {
		return conditionOne;
	}

	public void setConditionOne(Integer conditionOne) {
		this.conditionOne = conditionOne;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

    public Integer getOperatorfalg() {
        return operatorfalg;
    }

    public void setOperatorfalg(Integer operatorfalg) {
        this.operatorfalg = operatorfalg;
    }

    public List<Long> getDates() {
		return dates;
	}

	public void setDates(List<Long> dates) {
		this.dates = dates;
	}

	public List<Integer> getCreators() {
		return creators;
	}

	public void setCreators(List<Integer> creators) {
		this.creators = creators;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public Integer getGoodsUserId() {
		return goodsUserId;
	}

	public void setGoodsUserId(Integer goodsUserId) {
		this.goodsUserId = goodsUserId;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public Integer getLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(Integer lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public String getAdd_time_str() {
		return add_time_str;
	}

	public void setAdd_time_str(String add_time_str) {
		this.add_time_str = add_time_str;
	}

	public String getExpiration_date_str() {
		return expiration_date_str;
	}

	public void setExpiration_date_str(String expiration_date_str) {
		this.expiration_date_str = expiration_date_str;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Integer getZkN() {
		return zkN;
	}

	public void setZkN(Integer zkN) {
		this.zkN = zkN;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getExpirationDateStr() {
		return expirationDateStr;
	}

	public void setExpirationDateStr(String expirationDateStr) {
		this.expirationDateStr = expirationDateStr;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getpCount() {
		return pCount;
	}

	public void setpCount(Integer pCount) {
		this.pCount = pCount;
	}

	public Integer getIsBarcode() {
		return isBarcode;
	}

	public void setIsBarcode(Integer isBarcode) {
		this.isBarcode = isBarcode;
	}

	public Integer getPickdId() {
		return pickdId;
	}

	public void setPickdId(Integer pickdId) {
		this.pickdId = pickdId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getYwType() {
		return ywType;
	}

	public void setYwType(Integer ywType) {
		this.ywType = ywType;
	}

	public Integer getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(Integer afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public String getAddtimes() {
		return addtimes;
	}

	public void setAddtimes(String addtimes) {
		this.addtimes = addtimes;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getZkCnt() {
		return zkCnt;
	}

	public void setZkCnt(Integer zkCnt) {
		this.zkCnt = zkCnt;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Integer getWarehousePickingDetailId() {
		return warehousePickingDetailId;
	}

	public void setWarehousePickingDetailId(Integer warehousePickingDetailId) {
		this.warehousePickingDetailId = warehousePickingDetailId;
	}

	public Integer getOutGoodsCnt() {
		return outGoodsCnt;
	}

	public void setOutGoodsCnt(Integer outGoodsCnt) {
		this.outGoodsCnt = outGoodsCnt;
	}

	public BigDecimal getXsprice() {
		return xsprice;
	}

	public void setXsprice(BigDecimal xsprice) {
		this.xsprice = xsprice;
	}

	public BigDecimal getCgprice() {
		return cgprice;
	}

	public void setCgprice(BigDecimal cgprice) {
		this.cgprice = cgprice;
	}

	public String getpCtn() {
		return pCtn;
	}

	public void setpCtn(String pCtn) {
		this.pCtn = pCtn;
	}

	public Integer getPickCnt() {
		return pickCnt;
	}

	public void setPickCnt(Integer pickCnt) {
		this.pickCnt = pickCnt;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getWarehouseArea() {
		return warehouseArea;
	}

	public void setWarehouseArea(String warehouseArea) {
		this.warehouseArea = warehouseArea;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getOutTime() {
		return outTime;
	}

	public void setOutTime(Long outTime) {
		this.outTime = outTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getWarehouseGoodsOperateLogId() {
		return warehouseGoodsOperateLogId;
	}

	public void setWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId) {
		this.warehouseGoodsOperateLogId = warehouseGoodsOperateLogId;
	}

	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getBarcodeFactory() {
		return barcodeFactory;
	}

	public void setBarcodeFactory(String barcodeFactory) {
		this.barcodeFactory = barcodeFactory == null ? null : barcodeFactory.trim();
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getStorageRoomId() {
		return storageRoomId;
	}

	public void setStorageRoomId(Integer storageRoomId) {
		this.storageRoomId = storageRoomId;
	}

	public Integer getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(Integer storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public Integer getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(Integer storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	public Integer getStorageRackId() {
		return storageRackId;
	}

	public void setStorageRackId(Integer storageRackId) {
		this.storageRackId = storageRackId;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber == null ? null : batchNumber.trim();
	}

	public Long getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Long expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getCheckStatusUser() {
		return checkStatusUser;
	}

	public void setCheckStatusUser(Integer checkStatusUser) {
		this.checkStatusUser = checkStatusUser;
	}

	public Long getCheckStatusTime() {
		return checkStatusTime;
	}

	public void setCheckStatusTime(Long checkStatusTime) {
		this.checkStatusTime = checkStatusTime;
	}

	public Integer getRecheckStatus() {
		return recheckStatus;
	}

	public void setRecheckStatus(Integer recheckStatus) {
		this.recheckStatus = recheckStatus;
	}

	public Integer getRecheckStatusUser() {
		return recheckStatusUser;
	}

	public void setRecheckStatusUser(Integer recheckStatusUser) {
		this.recheckStatusUser = recheckStatusUser;
	}

	public Long getRecheckStatusTime() {
		return recheckStatusTime;
	}

	public void setRecheckStatusTime(Long recheckStatusTime) {
		this.recheckStatusTime = recheckStatusTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments == null ? null : comments.trim();
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

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public String getBuytraderName() {
		return buytraderName;
	}

	public void setBuytraderName(String buytraderName) {
		this.buytraderName = buytraderName == null ? null : buytraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getSaletraderName() {
		return saletraderName;
	}

	public void setSaletraderName(String saletraderName) {
		this.saletraderName = saletraderName == null ? null : saletraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getRecheckUserName() {
		return recheckUserName;
	}

	public void setRecheckUserName(String recheckUserName) {
		this.recheckUserName = recheckUserName;
	}

	public Integer getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getExpirationDateStatus() {
		return expirationDateStatus;
	}

	public void setExpirationDateStatus(Integer expirationDateStatus) {
		this.expirationDateStatus = expirationDateStatus;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getStorageRoomName() {
		return storageRoomName;
	}

	public void setStorageRoomName(String storageRoomName) {
		this.storageRoomName = storageRoomName;
	}

	public String getStorageAreaName() {
		return storageAreaName;
	}

	public void setStorageAreaName(String storageAreaName) {
		this.storageAreaName = storageAreaName;
	}

	public String getStorageRackName() {
		return storageRackName;
	}

	public void setStorageRackName(String storageRackName) {
		this.storageRackName = storageRackName;
	}

	public String getStorageLocationName() {
		return storageLocationName;
	}

	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}

	public Integer getStoreTotalNum() {
		return storeTotalNum;
	}

	public void setStoreTotalNum(Integer storeTotalNum) {
		this.storeTotalNum = storeTotalNum;
	}

	public Integer getExpirationDateWarnStatus() {
		return expirationDateWarnStatus;
	}

	public void setExpirationDateWarnStatus(Integer expirationDateWarnStatus) {
		this.expirationDateWarnStatus = expirationDateWarnStatus;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer == null ? null : manufacturer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getProductionLicenseNumber()
	{
		return productionLicenseNumber;
	}

	public void setProductionLicenseNumber(String productionLicenseNumber)
	{
		this.productionLicenseNumber = productionLicenseNumber;
	}

	public String getRegistrationNumber()
	{
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber)
	{
		this.registrationNumber = registrationNumber;
	}

	public String getProductAddress()
	{
		return productAddress;
	}

	public void setProductAddress(String productAddress)
	{
		this.productAddress = productAddress;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getProblemRemark() {
		return problemRemark;
	}

	public void setProblemRemark(String problemRemark) {
		this.problemRemark = problemRemark;
	}

	public Integer getLendOutId() {
		return lendOutId;
	}

	public void setLendOutId(Integer lendOutId) {
		this.lendOutId = lendOutId;
	}

	public Long getProductDate() {
		return productDate;
	}

	public void setProductDate(Long productDate) {
		this.productDate = productDate;
	}

	public String getProductDateStr() {
		return productDateStr;
	}

	public void setProductDateStr(String productDateStr) {
		this.productDateStr = productDateStr;
	}

	@Override
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("warehouseGoodsOperateLogId = ").append(warehouseGoodsOperateLogId)
		.append(", barcodeId = ").append(barcodeId)
		.append(", companyId = ").append(companyId)
		.append(", operateType = ").append(operateType)
		.append(", relatedId = ").append(relatedId)
		.append(", barcodeFactory = ").append(barcodeFactory)
		.append(", num = ").append(num)
		.append(", warehouseId = ").append(warehouseId)
		.append(", storageRoomId = ").append(storageRoomId)
		.append(", storageAreaId = ").append(storageAreaId)
		.append(", storageLocationId").append(storageLocationId)
		.append(", storageRackId = ").append(storageRackId)
		.append(", batchNumber = ").append(batchNumber)
		.append(", expirationDate = ").append(expirationDate)
		.append(", checkStatus = ").append(checkStatus)
		.append(", checkStatusUser = ").append(checkStatusUser)
		.append(", checkStatusTime = ").append(checkStatusTime)
		.append(", recheckStatus = ").append(recheckStatus)
		.append(", recheckStatusUser = ").append(recheckStatusUser)
		.append(", recheckStatusTime = ").append(recheckStatusTime)
		.append(", comments").append(comments)
		.append(", addTime = ").append(addTime)
		.append(", creator = ").append(creator)
		.append(", modTime = ").append(modTime)
		.append(", isEnable = ").append(isEnable)
		.append(", updater = ").append(updater)
		.append(", warehousePickingDetailId = ").append(warehousePickingDetailId)
		.append(", goodsName = ").append(goodsName)
		.append(", model").append(model)
		.append(", brandName = ").append(brandName)
		.append(", materialCode = ").append(materialCode)
		.append(", unitName = ").append(unitName)
		.append(", sku = ").append(sku)
		.append(", outTime = ").append(outTime)
		.append(", operator = ").append(operator)
		.append(", checkUserName = ").append(checkUserName)
		.append(", recheckUserName = ").append(recheckUserName)
		.append(", warehouseArea = ").append(warehouseArea)
		.append(", buyorderNo").append(buyorderNo)
		.append(", buyorderId = ").append(buyorderId)
		.append(", saleorderNo = ").append(saleorderNo)
		.append(", saleorderId = ").append(saleorderId)
		.append(", afterSalesId = ").append(afterSalesId)
		.append(", afterSalesNo = ").append(afterSalesNo)
		.append(", cnt = ").append(cnt)
		.append(", storeNum = ").append(storeNum)
		.append(", price = ").append(price)
		.append(", pickCnt").append(pickCnt)
		.append(", buytraderName = ").append(buytraderName)
		.append(", saletraderName = ").append(saletraderName)
		.append(", barcode = ").append(barcode)
		.append(", isBarcode = ").append(isBarcode)
		.append(", beginTime = ").append(beginTime)
		.append(", endTime = ").append(endTime)
		.append(", xsprice = ").append(xsprice)
		.append(", cgprice").append(cgprice)
		.append(", pCtn = ").append(pCtn)
		.append(", outGoodsCnt = ").append(outGoodsCnt)
		.append(", expirationDateStatus = ").append(expirationDateStatus)
		.append(", expirationDateStr = ").append(expirationDateStr)
		.append(", traderName = ").append(traderName)
		.append(", zkCnt = ").append(zkCnt)
		.append(", rowNum = ").append(rowNum)
		.append(", addtimes = ").append(addtimes)
		.append(", nums").append(nums)
		.append(", ywType = ").append(ywType)
		.append(", bussinessType = ").append(bussinessType)
		.append(", orderId = ").append(orderId)
		.append(", orderNo = ").append(orderNo)
		.append(", pickdId = ").append(pickdId)
		.append(", zkN = ").append(zkN)
		.append(", flag = ").append(flag)
		.append(", warehouseName").append(warehouseName)
		.append(", storageRoomName = ").append(storageRoomName)
		.append(", storageAreaName = ").append(storageAreaName)
		.append(", storageRackName = ").append(storageRackName)
		.append(", storageLocationName = ").append(storageLocationName)
		.append(", creatorNm = ").append(creatorNm)
		.append(", add_time_str = ").append(add_time_str)
		.append(", expiration_date_str = ").append(expiration_date_str)
		.append(", expirationDateWarnStatus = ").append(expirationDateWarnStatus)
		.append(", manufacturer = ").append(manufacturer)
		.append(", productionLicenseNumber = ").append(productionLicenseNumber)
		.append(", registrationNumber = ").append(registrationNumber)
		.append(", productAddress = ").append(productAddress)
		.append(", recordNumber = ").append(recordNumber)
		.append(", temperaTure =").append(temperaTure)
		.append(", conditionOne =").append(conditionOne)
		.append(", title =").append(title)
		.append(", productCompanyLicence =").append(productCompanyLicence)
		.append(", storageConditionOne =").append(storageConditionOne);
		

		return buf.toString();
	}
}