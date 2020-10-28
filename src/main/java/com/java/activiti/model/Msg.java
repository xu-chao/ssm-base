package com.java.activiti.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Msg implements Serializable{

    private static final long serialVersionUID = 1L;

    //反馈代码
    private int code;

    //反馈信息
    private String message;

    //反馈数据
    private Map<String,Object> data = new HashMap<String, Object>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
