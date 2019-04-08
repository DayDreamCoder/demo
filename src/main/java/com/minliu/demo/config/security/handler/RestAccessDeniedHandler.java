package com.minliu.demo.config.security.handler;

import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.util.HttpResponseWriteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足返回给前端
 *
 * @author: liumin
 * @date: 2019/4/8 19:23
 * @version: JDK1.8
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.warn("未登录用户调用...");
        logger.info("请求路径:{}", request.getRequestURL());
        WebResponse webResponse = new WebResponse(ResponseEnum.NO_AUTHORITIES);
        HttpResponseWriteUtils.writeData(webResponse, response);
    }
}
