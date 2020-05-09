package com.vedeng.logistics.service.impl;
import	java.util.stream.Collectors;

import com.rabbitmq.MsgProducer;
import com.rabbitmq.RabbitConfig;
import com.vedeng.aftersales.dao.AfterSalesGoodsMapper;
import com.vedeng.common.constant.stock.StockOperateTypeConst;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.logistics.dao.WarehouseGoodsOperateLogMapper;
import com.vedeng.logistics.dao.WarehouseGoodsStatusMapper;
import com.vedeng.logistics.dao.WarehouseStockMapper;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.stock.api.stock.dto.ActionStockDto;
import com.vedeng.stock.api.stock.dto.StockInfoDto;
import com.vedeng.stock.api.stock.dto.WarehouseDto;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("warehouseStockService")
public class WarehouseStockServiceImpl extends BaseServiceimpl implements WarehouseStockService{
    Logger logger= LoggerFactory.getLogger(WarehouseStockServiceImpl.class);
    @Autowired
    @Qualifier("warehouseGoodsStatusMapper")
    private WarehouseGoodsStatusMapper warehouseGoodsStatusMapper;

    @Autowired
    @Qualifier("warehouseStockMapper")
    private WarehouseStockMapper warehouseStockMapper;

    @Resource
    private SaleorderMapper saleorderMapper;

    @Resource
    private SaleorderGoodsMapper saleorderGoodsMapper;

    @Resource
    private WarehouseGoodsOperateLogMapper warehouseGoodsOperateLogMapper;

    @Resource
    private AfterSalesGoodsMapper afterSalesGoodsMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Autowired
    private MsgProducer msgProducer;

    /**
     *更新库存服务占用数量
     * @Author:strange
     * @Date:14:23 2019-11-15
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateOccupyStockService(Saleorder s, int i) {
        if(s.getSaleorderId()==null){
            return 0;
        }
        StockInfoDto stockInfoDto = new StockInfoDto();
        //业务类型
        if(s.getOperateType()!=null){
            stockInfoDto.setOccupyType(s.getOperateType());
        }
        Saleorder saleOrder = saleorderMapper.getSaleOrderById(s.getSaleorderId());
        //获取商品占用信息
        Map<String, Integer> map = updateSaleorderOccupyNum(saleOrder, 0);
        Map<String, Integer> actionMap = new HashMap<> (16);

        List<WarehouseDto> warehouseStockList = new ArrayList<>();
        //为活动订单
        if(saleOrder.getActionId() > 0){
            //获取当前活动商品变化的活动占用数量
            actionMap = updateSaleorderActionOccupyNum(saleOrder,0);
            if(!org.springframework.util.CollectionUtils.isEmpty(actionMap)) {
                Set<String> actionsku = actionMap.keySet();
                //将活动变化量存入实体类中
                for (String s2 : actionsku) {
                    WarehouseDto warehouseStock = new WarehouseDto();
                    Integer actionNum = actionMap.get(s2);
                    if(actionNum==null||actionNum==0){
                        continue;
                    }
                    warehouseStock.setActionId(saleOrder.getActionId());
                    warehouseStock.setSku(s2);
                    warehouseStock.setActionOccupyNum(actionNum);
                    warehouseStockList.add(warehouseStock);
                }
            }
        }

        if(org.springframework.util.CollectionUtils.isEmpty(map) && org.springframework.util.CollectionUtils.isEmpty(actionMap)){
            return 0;
        }
        //将商品变化量存入实体类中
        Set<String> sku = map.keySet();
        for (String s1 : sku) {
            WarehouseDto warehouseStock = new WarehouseDto();
            Integer num = map.get(s1);
            if(num==null||num==0){
                continue;
            }
            warehouseStock.setSku(s1);
            warehouseStock.setOccupyNum(num);
            warehouseStockList.add(warehouseStock);
        }
        stockInfoDto.setWarehouseStockList(warehouseStockList);
        stockInfoDto.setRelatedId(s.getSaleorderId());
        String mqonlykey = s.getSaleorderId()+","+"occupy"+"#"+System.currentTimeMillis()+"#"+UUID.randomUUID().toString();
        stockInfoDto.setMqOnlyKey(mqonlykey);
        try {
            String json = JsonUtils.translateToJson(stockInfoDto);
            msgProducer.sendMsg(RabbitConfig.STOCK_SERVICE_EXCHANGE,RabbitConfig.STOCK_SERVICE_OCCUPY_ROUTINGKEY,json);
           // logger.info("更新库存服务占用数量发送消息发送成功json:{}",json);
        }catch (Exception e){
            logger.error("更新库存服务占用数量失败:",e);
            return 0;
        }

        return 1;
    }

    /**
     *更新库存服务库存量
     * @Author:strange
     * @Date:14:22 2019-11-15
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStockNumService(List<WarehouseGoodsOperateLog> wlogList) {
        //获取库存变化量
        List<WarehouseStock> list =  getWarehouseGoodsOperatelogInfo(wlogList);
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        //判断是否更改占用数量
        updateOccupyOnStock(wlogList.get(0));

        List<WarehouseDto> warehouseStockList = new ArrayList<>();
        //将库存变化量存入实体类中
        for (WarehouseStock warehouseStock : list) {
            WarehouseDto warehouseDto = new WarehouseDto();
            warehouseDto.setSku(warehouseStock.getSku());
            warehouseDto.setActionId(warehouseStock.getActionId());
            //是活动商品
            if(warehouseStock.getActionId() > 0){
                warehouseDto.setActionLockNum(warehouseStock.getActionLockNum());
                warehouseDto.setStockNum(warehouseStock.getActionLockNum());
            }else{
                warehouseDto.setStockNum(warehouseStock.getStockNum());
            }
            warehouseStockList.add(warehouseDto);
        }

        StockInfoDto stockInfoDto = new StockInfoDto();
        String id = null;
        for (WarehouseGoodsOperateLog wlog : wlogList) {
            Integer warehouseGoodsOperateLogId = wlog.getWarehouseGoodsOperateLogId();
            if(warehouseGoodsOperateLogId==null){
                id=wlog.getRelatedId().toString()+":orderid:";
                stockInfoDto.setRelatedId(wlog.getRelatedId());
            }else{
                id = warehouseGoodsOperateLogId.toString()+"logid";
                stockInfoDto.setRelatedId(warehouseGoodsOperateLogId);
            }
        }
        stockInfoDto.setWarehouseStockList(warehouseStockList);
        String mqonlykey =id+","+"stock"+"#"+System.currentTimeMillis()+"#"+UUID.randomUUID().toString();
        stockInfoDto.setMqOnlyKey(mqonlykey);
        //业务类型
        stockInfoDto.setStockType(wlogList.get(0).getOperateType());
        try {
            String json = JsonUtils.translateToJson(stockInfoDto);
            msgProducer.sendMsg(RabbitConfig.STOCK_SERVICE_EXCHANGE,RabbitConfig.STOCK_SERVICE_STOCKNUM_ROUTINGKEY,json);
            //logger.info("更新库存服务库存量发送消息发送成功json:{}",json);
        }catch (Exception e){
            logger.error("更新库存服务库存量失败:{}",e);
            return 0;
        }
        return 1;
    }
    /**
    *将库存信息保存入库存服务
    * @Author:strange
    * @Date:14:25 2019-11-15
    */
    @Override
    public String insertNewStock() {
       //订单表符合goodsid
        List<WarehouseStock> stockNum = warehouseGoodsStatusMapper.getAllStockId();
        List<String> skulist = new ArrayList<>();
        List<Integer> goodsId = new ArrayList<>();
        HashMap < String,WarehouseStock> map = new HashMap<>();
        if(stockNum!=null){
            for (WarehouseStock goodsData : stockNum) {
                if(goodsData.getSku()!=null){
                    skulist.add(goodsData.getSku());
                    map.put(goodsData.getSku(),goodsData);
                }
                if(goodsData.getGoodsId()!=null) {
                    goodsId.add(goodsData.getGoodsId());
                }
            }
        }
        //库存量
        List<WarehouseStock> stockNum1 = warehouseGoodsStatusMapper.getStockNumByGoodsId(goodsId);
        for (WarehouseStock s : stockNum1) {
            if(s.getSku()!=null) {
                WarehouseStock warehouseStock = map.get(s.getSku());
                warehouseStock.setStockNum(s.getStockNum());
                map.put(s.getSku(), warehouseStock);
            }
        }
        //sku的占用库存
        HashMap<String,Integer> occupyMap = getOccupyNumBySku(skulist);
        Date time = new Date();
        int i = 0;
        for (WarehouseStock warehouseStock : stockNum) {
            if(warehouseStock.getSku()!=null) {
                WarehouseStock w2 = map.get(warehouseStock.getSku());
                Integer occupyNum = occupyMap.get(warehouseStock.getSku());
                if(occupyNum==null){
                    occupyNum=0;
                }
                w2.setOccupyNum(occupyNum);
                w2.setAddTime(time);
                i++;
            }
        }

        Integer count = 0;
        String re=null;
        do {
            List<WarehouseStock> stocklist = new ArrayList<>();
            for (int k = count; k < count+200; k++) {
                if(k==stockNum.size()){
                    break;
                }
                stocklist.add(stockNum.get(k));
            }
            try {
                String json = JsonUtils.translateToJson(stocklist);
                String url = stockUrl + "/stock/addStockInfo";
                JSONObject result2 = NewHttpClientUtils.httpPost(url, json);
                re=result2.toString();
                logger.info("库存数据迁移:{}" + result2.toString());
            } catch (Exception e) {
                logger.error("库存数据迁移失败", e);

            }
            count+=200;
        }while (count < stockNum.size());
        return re;
    }

    /**
    *更新订单内占用数量
    * @Author:strange
    * @Date:14:24 2019-11-15
    */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String,Integer> updateSaleorderOccupyNum(Saleorder s,Integer i) {
        List<Integer> saleorderidList = new ArrayList<>();
        //当i为1时查询所有符合条件订单
        if(i.equals(1)) {
            saleorderidList = saleorderMapper.getSaleorderidByStatusLimit(s.getOrgId());
        }else{
            if(s.getSaleorderId()!=null) {
                saleorderidList.add(s.getSaleorderId());
            }else{
                return null;
            }
        }
        //logger.info("updateSaleorderOccupyNum订单id为:{}",saleorderidList.toString());
        //库存占用数量
        Map<String,Integer> occupyNumMap = new HashMap<>();
        int j = 0;
        if(!CollectionUtils.isEmpty(saleorderidList)) {
            for (Integer saleorderId : saleorderidList) {
                //获取新的占用数量
                Map<String, Integer> occupymap = getOccupyNum(saleorderId);
                s.setSaleorderId(saleorderId);
                List<SaleorderGoods> saleordergoodsList = saleorderGoodsMapper.getSaleordergoodsOccupyNumList(s);
                for (SaleorderGoods saleorderGoods : saleordergoodsList) {
                        String sku = saleorderGoods.getSku();
                        //新占用数量
                        Integer newnum = occupymap.get(sku);
                        if (newnum == null) {
                            newnum = 0;
                        }
                        Integer oldNum = saleorderGoods.getOccupyNum();
                        if (oldNum.equals(newnum)) {
                            continue;
                        }
                        Integer occupy = occupyNumMap.get(sku);
                        if (occupy == null) {
                            occupy = 0;
                        }
                        //将变化量存入map
                        occupyNumMap.put(sku, occupy + newnum - oldNum);
                        saleorderGoods.setOccupyNum(newnum);
                        logger.info("updateSaleorderOccupyNum订单id为:{},占用量为:{}",saleorderId,newnum);
                    int k = saleorderGoodsMapper.updateOccupyNum(saleorderGoods);
                    j += k;
                }
            }
        }
        logger.info("总计更新:"+j+"条数据");
        logger.info("updateSaleorderOccupyNum更新结束返回数据:{}",occupyNumMap.keySet(),occupyNumMap.values());
        return occupyNumMap;
    }
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Map<String, Integer> updateSaleorderActionOccupyNum(Saleorder saleorder, int i) {
        List<Integer> saleorderidList = new ArrayList<>();
        //当i为1时查询所有活动订单
        if(i == 1) {
            saleorderidList = saleorderMapper.getActionOrderLimit(saleorder.getOrgId());
        }else{
            if(saleorder.getSaleorderId() != null) {
                saleorderidList.add(saleorder.getSaleorderId());
            }else{
                return null;
            }
        }
        Map<String ,Integer> actionOccupyNumMap = new HashMap<> (16);
        if(CollectionUtils.isNotEmpty(saleorderidList)){
            for (Integer saleorderId : saleorderidList) {
                saleorder = saleorderMapper.getSaleOrderById(saleorderId);
                //活动占用数量
                if(saleorder.getActionId() == 0){
                    continue;
                }
                //获取活动占用数量
                Map<String, Integer> actionmap = getActionOccupyNum(saleorder.getSaleorderId());
                List<SaleorderGoods> saleordergoodsList = saleorderGoodsMapper.getActionGoodsList(saleorder);
                for (SaleorderGoods saleorderGoods : saleordergoodsList) {
                    //为活动商品
                    if(saleorderGoods.getIsActionGoods() > 0){
                        String sku = saleorderGoods.getSku();
                        //新活动占用数量
                        Integer newnum = actionmap.get(sku);
                        if (newnum == null) {
                            newnum = 0;
                        }
                        Integer oldNum = saleorderGoods.getActionOccupyNum();
                        if (oldNum.equals(newnum)) {
                            continue;
                        }
                        Integer actionOccupyNum = actionOccupyNumMap.get(sku);
                        if (actionOccupyNum == null) {
                            actionOccupyNum = 0;
                        }
                        //将变化量存入map
                        actionOccupyNumMap.put(sku, actionOccupyNum + newnum - oldNum);
                        saleorderGoods.setActionOccupyNum(newnum);
                        logger.info("updateSaleorderActionOccupyNum:{},活动占用量为:{}",saleorder.getSaleorderId(),newnum);
                        int k = saleorderGoodsMapper.updateOccupyNum(saleorderGoods);
                    }
                }
            }
        }
        logger.info("updateSaleorderActionOccupyNum更新结束返回数据:{}",actionOccupyNumMap.keySet(),actionOccupyNumMap.values());
        return actionOccupyNumMap;
    }


    public Map<String, Integer> getActionOccupyNum(Integer saleorderId) {
        Saleorder saleOrder = saleorderMapper.getSaleOrderById(saleorderId);
        //是否为活动订单
        Map<String,Integer> map = new HashMap<>(16);
        if(saleOrder.getActionId() == 0){
            return map;
        }
        logger.info("获取活动占用数量开始查询订单id为:{}",saleorderId);
        List<WarehouseStock> warehouseStocks = warehouseStockMapper.getActionOccupyNum(saleorderId);
        if(CollectionUtils.isNotEmpty(warehouseStocks)){
            for (WarehouseStock warehouseStock : warehouseStocks) {
                map.put(warehouseStock.getSku(),warehouseStock.getActionOccupyNum());
            }
        }
        logger.info("获取活动占用数量订单id为:{},结果:{}",saleorderId,map);
        return map;
    }

    /**
    *   获取订单sku的占用数量
    * @Author:strange
    * @Date:14:24 2019-11-15
    */
    public HashMap<String, Integer> getOccupyNumBySku(List<String> sku) {
        HashMap<String,Integer > map = new HashMap<>();
       List<WarehouseStock> list =  warehouseStockMapper.getOrderOccupyNumBySku(sku);
       if(!CollectionUtils.isEmpty(list)) {
           for (WarehouseStock warehouseStock : list) {
               map.put(warehouseStock.getSku(), warehouseStock.getOccupyNum());
           }
       }
        return  map;
    }

    /**
    *   通过订单id获取此订单当前占用数量
    * @Author:strange
    * @Date:14:23 2019-11-15
    */
    @Override
    public Map<String, Integer> getOccupyNum(Integer saleorderId) {
      //  logger.info("获取占用数量开始查询订单id为:{}",saleorderId);
        Map<String,Integer> map = new HashMap<>();
        if(ObjectUtils.isEmpty(saleorderId)){
            return null;
        }
        Saleorder order = saleorderMapper.getSaleOrderById(saleorderId);
        List<WarehouseStock> orderoccupyList;
        //售后换货订单占用数量
        List<WarehouseStock> afteroccupyList;
        if(order!= null &&order.getOrderType() != 5) {
            //非耗材订单占用数量
            orderoccupyList = warehouseStockMapper.getOrderOccupyNum(saleorderId);
        }else{
            //耗材订单占用数量
            orderoccupyList =  warehouseStockMapper.getHCorderOccupyNum(saleorderId);
        }
        afteroccupyList = warehouseStockMapper.getAfterOccupyNum(saleorderId);
        if(orderoccupyList!=null && orderoccupyList.size() > 0){
            for (WarehouseStock w : orderoccupyList) {
                String sku = w.getSku();
                Integer occupyNum = w.getOccupyNum();
                map.put(sku,occupyNum);
            }
        }
        //如果存在售后换货将占用数量计入
        if(afteroccupyList!=null&&afteroccupyList.size() > 0){
            for (WarehouseStock a : afteroccupyList) {
                String sku = a.getSku();
                Integer afterOccupyNum = a.getOccupyNum();
                Integer occupyNum = map.get(sku);
                if(occupyNum == null){
                   occupyNum = 0;
                }
                map.put(sku,afterOccupyNum+occupyNum);
            }
        }
       // logger.info("getOccupyNum获取占用数量key为:"+map.keySet()+"value为:"+map.values());
        return map;
    }


    /**
    *获取库存变化量
    * @Author:strange
    * @Date:14:22 2019-11-15
    */
    private List<WarehouseStock> getWarehouseGoodsOperatelogInfo(List<WarehouseGoodsOperateLog> wlogList) {
        logger.info("getWarehouseGoodsOperatelogInfo 更新库存量开始:{}",wlogList.toString());
        HashMap<String,Integer> map = new HashMap<>();
        HashMap<String, Integer> actionMap = new HashMap<>();
        List<WarehouseStock> warehouseStockList = new ArrayList<>();
        Integer actionId = 0;

        //1入库 2出库3销售换货入库4销售换货出库5销售退货入库6采购退货出库7采购换货出库8采购换货入库9外借入库 10外借出库
        for (WarehouseGoodsOperateLog log : wlogList) {
            if(log.getRelatedId() == null){
                WarehouseGoodsOperateLog warehouseInfoById = warehouseGoodsOperateLogMapper.getWarehouseInfoById(log.getWarehouseGoodsOperateLogId());
                log.setRelatedId(warehouseInfoById.getRelatedId());
            }
            if(log.getIsActionGoods() != null && log.getIsActionGoods() > 0){
                //活动商品库存变化量
                actionMap =  getStockChangeNum(log,actionMap);
                //获取活动id
                Integer operateType = log.getOperateType();
                if(operateType.equals(StockOperateTypeConst.WAREHOUSE_OUT)){
                    SaleorderGoods sg = saleorderGoodsMapper.getSaleorderGoodsInfoById(log.getRelatedId());
                    Saleorder saleOrder = saleorderMapper.getSaleOrderById(sg.getSaleorderId());
                    actionId=saleOrder.getActionId();

                }else if(operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_CHANGE_IN) || operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_CHANGE_OUT)
                        || operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_BACK_IN)){

                    Integer saleorderId = afterSalesGoodsMapper.getSaleOrderIdByafterSalesGoodsId(log.getRelatedId());
                    Saleorder saleOrder = saleorderMapper.getSaleOrderById(saleorderId);
                    actionId = saleOrder.getActionId();
                }
            }else {
                //普通商品库存变化量
                map = getStockChangeNum(log,map);
            }
        }
        //将变化量存入实体类
        Set<String> action = actionMap.keySet();
        for (String sku : action) {
            WarehouseStock warehouseStock = new WarehouseStock();
            warehouseStock.setSku(sku);
            warehouseStock.setActionLockNum(actionMap.get(sku));
            warehouseStock.setActionId(actionId);
            warehouseStockList.add(warehouseStock);
        }
        Set<String> stock = map.keySet();
        for (String sku : stock) {
            WarehouseStock warehouseStock = new WarehouseStock();
            warehouseStock.setSku(sku);
            warehouseStock.setStockNum(map.get(sku));
            warehouseStock.setActionId(0);
            warehouseStockList.add(warehouseStock);
        }
        logger.info("getWarehouseGoodsOperatelogInfo 更新库存量结束:key为"+map.keySet()+"value为:"+map.values());
        return warehouseStockList;
    }

    public  HashMap<String, Integer> getStockChangeNum(WarehouseGoodsOperateLog log, HashMap<String, Integer> map) {
        //撤销操作
        if (log.getIsEnable().equals(0)) {
            WarehouseGoodsOperateLog wlog = warehouseGoodsOperateLogMapper.getWarehouseInfoById(log.getWarehouseGoodsOperateLogId());
            Integer num = map.get(wlog.getSku());
            if (num == null) {
                num = 0;
                map.put(wlog.getSku(), -wlog.getNum());
            }
            map.put(wlog.getSku(), num - wlog.getNum());

        } else {
            String sku = goodsMapper.getSkuByGoodsId(log.getGoodsId());
            log.setSku(sku);
            Integer num = map.get(log.getSku());
            if (num == null) {
                num = 0;
                map.put(log.getSku(), log.getNum());
            }
            map.put(log.getSku(), num + log.getNum());
        }
        return map;
    }

    /**
    *出入库时更新占用数量
    * @Author:strange
    * @Date:14:21 2019-11-15
    */
    private void updateOccupyOnStock(WarehouseGoodsOperateLog wlog) {
        Integer operateType = wlog.getOperateType();
        Saleorder saleOrder = new Saleorder();
        if(operateType.equals(StockOperateTypeConst.WAREHOUSE_OUT)){
            SaleorderGoods sg = saleorderGoodsMapper.getSaleorderGoodsInfoById(wlog.getRelatedId());
            saleOrder.setSaleorderId(sg.getSaleorderId());
            this.updateOccupyStockService(saleOrder,0);
        }else if(operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_CHANGE_IN)||operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_CHANGE_OUT)
                ||operateType.equals(StockOperateTypeConst.ORDER_WAREHOUSE_BACK_IN)){
            Integer saleorderId = afterSalesGoodsMapper.getSaleOrderIdByafterSalesGoodsId(wlog.getRelatedId());
            saleOrder.setSaleorderId(saleorderId);
            this.updateOccupyStockService(saleOrder,0);
        }
    }
    /**
    * 根据sku获取库存信息
    * @Author:strange
    * @Date:09:29 2019-11-20
    */
    @Override
    public Map<String, WarehouseStock> getStockInfo(List<String> list) {
        Map<String, WarehouseStock> map2 = new HashMap<>(16);
        try {
            String json = JsonUtils.translateToJson(list);
            String url = stockUrl+"/stock/getStockInfo";
            logger.info("getStockInfo 调用库存服务查询sku:"+list);
            JSONObject result2 = NewHttpClientUtils.httpPost(url, json);
            if(result2 != null) {
                logger.info("getStockInfo 调用库存服务查询结果:{}",result2.toString());
                Map<String, WarehouseStock> map = ((List<Map<String, Object>>) result2.get("warehouseStockList")).stream()
                        .map(map1 -> {
                            WarehouseStock item = new WarehouseStock();
                            item.setSku(map1.get("sku").toString());
                            item.setOccupyNum(Integer.valueOf((map1.get("occupyNum") != null ? map1.get("occupyNum") :0).toString()));//占用数量
                            item.setStockNum(Integer.valueOf((map1.get("stockNum") != null ? map1.get("stockNum") : 0).toString()));//库存量
                            item.setAvailableStockNum(Integer.valueOf((map1.get("availableStockNum") != null ? map1.get("availableStockNum") : 0).toString()));//可用库存
                            item.setActionLockNum(Integer.valueOf((map1.get("actionLockNum") !=null ? map1.get("actionLockNum") : 0).toString()));//活动锁定库存
                            return item;
                        })
                        .collect(Collectors.toMap(WarehouseStock::getSku, item -> item));

                list.forEach(item -> {
                   if(!map.containsKey(item)){
                       WarehouseStock warehouseStock = new WarehouseStock();
                       warehouseStock.setStockNum(0);
                       warehouseStock.setOccupyNum(0);
                       warehouseStock.setAvailableStockNum(0);
                       warehouseStock.setActionLockNum(0);
                       map.put(item,warehouseStock);
                   }
                });

                return map;
            }
        }catch (Exception e){
            logger.error("获取库存信息error:",e);
        }
        list.forEach(item -> {
            if(!map2.containsKey(item)){
                WarehouseStock warehouseStock = new WarehouseStock();
                warehouseStock.setStockNum(0);
                warehouseStock.setOccupyNum(0);
                warehouseStock.setAvailableStockNum(0);
                warehouseStock.setActionLockNum(0);
                map2.put(item,warehouseStock);
            }
        });
        return map2;
    }

    @Override
    public List<Integer> getAllActionId() {
        return saleorderMapper.getAllActionId();
    }

    @Override
    public ActionStockDto getActionGoodsInfo(Integer actionId) {
        SaleorderVo saleorderVo = new SaleorderVo();
        saleorderVo.setActionId(actionId);
        saleorderVo.setType(2);
        ActionStockDto actionStockDto = new ActionStockDto();
        actionStockDto.setActionId(actionId);
        try {
        //获取当前活动下活动商品信息
        List<SaleorderGoods> actionGoodslist = saleorderGoodsMapper.getActionGoodsInfo(saleorderVo);
        List<WarehouseDto> warehouseDtoList = actionGoodslist.stream().map(saleorderGoods -> {
            WarehouseDto warehouseDto =  new WarehouseDto();
            warehouseDto.setSku(saleorderGoods.getSku());
            warehouseDto.setActionOccupyNum(saleorderGoods.getActionOccupyNum());
            warehouseDto.setActionLockNum(saleorderGoods.getDeliveryNum());
            return warehouseDto;
        }).collect(Collectors.toList());

        actionStockDto.setWarehouseDtoList(warehouseDtoList);
        }catch (Exception e){
            logger.error("getActionGoodsInfo error:{}",e);
        }
        return actionStockDto;
    }
}
