package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ������ȵĲ���key
     */
    public static final String JOB_PARAM_KEY = "jobParam";

    /**
     * ����id
     */
    private Long scheduleJobId;

    /**
     * ��������
     */
    private String jobName;

    /**
     * �������
     */
    private String aliasName;

    /**
     * �������
     */
    private String jobGroup;

    /**
     * ������
     */
    private String jobTrigger;

    /**
     * ����״̬
     */
    private String status;

    /**
     * ��������ʱ����ʽ
     */
    private String cronExpression;

    /**
     * �Ƿ��첽
     */
    private Boolean isSync;

    /**
     * ��������
     */
    private String description;

    /**
     * ����ʱ��
     */
    private Date gmtCreate;

    /**
     * �޸�ʱ��
     */
    private Date gmtModify;

    /**
     * ����ִ��url
     */
    private String url;

    public Long getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Long scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobTrigger() {
        return jobTrigger;
    }

    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "scheduleJobId=" + scheduleJobId +
                ", jobName='" + jobName + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobTrigger='" + jobTrigger + '\'' +
                ", status='" + status + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", isSync=" + isSync +
                ", description='" + description + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", url='" + url + '\'' +
                '}';
    }
}
