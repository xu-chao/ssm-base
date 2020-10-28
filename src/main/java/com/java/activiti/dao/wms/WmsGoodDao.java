package com.java.activiti.dao.wms;

import com.java.activiti.model.wms.WmsGood;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WmsGoodDao {

    WmsGood findById(String wmsGoodId);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int wmsGoodCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<WmsGood> wmsGoodPage(Map<String, Object> map);

    List<WmsGood> selectALL();

    int addWmsGood(WmsGood wmsGood);

    int updateWmsGood(WmsGood wmsGood);

    int deleteWmsGoodById(List<String> id);

    List<WmsGood> findWmsGood(WmsGood wmsGood);

    /**
     * ���ϱ������
     * @param goodEncoding
     * @return
     */
    List<WmsGood> selectWmsGoodByGoodEncoding(String goodEncoding);

    WmsGood findWmsGoodByExcel(WmsGood wmsGood);


    /**
     *
     * @Title: selectGoodEncodingLike
     * @Description: ��ѯ���ϱ��룬�Զ���ȫ
     * @param goodEncoding
     * @return
     */
    List<WmsGood> selectGoodEncodingLike(@Param("goodEncoding") String goodEncoding);

    /**
     *
     * @Title: selectGoodNameLike
     * @Description: ��ѯ�������ƣ��Զ���ȫ
     * @param goodName
     * @return
     */
    List<WmsGood> selectGoodNameLike(@Param("goodName") String goodName);

    /**
     *
     * @Title: selectGoodModelLike
     * @Description: ��ѯ���ͣ��Զ���ȫ
     * @param goodModel
     * @return
     */
    List<WmsGood> selectGoodModelLike(@Param("goodModel") String goodModel);


}
