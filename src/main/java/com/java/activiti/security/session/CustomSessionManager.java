package com.java.activiti.security.session;

import com.java.activiti.model.User;
import com.java.activiti.pojo.UserOnlineVO;
import com.java.activiti.security.CustomShiroSessionDAO;
import com.java.activiti.util.LoggerUtils;
import com.java.activiti.util.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import javax.annotation.Resource;
import java.util.*;

public class CustomSessionManager {

    /**
     * session status
     */
    public static final String SESSION_STATUS ="activiti-online-status";

    @Resource
    ShiroSessionRepository shiroSessionRepository;

    @Resource
    CustomShiroSessionDAO customShiroSessionDAO;

    /**
     * ��ȡ���е���ЧSession�û�
     * @return
     */
    public List<UserOnlineVO> getAllUser() {
        //��ȡ����session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        List<UserOnlineVO> list = new ArrayList<UserOnlineVO>();

        for (Session session : sessions) {
            UserOnlineVO bo = getSessionBo(session);
            if(null != bo){
                list.add(bo);
            }
        }
        return list;
    }
    /**
     * ����ID��ѯ SimplePrincipalCollection
     * @param userIds	�û�ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Long...userIds){
        //��userIds ת��Set�����ж�
        Set<Long> idset = (Set<Long>) StringUtils.array2Set(userIds);
        //��ȡ����session
        Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
        //���巵��
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
        for (Session session : sessions) {
            //��ȡSimplePrincipalCollection
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(null != obj && obj instanceof SimplePrincipalCollection){
                //ǿת
                SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
                //�ж��û���ƥ���û�ID��
                obj = spc.getPrimaryPrincipal();
                if(null != obj && obj instanceof User){
                    User user = (User)obj;
                    //�Ƚ��û�ID�����ϼ����뼯��
                    if(null != user && idset.contains(user.getId())){
                        list.add(spc);
                    }
                }
            }
        }
        return list;
    }



    /**
     * ��ȡ����Session
     * @param sessionId
     * @return
     */
    public UserOnlineVO getSession(String sessionId) {
        Session session = shiroSessionRepository.getSession(sessionId);
        UserOnlineVO bo = getSessionBo(session);
        return bo;
    }
    private UserOnlineVO getSessionBo(Session session){
        //��ȡsession��¼��Ϣ��
        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if(null == obj){
            return null;
        }
        //ȷ���� SimplePrincipalCollection����
        if(obj instanceof SimplePrincipalCollection){
            SimplePrincipalCollection spc = (SimplePrincipalCollection)obj;
            /**
             * ��ȡ�û���¼�ģ�@link SampleRealm.doGetAuthenticationInfo(...)������
             * return new SimpleAuthenticationInfo(user,user.getPswd(), getName());��user ����
             */
            obj = spc.getPrimaryPrincipal();
            if(null != obj && obj instanceof User){
                //�洢session + user �ۺ���Ϣ
                UserOnlineVO userBo = new UserOnlineVO((User)obj);
                //���һ�κ�ϵͳ������ʱ��
                userBo.setLastAccess(session.getLastAccessTime());
                //������ip��ַ
                userBo.setHost(session.getHost());
                //session ID
                userBo.setSessionId(session.getId().toString());
                //session���һ����ϵͳ������ʱ��
                userBo.setLastLoginTime(session.getLastAccessTime());
                //�ػ����� ttl(ms)
                userBo.setTimeout(session.getTimeout());
                //session����ʱ��
                userBo.setStartTime(session.getStartTimestamp());
                //�Ƿ��߳�
                SessionStatus sessionStatus = (SessionStatus)session.getAttribute(SESSION_STATUS);
                boolean status = Boolean.TRUE;
                if(null != sessionStatus){
                    status = sessionStatus.getOnlineStatus();
                }
                userBo.setSessionStatus(status);
                return userBo;
            }
        }
        return null;
    }
    /**
     * �ı�Session״̬
     * @param status {true:�߳�,false:����}
     * @param sessionIds
     * @return
     */
    public Map<String, Object> changeSessionStatus(Boolean status,
                                                   String sessionIds) {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            String[] sessionIdArray = null;
            if(sessionIds.indexOf(",") ==-1){
                sessionIdArray = new String[]{sessionIds};
            }else{
                sessionIdArray = sessionIds.split(",");
            }
            for (String id : sessionIdArray) {
                Session session = shiroSessionRepository.getSession(id);
                SessionStatus sessionStatus = new SessionStatus();
                sessionStatus.setOnlineStatus(status);
                session.setAttribute(SESSION_STATUS, sessionStatus);
                customShiroSessionDAO.update(session);
            }
            map.put("status", 200);
            map.put("sessionStatus", status?1:0);
            map.put("sessionStatusText", status?"�߳�":"����");
            map.put("sessionStatusTextTd", status?"��Ч":"���߳�");
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "�ı�Session״̬����sessionId[%s]", sessionIds);
            map.put("status", 500);
            map.put("message", "�ı�ʧ�ܣ��п���Session�����ڣ���ˢ�����ԣ�");
        }
        return map;
    }
    /**
     * ��ѯҪ���õ��û��Ƿ����ߡ�
     * @param id		�û�ID
     * @param status	�û�״̬
     */
    public void forbidUserById(String id, Long status) {
        //��ȡ���������û�
        for(UserOnlineVO bo : getAllUser()){
            String userId = bo.getId();
            //ƥ���û�ID
            if(userId.equals(id)){
                //��ȡ�û�Session
                Session session = shiroSessionRepository.getSession(bo.getSessionId());
                //����û�Session
                SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
                //�Ƿ��߳� true:��Ч��false���߳���
                sessionStatus.setOnlineStatus(status.intValue() == 1);
                //����Session
                customShiroSessionDAO.update(session);
            }
        }
    }
    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
        this.customShiroSessionDAO = customShiroSessionDAO;
    }
}
