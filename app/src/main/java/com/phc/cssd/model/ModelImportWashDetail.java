package com.phc.cssd.model;

import com.phc.cssd.R;

public class ModelImportWashDetail implements Cloneable {

    private String i_id,
            i_code,
            i_name,
            i_alt_name,
            i_barcode,
            i_qty,
            i_program,
            i_program2,
            i_program_id,
            PackingMat,
            Shelflife,
            PackingMatID,
            BasketName,
            IsRemarkExpress,
            i_UsageCode,
            BasketCode;

    private int index = -1;

    public boolean IsCheck = false;

    public ModelImportWashDetail(int index, boolean isCheck, String i_id, String i_code, String i_name, String i_alt_name, String i_barcode, String i_qty, String i_program, String i_program2, String i_program_id, String PackingMat, String Shelflife, String PackingMatID, String BasketName, String IsRemarkExpress) {
        this.i_id = i_id;
        this.i_code = i_code;
        this.i_name = i_name;
        this.i_alt_name = i_alt_name;
        this.i_barcode = i_barcode;
        this.i_qty = i_qty;
        this.i_program = i_program;
        this.i_program2 = i_program2;
        this.i_program_id = i_program_id;
        this.PackingMat = PackingMat;
        this.Shelflife = Shelflife;
        this.PackingMatID = PackingMatID;
        this.index = index;
        IsCheck = isCheck;
        this.BasketName = BasketName;
        this.IsRemarkExpress = IsRemarkExpress;
    }

    public ModelImportWashDetail(int index, boolean isCheck, String i_id, String i_code, String i_name, String i_alt_name, String i_barcode, String i_qty, String i_program, String i_program2, String i_program_id, String PackingMat, String Shelflife, String PackingMatID, String BasketName, String IsRemarkExpress,String i_UsageCode,String BasketCode) {
        this.i_id = i_id;
        this.i_code = i_code;
        this.i_name = i_name;
        this.i_alt_name = i_alt_name;
        this.i_barcode = i_barcode;
        this.i_qty = i_qty;
        this.i_program = i_program;
        this.i_program2 = i_program2;
        this.i_program_id = i_program_id;
        this.PackingMat = PackingMat;
        this.Shelflife = Shelflife;
        this.PackingMatID = PackingMatID;
        this.index = index;
        IsCheck = isCheck;
        this.BasketName = BasketName;
        this.BasketCode = BasketCode;
        this.IsRemarkExpress = IsRemarkExpress;
        this.i_UsageCode = i_UsageCode;
    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }

    public String getI_UsageCode() {
        return i_UsageCode;
    }

    public void setI_UsageCode(String i_UsageCode) {
        this.i_UsageCode = i_UsageCode;
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

    public String getI_program2() {
        return "(" + i_program2 + ")";
    }

    public void setI_program2(String i_program2) {
        this.i_program2 = i_program2;
    }

    public String getI_program_id() {
        return i_program_id;
    }

    public void setI_program_id(String i_program_id) {
        this.i_program_id = i_program_id;
    }

    public int getIndex() {
        return index;
    }

    public String getI_no() {
        return Integer.toString(index + 1);
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

    public void setI_qty_plus(int qty) {
        this.i_qty = (Integer.parseInt(this.i_qty)+qty)+"";
    }

    @Override
    public Object clone() {
        try {
            return (ModelImportWashDetail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
