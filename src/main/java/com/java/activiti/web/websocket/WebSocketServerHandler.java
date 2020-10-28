package com.java.activiti.web.websocket;

import com.alibaba.fastjson.JSONObject;
import com.java.activiti.pojo.ResponseJson;
import com.java.activiti.service.ChatService;
import com.java.activiti.util.web.Constant;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    private static final Logger logger= LoggerFactory.getLogger(WebSocketServerHandler.class);

    @Resource
    private ChatService chatService;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {
        handlerWebSocketFrame(channelHandlerContext, webSocketFrame);
    }

    /**
     * ����: ����WebSocketFrame
     * @param ctx
     * @param frame
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // �ر�����
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker =
                    Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null) {
                sendErrorMessage(ctx, "�����ڵĿͻ������ӣ�");
            } else {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        // ping����
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // ֻ֧���ı���ʽ����֧�ֶ�������Ϣ
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "��֧���ı�(Text)��ʽ����֧�ֶ�������Ϣ");
            return;
        }

        // �ͷ��˷��͹�������Ϣ
        String request = ((TextWebSocketFrame)frame).text();
        logger.info("������յ�����Ϣ��" + request);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON�ַ���ת������");
            e.printStackTrace();
        }
        if (param == null) {
            sendErrorMessage(ctx, "����Ϊ�գ�");
            return;
        }
        String type = (String) param.get("type");
        switch (type) {
            case "LOGIN"://�����ǵ�¼Netty������
                chatService.login(param, ctx);
                break;
            case "SINGLE_SENDING"://������Ϣ����
                chatService.singleSend(param, ctx);
                break;
            case "GROUP_SENDING"://Ⱥ����Ϣ����
                chatService.groupSend(param, ctx);
                break;
            case "FILE_MSG_SINGLE_SENDING"://�����ļ�����
                chatService.FileMsgSingleSend(param, ctx);
                break;
            case "FILE_MSG_GROUP_SENDING"://Ⱥ���ļ�����
                chatService.FileMsgGroupSend(param, ctx);
                break;
            default:
                chatService.typeError(ctx);
                break;
        }
    }

    /**
     * �������ͻ��˶Ͽ�����
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatService.remove(ctx);
    }

    /**
     * �쳣�����ر�channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) {
        String responseJson = new ResponseJson()
                .error(errorMsg)
                .toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }
}
