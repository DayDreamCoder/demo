package com.minliu.demo.controller;

import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.config.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块控制层
 *
 * @author: liumin
 * @date: 2019/4/7 20:41
 * @version: JDK1.8
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation("获取当前登录用户")
    @GetMapping
    public WebResponse getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal =(UserPrincipal) authentication.getPrincipal();
        if (principal == null ){
            logger.warn("未登录用户请求...");
            return new WebResponse(ResponseEnum.NOT_LOGIN);
        }
        WebResponse response = new WebResponse(ResponseEnum.SUCCESS);
        response.putData("user",principal);
        return response;
    }

}
