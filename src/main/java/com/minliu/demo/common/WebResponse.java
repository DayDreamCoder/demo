package com.minliu.demo.common;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * 返回给前端数据格式
 * ClassName: WebResponse <br>
 * date: 4:32 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public class WebResponse implements Serializable {
    private Integer code;

    private String msg;

    private transient Map<String, Object> data;

    private String jwtToken;

    public WebResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMessage();
    }

    public void putData(String name, Object value) {
        this.data = Maps.newHashMap();
        data.put(name, value);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
