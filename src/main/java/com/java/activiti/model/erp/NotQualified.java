package com.java.activiti.model.erp;

import java.io.Serializable;
import java.util.Date;

public class NotQualified implements Serializable {

    private static final long serialVersionUID = 1L;

    private String NotQuaId;//���ϸ�Id

    private int NotQuaLook;//���ϸ����

    private int NotQuaSize;//���ϸ�ߴ�

    private int NotQuaYd;//���ϸ�Ӳ��

    private int NotQuaType;//���ϸ��ͺ�

    private int NotQuaXn;//���ϸ�����

    private int NotQuaTs;//���ϸ�̽��

    private int NotQuaZl;//���ϸ�����

    private int NotQuaQt;//���ϸ�����

    private String NotQuaDesc;//���ϸ�����

    private String RCheckoutId;//����Id

    private RCheckout rCheckout;//����

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
