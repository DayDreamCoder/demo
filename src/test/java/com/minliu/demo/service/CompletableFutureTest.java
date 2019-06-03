package com.minliu.demo.service;

import com.minliu.demo.pojo.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试CompletableFuture在项目中的使用（需要将LogAopUtil的注解注释掉不然会报错）
 * <p>
 * ClassName: CompletableFutureTest <br>
 * date: 9:54 AM 01/03/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompletableFutureTest {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFuture.class);

    @Resource
    private ProductService productService;

    @Resource
    private RedisService redisService;

    @Resource
    private MessageProduceService messageProduceService;

    //测试异步
    @Test
    public void testAsync() {
        //初始化线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        long last = System.currentTimeMillis();
        //全 异步，获取全部商品与id为1的商品汇总，输出数量
        List<Product> products = CompletableFuture.supplyAsync(() -> productService.getAllProducts(), executor)
                .thenCombineAsync(CompletableFuture.supplyAsync(
                        () -> productService.findById(1)),
                        (list, product) -> {
                            list.add(product);
                            return list;
                        },
                        executor)
                .join();
        System.out.println(products.size());
        logger.info("异步共耗时：{}ms", System.currentTimeMillis() - last);
    }

    //测试同步
    @Test
    public void testSync() {
        long last = System.currentTimeMillis();
        Product product = productService.findById(1);
        List<Product> products = productService.getAllProducts();
        products.add(product);
        System.out.println(products.size());
        logger.info("同步共耗时：{}ms", System.currentTimeMillis() - last);
    }

}
