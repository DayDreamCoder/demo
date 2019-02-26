package com.minliu.demo.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Configuration
public class ShiroConfig {
/*

    @Bean(name = "myUserRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    *//**
     * 安全管理器
     *
     * @return SecurityManager
     *//*
//    @Bean(name = "mySecurityManager")
    public SecurityManager mySecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    *//**
     * 凭证匹配器
     *
     * @return HashedCredentialsMatcher
     *//*
    @Bean(name = "credentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    *//**
     * shiro 过滤器
     *
     * @return ShiroFilterFactoryBean
     *//*
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //安全管理器
        factoryBean.setSecurityManager(mySecurityManager());
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new FormPostAuthorizeFilter());
        factoryBean.setFilters(filterMap);
        //设置过滤链
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/root/**", "authc");
        filterChainMap.put("/guest/**", "authc");
        filterChainMap.put("/login/**", "anno");
        filterChainMap.put("/logout", "logout");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
        return factoryBean;
    }

    *//**
     * shiro 生命周期处理器
     *
     * @return LifecycleBeanPostProcessor
     *//*
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    *//**
     * 启用shiro 注解(@RequestRole,@RequestPermission等)
     *
     * @return DefaultAdvisorAutoProxyCreator
     *//*
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(mySecurityManager());
        return authorizationAttributeSourceAdvisor;
    }

    *//**
     * ehcache缓存管理器
     *
     * @return EhCacheManager
     *//*
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:shiro-cache.xml");
        return ehCacheManager;
    }*/

}
