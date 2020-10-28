package com.java.activiti.service.impl;

import com.java.activiti.dao.ContactDao;
import com.java.activiti.dao.UserChatDao;
import com.java.activiti.pojo.ResponseJson;
import com.java.activiti.pojo.UserChat;
import com.java.activiti.service.UserChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userChatService")
public class UserChatServiceImpl implements UserChatService {

    @Resource
    UserChatDao userChatDao;

    @Resource
    ContactDao contactDao;

    @Override
    public ResponseJson getByUserId(String userId) {
        UserChat userChat = userChatDao.getByUserId(userId);
        return new ResponseJson().success()
                .setData("userChat", userChat);
    }

    @Override
    public UserChat get(String i) {
        return userChatDao.get(i);
    }

    @Override
    public ResponseJson contact(String userId, String findName) {
        UserChat userChat = userChatDao.getByUsername(findName);
        if (userChat == null) {
            return new ResponseJson().error("不存在该用户名,请检查");
        }
        contactDao.join(userId, userChat.getUserId());
        contactDao.join(userChat.getUserId(), userId);
        return new ResponseJson().success().setMsg("用户" + userId + "添加用户" + userChat.getUserId() + "好友成功！");
    }
}
