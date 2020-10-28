package com.java.activiti.service.impl;

import com.java.activiti.dao.CityDao;
import com.java.activiti.model.City;
import com.java.activiti.service.CityService;
import com.java.activiti.util.StringUtil;
import com.java.activiti.util.aop.Operation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("cityService")
public class CityServiceImpl implements CityService {

    @Resource
    private CityDao cityDao;

    @Override
    public List<City> cityPage(Map<String, Object> map) {
        return cityDao.cityPage(map);
    }

    @Override
    public int cityCount(Map<String, Object> map) {
        return cityDao.cityCount(map);
    }

    @Override
    public List<City> findCityName() {
        return cityDao.selectALL();
    }

    /**
     * 新增城市
     *
     * @param city
     * @return
     */
    @Operation(value = "新增城市")
    public int addCity(City city) {
        return cityDao.addCity(city);
    }

    /**
     * 修改城市
     *
     * @param city
     * @return
     */
    @Operation(value = "更新城市信息")
    public int updateCity(City city) {
        return cityDao.updateCity(city);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Operation(value = "批量删除城市")
    public int deleteCityById(List<String> id) {
        return cityDao.deleteCityById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param city
     * @return
     */
    public List<City> findCity(City city) {
        return cityDao.findCity(city);
    }

    public City findCityByName(String cityName) {
        return cityDao.findCityByName(cityName);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // 获取所有供应商信息
        List<City> cityList = cityDao.cityPage(map);
        // 1.创建excel工作薄
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.创建一个工作表
        HSSFSheet sheet = wk.createSheet("系统用户");
        // 3.写入表头
        HSSFRow row = sheet.createRow(0);
        // 表头
        String[] headerName = {"城市ID", "城市名称", "经度 ", "纬度"};
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
        for (City city : cityList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(city.getCityID());// 城市ID
            row.createCell(1).setCellValue(city.getCityName());// 城市名称
            row.createCell(2).setCellValue(city.getLongitude());// 经度
            row.createCell(3).setCellValue(city.getLatitude()); // 纬度
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
    @Operation(value = "Excel导入城市信息")
    @Override
    public void cityImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // 读取数据
            // 最后一行的行号
            int lastRow = sheet.getLastRowNum();
            City city = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                city = new City();
                city.setCityID((int) (sheet.getRow(i).getCell(0).getNumericCellValue()));
                // 判断是否已经存在，通过cityID来判断
                List<City> list = cityDao.selectCityByCityID(city.getCityID());
                if (list.size() > 0) {
                    // 说明存在相同，需要更新
                    city = list.get(0);
                }
                HSSFCell cell = null;
                // 城市名称
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                city.setCityName(sheet.getRow(i).getCell(1).getStringCellValue());
                //公园简拼
                city.setCityPinYin(StringUtil.ChineseToSpell(city.getCityName()));
                // 经度
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                city.setLongitude(sheet.getRow(i).getCell(2).getStringCellValue());
                // 纬度
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                city.setLatitude(sheet.getRow(i).getCell(3).getStringCellValue());
                if (list.size() == 0) {
                    // 说明不存在用户信息，需要新增
                    cityDao.addCity(city);
                } else {
                    // 更新用户信息
                    cityDao.updateCity(city);
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
