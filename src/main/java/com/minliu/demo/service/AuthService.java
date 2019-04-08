package com.minliu.demo.service;

import com.google.common.collect.Sets;
import com.minliu.demo.common.Constant;
import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.config.security.JwtService;
import com.minliu.demo.exception.AppException;
import com.minliu.demo.exception.BadRequestException;
import com.minliu.demo.mapper.UserMapper;
import com.minliu.demo.pojo.User;
import com.minliu.demo.pojo.payload.LoginRequest;
import com.minliu.demo.pojo.payload.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

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

    private static final Set<String> UNIQUE_FIELDS = Sets.newHashSet();

    static {
        UNIQUE_FIELDS.add("username");
        UNIQUE_FIELDS.add("email");
        UNIQUE_FIELDS.add("phone");
    }


    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtService jwtService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 用户登录认证，返回token
     *
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

    /**
     * 验证字段唯一性
     *
     * @param field 字段名
     * @param value 值
     * @return Boolean
     */
    public WebResponse validateExists(String field, String value) {
        if (!UNIQUE_FIELDS.contains(field)) {
            throw new BadRequestException("不存在该field:" + field);
        }
        try {
            Integer data = userMapper.findData(field, value);
            WebResponse webResponse = new WebResponse(ResponseEnum.SUCCESS);
            webResponse.putData("exists", data != null);
            return webResponse;
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }
        return new WebResponse(ResponseEnum.SERVER_ERROR);
    }

    /**
     * 注册保存用户
     *
     * @param signUpRequest 注册请求
     * @return WebResponse
     */
    @Transactional(rollbackFor = Exception.class)
    public WebResponse saveUser(SignUpRequest signUpRequest) {
        try {
            User user = new User(signUpRequest.getUsername(),
                    //密码加密
                    passwordEncoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getEmail(),
                    signUpRequest.getPhone());
            //插入用户表返回主键
            userMapper.insert(user);
            //赋予普通用户角色
            userMapper.assignRole(user.getId(), Constant.ROLE_USER);
            return new WebResponse(ResponseEnum.REGISTER_SUCCESS);
        } catch (Exception ex) {
            logger.error("保存用户发生错误...");
            logger.error(ex.getMessage(), ex);
            throw new AppException("保存用户发生错误...");
        }
    }
}
