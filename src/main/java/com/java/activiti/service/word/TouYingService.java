package com.java.activiti.service.word;

import com.java.activiti.model.question.QuestionInfo;
import com.java.activiti.model.word.TouYing;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface TouYingService {
    /**
     * ����  ͶӰ�豸��װ��������
     * @return
     */
    int addTouYing(TouYing touYing);

    List<TouYing> touYingPage(Map<String, Object> map);

    int touYingCount(Map<String, Object> map);

     TouYing findById(String id);
}
