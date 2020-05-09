package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsChannelPriceExtend;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.SaleorderGoods;

/**
 * <b>Description:</b><br> 产品核价接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> GoodsChannelPriceService
 * <br><b>Date:</b> 2017年12月19日 下午7:02:46
 */
public interface GoodsChannelPriceService {

	/**
	 * <b>Description:</b><br> 销售订单产品核价查询
	 * @param salesAreaId
	 * @param customerNature
	 * @param saleGoodsList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月19日 下午6:59:33
	 */
	List<SaleorderGoods> getSaleChannelPriceList(Integer salesAreaId, Integer customerNature,Integer ownerShip,List<SaleorderGoods> saleGoodsList);
	
	/**
	 * <b>Description:</b><br> 报价订单产品核价查询
	 * @param salesAreaId
	 * @param customerNature
	 * @param saleGoodsList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月19日 下午6:59:46
	 */
	List<QuoteorderGoods> getQuoteChannelPriceList(Integer salesAreaId, Integer customerNature,Integer ownerShip,List<QuoteorderGoods> quoteGoodsList);
	
	List<Goods> getGoodsChannelPriceList(Integer salesAreaId, Integer customerNature,Integer ownerShip,List<Goods> goodsList);
	
	/**
	 * <b>Description:</b><br> 查询指定产品的核价信息
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月4日 下午3:40:26
	 */
	GoodsChannelPrice getGoodsChannelByGoodsId(Integer salesAreaId, Integer customerNature,Integer goodsId);

	/**
	 * <b>Description:</b><br> 查询某个产品的核价信息
	 * @param goodsId
	 * @param type 
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月22日 下午2:52:39
	 */
	List<GoodsChannelPrice> getGoodsChannelPriceByGoodsId(Map map);

	/**
	 * <b>Description:</b><br> 重置产品核价信息
	 * @param goodsChannelPrice
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月23日 上午11:13:39
	 */
	ResultInfo<?> delGoodsChannelPrice(GoodsChannelPrice goodsChannelPrice);
	
	/**
	 * 
	 * <b>Description:</b>删除核价信息
	 * @param goodsChannelPrice
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 下午2:17:21
	 */
	ResultInfo<?> delGoodsChannelPriceAll(GoodsChannelPrice goodsChannelPrice);
    /**
     * 
     * <b>Description:</b><br> 商品授权与定价
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月16日 下午2:48:15
     */
	List<GoodsChannelPrice> getGoodsChannelPriceList(Goods goods);
    /**
     * 
     * <b>Description:</b><br> 产品核价附属信息
     * @param goodsChannelPriceExtend
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月16日 下午5:26:35
     */
	ResultInfo<?> showPriceList(GoodsChannelPriceExtend goodsChannelPriceExtend);
	
	/**
	 * 根据商品Id查询地区为中国的经销价
	 */
	List<GoodsChannelPrice> getGoodsChannelPriceListByChina(List<Integer> goodsIdList,Integer provinceId);
	
	/**
	 * 
	 * <b>Description:</b>获取已核价产品信息列表
	 * @return List<GoodsChannelPrice>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 上午10:31:12
	 */
	List<GoodsChannelPrice> getGoodsChannelPriceListAll();
	
	/**
	 * 
	 * <b>Description:</b>查询产品的核价信息
	 * @param goods
	 * @return GoodsChannelPrice
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 下午1:05:12
	 */
	GoodsChannelPrice getGoodsChannelPriceByGoodsIdOne(Goods goods);

	/**
	 * 设置产品的成本价
	 * @param saleorderGoodsList
	 */
    void setGoodsReferenceCostPrice(List<SaleorderGoods> saleorderGoodsList);
}
