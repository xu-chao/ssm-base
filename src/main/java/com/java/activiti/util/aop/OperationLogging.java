package com.java.activiti.util.aop;

import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.MemberShip;
import com.java.activiti.service.OperationRecordService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;

import javax.annotation.Resource;

public class OperationLogging {

    @Resource
    OperationRecordService operationRecordService;

    /**
     * 记录用户操作
     *
     * @param joinPoint 切入点信息
     */
    public void loggingUserOperation(JoinPoint joinPoint, Object returnValue, Operation operation){
        // 获取用户ID信息
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        String userID = sessionInfo.getUserId();
        String userName = sessionInfo.getUser().getFirstName() + sessionInfo.getUser().getLastName();

        if (operation != null) {
            // 获取 annotation 的值
            String operationValue = operation.value();

            // 获取调用的方法名
            String methodName = joinPoint.getSignature().getName();

            // 获取除 import* export* 外的方法的返回值
            String invokedResult = "-";
            if (!methodName.matches("^import\\w*") && !methodName.matches("^export\\w*")){
                if (returnValue instanceof Boolean) {
                    boolean result = (boolean) returnValue;
                    invokedResult = result ? "成功" : "失败";
                }else if((int)returnValue == 1){
                    invokedResult = "成功";
                }else if((int)returnValue == 0){
                    invokedResult = "失败";
                }else if((int)returnValue >= 0){
                    invokedResult = "批量操作成功";
                }else {
                    invokedResult = "异常";
                }
            }

            try {
                operationRecordService.insertOperationRecord(userID,userName,operationValue,invokedResult);
            } catch (SystemLogServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
