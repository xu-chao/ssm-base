package com.java.activiti.util;

import org.springframework.util.DigestUtils;

public class MD5Util {

    //slat���ڻ콻md5
    private static final String slat = "&%5123***&&%%$$#@";

    /**
     * ����md5
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        String base = str + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;

    }
}
