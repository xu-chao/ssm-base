package com.java.activiti.pojo;

import java.util.ArrayList;
import java.util.List;

public class EasyUIOptionalTreeNode {
    private String id;// �˵�id
    private String text;// �˵�����
    private boolean checked;// �Ƿ�Ϊѡ��
    private List<EasyUIOptionalTreeNode> children = new ArrayList<EasyUIOptionalTreeNode>();// �¼��˵�

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
    public List<EasyUIOptionalTreeNode> getChildren() {
        return children;
    }
    public void setChildren(List<EasyUIOptionalTreeNode> children) {
        this.children = children;
    }
    @Override
    public String toString() {
        return "EasyUIOptionalTreeNode [id=" + id + ", text=" + text + ", checked=" + checked + ", children=" + children
                + "]";
    }
}
