package com.vedeng.goods.dao;

import java.util.List;

import javax.inject.Named;

import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsChannelPriceExtend;
@Named("goodsChannelPriceExtendMapper")
public interface GoodsChannelPriceExtendMapper {
    int deleteByPrimaryKey(Integer goodsChannelPriceExtendId);

    int insert(GoodsChannelPriceExtend record);

    int insertSelective(GoodsChannelPriceExtend record);

    GoodsChannelPriceExtend selectByPrimaryKey(Integer goodsChannelPriceExtendId);

    int updateByPrimaryKeySelective(GoodsChannelPriceExtend record);

    int updateByPrimaryKey(GoodsChannelPriceExtend record);
    /**
     * 
     * <b>Description:</b><br> 查询产品核价附属信息
     * @param goodsChannelPriceExtend
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月16日 下午5:33:16
     */
	List<GoodsChannelPriceExtend> getGoodsChannelPriceExtendList(GoodsChannelPriceExtend goodsChannelPriceExtend);
    /**
     * 
     * <b>Description:</b><br> 删除产品核价附属信息
     * @param gc
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月21日 下午2:33:57
     */
	void deleteGoodsChannelPriceExtend(GoodsChannelPriceExtend gc);
	
	/**
	 * 
	 * <b>Description:</b>删除核价附属信息
	 * @param gc void
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 下午2:21:04
	 */
	void deleteGoodsChannelPriceExtendAll(GoodsChannelPrice gcp);
}