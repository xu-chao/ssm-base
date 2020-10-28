package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.SwylsbJh;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbJhDao {

    List<SwylsbJh> findById(int ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbJhCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SwylsbJh> swylsbJhPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param sbName
     * @return
     */
    List<SwylsbJh> selectSwylsbJhName(@Param("sbName") String sbName);

    List<SwylsbJh> selectALL();

    int addSwylsbJh(SwylsbJh swylsbJh);

    int updateSwylsbJh(List<SwylsbJh> swylsbJhs);

    int deleteSwylsbJhById(List<String> id);

    List<SwylsbJh> findSwylsbJh(SwylsbJh swylsbJh);

    List<SwylsbJh> selectSwylsbJhBySwylsbJhID(int ID);

    List<SwylsbJh> findAllSwylsbJh();
}
