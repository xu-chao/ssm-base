package com.java.activiti.exception;

public class SystemLogServiceException extends BusinessException {
    public SystemLogServiceException(){
        super();
    }

    public SystemLogServiceException(Exception e){
        super(e);
    }

    public SystemLogServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

    public SystemLogServiceException(String exceptionDesc){
        super(exceptionDesc);
    }
}
