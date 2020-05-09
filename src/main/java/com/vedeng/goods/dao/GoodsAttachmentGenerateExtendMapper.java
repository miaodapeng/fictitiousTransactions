package com.vedeng.goods.dao;

import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.vo.GoodsAttachmentVo;

import javax.inject.Named;
import java.util.List;

@Named("goodsAttachmentGenerateExtendMapper")
public interface GoodsAttachmentGenerateExtendMapper {
    /**
     * 获取spu下没有图片的sku的id列表
     * @param record
     * @return
     */
    List<GoodsAttachmentVo> getNoAttachmentsSkuNumber(GoodsAttachment record);

    /**
     * 删除图片（禁用）
     * @param record
     * @return
     */
    Integer deleteByGoodsIdAndType(GoodsAttachment record);

    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer insertBatch(List<GoodsAttachment> list);

    /**
     * 获取图片列表
     * @param record
     * @return
     */
    List<GoodsAttachment> getGoodsAttachment(GoodsAttachment record);
}