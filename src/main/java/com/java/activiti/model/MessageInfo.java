package com.java.activiti.model;

import com.java.activiti.util.web.ChatType;

public class MessageInfo {

    private int id;
    private String fromUserId;
    private String toUserId;
    private Integer toGroupId;
    private String content;
    private String type;
    private String fileUrl;
    private String originalFilename;
    private String fileSize;

    public MessageInfo() {
    }
    /*
        消息发送构造函数
     */
    public MessageInfo(String fromUserId, String to, String content, ChatType type) {
        this.fromUserId=fromUserId;
        this.content=content;
        if(type== ChatType.SINGLE_SENDING) {
            this.toUserId=to;
        }else{
            this.toGroupId=Integer.valueOf(to);
        }
        this.type=type.toString();
    }

    public MessageInfo(String fromUserId, String to, String originalFilename, String fileSize, String fileUrl, ChatType type) {
        this.fromUserId=fromUserId;
        this.originalFilename=originalFilename;
        this.fileSize=fileSize;
        this.fileUrl=fileUrl;
        if(type==ChatType.FILE_MSG_SINGLE_SENDING) {
            this.toUserId=to;
        }else{
            this.toGroupId=Integer.valueOf(to);
        }
        this.type=type.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getToGroupId() {
        return toGroupId;
    }

    public void setToGroupId(Integer toGroupId) {
        this.toGroupId = toGroupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    @Override
    public String toString(){
        String s=toGroupId==null? toUserId.toString():toGroupId.toString();
        return "\n"+
                fromUserId.toString()+"\n" +
                s+"\n" +
                content+"\n" +
                type;
    }
}
