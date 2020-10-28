package com.java.activiti.service;

import com.alibaba.fastjson.JSONObject;
import com.java.activiti.model.GroupInfo;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface ChatService {

    public void login(JSONObject param, ChannelHandlerContext ctx);

    public void singleSend(JSONObject param, ChannelHandlerContext ctx);

    public void groupSend(JSONObject param, ChannelHandlerContext ctx);

    public void FileMsgSingleSend(JSONObject param, ChannelHandlerContext ctx);

    public void FileMsgGroupSend(JSONObject param, ChannelHandlerContext ctx);

    public void remove(ChannelHandlerContext ctx);

    public void typeError(ChannelHandlerContext ctx);

    List<GroupInfo> listAll();
}
