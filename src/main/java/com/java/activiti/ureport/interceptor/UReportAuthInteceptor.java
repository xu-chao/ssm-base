package com.java.activiti.ureport.interceptor;/**
 * @ProjectName: OMMS
 * @Package: com.java.activiti.ureport.interceptor
 * @ClassName: UReportAuthInteceptor
 * @Author: xuchao
 * @Description: UReport������
 * @Date: 2019/12/29 23:45
 * @Version: 1.0
 */

import com.java.activiti.util.UserUtil;
import com.java.activiti.util.web.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author xuchao
 * @version 1.0
 * @description
 * @updateRemark
 * @updateUser
 * @createDate 2019/12/29 23:45
 * @updateDate 2019/12/29 23:45
 **/
public class UReportAuthInteceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(UReportAuthInteceptor.class);

    private static final String UREPORT = "���ص�һ��ֱ�ӷ���UReport������";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {//�����ж�һ��ȫ��������û�����Ƿ�Ϊ��
            logger.info(UREPORT);
            httpServletResponse.sendRedirect("login");//�ض��򵽵�¼����
            return false;
        } else if (!UserUtil.getSubjectUserID().equals("1001")) {
            logger.info(UREPORT);
            httpServletResponse.sendRedirect("login");//�ض��򵽵�¼����
            return false;
        } else {
            return true;
        }
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
