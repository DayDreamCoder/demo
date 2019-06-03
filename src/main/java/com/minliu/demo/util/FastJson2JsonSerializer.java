package com.minliu.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.UnsupportedEncodingException;

/**
 * ClassName: FastJson2JsonSerializer <br>
 * date: 3:37 PM 26/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class FastJson2JsonSerializer<T> implements RedisSerializer<T> {
    /**
     * 默认字符集 utf-8
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final byte[] NULL = new byte[0];

    private static final Logger logger = LoggerFactory.getLogger(FastJson2JsonSerializer.class);

    private Class<T> clazz;

    private String charset = DEFAULT_CHARSET;

    @Override
    public byte[] serialize(T t) throws SerializationException {
        try {
            return t == null ? NULL : JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的字符集:{}", charset);
            throw new SerializationException(e.getMessage());
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        try {
            String text = new String(bytes, charset);
            return JSON.parseObject(text, clazz);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的字符集:{}", charset);
            throw new SerializationException(e.getMessage());
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
