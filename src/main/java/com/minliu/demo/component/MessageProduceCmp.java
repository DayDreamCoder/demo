package com.minliu.demo.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * ClassName: MessageProduceCmp <br>
 * date: 4:16 PM 19/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class MessageProduceCmp {
    private static final Logger logger = LoggerFactory.getLogger(MessageProduceCmp.class);

    @JmsListener(destination = "out.queue")
    public void consumerMessage(String text){
        logger.info("从out.queue返回的消息：{}",text);
    }
}
