package com.vedeng.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.goods.GoodsConstants;
import com.vedeng.goods.dao.*;
import com.vedeng.goods.model.CoreOperateInfoGenerate;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.dto.CoreSkuBaseDTO;
import com.vedeng.goods.model.vo.*;
import com.vedeng.goods.service.CoreOperateInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("coreOperateInfoService")
public class CoreOperateInfoServiceImpl implements CoreOperateInfoService {

    @Autowired
    CoreOperateInfoGenerateMapper coreOperateInfoGenerateMapper;

    @Autowired
    CoreSkuGenerateMapper coreSkuGenerateMapper;

    @Autowired
    CoreOperateInfoGenerateExtendMapper coreOperateInfoGenerateExtendMapper;

    @Autowired
    GoodsAttachmentGenerateMapper goodsAttachmentGenerateMapper;

    @Autowired
    GoodsAttachmentGenerateExtendMapper goodsAttachmentGenerateExtendMapper;

    @Override
    public CoreOperateInfoGenerateVo getCoreOperateInfoById(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo) {

        return coreOperateInfoGenerateExtendMapper.getCoreOperateInfoById(coreOperateInfoGenerateVo);
    }

    @Override
    public String getProductNameBySkuId(Integer skuId) {

        return coreOperateInfoGenerateExtendMapper.getProductNameBySkuId(skuId);
    }

    @Override
    public String getProductNameBySpuId(Integer spuId) {

        return coreOperateInfoGenerateExtendMapper.getProductNameBySpuId(spuId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Integer saveCoreOperateInfo(CoreOperateInfoGenerateVo record, List<GoodsAttachment> goodsAttachmentList) {
        //处理seo关键词，以英文逗号拼接
        /*String[] seoKsyWordsArray = record.getSeoKeyWordsArray();
        if (seoKsyWordsArray.length > 0){
            String seoKeyWords = "";
            for (String seoKeyWordsTemp : seoKsyWordsArray){
                seoKeyWords = seoKeyWords + seoKeyWordsTemp + ",";
            }
            record.setSeoKeywords(seoKeyWords.substring(0,seoKeyWords.length()-1));
        }
        //处理seo标题以及seo描述，设置默认值
        if (record.getSeoTitle() == null || record.getSeoTitle() == ""){
            record.setSeoTitle(record.getGoodsName());
        }
        if (record.getSeoDescript() == null || record.getSeoDescript() == ""){
            record.setSeoDescript("为您提供"+record.getGoodsName()+"，包括 "+record.getSeoKeywords().replaceAll(",","、"));
        }*/
        // 根据skuId或spuId查看是否已存在运营信息
        CoreOperateInfoGenerate operateInfoGenerate = coreOperateInfoGenerateMapper.selectCoreOperateInfo(record);
        Integer result = 0;
        //判断是编辑保存还是新增保存
        if (operateInfoGenerate != null && operateInfoGenerate.getOperateInfoId() != null){
            record.setOperateInfoId(operateInfoGenerate.getOperateInfoId());
            //编辑保存更新
            result = coreOperateInfoGenerateMapper.updateByPrimaryKeySelective(record);
        }else{
            //新增保存插入
            result = coreOperateInfoGenerateMapper.insertSelective(record);

        }
        if (result > 0){//保存成功
            if (CollectionUtils.isNotEmpty(goodsAttachmentList)){
                GoodsAttachment goodsAttachment = new GoodsAttachment();
                //保存商品图片
                if (record.getOperateInfoType().equals(CommonConstants.OPERATE_INFO_TYPE_SPU_1)){
                    //查询该SPU下所有没有图片的SKUID
                    goodsAttachment.setGoodsId(record.getSpuId());
                    goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
                    goodsAttachment.setStatus(CommonConstants.STATUS_1);
                    List<GoodsAttachmentVo> goodsAttachmentVoList = goodsAttachmentGenerateExtendMapper.getNoAttachmentsSkuNumber(goodsAttachment);
                    //删除原SPU图片
                    goodsAttachment.setStatus(CommonConstants.STATUS_0);
                    goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SPU_1002);
                    goodsAttachmentGenerateExtendMapper.deleteByGoodsIdAndType(goodsAttachment);
                    //插入新的SPU图片
                    goodsAttachmentGenerateExtendMapper.insertBatch(goodsAttachmentList);
                    //为没有图片的SKU插入图片
                    List<GoodsAttachment> skuImgList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(goodsAttachmentVoList)){
                        for (GoodsAttachmentVo goodsAttachmentVo : goodsAttachmentVoList){
                            for (GoodsAttachment spuImg : goodsAttachmentList){
                                GoodsAttachment skuImg = new GoodsAttachment();
                                skuImg.setGoodsId(goodsAttachmentVo.getGoodsId());
                                skuImg.setStatus(spuImg.getStatus());
                                skuImg.setSort(spuImg.getSort());
                                skuImg.setDomain(spuImg.getDomain());
                                skuImg.setUri(spuImg.getUri());
                                skuImg.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
                                skuImgList.add(skuImg);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(skuImgList)){
                            goodsAttachmentGenerateExtendMapper.insertBatch(skuImgList);
                        }
                    }
                }else{
                    //删除原SKU图片
                    goodsAttachment.setGoodsId(record.getSkuId());
                    goodsAttachment.setAttachmentType(CommonConstants.ATTACHMENT_TYPE_SKU_1001);
                    goodsAttachment.setStatus(CommonConstants.STATUS_0);
                    goodsAttachmentGenerateExtendMapper.deleteByGoodsIdAndType(goodsAttachment);
                    //插入新的SKU图片
                    goodsAttachmentGenerateExtendMapper.insertBatch(goodsAttachmentList);
                }
            }
        }
        return record.getOperateInfoId();
    }

    @Override
    public List<GoodsAttachment> getGoodsAttachment(GoodsAttachment goodsAttachment) {

        return goodsAttachmentGenerateExtendMapper.getGoodsAttachment(goodsAttachment);
    }

    @Override
    public CoreOperateInfoGenerateVo getCoreOperateInfoBySkuId(Integer skuId) {

        return coreOperateInfoGenerateExtendMapper.getCoreOperateInfoBySkuId(skuId);
    }

    @Override
    public CoreOperateInfoGenerateVo getCoreOperateInfoBySpuId(Integer spuId) {

        return coreOperateInfoGenerateExtendMapper.getCoreOperateInfoBySpuId(spuId);
    }


    private void updateSkuPushStatus(List<String> platformIds,Integer skuId){
        int pushStatus = coreSkuGenerateMapper.getPushStatusBySkuId(skuId);

        // changed by Tomcat.Hui 2020/2/21 3:04 下午 .Desc: VDERP-2045 erp商品推送指南针增加科研购平台.start.
        if(CollectionUtils.isNotEmpty(platformIds) && platformIds.size() > 0) {

            /** 原有推送状态为 0未推送 1贝登推送 2医械购推送 3全部推送
             * 现改为二进制方式保存  0000-未推送 0001-贝登推送 0010-医械购推送 0100-科研购
             * 根据按位与状态判断是否为全部推送 **/

            //这里要注意:因为目前枚举值1,2,4正好与0001,0010,0100对应
            //注意:如果继续增加枚举值 例如：5 则应该转换为8(1000);6转换为16(0001 0000);7转换为32(0010 0000) 以此类推
            List<Integer> codes = platformIds.stream().map(x -> {switch(x){ default: return Integer.parseInt(x);}}).collect(Collectors.toList());

            //计算得到本次需要添加的平台ID总和
            //例如:本次添加平台ID为 1、4,则值为5(0101)
            Integer pushStatusAdd = codes.stream().reduce((sum,item) -> sum | item).get();

            //与已添加平台合并(或运算)
            //例如已添加平台2,本次添加平台1、4,添加完成之后值为7(0111)
            pushStatusAdd = pushStatusAdd | pushStatus;

            coreSkuGenerateMapper.updatePushStatusBySkuId(skuId, pushStatusAdd);
        }
        // changed by Tomcat.Hui 2020/2/21 3:05 下午 .Desc: VDERP-2045 erp商品推送指南针增加科研购平台. end.
    }
    @Override
    public JSONObject getPushGoodsInfo(String platfromIds, Integer skuId, Integer spuId) {

        String[] split = platfromIds.split(",");
        List<String> platfromIdList = Arrays.asList(split);

        OperateSkuVo operateSkuVo = coreOperateInfoGenerateExtendMapper.selectSkuOperateBySpuId(skuId);
        if(operateSkuVo == null){
            return null;
        }
        operateSkuVo.setPlatfromIdList(platfromIdList);
        updateSkuPushStatus(platfromIdList,skuId);
        List<GoodsAttachment> attachmentList = coreOperateInfoGenerateExtendMapper.selectSkuOperateAttachmentList(skuId,1001);
        List<Attachment> attachments = new ArrayList<>();
        if(attachmentList != null){
            for(int i=0;i<attachmentList.size();i++){
                Attachment attachment = new Attachment();
                attachment.setRelatedId(Long.parseLong(attachmentList.get(i).getGoodsId().toString()));
                attachment.setDomain(attachmentList.get(i).getDomain());
                attachment.setUri(attachmentList.get(i).getUri());
                attachment.setDescription(attachmentList.get(i).getAlt());
                attachment.setSort(attachmentList.get(i).getSort());
                attachments.add(attachment);
            }
            operateSkuVo.setAttachmentList(attachments);
        }

        OperateSpuVo operateSpuVo = coreOperateInfoGenerateExtendMapper.selectSpuOperateBySpuId(Integer.parseInt(operateSkuVo.getSpuId().toString()));
        operateSpuVo.setOperateSku(operateSkuVo);
        return (JSONObject) JSONObject.toJSON(operateSpuVo);
    }

    @Override
    public List<Integer> getCoreSkuInfoBySkuNo(List<String> skuNoList) {
        return coreOperateInfoGenerateExtendMapper.getCoreSkuInfoBySkuNo(skuNoList);
    }

    public static void main(String[] args) {
        int a = 8;
        int b = 4;

//        List<String> arr = Lists.newArrayList("8","2","1","5");
        List<Integer> arr = Lists.newArrayList(8,2,1,5);

//        arr.stream().map(x -> { switch(x){ case 8:return 81; case 2: return 21; default : return x;}}).collect(Collectors.toList());
        System.out.println(        arr.stream().map(x -> { switch(x){ case 8:return 81; case 2: return 21; default : return x;}}).collect(Collectors.toList()));
//        List<Integer> arr1 = arr.stream().map(x -> {
//
//        });
//        System.out.println(Integer.toBinaryString(arr.stream().reduce((sum,item) -> sum | item).get()));
//        System.out.println(Integer.toBinaryString(20));
    }
}