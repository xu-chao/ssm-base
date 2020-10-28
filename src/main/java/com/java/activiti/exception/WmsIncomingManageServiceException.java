package com.java.activiti.exception;

public class WmsIncomingManageServiceException extends BusinessException {

    WmsIncomingManageServiceException(){
        super();
    }

    public WmsIncomingManageServiceException(Exception e){
        super(e);
    }

    WmsIncomingManageServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }
}
