package com.minliu.demo.config.security;

import com.google.common.collect.Maps;
import com.minliu.demo.common.Constant;
import com.minliu.demo.pojo.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * ClassName: JwtService <br>
 * date: 10:00 AM 02/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 用数据声明生成token
     *
     * @param claims 数据声明
     * @return token
     */
    private String generateToken(Map<String, Object> claims) {
        Date expireDate = new Date(System.currentTimeMillis() + jwtProperties.getExpire());
        String compact = Jwts.builder().setClaims(claims)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES512, jwtProperties.getSecret())
                .compact();
        return jwtProperties + " " + compact;
    }

    /**
     * 生成token
     *
     * @param userDetails 用户信息
     * @return token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(Constant.CREATED_TAG, new Date());
        claims.put(Constant.SUB, userDetails.getUsername());
        return generateToken(claims);
    }

    /**
     * 从token中获取数据声明
     *
     * @param token token
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return claims;
    }

    /**
     * 从token中获取用户名
     *
     * @param token token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Optional<Claims> claimsOptional = Optional.ofNullable(getClaimsFromToken(token));
        if (claimsOptional.isPresent()) {
            return claimsOptional.get().getSubject();
        }
        return null;
    }

    /**
     * 判断token 是否过期
     *
     * @param token token
     * @return boolean
     */
    private Boolean isTokenExpired(String token) {
        Optional<Claims> optional = Optional.ofNullable(getClaimsFromToken(token));
        if (optional.isPresent()) {
            Date expiration = optional.get().getExpiration();
            return expiration.before(new Date());
        }
        return true;
    }

    /**
     * 刷新token
     *
     * @param token token
     * @return 刷新后token
     */
    public String refreshToken(String token) {
        String refreshedToken = null;
        Optional<Claims> optional = Optional.ofNullable(getClaimsFromToken(token));
        if (optional.isPresent()) {
            Claims claims = optional.get();
            claims.put(Constant.CREATED_TAG, new Date());
            refreshedToken = generateToken(claims);
        }
        return refreshedToken;
    }

    /**
     * 验证token是否有效
     *
     * @param token token
     * @return 是否有效
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        SysUser sysUser = (SysUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(sysUser.getUsername()) && !isTokenExpired(token));
    }

}
