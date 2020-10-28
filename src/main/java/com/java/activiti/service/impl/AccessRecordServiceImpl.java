package com.java.activiti.service.impl;

import com.java.activiti.dao.AccessRecordDao;
import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.AccessRecord;
import com.java.activiti.service.AccessRecordService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Service("accessRecordService")
public class AccessRecordServiceImpl implements AccessRecordService {

    @Resource
    private AccessRecordDao accessRecordDao;

    /**
     * 插入用户登入登出记录
     *
     * @param userID     用户ID
     * @param userName   用户名
     * @param accessIP   登陆IP
     * @param accessType 记录类型
     */
    @Override
    public void insertAccessRecord(String userID, String userName, String accessIP, String accessType) throws SystemLogServiceException {
        // 创建 AccessRecordDO 对象
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setUser_ID(userID);
        accessRecord.setUser_Name(userName);
        accessRecord.setAccess_Time(new Date());
        accessRecord.setAccess_IP(accessIP);
        accessRecord.setAccess_Type(accessType);

        // 持久化 AccessRecordDO 对象到数据库
        try {
            accessRecordDao.insertAccessRecord(accessRecord);
        } catch (PersistenceException e) {
            throw new SystemLogServiceException(e, "Fail to persist AccessRecord Object");
        }
    }

    @Override
    public Map<String, Object> selectAccessRecord(String userID, String accessType, String startDateStr, String endDateStr) throws SystemLogServiceException {
        return null;
    }

    @Override
    public Map<String, Object> selectAccessRecord(String userID, String accessType, String startDateStr, String endDateStr, int offset, int limit) throws SystemLogServiceException {
        return null;
    }

    public List<AccessRecord> accessRecordPage(Map<String, Object> map) {
        return accessRecordDao.accessRecordPage(map);
    }

    public int accessRecordCount(Map<String, Object> map) {
        return accessRecordDao.accessRecordCount(map);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // 获取所有供应商信息
        List<AccessRecord> accessRecordList = accessRecordDao.accessRecordPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = { "记录ID", "用户ID", "用户名 ", "登录类型", "操作时间", "登录IP" };
        // 列宽
        int[] columnWidths = { 6000, 6000, 6000, 6000, 6000, 6000 };
        HSSFCell cell = null;
        for (int i = 0; i < headerName.length; i++) {
            // 创建表头单元格
            cell = row.createCell(i);
            // 向表头单元格写值
            cell.setCellValue(headerName[i]);
            sheet.setColumnWidth(i, columnWidths[i]);
        }
        // 4.向内容单元格写值
        int i = 1;
        for (AccessRecord accessRecord : accessRecordList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(accessRecord.getRecord_ID());// 记录ID
            row.createCell(1).setCellValue(accessRecord.getUser_ID());// 用户ID
            if (accessRecord.getUser_Name() != null) {
                row.createCell(2).setCellValue(accessRecord.getUser_Name());// 用户名
            }
            row.createCell(3).setCellValue(accessRecord.getAccess_Type()); // 登录类型
            if (accessRecord.getAccess_Time() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                row.createCell(4).setCellValue(accessRecord.getAccess_Time());// 操作时间
                sheet.getRow(i).getCell(4).setCellStyle(style_date);
            }
            row.createCell(5).setCellValue(accessRecord.getAccess_IP());// 登录IP
            i++;
        }
        try {
            // 写入到输出流中
            wk.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭工作簿
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
