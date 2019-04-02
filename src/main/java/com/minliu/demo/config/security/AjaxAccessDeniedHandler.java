package com.minliu.demo.config.security;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 权限不足返回给前端信息
 * ClassName: AjaxAccessDeniedHandler <br>
 * date: 4:52 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AjaxAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException){
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSON.toJSONString(new WebResponse(ResponseEnum.NO_AUTHORITIES)));
            writer.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
