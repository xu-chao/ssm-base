package com.java.activiti.model.erp;

import java.io.Serializable;
import java.util.Date;

public class NotQualified implements Serializable {

    private static final long serialVersionUID = 1L;

    private String NotQuaId;//不合格Id

    private int NotQuaLook;//不合格外观

    private int NotQuaSize;//不合格尺寸

    private int NotQuaYd;//不合格硬度

    private int NotQuaType;//不合格型号

    private int NotQuaXn;//不合格性能

    private int NotQuaTs;//不合格探伤

    private int NotQuaZl;//不合格资料

    private int NotQuaQt;//不合格其他

    private String NotQuaDesc;//不合格描述

    private String RCheckoutId;//返修Id

    private RCheckout rCheckout;//返修

    public String getNotQuaId() {
        return NotQuaId;
    }

    public void setNotQuaId(String notQuaId) {
        NotQuaId = notQuaId;
    }

    public int getNotQuaLook() {
        return NotQuaLook;
    }

    public void setNotQuaLook(int notQuaLook) {
        NotQuaLook = notQuaLook;
    }

    public int getNotQuaSize() {
        return NotQuaSize;
    }

    public void setNotQuaSize(int notQuaSize) {
        NotQuaSize = notQuaSize;
    }

    public int getNotQuaYd() {
        return NotQuaYd;
    }

    public void setNotQuaYd(int notQuaYd) {
        NotQuaYd = notQuaYd;
    }

    public int getNotQuaType() {
        return NotQuaType;
    }

    public void setNotQuaType(int notQuaType) {
        NotQuaType = notQuaType;
    }

    public int getNotQuaXn() {
        return NotQuaXn;
    }

    public void setNotQuaXn(int notQuaXn) {
        NotQuaXn = notQuaXn;
    }

    public int getNotQuaTs() {
        return NotQuaTs;
    }

    public void setNotQuaTs(int notQuaTs) {
        NotQuaTs = notQuaTs;
    }

    public int getNotQuaZl() {
        return NotQuaZl;
    }

    public void setNotQuaZl(int notQuaZl) {
        NotQuaZl = notQuaZl;
    }

    public int getNotQuaQt() {
        return NotQuaQt;
    }

    public void setNotQuaQt(int notQuaQt) {
        NotQuaQt = notQuaQt;
    }

    public String getNotQuaDesc() {
        return NotQuaDesc;
    }

    public void setNotQuaDesc(String notQuaDesc) {
        NotQuaDesc = notQuaDesc;
    }

    public String getRCheckoutId() {
        return RCheckoutId;
    }

    public void setRCheckoutId(String RCheckoutId) {
        this.RCheckoutId = RCheckoutId;
    }

    public RCheckout getrCheckout() {
        return rCheckout;
    }

    public void setrCheckout(RCheckout rCheckout) {
        this.rCheckout = rCheckout;
    }

    @Override
    public String toString() {
        return "NotQualified{" +
                "NotQuaId='" + NotQuaId + '\'' +
                ", NotQuaLook=" + NotQuaLook +
                ", NotQuaSize=" + NotQuaSize +
                ", NotQuaYd=" + NotQuaYd +
                ", NotQuaType=" + NotQuaType +
                ", NotQuaXn=" + NotQuaXn +
                ", NotQuaTs=" + NotQuaTs +
                ", NotQuaZl=" + NotQuaZl +
                ", NotQuaQt=" + NotQuaQt +
                ", NotQuaDesc='" + NotQuaDesc + '\'' +
                ", RCheckoutId='" + RCheckoutId + '\'' +
                ", rCheckout=" + rCheckout +
                '}';
    }
}
