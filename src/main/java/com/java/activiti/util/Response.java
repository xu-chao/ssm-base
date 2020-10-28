package com.java.activiti.util;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class Response {

    public static final String RESPONSE_RESULT_SUCCESS = "success";
    public static final String RESPONSE_RESULT_ERROR = "error";

    // response �п���ʹ�õ�ֵ
    private static final String RESPONSE_RESULT = "result";
    private static final String RESPONSE_MSG = "msg";
    private static final String RESPONSE_DATA = "data";
    private static final String RESPONSE_TOTAL = "total";

    // �����Ӧ�е���Ϣ
    private Map<String,Object> responseContent;

    // Constructor
    Response() {
        this.responseContent = new HashedMap(10);
    }

    /**
     * ���� response ��״̬
     * @param result response ��״̬��ֵΪ success �� error
     */
    public void setResponseResult(String result){
        this.responseContent.put(Response.RESPONSE_RESULT,result);
    }

    /**
     * ���� response �ĸ�����Ϣ
     * @param msg response  �ĸ�����Ϣ
     */
    public void setResponseMsg(String msg){
        this.responseContent.put(Response.RESPONSE_MSG,msg);
    }

    /**
     * ���� response ��Я��������
     * @param data response ��Я��������
     */
    public void setResponseData(Object data){
        this.responseContent.put(Response.RESPONSE_DATA,data);
    }

    /**
     * ���� response ��Я�����ݵ��������� RESPONSE_DATA ���ʹ��
     * @param total Я�����ݵ�����
     */
    public void setResponseTotal(long total){
        this.responseContent.put(Response.RESPONSE_TOTAL,total);
    }

    /**
     * ���� response �Զ�����Ϣ
     * @param key �Զ�����Ϣ�� key
     * @param value �Զ�����Ϣ��ֵ
     */
    public void setCustomerInfo(String key, Object value){
        this.responseContent.put(key,value);
    }

    /**
     * ���� response
     * @return ���� response ��һ�� Map ����
     */
    public Map<String, Object> generateResponse(){
        return this.responseContent;
    }
}
