package com.minliu.demo.pojo;

import java.io.Serializable;

/**
 * @author minliu
 */
public class Product implements Serializable {
    private Integer id;

    private String name;

    private Double price;

    private String location;

    private String code;

    public Product(Integer id, String name, Double price, String location, String code) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.location = location;
        this.code = code;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}