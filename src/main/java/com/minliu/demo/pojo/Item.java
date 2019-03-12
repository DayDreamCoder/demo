package com.minliu.demo.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author minliu
 */
public class Item implements Serializable {
    private Integer id;

    private String name;

    private String link;

    private BigDecimal price;

    private String store;

    private Integer comments;

    private BigDecimal good;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store == null ? null : store.trim();
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public BigDecimal getGood() {
        return good;
    }

    public void setGood(BigDecimal good) {
        this.good = good;
    }
}