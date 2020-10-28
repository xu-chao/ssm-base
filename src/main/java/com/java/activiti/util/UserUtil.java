package com.java.activiti.util;

import com.java.activiti.model.MemberShip;
import com.java.activiti.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * @ClassName:  MenuController
 * @Description:shiro用户管理
 * @author:     许超
 * @date:       2019年8月8日
 */
@Component
public class UserUtil {

    /**
     *
     * @Title: getSubjectUser
     * @Description: 获取shiro中登录的用户ID
     * @return
     */
    public static String getSubjectUserID() {
        // 获取用户ID信息
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        String userID = sessionInfo.getUserId();
        return userID;
    }

    /**
     *
     * @Title: getSubjectUser
     * @Description: 获取shiro中登录的用户
     * @return
     */
    public static User getSubjectUser() {
        // 获取用户ID信息
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        User user = sessionInfo.getUser();
        return user;
    }
    /**
     *
     * @Title: removeSubjectUser
     * @Description: 从shiro中移除登录的用户
     * @author: 许超
     */
    public static void removeSubjectUser() {
        SecurityUtils.getSubject().logout();
    }
}
