package com.java.activiti.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.activiti.model.Dept;

import java.util.List;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DeptTree {

    private String id;//�˵�id

    private String text;//�˵�����

    private Integer status;//�Ƿ���ɾ��

    private boolean checked;//�Ƿ�Ϊѡ��

    private List<Dept> children;// �¼�����

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
