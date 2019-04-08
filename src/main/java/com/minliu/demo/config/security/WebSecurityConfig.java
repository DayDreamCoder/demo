package com.minliu.demo.config.security;

import com.minliu.demo.config.security.handler.RestAccessDeniedHandler;
import com.minliu.demo.config.security.handler.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * 系统安全配置类
 * ClassName: WebSecurityConfig <br>
 * date: 4:06 PM 01/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 未登陆时返回 JSON 格式的数据给前端
     */
    @Resource
    private RestAuthenticationEntryPoint unauthorizedHandler;

    /**
     * 无权访问返回的 JSON 格式数据给前端
     */
    @Resource
    private RestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Resource
    private CustomUserDetailService userDetailService;

    /**
     * 密码加密器
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 验证管理器
     *
     * @return AuthenticationManager
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .cors()
                    .and()
               .csrf()
                    .disable()
               /*.formLogin()
                    .loginProcessingUrl("/api/auth/signIn")
                    .failureHandler(authenticationFailureHandler)
                    .and()*/
               .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
               .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
               .userDetailsService(userDetailService)
               .authorizeRequests()
               .antMatchers("/",
                       "/favicon.ico",
                       "/csrf/**",
                       "/**/*.png",
                       "/**/*.gif",
                       "/**/*.svg",
                       "/**/*.jpg",
                       "/**/*.html",
                       "/**/*.css",
                       "/**/*.js")
                        .permitAll()
               .antMatchers("/api/auth/**")
                        .permitAll()
               .anyRequest()
                        .authenticated();

        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/login",
                "/swagger-resources",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/druid/**");
    }



}
