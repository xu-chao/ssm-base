package com.java.activiti.service.erp.impl;

import com.java.activiti.dao.erp.RespositoryDao;
import com.java.activiti.dao.erp.SupplierDao;
import com.java.activiti.model.Goods;
import com.java.activiti.model.erp.Respository;
import com.java.activiti.model.erp.Supplier;
import com.java.activiti.service.erp.RespositoryService;
import com.java.activiti.service.erp.SupplierService;
import com.java.activiti.util.aop.Operation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierDao supplierDao;

    @Override
    public List<Supplier> supplierPage(Map<String, Object> map) {
        return supplierDao.supplierPage(map);
    }

    @Override
    public int supplierCount(Map<String, Object> map) {
        return supplierDao.supplierCount(map);
    }

    @Override
    public List<Supplier> findSupplierName(String q) {
        return supplierDao.selectSupplierName(q);
    }

    @Override
    public List<Supplier> findSupplierName() {
        return supplierDao.selectALL();
    }

    /**
     * ��������
     *
     * @param supplier
     * @return
     */
    @Operation(value = "������Ӧ��")
    public int addSupplier(Supplier supplier) {
        return supplierDao.addSupplier(supplier);
    }

    /**
     * �޸�����
     *
     * @param supplier
     * @return
     */
    @Operation(value = "���¹�Ӧ����Ϣ")
    public int updateSupplier(Supplier supplier) {
        return supplierDao.updateSupplier(supplier);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Operation(value = "����ɾ����Ӧ��")
    public int deleteSupplierById(List<String> id) {
        return supplierDao.deleteSupplierById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param supplier
     * @return
     */
    public List<Supplier> findSupplier(Supplier supplier) {
        return supplierDao.findSupplier(supplier);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Supplier> supplierList = supplierDao.supplierPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"��Ӧ��Id", "��Ӧ������", "��Ӧ�̸����� ", "��Ӧ����ϵ��ʽ", "��Ӧ��Email", "��Ӧ�̵�ַ"};
        // �п�
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
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
        for (Supplier supplier : supplierList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(supplier.getSUPPLIER_ID());// SUPPLIER_ID()
            row.createCell(1).setCellValue(supplier.getSUPPLIER_NAME());// SUPPLIER_NAME()
            row.createCell(2).setCellValue(supplier.getSUPPLIER_PERSON());// SUPPLIER_PERSON()
            row.createCell(3).setCellValue(supplier.getSUPPLIER_TEL());// SUPPLIER_TEL()
            row.createCell(4).setCellValue(supplier.getSUPPLIER_EMAIL());// SUPPLIER_EMAIL()
            row.createCell(4).setCellValue(supplier.getSUPPLIER_ADDRESS());// SUPPLIER_ADDRESS()
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
    public void supplierImport(InputStream is) throws IOException {
//        XSSFWorkbook wb = null;
        HSSFWorkbook wb = null;
        try {
//            wb = new XSSFWorkbook(is);
            wb = new HSSFWorkbook(is);
//            XSSFSheet sheet = wb.getSheetAt(0);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Supplier supplier = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                supplier = new Supplier();
                supplier.setSUPPLIER_ID((int)sheet.getRow(i).getCell(0).getNumericCellValue());
                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
                List<Supplier> list = supplierDao.selectSupplierBySupplierID(supplier.getSUPPLIER_ID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    supplier = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_NAME(sheet.getRow(i).getCell(1).getStringCellValue());
                // ��������
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_PERSON(sheet.getRow(i).getCell(2).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_TEL(sheet.getRow(i).getCell(3).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_EMAIL(sheet.getRow(i).getCell(4).getStringCellValue());
                // ����
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_ADDRESS(sheet.getRow(i).getCell(5).getStringCellValue());
                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    supplierDao.addSupplier(supplier);
                } else {
                    // �����û���Ϣ
                    supplierDao.updateSupplier(supplier);
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
    public Supplier selectSupplierById(int SUPPLIER_ID) {
        return supplierDao.findById(SUPPLIER_ID);
    }

    @Override
    public List<Supplier> findSUPPLIER_PERSONLike(String q) {
        return supplierDao.selectSUPPLIER_PERSONLike(q);
    }

    @Override
    public List<Supplier> findAllSupplier() {
        return supplierDao.findAllSupplier();
    }
}
