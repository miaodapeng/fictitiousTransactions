package com.vedeng.goods.model.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsFaq;
import com.vedeng.goods.model.GoodsSysOptionAttribute;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.system.model.Attachment;

public class GoodsVo extends Goods {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String brandName;// 品牌名称

	private String goodsLevelName;// 产品级别名称

	private Integer inventory;// 库存

	private String unitName;// 单位

	private String proUserName;// 产品负责人

	private String saleorderNo;// 销售单号

	private Integer deliveryDirect;// 是否直发 0否 1是
	
	private Integer proOrgtId;//产品部门
	
	private Integer proUserId;//产品负责人
	
	private Integer userId;//申请人id
	
	private Integer proBuySum;//每个待采购产品的总计

	private List<SaleorderVo> saleorderVoList = new ArrayList<>();
	
	private List<SaleorderGoodsVo> sgvList = new ArrayList<>();;//加入采购订单页面的产品列表
	
	private Integer goodsStock;//库存数量
	
	private Integer canUseGoodsStock;//可用库存数量
	
	private Integer ztGoodsStock;//在途数量
    
    private Long estimateArrivalTime;//预计到货时间

    private String buyorderNo;//采购单号
	
	private Integer companyId;
	
    private List<BuyorderVo> bvList = new ArrayList<>();//在途商品
    
    private Integer maybeSaleNum30;//预计30天备货量
    
    private Integer safeNum;//安全库存
    
    private BigDecimal purchasePrice;//采购价
    
    private BigDecimal occupyAmount; // 在途资金占用
    
	private double averageArrivalTime;//平均到货时间
	
	private BigDecimal occupyStockAmount;//库存资金占用
	
	private List<Integer> goodsIds;//产品ID集合
	
	private Integer needNum;//需求数量
	
	private String owner;//
	
	private Integer arrivalNum15;//主渠道未来到货量（15天)
	
	private Integer arrivalNum30;//主渠道未来到货量（30天)
	
	private Integer arrivalNum45;//主渠道未来到货量（45天)
	
	private Integer arrivalNum60;//主渠道未来到货量（60天)
	
	private Integer arrivalNumLong;//主渠道未来到货量（更长)
	
	private List<Integer> categoryIdList;//
	
	private Integer searchUserId;//待采购当前查询人的主键id,此字段只用于待采购的查询且没有查询意义，只是作为dbcenter数据缓存map的key值，查询完成就删除
	
	private Integer currentCount;//当前是第几次查询，默认从2开始
	
	private Integer orderType;//销售单类型1-非备货单；2-备货单
	
	private Integer isValid;
	
	//销售聚合页----开始
	private String domain;//产品的
	
	private String uri;//产品的
	
	private String sellingWords;//销售话术
	
	private String advantage;//产品优势
	
	private String assistantDomain;//选型辅助
	
	private String assistantName;//选型辅助
	
	private String assistantUri;//选型辅助
	
	private String competitiveAnalysis;//竞品分析
	
	private String warrantyPeriod;//质保年限
	
	private Integer isRefund;//是否允许退货 0 否 1是
	
	private String delivery;//现货交货期
	
	private String transportWeight;//运输重量
	
	private Integer isHvaeFreight;//是否包含运费 0否 1是
	
	private List<GoodsFaq> goodsFaqs;//常见问题
	
	private List<Attachment> brochureList;//宣传彩页
	
	private List<GoodsSysOptionAttribute> goodsAftersList;//售后内容

	private Integer occupyNum;//占用数量

	private Integer actionLockCount;//活动锁定库存
	//2019-12-20
	private List<Integer> saleOrderGoodsIdList;//销售产品

	public List<Integer> getSaleOrderGoodsIdList() {
		return saleOrderGoodsIdList;
	}

	public void setSaleOrderGoodsIdList(List<Integer> saleOrderGoodsIdList) {
		this.saleOrderGoodsIdList = saleOrderGoodsIdList;
	}



	
	/************结束****************/
	
	private String que;//问题
	
	private String ans;//答案
	
	private String [] ques;
	
	private String [] anss;

	public Integer getOccupyNum() {
		return occupyNum;
	}

	public void setOccupyNum(Integer occupyNum) {
		this.occupyNum = occupyNum;
	}

	public String getQue() {
		return que;
	}

	public void setQue(String que) {
		this.que = que;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public String[] getQues() {
		return ques;
	}

	public void setQues(String[] ques) {
		this.ques = ques;
	}

	public String[] getAnss() {
		return anss;
	}

	public void setAnss(String[] anss) {
		this.anss = anss;
	}

	public String getDomain() {
		return domain;
	}

	public List<Attachment> getBrochureList() {
		return brochureList;
	}

	public void setBrochureList(List<Attachment> brochureList) {
		this.brochureList = brochureList;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSellingWords() {
		return sellingWords;
	}

	public void setSellingWords(String sellingWords) {
		this.sellingWords = sellingWords;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getAssistantDomain() {
		return assistantDomain;
	}

	public void setAssistantDomain(String assistantDomain) {
		this.assistantDomain = assistantDomain;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public String getAssistantUri() {
		return assistantUri;
	}

	public void setAssistantUri(String assistantUri) {
		this.assistantUri = assistantUri;
	}

	public String getCompetitiveAnalysis() {
		return competitiveAnalysis;
	}

	public void setCompetitiveAnalysis(String competitiveAnalysis) {
		this.competitiveAnalysis = competitiveAnalysis;
	}

	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getTransportWeight() {
		return transportWeight;
	}

	public void setTransportWeight(String transportWeight) {
		this.transportWeight = transportWeight;
	}

	public Integer getIsHvaeFreight() {
		return isHvaeFreight;
	}

	public void setIsHvaeFreight(Integer isHvaeFreight) {
		this.isHvaeFreight = isHvaeFreight;
	}

	public List<GoodsFaq> getGoodsFaqs() {
		return goodsFaqs;
	}

	public void setGoodsFaqs(List<GoodsFaq> goodsFaqs) {
		this.goodsFaqs = goodsFaqs;
	}

	public List<GoodsSysOptionAttribute> getGoodsAftersList() {
		return goodsAftersList;
	}

	public void setGoodsAftersList(List<GoodsSysOptionAttribute> goodsAftersList) {
		this.goodsAftersList = goodsAftersList;
	}

	public List<BuyorderVo> getBvList() {
		return bvList;
	}

	public void setBvList(List<BuyorderVo> bvList) {
		this.bvList = bvList;
	}
	
	public Long getEstimateArrivalTime() {
		return estimateArrivalTime;
	}

	public void setEstimateArrivalTime(Long estimateArrivalTime) {
		this.estimateArrivalTime = estimateArrivalTime;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public Integer getZtGoodsStock() {
		return ztGoodsStock;
	}

	public void setZtGoodsStock(Integer ztGoodsStock) {
		this.ztGoodsStock = ztGoodsStock;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getGoodsLevelName() {
		return goodsLevelName;
	}

	public void setGoodsLevelName(String goodsLevelName) {
		this.goodsLevelName = goodsLevelName;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getProUserName() {
		return proUserName;
	}

	public void setProUserName(String proUserName) {
		this.proUserName = proUserName;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public List<SaleorderVo> getSaleorderVoList() {
		return saleorderVoList;
	}

	public void setSaleorderVoList(List<SaleorderVo> saleorderVoList) {
		this.saleorderVoList = saleorderVoList;
	}

	public Integer getProOrgtId() {
		return proOrgtId;
	}

	public void setProOrgtId(Integer proOrgtId) {
		this.proOrgtId = proOrgtId;
	}

	public Integer getProUserId() {
		return proUserId;
	}

	public void setProUserId(Integer proUserId) {
		this.proUserId = proUserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProBuySum() {
		return proBuySum;
	}

	public void setProBuySum(Integer proBuySum) {
		this.proBuySum = proBuySum;
	}

	public List<SaleorderGoodsVo> getSgvList() {
		return sgvList;
	}

	public void setSgvList(List<SaleorderGoodsVo> sgvList) {
		this.sgvList = sgvList;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public Integer getCanUseGoodsStock() {
		return canUseGoodsStock;
	}

	public void setCanUseGoodsStock(Integer canUseGoodsStock) {
		this.canUseGoodsStock = canUseGoodsStock;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getMaybeSaleNum30() {
		return maybeSaleNum30;
	}

	public void setMaybeSaleNum30(Integer maybeSaleNum30) {
		this.maybeSaleNum30 = maybeSaleNum30;
	}

	public Integer getSafeNum() {
		return safeNum;
	}

	public void setSafeNum(Integer safeNum) {
		this.safeNum = safeNum;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getOccupyAmount() {
		return occupyAmount;
	}

	public void setOccupyAmount(BigDecimal occupyAmount) {
		this.occupyAmount = occupyAmount;
	}

	public double getAverageArrivalTime() {
		return averageArrivalTime;
	}

	public void setAverageArrivalTime(double averageArrivalTime) {
		this.averageArrivalTime = averageArrivalTime;
	}

	public BigDecimal getOccupyStockAmount() {
		return occupyStockAmount;
	}

	public void setOccupyStockAmount(BigDecimal occupyStockAmount) {
		this.occupyStockAmount = occupyStockAmount;
	}

	public List<Integer> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<Integer> goodsIds) {
		this.goodsIds = goodsIds;
	}

	public Integer getNeedNum() {
		return needNum;
	}

	public void setNeedNum(Integer needNum) {
		this.needNum = needNum;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getArrivalNum15() {
		return arrivalNum15;
	}

	public void setArrivalNum15(Integer arrivalNum15) {
		this.arrivalNum15 = arrivalNum15;
	}

	public Integer getArrivalNum30() {
		return arrivalNum30;
	}

	public void setArrivalNum30(Integer arrivalNum30) {
		this.arrivalNum30 = arrivalNum30;
	}

	public Integer getArrivalNum45() {
		return arrivalNum45;
	}

	public void setArrivalNum45(Integer arrivalNum45) {
		this.arrivalNum45 = arrivalNum45;
	}

	public Integer getArrivalNum60() {
		return arrivalNum60;
	}

	public void setArrivalNum60(Integer arrivalNum60) {
		this.arrivalNum60 = arrivalNum60;
	}

	public Integer getArrivalNumLong() {
		return arrivalNumLong;
	}

	public void setArrivalNumLong(Integer arrivalNumLong) {
		this.arrivalNumLong = arrivalNumLong;
	}

	public List<Integer> getCategoryIdList() {
		return categoryIdList;
	}

	public void setCategoryIdList(List<Integer> categoryIdList) {
		this.categoryIdList = categoryIdList;
	}

	public Integer getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(Integer searchUserId) {
		this.searchUserId = searchUserId;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getActionLockCount() {
		return actionLockCount;
	}

	public void setActionLockCount(Integer actionLockCount) {
		this.actionLockCount = actionLockCount;
	}
}
