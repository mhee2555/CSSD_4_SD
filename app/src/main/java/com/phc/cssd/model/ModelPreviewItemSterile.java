package com.phc.cssd.model;

public class ModelPreviewItemSterile {

    private String itemid;
    private String itemcode;
    private String itemname;
    private String Picture_1;
    private String Picture_2;
    private String set_count;
    private String set_qty;
    private String IsSet;
    private String UsageCode;

    public ModelPreviewItemSterile(String itemid, String itemcode, String itemname, String picture_1, String picture_2, String set_count, String set_qty, String IsSet) {
        this.itemid = itemid;
        this.itemcode = itemcode;
        this.itemname = itemname;
        Picture_1 = picture_1;
        Picture_2 = picture_2;
        this.set_count = set_count;
        this.set_qty = set_qty;
        this.IsSet = IsSet;
    }


    public String getUsageCode() { return UsageCode; }

    public void setUsageCode(String usageCode) { UsageCode = usageCode; }

    public String getIsSet() { return IsSet; }

    public void setIsSet(String isSet) { IsSet = isSet; }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
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

    public String getPicture_1() {
        return Picture_1;
    }

    public void setPicture_1(String picture_1) {
        Picture_1 = picture_1;
    }

    public String getPicture_2() {
        return Picture_2;
    }

    public void setPicture_2(String picture_2) {
        Picture_2 = picture_2;
    }

    public String getSet_count() {
        return set_count;
    }

    public void setSet_count(String set_count) {
        this.set_count = set_count;
    }

    public String getSet_qty() {
        return set_qty;
    }

    public void setSet_qty(String set_qty) {
        this.set_qty = set_qty;
    }
}
