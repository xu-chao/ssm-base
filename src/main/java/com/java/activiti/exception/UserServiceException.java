package com.java.activiti.exception;

public class UserServiceException extends BusinessException {

    public static final String PASSWORD_ERROR = "passwordError";
    public static final String PASSWORD_UNMATCH = "passwordUnmatched";

    public UserServiceException() {
        super();
    }

    public UserServiceException(String exceptionDesc) {
        super(null, exceptionDesc);
    }

    UserServiceException(Exception e) {
        super(e);
    }

    UserServiceException(Exception e, String exceptionDesc) {
        super(e, exceptionDesc);
    }
}
