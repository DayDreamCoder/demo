package com.minliu.demo.config.security;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.common.Response;
import com.minliu.demo.common.WebResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足返回给前端信息
 * ClassName: AjaxAccessDeniedHandler <br>
 * date: 4:52 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.getWriter().write(JSON.toJSONString(new WebResponse(Response.NO_AUTHORITIES)));
    }
}
