package com.minliu.demo.service;

import com.minliu.demo.mapper.ProductMapper;
import com.minliu.demo.pojo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author minliu
 */
@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Resource
    private ProductMapper productMapper;

    @Cacheable(value = "product:id:", cacheManager = "cacheManager", key = "#id")
    public Product findById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public boolean updateProduct(Product product) {
        if (product != null) {
            logger.info(product.toString());
        }
        return productMapper.updateByPrimaryKey(product) == 1;
    }

    public void deleteProduct(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }
}
