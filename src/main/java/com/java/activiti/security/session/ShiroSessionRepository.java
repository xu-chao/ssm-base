package com.java.activiti.security.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

public interface ShiroSessionRepository {

    /**
     * �洢Session
     * @param session
     */
    void saveSession(Session session);
    /**
     * ɾ��session
     * @param sessionId
     */
    void deleteSession(Serializable sessionId);
    /**
     * ��ȡsession
     * @param sessionId
     * @return
     */
    Session getSession(Serializable sessionId);
    /**
     * ��ȡ����sessoin
     * @return
     */
    Collection<Session> getAllSessions();
}
