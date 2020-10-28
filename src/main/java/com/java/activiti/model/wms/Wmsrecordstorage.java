package com.java.activiti.model.wms;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Wmsrecordstorage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("recordGoodId")
        private String recordGoodId;

    @Field("recordGood")
    private WmsGood recordGood;//����

    @Field("recordWarehouseId")
    private Integer recordWarehouseId;//�ֿ�ID


    @Field("recordWarehouse")
    private Warehouse recordWarehouse;//�ֿ�

    @Field("recordNumber")
    private long recordNumber;//����

    public String getRecordGoodId() {
        return recordGoodId;
    }

    public void setRecordGoodId(String recordGoodId) {
        this.recordGoodId = recordGoodId;
    }

    public WmsGood getRecordGood() {
        return recordGood;
    }

    public void setRecordGood(WmsGood recordGood) {
        this.recordGood = recordGood;
    }

    public Integer getRecordWarehouseId() {
        return recordWarehouseId;
    }

    public void setRecordWarehouseId(Integer recordWarehouseId) {
        this.recordWarehouseId = recordWarehouseId;
    }

    public Warehouse getRecordWarehouse() {
        return recordWarehouse;
    }

    public void setRecordWarehouse(Warehouse recordWarehouse) {
        this.recordWarehouse = recordWarehouse;
    }

    public long getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(long recordNumber) {
        this.recordNumber = recordNumber;
    }
}
