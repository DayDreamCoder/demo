package com.minliu.demo.pojo;

import java.io.Serializable;

/**
 * 角色
 *
 * @author minliu
 */
public class Role implements Serializable {
    private Integer id;

    private String name;

    public Role(String name) {
        this.name = name;
    }

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}