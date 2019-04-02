package com.minliu.demo.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 每次请求的时候都会被该过滤器过滤拦截,校验token的有效性
 * <p>
 * ClassName: JwtAuthenticationTokenFilter <br>
 * date: 11:01 AM 02/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailServiceImpl userDetailsService;

    @Resource
    private JwtService jwtService;

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 拦截请求，验证token
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器执行链
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(jwtProperties.getHeader());
        String tokenHead = jwtProperties.getTokenHead();
        //验证token 头部
        if (StringUtils.hasText(header) && header.startsWith(tokenHead)) {
            String authToken = header.substring(tokenHead.length());
            String username = jwtService.getUsernameFromToken(authToken);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            //验证用户名
            if (StringUtils.hasText(username) && securityContext.getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证用户名是否有效以及token是否过期
                if (jwtService.isTokenValid(authToken, userDetails)) {
                    //token有效将用户信息设置到ThreadLocal
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
