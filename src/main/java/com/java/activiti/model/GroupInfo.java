package com.java.activiti.model;

import java.util.List;

public class GroupInfo {

    private Integer groupId;
    private String groupName;
    private String groupAvatarUrl;
    private List<Belong> members;
    private List<MessageInfo> messageList;
    public GroupInfo() {

    }

    public List<MessageInfo> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageInfo> messageList) {
        this.messageList = messageList;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }

    public List<Belong> getMembers() {
        return members;
    }

    public void setMembers(List<Belong> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return this.groupId + " " + this.groupName + " " + this.groupAvatarUrl;
    }
}
