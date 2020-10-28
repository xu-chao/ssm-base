package com.java.activiti.model.question;

import java.util.Date;

public class QuestionTask {

    private String id; // ����id
    private String questionID; // ����ID
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

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
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

    @Override
    public String toString() {
        return "QuestionTask{" +
                "id='" + id + '\'' +
                ", questionID='" + questionID + '\'' +
                ", taskID='" + taskID + '\'' +
                ", processID='" + processID + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                '}';
    }
}
