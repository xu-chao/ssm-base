package com.java.activiti.pojo;

public class EasyUIOptionalDataListNode {
    private int id;// Ȩ��id
    private String text;// Ȩ������
    private boolean checked;// �Ƿ�Ϊѡ��

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
