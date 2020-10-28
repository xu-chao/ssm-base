package com.java.activiti.model;

import java.io.Serializable;

/**
 * ADD BY LIUHD AT 20191112
 * 下拉框枚举
 */
public class MyType implements Serializable {

    private static final long serialVersionUID = 1L;

    private int typeID;//类型ID-(1,2,3.....)

    private String typeCode;//区分类型

    private String typeName;//类型名称

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
