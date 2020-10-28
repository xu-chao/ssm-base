package com.java.activiti.web.interceptor;

import com.java.activiti.util.web.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserAuthInteceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(UserAuthInteceptor.class);

    private static final String loginError = "���ص�һ��ֱ�ӷ��ʵ�����";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        //��ȡ�����RUi:ȥ��http:localhost:8080
//        String uri = httpServletRequest.getRequestURI();
//        //����/login�ǿ��Թ������ʵģ�������URL���������ؿ���
//        if (uri.indexOf("/login") >= 0) {
//            return true;
//        }else
            if (userToken == null) {//�����ж�һ��ȫ��������û�����Ƿ�Ϊ��
            logger.info(loginError);
            httpServletResponse.sendRedirect("login");//�ض��򵽵�¼����
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
