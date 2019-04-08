package com.minliu.demo.pojo.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 唯一性校验请求实体
 * <p>
 * ClassName: UniqueValidateRequest <br>
 * date: 1:56 PM 08/04/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public class UniqueValidateRequest implements Serializable {

    /**
     * 字段
     */
    @NotBlank
    private String field;

    /**
     * 值
     */
    @NotBlank
    private String value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
