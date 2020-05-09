package com.vedeng.logistics.service;

import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.order.model.Saleorder;
import com.vedeng.stock.api.stock.dto.ActionStockDto;

import java.util.List;
import java.util.Map;

/**
*   库存管理
* @Author:strange
* @Date:15:03 2019-11-05
*/
public interface WarehouseStockService extends BaseService {

    String insertNewStock();
    /**
    *更新订单中占用数量(传入订单id更新该订单占用数量,i=1时更新所有订单)
    * @Author:strange
    * @Date:19:10 2019-11-11
     *
    */
    Map<String,Integer> updateSaleorderOccupyNum(Saleorder saleorder ,Integer i);

    /**
    *根据订单id获取此订单商品占用数量
    * @Author:strange
    * @Date:10:24 2019-11-08
    */
    Map<String, Integer> getOccupyNum(Integer saleorderId);

    /**
    *调用库存服务更新占用数量
    * @Author:strange
    * @Date:15:37 2019-11-12
     * @param saleorder 订单信息
     * @param type 业务类型
    */
    int updateOccupyStockService(Saleorder saleorder, int type);

    /**
    *更新库存服务中库存数量
    * @Author:strange
    * @Date:20:00 2019-11-12
    */
    int updateStockNumService(List<WarehouseGoodsOperateLog> wlogList);
    /**
    *调用库存服务得到库存信息
    * @Author:strange
    * @Date:17:26 2019-11-19
    */
    Map<String, WarehouseStock> getStockInfo(List<String> list);
    /**
    *更新活动订单占用数量
    * @Author:strange
    * @Date:16:03 2019-12-05
    */
    Map<String, Integer> updateSaleorderActionOccupyNum(Saleorder saleorder, int i);
    /**
    *获取活动商品出库量和占用数量
    * @Author:strange
    * @Date:10:01 2019-12-23
    */
    ActionStockDto getActionGoodsInfo(Integer actionId);
    /**
    *获取订单所有活动id
    * @Author:strange
    * @Date:13:20 2019-12-23
    */
    List<Integer> getAllActionId();
}
