package com.vedeng.finance.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.AccountPeriod;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.trader.model.TraderAmountBill;

public interface TraderAccountPeriodApplyService extends BaseService{

	/**
	 * <b>Description:</b><br> 查询账期申请记录列表信息
	 * @param tapa
	 * @param page
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月1日 下午2:01:56
	 */
	Map<String, Object> getAccountPeriodApplyListPage(TraderAccountPeriodApply tapa, Page page) throws Exception;

	/**
	 * <b>Description:</b><br> 根据主键获取账期申请信息
	 * @param tapa
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月2日 上午10:24:22
	 */
	TraderAccountPeriodApply getAccountPeriodApply(TraderAccountPeriodApply tapa) throws Exception;

	/**
	 * <b>Description:</b><br> 获取（客户、供应商）资质信息
	 * @param traderId
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月3日 下午4:27:23
	 */
	Map<String, Object> getTraderAptitudeInfo(Integer traderId) throws Exception;

	/**
	 * <b>Description:</b><br> 修改账期审核状态
	 * @param tapa
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月3日 下午4:28:12
	 */
	ResultInfo<?> editAccountPeriodAudit(TraderAccountPeriodApply tapa) throws Exception;

	/**
	 * <b>Description:</b><br> 查询账期审核记录（不分页）
	 * @param tab
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月10日 下午3:53:27
	 */
	List<TraderAmountBill> getTraderAmountBillList(TraderAmountBill tab);

	/**
	 * <b>Description:</b><br> 获取客户账期记录
	 * @param tapa
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月6日 上午9:30:40
	 */
	Map<String, Object> getCustomerAccountListPage(AccountPeriod ap, Page page);

	/**
	 * <b>Description:</b><br> 获取供应商账期记录
	 * @param tapa
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月7日 上午10:24:12
	 */
	Map<String, Object> getSupplierAccountListPage(AccountPeriod ap, Page page);

	/**
	 * <b>Description:</b><br> 导出账期申请记录
	 * @param tapa
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月20日 下午6:11:09
	 */
	List<TraderAccountPeriodApply> exportAccountPeriodApplyList(TraderAccountPeriodApply tapa);

}
