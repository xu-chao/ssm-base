package com.java.activiti.model;

import java.util.Date;

/**
 * �Զ�������ʵ�� תjson��ʱ���õ�
 * @author user
 *
 */
public class MyTask {

	private String id; // ����id
	private String taskID; // ����id
	private String processID; // ����id
	private String name; // ��������
	private Date createTime;  // ��������
	private Date endTime; // ��������
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
