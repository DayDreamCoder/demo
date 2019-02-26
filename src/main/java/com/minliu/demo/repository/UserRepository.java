package com.minliu.demo.repository;

import com.minliu.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户数据库接口
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("from User where username = ?1")
    User findUserByUsername(String username);
}
