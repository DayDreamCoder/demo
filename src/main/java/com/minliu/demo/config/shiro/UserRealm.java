package com.minliu.demo.config.shiro;

import com.minliu.demo.pojo.Permission;
import com.minliu.demo.pojo.Role;
import com.minliu.demo.pojo.User;
import com.minliu.demo.repository.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.boot.autoconfigure.ShiroBeanAutoConfiguration;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * 认证授权逻辑实现类
 *
 * @author liumin
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Resource
    private UserRepository userRepository;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.fromRealm(getName()).iterator().next();
        if (user != null) {
            logger.info("开始授权...,登录名:{}", user.getUsername());
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //获取所有角色，添加到授权信息
            List<Role> roles = user.getRoles();
            List<String> roleNames = roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());
            info.addRoles(roleNames);
            //获取每个角色的权限，添加到授权信息
            roles.forEach(role -> {
                List<Permission> permissions = role.getPermissions();
                List<String> permissionNames = permissions.stream()
                        .map(Permission::getName)
                        .collect(Collectors.toList());
                info.addStringPermissions(permissionNames);
            });

            return info;
        }
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        logger.info("开始验证...,用户名:{}", username);
        User user = userRepository.findUserByUsername(username);
        if (user != null){
            //盐值
            ByteSource salt = ByteSource.Util.bytes(username);
            return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        }
        return null;
    }

}
