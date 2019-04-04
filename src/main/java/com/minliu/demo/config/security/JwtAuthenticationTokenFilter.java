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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private CustomUserDetailService userDetailsService;

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
        try {
            //从请求头中获取token
            String jwt = getJwtFromRequest(request);
            //验证token
            if (StringUtils.hasText(jwt) && jwtService.isTokenValid(jwt)) {
                Integer id = jwtService.getUserIdFromToken(jwt);
                /*
                    Note that you could also encode the user's username and roles inside JWT claims
                    and create the UserDetails object by parsing those claims from the JWT.
                    That would avoid the following database hit. It's completely up to you.
                 */
                UserDetails userDetails = userDetailsService.findUserById(id);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //绑定当前线程
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从request中获取token
     *
     * @param request 请求
     * @return token
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenHead())) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
