package com.minliu.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: TestController <br>
 * date: 4:36 PM 15/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String check(){
        return "OK";
    }
}
