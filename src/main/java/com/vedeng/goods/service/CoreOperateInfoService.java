package com.vedeng.goods.service;

import com.alibaba.fastjson.JSONObject;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.vo.CoreOperateInfoGenerateVo;

import java.util.List;

public interface CoreOperateInfoService {

    /**
     * 根据主键获取运营信息
     * @param coreOperateInfoGenerateVo
     * @return
     */
    CoreOperateInfoGenerateVo getCoreOperateInfoById(CoreOperateInfoGenerateVo coreOperateInfoGenerateVo);

    /**
     * 根据SKUID获取商品名称
     * @param skuId
     * @return
     */
    String getProductNameBySkuId(Integer skuId);

    /**
     * 根据SPUID获取商品名称
     * @param spuId
     * @return
     */
    String getProductNameBySpuId(Integer spuId);

    /**
     * 保存商品运营信息
     * @param record
     * @return
     */
    Integer saveCoreOperateInfo(CoreOperateInfoGenerateVo record, List<GoodsAttachment> goodsAttachmentList);

    /**
     * 获取图片列表
     * @param goodsAttachment
     * @return
     */
    List<GoodsAttachment> getGoodsAttachment(GoodsAttachment goodsAttachment);

    /**
     * 根据SKUID获取运营信息
     * @param skuId
     * @return
     */
    CoreOperateInfoGenerateVo getCoreOperateInfoBySkuId(Integer skuId);

    /**
     * 根据SPUID获取运营信息
     * @param spuId
     * @return
     */
    CoreOperateInfoGenerateVo getCoreOperateInfoBySpuId(Integer spuId);

    /**
     * 功能描述: 推送sku相关信息到运营管理后台
     * @param: [platfromIds, skuId, spuId]
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: duke.li
     * @date: 2019/7/24 9:31
     */
    JSONObject getPushGoodsInfo(String platfromIds, Integer skuId, Integer spuId);

    List<Integer> getCoreSkuInfoBySkuNo(List<String> skuNoList);
}
