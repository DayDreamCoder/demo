package com.minliu.demo.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录返回给前端
 * ClassName: AjaxAuthenticationEntryPoint <br>
 * date: 4:38 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.error("未登录用户调用...");
        logger.info("URL:{}",request.getRequestURL().toString());
        WebResponse webResponse = new WebResponse(ResponseEnum.NOT_LOGIN);
        response.getWriter().write(JSON.toJSONString(webResponse));
    }
}
