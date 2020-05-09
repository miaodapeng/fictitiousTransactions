package com.vedeng.goods.dao;



import java.util.List;
import java.util.Map;

import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.model.Goods;
import com.vedeng.authorization.model.User;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import org.apache.ibatis.annotations.Param;

import com.vedeng.order.model.SaleorderGoods;



/**
* @ClassName: GoodsMapper
* @Description: TODO(商品管理)
* @author strange
* @date 2019年7月18日
*
*/
public interface GoodsMapper {

    /**
     * <b>Description:</b>根据sku集合查询商品所属的科室<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> ${date} ${time}
     */
	List<String> getGoodsMedicalOffice(List<String> skus);
	/**
	* @Title: getGoodsIdBySku
	* @Description: TODO(根据sku查询goodsID)
	* @param @param skuNo
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月18日
	*/
	Integer getGoodsIdBySku(@Param("skuNo") String skuNo);

	/**
	* @Title: getGoodsPriceList
	* @Description: TODO(根据订单id获取商品sku表信息)
	* @param @param saleorderId
	* @param @return    参数
	* @return List<SaleorderGoods>    返回类型
	* @author strange
	* @throws
	* @date 2019年7月24日
	*/
	List<SaleorderGoods> getGoodsPriceList(Integer saleorderId);

	/**
	* @Title: getGoodsInfoById
	* @Description: TODO(id获取商品信息)
	* @param @param goodsId
	* @return Goods    返回类型
	* @author strange
	* @throws
	* @date 2019年8月26日
	*/
	SaleorderGoods getGoodsInfoById(Integer goodsId);

	/**
	*商品id获取到sku
	* @Author:strange
	* @Date:17:57 2019-11-13
	*/
	String getSkuByGoodsId(Integer goodsId);

	/**
	 * <b>Description:</b> 查询商品扩展信息
	 * @param goodsId
	 * @return Goods
	 * @Note
	 * <b>Author：</b> duke.li
	 * <b>Date:</b> 2018年11月12日 上午9:17:41
	 */
	Goods selectGoodsExtendInfo(@Param("sku")String sku);

	/**
	 * <b>Description:</b>验证商品存在归属产品经理<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/1/13
	 */
	Integer getAssignManageUserCountByGoods(@Param("goodsId")Integer goodsId);
    /**
     * <b>Description:</b>根据商品id集合获取归属人id集合<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/12/23
     */
	List<Integer> getAssignUserIdsByGoods(List<Integer> goodsIds);

	/**
	 * <b>Description:</b>根据商品id集合获取归属经理名称集合<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/26
	 */
	List<User> getAssignManageUserByGoods(List<Integer> goodsIds);
	/**
	*获取商品产品类型
	* @Author:strange
	* @Date:16:30 2020-03-12
	*/
    Integer getGoodsSpuType(Integer goodsId);

	/**
	 * 获取弹窗的sku信息
	 * @param sku
	 * @return
	 */
	Map<String, Object> getGoodsInfoTips(Integer sku);

	List<Map<String, Object>> skuTipList(List<Integer> skuIds);

	List<CoreSpuGenerate> findSpuNamesBySpuIds(List<Integer> goodsIds);

	/**
	 * 直接通过入库条码找到采购订单
	 * @param optType
	 * @param orderDetailId
	 * @return
	 */
    List<WarehouseGoodsOperateLogVo> getBuyPricesOrderId(int optType,@Param("orderDetailId") Integer orderDetailId);

	//List<Integer> getOrderDetailIdsByRelatedIds(List<Integer> relatedIds);

	/**
	 *  直发的订单能够直接关联到采购单
	 * @param orderDetailId
	 * @return
	 */
	List<WarehouseGoodsOperateLogVo> getBuyPricesDerictOrderDetailId(Integer orderDetailId);

	/**
	 *
	 * @param orderDetailId
	 * @return
	 */
	List<WarehouseGoodsOperateLogVo> getBuyPricesNoDirectDetailId(Integer orderDetailId);

	/**
	 * 通过采购售后换货退货入库找价格
	 * @param orderDetailId
	 * @return
	 */
	List<WarehouseGoodsOperateLogVo> getBuyPriceByBuyorderAftersale(Integer orderDetailId);
	/**
	 * 通过销售售后条码找售后入库的订单goodid
	 * @param orderDetailId
	 * @return
	 */
	List<Integer> getAfterSaleOrderDetailId(Integer orderDetailId);
}
