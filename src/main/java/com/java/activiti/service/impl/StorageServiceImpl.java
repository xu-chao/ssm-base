package com.java.activiti.service.impl;

import com.java.activiti.dao.GoodsDao;
import com.java.activiti.dao.StorageDao;
import com.java.activiti.model.Goods;
import com.java.activiti.model.Storage;
import com.java.activiti.pojo.Tree;
import com.java.activiti.service.GoodsService;
import com.java.activiti.service.StorageService;
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

@Service("storageService")
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public List<Storage> storagePage(Map<String, Object> map) {
        return storageDao.storagePage(map);
    }

    @Override
    public int storageCount(Map<String, Object> map) {
        return storageDao.storageCount(map);
    }

    @Override
    public List<Storage> findStorageName(String q) {
        return storageDao.selectStorageName(q);
    }

    /**
     * 新增物料
     *
     * @param storage
     * @return
     */
    public int addStorage(Storage storage) {
        return storageDao.addStorage(storage);
    }

    /**
     * 修改物料
     *
     * @param storage
     * @return
     */
    public int updateStorage(Storage storage) {
        return storageDao.updateStorage(storage);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    public int deleteStorageById(List<String> id) {
        return storageDao.deleteStorageById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param storage
     * @return
     */
    public List<Storage> findStorage(Storage storage) {
        return storageDao.findStorage(storage);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<Storage> storageList = storageDao.storagePage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"库存Id", "工艺数量", "备用数量", "实购数量", "合格数量", "不合格数量"};
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
        for (Storage storage : storageList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(storage.getStorageId());// StorageId
            row.createCell(1).setCellValue(storage.getMount());// Mount
            row.createCell(2).setCellValue(storage.getMountBack());// MountBack
            row.createCell(3).setCellValue(storage.getMountIn());// MountIn
            row.createCell(4).setCellValue(storage.getMountQualified());// MountQualified
            row.createCell(5).setCellValue(storage.getMountNotQualified()); // MountNotQualified
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
    @Override
    public void storageImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Storage storage = null;
            for (int i = 1; i <= lastRow; i++) {
                storage = new Storage();
                storage.setStorageId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
                List<Storage> list = storageDao.selectStorageByStorageID(storage.getStorageId());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    storage = list.get(0);
                }
                HSSFCell cell = null;
                // 城市名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMount((int) sheet.getRow(i).getCell(1).getNumericCellValue());
                // 城市名称
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountBack((int)sheet.getRow(i).getCell(2).getNumericCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountIn((int)sheet.getRow(i).getCell(3).getNumericCellValue());
                // 经度
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountQualified((int) sheet.getRow(i).getCell(4).getNumericCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountNotQualified((int)sheet.getRow(i).getCell(5).getNumericCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    storageDao.addStorage(storage);
                } else {
                    // 更新用户信息
                    storageDao.updateStorage(storage);
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
}
