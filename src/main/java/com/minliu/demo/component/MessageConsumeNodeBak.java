package com.minliu.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * ClassName: MessageConsumeNodeBak <br>
 * date: 4:13 PM 19/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class MessageConsumeNodeBak {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumeNodeBak.class);

    @JmsListener(destination = "a.queue")
    @SendTo("out.queue")
    public String receiveQueue(String text){
        logger.info("bak收到的消息：{}",text);
        logger.info("bak收到消息返回给broker");
        return text + "_bak";
    }
}
