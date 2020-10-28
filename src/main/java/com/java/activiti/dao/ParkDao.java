package com.java.activiti.dao;

import com.java.activiti.model.Park;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ParkDao {

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int parkCount(Map<String, Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Park> parkPage(Map<String, Object> map);


    List<Park> selectParkName(@Param("parkName") String parkName);
    List<Park> selectALL();

    int addPark(Park park);

    int updatePark(Park park);

    int deleteParkById(List<String> id);

    List<Park> findPark(Park park);

    /**
     *
     * @Title: selectCityByCityID
     * @Description: ֤��ID��ѯ��Ӧ����
     * @author: ��
     * @param parkID ����ID
     * @return
     */
    List<Park> selectParkByParkID(int parkID);

     Park findById(int parkID);

     Park findParkByCityID(@Param("cityID") int cityID,@Param("parkName") String parkName);
}
