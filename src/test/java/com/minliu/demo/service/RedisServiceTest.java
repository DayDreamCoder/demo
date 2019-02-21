package com.minliu.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceTest.class);

    @Resource
    private RedisService redisService;

    @Test
    public void set() {
        boolean isSuccess = redisService.set("a", "hello", -1);
        logger.info("The result is {}",isSuccess);
    }

    @Test
    public void get() {
        String result = redisService.get("a");
        logger.info("The Result is {}",result);
    }
}