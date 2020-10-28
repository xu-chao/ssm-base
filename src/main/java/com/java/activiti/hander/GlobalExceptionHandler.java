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
 * ȫ���쳣������
 */


public class GlobalExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Resource
    private ExceptionLogService exceptionLogService;


    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("error", ex);

        if (handler instanceof HandlerMethod) {
            LOGGER.info(">>>>>>ϵͳ�쳣����¼�쳣��Ϣ�����ݿ�------start------");
            // Զ�̷���IP
            String ip = IPUtil.getIpAddress();
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getName();
            String methodName = handlerMethod.getMethod().getName();
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));

            // �����쳣��־�����ݿ�
            ExceptionInfo log = new ExceptionInfo();
            log.setIP(ip);
            log.setClassName(className);
            log.setMethodName(methodName);
            log.setExceptionType(ex.getClass().getSimpleName());
            log.setExceptionMsg(sw.toString()); // �쳣��ϸ��Ϣ
            log.setExceptionTime(new Date());
            this.exceptionLogService.addExceptionLog(log);
            LOGGER.info(">>>>>>ϵͳ�쳣����¼�쳣��Ϣ�����ݿ�------end------");
        }

        String viewName = "error";
        if (ex instanceof UnauthorizedException) {  // ��̨ϵͳ�õ�shiro��Ȩ�޿��ƣ����쳣Ҳ��shiro���쳣
            viewName = "error";
        }
        return new ModelAndView(viewName, model);
    }
}