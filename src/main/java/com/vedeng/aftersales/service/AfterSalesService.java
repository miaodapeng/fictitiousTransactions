package com.vedeng.aftersales.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesCost;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.AfterSalesInstallstion;
import com.vedeng.aftersales.model.AfterSalesInvoice;
import com.vedeng.aftersales.model.AfterSalesRecord;
import com.vedeng.aftersales.model.AfterSalesTrader;
import com.vedeng.aftersales.model.CostType;
import com.vedeng.aftersales.model.vo.AfterSalesCostVo;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesInstallstionVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.vo.PayApplyVo;
import com.vedeng.trader.model.vo.TraderVo;

/**
 * <b>Description:</b><br> 
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.aftersales.service
 * <br><b>ClassName:</b> AfterSalesService
 * <br><b>Date:</b> 2017年10月9日 上午11:18:26
 */
public interface AfterSalesService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 查询售后订单分页信息
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月9日 上午11:22:12
	 */
	Map<String, Object> getAfterSalesPage(AfterSalesVo afterSalesVo, Page page,List<User> userList);

	/**
	 * <b>Description:</b><br> 查询采购订单售后列表
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月9日 下午5:29:34
	 */
	Map<String, Object> getbuyorderAfterSalesList(AfterSalesVo afterSalesVo, Page page);
	
	/**
	 * <b>Description:</b><br> 根据订单id查询对应的售后订单列表
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月10日 上午9:12:45
	 */
	List<AfterSalesVo> getAfterSalesVoListByOrderId(AfterSalesVo afterSalesVo);
	
	/**
	 * <b>Description:</b><br> 保存新增售后
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	ResultInfo<?> saveAddAfterSales(AfterSalesVo afterSalesVo,User user);
	
	/**
	 * <b>Description:</b><br> 保存编辑售后
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	ResultInfo<?> saveEditAfterSales(AfterSalesVo afterSalesVo,User user);
	
	/**
	 * <b>Description:</b><br> 保存申请付款
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	ResultInfo<?> saveApplyPay(PayApplyVo payApplyVo,User user);
	
	/**
	 * <b>Description:</b><br> 销售执行退款前验证财务是否已确认退票
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年9月4日 下午2:37:34
	 */
	ResultInfo<?> isAllReturnTicket(AfterSalesVo afterSalesVo);
	
	/**
	 * <b>Description:</b><br> 执行退款运算操作
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	ResultInfo<?> executeRefundOperation(AfterSalesVo afterSalesVo,User user);
	
	/**
	 * <b>Description:</b><br> 保存退货的申请付款
	 * @param afterSalesVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午4:28:14
	 */
	ResultInfo<?> saveRefundApplyPay(PayApplyVo payApplyVo,User user);

	
	/**
	 * <b>Description:</b><br> 获取售后的订单详情
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月17日 下午2:01:58
	 */
	AfterSalesVo getAfterSalesVoDetail(AfterSales afterSales);

    /**
     * 
     * <b>Description:</b><br> 仓储物流售后列表
     * @param afterSalesVo
     * @param page
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月17日 下午3:04:03
     */
	Map<String, Object> getStorageAftersalesPage(AfterSalesVo afterSalesVo, Page page);
    /**
     * 
     * <b>Description:</b><br> 仓储物流订单下的商品
     * @param asv
     * @param session 
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月17日 下午5:21:15
     */
	List<AfterSalesGoodsVo> getAfterSalesGoodsVoList(AfterSalesVo asv, HttpSession session);
    /**
     * 
     * <b>Description:</b><br> 根据id查询售后订单
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月18日 下午4:03:25
     */
	AfterSalesVo getAfterSalesVoListById(AfterSales afterSales);
    /**
     * 
     * <b>Description:</b><br> 根据id查询售后产品
     * @param afterSalesGoods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月19日 上午10:22:46
     */
	AfterSalesGoodsVo getAfterSalesGoodsInfo(AfterSalesGoods afterSalesGoods);
	
	/**
	 * <b>Description:</b><br> 申请审核
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月19日 下午4:43:37
	 */
	ResultInfo<?> editApplyAudit(AfterSalesVo afterSales);

	/**
	 * <b>Description:</b><br> 关闭售后订单
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午10:16:45
	 */
	ResultInfo<?> saveCloseAfterSales(AfterSalesVo afterSales, User user);
	
	/**
	 * <b>Description:</b><br> 保存售后的退换货手续费
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:38:35
	 */
	ResultInfo<?> saveEditRefundFee(AfterSalesVo afterSales, User user);
	
	/**
	 * <b>Description:</b><br> 保存编辑的退票信息
	 * @param afterSales
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:38:35
	 */
	ResultInfo<?> saveEditRefundTicket(AfterSalesInvoice afterSalesInvoice);
	
	/**
	 * <b>Description:</b><br> 保存新增或更新的售后过程
	 * @param afterSalesInvoice
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午5:35:59
	 */
	ResultInfo<?> saveAfterSalesRecord(AfterSalesRecord afterSalesRecord,User user);
	
	/**
	 * <b>Description:</b><br> 获取售后过程
	 * @param afterSalesInvoice
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午5:35:59
	 */
	AfterSalesRecord getAfterSalesRecord(AfterSalesRecord afterSalesRecord);
	
	/**
	 * <b>Description:</b><br> 查询新增产品与工程师页面所需数据
	 * @param afterSalesInstallstion
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午3:23:15
	 */
	List<AfterSalesGoodsVo> getAfterSalesInstallstionVoByParam(AfterSalesVo afterSales);
	
	/**
	 * <b>Description:</b><br> 保存编辑的安调信息
	 * @param afterSalesInvoice
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午5:35:59
	 */
	ResultInfo<?> saveEditInstallstion(AfterSalesDetail afterSalesDetail);
	
	/**
	 * <b>Description:</b><br> 查询售后服务费信息
	 * @param afterSalesDetail
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年4月16日 上午11:04:41
	 */
	AfterSalesDetailVo getAfterSalesDetailVoByParam(AfterSalesDetail afterSalesDetail);
	
	/**
	 * <b>Description:</b><br> 查询工程师分页信息
	 * @param afterSalesInstallstion
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午3:23:15
	 */
	Map<String, Object> getEngineerPage(AfterSalesVo afterSales, Page page);

	/**
	 * <b>Description:</b><br> 查询客户的联系人和财务信息
	 * @param traderVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月3日 下午1:08:22
	 */
	Map<String, Object> getCustomerContactAndFinace(TraderVo traderVo);
	
	/**
	 * <b>Description:</b><br> 保存新增或编辑的工程师与售后产品的关系
	 * @param afterSalesInstallstionVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月27日 下午5:57:28
	 */
	ResultInfo<?> saveAfterSalesEngineer(AfterSalesInstallstionVo afterSalesInstallstionVo,User user);
	
	/**
	 * <b>Description:</b><br> 获取编辑工程师与售后产品的信息
	 * @param afterSalesInstallstionVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月31日 下午1:01:03
	 */
	AfterSalesInstallstionVo getAfterSalesInstallstionVo(AfterSalesInstallstionVo afterSalesInstallstionVo);
	
	/**
	 * <b>Description:</b><br> 获取售后安调维修申请付款页面的信息--工程师和安调维修费用
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月7日 下午1:57:19
	 */
	AfterSalesVo getAfterSalesApplyPay(AfterSalesVo afterSalesVo);
    /**
     * 
     * <b>Description:</b><br> 根据id获取售后单
     * @param as
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月11日 上午11:26:22
     */
	AfterSales selectById(AfterSales as);

	/**
	 * <b>Description:</b><br> 派单短信
	 * @param afterSalesInstallstionVo
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月29日 上午11:48:13
	 */
	ResultInfo sendInstallstionSms(AfterSalesInstallstionVo afterSalesInstallstionVo, User user);
	
	/**
	 * <b>Description:</b><br> 保存售后开票申请
	 * @param invoiceApply
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 下午2:08:31
	 */
	ResultInfo<?> saveOpenInvoceApply(InvoiceApply invoiceApply);
	
	/**
	 * <b>Description:</b><br> 售后直发产品确认全部收货
	 * @param invoiceApply
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 下午2:08:31
	 */
	ResultInfo<?> updateAfterSalesGoodsArrival(AfterSalesGoods afterSalesGoods);
    /**
     * 
     * <b>Description:</b><br> 查询售后单 详情
     * @param as
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月25日 上午11:43:59
     */
	AfterSalesDetail selectadtById(AfterSales as);
    /**
     * 
     * <b>Description:</b><br> 查询售后单下未出库的商品
     * @param afterSalesId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月18日 下午3:24:28
     */
	List<AfterSalesGoods> getAfterSalesGoodsNoOutList(Integer afterSalesId);
	
	/**
	 * <b>Description:</b>根据Id查找对应的费用类型列表(废弃)<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月23日 19:33
	 */
	List<CostType> getCostTypeById(Integer costType);
	
	/**
	 * 
	 * <b>Description:</b>根据订单Id查找售后费用类型列表<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:30
	 */
	List<AfterSalesCostVo> getAfterSalesCostListById(AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>新增订单的售后类型费用<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:31
	 */
	ResultInfo addAfterSalesCost(AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>删除订单的售后费用类型记录<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:33
	 */
	ResultInfo deleteAfterSalesCostById(AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>更改订单的售后费用类型记录<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:36
	 */
	ResultInfo updateAfterSalesCostById(AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>查询某条费用类型记录<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 16:04
	 */
	AfterSalesCostVo getAfterSalesCostBycostId(AfterSalesCost afterSalesCost);
	

	/**
	 * <b>Description:</b><br>新增或编辑售后对象 
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午6:12:18
	 */
	ResultInfo saveOrUpdateAfterTradder(AfterSalesTrader afterSalesTrader);
	
	/**
	 * <b>Description:</b><br> 查询售后对象
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年8月1日 上午8:50:16
	 */
	AfterSalesTrader getAfterSalesTrader(AfterSalesTrader afterSalesTrader);
	
	/**
	 * 
	 * <b>Description: 根据售后订单ID查询售后对象列表</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月09日 09:28
	 */
	List<AfterSalesTrader> getAfterSalesTraderList(Integer afterSalesId);
	
	/**
	 * 
	 * <b>Description:</b><br> 根据主键修改售后单信息
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月15日 上午10:54:31
	 */
	ResultInfo<?> updateAfterSalesById(AfterSalesVo afterSales);

	/**
	 * 解锁产品
	 * @param afterSales
	 */
	void getNoLockSaleorderGoodsVo(AfterSalesVo afterSales);

	/**
	 * <b>Description:</b><br> 查询销售订单退还余额的金额
	 * add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月4日 上午11:13:22
	 */
	BigDecimal getRefundBalanceAmountBySaleorderId(Integer saleorderId);


	/**
	* @Title: searchAfterOrder
	* @Description: TODO(获取售后销售订单信息及商品信息)
	* @param @param afterSalesVo
	* @param @param page
	* @param @return    参数
	* @return Map<String,Object>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月22日
	*/
	Map<String, Object> searchAfterOrder(AfterSalesVo afterSalesVo, Page page);

	/**
	*获取售后单内商品未出库数量
	* @Author:strange
	* @Date:17:42 2019-12-07
	*/
    List<AfterSalesGoods> getAfterSalesGoodsNoOutNumList(Integer afterSalesId);
	/**
	*获取销售单下非关闭售后单
	 * @param afterSales orderId为销售单id
	* @Author:strange
	* @Date:20:41 2019-12-30
	*/
	List<AfterSales> getAfterSaleListById(AfterSales afterSales);

	/**
	 * @description: BD订单产生售后退货完成时通知Mjx.
	 * @jira: VDERP-2057 BD订单流程优化-ERP部分  售后产生的付款状态变更需要通知mjx.
	 * @notes: .
	 * @version: 1.0.
	 * @date: 2020/3/6 2:09 下午.
	 * @author: Tomcat.Hui.
	 * @param saleorderId: .
	 * @return: void.
	 * @throws: .
	 */
	void notifyStatusToMjx(Integer saleorderId,Integer afterSalesId);

	AfterSalesDetail getAfterSalesDetailByAfterSaleDetailId(Integer afterSalesDetailId);

	AfterSales getAfterSalesById(Integer afterSalesId);
}
