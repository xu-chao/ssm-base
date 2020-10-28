package com.java.activiti.pojo;

/**
 * URL 的角色角色权限信息
 *
 * @author XU
 * @since 2019/7/11.
 */
public class GroupPermissionVO {
    /**
     * URL 的角色角色权限信息名称
     */
    private String name;

    /**
     * URL 的角色角色权限信息对应的URL
     */
    private String url;

    /**
     * URL 的角色角色权限信息对应的角色
     */
    private String group;


    /**
     * 对应的 getter & setter
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
