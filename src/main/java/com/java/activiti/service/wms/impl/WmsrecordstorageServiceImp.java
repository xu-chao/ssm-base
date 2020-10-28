package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WmsrecordstorageDao;
import com.java.activiti.dao.wms.WmssupplierDao;
import com.java.activiti.model.wms.Wmsrecordstorage;
import com.java.activiti.model.wms.Wmssupplier;
import com.java.activiti.service.wms.WmsrecordstorageService;
import com.java.activiti.service.wms.WmssupplierService;
import com.java.activiti.util.aop.Operation;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("wmsrecordstorageService")
public class WmsrecordstorageServiceImp implements WmsrecordstorageService {

    @Resource
    private WmsrecordstorageDao wmsrecordstorageDao;

    @Override
    public List<Wmsrecordstorage> wmsrecordstoragePage(Map<String, Object> map) {
        return wmsrecordstorageDao.wmsrecordstoragePage(map);
    }

    @Override
    public int wmsrecordstorageCount(Map<String, Object> map) {
        return wmsrecordstorageDao.wmsrecordstorageCount(map);
    }


    @Override
    public Map<String, Object> stockInOperation(String goodsID, Integer repositoryID, long number) throws Exception {
        return null;
    }

    @Override
    public boolean storageIncrease(String goodsID, Integer repositoryID, long number) throws Exception {
        // ��������������Ŀ����Ч��
        if (number < 0) {
            return false;
        }
        synchronized (this) {
            // ����Ӧ�Ŀ���¼�Ƿ����
            Wmsrecordstorage storage = getStorage(goodsID, repositoryID);
            if (storage != null) {
                long newStorage = storage.getRecordNumber() + number;
                updateStorage(goodsID, repositoryID, newStorage);
            } else {
                addNewStorage(goodsID, repositoryID, number);
            }
        }
        return true;


    }

    @Override
    public boolean storageDecrease(String goodsID, Integer repositoryID, long number) throws Exception {
        synchronized (this) {
            // ����Ӧ�Ŀ���¼�Ƿ����
            Wmsrecordstorage storage = getStorage(goodsID, repositoryID);
            if (null != storage) {
                // ����������Ŀ�ķ�Χ�Ƿ����
                if (number < 0 || storage.getRecordNumber() < number) {
                    return false;
                }

                long newStorage = storage.getRecordNumber() - number;
                updateStorage(goodsID, repositoryID, newStorage);
                return true;
            } else {
                return false;
            }
        }
    }
    /**
     * ��ȡָ������ID���ֿ�ID��Ӧ�Ŀ���¼
     *
     * @param goodsID      ����ID
     * @param repositoryID �ֿ�ID
     * @return �������򷵻ض�Ӧ�ļ�¼�����򷵻�null
     */
    private Wmsrecordstorage getStorage(String goodsID, Integer repositoryID) {
        Wmsrecordstorage storage = null;
        List<Wmsrecordstorage> storageList = wmsrecordstorageDao.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
        if (!storageList.isEmpty()) {
            storage = storageList.get(0);
        }
        return storage;
    }

    /**
     * ����һ������¼
     *
     * @param goodsID      ָ���Ļ���ID
     * @param repositoryID ָ���Ĳֿ�ID
     * @param number       ���µĿ������
     * @return ����һ��booleanֵ��ֵΪtrue������³ɹ����������ʧ��
     */
    @Operation(value = "�޸Ŀ���¼")
    @Override
    public boolean updateStorage(String goodsID, Integer repositoryID, long number) throws Exception {
        try {
            boolean isUpdate = false;

            // validate
            List<Wmsrecordstorage> storageList = wmsrecordstorageDao.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (storageList != null && !storageList.isEmpty()) {
                if (number >= 0) {
                    // update
                    Wmsrecordstorage storage = storageList.get(0);
                    storage.setRecordNumber(number);
                    wmsrecordstorageDao.update(storage);
                    isUpdate = true;
                }
            }

            return isUpdate;
        } catch (PersistenceException e) {
            throw new Exception(e);
        }
    }
    /**
     * ���һ������¼
     *
     * @param goodsID      ָ���Ļ���ID
     * @param repositoryID ָ���Ĳֿ�ID
     * @param number       �������
     * @return ����һ��booleanֵ��ֵΪtrue������³ɹ����������ʧ��
     */
    @Operation(value = "��ӿ���¼")
    @Override
    public boolean addNewStorage(String goodsID, Integer repositoryID, long number) throws Exception {
        try {
            boolean isAvailable = true;

            List<Wmsrecordstorage> storageList = wmsrecordstorageDao.selectByGoodsIDAndRepositoryID(goodsID, repositoryID);
            if (!(storageList != null && storageList.isEmpty()))
                isAvailable = false;

            if (isAvailable) {
                // insert
                Wmsrecordstorage storage = new Wmsrecordstorage();
                storage.setRecordGoodId(goodsID);
                storage.setRecordWarehouseId(repositoryID);
                storage.setRecordNumber(number);
                wmsrecordstorageDao.insert(storage);
            }

            return isAvailable;
        } catch (PersistenceException e) {
            throw new Exception(e);
        }
    }

//    /**
//     * ���ݵ���
//     */
//    @Operation(value = "Excel���빩Ӧ����Ϣ")
//    @Override
//    public void wmssupplierImport(InputStream is) throws IOException {

}
