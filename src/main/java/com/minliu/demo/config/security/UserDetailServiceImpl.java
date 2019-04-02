package com.minliu.demo.config.security;

import com.minliu.demo.mapper.SysUserMapper;
import com.minliu.demo.pojo.SysRole;
import com.minliu.demo.pojo.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: UserDetailServiceImpl <br>
 * date: 5:11 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //构建用户信息的逻辑
        if (!StringUtils.hasText(username)) {
            logger.info("用户名为空...");
            return null;
        }
        SysUser sysUser = sysUserMapper.selectByName(username);
        if (sysUser == null){
            logger.info("没有用户名为：{}的用户",username);
            throw new UsernameNotFoundException(String.format("没有用户名为'%s'的用户",username));
        }
        List<SysRole> roles = sysUserMapper.findRolesById(sysUser.getId());
        roles.forEach(role -> {
            //todo 权限模块
        });
        return sysUser;
    }
}
