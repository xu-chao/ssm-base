package com.java.activiti.service.impl;

import com.java.activiti.dao.ScheduleJobDao;
import com.java.activiti.exception.ScheduleException;
import com.java.activiti.model.City;
import com.java.activiti.model.ScheduleJob;
import com.java.activiti.service.ScheduleJobService;
import com.java.activiti.util.ScheduleUtils;
import com.java.activiti.util.aop.Operation;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    /** 通用dao */
    @Resource
    private ScheduleJobDao scheduleJobDao;

    @Override
    public List<ScheduleJob> scheduleJobPage(Map<String, Object> map) {
        return scheduleJobDao.scheduleJobPage(map);
    }

    @Override
    public int scheduleJobCount(Map<String, Object> map) {
        return scheduleJobDao.scheduleJobCount(map);
    }

    public void initScheduleJob() throws ScheduleException {
        List<ScheduleJob> scheduleJobList = scheduleJobDao.queryList();
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    public Long insert(ScheduleJob scheduleJob) throws ScheduleException {
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        return scheduleJobDao.insert(scheduleJob);
    }

    public Long update(ScheduleJob scheduleJob) throws ScheduleException {
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);

//        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        return scheduleJobDao.update(scheduleJob);
    }

    public void delUpdate(ScheduleJob scheduleJob) throws ScheduleException {
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        scheduleJobDao.update(scheduleJob);
    }

    public void delete(Long scheduleJobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        scheduleJobDao.delete(scheduleJobId);
    }

    public Long deleteScheduleJobById(List<Long> id) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.get(id.get(0));
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        return scheduleJobDao.delete(id.get(0));
    }

    public List<ScheduleJob> findScheduleJob(ScheduleJob scheduleJob) {
        return scheduleJobDao.findScheduleJob(scheduleJob);
    }

    public void runOnce(Long scheduleJobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    public void pauseJob(Long scheduleJobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    public void resumeJob(Long scheduleJobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //演示数据库就不更新了
    }

    public ScheduleJob get(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobDao.get(scheduleJobId);
        return scheduleJob;
    }

    public List<ScheduleJob> queryList() {

        List<ScheduleJob> scheduleJobs = scheduleJobDao.queryList();

        try {
            for (ScheduleJob scheduleJob : scheduleJobs) {

                JobKey jobKey = ScheduleUtils.getJobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                scheduleJob.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                scheduleJob.setStatus(triggerState.name());

                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    scheduleJob.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            //暂时不处理
        }
        return scheduleJobs;
    }

    /**
     * 获取运行中的job列表
     * @return
     */
    public List<ScheduleJob> queryExecutingJobList(Map<String,Object> map) {
        try {
            // 存放结果集
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();

            // 获取scheduler中的JobGroupName
            for (String group:scheduler.getJobGroupNames()){
                // 获取JobKey 循环遍历JobKey
                for(JobKey jobKey:scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))){
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob)jobDataMap.get("jobParam");
//                    ScheduleJob scheduleJobVo = new ScheduleJob();
//                    BeanConverter.convert(scheduleJobVo,scheduleJob);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJob.setJobTrigger(trigger.getKey().getName());
                    scheduleJob.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJob.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if(triggerState.name().equals("NORMAL")){
                        jobList.add(scheduleJob);
                    }
                }
            }

            /** 非集群环境获取正在执行的任务列表 */
            /**
             List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
             List<ScheduleJobVo> jobList = new ArrayList<ScheduleJobVo>(executingJobs.size());
             for (JobExecutionContext executingJob : executingJobs) {
             ScheduleJobVo job = new ScheduleJobVo();
             JobDetail jobDetail = executingJob.getJobDetail();
             JobKey jobKey = jobDetail.getKey();
             Trigger trigger = executingJob.getTrigger();
             job.setJobName(jobKey.getName());
             job.setJobGroup(jobKey.getGroup());
             job.setJobTrigger(trigger.getKey().getName());
             Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
             job.setStatus(triggerState.name());
             if (trigger instanceof CronTrigger) {
             CronTrigger cronTrigger = (CronTrigger) trigger;
             String cronExpression = cronTrigger.getCronExpression();
             job.setCronExpression(cronExpression);
             }
             jobList.add(job);
             }*/

            return jobList;
        } catch (SchedulerException e) {
            return null;
        }

    }
}
