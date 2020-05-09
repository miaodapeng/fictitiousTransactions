package com.vedeng.orderrabbitmq.service;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.vedeng.authorization.dao.PositionMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.vo.BusinessChanceMqDto;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.system.dao.SysOptionDefinitionMapper;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.model.RTraderJUser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 前台推送商机消息
 * @author daniel
 * @date 2020/03/17
 **/
@Component
public class BusinessChanceConsumer implements ChannelAwareMessageListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(BusinessChanceConsumer.class);

    @Autowired
    private SysOptionDefinitionMapper sysOptionDefinitionMapper;

    @Autowired
    private BussinessChanceService bussinessChanceService;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private RTraderJUserMapper rTraderJUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Value("${business_chance_source}")
    private String businessChanceSourceId;

    @Value("${business_chance_entrance}")
    private String businessChanceEntranceId;

    @Value("${business_chance_function}")
    private String businessChanceFunctionId;

    @Override
    public void onMessage(Message message, Channel channel){

        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        LOGGER.info("前台推送商机信息：{}",messageBody);

        try {
            BusinessChanceMqDto mqDto = JSONObject.parseObject(messageBody,BusinessChanceMqDto.class);

            //数据校验
            if (mqDto.getSource() == null || mqDto.getEntrances() == null || mqDto.getFunctions() == null ||
                    StringUtils.isBlank(mqDto.getCheckTraderContactMobile())){
                throw new Exception("商机信息参数异常");
            }

            BussinessChance bussinessChance = new BussinessChance();
            bussinessChance.setSource(getSysOptionDefinitionByMapping(businessChanceSourceId,mqDto.getSource()));
            bussinessChance.setEntrances(getSysOptionDefinitionByMapping(businessChanceEntranceId,mqDto.getEntrances()));
            bussinessChance.setFunctions(getSysOptionDefinitionByMapping(businessChanceFunctionId,mqDto.getFunctions()));
            //默认为自主询价
            bussinessChance.setType(394);
            bussinessChance.setGoodsName(xssPreventionHandle(mqDto.getGoodsName()));
            bussinessChance.setContent(xssPreventionHandle(mqDto.getContent()));
            bussinessChance.setTraderContactName(xssPreventionHandle(mqDto.getCheckTraderContactName()));
            bussinessChance.setMobile(xssPreventionHandle(mqDto.getCheckTraderContactMobile()));
            bussinessChance.setTraderName(xssPreventionHandle(mqDto.getTraderName()));
            bussinessChance.setAddTime(mqDto.getAddTime());

            bussinessChance.setReceiveTime(DateUtil.sysTimeMillis());
            bussinessChance.setCompanyId(1);

            //商机分配给销售
            //根据商机联系方式查找销售
            bussinessChance.setUserId(getSaleUserIdByBusinessChanceMobile(mqDto.getCheckTraderContactMobile()));
            bussinessChance.setOrgId(getOrgByUserId(bussinessChance.getUserId()));

            //附件
            Attachment attachment = null;
            if (StringUtils.isNotBlank(mqDto.getAttachment())){
                attachment = new Attachment();
                //根据attachmentId来区分附件存储的位置，-1为存储在oss
                attachment.setAttachmentId(-1);
                attachment.setUri("/file/display?resourceId=" + mqDto.getAttachment());
            }

            //商机操作人默认为njadmin
            User njadmin = userMapper.getByUsername("njadmin",1);

            if (bussinessChanceService.saveBussinessChance(bussinessChance,njadmin,attachment) == null){
                throw new Exception("商机同步到dbcenter后台失败，商机消息");
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e){
            LOGGER.error("消费前台推送的商机信息发送错误，商机内容：{}，错误信息：",messageBody,e);
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            } catch (IOException ex) {
                LOGGER.error("商机消息消费失败，将消息返回给rabbitmq错误：",ex);
            }
        }
    }


    private String xssPreventionHandle(String source){
        return StringEscapeUtils.escapeHtml4(source);
    }


    private Integer getSysOptionDefinitionByMapping(String idString, Integer index){
        String[] idArray = idString.split(",");
        if (idArray.length < index){
            throw new IndexOutOfBoundsException("商机信息参数越界");
        }
        return Integer.valueOf(idArray[index - 1]);
    }



    /**
     * 根据商机联系方式查找对应的销售
     * VDERP-2266： 当该手机号匹配到多个客户时，查看这个客户是否归属同一个销售，如果是，则分配到该销售；如果不是，那么则不分配，将商机推送给总机
     * @param mobile 手机号
     * @return 销售
     */
    private Integer getSaleUserIdByBusinessChanceMobile(String mobile){
        List<RTraderJUser> saleUserIdByContactMobile = rTraderJUserMapper.getSaleUserIdByContactMobile(mobile);
        if (saleUserIdByContactMobile.size() == 0){
            return 0;
        } else if (saleUserIdByContactMobile.size() == 1){
            return saleUserIdByContactMobile.get(0).getUserId();
        } else {
            //当该手机号匹配到多个客户时，查看这个客户是否归属同一个销售，如果是，则分配到该销售；如果不是，那么则不分配，将商机推送给总机
            int countOfSales = saleUserIdByContactMobile.stream().map(RTraderJUser::getUserId).collect(Collectors.toSet()).size();
            if (countOfSales == 1){
                return saleUserIdByContactMobile.get(0).getUserId();
            } else {
                return 0;
            }
        }
    }


    /**
     * 根据销售查找其归属部门
     * @param userId 用户id
     * @return 部门id
     */
    private Integer getOrgByUserId(Integer userId){
        if (userId.equals(0)){
            return 0;
        }
        List<Position> positions = positionMapper.getPositionByUserId(userId);
        if (CollectionUtils.isEmpty(positions)){
            return 0;
        } else {
            return positions.get(0).getOrgId();
        }
    }
}
