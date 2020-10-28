package com.java.activiti.model;

import com.java.activiti.pojo.UserChat;

public class Contact {

    private long id;
    private UserChat userChat1;
    private UserChat userChat2;

    public Contact() {
    }

    public Contact(UserChat userChat1, UserChat userChat2) {
        this.userChat1 = userChat1;
        this.userChat2 = userChat2;
    }

    public UserChat getUserChat1() {
        return userChat1;
    }

    public void setUserChat1(UserChat userChat1) {
        this.userChat1 = userChat1;
    }

    public UserChat getUserChat2() {
        return userChat2;
    }

    public void setUserChat2(UserChat userChat2) {
        this.userChat2 = userChat2;
    }
}
