package com.phc.cssd.model;

import com.phc.cssd.R;

public class ModelWashDetail implements Cloneable{

    private String ID,
            WashDocNo,
            ItemStockID,
            Qty,
            DepName,
            DepName2,
            itemname,
            itemcode,
            UsageCode,
            age,
            ImportID,
            PackingMatID,
            PackingMat,
            Shelflife,
            BasketName,
            BasketCode,
            IsRemarkExpress;

    boolean isBasket;

    private int index = -1;

    public boolean IsCheck = false;

    public ModelWashDetail(String ID, String washDocNo, String itemStockID, String qty, String depName, String depName2, String itemname, String itemcode, String usageCode, String age, String importID, String packingMatID, String packingMat, String shelflife, String BasketName, String IsRemarkExpress,int index) {
        this.ID = ID;
        WashDocNo = washDocNo;
        ItemStockID = itemStockID;
        Qty = qty;
        DepName = depName;
        DepName2 = depName2;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        PackingMatID = packingMatID;
        PackingMat = packingMat;
        Shelflife = shelflife;
        this.BasketName = BasketName;
        this.IsRemarkExpress = IsRemarkExpress;
        this.index = index;
    }

    public boolean isBasket() {
        return isBasket;
    }

    public void setBasket(boolean basket) {
        isBasket = basket;
    }

    public String getIsRemarkExpress() { return IsRemarkExpress; }

    public void setIsRemarkExpress(String isRemarkExpress) { IsRemarkExpress = isRemarkExpress; }

    public String getBasketName() { return ( BasketName == null || BasketName.equals("-") ) ? "" : (" " + BasketName + " "); }

    public void setBasketName(String basketName) {
        BasketName = basketName;
    }

    public String getPackingMatID() {
        return PackingMatID;
    }

    public void setPackingMatID(String packingMatID) {
        PackingMatID = packingMatID;
    }

    public String getPackingMat() {
        return PackingMat;
    }

    public void setPackingMat(String packingMat) {
        PackingMat = packingMat;
    }

    public String getShelflife() {
        return Shelflife;
    }

    public void setShelflife(String shelflife) {
        Shelflife = shelflife;
    }

    public String getImportID() {
        return ImportID;
    }

    public void setImportID(String importID) {
        ImportID = ( importID.equals("-") || importID.equals("") ) ? null : importID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getWashDocNo() {
        return WashDocNo;
    }

    public void setWashDocNo(String washDocNo) {
        WashDocNo = washDocNo;
    }

    public String getItemStockID() {
        return ItemStockID;
    }

    public void setItemStockID(String itemStockID) {
        ItemStockID = itemStockID;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getDepName() {
        return DepName;
    }

    public void setDepName(String depName) {
        DepName = depName;
    }

    public String getDepName2() {
        return DepName2;
    }

    public void setDepName2(String depName2) {
        DepName2 = depName2;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) {
        UsageCode = usageCode;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCheck() {
        return IsCheck;
    }

    public int getCheck() {
        return IsCheck ? R.drawable.ic_box_check_blue : R.drawable.ic_box_normal_blue;
    }

    public void setCheck(boolean check) {
        IsCheck = check;
    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }

    @Override
    public Object clone() {
        try {
            return (ModelWashDetail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
