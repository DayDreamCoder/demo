package com.minliu.demo.service;

import com.alibaba.fastjson.JSON;
import com.minliu.demo.util.FastJson2JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: RedisService <br>
 * date: 3:35 PM 21/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@SuppressWarnings("all")
@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private FastJson2JsonSerializer serializer;

    @PostConstruct
    public void init() {
        serializer = (FastJson2JsonSerializer) redisTemplate.getValueSerializer();
    }

    /**
     * delete given key
     *
     * @param keys key
     * @return boolean
     */
    public boolean delete(List<String> keys) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            keys.forEach(key -> connection.del(serializer.serialize(key)));
            return true;
        });
    }

    /**
     * batch delete given keys
     *
     * @param key given keys
     * @return boolean
     */
    public boolean delete(String... key) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            List<String> keys = Arrays.asList(key);
            keys.forEach(k -> connection.del(serializer.serialize(k)));
            return true;
        });
    }

    /**
     * set string
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间
     */
    public boolean set(final String key, final String value, long expire) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.set(serializer.serialize(key), serializer.serialize(value));
            if (expire > 0) {
                connection.expire(serializer.serialize(key), expire);
            }
            return true;
        });
    }

    /**
     * persist reserve
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public boolean set(final String key, Object value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(serializer.serialize(key),
                serializer.serialize(value)));
    }


    /**
     * set list
     *
     * @param key    key
     * @param values values
     * @param <V>    Type of value
     * @return if success
     */
    public <V> boolean setList(final String key, List<V> values) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(serializer.serialize(key), serializer.serialize(values)));
    }

    /**
     * set list with expire time
     *
     * @param key    key
     * @param values values
     * @param expire expire time
     * @param <V>    class
     * @return Boolean
     */
    public <V> boolean setList(final String key, List<V> values, long expire) {
        return set(key, JSON.toJSONString(values), expire);
    }


    /**
     * get list and transfer to given class
     *
     * @param key   key
     * @param clazz Class
     * @param <V>   V
     * @return List
     */
    @SuppressWarnings("unchecked")
    public <V> List<V> getList(String key, Class<V> clazz) {
        return redisTemplate.execute((RedisCallback<List<V>>) connection -> {
            byte[] bytes = connection.get(serializer.serialize(key));
            if (bytes.length > 0) {
                try {
                    String text = new String(bytes, serializer.getCharset());
                    return JSON.parseArray(text, clazz);
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return Collections.emptyList();
        });
    }

    /**
     * get Value
     *
     * @param key key
     * @return String
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> cl) {
        return redisTemplate.execute((RedisCallback<T>) connection -> {
            byte[] bytes = connection.get(serializer.serialize(key));
            return (T) serializer.deserialize(bytes);
        });
    }

    /**
     * get the value(string)
     *
     * @param key
     * @return String
     */
    public String get(String key) {
        return get(key, String.class);
    }

}
