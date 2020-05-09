package com.vedeng.orderrabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderCoupon;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.service.OrderAndProductConnectService;
import com.vedeng.rabbitmqlogs.dao.RabbitmqLogsMapper;
import com.vedeng.rabbitmqlogs.model.RabbitmqLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: erp
 * @description: 耗材监听
 * @author: addis
 * @create: 2019-12-11 09:56
 **/

@Component("hcorderConsumer")
public class HcorderConsumer implements ChannelAwareMessageListener {
    Logger logger= LoggerFactory.getLogger(HcorderConsumer.class);

    @Resource
    SaleorderMapper saleorderMapper;

    @Resource
    GoodsService goodsService;

    @Resource
    OrderAndProductConnectService orderAndProductConnectService;

    @Resource
    RabbitmqLogsMapper rabbitmqLogsMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String param_data = new String(message.getBody(),"utf-8");
        logger.info("HcorderConsumer 消费者开始监听医械购订单商品消息============================"+ JSON.toJSONString(param_data));

        Saleorder order = new Saleorder();
        int count=0;
        try {
            // 运费
            SaleorderGoods sg = null;
            // 优惠券
            List<SaleorderCoupon> couponList = new ArrayList<>();
            Map<String, Object> map = JsonUtils.readValue(param_data, Map.class);
            Saleorder saleorder = new Saleorder();
            saleorder.setSaleorderNo(map.get("orderNo").toString());
            Saleorder orderInfo = saleorderMapper.getSaleorderBySaleorderNo(saleorder);
            if(orderInfo!=null){
                logger.info("HcorderConsumer exist============================"+ order.getSaleorderNo());
                int savaRabbitmqCount=saveRabbitmq(param_data,"该订单已存在",order.getSaleorderNo());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                return ;
            }

            if (map != null) {
                for (String key : map.keySet()) {
                    switch (key) {
                        case "orderNo":
                            order.setSaleorderNo(map.get(key).toString());// 订单号
                            break;
                        case "companyId":
                            order.setCompanyId(Integer.valueOf(map.get(key).toString()));// 公司ID
                            break;
                        case "orderSrc":
                            order.setSource(Integer.valueOf((map.get(key).toString())));// 订单来源
                            break;
                        case "isPay":
                            order.setPaymentStatus(0);// 支付状态Integer.valueOf(map.get(key).toString()) 始终取0
                            order.setIsPayment(Integer.valueOf(map.get(key).toString()));// 结款状态
                            break;
                        case "orderStatus":
                            order.setStatus(Integer.valueOf(map.get(key).toString()));// 订单状态
                            break;
                        case "couponMoney":
                            order.setCouponMoney(new BigDecimal(map.get(key).toString()));
                            break;
                        //活动id
                        case "activityId":
                            if(map.get(key)!=null && Integer.valueOf(map.get(key).toString())>0){
                                order.setActionId(Integer.valueOf(map.get(key).toString()));
                            }else{
                                order.setActionId(0);
                            }
                            break;
                        //是否使用优惠券
                        case "isCoupons":
                            if(map.get(key)!=null && Integer.valueOf(map.get(key).toString())>0){
                                order.setIsCoupons(Integer.valueOf(map.get(key).toString()));
                            }else{
                                order.setIsCoupons(0);
                            }
                            break;
                        case "payTime":
                            order.setPaymentTime(Long.valueOf(map.get(key).toString()));// 支付时间
                            break;
                        case "realTotalMoney":
                            order.setTotalAmount(new BigDecimal(map.get(key).toString()));// 订单总金额+运费
                            order.setPaymentType(419);//先款后货，预付100%
                            order.setPrepaidAmount(order.getTotalAmount());//预付金额
                            break;
                        case "totalMoney":
                            order.setOriginalAmount(new BigDecimal(map.get(key).toString()).add(new BigDecimal(map.get("deliverMoney").toString())));//订单原金额
                            break;
                        case "traderId":
                            order.setTraderId(Integer.valueOf(map.get(key).toString()));
                            break;
                        case "traderName":
                            order.setTraderName(map.get(key).toString());
                            order.setTakeTraderName(map.get(key).toString());//收货公司名称
                            order.setInvoiceTraderName(map.get(key).toString());//收票公司名称
                            break;
                        case "deliveryUserName":
                            order.setTakeTraderContactName(map.get(key).toString());// 收货名称
                            order.setTraderContactName(map.get(key).toString());// 联系人
                            break;
                        case "deliveryUserPhone":
                            order.setTakeTraderContactMobile(map.get(key).toString());// 收货手机
                            order.setTraderContactMobile(map.get(key).toString());// 手机
                            break;
                        case "deliveryUserTel":
                            order.setTakeTraderContactTelephone(map.get(key).toString());// 收货电话
                            order.setTraderContactTelephone(map.get(key).toString()); // 电话
                            break;
                        case "traderAddressId":
                            // 客户地区最小级ID traderAreaId
                            order.setTraderAreaId(Integer.valueOf(map.get(key).toString()));
                            break;
                        case "deliveryType":
                            order.setDeliveryType(Integer.valueOf(map.get(key).toString()));//发货方式
                            break;
                        case "takeTraderAddressId":
                            // 收货地区最小级ID takeTraderAreaId
                            order.setTakeTraderAreaId(Integer.valueOf(map.get(key).toString()));//收货地址ID
                            break;
                        case "deliveryUserArea":
                            order.setTakeTraderArea(map.get(key).toString());// 收货省市区
                            order.setTraderArea(map.get(key).toString());// 客户地区
                            break;
                        case "deliveryUserAddress":
                            order.setTakeTraderAddress(map.get(key).toString());// 收货详细地址
                            order.setTraderAddress(map.get(key).toString());// 联系详细地址(含省市区)
                            break;
                        case "isInvoice":
                            // 是否寄送发票 0否 1是 -- 是否需要发票：0需要、1不需要
                            order.setIsSendInvoice(Integer.valueOf(map.get(key).toString().equals("0") ? "1" : "0"));
                            if (map.get(key).toString().equals("1")) { // 不需要发票
                                order.setInvoiceType(971);// 票种
                                order.setInvoiceMethod(2);// 自动
                            } else { // 需要发票
                                if (map.get("openInvoiceMode").toString().equals("2")) {// 电子票
                                    order.setIsSendInvoice(0);// 不需要寄送
                                }
                            }
                            break;
                        case "invoiceType":
                            order.setInvoiceType(Integer.valueOf(map.get(key).toString().equals("355") ? "972" : "971"));// 票种
                            break;
                        case "openInvoiceMode": // 开票方式1手动纸质开票、2自动纸质开票、3自动电子发票
                            order.setInvoiceMethod(Integer.valueOf(map.get(key).toString().equals("2") ? "3" : "2"));// 开票方式：1纸质发票、2电子发票
                            break;
                        case "invoiceUserName":
                            order.setInvoiceTraderContactName(map.get(key).toString());
                            break;
                        case "invoiceUserPhone":
                            order.setInvoiceTraderContactMobile(map.get(key).toString());// 默认为客户手机号
                            break;
                        case "invoiceUserTel":
                            order.setInvoiceTraderContactTelephone(map.get(key).toString());// 收票人电话
                            break;
                        case "invoiceTraderAddressId":
                            order.setInvoiceTraderAreaId(Integer.valueOf(map.get(key).toString()));
                            break;
                        case "invoiceUserArea":
                            order.setInvoiceTraderArea(map.get(key).toString());// 收票人省市区
                            break;
                        case "invoiceUserAddress":
                            order.setInvoiceTraderAddress(map.get(key).toString());// 收票人详细地址
                            break;
                        case "orderRemark":
                            order.setAdditionalClause(map.get(key).toString());// 附加条款
                            break;
                        case "invoiceUserMailbox":
                            order.setInvoiceEmail(map.get(key).toString());// 收票邮箱
                            break;
                        case "addTime":
                            order.setAddTime(Long.valueOf(map.get(key).toString()));
                            break;
                        case "modTime":
                            order.setModTime(Long.valueOf(map.get(key).toString()));
                            break;
                        case "deliverMoney":// 运费
                            Goods goods = new Goods();
                            goods.setGoodsId(127063);
                            goods = goodsService.getGoodsById(goods);
                            sg = new SaleorderGoods();
                            if (goods == null) {
//                                System.out.println("----------------系统中无运费商品[sku:127063]------------------");
                                sg.setGoodsId(127063);
                                sg.setGoodsName("运费");
                                sg.setSku("V127063");
                                sg.seteNum(1);
                                sg.setPrice(new BigDecimal(map.get(key).toString()));
                                sg.setRealPrice(new BigDecimal(map.get(key).toString()));

                                // start add by brianna.ben for VDERP-1788 医械购运费-设定为固定值 参考成本默认为8
                                sg.setReferenceCostPrice(new BigDecimal(8));
                                // end add by brianna.ben for VDERP-1788 医械购运费-设定为固定值 参考成本默认为8

                                sg.setCreator(Integer.valueOf(map.get("creator").toString()));
                                sg.setAddTime(Long.valueOf(map.get("addTime").toString()));
                                sg.setUpdater(Integer.valueOf(map.get("updater").toString()));
                                sg.setModTime(Long.valueOf(map.get("modTime").toString()));
                                // add by franlin.wu for[设置运费小计即运费最大退款值] at 2018-1-9 begin
                                sg.setMaxSkuRefundAmount(new BigDecimal(map.get(key).toString()));
                                // add by franlin.wu for[设置运费小计即运费最大退款值] at 2018-1-9 end
                            } else {
                                sg.setGoodsId(goods.getGoodsId());
                                sg.setGoodsName(goods.getGoodsName());
                                sg.setSku(goods.getSku());
                                sg.setBrandName(goods.getBrandName());
                                sg.setModel(goods.getModel());
                                sg.setUnitName(goods.getUnitName());
                                sg.seteNum(1);
                                sg.setPrice(new BigDecimal(map.get(key).toString()));
                                sg.setRealPrice(new BigDecimal(map.get(key).toString()));

                                // start add by brianna.ben for VDERP-1788 医械购运费-设定为固定值 参考成本默认为8
                                sg.setReferenceCostPrice(new BigDecimal(8));
                                // end add by brianna.ben for VDERP-1788 医械购运费-设定为固定值 参考成本默认为8

                                sg.setCreator(Integer.valueOf(map.get("creator").toString()));
                                sg.setAddTime(Long.valueOf(map.get("addTime").toString()));
                                sg.setUpdater(Integer.valueOf(map.get("updater").toString()));
                                sg.setModTime(Long.valueOf(map.get("modTime").toString()));
                                // add by franlin.wu for[设置运费小计即运费最大退款值] at 2018-1-9 begin
                                sg.setMaxSkuRefundAmount(new BigDecimal(map.get(key).toString()));
                                // add by franlin.wu for[设置运费小计即运费最大退款值] at 2018-1-9 end
                            }
//						sf.setAmount(Double.valueOf(map.get(key).toString()));
//						sf.setFreightType(1);// 默认1
                            break;
                        default:
                            break;
                    }
                    order.setOrderType(5);// 耗材订单
                }

                //是否需要将客户信息带到发票信息的标记，默认false
                boolean flag = false;
                //不开发票
                if("1".equals(map.get("isInvoice").toString())){
                    flag = true;
                }else{
                    //如果需要开发票，判断是否是电子票
                    if (map.get("openInvoiceMode").toString().equals("2")) {
                        flag = true;
                    }
                }

                //如果不需要发票，前台都不会设置如下值,给定默认值
                if(flag){

                    if(map.get("deliveryUserName") != null){
                        order.setInvoiceTraderContactName(map.get("deliveryUserName").toString());// 默认为客户联系人名称
                    }

                    if(map.get("deliveryUserPhone") != null){
                        order.setInvoiceTraderContactMobile(map.get("deliveryUserPhone").toString());// 默认为客户手机号
                    }

                    if(map.get("deliveryUserTel") != null){
                        order.setInvoiceTraderContactTelephone(map.get("deliveryUserTel").toString());
                    }

                    if(map.get("traderAddressId") != null){
                        order.setInvoiceTraderAreaId(Integer.valueOf(map.get("traderAddressId").toString()));
                    }

                    if(map.get("deliveryUserAddress") != null){
                        order.setInvoiceTraderAddress(map.get("deliveryUserAddress").toString());
                    }
                }

                // 订单商品信息
                if (map.get("orderSkuList") != null) {
                    List<SaleorderGoods> goodsList = new ArrayList<>();
                    List<Map<String, Object>> list = (ArrayList<Map<String, Object>>) map.get("orderSkuList");
                    for (Map<String, Object> goodsMap : list) {
                        SaleorderGoods orderGoods = new SaleorderGoods();
                        for (String s : goodsMap.keySet()) {
                            switch (s) {
                                case "skuId":
                                    orderGoods.setGoodsId(Integer.valueOf(goodsMap.get(s).toString()));
                                    break;
                                case "skuName":
                                    orderGoods.setGoodsName(goodsMap.get(s).toString());
                                    break;
                                case "brandName":
                                    orderGoods.setBrandName(goodsMap.get(s).toString());
                                    break;
                                case "skuSpecifications":
                                    orderGoods.setModel(goodsMap.get(s).toString());
                                    break;
                                case "skuCode":
                                    orderGoods.setSku(goodsMap.get(s).toString());
                                    break;
                                case "skuNum":
                                    orderGoods.setNum(Integer.valueOf(goodsMap.get(s).toString()));// 订单数量
                                    break;
                                case "skuPrice":
                                    orderGoods.setRealPrice(new BigDecimal(goodsMap.get(s).toString()));// 实际单价 耗材商城实际单价
                                    break;
                                // modify by franlin.wu for[优惠后商品单价] at 2018-12-17 begin
                                case "skuCostPrice":
                                    orderGoods.setReferenceCostPrice(new BigDecimal(goodsMap.get(s).toString()));// 实际单价 耗材商城实际单价
                                    break;
                                // modify by franlin.wu for[优惠后商品单价] at 2018-12-17 begin
                                case "couponAmount":
                                    orderGoods.setPrice(new BigDecimal(goodsMap.get(s).toString()));//单价 在耗材商城订单中为优惠单价
                                    break;
                                // modify by franlin.wu for[优惠后商品单价] at 2018-12-17 end
                                case "addTime":
                                    orderGoods.setAddTime(Long.valueOf(goodsMap.get(s).toString()));
                                    break;
                                case "creator":
                                    orderGoods.setCreator(Integer.valueOf(goodsMap.get(s).toString()));
                                    break;
                                case "modTime":
                                    orderGoods.setModTime(Long.valueOf(goodsMap.get(s).toString()));
                                    break;
                                case "updater":
                                    orderGoods.setUpdater(Integer.valueOf(goodsMap.get(s).toString()));
                                    break;
                                // add by franlin.wu for[sku商品实际总额(包含优惠)] at 2018-12-17 begin
                                case "skuAmount":
                                    orderGoods.setMaxSkuRefundAmount(new BigDecimal(goodsMap.get(s).toString()));
                                    break;
                                // add by franlin.wu for[sku商品实际总额(包含优惠)] at 2018-12-17 end
                                //是否是活动商品
                                case "activityId":
                                    if(goodsMap.get(s)!=null && Integer.valueOf(goodsMap.get(s).toString())>0){
                                        orderGoods.setIsActionGoods(1);
                                    }else{
                                        orderGoods.setIsActionGoods(0);
                                    }
                                    break;
                                //该商品是否使用优惠券
                                case "couponFlag":
                                    if(goodsMap.get(s)!=null &&Integer.valueOf(goodsMap.get(s).toString())>0){
                                        orderGoods.setIsCoupons(Integer.valueOf(goodsMap.get(s).toString()));
                                    }else{
                                        orderGoods.setIsCoupons(0);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        // modify by franlin.wu for[优惠后商品单价] at 2018-12-17 begin
                        orderGoods.setPrice(null == orderGoods.getPrice() ? orderGoods.getRealPrice() : orderGoods.getPrice());
                        // modify by franlin.wu for[优惠后商品单价] at 2018-12-17 end

                        // 其他信息根据goodsId在erp中查询后赋值
                        goodsList.add(orderGoods);
                    }
                    if (sg != null) {
                        goodsList.add(sg);
                    }
                    order.setGoodsList(goodsList);
                }
                // 订单优惠券信息
                if (map.get("orderCouponList") != null) {
                    List<Map<String, Object>> couponMap = (List<Map<String, Object>>) map.get("orderCouponList");
                    for (int k = 0; k < couponMap.size(); k++) {
                        SaleorderCoupon sc = new SaleorderCoupon();
                        for (String key : couponMap.get(k).keySet()) {
                            if ("couponType".equals(key)) {
                                sc.setCouponType(Integer.valueOf(couponMap.get(k).get(key).toString()));
                            }
                            if ("denomination".equals(key)) {
                                sc.setDenomination(new BigDecimal(couponMap.get(k).get(key).toString()));
                            }
                        }
                        couponList.add(sc);
                    }
                }
                int i=orderAndProductConnectService.saveHCOrder(order,couponList);
                if(i>0){
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    logger.info("消费者已接受医械购订单商品消息完毕============================"+ JSON.toJSONString(param_data));
                }else{
                    int savaRabbitmqCount=saveRabbitmq(param_data,"没有添加成功",order.getSaleorderNo());
                    if(savaRabbitmqCount<3){
                        logger.info("消费者接受医械购订单商品消息失败"+savaRabbitmqCount+"次============================"+ JSON.toJSONString(param_data));
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    }
                    else{
                        logger.info("消费者接受医械购订单商品消息失败"+savaRabbitmqCount+"次============================"+ JSON.toJSONString(param_data));
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                    }
                }
            }
        }catch (Exception e){
            try {
                int savaRabbitmqCount=saveRabbitmq(param_data,"有异常情况",order.getSaleorderNo());
                if(savaRabbitmqCount<3){
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                }else {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                }
                logger.error("消费者接受医械购订单商品消息失败"+savaRabbitmqCount+"次============================", e);
            }catch (Exception e1){
                logger.error("添加日志表失败",e1);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }

        }

    }


    /**
     * @Description: 保存添加不成功抛异常的数据
     * @Param:
     * @return:
     * @Author: addis
     * @Date: 2019/12/13
     */
    public int saveRabbitmq(String param_data,String error,String orderNo){
        RabbitmqLogs logs= rabbitmqLogsMapper.selectByPrimaryKey(orderNo);
        if(logs!=null){
            RabbitmqLogs rabbitmqLogs=new RabbitmqLogs();
            rabbitmqLogs.setOrderNo(orderNo);
            rabbitmqLogs.setUpdateTime(new Date());
            rabbitmqLogs.setNumberRetries(logs.getNumberRetries()+1);
            int i=rabbitmqLogsMapper.updateByPrimaryKeySelective(rabbitmqLogs);
            if(i>0){
                return  logs.getNumberRetries()+1;
            }else{
                return 0;
            }
        }else {
            RabbitmqLogs rabbitmqLogs=new RabbitmqLogs();
            rabbitmqLogs.setOrderNo(orderNo);
            rabbitmqLogs.setAddTime(new Date());
            rabbitmqLogs.setErrorMessage(error);
            rabbitmqLogs.setOrderInformation(param_data);
            rabbitmqLogs.setNumberRetries(1);
            int i=rabbitmqLogsMapper.insertSelective(rabbitmqLogs);
            if(i>0){
                return i;
            }else{
                return 0;
            }

        }
    }



}