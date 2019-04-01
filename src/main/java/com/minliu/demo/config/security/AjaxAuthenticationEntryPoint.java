package com.minliu.demo.config.security;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.common.Response;
import com.minliu.demo.common.WebResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
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
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        WebResponse webResponse = new WebResponse(Response.NOT_LOGIN);
        response.getWriter().write(JSON.toJSONString(webResponse));
    }
}
