package com.minliu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * redis 做消息队列
 * ClassName: RedisMessagePublisher <br>
 * date: 1:57 PM 01/03/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Api(tags = "Redis做消息队列发布消息")
@RestController
@RequestMapping("/redisMsg")
public class RedisMessagePublisher {
    private static final Logger logger = LoggerFactory.getLogger(RedisMessagePublisher.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "发送redis消息")
    @GetMapping("/{msg}")
    public void send(@PathVariable("msg") String msg){
        logger.info("需要发送的消息：{}",msg);
        stringRedisTemplate.convertAndSend("redis-topic",msg);
    }


}
