package com.minliu.demo.pojo.audit;

/**
 * 创建者，修改者(主键)
 *
 * @author: liumin
 * @date: 2019/4/2 22:52
 * @version: JDK1.8
 */
public abstract class UserDateAudit extends DateAudit{
    private Integer createdBy;

    private Integer updatedBy;

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
