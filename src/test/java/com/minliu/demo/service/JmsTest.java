package com.minliu.demo.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQQueueSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Enumeration;

/**
 * ClassName: JmsTest <br>
 * date: 4:33 PM 28/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest {
    private static final Logger logger = LoggerFactory.getLogger(JmsTest.class);

    @Resource
    private ActiveMQConnectionFactory connectionFactory;

    @Resource
    private JmsTemplate jmsTemplate;

    @Test
    public void sendWithTransaction() throws JMSException, InterruptedException {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        MessageProducer producer = session.createProducer(new ActiveMQQueue("boot.queue1"));
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("Hello World");
        producer.send(message);
        session.commit();
        Thread.sleep(100000);
    }

    @Test
    public void traverseMessage(){
        jmsTemplate.execute(session -> {
            QueueBrowser browser = session.createBrowser(new ActiveMQQueue("boot.queue1"));
            Enumeration enumeration = browser.getEnumeration();
            if (enumeration.hasMoreElements()) {
                Object element = enumeration.nextElement();
                System.out.println(element.toString());
            }
            return null;
        });
    }
}
