package com.java.activiti.pojo;

import java.io.Serializable;

/**
 *
 * @ClassName:  GlobalResult
 * @Description:ȫ�ַ���ֵ
 * @author:     ��
 * @date:       2019��8��9��
 */
public class GlobalResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // ��Ӧҵ��״̬
    private Integer status;

    // ��Ӧ��Ϣ
    private String msg;

    // ��Ӧ�е�����
    private Object data;

    public static GlobalResultVO build(Integer status, String msg, Object data) {
        return new GlobalResultVO(status, msg, data);
    }

    public static GlobalResultVO ok(Object data) {
        return new GlobalResultVO(data);
    }

    public static GlobalResultVO ok() {
        return new GlobalResultVO(null);
    }

    public GlobalResultVO() {

    }

    public static GlobalResultVO build(Integer status, String msg) {
        return new GlobalResultVO(status, msg, null);
    }

    public GlobalResultVO(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public GlobalResultVO(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ManagerResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
    }
}
