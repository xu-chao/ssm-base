package com.java.activiti.model;

import java.math.BigInteger;
import java.util.Date;

/**
 * 请假单实体
 * @author user
 *
 */
public class Leave {

	private String id; // 故障申报单号
	private String leaveReason; // 故障描述
	private User user; // 运维人
	private Date leaveDate;// 运维日期
	private Integer leaveDays; // 运维天数
	private String state; // 审核状态  未提交  审核中 审核通过 审核未通过
	private String processInstanceId; // 流程实例Id
	
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
