package com.minliu.demo.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 * ClassName: JwtProperties <br>
 * date: 5:32 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
}
