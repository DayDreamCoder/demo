package com.minliu.demo.controller;

import com.google.common.collect.Lists;
import com.minliu.demo.common.Constant;
import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.pojo.SysUser;
import com.minliu.demo.service.SysRoleService;
import com.minliu.demo.service.SysUserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: LoginController <br>
 * date: 3:35 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Api(tags = {"登录接口"})
@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private SysUserService userService;

    @Resource
    private SysRoleService roleService;

    @PostMapping
    public WebResponse login(@RequestBody SysUser sysUser){
        String token = userService.login(sysUser.getUsername(), sysUser.getPassword());
        WebResponse response = new WebResponse(ResponseEnum.LOGIN_SUCCESS);
        response.putData(Constant.TOKEN, token);
        return response;
    }



    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<String> printAdmin(){
        return Lists.newArrayList("if you can see this word, then you're admin");
    }


}
