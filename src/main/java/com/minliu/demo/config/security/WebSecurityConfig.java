package com.minliu.demo.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * 系统安全配置类
 * ClassName: WebSecurityConfig <br>
 * date: 4:06 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 未登陆时返回 JSON 格式的数据给前端
     */
    @Resource
    private AjaxAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 登录成功返回JSON给前端
     */
    @Resource
    private AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 登录失败返回JSON给前端
     */
    @Resource
    private AjaxAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 无权访问返回的 JSON 格式数据给前端
     */
    @Resource
    private AjaxAccessDeniedHandler accessDeniedHandler;

    /**
     * 退出成功返回 JSON 格式数据给前端
     */
    @Resource
    private AjaxLogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
