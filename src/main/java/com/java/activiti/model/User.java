package com.java.activiti.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户扩展实体
 * @author user
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	//0:禁止登录
	public static final int _0 = 0;
	//1:有效
	public static final int _1 = 1;
	private String id; // 主键 用户名
	private String firstName;  // 姓
	private String lastName; // 名
	private String email; // 邮箱
	private String allName; // ALLNAME_ 姓名
	private String password; // 密码
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime; //创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime; //最后一次登录时间
	private Integer status; //登录状态
	private String groups; // 用户所有的角色 多个角色之间用逗号隔开
	private String phone;//PHONE_电话号码
	private String pictureID;//PICTURE_ID_头像
	private String position;//POSITION_地址
	private String certificate;//CERTIFICATE_身份证书
	public User() {}

	public User(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.allName = user.getAllName();
		this.password = user.getPassword();
		this.createTime = user.getCreateTime();
		this.lastLoginTime = user.getLastLoginTime();
		this.status = user.getStatus();
		this.phone = user.getPhone();
		this.pictureID = user.getPictureID();
		this.position = user.getPosition();
		this.certificate = user.getCertificate();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() { return createTime; }
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) { this.createTime = createTime; }
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime() { return lastLoginTime; }
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setLastLoginTime(Date lastLoginTime) { this.lastLoginTime = lastLoginTime; }
	public Integer getStatus() { return status; }
	public void setStatus(Integer status) { this.status = status; }
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPictureID() {
		return pictureID;
	}

	public void setPictureID(String pictureID) {
		this.pictureID = pictureID;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", allName='" + allName + '\'' +
				", password='" + password + '\'' +
				", createTime=" + createTime +
				", lastLoginTime=" + lastLoginTime +
				", status=" + status +
				", groups='" + groups + '\'' +
				", phone='" + phone + '\'' +
				", pictureID='" + pictureID + '\'' +
				", position='" + position + '\'' +
				", certificate='" + certificate + '\'' +
				'}';
	}
}
