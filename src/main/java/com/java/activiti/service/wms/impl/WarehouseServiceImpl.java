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
     * �����ֿ�
     *
     * @param warehouse
     * @return
     */
    @Override
    @Operation(value = "�����ֿ�")
    public int addWarehouse(Warehouse warehouse) {
        return warehouseDao.addWarehouse(warehouse);
    }

    /**
     * �޸Ĳֿ�
     *
     * @param warehouse
     * @return
     */
    @Override
    @Operation(value = "���²ֿ���Ϣ")
    public int updateWarehouse(Warehouse warehouse) {
        return warehouseDao.updateWarehouse(warehouse);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "����ɾ���ֿ�")
    public int deleteWarehouseById(List<String> id) {
        return warehouseDao.deleteWarehouseById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param warehouse
     * @return
     */
    @Override
    public List<Warehouse> findWarehouse(Warehouse warehouse) {
        return warehouseDao.findWarehouse(warehouse);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Warehouse> warehouseList = warehouseDao.warehousePage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"�ֿ�ID", "�ֿ�����", "�ֿ��ַ ", "����"};
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
        for (Warehouse warehouse : warehouseList) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(warehouse.getWhId());// WarehouseID
            row.createCell(1).setCellValue(warehouse.getWhName());// WarehouseName
            row.createCell(2).setCellValue(warehouse.getWhAddress());// WarehouseSysName
            row.createCell(3).setCellValue(warehouse.getWhDesc());// WarehouseType
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
    @Operation(value = "Excel����ֿ���Ϣ")
    @Override
    public void warehouseImport(InputStream is) throws IOException {
//        HSSFWorkbook wb = null;
//        try {
//            wb = new HSSFWorkbook(is);
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // ��ȡ����
//            // ���һ�е��к�
//            int lastRow = sheet.getLastRowNum();
//            Warehouse warehouse = null;
//            for (int i = 1; i <= lastRow; i++) {
//                // cityID
//                warehouse = new Warehouse();
//                warehouse.setWarehouseId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
//                // �ж��Ƿ��Ѿ����ڣ�ͨ��cityID���ж�
//                List<Warehouse> list = warehouseDao.selectWarehouseByWarehouseID(warehouse.getWarehouseId());
//                if (list.size() > 0) {
//                    // ˵��������ͬ����Ҫ����
//                    warehouse = list.get(0);
//                }
//                HSSFCell cell = null;
//                // ��������
//                cell = sheet.getRow(i).getCell(1);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseName(sheet.getRow(i).getCell(1).getStringCellValue());
//                // ��������
//                cell = sheet.getRow(i).getCell(2);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseSysName(sheet.getRow(i).getCell(2).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(3);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseType(sheet.getRow(i).getCell(3).getStringCellValue());
//                // ����
//                cell = sheet.getRow(i).getCell(4);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseCode(sheet.getRow(i).getCell(4).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(5);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseUnit(sheet.getRow(i).getCell(5).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(6);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseSize(sheet.getRow(i).getCell(6).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(7);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseFunction(sheet.getRow(i).getCell(7).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(8);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseMessage(sheet.getRow(i).getCell(8).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(9);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseElse(sheet.getRow(i).getCell(9).getStringCellValue());
//                // γ��
//                cell = sheet.getRow(i).getCell(10);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                warehouse.setWarehouseLink(sheet.getRow(i).getCell(10).getStringCellValue());
//                if (list.size() == 0) {
//                    // ˵���������û���Ϣ����Ҫ����
//                    warehouseDao.addWarehouse(warehouse);
//                } else {
//                    // �����û���Ϣ
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
