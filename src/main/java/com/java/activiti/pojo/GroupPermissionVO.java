package com.java.activiti.pojo;

/**
 * URL �Ľ�ɫ��ɫȨ����Ϣ
 *
 * @author XU
 * @since 2019/7/11.
 */
public class GroupPermissionVO {
    /**
     * URL �Ľ�ɫ��ɫȨ����Ϣ����
     */
    private String name;

    /**
     * URL �Ľ�ɫ��ɫȨ����Ϣ��Ӧ��URL
     */
    private String url;

    /**
     * URL �Ľ�ɫ��ɫȨ����Ϣ��Ӧ�Ľ�ɫ
     */
    private String group;


    /**
     * ��Ӧ�� getter & setter
     */

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRole(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "GroupPermissionVO{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
