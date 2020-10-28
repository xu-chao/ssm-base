package com.java.activiti.exception;

/**
 * UserInfoService“Ï≥£
 *
 * @author Xu
 * @since 2019/7/13.
 */
public class UserInfoServiceException extends BusinessException {

    public UserInfoServiceException(){
        super();
    }

    public UserInfoServiceException(Exception e){
        super(e);
    }

    public UserInfoServiceException(Exception e, String exceptionDesc){
        super(e, exceptionDesc);
    }

    public UserInfoServiceException(String exceptionDesc){
        super(exceptionDesc);
    }

}
