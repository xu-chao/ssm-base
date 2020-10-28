package com.java.activiti.util;

import com.java.activiti.model.MemberShip;
import com.java.activiti.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * @ClassName:  MenuController
 * @Description:shiro�û�����
 * @author:     ��
 * @date:       2019��8��8��
 */
@Component
public class UserUtil {

    /**
     *
     * @Title: getSubjectUser
     * @Description: ��ȡshiro�е�¼���û�ID
     * @return
     */
    public static String getSubjectUserID() {
        // ��ȡ�û�ID��Ϣ
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        String userID = sessionInfo.getUserId();
        return userID;
    }

    /**
     *
     * @Title: getSubjectUser
     * @Description: ��ȡshiro�е�¼���û�
     * @return
     */
    public static User getSubjectUser() {
        // ��ȡ�û�ID��Ϣ
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        User user = sessionInfo.getUser();
        return user;
    }
    /**
     *
     * @Title: removeSubjectUser
     * @Description: ��shiro���Ƴ���¼���û�
     * @author: ��
     */
    public static void removeSubjectUser() {
        SecurityUtils.getSubject().logout();
    }
}
