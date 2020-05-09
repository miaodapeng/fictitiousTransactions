package com.vedeng.trader.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderOrderGoods;
import com.vedeng.trader.model.TraderSupplier;
import com.vedeng.trader.model.vo.TraderSupplierVo;

/**
 * <b>Description:</b><br> 接口
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.service
 * <br><b>ClassName:</b> TraderSupplierService
 * <br><b>Date:</b> 2017年5月11日 下午1:29:05
 */
public interface TraderSupplierService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 获取供应商列表
	 * @param traderSupplier
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 下午1:28:41
	 */
	Map<String,Object> getTraderSupplierList(TraderSupplierVo traderSupplierVo,Page page,List<User> userList);
	/**
	 * <b>Description:</b><br> 获取供应商列表
	 * @param traderSupplier
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 下午1:28:41
	 */
	Map<String,Object> getSupplierByName(TraderSupplierVo traderSupplierVo,Page page,List<User> userList);
	

	/**
	 * <b>Description:</b><br> 是否置顶
	 * @param id
	 * @param isTop
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo isStick(Integer id ,Integer isTop,User user);
	
	/**
	 * <b>Description:</b><br> 是否禁用
	 * @param id
	 * @param isTop
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo isDisabled(User user,Integer id ,Integer isDisabled,String disableReason);
	
	/**
	 * <b>Description:</b><br> 保存添加供应商
	 * @param trader
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 上午9:55:51
	 */
	TraderSupplier saveSupplier(Trader trader,HttpServletRequest request,HttpSession session);
	
	/**
	 * <b>Description:</b><br> 基本信息
	 * @param traderSupplier
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 上午10:58:23
	 */
	TraderSupplierVo getTraderSupplierBaseInfo(TraderSupplier traderSupplier);
	
	/**
	 * <b>Description:</b><br> 管理信息
	 * @param traderSupplier
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 上午10:58:31
	 */
	TraderSupplierVo getTraderSupplierManageInfo(TraderSupplier traderSupplier);
	
	/**
	 * <b>Description:</b><br> 保存基本信息
	 * @param traderSupplier
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 下午1:56:03
	 */
	TraderSupplier saveEditManageInfo(TraderSupplier traderSupplier,HttpServletRequest request,HttpSession session);
	
	/**
	 * <b>Description:</b><br> 保存管理信息
	 * @param trader
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月18日 下午1:56:14
	 */
	TraderSupplier saveEditBaseInfo(Trader trader,HttpServletRequest request,HttpSession session);

	/**
	 * <b>Description:</b><br> 沟通记录
	 * @param communicateRecord
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月24日 下午3:37:06
	 */
	List<CommunicateRecord> getCommunicateRecordListPage(CommunicateRecord communicateRecord,Page page);
	
	/**
	 * <b>Description:</b><br> 保存新增沟通
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月24日 下午3:39:02
	 */
	Boolean saveAddCommunicate(CommunicateRecord communicateRecord,HttpServletRequest request,HttpSession session) throws Exception;
	
	/**
	 * <b>Description:</b><br> 保存编辑沟通
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月24日 下午2:38:31
	 */
	Boolean saveEditCommunicate(CommunicateRecord communicateRecord,HttpServletRequest request,HttpSession session) throws Exception;
	
	
	/**
	 * <b>Description:</b><br> 查询用户供应商数量
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月5日 下午7:02:46
	 */
	Integer getUserSupplierNum(Integer userId);
	
	/**
	 * <b>Description:</b><br> 查询供应商归属
	 * @param rTraderJUser
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月6日 上午8:58:37
	 */
	Map<String, Object> getUserTraderByTraderNameListPage(RTraderJUser rTraderJUser, Page page);
	
	/**
	 * <b>Description:</b><br> 单个分配
	 * @param traderId
	 * @param single_to_user
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月6日 下午1:29:09
	 */
	Boolean assignSingleSupplier(Integer traderId,Integer single_to_user); 
	
	/**
	 * <b>Description:</b><br> 批量分配
	 * @param from_user
	 * @param batch_to_user
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月6日 下午1:30:03
	 */
	Boolean assignBatchSupplier(Integer from_user,Integer batch_to_user);

	/**
	 * <b>Description:</b><br> 根据供应商交易者IDor供应商ID获取供应商基本表信息
	 * @param traderSupplier
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月28日 上午9:40:38
	 */
	TraderSupplierVo getSupplierInfoByTraderSupplier(TraderSupplier traderSupplier);
	
	/**
	 * <b>Description:</b><br> 查询供应商基本信息
	 * @param ts
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月2日 下午5:29:18
	 */
	TraderSupplierVo getTraderSupplierInfo(TraderSupplier ts);
	
	/**
	 * <b>Description:</b><br> 查询供应商最近一次账期申请信息
	 * @param traderSupplierId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月8日 上午10:43:57
	 */
	TraderAccountPeriodApply getTraderSupplierLastAccountPeriodApply(Integer traderId);
	
	/**
	 * <b>Description:</b><br> 查询供应商账期信息
	 * @param traderSupplierVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月8日 上午10:44:26
	 */
	TraderSupplierVo getTraderSupplierForAccountPeriod(TraderSupplierVo traderSupplierVo);
	
	/**
	 * <b>Description:</b><br> 获取主供应商列表（根据产品ID）
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月22日 下午6:24:43
	 */
	List<TraderSupplierVo> getMainSupplyListByGoodsId(Integer goodsId);
	
	/**
	 * <b>Description:</b><br> 查询沟通记录的主键
	 * @param traderSupplierVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月16日 下午1:41:50
	 */
	List<Integer> getTagTraderIdList(TraderSupplierVo traderSupplierVo);
	
	/**
	 * <b>Description:</b><br> 交易记录
	 * @param buyorderGoodsVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月25日 下午1:50:46
	 */
	Map<String, Object> getBusinessListPage(BuyorderGoodsVo buyorderGoodsVo, Page page);
	
	/**
	 * 订单覆盖品类品牌区域
	 * <b>Description:</b><br> 
	 * @param traderOrderGoods
	 * @return
	 * @Note
	 * <b>Author:</b> Bill
	 * <br><b>Date:</b> 2018年7月9日 上午9:34:09
	 */
	TraderSupplierVo getOrderCoverInfo(TraderOrderGoods traderOrderGoods);
}
