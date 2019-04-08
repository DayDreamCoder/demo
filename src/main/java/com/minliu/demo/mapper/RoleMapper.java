package com.minliu.demo.mapper;

import com.minliu.demo.pojo.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Set;

public interface RoleMapper {

    int deleteByPrimaryKey(Integer id);


    int insert(Role record);


    Role selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(Role record);

    /**
     * 根据用户Id查询权限
     *
     * @param userId 用户Id
     * @return Set<Role>
     */
    Set<Role> getRolesByUserId(@Param("userId") Integer userId);
}