package com.minliu.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


/**
 * 自定义线程池配置类
 *
 * @author liumin
 */
@Configuration
@ConfigurationProperties(prefix = "pool",ignoreUnknownFields = false)
@PropertySource("classpath:config/threadpool.properties")
@Component
public class ThreadPoolProperties {

    /**
     * 核心线程
     */
    private Integer coreSize;

    /**
     * 最大线程
     */
    private Integer maxSize;

    /**
     * 非核心线程存活时间
     */
    private Integer maxTime;

    /**
     * 时间单位
     */
    private String timeType;

    private TimeUnit timeUnit;

    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
        this.timeUnit = TimeType.getTimeUnit(timeType);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    enum TimeType{
        /**
         * 秒
         */
        SECONDS("SECOND",TimeUnit.SECONDS),
        /**
         * 分钟
         */
        MINUTES("MINUTE",TimeUnit.MINUTES),
        /**
         * 小时
         */
        HOURS("HOUR",TimeUnit.HOURS);

        private String unitType;

        private TimeUnit timeUnit;

        TimeType(String unitType, TimeUnit timeUnit) {
            this.unitType = unitType;
            this.timeUnit = timeUnit;
        }

        public String getUnitType() {
            return unitType;
        }

        public void setUnitType(String unitType) {
            this.unitType = unitType;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }

        public static TimeUnit getTimeUnit(String type){
            Optional<TimeType> any = Stream.of(TimeType.values()).filter(timeType -> type.equals(timeType.getUnitType()))
                    .findAny();
            if (any.isPresent()) {
                return any.get().getTimeUnit();
            }
            return DEFAULT_TIME_UNIT;
        }
    }
}
