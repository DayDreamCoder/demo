package com.minliu.demo.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * response 写入JSON，流 工具类
 *
 * @author: liumin
 * @date: 2019/4/7 20:06
 * @version: JDK1.8
 */
public class HttpResponseWriteUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseWriteUtils.class);

    private HttpResponseWriteUtils() {
        //
    }

    /**
     * 将数据转为JSON,写入response返回给前端
     *
     * @param data     Data
     * @param response HttpServletResponse
     * @param <D>      数据类型
     */
    public static <D extends Serializable> void writeData(D data, HttpServletResponse response) {
        //try with resources
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSON.toJSONString(data));
            writer.flush();
        } catch (Exception ex) {
            logger.error("RESPONSE 写入数据失败...");
            logger.error(ex.getMessage(), ex);
        }
    }
}
