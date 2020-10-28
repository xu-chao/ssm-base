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
     * ��¼�û�����
     *
     * @param joinPoint �������Ϣ
     */
    public void loggingUserOperation(JoinPoint joinPoint, Object returnValue, Operation operation){
        // ��ȡ�û�ID��Ϣ
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        String userID = sessionInfo.getUserId();
        String userName = sessionInfo.getUser().getFirstName() + sessionInfo.getUser().getLastName();

        if (operation != null) {
            // ��ȡ annotation ��ֵ
            String operationValue = operation.value();

            // ��ȡ���õķ�����
            String methodName = joinPoint.getSignature().getName();

            // ��ȡ�� import* export* ��ķ����ķ���ֵ
            String invokedResult = "-";
            if (!methodName.matches("^import\\w*") && !methodName.matches("^export\\w*")){
                if (returnValue instanceof Boolean) {
                    boolean result = (boolean) returnValue;
                    invokedResult = result ? "�ɹ�" : "ʧ��";
                }else if((int)returnValue == 1){
                    invokedResult = "�ɹ�";
                }else if((int)returnValue == 0){
                    invokedResult = "ʧ��";
                }else if((int)returnValue >= 0){
                    invokedResult = "���������ɹ�";
                }else {
                    invokedResult = "�쳣";
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
