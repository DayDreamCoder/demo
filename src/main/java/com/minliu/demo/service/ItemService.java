package com.minliu.demo.service;

import com.minliu.demo.mapper.ItemMapper;
import com.minliu.demo.pojo.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品服务层
 * @author: liumin
 * @date: 2019/4/8 20:31
 * @version: JDK1.8
 */
@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Resource
    private ItemMapper itemMapper;

    /**
     * 根据商品Id 查询商品
     * @param id id
     * @return Item
     */
    public Item getItemById(Integer id) {
        logger.info("查询商品ID:{}",id);
       return itemMapper.selectByPrimaryKey(id);
    }

}
