package com.java.activiti.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.java.activiti.model.Menu;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Tree {

    private String id;//�˵�id
    private String text;//�˵�����
    private Integer status;//�Ƿ���ɾ��
    private boolean checked;//�Ƿ�Ϊѡ��
    private List<Menu> children;// �¼��˵�
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
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
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public List<Menu> getChildren() {
        if(null == children) {
            children = new ArrayList<>();
        }
        return children;
    }
    public void setChildren(List<Menu> children) {
        this.children = children;
    }
    @Override
    public String toString() {
        return "Tree [id=" + id + ", text=" + text + ", status=" + status + ", checked=" + checked + ", children="
                + children + "]";
    }
}
