package com.minliu.demo.service;

import com.minliu.demo.config.security.JwtService;
import com.minliu.demo.pojo.payload.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 授权认证服务
 * <p>
 * ClassName: AuthService <br>
 * date: 3:18 PM 03/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtService jwtService;

    /**
     * 用户登录认证，返回token
     * @param loginRequest 登录请求
     * @return token
     */
    public String authenticateUser(LoginRequest loginRequest) {
        logger.info("开始认证,用户名:{},密码:{}", loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        //登录认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //返回token
        return jwtService.generateToken(authentication);
    }
}
