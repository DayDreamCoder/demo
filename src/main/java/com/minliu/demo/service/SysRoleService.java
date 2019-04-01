package com.minliu.demo.service;

import com.minliu.demo.mapper.SysRoleMapper;
import com.minliu.demo.pojo.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: SysRoleService <br>
 * date: 2:52 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
public class SysRoleService {
    private static final Logger logger = LoggerFactory.getLogger(SysRoleService.class);

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据主键查询角色
     *
     * @param id 主键
     * @return SysRole
     */
    public SysRole findRoleById(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

}
