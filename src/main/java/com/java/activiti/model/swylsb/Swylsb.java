package com.java.activiti.model.swylsb;

import com.java.activiti.model.MyType;
import com.java.activiti.model.Project;
import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class Swylsb implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;//�豸���

    private Integer jdId;//���ȱ�ID

    private SwylsbJd swylsbJd;//���ȱ�

    private Integer xqId;//�����ID

    private SwylsbXq swylsbXq;//�����

    private String userId;//������Id

    private User user;//��������ϸ��Ϣ

    private Integer projectID;//��ĿID

    private Project project;//��Ŀ

    private Integer sbID;//�豸ID

    private String sbName;//�豸����

    private String sbClass;//�豸�ͺ�

    private Integer sbStatus;//�豸����

    private MyType sblx;//�豸����

    private String bzlx;//��װ����

    private String yyqk;//�������

    private Integer jyjbStatus;//���鼶��

    private MyType jyjb;//���鼶��

    private String gydw;//��Ӧ��λ

    private String azdw;//��װ��λ

    private Integer xssyStatus;//��ʽ����

    private MyType xuyao;//��ʽ����

    private Integer sbsl;//�豸����

    private String fzrUser;//��-������

    private Date startDate;//�ܹ���ƻ�������ʼ����

    private Date completeDate;//�ܹ���ƻ������������

    private Date approachDate;//�ܹ���ƻ��豸��������

    private Date sbDate;//��дʱ��

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getJdId() {
        return jdId;
    }

    public void setJdId(Integer jdId) {
        this.jdId = jdId;
    }

    public SwylsbJd getSwylsbJd() {
        return swylsbJd;
    }

    public void setSwylsbJd(SwylsbJd swylsbJd) {
        this.swylsbJd = swylsbJd;
    }

    public Integer getXqId() {
        return xqId;
    }

    public void setXqId(Integer xqId) {
        this.xqId = xqId;
    }

    public SwylsbXq getSwylsbXq() {
        return swylsbXq;
    }

    public void setSwylsbXq(SwylsbXq swylsbXq) {
        this.swylsbXq = swylsbXq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getSbID() {
        return sbID;
    }

    public void setSbID(Integer sbID) {
        this.sbID = sbID;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getSbClass() {
        return sbClass;
    }

    public void setSbClass(String sbClass) {
        this.sbClass = sbClass;
    }

    public Integer getSbStatus() {
        return sbStatus;
    }

    public void setSbStatus(Integer sbStatus) {
        this.sbStatus = sbStatus;
    }

    public MyType getSblx() {
        return sblx;
    }

    public void setSblx(MyType sblx) {
        this.sblx = sblx;
    }

    public String getBzlx() {
        return bzlx;
    }

    public void setBzlx(String bzlx) {
        this.bzlx = bzlx;
    }

    public String getYyqk() {
        return yyqk;
    }

    public void setYyqk(String yyqk) {
        this.yyqk = yyqk;
    }

    public Integer getJyjbStatus() {
        return jyjbStatus;
    }

    public void setJyjbStatus(Integer jyjbStatus) {
        this.jyjbStatus = jyjbStatus;
    }

    public MyType getJyjb() {
        return jyjb;
    }

    public void setJyjb(MyType jyjb) {
        this.jyjb = jyjb;
    }

    public String getGydw() {
        return gydw;
    }

    public void setGydw(String gydw) {
        this.gydw = gydw;
    }

    public String getAzdw() {
        return azdw;
    }

    public void setAzdw(String azdw) {
        this.azdw = azdw;
    }

    public Integer getXssyStatus() {
        return xssyStatus;
    }

    public void setXssyStatus(Integer xssyStatus) {
        this.xssyStatus = xssyStatus;
    }

    public MyType getXuyao() {
        return xuyao;
    }

    public void setXuyao(MyType xuyao) {
        this.xuyao = xuyao;
    }

    public Integer getSbsl() {
        return sbsl;
    }

    public void setSbsl(Integer sbsl) {
        this.sbsl = sbsl;
    }

    public String getFzrUser() {
        return fzrUser;
    }

    public void setFzrUser(String fzrUser) {
        this.fzrUser = fzrUser;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getApproachDate() {
        return approachDate;
    }

    public void setApproachDate(Date approachDate) {
        this.approachDate = approachDate;
    }

    public Date getSbDate() {
        return sbDate;
    }

    public void setSbDate(Date sbDate) {
        this.sbDate = sbDate;
    }
}
