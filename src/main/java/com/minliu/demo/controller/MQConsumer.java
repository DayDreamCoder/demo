package com.minliu.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息收费者
 * <p>
 * ClassName: MQConsumer <br>
 * date: 11:29 AM 28/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);

    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * 测试消息重发
     *
     * @param message 消息
     * @param session session
     * @throws JMSException he root class of all checked exceptions
     */
    @JmsListener(destination = "boot.queue1", containerFactory = "jmsListenerContainerFactory")
    public void receiveQueue(final TextMessage message, Session session) throws JMSException {
        try {
            boolean transacted = session.getTransacted();
            logger.info("是否是事物消息：{}", transacted);
            logger.info("consumer收到的消息：{}", message.getText());
            if (message.getText().contains("a") && count.get() != 5) {
                count.getAndIncrement();
                throw new JMSException("自定义错误...");
            }
//            message.acknowledge();
        } catch (JMSException e) {
            logger.info("重发次数:{}", count.get());
            logger.info(e.getMessage(), e);
            session.recover();
        }

    }
}
