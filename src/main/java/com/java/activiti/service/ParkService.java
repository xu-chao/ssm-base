package com.java.activiti.service;

import com.java.activiti.model.Park;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface ParkService {

    /**
     * �Զ����ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼�ķ�ҳ����
     */
    List<Park> parkPage(Map<String, Object> map);

    /**
     * ��ҳ����
     *
     * @param map
     * @return �������з��������ļ�¼����
     */
    int parkCount(Map<String, Object> map);

    /**
     *
     * @Title: findCityName
     * @Description: like��ѯ��ɫ��
     * @param parkName ��ɫ��
     * @return
     */
    List<Park> findParkName(String parkName);

    Park findParkByID(int parkID);

    List<Park> findALL();

    /**
     * �޸Ĺ�԰
     * @param park
     * @return
     */
    int updatePark(Park park);

    /**
     * ��������
     * @param park
     * @return
     */
    int addPark(Park park);

    /**
     * ����ɾ����԰
     * @param id
     * @return
     */
    int deleteParkById(List<String> id);
//
    /**
     * �жϹ�԰�Ƿ��Ѿ�����
     * @param park
     * @return
     */
    List<Park> findPark(Park park);

    /**
     *
     * @Title: export
     * @Description: ����excel
     * @author: ��
     * @param os
     * @param map
     */
    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: parkImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    void parkImport(InputStream is) throws IOException;

    Park findParkByCityID(int cityID, String parkName);

}
