package com.minliu.demo.mapper;

import com.minliu.demo.pojo.Product;

import java.util.List;

/**
 * @author minliu
 */
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getAllProducts();
}