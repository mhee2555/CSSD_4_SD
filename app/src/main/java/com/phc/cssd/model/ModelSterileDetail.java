package com.phc.cssd.model;

import com.phc.cssd.R;

public class ModelSterileDetail implements Cloneable {

    String ID;
    String DocNo;
    String ItemStockID;
    String Qty;
    String itemname;

    String itemcode;
    String UsageCode;
    String age;
    String ImportID;
    String SterileDate;

    String ExpireDate;
    String IsStatus;
    String OccuranceQty;
    String DepName;
    String DepName2;

    String LabelID;
    String usr_sterile;
    String usr_prepare;
    String usr_approve;
    String SterileRoundNumber;

    String MachineName;
    String Price;
    String Time;
    String SterileProcessID;

    String Qty_Print = "1";

    String Printer;
    String UsageCount;
    String ItemSetData;
    String PackingMatID;
    String BasketName = "-";
    String BasketCode;
    boolean IsBasket = false;
    String IsRemarkExpress;

    private int index = -1;

    public boolean IsCheck = false;

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String SterileProcessID, int index) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        this.SterileProcessID = SterileProcessID;
        this.index = index;
    }

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String SterileProcessID, String qty_Print, int index) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        this.SterileProcessID = SterileProcessID;
        Qty_Print = qty_Print;
        this.index = index;
    }

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String sterileProcessID, String qty_Print, String printer, String UsageCount, int index) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        SterileProcessID = sterileProcessID;
        Qty_Print = qty_Print;
        Printer = printer;
        this.UsageCount = UsageCount;
        this.index = index;
    }

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String sterileProcessID, String qty_Print, String printer, String usageCount, String itemSetData, int index) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        SterileProcessID = sterileProcessID;
        Qty_Print = qty_Print;
        Printer = printer;
        UsageCount = usageCount;
        ItemSetData = itemSetData;
        this.index = index;
    }

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String sterileProcessID, String qty_Print, String printer, String usageCount, String itemSetData, int index, String IsRemarkExpress) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        SterileProcessID = sterileProcessID;
        Qty_Print = qty_Print;
        Printer = printer;
        UsageCount = usageCount;
        ItemSetData = itemSetData;
        this.index = index;
        this.IsRemarkExpress = IsRemarkExpress;
    }

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String sterileProcessID, String qty_Print, String printer, String usageCount, String itemSetData, String BasketName, String  IsRemarkExpress,int index) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        SterileProcessID = sterileProcessID;
        Qty_Print = qty_Print;
        Printer = printer;
        UsageCount = usageCount;
        ItemSetData = itemSetData;
        this.index = index;
        this.BasketName = BasketName;
        this.IsRemarkExpress = IsRemarkExpress;
    }

    public ModelSterileDetail(String ID, String docNo, String itemStockID, String qty, String itemname, String itemcode, String usageCode, String age, String importID, String sterileDate, String expireDate, String isStatus, String occuranceQty, String depName, String depName2, String labelID, String usr_sterile, String usr_prepare, String usr_approve, String sterileRoundNumber, String machineName, String price, String time, String sterileProcessID, String qty_Print, String printer, String usageCount, String itemSetData, String BasketName, String  IsRemarkExpress,int index,String BasketCode) {
        this.ID = ID;
        DocNo = docNo;
        ItemStockID = itemStockID;
        Qty = qty;
        this.itemname = itemname;
        this.itemcode = itemcode;
        UsageCode = usageCode;
        this.age = age;
        ImportID = importID;
        SterileDate = sterileDate;
        ExpireDate = expireDate;
        IsStatus = isStatus;
        OccuranceQty = occuranceQty;
        DepName = depName;
        DepName2 = depName2;
        LabelID = labelID;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        SterileRoundNumber = sterileRoundNumber;
        MachineName = machineName;
        Price = price;
        Time = time;
        SterileProcessID = sterileProcessID;
        Qty_Print = qty_Print;
        Printer = printer;
        UsageCount = usageCount;
        ItemSetData = itemSetData;
        this.index = index;
        this.BasketName = BasketName;
        this.BasketCode = BasketCode;
        this.IsRemarkExpress = IsRemarkExpress;
    }

    public boolean getIsBasket() {
        return IsBasket;
    }

    public void setIsBasket(boolean isBasket) {
        IsBasket = isBasket;
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

    public String getIsRemarkExpress() {
        return IsRemarkExpress;
    }

    public void setIsRemarkExpress(String isRemarkExpress) {
        IsRemarkExpress = isRemarkExpress;
    }

    public String getBasketName() {
        return ( BasketName == null || BasketName.equals("-") ) ? "" : (" " + BasketName + " ");
    }

    public String getPackingMatID() {
        return PackingMatID;
    }

    public void setPackingMatID(String packingMatID) {
        PackingMatID = packingMatID;
    }

    public String getItemSetData() {
        return ItemSetData;
    }

    public void setItemSetData(String itemSetData) {
        ItemSetData = itemSetData;
    }

    public String getUsageCount() {
        return UsageCount;
    }

    public void setUsageCount(String usageCount) {
        UsageCount = usageCount;
    }

    public String getPrinter() {
        return Printer;
    }

    public void setPrinter(String printer) {
        Printer = printer;
    }

    public String getSterileProcessID() {
        return SterileProcessID;
    }

    public void setSterileProcessID(String sterileProcessID) {
        SterileProcessID = sterileProcessID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getItemStockID() {
        return ItemStockID;
    }

    public int getItemStockId() {
        return Integer.valueOf(ItemStockID).intValue();
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

    public String getAgeDay() {
        return age ;
    }

    public String getExpDay() {
        return " (" + age + ")";
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImportID() {
        return ImportID;
    }

    public void setImportID(String importID) {
        ImportID = importID;
    }

    public String getSterileDate() {
        return SterileDate;
    }

    public void setSterileDate(String sterileDate) {
        SterileDate = sterileDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(String isStatus) {
        IsStatus = isStatus;
    }

    public String getOccuranceQty() {
        return OccuranceQty;
    }

    public void setOccuranceQty(String occuranceQty) {
        OccuranceQty = occuranceQty;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isCheck() {
        return IsCheck;
    }

    public int getCheck() {
        return IsCheck ? R.drawable.ic_box_check_blue : R.drawable.ic_box_normal_blue;
    }

    public int getPrintStatus() {

        //System.out.println("Qty_Print = " + Qty_Print);

        try {
            return Qty_Print.equals("0") ? R.drawable.ic_radio_normal_grey : R.drawable.ic_radio_check_grey;
        }catch(Exception e){
            return R.drawable.ic_radio_normal_grey;
        }
    }

    public int getImagePrintCount() {
        try {
            return Qty_Print.equals("0") ? R.drawable.ic_radio_check_grey : R.drawable.ic_radio_normal_grey;
        }catch(Exception e){
            return R.drawable.ic_radio_normal_grey;
        }
    }

    public void setCheck(boolean check) {
        IsCheck = check;
    }

    public String getLabelID() {
        return LabelID;
    }

    public void setLabelID(String labelID) {
        LabelID = labelID;
    }

    public String getUsr_sterile() {
        return usr_sterile;
    }

    public void setUsr_sterile(String usr_sterile) {
        this.usr_sterile = usr_sterile;
    }

    public String getUsr_prepare() {
        return usr_prepare;
    }

    public void setUsr_prepare(String usr_prepare) {
        this.usr_prepare = usr_prepare;
    }

    public String getUsr_approve() {
        return usr_approve;
    }

    public void setUsr_approve(String usr_approve) {
        this.usr_approve = usr_approve;
    }

    public String getSterileRoundNumber() {
        return SterileRoundNumber;
    }

    public void setSterileRoundNumber(String sterileRoundNumber) {
        SterileRoundNumber = sterileRoundNumber;
    }

    public String getMachineName() {
        return MachineName;
    }

    public void setMachineName(String machineName) {
        MachineName = machineName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getQty_Print() {
        if(Qty_Print.trim().equals(""))
            return "1";
        else
            return Qty_Print;
    }

    public int getPrintCount() {
        try{
            return Integer.valueOf(Qty_Print).intValue();
        }catch(Exception e){
            return 0;
        }
    }

    public void setQty_Print(String qty_Print) {
        Qty_Print = qty_Print;
    }

    @Override
    public Object clone() {
        try {
            return (ModelSterileDetail) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}