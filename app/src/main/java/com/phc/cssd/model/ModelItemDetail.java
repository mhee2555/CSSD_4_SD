package com.phc.cssd.model;

public class ModelItemDetail {
    private boolean IsChecked = false;

    private int index = 0;
    private String ID;
    private String itemcode;
    private String itemname;
    private String Alternatename;
    private String Barcode;
    private String SetCount;
    private String UnitName;
    private String ID_set;
    private String itemDetailID;
    private String Qty;
    private String itemcode_set;
    private String itemname_set;
    private String Alternatename_set;
    private String Barcode_set;
    private String Picture_set;
    private String Picture_detail;
    private int IsChk;

    public ModelItemDetail(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set) {
        this.index = index;
        this.ID = ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
        Alternatename = alternatename;
        Barcode = barcode;
        SetCount = setCount;
        UnitName = unitName;
        this.ID_set = ID_set;
        this.itemDetailID = itemDetailID;
        Qty = qty;
        this.itemcode_set = itemcode_set;
        this.itemname_set = itemname_set;
        Alternatename_set = alternatename_set;
        Barcode_set = barcode_set;

    }

    public ModelItemDetail(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set, String picture_set, String picture_detail,int IsChk) {
        this.index = index;
        this.ID = ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
        Alternatename = alternatename;
        Barcode = barcode;
        SetCount = setCount;
        UnitName = unitName;
        this.ID_set = ID_set;
        this.itemDetailID = itemDetailID;
        Qty = qty;
        this.itemcode_set = itemcode_set;
        this.itemname_set = itemname_set;
        Alternatename_set = alternatename_set;
        Barcode_set = barcode_set;
        Picture_set = picture_set;
        IsChk = IsChk;
    }

    public ModelItemDetail(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set, String picture_set, String picture_detail) {
        this.index = index;
        this.ID = ID;
        this.itemcode = itemcode;
        this.itemname = itemname;
        Alternatename = alternatename;
        Barcode = barcode;
        SetCount = setCount;
        UnitName = unitName;
        this.ID_set = ID_set;
        this.itemDetailID = itemDetailID;
        Qty = qty;
        this.itemcode_set = itemcode_set;
        this.itemname_set = itemname_set;
        Alternatename_set = alternatename_set;
        Barcode_set = barcode_set;
        Picture_set = picture_set;
        Picture_detail = picture_detail;
        this.itemname_set = itemname_set;
    }


    public int getIsChk() { return IsChk; }

    public void setIsChk(int isChk) { IsChk = isChk; }

    public String getPicture_detail() {
        return Picture_detail;
    }

    public void setPicture_detail(String picture_detail) {
        Picture_detail = picture_detail;
    }

    public String getPicture_set() {
        return Picture_set;
    }

    public void setPicture_set(String picture_set) {
        Picture_set = picture_set;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getAlternatename() {
        return Alternatename;
    }

    public void setAlternatename(String alternatename) {
        Alternatename = alternatename;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getSetCount() {
        return SetCount;
    }

    public void setSetCount(String setCount) {
        SetCount = setCount;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getID_set() {
        return ID_set;
    }

    public void setID_set(String ID_set) {
        this.ID_set = ID_set;
    }

    public String getItemDetailID() {
        return itemDetailID;
    }

    public void setItemDetailID(String itemDetailID) {
        this.itemDetailID = itemDetailID;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getItemcode_set() {
        return itemcode_set;
    }

    public void setItemcode_set(String itemcode_set) {
        this.itemcode_set = itemcode_set;
    }

    public String getItemname_set() {
        return itemname_set;
    }

    public void setItemname_set(String itemname_set) {
        this.itemname_set = itemname_set;
    }

    public String getAlternatename_set() {
        return Alternatename_set;
    }

    public void setAlternatename_set(String alternatename_set) {
        Alternatename_set = alternatename_set;
    }

    public String getBarcode_set() {
        return Barcode_set;
    }

    public void setBarcode_set(String barcode_set) {
        Barcode_set = barcode_set;
    }

    public String getRow() {
        return (index+1)+".";
    }
}
