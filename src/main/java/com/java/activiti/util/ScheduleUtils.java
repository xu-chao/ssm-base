package com.java.activiti.util;

import com.java.activiti.exception.ScheduleException;
import com.java.activiti.model.ScheduleJob;
import com.java.activiti.quartz.AsyncJobFactory;
import com.java.activiti.quartz.SyncJobFactory;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleUtils {

    /** ��־���� */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleUtils.class);

    /**
     * ��ȡ������key
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(String jobName, String jobGroup) {

        return TriggerKey.triggerKey(jobName, jobGroup);
    }

    /**
     * ��ȡ���ʽ������
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @return cron trigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) throws ScheduleException {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            return (CronTrigger) scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
            LOG.error("��ȡ��ʱ����CronTrigger�����쳣", e);
            throw new ScheduleException("��ȡ��ʱ����CronTrigger�����쳣");
        }
    }

    /**
     * ��������
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ScheduleException {
        createScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
                scheduleJob.getCronExpression(), scheduleJob.getIsSync(), scheduleJob);
    }

    /**
     * ������ʱ����
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     */
    public static void createScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, boolean isSync, Object param) throws ScheduleException {
        //ͬ�����첽
        Class<? extends Job> jobClass = isSync ? AsyncJobFactory.class : SyncJobFactory.class;

        //����job��Ϣ
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();

        //���ʽ���ȹ�����
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //���µ�cronExpression���ʽ����һ���µ�trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();

        String jobTrigger = trigger.getKey().getName();

        ScheduleJob scheduleJob = (ScheduleJob)param;
        scheduleJob.setJobTrigger(jobTrigger);

        //�������������ʱ�ķ������Ի�ȡ
        jobDetail.getJobDataMap().put("jobParam", scheduleJob);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOG.error("������ʱ����ʧ��", e);
            throw new ScheduleException("������ʱ����ʧ��");
        }
    }

    /**
     * ����һ������
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) throws ScheduleException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("����һ�ζ�ʱ����ʧ��", e);
            throw new ScheduleException("����һ�ζ�ʱ����ʧ��");
        }
    }

    /**
     * ��ͣ����
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) throws ScheduleException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("��ͣ��ʱ����ʧ��", e);
            throw new ScheduleException("��ͣ��ʱ����ʧ��");
        }
    }

    /**
     * �ָ�����
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) throws ScheduleException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            LOG.error("��ͣ��ʱ����ʧ��", e);
            throw new ScheduleException("��ͣ��ʱ����ʧ��");
        }
    }

    /**
     * ��ȡjobKey
     *
     * @param jobName the job name
     * @param jobGroup the job group
     * @return the job key
     */
    public static JobKey getJobKey(String jobName, String jobGroup) {

        return JobKey.jobKey(jobName, jobGroup);
    }

    /**
     * ���¶�ʱ����
     *
     * @param scheduler the scheduler
     * @param scheduleJob the schedule job
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ScheduleException {
        updateScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
                scheduleJob.getCronExpression(), scheduleJob.getIsSync(), scheduleJob);
    }

    /**
     * ���¶�ʱ����
     *
     * @param scheduler the scheduler
     * @param jobName the job name
     * @param jobGroup the job group
     * @param cronExpression the cron expression
     * @param isSync the is sync
     * @param param the param
     */
    public static void updateScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, boolean isSync, Object param) throws ScheduleException {

        //ͬ�����첽
//        Class<? extends Job> jobClass = isSync ? AsyncJobFactory.class : SyncJobFactory.class;

        try {
//            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(jobName, jobGroup));

//            jobDetail = jobDetail.getJobBuilder().ofType(jobClass).build();

            //���²��� ʵ�ʲ����з����޷�����
//            JobDataMap jobDataMap = jobDetail.getJobDataMap();
//            jobDataMap.put(ScheduleJobVo.JOB_PARAM_KEY, param);
//            jobDetail.getJobBuilder().usingJobData(jobDataMap);

            TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName, jobGroup);

            //���ʽ���ȹ�����
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            trigger.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, param);

            //���µ�cronExpression���ʽ���¹���trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            // ����״̬ΪPAUSED�����񣬽����Ⱥ�������������������ö�ʱ����ΪPAUSED״̬�󣬼�Ⱥ����������һ̨����ʱ��ʱ����ȫ�����ѵ�bug
            if(!triggerState.name().equalsIgnoreCase("PAUSED")){
                //���µ�trigger��������jobִ��
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            LOG.error("���¶�ʱ����ʧ��", e);
            throw new ScheduleException("���¶�ʱ����ʧ��");
        }
    }

    /**
     * ɾ����ʱ����
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) throws ScheduleException {
        try {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            LOG.error("ɾ����ʱ����ʧ��", e);
            throw new ScheduleException("ɾ����ʱ����ʧ��");
        }
    }
}
