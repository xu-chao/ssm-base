package com.java.activiti.util.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:������Ϣ���͵�ö����
 */
public enum ChatType {
    LOGIN, REGISTER, SINGLE_SENDING, GROUP_SENDING, FILE_MSG_SINGLE_SENDING, FILE_MSG_GROUP_SENDING;
    private static final Logger logger= LoggerFactory.getLogger(ChatType.class);
    public static void main(String[] args) {
        logger.info(String.valueOf(ChatType.REGISTER));
    }
}
