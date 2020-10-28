package com.java.activiti.model;

import com.java.activiti.pojo.UserChat;

public class Belong {

    private long id;
    private GroupInfo groupInfo;
    private UserChat userChat;

    public Belong() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public UserChat getUserChat() {
        return userChat;
    }

    public void setUserChat(UserChat userChat) {
        this.userChat = userChat;
    }
}
