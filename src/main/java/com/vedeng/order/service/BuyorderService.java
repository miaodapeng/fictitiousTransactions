package com.vedeng.order.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.PayApply;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.BuyorderModifyApply;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderModifyApplyGoodsVo;
import com.vedeng.order.model.vo.BuyorderModifyApplyVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.model.vo.AddressVo;

/**
 * <b>Description:</b><br> 采购订单service
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.order.service
 * <br><b>ClassName:</b> BuyOrderService
 * <br><b>Date:</b> 2017年7月11日 上午9:14:17
 */
public interface BuyorderService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 查询采购列表分页信息
	 * @param buyorderVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月11日 上午9:25:35
	 */
	Map<String, Object> getBuyorderVoPage(BuyorderVo buyorderVo,Page page);
	
	/**
	 * <b>Description:</b><br> 获取新增页采购订单的详情
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	BuyorderVo getAddBuyorderVoDetail(Buyorder buyorder,User user);
	
	/**
	 * <b>Description:</b><br> 获取采购订单的详情
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	BuyorderVo getBuyorderVoDetail(Buyorder buyorder,User user);
	/**
	 * <b>Description:</b><br> 获取采购订单的详情
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	BuyorderVo getBuyorderVoDetailNew(Buyorder buyorder,User user);
	/**
	 * 通过ajax后补数据
	 * <b>Description:</b><br> 
	 * @param buyorder
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年7月6日 上午8:58:04
	 */
	BuyorderVo getSaleBuyNumByAjax(Buyorder buyorder,User user);
	
	/**
	 * <b>Description:</b><br> 获取采购订单的详情（申请修改时用到）
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	BuyorderVo getBuyorderVoDetail(Buyorder buyorder);
	
	/**
	 * <b>Description:</b><br> 获取采购订单的详情异步
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	BuyorderVo getBuyorderVoDetailByAjax(Buyorder buyorder,User user);
	
	/**
	 * <b>Description:</b><br> 获取采购订单的详情异步
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	List<BuyorderGoodsVo> getBuyorderGoodsVoListByAjax(BuyorderVo buyorder,User user);
	
	/**
	 * <b>Description:</b><br> 获取出入库记录
	 * @param buyorderVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月1日 上午9:14:55
	 */
	List<WarehouseGoodsOperateLogVo> getWarehouseGoodsOperateLogVoListPage(BuyorderVo buyorderVo);
	
	/**
	 * <b>Description:</b><br> 获取采购详情中的产品信息
	 * @param buyorderVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月1日 上午9:14:55
	 */
	BuyorderVo getBuyorderGoodsVoListPage(BuyorderVo buyorderVo);
	
	/**
	 * <b>Description:</b><br> 查询待采购列表
	 * @param saleorderGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月13日 下午4:51:06
	 */
	Map<String, Object> getSaleorderGoodsVoList(GoodsVo goodsVo);
	
	/**
	 * <b>Description:</b><br> 查询待采购列表分页
	 * @param saleorderGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月13日 下午4:51:06
	 */
	Map<String, Object> getSaleorderGoodsVoListPage(GoodsVo goodsVo,Page page);
	
	/**
	 * <b>Description:</b><br> 忽略待采购订单
	 * @param saleorderGoodsIds
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月21日 下午5:23:38
	 */
	ResultInfo saveIgnore(String saleorderGoodsIds,User user);
	

	
	/**
	 * <b>Description:</b><br> 获取加入采购订单页面的列表信息
	 * @param buyorderVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月8日 上午11:21:00
	 */
	Map<String, Object> getGoodsVoList(BuyorderVo buyorderVo);
	
	/**
	 * <b>Description:</b><br> 保存或更新
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月11日 上午9:27:14
	 */
	ResultInfo<?> saveOrUpdateBuyorderVo(BuyorderVo buyorderVo , User user);
	
	/**
	 * <b>Description:</b><br> 更新采购订单
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月11日 上午9:27:14
	 */
	ResultInfo<?> saveEditBuyorderAndBuyorderGoods(Buyorder buyorder ,HttpServletRequest request);
	
	/**
	 * <b>Description:</b><br> 加入已存在采购订单
	 * @param buyorder
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月9日 上午10:34:46
	 */
	ResultInfo<?> saveAddHavedBuyorder(Map<String, Object> map);
	
	/**
	 * <b>Description:</b><br> 查询已忽略订单列表分页信息
	 * @param saleorderGoodsVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月11日 上午9:25:35
	 */
	Map<String, Object> getIgnoreSaleorderPage(SaleorderGoodsVo saleorderGoodsVo,Page page);
	
	/**
	 * <b>Description:</b><br> 获取普发的收货地址列表
	 * @param paramsConfigValue
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午6:22:31
	 */
	List<AddressVo> getAddressVoList(ParamsConfigValue paramsConfigValue);

	/**
	 * <b>Description:</b><br> 保存付款申请
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年8月31日 上午10:46:43
	 */
	ResultInfo<?> saveApplyPayment(PayApply payApply); 
	
	/**
	 * <b>Description:</b><br> 申请审核
	 * @param buyorder
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年9月7日 上午9:31:36
	 */
	ResultInfo<?> saveApplyReview(Buyorder buyorder);
	
	/**
	 * <b>Description:</b><br> 关闭采购订单
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年9月7日 上午9:38:08
	 */
	ResultInfo<?> saveCloseBuyorder(Buyorder buyorder);

	/**
	 * <b>Description:</b><br> 根据采购订单的id获取销售订单产品的列表
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月19日 下午2:18:56
	 */
	BuyorderVo getBuyorderGoodsVoList(Buyorder buyorder);
	/**
	 * 保存采购单主表信息
	 */
	ResultInfo<?> saveBuyorder(Buyorder buyorder);

	/**
	 * <b>Description:</b><br> 获取备货 计划产品列表
	 * @param goodsVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午5:32:04	
	 */
	Map<String, Object> getBHManageList(GoodsVo goodsVo, Page page);

	/**
	 * <b>Description:</b><br> 批量添加备货产品
	 * @param list
	 * @param bhSaleorder
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月23日 下午3:35:22
	 */
	ResultInfo batchAddBhSaleorderGoods(List<Integer> list, Saleorder bhSaleorder, HttpSession session);
    /**
     * 
     * <b>Description:</b><br> 打印采购单
     * @param buyorder
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月14日 上午10:52:53
     */
	BuyorderVo getBuyOrderPrintInfo(Buyorder buyorder);
	 /**
     * 
     * <b>Description:</b><br> 根据销售订单产品ID集合获取采购归属人id
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年12月14日 上午10:52:53
     */
	List<Integer> getBuyorderUserBySaleorderGoodsIds(List<Integer> saleorderGoodsIds);

	/**
	 * <b>Description:</b><br> 备货计划管理分析
	 * @param goodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年2月10日 下午2:01:09
	 */
	Map<String, Object> getBHManageStat(GoodsVo goodsVo);
	/**
	 * 
	 * <b>Description:</b><br> 获取采购入库采购订单的详情
	 * @param buyorder
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年2月11日 下午1:19:08
	 */
	BuyorderVo getBuyorderInDetail(BuyorderVo buyorder, User user);

	/**
	 * <b>Description:</b><br> 采购订单确认收货
	 * @param buyorder
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月11日 下午4:13:41
	 */
	Buyorder confirmArrival(Buyorder buyorder, HttpServletRequest request, HttpSession session);
	
	/**
	 * <b>Description:</b><br> 根据采购单查询运费
	 * @param buyorderGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月6日 上午10:14:12
	 */
	BuyorderGoodsVo getFreightByBuyorderId(BuyorderGoodsVo buyorderGoodsVo);
	
	/**
	 * <b>Description:</b><br> 保存采购订单的运费
	 * @param buyorderGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月6日 上午11:01:46
	 */
	ResultInfo<?> saveBuyorderFreight(BuyorderGoodsVo buyorderGoodsVo,User user);
	
	/**
	 * <b>Description:</b><br> 删除采购订单的商品
	 * @param buyorderGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月6日 上午11:01:46
	 */
	ResultInfo<?> saveApplyBuyorderModfiyValidStatus(BuyorderModifyApply buyorderModifyApply);
	
	/**
	 * <b>Description:</b><br> 获取采购订单申请修改的详情
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:13:30
	 */
	BuyorderVo getBuyorderVoApplyUpdateDetail(Buyorder buyorder);
	
	/**
	 * <b>Description:</b><br> 保存采购订单的申请修改
	 * @param buyorderGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月6日 上午11:01:46
	 */
	ResultInfo<?> saveBuyorderApplyUpdate(BuyorderModifyApply buyorderModifyApply,BuyorderModifyApplyGoodsVo buyorderModifyApplyGoodsVo);
	
	/**
	 * <b>Description:</b><br> 采购订单修改列表分页
	 * @param buyorderModifyApplyVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月18日 上午11:13:55
	 */
	Map<String, Object> getBuyorderModifyApplyListPage(BuyorderModifyApplyVo buyorderModifyApplyVo, Page page);
	
	/**
	 * <b>Description:</b><br> 获取详情
	 * @param buyorderModifyApply
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月18日 下午2:34:24
	 */
	BuyorderModifyApplyVo getBuyorderModifyApplyVoDetail(BuyorderModifyApply buyorderModifyApply);
	/**
	 * 
	 * <b>Description:</b><br> 采购订单修改列表
	 * @param buyorderModifyApply
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年7月24日 下午7:27:33
	 */
	List<BuyorderModifyApply> getBuyorderModifyApplyList(BuyorderModifyApply buyorderModifyApply);
	/**
	 * 
	 * <b>Description:</b><br> 获取批量生产条码采购单信息信息
	 * @param request
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年7月26日 下午2:20:53
	 */
	BuyorderVo getBuyorderBarcodeVoDetail(BuyorderVo buyorderVo, User user);

	List<Integer> getBuyOrderIdsByCurrentOperateUser(Page page, String currentOperateUser);


//	/**
//	* @Title: getBuyorderTypeById
//	* @Description: TODO(采购单id获取采购信息)
//	* @param @param buyorderId
//	* @return BuyorderVo    返回类型
//	* @author strange
//	* @throws
//	* @date 2019年9月2日
//	*/
//	BuyorderVo getBuyorderById(Integer buyorderId);
	
	

}
