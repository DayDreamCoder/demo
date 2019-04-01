package com.minliu.demo.service;

import com.minliu.demo.mapper.SysUserMapper;
import com.minliu.demo.pojo.SysRole;
import com.minliu.demo.pojo.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理接口
 * ClassName: SysUserService <br>
 * date: 2:57 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
public class SysUserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserService.class);

    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser findById(Integer id){
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public SysUser findByName(String username){
        logger.debug("查询用户名:{}",username);
        return sysUserMapper.selectByName(username);
    }

    public List<SysRole> listRolesByUser(Integer id){
        logger.debug("根据用户ID查询用户权限，ID:{}",id);
        return sysUserMapper.findRolesById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
