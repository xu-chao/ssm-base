package com.java.activiti.security.filter;

import com.java.activiti.model.MemberShip;
import com.java.activiti.security.cache.VCache;
import com.java.activiti.security.session.ShiroSessionRepository;
import com.java.activiti.util.LoggerUtils;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings({"unchecked","static-access"})
public class KickoutSessionFilter extends AccessControlFilter {

    //��̬ע��
    static String kickoutUrl;
    //�����û�
    final static String ONLINE_USER = KickoutSessionFilter.class.getCanonicalName()+ "_online_user";
    //�߳�״̬��true��ʾ�߳�
    final static String KICKOUT_STATUS = KickoutSessionFilter.class.getCanonicalName()+ "_kickout_status";
    static VCache cache;

    //session��ȡ
    static ShiroSessionRepository shiroSessionRepository;


    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest httpRequest = ((HttpServletRequest)request);
        String url = httpRequest.getRequestURI();
        Subject subject = getSubject(request, response);
        //��������Ŀ¼ or ���û�е�¼ ��ֱ��return true
        if(url.startsWith("/open/") || (!subject.isAuthenticated() && !subject.isRemembered())){
            return Boolean.TRUE;
        }
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        /**
         * �ж��Ƿ��Ѿ��߳�
         * 1.�����Ajax ���ʣ���ô����json����ֵ��ʾ��
         * 2.�������ͨ����ֱ����ת����¼ҳ
         */
        Boolean marker = (Boolean)session.getAttribute(KICKOUT_STATUS);
        if (null != marker && marker ) {
            Map<String, String> resultMap = new HashMap<String, String>();
            //�ж��ǲ���Ajax����
            if (ShiroFilterUtils.isAjax(request) ) {
                LoggerUtils.debug(getClass(), "��ǰ�û��Ѿ��������ط���¼��������Ajax����");
                resultMap.put("user_status", "300");
                resultMap.put("message", "���Ѿ��������ط���¼�������µ�¼��");
                out(response, resultMap);
            }
            return  Boolean.FALSE;
        }


        //�ӻ����ȡ�û�-Session��Ϣ <UserId,SessionId>
        LinkedHashMap<String, Serializable> infoMap = cache.get(ONLINE_USER, LinkedHashMap.class);
        //��������ڣ�����һ���µ�
        infoMap = null == infoMap ? new LinkedHashMap<String, Serializable>() : infoMap;

        //��ȡtokenId
        //��ȡ��ǰ�û�ID
        //String userId = TokenManager.getUserId();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        String userId = sessionInfo.getUserId();

        //����Ѿ�������ǰSession��������ͬһ���û���������
        if(infoMap.containsKey(userId) && infoMap.containsValue(sessionId)){
            //���´洢������1��Сʱ�����ʱ����ú�session����Ч��һ�»��ߴ���session����Ч�ڣ�
            cache.setex(ONLINE_USER, infoMap, 3600);
            return Boolean.TRUE;
        }
        //����û���ͬ��Session����ͬ����ô��Ҫ������
        /**
         * ����û�Id��ͬ,Session����ͬ
         * 1.��ȡ��ԭ����session�����ұ��Ϊ�߳���
         * 2.������
         */
        if(infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)){
            Serializable oldSessionId = infoMap.get(userId);
            Session oldSession = shiroSessionRepository.getSession(oldSessionId);
            if(null != oldSession){
                //���session�Ѿ��߳�
                oldSession.setAttribute(KICKOUT_STATUS, Boolean.TRUE);
                shiroSessionRepository.saveSession(oldSession);//����session
                LoggerUtils.fmtDebug(getClass(), "kickout old session success,oldId[%s]",oldSessionId);
            }else{
                shiroSessionRepository.deleteSession(oldSessionId);
                infoMap.remove(userId);
                //�洢������1��Сʱ�����ʱ����ú�session����Ч��һ�»��ߴ���session����Ч�ڣ�
                cache.setex(ONLINE_USER, infoMap, 3600);
            }
            return  Boolean.TRUE;
        }

        if(!infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)){
            infoMap.put(userId, sessionId);
            //�洢������1��Сʱ�����ʱ����ú�session����Ч��һ�»��ߴ���session����Ч�ڣ�
            cache.setex(ONLINE_USER, infoMap, 3600);
        }
        return Boolean.TRUE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        //���˳�
        Subject subject = getSubject(request, response);
        subject.logout();
        WebUtils.getSavedRequest(request);
        //���ض���
        WebUtils.issueRedirect(request, response,kickoutUrl);
        return false;
    }

    private void out(ServletResponse hresponse, Map<String, String> resultMap)
            throws IOException {
        try {
            hresponse.setCharacterEncoding("UTF-8");
            PrintWriter out = hresponse.getWriter();
            out.println(JSONObject.fromObject(resultMap).toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "KickoutSessionFilter.class ���JSON�쳣�����Ժ��ԡ�");
        }
    }

    public static void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        KickoutSessionFilter.shiroSessionRepository = shiroSessionRepository;
    }

    public static String getKickoutUrl() {
        return kickoutUrl;
    }

    public void setKickoutUrl(String kickoutUrl) {
        KickoutSessionFilter.kickoutUrl = kickoutUrl;
    }
}
