package com.vedeng.common.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageTemplate;
import com.vedeng.system.model.MessageUser;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.MessageService;
import com.vedeng.system.service.MessageTemplateService;
import com.vedeng.system.service.SysOptionDefinitionService;

import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 消息推送
 * 
 * @author scott
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.websocket <br>
 *       <b>ClassName:</b> EchoSpcket <br>
 *       <b>Date:</b> 2017年11月28日 下午2:24:46
 */
//@ServerEndpoint(value = "/websocket/echo", configurator = SpringConfigurator.class)
public class EchoSpcket {

    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

    private MessageTemplateService messageTemplateService =
            (MessageTemplateService) context.getBean("messageTemplateService");
    private MessageService messageService = (MessageService) context.getBean("messageService");
    private SysOptionDefinitionService sysOptionDefinitionService =
            (SysOptionDefinitionService) context.getBean("sysOptionDefinitionService");

    private Integer userId;
    private Integer type;
    private static CopyOnWriteArraySet<EchoSpcket> sessions = new CopyOnWriteArraySet<EchoSpcket>();
    public static ConcurrentMap<Integer, Session> map = new ConcurrentHashMap<Integer, Session>();
    private static List<Integer> userIds = new ArrayList<>();
    private Session session;

    public EchoSpcket() {
    }

    @OnOpen
    public void open(Session session) {
        String queryString = session.getQueryString();
        //获取登录的userId
       String []strPames = queryString.split("&");
       if(strPames.length>1){
           this.userId = Integer.parseInt(strPames[0].split("=")[1]);
           this.type = Integer.parseInt(strPames[1].split("=")[1]);
       }

        //userId = Integer.parseInt(queryString.substring(queryString.indexOf("=") + 1));
        this.session = session;
        sessions.add(this);
        try{
            EchoSpcket.map.put(userId, session);
            if(this.type == 1){
                sendMsg(session,this.userId);
            }
        }catch (Exception e){

        }

    }

    @SuppressWarnings("unused")
    @OnMessage
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void receiveMsg(Session session, String msg, String... str) throws IOException {
        User session_user = new User();
        //心跳测试
        if ("HeartBeat".equals(msg)) {
            session.getBasicRemote().sendText("HeartBeat");
        } else {
            if (str.length >= 2 && "2".equals(str[1])) {
                session_user.setUserId(Integer.valueOf(str[1]));// str[1]作为系统管理员的userID
            } else {
                session_user = (User) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest().getSession().getAttribute(ErpConst.CURR_USER);
            }
            JSONObject jsonObject = JSONObject.fromObject(msg);

            String toUserIds = jsonObject.getString("toUserId");
            String[] toUserId = toUserIds.split("_");

            Integer messageTemplateId = Integer.parseInt(jsonObject.getString("templateId"));

            String parameters = jsonObject.getString("parameters");
            Map<String, String> paramsMap = JSON.parseObject(parameters, Map.class);

            String url = jsonObject.getString("url");

            Integer category = 0;// 站内信类型
            if (messageTemplateId == 1 || messageTemplateId == 2 || messageTemplateId == 60 || messageTemplateId == 72
                    || messageTemplateId == 73 || messageTemplateId == 74) {
                category = 594;// 客户审核
            }
            if (messageTemplateId == 3 || messageTemplateId == 4 || messageTemplateId == 5) {
                category = 597;// 报价审核
            }
            if (messageTemplateId == 6 || messageTemplateId == 7 || messageTemplateId == 8) {
                category = 592;// 订单审核
            }
            if (messageTemplateId == 9 || messageTemplateId == 10 || messageTemplateId == 11
                    || messageTemplateId == 34) {
                category = 479;// 订单提醒
            }
            if (messageTemplateId == 12 || messageTemplateId == 13 || messageTemplateId == 56) {
                category = 599;// 商品审核
            }
            if (messageTemplateId == 14 || messageTemplateId == 15 || messageTemplateId == 16 || messageTemplateId == 57
                    || messageTemplateId == 58 || messageTemplateId == 59) {
                category = 601;// 订单审核-采购
            }
            if (messageTemplateId == 17) {
                category = 605;// 订单提醒-采购
            }
            if (messageTemplateId == 18 || messageTemplateId == 19 || messageTemplateId == 20) {
                category = 593;// 订单采购
            }
            if (messageTemplateId == 21 || messageTemplateId == 71) {
                category = 606;// 开票审核
            }
            if (messageTemplateId == 22 || messageTemplateId == 23) {
                category = 595;// 帐期审核
            }
            if (messageTemplateId == 24 || messageTemplateId == 25) {
                category = 600;// 帐期审核
            }
            if (messageTemplateId == 26) {
                category = 607;// 帐期管理
            }
            if (messageTemplateId == 27 || messageTemplateId == 28 || messageTemplateId == 29) {
                category = 603;// 售后审核
            }
            if (messageTemplateId == 30) {
                category = 604;// 注册用户
            }
            if (messageTemplateId == 31 || messageTemplateId == 32) {
                category = 598;// 报价咨询
            }
            if (messageTemplateId == 33) {
                category = 596;// 资质提醒
            }
            if (messageTemplateId == 35 || messageTemplateId == 36 || messageTemplateId == 37) {
                category = 602;// 备货审核
            }

            if (messageTemplateId == 38 || messageTemplateId == 39 || messageTemplateId == 40 || messageTemplateId == 41
                    || messageTemplateId == 42 || messageTemplateId == 43) {
                category = 624;// 售后管理
            }
            if (messageTemplateId == 44) {
                category = 625;// 帐期申请
            }
            if (messageTemplateId == 45 || messageTemplateId == 46 || messageTemplateId == 47) {
                category = 626;// 报价管理
            }
            if (messageTemplateId == 48 || messageTemplateId == 48 || messageTemplateId == 50
                    || messageTemplateId == 69) {
                category = 627;// 商机管理
            }
            if (messageTemplateId == 51 || messageTemplateId == 52 || messageTemplateId == 53) {
                category = 628;// 文件寄送
            }
            if (messageTemplateId == 54 || messageTemplateId == 55 || messageTemplateId == 70) {
                category = 629;// 付款管理
            }
            if (messageTemplateId == 61 || messageTemplateId == 62 || messageTemplateId == 63) {
                category = 632;// 付款管理
            }
            if (messageTemplateId == 64 || messageTemplateId == 65 || messageTemplateId == 66 || messageTemplateId == 75
                    || messageTemplateId == 76 || messageTemplateId == 77 || messageTemplateId == 78) {
                category = 633;// 订单修改
            }
            if (messageTemplateId == 67 || messageTemplateId == 68) {
                category = 639;// 订货管理
            }
            if (messageTemplateId == 79) {
                category = 854;// 账期提醒
            }
            if (messageTemplateId == 86) {
                category = 867;// 产品成本
            }
            if (messageTemplateId == 87 || messageTemplateId == 88) {
                category = 896;// 合同审核
            }
            // String type = jsonObject.getString("type");

            // 根据id获取模板
            MessageTemplate template = new MessageTemplate();
            template.setMessageTemplateId(messageTemplateId);
            MessageTemplate messageTemplate = messageTemplateService.getMessageTemplate(template);

            if (null != messageTemplate) {
                String title = messageTemplate.getTitle();
                String content = messageTemplate.getContent();
                for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                    String key = "\\{\\$" + entry.getKey() + "\\}";
                    String value = entry.getValue();
                    if (value == null) {
                        title = title.replaceAll(key, value);
                        content = content.replaceAll(key, "");
                    } else {
                        title = title.replaceAll(key, value);
                        content = content.replaceAll(key, value);
                    }
                }
                String returnMsg = "title:" + title + "content:" + content;

                String msgs = returnMsg;
                // 新增消息
                Message message = new Message();
                Long time = DateUtil.sysTimeMillis();
                message.setCategory(category);
                message.setTitle(msgs.substring(msgs.indexOf("title:") + 6, msgs.lastIndexOf("content:")));
                message.setContent(msgs.substring(msgs.indexOf("content:") + 8, msgs.length()));
                message.setUrl(url);
                if (null != session_user && null != session_user.getUserId() && session_user.getUserId() > 0) {
                    message.setCreator(session_user.getUserId());
                } else {
                    message.setCreator(0);
                }
                message.setAddTime(time);
                if (str != null && str.length > 0) {
                    message.setSourceName(str[0]);
                }
                Integer mId = messageService.saveMessage(message);
                // 保存消息和关联用户
                List<MessageUser> messageUserList = new ArrayList<MessageUser>();
                for (String id : toUserId) {
                    MessageUser messageUser = new MessageUser();
                    messageUser.setIsView(0);
                    messageUser.setUserId(Integer.parseInt(id));
                    messageUser.setMessageId(mId);
                    messageUser.setViewTime(0L);
                    messageUserList.add(messageUser);
                }
                messageService.saveMessageList(messageUserList);
                /*// 获取消息类型
                SysOptionDefinition sd = new SysOptionDefinition();
                sd.setSysOptionDefinitionId(category);
                SysOptionDefinition s = sysOptionDefinitionService.getOptionById(sd);

                // modify by Franlin at 2018-08-13 for[4508 从消息弹框点进去查看该消息，仍是未读状态] begin
                url = url + "&messageId=" + mId;
                // modify by Franlin at 2018-08-13 for[4508 从消息弹框点进去查看该消息，仍是未读状态] end

                if (s != null) {
                    returnMsg = "title:" + s.getTitle() + "content:" + title + "url:" + url;
                } else {
                    returnMsg = "title:消息content:" + title + "url:" + url;
                }*/
                returnMsg = getReturnMsg(category,url,title,mId);
                // 消息推送
                for (String id : toUserId) {
                    Session to_session = EchoSpcket.map.get(Integer.parseInt(id));
                    if (to_session != null) {
                        if (to_session.isOpen()) {
                            to_session.getBasicRemote().sendText(returnMsg);
                        }
                    }
                }
            }
        }

    }

    @OnClose
    public void close(Session session) {
        sessions.remove(this);
    }

    /**
     * 用户是重新连接的，登陆后，将客户未读的消息推送给用户
     */
    private void sendMsg(Session session ,Integer userId){
        //查询用户所有未读的消息
        String  returnMsg = "";
        List<Message> msgList = messageService.getMessageList(userId);
        if(CollectionUtils.isNotEmpty(msgList)){
            Session to_session = EchoSpcket.map.get(userId);
            if (to_session != null) {
                if (to_session.isOpen()) {
                    for(int i=0;i<msgList.size();i++){
                        returnMsg = getReturnMsg(msgList.get(i).getCategory(),msgList.get(i).getUrl(),msgList.get(i).getTitle(),msgList.get(i).getMessageId());
                        try {
                            to_session.getBasicRemote().sendText(returnMsg);
                        }catch (Exception e){
                            System.out.println("消息发送失败"+e);
                        }
                     }
                }
            }
        }

    }

    /**
     * 获取要发送的消息内容
     * @return
     */
    private String getReturnMsg(Integer category,String url,String title,Integer mId){
        String returnMsg = "";
        // 获取消息类型
        SysOptionDefinition sd = new SysOptionDefinition();
        sd.setSysOptionDefinitionId(category);
        SysOptionDefinition s = sysOptionDefinitionService.getOptionById(sd);

        url = url + "&messageId=" + mId;

        if (s != null) {
            returnMsg = "title:" + s.getTitle() + "content:" + title + "url:" + url;
        } else {
            returnMsg = "title:消息content:" + title + "url:" + url;
        }
        return  returnMsg;
    }
    @OnError
    public void onError(Throwable throwable,Session session) {
    	sessions.remove(this);
    }

}
