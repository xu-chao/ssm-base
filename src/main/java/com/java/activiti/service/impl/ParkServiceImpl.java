package com.java.activiti.service.impl;

import com.java.activiti.dao.CityDao;
import com.java.activiti.dao.ParkDao;
import com.java.activiti.model.City;
import com.java.activiti.model.Park;
import com.java.activiti.service.CityService;
import com.java.activiti.service.ParkService;
import com.java.activiti.util.StringUtil;
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
import java.util.List;
import java.util.Map;

@Service("parkService")
public class ParkServiceImpl implements ParkService {

    @Resource
    private ParkDao parkDao;
    @Resource
    private CityDao cityDao;

    @Override
    public List<Park> parkPage(Map<String, Object> map) {
        return parkDao.parkPage(map);
    }

    @Override
    public int parkCount(Map<String, Object> map) {
        return parkDao.parkCount(map);
    }

    @Override
    public List<Park> findParkName(String parkName) {
            return parkDao.selectParkName(parkName);
    }

    @Override
    public Park findParkByID(int parkID) {
        return parkDao.findById(parkID);
    }

    @Override
    public List<Park> findALL() {
        return parkDao.selectALL();
    }
    /**
     * 新增城市
     * @param park
     * @return
     */
    @Operation(value = "新增城市")
    public int addPark(Park park){
        return parkDao.addPark(park);
    }

    /**
     * 修改城市
     * @param park
     * @return
     */
    @Operation(value = "更新城市信息")
    public int updatePark(Park park){
        return parkDao.updatePark(park);
    }

    /**
     * 批量删除公园
     * @param id
     * @return
     */
    @Operation(value = "批量删除公园")
    public int deleteParkById(List<String> id){
        return parkDao.deleteParkById(id);
    }

    /**
     * 判断城市是否已经存在
     * @param cityID
     * @return
     */
    public Park findParkByCityID(int cityID, String parkName){
        return parkDao.findParkByCityID(cityID,parkName);
    }

    /**
     * 判断城市是否已经存在
     * @param park
     * @return
     */
    public List<Park> findPark(Park park){
        return parkDao.findPark(park);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // 获取所有供应商信息
        List<Park> parkList = parkDao.parkPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = { "公园ID", "公园名称", "城市"};
        // 列宽
        int[] columnWidths = { 6000, 6000, 6000, 6000 };
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
        for (Park park : parkList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(park.getParkID());// 公园ID
            row.createCell(1).setCellValue(park.getParkName());// 公园名称
            row.createCell(2).setCellValue(park.getCity().getCityName());// 城市
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
    public void parkImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            Park park = null;
            for (int i = 1; i <= lastRow; i++) {
                // parkID
                park = new Park();
                park.setParkID((int)(sheet.getRow(i).getCell(0).getNumericCellValue()));
                // 判断是否已经存在，通过parkID来判断
                List<Park> list = parkDao.selectParkByParkID(park.getParkID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    park = list.get(0);
                }
                HSSFCell cell = null;
                // 公园名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                park.setParkName(sheet.getRow(i).getCell(1).getStringCellValue());
                //公园简称
                park.setParkAb(park.getParkAb());
                // 城市名称
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                park.setParkID(cityDao.selectCityName(sheet.getRow(i).getCell(2).getStringCellValue()).get(0).getCityID());

                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    parkDao.addPark(park);
                } else {
                    // 更新用户信息
                    parkDao.updatePark(park);
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
