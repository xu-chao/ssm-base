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
        // 检查货物库存增加数目的有效性
        if (number < 0) {
            return false;
        }
        synchronized (this) {
            // 检查对应的库存记录是否存在
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
            // 检查对应的库存记录是否存在
            Wmsrecordstorage storage = getStorage(goodsID, repositoryID);
            if (null != storage) {
                // 检查库存减少数目的范围是否合理
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
     * 获取指定货物ID，仓库ID对应的库存记录
     *
     * @param goodsID      货物ID
     * @param repositoryID 仓库ID
     * @return 若存在则返回对应的记录，否则返回null
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
     * 更新一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       更新的库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Operation(value = "修改库存记录")
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
     * 添加一条库存记录
     *
     * @param goodsID      指定的货物ID
     * @param repositoryID 指定的仓库ID
     * @param number       库存数量
     * @return 返回一个boolean值，值为true代表更新成功，否则代表失败
     */
    @Operation(value = "添加库存记录")
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
//     * 数据导入
//     */
//    @Operation(value = "Excel导入供应商信息")
//    @Override
//    public void wmssupplierImport(InputStream is) throws IOException {

}
