package com.minliu.demo.config.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

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
     * 用数据认证生成token
     *
     * @param authentication 认证
     * @return token
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
        Date expireDate = new Date(System.currentTimeMillis() + jwtProperties.getExpire());
        return Jwts.builder()
                .setSubject(Integer.toString(principal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.ES512,jwtProperties.getSecret())
                .compact();
    }


    /**
     * 从token中获取用户Id
     *
     * @param token token
     * @return 用户Id
     */
    public Integer getUserIdFromToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(body.getSubject());
    }

    /**
     * 判断token 是否有效
     *
     * @param token token
     * @return boolean
     */
    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }





}
