package com.java.activiti.service.question.impl;

import com.java.activiti.dao.question.QuestionDao;
import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.service.question.QuestionService;
import com.java.activiti.util.Excel;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionDao questionDao;

    public List<QuestionInfo> questionPage(Map<String, Object> map) {
        return questionDao.questionPage(map);
    }

    @Override
    public List<QuestionInfo> questionThrough(Map<String, Object> map) {
        return questionDao.questionThrough(map);
    }

    public int questionCount(Map<String, Object> map) {
        return questionDao.questionCount(map);
    }

    @Override
    public int questionThoughtCount(Map<String, Object> map) {
        return questionDao.questionThoughtCount(map);
    }

    public int addQuestion(QuestionInfo question) {
        return questionDao.addQuestion(question);
    }

    public QuestionInfo findById(String id) {
        return questionDao.findById(id);
    }

    public int updateQuestion(QuestionInfo question) {
        return questionDao.updateQuestion(question);
    }

    public QuestionInfo getQuestionByTaskId(String processInstanceId) {
        return questionDao.getQuestionByTaskId(processInstanceId);
    }

    public List<QuestionInfo> selectTaskByProcessID(List<String> map) {
        return questionDao.selectTaskByProcessID(map);
    }

    @Override
    public void export(OutputStream os, HttpServletRequest request, Map<String, Object> map) throws IOException {
        Excel excel = new Excel();
        List<Map> data = new ArrayList<Map>();
//      ��ȡ���й�Ӧ����Ϣ
        List<QuestionInfo> questionInfos = questionDao.questionThrough(map);

        for (QuestionInfo question : questionInfos) {
            LinkedHashMap<String, Object> e = new LinkedHashMap<String, Object>();
            e.put("0", question.getQuestionID());// ���ⵥ��
            e.put("1",question.getUser().getAllName());// �ύ��
            e.put("2", question.getPark().getParkName());// ��������
            e.put("3", question.getProject().getProjectName());// ��Ŀ����
            e.put("4", question.getSubjectID());// רҵ����
            e.put("5", question.getProblemTitle());// ��������
            e.put("6", question.getProblemText());// ��������
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(question.getQuestionDate());
            e.put("7", dateString);// �ύ����
            data.add(e);
        }
        String[] headNames = { "���ⵥ��", "�ύ��", "��������", "��Ŀ����", "רҵ����", "�������","��������", "�ύ����" };
        String[] keys = { "0",  "1", "2","3","4","5","6","7"};
        int colWidths[] = { 300, 200, 200, 200, 200,300,300 };
        InputStream input = (excel.getExcelFile(data, "��λ", headNames, keys, colWidths,request));
        HSSFWorkbook book = new HSSFWorkbook(input);
        book.write(os);
    }

}
