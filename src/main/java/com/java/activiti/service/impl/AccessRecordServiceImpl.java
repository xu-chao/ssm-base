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
     * �����û�����ǳ���¼
     *
     * @param userID     �û�ID
     * @param userName   �û���
     * @param accessIP   ��½IP
     * @param accessType ��¼����
     */
    @Override
    public void insertAccessRecord(String userID, String userName, String accessIP, String accessType) throws SystemLogServiceException {
        // ���� AccessRecordDO ����
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setUser_ID(userID);
        accessRecord.setUser_Name(userName);
        accessRecord.setAccess_Time(new Date());
        accessRecord.setAccess_IP(accessIP);
        accessRecord.setAccess_Type(accessType);

        // �־û� AccessRecordDO �������ݿ�
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
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<AccessRecord> accessRecordList = accessRecordDao.accessRecordPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = { "��¼ID", "�û�ID", "�û��� ", "��¼����", "����ʱ��", "��¼IP" };
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
        for (AccessRecord accessRecord : accessRecordList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(accessRecord.getRecord_ID());// ��¼ID
            row.createCell(1).setCellValue(accessRecord.getUser_ID());// �û�ID
            if (accessRecord.getUser_Name() != null) {
                row.createCell(2).setCellValue(accessRecord.getUser_Name());// �û���
            }
            row.createCell(3).setCellValue(accessRecord.getAccess_Type()); // ��¼����
            if (accessRecord.getAccess_Time() != null) {
                HSSFCellStyle style_date = wk.createCellStyle();
                DataFormat df = wk.createDataFormat();
                style_date.setDataFormat(df.getFormat("yyyy-MM-dd HH:mm:ss"));
                row.createCell(4).setCellValue(accessRecord.getAccess_Time());// ����ʱ��
                sheet.getRow(i).getCell(4).setCellStyle(style_date);
            }
            row.createCell(5).setCellValue(accessRecord.getAccess_IP());// ��¼IP
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
