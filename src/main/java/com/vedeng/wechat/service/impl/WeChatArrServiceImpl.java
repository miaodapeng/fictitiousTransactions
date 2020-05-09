package com.vedeng.wechat.service.impl;

import com.vedeng.common.constant.ApiUrlConstant;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.model.TemplateVar;
import com.vedeng.common.model.vo.ReqTemplateVariable;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.WeChatSendTemplateUtil;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.wechat.dao.WeChatArrMapper;
import com.vedeng.wechat.model.WeChatArr;
import com.vedeng.wechat.service.WeChatArrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.vedeng.common.controller.BaseSonContrller.sendTemplateMsg;

/**
 * @Description:
 * @Author: Franlin.wu
 * @Version: V1.0.0
 * @Since: 1.0
 * @Date: 2019/6/26
 */
@Service
public class WeChatArrServiceImpl extends BaseServiceimpl implements WeChatArrService {

    @Autowired
    private WeChatArrMapper weChatArrMapper;

    @Value("${vx_service}")
    protected String vxService;

    @Value("${OrdeToSignFor}")
    protected String OrdeToSignFor;  //订单签收通知id


    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;


    @Override
    public void sendTemplateMsgHcForOrderOk(WeChatArr arr) {
        logger.info("WeChatArrServiceImpl.sendTemplateMsgHcForOrderOk | 医械购微信发送订单签收模板消息 | begin .......");
        logger.info("WeChatArrServiceImpl.sendTemplateMsgHcForOrderOk | 医械购微信发送订单签收模板消息 | WeChatArr:{}", arr);
        if(null == arr || null == arr.getSaleorderId()) {
            return;
        }
        Integer orderType = arr.getOrderType();
        Integer saleorderId = arr.getSaleorderId();
        //修改于VDERP-1331 ,2019-10-14 开始
        Saleorder saleorder = new Saleorder();
        if(OrderConstant.ORDER_TYPE_HC.equals(orderType)) {
        	saleorder = saleorderService.getsaleorderbySaleorderId(saleorderId);
        }
        // add by franlin.wu for[微信推送 医械购 ] at 2019-06-20 begin
        if(OrderConstant.ORDER_TYPE_HC.equals(orderType)&&saleorder.getWebTakeDeliveryTime().equals(0L)) {
        //修改于VDERP-1331 ,2019-10-14 前台没有确认收货所以WebTakeDeliveryTime为默认值0 结束

            // add by franlin.wu for [微信推送 订单签收] at 2019-06-19 begin
            ReqTemplateVariable reqTemp = new ReqTemplateVariable();
            logger.info("sendTemplateMsgHcForOrderOk | traderConMobile:{}", arr.getTraderContactMobile());
            // 订单客户联系人
            reqTemp.setPhoneNumber(arr.getTraderContactMobile());
            // 模板消息数字字典Id
            reqTemp.setTemplateId(WeChatSendTemplateUtil.TEMPLATE_ORDER_SIGNING_NOTICE);

            TemplateVar first = new TemplateVar();
            String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已全部收货", SysOptionConstant.ID_TEMPLATE_ORDER_SIGNING_NOTICE_FIRST);
            logger.info("获取数据配置 | firstStr：{} ", firstStr);
            first.setValue(firstStr);

            TemplateVar keyword1 = new TemplateVar();
            TemplateVar keyword2 = new TemplateVar();
            TemplateVar keyword3 = new TemplateVar();
            TemplateVar keyword4 = new TemplateVar();

            TemplateVar remark = new TemplateVar();
            String remarkStr = getConfigStringByDefault("感谢您对医械购的支持与信任，如有疑问请联系：18651638763", SysOptionConstant.ID_WECHAT_TEMPLATE_REMARK);
            remark.setValue(remarkStr);

            // （取耗材商城-销售详情页-交易信息，“订单实际金额”信息
            // 获取交易信息（订单实际金额，客户已付款金额）
            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
            BigDecimal realAmount =  saleorderDataInfo.get("realAmount");
            // 订单金额
            keyword1.setValue(null == realAmount ? "0.00" : realAmount.toString() + "元");

            // 获取订单产品信息(与订单详情中获取相同)
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(arr.getSaleorderId());
            sale.setTraderId(arr.getTraderId());
            sale.setCompanyId(arr.getCompanyId());
            List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
            //商品总数量（除去售后数量）
            Integer saleorderAllNum = 0;
            String saleorderFirstGoodsName = "";
            if(saleorderGoodsList != null && saleorderGoodsList.size()>0) {
                for (SaleorderGoods sg : saleorderGoodsList) {
                    // 运费
                    if(null == sg || "V127063".equals(sg.getSku()) || "V251526".equals(sg.getSku()) || "V252843".equals(sg.getSku()) || "V256675".equals(sg.getSku())) {
                        continue;
                    }
                    if(EmptyUtils.isBlank(saleorderFirstGoodsName)) {
                        saleorderFirstGoodsName = sg.getGoodsName();
                    }
                    //商品数-售后数
                    saleorderAllNum = saleorderAllNum+(sg.getNum()-sg.getAfterReturnNum());
                }
            }
            // 商品详情
            keyword2.setValue(saleorderFirstGoodsName + "等 " + saleorderAllNum + "个商品");
            // 收货信息
            keyword3.setValue(arr.getTraderContactName() + " " + arr.getTraderContactMobile() + " " + arr.getTraderAddress());
            // 订单编号
            keyword4.setValue(arr.getSaleorderNo());

            reqTemp.setFirst(first);
            reqTemp.setKeyword1(keyword1);
            reqTemp.setKeyword2(keyword2);
            reqTemp.setKeyword3(keyword3);
            reqTemp.setKeyword4(keyword4);

            reqTemp.setRemark(remark);
            // 发送 微信服务 消息模板 订单签收
            WeChatSendTemplateUtil.sendTemplateMsg(apiUrl + ApiUrlConstant.API_WECHAT_SEND_TEMPLATE_MSG, reqTemp);

        }
        logger.info("WeChatArrServiceImpl.sendTemplateMsgHcForOrderOk | 医械购微信发送订单签收模板消息 | end .......");
        // add by franlin.wu for[微信推送 医械购  订单签收] at 2019-06-20 begin
    }

    @Override
    public void sendTemplateMsgVedeng(WeChatArr arr) {
        logger.info("sendTemplateMsgVedeng | 贝登微信发送订单签收模板消息 | begin .......");
        logger.info("sendTemplateMsgVedeng | 贝登微信发送订单签收模板消息 | WeChatArr:{}", arr);
        if(null == arr || null == arr.getSaleorderId()) {
            return;
        }
        Integer orderType = arr.getOrderType();
        Integer saleorderId = arr.getSaleorderId();
        //必须是BD JX DH VS 订单
        if (OrderConstant.ORDER_TYPE_SALE.equals(orderType) || OrderConstant.ORDER_TYPE_DH.equals(orderType)
                || OrderConstant.ORDER_TYPE_JX.equals(orderType) || OrderConstant.ORDER_TYPE_BD.equals(orderType)){
            com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable reqTemp = new com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable();
            logger.info("sendTemplateMsgHcForOrderOk | traderConMobile:{}", arr.getTraderContactMobile());
            // 订单客户联系人
            reqTemp.setMobile(arr.getTraderContactMobile());
           // reqTemp.setMobile("17554243894");
            // 模板消息数字字典Id
            reqTemp.setTemplateId(OrdeToSignFor);

            com.vedeng.passport.api.wechat.dto.template.TemplateVar first = new com.vedeng.passport.api.wechat.dto.template.TemplateVar();
            String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已全部收货", SysOptionConstant.ID_TEMPLATE_ORDER_SIGNING_NOTICE_FIRST);
            logger.info("获取数据配置 | firstStr：{} ", firstStr);
            first.setValue(firstStr);

            com.vedeng.passport.api.wechat.dto.template.TemplateVar keyword1 = new com.vedeng.passport.api.wechat.dto.template.TemplateVar();
            com.vedeng.passport.api.wechat.dto.template.TemplateVar keyword2 = new com.vedeng.passport.api.wechat.dto.template.TemplateVar();
            com.vedeng.passport.api.wechat.dto.template.TemplateVar keyword3 = new com.vedeng.passport.api.wechat.dto.template.TemplateVar();
            com.vedeng.passport.api.wechat.dto.template.TemplateVar keyword4 = new com.vedeng.passport.api.wechat.dto.template.TemplateVar();

            com.vedeng.passport.api.wechat.dto.template.TemplateVar remark = new com.vedeng.passport.api.wechat.dto.template.TemplateVar();
            String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
            remark.setValue(remarkStr);

            // （取耗材商城-销售详情页-交易信息，“订单实际金额”信息
            // 获取交易信息（订单实际金额，客户已付款金额）
            Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
            BigDecimal realAmount =  saleorderDataInfo.get("realAmount");
            // 订单金额
            keyword1.setValue(null == realAmount ? "0.00" : realAmount.toString() + "元");

            // 获取订单产品信息(与订单详情中获取相同)
            Saleorder sale = new Saleorder();
            sale.setSaleorderId(arr.getSaleorderId());
            sale.setTraderId(arr.getTraderId());
            sale.setCompanyId(arr.getCompanyId());
            List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
            //商品总数量（除去售后数量）
            Integer saleorderAllNum = 0;
            String saleorderFirstGoodsName = "";
            if(saleorderGoodsList != null && saleorderGoodsList.size()>0) {
                for (SaleorderGoods sg : saleorderGoodsList) {
                    // 运费
                    if(null == sg || "V127063".equals(sg.getSku()) || "V251526".equals(sg.getSku()) || "V252843".equals(sg.getSku()) || "V256675".equals(sg.getSku())) {
                        continue;
                    }
                    if(EmptyUtils.isBlank(saleorderFirstGoodsName)) {
                        saleorderFirstGoodsName = sg.getGoodsName();
                    }
                    //商品数-售后数
                    saleorderAllNum = saleorderAllNum+(sg.getNum()-sg.getAfterReturnNum());
                }
            }
            // 商品详情
            keyword2.setValue(saleorderFirstGoodsName + "等 " + saleorderAllNum + "个商品");
            // 收货信息
            keyword3.setValue(arr.getTraderContactName() + " " + arr.getTraderContactMobile() + " " + arr.getTraderAddress());
            // 订单编号
            keyword4.setValue(arr.getSaleorderNo()+"\r\n");

            reqTemp.setFirst(first);
            reqTemp.setKeyword1(keyword1);
            reqTemp.setKeyword2(keyword2);
            reqTemp.setKeyword3(keyword3);
            reqTemp.setKeyword4(keyword4);

            reqTemp.setRemark(remark);
            // 发送 微信服务 消息模板 订单签收
            sendTemplateMsg(vxService+"/wx/wxchat/send", reqTemp);
            //sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);

        }
        logger.info("贝登订单签收通知完毕.");
        // add by franlin.wu for[微信推送 贝登  订单签收] at 2019-06-20 begin
    }
}
