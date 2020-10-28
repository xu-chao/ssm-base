package com.java.activiti.quartz.init;

import com.java.activiti.exception.ScheduleException;
import com.java.activiti.service.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ScheduleJobInit {

    /** ��־���� */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

    /** ��ʱ����service */
    @Resource
    private ScheduleJobService scheduleJobService;

    /**
     * ��Ŀ����ʱ��ʼ��
     */
    @PostConstruct
    public void init() throws ScheduleException {

        if (LOG.isInfoEnabled()) {
            LOG.info("init");
        }

        scheduleJobService.initScheduleJob();

        if (LOG.isInfoEnabled()) {
            LOG.info("end");
        }
    }
}
