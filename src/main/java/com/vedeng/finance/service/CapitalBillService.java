package com.vedeng.finance.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.BuyorderData;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.SaleorderData;
import com.vedeng.order.model.Saleorder;

public interface CapitalBillService extends BaseService{

	/**
	 * <b>Description:</b><br> 获取资金流水列表（不分页）
	 * @param capitalBill
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月12日 下午5:39:25
	 */
	List<CapitalBill> getCapitalBillList(CapitalBill capitalBill);
	
	/**
	 * <b>Description:</b><br> 资金流水列表（分页）
	 * @param request
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月12日 下午5:39:41
	 */
	Map<String, Object> getCapitalBillListPage(HttpServletRequest request, CapitalBill capitalBill, Page page);
	
	/**
	 * <b>Description:</b><br> 保存新增的资金流水
	 * @param capitalBill
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月13日 下午5:48:37
	 */
	ResultInfo<?> saveAddCapitalBill(CapitalBill capitalBill);

	/**
	 * <b>Description:</b><br> 付款记录（分页）
	 * @param request
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月25日 下午6:00:09
	 */
	Map<String, Object> getPaymentRecordListPage(HttpServletRequest request, CapitalBill capitalBill, Page page);

	/**
	 * <b>Description:</b><br> 收款记录（分页）
	 * @param request
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月25日 下午6:16:47
	 */
	Map<String, Object> getCollectionRecordListPage(HttpServletRequest request, CapitalBill capitalBill, Page page);

	/**
	 * <b>Description:</b><br> 根据销售订单ID查询账期付款总额-订单未还账期款
	 * @param saleorderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月27日 下午5:30:23
	 */
	List<SaleorderData> getCapitalListBySaleorderId(List<Integer> saleorderIdList);
	
	/**
	 * <b>Description:</b><br> 根据销售订单ID查询账期付款总额-订单未还账期款
	 * @param saleorderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月27日 下午5:30:23
	 */
	List<BuyorderData> getCapitalListByBuyorderId(List<Integer> buyorderIdList);
	
	/**
	 * <b>Description:</b><br> 根据销售订单ID查询账期付款总额-订单未还账期款
	 * @param saleorderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月27日 下午5:30:23
	 */
	Saleorder getSaleorderCapitalById(Integer saleorderId);

	/**
	 * <b>Description:</b><br> 财务退款业务操作保存
	 * @param capitalBill
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月2日 下午3:14:36
	 */
	ResultInfo<?> saveRefundCapitalBill(CapitalBill capitalBill);

	/**
	 * <b>Description:</b><br> 付款申请添加流水记录
	 * @param capitalBill
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月8日 下午3:15:05
	 */
	ResultInfo<?> savePayCapitalBill(CapitalBill capitalBill);
	
	/**
	 * 
	 * <b>Description:</b><br> 添加流水记录
	 * @param capitalBill
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年2月1日 下午10:45:24
	 */
	ResultInfo<?> saveCapitalBill(CapitalBill capitalBill);

	/**
	 * <b>Description:</b><br> 付款列表数据异步补充
	 * @param saleOrderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年9月12日 上午11:27:31
	 */
	ResultInfo<?> getCollectionRecordInfoAjax(List<Integer> saleOrderIdList);

	/**	
	* @Description: TODO(生成对私提现流水记录)
	* @param @param saleorderInfo
	* @param @param capitalBill
	* @param @return   
	* @return ResultInfo   
	* @author strange
	* @throws
	* @date 2019年10月16日
	*/
	ResultInfo saveSecondCapitalBill(Saleorder saleorderInfo, CapitalBill capitalBill);

}
