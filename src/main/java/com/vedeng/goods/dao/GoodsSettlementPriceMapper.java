package com.vedeng.goods.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.goods.model.GoodsSettlementPrice;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.SaleorderGoods;

@Named("goodsSettlementPriceMapper")
public interface GoodsSettlementPriceMapper {
    int deleteByPrimaryKey(Integer goodsSettlementPriceId);

    int insert(GoodsSettlementPrice record);

    int insertSelective(GoodsSettlementPrice record);

    GoodsSettlementPrice selectByPrimaryKey(Integer goodsSettlementPriceId);

    int updateByPrimaryKeySelective(GoodsSettlementPrice record);

    int updateByPrimaryKey(GoodsSettlementPrice record);

    /**
     * <b>Description:</b><br>
     * 验证产品结算信息是否存在，存在-更新，不存在-新增；goodsId唯一
     * 
     * @param list
     * @return
     * @Note <b>Author:</b> duke <br>
     *       <b>Date:</b> 2017年12月7日 上午11:51:25
     */
    int batchGoodsSettelmentSave(@Param("goodsList") List<GoodsSettlementPrice> goodsList);
    
    BigDecimal getSaleorderGoodsSettlementPrice(@Param("goodsId")Integer goodsId,@Param("companyId")Integer companyId);

	List<GoodsSettlementPrice> getGoodsSettlePriceByGoodsList(@Param("companyId")Integer companyId, @Param("quoteGoodsList")List<QuoteorderGoods> quoteGoodsList);

	List<GoodsSettlementPrice> getGoodsSettlePriceBySaleorderGoodsList(@Param("companyId")Integer companyId,
			@Param("saleorderGoodsList")List<SaleorderGoods> saleorderGoodsList);

	/**
	 * <b>Description:</b><br> 获取单个产品结算价
	 * @param goodsSettlementPrice
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年1月29日 上午11:16:48
	 */
	GoodsSettlementPrice getGoodsSettlePriceByGoods(GoodsSettlementPrice goodsSettlementPrice);
	/**
	 * 
	 * <b>Description:</b><br>根据产品id集合获取产品的结算价Map 
	 * @param goodsIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年7月20日 上午8:57:31
	 */
	List<GoodsSettlementPrice> getSaleorderGoodsSettlementPriceByGoodsIds(@Param("goodsIds")List<Integer> goodsIds,@Param("companyId")Integer companyId);

}