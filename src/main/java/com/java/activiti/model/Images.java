package com.java.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class Images implements Serializable {

    private static final long serialVersionUID = 1L;


    private int imagesID;//id
    private String repairID;//repair_id

    private String Path;//path

    private String remark;//remark

    private int is_deleted;//is_deleted

    private Date create_time;//create_time

    public int getImagesID() {
        return imagesID;
    }

    public void setImagesID(int imagesID) {
        this.imagesID = imagesID;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Images{" +
                "imagesID=" + imagesID +
                ", repairID='" + repairID + '\'' +
                ", Path='" + Path + '\'' +
                ", remark='" + remark + '\'' +
                ", is_deleted=" + is_deleted +
                ", create_time=" + create_time +
                '}';
    }
}
