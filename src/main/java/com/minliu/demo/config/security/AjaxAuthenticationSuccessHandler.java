package com.minliu.demo.config.security;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.common.Response;
import com.minliu.demo.common.WebResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录成功返回给前端信息
 * <p>
 * ClassName: AjaxAuthenticationSuccessHandler <br>
 * date: 4:47 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.getWriter().write(JSON.toJSONString(new WebResponse(Response.LOGIN_SUCCESS)));
    }
}
