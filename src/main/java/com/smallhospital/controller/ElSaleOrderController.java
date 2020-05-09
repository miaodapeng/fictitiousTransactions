package com.smallhospital.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.smallhospital.dto.*;
import com.smallhospital.service.ELContractService;
import com.smallhospital.service.impl.chain.CreateOrderStep;
import com.smallhospital.service.ElSaleOrderService;
import com.smallhospital.service.impl.build.CreateOrderStepBuild;
import com.vedeng.order.dao.SaleorderGenerateMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Daniel
 * @Description: 小医院订单控制类
 * @Date created in 2019/11/20 6:37 下午
 */
@RestController
@RequestMapping("/el/saleOrder")
public class ElSaleOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElSaleOrderController.class);

    @Autowired
    private ElSaleOrderService elSaleOrderService;

    @Autowired
    private ELContractService contractService;

    @Autowired
    @Qualifier("saleorderGenerateMapper")
    private SaleorderGenerateMapper saleorderGenerateMapper;

    @Autowired
    private CreateOrderStepBuild createOrderStepBuild;

    /**
     * 小医院系统发起新增订单请求
     * @return 请求结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ElResultDTO save(@RequestBody ELOrderDto orderDto){

        if(orderDto == null){
            return ElResultDTO.error("请求参数不能为空");
        }

        if(validatorSaleOrderExsit(orderDto.getOrderNumber())){
            return ElResultDTO.error("该订单号已经存在");
        }

        //校验订单中的商品对应的合同是否过期
        ValidatorResult validatorResult = contractService.validatorContractExpire(orderDto);
        if(!validatorResult.getResult()){
            return ElResultDTO.error(validatorResult.getMessage());
        }

        CreateOrderStep createOrderStep = createOrderStepBuild.getCreateOrderStep();

        try {
            createOrderStep.dealWith(orderDto);
        } catch (Exception e) {
            LOGGER.error("创建订单失败",e);
            return ElResultDTO.error(e.getMessage());
        }

        return ElResultDTO.ok();
    }

    /**
     * 校验销售单是否存在
     * @param orderNumber
     * @return 存在返回true
     */
    private boolean validatorSaleOrderExsit(String orderNumber) {
        return saleorderGenerateMapper.findByElorderNo(orderNumber) > 0;
    }


    /**
     * 小医院系统发起终止订单请求
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/terminate")
    public ElResultDTO terminateSaleOrder(@RequestBody ElSaleOrderTerminateDTO terminateDTO){

        LOGGER.info("订单中止的请求参数:" + JSON.toJSONString(terminateDTO));

        if (terminateDTO.getOrderListId() == null || terminateDTO.getOrderListId().length == 0) {
            return ElResultDTO.error("参数错误");
        }

        try{

            ElResultDTO resultDTO = elSaleOrderService.terminateSaleOrder(terminateDTO);
            LOGGER.info("订单中止的响应:" + JSON.toJSONString(resultDTO));
            return resultDTO;
        }catch (Exception e){
            LOGGER.info("订单中止失败",e);
            return ElResultDTO.error(e.getMessage());
        }
    }


    /**
     * 小医院系统确认收货，同步到erp
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/confirm")
    public ElResultDTO confirmElSaleOrder(@RequestBody ConfirmElSaleOrderDTO dto){

        LOGGER.info("入库单确认的请求参数:" + new Gson().toJson(dto));

        if (dto == null || dto.getConfirm() != 1 || dto.getGHDMX().size() == 0){
            return ElResultDTO.error("参数错误");
        }

        try{
            elSaleOrderService.confirmElSaleOrder(dto);
            return ElResultDTO.ok();
        }catch (Exception e){
            LOGGER.error("入库单确认收货异常",e);
            return ElResultDTO.errorInternal(e.getMessage());
        }
    }

    /**
     * 小医院用户发起订单售后请求，同步新增到erp售后
     * @return 结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "/afterSale")
    public ElResultDTO afterSale(@RequestBody ElAfterSaleOrderDTO dto){

        LOGGER.info("新增售后的请求参数:" + JSON.toJSONString(dto));

        if (dto.getTHDMX() == null || dto.getTHDMX().size() == 0) {
            return ElResultDTO.error("参数错误");
        }
        try{
            elSaleOrderService.afterSaleOrder(dto);

            LOGGER.info("新增售后单的响应:" + ElResultDTO.ok());

            return ElResultDTO.ok();
        }catch (Exception e){
            LOGGER.info("新增售后的失败..",e);
            return ElResultDTO.error(e.getMessage());
        }
    }


    /**
     * 小医院系统获取订单物流信息
     * 注意要判断是否是我们系统里的物流单号，防止恶意查询
     * @return 物流信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/logistics")
    public ElResultDTO getElSaleOrderLogisticsInfo(@RequestBody ELLogisticQueryDto logisticsOrderDto){
        if (logisticsOrderDto.getHospitalId() == null || StringUtils.isBlank(logisticsOrderDto.getLogisticsNumber())){
            return ElResultDTO.error("参数错误");
        }
        LOGGER.info("入库单获取物流单的请求参数:" + new Gson().toJson(logisticsOrderDto));


        ElResultDTO<LogisticsDTO> result = ElResultDTO.ok();
        try {
            result.setData(elSaleOrderService.queryLogisticsInfo(logisticsOrderDto.getLogisticsNumber(),logisticsOrderDto.getHospitalId()));
            LOGGER.info("入库单获取物流单的响应:" + new Gson().toJson(result));

        } catch (Exception e) {
            e.printStackTrace();
            return ElResultDTO.error(e.getMessage());
        }

        return result;
    }
}
