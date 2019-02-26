package com.minliu.demo.repository;

import com.minliu.demo.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色数据库接口
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {
    //
}
