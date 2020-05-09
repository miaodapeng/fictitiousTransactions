package com.vedeng.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.goods.dao.GoodsSettlementPriceMapper;
import com.vedeng.goods.model.GoodsSettlementPrice;
import com.vedeng.goods.service.GoodsSettlementPriceService;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.SaleorderGoods;


@Service("goodsSettlementPriceService")
public class GoodsSettlementPriceServiceImpl implements GoodsSettlementPriceService{
	
	
	@Autowired
	@Qualifier("goodsSettlementPriceMapper")
	private GoodsSettlementPriceMapper goodsSettlementPriceMapper;

	@Override
	public List<QuoteorderGoods> getGoodsSettlePriceByGoodsList(Integer companyId, List<QuoteorderGoods> quoteGoodsList) {
		if(quoteGoodsList != null && !quoteGoodsList.isEmpty()){
			List<GoodsSettlementPrice> list = goodsSettlementPriceMapper.getGoodsSettlePriceByGoodsList(companyId,quoteGoodsList);
			if(list != null && !list.isEmpty()){
				for(int i=0;i<quoteGoodsList.size();i++){
					for(int j=0;j<list.size();j++){
						if(quoteGoodsList.get(i).getGoods().getGoodsId().equals(list.get(j).getGoodsId())){
							quoteGoodsList.get(i).setSettlePrice(list.get(j).getSettlementPrice());//商品结算价
						}
					}
				}
			}
		}
		return quoteGoodsList;
	}

	@Override
	public List<SaleorderGoods> getGoodsSettlePriceBySaleorderGoodsList(Integer companyId,
			List<SaleorderGoods> saleorderGoodsList) {
		if(saleorderGoodsList != null && !saleorderGoodsList.isEmpty()){
			List<GoodsSettlementPrice> list = goodsSettlementPriceMapper.getGoodsSettlePriceBySaleorderGoodsList(companyId,saleorderGoodsList);
			if(list != null && !list.isEmpty()){
				for(int i=0;i<saleorderGoodsList.size();i++){
					for(int j=0;j<list.size();j++){
						if(saleorderGoodsList.get(i).getGoods().getGoodsId().equals(list.get(j).getGoodsId())){
							saleorderGoodsList.get(i).setSettlePrice(list.get(j).getSettlementPrice());//商品结算价
						}
					}
				}
			}
		}
		return saleorderGoodsList;
	}

	@Override
	public GoodsSettlementPrice getGoodsSettlePriceByGoods(GoodsSettlementPrice goodsSettlementPrice) {
		return goodsSettlementPriceMapper.getGoodsSettlePriceByGoods(goodsSettlementPrice);
	}

}
