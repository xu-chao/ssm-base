package com.java.activiti.exception;

public class CityManageServiceException extends BusinessException {

    CityManageServiceException(){
        super();
    }

    public CityManageServiceException(Exception e){
        super(e);
    }

    CityManageServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }
}
