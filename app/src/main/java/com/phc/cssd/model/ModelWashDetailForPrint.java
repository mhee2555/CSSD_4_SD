package com.phc.cssd.model;

public class ModelWashDetailForPrint {

    private String
            ID,
            itemcode,
            itemname,
            UsageCode,
            Packer,
            Age,
            MFG,
            EXP,
            CaseLabel,
            PrinterIP,
            UsageCount,
            IsCheckList;

    public ModelWashDetailForPrint(String ID, String itemcode, String itemname, String usageCode, String packer, String age, String MFG, String EXP, String caseLabel, String printerIP, String usageCount, String isCheckList) {
        this.ID = ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
        UsageCode = usageCode;
        Packer = packer;
        Age = age;
        this.MFG = MFG;
        this.EXP = EXP;
        CaseLabel = caseLabel;
        PrinterIP = printerIP;
        UsageCount = usageCount;
        IsCheckList = isCheckList;
    }

    public String getIsCheckList() {
        return IsCheckList;
    }

    public void setIsCheckList(String isCheckList) {
        IsCheckList = isCheckList;
    }

    public String getUsageCount() {
        return UsageCount;
    }

    public void setUsageCount(String usageCount) {
        UsageCount = usageCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) {
        UsageCode = usageCode;
    }

    public String getPacker() {
        return Packer == null ? "- Pack" : Packer;
    }

    public void setPacker(String packer) {
        Packer = packer;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getMFG() {
        return MFG;
    }

    public void setMFG(String MFG) {
        this.MFG = MFG;
    }

    public String getEXP() {
        return EXP;
    }

    public void setEXP(String EXP) {
        this.EXP = EXP;
    }

    public String getCaseLabel() {
        return CaseLabel;
    }

    public void setCaseLabel(String caseLabel) {
        CaseLabel = caseLabel;
    }

    public String getPrinterIP() {
        return PrinterIP;
    }

    public void setPrinterIP(String printerIP) {
        PrinterIP = printerIP;
    }
}
