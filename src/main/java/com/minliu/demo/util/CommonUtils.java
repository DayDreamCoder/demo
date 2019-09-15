package com.minliu.demo.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 通用工具类
 *
 * @author: liumin
 * @date: 2019/8/1 23:55
 * @version: JDK1.8
 */
public class CommonUtils {

    public static <E> List<List<E>> split(List<E> list, int length) {
        List<List<E>> lists = Lists.newArrayList();
        if (list == null || list.size() < length) {
            lists.add(list);
            return lists;
        }
        int size = list.size();
        int remainCount = size % length;
        for (int i = 0; i < size; i += length) {
            List<E> eleList = list.subList(i, (i + remainCount) < size ? i + length : i + remainCount);
            lists.add(eleList);
        }

        return lists;
    }

}
