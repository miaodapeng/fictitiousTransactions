package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.GoodsAttribute;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsExtend;
import com.vedeng.goods.model.GoodsOpt;
import com.vedeng.goods.model.GoodsPackage;
import com.vedeng.goods.model.GoodsRecommend;
import com.vedeng.goods.model.GoodsSafeStock;
import com.vedeng.goods.model.GoodsSettlementPrice;
import com.vedeng.goods.model.GoodsSysOptionAttribute;
import com.vedeng.goods.model.RGoodsJTraderSupplier;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.model.SysOptionDefinition;

/**
 * <b>Description:</b><br> 产品管理功能
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> GoodsService
 * <br><b>Date:</b> 2017年5月19日 下午4:34:43
 */
public interface GoodsService extends BaseService {

	/**
	 * <b>Description:</b><br> 保存添加的产品
	 * @param goods
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:53:35
	 */
	Goods saveAdd(Goods goods, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br> 获取产品列表（分页）
	 * @param request
	 * @param goods
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:53:59
	 */
	Map<String, Object> getGoodsListPage(HttpServletRequest request, Goods goods, Page page,HttpSession session);

	/**
	 * <b>Description:</b><br> 产品是否禁用
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:55:04
	 */
	ResultInfo<?> isDiscardById(Goods goods);

	/**
	 * <b>Description:</b><br> 获取产品信息（根据主键）
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:55:22
	 */
	Goods getGoodsById(Goods goods);

	/**
	 * <b>Description:</b><br> 保存产品基本信息
	 * @param goods
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:55:44
	 */
	Goods saveBaseInfo(Goods goods, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br> 保存产品销售信息
	 * @param goods
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:56:15
	 */
	Goods saveSaleInfo(Goods goods, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br> 获取产品属性列表
	 * @param goodsAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:57:03
	 */
	List<GoodsAttribute> getGoodsAttributeList(GoodsAttribute goodsAttribute);

	/**
	 * <b>Description:</b><br> 获取产品属性值列表
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:57:18
	 */
	List<GoodsSysOptionAttribute> getGoodsSysOptionAttributeList(Goods goods);
	
	/**
	 * <b>Description:</b><br> 获取商品信息基础列表
	 * @param goodsOpt
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:57:40
	 */
	Map<String,Object> getGoodsOptListPage(GoodsOpt goodsOpt,Page page);
	
	/**
	 * <b>Description:</b><br> 保存产品配件
	 * @param goodsPackage
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:58:03
	 */
	ResultInfo<?> saveGoodsPackage(GoodsPackage goodsPackage);
	
	/**
	 * <b>Description:</b><br> 保存产品关联
	 * @param goodsRecommend
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:58:19
	 */
	ResultInfo<?> saveGoodsRecommend(GoodsRecommend goodsRecommend);
	
	/**
	 * <b>Description:</b><br> 删除产品配件
	 * @param goodsPackage
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午4:59:54
	 */
	ResultInfo<?> delGoodsPackageById(GoodsPackage goodsPackage);
	
	/**
	 * <b>Description:</b><br> 删除产品关联
	 * @param goodsRecommend
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:00:08
	 */
	ResultInfo<?> delGoodsRecommendById(GoodsRecommend goodsRecommend);
	
	/**
	 * <b>Description:</b><br> 获取产品配件列表（不分页）
	 * @param goodsOpt
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:00:21
	 */
	List<GoodsOpt> getGoodsPackageList(GoodsOpt goodsOpt);

	/**
	 * <b>Description:</b><br> 获取产品附件列表（根据产品ID）
	 * @param goodsAttachment
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:00:38
	 */
	List<GoodsAttachment> getGoodsAttachmentListByGoodsId(GoodsAttachment goodsAttachment);

	/**
	 * <b>Description:</b><br> 获取产品关联列表（不分页）
	 * @param goodsOpt
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:01:01
	 */
	List<GoodsOpt> getGoodsRecommendList(GoodsOpt goodsOpt);
	
	/**
	 * <b>Description:</b><br> 获取产品单元列表（不分页）
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:01:28
	 */
	List<Goods> getGoodsUnitList(Goods goods);
	
	/**
	 * <b>Description:</b><br> 更新产品税收分类编码
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:01:44
	 */
	ResultInfo<?> updateGoodsTaxCategoryNo(List<Goods> list);
	
	/**
	 * <b>Description:</b><br> 批量添加时验证产品名称是否重复
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:02:05
	 */
	List<String> batchSaveVailGoodsName(List<Goods> list);
	
	/**
	 * <b>Description:</b><br> 批量更新时验证产品名称是否重复
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:02:31
	 */
	List<String> batchEditVailGoodsName(List<Goods> list);
	
	/**
	 * <b>Description:</b><br> 批量保存产品
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:02:54
	 */
	ResultInfo<?> batchSaveGoods(List<Goods> list);
	
	/**
	 * <b>Description:</b><br> 批量更新产品
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:03:11
	 */
	ResultInfo<?> batchUpdateGoodsSave(List<Goods> list);

	/**
	 * <b>Description:</b><br> 验证产品名称是否重复
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:03:27
	 */
	ResultInfo<?> validGoodName(Goods goods);
	
	/**
	 * <b>Description:</b><br> 产品列表搜索结果（简单版）如添加配件，关联产品时
	 * @param goods
	 * @param page
	 * @param session 
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月3日 下午5:03:41
	 */
	Map<String, Object> queryGoodsListPage(Goods goods,Page page, HttpSession session);
	/**
	 * <b>Description:</b><br> 库存统计列表
	 * @param goods
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年9月13日 下午5:03:41
	 */
	Map<String, Object> getlistGoodsStockPage(HttpServletRequest request, Goods goods, Page page);

	/**
	 * <b>Description:</b><br> 批量操作产品
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年10月9日 下午4:53:16
	 */
	ResultInfo<?> batchOptGoods(Goods goods);
    /**
     * 
     * <b>Description:</b><br> 在途列表
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月9日 下午1:20:05
     */
	List<BuyorderVo> getBuyorderVoList(Goods goods);

	/**
	 * <b>Description:</b><br> 产品管理类别批量分配列表
	 * @param optionDefinitions
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 上午11:41:32
	 */
	List<SysOptionDefinition> getAssignList(List<SysOptionDefinition> optionDefinitions,HttpSession session);
	
	/**
	 * <b>Description:</b><br> 查询产品分类归属
	 * @param manageCategory
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 下午2:43:43
	 */
	List<User> getUserByManageCategory(Integer manageCategory,Integer companyId);

	/**
	 * <b>Description:</b><br> 保存产品分类归属
	 * @param userId
	 * @param manageCategories
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 下午3:33:08
	 */
	Boolean saveEditCategoryOwner(List<Integer> userId, String manageCategories, HttpSession session);

	/**
	 * <b>Description:</b><br> 批量设置产品安全库存
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月27日 下午3:26:08
	 */
	ResultInfo<?> saveUplodeGoodsSafeSotck(List<GoodsSafeStock> list);

	/**
	 * <b>Description:</b><br> 批量保存核价上次信息
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月4日 下午6:37:25
	 */
	ResultInfo<?> batchGoodsPriceSave(List<GoodsChannelPrice> list) throws Exception;

	/**
	 * <b>Description:</b><br> 验证sku是否存在产品表
	 * @param sku_list
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月4日 下午6:49:18
	 */
	List<Goods> batchVailGoodsSku(List<String> sku_list);

	/**
	 * <b>Description:</b><br> 批量更新产品批量修改结算核价
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月7日 上午11:47:17
	 */
	ResultInfo<?> batchGoodsSettelmentSave(List<GoodsSettlementPrice> list);
	
	/**
	 * <b>Description:</b><br> 获取供应商供应的产品ID集合
	 * @param traderSupplierId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月22日 上午10:44:50
	 */
	public List<Integer> getSupplierGoodsIds(Integer traderSupplierId);

	/**
	 * <b>Description:</b><br> 保存添加主要供应商
	 * @param rGoodsJTraderSupplier
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月22日 下午5:40:20
	 */
	ResultInfo<?> saveMainSupply(RGoodsJTraderSupplier rGoodsJTraderSupplier);

	/**
	 * <b>Description:</b><br> 删除主供应商
	 * @param rGoodsJTraderSupplier
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月23日 上午10:22:44
	 */
	ResultInfo<?> delMainSupply(RGoodsJTraderSupplier rGoodsJTraderSupplier);

	/**
	 * <b>Description:</b><br> 产品基本信息搜索
	 * @param goods
	 * @param page
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午1:53:20
	 */
	Map<String, Object> getGoodsBaseinfoListPage(Goods goods,Page page, HttpSession session);

	/**
	 * <b>Description:</b><br> 产品重置为待审核
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年1月23日 下午3:55:09
	 */
	ResultInfo restVerify(Goods goods);

	/**
	 * <b>Description:</b><br> ajax补充产品列表相关信息
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月10日 下午7:01:54
	 */
	ResultInfo<?> getGoodsListExtraInfo(Goods goods);
    /**
     * 
     * <b>Description:</b><br> 获取产品扩展信息
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月13日 下午3:18:25
     */
	GoodsExtend getGoodsExtend(Goods goods);
    /**
     * 
     * <b>Description:</b><br>  保存编辑商品工具
     * @param map
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月14日 下午5:11:11
     */
	ResultInfo saveCommodityPropaganda(Map<String, Object> map);
    /**
     * 
     * <b>Description:</b><br> 批量保存商品物流运输
     * @param list
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月20日 下午3:25:02
     */
	ResultInfo<?> batchSaveGoodsExtend(List<GoodsExtend> list);
     /**
      * 
      * <b>Description:</b><br> 产品复制功能（从总部复制到分公司）
      * @param goods
      * @return
      * @Note
      * <b>Author:</b> Michael
      * <br><b>Date:</b> 2018年5月16日 下午3:29:03
      */
     ResultInfo<?> copyGoods(Goods goods);
     
     /**
     * <b>Description:</b><br> 获取销售聚合页产品详情
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年6月25日 上午9:56:02
     */
    GoodsVo getSaleJHGoodsDetail(Goods goods);
    
    /**
     * <b>Description:</b><br> 保存产品的常见问题
     * @param goodsVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年6月25日 下午5:29:53
     */
    ResultInfo<?> saveGoodsVoFaqs(GoodsVo goodsVo);
    
    /**
     * <b>Description:</b><br> 根据产品id查询常见问题
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年6月26日 上午10:43:57
     */
    ResultInfo<?> getGoodsVoFaqs(Goods goods);

    /** 根据商品Id修改商品信息
     * @param goods
     * @return
     */
	ResultInfo<?> updateGoodsInfoById(Goods goods);

	/**
	* @Title: getGoodsIdBySku
	* @Description: TODO(根据sku查询goodsID)
	* @param @param skuNo
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月17日
	*/
	Integer getGoodsIdBySku(String skuNo);

	List<SaleorderGoods> getGoodsPriceList(Integer saleorderId);
}
