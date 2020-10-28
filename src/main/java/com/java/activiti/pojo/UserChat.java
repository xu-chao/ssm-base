package com.java.activiti.pojo;

import com.java.activiti.model.Belong;
import com.java.activiti.model.Contact;
import com.java.activiti.model.MessageInfo;

import java.util.List;

public class UserChat {

    private String userId;
    private String userName;
    private String password;
    private String avatarUrl;
    private String email;
    private List<Contact> friendList;
    private List<Belong> groupList;
    private List<MessageInfo> messageList;

    public UserChat() {
    }

    public List<MessageInfo> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageInfo> messageList) {
        this.messageList = messageList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public UserInfo(int userId, String userName, String password, String avatarUrl) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }*/

    public UserChat(String username, String password, String avatarShow, String mail) {
        this.userName = username;
        this.password = password;
        this.avatarUrl = avatarShow;
        this.email=mail;
    }

    public UserChat(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserChat(String username, String password, String avatarShow) {
        this.userName = username;
        this.password = password;
        this.avatarUrl = avatarShow;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Contact> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Contact> friendList) {
        this.friendList = friendList;
    }

    public List<Belong> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Belong> groupList) {
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        return this.userId+" "+this.userName;
    }
    public String totoString(){
        return this.userId+"\n"
                +this.userName+"\n"
                +this.avatarUrl+"\n"
                +this.email+"\n";
    }
}
