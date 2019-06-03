package com.minliu.demo.mapper;

import com.minliu.demo.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    void batchInsert(@Param("dataList") List<Item> dataList);
}