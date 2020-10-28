package com.java.activiti.security.listener;

import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.MemberShip;
import com.java.activiti.service.AccessRecordService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 用户Session监听器
 * 当用户session注销时，记录用户账户登出的时间
 *
 * @author Xu
 * @since 2019/8/11.
 */
@Component
public class AccountSessionListener implements HttpSessionListener, ApplicationContextAware {
    @Resource
    private AccessRecordService accessRecordService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // 当session被创建时
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 获取用户的session
        HttpSession session = se.getSession();

        // 判断是否为已经登陆的用户
        // 判断依据是isAuthentication的值
        try {
            String isAuthenticate = (String) session.getAttribute("isAuthenticate");
            if (isAuthenticate != null && isAuthenticate.equals("true")) {
                MemberShip sessionInfo= (MemberShip) session.getAttribute("sessionInfo");
                String userID = sessionInfo.getUserId();
                String userName = sessionInfo.getUser().getFirstName() + sessionInfo.getUser().getLastName();
                String accessIP = (String) session.getAttribute("org.apache.shiro.web.session.HttpServletSession.HOST_SESSION_KEY");
                accessRecordService.insertAccessRecord(userID, userName, accessIP, accessRecordService.ACCESS_TYPE_LOGOUT);
            }
        } catch (SystemLogServiceException e) {
            // do log
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof WebApplicationContext){
            ((WebApplicationContext)applicationContext).getServletContext().addListener(this);
        }else{
            throw new RuntimeException();
        }
    }
}
