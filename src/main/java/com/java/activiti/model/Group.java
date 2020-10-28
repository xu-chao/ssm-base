package com.java.activiti.model;


import java.io.Serializable;

/**
 * 角色实体扩展
 * @author user
 *
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // 主键 角色名
	private String name; // 名称
	private String permissions; // 用户所有的权限，多个权限之间用逗号隔开
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissioms) {
		this.permissions = permissioms;
	}

	@Override
	public String toString() {
		return "Group{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", permissions='" + permissions + '\'' +
				'}';
	}
}
