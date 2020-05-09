package com.vedeng.logistics.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.logistics.model.*;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable;
import com.vedeng.passport.api.wechat.dto.template.TemplateVar;
import com.vedeng.wechat.model.WeChatArr;

/**
 * 
 * <b>Description:</b><br>
 * 物流信息
 * 
 * @author scott
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.logistics.service <br>
 *       <b>ClassName:</b> expressService <br>
 *       <b>Date:</b> 2017年8月14日 上午9:25:10
 */
public interface ExpressService extends BaseService {
	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询物流清单
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年8月14日 上午9:27:30
	 */
	List<Express> getExpressInfo(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 分页查询快递列表
	 * 
	 * @param express
	 * @param page
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年8月31日 下午2:05:36
	 */
	Map<String, Object> getExpressListPage(Express express, Page page);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 获取快递信息
	 * 
	 * @param express
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年7月31日 下午1:30:41
	 */
	List<Express> getExpressList(Express express) throws Exception;
	List<Express> getExpressListNew(Express express) throws Exception;

	/**
	 * 
	 * <b>Description:</b><br>
	 * 保存快递单
	 * 
	 * @param express
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月29日 下午5:34:04
	 */
	ResultInfo<?> saveExpress(Express express);

	/**
	 * 
	 * <b>Description: 批量更新快递信息</b><br>
	 * 
	 * @param epList
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年5月7日 下午1:51:48 </b>
	 */
	ResultInfo<?> batchUpdateExpress(List<Express> epList);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量修改物流
	 * 
	 * @param epList
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年9月4日 下午6:37:31
	 */
	ResultInfo<?> editBatchExpress(List<Express> epList);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 删除快递单
	 * 
	 * @param express
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年9月30日 下午1:42:38
	 */
	ResultInfo<?> delExpress(Express express);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 刷新快递信息
	 * 
	 * @param cyId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月25日 下午4:52:55
	 */
	ResultInfo<?> queryState();

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询未签收的快递单数
	 * 
	 * @param express
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月26日 下午4:33:28
	 */
	Express getCntExpress(Express express);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量更新物流信息
	 * 
	 * @param ldList
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月28日 下午5:25:56
	 */
	ResultInfo<?> editLogisticsDetail(List<LogisticsDetail> ldList);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 更新物流信息
	 * 
	 * @param eL
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月28日 下午5:27:02
	 */
	ResultInfo editExpres(List<Express> eL);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据快递单号查询快递
	 * 
	 * @param ex
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月28日 下午5:27:29
	 */
	Express getExpressInfoByNo(Express ex);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 获取销售产品
	 * 
	 * @param relatedId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月28日 下午5:28:06
	 */
	SaleorderGoods getSaleorderGoodsInfoById(Integer relatedId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据ExpressId查询快递
	 * 
	 * @param ex
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月28日 下午5:27:29
	 */
	Express getExpressInfoById(Express ex);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 将快递单号写入快递表
	 * 
	 * @param express
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年5月16日 上午10:14:38
	 */
	Express updateExpressInfoById(Express express);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据发票获取快递信息
	 * 
	 * @param integer
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年5月16日 下午5:00:26
	 */
	Express getExpressInfoByInvoiceNo(Integer integer);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询物流的快递信息
	 * 
	 * @param express
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年8月17日 下午2:27:59
	 */
	LogisticsDetail getLogisticsDetailInfo(Express express);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据销售订单号获取对应的快递单信息及物流信息（如果是直发则获取对应采购单物流信息）
	 * 
	 * @param saleorderNo
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年8月29日 下午12:59:10
	 */
	List<Express> getExpressListBySaleorderNo(String saleorderNo) throws Exception;

	/**
	 * 
	 * <b>Description:根据快递单Id查询快递单下的商品信息</b>
	 * 
	 * @param expressId
	 * @return List<SaleorderGoods>
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月26日 下午2:34:31
	 */
	List<SaleorderGoods> getSaleorderGoodsListByexpressId(Integer expressId);
	
	/**
	 * <b>Description:订单发货微信消息推送</b><br>
	 * 
	 *
	 * @param :[express]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/20 5:20 PM
	 */
	ResultInfo<?> sendWxMessageForExpress(Saleorder saleOrderInfo, Express express, Map<String, String> wxDataMap);

	/**
	 * <b>Description:订单签收微信消息推送</b><br>
	 *
	 *
	 * @param :[saleorderId]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/21 6:47 PM
	 */
	ResultInfo<?> sendWxMessageForArrival(Integer saleorderId);

	/**
	 * <b>Description:订单签收微信消息数据</b><br>
	 *
	 *
	 * @param :[saleOrderInfo]
	 * @param :[express]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> franlin.wu copy Michael 代码提取 <br>
	 *       <b>Date:</b> 2019/6/24
	 */
	Map<String, String> sendForExpress(Saleorder saleOrderInfo, Express express);

	/**
	* @Title: getLendOutExpressList
	* @Description: TODO(外接单物流信息)
	* @param @param express
	* @return List<Express>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月29日
	*/
	List<Express> getLendOutExpressList(Express express);

    /**
     * @Description: 发货提醒参数接口
     * @Param: [express]
     * @return: java.util.List<com.vedeng.logistics.model.ShipmentToRemind>
     * @Author: addis
     * @Date: 2019/9/25
     */
/*	Map<String, String> shipmentToRemind(Saleorder saleOrderInfo,Express express);*/

	/**
	 * @Description: 定时任务订单关闭 消息推送
	 * @Param: [arr]
	 * @return: void
	 * @Author: addis
	 * @Date: 2019/9/29
	 */

	void sendOrderConfirmedClose(Saleorder saleorders, Map sTempMap);

	/** @description: VDERP-1325 分批开票 查询已收货数量..
	 * @notes: 从dbcenter迁移过来.
	 * @author: Tomcat.Hui.
	 * @date: 2019/11/11 11:28.
	 * @param saleorderIds
	 * @return: com.vedeng.logistics.model.Express.
	 * @throws: .
	 */
	List<ExpressDetail> getSEGoodsNum(List<Integer> saleorderIds);
	/**
	*获取当前订单快递信息
	* @Author:strange
	* @Date:19:12 2019-12-30
	*/
	List<ExpressDetail> getExpressDetailInfoBySaleorderId(Saleorder saleorder);
	/**
	*获取当前订单已发货快递数量
	 * @param saleorder  获取订单id
	* @Author:strange
	* @Date:19:37 2019-12-30
	*/
	Map<Integer, Integer> getExpressDetailNumInfo(Saleorder saleorder);

	/**
	 * 获得满足订单集合
	 * @param express
	 * @return
	 */
	List<Integer> getExpressIds();
	/**
	* 获取当前快递下某商品数量
	* @Author:strange
	* @Date:15:24 2020-01-06
	*/
    ExpressDetail getExpressDetailNumByExpressId(ExpressDetail expressDetail);
	/**
	*获取当前快递单商品详情
	* @Author:strange
	* @Date:08:55 2020-01-07
	*/
	List<ExpressDetail> getExpressDetailByExpressId(Integer expressId);

	/**
	*判断是否需要重新生成开票申请
	* @Author:strange
	* @Date:15:20 2020-01-07
	*/
    boolean isUpdateExpressAndInvoice(Express express);

	/**
	 * 查询是否为第一次物流
	 * @param traId
	 * @return
	 */
	List<Express> getFirst(Integer traId);
	//改变发票是否开据状态
    int changeIsinvoicing(Integer invoiceApplyId);
	//改变发票是否开据状态
	int updateIsinvoicing(Integer expressId);
	//改变发票是否开据状态
    int updateIsinvoicingNo(Integer expressId);
}
