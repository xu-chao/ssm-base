package com.java.activiti.service.impl;

import com.java.activiti.dao.OperationRecordDao;
import com.java.activiti.exception.SystemLogServiceException;
import com.java.activiti.model.OperationRecord;
import com.java.activiti.service.OperationRecordService;
import com.java.activiti.util.aop.Operation;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("operationRecordService")
public class OperationRecordServiceImpl implements OperationRecordService {

    @Resource
    private OperationRecordDao operationRecordDao;

    public void insertOperationRecord(String userID, String userName, String operationName, String operationResult) throws SystemLogServiceException {
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setUser_ID(userID);
        operationRecord.setUser_Name(userName);
        operationRecord.setOperation_Name(operationName);
        operationRecord.setOperation_Result(operationResult);
        Calendar cal = Calendar.getInstance();
        cal.get(Calendar.HOUR_OF_DAY );//得到24小时机制的
        Date date=cal.getTime();
        operationRecord.setOperation_Time(date);
        try {
            operationRecordDao.insertOperationRecord(operationRecord);
        }catch (PersistenceException e) {
            throw new SystemLogServiceException(e, "Fail to persist operationRecordDao Object");
        }
    }

    public Map<String, Object> selectUserOperationRecord(String userID, String startDateStr, String endDateStr) throws SystemLogServiceException {
        return null;
    }

    public Map<String, Object> selectUserOperationRecord(String userID, String startDateStr, String endDateStr, int offset, int limit) throws SystemLogServiceException {
        return null;
    }

    public List<OperationRecord> operationRecordPage(Map<String, Object> map) {
        return operationRecordDao.operationRecordPage(map);
    }

    public int operationRecordCount(Map<String, Object> map) {
        return operationRecordDao.operationRecordCount(map);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // 获取所有供应商信息
        List<OperationRecord> operationRecordList = operationRecordDao.operationRecordPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = { "记录ID", "用户ID", "用户名 ", "操作记录", "操作时间", "操作结果" };
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
        for (OperationRecord operationRecord : operationRecordList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(operationRecord.getrecord_ID());// 记录ID
            row.createCell(1).setCellValue(operationRecord.getUser_ID());// 用户ID
            if (operationRecord.getUser_Name() != null) {
                row.createCell(2).setCellValue(operationRecord.getUser_Name());// 用户名
            }
            row.createCell(3).setCellValue(operationRecord.getOperation_Name()); // 操作记录
            if (operationRecord.getOperation_Time() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                row.createCell(4).setCellValue(operationRecord.getOperation_Time());// 操作时间
                sheet.getRow(i).getCell(4).setCellStyle(style_date);
            }
            row.createCell(5).setCellValue(operationRecord.getOperation_Result());// 操作结果
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
