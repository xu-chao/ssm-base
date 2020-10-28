package com.java.activiti.service;

import com.java.activiti.model.gaizao.Huiyishi;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.model.Repair;

public interface MailService {

    Boolean sendMail(String subject, Repair content, String emailName, String to, String basePath) throws Exception;
    Boolean sendMailQestion(String subject, QuestionInfo content, String emailName, String to, String basePath) throws Exception;
    Boolean sendMailHuiyishi(String subject, Huiyishi huiyishi, String emailName, String to, String basePath) throws Exception;
}
