package com.vedeng.logistics.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.vedeng.logistics.model.*;
import com.vedeng.order.model.Saleorder;
import org.apache.ibatis.annotations.Param;


@Named("expressMapper")
public interface ExpressMapper {
    /**
     * 
     * <b>Description:</b><br>  查询所有的出库订单物流信息
     * @param express
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月30日 下午5:09:00
     */
	List<Express> getExpressInfoList(Express express);

	/**
	* @Title: getLendOutExpressInfo
	* @Description: TODO(查看外接单物流信息)
	* @param @param express
	* @return List<Express>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月29日
	*/
	List<Express> getLendOutExpressInfo(Express express);
	
	
	/** 
	* @Description: 发货提醒参数接口 
	* @Param: [express] 
	* @return: java.util.List<com.vedeng.logistics.model.ShipmentToRemind> 
	* @Author: addis
	* @Date: 2019/9/25 
	*/ 
/*	List<ShipmentToRemind> shipmentToRemind(Express express);*/

    
    /**
    * @Description: 根据快递单号查询商品的数量和订货号
    * @Param: 
    * @return: 
    * @Author: addis
    * @Date: 2019/11/9
    */ 
	List<LogisticsOrderGoodsData> selectExpressGood(@Param(value = "expressId") Integer expressId);

	Integer batchInsert(List<LogisticsDetail> logisticsDetailList);
	/**
	*获取当前订单快递详情
	* @Author:strange
	* @Date:19:45 2019-12-30
	*/
	List<ExpressDetail> getExpressDetailList(Saleorder saleorder);
	//20.19-12-31
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
	 * 查询是否第一次物流
	 */
	List<Express> getFirst(Integer traId);
	//改变是否开据发票状态
    int changeIsinvoicing(Integer invoiceApplyId);
	//改变是否开据发票状态
    int updateIsinvoicing(Integer expressId);

	/**
	 * 更新快递单的签收状态
	 * @param express
	 */
	void updateExpressArrivalStatusById(Express express);

	/**
	 * 获取已经签收的商品数量
	 * @return
	 */
	Integer getSaleorderGoodCountHasReceived(Integer expressDetailId);

	/**
	 * 获取已经发送的商品数量
	 * @return
	 */
	Integer getSaleorderGoodsNumOfExpress(Integer saleorderGoodsId);
	//改变是否开据发票状态
    int updateIsinvoicingNo(Integer expressId);

	Express getSEGoodsNum(Express express);
	/**
	*获取未删除快递单id
	* @Author:strange
	* @Date:14:58 2020-02-10
	*/
    Integer getExpressIdByWlogId(Integer wlogId);
}