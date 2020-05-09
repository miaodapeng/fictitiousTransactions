package com.vedeng.ordergoods.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.ordergoods.model.OrdergoodsCategory;
import com.vedeng.ordergoods.model.OrdergoodsLaunch;
import com.vedeng.ordergoods.model.OrdergoodsLaunchGoal;
import com.vedeng.ordergoods.model.OrdergoodsStore;
import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.ordergoods.model.ROrdergoodsGoodsJCategory;
import com.vedeng.ordergoods.model.ROrdergoodsLaunchGoodsJCategory;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsAccountVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsLaunchVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsStoreAccountVo;
import com.vedeng.ordergoods.model.vo.ROrdergoodsLaunchGoodsJCategoryVo;
import com.vedeng.trader.model.vo.TraderVo;

/**
 * <b>Description:</b><br> 订货系统service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.ordergoods.service
 * <br><b>ClassName:</b> OrdergoodsService
 * <br><b>Date:</b> 2017年11月20日 下午2:31:56
 */
/**
 * <b>Description:</b><br> 
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.ordergoods.service
 * <br><b>ClassName:</b> OrdergoodsService
 * <br><b>Date:</b> 2018年1月5日 上午11:18:51
 */
public interface OrdergoodsService extends BaseService {

	/**
	 * <b>Description:</b><br> 获取店铺
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午5:49:03
	 */
	List<OrdergoodsStore> getStore(Integer companyId);

	/**
	 * <b>Description:</b><br> 根据公司名称,公司ID获取店铺列表
	 * @param ordergoodsStore
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午9:21:06
	 */
	OrdergoodsStore getStoreByName(OrdergoodsStore ordergoodsStore);

	/**
	 * <b>Description:</b><br> 新增店铺
	 * @param ordergoodsStore
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午9:23:43
	 */
	Integer saveAddStore(OrdergoodsStore ordergoodsStore, HttpSession session);

	/**
	 * <b>Description:</b><br> 根据ID查询店铺信息
	 * @param ordergoodsStore
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午9:29:59
	 */
	OrdergoodsStore getStoreById(OrdergoodsStore ordergoodsStore);

	/**
	 * <b>Description:</b><br> 保存编辑店铺
	 * @param ordergoodsStore
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午9:34:10
	 */
	Integer saveeditStore(OrdergoodsStore ordergoodsStore, HttpSession session);

	/**
	 * <b>Description:</b><br> 更改店铺状态
	 * @param ordergoodsStore
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午10:10:17
	 */
	Boolean changeEnable(OrdergoodsStore ordergoodsStore, HttpSession session);

	/**
	 * <b>Description:</b><br> 获取店铺产品分类
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 上午10:45:04
	 */
	List<OrdergoodsCategory> getOrdergoodsCategory(Integer parentId, Integer ordergoodsStoreId,Boolean joinChar);

	/**
	 * <b>Description:</b><br> 新增店铺分类
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 上午11:46:27
	 */
	Integer saveAddCategory(OrdergoodsCategory ordergoodsCategory);

	/**
	 * <b>Description:</b><br> 获取分类信息
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午1:54:02
	 */
	OrdergoodsCategory getOrdergoodsCategoryById(OrdergoodsCategory ordergoodsCategory);
	
	/**
	 * <b>Description:</b><br> 获取分类信息
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午2:09:09
	 */
	OrdergoodsCategory getOrdergoodsCategoryByCate(OrdergoodsCategory ordergoodsCategory);

	/**
	 * <b>Description:</b><br> 编辑店铺分类
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午2:17:34
	 */
	Integer saveEditCategory(OrdergoodsCategory ordergoodsCategory,Boolean update,Integer level);

	/**
	 * <b>Description:</b><br> 分页搜索产品
	 * @param ordergoodsGoods
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午3:33:13
	 */
	Map<String, Object> getGoodsListPage(OrdergoodsGoodsVo ordergoodsGoodsVo, Page page);

	/**
	 * <b>Description:</b><br> 保存产品
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午5:05:06
	 */
	ResultInfo saveUplodeBatchGoods(List<OrdergoodsGoodsVo> list, HttpSession session);

	/**
	 * <b>Description:</b><br> 删除订货产品
	 * @param ordergoodsGoods
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 上午9:47:55
	 */
	Boolean deleteOrdergoodsGoods(OrdergoodsGoodsVo ordergoodsGoodsVo);

	/**
	 * <b>Description:</b><br> 删除分类产品
	 * @param ordergoodsGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 上午11:03:40
	 */
	Boolean deleteOrdergoodsCategoryGoods(OrdergoodsGoodsVo ordergoodsGoodsVo);

	/**
	 * <b>Description:</b><br> 删除分类产品
	 * @param ordergoodsCategoryId
	 * @param ids
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午1:35:43
	 */
	Boolean saveBindCategoryGoods(Integer ordergoodsCategoryId, String ids);

	/**
	 * <b>Description:</b><br> 更改排序
	 * @param rOrdergoodsGoodsJCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午2:08:11
	 */
	Boolean changSort(ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory);

	/**
	 * <b>Description:</b><br> 设备与试剂分类绑定列表
	 * @param rOrdergoodsLaunchGoodsJCategoryVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午2:40:45
	 */
	Map<String, Object> getGoodsCategoriesListPage(ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo, Page page);

	List<OrdergoodsCategory> getOrdergoodsCategry(Integer ordergoodsStoreId);

	/**
	 * <b>Description:</b><br> 获取店铺产品
	 * @param rOrdergoodsLaunchGoodsJCategoryVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午2:27:13
	 */
	ROrdergoodsLaunchGoodsJCategoryVo getSotreGoods(
			ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo);

	/**
	 * <b>Description:</b><br> 添加设备与试剂分类关系
	 * @param rOrdergoodsLaunchGoodsJCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午2:50:35
	 */
	Integer saveAddEquipmentCategory(ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory);

	/**
	 * <b>Description:</b><br> 获取设备分类信息
	 * @param rOrdergoodsLaunchGoodsJCategoryVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午3:03:40
	 */
	ROrdergoodsLaunchGoodsJCategoryVo getSotreGoodsInfo(
			ROrdergoodsLaunchGoodsJCategoryVo rOrdergoodsLaunchGoodsJCategoryVo);

	/**
	 * <b>Description:</b><br> 保存编辑设备与试剂分类关系
	 * @param rOrdergoodsLaunchGoodsJCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午3:20:31
	 */
	Integer saveEditEquipmentCategory(ROrdergoodsLaunchGoodsJCategory rOrdergoodsLaunchGoodsJCategory);

	/**
	 * <b>Description:</b><br> 投放列表
	 * @param ordergoodsLaunchVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午3:49:57
	 */
	Map<String, Object> getOrdergoodsLaunchListPage(OrdergoodsLaunchVo ordergoodsLaunchVo, Page page, HttpSession session);

	/**
	 * <b>Description:</b><br> 分页获取客户基本信息
	 * @param tcv
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 上午9:29:33
	 */
	Map<String, Object> getTraderCustomerListPage(TraderVo traderVo, Page page);

	/**
	 * <b>Description:</b><br> 保存投放
	 * @param ordergoodsLaunch
	 * @param startdate
	 * @param enddate
	 * @param goal
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午1:15:00
	 */
	Integer saveAddOrdergoodsLaunch(OrdergoodsLaunch ordergoodsLaunch, String[] startdate, String[] enddate,
			String[] goal, HttpSession session);

	/**
	 * <b>Description:</b><br> 获取投放主表
	 * @param ordergoodsLaunch
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午2:29:30
	 */
	OrdergoodsLaunchVo getOrdergoodsLaunch(OrdergoodsLaunch ordergoodsLaunch,HttpSession session);

	/**
	 * <b>Description:</b><br> 获取投放目标
	 * @param ordergoodsLaunch
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午2:29:37
	 */
	List<OrdergoodsLaunchGoal> getOrdergoodsLaunchGoal(OrdergoodsLaunch ordergoodsLaunch);

	/**
	 * <b>Description:</b><br> 保存编辑投放
	 * @param ordergoodsLaunch
	 * @param startdate
	 * @param enddate
	 * @param goal
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午3:26:53
	 */
	Boolean saveEditOrdergoodsLaunch(OrdergoodsLaunch ordergoodsLaunch, String[] startdate, String[] enddate,
			String[] goal, HttpSession session);

	/**
	 * <b>Description:</b><br>投放相关的订货订单 
	 * @param ordergoodsLaunchVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月28日 下午4:33:03
	 */
	List<Saleorder> getLaunchSaleorderListPage(OrdergoodsLaunchVo ordergoodsLaunchVo, Page page,HttpSession session);

	/**
	 * <b>Description:</b><br> 月完成情况
	 * @param ordergoodsLaunchVo
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月29日 上午9:42:34
	 */
	OrdergoodsLaunchVo getLaunchMonthGoal(OrdergoodsLaunchVo ordergoodsLaunchVo, HttpSession session);

	/**
	 * <b>Description:</b><br> 店铺账号
	 * @param ordergoodsStoreAccountVo
	 * @param page
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月29日 下午2:12:21
	 */
	Map<String, Object> getOrdergoodsAccountListPage(OrdergoodsStoreAccountVo ordergoodsStoreAccountVo, Page page,
			HttpSession session);

	/**
	 * <b>Description:</b><br> 添加经销商账号
	 * @param ordergoodsStoreAccount
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月4日 上午10:52:37
	 */
	Integer saveAddOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session);

	/**
	 * <b>Description:</b><br> 获取经销商账号信息
	 * @param ordergoodsStoreAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月4日 上午10:57:49
	 */
	Map<String, Object> getOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount);

	/**
	 * <b>Description:</b><br> 获取编辑经销商账号信息
	 * @param ordergoodsStoreAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午10:11:31
	 */
	Map<String, Object> getEditOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount);

	/**
	 * <b>Description:</b><br> 保存编辑经销商账号
	 * @param ordergoodsStoreAccount
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午11:18:54
	 */
	Integer saveEditOrdergoodsAccount(OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session);

	/**
	 * <b>Description:</b><br> 启用/禁用经销商账号
	 * @param ordergoodsStoreAccount
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 上午11:19:06
	 */
	Boolean changeAccountEnable(OrdergoodsStoreAccount ordergoodsStoreAccount, HttpSession session);

	/**
	 * 
	 * <b>Description:</b><br> 获取订货分类产品
	 * @param ordergoodsGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午4:58:02
	 */
	ROrdergoodsGoodsJCategory getOrdergoodsCategoryGoods(OrdergoodsGoodsVo ordergoodsGoodsVo);

	/**
	 * <b>Description:</b><br> 查询账号
	 * @param ordergoodsStoreAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月8日 下午5:48:47
	 */
	OrdergoodsStoreAccount getOrdergoodsStoreAccount(OrdergoodsStoreAccount ordergoodsStoreAccount);

	OrdergoodsStoreAccount getOrdergoodsStoreAccountByPrimaryKey(OrdergoodsStoreAccount ordergoodsStoreAccount);

	/**
	 * <b>Description:</b><br> 分页获取经销商产品价格列表
	 * @param ordergoodsGoodsAccountVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:47:31
	 */
	Map<String, Object> getAccountGoodsListPage(OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo, Page page);

	/**
	 * <b>Description:</b><br> 保存经销商产品价格
	 * @param list
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:48:39
	 */
	ResultInfo<?> saveUplodeBatchAccountGoods(List<OrdergoodsGoodsAccountVo> list, HttpSession session);

	/**
	 * <b>Description:</b><br>  删除经销商产品价格
	 * @param ordergoodsGoodsAccountVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 上午11:49:11
	 */
	Boolean deleteOrdergoodsAccountGoods(OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo);

}
