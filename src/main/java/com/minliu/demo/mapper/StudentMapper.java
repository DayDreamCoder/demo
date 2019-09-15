package com.minliu.demo.mapper;

import com.minliu.demo.pojo.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: liumin
 * @date: 2019/8/2 0:31
 * @version: JDK1.8
 */
public interface StudentMapper {
    /**
     * 批量插入
     * @param dataList
     */
    void batchInsert(@Param("dataList") List<Student> dataList);
}
