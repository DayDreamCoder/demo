package com.minliu.demo.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理
 * @author minliu
 */
@ControllerAdvice(basePackages = "com.minliu.demo.controller",
                    annotations = {Controller.class,RestController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleException(){
        return "系统异常";
    }
}
