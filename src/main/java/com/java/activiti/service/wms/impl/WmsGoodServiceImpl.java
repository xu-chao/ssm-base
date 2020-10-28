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
     * 新增物料
     *
     * @param wmsGood
     * @return
     */
    @Override
    @Operation(value = "新增物料")
    public int addWmsGood(WmsGood wmsGood) {
        return wmsGoodDao.addWmsGood(wmsGood);
    }

    /**
     * 修改物料
     *
     * @param wmsGood
     * @return
     */
    @Override
    @Operation(value = "更新物料信息")
    public int updateWmsGood(WmsGood wmsGood) {
        return wmsGoodDao.updateWmsGood(wmsGood);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "批量删除物料")
    public int deleteWmsGoodById(List<String> id) {
        return wmsGoodDao.deleteWmsGoodById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param wmsGood
     * @return
     */
    @Override
    public List<WmsGood> findWmsGood(WmsGood wmsGood) {
        return wmsGoodDao.findWmsGood(wmsGood);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<WmsGood> wmsGoodList = wmsGoodDao.wmsGoodPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"物料编码", "物料名称", "物料型号 ","物料单位", "性能类别", "检修类别"};
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
    public void wmsGoodImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            WmsGood wmsGood = null;
            for (int i = 1; i <= lastRow; i++) {
                // 物料编码
                wmsGood = new WmsGood();
                wmsGood.setGoodEncoding((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
                // 判断是否已经存在，通过cityID来判断
                List<WmsGood> list = wmsGoodDao.selectWmsGoodByGoodEncoding(wmsGood.getGoodEncoding());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    wmsGood = list.get(0);
                }
                HSSFCell cell = null;
                // 物料名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setGoodName(sheet.getRow(i).getCell(1).getStringCellValue());
                // 物料型号
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setGoodModel(sheet.getRow(i).getCell(2).getStringCellValue());
                // 物料单位
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setGoodUnit(sheet.getRow(i).getCell(3).getStringCellValue());
                // 性能类别
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setPerformStatus(Integer.parseInt(sheet.getRow(i).getCell(4).getStringCellValue()));
                // 检修类别
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                wmsGood.setOverhaulStatus(Integer.parseInt(sheet.getRow(i).getCell(5).getStringCellValue()));

                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    wmsGoodDao.addWmsGood(wmsGood);
                } else {
                    // 更新用户信息
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
