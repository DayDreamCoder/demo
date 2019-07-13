package com.minliu.demo;

import java.io.Serializable;

/**
 * ClassName: Column <br>
 * date: 10:50 AM 03/07/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */

public class Column implements Serializable{
    private String columnName;

    private String dataType;

    public Column() {
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
