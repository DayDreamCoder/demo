package com.minliu.demo.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minliu
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidSourceConfig {

    @Autowired
    WallFilter wallFilter;


    public class DruidConfiguration {
        @Bean(destroyMethod = "close", initMethod = "init")
        @Primary
        public DataSource druidDataSource(@Value("${spring.datasource.url}") String url,
                                          @Value("${spring.datasource.driverClassName}") String driver,
                                          @Value("${spring.datasource.username}") String userName,
                                          @Value("${spring.datasource.password}") String password,
                                          @Value("${spring.datasource.maxActive}") int maxActive,
                                          @Value("${spring.datasource.initialSize}") int initialSize,
                                          @Value("${spring.datasource.minIdle}") int minIdle, @Value("${spring.datasource.maxWait}") int maxWait,
                                          @Value("${spring.datasource.timeBetweenEvictionRunsMillis}") int timeBetweenEvictionRunsMillis,
                                          @Value("${spring.datasource.minEvictableIdleTimeMillis}") int minEvictableIdleTimeMillis,
                                          @Value("${spring.datasource.validationQuery}") String validationQuery,
                                          @Value("${spring.datasource.testWhileIdle}") boolean testWhileIdle,
                                          @Value("${spring.datasource.testOnBorrow}") boolean testOnBorrow,
                                          @Value("${spring.datasource.filters}") String filters,
                                          @Value("${spring.datasource.testOnReturn}") boolean testOnReturn) throws SQLException {
            DruidDataSource dataSource = new DruidDataSource();
            /* 数据源主要配置 */
            dataSource.setUrl(url);
            dataSource.setDriverClassName(driver);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            /* 数据源补充配置 */
            dataSource.setMaxActive(maxActive);
            dataSource.setInitialSize(initialSize);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxWait(maxWait);
            dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            dataSource.setValidationQuery(validationQuery);
            dataSource.setTestOnBorrow(testOnBorrow);
            dataSource.setTestOnReturn(testOnReturn);
            List<Filter> filterList = new ArrayList<>();
            filterList.add(statFilter());
            filterList.add(log4jFilter());
            filterList.add(wallFilter);
            dataSource.setProxyFilters(filterList);
            dataSource.setTestWhileIdle(testWhileIdle);
            return dataSource;

        }

    }

    @Bean(name = "wallConfig")
    WallConfig wallFilterConfig() {
        WallConfig wc = new WallConfig();
        wc.setMultiStatementAllow(true);
        return wc;
    }

    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    WallFilter wallFilterProxy(WallConfig wallConfig) {
        WallFilter walter = new WallFilter();
        walter.setConfig(wallConfig);
        return walter;
    }

    @Bean
    StatFilter statFilter() {
        return new StatFilter();
    }

    @Bean
    Log4jFilter log4jFilter() {
        return new Log4jFilter();
    }


    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServle2() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("loginUsername", "admin2");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> druidStatFilter2() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
