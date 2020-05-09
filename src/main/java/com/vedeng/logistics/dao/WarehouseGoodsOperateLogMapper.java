package com.vedeng.logistics.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.order.model.Saleorder;

import org.apache.ibatis.annotations.Param;

import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import org.springframework.web.bind.annotation.RequestParam;


public interface WarehouseGoodsOperateLogMapper {
    


	/**
     * 
     * <b>Description:</b><br> 采购入库信息
     * @param wg
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月2日 上午10:43:00
     */
	WarehouseGoodsOperateLog getWOLByC(WarehouseGoodsOperateLog wg);
    /**
     * 
     * <b>Description:</b><br> 销售售后的采购信息
     * @param wg
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月2日 上午10:46:43
     */
	WarehouseGoodsOperateLog getWOLByS(WarehouseGoodsOperateLog wg);
    /**
     * 
     * <b>Description:</b><br> 采购售后的采购信息
     * @param wg
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月2日 下午1:50:19
     */
	WarehouseGoodsOperateLog getWOLByCH(WarehouseGoodsOperateLog wg);

	/**
	* @Description: 修改库存问题
	* @Param: [wg]
	* @return: java.lang.Integer
	* @Author: addis
	* @Date: 2019/8/19
	*/
	Integer updateWarehouse(@Param("wg") List<WarehouseGoodsOperateLogVo> wg);

	/**	
	* @Description: TODO(首营的信息 产品注册证号/者备案凭证编号 ,生产企业,生产企业许可证号/备案凭证编号	,储运条件)
	* @param @param woList
	* @param @return   
	* @return List<WarehouseGoodsOperateLog>   
	* @author strange
	* @throws
	* @date 2019年9月30日
	*/
	List<WarehouseGoodsOperateLog> getfirstRegistrationInfo(List<WarehouseGoodsOperateLog> woList);
    
	/** 
	* @Description: 查询有问题goodid 
	* @Param: [warehouseGoodsOperateLogVo] 
	* @return: java.util.List<com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo> 
	* @Author: addis
	* @Date: 2019/8/20 
	*/ 
	List<WarehouseGoodsOperateLogVo> getWarehouseGoodsId(WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo);

	 /**
     * 
     * <b>Description:</b><br> 获取换货已退回或已重发数量
     * @param wl
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年9月12日 下午6:11:09
     */
	Integer getAftersalesGoodsSum(WarehouseGoodsOperateLog wl);

	/**
	* @Title: getWarehouseLendOutList
	* @Description: TODO(获取商品外借单出库记录)
	* @param @param saleorder
	* @return List<WarehouseGoodsOperateLog>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月29日
	*/
	List<WarehouseGoodsOperateLogVo> getWarehouseLendOutList(Saleorder saleorder);
	
	/**	
	* @Description: TODO(外借单信息)
	* @param @param w
	* @param @return   
	* @return WarehouseGoodsOperateLog   
	* @author strange
	* @throws
	* @date 2019年9月18日
	*/
	WarehouseGoodsOperateLog getLendoutByL(WarehouseGoodsOperateLog w);
	/**
	* 获取出入库记录
	 * @Author:strange
	* @Date:16:33 2019-11-13
	*/
	WarehouseGoodsOperateLog getWarehouseInfoById(Integer warehouseGoodsOperateLogId);
	/**
	*获取使用过此条码的出库记录
	* @Author:strange
	* @Date:14:44 2019-12-16
	*/
    List<WarehouseGoodsOperateLog> getBarcodeIsEnable(WarehouseGoodsOperateLog wl);
	/**
	*获取未关联快递详情出入库日志id
	* @Author:strange
	* @Date:13:27 2020-02-10
	*/
    List<Integer> getWarehouseIdByExpressDetail(ExpressDetail expressDetail);
	/**
	*保存快递关联出入库记录关系
	* @Author:strange
	* @Date:14:07 2020-02-10
	*/
	int insertExpressWarehouse(@Param("warehouseLogId") Integer warehouseLogId,@Param("expressDetailId") Integer expressDetailId);
	/**
	*更新重复出入库id无效
	* @Author:strange
	* @Date:15:38 2020-02-10
	*/
	void updateExpressWarehouse(Integer warehouseLogId);
	/**
	 *获取关联快递的出入库记录id
	 * @Author:strange
	 * @Date:19:28 2020-02-10
	 */
    List<Integer> getExpressWlogIds(Integer expressId);
	/**
	*获取最后出库时间
	* @Author:strange
	* @Date:09:37 2020-02-25
	*/
    Long getLastOutTime(WarehouseGoodsOperateLog w);
	/**
	 *获取订单下有效出库记录
	 * @Author:strange
	 * @Date:13:48 2020-02-26
	 */
    List<Integer> getWarehouseLogIdBy(Integer saleorderId);

    int getWarehouseoutRecordCounts(@Param("saleorderId") int saleorderId);


    /**
    * @Description: 查询该商品可用的库存量
    * @Param:
    * @return:
    * @Author: addis
    * @Date: 2020/3/25
    */
    List<WarehouseGoodsOperateLogVo> getSameBatchGoodsInfo(WarehouseGoodsOperateLogVo warehouseGoodsOperateLogVo);
	/**
	*获取日志记录的库位信息
	* @Author:strange
	* @Date:11:34 2020-03-24
	*/
	WarehouseGoodsOperateLog getStorageInfo(Integer warehouseGoodsOperateLogId);
}