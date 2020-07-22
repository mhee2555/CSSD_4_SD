package com.phc.cssd.model;

public class ModelLinenDetail {
    private String Itemname;
    private String UsageCode;
    private String PackDate;
    private String ExpireDate;
    private String Date;

    public ModelLinenDetail(String Itemname, String UsageCode, String PackDate, String ExpireDate, String Date) {
        this.Itemname = Itemname;
        this.UsageCode = UsageCode;
        this.PackDate = PackDate;
        this.ExpireDate = ExpireDate;
        this.Date = Date;
    }

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
