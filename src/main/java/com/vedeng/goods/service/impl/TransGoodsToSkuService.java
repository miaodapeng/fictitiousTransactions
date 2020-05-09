package com.vedeng.goods.service.impl;

import com.vedeng.common.constant.goods.SpuLevelEnum;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.*;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.system.model.SysOptionDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 新老商品转换
 */
@Component
public class TransGoodsToSkuService extends BaseServiceimpl {

    @Autowired
    GoodsGenerateMapper goodsGenerateMapper;
    @Autowired
    GoodsGysOptionAttributegenerateMapper goodsGysOptionAttributegenerateMapper;
    @Autowired
    GoodsExtendGenerateMapper extendGenerateMapper;
    @Autowired
    GoodsAttachmentGenerateMapper attachmentGenerateMapper;
    /*@Autowired
    BaseService baseService;*/
    @Autowired
    CoreSpuGenerateExtendMapper coreSpuGenerateExtendMapper;
    @Autowired
    CoreSkuGenerateMapper skuGenerateMapper;
    @Autowired
    CoreSpuGenerateMapper spuGenerateMapper;



    public CoreSpuGenerate goodsToSpu(GoodsGenerateWithBLOBs goods) {
        CoreSpuGenerate spu = new CoreSpuGenerate();
        spu.setStatus(1);
        spu.setSpuId(goods.getGoodsId());
        spu.setBrandId(goods.getBrandId());
        spu.setCategoryId(goods.getCategoryId()==null?0:goods.getCategoryId());
        spu.setSpuNo(goods.getSku());
        spu.setSpuName(goods.getGoodsName());
        spu.setShowName(goods.getGoodsName());
        spu.setSpuType(goods.getGoodsType());
        spu.setCheckStatus(0);
        spu.setWikiHref(goods.getHref());
        //335 hx 336 ls 337 qita
        if (335 == goods.getGoodsLevel()) {
            spu.setSpuLevel(SpuLevelEnum.CORE.spuLevel());
        }
        if (336 == goods.getGoodsLevel()) {
            spu.setSpuLevel(SpuLevelEnum.TEMP.spuLevel());
        }
        if (337 == goods.getGoodsLevel()) {
            spu.setSpuLevel(SpuLevelEnum.OTHER.spuLevel());
        }
        spu.setModTime(new Date());
        spu.setAddTime(new Date(goods.getAddTime()));
        spu.setUpdater(goods.getUpdater());
        spu.setCreator(goods.getCreator());





        return spu;
    }

    public CoreSkuGenerate goodsToSku(GoodsGenerateWithBLOBs goods, List<GoodsGysOptionAttributegenerate> opt1, List<GoodsGysOptionAttributegenerate> opt2) {
        CoreSkuGenerate sku = new CoreSkuGenerate();
        sku.setSource(goods.getSource());
        sku.setStatus(1);
        sku.setSpuId(goods.getGoodsId());
        sku.setSkuId(goods.getGoodsId());
        sku.setSkuNo(goods.getSku());
        sku.setCheckStatus(0);
        sku.setSkuName(goods.getGoodsName());
        sku.setShowName(goods.getGoodsName());
        sku.setModel(goods.getModel());
        sku.setMaterialCode(goods.getMaterialCode());
        sku.setBaseUnitId(goods.getUnitId());
        sku.setChangeNum(NumberUtils.toLong(goods.getChangeNum() + ""));
        sku.setUnitId(goods.getUnitId());
        sku.setGrossWeight(goods.getGrossWeight());
        sku.setNetWeight(goods.getNetWeight())
        ;
        sku.setGoodsLength(goods.getGoodsLength());
        sku.setGoodsHeight(goods.getGoodsHeight());
        sku.setGoodsWidth(goods.getGoodsWidth());

        sku.setPackageHeight(goods.getPackageHeight());
        sku.setPackageLength(goods.getPackageLength());
        sku.setPackageWidth(goods.getPackageWidth());


        sku.setPackingList(goods.getPackingList());

        sku.setTechnicalParameter(StringUtils.replaceAll(StringUtils.replaceAll(goods.getTechnicalParameter(),"；",";"),"：",":"));
        sku.setPerformanceParameter(StringUtils.replaceAll(StringUtils.replaceAll(goods.getPerformanceParameter(),"；",";"),"：",":")  );
        sku.setSpecParameter(StringUtils.replaceAll(StringUtils.replaceAll(goods.getSpecParameter(),"；",";"),"：",":" ) );


        sku.setModTime(new Date(goods.getModTime()));
        sku.setAddTime(new Date(goods.getAddTime()));
        sku.setUpdater(goods.getUpdater());
        sku.setCreator(goods.getCreator());

        sku.setSupplyModel(goods.getSupplyModel());
        sku.setSpec(goods.getSpec());

        sku.setStorageConditionOne(goods.getStorageRequirements());
        sku.setWikiHref(goods.getHref());
        sku.setTaxCategoryNo(goods.getTaxCategoryNo());

//        售后内容  -654
        if (CollectionUtils.isNotEmpty(opt1)) {
            for (GoodsGysOptionAttributegenerate opt : opt1) {
                int newid = 0;
                if (655 == opt.getAttributeId()) {
                    newid = 1;
                }
//                if (656 == opt.getAttributeId()) {
//                    newid = 5;
//                }
                if (657 == opt.getAttributeId()) {
                    newid = 2;
                }
                sku.setAfterSaleContent(sku.getAfterSaleContent() + newid + ",");
            }
        }
        GoodsExtendGenerateExample extendGenerateExample = new GoodsExtendGenerateExample();
        extendGenerateExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());

        List<GoodsExtendGenerate> extendGenerateList = extendGenerateMapper.selectByExampleWithBLOBs(extendGenerateExample);
        GoodsExtendGenerate extendGenerate = null;
        if (CollectionUtils.isNotEmpty(extendGenerateList)) {
            extendGenerate = extendGenerateList.get(0);
        }
        if (extendGenerate != null) {

//                质保年限
            sku.setQaYears(extendGenerate.getWarrantyPeriod());
//        质保期限规则
            sku.setQaRule(extendGenerate.getWarrantyPeriodRule());
//                质保外维修价格
            sku.setQaOutPrice(extendGenerate.getWarrantyRepairFee());
//        响应时间
            sku.setQaResponseTime(NumberUtils.toLong(extendGenerate.getResponseTime()));
//                有无备用机
            sku.setHasBackupMachine(extendGenerate.getHaveStandbyMachine() + "");
//        供应商延保价格
            try{
            sku.setSupplierExtendGuaranteePrice(NumberUtils.createBigDecimal(extendGenerate.getExtendedWarrantyFee()));}catch(Exception e){}
//                核心零部件价格 TODO

//        退货条件
            sku.setReturnGoodsConditions(extendGenerate.getIsRefund());
//        运费说明
            sku.setFreightIntroductions(extendGenerate.getFreightDescription());
//        换货条件
            sku.setExchangeGoodsConditions(extendGenerate.getExchangeConditions());
//                换货方式
            sku.setExchangeGoodsMethod(extendGenerate.getExchangeMode());

        }
//        商品备注
        sku.setGoodsComments(  (StringUtils.isBlank(goods.getPurchaseRemind()) ?"采购提醒：":goods.getPurchaseRemind() )+( StringUtils.isBlank(goods.getTos())?" 服务条款：":goods.getPurchaseRemind() ) );

        return sku;
    }

    public GoodsGenerateWithBLOBs sku2Goods(CoreSkuGenerate skuGenerate, CoreSpuGenerate spuGenerate) {
        GoodsGenerateWithBLOBs goodsGenerate = new GoodsGenerateWithBLOBs();
        int goodsLevel = 336;
        if (SpuLevelEnum.CORE.spuLevel() == spuGenerate.getSpuLevel()) {
            goodsLevel = 335;
        }
        if (SpuLevelEnum.TEMP.spuLevel() == spuGenerate.getSpuLevel()) {
            goodsLevel = 336;
        }
        if (SpuLevelEnum.OTHER.spuLevel() == spuGenerate.getSpuLevel()) {
            goodsLevel = 337;
        }
        goodsGenerate.setGoodsLevel(goodsLevel);
        goodsGenerate.setIsDiscard(0);
        goodsGenerate.setCompanyId(1);
        goodsGenerate.setParentId(0);
        goodsGenerate.setToSkuFlag(1);
        goodsGenerate.setGoodsId(skuGenerate.getSkuId());
        goodsGenerate.setBrandId(spuGenerate.getBrandId());
        goodsGenerate.setCategoryId(spuGenerate.getCategoryId());
        goodsGenerate.setSku(skuGenerate.getSkuNo());
        goodsGenerate.setGoodsName(spuGenerate.getShowName());
        goodsGenerate.setAliasName(spuGenerate.getSpuName());
        goodsGenerate.setGoodsType(spuGenerate.getSpuType());


        //
        // goodsGenerate.setStatus(1);
        goodsGenerate.setGoodsId(skuGenerate.getSpuId());
        goodsGenerate.setGoodsId(skuGenerate.getSkuId());
        goodsGenerate.setSku(skuGenerate.getSkuNo());
        //  goodsGenerate.setCheckStatus(GoodsCheckStatusEnum.PRE.getStatus());
        goodsGenerate.setGoodsName(skuGenerate.getSkuName());
        goodsGenerate.setModel(skuGenerate.getModel());
        goodsGenerate.setMaterialCode(skuGenerate.getMaterialCode());
        goodsGenerate.setBaseUnitId(skuGenerate.getBaseUnitId());
        goodsGenerate.setChangeNum(NumberUtils.toInt(skuGenerate.getChangeNum() + ""));
        goodsGenerate.setUnitId(skuGenerate.getBaseUnitId());
        goodsGenerate.setGrossWeight(skuGenerate.getGrossWeight());
        goodsGenerate.setNetWeight(skuGenerate.getNetWeight())
        ;
        goodsGenerate.setGoodsLength(skuGenerate.getGoodsLength());
        goodsGenerate.setGoodsHeight(skuGenerate.getGoodsHeight());
        goodsGenerate.setGoodsWidth(skuGenerate.getGoodsWidth());

        goodsGenerate.setPackageHeight(skuGenerate.getPackageHeight());
        goodsGenerate.setPackageLength(skuGenerate.getPackageLength());
        goodsGenerate.setPackageWidth(skuGenerate.getPackageWidth());

        //goodsGenerate.setGoodsComments("采购提醒："+skuGenerate.getPurchaseRemind()+",服务条款："+skuGenerate.getTos());

        goodsGenerate.setPackingList(skuGenerate.getPackingList());
        String technicalParameter = skuGenerate.getTechnicalParameter();
        if(StringUtils.isNotBlank(technicalParameter)) {
            technicalParameter.replaceAll(":", "：");
            technicalParameter.replaceAll(";", "；");
        }
        goodsGenerate.setTechnicalParameter(technicalParameter);


        String PerformanceParameter = skuGenerate.getPerformanceParameter();
        if(StringUtils.isNotBlank(PerformanceParameter)) {
            PerformanceParameter.replaceAll(":", "：");
            PerformanceParameter.replaceAll(";", "；");
        }
        goodsGenerate.setTechnicalParameter(PerformanceParameter);

        String SpecParameter = skuGenerate.getSpecParameter();
        if(StringUtils.isNotBlank(SpecParameter)) {
            SpecParameter.replaceAll(":", "：");
            SpecParameter.replaceAll(";", "；");
        }
        goodsGenerate.setSpecParameter(SpecParameter);

        goodsGenerate.setModTime(skuGenerate.getModTime() == null ? System.currentTimeMillis() : skuGenerate.getModTime().getTime());
        goodsGenerate.setAddTime(skuGenerate.getAddTime() == null ? System.currentTimeMillis() : skuGenerate.getAddTime().getTime());
        goodsGenerate.setUpdater(skuGenerate.getUpdater());
        goodsGenerate.setCreator(skuGenerate.getCreator());

        goodsGenerate.setSupplyModel(skuGenerate.getSupplyModel());
        goodsGenerate.setSpec(skuGenerate.getSpec());

        goodsGenerate.setStorageRequirements(skuGenerate.getStorageConditionOne());
        goodsGenerate.setHref(skuGenerate.getWikiHref());



     //   GoodsExtendGenerate extendGenerate = extendGenerateMapper.selectByPrimaryKey(skuGenerate.getSkuId());

        GoodsExtendGenerateExample extendGenerateExample = new GoodsExtendGenerateExample();
        extendGenerateExample.createCriteria().andGoodsIdEqualTo(skuGenerate.getSkuId());

        List<GoodsExtendGenerate> extendGenerateList = extendGenerateMapper.selectByExampleWithBLOBs(extendGenerateExample);
        GoodsExtendGenerate extendGenerate = null;
        if (CollectionUtils.isNotEmpty(extendGenerateList)) {
            extendGenerate = extendGenerateList.get(0);
        }
        if (extendGenerate == null) {
            extendGenerate = new GoodsExtendGenerate();
        }
        GoodsGysOptionAttributegenerateExample exampleaftersale = new GoodsGysOptionAttributegenerateExample();
        //售后内容
        String aftersale[] = StringUtils.split(skuGenerate.getAfterSaleContent(), ",");
        //
        GoodsGysOptionAttributegenerateExample example = new GoodsGysOptionAttributegenerateExample();
        example.createCriteria().andGoodsIdEqualTo(skuGenerate.getSkuId());
        goodsGysOptionAttributegenerateMapper.deleteByExample(example);
        if (ArrayUtils.isNotEmpty(aftersale)) {
            for (String id : aftersale) {
                int newid = 0;
                if ("1".equals(id)) {
                    newid = 655;
                } else if ("2".equals(id)) {
                    newid = 657;
                }
                if(newid>0){


                GoodsGysOptionAttributegenerate goodsGysOptionAttributegenerate = new GoodsGysOptionAttributegenerate();
                goodsGysOptionAttributegenerate.setGoodsId(skuGenerate.getSkuId());
                goodsGysOptionAttributegenerate.setAttributeType(654);
                goodsGysOptionAttributegenerate.setAttributeId(newid);
                goodsGysOptionAttributegenerateMapper.insert(goodsGysOptionAttributegenerate);  }
            }
        }
        //                质保年限
        extendGenerate.setWarrantyPeriod(skuGenerate.getQaYears());
//        质保期限规则
        extendGenerate.setWarrantyPeriodRule(skuGenerate.getQaRule());
//                质保外维修价格
        extendGenerate.setWarrantyRepairFee(skuGenerate.getQaOutPrice());
//        响应时间
        if(skuGenerate.getQaResponseTime()!=null){
            extendGenerate.setResponseTime(skuGenerate.getQaResponseTime() + "");
        }
//                有无备用机
        extendGenerate.setHaveStandbyMachine(NumberUtils.toInt(skuGenerate.getHasBackupMachine()));
//        供应商延保价格
        if(skuGenerate.getSupplierExtendGuaranteePrice()!=null){
        extendGenerate.setExtendedWarrantyFee(String.valueOf(skuGenerate.getSupplierExtendGuaranteePrice()));}
//                核心零部件价格 TODO

//        退货条件
        extendGenerate.setIsRefund(skuGenerate.getReturnGoodsConditions());
//        运费说明
        extendGenerate.setFreightDescription(skuGenerate.getFreightIntroductions());
//        换货条件
        extendGenerate.setExchangeConditions(skuGenerate.getExchangeGoodsConditions());
//                换货方式
        extendGenerate.setExchangeMode(skuGenerate.getExchangeGoodsMethod());
        extendGenerate.setGoodsId(skuGenerate.getSkuId());
//        商品备注
        //extendGenerate.setGoodsComments("采购提醒："+goods.getPurchaseRemind()+",服务条款："+goods.getTos() );
        if (extendGenerate.getGoodsExtendId() != null && extendGenerate.getGoodsExtendId() > 0) {
            extendGenerateMapper.updateByPrimaryKeySelective(extendGenerate);
        } else {
            extendGenerateMapper.insert(extendGenerate);
        }

        return goodsGenerate;

    }

    private Integer getOptionIdByOptionType(String optionType) {
        SysOptionDefinition option = getFirstSysOptionDefinitionList(optionType);
        if (option != null) {
            return option.getSysOptionDefinitionId();
        }
        return -1;
    }


}
