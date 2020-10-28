package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.SwylsbXq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbXqDao {

    SwylsbXq findById(int ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbXqCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SwylsbXq> swylsbXqPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param sbName
     * @return
     */
    List<SwylsbXq> selectSwylsbXqName(@Param("sbName") String sbName);

    List<SwylsbXq> selectALL();

    int addSwylsbXq(SwylsbXq swylsbXq);

    int updateSwylsbXq(SwylsbXq swylsbXq);

    int deleteSwylsbXqById(List<String> id);

    List<SwylsbXq> findSwylsbXq(SwylsbXq swylsbXq);

    List<SwylsbXq> selectSwylsbXqBySwylsbXqID(int ID);

    List<SwylsbXq> findAllSwylsbXq();
}
