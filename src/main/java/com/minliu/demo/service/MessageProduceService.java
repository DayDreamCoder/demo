package com.minliu.demo.service;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * 发送消息服务
 * ClassName: MessageProduceService <br>
 * date: 1:30 PM 28/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
public class MessageProduceService {
    private static final Logger logger = LoggerFactory.getLogger(MessageProduceService.class);

    @Resource
    private JmsTemplate jmsTemplate;

    public void send(Destination destination, String message) {
        logger.info("要发送的消息：{}", message);
        jmsTemplate.convertAndSend(destination, message);
    }



    public void sendWithTransaction(Destination destination,String message){
        logger.info("要发送的消息：{}", message);
        jmsTemplate.execute((session, producer) -> {
            ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText(message);
            producer.send(destination,textMessage,Session.SESSION_TRANSACTED,4,
                    100000L);
            session.commit();
            return true;
        });

    }

}
