package com.java.activiti.service.gaizao.impl;

import com.java.activiti.dao.gaizao.HuiyishiDao;
import com.java.activiti.model.gaizao.Huiyishi;
import com.java.activiti.service.gaizao.HuiyishiService;
import com.java.activiti.util.Excel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("huiyishiService")
public class HuiyishiServiceImpl implements HuiyishiService {

    @Resource
    private HuiyishiDao huiyishiDao;

    public List<Huiyishi> huiyishiPage(Map<String, Object> map) {
        return huiyishiDao.huiyishiPage(map);
    }

    @Override
    public List<Huiyishi> huiyishiThrough(Map<String, Object> map) {
        return huiyishiDao.huiyishiThrough(map);
    }

    public int huiyishiCount(Map<String, Object> map) {
        return huiyishiDao.huiyishiCount(map);
    }

    @Override
    public int huiyishiThoughtCount(Map<String, Object> map) {
        return huiyishiDao.huiyishiThoughtCount(map);
    }

    public int addHuiyishi(Huiyishi huiyishi) {
        return huiyishiDao.addHuiyishi(huiyishi);
    }

    public Huiyishi findById(String id) {
        return huiyishiDao.findById(id);
    }

    public int updateHuiyishi(Huiyishi huiyishi) {
        return huiyishiDao.updateHuiyishi(huiyishi);
    }

    public Huiyishi getHuiyishiByTaskId(String processInstanceId) {
        return huiyishiDao.getHuiyishiByTaskId(processInstanceId);
    }

    public List<Huiyishi> selectTaskByProcessID(List<String> map) {
        return huiyishiDao.selectTaskByProcessID(map);
    }

    @Override
    public void export(OutputStream os, HttpServletRequest request, Map<String, Object> map) throws IOException {
        Excel excel = new Excel();
        List<Map> data = new ArrayList<Map>();
//      ��ȡ���й�Ӧ����Ϣ
        List<Huiyishi> huiyishiInfos = huiyishiDao.huiyishiThrough(map);

        for (Huiyishi huiyishi : huiyishiInfos) {
            LinkedHashMap<String, Object> e = new LinkedHashMap<String, Object>();
            e.put("0", huiyishi.getHysID());// ���ⵥ��
            e.put("1",huiyishi.getUserID().getAllName());// �ύ��
            e.put("2", huiyishi.getPark().getParkName());// ��������
            e.put("3", huiyishi.getHysText());// ��Ŀ����
            e.put("4", huiyishi.getRemark1());// רҵ����
            e.put("5", huiyishi.getRemark2());// ��������
            e.put("6", huiyishi.getUserAdviser().getAllName());// ������
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(huiyishi.getHysDate());
            e.put("7", dateString);// �ύ����
            data.add(e);
        }
        String[] headNames = { "ID", "�ύ��", "��԰����", "����������", "�ύ�˱�ע", "�����˱�ע","������", "�ύ����" };
        String[] keys = { "0",  "1", "2","3","4","5","6","7"};
        int colWidths[] = { 300, 200, 200, 200, 200,300,300 };
        InputStream input = (excel.getExcelFile(data, "��λ", headNames, keys, colWidths,request));
        HSSFWorkbook book = new HSSFWorkbook(input);
        book.write(os);
    }

}
