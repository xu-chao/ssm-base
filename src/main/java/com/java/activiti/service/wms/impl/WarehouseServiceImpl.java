package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WarehouseDao;
import com.java.activiti.model.wms.Warehouse;
import com.java.activiti.service.wms.WarehouseService;
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
import java.util.List;
import java.util.Map;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

    @Resource
    private WarehouseDao warehouseDao;

    @Override
    public List<Warehouse> warehousePage(Map<String, Object> map) {
        return warehouseDao.warehousePage(map);
    }

    @Override
    public int warehouseCount(Map<String, Object> map) {
        return warehouseDao.warehouseCount(map);
    }


    /**
     * 新增仓库
     *
     * @param warehouse
     * @return
     */
    @Override
    @Operation(value = "新增仓库")
    public int addWarehouse(Warehouse warehouse) {
        return warehouseDao.addWarehouse(warehouse);
    }

    /**
     * 修改仓库
     *
     * @param warehouse
     * @return
     */
    @Override
    @Operation(value = "更新仓库信息")
    public int updateWarehouse(Warehouse warehouse) {
        return warehouseDao.updateWarehouse(warehouse);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "批量删除仓库")
    public int deleteWarehouseById(List<String> id) {
        return warehouseDao.deleteWarehouseById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param warehouse
     * @return
     */
    @Override
    public List<Warehouse> findWarehouse(Warehouse warehouse) {
        return warehouseDao.findWarehouse(warehouse);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Warehouse> warehouseList = warehouseDao.warehousePage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"仓库ID", "仓库名称", "仓库地址 ", "描述"};
        // 列宽
        int[] columnWidths = {6000, 6000, 6000, 6000};
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
        for (Warehouse warehouse : warehouseList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(warehouse.getWhId());// WarehouseID
            row.createCell(1).setCellValue(warehouse.getWhName());// WarehouseName
            row.createCell(2).setCellValue(warehouse.getWhAddress());// WarehouseSysName
            row.createCell(3).setCellValue(warehouse.getWhDesc());// WarehouseType
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
    @Operation(value = "Excel导入仓库信息")
    @Override
    public void warehouseImport(InputStream is) throws IOException {
//        HSSFWorkbook wb = null;
//        try {
//            wb = new HSSFWorkbook(is);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // 读取数据
//            // 最后一行的行号
//            int lastRow = sheet.getLastRowNum();
//            Warehouse warehouse = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                warehouse = new Warehouse();
//                warehouse.setWarehouseId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
//                // 判断是否已经存在，通过cityID来判断
//                List<Warehouse> list = warehouseDao.selectWarehouseByWarehouseID(warehouse.getWarehouseId());
//                if (list.size() > 0) {
//                    // 说明存在相同，需要更新
//                    warehouse = list.get(0);
//                }
//                HSSFCell cell = null;
//                // 城市名称
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseName(sheet.getRow(i).getCell(1).getStringCellValue());
//                // 城市名称
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseSysName(sheet.getRow(i).getCell(2).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseType(sheet.getRow(i).getCell(3).getStringCellValue());
//                // 经度
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseCode(sheet.getRow(i).getCell(4).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseUnit(sheet.getRow(i).getCell(5).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(6);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseSize(sheet.getRow(i).getCell(6).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(7);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseFunction(sheet.getRow(i).getCell(7).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(8);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseMessage(sheet.getRow(i).getCell(8).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(9);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseElse(sheet.getRow(i).getCell(9).getStringCellValue());
//                // 纬度
//                cell = sheet.getRow(i).getCell(10);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseLink(sheet.getRow(i).getCell(10).getStringCellValue());
//                if (list.size() == 0) {
//                    // 说明不存在用户信息，需要新增
//                    warehouseDao.addWarehouse(warehouse);
//                } else {
//                    // 更新用户信息
//                    warehouseDao.updateWarehouse(warehouse);
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
    public Warehouse selectWarehouseById(Integer ECodeID) {
        return warehouseDao.findById(ECodeID);
    }

    @Override
    public List<Warehouse> findALL() {
        return warehouseDao.selectALL();
    }

    @Override
    public List<Warehouse> findWarehouseName(String q, String whType) {
        return warehouseDao.findWarehouseName(q,whType);
    }

    @Override
    public List<Warehouse> findWarehouseNameByWhType(String whType) {
        return warehouseDao.findWarehouseNameByWhType(whType);
    }
}
