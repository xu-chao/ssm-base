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
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsrecordoutCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
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
     * @Description: ��ѯ�������ƣ��Զ���ȫ
     * @param projectName
     * @return
     */
    List<Wmsrecordout> selectProjectNameLike(@Param("projectName") String projectName);

}
