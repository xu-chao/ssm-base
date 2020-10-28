package com.java.activiti.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.activiti.dao.GroupInfoDao;
import com.java.activiti.dao.MessageInfoDao;
import com.java.activiti.dao.UserDao;
import com.java.activiti.model.GroupInfo;
import com.java.activiti.model.MessageInfo;
import com.java.activiti.pojo.ResponseJson;
import com.java.activiti.service.ChatService;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.web.ChatType;
import com.java.activiti.util.web.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Resource
    private GroupInfoDao groupInfoDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageInfoDao messageInfoDao;

    @Override
    public void login(JSONObject param, ChannelHandlerContext ctx) {
//         String userId = UserUtil.getSubjectUserID();
//          String userId = "1001";
          String userId = param.get("userId").toString();
//          if(userId){
//              userId = UserUtil.getSubjectUserID();
//          }
        Constant.onlineUserMap.put(userId, ctx);
        String responseJson = new ResponseJson().success()
                .setData("type", ChatType.LOGIN)
                .toString();
        sendMessage(ctx, responseJson);
        LOGGER.info(MessageFormat.format("userId为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
                , userId, Constant.onlineUserMap.size()));
    }

    @Override
    public void singleSend(JSONObject param, ChannelHandlerContext ctx) {

        String fromUserId = param.get("fromUserId").toString();
        String toUserId = param.get("toUserId").toString();
        String content = param.get("content").toString();
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);
        if (toUserCtx == null) {
            String responseJson = new ResponseJson()
                    .error(MessageFormat.format("userId为 {0} 的用户没有登录！", toUserId))
                    .toString();
            sendMessage(ctx, responseJson);
        } else {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("type", ChatType.SINGLE_SENDING)
                    .toString();
            MessageInfo messageInfo=new MessageInfo(fromUserId,toUserId,content,ChatType.SINGLE_SENDING);
            messageInfoDao.insert(messageInfo);
            sendMessage(toUserCtx, responseJson);
        }
    }

    @Override
    public void groupSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = param.get("fromUserId").toString();
        String toGroupId = (String) param.get("toGroupId").toString();
        String content = (String) param.get("content");
        Integer togrouId = Integer.parseInt(toGroupId);
        Integer fromuserId = Integer.parseInt(fromUserId);
        GroupInfo groupInfo = groupInfoDao.getByGroupId(togrouId);
        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("content", content)
                .setData("toGroupId", toGroupId)
                .setData("type", ChatType.GROUP_SENDING)
                .toString();
        MessageInfo messageInfo=new MessageInfo(fromUserId,toGroupId,content,ChatType.GROUP_SENDING);
        messageInfoDao.insert(messageInfo);

        groupInfo.getMembers().stream()//使用JDK1.8 lambda 新语法
                .forEach(member -> {
                    ChannelHandlerContext toCtx = Constant.onlineUserMap.get(String.valueOf(member.getUserChat().getUserId()));//userID是long类型的应该转化为String
                    if (toCtx != null && member.getUserChat().getUserId().equals(fromuserId)) {//不等于发送者就转发
                        //System.out.println("转发");
                        LOGGER.info(groupInfo.getGroupName() + "群组消息:" + String.valueOf(fromuserId) + "--->" + String.valueOf(member.getUserChat().getUserId()));
                        sendMessage(toCtx, responseJson);
                    }
                });
        /*List<Belong> Members = groupInfo.getMembers();
            for (Belong member : Members) {
            ChannelHandlerContext toCtx = Constant.onlineUserMap.get(String.valueOf(member.getUserInfo().getUserId()));//userID是long类型的应该转化为String
            if (toCtx != null && member.getUserInfo().getUserId() != (fromuserId)) {//不等与发送者就转发
                //System.out.println("转发");
                LOGGER.info(groupInfo.getGroupName() + "群组消息:" + String.valueOf(fromuserId) + "--->" + String.valueOf(member.getUserInfo().getUserId()));
                sendMessage(toCtx, responseJson);

            }
        }*/
    }

    @Override
    public void FileMsgSingleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = param.get("fromUserId").toString();
        String toUserId = param.get("toUserId").toString();
        String originalFilename = (String) param.get("originalFilename");
        String fileSize = (String) param.get("fileSize");
        String fileUrl = (String) param.get("fileUrl");
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);
        if (toUserCtx == null) {
            String responseJson = new ResponseJson()
                    .error(MessageFormat.format("userId为 {0} 的用户没有登录！", toUserId))
                    .toString();
            sendMessage(ctx, responseJson);
        } else {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("originalFilename", originalFilename)
                    .setData("fileSize", fileSize)
                    .setData("fileUrl", fileUrl)
                    .setData("type", ChatType.FILE_MSG_SINGLE_SENDING)
                    .toString();
            MessageInfo messageInfo=new MessageInfo(fromUserId,toUserId,originalFilename,fileSize,fileUrl,ChatType.FILE_MSG_SINGLE_SENDING);
            messageInfoDao.insert(messageInfo);
            sendMessage(toUserCtx, responseJson);
        }
    }

    @Override
    public void FileMsgGroupSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = param.get("fromUserId").toString();
        String toGroupId = param.get("toGroupId").toString();
        String originalFilename = (String) param.get("originalFilename");
        String fileSize = (String) param.get("fileSize");
        String fileUrl = (String) param.get("fileUrl");
        GroupInfo groupInfo = groupInfoDao.getByGroupId(Integer.valueOf(toGroupId));
        String responseJson = new ResponseJson().success()
                .setData("fromUserId", fromUserId)
                .setData("toGroupId", toGroupId)
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", fileUrl)
                .setData("type", ChatType.FILE_MSG_GROUP_SENDING)
                .toString();
        MessageInfo messageInfo=new MessageInfo(fromUserId,toGroupId,originalFilename,fileSize,fileUrl,ChatType.FILE_MSG_GROUP_SENDING);
        messageInfoDao.insert(messageInfo);
        groupInfo.getMembers().stream()
                .forEach(member -> {
                    ChannelHandlerContext toCtx = Constant.onlineUserMap.get(String.valueOf(member.getUserChat().getUserId()));
                    if (toCtx != null && member.getUserChat().getUserId().equals(fromUserId)) {
                        sendMessage(toCtx, responseJson);
                    }
                });

    }

    @Override
    public void remove(ChannelHandlerContext ctx) {
        Iterator<Map.Entry<String, ChannelHandlerContext>> iterator =
                Constant.onlineUserMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                LOGGER.info("正在移除握手实例...");
                Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                LOGGER.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                        , Constant.webSocketHandshakerMap.size()));
                iterator.remove();
                LOGGER.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                        , entry.getKey(), Constant.onlineUserMap.size()));
                break;
            }
        }
    }

    @Override
    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = new ResponseJson()
                .error("该类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }

    private void sendMessage(ChannelHandlerContext ctx, String message) {

        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    @Override
    public List<GroupInfo> listAll() {
        return groupInfoDao.listAll();
    }
}
