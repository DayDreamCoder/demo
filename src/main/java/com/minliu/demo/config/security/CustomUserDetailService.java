package com.minliu.demo.config.security;

import com.minliu.demo.exception.ResourceNotFoundException;
import com.minliu.demo.mapper.UserMapper;
import com.minliu.demo.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * UserDetailService 实现接口
 *
 * ClassName: CustomUserDetailService <br>
 * date: 2:09 PM 03/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
@SuppressWarnings("all")
public class CustomUserDetailService implements UserDetailsService{

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = Optional.of(userMapper.selectByUsernameOrEmail(usernameOrEmail))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails findUserById(Integer id){
        User user = Optional.of(userMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserPrincipal.create(user);
    }
}
