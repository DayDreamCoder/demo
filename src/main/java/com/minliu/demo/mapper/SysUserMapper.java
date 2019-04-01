package com.minliu.demo.mapper;

import com.minliu.demo.pojo.SysRole;
import com.minliu.demo.pojo.SysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface SysUserMapper {
    @Delete({
            "delete from sys_user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into sys_user (id, username, ",
            "password, account_non_expired, ",
            "account_non_locked, credentials_non_expired, ",
            "enabled, phone, email, ",
            "address, remark)",
            "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
            "#{password,jdbcType=VARCHAR}, #{accountNonExpired,jdbcType=BIT}, ",
            "#{accountNonLocked,jdbcType=BIT}, #{credentialsNonExpired,jdbcType=BIT}, ",
            "#{enabled,jdbcType=BIT}, #{phone,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
            "#{address,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(SysUser record);


    @Select({
            "select",
            "id, username, password, account_non_expired, account_non_locked, credentials_non_expired, ",
            "enabled, phone, email, address, remark",
            "from sys_user",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.BIT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.BIT),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    SysUser selectByPrimaryKey(Integer id);

    @Select({
            "select",
            "id, username, password, account_non_expired, account_non_locked, credentials_non_expired, ",
            "enabled, phone, email, address, remark",
            "from sys_user",
            "where username = #{name,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "account_non_expired", property = "accountNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "account_non_locked", property = "accountNonLocked", jdbcType = JdbcType.BIT),
            @Result(column = "credentials_non_expired", property = "credentialsNonExpired", jdbcType = JdbcType.BIT),
            @Result(column = "enabled", property = "enabled", jdbcType = JdbcType.BIT),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    SysUser selectByName(@Param("name") String name);

    @Update({
            "update sys_user",
            "set username = #{username,jdbcType=VARCHAR},",
            "password = #{password,jdbcType=VARCHAR},",
            "account_non_expired = #{accountNonExpired,jdbcType=BIT},",
            "account_non_locked = #{accountNonLocked,jdbcType=BIT},",
            "credentials_non_expired = #{credentialsNonExpired,jdbcType=BIT},",
            "enabled = #{enabled,jdbcType=BIT},",
            "phone = #{phone,jdbcType=VARCHAR},",
            "email = #{email,jdbcType=VARCHAR},",
            "address = #{address,jdbcType=VARCHAR},",
            "remark = #{remark,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SysUser record);

    @Select("SELECT role.name,role.remark " +
            "FROM sys_role role " +
            "  INNER JOIN sys_user_role ur ON role.id = ur.role_id " +
            "  INNER JOIN sys_user u ON ur.user_id = u.id " +
            "WHERE u.id=#{id,jdbcType=INTEGER}")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.VARCHAR)
    })
    List<SysRole> findRolesById(@Param("id") Integer id);
}