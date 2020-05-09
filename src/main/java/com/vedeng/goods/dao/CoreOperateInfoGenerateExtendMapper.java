package com.vedeng.goods.dao;

import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.vo.CoreOperateInfoGenerateVo;
import com.vedeng.goods.model.vo.OperateSkuVo;
import com.vedeng.goods.model.vo.OperateSpuVo;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;

@Named("coreOperateInfoGenerateExtendMapper")
public interface CoreOperateInfoGenerateExtendMapper {
    /**
     * 根据主键获取运营信息
     * @param record
     * @return
     */
    CoreOperateInfoGenerateVo getCoreOperateInfoById(CoreOperateInfoGenerateVo record);

    /**
     * 根据SKUID获取商品名称
     * @param skuId
     * @return
     */
    String getProductNameBySkuId(Integer skuId);

    /**
     * 根据SKUID获取商品名称
     * @param spuId
     * @return
     */
    String getProductNameBySpuId(Integer spuId);

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

    OperateSpuVo selectSpuOperateBySpuId(Integer spuId);

    OperateSkuVo selectSkuOperateBySpuId(Integer skuId);

    List<GoodsAttachment> selectSkuOperateAttachmentList(@Param("goodsId") Integer goodsId, @Param("attachmentType")int attachmentType);

    List<Integer> getCoreSkuInfoBySkuNo(@Param("skuNoList")List<String> skuNoList);
}