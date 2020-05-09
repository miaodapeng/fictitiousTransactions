package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.CoreOperateInfoGenerate;
import com.vedeng.goods.model.GoodsAttachment;

import java.util.List;

public class CoreOperateInfoGenerateVo extends CoreOperateInfoGenerate {

    /**商品名称 PRODUCT_NAME**/
    private String productName;

    /**商品图片路径 GOODS_IMAGE**/
    private String[] goodsImage;

    /**seo关键词数组 GOODS_IMAGE**/
    private String[] seoKeyWordsArray;

    /**商品图片列表**/
    private List<GoodsAttachment> goodsAttachmentList;

    /**sku对应的spuID**/
    private Integer upSpuId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String[] getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String[] goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String[] getSeoKeyWordsArray() {
        return seoKeyWordsArray;
    }

    public void setSeoKeyWordsArray(String[] seoKeyWordsArray) {
        this.seoKeyWordsArray = seoKeyWordsArray;
    }

    public List<GoodsAttachment> getGoodsAttachmentList() {
        return goodsAttachmentList;
    }

    public void setGoodsAttachmentList(List<GoodsAttachment> goodsAttachmentList) {
        this.goodsAttachmentList = goodsAttachmentList;
    }

    public Integer getUpSpuId() {
        return upSpuId;
    }

    public void setUpSpuId(Integer upSpuId) {
        this.upSpuId = upSpuId;
    }
}
