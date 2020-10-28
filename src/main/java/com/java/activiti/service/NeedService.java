package com.java.activiti.service;

import com.java.activiti.model.MyTask;
import com.java.activiti.model.Need;
import com.java.activiti.model.NeedGoods;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface NeedService {

    List<Need> needPage(Map<String, Object> map);

    int needCount(Map<String, Object> map);

    int needCountByMonth();

    int addNeed(Need need);

    Need findById(String id);

    Need findNeedById(String id);

    int updateNeed(Need need);

    int updateNeedByNId(Need need);

    Need getNeedByTaskId(String processInstanceId);
/**
 * @author      LIUHD
 * ���� map
 * @description ���� processInstanceId ����ID ������ѯneed����
 * @date        2019/12/30 17:25
 * @Version     1.0
 */
    List<Need> selectTaskByProcessID(List<String> maps);

    void export(OutputStream os, Map<String, Object> map);

    /**
     *
     * @Title: goodsImport
     * @Description: ����excel
     * @author: ��
     * @param is
     * @throws IOException
     */
    List<NeedGoods> goodsImport(InputStream is) throws IOException;

    void needImport(InputStream is) throws IOException;

    /**
     * �ж������Ƿ��Ѿ�����
     * @param need
     * @return
     */
    List<Need> findNeed(Need need);
}
