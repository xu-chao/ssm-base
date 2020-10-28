package com.java.activiti.pojo.erp;

import java.io.Serializable;

public class Footer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private Integer count;

    private Integer total;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Footer{" +
                "code='" + code + '\'' +
                ", count=" + count +
                ", total=" + total +
                '}';
    }
}
