package com.minliu.demo.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色
 *
 * @author liumin
 */
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    private Boolean available = Boolean.TRUE;

    private String description;

    /**
     * 与用户表映射关系，一个角色对应多个用户
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id"),},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users;

    /**
     * 与角色表映射关系，一个角色对应多个权限
     */
    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", columnDefinition = "id")})
    private List<Permission> permissions;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
