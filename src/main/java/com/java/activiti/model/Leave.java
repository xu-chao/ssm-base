package com.java.activiti.model;

import java.math.BigInteger;
import java.util.Date;

/**
 * ��ٵ�ʵ��
 * @author user
 *
 */
public class Leave {

	private String id; // �����걨����
	private String leaveReason; // ��������
	private User user; // ��ά��
	private Date leaveDate;// ��ά����
	private Integer leaveDays; // ��ά����
	private String state; // ���״̬  δ�ύ  ����� ���ͨ�� ���δͨ��
	private String processInstanceId; // ����ʵ��Id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public Integer getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	
}
