package com.vedeng.goods.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsChannelPrice;

@Named("goodsChannelPriceMapper")
public interface GoodsChannelPriceMapper {
    int deleteByPrimaryKey(Integer goodsChannelPriceId);

    int insert(GoodsChannelPrice record);

    int insertSelective(GoodsChannelPrice record);

    GoodsChannelPrice selectByPrimaryKey(Integer goodsChannelPriceId);

    int updateByPrimaryKeySelective(GoodsChannelPrice record);

    int updateByPrimaryKey(GoodsChannelPrice record);
    
    /**
     * <b>Description:</b><br> 获取产品采购价
     * @param goodsId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月23日 下午3:53:11
     */
    GoodsChannelPrice getPurchasePriceByGoodsID(@Param("goodsId")Integer goodsId);
    
    /**
     * <b>Description:</b><br> 批量查询
     * @param goodsIds
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月30日 下午4:57:52
     */
    List<GoodsChannelPrice> getPurchasePrice(@Param("goodsIds")List<Integer> goodsIds);

    /**
     * <b>Description:</b><br> 验证核价信息是否存在，存在-更新，不存在-新增；；sku与省市id组合唯一
     * @param list
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年12月5日 上午11:35:12
     */
	int batchGoodsPriceSave(@Param("goodsList")List<GoodsChannelPrice> goodsList);
	
	List<GoodsChannelPrice> getSaleChannelPriceList(@Param("provinceId")Integer provinceId, @Param("cityId")Integer cityId, @Param("customerNature")Integer customerNature,@Param("ownerShip")Integer ownerShip,@Param("goodsIdList")List<Integer> goodsIdList, @Param("nowTime")Long nowTime, @Param("type")Integer type);

	/**
	 * <b>Description:</b><br> 查询单个产品的核价信息
	 * @param goodsId
	 * @param type 
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月22日 下午2:57:33
	 */
	List<GoodsChannelPrice> getGoodsChannelPriceByGoodsId(Map map);

	/**
	 * <b>Description:</b><br> 重置产品核价信息
	 * @param goodsChannelPrice
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月23日 上午11:17:29
	 */
	int delGoodsChannelPrice(GoodsChannelPrice goodsChannelPrice);

	/**
	 * <b>Description:</b><br> 查询指定产品的核价信息
	 * @param salesAreaId
	 * @param customerNature
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月4日 下午3:41:52
	 */
	GoodsChannelPrice getGoodsChannelByGoodsId(@Param("provinceId")Integer provinceId, @Param("cityId")Integer cityId, @Param("goodsId")Integer goodsId);
    /**
     * 
     * <b>Description:</b><br> 商品授权与定价信息
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月16日 下午2:56:16
     */
	List<GoodsChannelPrice> getGoodsChannelPriceList(Goods goods);
    /**
     * 
     * <b>Description:</b><br> 删除商品核价信息
     * @param goodsChannelPrice
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月21日 下午1:57:36
     */
	int deleteGoodsPrice(GoodsChannelPrice goodsChannelPrice);
	
	/**
	 * 
	 * <b>Description:</b>删除商品核价信息
	 * @param goodsChannelPrice
	 * @return int
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 下午2:51:57
	 */
	int delGoodsChannelPriceById(GoodsChannelPrice goodsChannelPrice);

    /**
     * 
     * <b>Description:</b><br> 查询要更新的核价信息
     * @param goodsChannelPrice
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月21日 下午6:00:03
     */
	List<GoodsChannelPrice> getGoodsChannelByG(GoodsChannelPrice goodsChannelPrice);
    /**
     * 
     * <b>Description:</b><br> 根据sku+省市id查询核价采购端信息
     * @param goodsChannelPrice
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年4月12日 上午9:00:40
     */
	GoodsChannelPrice getGoodsChannelPriceInfo(GoodsChannelPrice goodsChannelPrice);

	/**
	 * 根据商品Id查询地区为中国的经销价
	 * @param goodsIdList
	 * @param provinceId
	 * @return
	 */
	List<GoodsChannelPrice> getGoodsChannelPriceListByChina(@Param("goodsIdList")List<Integer> goodsIdList, @Param("provinceId")Integer provinceId);
	
	/**
	 * 
	 * <b>Description:</b>查询产品的核价信息
	 * @param goods
	 * @return GoodsChannelPrice
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 下午12:59:52
	 */
	GoodsChannelPrice getGoodsChannelPriceByGoodsIdOne(Goods goods);

	/**
	 * 
	 * <b>Description:</b>所有已核价的产品
	 * @return List<GoodsChannelPrice>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年1月22日 下午12:59:24
	 */
	List<GoodsChannelPrice> getGoodsChannelPriceListAll();
}