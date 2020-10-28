package com.java.activiti.service.swylsb.impl;

import com.java.activiti.dao.swylsb.SwylsbJhDao;
import com.java.activiti.model.swylsb.SwylsbJh;
import com.java.activiti.service.swylsb.SwylsbJhService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("swylsbJhService")
public class SwylsbJhServiceImpl implements SwylsbJhService {

    @Resource
    private SwylsbJhDao swylsbJhDao;

    @Override
    public List<SwylsbJh> swylsbJhPage(Map<String, Object> map) {
        return swylsbJhDao.swylsbJhPage(map);
    }

    @Override
    public int swylsbJhCount(Map<String, Object> map) {
        return swylsbJhDao.swylsbJhCount(map);
    }

    @Override
    public List<SwylsbJh> findSwylsbJhName(String q) {
        return swylsbJhDao.selectSwylsbJhName(q);
    }

    @Override
    public List<SwylsbJh> findSwylsbJhName() {
        return swylsbJhDao.findAllSwylsbJh();
    }

    public int addSwylsbJh(SwylsbJh swylsbJh) {
        return swylsbJhDao.addSwylsbJh(swylsbJh);
    }

    public int updateSwylsbJh(List<SwylsbJh> swylsbJh) {
        return swylsbJhDao.updateSwylsbJh(swylsbJh);
    }

    public int deleteSwylsbJhById(List<String> id) {
        return swylsbJhDao.deleteSwylsbJhById(id);
    }

    public List<SwylsbJh> findSwylsbJh(SwylsbJh swylsbJh) {
        return swylsbJhDao.findSwylsbJh(swylsbJh);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
//        // ��ȡ���й�Ӧ����Ϣ
//        List<Supplier> supplierList = swylsbDao.supplierPage(map);
//        // 1.����excel������
//        HSSFWorkbook wk = new HSSFWorkbook();
//        // 2.����һ��������
//        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
//        // 3.д���ͷ
//        HSSFRow row = sheet.createRow(0);
//        // ��ͷ
//        String[] headerName = {"��Ӧ��Id", "��Ӧ������", "��Ӧ�̸����� ", "��Ӧ����ϵ��ʽ", "��Ӧ��Email", "��Ӧ�̵�ַ"};
//        // �п�
//        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
//        HSSFCell cell = null;
//        for (int i = 0; i < headerName.length; i++) {
//            // ������ͷ��Ԫ��
//            cell = row.createCell(i);
//            // ���ͷ��Ԫ��дֵ
//            cell.setCellValue(headerName[i]);
//            sheet.setColumnWidth(i, columnWidths[i]);
//        }
//        // 4.�����ݵ�Ԫ��дֵ
//        int i = 1;
//        for (Supplier supplier : supplierList) {
//            row = sheet.createRow(i);
//            row.createCell(0).setCellValue(supplier.getSUPPLIER_ID());// SUPPLIER_ID()
//            row.createCell(1).setCellValue(supplier.getSUPPLIER_NAME());// SUPPLIER_NAME()
//            row.createCell(2).setCellValue(supplier.getSUPPLIER_PERSON());// SUPPLIER_PERSON()
//            row.createCell(3).setCellValue(supplier.getSUPPLIER_TEL());// SUPPLIER_TEL()
//            row.createCell(4).setCellValue(supplier.getSUPPLIER_EMAIL());// SUPPLIER_EMAIL()
//            row.createCell(4).setCellValue(supplier.getSUPPLIER_ADDRESS());// SUPPLIER_ADDRESS()
//            i++;
//        }
//        try {
//            // д�뵽�������
//            wk.write(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // �رչ�����
//                wk.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void swylsbJhImport(InputStream is) throws IOException {
////        XSSFWorkbook wb = null;
//        HSSFWorkbook wb = null;
//        try {
////            wb = new XSSFWorkbook(is);
//            wb = new HSSFWorkbook(is);
////            XSSFSheet sheet = wb.getSheetAt(0);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // ��ȡ����
//            // ���һ�е��к�
//            int lastRow = sheet.getLastRowNum();
//            Supplier supplier = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                supplier = new Supplier();
//                supplier.setSUPPLIER_ID((int)sheet.getRow(i).getCell(0).getNumericCellValue());
//                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
//                List<Supplier> list = swylsbDao.selectSupplierBySupplierID(supplier.getSUPPLIER_ID());
//                if (list.size() > 0) {
//                    // ˵��������ͬ����Ҫ����
//                    supplier = list.get(0);
//                }
//                HSSFCell cell = null;
//                // ��������
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_NAME(sheet.getRow(i).getCell(1).getStringCellValue());
//                // ��������
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_PERSON(sheet.getRow(i).getCell(2).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_TEL(sheet.getRow(i).getCell(3).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_EMAIL(sheet.getRow(i).getCell(4).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_ADDRESS(sheet.getRow(i).getCell(5).getStringCellValue());
//                if (list.size() == 0) {
//                    // ˵���������û���Ϣ����Ҫ����
//                    swylsbDao.addSupplier(supplier);
//                } else {
//                    // �����û���Ϣ
//                    swylsbDao.updateSupplier(supplier);
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
    public List<SwylsbJh> selectSwylsbJhById(int ID) {
        return swylsbJhDao.findById(ID);
    }

//    @Override
//    public List<Supplier> findSUPPLIER_PERSONLike(String q) {
//        return swylsbDao.selectSUPPLIER_PERSONLike(q);
//    }

    @Override
    public List<SwylsbJh> findAllSwylsbJh() {
        return swylsbJhDao.findAllSwylsbJh();
    }
}
