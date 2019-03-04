package com.minliu.demo.controller;

import com.minliu.demo.config.websocket.MyWebSocketHandler;
import com.minliu.demo.pojo.Product;
import com.minliu.demo.service.MessageProduceService;
import com.minliu.demo.service.ProductService;
import com.minliu.demo.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * 测试接口
 * ClassName: TestController <br>
 * date: 4:36 PM 15/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Api
@RestController
public class TestController {

    @Resource
    private Queue queue;

    @Resource
    private RedisService redisService;

    @Resource
    private MessageProduceService messageProduceService;

    @Resource
    private ProductService productService;

    @Resource
    private MyWebSocketHandler myWebSocketHandler;


    @GetMapping("/test")
    public String check() {
        return "OK";
    }

    @GetMapping("/send")
    public void testSendMessage(@RequestParam(name = "msg") String message) throws JMSException {
        messageProduceService.sendWithTransaction(queue, message);
    }


    @ApiOperation(value = "获取redis中key为a 的值")
    @GetMapping("/redis")
    public String testRedis() {
        return redisService.get("a");
    }

    @ApiOperation(value = "设置redis中key为a的值")
    @GetMapping("/redisSet/{value}")
    public Boolean set(@PathVariable(name = "value") String value) {
        return redisService.set("a", value);
    }

    @ApiOperation(value = "根据主键查询商品")
    @GetMapping("/product/{id}")
    public Product findProduct(@PathVariable("id") Integer id) {
        return productService.findById(id);
    }


}
