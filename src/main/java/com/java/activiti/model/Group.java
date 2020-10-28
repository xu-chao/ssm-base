package com.java.activiti.model;


import java.io.Serializable;

/**
 * ��ɫʵ����չ
 * @author user
 *
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // ���� ��ɫ��
	private String name; // ����
	private String permissions; // �û����е�Ȩ�ޣ����Ȩ��֮���ö��Ÿ���
	
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
