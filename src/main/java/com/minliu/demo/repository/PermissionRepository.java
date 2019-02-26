package com.minliu.demo.repository;

import com.minliu.demo.pojo.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 权限数据库接口
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    //
}
