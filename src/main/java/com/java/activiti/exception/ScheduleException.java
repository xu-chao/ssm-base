package com.java.activiti.exception;

public class ScheduleException extends BusinessException {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public ScheduleException(Throwable e) {
        super(e.getMessage());
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public ScheduleException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param e
     * @param message the message
     */
    public ScheduleException(Exception e, String message) {
        super(e, message);
    }
}
