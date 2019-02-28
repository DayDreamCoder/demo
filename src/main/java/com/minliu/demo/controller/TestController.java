package com.minliu.demo.controller;

import com.minliu.demo.service.MessageProduceService;
import com.minliu.demo.service.RedisService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Queue;

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

    @Resource
    private Queue queue;

    @Resource
    private RedisService redisService;

    @Resource
    private MessageProduceService messageProduceService;

    @GetMapping("/test")
    public String check() {
        return "OK";
    }

    @GetMapping("/send")
    public void testSendMessage(@RequestParam(name = "msg") String message) throws JMSException {
        messageProduceService.sendWithTransaction(queue, message);
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
