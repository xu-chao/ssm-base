package com.java.activiti.model.wms;

import com.java.activiti.model.User;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * ����
 */

public class WmsIncoming implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("documentsId")
    private String documentsId;//������ID

    @Field("companyName")
    private String companyName;//��˾����

    @Field("companyNo")
    private String companyNo;//��˾����

    @Field("inComeDate")
    private Date inComeDate;//���ʱ��

    @Field("documentType")
    private String documentType;//��������

    @Field("documentsNo")
    private String documentsNo;//���ݺ�

    @Field("transceiverType")
    private String transceiverType;//�շ����

    @Field("warehouseId")
    private Integer warehouseId;//�ֿ�ID

    private String warehouseName;//�ֿ�����

    @Field("warehouse")
    private Warehouse warehouse;//�ֿ�

    @Field("innkeeper")
    private String innkeeper;//���Ա

    @Field("department")
    private String department;//����

    @Field("salesMan")
    private String salesMan;//ҵ��Ա

    @Field("customerNo")
    private String customerNo;//�ͻ�����

    @Field("custRemNo")
    private String custRemNo;//�ͻ�������

    @Field("customerName")
    private String customerName;//�ͻ�

    @Field("supplierName")
    private String supplierName;//��Ӧ��

    @Field("suppierNo")
    private String suppierNo;//��Ӧ�̱���

    @Field("supRemNo")
    private String supRemNo;//��Ӧ��������

    @Field("notes")
    private String notes;//��ע

    @Field("lineNo")
    private Integer lineNo;//�к�

    @Field("inventoryNo")
    private String inventoryNo;//����ID  �������

    private String inventoryName;//��������

    @Field("inventory")
    private WmsGood inventory;//����

    @Field("specifications")
    private String specifications;//���

    @Field("model")
    private String model;//�ͺ�

    @Field("measurementUnit")
    private String measurementUnit;//������λ

    @Field("secondaryUnit")
    private String secondaryUnit;//��������λ

    @Field("conversion")
    private String conversion;//������

    @Field("batchNo")
    private String batchNo;//���κ�

    @Field("inExNum")
    private Integer  inExNum;//��⸨����

    @Field("inNum")
    private Integer  inNum;//�������

    @Field("inGross")
    private Integer  inGross;//���ë��

    @Field("inPrice")
    private double  inPrice;//����

    @Field("inAmount")
    private double  inAmount;//�����

    @Field("whetherGifts")
    private String  whetherGifts;//�Ƿ���Ʒ

    @Field("costObject")
    private String  costObject;//�ɱ�����

    @Field("projectName")
    private String  projectName;//��Ŀ

    @Field("projectStage")
    private String  projectStage;//��Ŀ�׶�

    @Field("ordersMan")
    private String  ordersMan;//�Ƶ���

    @Field("signatoryMan")
    private String  signatoryMan;//ǩ����

    @Field("sourceDocuType")
    private String  sourceDocuType;//��Դ��������

    @Field("sourceDocuNo")
    private String  sourceDocuNo;//��Դ���ݺ�

    @Field("aSourceDocuType")
    private String  aSourceDocuType;//Դͷ��������

    @Field("aSourceDocNo")
    private String  aSourceDocNo;//Դͷ���ݺ�

    @Field("substanceAppliState")
    private String  substanceAppliState;//���������ʶ

    @Field("settlementDepart")
    private String  settlementDepart;//���㲿��

    @Field("traders")
    private String  traders;//ó����

    @Field("proforma")
    private String  proforma;//�ᵥ��

    @Field("reimbursement")
    private String  reimbursement;//��������

    @Field("purchCoInvoType")
    private String  purchCoInvoType;//�ɹ���ͬ��Ʊ����

    @Field("whetherCollectSelf")
    private String  whetherCollectSelf;//�Ƿ��������Ƽ�

    @Field("HUDC8h")
    private String  HUDC8h;//H-UDC8h

    @Field("subscripDepartFormal")
    private String  subscripDepartFormal;//�깺����-��ʽ

    @Field("HUDC10h")
    private String  HUDC10h;//�깺����-��ʽ

    @Field("unitPriceIncludTaxTraders")
    private String  unitPriceIncludTaxTraders;//ó���̺�˰����

    @Field("useOfCollar")
    private String  useOfCollar;//������;

    @Field("purContractRemarks")
    private String  purContractRemarks;//�ɹ���ͬ��ע

    @Field("purFromManufacturer")
    private String  purFromManufacturer;//�빺��������

    @Field("settlementDepartment")
    private String  settlementDepartment;//���㲿��

    @Field("BUDC6")
    private String  BUDC6;//B-UDC6

    @Field("BUDC7")
    private String  BUDC7;//B-BUDC7

    @Field("BUDC8")
    private String  BUDC8;//B-BUDC8

    @Field("materialPrePlanNo")
    private String  materialPrePlanNo;//���ϼƻ���

    @Field("BUDC10")
    private String  BUDC10;//B-BUDC10

    @Field("BUDC11")
    private String  BUDC11;//B-BUDC11

    @Field("BUDC12")
    private String  BUDC12;//B-BUDC12

    @Field("BUDC13")
    private String  BUDC13;//B-BUDC13

    @Field("BUDC14")
    private String  BUDC14;//B-BUDC14

    @Field("BUDC15")
    private String  BUDC15;//B-BUDC15

    @Field("BUDC16")
    private String  BUDC16;//B-BUDC16

    @Field("BUDC17")
    private String  BUDC17;//B-BUDC17

    @Field("parkOperationProject")
    private String  parkOperationProject;//��԰��Ӫ��Ŀ

    @Field("purposevFeedingScreen")
    private String  purposevFeedingScreen;//��ɸĿ��̬

    @Field("whetherSubmittedInspection")
    private String  whetherSubmittedInspection;//�Ƿ����ͼ�

    @Field("projectNo20180412")
    private String  projectNo20180412;//������20180412

    @Field("HUDC12h")
    private String  HUDC12h;//B-HUDC12h

    @Field("HUDC13h")
    private String  HUDC13h;//B-HUDC13h

    @Field("HUDC14h")
    private String  HUDC14h;//B-HUDC14h

    @Field("HUDC15h")
    private String  HUDC15h;//B-HUDC15h

    @Field("HUDC16h")
    private String  HUDC16h;//B-HUDC16h

    @Field("wheResearchDevelopment")
    private String  wheResearchDevelopment;//�Ƿ��з�

    @Field("inAgent")
    private String  inAgent;//������

    @Field("procurementType")
    private String  procurementType;//�ɹ�����

    @Field("requiredCompletionDate")
    private Date  requiredCompletionDate;//Ҫ���������

    @Field("receivableNum")
    private Integer  receivableNum;//Ӧ������

    @Field("receivableExNum")
    private Integer  receivableExNum;//Ӧ�ո�����

    @Field("bodyRemark")
    private String   bodyRemark;//���屸ע

    @Field("transferOutCompany")
    private String   transferOutCompany;//������˾

    @Field("transferOutOrganization")
    private String   transferOutOrganization;//������֯

    @Field("transferOutWarehouse")
    private String   transferOutWarehouse;//�����ֿ�

    @Field("settleCompMark")
    private String  settleCompMark;//������ɱ�ʶ

    @Field("settleAmount")
    private double  settleAmount;//��������

    @Field("setteNum")
    private Integer  setteNum;//��������

    @Field("estimateMoney")
    private double  estimateMoney;//�ݹ����

    @Field("userId")
    private String userId;//�û�

    @Field("user")
    private User user;//�û�

    @Field("inDate")
    private Date inDate;//�������

    public String getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public Date getInComeDate() {
        return inComeDate;
    }

    public void setInComeDate(Date inComeDate) {
        this.inComeDate = inComeDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentsNo() {
        return documentsNo;
    }

    public void setDocumentsNo(String documentsNo) {
        this.documentsNo = documentsNo;
    }

    public String getTransceiverType() {
        return transceiverType;
    }

    public void setTransceiverType(String transceiverType) {
        this.transceiverType = transceiverType;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getInnkeeper() {
        return innkeeper;
    }

    public void setInnkeeper(String innkeeper) {
        this.innkeeper = innkeeper;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustRemNo() {
        return custRemNo;
    }

    public void setCustRemNo(String custRemNo) {
        this.custRemNo = custRemNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSuppierNo() {
        return suppierNo;
    }

    public void setSuppierNo(String suppierNo) {
        this.suppierNo = suppierNo;
    }

    public String getSupRemNo() {
        return supRemNo;
    }

    public void setSupRemNo(String supRemNo) {
        this.supRemNo = supRemNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    public WmsGood getInventory() {
        return inventory;
    }

    public void setInventory(WmsGood inventory) {
        this.inventory = inventory;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getSecondaryUnit() {
        return secondaryUnit;
    }

    public void setSecondaryUnit(String secondaryUnit) {
        this.secondaryUnit = secondaryUnit;
    }

    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getInExNum() {
        return inExNum;
    }

    public void setInExNum(Integer inExNum) {
        this.inExNum = inExNum;
    }

    public Integer getInNum() {
        return inNum;
    }

    public void setInNum(Integer inNum) {
        this.inNum = inNum;
    }

    public Integer getInGross() {
        return inGross;
    }

    public void setInGross(Integer inGross) {
        this.inGross = inGross;
    }

    public double getInPrice() {
        return inPrice;
    }

    public void setInPrice(double inPrice) {
        this.inPrice = inPrice;
    }

    public double getInAmount() {
        return inAmount;
    }

    public void setInAmount(double inAmount) {
        this.inAmount = inAmount;
    }

    public String getWhetherGifts() {
        return whetherGifts;
    }

    public void setWhetherGifts(String whetherGifts) {
        this.whetherGifts = whetherGifts;
    }

    public String getCostObject() {
        return costObject;
    }

    public void setCostObject(String costObject) {
        this.costObject = costObject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

    public String getOrdersMan() {
        return ordersMan;
    }

    public void setOrdersMan(String ordersMan) {
        this.ordersMan = ordersMan;
    }

    public String getSignatoryMan() {
        return signatoryMan;
    }

    public void setSignatoryMan(String signatoryMan) {
        this.signatoryMan = signatoryMan;
    }

    public String getSourceDocuType() {
        return sourceDocuType;
    }

    public void setSourceDocuType(String sourceDocuType) {
        this.sourceDocuType = sourceDocuType;
    }

    public String getSourceDocuNo() {
        return sourceDocuNo;
    }

    public void setSourceDocuNo(String sourceDocuNo) {
        this.sourceDocuNo = sourceDocuNo;
    }

    public String getaSourceDocuType() {
        return aSourceDocuType;
    }

    public void setaSourceDocuType(String aSourceDocuType) {
        this.aSourceDocuType = aSourceDocuType;
    }

    public String getaSourceDocNo() {
        return aSourceDocNo;
    }

    public void setaSourceDocNo(String aSourceDocNo) {
        this.aSourceDocNo = aSourceDocNo;
    }

    public String getSubstanceAppliState() {
        return substanceAppliState;
    }

    public void setSubstanceAppliState(String substanceAppliState) {
        this.substanceAppliState = substanceAppliState;
    }

    public String getSettlementDepart() {
        return settlementDepart;
    }

    public void setSettlementDepart(String settlementDepart) {
        this.settlementDepart = settlementDepart;
    }

    public String getTraders() {
        return traders;
    }

    public void setTraders(String traders) {
        this.traders = traders;
    }

    public String getProforma() {
        return proforma;
    }

    public void setProforma(String proforma) {
        this.proforma = proforma;
    }

    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getPurchCoInvoType() {
        return purchCoInvoType;
    }

    public void setPurchCoInvoType(String purchCoInvoType) {
        this.purchCoInvoType = purchCoInvoType;
    }

    public String getWhetherCollectSelf() {
        return whetherCollectSelf;
    }

    public void setWhetherCollectSelf(String whetherCollectSelf) {
        this.whetherCollectSelf = whetherCollectSelf;
    }

    public String getHUDC8h() {
        return HUDC8h;
    }

    public void setHUDC8h(String HUDC8h) {
        this.HUDC8h = HUDC8h;
    }

    public String getSubscripDepartFormal() {
        return subscripDepartFormal;
    }

    public void setSubscripDepartFormal(String subscripDepartFormal) {
        this.subscripDepartFormal = subscripDepartFormal;
    }

    public String getHUDC10h() {
        return HUDC10h;
    }

    public void setHUDC10h(String HUDC10h) {
        this.HUDC10h = HUDC10h;
    }

    public String getUnitPriceIncludTaxTraders() {
        return unitPriceIncludTaxTraders;
    }

    public void setUnitPriceIncludTaxTraders(String unitPriceIncludTaxTraders) {
        this.unitPriceIncludTaxTraders = unitPriceIncludTaxTraders;
    }

    public String getUseOfCollar() {
        return useOfCollar;
    }

    public void setUseOfCollar(String useOfCollar) {
        this.useOfCollar = useOfCollar;
    }

    public String getPurContractRemarks() {
        return purContractRemarks;
    }

    public void setPurContractRemarks(String purContractRemarks) {
        this.purContractRemarks = purContractRemarks;
    }

    public String getPurFromManufacturer() {
        return purFromManufacturer;
    }

    public void setPurFromManufacturer(String purFromManufacturer) {
        this.purFromManufacturer = purFromManufacturer;
    }

    public String getSettlementDepartment() {
        return settlementDepartment;
    }

    public void setSettlementDepartment(String settlementDepartment) {
        this.settlementDepartment = settlementDepartment;
    }

    public String getBUDC6() {
        return BUDC6;
    }

    public void setBUDC6(String BUDC6) {
        this.BUDC6 = BUDC6;
    }

    public String getBUDC7() {
        return BUDC7;
    }

    public void setBUDC7(String BUDC7) {
        this.BUDC7 = BUDC7;
    }

    public String getBUDC8() {
        return BUDC8;
    }

    public void setBUDC8(String BUDC8) {
        this.BUDC8 = BUDC8;
    }

    public String getMaterialPrePlanNo() {
        return materialPrePlanNo;
    }

    public void setMaterialPrePlanNo(String materialPrePlanNo) {
        this.materialPrePlanNo = materialPrePlanNo;
    }

    public String getBUDC10() {
        return BUDC10;
    }

    public void setBUDC10(String BUDC10) {
        this.BUDC10 = BUDC10;
    }

    public String getBUDC11() {
        return BUDC11;
    }

    public void setBUDC11(String BUDC11) {
        this.BUDC11 = BUDC11;
    }

    public String getBUDC12() {
        return BUDC12;
    }

    public void setBUDC12(String BUDC12) {
        this.BUDC12 = BUDC12;
    }

    public String getBUDC13() {
        return BUDC13;
    }

    public void setBUDC13(String BUDC13) {
        this.BUDC13 = BUDC13;
    }

    public String getBUDC14() {
        return BUDC14;
    }

    public void setBUDC14(String BUDC14) {
        this.BUDC14 = BUDC14;
    }

    public String getBUDC15() {
        return BUDC15;
    }

    public void setBUDC15(String BUDC15) {
        this.BUDC15 = BUDC15;
    }

    public String getBUDC16() {
        return BUDC16;
    }

    public void setBUDC16(String BUDC16) {
        this.BUDC16 = BUDC16;
    }

    public String getBUDC17() {
        return BUDC17;
    }

    public void setBUDC17(String BUDC17) {
        this.BUDC17 = BUDC17;
    }

    public String getParkOperationProject() {
        return parkOperationProject;
    }

    public void setParkOperationProject(String parkOperationProject) {
        this.parkOperationProject = parkOperationProject;
    }

    public String getPurposevFeedingScreen() {
        return purposevFeedingScreen;
    }

    public void setPurposevFeedingScreen(String purposevFeedingScreen) {
        this.purposevFeedingScreen = purposevFeedingScreen;
    }

    public String getWhetherSubmittedInspection() {
        return whetherSubmittedInspection;
    }

    public void setWhetherSubmittedInspection(String whetherSubmittedInspection) {
        this.whetherSubmittedInspection = whetherSubmittedInspection;
    }

    public String getProjectNo20180412() {
        return projectNo20180412;
    }

    public void setProjectNo20180412(String projectNo20180412) {
        this.projectNo20180412 = projectNo20180412;
    }

    public String getHUDC12h() {
        return HUDC12h;
    }

    public void setHUDC12h(String HUDC12h) {
        this.HUDC12h = HUDC12h;
    }

    public String getHUDC13h() {
        return HUDC13h;
    }

    public void setHUDC13h(String HUDC13h) {
        this.HUDC13h = HUDC13h;
    }

    public String getHUDC14h() {
        return HUDC14h;
    }

    public void setHUDC14h(String HUDC14h) {
        this.HUDC14h = HUDC14h;
    }

    public String getHUDC15h() {
        return HUDC15h;
    }

    public void setHUDC15h(String HUDC15h) {
        this.HUDC15h = HUDC15h;
    }

    public String getHUDC16h() {
        return HUDC16h;
    }

    public void setHUDC16h(String HUDC16h) {
        this.HUDC16h = HUDC16h;
    }

    public String getWheResearchDevelopment() {
        return wheResearchDevelopment;
    }

    public void setWheResearchDevelopment(String wheResearchDevelopment) {
        this.wheResearchDevelopment = wheResearchDevelopment;
    }

    public String getInAgent() {
        return inAgent;
    }

    public void setInAgent(String inAgent) {
        this.inAgent = inAgent;
    }

    public String getProcurementType() {
        return procurementType;
    }

    public void setProcurementType(String procurementType) {
        this.procurementType = procurementType;
    }

    public Date getRequiredCompletionDate() {
        return requiredCompletionDate;
    }

    public void setRequiredCompletionDate(Date requiredCompletionDate) {
        this.requiredCompletionDate = requiredCompletionDate;
    }

    public Integer getReceivableNum() {
        return receivableNum;
    }

    public void setReceivableNum(Integer receivableNum) {
        this.receivableNum = receivableNum;
    }

    public Integer getReceivableExNum() {
        return receivableExNum;
    }

    public void setReceivableExNum(Integer receivableExNum) {
        this.receivableExNum = receivableExNum;
    }

    public String getBodyRemark() {
        return bodyRemark;
    }

    public void setBodyRemark(String bodyRemark) {
        this.bodyRemark = bodyRemark;
    }

    public String getTransferOutCompany() {
        return transferOutCompany;
    }

    public void setTransferOutCompany(String transferOutCompany) {
        this.transferOutCompany = transferOutCompany;
    }

    public String getTransferOutOrganization() {
        return transferOutOrganization;
    }

    public void setTransferOutOrganization(String transferOutOrganization) {
        this.transferOutOrganization = transferOutOrganization;
    }

    public String getTransferOutWarehouse() {
        return transferOutWarehouse;
    }

    public void setTransferOutWarehouse(String transferOutWarehouse) {
        this.transferOutWarehouse = transferOutWarehouse;
    }

    public String getSettleCompMark() {
        return settleCompMark;
    }

    public void setSettleCompMark(String settleCompMark) {
        this.settleCompMark = settleCompMark;
    }

    public double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(double settleAmount) {
        this.settleAmount = settleAmount;
    }

    public Integer getSetteNum() {
        return setteNum;
    }

    public void setSetteNum(Integer setteNum) {
        this.setteNum = setteNum;
    }

    public double getEstimateMoney() {
        return estimateMoney;
    }

    public void setEstimateMoney(double estimateMoney) {
        this.estimateMoney = estimateMoney;
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

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
