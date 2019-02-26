package com.minliu.demo.config.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *自定义过滤器拦截未登录请求
 * @author liumin
 */
public class FormPostAuthorizeFilter extends FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(FormPostAuthorizeFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setContentType("text/application-json;charset=utf-8");
            PrintWriter writer = res.getWriter();
            writer.write("You must login first");
            writer.flush();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return false;
    }

    @Bean
    public FilterRegistrationBean registration(FormPostAuthorizeFilter filter) {
        @SuppressWarnings("unchecked")
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
