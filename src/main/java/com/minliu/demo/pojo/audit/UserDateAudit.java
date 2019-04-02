package com.minliu.demo.pojo.audit;

/**
 * 创建者，修改者
 *
 * @author: liumin
 * @date: 2019/4/2 22:52
 * @version: JDK1.8
 */
public abstract class UserDateAudit extends DateAudit{
    private String createdBy;

    private String updatedBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
