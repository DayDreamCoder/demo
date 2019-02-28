package com.minliu.demo.controller;

import com.minliu.demo.service.MessageProduceService;
import com.minliu.demo.service.RedisService;
import io.netty.channel.ChannelHandler;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * ClassName: TestController <br>
 * date: 4:36 PM 15/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@RestController
public class TestController {
    private static final Queue queue = new ActiveMQQueue("queue_test");

    private static final Topic topic = new ActiveMQTopic("topic_test");

    @Resource
    private MessageProduceService messageProduceService;

    @Resource
    private RedisService redisService;

    @GetMapping("/test")
    public String check() {
        return "OK";
    }

    @GetMapping("/send")
    public void testSendMessage(@RequestParam(name = "msg") String message) {
        Destination destination = new ActiveMQQueue("a.queue");
        messageProduceService.convertAndSend(destination, message);
    }

    @GetMapping("/queue/{msg}")
    public void testSendQueueMessage(@PathVariable("msg") String msg) {
        messageProduceService.sendQueueMessage(queue, msg);
    }

    @GetMapping("/topic/{msg}")
    public void testSendTopicMessage(@PathVariable("msg") String msg) {
        messageProduceService.sendTopicMessage(topic, msg);
    }

    @GetMapping("/redis")
    public String testRedis() {
        return redisService.get("a");
    }

    @GetMapping("/redisSet/{value}")
    public Boolean set(@PathVariable(name = "value") String value) {
        return redisService.set("a", value);
    }
}
