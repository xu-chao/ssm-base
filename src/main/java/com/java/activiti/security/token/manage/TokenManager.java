package com.java.activiti.security.token.manage;

import com.java.activiti.model.User;
import com.java.activiti.security.realms.UserAuthorizingRealm;
import com.java.activiti.security.session.CustomSessionManager;
import com.java.activiti.security.token.ShiroToken;
import com.java.activiti.util.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.List;

public class TokenManager {

    //�û���¼����
    public static final UserAuthorizingRealm realm = SpringContextUtil.getBean("userAuthorizingRealm",UserAuthorizingRealm.class);
    //�û�session����
    public static final CustomSessionManager customSessionManager = SpringContextUtil.getBean("customSessionManager",CustomSessionManager.class);
    /**
     * ��ȡ��ǰ��¼���û�User����
     * @return
     */
    public static User getToken(){
        User token = (User) SecurityUtils.getSubject().getPrincipal();
        return token ;
    }



    /**
     * ��ȡ��ǰ�û���Session
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
    /**
     * ��ȡ��ǰ�û�NAME
     * @return
     */
    public static String getNickname(){
        return getToken().getFirstName() + getToken().getLastName();
    }
    /**
     * ��ȡ��ǰ�û�ID
     * @return
     */
    public static String getUserId(){
        return getToken()==null?null:getToken().getId();
    }
    /**
     * ��ֵ���뵽��ǰ��¼�û���Session��
     * @param key
     * @param value
     */
    public static void setVal2Session(Object key ,Object value){
        getSession().setAttribute(key, value);
    }
    /**
     * �ӵ�ǰ��¼�û���Session��ȡֵ
     * @param key
     * @return
     */
    public static Object getVal2Session(Object key){
        return getSession().getAttribute(key);
    }
    /**
     * ��ȡ��֤�룬��ȡһ�κ�ɾ��
     * @return
     */
    public static String getYZM(){
        String code = (String) getSession().getAttribute("CODE");
        getSession().removeAttribute("CODE");
        return code ;
    }


    /**
     * ��¼
     * @param user
     * @param rememberMe
     * @return
     */
    public static User login(User user,Boolean rememberMe){
        ShiroToken token = new ShiroToken(user.getId(), user.getPassword());
        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);
        return getToken();
    }


    /**
     * �ж��Ƿ��¼
     * @return
     */
    public static boolean isLogin() {
        return null != SecurityUtils.getSubject().getPrincipal();
    }
    /**
     * �˳���¼
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * ��յ�ǰ�û�Ȩ����Ϣ��
     * Ŀ�ģ�Ϊ�����ж�Ȩ�޵�ʱ���ٴλ��ٴ� <code>doGetAuthorizationInfo(...)  </code>������
     * ps��	��Ȼ������ֶ�����  <code> doGetAuthorizationInfo(...)  </code>������
     * 		����ֻ��˵��������߼������������Ȩ�ޣ�<code> doGetAuthorizationInfo(...)  </code>�ͻᱻ�ٴε��á�
     */
//    public static void clearNowUserAuth(){
//        /**
//         * ������Ҫ��ȡ��shrio.xml �����ļ��У���Realm��ʵ�������󡣲��ܵ��õ� Realm ����ķ�����
//         */
//        /**
//         * ��ȡ��ǰϵͳ��Realm��ʵ�������󣬷���һ��ͨ�� @link org.apache.shiro.web.mgt.DefaultWebSecurityManager ��������ʵ�������{Collection<Realm> getRealms()}������ȡ����
//         * ��ȡ����ʱ����һ�����ϡ�Collection<Realm>
//         RealmSecurityManager securityManager =
//         (RealmSecurityManager) SecurityUtils.getSecurityManager();
//         SampleRealm realm = (SampleRealm)securityManager.getRealms().iterator().next();
//         */
//        /**
//         * ��������ͨ��ApplicationContext ��Spring�������ȡʵ�л�����
//         */
//        realm.clearCachedAuthorizationInfo();
//        /**
//         * ��Ȼ���кܶ�ֱ�ӻ��߼�ӵķ������˴������ᡣ
//         */
//    }




    /**
     * ����UserIds 	���Ȩ����Ϣ��
     * @param userIds	�û�ID
     */
//    public static void clearUserAuthByUserId(Long...userIds){
//
//        if(null == userIds || userIds.length == 0)	return ;
//        List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);
//
//        for (SimplePrincipalCollection simplePrincipalCollection : result) {
//            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
//        }
//    }


//    /**
//     * ��������
//     * @param userIds
//     */
//    public static void clearUserAuthByUserId(List<Long> userIds) {
//        if(null == userIds || userIds.size() == 0){
//            return ;
//        }
//        clearUserAuthByUserId(userIds.toArray(new Long[0]));
//    }
}
