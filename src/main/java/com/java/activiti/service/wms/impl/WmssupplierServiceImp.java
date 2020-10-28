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
     * 新增供应商
     *
     * @param wmssupplier
     * @return
     */
    @Override
    @Operation(value = "新增供应商")
    public int addWmssupplier(Wmssupplier wmssupplier) {
        return wmssupplierDao.addWmssupplier(wmssupplier);
    }

    /**
     * 修改供应商
     *
     * @param wmssupplier
     * @return
     */
    @Override
    @Operation(value = "更新供应商信息")
    public int updateWmssupplier(Wmssupplier wmssupplier) {
        return wmssupplierDao.updateWmssupplier(wmssupplier);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "批量删除供应商")
    public int deleteWmssupplierById(List<String> id) {
        return wmssupplierDao.deleteWmssupplierById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param wmssupplier
     * @return
     */
    @Override
    public List<Wmssupplier> findWmssupplier(Wmssupplier wmssupplier) {
        return wmssupplierDao.findWmssupplier(wmssupplier);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Wmssupplier> wmssupplierList = wmssupplierDao.wmssupplierPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("供应商");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"供应商ID", "供应商名称","供应商电话","供应商邮箱", "供应商地址 "};
        // 列宽
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000};
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
    @Operation(value = "Excel导入供应商信息")
    @Override
    public void wmssupplierImport(InputStream is) throws IOException {
//        HSSFWorkbook wb = null;
//        try {
//            wb = new HSSFWorkbook(is);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // 读取数据
//            // 最后一行的行号
//            int lastRow = sheet.getLastRowNum();
//            Wmssupplier wmssupplier = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                wmssupplier = new Wmssupplier();
//                wmssupplier.setWmssupplierId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
//                // 判断是否已经存在，通过cityID来判断
//                List<Wmssupplier> list = wmssupplierDao.selectWmssupplierByWmssupplierID(wmssupplier.getWmssupplierId());
//                if (list.size() > 0) {
//                    // 说明存在相同，需要更新
//                    wmssupplier = list.get(0);
//                }
//                HSSFCell cell = null;
//                // 城市名称
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierName(sheet.getRow(i).getCell(1).getStringCellValue());
//                // 城市名称
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierSysName(sheet.getRow(i).getCell(2).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierType(sheet.getRow(i).getCell(3).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierCode(sheet.getRow(i).getCell(4).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierUnit(sheet.getRow(i).getCell(5).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(6);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierSize(sheet.getRow(i).getCell(6).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(7);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierFunction(sheet.getRow(i).getCell(7).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(8);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierMessage(sheet.getRow(i).getCell(8).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(9);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierElse(sheet.getRow(i).getCell(9).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(10);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                wmssupplier.setWmssupplierLink(sheet.getRow(i).getCell(10).getStringCellValue());
//                if (list.size() == 0) {
//                    // 说明不存在用户信息，需要新增
//                    wmssupplierDao.addWmssupplier(wmssupplier);
//                } else {
//                    // 更新用户信息
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
