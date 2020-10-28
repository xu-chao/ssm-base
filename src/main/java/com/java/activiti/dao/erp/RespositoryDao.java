package com.java.activiti.dao.erp;

import com.java.activiti.model.erp.Respository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RespositoryDao {

    Respository findById(int REPO_ID);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int respositoryCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Respository> respositoryPage(Map<String, Object> map);

    /**
     *
     * @Title: selectRespositoryName
     * @Description: ��ѯ���ϣ��Զ���ȫ
     * @param REPO_ADDRESS
     * @return
     */
    List<Respository> selectRespositoryName(@Param("REPO_ADDRESS") String REPO_ADDRESS);

    List<Respository> selectALL();

    int addRespository(Respository respository);

    int updateRespository(Respository respository);

    int deleteRespositoryById(List<String> id);

    List<Respository> findRespository(Respository respository);

    /**
     *
     * @Title: selectGoodsByGoodsID
     * @Description: ֤��ID��ѯ��Ӧ�������
     * @author: ��
     * @param REPO_ID ����ID
     * @return
     */
    List<Respository> selectRespositoryByRespositoryID(int REPO_ID);

}
