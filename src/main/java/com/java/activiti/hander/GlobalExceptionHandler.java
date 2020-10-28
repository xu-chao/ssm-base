package com.java.activiti.hander;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.activiti.exception.BusinessException;
import com.java.activiti.model.ExceptionInfo;
import com.java.activiti.service.ExceptionLogService;
import com.java.activiti.util.IPUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 */


public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Resource
    private ExceptionLogService exceptionLogService;


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("error", ex);

        if (handler instanceof HandlerMethod) {
            LOGGER.info(">>>>>>系统异常，记录异常信息到数据库------start------");
            // 远程访问IP
            String ip = IPUtil.getIpAddress();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getName();
            String methodName = handlerMethod.getMethod().getName();
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));

            // 插入异常日志到数据库
            ExceptionInfo log = new ExceptionInfo();
            log.setIP(ip);
            log.setClassName(className);
            log.setMethodName(methodName);
            log.setExceptionType(ex.getClass().getSimpleName());
            log.setExceptionMsg(sw.toString()); // 异常详细信息
            log.setExceptionTime(new Date());
            this.exceptionLogService.addExceptionLog(log);
            LOGGER.info(">>>>>>系统异常，记录异常信息到数据库------end------");
        }

        String viewName = "error";
        if (ex instanceof UnauthorizedException) {  // 后台系统用的shiro做权限控制，该异常也是shiro的异常
            viewName = "error";
        }
        return new ModelAndView(viewName, model);
    }
}