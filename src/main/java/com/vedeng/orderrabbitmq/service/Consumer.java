package com.vedeng.orderrabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.vedeng.logistics.model.LogisticsOrderData;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: erp
 * @description: 消费者消费消息
 * @author: addis
 * @create: 2019-11-07 17:12
 **/



@Component("consumer")
public class Consumer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        try {

            LogisticsOrderData logisticsOrderData= JSONObject.parseObject(new String(message.getBody()) , LogisticsOrderData.class);
            System.out.println("消费者消费消息=========="+JSON.toJSONString(logisticsOrderData));
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}




