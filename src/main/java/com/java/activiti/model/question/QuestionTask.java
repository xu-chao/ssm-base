package com.java.activiti.model.question;

import java.util.Date;

public class QuestionTask {

    private String id; // 任务id
    private String questionID; // 问题ID
    private String taskID; // 任务id
    private String processID; // 流程id
    private String name; // 任务名称
    private Date createTime;  // 创建日期
    private Date endTime; // 结束日期

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
