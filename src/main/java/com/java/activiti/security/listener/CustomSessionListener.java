package com.java.activiti.security.listener;

import com.java.activiti.security.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class CustomSessionListener implements SessionListener {

    private ShiroSessionRepository shiroSessionRepository;

    /**
     * һ���ػ����������ڿ�ʼ
     */
    @Override
    public void onStart(Session session) {
        //TODO
        System.out.println("on start");
    }
    /**
     * һ���ػ����������ڽ���
     */
    @Override
    public void onStop(Session session) {
        //TODO
        System.out.println("on stop");
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
}
