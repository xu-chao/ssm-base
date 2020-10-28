package com.java.activiti.web.controller;

import com.java.activiti.pojo.ResponseJson;
import com.java.activiti.security.service.UserInfoService;
import com.java.activiti.service.ChatService;
import com.java.activiti.service.UserChatService;
import com.java.activiti.util.UserUtil;
import com.java.activiti.util.web.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
public class ChatroomController {

    Logger logger = LoggerFactory.getLogger(ChatroomController.class);
    @Resource
    UserChatService userChatService;

    @Resource
    ChatService chatService;

    /**
     * 描述：登录成功后，调用此接口进行页面跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String toChatroom() {
        logger.info("直接进入聊天室");
        return "chatroom/chatroom";
    }

    /**
     * 描述：登录成功跳转页面后，调用此接口获取用户信息
     * @param
     * @return
     */
    @RequestMapping(value = "/get_userinfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson getUserInfo(HttpSession session) {
        String userId = UserUtil.getSubjectUserID();
        logger.info("输出userID: " + userId);
        //ResponseJson json = userChatService.getByUserId(userId);
        return userChatService.getByUserId(userId);
    }

    @RequestMapping(value = "/add_friend", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson addfriend(HttpSession session, @RequestParam String findName) {
        String userId = (String) session.getAttribute(Constant.USER_TOKEN);
        //ResponseJson json = userChatService.getByUserId(userId);
        logger.info("接收添加好友请求");
        System.out.println(userId);
        return userChatService.contact(userId, findName);
    }
}
