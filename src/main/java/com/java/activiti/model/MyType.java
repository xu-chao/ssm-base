package com.java.activiti.model;

import java.io.Serializable;

/**
 * ADD BY LIUHD AT 20191112
 * ������ö��
 */
public class MyType implements Serializable {

    private static final long serialVersionUID = 1L;

    private int typeID;//����ID-(1,2,3.....)

    private String typeCode;//��������

    private String typeName;//��������

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
