package com.minliu.demo.service;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceTest.class);

    @Resource
    private RedisService redisService;

    @Test
    public void set() {
        boolean isSuccess = redisService.set("qq", "hello java", -1);
        logger.info("The result is {}", isSuccess);
    }

    @Test
    public void get() {
        String result = redisService.get("qq");
        logger.info("The Result is {}", result);
    }

    @Test
    public void setList() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Apple001", 2000.00, "USA", "A001"));
        products.add(new Product(2, "HuaWei", 21000.00, "CHINA", "C001"));
        products.add(new Product(3, "SamSung", 2500.00, "KOREAN", "K001"));
        products.add(new Product(4, "XiaoMi", 1900.00, "CHINA", "C001"));
        boolean isSuccess = redisService.setList("products", products);
        logger.info("set list success :{}", isSuccess);
    }


    @Test
    public void getList() {
        List<Product> products = redisService.getList("products", Product.class);
        if (!CollectionUtils.isEmpty(products))
            products.forEach(product -> logger.info(product.toString()));
    }
}