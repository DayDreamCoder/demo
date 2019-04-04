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
 * <p>
 * ClassName: CustomUserDetailService <br>
 * date: 2:09 PM 03/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Resource
    private UserMapper userMapper;

    //todo 循环调用，待解决
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userMapper.selectByUsernameOrEmail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));
        return UserPrincipal.create(user);
    }

    @Transactional(readOnly = true)
    public UserDetails findUserById(Integer id) {
        try {
            logger.info("查询用户Id:{}", id);
            User user = Optional.of(userMapper.selectByPrimaryKey(id))
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
            return UserPrincipal.create(user);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
}
