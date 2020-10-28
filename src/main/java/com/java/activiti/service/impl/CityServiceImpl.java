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
     * ��������
     *
     * @param city
     * @return
     */
    @Operation(value = "��������")
    public int addCity(City city) {
        return cityDao.addCity(city);
    }

    /**
     * �޸ĳ���
     *
     * @param city
     * @return
     */
    @Operation(value = "���³�����Ϣ")
    public int updateCity(City city) {
        return cityDao.updateCity(city);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Operation(value = "����ɾ������")
    public int deleteCityById(List<String> id) {
        return cityDao.deleteCityById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
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
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<City> cityList = cityDao.cityPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"����ID", "��������", "���� ", "γ��"};
        // �п�
        int[] columnWidths = {6000, 6000, 6000, 6000};
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
        for (City city : cityList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(city.getCityID());// ����ID
            row.createCell(1).setCellValue(city.getCityName());// ��������
            row.createCell(2).setCellValue(city.getLongitude());// ����
            row.createCell(3).setCellValue(city.getLatitude()); // γ��
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
    @Operation(value = "Excel���������Ϣ")
    @Override
    public void cityImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            City city = null;
            for (int i = 1; i <= lastRow; i++) {
                // cityID
                city = new City();
                city.setCityID((int) (sheet.getRow(i).getCell(0).getNumericCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
                List<City> list = cityDao.selectCityByCityID(city.getCityID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    city = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                city.setCityName(sheet.getRow(i).getCell(1).getStringCellValue());
                //��԰��ƴ
                city.setCityPinYin(StringUtil.ChineseToSpell(city.getCityName()));
                // ����
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                city.setLongitude(sheet.getRow(i).getCell(2).getStringCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                city.setLatitude(sheet.getRow(i).getCell(3).getStringCellValue());
                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    cityDao.addCity(city);
                } else {
                    // �����û���Ϣ
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
