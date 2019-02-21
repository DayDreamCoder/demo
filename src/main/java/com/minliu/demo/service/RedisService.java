package com.minliu.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * ClassName: RedisService <br>
 * date: 3:35 PM 21/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private RedisSerializer<String> serializer;

    @PostConstruct
    public void init() {
        serializer = redisTemplate.getStringSerializer();
    }

    /**
     * set 方法
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间
     */
    public boolean set(final String key, final String value, long expire) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.set(serializer.serialize(key), serializer.serialize(value));
            if (expire > 0){
                connection.expire(serializer.serialize(key),expire);
            }
            return true;
        });
    }

    public String get(String key){
        return redisTemplate.execute((RedisCallback<String>)connection ->{
            byte[] bytes = connection.get(serializer.serialize(key));
            return serializer.deserialize(bytes);
        });
    }

}
