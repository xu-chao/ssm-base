package com.java.activiti.exception;

public class LoginRecordServiceException extends BusinessException {

    public LoginRecordServiceException(){
        super();
    }

    public LoginRecordServiceException(Exception e){
        super(e);
    }

    public LoginRecordServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

    public LoginRecordServiceException(String exceptionDesc){
        super(exceptionDesc);
    }
}
