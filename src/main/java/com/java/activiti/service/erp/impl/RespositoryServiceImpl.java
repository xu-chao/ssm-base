package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.RespositoryDao;
import com.java.activiti.model.erp.Respository;
import com.java.activiti.service.erp.RespositoryService;
import com.java.activiti.util.aop.Operation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("respositoryService")
public class RespositoryServiceImpl implements RespositoryService {

    @Resource
    private RespositoryDao respositoryDao;

    @Override
    public List<Respository> respositoryPage(Map<String, Object> map) {
        return respositoryDao.respositoryPage(map);
    }

    @Override
    public int respositoryCount(Map<String, Object> map) {
        return respositoryDao.respositoryCount(map);
    }

    @Override
    public List<Respository> findRespositoryName(String q) {
        return respositoryDao.selectRespositoryName(q);
    }

    @Override
    public List<Respository> findRespositoryName() {
        return respositoryDao.selectALL();
    }

    /**
     * ��������
     *
     * @param respository
     * @return
     */
    @Operation(value = "�����ֿ�")
    public int addRespository(Respository respository) {
        return respositoryDao.addRespository(respository);
    }

    /**
     * �޸�����
     *
     * @param respository
     * @return
     */
    @Operation(value = "���²ֿ���Ϣ")
    public int updateRespository(Respository respository) {
        return respositoryDao.updateRespository(respository);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Operation(value = "����ɾ���ֿ�")
    public int deleteRespositoryById(List<String> id) {
        return respositoryDao.deleteRespositoryById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param respository
     * @return
     */
    public List<Respository> findRespository(Respository respository) {
        return respositoryDao.findRespository(respository);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Respository> respositoryList = respositoryDao.respositoryPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"�ֿ�Id", "�ֿ��ַ", "״̬ ", "����", "����"};
        // �п�
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000};
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
        for (Respository respository : respositoryList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(respository.getREPO_ID());// REPO_ID
            row.createCell(1).setCellValue(respository.getREPO_ADDRESS());// REPO_ADDRESS
            row.createCell(2).setCellValue(respository.getREPO_STATUS());// REPO_STATUS
            row.createCell(3).setCellValue(respository.getREPO_AREA());// REPO_AREA
            row.createCell(4).setCellValue(respository.getREPO_DESC());// REPO_DESC
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

    /**
     * ���ݵ���
     */
    @Operation(value = "Excel����������Ϣ")
    @Override
    public void respositoryImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Respository respository = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                respository = new Respository();
                respository.setREPO_ID((int) (sheet.getRow(i).getCell(0).getNumericCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
                List<Respository> list = respositoryDao.selectRespositoryByRespositoryID(respository.getREPO_ID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    respository = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_ADDRESS(sheet.getRow(i).getCell(1).getStringCellValue());
                // ��������
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_STATUS(sheet.getRow(i).getCell(2).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_AREA(sheet.getRow(i).getCell(3).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                respository.setREPO_DESC(sheet.getRow(i).getCell(4).getStringCellValue());
                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    respositoryDao.addRespository(respository);
                } else {
                    // �����û���Ϣ
                    respositoryDao.updateRespository(respository);
                }
            }
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Respository selectRespositoryById(int REPO_ID) {
        return respositoryDao.findById(REPO_ID);
    }
}
