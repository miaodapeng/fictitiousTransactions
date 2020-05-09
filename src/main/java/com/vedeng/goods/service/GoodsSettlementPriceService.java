package com.vedeng.goods.service;

import java.util.List;

import com.vedeng.goods.model.GoodsSettlementPrice;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.SaleorderGoods;

public interface GoodsSettlementPriceService {

	/**
	 * <b>Description:</b><br> 查询产品结算价
	 * @param quoteGoodsList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月23日 下午5:44:16
	 */
	List<QuoteorderGoods> getGoodsSettlePriceByGoodsList(Integer companyId,List<QuoteorderGoods> quoteGoodsList);

	/**
	 * <b>Description:</b><br> 查询产品结算价(销售订单产品)
	 * @param companyId
	 * @param saleorderGoodsList
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年1月18日 下午6:02:06
	 */
	List<SaleorderGoods> getGoodsSettlePriceBySaleorderGoodsList(Integer companyId,
			List<SaleorderGoods> saleorderGoodsList);

	/**
	 * <b>Description:</b><br> 获取单个产品的结算价
	 * @param goodsSettlementPrice
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年1月29日 上午11:15:12
	 */
	GoodsSettlementPrice getGoodsSettlePriceByGoods(GoodsSettlementPrice goodsSettlementPrice);

}
