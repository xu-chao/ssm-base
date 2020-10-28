package com.java.activiti.model.swylsb;

import com.java.activiti.model.MyType;
import com.java.activiti.model.Project;
import com.java.activiti.model.User;

import java.io.Serializable;
import java.util.Date;

public class Swylsb implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;//设备序号

    private Integer jdId;//进度表ID

    private SwylsbJd swylsbJd;//进度表

    private Integer xqId;//详情表ID

    private SwylsbXq swylsbXq;//详情表

    private String userId;//负责人Id

    private User user;//负责人详细信息

    private Integer projectID;//项目ID

    private Project project;//项目

    private Integer sbID;//设备ID

    private String sbName;//设备名称

    private String sbClass;//设备型号

    private Integer sbStatus;//设备类型

    private MyType sblx;//设备类型

    private String bzlx;//包装类型

    private String yyqk;//沿用情况

    private Integer jyjbStatus;//检验级别

    private MyType jyjb;//检验级别

    private String gydw;//供应单位

    private String azdw;//安装单位

    private Integer xssyStatus;//型式试验

    private MyType xuyao;//型式试验

    private Integer sbsl;//设备数量

    private String fzrUser;//二-负责人

    private Date startDate;//总工办计划基础开始日期

    private Date completeDate;//总工办计划基础完成日期

    private Date approachDate;//总工办计划设备进场日期

    private Date sbDate;//填写时间

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
