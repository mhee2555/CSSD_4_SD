package com.phc.cssd.model;

public class ModelLinenDetail {
    private String Itemname;
    private String UsageCode;
    private String PackDate;
    private String ExpireDate;
    private String Date;
    private String Chk;
    private int index;

    public ModelLinenDetail(String Itemname, String UsageCode, String PackDate, String ExpireDate, String Date, String Chk, int index) {
        this.Itemname = Itemname;
        this.UsageCode = UsageCode;
        this.PackDate = PackDate;
        this.ExpireDate = ExpireDate;
        this.Date = Date;
        this.Chk = Chk;
        this.index = index;
    }


    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public String getChk() { return Chk; }

    public void setChk(String chk) { Chk = chk; }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) {
        UsageCode = usageCode;
    }

    public String getPackDate() {
        return PackDate;
    }

    public void setPackDate(String packDate) {
        PackDate = packDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
