package com.minliu.demo.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池
 *
 * @author minliu
 */
@Configuration
@Component
public class ThreadPoolConfig {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Resource
    private ThreadPoolProperties threadPoolProperties;

    /**
     * 线程池命名格式
     */
    private static ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("cums-thread-%d").build();

    @Bean("executor")
    public Executor getExecutor() {
        logger.info("配置线程池...");
        return
                new ThreadPoolExecutor(threadPoolProperties.getCoreSize(),
                        threadPoolProperties.getMaxSize(),
                        threadPoolProperties.getMaxTime(),
                        threadPoolProperties.getTimeUnit(),
                        new LinkedBlockingQueue<>(1024),
                        factory,
                        new ThreadPoolExecutor.AbortPolicy()
                );
    }
}
