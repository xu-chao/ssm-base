package com.java.activiti.config;

import com.java.activiti.web.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope("singleton")
public class NettyConfig {

    private static final Logger logger= LoggerFactory.getLogger(NettyConfig.class);
    @Autowired
    private WebSocketServer webSocketServer;

    private Thread nettyThread;

    /**
     * ������Tomcat������ApplicationContext-main��netty�ļ���
     *       ����Netty WebSocket��������
     */
    @PostConstruct // Constructor >> @Autowired >> @PostConstruct
    // @PostConstructע��ķ�������������ע����ɺ��Զ�����
    public void init() {
        nettyThread =new Thread(webSocketServer);
        logger.info("���������̣߳�����Netty WebSocket������...");
        //logger.info( System.getProperty("user.dir"));
        nettyThread.start();
    }
    /**
     * ������Tomcat�������ر�ǰ��Ҫ�ֶ��ر�Netty Websocket�����Դ�����������ڴ�й©��
     *      1. �ͷ�Netty Websocket������ӣ�
     *      2. �ر�Netty Websocket�������߳�
     */
    @SuppressWarnings("deprecation")
    @PreDestroy //��Bean����������֮ǰ�����ñ�@PreDestroyע��ķ���
    public void close() {
        logger.info("�����ͷ�Netty Websocket�������...");
        webSocketServer.close();
        logger.info("���ڹر�Netty Websocket�������߳�...");
        nettyThread.stop();
        logger.info("ϵͳ�ɹ��رգ�");
    }
}
