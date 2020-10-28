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
        cal.get(Calendar.HOUR_OF_DAY );//�õ�24Сʱ���Ƶ�
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
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<OperationRecord> operationRecordList = operationRecordDao.operationRecordPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = { "��¼ID", "�û�ID", "�û��� ", "������¼", "����ʱ��", "�������" };
        // �п�
        int[] columnWidths = { 6000, 6000, 6000, 6000, 6000, 6000 };
        HSSFCell cell = null;
        for (int i = 0; i < headerName.length; i++) {
            // ������ͷ��Ԫ��
            cell = row.createCell(i);
            // ���ͷ��Ԫ��дֵ
            cell.setCellValue(headerName[i]);
            sheet.setColumnWidth(i, columnWidths[i]);
        }
        // 4.�����ݵ�Ԫ��дֵ
        int i = 1;
        for (OperationRecord operationRecord : operationRecordList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(operationRecord.getrecord_ID());// ��¼ID
            row.createCell(1).setCellValue(operationRecord.getUser_ID());// �û�ID
            if (operationRecord.getUser_Name() != null) {
                row.createCell(2).setCellValue(operationRecord.getUser_Name());// �û���
            }
            row.createCell(3).setCellValue(operationRecord.getOperation_Name()); // ������¼
            if (operationRecord.getOperation_Time() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                row.createCell(4).setCellValue(operationRecord.getOperation_Time());// ����ʱ��
                sheet.getRow(i).getCell(4).setCellStyle(style_date);
            }
            row.createCell(5).setCellValue(operationRecord.getOperation_Result());// �������
            i++;
        }
        try {
            // д�뵽�������
            wk.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // �رչ�����
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
