package com.minliu.demo.common;

/**
 * ClassName: ResponseEnum <br>
 * date: 4:35 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public enum ResponseEnum {

    /**
     * 成功
     */
    SUCCESS(0, "success"),

    /**
     * 注册成功
     */
    REGISTER_SUCCESS(99, "register success"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(100, "login success"),
    /**
     * 未登录
     */
    NOT_LOGIN(101, "not logged in"),
    /**
     * 没有权限
     */
    NO_AUTHORITIES(102, "no authorities"),
    /**
     * 登录失败
     */
    LOGIN_FAILED(103, "login failed"),
    /**
     * 退出成功
     */
    LOGOUT_SUCCESS(104, "logout success"),

    /**
     * 注册失败
     */
    REGISTER_FAILED(105, "register failed"),

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(106, "username is already taken"),

    /**
     * 邮箱已被注册
     */
    EMAIL_EXISTS(107, "email is already registered"),

    /**
     * 电话已被注册
     */
    PHONE_EXISTS(108, "phone is already registered"),
    /**
     * 系统异常
     */
    SERVER_ERROR(500, "server error");


    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
