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
        LOGGER.info(MessageFormat.format("userIdΪ {0} ���û��Ǽǵ������û�����ǰ��������Ϊ��{1}"
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
                    .error(MessageFormat.format("userIdΪ {0} ���û�û�е�¼��", toUserId))
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

        groupInfo.getMembers().stream()//ʹ��JDK1.8 lambda ���﷨
                .forEach(member -> {
                    ChannelHandlerContext toCtx = Constant.onlineUserMap.get(String.valueOf(member.getUserChat().getUserId()));//userID��long���͵�Ӧ��ת��ΪString
                    if (toCtx != null && member.getUserChat().getUserId().equals(fromuserId)) {//�����ڷ����߾�ת��
                        //System.out.println("ת��");
                        LOGGER.info(groupInfo.getGroupName() + "Ⱥ����Ϣ:" + String.valueOf(fromuserId) + "--->" + String.valueOf(member.getUserChat().getUserId()));
                        sendMessage(toCtx, responseJson);
                    }
                });
        /*List<Belong> Members = groupInfo.getMembers();
            for (Belong member : Members) {
            ChannelHandlerContext toCtx = Constant.onlineUserMap.get(String.valueOf(member.getUserInfo().getUserId()));//userID��long���͵�Ӧ��ת��ΪString
            if (toCtx != null && member.getUserInfo().getUserId() != (fromuserId)) {//�����뷢���߾�ת��
                //System.out.println("ת��");
                LOGGER.info(groupInfo.getGroupName() + "Ⱥ����Ϣ:" + String.valueOf(fromuserId) + "--->" + String.valueOf(member.getUserInfo().getUserId()));
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
                    .error(MessageFormat.format("userIdΪ {0} ���û�û�е�¼��", toUserId))
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
                LOGGER.info("�����Ƴ�����ʵ��...");
                Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                LOGGER.info(MessageFormat.format("���Ƴ�����ʵ������ǰ����ʵ������Ϊ��{0}"
                        , Constant.webSocketHandshakerMap.size()));
                iterator.remove();
                LOGGER.info(MessageFormat.format("userIdΪ {0} ���û����˳����죬��ǰ��������Ϊ��{1}"
                        , entry.getKey(), Constant.onlineUserMap.size()));
                break;
            }
        }
    }

    @Override
    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = new ResponseJson()
                .error("�����Ͳ����ڣ�")
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
