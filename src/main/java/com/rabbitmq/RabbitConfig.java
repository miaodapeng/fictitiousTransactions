package com.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
 
//    @Value("${mjx.rabbitmq.host}")
//    private String host;
//
////    @Value("${mjx.rabbitmq.port}")
////    private int port;
//
//    @Value("${mjx.rabbitmq.username}")
//    private String username;
//
//    @Value("${mjx.rabbitmq.password}")
//    private String password;
//
//    @Value("${mjx.rabbitmq.virtualHost}")
//    private String virtualHost;


    //exchange
    public static final String MJX_ADDLOGISTICS_EXCHANGE = "MjxLogisticsExchange";
    public static final String STOCK_SERVICE_EXCHANGE = "StockServiceExchange";

    //queue
    public static final String MJX_ADDLOGISTICS_QUEUE = "MjxLogisticsQueue";
    public static final String ERP_TEXT_QUEUE = "erp.text.Queue";
    public static final String STOCK_SERVICE_OCCUPY_QUEUE = "StockServiceOccupyQueue";
    public static final String STOCK_SERVICE_STOCKNUM_QUEUE = "StockServiceStockNumQueue";

    //routingkey
    public static final String MJX_ADDLOGISTICS_ROUTINGKEY = "MjxLogisticsRoutingkey";
    public static final String TEXT_ERP_ROUTINGKEY ="erp.text.routingkey";
    public static final String STOCK_SERVICE_OCCUPY_ROUTINGKEY = "StockServiceOccupyRoutingkey";
    public static final String STOCK_SERVICE_STOCKNUM_ROUTINGKEY = "StockServiceStockNumRoutingkey";


  /*  @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
 
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }*/

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
/*    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(MJX_ADDLOGISTICS_EXCHANGE);
    }*/

    /**
     * 获取队列A
     * @return
     */
/*    @Bean
    public Queue queueA() {
        return new Queue(MJX_ADDLOGISTICS_QUEUE, true); //队列持久
    }*/

    /**
     * 获取队列B
     * @return
     */
 /*   @Bean
    public Queue queueB() {
        return new Queue(MJX_REMOVELOGISTICS_QUEUE, true); //队列持久
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.MJX_ADDLOGISTICS_ROUTINGKEY);
    }

    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(RabbitConfig.MJX_REMOVELOGISTICS_ROUTINGKEY);
    }
*/
}