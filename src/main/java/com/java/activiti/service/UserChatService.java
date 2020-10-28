package com.java.activiti.service;

import com.java.activiti.pojo.ResponseJson;
import com.java.activiti.pojo.UserChat;

public interface UserChatService {

    ResponseJson getByUserId(String userId);

    UserChat get(String i);

    ResponseJson contact(String userId, String addUserId);
}
