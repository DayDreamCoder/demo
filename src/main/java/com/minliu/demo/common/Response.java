package com.minliu.demo.common;

/**
 * ClassName: Response <br>
 * date: 4:35 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public enum Response {

    /**
     * 成功
     */
    SUCCESS(0, "success"),

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
    LOGOUT_SUCCESS(104, "logout success");


    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    Response(Integer code, String message) {
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
