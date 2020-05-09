package com.vedeng.goods.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.Region;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.dao.GoodsChannelPriceExtendMapper;
import com.vedeng.goods.dao.GoodsChannelPriceMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsChannelPriceExtend;
import com.vedeng.goods.model.GoodsExtend;
import com.vedeng.goods.service.GoodsChannelPriceService;
import com.vedeng.goods.service.GoodsExtendService;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.system.service.RegionService;

import javax.xml.crypto.Data;

@Service("goodsChannelPriceService")
public class GoodsChannelPriceServiceImpl extends BaseServiceimpl implements GoodsChannelPriceService {

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;

	@Autowired
	@Qualifier("goodsExtendService")
	private GoodsExtendService goodsExtendService;

	@Autowired
	@Qualifier("goodsChannelPriceMapper")
	private GoodsChannelPriceMapper goodsChannelPriceMapper;

	@Autowired
	@Qualifier("goodsChannelPriceExtendMapper")
	private GoodsChannelPriceExtendMapper goodsChannelPriceExtendMapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<SaleorderGoods> getSaleChannelPriceList(Integer salesAreaId, Integer customerNature, Integer ownerShip,
			List<SaleorderGoods> saleGoodsList) {
		// 销售区域最小级ID不允许为空，，产品列表不允许为空，，客户性质不允许为空
		if (salesAreaId != null && saleGoodsList != null && (!saleGoodsList.isEmpty()) && customerNature != null) {
			// 获取订单ID
			Integer saleorderId = saleGoodsList.get(0).getSaleorderId();
			// 根据ID获取产品额外信息
			GoodsExtend goodsExtend = new GoodsExtend();
			goodsExtend.setSaleorderId(saleorderId);
			List<GoodsExtend> goodsExtendList = goodsExtendService.getGoodsExtendByOrderIdList(goodsExtend);
			if (goodsExtendList != null) {
				for (int i = 0; i < saleGoodsList.size(); i++) {
					for (int k = 0; k < goodsExtendList.size(); k++) {
						if (saleGoodsList.get(i).getGoodsId().equals(goodsExtendList.get(k).getGoodsId())) {
							saleGoodsList.get(i).setChannelDeliveryCycle(goodsExtendList.get(k).getFuturesDelivery());
							saleGoodsList.get(i).setDelivery(goodsExtendList.get(k).getDelivery());
						}
					}
				}
			}
			Object obj = regionService.getRegion(salesAreaId, 1);
			if (obj != null) {// 销售区域最小级ID对应省市不为空
				Integer provinceId = null, cityId = null;
				List<Region> regionList = (List<Region>) obj;
				for (int i = 0; i < regionList.size(); i++) {
					if (i == 1) {
						provinceId = regionList.get(i).getRegionId();
						continue;
					}
					if (i == 2) {
						cityId = regionList.get(i).getRegionId();
						continue;
					}
				}
				if (provinceId != null) {// 省市ID不为空
					List<Integer> goodsIdList = new ArrayList<>();
					for (int k = 0; k < saleGoodsList.size(); k++) {
						goodsIdList.add(saleGoodsList.get(k).getGoodsId());
					
					if (goodsIdList != null && !goodsIdList.isEmpty()) {// 对应的产品ID不为空
						// provinceId省ID,cityId市ID,customerNature客户性质,list产品ID
						Long nowTime = DateUtil.gainNowDate();
						Integer type = 1;
						List<GoodsChannelPrice> channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(
								provinceId, cityId, customerNature, ownerShip, goodsIdList, nowTime, type);
						// 如果没有省和市的 渠道价格 就去省的渠道价格
						if (channelPriceList.isEmpty()) {
							channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(provinceId, null,
									customerNature, ownerShip, goodsIdList, nowTime, type);
						}
						// 如果没有省和市的 渠道价格 就取全国的渠道价格
						if (channelPriceList.isEmpty()) {
							channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(1, null, customerNature,
									ownerShip, goodsIdList, nowTime, type);
						}
						// 核算价格不为空
						if (channelPriceList != null && !channelPriceList.isEmpty()) {
							int num = 0;
							for (int i = 0; i < saleGoodsList.size(); i++) {
								for (int j = 0; j < channelPriceList.size(); j++) {
									if (saleGoodsList.get(i).getGoodsId()
											.equals(channelPriceList.get(j).getGoodsId())) {
										num++;
										saleGoodsList.get(i).setChannelPrice(channelPriceList.get(j).getChannelPrice());
										saleGoodsList.get(i).setCostPrice(channelPriceList.get(j).getCostPrice());
										// saleGoodsList.get(i).setChannelDeliveryCycle(channelPriceList.get(j).getDeliveryCycle());
									}
								}

								if (num == 0) {
									saleGoodsList.get(i).setAreaControl(1);
								}
							}
						}
					}
					}
				}
			}
		}
		return saleGoodsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuoteorderGoods> getQuoteChannelPriceList(Integer salesAreaId, Integer customerNature,
			Integer ownerShip, List<QuoteorderGoods> quoteGoodsList) {
		// 销售区域最小级ID不允许为空，，产品列表不允许为空，，客户性质不允许为空
		if (salesAreaId != null && quoteGoodsList != null && (!quoteGoodsList.isEmpty()) && customerNature != null) {
			// 获取订单ID
			Integer quoteorderId = quoteGoodsList.get(0).getQuoteorderId();
			// 根据ID获取产品额外信息
			GoodsExtend goodsExtend = new GoodsExtend();
			goodsExtend.setQuoteorderId(quoteorderId);
			List<GoodsExtend> goodsExtendList = goodsExtendService.getGoodsExtendByOrderIdList(goodsExtend);
			for (int i = 0; i < quoteGoodsList.size(); i++) {
				if (goodsExtendList != null) {
					for (int k = 0; k < goodsExtendList.size(); k++) {
						if (quoteGoodsList.get(i).getGoodsId().equals(goodsExtendList.get(k).getGoodsId())) {
							quoteGoodsList.get(i).setChannelDeliveryCycle(goodsExtendList.get(k).getFuturesDelivery());
							quoteGoodsList.get(i).setDelivery(goodsExtendList.get(k).getDelivery());
						}
					}
				}
			}
			Object obj = regionService.getRegion(salesAreaId, 1);
			if (obj != null) {// 销售区域最小级ID对应省市不为空
				Integer provinceId = null, cityId = null;
				List<Region> regionList = (List<Region>) obj;
				for (int i = 0; i < regionList.size(); i++) {
					if (i == 1) {
						provinceId = regionList.get(i).getRegionId();
						continue;
					}
					if (i == 2) {
						cityId = regionList.get(i).getRegionId();
						continue;
					}
				}
				if (provinceId != null) {// 省市ID不为空
					for (int k = 0; k < quoteGoodsList.size(); k++) {
						List<Integer> goodsIdList = new ArrayList<>();
						goodsIdList.add(quoteGoodsList.get(k).getGoodsId());
					
					if (goodsIdList != null && !goodsIdList.isEmpty()) {// 对应的产品ID不为空
						// provinceId省ID,cityId市ID,customerNature客户性质,list产品ID
						Long nowTime = DateUtil.gainNowDate();
						Integer type = 1;
						List<GoodsChannelPrice> channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(
								provinceId, cityId, customerNature, ownerShip, goodsIdList, nowTime, type);
						// 如果没有省和市的 渠道价格 就去省的渠道价格
						if (channelPriceList.isEmpty()) {
							channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(provinceId, null,
									customerNature, ownerShip, goodsIdList, nowTime, type);
						}
						// 如果没有省和市的 渠道价格 就取全国的渠道价格
						if (channelPriceList.isEmpty()) {
							channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(1, null, customerNature,
									ownerShip, goodsIdList, nowTime, type);
						}
						// 核算价格不为空
						if (channelPriceList != null && !channelPriceList.isEmpty()) {
							int num = 0;
							for (int i = 0; i < quoteGoodsList.size(); i++) {
								for (int j = 0; j < channelPriceList.size(); j++) {
									if (quoteGoodsList.get(i).getGoodsId()
											.equals(channelPriceList.get(j).getGoodsId())) {
										num++;
										quoteGoodsList.get(i)
												.setChannelPrice(channelPriceList.get(j).getChannelPrice());
										// 成本价
										quoteGoodsList.get(i).setCostPrice(channelPriceList.get(j).getCostPrice());
										// 货期
										// quoteGoodsList.get(i).setChannelDeliveryCycle(channelPriceList.get(j).getDeliveryCycle());
									}
								}
								if (num == 0) {
									quoteGoodsList.get(i).setAreaControl(1);
								}
							}
						}
					}
					}
				}
			}
		}
		return quoteGoodsList;
	}

	@Override
	public List<GoodsChannelPrice> getGoodsChannelPriceByGoodsId(Map map) {
		List<GoodsChannelPrice> list = new ArrayList<GoodsChannelPrice>();
		list = goodsChannelPriceMapper.getGoodsChannelPriceByGoodsId(map);
		// 获取对应采购的市场价
		for (GoodsChannelPrice goodsChannelPrice : list) {
			GoodsChannelPrice g = goodsChannelPriceMapper.getGoodsChannelPriceInfo(goodsChannelPrice);
			if (g != null) {
				goodsChannelPrice.setMarketPrice(g.getMarketPrice());
			}

		}
		return list;
	}

	@Override
	public ResultInfo<?> delGoodsChannelPrice(GoodsChannelPrice goodsChannelPrice) {
		ResultInfo<?> result = new ResultInfo<>();
		List<GoodsChannelPriceExtend> goodsChannelPriceExtendList = goodsChannelPrice.getGoodsChannelPriceExtendList();
		// 先删除核价子表附属信息
		if (null != goodsChannelPriceExtendList) {
			for (GoodsChannelPriceExtend ex : goodsChannelPriceExtendList) {
				// 删除核价附属信息
				GoodsChannelPriceExtend gc = new GoodsChannelPriceExtend();
				gc.setGoodsChannelPriceId(ex.getGoodsChannelPriceId());
				goodsChannelPriceExtendMapper.deleteGoodsChannelPriceExtend(gc);
			}
		}
		// 删除核价主表
		goodsChannelPriceMapper.delGoodsChannelPrice(goodsChannelPrice);
		result.setCode(0);
		result.setMessage("操作成功");
		return result;
	}

	@Override
	public GoodsChannelPrice getGoodsChannelByGoodsId(Integer salesAreaId, Integer customerNature, Integer goodsId) {
		// 销售区域最小级ID不允许为空，，产品列表不允许为空，，客户性质不允许为空
		if (salesAreaId != null && goodsId != null && customerNature != null) {
			Object obj = regionService.getRegion(salesAreaId, 1);
			if (obj != null) {// 销售区域最小级ID对应省市不为空
				Integer provinceId = null, cityId = null;
				List<Region> regionList = (List<Region>) obj;
				for (int i = 0; i < regionList.size(); i++) {
					if (i == 1) {
						provinceId = regionList.get(i).getRegionId();
						continue;
					}
					if (i == 2) {
						cityId = regionList.get(i).getRegionId();
						continue;
					}
				}
				if (provinceId != null && cityId != null) {// 省市ID不为空
					return goodsChannelPriceMapper.getGoodsChannelByGoodsId(provinceId, cityId, goodsId);
				}
			}
		}
		return null;
	}

	@Override
	public List<Goods> getGoodsChannelPriceList(Integer salesAreaId, Integer customerNature, Integer ownerShip,
			List<Goods> goodsList) {
		// 销售区域最小级ID不允许为空，，产品列表不允许为空，，客户性质不允许为空
		if (salesAreaId != null && goodsList != null && (!goodsList.isEmpty()) && customerNature != null) {
			Object obj = regionService.getRegion(salesAreaId, 1);
			if (obj != null) {// 销售区域最小级ID对应省市不为空
				Integer provinceId = null, cityId = null;
				List<Region> regionList = (List<Region>) obj;
				for (int i = 0; i < regionList.size(); i++) {
					if (i == 1) {
						provinceId = regionList.get(i).getRegionId();
						continue;
					}
					if (i == 2) {
						cityId = regionList.get(i).getRegionId();
						continue;
					}
				}
				if (provinceId != null) {// 省市ID不为空
					List<Integer> goodsIdList = new ArrayList<>();
					for (int k = 0; k < goodsList.size(); k++) {
						goodsIdList.add(goodsList.get(k).getGoodsId());
					
					if (goodsIdList != null && !goodsIdList.isEmpty()) {// 对应的产品ID不为空
						// provinceId省ID,cityId市ID,customerNature客户性质,list产品ID
						Long nowTime = DateUtil.gainNowDate();
						Integer type = 1;
						List<GoodsChannelPrice> channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(
								provinceId, cityId, customerNature, ownerShip, goodsIdList, nowTime, type);
						// 如果没有省和市的 渠道价格 就去省的渠道价格
						if (channelPriceList.isEmpty()) {
							channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(provinceId, null,
									customerNature, ownerShip, goodsIdList, nowTime, type);
						}
						// 如果没有省和市的 渠道价格 就取全国的渠道价格
						if (channelPriceList.isEmpty()) {
							channelPriceList = goodsChannelPriceMapper.getSaleChannelPriceList(1, null, customerNature,
									ownerShip, goodsIdList, nowTime, type);
						}
						// 核算价格不为空
						if (channelPriceList != null && !channelPriceList.isEmpty()) {
							for (int i = 0; i < goodsList.size(); i++) {
								for (int j = 0; j < channelPriceList.size(); j++) {
									if (goodsList.get(i).getGoodsId().equals(channelPriceList.get(j).getGoodsId())) {
										goodsList.get(i).setChannelPrice(channelPriceList.get(j).getChannelPrice());
									}
								}
							}
						}
					}
					}
				}
			}
		}
		return goodsList;
	}

	@Override
	public List<GoodsChannelPrice> getGoodsChannelPriceList(Goods goods) {
		List<GoodsChannelPrice> list = goodsChannelPriceMapper.getGoodsChannelPriceList(goods);
		return list;
	}

	@Override
	public ResultInfo showPriceList(GoodsChannelPriceExtend goodsChannelPriceExtend) {
		List<GoodsChannelPriceExtend> list = goodsChannelPriceExtendMapper
				.getGoodsChannelPriceExtendList(goodsChannelPriceExtend);
		ResultInfo resultInfo = new ResultInfo<>();
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		resultInfo.setData(list);
		return resultInfo;
	}

	@Override
	public List<GoodsChannelPrice> getGoodsChannelPriceListByChina(List<Integer> goodsIdList, Integer provinceId) {
		return goodsChannelPriceMapper.getGoodsChannelPriceListByChina(goodsIdList,provinceId);
	}

	@Override
	public List<GoodsChannelPrice> getGoodsChannelPriceListAll() {
		return goodsChannelPriceMapper.getGoodsChannelPriceListAll();
	}
	
	@Override
	public GoodsChannelPrice getGoodsChannelPriceByGoodsIdOne(Goods goods) {
		return goodsChannelPriceMapper.getGoodsChannelPriceByGoodsIdOne(goods);	
	}

	/**
	 * 设置产品价格
	 * @param saleorderGoodsList
	 */
	@Override
	public void setGoodsReferenceCostPrice(List<SaleorderGoods> saleorderGoodsList) {

		if(CollectionUtils.isEmpty(saleorderGoodsList)){
			return;
		}

		saleorderGoodsList.stream().forEach(saleorderGoods -> {

			if(saleorderGoods.getReferenceCostPrice() == null || BigDecimal.ZERO.compareTo(saleorderGoods.getReferenceCostPrice()) == 0){
				//获取产品对应核价信息
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("goodsId", saleorderGoods.getGoodsId());
				map.put("type", 1);
				List<GoodsChannelPrice> goodsChannelPriceList = goodsChannelPriceMapper.getGoodsChannelPriceByGoodsId(map);

				//获取核价信息
				goodsChannelPriceList.stream().findFirst().ifPresent(channelPrice -> {

					GoodsChannelPriceExtend goodsChannelPriceExtend  = new GoodsChannelPriceExtend();
					goodsChannelPriceExtend.setGoodsChannelPriceId(channelPrice.getGoodsChannelPriceId());
					goodsChannelPriceExtend.setPriceType(1);

					//获取成本信息
					List<GoodsChannelPriceExtend> extendList = goodsChannelPriceExtendMapper.getGoodsChannelPriceExtendList(goodsChannelPriceExtend);
					extendList.stream().findFirst().ifPresent(extend -> {

						if(extend.getBatchPrice() != null && isValidTime(extend.getStartTime(),extend.getEndTime())){
							saleorderGoods.setReferenceCostPrice(extend.getBatchPrice());
						}

					});

				});
			}

		});

	}

	private boolean isValidTime(Long startTime, Long endTime) {
		long currentTime = DateUtil.getNowDayMillisecond();
		return (currentTime >= startTime && currentTime <= endTime) ? true : false;
	}

	public ResultInfo<?> delGoodsChannelPriceAll(GoodsChannelPrice goodsChannelPrice) {
		ResultInfo<?> result = new ResultInfo<>();
		// 先删除核价子表附属信息
		goodsChannelPriceExtendMapper.deleteGoodsChannelPriceExtendAll(goodsChannelPrice);
		// 删除核价主表
		goodsChannelPriceMapper.delGoodsChannelPriceById(goodsChannelPrice);
		result.setCode(0);
		result.setMessage("操作成功");
		return result;
	}

}
