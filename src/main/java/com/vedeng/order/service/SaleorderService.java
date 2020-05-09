package com.vedeng.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.SaleorderWarehouseComments;
import com.vedeng.order.model.*;
import com.vedeng.order.model.vo.OrderData;
import com.vedeng.order.model.vo.OrderGoodsData;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.WebAccount;

import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 订单管理接口
 * 
 * @author leo.yang
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.service <br>
 *       <b>ClassName:</b> SaleorderService <br>
 *       <b>Date:</b> 2017年7月5日 下午1:42:24
 */
public interface SaleorderService extends BaseService {

	/**
	 * <b>Description:</b>检查订单商品资质是否与客户资质相符<br>
	 * @param saleorderId 订单标识
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/17
	 */
	ResultInfo checkGoodAptitude(Integer saleorderId);
	/**
	 * <b>Description:</b><br>
	 * 订单列表（分页）
	 * 
	 * @param request
	 * @param saleorder
	 * @param page
	 * @param i 0是销售订单,1是线上订单
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月5日 下午1:42:42
	 */
	Map<String, Object> getSaleorderListPage(HttpServletRequest request, Saleorder saleorder, Page page, Integer i);

	/**
	 * <b>Description:</b><br>
	 * 获取沟通记录
	 * 
	 * @param list
	 * @param communicateType
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月5日 下午1:43:00
	 */
	List<CommunicateRecord> getCommunicateRecord(List<Integer> list, String communicateType);

	/**
	 * <b>Description:</b><br>
	 * 获取订单基本信息
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月5日 下午1:55:02
	 */
	Saleorder getBaseSaleorderInfo(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 获取订单基本信息(新)标签页方式
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月20日 下午5:16:18
	 */
	Saleorder getBaseSaleorderInfoNew(Saleorder saleorder);

	/**
	 * 获取订单产品Id列表 <b>Description:</b><br>
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月25日 上午11:18:21
	 */
	List<SaleorderGoods> getSaleorderGoodsByIdNew(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 获取订单产品列表
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月5日 下午3:48:50
	 */
	List<SaleorderGoods> getSaleorderGoodsById(Saleorder saleorder);
	List<SaleorderGoods> getSaleorderGoodsByIdOther(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据产品ID获取结算价格
	 * 
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年12月18日 上午10:49:02
	 */
	BigDecimal getSaleorderGoodsSettlementPrice(Integer goodsId, Integer companyId);

	/**
	 * <b>Description:</b><br>
	 * 编辑保存的订单信息
	 * 
	 * @param saleorder
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 上午10:49:32
	 */
	Saleorder saveEditSaleorderInfo(Saleorder saleorder, HttpServletRequest request, HttpSession session);

	Saleorder saveEditSaleorderInfo(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 保存订单产品信息
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午1:26:58
	 */
	ResultInfo<?> saveSaleorderGoods(SaleorderGoods saleorderGoods);

	/**
	 * <b>Description:</b><br>
	 * 获取订单产品信息（根据订单产品主键ID）
	 * 
	 * @param saleorderGoodsId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午2:35:16
	 */
	SaleorderGoods getSaleorderGoodsInfoById(Integer saleorderGoodsId);

	/**
	 * <b>Description:</b><br>
	 * 删除订单产品
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午5:10:59
	 */
	ResultInfo<?> delSaleorderGoodsById(SaleorderGoods saleorderGoods);

	/**
	 * <b>Description:</b><br>
	 * 关闭订单
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月7日 上午9:41:35
	 */
	ResultInfo<?> closeSaleorder(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 保存订单沟通记录
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月17日 上午11:44:41
	 */
	ResultInfo<?> editSaleorderHaveCommunicate(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 报价转为订单
	 * 
	 * @param quoteorderId
	 * @param user
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月19日 下午5:06:24
	 */
	Saleorder quoteorderToSaleorder(Integer quoteorderId, User user);

	/**
	 * <b>Description:</b><br>
	 * 获取沟通记录ID列表（商机，报价，销售订单）
	 * 
	 * @param searchBegintime
	 * @param searchEndtime
	 * @param string
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月20日 下午3:03:50
	 */
	List<Integer> getCommunicateRecordByDate(Long searchBegintime, Long searchEndtime, String communicateType);

	/**
	 * <b>Description:</b><br>
	 * 订单合同回传保存
	 * 
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午2:58:44
	 */
	ResultInfo<?> saveSaleorderAttachment(Attachment attachment);

	/**
	 * <b>Description:</b><br>
	 * 订单合同回传删除
	 * 
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午2:59:15
	 */
	ResultInfo<?> delSaleorderAttachment(Attachment attachment);

	/**
	 * <b>Description:</b><br>
	 * 获取订单合同回传&订单送货单回传列表
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午5:33:41
	 */
	List<Attachment> getSaleorderAttachmentList(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 获取订单沟通次数（包含商机，报价，销售订单）
	 * 
	 * @param cr
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月25日 下午2:38:53
	 */
	int getSaleorderCommunicateRecordCount(CommunicateRecord cr);

	/**
	 * <b>Description:</b><br>
	 * 订单生效
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月27日 下午2:49:45
	 */
	ResultInfo<?> validSaleorder(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 判断订单是否可以生效
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 上午9:57:27
	 */
	ResultInfo<?> isvalidSaleorder(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 撤销订单生效
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月27日 下午2:50:53
	 */
	ResultInfo<?> noValidSaleorder(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 报价转订单预判断
	 * 
	 * @param quoteorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月31日 下午2:11:40
	 */
	ResultInfo<?> preQuoteorderToSaleorder(Integer quoteorderId);

	/**
	 * <b>Description:</b><br>
	 * 保存新增的备货订单
	 * 
	 * @param saleorder
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月3日 上午11:31:48
	 */
	Saleorder saveAddBhSaleorder(Saleorder saleorder, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 保存备货基本信息
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月4日 下午5:14:37
	 */
	ResultInfo<?> saveEditBhSaleorder(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 通过销售订单NO获取订单基本表信息
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年8月8日 下午1:28:15
	 */
	Saleorder getSaleorderBySaleorderNo(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询销售订单及其下面的商品
	 * 
	 * @param request
	 * @param saleorder
	 * @param page
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年8月9日 下午2:21:43
	 */
	Map<String, Object> getSaleorderGoodsListPage(HttpServletRequest request, Saleorder saleorder, Page page);

	/**
	 * <b>Description:</b><br>
	 * 备货单批量添加产品信息
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月14日 上午10:40:09
	 */
	ResultInfo<?> batchSaveBhSaleorderGoods(List<SaleorderGoods> list, Saleorder saleorder) throws Exception;

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询订单状态
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年8月22日 下午3:59:34
	 */
	Saleorder getSaleorderFlag(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 在售商品列表
	 * 
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年8月22日 下午3:59:34
	 */
	List<SaleorderGoodsVo> getSdList(Goods goods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 列表商品信息
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月12日 上午11:30:51
	 */
	List<SaleorderGoods> getSaleorderGoodsInfo(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 根据销售订单的id获取销售订单产品的列表
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月10日 下午1:00:18
	 */
	SaleorderVo getSaleorderGoodsVoList(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 查询订单保卡信息
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月20日 上午10:47:51
	 */
	List<SaleorderGoodsWarrantyVo> getSaleorderGoodsWarrantys(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 获取销售订单关于资金数据信息
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年10月23日 下午1:57:13
	 */
	Map<String, BigDecimal> getSaleorderDataInfo(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 销售订单全部录保卡/未录保卡的产品
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月20日 下午2:58:32
	 */
	List<SaleorderGoodsWarrantyVo> getAllSaleorderGoodsWarrantys(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 新增保卡查询产品信息
	 * 
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月20日 下午4:22:23
	 */
	SaleorderGoodsWarrantyVo getSaleorderGoodsInfoForWarranty(SaleorderGoodsWarrantyVo goodsWarrantyVo);

	/**
	 * <b>Description:</b><br>
	 * 保存录保卡
	 * 
	 * @param request
	 * @param session
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月23日 下午1:16:10
	 */
	SaleorderGoodsWarrantyVo saveAddGoodsWarranty(HttpServletRequest request, HttpSession session,
			SaleorderGoodsWarrantyVo goodsWarrantyVo);

	/**
	 * <b>Description:</b><br>
	 * 查看录保卡
	 * 
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月23日 下午2:47:49
	 */
	SaleorderGoodsWarrantyVo getGoodsWarrantyInfo(SaleorderGoodsWarrantyVo goodsWarrantyVo);
	SaleorderGoodsWarrantyVo getGoodsWarrantyInfoNew(SaleorderGoodsWarrantyVo goodsWarrantyVo);

	/**
	 * <b>Description:</b><br>
	 * 保存编辑录保卡
	 * 
	 * @param request
	 * @param session
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月24日 下午1:11:25
	 */
	SaleorderGoodsWarrantyVo saveEditGoodsWarranty(HttpServletRequest request, HttpSession session,
			SaleorderGoodsWarrantyVo goodsWarrantyVo);

	/**
	 * <b>Description:</b><br>
	 * 验证销售订单中产品是否重复
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年11月20日 下午5:47:10
	 */
	ResultInfo<?> vailSaleorderGoodsRepeat(SaleorderGoods saleorderGoods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 获取打印合同信息
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月12日 下午2:15:24
	 */
	SaleorderVo getPrintOrderInfo(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 保存新增的销售订单
	 * 
	 * @param saleorder
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月16日 下午3:23:14
	 */
	Saleorder saveAddSaleorderInfo(Saleorder saleorder, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 销售订单确认收货
	 * 
	 * @param saleorder
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月16日 下午2:55:00
	 */
	Saleorder confirmArrival(Saleorder saleorder, HttpServletRequest request, HttpSession session);


	/**
	 * <b>Description:</b>生成新的订单申请修改<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/1/13
	 */
	SaleorderModifyApply convertModifyApply(SaleorderModifyApply saleorderModifyApply, HttpServletRequest request,
										 HttpSession session);
	/**
	 * <b>Description:</b><br>
	 * 销售订单申请修改提交审核
	 * 
	 * @param saleorderModifyApply
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 上午11:25:20
	 */
	SaleorderModifyApply modifyApplySave(SaleorderModifyApply saleorderModifyApply, HttpServletRequest request,
			HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 保存申请提前采购
	 * 
	 * @param saleorder
	 * @param user
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年1月17日 上午9:56:21
	 */
	ResultInfo<?> saveApplyPurchase(Saleorder saleorder, User user);

	/**
	 * <b>Description:</b><br>
	 * 订单修改列表
	 * 
	 * @param request
	 * @param saleorderModifyApply
	 * @param page
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 下午3:51:04
	 */
	Map<String, Object> getSaleorderModifyApplyListPage(HttpServletRequest request, Saleorder saleorderModifyApply,
			Page page);

	/**
	 * <b>Description:</b><br>
	 * 获取订单修改基本信息
	 * 
	 * @param saleorderModifyApply
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 下午4:08:43
	 */
	SaleorderModifyApply getSaleorderModifyApplyInfo(SaleorderModifyApply saleorderModifyApply);

	/**
	 * <b>Description:</b><br>
	 * 获取订单修改产品信息
	 * 
	 * @param saleorderModifyApply
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 下午4:09:07
	 */
	List<SaleorderModifyApplyGoods> getSaleorderModifyApplyGoodsById(SaleorderModifyApply saleorderModifyApply);

	/**
	 * <b>Description:</b><br>
	 * 订单修改信息同步到订单
	 * 
	 * @param saleorder
	 * @param user
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月20日 上午11:41:18
	 */
	ResultInfo<?> saveSaleorderModifyApplyToSaleorder(SaleorderModifyApply saleorderModifyApply);

	/**
	 * <b>Description:</b><br>
	 * 订单修改申请列表（不分页）
	 * 
	 * @param saleorderModifyApply
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月25日 上午10:56:19
	 */
	List<SaleorderModifyApply> getSaleorderModifyApplyList(SaleorderModifyApply saleorderModifyApply);

	/**
	 * <b>Description:</b><br>
	 * ajax补充订单详情中数据
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年2月9日 下午4:16:39
	 */
	ResultInfo<?> getSaleorderGoodsExtraInfo(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 获取同步订单信息
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年3月16日 上午10:53:59
	 */
	SaleorderVo getSaleorderForSync(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 订单消息通知内容
	 * 
	 * @param orderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年3月17日 下午3:02:29
	 */
	SaleorderVo getMessageInfoForSync(Integer orderId);

	/**
	 * <b>Description:</b><br>
	 * 保存销售单批量新增商品
	 * 
	 * @param list
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年4月20日 上午10:34:47
	 */
	ResultInfo<?> saveBatchAddSaleGoods(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询当前销售单下的未出库的商品
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年5月16日 下午3:15:10
	 */
	List<SaleorderGoods> getSaleorderGoodNoOutList(Integer saleorderId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量更新销售订单产品参考成本
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年6月12日 下午4:58:22
	 */
	ResultInfo<?> saveBatchReferenceCostPrice(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量获取
	 * 
	 * @param goodsIds
	 * @param companyId
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年7月19日 下午8:27:38
	 */
	Map<Integer, Object> getSaleorderGoodsSettlementPriceByGoodsIds(List<Integer> goodsIds, Integer companyId);

	/**
	 * <b>Description:</b><br>
	 * 销售订单商品编辑保存
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年8月3日 上午10:42:17
	 */
	ResultInfo<?> updateSaleGoodsSave(SaleorderGoods saleorderGoods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 如果该订单只包含特殊产品，需要将特殊商品的发货状态、到货状态自动刷为已发货、已收货
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年8月17日 上午11:26:18
	 */
	ResultInfo<?> updateSaleGoodsByAllSpecialGoods(Integer saleorderId);

	/**
	 * 获取订单信息
	 * 
	 * @param saleorder
	 * @return
	 */
	Saleorder getSaleOrderInfo(Saleorder saleorder);

	/**
	 * @param sale
	 * @return
	 */
	List<SaleorderGoods> getSaleOrderGoodsList(Saleorder sale);

	/**
	 * 
	 * <b>Description:根据发票ID列表获取订单列表</b>
	 * 
	 * @param invoiceIdList
	 * @return List<Saleorder>
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年12月3日 上午10:15:21
	 */
	List<Saleorder> getSaleorderListByInvoiceIds(List<Integer> invoiceIdList);

	/**
	 * 
	 * <b>Description:</b>查询订单的发货备注
	 * 
	 * @param saleorder
	 * @return List<SaleorderWarehouseComments>
	 * @Note <b>Author：</b> scott.zhu <b>Date:</b> 2019年1月22日 上午11:10:55
	 */
	List<SaleorderWarehouseComments> getListComments(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b>新增或更新销售单出库备注
	 * 
	 * @param saleorderWarehouseComments
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> scott.zhu <b>Date:</b> 2019年1月23日 下午1:41:14
	 */
	ResultInfo<?> updateComments(SaleorderWarehouseComments saleorderWarehouseComments);

	/**
	 * 
	 * <b>Description:</b>查询销售单出库备注
	 * 
	 * @param saleorderWarehouseComments
	 * @return SaleorderWarehouseComments
	 * @Note <b>Author：</b> scott.zhu <b>Date:</b> 2019年1月23日 下午2:34:25
	 */
	SaleorderWarehouseComments getSaleorderWarehouseComments(SaleorderWarehouseComments saleorderWarehouseComments);

	/**
	 * <b>Description:</b><br>
	 * 同步状态订单有售后的状态
	 * 
	 * @param :
	 * @return :
	 * @Note <b>Author:</b> Bert <br>
	 *       <b>Date:</b> 2019/2/1 11:44
	 */
	void synchronousOrderStatus(HttpServletRequest request, Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 同步
订单有售后的状态
	 * 
	 * @param :
	 * @return :
	 * @Note <b>Author:</b> Bert <br>
	 *       <b>Date:</b> 2019/2/1 11:44
	 */
	String updateSalderStatusDelivery(HttpServletRequest request, Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 查询售后退货数量
	 * 
	 * @param goodsId
	 * @param companyId
	 * @param afterType
	 *            535销售 536采购
	 * @return
	 * @Note <b>Author:</b> Bert <br>
	 * 		<b>Date:</b> 2019年01月15日 下午6:47:29
	 */
	public Integer getGoodsAfterReturnNum(SaleorderVo saleorder);

	/**
	 * 根据参数查询订单id集合 <b>Description:</b><br>
	 * 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> bill.bo <br>
	 * 		<b>Date:</b> 2019年2月27日
	 */
	List<Integer> getSaleOrderIdListByParam(Map<String, Object> paraMap);

	/**
	 * 根据订单id查询订单信息 <b>Description:</b><br>
	 * 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> bill.bo <br>
	 * 		<b>Date:</b> 2019年2月27日
	 */
	List<Saleorder> getOrderListInfoById(Map<String, Object> paraMap);

	/**
	 * 
	 * <b>Description:</b>查询销售订单合同回传相关信息
	 * 
	 * @param paraMap
	 * @return List<Saleorder>
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月5日 下午2:02:56
	 */
	Map<String, Object> getContractReturnOrderListPage(SaleorderContract contract, Page page, String searchType);

	/*
	 * * 功能描述: 修改订单税率
	 * 
	 * @param: [orderId, invoiceType]
	 * 
	 * @return: com.vedeng.common.model.ResultInfo
	 * 
	 * @auther: duke.li
	 * 
	 * @date: 2019/3/29 14:45
	 */
	ResultInfo saveOrderRatioEdit(Integer orderId, Integer invoiceType);
    /** 
    * @Description: 查询客户下的耗材订单或直接查询订单 
    * @Param: [saleorder] 
    * @return: java.util.List<com.vedeng.order.model.Saleorder> 
    * @Author: scott.zhu 
    * @Date: 2019/5/14 
    */
    List<Saleorder> getHcOrderList(Saleorder saleorder);
    
	
	/**
	* @Title: saveAddSaleorder
	* @Description: TODO(保存BD订单)
	* @param @param orderData
	* @return List<?>    返回类型
	* @author strange
	* @throws
	* @date 2019年7月16日
	*/
    Saleorder saveBDAddSaleorder(OrderData orderData);

	/**
	* @Title: updateBDSaleStatus
	* @Description: TODO(更新贝登精选BD订单状态)
	* @param @param orderData
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月16日
	*/
	Integer updateBDSaleStatus(OrderData orderData);

	/**
	* @Title: updateBDChangeErp
	* @Description: TODO(BD订单关联erp更新订单信息)
	* @param @param erpAccountId
	* @param @param traderId
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	 * @param saleorderId 
	* @throws
	* @date 2019年7月24日
	*/
	Integer updateBDChangeErp(WebAccount webAccount, Integer traderId, Integer saleorderId);

	/**
	* @Title: getsaleorderbySaleorderId
	* @Description: TODO(订单id获取订单信息)
	* @param @param saleorderId
	* @param @return    参数
	* @return Saleorder    返回类型
	* @author strange
	* @throws
	* @date 2019年7月25日
	*/
	Saleorder getsaleorderbySaleorderId(Integer saleorderId);

	/**
	* @Title: updateVedengJX
	* @Description: TODO(审核通过更新贝登精选的数据)
	* @param @param saleorderId
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	 * @throws Exception 
	* @throws
	* @date 2019年7月25日
	*/
	JSONObject updateVedengJX(Integer saleorderId) throws Exception;

	/**
	* @Title: returnBDStatus
	* @Description: TODO(撤回BD订单状态)
	* @param @param saleorderId
	* @return ResultInfo    返回类型
	* @author strange
	 * @throws
	* @date 2019年7月31日
	*/
	ResultInfo returnBDStatus(Integer saleorderId);

	/**
	* @Title: ChangeEditSaleOrder
	* @Description: TODO(申请修改BD订单更新前端数据)
	* @param @param saleorder
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年8月2日
	*/
	JSONObject ChangeEditSaleOrder(Saleorder saleorder);
	/**
	 * 功能描述: 商品归属
	 * @param: [categoryId, companyId]
	 * @return: java.util.List<com.vedeng.authorization.model.User>
	 * @auther: duke.li
	 * @date: 2019/8/12 10:02
	 */
    List<User> getUserByCategory(Integer categoryId, Integer companyId);


	/**
	 * @Description: 获取订单编号
	 * @Param: [saleorder]
	 * @return: com.vedeng.order.model.Saleorder
	 * @Author: addis
	 * @Date: 2019/8/13
	 */
	List<Saleorder> selectSaleorderNo(Saleorder saleorder);

	/**
	* @Title: closeBDSaleorder
	* @Description: TODO(关闭BD订单)
	* @param @param saleorder
	* @param @return    参数
	* @return ResultInfo<?>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月14日
	*/
	ResultInfo<?> closeBDSaleorder(Saleorder saleorder);

	/**
	 * @Description: 查询订单里是否有该报价存在
	 * @Param: [quoteorderId]
	 * @return: java.lang.Integer
	 * @Author: addis
	 * @Date: 2019/8/30
	 */
	Integer isExistQuoteorderId(Integer quoteorderId);


	/**
	 * <b>Description:</b><br>
	 * 剩余账期未还金额 --- 使用的账期额度减去归还的账期额度减去退还的账期额度
	 * add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月16日 上午10:20:11
	 */
	public BigDecimal getLackAccountPeriodAmount(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 订单账期金额(已收款) --- 已收款账期金额减去退还账期金额
	 * add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月23日 上午10:57:30
	 */
	public BigDecimal getPeriodAmount(Integer saleorderId);


	/**
	 * @Description: 根据订单id查询用户id
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2019/11/8
	 */
	Saleorder getWebAccountId(Integer saleorderId);



	/**
	* @Description: TODO(订单状态，付款状态，发货状态，收货状态，审核状态，生效状态，开票状态
	* ，支付方式（0线上、1线下），支付类型:(1支付宝、2微信、3银行) 接口 )
	* @param @param saleorder
	* @param @return
	* @return Saleorder
	* @author strange
	* @throws
	* @date 2019年10月12日
	*/
	List<Saleorder> getSaleOrderInfoBySaleorderNo(ArrayList<String> orderNoList,Integer type);

	/**
	* @Description: TODO(更改订单状态)
	* @param @param saleorder
	* @param @return
	* @return Integer
	* @author strange
	* @throws
	* @date 2019年10月12日
	*/
	Integer updateOrderDeliveryStatus(Saleorder saleorder);

    /**
     *更新开票申请按钮规则
     * @Author:strange
     * @Date:10:57 2019-11-23
     */
    Saleorder updateisOpenInvoice(Saleorder saleorder,List<SaleorderGoods> saleorderGoodsList);
	/**
	*新在售列表
	* @Author:strange
	* @Date:18:40 2019-11-13
	*/
	List<SaleorderGoodsVo> getNewSdList(Goods goods);

	List<SaleorderGoods> getSaleorderGoods(Saleorder saleorder);
	/**
	*获取订单内未出库商品数
	* @Author:strange
	* @Date:17:13 2019-12-07
	*/
    List<SaleorderGoods> getSaleGoodsNoOutNumList(Integer saleorderId);
	/**
	 *根据订单号取消订单（用于耗材商城订单状态同步）
	 * <b>Description:</b>
	 * @param saleorder
	 * @return ResultInfo
	 * @Note
	 * <b>Author：</b> barry.xu
	 * <b>Date:</b> 2018年11月27日 上午10:05:32
	 */
	ResultInfo updateOrderStatusByOrderNo(Saleorder saleorder);
	/**
	*活动锁定库存列表
	* @Author:strange
	* @Date:15:35 2019-12-11
	*/
    List<SaleorderGoodsVo> getactionLockList(Goods goods);

	/**
	 * @Description: 查询活动出库商品
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2019/12/10
	 */
	Integer queryOutBoundQuantity(SaleorderVo saleorderVo);

	/**
	 * 根据订单号查询订单ID
	 * @param saleorderNo
	 * @return
	 */
	Saleorder getsaleorderId(String saleorderNo);
	/**
	*更新订单直发状态
	* @Author:strange
	* @Date:13:05 2019-12-12
	*/
    void updateOrderdeliveryDirect(Saleorder s);
	//2019-12-20
    List<Integer> getSaleOrderGoodsIdListByUserId(Integer proUserId);
	//根据Sku获得归属人信息
    Spu getSpu(String sku);
	//根据ids查询销售订单
	List<Integer> getSaleOrderGoodsIdListByUserIds(List<User> userList);

	//第一次物流改变评论
	int updateLogisticsComments(Integer saleorderId, String s);
	/**
	*获取宝石花出库单界面列表
	* @Author:strange
	* @Date:15:10 2020-02-20
	*/
    Map<String, Object> getFlowerPrintOutListPage(HttpServletRequest request, Saleorder saleorder, Page page);

	Integer getContractReturnOrderCount(SaleorderContract saleOrderContract,
										String searchType);
	/*校验锁的状态*/
	void updateLockedStatus(Integer saleorderId);

	public CoreSpuGenerate getSpuBase(Integer skuId);
}
