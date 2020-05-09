package com.vedeng.soap.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.trader.model.WebAccount;

/**
 * <b>Description:</b><br> ERP往贝登网站/手机/订货系统同步
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.soap.service
 * <br><b>ClassName:</b> VedengSoapService
 * <br><b>Date:</b> 2017年11月20日 下午3:04:23
 */
public interface VedengSoapService extends BaseService {
	/**
	 * <b>Description:</b><br> 订单信息同步
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月20日 下午3:07:14
	 */
	public ResultInfo orderSync(Integer saleorderId);
	
	/**
	 * <b>Description:</b><br> 支付信息同步
	 * @param capitalBillId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 上午11:49:54
	 */
	public ResultInfo paylogSync(Integer capitalBillId);
	
	/**
	 * <b>Description:</b><br> 开票信息同步
	 * @param invoiceId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午12:59:58
	 */
	public ResultInfo invoiceSync(Integer invoiceId);
	
	/**
	 * <b>Description:</b><br> 快递信息同步（销售发货，发票寄送）
	 * @param expressId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月21日 下午1:00:49
	 */
	public ResultInfo expressSync(Integer expressId);
	
	/**
	 * <b>Description:</b><br> 产品信息同步
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月9日 下午6:17:38
	 */
	public ResultInfo goodsSync(Integer goodsId);
	
	/**
	 * <b>Description:</b><br> 注册账号信息同步
	 * @param accountId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月9日 下午6:19:08
	 */
	public ResultInfo accountSync(Integer accountId);

	/**
	 * <b>Description:</b><br> 注册账号密码重置
	 * @param webAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:09:36
	 */
	public ResultInfo webAccountRestPasswordSync(WebAccount webAccount);
	
	/**
	 * <b>Description:</b><br> 品牌同步
	 * @param brandId
	 * @param isDel
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月15日 下午4:35:05
	 */
	public ResultInfo brandSync(Integer brandId,Boolean isDel);
	
	/**
	 * <b>Description:</b><br> 单位同步
	 * @param unitId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月15日 下午4:35:49
	 */
	public ResultInfo unitSync(Integer unitId);
	
	/**
	 * <b>Description:</b><br> 报价同步
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:12:39
	 */
	public ResultInfo quoteorderSync(Integer quoteorderId);
	
	/**
	 * <b>Description:</b><br> 地址同步
	 * @param regionId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:13:16
	 */
	public ResultInfo regionSync(Integer regionId);
	
	/**
	 * <b>Description:</b><br> 分类同步
	 * @param categoryId
	 * @param isDel
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月13日 下午5:14:17
	 */
	public ResultInfo categorySync(Integer categoryId,Boolean isDel);
	
	/**
	 * <b>Description:</b><br> 订单同步（非订货订单）
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月16日 上午8:49:16
	 */
	public ResultInfo orderSyncWeb(Integer saleorderId);
	
	/**
	 * <b>Description:</b><br> 消息通知
	 * @param type 1报价 2 订单
	 * @param orderId 订单\报价 ID
	 * @param messageType 报价传0，订单 1审核 2付款 3发货 4签收 5收货
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月17日 下午2:42:14
	 */
	public ResultInfo messageSyncWeb(Integer type, Integer orderId, Integer messageType);
	
	/**
	 * <b>Description:</b><br> 分类属性
	 * @param categoryAttributeId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月19日 下午4:15:18
	 */
	public ResultInfo categoryAttrSync(Integer categoryAttributeId);

	/**
	 * <b>Description:</b><br> 消息通知(批量签收)
	 * @param saleorderNos
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月22日 上午10:23:49
	 */
	public ResultInfo messageBtachSignSyncWeb(List<SaleorderVo> saleorderList);
	
	/**
	 * <b>Description:</b><br> 销售合同、送货单同步
	 * @param attachmentId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月26日 下午3:11:11
	 */
	public ResultInfo saleorderAttachmentSyncWeb(Integer attachmentId,Boolean isDel);

	/**
	 * 入库附件同步
	 * <b>Description:</b><br> 
	 * @param attachmentId
	 * @param isDel
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年8月1日 上午11:32:34
	 */
	public ResultInfo warehouseinAttachmentSyncWeb(Integer attachmentId,Boolean isDel);
	/**
	 * <b>Description:</b><br> 报价转订单（通知网站）
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月28日 下午2:47:20
	 */
	public ResultInfo quoteoderToOrderSync(Integer quoteorderId);
	/**
	 * 
	 * <b>Description:</b><br> 客户开票信息同步到网站
	 * @param traderId
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月23日 上午10:48:10
	 */
	public ResultInfo financeInfoSync(Integer traderId);

	/**
	 * <b>Description:同步贝登官网贝登精选信息</b><br>
	 *
	 *
	 * @param :
	 * webAccount 贝登官网用户对象
	 * @return :com.vedeng.common.model.ResultInfo
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/15 6:52 PM
	 */
	public ResultInfo webAccountIsVedengJxSync(WebAccount webAccount);
	
	/**
	 * <b>Description:发送微信模版消息给指定UUID的注册用户</b><br>
	 * 
	 *
	 * @param :[messageInfo, wxTemplateNo, uuid]
	 * messageInfo 消息内部参数
	 * wxTemplateNo 消息模版号
	 * uuid 注册用户的UUID
	 * @return :com.vedeng.common.model.ResultInfo
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/20 11:41 AM
	 */
	public ResultInfo sendWxMessage(Map messageInfo,Integer wxTemplateNo,String uuid);

	/**
	 * 功能描述: 商品7天无理由退换货字段同步
	 * @param: [goodsId, isNoReasonReturn]
	 * @return: com.vedeng.common.model.ResultInfo
	 * @auther: duke.li
	 * @date: 2019/6/24 19:51
	 */
	public ResultInfo goodsIsNoReasonReturnSync(Integer goodsId,Integer isNoReasonReturn);
}
