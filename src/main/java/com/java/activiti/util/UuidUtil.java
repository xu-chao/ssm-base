package com.java.activiti.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UuidUtil {

    /**
     *
     * @Title: getRequest
     * @Description: ��ȡ��ǰ��request
     * @author: ��
     * @return
     */
    public static String uuidUtil() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result1 = "";
        Random random = new Random();
        result1 += random.nextInt(10);
        String uuid = newDate+result1;//ʹ��UUID��ǰ׺�����ļ�����ֹ�����ظ�������
        return uuid;
    }
}
