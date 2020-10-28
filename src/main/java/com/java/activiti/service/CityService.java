package com.java.activiti.service;

import com.java.activiti.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CityService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<City> cityPage(Map<String,Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int cityCount(Map<String,Object> map);

    /**
     *
     * @Title: findCityName
     * @Description: like��ѯ��ɫ��
     * @return
     */
    List<City> findCityName();

    /**
     * �޸ĳ���
     * @param city
     * @return
     */
    int updateCity(City city);

    /**
     * ��������
     * @param city
     * @return
     */
    int addCity(City city);

    /**
     * ����ɾ������
     * @param id
     * @return
     */
    int deleteCityById(List<String> id);

    /**
     * �жϳ����Ƿ��Ѿ�����
     * @param city
     * @return
     */
    List<City> findCity(City city);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String,Object> map);

    /**
     *
     * @Title: cityImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void cityImport(InputStream is) throws IOException;

    City findCityByName(String cityName);
}
