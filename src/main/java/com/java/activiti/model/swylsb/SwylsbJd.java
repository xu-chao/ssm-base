package com.java.activiti.model.swylsb;

import com.java.activiti.model.MyType;
import java.io.Serializable;
import java.util.Date;

public class SwylsbJd implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer ID;//ID

    private Integer jdId;//ID

//    private SwylsbJh swylsbJh;//计划详情表

    private Integer azStatus;//安装基础状态
    private MyType azStatusType;//安装基础状态

    private Integer tsStatus;//图审状态
    private MyType tsStatusType;//图审状态

    private Integer xxfaStatus;//选型方案
    private MyType xxfaStatusType;//选型方案

    private Integer tdztStatus;//提单状态-tzzt
    private MyType tdztStatusType;//提单状态-tzzt

    private Integer cgztStatus;//采购状态
    private MyType cgztStatusType;//采购状态

    private Integer shfaStatus;//深化方案
    private MyType shfaStatusType;//深化方案

    private Integer ztjgscStatus;//主体结构生产
    private MyType ztjgscStatusType;//主体结构生产

    private Integer zxscStatus;//造型生产
    private MyType zxscStatusType;//造型生产

    private Integer dqscStatus;//电气生产
    private MyType dqscStatusType;//电气生产

    private Integer ztjgdhStatus;//主体结构到货
    private MyType ztjgdhStatusType;//主体结构到货

    private Integer zxdhStatus;//造型到货
    private MyType zxdhStatusType;//造型到货

    private Integer dqdhStatus;//电气到货
    private MyType dqdhStatusType;//电气到货

    private Integer zjganStatus;//主结构安装
    private MyType zjganStatusType;//主结构安装

    private Integer zxazStatus;//造型安装
    private MyType zxazStatusType;//造型安装

    private Integer dqazStatus;//电气安装
    private MyType dqazStatusType;//电气安装

    private Integer sbtsStatus;//设备调试
    private MyType sbtsStatusType;//设备调试

    private Integer xssy1Status;//型式试验
    private MyType xssy1StatusType;//型式试验

    private Integer gsjStatus;//国/省检
    private MyType gsjStatusType;//国/省检

    private Integer jgysStatus;//竣工验收
    private MyType jgysStatusType;//竣工验收

    private Date azjcsjStartDate;//安装基础实际开始日期

    private Date azjcsjEndDate;//安装基础实际完成日期

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

    public Integer getAzStatus() {
        return azStatus;
    }

    public void setAzStatus(Integer azStatus) {
        this.azStatus = azStatus;
    }

    public MyType getAzStatusType() {
        return azStatusType;
    }

    public void setAzStatusType(MyType azStatusType) {
        this.azStatusType = azStatusType;
    }

    public Integer getTsStatus() {
        return tsStatus;
    }

    public void setTsStatus(Integer tsStatus) {
        this.tsStatus = tsStatus;
    }

    public MyType getTsStatusType() {
        return tsStatusType;
    }

    public void setTsStatusType(MyType tsStatusType) {
        this.tsStatusType = tsStatusType;
    }

    public Integer getXxfaStatus() {
        return xxfaStatus;
    }

    public void setXxfaStatus(Integer xxfaStatus) {
        this.xxfaStatus = xxfaStatus;
    }

    public MyType getXxfaStatusType() {
        return xxfaStatusType;
    }

    public void setXxfaStatusType(MyType xxfaStatusType) {
        this.xxfaStatusType = xxfaStatusType;
    }

    public Integer getTdztStatus() {
        return tdztStatus;
    }

    public void setTdztStatus(Integer tdztStatus) {
        this.tdztStatus = tdztStatus;
    }

    public MyType getTdztStatusType() {
        return tdztStatusType;
    }

    public void setTdztStatusType(MyType tdztStatusType) {
        this.tdztStatusType = tdztStatusType;
    }

    public Integer getCgztStatus() {
        return cgztStatus;
    }

    public void setCgztStatus(Integer cgztStatus) {
        this.cgztStatus = cgztStatus;
    }

    public MyType getCgztStatusType() {
        return cgztStatusType;
    }

    public void setCgztStatusType(MyType cgztStatusType) {
        this.cgztStatusType = cgztStatusType;
    }

    public Integer getShfaStatus() {
        return shfaStatus;
    }

    public void setShfaStatus(Integer shfaStatus) {
        this.shfaStatus = shfaStatus;
    }

    public MyType getShfaStatusType() {
        return shfaStatusType;
    }

    public void setShfaStatusType(MyType shfaStatusType) {
        this.shfaStatusType = shfaStatusType;
    }

    public Integer getZtjgscStatus() {
        return ztjgscStatus;
    }

    public void setZtjgscStatus(Integer ztjgscStatus) {
        this.ztjgscStatus = ztjgscStatus;
    }

    public MyType getZtjgscStatusType() {
        return ztjgscStatusType;
    }

    public void setZtjgscStatusType(MyType ztjgscStatusType) {
        this.ztjgscStatusType = ztjgscStatusType;
    }

    public Integer getZxscStatus() {
        return zxscStatus;
    }

    public void setZxscStatus(Integer zxscStatus) {
        this.zxscStatus = zxscStatus;
    }

    public MyType getZxscStatusType() {
        return zxscStatusType;
    }

    public void setZxscStatusType(MyType zxscStatusType) {
        this.zxscStatusType = zxscStatusType;
    }

    public Integer getDqscStatus() {
        return dqscStatus;
    }

    public void setDqscStatus(Integer dqscStatus) {
        this.dqscStatus = dqscStatus;
    }

    public MyType getDqscStatusType() {
        return dqscStatusType;
    }

    public void setDqscStatusType(MyType dqscStatusType) {
        this.dqscStatusType = dqscStatusType;
    }

    public Integer getZtjgdhStatus() {
        return ztjgdhStatus;
    }

    public void setZtjgdhStatus(Integer ztjgdhStatus) {
        this.ztjgdhStatus = ztjgdhStatus;
    }

    public MyType getZtjgdhStatusType() {
        return ztjgdhStatusType;
    }

    public void setZtjgdhStatusType(MyType ztjgdhStatusType) {
        this.ztjgdhStatusType = ztjgdhStatusType;
    }

    public Integer getZxdhStatus() {
        return zxdhStatus;
    }

    public void setZxdhStatus(Integer zxdhStatus) {
        this.zxdhStatus = zxdhStatus;
    }

    public MyType getZxdhStatusType() {
        return zxdhStatusType;
    }

    public void setZxdhStatusType(MyType zxdhStatusType) {
        this.zxdhStatusType = zxdhStatusType;
    }

    public Integer getDqdhStatus() {
        return dqdhStatus;
    }

    public void setDqdhStatus(Integer dqdhStatus) {
        this.dqdhStatus = dqdhStatus;
    }

    public MyType getDqdhStatusType() {
        return dqdhStatusType;
    }

    public void setDqdhStatusType(MyType dqdhStatusType) {
        this.dqdhStatusType = dqdhStatusType;
    }

    public Integer getZjganStatus() {
        return zjganStatus;
    }

    public void setZjganStatus(Integer zjganStatus) {
        this.zjganStatus = zjganStatus;
    }

    public MyType getZjganStatusType() {
        return zjganStatusType;
    }

    public void setZjganStatusType(MyType zjganStatusType) {
        this.zjganStatusType = zjganStatusType;
    }

    public Integer getZxazStatus() {
        return zxazStatus;
    }

    public void setZxazStatus(Integer zxazStatus) {
        this.zxazStatus = zxazStatus;
    }

    public MyType getZxazStatusType() {
        return zxazStatusType;
    }

    public void setZxazStatusType(MyType zxazStatusType) {
        this.zxazStatusType = zxazStatusType;
    }

    public Integer getDqazStatus() {
        return dqazStatus;
    }

    public void setDqazStatus(Integer dqazStatus) {
        this.dqazStatus = dqazStatus;
    }

    public MyType getDqazStatusType() {
        return dqazStatusType;
    }

    public void setDqazStatusType(MyType dqazStatusType) {
        this.dqazStatusType = dqazStatusType;
    }

    public Integer getSbtsStatus() {
        return sbtsStatus;
    }

    public void setSbtsStatus(Integer sbtsStatus) {
        this.sbtsStatus = sbtsStatus;
    }

    public MyType getSbtsStatusType() {
        return sbtsStatusType;
    }

    public void setSbtsStatusType(MyType sbtsStatusType) {
        this.sbtsStatusType = sbtsStatusType;
    }

    public Integer getXssy1Status() {
        return xssy1Status;
    }

    public void setXssy1Status(Integer xssy1Status) {
        this.xssy1Status = xssy1Status;
    }

    public MyType getXssy1StatusType() {
        return xssy1StatusType;
    }

    public void setXssy1StatusType(MyType xssy1StatusType) {
        this.xssy1StatusType = xssy1StatusType;
    }

    public Integer getGsjStatus() {
        return gsjStatus;
    }

    public void setGsjStatus(Integer gsjStatus) {
        this.gsjStatus = gsjStatus;
    }

    public MyType getGsjStatusType() {
        return gsjStatusType;
    }

    public void setGsjStatusType(MyType gsjStatusType) {
        this.gsjStatusType = gsjStatusType;
    }

    public Integer getJgysStatus() {
        return jgysStatus;
    }

    public void setJgysStatus(Integer jgysStatus) {
        this.jgysStatus = jgysStatus;
    }

    public MyType getJgysStatusType() {
        return jgysStatusType;
    }

    public void setJgysStatusType(MyType jgysStatusType) {
        this.jgysStatusType = jgysStatusType;
    }

    public Date getAzjcsjStartDate() {
        return azjcsjStartDate;
    }

    public void setAzjcsjStartDate(Date azjcsjStartDate) {
        this.azjcsjStartDate = azjcsjStartDate;
    }

    public Date getAzjcsjEndDate() {
        return azjcsjEndDate;
    }

    public void setAzjcsjEndDate(Date azjcsjEndDate) {
        this.azjcsjEndDate = azjcsjEndDate;
    }
}
