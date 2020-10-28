package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.Wmsrecordout;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WmsrecordoutDao {

    Wmsrecordout findById(Integer outId);

    /**
     * 分页总数
     *
     * @param map
     * @return 返回所有符合条件的记录总数
     */
    int wmsrecordoutCount(Map<String, Object> map);

    /**
     * 自定义分页对象
     *
     * @param map
     * @return 返回所有符合条件的记录的分页对象
     */
    List<Wmsrecordout> wmsrecordoutPage(Map<String, Object> map);

    int addWmsrecordout(Wmsrecordout wmsrecordout);

    int updateWmsrecordout(Wmsrecordout wmsrecordout);

    int deleteWmsrecordoutById(List<String> outId);

    List<Wmsrecordout> findWmsrecordout(Wmsrecordout wmsrecordout);

    Wmsrecordout findWmsrecordoutByExcel(Wmsrecordout wmsrecordout);

    /**
     *
     * @Title: selectProjectNameLike
     * @Description: 查询物料名称，自动补全
     * @param projectName
     * @return
     */
    List<Wmsrecordout> selectProjectNameLike(@Param("projectName") String projectName);

}
