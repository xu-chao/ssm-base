package com.java.activiti.util.web;

import com.java.activiti.model.GroupInfo;
import com.java.activiti.model.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ����: ȫ�ֳ���
 *      1. USER_TOKEN �û���֤�ļ�������ƥ��http session�еĶ�ӦuserId��
 *      2. webSocketServerHandshaker����channelIdΪ�����������ʵ����������ӦCloseWebSocketFrame������
 *      3. onlineUser����userIdΪ����������ߵĿͻ������������ģ�
 *      4. groupInfo����groupIdΪ�������Ⱥ��Ϣ��
 *      5. userInfo����usernameΪ��������û���Ϣ��
 */
public class Constant {

    public static final String USER_TOKEN="sessionInfo";

    public static Map<String, WebSocketServerHandshaker> webSocketHandshakerMap=
            new ConcurrentHashMap<String, WebSocketServerHandshaker>();

    public static Map<String, ChannelHandlerContext> onlineUserMap=
            new ConcurrentHashMap<String,ChannelHandlerContext>();

    public static Map<String, GroupInfo> groupInfoMap=
            new ConcurrentHashMap<String,GroupInfo>();

    public static Map<String, User> userInfoMap=
            new HashMap<String,User>();
}
