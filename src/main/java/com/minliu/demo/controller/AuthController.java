package com.minliu.demo.controller;

import com.minliu.demo.common.Constant;
import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.pojo.payload.LoginRequest;
import com.minliu.demo.pojo.payload.SignUpRequest;
import com.minliu.demo.pojo.payload.UniqueValidateRequest;
import com.minliu.demo.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 用户注册登录控制层
 * <p>
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
    public WebResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.authenticateUser(loginRequest);
            WebResponse webResponse = new WebResponse(ResponseEnum.LOGIN_SUCCESS);
            webResponse.putData(Constant.TOKEN, token);
            return webResponse;
        } catch (BadCredentialsException ex) {
            logger.warn("用户名或密码错误...");
            logger.info("本次登录用户名:{}", loginRequest.getUsernameOrEmail());
            return new WebResponse(ResponseEnum.LOGIN_FAILED);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new WebResponse(ResponseEnum.SERVER_ERROR);
        }

    }

    @ApiOperation("验证字段是否重复(username,email,phone)")
    @PostMapping("/validate")
    public WebResponse validateField(@Valid @RequestBody UniqueValidateRequest validateRequest) {
        logger.info("验证字段:{}",validateRequest.getField());
        logger.info("验证值:{}",validateRequest.getValue());
        return authService.validateExists(validateRequest.getField(),validateRequest.getValue());
    }

    @ApiOperation("用户注册")
    @PostMapping("/signUp")
    public WebResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.info("注册用户:{}",signUpRequest.getUsername());
        return authService.saveUser(signUpRequest);
    }


}
