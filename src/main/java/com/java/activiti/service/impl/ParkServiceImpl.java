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
     * ��������
     * @param park
     * @return
     */
    @Operation(value = "��������")
    public int addPark(Park park){
        return parkDao.addPark(park);
    }

    /**
     * �޸ĳ���
     * @param park
     * @return
     */
    @Operation(value = "���³�����Ϣ")
    public int updatePark(Park park){
        return parkDao.updatePark(park);
    }

    /**
     * ����ɾ����԰
     * @param id
     * @return
     */
    @Operation(value = "����ɾ����԰")
    public int deleteParkById(List<String> id){
        return parkDao.deleteParkById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     * @param cityID
     * @return
     */
    public Park findParkByCityID(int cityID, String parkName){
        return parkDao.findParkByCityID(cityID,parkName);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     * @param park
     * @return
     */
    public List<Park> findPark(Park park){
        return parkDao.findPark(park);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String,Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Park> parkList = parkDao.parkPage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = { "��԰ID", "��԰����", "����"};
        // �п�
        int[] columnWidths = { 6000, 6000, 6000, 6000 };
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
        for (Park park : parkList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(park.getParkID());// ��԰ID
            row.createCell(1).setCellValue(park.getParkName());// ��԰����
            row.createCell(2).setCellValue(park.getCity().getCityName());// ����
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
    @Override
    public void parkImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Park park = null;
            for (int i = 1; i <= lastRow; i++) {
                // parkID
                park = new Park();
                park.setParkID((int)(sheet.getRow(i).getCell(0).getNumericCellValue()));
                // �ж��Ƿ��Ѿ����ڣ�ͨ��parkID���ж�
                List<Park> list = parkDao.selectParkByParkID(park.getParkID());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    park = list.get(0);
                }
                HSSFCell cell = null;
                // ��԰����
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                park.setParkName(sheet.getRow(i).getCell(1).getStringCellValue());
                //��԰���
                park.setParkAb(park.getParkAb());
                // ��������
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                park.setParkID(cityDao.selectCityName(sheet.getRow(i).getCell(2).getStringCellValue()).get(0).getCityID());

                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    parkDao.addPark(park);
                } else {
                    // �����û���Ϣ
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
