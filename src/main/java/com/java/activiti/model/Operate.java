package com.java.activiti.model;

public class Operate {

    private long id;//slowlogΨһ���id
    private String executeTime;// ��ѯ��ʱ���
    private String usedTime;// ��ѯ�ĺ�ʱ��΢������ʾ���������ѯ��ʱ47΢��
    private String args;// ��ѯ�����������Ϊ SLOWLOG GET��slowlog��ౣ��ǰ���31��key��128�ַ�

    public Operate() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }


    public String getArgs() {
        return args;
    }


    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Operate{" +
                "id=" + id +
                ", executeTime='" + executeTime + '\'' +
                ", usedTime='" + usedTime + '\'' +
                ", args='" + args + '\'' +
                '}';
    }
}
