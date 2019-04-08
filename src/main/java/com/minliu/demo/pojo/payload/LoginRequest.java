package com.minliu.demo.pojo.payload;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录请求实体
 * ClassName: LoginRequest <br>
 * date: 3:20 PM 03/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public class LoginRequest implements Serializable{
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
