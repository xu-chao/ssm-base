package com.java.activiti.model.question;

import com.java.activiti.model.*;

import java.io.Serializable;
import java.util.Date;

public class QuestionInfo implements Serializable {//问题反馈单
    private static final long serialVersionUID = 1L;
    private String questionID;//id
    private User user; // 提交人
    private Park park;//park_id 工程
    private Project project;//project_id 项目
    private Dept dept;//专业
    private String subjectID;//subject_id 专业名
    private String problemTitle;//problem_text 问题描述
    private String problemText;//problem_text 问题描述
    private String fileBeforeID;//fileBefore_id 报修前文件ID
    private File fileBefore; //file_id报修前文件
    private Images imagesBefore; //file_id报修前文件I
    private String fileAfterID;//fileAfter_id 报修后文件ID
    private File fileAfter; //报修前文件
    private Images imagesAfter; //报修后图片
    private Date questionDate;//question_date 提交时间
    private String state; // 审核状态  未提交  审核中 审核通过 审核未通过
    private int stateID; // 审核状态  未提交  审核中 审核通过 审核未通过
    private String processInstanceId; // 流程实例Id processInstanceId

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getProblemTitle() {
        return problemTitle;
    }

    public void setProblemTitle(String problemTitle) {
        this.problemTitle = problemTitle;
    }

    public String getProblemText() {
        return problemText;
    }

    public void setProblemText(String problemText) {
        this.problemText = problemText;
    }

    public String getFileBeforeID() {
        return fileBeforeID;
    }

    public void setFileBeforeID(String fileBeforeID) {
        this.fileBeforeID = fileBeforeID;
    }

    public File getFileBefore() {
        return fileBefore;
    }

    public void setFileBefore(File fileBefore) {
        this.fileBefore = fileBefore;
    }

    public Images getImagesBefore() {
        return imagesBefore;
    }

    public void setImagesBefore(Images imagesBefore) {
        this.imagesBefore = imagesBefore;
    }

    public String getFileAfterID() {
        return fileAfterID;
    }

    public void setFileAfterID(String fileAfterID) {
        this.fileAfterID = fileAfterID;
    }

    public File getFileAfter() {
        return fileAfter;
    }

    public void setFileAfter(File fileAfter) {
        this.fileAfter = fileAfter;
    }

    public Images getImagesAfter() {
        return imagesAfter;
    }

    public void setImagesAfter(Images imagesAfter) {
        this.imagesAfter = imagesAfter;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
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

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "questionID='" + questionID + '\'' +
                ", user=" + user +
                ", park=" + park +
                ", project=" + project +
                ", dept=" + dept +
                ", subjectID='" + subjectID + '\'' +
                ", problemTitle='" + problemTitle + '\'' +
                ", problemText='" + problemText + '\'' +
                ", fileBeforeID='" + fileBeforeID + '\'' +
                ", fileBefore=" + fileBefore +
                ", imagesBefore=" + imagesBefore +
                ", fileAfterID='" + fileAfterID + '\'' +
                ", fileAfter=" + fileAfter +
                ", imagesAfter=" + imagesAfter +
                ", questionDate=" + questionDate +
                ", state='" + state + '\'' +
                ", stateID=" + stateID +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
