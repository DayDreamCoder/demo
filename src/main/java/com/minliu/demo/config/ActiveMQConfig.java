package com.minliu.demo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * ClassName: ActiveMQConfig <br>
 * date: 10:29 AM 28/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@EnableJms
@Configuration
public class ActiveMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConfig.class);

    @Value("${spring.activemq.broker-url}")
    public String url;

    @Value("${spring.activemq.user}")
    public String username;

    @Value("${spring.activemq.password}")
    public String password;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("boot.queue1");
    }


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(username,password,url);
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setMaximumRedeliveries(5);
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        redeliveryPolicy.setBackOffMultiplier(2);
        redeliveryPolicy.setUseCollisionAvoidance(false);
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        redeliveryPolicy.setPreDispatchCheck(true);

        factory.setRedeliveryPolicy(redeliveryPolicy);
        logger.info("ActiveMQ连接工厂配置完成");
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory, Queue queue) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(activeMQConnectionFactory);
        template.setDeliveryMode(1);
        template.setDefaultDestination(queue);
        template.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        template.setMessageTimestampEnabled(true);
        logger.info("JmsTemplate配置完成");
        return template;
    }

    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory(activeMQConnectionFactory);
        //设置并发连接数
        jmsListenerContainerFactory.setConcurrency("1-10");
        //重连时间间隔
        jmsListenerContainerFactory.setRecoveryInterval(2000L);
        jmsListenerContainerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        logger.info("Jms监听器工厂配置完成");
        return jmsListenerContainerFactory;
    }


}
