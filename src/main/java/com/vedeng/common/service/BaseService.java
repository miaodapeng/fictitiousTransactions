package com.vedeng.common.service;

import java.util.List;

import com.vedeng.system.model.SysOptionDefinition;

/**
 * <b>Description:</b><br>
 * 基础service 接口
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.service <br>
 *       <b>ClassName:</b> BaseService <br>
 *       <b>Date:</b> 2017年4月25日 上午11:13:50
 */
public interface BaseService {

	/**
	 * <b>Description:</b><br>
	 * 根据父id查询子列表
	 * 
	 * @param parentId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年9月15日 上午9:21:48
	 */
	List<SysOptionDefinition> getSysOptionDefinitionListByParentId(Integer parentId);

	/**
	 * <b>Description:</b><br>
	 * 查询字典表对象
	 * 
	 * @param SysOptionDefinitionId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年9月15日 上午11:01:34
	 */
	SysOptionDefinition getSysOptionDefinitionById(Integer SysOptionDefinitionId);

	String getApiUri();

	List<SysOptionDefinition> getSysOptionDefinitionList(String optionType);

	SysOptionDefinition getFirstSysOptionDefinitionList(String optionType);


	/**
	 * 1.订单生成<p>-
	 * 2.订单生效审核<p>-
	 * 3.订单结款<p>-
	 * 4.订单出库<p>-
	 * 5.订单完结<p>订单完结逻辑在db开票及快递签收中因此可以合并.方法为saleorderDataService.getIsEnd
	 * 6.订单关闭<p>-
	 * 7.订单快递签收<p>-
	 * 8.订单开票<p>-自动纸质票开票方法:ST_INVOICE,为航信软件直调db
	 * 9.订单编辑<p>-
	 * 10.订单商品编辑<p>-
	 * 11.生成关联采购单<p>-
	 * 12.销售单关联退货换货售后单操作<p>-
	 * 更新销售单updateDataTime
	 * @param operateType 和上述编号一致
	 *  @Author:strange
	 *  @Date:10:15 2020-04-06
	 */
	void updateSaleOrderDataUpdateTime(Integer orderId, Integer orderDetailId, String operateType) ;
	/**
	 * 1.备货单生成<p>-
	 * 2.采购单生成-更新销售单<p>-
	 * 3.采购单审核<p>-
	 * 4.采购单付款<p>-
	 * 5.采购单入库<p>-
	 * 6.采购单完成<p>
	 * 7.采购单关闭<p>-
	 * 8.采购录票<p>-
	 * 9.采购单编辑<p>-
	 * 10.采购单确认收货<p>-
	 * 11.采购单关联售后单操作<p>-
	 * 更新采购单updateDataTime
	 * @param operateType 和上述编号一致
	 * @Author:strange
	 * @Date:10:15 2020-04-06
	 */
	void updateBuyOrderDataUpdateTime(Integer orderId,Integer orderDetailId,String operateType) ;

	/**
	 *处理售后换货,售后退货
	 * 采购单和销售单售后情况
	 * 1.生成售后单<p>-
	 * 2.售后单生效审核<p>-
	 * 3.销售换货入库<p>-
	 * 4.销售换货出库<p>-
	 * 5.销售退货入库<p>-
	 * 6.采购退货出库<p>-
	 * 7.采购换货出库<p>-
	 * 8.采购换货入库<p>-
	 * 9.售后单执行退款运算<p>-
	 * 10.售后单完结<p>-
	 * 11.售后单关闭<p>-
	 * 12.售后单结款<p>-
	 * 13.售后录票<p>-
	 * 更新售后单updateDataTime
	 * @param operateType 和上述编号一致
	 * @Author:strange
	 * @Date:10:15 2020-04-06
	 */
	void updateAfterOrderDataUpdateTime(Integer afterSaleOrderId,Integer afterSaleOrderDetailId,String operateType) ;
	//客户
	void updateTraderDataUpdateTime(Integer traderId,String operateType) ;

}
