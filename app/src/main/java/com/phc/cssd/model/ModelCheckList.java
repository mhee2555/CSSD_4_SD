package com.phc.cssd.model;

public class ModelCheckList {
    String ID;
    String Send_ID;
    String itemcode;
    String itemname;
    String Qty;
    String AdminRemark;
    String DateRemark;
    String Remark;
    String NameType;
    boolean IsCheck = false;

    public ModelCheckList(String ID, String send_ID, String itemcode, String itemname, String qty, String adminRemark, String dateRemark, String remark, String NameType, boolean IsCheck) {
        this.ID = ID;
        Send_ID = send_ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
        Qty = qty;
        AdminRemark = adminRemark;
        DateRemark = dateRemark;
        Remark = remark;
        this.NameType = NameType;
        this.IsCheck = IsCheck;
    }

    public String getNameType() {
        return NameType.equals("-") ? "" : ("Type : " + NameType);
    }

    public void setNameType(String nameType) {
        NameType = nameType;
    }

    public boolean isCheck() {
        return IsCheck;
    }

    public void setCheck(boolean check) {
        IsCheck = check;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSend_ID() {
        return Send_ID;
    }

    public void setSend_ID(String send_ID) {
        Send_ID = send_ID;
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

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getAdminRemark() {
        return AdminRemark.equals("-") ? "" : ("By : " + AdminRemark);
    }

    public void setAdminRemark(String adminRemark) {
        AdminRemark = adminRemark;
    }

    public String getDateRemark() {
        return Remark.equals("-") ? "" : DateRemark;
    }

    public void setDateRemark(String dateRemark) {
        DateRemark = dateRemark;
    }

    public String getRemark() {
        return Remark.equals("-") ? "" : ("Remark : " + Remark);
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
