package com.minliu.demo.controller;

import com.minliu.demo.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "登录控制器")
@RestController
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Assert.notNull(user,"Login User could not be null");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                return "你已经登陆过了";
            }
        }catch (AuthenticationException e){
            logger.error(e.getMessage(),e);
            return "用户名或密码错误";
        }
        return "登录成功";
    }
}
