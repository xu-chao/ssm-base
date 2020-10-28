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
 * Netty WebSocket服务器
 * 使用独立的线程启动
 */
public class WebSocketServer implements Runnable {

    private final Logger logger= LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * EventLoopGroup 实现于Executor顶级的线程池接口
     * 因此bossGroup 和 workerGroup 是 Bootstrap 构造
     * 方法中传入的两个对象，这两个 group 均是线程池：
     */
    //bossGroup 线程池则只是在 Bind 某个端口后，获得其中一个线程作为 MainReactor，
    // 专门处理端口的 Accept 事件，每个端口对应一个 Boss 线程、(接待线程池)
    @Autowired
    private EventLoopGroup bossGroup;
    //workerGroup 线程池会被各个 SubReactor 和 Worker 线程充分利用、(服务线程池)
    @Autowired
    private EventLoopGroup workerGroup;
    @Autowired
    //Bootstrap 意思是引导，一个 Netty 应用通常由一个 Bootstrap 开始，
    // 主要作用是配置整个 Netty 程序，串联各个组件，Netty 中 Bootstrap 类是客户端程序的启动引导类，ServerBootstrap 是服务端启动引导类。
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
            serverBootstrap.group(bossGroup, workerGroup) //boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
                    .channel(NioServerSocketChannel.class) //配置客户端的channel类型
                    .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.TCP_NODELAY, true) //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
                    .childHandler(childChannelHandler); //绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
            long end = System.currentTimeMillis();
            logger.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");
            serverChannelFuture = serverBootstrap.bind(port).sync();
        } catch (Exception e) {
            logger.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    /**
     * 描述：关闭Netty Websocket服务器，主要是释放连接
     *     连接包括：服务器连接serverChannel，
     *     客户端TCP处理连接bossGroup，
     *     客户端I/O操作连接workerGroup
     *
     *     若只使用
     *         bossGroupFuture = bossGroup.shutdownGracefully();
     *         workerGroupFuture = workerGroup.shutdownGracefully();
     *     会造成内存泄漏。
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
