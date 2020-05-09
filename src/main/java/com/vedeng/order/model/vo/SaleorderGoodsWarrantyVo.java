package com.vedeng.order.model.vo;

import java.util.List;

import com.vedeng.order.model.SaleorderGoodsWarranty;

public class SaleorderGoodsWarrantyVo extends SaleorderGoodsWarranty {
	private String sku;// 订货号

	private String goodsName;// 产品名称

	private String brandName;// 品牌

	private String model;// 型号

	private String materialCode;// 物料编码

	private String barcode;// 贝登条码

	private String barcodeFactory;// 厂商条码

	private Integer num;// 出库数量

	private String saleorderNo;

	private Integer salesAreaId;

	private String takeTraderName;

	private Integer customerNature;

	private String traderName;

	private String uri;// 附件

	private String domain;// 域名

	private String fileName;// 附件名称

	private String area;// 地区

	private Integer saleorderId;// 销售订单ID

	private Long checkTimeStart;//

	private Long checkTimeEnd;//

	private Long addTimeStart;//

	private Long addTimeEnd;//

	private String checkTimeStartStr;

	private String checkTimeEndStr;

	private String addTimeStartStr;

	private String addTimeEndStr;
	
	private Integer companyId;
	
	private Integer goodsId;
	
	private String addTimeStr;
	
	private List<SaleorderGoodsWarranty> warranties;

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getSalesAreaId() {
		return salesAreaId;
	}

	public void setSalesAreaId(Integer salesAreaId) {
		this.salesAreaId = salesAreaId;
	}

	public String getTakeTraderName() {
		return takeTraderName;
	}

	public void setTakeTraderName(String takeTraderName) {
		this.takeTraderName = takeTraderName==null ? null :takeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeFactory() {
		return barcodeFactory;
	}

	public void setBarcodeFactory(String barcodeFactory) {
		this.barcodeFactory = barcodeFactory;
	}

	private String createName;// 创建人

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Long getCheckTimeStart() {
		return checkTimeStart;
	}

	public void setCheckTimeStart(Long checkTimeStart) {
		this.checkTimeStart = checkTimeStart;
	}

	public Long getCheckTimeEnd() {
		return checkTimeEnd;
	}

	public void setCheckTimeEnd(Long checkTimeEnd) {
		this.checkTimeEnd = checkTimeEnd;
	}

	public Long getAddTimeStart() {
		return addTimeStart;
	}

	public void setAddTimeStart(Long addTimeStart) {
		this.addTimeStart = addTimeStart;
	}

	public Long getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(Long addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public String getCheckTimeStartStr() {
		return checkTimeStartStr;
	}

	public void setCheckTimeStartStr(String checkTimeStartStr) {
		this.checkTimeStartStr = checkTimeStartStr;
	}

	public String getCheckTimeEndStr() {
		return checkTimeEndStr;
	}

	public void setCheckTimeEndStr(String checkTimeEndStr) {
		this.checkTimeEndStr = checkTimeEndStr;
	}

	public String getAddTimeStartStr() {
		return addTimeStartStr;
	}

	public void setAddTimeStartStr(String addTimeStartStr) {
		this.addTimeStartStr = addTimeStartStr;
	}

	public String getAddTimeEndStr() {
		return addTimeEndStr;
	}

	public void setAddTimeEndStr(String addTimeEndStr) {
		this.addTimeEndStr = addTimeEndStr;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<SaleorderGoodsWarranty> getWarranties() {
		return warranties;
	}

	public void setWarranties(List<SaleorderGoodsWarranty> warranties) {
		this.warranties = warranties;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}
}
