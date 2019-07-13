package com.minliu.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 无需注入Bean，获取该Bean的实例
 * <p>
 * ClassName: SpringContextHolder <br>
 * date: 2:56 PM 26/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取静态变量的applicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 根据Bean的名称获取Bean
     *
     * @param name 名称
     * @param <T>  类型
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据Bean的类型获取Bean
     *
     * @param type Class
     * @param <T>  T
     * @return T
     */
    public static <T> T getBean(Class<T> type) {
        assertContextInjected();
        return applicationContext.getBean(type);
    }

    /**
     * 校验applicationContext是否已注入
     *
     * @throw RuntimeException
     */
    private static void assertContextInjected() {
        Assert.notNull(applicationContext, "applicationContext 没有注入...");
    }

    /**
     * 清空applicationContext
     */
    public static void clear() {
        if (logger.isDebugEnabled()) {
            logger.debug("清空SpringContextHolder...");
        }
        SpringContextHolder.applicationContext = null;
    }
}
