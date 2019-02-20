package com.minliu.demo.component;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * ClassName: MessageConsumeNode <br>
 * date: 4:10 PM 19/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class MessageConsumeNode {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumeNode.class);


    @JmsListener(destination = "queue_test")
    public void receiveMessage(String text){
        logger.info("消费者1收到的队列消息为：{}",text);
    }

    @JmsListener(destination = "topic_test")
    public void recMessage(String message){
        logger.info("消费者1收到的主题消息为：{}",message);
    }
}
