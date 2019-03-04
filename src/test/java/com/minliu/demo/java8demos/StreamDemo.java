package com.minliu.demo.java8demos;

import com.minliu.demo.pojo.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测试 Stream
 */
public class StreamDemo {

    public static void main(String[] args) {
        List<Product> products = getProducts();
        //根据国家分组，查询地区为China且价格低于5000的产品
        products.stream()
                //  按地区分组
                .collect(Collectors.groupingBy(Product::getLocation))
                .entrySet().stream()
                .filter(entry -> entry.getKey().equals("China"))
                .map(Map.Entry::getValue)
                .findFirst().get()
                .stream().filter(product -> product.getPrice() < 5000)
                .collect(Collectors.toList())
                .forEach(System.out::println);

    }


    //获取初始数据
    private static List<Product> getProducts() {
        return Arrays.asList(
                new Product(1, "Apple", 6800.00, "America", "A001"),
                new Product(2, "MacBook", 12000.00, "America", "A002"),
                new Product(3, "Apple Watch", 3600.00, "America", "A003"),
                new Product(4, "Hua Wei", 5900.00, "China", "C001"),
                new Product(5, "Vivo", 3998.00, "China", "C002"),
                new Product(6, "SamSung", 7998.00, "Korea", "K001"),
                new Product(6, "Oppo", 3798.00, "China", "C003"));
    }
}
