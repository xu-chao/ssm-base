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
     * 描述：Tomcat加载完ApplicationContext-main和netty文件后：
     *       启动Netty WebSocket服务器；
     */
    @PostConstruct // Constructor >> @Autowired >> @PostConstruct
    // @PostConstruct注解的方法将会在依赖注入完成后被自动调用
    public void init() {
        nettyThread =new Thread(webSocketServer);
        logger.info("开启独立线程，启动Netty WebSocket服务器...");
        //logger.info( System.getProperty("user.dir"));
        nettyThread.start();
    }
    /**
     * 描述：Tomcat服务器关闭前需要手动关闭Netty Websocket相关资源，否则会造成内存泄漏。
     *      1. 释放Netty Websocket相关连接；
     *      2. 关闭Netty Websocket服务器线程
     */
    @SuppressWarnings("deprecation")
    @PreDestroy //当Bean在容器销毁之前，调用被@PreDestroy注解的方法
    public void close() {
        logger.info("正在释放Netty Websocket相关连接...");
        webSocketServer.close();
        logger.info("正在关闭Netty Websocket服务器线程...");
        nettyThread.stop();
        logger.info("系统成功关闭！");
    }
}
