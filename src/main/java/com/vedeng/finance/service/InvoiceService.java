package com.vedeng.finance.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.logistics.model.Express;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;

public interface InvoiceService extends BaseService{

	/**
	 * <b>Description:</b><br> 根据外键查询发票记录（对应产品开票记录）
	 * @param bo
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月18日 上午11:31:04
	 */
	Map<String,Object> getInvoiceListByBuyorder(BuyorderVo bo,Invoice invoice,Integer inputInvoiceType) throws Exception;

	/**
	 * <b>Description:</b><br> 录票信息添加保存
	 * @param invoice
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月21日 下午3:47:30
	 */
	ResultInfo<?> saveInvoice(Invoice invoice) throws Exception;

	/**
	 * <b>Description:</b><br> 根据发票单号查询收票审核
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月23日 上午11:27:15
	 */
	Map<String, Object> getInvoiceAuditListByInvoiceNo(Invoice invoice);

	/**
	 * <b>Description:</b><br> 保存开票记录审核结果
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月24日 下午3:04:27
	 */
	ResultInfo<?> saveInvoiceAudit(Invoice invoice);

	/**
	 * <b>Description:</b><br> 查询收票记录列表
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月24日 下午4:20:57
	 */
	Map<String,Object> getInvoiceListPage(Invoice invoice,Page page);
	
	/**
	 * <b>Description:</b><br> 保存批量认证
	 * @param invoices
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年5月28日 下午4:56:51
	 */
	ResultInfo<?> saveBatchAuthentication(List<Invoice> invoices);

	/**
	 * <b>Description:</b><br> 开票记录列表导出
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 上午9:10:22
	 */
	Map<String, Object> exportIncomeInvoiceList(Invoice invoice);

	/**
	 * <b>Description:</b><br> 保存开票申请（包括提前开票）
	 * @param invoiceApply
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 下午2:08:31
	 */
	ResultInfo<?> saveOpenInvoceApply(InvoiceApply invoiceApply);

	/**
	 * <b>Description:</b><br> 开票申请列表页
	 * @param invoiceApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 下午3:08:00
	 */
	Map<String, Object> getSaleInvoiceApplyListPage(InvoiceApply invoiceApply,Page page);
	
	/**
	 * <b>Description:</b><br> 分公司-开票申请待审核列表（审核流程）
	 * @param invoiceApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年5月29日 下午3:53:50
	 */
	Map<String, Object> getInvoiceApplyVerifyListPage(InvoiceApply invoiceApply, Page page);

	/**
	 * <b>Description:</b><br> 保存开票申请审核结果
	 * @param invoiceApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月29日 下午2:23:06
	 */
	ResultInfo<?> saveOpenInvoiceAudit(InvoiceApply invoiceApply);

	/**
	 * <b>Description:</b><br> 批量审核开票记录（提前开票）
	 * @param invoiceApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月29日 下午5:51:55
	 */
	ResultInfo<?> saveOpenInvoiceAuditBatch(InvoiceApply invoiceApply);

	/**
	 * <b>Description:</b><br> 提前开票申请列表页
	 * @param invoiceApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月30日 上午9:17:11
	 */
	Map<String, Object> getAdvanceInvoiceApplyListPage(InvoiceApply invoiceApply, Page page);

	/**
	 * <b>Description:</b><br> 获取销售产品及已开票数量
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月30日 上午11:47:10
	 */
	Map<String, Object> getSaleInvoiceList(Invoice invoice);

	/**
	 * <b>Description:</b><br> 开票记录（销售）查询
	 * @param invoice
	 * @param page 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月1日 下午2:00:21
	 */
	Map<String, Object> getSaleInvoiceListPage(Invoice invoice, Page page);

	/**
	 * <b>Description:</b><br> 根据单号(销售或其他)获取发票信息
	 * @param saleorderId
	 * @param id505
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月1日 下午3:33:10
	 */
	List<Invoice> getInvoiceInfoByRelatedId(Integer relatedId, Integer id);

	/**
	 * <b>Description:</b><br> 根据销售订单获取开票申请记录
	 * @param saleorderId
	 * @param id505
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2017年9月11日 下午3:40:52
	 */
	List<InvoiceApply> getSaleInvoiceApplyList(Integer saleorderId, Integer id505,Integer isAdvance);

	/**
	 * <b>Description:</b><br> 查询此订单最近一次开票申请记录
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月29日 上午9:11:38
	 */
	InvoiceApply getInvoiceApplyByRelatedId(Integer saleorderId,Integer type,Integer companyId);
	/**
	 * <b>Description:</b><br> 保存发票寄送快递信息
	 * @param express
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月30日 下午1:35:45
	 */
	ResultInfo<?> saveExpressInvoice(Express express);

	/**
	 * <b>Description:</b><br> 获取采购发票待审核列表
	 * @param invoice
	 * @param page 
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月9日 下午1:35:05
	 */
	Map<String, Object> getBuyInvoiceAuditList(Invoice invoice, Page page);
    /**
     * 
     * <b>Description:</b><br> 根据单号获取发票信息
     * @param ic
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月11日 上午8:57:20
     */
	Invoice getInvoiceByNo(Invoice ic);

	/**
	 * <b>Description:</b><br> 售后开票申请列表
	 * @param invoiceApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年4月16日 下午4:29:47
	 */
	Map<String, Object> getAfterOpenInvoiceListPage(InvoiceApply invoiceApply, Page page);

	/**
	 * <b>Description:</b><br> 售后开票申请待审核列表（分公司流程）
	 * @param invoiceApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年5月30日 下午2:55:20
	 */
	Map<String, Object> getAfterInvoiceApplyVerifyListPage(InvoiceApply invoiceApply, Page page);
	
	/**
	 * <b>Description:</b><br> 采购录票确认列表
	 * @param invoice
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年5月4日 下午2:07:29
	 */
	Map<String, Object> getBuyInvoiceConfirmListPage(Invoice invoice, Page page);

	/**
	 * 发送至金蝶开票记录
	 * <b>Description:</b><br> 
	 * @param invoice
	 * @param page
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Bill
	 * <br><b>Date:</b> 2018年5月30日 下午1:34:26
	 */
	ResultInfo<?> sendOpenInvoicelist(Invoice invoice, Page page, HttpSession session) throws Exception;

	/**
	 * 发送至金蝶收票记录
	 * <b>Description:</b><br> 
	 * @param invoice
	 * @param page
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Bill
	 * <br><b>Date:</b> 2018年5月30日 下午1:35:36
	 */
	ResultInfo<?> sendIncomeInvoiceList(Invoice invoice, Page page, HttpSession session) throws Exception;

	/**
	 * <b>Description:</b><br> 开具电子发票
	 * @param invoiceApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年6月15日 上午9:25:06
	 */
	ResultInfo<?> openEInvoicePush(InvoiceApply invoiceApply);

	/**
	 * <b>Description:</b><br> 电子发票下载
	 * @param invoiceApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年6月25日 上午10:25:41
	 */
	ResultInfo<?> batchDownEInvoice(Invoice invoice);

	/**
	 * <b>Description:</b><br> 电子发票作废
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年6月27日 下午3:20:11
	 */
	ResultInfo<?> cancelEInvoicePush(Invoice invoice);
    /**
     * 
     * <b>Description:</b><br> 发票状态回退
     * @param express
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月3日 上午8:44:11
     */
	ResultInfo<?> updateExpressInvoice(Express express);

	/**
	 * <b>Description:</b><br> 电子票推送短信和邮箱
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年7月20日 下午6:44:01
	 */
	ResultInfo<?> sendInvoiceSmsAndMail(Invoice invoice);

	/* *
	 * 功能描述: 查询所有集中开票客户
	 * @param: [traderName]
	 * @return: java.util.List<TraderCustomer>
	 * @auther: duke.li
	 * @date: 2019/4/12 9:09
	 */
	List<TraderCustomerVo> getCollectInvoiceTraderName(String traderName);

	/* *
	 * 功能描述: 集中开票客户导入
	 * @param: [list]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: duke.li
	 * @date: 2019/4/12 13:07
	 */
	ResultInfo<?> updateCollectInvoiceTrader(List<Trader> list);

	/* *
	 * 功能描述: 批量删除集中开票客户
	 * @param: [traderCustomerVo]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: duke.li
	 * @date: 2019/4/12 17:07
	 */
	ResultInfo<?> delCollectInvoiceTrader(TraderCustomerVo traderCustomerVo);

	/* *
	 * 功能描述: 修改发票申请标记状态
	 * @param: [invoiceApply]
	 * @return: com.vedeng.common.model.ResultInfo<?>
	 * @auther: duke.li
	 * @date: 2019/4/13 14:48
	 */
    ResultInfo<?> updateInvoiceApplySign(InvoiceApply invoiceApply);
	
    /**
     * <b>Description:</b><br>发票寄送后发微信模版消息给对应客户
     * 
     *
     * @param :[invoiceIdList]
     * @return :com.vedeng.common.model.ResultInfo<?>
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2019/5/22 11:23 AM
     */
	ResultInfo<?> sendWxMessageForInvoice(List<Invoice> invoiceList);

	/**
	 * 根据发票ID查询list
	 * @param invoiceIdLis
	 * @return
	 */
	List<Invoice> getInvoiceListByInvoiceIdList(List<Integer> invoiceIdLis);


	/** @description: getInvoiceNums.
	 * @notes: VDERP-1325 分批开票 获取已开票数量和未开票数量.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/8 15:15.
	 * @param saleorderNo
	 * @return: java.util.Map<java.lang.Integer , java.util.Map < java.lang.String , java.math.BigDecimal>>.
	 * @throws: .
	 */
	Map<Integer ,Map<String,Object>> getInvoiceNums(Saleorder saleorder);

	/** @description: getApplyDetailById.
	 * @notes: VDERP-1325 分批开票 获取开票申请详情.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/21 9:58.
	 * @param invoiceApply
	 * @return: com.vedeng.finance.model.InvoiceApply.
	 * @throws: .
	 */
	InvoiceApply getApplyDetailById(InvoiceApply invoiceApply);

	/** @description: getInvoiceDetailById.
	 * @notes:  VDERP-1325 分批开票 获取发票明细详情.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/21 10:03.
	 * @param invoice
	 * @return: com.vedeng.finance.model.Invoice.
	 * @throws: .
	 */
	Invoice getInvoiceDetailById(Invoice invoice);
	/**
	*获取当前订单开票审核状态 1 存在审核中 0 不存在
	* @Author:strange
	* @Date:10:25 2019-11-23
	*/
    Integer getInvoiceApplyNowAuditStatus(Integer saleorderId);

	/**
	 * 根据ID查询关联表ID
	 * @param InvoiceApplyId
	 * @return
	 */
	InvoiceApply getInvoiceApply(Integer InvoiceApplyId);

	/**
	 * 批量开发票
	 * @param invoiceApply
	 * @param invoiceApplyIdArr
	 * @return
	 * @author Rock
	 */
	ResultInfo<?> openEInvoiceBatchPush(InvoiceApply invoiceApply, String invoiceApplyIdArr);
	/**
	*是否是票货同行订单
	 * @param saleorderId
	 * @return true 是票货同行订单
	* @Author:strange
	* @Date:13:54 2019-12-30
	*/
    boolean getOrderIsGoodsPeer(Integer saleorderId);
	/**
	* 更新开票申请信息
	* @Author:strange
	* @Date:15:40 2019-12-30
	*/
    InvoiceApply updateInvoiceApplyInfo(InvoiceApply invoiceApply);
	/**
	* 票货同行快递单是否可编辑
	* @Author:strange
	* @Date:09:59 2020-01-06
	*/
    Boolean getInvoiceApplyByExpressId(Integer expressId);
	/**
	*驳回票货同行未开票申请
	* @Author:strange
	* @Date:10:01 2020-01-06
	*/
	void delInvoiceApplyByExpressId(Integer expressId);

    /** @description: getInvoiceApplyByExpressId.
     * @notes: VDERP-1039 票货同行 根据快递单ID查询开票申请..
     * @author: Tomcat.Hui.
     * @date: 2020/1/3 17:13.
     * @param invoiceApply
     * @return: com.vedeng.model.finance.InvoiceApply.
     * @throws: .
     */
	List<InvoiceApply> getInvoiceApplyInfoByExpressId(InvoiceApply invoiceApply);

    /** @description: getInvoiceByApplyId.
     * @notes: VDERP-1039 票货同行 根据开票申请ID获取发票信息.
     * @author: Tomcat.Hui.
     * @date: 2020/1/6 10:08.
     * @param InvoiceApplyId
     * @return: java.util.List<com.vedeng.finance.model.Invoice>.
     * @throws: .
     */
    List<Invoice> getInvoiceByApplyId(Integer InvoiceApplyId);
	/**
	*票货同行订单是否可删除快递
	* @Author:strange
	* @Date:21:20 2020-01-06
	*/
    boolean isDelExpressByExpressId(Express express);


    /** @description: getInvoicedData.
	 * @notes: VDERP-1874 开票中和已开票数量、金额的计算规则变更 获取已被占用数量(已申请+已开票).
	 * @author: Tomcat.Hui.
	 * @date: 2020/1/16 19:23.
	 * @param saleorderNo
	 * @return: java.util.Map<java.lang.Integer , java.math.BigDecimal>.
	 * @throws: .
	 */
	Map<Integer, BigDecimal> getInvoiceOccupiedAmount(Saleorder saleorder);

	/** @description: getExpressInvoiceInfo.
     * @notes: 查询物流list每个对象的开票申请和开票信息.
     * @author: Tomcat.Hui.
     * @date: 2020/1/17 10:30.
     * @param expressList
     * @return: java.util.List<com.vedeng.logistics.model.Express>.
     * @throws: .
     */
    public List<Express> getExpressInvoiceInfo(List<Express> expressList);

	/**
	 * 快递是否关联发票
	 * @param expressId
	 * @return
	 */
	Boolean getInvoiceApplyByExpressIdNo(Integer expressId);
	/**
	 * 快递是否关联都为不通过发票
	 * @param expressId
	 * @return
	 */
    Boolean getInvoiceApplyByExpressIdFaile(Integer expressId);

	/**
	 * 判断是否为待审核
	 * @param expressId
	 * @return
	 */
	Boolean getInvoiceApplyByExpressIdByValidIsZero(Integer expressId);
	//查询所有开票申请
	List<InvoiceApply> getAllSaleInvoiceApplyList();
	//根据订单id查找开票记录
	List<Integer> getInvoiceApplyIdsBySaleOrderIds(List saleOrderNoList);
	//改变标记状态
	int changeIsSign(List<Integer> endInvoiceApplyIds);
}
