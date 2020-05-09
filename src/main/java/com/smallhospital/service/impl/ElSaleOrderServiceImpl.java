package com.smallhospital.service.impl;

import com.google.common.collect.Lists;
import com.smallhospital.dao.*;
import com.smallhospital.dto.*;
import com.smallhospital.model.ElAfterSalesIntention;
import com.smallhospital.model.ElAfterSalesIntentionDetail;
import com.smallhospital.model.ElSaleOrderDelivery;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.model.vo.ElContractSkuVO;
import com.smallhospital.service.ELContractService;
import com.smallhospital.service.ELContractSkuService;
import com.smallhospital.service.ElSaleOrderService;
import com.smallhospital.service.impl.remote.SynReturnGoodsConformServcice;
import com.smallhospital.service.impl.remote.SynWarehouseOutService;
import com.smallhospital.util.LogisticsUtil;
import com.vedeng.aftersales.dao.AfterSalesDetailMapper;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.dao.CoreSkuGenerateMapper;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.goods.model.CoreSkuGenerateExample;
import com.vedeng.logistics.dao.ExpressMapper;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

/**
 * @Author: Daniel
 * @Description: 小医院订单服务实现类
 * @Date created in 2019/11/21 11:22 上午
 */
@Service
public class ElSaleOrderServiceImpl extends BaseServiceimpl implements ElSaleOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElSaleOrderServiceImpl.class);

    @Autowired
    private ElTraderMapper elTraderMapper;

    @Autowired
    private ElSkuMapper elSkuMapper;


    @Autowired
    private SaleorderMapper saleorderMapper;

    @Autowired
    private ElSaleOrderDeliveryMapper elSaleOrderDeliveryMapper;

    @Autowired
    @Qualifier("saleorderGoodsMapper")
    private SaleorderGoodsMapper saleorderGoodsMapper;

    @Autowired
    private SynWarehouseOutService warehouseOutService;

    @Autowired
    private ELContractService contractService;

    @Autowired
    private ELContractSkuService contractSkuService;

    @Autowired
    @Qualifier("coreSkuGenerateMapper")
    private CoreSkuGenerateMapper coreSkuGenerateMapper;

    @Autowired
    private ExpressMapper expressMapper;

    @Autowired
    private ElAfterSaleIntentionMapper elAfterSaleIntentionMapper;

    @Autowired
    private ElAfterSalesIntentionDetailMapper elAfterSalesIntentionDetailMapper;

    /**
     * 根据订单号查询物流详情
     * 判断该订单号是否为小医院系统合法订单，防止恶意接口调用
     * @param logisticsNo 物流号
     * @param hospitalId 医疗机构id
     * @return 物流详情
     */
    @Override
    public LogisticsDTO queryLogisticsInfo(String logisticsNo, Integer hospitalId) throws Exception{

        String logisticsCode = elTraderMapper.getExpressByLogisticsNo(logisticsNo);
        if(logisticsCode == null){
            throw new Exception("非小医院的快递单,请重试");
        }

        return LogisticsUtil.queryLogisticsInfo(logisticsCode,logisticsNo,1);
    }

    /**
     * 给医流网发送订单出库信息
     * @param express 物流信息
     * @param saleorder 订单信息
     * @return 操作结果
     */
    @Override
    public boolean sendElWareHouseInfo(Express express, Saleorder saleorder) {

        ElWarehouseOutDTO dto = new ElWarehouseOutDTO();
        dto.setSupplyId(express.getExpressId()+"");
        dto.setPurchaserId(String.valueOf(saleorder.getTraderId()));
        dto.setPurchaserName(saleorder.getTraderName());
        dto.setLogisticsNumber(express.getLogisticsNo());
        dto.setLogisticsCompany(express.getLogisticsName());
        dto.setSupplyNumber(saleorder.getSaleorderNo());

        //根据物流单号获取该订单下的商品详情express_detail
        List<ElWarehouseOutDTO.GHDMX> list = new ArrayList<>();
        List<Integer> saleOrderGoodsIdList = express.getExpressDetail().stream().map(ExpressDetail::getRelatedId).collect(Collectors.toList());
        //产品的出库信息
        Map<Integer,WarehouseGoodsOperateLog> warehouseOutMap = elSkuMapper.getWarehouseOutInfoByGoods(saleOrderGoodsIdList)
                    .stream()
                    .collect(Collectors.toMap(WarehouseGoodsOperateLog::getRelatedId, item -> item,(k,v)-> v));

        express.getExpressDetail()
                .forEach(expressDetail -> {
                    com.smallhospital.dto.ElWarehouseOutDTO.GHDMX item = new ElWarehouseOutDTO.GHDMX();
                    item.setSupplyListId(String.valueOf(expressDetail.getExpressDetailId()));

                    //获取saleGoods
                    SaleorderGoods saleorderGoods = this.saleorderGoodsMapper.getSaleorderGoodsInfoById(expressDetail.getRelatedId());
                    item.setOrderListId(saleorderGoods.getElOrderlistId());

                    //根据skuNo查询skuId
                    CoreSkuGenerateExample skuExample = new CoreSkuGenerateExample();
                    skuExample.createCriteria().andSkuNoEqualTo(saleorderGoods.getSku());

                    this.coreSkuGenerateMapper.selectByExample(skuExample).stream()
                            .findFirst()
                            .map(CoreSkuGenerate::getSkuId)
                            .ifPresent(skuId -> {
                                //获取客户合同
                                List<ELContractVO> contractLists = contractService.findByTradeId(saleorder.getTraderId());

                                for(ELContractVO contractVO : contractLists){

                                    ElContractSkuVO contractSku = contractSkuService.findByContractId(contractVO.getElContractId())
                                            .stream()
                                            .filter(sku -> skuId.equals(sku.getSkuId()))
                                            .findFirst()
                                            .orElse(null);

                                    if(contractSku != null){
                                        item.setContractListId(contractSku.getElContractSkuId());
                                        break;
                                    }
                                }
                            });

                    item.setProductId(warehouseOutMap.get(expressDetail.getRelatedId()).getGoodsId());

                    item.setQuantitySupplied(expressDetail.getNum());
                    item.setSupplyId(express.getExpressId()+"");
                    item.setType(1);
                    item.setProductBatchNo(warehouseOutMap.get(expressDetail.getRelatedId()).getBatchNumber());
                    long timestamp = warehouseOutMap.get(expressDetail.getRelatedId()).getExpirationDate();
                    String timeString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
                    item.setBatchValidityDate(timeString);
                    list.add(item);
                });

        dto.setGHDMX(list);

        //同步小医院
        return warehouseOutService.syncData(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmElSaleOrder(ConfirmElSaleOrderDTO dto) {
        LOGGER.info("医流网同步物流信息，{}",dto.toString());
        //获取销售单的id
        Integer saleOrderId = elSaleOrderDeliveryMapper.getSaleOrderIdByExpressId(Integer.valueOf(dto.getSupplyId()));
        long time = DateUtil.gainNowDate();
        //在保存新物流信息之前，先查询之前的指定supplyId的物流信息，由于在同一个事务里，先保存再查询，会将刚保存的数据查出来
        List<ElSaleOrderDelivery> elSaleOrderDeliveries = elSaleOrderDeliveryMapper.getElSaleorderDeliveryByExpressId(Integer.valueOf(dto.getSupplyId()));

        //保存医流网物流接收信息
        List<ElSaleOrderDelivery> newElSaleOrderDeliveries = dto.getGHDMX().stream()
                .map(
                        item -> new ElSaleOrderDelivery(Integer.valueOf(dto.getSupplyId()),Integer.valueOf(item.getSupplyListId()),item.getReceiveQuantity())
                )
                .collect(Collectors.toList());
        elSaleOrderDeliveryMapper.insertBatch(newElSaleOrderDeliveries);

        //将保存的医流网物流接收详情与erp下的物流详情进行比较，判断该物流单是否全部收货；
        Map<Integer, Integer> theCountOfHasReceived = new HashMap<>();
        elSaleOrderDeliveries.addAll(newElSaleOrderDeliveries);
        for (ElSaleOrderDelivery item : elSaleOrderDeliveries){
            if (!theCountOfHasReceived.containsKey(item.getExpressDetailId())){
                theCountOfHasReceived.put(item.getExpressDetailId(),item.getNum());
            } else {
                int count = theCountOfHasReceived.get(item.getExpressDetailId()) + item.getNum();
                theCountOfHasReceived.put(item.getExpressDetailId(),count);
            }
        }
        List<ExpressDetail> expressDetails = expressMapper.getExpressDetailByExpressId(Integer.valueOf(dto.getSupplyId()));
        boolean allReceived = true;
        for (ExpressDetail detail : expressDetails){
            int hasReceived = 0;
            if (theCountOfHasReceived.containsKey(detail.getExpressDetailId())){
                hasReceived = theCountOfHasReceived.get(detail.getExpressDetailId());
            }
            if (detail.getNum() > hasReceived){
                allReceived = false;
                break;
            }
        }
        //更新物流收货状态
        Express express = new Express();
        express.setExpressId(Integer.valueOf(dto.getSupplyId()));
        express.setArrivalStatus(allReceived ? 2 : 1);
        expressMapper.updateExpressArrivalStatusById(express);

        //更新订单商品收货状态
        dto.getGHDMX().forEach(
                item -> {
                    //订单商品的数量跟确认收货的物流详情商品数量做对比
                    int expressDetailSum = expressMapper.getSaleorderGoodCountHasReceived(Integer.valueOf(item.getSupplyListId()));
                    SaleorderGoods goods = saleorderGoodsMapper.getSaleorderGoodsByExpressDetailId(Integer.valueOf(item.getSupplyListId()));
                    SaleorderGoods var = new SaleorderGoods();
                    var.setSaleorderGoodsId(goods.getSaleorderGoodsId());
                    var.setArrivalTime(time);
                    var.setArrivalStatus(goods.getNum() > expressDetailSum ? 1 : 2);
                    elSaleOrderDeliveryMapper.updateSaleOrderGoodsArrivalStatus(var);
                }
        );

        //更新订单收货状态（查看该销售订单下的所有商品收货状态）
        int theCountOfNotAllReceived = saleorderGoodsMapper.getCountOfNotAllReceived(saleOrderId);

        Saleorder updateSaleOrder = new Saleorder();
        updateSaleOrder.setArrivalStatus(theCountOfNotAllReceived == 0 ? 2 : 1);
        updateSaleOrder.setSaleorderId(saleOrderId);
        updateSaleOrder.setWebTakeDeliveryTime(time);
        updateSaleOrder.setModTime(time);
        elSaleOrderDeliveryMapper.updateSaleorderArrivalStatus(updateSaleOrder);
    }

    /**
     * 订单终止，小医院所谓的订单终止，是终止的订单中的部分商品
     * 订单终止商品的数量 = 订单商品原数量 - 已发货的数量
     * @param terminateDTO 订单明细id集合， saleorder_goods_id
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ElResultDTO terminateSaleOrder(ElSaleOrderTerminateDTO terminateDTO){
        LOGGER.info("医流网用户申请订单终止售后，{}",terminateDTO.toString());
        String orderListIdString = String.join(",", terminateDTO.getOrderListId());

        //小医院的订单项id(他们的主键)
        List<Integer> orderListId = Arrays.stream(terminateDTO.getOrderListId()).map(Integer::valueOf).collect(Collectors.toList());

        Saleorder saleorder = saleorderMapper.getSaleorderByOrderListId(orderListId.get(0));

        ElAfterSaleIntentionDto intentionDto = new ElAfterSaleIntentionDto(saleorder.getSaleorderId(),saleorder.getSaleorderNo(),saleorder.getTraderName(),orderListIdString);
        List<ElAfterSalesIntentionDetail> intentionDetails = saleorderMapper.getSaleorderGoodsByOrderListId(orderListId)
                .stream()
                .map(goods -> {
                    //订单终止商品的数量 = 订单商品原数量 - 已发货的数量
                    int afterSaleGoodNum = goods.getNum() - expressMapper.getSaleorderGoodsNumOfExpress(goods.getSaleorderGoodsId());
                    return new ElAfterSalesIntentionDetail(saleorder.getSaleorderNo(),goods.getSku(),goods.getGoodsName(),afterSaleGoodNum);
                })
                .collect(Collectors.toList());
        intentionDto.setDetails(intentionDetails);

        saveAfterSaleIntention(Collections.singletonList(intentionDto),1);

        return ElResultDTO.ok();

    }

    /**
     * 小医院用户申请售后，退货
     * @param elAfterSaleOrderDTO 售后申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterSaleOrder(ElAfterSaleOrderDTO elAfterSaleOrderDTO){
        LOGGER.info("医流网用户申请退货售后，{}",elAfterSaleOrderDTO.toString());
        List<Integer> expressDetailIdList = elAfterSaleOrderDTO.getTHDMX().stream().map(THDMX::getSupplyListId).collect(Collectors.toList());
        //expressDetailId：returnQuantity的map集合
        Map<Integer, Integer> expressDetailId2NumMap = elAfterSaleOrderDTO.getTHDMX().stream().collect(Collectors.toMap(THDMX::getSupplyListId,THDMX::getReturnQuantity));

        //将同一销售订单的expressDetail合并到一起，expressDetailId以逗号分隔
        List<Saleorder> saleorders = saleorderMapper.getSaleorderByExpressDetailId(expressDetailIdList);

        //expressDetailId：saleorderGoods的map集合
        Map<Integer, SaleorderGoods> saleorderGoodsMap = saleorderGoodsMapper.getSaleorderGoodsByExpressDetailIdList(expressDetailIdList)
                .stream().collect(Collectors.toMap(SaleorderGoods::getGoodsId,item -> item));

        List<ElAfterSaleIntentionDto> intentionDtos = saleorders.stream()
                .map(saleorder -> {
                    ElAfterSaleIntentionDto intentionDto = new ElAfterSaleIntentionDto(saleorder.getSaleorderId(),saleorder.getSaleorderNo(),saleorder.getTraderName(),elAfterSaleOrderDTO.getReturnId());
                    List<ElAfterSalesIntentionDetail> intentionDetails = Arrays.stream(saleorder.getComments().split(","))
                            .map(Integer::valueOf)
                            .filter(saleorderGoodsMap::containsKey)
                            .map(expressDetailId -> {
                                SaleorderGoods goods = saleorderGoodsMap.get(expressDetailId);
                                return new ElAfterSalesIntentionDetail(saleorder.getSaleorderNo(),goods.getSku(),goods.getGoodsName(),expressDetailId2NumMap.get(expressDetailId));
                            })
                            .collect(Collectors.toList());
                    intentionDto.setDetails(intentionDetails);
                    return intentionDto;
                })
                .collect(Collectors.toList());

        saveAfterSaleIntention(intentionDtos,0);
    }


    private void saveAfterSaleIntention(List<ElAfterSaleIntentionDto> intentionDtos, Integer afterSaleType){
        LOGGER.info("保存医流网售后申请详情，{}",intentionDtos.toString());
        intentionDtos.forEach(
                item -> {
                    ElAfterSalesIntention intention = new ElAfterSalesIntention(item.getSaleorderId(),item.getSaleorderNo(),item.getTraderName(),afterSaleType,item.getElAfterSaleId(),0,DateUtil.sysTimeMillis());
                    elAfterSaleIntentionMapper.insert(intention);
                    if (item.getDetails().size() > 0){
                        List<ElAfterSalesIntentionDetail> details = item.getDetails()
                                .stream()
                                .map(var -> new ElAfterSalesIntentionDetail(intention.getElAfterSaleIntentionId(),var.getSaleorderNo(),var.getSkuNo(),var.getSkuName(),var.getAfterSaleCount()))
                                .collect(Collectors.toList());
                        elAfterSalesIntentionDetailMapper.insertBatch(details);
                    }
                }
        );
    }



}

