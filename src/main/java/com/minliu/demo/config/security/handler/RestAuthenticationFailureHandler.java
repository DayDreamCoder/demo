package com.minliu.demo.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.common.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录失败返回给前端失败信息
 * <p>
 * ClassName: AjaxAuthenticationFailureHandler <br>
 * date: 4:43 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        logger.info("登录失败...");
        response.getWriter().write(JSON.toJSONString(ResponseEnum.LOGIN_FAILED));
    }
}
