package com.java.activiti.model;

import java.util.LinkedList;
import java.util.List;

public class Menu {
    private String menuid;// 编号
    private String menuname;// 名称
    private String url;// 对应URL
    private String icon;// 图标样式
    private String pid;// 上一级菜单编号
    private Integer is_parent;// 该菜单是否为父菜单，1为true，0为false
    private List<Menu> menus = new LinkedList<Menu>();

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(Integer is_parent) {
        this.is_parent = is_parent;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuid=" + menuid +
                ", menuname=" + menuname +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", pid=" + pid +
                ", is_parent='" + is_parent + '\'' +
                '}';
    }
}
