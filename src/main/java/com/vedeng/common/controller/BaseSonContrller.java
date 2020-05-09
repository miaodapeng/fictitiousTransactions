package com.vedeng.common.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedeng.common.constant.ApiUrlConstant;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.common.util.WeChatSendTemplateUtil;
import com.vedeng.finance.model.Invoice;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable;
import com.vedeng.passport.api.wechat.dto.res.ResWeChatDTO;
import com.vedeng.passport.api.wechat.dto.template.TemplateVar;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.ActiongroupService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.wechat.dao.WeChatArrMapper;
import com.vedeng.wechat.model.WeChatArr;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: erp
 * @description: 公告类
 * @author: addis
 * @create: 2019-09-25 15:40
 **/
public class BaseSonContrller {
    @Autowired
    @Qualifier("actionService")
    private ActionService actionService;
    @Autowired
    @Qualifier("actiongroupService")
    private ActiongroupService actiongroupService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("logisticsService")
    private LogisticsService logisticsService;
    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;
    @Autowired
    @Qualifier("ftpUtilService")
    protected FtpUtilService ftpUtilService;

    @Resource
    private BaseService baseService;
    @Value("${redis_dbtype}")
    protected String dbType;
    @Value("${file_url}")
    protected String domain;

    @Value("${rest_db_url}")
    protected String restDbUrl;

    @Value("${api_http}")
    protected String api_http;

    @Value("${api_url}")
    protected String apiUrl;

    @Value("${ws_url}")
    protected String wsUrl;
    @Value("${mjx_url}")
    protected String mjxUrl;
    @Value("${mjx_page}")
    protected String mjxPage;
    @Value("${vx_service}")
    protected String vxService;

    @Value("${shipmentToRemind}")
    protected String shipmentToRemind;//发货提醒模板id

    @Value("${OrdeToSignFor}")
    protected String OrdeToSignFor;  //订单签收通知id

    @Value("${invoiceSent}")
    protected String invoiceSent;//发票寄出提醒模板id

    @Value("${ApplicationApproved}")
    protected String ApplicationApproved; //申请审核通过模板id

    @Value("${orderConfirmationReminder}")
    protected String orderConfirmationReminder;  //订单待确认提醒模板id

    /**
     * ERP 发送医械购微信模板消息开关 1 默认开启 0 不开启
     */
    @Value("${erp_send_yxg_wx_temp_msg_flag}")
    protected Integer sendYxgWxTempMsgFlag;

    protected String OrderClosingNotice="dw3znohSAUelM0w5rtStAhXE8bixVPhsGOgTbV1W43U"; //订单关闭通知

    private String phone="18362995095";

    public static final Logger log = LoggerFactory.getLogger("controller");

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Description: 微信服务号发货推送消息
     * @Param:
     * @return:
     * @Author: addis
     * @Date: 2019/9/25
     */

    public void sendTemplateVxService(Saleorder saleorders, Map<String, String> sTempMap) {
        logger.info("微信服务号推送消息 发货提醒");
        Integer orderType = saleorders.getOrderType();

        Integer saleorderId = saleorders.getSaleorderId();
        if (null != saleorders && null != sTempMap) {
           //必须是BD JX DH VS 订单
            if (OrderConstant.ORDER_TYPE_SALE.equals(orderType) || OrderConstant.ORDER_TYPE_DH.equals(orderType)
                    || OrderConstant.ORDER_TYPE_JX.equals(orderType) || OrderConstant.ORDER_TYPE_BD.equals(orderType)) {
                logger.warn("微信服务号推送消息 发货提醒");

                logger.info("微信服务号推送消息模板");
                // add by franlin.wu for [微信推送 发货提醒] at 2019-06-19 begin
                ReqTemplateVariable reqTemp = new ReqTemplateVariable();
                logger.info("开始发货");
                // 订单客户联系人
                //reqTemp.setMobile(phone);
                reqTemp.setMobile(saleorders.getTraderContactMobile());
                // 模板消息数字字典Id
                reqTemp.setTemplateId(shipmentToRemind);

                // 头
                TemplateVar first = new TemplateVar();
                String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已发货，请注意查收：", SysOptionConstant.ID_WECHAT_TEMPLATE_ORDER_SIGNING_NOTICE_FIRST);
                logger.info("sendTemplateMsgHcForShip | first :{}", firstStr);
                first.setValue(firstStr + "\r\n");
                // 商品名称
                TemplateVar keyword1 = new TemplateVar();
                // 订单号
                keyword1.setValue(sTempMap.get("saleorderNo"));
                // 商品名称
                TemplateVar keyword2 = new TemplateVar();
                // saleorderAllNum
                keyword2.setValue(sTempMap.get("expressFirstGoodsName") + "等" + " " + sTempMap.get("saleorderAllNum") + "个商品");

                TemplateVar keyword3 = new TemplateVar();
                // 商品数量
                keyword3.setValue(sTempMap.get("expressAllNum"));

                TemplateVar keyword4 = new TemplateVar();
                // 下单时间
                keyword4.setValue(sTempMap.get("validTime"));
                // 客户信息 联系人 以及 客户手机号
                TemplateVar keyword5 = new TemplateVar();
                String keyword5Str = sTempMap.get("customerInfo");
                String keyword6Str = sTempMap.get("logisticsName") + " " + sTempMap.get("logisticsNo");
                keyword5Str += "\r\n物流信息:" + keyword6Str + "\r\n";
                keyword5.setValue(keyword5Str);
                TemplateVar keyword6 = new TemplateVar();
                //快递信息（快递公司名称+快递单号）
                keyword6.setValue(keyword6Str);
                TemplateVar remark = new TemplateVar();
                String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
                logger.info("sendTemplateMsgHcForShip | remark :{}", remarkStr);
                remark.setValue(remarkStr);
                reqTemp.setFirst(first);
                reqTemp.setKeyword1(keyword1);
                reqTemp.setKeyword2(keyword2);
                reqTemp.setKeyword3(keyword3);
                reqTemp.setKeyword4(keyword4);
                reqTemp.setKeyword5(keyword5);
                reqTemp.setKeyword6(keyword6);
                reqTemp.setRemark(remark);
                sendTemplateMsg(vxService+"/wx/wxchat/send", reqTemp);
                //sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);
                logger.info("sendTemplateMsgHcForShip | 微信 发货 模板消息 | end .......");

            }
        }
    }

    /**
     * @Description: 订单签收通知
     * @Param: [saleorders, sTempMap]
     * @return: void
     * @Author: addis
     * @Date: 2019/9/25
     */
    public void sendOrderFor(Saleorder saleorders, Map sTempMap) {
        logger.info("微信消息公众号订单签收通知begin .......");
        if (null == saleorders) {
            return;
        }
        Integer orderType = saleorders.getOrderType();

        Integer saleorderId = saleorders.getSaleorderId();

        //必须是BD JX DH VS 订单
        if (saleorders.getArrivalStatus().equals(2)&&(OrderConstant.ORDER_TYPE_SALE.equals(orderType) || OrderConstant.ORDER_TYPE_DH.equals(orderType)
                || OrderConstant.ORDER_TYPE_JX.equals(orderType) || OrderConstant.ORDER_TYPE_BD.equals(orderType))){
            // 贝登消息推送
            ReqTemplateVariable reqTemp = new ReqTemplateVariable();
            logger.info("sendTemplateMsgHcForOrderOk | traderConMobile:{}", saleorders.getTraderContactMobile());
            // 订单客户联系人
            reqTemp.setMobile(saleorders.getTraderContactMobile());
            // reqTemp.setMobile("17554243894");
            // 模板消息数字字典Id
            reqTemp.setTemplateId(OrdeToSignFor);


            TemplateVar first = new TemplateVar();
            String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已全部收货", SysOptionConstant.ID_TEMPLATE_ORDER_SIGNING_NOTICE_FIRST);
            logger.info("获取数据配置 | firstStr：{} ", firstStr);
            first.setValue(firstStr + "\r\n");

            TemplateVar keyword1 = new TemplateVar();
            TemplateVar keyword2 = new TemplateVar();
            TemplateVar keyword3 = new TemplateVar();
            TemplateVar keyword4 = new TemplateVar();

            TemplateVar remark = new TemplateVar();
            String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
            remark.setValue(remarkStr);

            if (null != sTempMap) {
                // 订单金额
                keyword1.setValue(String.valueOf(sTempMap.get("totalAmount")) + "元");
                String saleorderAllNum =String.valueOf(sTempMap.get("saleorderAllNum"));
                // 商品详情
                keyword2.setValue((String) sTempMap.get("saleorderFirstGoodsName") + "等 " + saleorderAllNum + "个商品");
                // 收货信息
                keyword3.setValue((String) sTempMap.get("traderContactName") + " " + (String) sTempMap.get("traderContactMobile") + " " + (String) sTempMap.get("takeTraderArea")+(String) sTempMap.get("takeTraderAddress"));
                // 订单编号
                keyword4.setValue(saleorders.getSaleorderNo() + "\r\n");

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
            logger.info("贝登微信发送订单签收模板消息 | end .......");
        }

    }


    /**
     * <b>Description: 贝登 发票微信 推送模板消息</b>
     * @param invoList   请求url
     * <b>Author：</b> addis.
     * <b>Date:</b> 2019年6月19日
     * @return
     */
    protected void wxSendMsgForInoice(List<Invoice> invoList) {
        logger.info("开始发票提醒");
        BigDecimal totalAmount=new BigDecimal(0.0);
        StringBuffer invoiceNos=new StringBuffer();
        // add by franlin.wu for [微信模板消息推送 ] at 2019-09-20 begin
        if(CollectionUtils.isNotEmpty(invoList)) {
            for(Invoice i:invoList){
                totalAmount =totalAmount.add(i.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
                invoiceNos.append(i.getInvoiceNo()+"、");
            }

                Integer orderType = invoList.get(0).getOrderType();
                //必须是BD JX DH VS 订单
                if (OrderConstant.ORDER_TYPE_SALE.equals(orderType) || OrderConstant.ORDER_TYPE_DH.equals(orderType)
                        || OrderConstant.ORDER_TYPE_JX.equals(orderType) || OrderConstant.ORDER_TYPE_BD.equals(orderType)) {
                    // 订单联系人
                    String traderConMobile =invoList.get(0).getTraderContactMobile();
                    logger.info("wxSendMsgForInvoice | traderConMobile:{}", traderConMobile);

                    ReqTemplateVariable reqTemp = new ReqTemplateVariable();
                    // 订单客户联系人
                   reqTemp.setMobile(traderConMobile);
                   //reqTemp.setMobile("17554243894");
                    // 模板消息数字字典Id  发票寄出 提醒
                    reqTemp.setTemplateId(invoiceSent);

                    // 头
                    TemplateVar first = new TemplateVar();
                    String firstStr = getConfigStringByDefault("尊敬的客户，您的发票已寄出，请注意查收。", SysOptionConstant.ID_TEMPLATE_INVOICE_REMINDER_FIRST);
                    first.setValue(firstStr + "\r\n");
                    TemplateVar remark = new TemplateVar();
                    String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
                    remark.setValue(remarkStr);

                    TemplateVar keyword1 = new TemplateVar();
                    TemplateVar keyword2 = new TemplateVar();
                    TemplateVar keyword3 = new TemplateVar();
                    TemplateVar keyword4 = new TemplateVar();

                    keyword1.setValue(invoList.get(0).getTraderName());
                 //   keyword2.setValue(null == invo.getAmount() ? "0.00" : invo.getAmount().toString() + "元");
                    keyword2.setValue(totalAmount.toString() + "元");
                    String invoiceNo = invoList.get(0).getInvoiceNo();
                    // 物流公司 + 快递单号
                    keyword3.setValue(invoList.get(0).getLogisticsName() + " " + invoList.get(0).getLogisticsNo() + "\r\n包含发票号：" + invoiceNos.toString() + "\r\n");
                    keyword4.setValue(invoiceNos.toString());

                    reqTemp.setFirst(first);
                    reqTemp.setKeyword1(keyword1);
                    reqTemp.setKeyword2(keyword2);
                    reqTemp.setKeyword3(keyword3);
                    reqTemp.setKeyword4(keyword4);
                    reqTemp.setRemark(remark);
                    // 发票寄出 提醒
                    sendTemplateMsg(vxService + "/wx/wxchat/send", reqTemp);
                    //sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);
                }
            }

    }
    /**
    * @Description: 待用户确认消息推送
    * @Param: [saleorders, sTempMap]
    * @return: void
    * @Author: addis
    * @Date: 2019/9/26
    */
    public void sendOrderConfirmed(Saleorder saleorders, Map sTempMap) {
        ReqTemplateVariable reqTemp = new ReqTemplateVariable();
        if (null != saleorders.getCreateMobile()) {
            // 订单客户联系人
            reqTemp.setMobile(saleorders.getCreateMobile());

        }
        // 模板消息数字字典Id  发票寄出 提醒
        //reqTemp.setMobile("17554243894");
        reqTemp.setJumpUrl(mjxPage+"?orderNo="+saleorders.getSaleorderNo());
        reqTemp.setTemplateId(orderConfirmationReminder);
        TemplateVar first = new TemplateVar();
        String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已通过审核，请在14天内完成确认", SysOptionConstant.THE_ORDER_IS_SUBJECT_TO_CUSTOMER_CONFIRMATION);
        logger.info("获取数据配置 | firstStr：{} ", firstStr);
        first.setValue(firstStr + "\r\n");
        TemplateVar keyword1 = new TemplateVar();
        TemplateVar keyword2 = new TemplateVar();
        TemplateVar keyword3 = new TemplateVar();
        TemplateVar keyword4 = new TemplateVar();

        TemplateVar remark = new TemplateVar();
        String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
        remark.setValue("截至时间：" + DateUtil.datePostponeTime(14) + "\r\n"+remarkStr);

        if (null != sTempMap) {
            // 订单编号
            keyword1.setValue(saleorders.getSaleorderNo());
            String saleorderAllNum = String.valueOf(sTempMap.get("saleorderAllNum"));
            // 商品名称
            keyword2.setValue((String) sTempMap.get("saleorderFirstGoodsName") + "等 " + saleorderAllNum + "个商品");
            // 订单金额
            keyword3.setValue(String.valueOf(sTempMap.get("totalAmount")) + "元");
            reqTemp.setFirst(first);
            reqTemp.setKeyword1(keyword1);
            reqTemp.setKeyword2(keyword2);
            reqTemp.setKeyword3(keyword3);
            reqTemp.setRemark(remark);
            // 发送 待用户确认消息推送
            sendTemplateMsg(vxService + "/wx/wxchat/send", reqTemp);
            //sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);
            logger.info("待用户确认消息推送结束");

        }
    }

    /**
    * @Description: 贝登申请通过提醒（1、已申请加入贝登精选  2、是贝登会员）
    * @Param: [webAccount]
    * @return: void
    * @Author: addis
    * @Date: 2019/9/27
    */
     public boolean  passReminder(WebAccount webAccount){
        if(webAccount!=null){
            if(webAccount.getIsVedengJoin()==1&&webAccount.getIsVedengJx()==1){  //必须是贝登会员和申请加入贝登才能发送
                ReqTemplateVariable reqTemp = new ReqTemplateVariable();
                reqTemp.setMobile(webAccount.getMobile());
               // reqTemp.setMobile(phone);
                reqTemp.setTemplateId(ApplicationApproved);
                //reqTemp.setTemplateId("OPENTM412465357");
 //               reqTemp.setJumpUrl("http://www.baidu.com");
                String firstStr = getConfigStringByDefault("尊敬的客户，您的贝登精选会员申请已审核通过：", SysOptionConstant.MEMBERSHIP_APPLICATION_HAS_BEEN_APPROVED);
                String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
                logger.info("获取数据配置 | firstStr：{} ", firstStr);
                TemplateVar first = new TemplateVar();
                first.setValue(firstStr + "\r\n");
                TemplateVar keyword1 = new TemplateVar();
                TemplateVar keyword2 = new TemplateVar();
                TemplateVar remark = new TemplateVar();

                keyword1.setValue(webAccount.getMobile());
                keyword2.setValue("已通过"+"\r\n");
                remark.setValue(remarkStr);
                reqTemp.setFirst(first);
                reqTemp.setKeyword1(keyword1);
                reqTemp.setKeyword2(keyword2);
                reqTemp.setRemark(remark);
                // 发送 贝登申请推送推送
                sendTemplateMsg(vxService + "/wx/wxchat/send", reqTemp);
                //sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);
                logger.info("贝登申请推送推送");
                return  true;
            }
        }
        return false;
     }

    /**
     *
     * <b>Description: 根据id查询数字字典值</b><br>
     * @param defaultValue  默认值
     * @param optionType
     * @return
     * <b>Author: Franlin.wu</b>
     * <br><b>Date: 2018年12月14日 下午2:37:40 </b>
     */
    public  String getConfigStringByDefault(String defaultValue, String optionType) {
        logger.debug("查询id：{}, 默认值：{}", optionType, defaultValue);

        try
        {
            // 根据id查询数字字典值
            SysOptionDefinition option = baseService.getFirstSysOptionDefinitionList(optionType);
            if(null != option && EmptyUtils.isNotBlank(option.getTitle())) {
                defaultValue = option.getTitle();
            }
        }
        catch(Exception e)
        {
            logger.error("根据id查询数字字典配置发生异常", e);
        }

        return defaultValue;
    }

    /**
     * <b>Description: 调用api的模板消息接口</b>
     * @param apiUrl   请求url
     * @param reqTemp  模板参数
     * <b>Author：</b> addis
     * <b>Date:</b> 2019年6月19日
     * @return
     */
    public static void sendTemplateMsg(String vxService, ReqTemplateVariable reqTemp) {
        log.info("sendTemplateMsg | 调用模板消息接口 | begin ........");
        log.info("sendTemplateMsg | 调用模板消息接口 | apiUrl:{}, reqTemp:{}", vxService, reqTemp);
        // 接口返回
        TypeReference<ResWeChatDTO> type = new TypeReference<ResWeChatDTO>() {};
        // 接口请求头
        HashMap<String, String> heads = new HashMap<String, String>();
        // 接口版本0
        heads.put(CommonConstants.INTER_VERSION, CommonConstants.INTER_VERSION_VALUE);
        // 调用接口
        ResWeChatDTO resultInfo =  HttpRestClientUtil.post2(vxService, type, heads, reqTemp);


        log.info("sendTemplateMsg | 调用模板消息接口 | 结果:{}", resultInfo);

        log.info("sendTemplateMsg | 调用模板消息接口 | end ........");
    }

}
