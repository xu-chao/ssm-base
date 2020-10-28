package com.java.activiti.util;

import com.java.activiti.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ȫ���쳣������
 *
 * @author Xu
 * @since 2019/7/11.
 */
//@ControllerAdvice
public class GlobalExceptionUtil {

    private static Logger logger = LoggerFactory.getLogger("ExceptionLogging");

    /**
     * ���񲢼�¼Controller���׳��ķ�BusinessException�쳣
     */
//    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e){
        if (!(e instanceof BusinessException)){
            if (logger.isErrorEnabled()){
                StringBuilder builder = new StringBuilder();
                builder.append("cause:").append(e.getMessage());
                builder.append("\n\tstackTrack:\n");
                for (StackTraceElement stack : e.getStackTrace()) {
                    builder.append("\t\t");
                    builder.append(stack.toString());
                    builder.append("\n");
                }
                logger.error(builder.toString());
            }
        }
    }
}
