package com.java.activiti.security.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ҳ���ض���
 *
 * @author XU
 * @since 2019/8/11.
 */
@RequestMapping("/")
@Controller
public class PageForwardController {

    /**
     * �ڲ��ض��򵽵�½ҳ��
     *
     * @return ��ת�� jsp
     */
    @RequestMapping("login")
    public String loginPageForward() {
        // �жϵ�ǰ�û��Ƿ��Ѿ���½
        Subject currentSubject = SecurityUtils.getSubject();
        if (!currentSubject.isAuthenticated())
            return "userLogin";
        else
            return "main";
    }

    /**
     * �ڲ��ض�����ҳ��
     *
     * @return ��ת�� jsp
     */
    @RequestMapping("main")
    public String showLoginView() {
        Subject currentSubject = SecurityUtils.getSubject();
        if (!currentSubject.isAuthenticated())
            return "userLogin";
        else
            return "main";
    }
}
