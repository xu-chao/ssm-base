package com.java.activiti.web.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description:
 * Netty WebSocket������
 * ʹ�ö������߳�����
 */
public class WebSocketServer implements Runnable {

    private final Logger logger= LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * EventLoopGroup ʵ����Executor�������̳߳ؽӿ�
     * ���bossGroup �� workerGroup �� Bootstrap ����
     * �����д������������������ group �����̳߳أ�
     */
    //bossGroup �̳߳���ֻ���� Bind ĳ���˿ں󣬻������һ���߳���Ϊ MainReactor��
    // ר�Ŵ���˿ڵ� Accept �¼���ÿ���˿ڶ�Ӧһ�� Boss �̡߳�(�Ӵ��̳߳�)
    @Autowired
    private EventLoopGroup bossGroup;
    //workerGroup �̳߳ػᱻ���� SubReactor �� Worker �̳߳�����á�(�����̳߳�)
    @Autowired
    private EventLoopGroup workerGroup;
    @Autowired
    //Bootstrap ��˼��������һ�� Netty Ӧ��ͨ����һ�� Bootstrap ��ʼ��
    // ��Ҫ�������������� Netty ���򣬴������������Netty �� Bootstrap ���ǿͻ��˳�������������࣬ServerBootstrap �Ƿ�������������ࡣ
    private ServerBootstrap serverBootstrap;

    private int port;
    private ChannelHandler childChannelHandler;
    private ChannelFuture serverChannelFuture;

    @Override
    public void run() {
        build();
    }
    public void build() {
        try {
            long begin = System.currentTimeMillis();
            serverBootstrap.group(bossGroup, workerGroup) //boss�����ͻ��˵�tcp��������  worker������ͻ���֮ǰ�Ķ�д����
                    .channel(NioServerSocketChannel.class) //���ÿͻ��˵�channel����
                    .option(ChannelOption.SO_BACKLOG, 1024) //����TCP�����������ַ�����������
                    .option(ChannelOption.TCP_NODELAY, true) //TCP_NODELAY�㷨�������ܷ��ʹ�����ݣ����ٳ���С������
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//��������������ƣ����ǿͻ��ˡ�����˽������Ӵ���ESTABLISHED״̬������2Сʱû�н��������ƻᱻ����
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//���ù̶����Ƚ��ջ�����������
                    .childHandler(childChannelHandler); //��I/O�¼��Ĵ�����,WebSocketChildChannelHandler�ж���
            long end = System.currentTimeMillis();
            logger.info("Netty Websocket������������ɣ���ʱ " + (end - begin) + " ms���Ѱ󶨶˿� " + port + " ����ʽ�Ⱥ�ͻ�������");
            serverChannelFuture = serverBootstrap.bind(port).sync();
        } catch (Exception e) {
            logger.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    /**
     * �������ر�Netty Websocket����������Ҫ���ͷ�����
     *     ���Ӱ���������������serverChannel��
     *     �ͻ���TCP��������bossGroup��
     *     �ͻ���I/O��������workerGroup
     *
     *     ��ֻʹ��
     *         bossGroupFuture = bossGroup.shutdownGracefully();
     *         workerGroupFuture = workerGroup.shutdownGracefully();
     *     ������ڴ�й©��
     */
    public void close(){
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();
        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelHandler getChildChannelHandler() {
        return childChannelHandler;
    }

    public void setChildChannelHandler(ChannelHandler childChannelHandler) {
        this.childChannelHandler = childChannelHandler;
    }
}
