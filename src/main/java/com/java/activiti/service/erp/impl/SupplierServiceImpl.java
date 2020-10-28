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
     * 新增物料
     *
     * @param supplier
     * @return
     */
    @Operation(value = "新增供应商")
    public int addSupplier(Supplier supplier) {
        return supplierDao.addSupplier(supplier);
    }

    /**
     * 修改物料
     *
     * @param supplier
     * @return
     */
    @Operation(value = "更新供应商信息")
    public int updateSupplier(Supplier supplier) {
        return supplierDao.updateSupplier(supplier);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Operation(value = "批量删除供应商")
    public int deleteSupplierById(List<String> id) {
        return supplierDao.deleteSupplierById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param supplier
     * @return
     */
    public List<Supplier> findSupplier(Supplier supplier) {
        return supplierDao.findSupplier(supplier);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Supplier> supplierList = supplierDao.supplierPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"供应商Id", "供应商名称", "供应商负责人 ", "供应商联系方式", "供应商Email", "供应商地址"};
        // 列宽
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
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

    /**
     * 数据导入
     */
    @Operation(value = "Excel导入物料信息")
    @Override
    public void supplierImport(InputStream is) throws IOException {
//        XSSFWorkbook wb = null;
        HSSFWorkbook wb = null;
        try {
//            wb = new XSSFWorkbook(is);
            wb = new HSSFWorkbook(is);
//            XSSFSheet sheet = wb.getSheetAt(0);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Supplier supplier = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                supplier = new Supplier();
                supplier.setSUPPLIER_ID((int)sheet.getRow(i).getCell(0).getNumericCellValue());
                // 判断是否已经存在，通过cityID来判断
                List<Supplier> list = supplierDao.selectSupplierBySupplierID(supplier.getSUPPLIER_ID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    supplier = list.get(0);
                }
                HSSFCell cell = null;
                // 城市名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_NAME(sheet.getRow(i).getCell(1).getStringCellValue());
                // 城市名称
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_PERSON(sheet.getRow(i).getCell(2).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_TEL(sheet.getRow(i).getCell(3).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_EMAIL(sheet.getRow(i).getCell(4).getStringCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                supplier.setSUPPLIER_ADDRESS(sheet.getRow(i).getCell(5).getStringCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    supplierDao.addSupplier(supplier);
                } else {
                    // 更新用户信息
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
