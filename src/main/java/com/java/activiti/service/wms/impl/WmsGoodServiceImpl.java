package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WmsGoodDao;
import com.java.activiti.model.wms.WmsGood;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.wms.WmsGoodService;
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

@Service("wmsGoodService")
public class WmsGoodServiceImpl implements WmsGoodService {

    @Resource
    private WmsGoodDao wmsGoodDao;

    @Override
    public List<WmsGood> wmsGoodPage(Map<String, Object> map) {
        return wmsGoodDao.wmsGoodPage(map);
    }

    @Override
    public int wmsGoodCount(Map<String, Object> map) {
        return wmsGoodDao.wmsGoodCount(map);
    }


    /**
     * ��������
     *
     * @param wmsGood
     * @return
     */
    @Override
    @Operation(value = "��������")
    public int addWmsGood(WmsGood wmsGood) {
        return wmsGoodDao.addWmsGood(wmsGood);
    }

    /**
     * �޸�����
     *
     * @param wmsGood
     * @return
     */
    @Override
    @Operation(value = "����������Ϣ")
    public int updateWmsGood(WmsGood wmsGood) {
        return wmsGoodDao.updateWmsGood(wmsGood);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ������")
    public int deleteWmsGoodById(List<String> id) {
        return wmsGoodDao.deleteWmsGoodById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param wmsGood
     * @return
     */
    @Override
    public List<WmsGood> findWmsGood(WmsGood wmsGood) {
        return wmsGoodDao.findWmsGood(wmsGood);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<WmsGood> wmsGoodList = wmsGoodDao.wmsGoodPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"���ϱ���", "��������", "�����ͺ� ","���ϵ�λ", "�������", "�������"};
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
        for (WmsGood wmsGood : wmsGoodList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(wmsGood.getGoodEncoding());// WmsGoodID
            row.createCell(1).setCellValue(wmsGood.getGoodName());// WmsGoodName
            row.createCell(2).setCellValue(wmsGood.getGoodModel());// WmsGoodSysName
            row.createCell(3).setCellValue(wmsGood.getGoodUnit());// WmsGoodType
            row.createCell(4).setCellValue(wmsGood.getPerform().getTypeName());// WmsGoodType
            row.createCell(5).setCellValue(wmsGood.getOverhaul().getTypeName()); // WmsGoodUnit

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
    public void wmsGoodImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            WmsGood wmsGood = null;
            for (int i = 1; i <= lastRow; i++) {
                // ���ϱ���
                wmsGood = new WmsGood();
                wmsGood.setGoodEncoding((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
                List<WmsGood> list = wmsGoodDao.selectWmsGoodByGoodEncoding(wmsGood.getGoodEncoding());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    wmsGood = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setGoodName(sheet.getRow(i).getCell(1).getStringCellValue());
                // �����ͺ�
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setGoodModel(sheet.getRow(i).getCell(2).getStringCellValue());
                // ���ϵ�λ
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setGoodUnit(sheet.getRow(i).getCell(3).getStringCellValue());
                // �������
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setPerformStatus(Integer.parseInt(sheet.getRow(i).getCell(4).getStringCellValue()));
                // �������
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setOverhaulStatus(Integer.parseInt(sheet.getRow(i).getCell(5).getStringCellValue()));

                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    wmsGoodDao.addWmsGood(wmsGood);
                } else {
                    // �����û���Ϣ
                    wmsGoodDao.updateWmsGood(wmsGood);
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
    public WmsGood selectWmsGoodById(String ECodeID) {
        return wmsGoodDao.findById(ECodeID);
    }

    @Override
    public List<WmsGood> findGoodEncodingLike(String q) {
        return wmsGoodDao.selectGoodEncodingLike(q);
    }
    @Override
    public List<WmsGood> findGoodNameLike(String q) {
        return wmsGoodDao.selectGoodNameLike(q);
    }
    @Override
    public List<WmsGood> findGoodModelLike(String q) {
        return wmsGoodDao.selectGoodModelLike(q);
    }


    @Override
    public List<WmsGood> findALL() {
        return wmsGoodDao.selectALL();
    }}
