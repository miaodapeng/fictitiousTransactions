package com.vedeng.trader.service.impl;

import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.passport.api.wechat.dto.req.ReqTemplateVariable;
import com.vedeng.passport.api.wechat.dto.template.TemplateVar;
import com.vedeng.soap.ApiSoap;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.ActiongroupService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.service.WebAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

import static com.vedeng.common.controller.BaseSonContrller.sendTemplateMsg;

/**
 * @program: erp
 * @description: 注册用户
 * @author: addis
 * @create: 2020-03-16 10:32
 **/
@Service
public class WebAccountServiceImpl implements WebAccountService {
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
    @Value("${mjx_first_page}")
    protected String mjxFirstPage;
    @Value("${vx_service}")
    protected String vxService;
    @Autowired
    @Qualifier("apiSoap")
    private ApiSoap apiSoap;
    @Value("${send_vip_message}")
    protected String sendVipMessage;//会员开通提醒模板id



    /**
     * ERP 发送医械购微信模板消息开关 1 默认开启 0 不开启
     */
    @Value("${erp_send_yxg_wx_temp_msg_flag}")
    protected Integer sendYxgWxTempMsgFlag;

    protected String OrderClosingNotice="dw3znohSAUelM0w5rtStAhXE8bixVPhsGOgTbV1W43U"; //订单关闭通知

    private String phone="18362995095";

    public static final Logger log = LoggerFactory.getLogger("controller");

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    WebAccountMapper webAccountMapper;

    @Override
    public List<WebAccount> SendVipMessageMethod(WebAccount webAccount) {
        List<WebAccount> webAccountList=webAccountMapper.getWebAccountListByParam(webAccount);
        if(webAccountList!=null &&webAccountList.size()>0){
            for(WebAccount webAccount1 : webAccountList){
                    if(StringUtil.isNotBlank(webAccount1.getCompanyName())){  //必须是贝登会员和申请加入贝登才能发送
                        ReqTemplateVariable reqTemp = new ReqTemplateVariable();
                        reqTemp.setMobile(webAccount1.getMobile());
                        // reqTemp.setMobile(phone);
                        reqTemp.setTemplateId(sendVipMessage);
                        reqTemp.setJumpUrl(mjxFirstPage);
                        //reqTemp.setTemplateId("OPENTM412465357");
                        //               reqTemp.setJumpUrl("http://www.baidu.com");
                        String anon="可查看商品价格和销售攻略";
                        String firstStr = getConfigStringByDefault("尊敬的客户，您的贝登商城会员申请已审核通过：", SysOptionConstant.WECHAT_TEMPLATE_VEDENG_VIP_FIRST);
                        String remarkStr = getConfigStringByDefault("感谢您对贝登的支持与信任，如有疑问请联系：4006-999-569", SysOptionConstant.WECHAT_TEMPLATE_BEDENG_REMARK);
                        logger.info("获取数据配置 | firstStr：{} ", firstStr);
                        TemplateVar first = new TemplateVar();
                        first.setValue(firstStr + "\r\n");
                        TemplateVar keyword1 = new TemplateVar();
                        TemplateVar keyword2 = new TemplateVar();
                        TemplateVar keyword3 = new TemplateVar();
                        TemplateVar remark = new TemplateVar();

                        keyword1.setValue(webAccount1.getCompanyName());
                        keyword2.setValue(DateUtil.getNowDate(null));
                        keyword3.setValue(anon);
                        remark.setValue(remarkStr);
                        reqTemp.setFirst(first);
                        reqTemp.setKeyword1(keyword1);
                        reqTemp.setKeyword2(keyword2);
                        reqTemp.setKeyword3(keyword3);
                        reqTemp.setRemark(remark);
                        // 发送 贝登申请推送推送
                        sendTemplateMsg(vxService + "/wx/wxchat/send", reqTemp);
                        //sendTemplateMsg("http://172.16.3.100:8280/wx/wxchat/send",reqTemp);
                        logger.info("贝登申请推送推送");
                        WebAccount webAccount2=new WebAccount();
                        webAccount2.setMobile(webAccount1.getMobile());
                        webAccount2.setIsSendMessage(1);
                        apiSoap.updateisVedengJoin(webAccount2);

                    }

            }
        }

        return null;
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

}
