package com.java.activiti.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UuidUtil {

    /**
     *
     * @Title: getRequest
     * @Description: 获取当前的request
     * @author: 许超
     * @return
     */
    public static String uuidUtil() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result1 = "";
        Random random = new Random();
        result1 += random.nextInt(10);
        String uuid = newDate+result1;//使用UUID加前缀命名文件，防止名字重复被覆盖
        return uuid;
    }
}
