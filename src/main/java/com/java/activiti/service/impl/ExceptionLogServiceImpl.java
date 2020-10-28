package com.java.activiti.service.impl;

import com.java.activiti.dao.ExceptionDao;
import com.java.activiti.model.ExceptionInfo;
import com.java.activiti.model.MemberShip;
import com.java.activiti.service.ExceptionLogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService {
@Resource
ExceptionDao exceptionDao;
    @Override
    public int addExceptionLog(ExceptionInfo exceptionInfo) {
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        MemberShip sessionInfo = (MemberShip) session.getAttribute("sessionInfo");
        String userID = sessionInfo.getUserId();
        exceptionInfo.setUserID(userID);
        return exceptionDao.addException(exceptionInfo);
    }

    @Override
    public List<ExceptionInfo> exceptionRecordPage(Map<String, Object> map) {
        return exceptionDao.exceptionRecordPage(map);
    }

    @Override
    public int exceptionRecordCount(Map<String, Object> map) {
        return exceptionDao.exceptionRecordCount(map);
    }
}
