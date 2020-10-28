package com.java.activiti.service.wms.impl;

import com.java.activiti.dao.wms.WmsrecordoutDao;
import com.java.activiti.model.wms.Wmsrecordout;
import com.java.activiti.service.wms.WmsrecordoutService;
import com.java.activiti.util.aop.Operation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service("wmsrecordoutService")
public class WmsrecordoutServiceImpl implements WmsrecordoutService {

    @Resource
    private WmsrecordoutDao wmsrecordoutDao;

    @Override
    public List<Wmsrecordout> wmsrecordoutPage(Map<String, Object> map) {
        return wmsrecordoutDao.wmsrecordoutPage(map);
    }

    @Override
    public int wmsrecordoutCount(Map<String, Object> map) {
        return wmsrecordoutDao.wmsrecordoutCount(map);
    }


    /**
     * 新增入库
     *
     * @param wmsrecordout
     * @return
     */
    @Override
    @Operation(value = "新增入库")
    public int addWmsrecordout(Wmsrecordout wmsrecordout) {
        return wmsrecordoutDao.addWmsrecordout(wmsrecordout);
    }

    /**
     * 修改入库
     *
     * @param wmsrecordout
     * @return
     */
    @Override
    @Operation(value = "更新入库信息")
    public int updateWmsrecordout(Wmsrecordout wmsrecordout) {
        return wmsrecordoutDao.updateWmsrecordout(wmsrecordout);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Override
    @Operation(value = "批量删除入库")
    public int deleteWmsrecordoutById(List<String> id) {
        return wmsrecordoutDao.deleteWmsrecordoutById(id);
    }

    /**
     * 判断城市是否已经存在
     *
     * @param wmsrecordout
     * @return
     */
    @Override
    public List<Wmsrecordout> foutdWmsrecordout(Wmsrecordout wmsrecordout) {
        return wmsrecordoutDao.findWmsrecordout(wmsrecordout);
    }

    /**
     * 导出excel文件
     */
    @Override
    public void export(OutputStream os, Map<String, Object> map) {

    }

    /**
     * 数据导入
     */
    @Operation(value = "Excel导入入库信息")
    @Override
    public void wmsrecordoutImport(InputStream is) throws IOException {
    }

    @Override
    public Wmsrecordout selectWmsrecordoutById(Integer supplierId) {
        return wmsrecordoutDao.findById(supplierId);
    }

    @Override
    public List<Wmsrecordout> selectProjectNameLike(String q) {
        return wmsrecordoutDao.selectProjectNameLike(q);
    }
}
