package com.minliu.demo.service;

import sun.misc.Unsafe;

import java.util.function.Consumer;

/**
 * @author: liumin
 * @date: 2019/6/30 20:10
 * @version: JDK1.8
 */
public class Demo {
    public static void main(String[] args) {
        Consumer consumer = System.out::println;
        consumer.accept("Hello World");
    }
}
