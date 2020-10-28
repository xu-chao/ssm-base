package com.java.activiti.dao;

import com.java.activiti.model.City;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CityDao {

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int cityCount(Map<String,Object> map);

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<City> cityPage(Map<String,Object> map);

    /**
     *
     * @Title: selectRoleName
     * @Description: ��ѯ���У��Զ���ȫ
     * @param cityName
     * @return
     */
    List<City> selectCityName(@Param("cityName")String cityName);

    List<City> selectALL();

    int addCity(City city);

    int updateCity(City city);

    int deleteCityById(List<String> id);

    List<City> findCity(City city);

    /**
     *
     * @Title: selectCityByCityID
     * @Description: ֤��ID��ѯ��Ӧ����
     * @author: ��
     * @param cityID ����ID
     * @return
     */
    List<City> selectCityByCityID(int cityID);

    City findById(int cityID);

    City findCityByName(@Param("cityName") String cityName);
}
