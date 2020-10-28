package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WmssupplierDao;
import com.java.activiti.model.wms.Wmssupplier;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.wms.WmssupplierService;
import com.java.activiti.util.aop.Operation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("wmssupplierService")
public class WmssupplierServiceImp implements WmssupplierService {

    @Resource
    private WmssupplierDao wmssupplierDao;

    @Override
    public List<Wmssupplier> wmssupplierPage(Map<String, Object> map) {
        return wmssupplierDao.wmssupplierPage(map);
    }

    @Override
    public int wmssupplierCount(Map<String, Object> map) {
        return wmssupplierDao.wmssupplierCount(map);
    }


    /**
     * ������Ӧ��
     *
     * @param wmssupplier
     * @return
     */
    @Override
    @Operation(value = "������Ӧ��")
    public int addWmssupplier(Wmssupplier wmssupplier) {
        return wmssupplierDao.addWmssupplier(wmssupplier);
    }

    /**
     * �޸Ĺ�Ӧ��
     *
     * @param wmssupplier
     * @return
     */
    @Override
    @Operation(value = "���¹�Ӧ����Ϣ")
    public int updateWmssupplier(Wmssupplier wmssupplier) {
        return wmssupplierDao.updateWmssupplier(wmssupplier);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ����Ӧ��")
    public int deleteWmssupplierById(List<String> id) {
        return wmssupplierDao.deleteWmssupplierById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param wmssupplier
     * @return
     */
    @Override
    public List<Wmssupplier> findWmssupplier(Wmssupplier wmssupplier) {
        return wmssupplierDao.findWmssupplier(wmssupplier);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Wmssupplier> wmssupplierList = wmssupplierDao.wmssupplierPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("��Ӧ��");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"��Ӧ��ID", "��Ӧ������","��Ӧ�̵绰","��Ӧ������", "��Ӧ�̵�ַ "};
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
        for (Wmssupplier wmssupplier : wmssupplierList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(wmssupplier.getSupplierId());// WmssupplierID
            row.createCell(1).setCellValue(wmssupplier.getSupplierName());// WmssupplierName
            row.createCell(2).setCellValue(wmssupplier.getSupplierTel());// WmssupplierSysName
            row.createCell(3).setCellValue(wmssupplier.getSupplierEmail());// WmssupplierType
            row.createCell(4).setCellValue(wmssupplier.getSupplierAddress());// WmssupplierType
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
    @Operation(value = "Excel���빩Ӧ����Ϣ")
    @Override
    public void wmssupplierImport(InputStream is) throws IOException {
//        HSSFWorkbook wb = null;
//        try {
//            wb = new HSSFWorkbook(is);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // ��ȡ����
//            // ���һ�е��к�
//            int lastRow = sheet.getLastRowNum();
//            Wmssupplier wmssupplier = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                wmssupplier = new Wmssupplier();
//                wmssupplier.setWmssupplierId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
//                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
//                List<Wmssupplier> list = wmssupplierDao.selectWmssupplierByWmssupplierID(wmssupplier.getWmssupplierId());
//                if (list.size() > 0) {
//                    // ˵��������ͬ����Ҫ����
//                    wmssupplier = list.get(0);
//                }
//                HSSFCell cell = null;
//                // ��������
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierName(sheet.getRow(i).getCell(1).getStringCellValue());
//                // ��������
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierSysName(sheet.getRow(i).getCell(2).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierType(sheet.getRow(i).getCell(3).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierCode(sheet.getRow(i).getCell(4).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierUnit(sheet.getRow(i).getCell(5).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(6);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierSize(sheet.getRow(i).getCell(6).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(7);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierFunction(sheet.getRow(i).getCell(7).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(8);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierMessage(sheet.getRow(i).getCell(8).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(9);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierElse(sheet.getRow(i).getCell(9).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(10);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierLink(sheet.getRow(i).getCell(10).getStringCellValue());
//                if (list.size() == 0) {
//                    // ˵���������û���Ϣ����Ҫ����
//                    wmssupplierDao.addWmssupplier(wmssupplier);
//                } else {
//                    // �����û���Ϣ
//                    wmssupplierDao.updateWmssupplier(wmssupplier);
//                }
//            }
//        } finally {
//            if (null != wb) {
//                try {
//                    wb.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    @Override
    public Wmssupplier selectWmssupplierById(Integer supplierId) {
        return wmssupplierDao.findById(supplierId);
    }

    @Override
    public List<Wmssupplier> selectSupplierNameLike(String q) {
        return wmssupplierDao.selectSupplierNameLike(q);
    }
    @Override
    public List<Wmssupplier> findALL() {
        return wmssupplierDao.selectALL();
    }
}
