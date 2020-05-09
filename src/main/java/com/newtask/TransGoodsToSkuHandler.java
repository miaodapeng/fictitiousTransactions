package com.newtask;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.firstengage.dao.FirstEngageMapper;
import com.vedeng.firstengage.dao.RegistrationNumberMapper;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.*;
import com.vedeng.goods.service.BaseGoodsService;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.system.dao.AttachmentMapper;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 任务Handler示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
@JobHandler(value = "transGoodsToSkuHandler")
@Component
public class TransGoodsToSkuHandler extends IJobHandler {
    public static Logger logger = LoggerFactory.getLogger(TransGoodsToSkuHandler.class);

    @Autowired
    BaseGoodsService baseGoodsService;
    @Autowired
    GoodsGenerateMapper mapper;
    @Autowired
    GoodsGysOptionAttributegenerateMapper goodsGysOptionAttributegenerateMapper;
    @Autowired
    GoodsExtendGenerateMapper extendGenerateMapper;
    @Autowired
    GoodsAttachmentGenerateMapper attachmentGenerateMapper;
    @Autowired
    BaseService baseService;
    @Autowired
    com.vedeng.goods.dao.CoreSpuGenerateExtendMapper coreSpuGenerateExtendMapper;
    @Autowired
    CoreSkuGenerateMapper skuGenerateMapper;
    @Autowired
    CoreSpuGenerateMapper spuGenerateMapper;
    @Autowired
    VgoodsService vgoodsService;
    @Autowired
    RegistrationNumberMapper registrationNumberMapper;
    @Autowired
    AttachmentMapper attachmentMapper;
    @Autowired
    @Qualifier("firstEngageMapper")
    private FirstEngageMapper firstEngageMapper;
    @Autowired
    com.vedeng.goods.service.impl.TransGoodsToSkuService transGoodsToSkuService;
    @Value("${api_http}")
    protected String api_http;
    @Value("${file_url}")
    protected String domain;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("transGoodsToSkuHandler start.");
        GoodsGenerateExample example = new GoodsGenerateExample();
        if (StringUtils.isBlank(param)) {
            example.createCriteria().andToSkuFlagIsNull().andIsDiscardEqualTo(0);
            PageHelper.startPage(0, 1000);
        } else {
            String[] ids = StringUtils.split(param,",");
            if (ArrayUtils.isNotEmpty(ids)) {
                List<Integer> list = Lists.newArrayList();
                for (String i : ids) {
                    list.add(NumberUtils.toInt(i));
                }
                example.createCriteria().andGoodsIdIn(list);
            }
        }
        List<GoodsGenerateWithBLOBs> goodslist = mapper.selectByExampleWithBLOBs(example);
        for (GoodsGenerateWithBLOBs goods : goodslist) {
            GoodsGysOptionAttributegenerateExample exampleaftersale = new GoodsGysOptionAttributegenerateExample();
            exampleaftersale.createCriteria().andGoodsIdEqualTo(goods.getGoodsId()).andAttributeTypeEqualTo(654);
            List<GoodsGysOptionAttributegenerate> afterSaleList = goodsGysOptionAttributegenerateMapper.selectByExample(exampleaftersale);
            GoodsGysOptionAttributegenerateExample examplespecialTransportConditions = new GoodsGysOptionAttributegenerateExample();
            examplespecialTransportConditions.createCriteria().andGoodsIdEqualTo(goods.getGoodsId()).andAttributeTypeEqualTo(661);
            List<GoodsGysOptionAttributegenerate> specialTransportConditionsList = goodsGysOptionAttributegenerateMapper.selectByExample(examplespecialTransportConditions);
            try {
                CoreSkuGenerate sku = transGoodsToSkuService.goodsToSku(goods, afterSaleList, specialTransportConditionsList);
                CoreSpuGenerate spu = transGoodsToSkuService.goodsToSpu(goods);

                //首营
                Map<String, Object> map = Maps.newHashMap();
                String searchValue = goods.getRegistrationNumber();
                if (StringUtils.isBlank(searchValue)) {
                    searchValue = goods.getRecordNumber();
                }
                Integer registerNumberId = null;

                List<Map<String, Object>> listf = vgoodsService.searchFirstEngageListPage(searchValue, Page.newBuilder(1, 2, ""));
                if (CollectionUtils.isNotEmpty(listf)) {
                    spu.setFirstEngageId(NumberUtils.toInt(listf.get(0).get("firstEngageId") + ""));
                    //注册证图片
                    FirstEngage firstEngage = firstEngageMapper.selectByPrimaryKey(spu.getFirstEngageId());
                    if (firstEngage != null) {
                        registerNumberId = firstEngage.getRegistrationNumberId();
                    }

                }
                if (null != coreSpuGenerateExtendMapper.selectSpuBaseBySpuId(goods.getGoodsId())) {
                    baseGoodsService.mergeSpu(spu);
                    // spuGenerateMapper.updateByPrimaryKeySelective(spu);
                } else {
                    coreSpuGenerateExtendMapper.insertSpu(spu);
                }
                if (null != coreSpuGenerateExtendMapper.selectSkuBaseBySkuId(goods.getGoodsId())) {
                    baseGoodsService.mergeSku(sku);
                } else {
                    coreSpuGenerateExtendMapper.insertSku(sku);
                }

                //检测报告  658 专利文件 659    产品核心零部件价格 652
                GoodsAttachmentGenerateExample goodsAttachmentGenerateExample = new GoodsAttachmentGenerateExample();
                goodsAttachmentGenerateExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId())
                        // .andStatusEqualTo(CommonConstants.STATUS_1)
                        .andAttachmentTypeIn(Lists.newArrayList(658, 659, 652, 343, 344, 680));
                List<GoodsAttachmentGenerate> list = attachmentGenerateMapper.selectByExample(goodsAttachmentGenerateExample);
                GoodsAttachmentGenerateExample goodsAttachmentGenerateExample2 = new GoodsAttachmentGenerateExample();
                goodsAttachmentGenerateExample2.createCriteria().andGoodsIdEqualTo(goods.getGoodsId())
                        // .andStatusEqualTo(CommonConstants.STATUS_1)
                        .andAttachmentTypeIn(Lists.newArrayList(getOptionIdByOptionType(SysOptionConstant.SPU_CHECK_FILES), getOptionIdByOptionType(SysOptionConstant.SKU_CORE_PART_PRICE_FILE),
                                getOptionIdByOptionType(SysOptionConstant.SKU_CHECK_FILES), CommonConstants.ATTACHMENT_TYPE_SKU_1001, CommonConstants.ATTACHMENT_TYPE_SPU_1002));
                attachmentGenerateMapper.deleteByExample(goodsAttachmentGenerateExample2);

                if (CollectionUtils.isNotEmpty(list)) {
                    for (GoodsAttachmentGenerate generate : list) {

                        if (generate.getAttachmentType().intValue() == 658) {
                            GoodsAttachmentGenerate temp = new GoodsAttachmentGenerate();
                            temp.setGoodsId(goods.getGoodsId());
                            temp.setGoodsAttachmentId(null);
                            temp.setStatus(CommonConstants.STATUS_1);
                            temp.setUri(generate.getUri());
                            Integer optionType = getOptionIdByOptionType(SysOptionConstant.SPU_CHECK_FILES);
                            temp.setAttachmentType(optionType);
                            Integer result = isGoodsIdExist(goods.getGoodsId(),optionType);
                            if(result == 0) {
                                attachmentGenerateMapper.insert(temp);
                            }
                            GoodsAttachmentGenerate temp2 = new GoodsAttachmentGenerate();
                            temp2.setGoodsId(goods.getGoodsId());
                            temp2.setGoodsAttachmentId(null);
                            temp2.setUri(generate.getUri());
                            temp2.setStatus(CommonConstants.STATUS_1);
                            Integer optionType2 = getOptionIdByOptionType(SysOptionConstant.SKU_CHECK_FILES);
                            temp2.setAttachmentType(optionType2);
                            Integer result2 = isGoodsIdExist(goods.getGoodsId(),optionType2);
                            if(result2 == 0) {
                                attachmentGenerateMapper.insert(temp2);
                            }
                        }
                        if (generate.getAttachmentType().intValue() == 659) {
                            GoodsAttachmentGenerate temp = new GoodsAttachmentGenerate();
                            temp.setGoodsAttachmentId(null);
                            temp.setGoodsId(goods.getGoodsId());
                            temp.setStatus(CommonConstants.STATUS_1);
                            temp.setUri(generate.getUri());
                            Integer optionType = getOptionIdByOptionType(SysOptionConstant.SPU_PATENT_FILES);
                            temp.setAttachmentType(optionType);
                            Integer result = isGoodsIdExist(goods.getGoodsId(),optionType);
                            if(result == 0) {
                                attachmentGenerateMapper.insert(temp);
                            }

                            GoodsAttachmentGenerate temp2 = new GoodsAttachmentGenerate();
                            temp2.setGoodsAttachmentId(null);
                            temp2.setGoodsId(goods.getGoodsId());
                            temp2.setStatus(CommonConstants.STATUS_1);
                            temp2.setUri(generate.getUri());
                            Integer optionType2 = getOptionIdByOptionType(SysOptionConstant.SKU_PATENT_FILES);
                            temp2.setAttachmentType(optionType2);

                            Integer result2 = isGoodsIdExist(goods.getGoodsId(),optionType2);
                            if(result2 == 0) {
                                attachmentGenerateMapper.insert(temp2);
                            }
                        }
                        if (generate.getAttachmentType().intValue() == 652) {
                            GoodsAttachmentGenerate temp2 = new GoodsAttachmentGenerate();
                            temp2.setGoodsAttachmentId(null);
                            temp2.setGoodsId(goods.getGoodsId());
                            temp2.setStatus(CommonConstants.STATUS_1);
                            temp2.setUri(generate.getUri());
                            Integer optionType2 = getOptionIdByOptionType(SysOptionConstant.SKU_CORE_PART_PRICE_FILE);
                            temp2.setAttachmentType(optionType2);
                            Integer result2 = isGoodsIdExist(goods.getGoodsId(),optionType2);
                            if(result2 == 0) {
                                attachmentGenerateMapper.insert(temp2);
                            }
                        }
                        //注册证与备案
                        if (generate.getAttachmentType().intValue() == 344 || generate.getAttachmentType().intValue() == 680) {

                            if (registerNumberId != null) {

                                Map<String, Object> paramMap = Maps.newHashMap();
                                paramMap.put("attachmentType", CommonConstants.ATTACHMENT_TYPE_974);


                                paramMap.put("registrationNumberId", registerNumberId);

                                attachmentMapper.deleteByParam(paramMap);
//                                ATTACHMENT_TYPE = #{attachmentType, jdbcType=INTEGER}
//                                AND RELATED_ID = #{registrationNumberId, jdbcType=INTEGER}

                                Attachment attachment = new Attachment();
                                attachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_974);
                                attachment.setAttachmentFunction(CommonConstants.ATTACHMENT_FUNCTION_975);
                                attachment.setRelatedId(registerNumberId);
                                attachment.setDomain(domain);
                                attachment.setUri(generate.getUri());
                                attachmentMapper.insert(attachment);
                            }

//                            GoodsAttachmentGenerate temp2=new GoodsAttachmentGenerate();
//                            temp2.setGoodsAttachmentId(null);
//                            temp2.setGoodsId(goods.getGoodsId());
//                            temp2.setStatus(CommonConstants.STATUS_1);
//                            temp2.setUri(generate.getUri());
//                            temp2.setAttachmentType(CommonConstants.ATTACHMENT_FUNCTION_975);
//                            attachmentGenerateMapper.insert(temp2);
                        }

                        if (generate.getAttachmentType().intValue() == 343) {
                            GoodsAttachmentGenerate temp2 = new GoodsAttachmentGenerate();
                            temp2.setGoodsAttachmentId(null);
                            temp2.setGoodsId(goods.getGoodsId());
                            temp2.setStatus(CommonConstants.STATUS_1);
                            temp2.setUri(generate.getUri());
                            Integer optionType2 = CommonConstants.ATTACHMENT_TYPE_SKU_1001;
                            //  temp2.setDomain("file1.vedeng.com");

                            temp2.setAttachmentType(optionType2);
                            Integer result2 = isGoodsIdExist(goods.getGoodsId(),optionType2);
                            if(result2 == 0) {
                                attachmentGenerateMapper.insert(temp2);
                            }
                            GoodsAttachmentGenerate temp3 = new GoodsAttachmentGenerate();
                            temp3.setGoodsAttachmentId(null);
                            temp3.setGoodsId(goods.getGoodsId());
                            temp3.setStatus(CommonConstants.STATUS_1);
                            temp3.setUri(generate.getUri());
//                           // temp3.setDomain("file1.vedeng.com");
                            Integer optionType3 = CommonConstants.ATTACHMENT_TYPE_SPU_1002;
                            temp3.setAttachmentType(optionType3);
                            Integer result3 = isGoodsIdExist(goods.getGoodsId(),optionType3);
                            if(result3 == 0) {
                                attachmentGenerateMapper.insert(temp3);
                            }
                        }
                    }
                }


                GoodsGenerateWithBLOBs UPDATE = new GoodsGenerateWithBLOBs();
                UPDATE.setToSkuFlag(1);
                UPDATE.setGoodsId(goods.getGoodsId());
                mapper.updateByPrimaryKeySelective(UPDATE);

            } catch (Exception e) {
                logger.error(Contant.ERROR_MSG, e);
                throw e;
            }
        }

        return SUCCESS;
    }

    private Integer getOptionIdByOptionType(String optionType) {
        SysOptionDefinition option = baseService.getFirstSysOptionDefinitionList(optionType);
        if (option != null) {
            return option.getSysOptionDefinitionId();
        }
        return -1;
    }

    private Integer isGoodsIdExist(Integer goodsId,Integer optionType){
        GoodsAttachmentGenerateExample example = new GoodsAttachmentGenerateExample();
        example.createCriteria().andGoodsIdEqualTo(goodsId).andAttachmentTypeEqualTo(optionType).andStatusEqualTo(1);
        Integer result = attachmentGenerateMapper.countByExample(example);
        return result;
    }

}
