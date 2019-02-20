package com.minliu.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * ClassName: MessageProduceService <br>
 * date: 4:05 PM 19/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service("messageProduceService")
public class MessageProduceService {
    private static final Logger logger = LoggerFactory.getLogger(MessageProduceService.class);

    @Resource
    private JmsTemplate jmsTemplate;

    public void convertAndSend(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
        logger.info("消息发送成功...");
    }

    /**
     * 发送队列消息
     *
     * @param destination 目的地
     * @param message     消息
     */
    public void sendQueueMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送主题消息
     *
     * @param destination 目的地
     * @param message     消息
     */
    public void sendTopicMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination,message);
    }

    @JmsListener(destination = "out.queue")
    public void getReturnMessage(String message){
        logger.info("生成者收到消息：{}",message);
    }


}
