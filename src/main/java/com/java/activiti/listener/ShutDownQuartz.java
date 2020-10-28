package com.java.activiti.listener;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Component
public class ShutDownQuartz implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        try {
//            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
//            defaultScheduler.shutdown(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * tomcat������ʼ��
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("tomcat�Ѿ�������");
    }

    /**
     * tomcat�ر�
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("tomcat�Ѿ��رգ���ʼ�ر�quartz��");
        try {
            SchedulerFactory sf = new StdSchedulerFactory();//�����µĵ���������
            Scheduler scheduler = null;
            scheduler = sf.getScheduler();//��ȡ��ǰ���̵����ж�ʱ���߳�����
            scheduler.shutdown(false);//�رն�ʱ���߳�

            System.out.println("�رն�ʱ���̳߳ɹ���");
        } catch (Exception e) {
            System.out.println("�رն�ʱ���߳�ʧ�ܣ�");
            e.printStackTrace();
        }
    }

}
