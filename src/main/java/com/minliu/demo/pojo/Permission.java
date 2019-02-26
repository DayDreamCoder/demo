package com.minliu.demo.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * 权限
 *
 * @author liumin
 */
@Entity
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 父权限Id
     */
    private Integer parentId;

    /**
     * 父权限Id 列表
     */
    private String parentIds;

    /**
     * 描述
     */
    private String description;

    /**
     * 与角色映射关系 多对多
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "permission_id",referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private List<Role> roles;

    public Permission() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
