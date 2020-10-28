package com.java.activiti.dao.swylsb;

import com.java.activiti.model.swylsb.SwylsbJd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SwylsbJdDao {

    SwylsbJd findById(int ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int swylsbJdCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<SwylsbJd> swylsbJdPage(Map<String, Object> map);

    /**
     *
     * @Title: selectSupplierName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param sbName
     * @return
     */
    List<SwylsbJd> selectSwylsbJdName(@Param("sbName") String sbName);

    List<SwylsbJd> selectALL();

    int addSwylsbJd(SwylsbJd swylsbJd);

    int updateSwylsbJd(SwylsbJd swylsbJd);

    int deleteSwylsbJdById(List<String> id);

    List<SwylsbJd> findSwylsbJd(SwylsbJd swylsbJd);

    List<SwylsbJd> selectSwylsbJdBySwylsbJdID(int ID);

    List<SwylsbJd> findAllSwylsbJd();
}
