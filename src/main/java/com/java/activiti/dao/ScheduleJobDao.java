package com.java.activiti.dao;

import com.java.activiti.model.City;
import com.java.activiti.model.ScheduleJob;

import java.util.List;
import java.util.Map;

public interface ScheduleJobDao {

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int scheduleJobCount(Map<String,Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<ScheduleJob> scheduleJobPage(Map<String,Object> map);

    /**
     * ����
     *
     * @param scheduleJob
     * @return
     */
    public Long insert(ScheduleJob scheduleJob);

    /**
     * ֱ���޸� ֻ���޸����е�ʱ�䣬������ͬ�첽���޷��޸�
     *
     * @param scheduleJob
     */
    public Long update(ScheduleJob scheduleJob);

    /**
     * ɾ�����´�����ʽ
     *
     * @param scheduleJob
     */
//    public void delUpdate(ScheduleJob scheduleJob);

    /**
     * ɾ��
     *
     * @param scheduleJobId
     */
    public Long delete(Long scheduleJobId);

    /**
     * ����һ������
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
//    public void runOnce(Long scheduleJobId);

    /**
     * ��ͣ����
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
//    public void pauseJob(Long scheduleJobId);

    /**
     * �ָ�����
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
//    public void resumeJob(Long scheduleJobId);

    /**
     * ��ȡ�������
     *
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(Long scheduleJobId);

    /**
     * ��ѯ�����б�
     *
     * @return
     */
    public List<ScheduleJob> queryList();

    /**
     * ��ȡ�����е������б�
     *
     * @return
     */
//    public List<ScheduleJob> queryExecutingJobList();

    List<ScheduleJob> findScheduleJob(ScheduleJob scheduleJob);
}
