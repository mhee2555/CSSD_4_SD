package com.phc.cssd.model;

public class BasketTag {
    String  ID,
            Name,
            MacId,BasketCode,
            MacName;
    int     qty;

    public BasketTag(String ID, String name, String macId, String macName, int qty,String BasketCode) {
        this.ID = ID;
        Name = name;
        MacId = macId;
        MacName = macName;
        this.qty = qty;
        this.BasketCode =BasketCode ;
    }

    public String getMacName() {
        return MacName;
    }

    public void setMacName(String macName) {
        MacName = macName;
    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMacId() {
        return MacId;
    }

    public void setMacId(String macId) {
        MacId = macId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
