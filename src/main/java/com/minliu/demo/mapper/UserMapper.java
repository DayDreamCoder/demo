package com.minliu.demo.mapper;

import com.minliu.demo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);



    User selectByPrimaryKey(Integer id);


    User selectByUsernameOrEmail(String usernameOrEmail);


    int updateByPrimaryKey(User record);

    Integer findData(@Param("field") String field, @Param("value") String value);

    void assignRole(@Param("userId") Integer userId,@Param("roleName") String roleName);
}