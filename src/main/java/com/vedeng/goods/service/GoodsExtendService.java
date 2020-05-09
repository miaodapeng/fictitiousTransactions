package com.vedeng.goods.service;

import java.util.List;

import com.vedeng.goods.model.GoodsExtend;


/**
 * <b>Description:</b><br> 产品扩展信息
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> GoodsChannelPriceService
 * <br><b>Date:</b> 2017年12月19日 下午7:02:46
 */
public interface GoodsExtendService {
    	/**
    	 * 
    	 * <b>Description:</b><br> 根据订单ID或者报价单ID获取产品扩展信息
    	 * @return
    	 * @Note
    	 * <b>Author:</b> Michael
    	 * <br><b>Date:</b> 2018年3月30日 下午6:17:19
    	 */
	List<GoodsExtend> getGoodsExtendByOrderIdList(GoodsExtend goodsExtend);
}
