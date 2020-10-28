package com.java.activiti.dao.gaizao;

import com.java.activiti.model.Project;
import com.java.activiti.model.gaizao.GaiZao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GaiZaoDao {

    /**
     * ��ҳ����
     *
     * @return �������з��������ļ�¼����
     */
    int gaiZaoCount(List<Project> projects);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<GaiZao> gaiZaoPage(@Param("map")Map<String, Object> map, @Param("list")List<Project> projects);/**

     * ��ȡ�ù�԰�����еļ�¼
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<GaiZao> gaiZaoDetail(Map<String, Object> map);

    /**
     * ���� �����¼
     *
     * @param gaiZao
     * @return ���� 0 1
     */
    int addGaiZao(GaiZao gaiZao);

    /**
     * ���� �����¼
     *
     * @param gaiZao
     * @return ���� 0 1
     */
    int updateGaiZao(GaiZao gaiZao);
    /**
     * ɾ�� �����¼
     *
     * @return ���� 0 1
     */
    int deleteGaiZao(List<String> id);

//    /**
//     *
//     * @Title: selectRoleName
//     * @Description: ��ѯ���У��Զ���ȫ
//     * @param cityName
//     * @return
//     */
//    List<City> selectCityName(@Param("cityName") String cityName);
//
//    List<City> selectALL();
//

//
//    int updateCity(City city);
//
//    int deleteCityById(List<String> id);
//
//    List<City> findCity(City city);
//
//    /**
//     *
//     * @Title: selectCityByCityID
//     * @Description: ֤��ID��ѯ��Ӧ����
//     * @author: ��
//     * @param cityID ����ID
//     * @return
//     */
//    List<City> selectCityByCityID(int cityID);
//
//    City findById(int cityID);
}
