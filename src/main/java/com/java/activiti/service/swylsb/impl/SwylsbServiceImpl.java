package com.java.activiti.service.swylsb.impl;

import com.java.activiti.dao.swylsb.SwylsbDao;
import com.java.activiti.model.swylsb.Swylsb;
import com.java.activiti.service.swylsb.SwylsbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("swylsbService")
public class SwylsbServiceImpl implements SwylsbService {

    @Resource
    private SwylsbDao swylsbDao;

    @Override
    public List<Swylsb> swylsbPage(Map<String, Object> map) {
        return swylsbDao.swylsbPage(map);
    }

    @Override
    public int swylsbCount(Map<String, Object> map) {
        return swylsbDao.swylsbCount(map);
    }

    @Override
    public List<Swylsb> findSwylsbName(String q) {
        return swylsbDao.selectSwylsbName(q);
    }

    @Override
    public List<Swylsb> findSwylsbName() {
        return swylsbDao.findAllSwylsb();
    }

    public int addSwylsb(Swylsb swylsb) {
        return swylsbDao.addSwylsb(swylsb);
    }

    public int updateSwylsb(Swylsb swylsb) {
        return swylsbDao.updateSwylsb(swylsb);
    }

    public int deleteSwylsbById(List<String> id) {
        return swylsbDao.deleteSwylsbById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param swylsb
     * @return
     */
    public List<Swylsb> findSwylsb(Swylsb swylsb) {
        return swylsbDao.findSwylsb(swylsb);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
//        // 获取所有供应商信息
//        List<Supplier> supplierList = swylsbDao.supplierPage(map);
//        // 1.创建excel工作薄
//        HSSFWorkbook wk = new HSSFWorkbook();
//        // 2.创建一个工作表
//        HSSFSheet sheet = wk.createSheet("系统用户");
//        // 3.写入表头
//        HSSFRow row = sheet.createRow(0);
//        // 表头
//        String[] headerName = {"供应商Id", "供应商名称", "供应商负责人 ", "供应商联系方式", "供应商Email", "供应商地址"};
//        // 列宽
//        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
//        HSSFCell cell = null;
//        for (int i = 0; i < headerName.length; i++) {
//            // 创建表头单元格
//            cell = row.createCell(i);
//            // 向表头单元格写值
//            cell.setCellValue(headerName[i]);
//            sheet.setColumnWidth(i, columnWidths[i]);
//        }
//        // 4.向内容单元格写值
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
//            // 写入到输出流中
//            wk.write(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // 关闭工作簿
//                wk.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void swylsbImport(InputStream is) throws IOException {
////        XSSFWorkbook wb = null;
//        HSSFWorkbook wb = null;
//        try {
////            wb = new XSSFWorkbook(is);
//            wb = new HSSFWorkbook(is);
////            XSSFSheet sheet = wb.getSheetAt(0);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // 读取数据
//            // 最后一行的行号
//            int lastRow = sheet.getLastRowNum();
//            Supplier supplier = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                supplier = new Supplier();
//                supplier.setSUPPLIER_ID((int)sheet.getRow(i).getCell(0).getNumericCellValue());
//                // 判断是否已经存在，通过cityID来判断
//                List<Supplier> list = swylsbDao.selectSupplierBySupplierID(supplier.getSUPPLIER_ID());
//                if (list.size() > 0) {
//                    // 说明存在相同，需要更新
//                    supplier = list.get(0);
//                }
//                HSSFCell cell = null;
//                // 城市名称
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_NAME(sheet.getRow(i).getCell(1).getStringCellValue());
//                // 城市名称
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_PERSON(sheet.getRow(i).getCell(2).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_TEL(sheet.getRow(i).getCell(3).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_EMAIL(sheet.getRow(i).getCell(4).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                supplier.setSUPPLIER_ADDRESS(sheet.getRow(i).getCell(5).getStringCellValue());
//                if (list.size() == 0) {
//                    // 说明不存在用户信息，需要新增
//                    swylsbDao.addSupplier(supplier);
//                } else {
//                    // 更新用户信息
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
    public Swylsb selectSwylsbById(int SUPPLIER_ID) {
        return swylsbDao.findById(SUPPLIER_ID);
    }

    @Override
    public Swylsb findById(int ID) {
        return swylsbDao.findById(ID);
    }

//    @Override
//    public List<Supplier> findSUPPLIER_PERSONLike(String q) {
//        return swylsbDao.selectSUPPLIER_PERSONLike(q);
//    }

    @Override
    public List<Swylsb> findAllSwylsb() {
        return swylsbDao.findAllSwylsb();
    }
}
