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
     * ��������
     *
     * @param storage
     * @return
     */
    public int addStorage(Storage storage) {
        return storageDao.addStorage(storage);
    }

    /**
     * �޸�����
     *
     * @param storage
     * @return
     */
    public int updateStorage(Storage storage) {
        return storageDao.updateStorage(storage);
    }

    /**
     * ����ɾ������
     *
     * @param id
     * @return
     */
    public int deleteStorageById(List<String> id) {
        return storageDao.deleteStorageById(id);
    }

    /**
     * �жϳ����Ƿ��Ѿ�����
     *
     * @param storage
     * @return
     */
    public List<Storage> findStorage(Storage storage) {
        return storageDao.findStorage(storage);
    }

    /**
     * ����excel�ļ�
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {
        // ��ȡ���й�Ӧ����Ϣ
        List<Storage> storageList = storageDao.storagePage(map);
        // 1.����excel������
        HSSFWorkbook wk = new HSSFWorkbook();
        // 2.����һ��������
        HSSFSheet sheet = wk.createSheet("ϵͳ�û�");
        // 3.д���ͷ
        HSSFRow row = sheet.createRow(0);
        // ��ͷ
        String[] headerName = {"���Id", "��������", "��������", "ʵ������", "�ϸ�����", "���ϸ�����"};
        // �п�
        int[] columnWidths = {6000, 6000, 6000, 6000, 6000, 6000};
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
    public void storageImport(InputStream is) throws IOException {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            // ��ȡ����
            // ���һ�е��к�
            int lastRow = sheet.getLastRowNum();
            Storage storage = null;
            for (int i = 1; i <= lastRow; i++) {
                storage = new Storage();
                storage.setStorageId((String) (sheet.getRow(i).getCell(0).getStringCellValue()));
                List<Storage> list = storageDao.selectStorageByStorageID(storage.getStorageId());
                if (list.size() > 0) {
                    // ˵��������ͬ����Ҫ����
                    storage = list.get(0);
                }
                HSSFCell cell = null;
                // ��������
                cell = sheet.getRow(i).getCell(1);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMount((int) sheet.getRow(i).getCell(1).getNumericCellValue());
                // ��������
                cell = sheet.getRow(i).getCell(2);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountBack((int)sheet.getRow(i).getCell(2).getNumericCellValue());
                // ����
                cell = sheet.getRow(i).getCell(3);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountIn((int)sheet.getRow(i).getCell(3).getNumericCellValue());
                // ����
                cell = sheet.getRow(i).getCell(4);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountQualified((int) sheet.getRow(i).getCell(4).getNumericCellValue());
                // γ��
                cell = sheet.getRow(i).getCell(5);
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                storage.setMountNotQualified((int)sheet.getRow(i).getCell(5).getNumericCellValue());
                if (list.size() == 0) {
                    // ˵���������û���Ϣ����Ҫ����
                    storageDao.addStorage(storage);
                } else {
                    // �����û���Ϣ
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
