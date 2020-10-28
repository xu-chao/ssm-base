package com.java.activiti.pojo;

public class UserTask {

    private String userID;

    private String taskID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @Override
    public String toString() {
        return "UserTask{" +
                "userID='" + userID + '\'' +
                ", taskID='" + taskID + '\'' +
                '}';
    }
}
