package com.java.activiti.model.swylsb;

import java.io.Serializable;
import java.util.Date;

public class SwylsbJh implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;//ID

    private String jdField;//进度字段

    private Date jhStartDate;//计划开始日期

    private Date jhEndDate;//计划完成日期

    private Date sjStartDate;//实际开始日期

    private Date sjEndDate;//实际完成日期

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

//    public SwylsbJd getSwylsbJd() {
//        return swylsbJd;
//    }

//    public void setSwylsbJd(SwylsbJd swylsbJd) {
//        this.swylsbJd = swylsbJd;
//    }

    public String getJdField() {
        return jdField;
    }

    public void setJdField(String jdField) {
        this.jdField = jdField;
    }

    public Date getJhStartDate() {
        return jhStartDate;
    }

    public void setJhStartDate(Date jhStartDate) {
        this.jhStartDate = jhStartDate;
    }

    public Date getJhEndDate() {
        return jhEndDate;
    }

    public void setJhEndDate(Date jhEndDate) {
        this.jhEndDate = jhEndDate;
    }

    public Date getSjStartDate() {
        return sjStartDate;
    }

    public void setSjStartDate(Date sjStartDate) {
        this.sjStartDate = sjStartDate;
    }

    public Date getSjEndDate() {
        return sjEndDate;
    }

    public void setSjEndDate(Date sjEndDate) {
        this.sjEndDate = sjEndDate;
    }

    @Override
    public String toString() {
        return "SwylsbJh{" +
                "ID=" + ID +
                ", jdField='" + jdField + '\'' +
                ", jhStartDate=" + jhStartDate +
                ", jhEndDate=" + jhEndDate +
                ", sjStartDate=" + sjStartDate +
                ", sjEndDate=" + sjEndDate +
                '}';
    }
}
