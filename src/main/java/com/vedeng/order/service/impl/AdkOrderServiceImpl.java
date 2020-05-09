package com.vedeng.order.service.impl;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.ResultCodeConstants;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.OrderNoDict;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.order.dao.AdkGoodsMapper;
import com.vedeng.order.dao.AdkLogMapper;
import com.vedeng.order.dao.AdkSupplierMapper;
import com.vedeng.order.dao.SaleorderGenerateMapper;
import com.vedeng.order.model.SaleorderGenerate;
import com.vedeng.order.model.adk.*;
import com.vedeng.order.service.AdkOrderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.ordergoods.dao.SaleorderGoodsGenerateMapper;
import com.vedeng.ordergoods.model.SaleorderGoodsGenerate;
import com.vedeng.system.dao.TraderAddressGenerateMapper;
import com.vedeng.system.model.TraderAddressGenerate;
import com.vedeng.system.model.TraderAddressGenerateExample;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.TraderContactGenerateMapper;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.TraderContactGenerateExample;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdkOrderServiceImpl extends BaseServiceimpl implements AdkOrderService {
    @Autowired
    @Qualifier("adkGoodsMapper")
    AdkGoodsMapper adkGoodsMapper;
    @Autowired
    @Qualifier("adkSupplierMapper")
    AdkSupplierMapper adkSupplierMapper;

    @Autowired
    @Qualifier("saleorderService")
    protected SaleorderService saleorderService;

    @Autowired
    @Qualifier("traderCustomerService")
    protected TraderCustomerService traderCustomerService;
    @Autowired
    @Qualifier("goodsService")
    private GoodsService goodsService;

    @Autowired
    SaleorderGenerateMapper saleorderGenerateMapper;
    @Autowired
    private SaleorderGoodsGenerateMapper saleorderGoodsGenerateMapper;

    @Autowired
    TraderContactGenerateMapper traderContactGenerateMapper;
    @Autowired
    TraderAddressGenerateMapper traderAddressGenerateMapper;

    @Autowired
    RegionService regionService;
    @Autowired
    AdkLogMapper adkLogMapper;

    @Override
    @Transactional
    public void importProducts(List<TAdkGoods> list, List<String> skuList, User user) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<Goods> goodsList = adkGoodsMapper.batchVailGoodsSku(skuList);
            Map<String, Integer> map = Maps.newHashMap();
            if (CollectionUtils.isNotEmpty(goodsList)) {
                for (Goods good : goodsList) {
                    map.put(good.getSku(), good.getGoodsId());
                }
            }
            for (TAdkGoods good : list) {
                Integer erpGoodsId = map.get(good.getErpGoodsSku());
                if (erpGoodsId != null && erpGoodsId != 0) {
                    good.setErpGoodsId(erpGoodsId);
                    TAdkGoodsExample example = new TAdkGoodsExample();
                    example.createCriteria().andAdkGoodsCodeEqualTo(good.getAdkGoodsCode());
                    if (adkGoodsMapper.countByExample(example) > 0) {
                        good.setStatus(CommonConstants.ENABLE + "");
                        adkGoodsMapper.updateByExampleSelective(good, example);
                    } else {
                        good.setStatus(CommonConstants.ENABLE + "");
                        adkGoodsMapper.insert(good);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void importSuppliers(List<TAdkSupplier> list, User user) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (TAdkSupplier good : list) {
                if (StringUtils.isBlank(good.getAdkSupplierCode()) || StringUtils.isBlank(good.getAdkSupplierName())) {
                    continue;
                }
                TAdkSupplierExample example = new TAdkSupplierExample();
                example.createCriteria().andAdkSupplierCodeEqualTo(good.getAdkSupplierCode());
                if (adkSupplierMapper.countByExample(example) > 0) {
                    good.setStatus(CommonConstants.ENABLE + "");
                    adkSupplierMapper.updateByExampleSelective(good, example);
                } else {
                    good.setStatus(CommonConstants.ENABLE + "");
                    adkSupplierMapper.insert(good);
                }
            }
        }
    }

    @Override
    @Transactional
    public void remoteAdd(AdkOrder adkOrder, ResultInfo<?> result) throws ShowErrorMsgException {
        AdkLogExample logExistExample = new AdkLogExample();
        logExistExample.createCriteria().andAdkOrderNoEqualTo(adkOrder.getOrderNo() + "," + adkOrder.getTraderName())
                .andAdkLogTypeEqualTo(CommonConstants.ADK_LOG_RECEIVE)
                .andAdkLogStatusEqualTo(CommonConstants.ENABLE + "");
        long existCount = adkLogMapper.countByExample(logExistExample);
        if (existCount > 0) {
            adkOrder.setOrderNo("exist:");
            logger.error("adkadd 重复发送订单");
            result.setCode(ResultCodeConstants.SUCCESS);
            return;
            //throw new ShowErrorMsgException(ResultCodeConstants.ADK_ORDER_EXIST_6009 + "", "重复发送订单");
        }
        Long time = DateUtil.sysTimeMillis();
        // 客户类型的公司
        Trader traderParam = new Trader();
        traderParam.setTraderType(CommonConstants.TRADER_TYPE_1);
        traderParam.setCompanyId(CommonConstants.COMPANY_ID_1);
        traderParam.setTraderName(adkOrder.getTraderName());
        Trader trader = traderCustomerService.getTraderByTraderName(traderParam, CommonConstants.COMPANY_ID_1);
        if (trader == null) {
            traderParam.setTraderName(adkOrder.getTraderName().replace("有限公司", ""));
            trader = traderCustomerService.getTraderByTraderName(traderParam, CommonConstants.COMPANY_ID_1);
        }
        if (trader == null) {
            traderParam.setTraderName(adkOrder.getTraderName() + "有限公司");
            trader = traderCustomerService.getTraderByTraderName(traderParam, CommonConstants.COMPANY_ID_1);
        }

        if (trader == null) {
           // logger.error("没有找到对应的公司：" + adkOrder.getTraderName());
            throw new ShowErrorMsgException(ResultCodeConstants.ADK_ORDER_EXIST_6009 + "",
                    "ERP系统没有找到对应的公司：" + adkOrder.getTraderName());
        }

        if (logger.isInfoEnabled()) {
            logger.info("adkadd trader：：：\ttrader=" + trader.getTraderId());
        }

        // 初始化 订单
        SaleorderGenerate saleorder = new SaleorderGenerate();
        // 订单基本信息
        setOrderBase(adkOrder, trader, saleorder);
        if (logger.isInfoEnabled()) {
            logger.info("adkadd 订单基本信息：：： " + adkOrder.getOrderNo());
        }
        // 订单联系人信息
        setOrderContact(adkOrder, trader, saleorder);
        if (logger.isInfoEnabled()) {
            logger.info("adkadd 订单联系人信息：：： " + adkOrder.getOrderNo());
        }
        // 订单地址信息
        setOrderAddress(saleorder);
        if (logger.isInfoEnabled()) {
            logger.info("adkadd 订单地址信息：：： " + adkOrder.getOrderNo());
        }
        // 订单产品信息
        List<SaleorderGoodsGenerate> goodsList = setOrderGoods(adkOrder, time);
        if (logger.isInfoEnabled()) {
            logger.info("adkadd 订单产品信息：：： " + adkOrder.getOrderNo());
        }
        // 根据traderId查询所属客户
        TraderCustomerVo traderCustomer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
        if (traderCustomer == null) {
            throw new ShowErrorMsgException(ResultCodeConstants.COM_NAME_NOTEXIST_6008 + "",
                    "未能匹配到客户信息：traderId：" + saleorder.getTraderId());
        }

        if (logger.isInfoEnabled()) {
            logger.info("adkadd 户信息：：： " + adkOrder.getOrderNo());
        }

        saleorder.setCustomerType(traderCustomer.getCustomerType());// 客户类型
        saleorder.setCustomerNature(traderCustomer.getCustomerNature());// 客户性质
        // 插入订单
        try {
            saleorderGenerateMapper.insertSelective(saleorder);
            SaleorderGenerate generateOrderNoUpdate = new SaleorderGenerate();
            generateOrderNoUpdate.setSaleorderId(saleorder.getSaleorderId());
            generateOrderNoUpdate
                    .setSaleorderNo(OrderNoDict.getOrderNum(saleorder.getSaleorderId(), OrderNoDict.ADC_ORDER_TYPE));
            adkOrder.setOrderNo(generateOrderNoUpdate.getSaleorderNo());
            saleorderGenerateMapper.updateByPrimaryKeySelective(generateOrderNoUpdate);
            for (int i = 0; i < goodsList.size(); i++) {
                goodsList.get(i).setSaleorderId(saleorder.getSaleorderId());
                saleorderGoodsGenerateMapper.insertSelective(goodsList.get(i));
            }
        } catch (Exception e) {
            logger.error("保存订单商品信息发生错误Order", e);
            throw new ShowErrorMsgException(ResultCodeConstants.SERVICE_ERROR_500 + "",
                    "保存订单商品信息发生错误Order" + saleorder.getTraderId());
        }
        if (logger.isInfoEnabled()) {
            logger.info("adkadd 插入订单 成功：：： " + adkOrder.getOrderNo());
        }
        // addAdkOrderLog(saleorder);

        result.setCode(ResultCodeConstants.SUCCESS);
    }

    // private void addAdkOrderLog(SaleorderGenerate saleorder) {
    // AdkOrderLogGenerate log = new AdkOrderLogGenerate();
    // log.setErpOrderId(saleorder.getSaleorderId());
    // log.setAdkOrderNo(saleorder.getAdkSaleorderNo());
    // log.setAddTime(new Date());
    // log.setDeliveryStatus(OrderConstant.ORDER_STATUS_INIT0 + "");
    // adkOrderLogGenerateMapper.insert(log);
    // }

    private List<SaleorderGoodsGenerate> setOrderGoods(AdkOrder adkOrder, Long time) throws ShowErrorMsgException {
        List<SaleorderGoodsGenerate> goodsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(adkOrder.getOrderGoods())) {// 订单产品明细
            // 添加订单产品
            for (AdkOrderGoods goods : adkOrder.getOrderGoods()) {
                SaleorderGoodsGenerate saleorderGoods = new SaleorderGoodsGenerate();
                TAdkGoodsExample example = new TAdkGoodsExample();
                example.createCriteria().andAdkGoodsCodeEqualTo(goods.getGoodsCode());

                List<TAdkGoods> list = adkGoodsMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(list)) {
                    logger.error("adkadd erp系统中没有找到接口传递的goodcode，系统只能放弃此产品=" + goods.getGoodsCode());
//					throw new ShowErrorMsgException(ResultCodeConstants.SERVICE_ERROR_500 + "",
//							"adkadd erp系统中没有找到接口传递的goodcodeq:" + goods.getGoodsCode());
                    continue;
                }
                TAdkGoods goodsCurrent = list.get(0);

                saleorderGoods.setGoodsId(goodsCurrent.getErpGoodsId());

                try {
                    BigDecimal bd = new BigDecimal(goods.getNum());
                    BigDecimal result = bd.divide(new BigDecimal(goodsCurrent.getChangeNum() + ""));
                    saleorderGoods.setNum(result.intValue());


                    saleorderGoods.setPrice(goodsCurrent.getErpSkuPrice());
                } catch (Exception e) {
                    saleorderGoods.setNum(NumberUtils.toInt(goods.getNum()));
                    logger.error("adkadd 数量处理有误", e);
                }
                try {
                    if (1 != goodsCurrent.getChangeNum()) {
                    } else {
                        saleorderGoods.setDeliveryCycle("13天");
                        saleorderGoods.setDeliveryCycle("14天");
                    }
                } catch (Exception e) {
                    logger.error("adkadd setDeliveryCycle", e);
                }
                saleorderGoods.setUnitName(goods.getUnitName());

                Goods goodsInfo = new Goods();
                goodsInfo.setGoodsId(saleorderGoods.getGoodsId());
                Goods goodsById = goodsService.getGoodsById(goodsInfo);
                if (goodsById == null) {
                    logger.error("adkadd 无法通过goodsId找到产品，系统只能放弃此产品=" + saleorderGoods.getGoodsId());
                    throw new ShowErrorMsgException(ResultCodeConstants.SERVICE_ERROR_500 + "",
                            "adkadd erp系统中没有找到接口传递的goodcode2:" + goods.getGoodsCode());
                }
                saleorderGoods.setSku(goodsById.getSku());
                saleorderGoods.setGoodsName(goodsById.getGoodsName());
                saleorderGoods.setBrandName(goodsById.getBrandName());
                saleorderGoods.setUnitName(goodsById.getUnitName());
                saleorderGoods.setModel(goodsById.getModel());
                saleorderGoods.setAddTime(time);
                saleorderGoods.setModTime(time);

                goodsList.add(saleorderGoods);
            }
        }
        //去重
        Map<String, SaleorderGoodsGenerate> map = new HashMap<>();
        List<SaleorderGoodsGenerate> result = Lists.newArrayList();
        try {
            for (SaleorderGoodsGenerate generate : goodsList) {
                if (!map.containsKey(generate.getSku())) {
                    map.put(generate.getSku(), generate);
                } else {
                    SaleorderGoodsGenerate buy = map.get(generate.getSku());
                    buy.setNum(buy.getNum() + generate.getNum());
                }
            }
            result.addAll(map.values());
            return result;
        } catch (Exception e) {
            logger.error("adk 去重失败 ",e);
            return goodsList;
        }

    }

    @Autowired
    UserService userService;

    private void setOrderBase(AdkOrder adkOrder, Trader trader, SaleorderGenerate saleorder) {
        saleorder.setAddTime(System.currentTimeMillis());
        saleorder.setTotalAmount(adkOrder.getTotalAmount());
        saleorder.setPrepaidAmount(adkOrder.getTotalAmount());
        saleorder.setTraderName(trader.getTraderName());
        saleorder.setOrderType(OrderConstant.ORDER_TYPE_SALE);
        saleorder.setCompanyId(CommonConstants.COMPANY_ID_1);
        saleorder.setSource(OrderConstant.ORDER_SOURCE_ADK_3);
        saleorder.setStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setInvoiceStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setPaymentStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setDeliveryStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setTraderId(trader.getTraderId());
        saleorder.setAdkSaleorderNo(adkOrder.getOrderNo());

        User user = userService.getUserByTraderId(trader.getTraderId(), 1);// 1客户，2供应商
        if (user != null) {
            User user1=userService.getUserById(user.getUserId());
            if(user1!=null){
                saleorder.setOrgId(user1.getOrgId());
                saleorder.setOrgName(user1.getOrgName());
            }
            saleorder.setUserId(user1.getUserId());
        }
        // 发票类型
        saleorder.setInvoiceType(972);
        // 发送方式
        saleorder.setInvoiceMethod(new Byte("1"));

    }

    private void setOrderContact(AdkOrder adkOrder, Trader trader, SaleorderGenerate saleorder) {
        // 初始化订单的发票联系人
        TraderContactGenerate invoiceContact = getContactByMobileAndName(saleorder.getTraderId(),
                adkOrder.getInvoiceTraderContactMobile(), adkOrder.getInvoiceTraderContactName(),
                adkOrder.getInvoiceTraderContactTelephone());
        // 发票
        saleorder.setInvoiceTraderContactMobile(invoiceContact.getMobile());
        saleorder.setInvoiceTraderContactTelephone(invoiceContact.getTelephone());
        saleorder.setInvoiceTraderContactId(invoiceContact.getTraderContactId());
        saleorder.setInvoiceTraderContactName(invoiceContact.getName());
        saleorder.setInvoiceTraderName(trader.getTraderName());
        saleorder.setInvoiceTraderId(trader.getTraderId());

        // 订单跟发票联系人保持一直
        saleorder.setTraderContactId(invoiceContact.getTraderContactId());
        saleorder.setTraderContactMobile(invoiceContact.getMobile());
        saleorder.setTraderContactName(invoiceContact.getName());
        saleorder.setTraderContactTelephone(invoiceContact.getTelephone());

        // 初始化订单的收件人
        TraderContactGenerate takeContact = getContactByMobileAndName(saleorder.getTraderId(),
                adkOrder.getTakeTraderContactMobile(), adkOrder.getTakeTraderContactName(),
                adkOrder.getTakeTraderContactTelephone());
        saleorder.setTakeTraderId(trader.getTraderId());
        saleorder.setTakeTraderName(trader.getTraderName());
        saleorder.setTakeTraderContactId(takeContact.getTraderContactId());
        saleorder.setTakeTraderContactMobile(takeContact.getMobile());
        saleorder.setTakeTraderContactTelephone(takeContact.getTelephone());
        saleorder.setTakeTraderContactName(takeContact.getName());
    }

    private void setOrderAddress(SaleorderGenerate saleorder) {
        TraderAddressGenerate takeAddress = null, invoiceAddress = null;

        TraderAddressGenerateExample takeExample = new TraderAddressGenerateExample();
        takeExample.createCriteria().andTraderIdEqualTo(saleorder.getTraderId())
                .andIsEnableEqualTo(CommonConstants.ENABLE);
        List<TraderAddressGenerate> addressGenerate = traderAddressGenerateMapper.selectByExample(takeExample);
        if (CollectionUtils.isNotEmpty(addressGenerate)) {
            if (addressGenerate.size() == 1) {
                takeAddress = addressGenerate.get(0);
                invoiceAddress = addressGenerate.get(0);
            } else if (addressGenerate.size() >= 2) {
                for (TraderAddressGenerate generate : addressGenerate) {
                    if (takeAddress == null
                            && StringUtils.equals(generate.getComments(), CommonConstants.TAKE_ADDRESS_COMMENT)) {
                        takeAddress = generate;
                        continue;
                    }
                    if (invoiceAddress == null
                            && StringUtils.equals(generate.getComments(), CommonConstants.INVOICE_ADDRESS_COMMENT)) {
                        invoiceAddress = generate;
                    }
                }
                if (takeAddress == null) {
                    takeAddress = addressGenerate.get(0);
                }
                if (invoiceAddress == null) {
                    invoiceAddress = addressGenerate.get(0);
                }
            }

            String invoiceAddr = regionService.getRegionNameStringByMinRegionIds(invoiceAddress.getAreaIds());
            String takeAddr = regionService.getRegionNameStringByMinRegionIds(takeAddress.getAreaIds());

            saleorder.setInvoiceTraderAddress(invoiceAddress.getAddress());
            saleorder.setInvoiceTraderAreaId(invoiceAddress.getAreaId());
            saleorder.setInvoiceTraderArea(invoiceAddr);
            saleorder.setInvoiceTraderAddressId(invoiceAddress.getTraderAddressId());

            saleorder.setTakeTraderAddress(takeAddress.getAddress());
            saleorder.setTakeTraderAreaId(takeAddress.getAreaId());
            saleorder.setTakeTraderArea(takeAddr);
            saleorder.setTakeTraderAddressId(takeAddress.getTraderAddressId());
            // 订单跟发票地址保持一致
            saleorder.setTraderAddress(invoiceAddress.getAddress());
            saleorder.setTraderAreaId(invoiceAddress.getAreaId());
            saleorder.setTraderArea(invoiceAddr);
            saleorder.setTraderAddressId(invoiceAddress.getTraderAddressId());
        }
    }

    private TraderContactGenerate getContactByMobileAndName(Integer traderId, String mobile, String name,
                                                            String telphone) {
        // 联系人
        TraderContactGenerateExample contactExample = new TraderContactGenerateExample();
        contactExample.createCriteria().andTraderIdEqualTo(traderId);
        List<TraderContactGenerate> list = traderContactGenerateMapper.selectByExample(contactExample);
        if (CollectionUtils.isEmpty(list)) {
            return new TraderContactGenerate();

        } else {
            return list.get(0);
        }
    }

}
