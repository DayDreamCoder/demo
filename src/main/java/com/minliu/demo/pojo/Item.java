package com.minliu.demo.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品
 *
 * @author minliu
 */
public class Item implements Serializable {
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 链接
     */
    private String link;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商店
     */
    private String store;

    /**
     * 好评数量
     */
    private Integer comments;

    /**
     * 好评率
     */
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