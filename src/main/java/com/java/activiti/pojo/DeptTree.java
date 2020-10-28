package com.java.activiti.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.activiti.model.Dept;

import java.util.List;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DeptTree {

    private String id;//菜单id

    private String text;//菜单名称

    private Integer status;//是否已删除

    private boolean checked;//是否为选中

    private List<Dept> children;// 下级部门

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Dept> getChildren() {
        return children;
    }

    public void setChildren(List<Dept> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DeptTree{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }
}
