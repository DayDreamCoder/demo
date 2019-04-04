package com.minliu.demo.controller;

import com.minliu.demo.common.Constant;
import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.pojo.payload.LoginRequest;
import com.minliu.demo.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 用户注册登录控制层
 *
 * ClassName: AuthController <br>
 * date: 3:16 PM 03/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Api(tags = "用户认证授权")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private AuthService authService;

    @ApiOperation(value = "用户登录认证")
    @PostMapping("/signIn")
    public WebResponse login(@Valid @RequestBody LoginRequest loginRequest){
        String token = authService.authenticateUser(loginRequest);
        if (! StringUtils.hasText(token)) {
            logger.info("LOGIN FAILED.");
            return new WebResponse(ResponseEnum.LOGIN_FAILED);
        }
        WebResponse webResponse = new WebResponse(ResponseEnum.LOGIN_SUCCESS);
        webResponse.putData(Constant.TOKEN,token);
        return webResponse;
    }
}
