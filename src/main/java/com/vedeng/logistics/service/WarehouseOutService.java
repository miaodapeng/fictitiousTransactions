package com.vedeng.logistics.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.model.Saleorder;

/**
 * 
 * <b>Description:</b><br> 出库清单
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.logistics.service
 * <br><b>ClassName:</b> WarehouseOutService
 * <br><b>Date:</b> 2017年8月11日 下午2:52:04
 */
public interface WarehouseOutService extends BaseService {
    /**
     * 
     * <b>Description:</b><br> 当前订单下的出库清单
     * @param saleorder
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月11日 下午2:56:11
     */
	List<WarehouseGoodsOperateLog> getOutDetil(Saleorder saleorder);
    /**
     * 
     * <b>Description:</b><br> 条码出库清单
     * @param saleorder
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月11日 下午4:20:24
     */
	List<WarehouseGoodsOperateLog> getBarcodeOutDetil(Saleorder saleorder);
	/**
	 * 
	 * <b>Description:</b><br> 根据条码查询商品
	 * @param warehouseGoodsOperateLog
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月25日 上午10:20:12
	 */
	WarehouseGoodsOperateLog getSMGoods( WarehouseGoodsOperateLog warehouseGoodsOperateLog);
	/**
	 * 
	 * <b>Description:</b><br> 扫码
	 * @param warehouseGoodsOperateLog
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年9月6日 下午4:43:43
	 */
	List<WarehouseGoodsOperateLog> getSGoods(WarehouseGoodsOperateLog warehouseGoodsOperateLog);
	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param saleorder
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年6月5日 下午2:45:10
	 */
	List<WarehouseGoodsOperateLog> getOutDetilList(Saleorder saleorder);
	/**
	* @Title: saveLendOut
	* @Description: TODO(生成外借单)
	* @param @param lendout
	* @param @return    参数
	* @return LendOut    返回类型
	* @author strange
	* @throws
	* @date 2019年8月26日
	*/
	LendOut saveLendOut(LendOut lendout);
	/**
	* @Title: getLendOutInfo
	* @Description: TODO(获取外接单信息)
	* @param @param lend
	* @return LendOut    返回类型
	* @author strange
	* @throws
	* @date 2019年8月27日
	*/
	LendOut getLendOutInfo(LendOut lend);
	/**
	* @Title: getLendOutDetil
	* @Description: TODO(获取外接单 出库记录清单)
	* @param @param saleorder
	* @return List<WarehouseGoodsOperateLog>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月29日
	*/
	List<WarehouseGoodsOperateLogVo> getLendOutDetil(Saleorder saleorder);
	/**	
	* @Description: TODO获取外接单信息
	* @param @param lendout
	* @param @return   
	* @return List<LendOut>   
	* @author strange
	* @throws
	* @date 2019年9月19日
	*/
	List<LendOut> getLendOutInfoList(AfterSalesVo afterSalesVo);
	/**	
	* @Description: TODO(获取外借单快递发货数量)
	* @param @param lendout
	* @param @return   
	* @return Integer   
	* @author strange
	* @throws
	* @date 2019年9月19日
	*/
	Integer getkdNum(LendOut lendout);
	/**	
	* @Description: TODO(获取外接单出库数量)
	* @param @param lendout
	* @param @return   
	* @return Integer   
	* @author strange
	* @throws
	* @date 2019年9月19日
	*/
	Integer getdeliveryNum(LendOut lendout);
	/**	
	* @Description: TODO(更新外借单已发数量)
	* @param @param lo
	* @param @return   
	* @return Integer   
	* @author strange
	* @throws
	* @date 2019年9月25日
	*/
	Integer updateLendoutDeliverNum(LendOut lendout);
	/**	
	* @Description: TODO(根据入库id获取条码信息)
	* @param @param warehouseGoodsOperateLogId
	* @return Barcode   
	* @author strange
	* @throws
	* @date 2019年9月24日
	*/

	Barcode getBarcodeByWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId);
	/**	
	* @Description: TODO(通过入库条码的id查找入库单号)
	* @param @param barcode
	* @param @return   
	* @return String   
	* @author strange
	* @throws
	* @date 2019年10月17日
	*/
	String getBuyOrderNoByBarcodeId(Barcode barcode);

	/**
	*更新出库单首营信息
	* @Author:strange
	* @Date:17:28 2020-02-07
	*/
	Integer updatefirstRegistraionInfo(List<WarehouseGoodsOperateLog> woList, List<WarehouseGoodsOperateLog> firstInfo, Integer titleType, String type_f, Integer printFlag);
	/**
	*保存快递详情月出入库记录关联关系
	* @Author:strange
	* @Date:11:51 2020-02-10
	*/
    void addExpressDeatilsWarehouse(Express express1);
	/**
	*判断此出入库记录是否关联快递单
	* @Author:strange
	* @Date:14:39 2020-02-10
	*/
    Boolean isEnableExpress(Integer wlogId);
	/**
	*获取关联快递的出入库记录id
	* @Author:strange
	* @Date:19:28 2020-02-10
	*/
    List<Integer> getExpressWlogIds(Integer expressId);
	/**
	*增加出库单中金额信息
	* @Author:strange
	* @Date:17:42 2020-02-11
	*/
	HashMap<String, BigDecimal> addMvAmoutinfo(List<WarehouseGoodsOperateLog> woList , Saleorder saleorder);

	int getWarehouseoutRecordCounts(int saleorderId);
	/**
	*获取最后出库时间
	* @Author:strange
	* @Date:09:34 2020-02-25
	*/
    Long getLastOutTime(WarehouseGoodsOperateLog w);
	/**
	*获取订单下有效出库记录
	* @Author:strange
	* @Date:13:48 2020-02-26
	*/
    List<Integer> getWarehouseLogIdBy(Integer saleorderId);
	/**
	*
	* @Author:strange
	* @Date:17:50 2020-02-26
	*/
    BigDecimal getPrintOutTotalPrice(List<WarehouseGoodsOperateLog> woList);
	/**
	 *组装出库单内出库记录id
	 * @Author:strange
	 * @Date:17:45 2020-02-26
	 */
	List<Integer> getPrintOutIdListByType(Saleorder saleorder);
	/**
	*计算出库单单种商品总价
	* @Author:strange
	* @Date:17:55 2020-02-26
	*/
	List<WarehouseGoodsOperateLog> getPrintOutSkuAmount(List<WarehouseGoodsOperateLog> woList);


	/**
	 * @Description: 查询该商品可用的库存量
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/3/25
	 */
	List<WarehouseGoodsOperateLogVo> getSameBatchGoodsInfo(WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo);

	/**
	 * @Author Strange
	 * @Description //TODO 获取外借单发货数量
	 * @Date 11:14 上午 2020/4/23
	 * @Param
	 * @return
	 **/
	Integer getLendOutdeliveryNum(LendOut lo);

	/**
	 * @Author Strange
	 * @Description //TODO 获取库存量
	 * @Date 11:19 上午 2020/4/23
	 * @Param
	 * @return
	 **/
	Integer getGoodsStockByGoodsStatus(WarehouseGoodsStatus warehouseGoodsStatus);

	/**
	 * @Author Strange
	 * @Description //TODO 更新外借单数据
	 * @Date 11:18 上午 2020/4/23
	 * @Param
	 * @return
	 **/
	Integer updateLendOutInfo(LendOut lendout);

	/**
	 * @Author Strange
	 * @Description //TODO 已快递出库数量
	 * @Date 11:43 上午 2020/4/23
	 * @Param
	 * @return
	 **/
    Integer getLendOutKdNum(LendOut lendout);
}
