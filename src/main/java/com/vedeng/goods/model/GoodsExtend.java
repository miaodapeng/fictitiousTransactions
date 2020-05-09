package com.vedeng.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.vedeng.system.model.Attachment;


public class GoodsExtend implements Serializable{
    private Integer goodsExtendId;

    private Integer goodsId;

    private String customerNames;

    private String sellingWords;

    private String marketStrategy;

    private String promotionPolicy;

    private String warrantyPeriod;

    private String warrantyPeriodRule;

    private BigDecimal warrantyRepairFee = new BigDecimal(0);

    private String responseTime;

    private Integer haveStandbyMachine;

    private String conditions;

    private String extendedWarrantyFee;

    private Integer isRefund=-1;

    private String exchangeConditions;

    private String exchangeMode;

    private String freightDescription;

    private String delivery;

    private String futuresDelivery;

    private String transportRequirements;

    private String transportWeight;

    private Integer isHvaeFreight;

    private Integer transportationCompletionStandard;

    private String acceptanceNotice;

    private String packingNumber;

    private String packingQuantity;
    
    private String advantage;//销售聚合页-产品优势-2018-6-19

    private String competitiveAnalysis;
    
    private String sku;
    
    private String propagandistVideos;//宣传视频字符串
    
    private String aftersaleContent;//售后内容字符串
    
    private List<Attachment> brochureList = new ArrayList<Attachment>();//宣传彩页
    
    private List<Attachment> salesEmpowermentList= new ArrayList<Attachment>();//销售授权书
    
    private List<Attachment> propagandistVideoList= new ArrayList<Attachment>();//宣传视频
    
    private List<Attachment> trainingMaterialsList= new ArrayList<Attachment>();//培训资料
    
    private List<Attachment> sparePartsPriceList= new ArrayList<Attachment>();//核心零部件价格
    
    private List<Attachment> otherQualificationsList= new ArrayList<Attachment>();//其他资质
    
    private List<Attachment> commodityInstructionsList= new ArrayList<Attachment>();//商品说明书
    
    private List<Attachment> technicalParameterList= new ArrayList<Attachment>();//技术参数
    
    private List<Attachment> standardDataList= new ArrayList<Attachment>();//陪标资料
    
    private Attachment assistantAttachment;//销售聚合页-选型辅助-2018-6-19
    
    private String [] attachName1;//宣传彩页附件名称，逗号隔开
	
	private String [] attachUri1;//宣传彩页附件uri,逗号隔开
	
    private String [] attachName2;//销售授权书附件名称，逗号隔开
	
	private String [] attachUri2;//销售授权书附件uri,逗号隔开
	
    private String [] attachName3;//培训资料附件名称，逗号隔开
	
	private String [] attachUri3;//培训资料附件uri,逗号隔开
	
	private String [] attachName4;//附件名称，逗号隔开
		
    private String [] attachUri4;//附件uri,逗号隔开
    
    private String [] attachName5;//其他资质，逗号隔开
	
   	private String [] attachUri5;//其他资质uri,逗号隔开
   	
   	// modify by Franlin at 2018-08-15 for[4518 上传的时候明明选的是技术参数，提交后就在商品说明书 正式环境 V255599 ] begin 
    private String [] attachName6;//技术参数名称，逗号隔开
   	
   	private String [] attachUri6;//技术参数附件uri,逗号隔开
   	
    private String [] attachName7;//商品说明书名称，逗号隔开
   	
   	private String [] attachUri7;//商品说明书附件uri,逗号隔开
   	// modify by Franlin at 2018-08-15 for[4518 上传的时候明明选的是技术参数，提交后就在商品说明书 正式环境 V255599 ] end 
   	
   	private String [] attachName8;//陪标资料名称，逗号隔开
   		
    private String [] attachUri8;//陪标资料uri,逗号隔开
    
   	private String [] attachName9;//选型辅助名称，逗号隔开
		
    private String [] attachUri9;//选型辅助uri,逗号隔开
	
	private String domain;//域名
	
	List<GoodsSysOptionAttribute> sysList = new ArrayList<GoodsSysOptionAttribute>();//售后内容
	
    List<GoodsSysOptionAttribute> sysStandardList = new ArrayList<GoodsSysOptionAttribute>();//运输完成标准
    
    List<GoodsSysOptionAttribute> sysConditionList = new ArrayList<GoodsSysOptionAttribute>();//特殊运输条件
    
    private Integer freightSettlementMethod;//运费结算方式
    
    private String tsCondition;//特殊运输条件字符串
    
    private Integer isLogistics=0;//是否是修改商品物流运输信息，默认否
    
    private Integer saleorderId;//销售订单ID
    
    private Integer quoteorderId;//报价订单ID
       
  	public String[] getAttachName9() {
		return attachName9;
	}

	public void setAttachName9(String[] attachName9) {
		this.attachName9 = attachName9;
	}

	public String[] getAttachUri9() {
		return attachUri9;
	}

	public void setAttachUri9(String[] attachUri9) {
		this.attachUri9 = attachUri9;
	}

	public Attachment getAssistantAttachment() {
		return assistantAttachment;
	}

	public void setAssistantAttachment(Attachment assistantAttachment) {
		this.assistantAttachment = assistantAttachment;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public Integer getIsLogistics() {
		return isLogistics;
	}

	public void setIsLogistics(Integer isLogistics) {
		this.isLogistics = isLogistics;
	}

	public List<Attachment> getOtherQualificationsList() {
		return otherQualificationsList;
	}

	public void setOtherQualificationsList(List<Attachment> otherQualificationsList) {
		this.otherQualificationsList = otherQualificationsList;
	}

	public List<Attachment> getCommodityInstructionsList() {
		return commodityInstructionsList;
	}

	public void setCommodityInstructionsList(List<Attachment> commodityInstructionsList) {
		this.commodityInstructionsList = commodityInstructionsList;
	}

	public List<Attachment> getTechnicalParameterList() {
		return technicalParameterList;
	}

	public void setTechnicalParameterList(List<Attachment> technicalParameterList) {
		this.technicalParameterList = technicalParameterList;
	}

	public List<Attachment> getStandardDataList() {
		return standardDataList;
	}

	public void setStandardDataList(List<Attachment> standardDataList) {
		this.standardDataList = standardDataList;
	}

	public String[] getAttachName5() {
		return attachName5;
	}

	public void setAttachName5(String[] attachName5) {
		this.attachName5 = attachName5;
	}

	public String[] getAttachUri5() {
		return attachUri5;
	}

	public void setAttachUri5(String[] attachUri5) {
		this.attachUri5 = attachUri5;
	}

	public String[] getAttachName6() {
		return attachName6;
	}

	public void setAttachName6(String[] attachName6) {
		this.attachName6 = attachName6;
	}

	public String[] getAttachUri6() {
		return attachUri6;
	}

	public void setAttachUri6(String[] attachUri6) {
		this.attachUri6 = attachUri6;
	}

	public String[] getAttachName7() {
		return attachName7;
	}

	public void setAttachName7(String[] attachName7) {
		this.attachName7 = attachName7;
	}

	public String[] getAttachUri7() {
		return attachUri7;
	}

	public void setAttachUri7(String[] attachUri7) {
		this.attachUri7 = attachUri7;
	}

	public String[] getAttachName8() {
		return attachName8;
	}

	public void setAttachName8(String[] attachName8) {
		this.attachName8 = attachName8;
	}

	public String[] getAttachUri8() {
		return attachUri8;
	}

	public void setAttachUri8(String[] attachUri8) {
		this.attachUri8 = attachUri8;
	}

	public String getTsCondition() {
		return tsCondition;
	}

	public void setTsCondition(String tsCondition) {
		this.tsCondition = tsCondition;
	}
    
	public Integer getFreightSettlementMethod() {
		return freightSettlementMethod;
	}

	public void setFreightSettlementMethod(Integer freightSettlementMethod) {
		this.freightSettlementMethod = freightSettlementMethod;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public List<GoodsSysOptionAttribute> getSysStandardList() {
		return sysStandardList;
	}

	public void setSysStandardList(List<GoodsSysOptionAttribute> sysStandardList) {
		this.sysStandardList = sysStandardList;
	}

	public List<GoodsSysOptionAttribute> getSysConditionList() {
		return sysConditionList;
	}

	public void setSysConditionList(List<GoodsSysOptionAttribute> sysConditionList) {
		this.sysConditionList = sysConditionList;
	}

	public String getAftersaleContent() {
		return aftersaleContent;
	}

	public void setAftersaleContent(String aftersaleContent) {
		this.aftersaleContent = aftersaleContent;
	}

    private String aftersalesContent;//售后内容 文字拼接
    
    private String specialTransportConditions;//特殊运输条件
	    
	public List<GoodsSysOptionAttribute> getSysList() {
		return sysList;
	}

	public void setSysList(List<GoodsSysOptionAttribute> sysList) {
		this.sysList = sysList;
	}
    
	public String[] getAttachName4() {
		return attachName4;
	}

	public void setAttachName4(String[] attachName4) {
		this.attachName4 = attachName4;
	}

	public String[] getAttachUri4() {
		return attachUri4;
	}

	public void setAttachUri4(String[] attachUri4) {
		this.attachUri4 = attachUri4;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String[] getAttachName1() {
		return attachName1;
	}

	public void setAttachName1(String[] attachName1) {
		this.attachName1 = attachName1;
	}

	public String[] getAttachUri1() {
		return attachUri1;
	}

	public void setAttachUri1(String[] attachUri1) {
		this.attachUri1 = attachUri1;
	}

	public String[] getAttachName2() {
		return attachName2;
	}

	public void setAttachName2(String[] attachName2) {
		this.attachName2 = attachName2;
	}

	public String[] getAttachUri2() {
		return attachUri2;
	}

	public void setAttachUri2(String[] attachUri2) {
		this.attachUri2 = attachUri2;
	}

	public String[] getAttachName3() {
		return attachName3;
	}

	public void setAttachName3(String[] attachName3) {
		this.attachName3 = attachName3;
	}

	public String[] getAttachUri3() {
		return attachUri3;
	}

	public void setAttachUri3(String[] attachUri3) {
		this.attachUri3 = attachUri3;
	}

	public String getPropagandistVideos() {
		return propagandistVideos;
	}

	public void setPropagandistVideos(String propagandistVideos) {
		this.propagandistVideos = propagandistVideos;
	}

	public List<Attachment> getBrochureList() {
		return brochureList;
	}

	public void setBrochureList(List<Attachment> brochureList) {
		this.brochureList = brochureList;
	}

	public List<Attachment> getSalesEmpowermentList() {
		return salesEmpowermentList;
	}

	public void setSalesEmpowermentList(List<Attachment> salesEmpowermentList) {
		this.salesEmpowermentList = salesEmpowermentList;
	}

	public List<Attachment> getPropagandistVideoList() {
		return propagandistVideoList;
	}

	public void setPropagandistVideoList(List<Attachment> propagandistVideoList) {
		this.propagandistVideoList = propagandistVideoList;
	}

	public List<Attachment> getTrainingMaterialsList() {
		return trainingMaterialsList;
	}

	public void setTrainingMaterialsList(List<Attachment> trainingMaterialsList) {
		this.trainingMaterialsList = trainingMaterialsList;
	}

	public List<Attachment> getSparePartsPriceList() {
		return sparePartsPriceList;
	}

	public void setSparePartsPriceList(List<Attachment> sparePartsPriceList) {
		this.sparePartsPriceList = sparePartsPriceList;
	}

    public Integer getGoodsExtendId() {
        return goodsExtendId;
    }

    public void setGoodsExtendId(Integer goodsExtendId) {
        this.goodsExtendId = goodsExtendId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getCustomerNames() {
        return customerNames;
    }

    public void setCustomerNames(String customerNames) {
        this.customerNames = customerNames == null ? null : customerNames.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getSellingWords() {
        return sellingWords;
    }

    public void setSellingWords(String sellingWords) {
        this.sellingWords = sellingWords == null ? null : sellingWords.trim();
    }

    public String getMarketStrategy() {
        return marketStrategy;
    }

    public void setMarketStrategy(String marketStrategy) {
        this.marketStrategy = marketStrategy == null ? null : marketStrategy.trim();
    }

    public String getPromotionPolicy() {
        return promotionPolicy;
    }

    public void setPromotionPolicy(String promotionPolicy) {
        this.promotionPolicy = promotionPolicy == null ? null : promotionPolicy.trim();
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod == null ? null : warrantyPeriod.trim();
    }

    public String getWarrantyPeriodRule() {
        return warrantyPeriodRule;
    }

    public void setWarrantyPeriodRule(String warrantyPeriodRule) {
        this.warrantyPeriodRule = warrantyPeriodRule == null ? null : warrantyPeriodRule.trim();
    }

    public BigDecimal getWarrantyRepairFee() {
        return warrantyRepairFee;
    }

    public void setWarrantyRepairFee(BigDecimal warrantyRepairFee) {
        this.warrantyRepairFee = warrantyRepairFee;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime == null ? null : responseTime.trim();
    }

    public Integer getHaveStandbyMachine() {
        return haveStandbyMachine;
    }

    public void setHaveStandbyMachine(Integer haveStandbyMachine) {
        this.haveStandbyMachine = haveStandbyMachine;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions == null ? null : conditions.trim();
    }

    public String getExtendedWarrantyFee() {
        return extendedWarrantyFee;
    }

    public void setExtendedWarrantyFee(String extendedWarrantyFee) {
        this.extendedWarrantyFee = extendedWarrantyFee == null ? null : extendedWarrantyFee.trim();
    }

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public String getExchangeConditions() {
        return exchangeConditions;
    }

    public void setExchangeConditions(String exchangeConditions) {
        this.exchangeConditions = exchangeConditions == null ? null : exchangeConditions.trim();
    }

    public String getExchangeMode() {
        return exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode == null ? null : exchangeMode.trim();
    }

    public String getFreightDescription() {
        return freightDescription;
    }

    public void setFreightDescription(String freightDescription) {
        this.freightDescription = freightDescription == null ? null : freightDescription.trim();
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery == null ? null : delivery.trim();
    }

    public String getFuturesDelivery() {
        return futuresDelivery;
    }

    public void setFuturesDelivery(String futuresDelivery) {
        this.futuresDelivery = futuresDelivery == null ? null : futuresDelivery.trim();
    }

    public String getTransportRequirements() {
        return transportRequirements;
    }

    public void setTransportRequirements(String transportRequirements) {
        this.transportRequirements = transportRequirements == null ? null : transportRequirements.trim();
    }

    public String getTransportWeight() {
        return transportWeight;
    }

    public void setTransportWeight(String transportWeight) {
        this.transportWeight = transportWeight == null ? null : transportWeight.trim();
    }

    public Integer getIsHvaeFreight() {
        return isHvaeFreight;
    }

    public void setIsHvaeFreight(Integer isHvaeFreight) {
        this.isHvaeFreight = isHvaeFreight;
    }

    public Integer getTransportationCompletionStandard() {
        return transportationCompletionStandard;
    }

    public void setTransportationCompletionStandard(Integer transportationCompletionStandard) {
        this.transportationCompletionStandard = transportationCompletionStandard;
    }

    public String getAcceptanceNotice() {
        return acceptanceNotice;
    }

    public void setAcceptanceNotice(String acceptanceNotice) {
        this.acceptanceNotice = acceptanceNotice == null ? null : acceptanceNotice.trim();
    }

    public String getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(String packingNumber) {
        this.packingNumber = packingNumber == null ? null : packingNumber.trim();
    }

    public String getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(String packingQuantity) {
        this.packingQuantity = packingQuantity == null ? null : packingQuantity.trim();
    }

    public String getCompetitiveAnalysis() {
        return competitiveAnalysis;
    }

    public void setCompetitiveAnalysis(String competitiveAnalysis) {
        this.competitiveAnalysis = competitiveAnalysis == null ? null : competitiveAnalysis.trim();
    }

	public String getAftersalesContent() {
		return aftersalesContent;
	}

	public void setAftersalesContent(String aftersalesContent) {
		this.aftersalesContent = aftersalesContent;
	}

	public String getSpecialTransportConditions() {
		return specialTransportConditions;
	}

	public void setSpecialTransportConditions(String specialTransportConditions) {
		this.specialTransportConditions = specialTransportConditions;
	}

	public Integer getSaleorderId() {
	    return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
	    this.saleorderId = saleorderId;
	}

	public Integer getQuoteorderId() {
	    return quoteorderId;
	}

	public void setQuoteorderId(Integer quoteorderId) {
	    this.quoteorderId = quoteorderId;
	}
}