package com.minliu.demo.java8demos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ClassName: Demo <br>
 * date: 3:08 PM 02/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class Demo {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Hello World");
    }
}
