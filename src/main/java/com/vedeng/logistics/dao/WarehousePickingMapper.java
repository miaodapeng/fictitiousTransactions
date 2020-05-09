package com.vedeng.logistics.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.logistics.model.WarehousePicking;
import com.vedeng.order.model.Saleorder;

@Named("warehousePickingMapper")
public interface WarehousePickingMapper {
	
    int deleteByPrimaryKey(Integer warehousePickingId);

    int insert(WarehousePicking record);

    int insertSelective(WarehousePicking record);

    WarehousePicking selectByPrimaryKey(WarehousePicking record);

    int updateByPrimaryKeySelective(WarehousePicking record);

    int updateByPrimaryKey(WarehousePicking record);
    /**
     * 
     * <b>Description:</b><br> 当前订单下的拣货列表清单
     * @param saleorder
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月11日 上午11:32:57
     */
	List<WarehousePicking> getWarehousePickList(Saleorder saleorder);
    /**
     * 
     * <b>Description:</b><br> 保存拣货订单
     * @param wp
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月24日 上午9:16:04
     */
	int savePickOrder(WarehousePicking wp);
    /**
     * 
     * <b>Description:</b><br> 根据订单id查询拣货单
     * @param saleorderId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月24日 下午2:09:14
     */
	WarehousePicking getPickOrder(Integer saleorderId);
    /**
     * 
     * <b>Description:</b><br> 更新拣货订单
     * @param wp
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月24日 下午2:14:40
     */
	int upPickOrder(WarehousePicking wp);
    /**
     * <b>Description:</b><br> 打印拣货单
     * @param wp
     * @return
     */
	List<WarehousePicking> getWarehousepickListById(WarehousePicking wp);

	/**
	* @Title: getWarehouseLendOutPickList
	* @Description: TODO(当前外借单下的拣货列表清单)
	* @param @param saleorder
	* @param @return    参数
	* @return List<WarehousePicking>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月29日
	*/
	List<WarehousePicking> getWarehouseLendOutPickList(Saleorder saleorder);

	/**
	* @Title: getLendOutPickCnt
	* @Description: TODO(获取外接单商品拣货数)
	* @param @param lendOutId
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年9月2日
	*/
	Integer getLendOutPickCnt(@Param("lendOutId")Integer lendOutId,@Param("goodsId")Integer goodsId ,@Param("orderType")Integer orderType);
}