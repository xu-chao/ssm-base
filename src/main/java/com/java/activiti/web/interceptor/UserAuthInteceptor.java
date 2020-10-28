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

    private static final String loginError = "拦截到一个直接访问的请求";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        //获取请求的RUi:去除http:localhost:8080
//        String uri = httpServletRequest.getRequestURI();
//        //除了/login是可以公开访问的，其他的URL都进行拦截控制
//        if (uri.indexOf("/login") >= 0) {
//            return true;
//        }else
            if (userToken == null) {//这里判断一个全局类里的用户标记是否为空
            logger.info(loginError);
            httpServletResponse.sendRedirect("login");//重定向到登录界面
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
