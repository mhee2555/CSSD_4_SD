package com.phc.cssd.model;

public class ModelSendSterileDetail implements Cloneable {

    String  i_id,
            i_code,
            i_name,
            i_alt_name,
            i_barcode,
            i_qty,
            i_program,
            i_program_id,
            PackingMat,
            Shelflife,
            PackingMatID,
            IsWashDept,
            IsRemarkExpress,
            BasketName,
            BasketCode,
            UsageCode;

    private int index = -1;

    boolean isBasket;


    public ModelSendSterileDetail(String i_id, String i_code, String i_name, String i_alt_name, String i_barcode, String i_qty, String i_program, String i_program_id, String packingMat, String shelflife, String packingMatID, String IsWashDept,String IsRemarkExpress,int index) {
        this.i_id = i_id;
        this.i_code = i_code;
        this.i_name = i_name;
        this.i_alt_name = i_alt_name;
        this.i_barcode = i_barcode;
        this.i_qty = i_qty;
        this.i_program = i_program;
        this.i_program_id = i_program_id;
        PackingMat = packingMat;
        Shelflife = shelflife;
        PackingMatID = packingMatID;
        this.IsWashDept = IsWashDept;
        this.IsRemarkExpress = IsRemarkExpress;
        this.index = index;
    }

    public String getBasketName() {
        return BasketName;
    }

    public void setBasketName(String basketName) {
        BasketName = basketName;
    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }

    public boolean isBasket() {
        return isBasket;
    }

    public void setBasket(boolean basket) {
        isBasket = basket;
    }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) {
        UsageCode = usageCode;
    }

    public String getIsRemarkExpress() { return IsRemarkExpress; }

    public void setIsRemarkExpress(String isRemarkExpress) { IsRemarkExpress = isRemarkExpress; }

    public String getIsWashDept() {
        return IsWashDept;
    }

    public void setIsWashDept(String isWashDept) {
        IsWashDept = isWashDept;
    }

    public String getI_id() {
        return i_id;
    }

    public void setI_id(String i_id) {
        this.i_id = i_id;
    }

    public String getI_code() {
        return i_code;
    }

    public void setI_code(String i_code) {
        this.i_code = i_code;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getI_alt_name() {
        return i_alt_name;
    }

    public void setI_alt_name(String i_alt_name) {
        this.i_alt_name = i_alt_name;
    }

    public String getI_barcode() {
        return i_barcode;
    }

    public void setI_barcode(String i_barcode) {
        this.i_barcode = i_barcode;
    }

    public String getI_qty() {
        return i_qty;
    }

    public void setI_qty(String i_qty) {
        this.i_qty = i_qty;
    }

    public String getI_program() {
        return i_program;
    }

    public void setI_program(String i_program) {
        this.i_program = i_program;
    }

    public String getI_program_id() {
        return i_program_id;
    }

    public void setI_program_id(String i_program_id) {
        this.i_program_id = i_program_id;
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

    public String getPackingMatID() {
        return PackingMatID;
    }

    public void setPackingMatID(String packingMatID) {
        PackingMatID = packingMatID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Object clone() {
        try {
            return (ModelSendSterileDetail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
