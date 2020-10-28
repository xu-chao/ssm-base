package com.java.activiti.exception;

public class MailServiceException extends BusinessException {

    public static final Boolean SEND_ERROR = false;
    public static final Boolean SEND_SUCCESS = true;

    public MailServiceException() {
        super();
    }

    public MailServiceException(String exceptionDesc) {
        super(null, exceptionDesc);
    }

    MailServiceException(Exception e) {
        super(e);
    }

    MailServiceException(Exception e, String exceptionDesc) {
        super(e, exceptionDesc);
    }
}
