package com.vedeng.soap;

import com.vedeng.trader.model.WebAccount;

/**
 * <b>Description:</b><br> 接口：http://localhost:8080/erp/services/api?wsdl
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.soap
 * <br><b>ClassName:</b> ApiSoap
 * <br><b>Date:</b> 2017年11月16日 下午3:31:44
 */
public interface ApiSoap {
	public String hello(String msg);
	
	/**
	 * <b>Description:</b><br> DEMO（自营网站产品废弃）
	 * @param goodsId
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月16日 上午11:48:45
	 */
	public String itemUpdate(String goodsId,String data);
	
	/**
	 * <b>Description:</b><br> 订单信息同步（网站往ERP）
	 * @param data
	 * @param goodsData
	 * @param payData
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月16日 下午3:39:42
	 */
	public String orderSync(String data,String goodsData,String payData);
	
	/**
	 * <b>Description:</b><br> 账号同步
	 * @param data
	 * @param uuid
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 上午11:42:43
	 */
	public String accountSync(String data,String uuid);
	
	/**
	 * <b>Description:</b><br> 附件回传
	 * @param data  web把attachment 除主键外全部同步过来
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:30:18
	 */
	public String attachmentSync(String data);
	
	/**
	 * <b>Description:</b><br> 报价同步
	 * @param data
	 * @param orderItemData
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:32:42
	 */
	public String quoteorderSync(String data,String orderItemData);
	
	/**
	 * <b>Description:</b><br> 订单开票申请
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:33:13
	 */
	public String saleorderInvoiceApplySync(String data);
	
	/**
	 * <b>Description:</b><br> 订单客户收货（全部收货 订单 +订单详情）
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:34:02
	 */
	public String saleorderArrivalSync(String data);
	
	/**
	 * <b>Description:</b><br> 账期记录（带分页）
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月15日 上午8:42:01
	 */
	public String periodInfoSync(String data);
	
	/**
	 * <b>Description:</b><br> 网站订单同步(非订货订单)
	 * @param data
	 * @param goodsData
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月15日 下午1:47:45
	 */
	public String orderSyncErp(String data,String goodsData);
	
	/**
	 * <b>Description:</b><br> 网站询价同步
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月21日 下午1:02:20
	 */
	public String enquirySyncErp(String data);
	
	/**
	 * <b>Description:</b><br> 开票申请
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月26日 下午2:12:21
	 */
	public String applyInvoice(String data);
	
	/**
	 * <b>Description:</b><br> 获取客户账期信息
	 * @param data
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年4月16日 上午8:53:14
	 */
	public String accountPeriodInfoSync(String data);
	
	/**
	 * <b>Description:</b><br> 销售合同生成pdf
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年8月24日 上午10:47:37
	 */
	public String printSaleContractPDF(String data);
	/**
	 * 
	 * <b>Description:</b><br> 物流信息查询接口
	 * @param logisticsNo
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月27日 上午9:16:01
	 */
	public String logisticsDetailSync(String logisticsNo);
	/**
	 * 
	 * <b>Description:</b><br> 根据销售单号查询对应单子下的快递信息（如果是直发订单就查采购单的快递信息）
	 * @param saleorderNo
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月29日 上午10:38:45
	 */
	public String logisticsExpressByOrderNoSync(String saleorderNo);

	/**
	 * <b>Description:</b><br>根据手机号查询该用户是否是老用户
	 *
	 *
	 * @param :int
	 * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/1 3:24 PM
	 */
	int getMobileCount(WebAccount webAccount);

	/**
	 * <b>Description:</b><br>根据手机号修改是否加入贝登精选状态
	 *
	 *
	 * @param :int
	 * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/1 3:24 PM
	 */
	int updateisVedengJoin(WebAccount webAccount);

	/**
	 * <b>Description:</b><br> 加入注册信息
	 * @param webAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Addis
	 * <br><b>Date:</b> 2018年1月11日 下午4:14:00
	 */
	int insert(WebAccount webAccount);

	/**
	 * <b>Description:</b><br>根据手机号码查询1、是否有效 2、是否是贝登会员 3、是否申请加入贝登精选
	 *
	 *
	 * @param :webAccount
	 * @return :webAccount
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/2 3:24 PM
	 */
	WebAccount selectMobileResult(WebAccount webAccount);
}
