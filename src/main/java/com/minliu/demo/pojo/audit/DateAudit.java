package com.minliu.demo.pojo.audit;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建日期与修改日期
 *
 * @author: liumin
 * @date: 2019/4/2 22:51
 * @version: JDK1.8
 */
public abstract class DateAudit implements Serializable {
    private Date createdAt;

    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
