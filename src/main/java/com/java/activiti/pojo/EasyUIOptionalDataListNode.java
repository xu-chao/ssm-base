package com.java.activiti.pojo;

public class EasyUIOptionalDataListNode {
    private int id;// 权限id
    private String text;// 权限名称
    private boolean checked;// 是否为选中

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "EasyUIOptionalDataListNode{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }
}
