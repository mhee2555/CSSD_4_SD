package com.phc.cssd.properties;

/**
 * Created by HPBO on 1/11/2018.
 */
public class pCustomer {
    //main
    private String docno;
    private String docdate;
    private String dept;
    private String deptname;
    private String qty;
    private String note;
    private String IsStatus;
    private String OccuranceID;
    private String UserReceive;
    private String UserSend;
    private String usr_receive;
    private String usr_send;
    //main_detail
    private String SendSterileDocNo;
    private String ItemID;
    private String Itemname;
    private String xqty;
    private String xqty1;
    private String xremark;
    private String Cnt;
    private String machine;
    private String Round;
    private String Packdate;
    private String ItemCount;
    //Edit_detail
    private String sterile;
    private String itemcode;
    private String xqty_detail;
    private String xremark_detail;
    private String checkBox;
    private String IsSterile;
    private boolean singlechk;
    private boolean ischeck;
    private boolean checkIsSterile;
    private Boolean chk_box_wash = true;
    private String ucode;
    private String resteriletype;
    private String resterilename;
    private String occurancetype;
    private String occurancename;
    private String type;
    private String refdocno;
    private String time;
    private String ss_rowid;
    private String IsWashDept;
    private String detailIsStatus;
    private String UsageCount;
    private String IsWeb;
    private String payoutIsStatus;
    private String RemarkExpress;
    private String RemarkEms;
    private String IsDenger;
    private boolean CheckAll;
    private String RemarkAdmin;
    private String IsPicture;
    private String RemarkItemCode;
    private String RemarkDocNo;
    private String QtyItemDetail;


    public String getQtyItemDetail() { return QtyItemDetail; }

    public void setQtyItemDetail(String qtyItemDetail) { QtyItemDetail = qtyItemDetail; }

    public String getRemarkDocNo() { return RemarkDocNo; }

    public void setRemarkDocNo(String remarkDocNo) { RemarkDocNo = remarkDocNo; }

    public String getRemarkItemCode() { return RemarkItemCode; }

    public void setRemarkItemCode(String remarkItemCode) { RemarkItemCode = remarkItemCode; }

    public String getIsPicture() { return IsPicture; }

    public void setIsPicture(String isPicture) { IsPicture = isPicture; }

    public String getRemarkAdmin() { return RemarkAdmin; }

    public void setRemarkAdmin(String remarkAdmin) { RemarkAdmin = remarkAdmin; }

    public String getIsDenger() { return IsDenger; }

    public void setIsDenger(String isDenger) { IsDenger = isDenger; }

    public String getRemarkEms() {
        return RemarkEms;
    }

    public void setRemarkEms(String remarkEms) {
        RemarkEms = remarkEms;
    }

    public String getRemarkExpress() { return RemarkExpress; }

    public void setRemarkExpress(String remarkExpress) { RemarkExpress = remarkExpress; }

    public String getUsageCount() {
        return UsageCount;
    }

    public void setUsageCount(String usageCount) {
        UsageCount = usageCount;
    }

    public String getIsWeb() {
        return IsWeb;
    }

    public void setIsWeb(String IsWeb) {
        this.IsWeb = IsWeb;
    }

    public String getPayoutIsStatus() {
        return payoutIsStatus;
    }

    public void setPayoutIsStatus(String payoutIsStatus) {
        this.payoutIsStatus = payoutIsStatus;
    }

    public String getDetailIsStatus() {
        return detailIsStatus;
    }

    public void setDetailIsStatus(String detailIsStatus) {
        this.detailIsStatus = detailIsStatus;
    }

    public String getItemCount() {
        return ItemCount;
    }

    public void setItemCount(String itemCount) {
        ItemCount = itemCount;
    }

    public String getPackdate() {
        return Packdate;
    }

    public void setPackdate(String packdate) {
        Packdate = packdate;
    }

    public String getUserReceive() {
        return UserReceive;
    }

    public void setUserReceive(String userReceive) {
        UserReceive = userReceive;
    }

    public String getUsr_receive() {
        return usr_receive;
    }

    public void setUsr_receive(String usr_receive) {
        this.usr_receive = usr_receive;
    }

    public String getUsr_send() {
        return usr_send;
    }

    public void setUsr_send(String usr_send) {
        this.usr_send = usr_send;
    }

    public String getUserSend() {
        return UserSend;
    }

    public void setUserSend(String userSend) {
        UserSend = userSend;
    }

    public String getIsWashDept() {
        return IsWashDept;
    }

    public void setIsWashDept(String IsWashDept) {
        this.IsWashDept = IsWashDept;
    }

    public String getSs_rowid() {
        return ss_rowid;
    }

    public void setSs_rowid(String ss_rowid) {
        this.ss_rowid = ss_rowid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRefdocno() {
        return refdocno;
    }

    public void setRefdocno(String refdocno) {
        this.refdocno = refdocno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOccurancetype() {
        return occurancetype;
    }

    public void setOccurancetype(String occurancetype) {
        this.occurancetype = occurancetype;
    }

    public String getOccurancename() {
        return occurancename;
    }

    public void setOccurancename(String occurancename) {
        this.occurancename = occurancename;
    }

    public String getResterilename() {
        return resterilename;
    }

    public void setResterilename(String resterilename) {
        this.resterilename = resterilename;
    }

    public String getResteriletype() {
        return resteriletype;
    }

    public void setResteriletype(String resteriletype) {
        this.resteriletype = resteriletype;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    private String UsageCode;

    public String getIsStatus() {
        return IsStatus;
    }

    public void setIsStatus(String isStatus) {
        IsStatus = isStatus;
    }

    public String getDocno() { return docno; }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getDocdate() {
        return docdate;
    }

    public void setDocdate(String docdate) {
        this.docdate = docdate;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSendSterileDocNo() {
        return SendSterileDocNo;
    }

    public void setSendSterileDocNo(String sendSterileDocNo) { SendSterileDocNo = sendSterileDocNo; }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getXqty() {
        return xqty;
    }

    public void setXqty(String xqty) {
        this.xqty = xqty;
    }

    public String getXqty1() {
        return xqty1;
    }

    public void setXqty1(String xqty1) {
        this.xqty1 = xqty1;
    }

    public String getXremark() {
        return xremark;
    }

    public void setXremark(String xremark) {
        this.xremark = xremark;
    }

    public String getSterile() {
        return sterile;
    }

    public void setSterile(String sterile) {
        this.sterile = sterile;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getXqty_detail() {
        return xqty_detail;
    }

    public void setXqty_detail(String xqty_detail) {
        this.xqty_detail = xqty_detail;
    }

    public String getXremark_detail() {
        return xremark_detail;
    }

    public void setXremark_detail(String xremark_detail) {
        this.xremark_detail = xremark_detail;
    }

    public String getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }

    public String getUsageCode() {
        return UsageCode;
    }

    public void setUsageCode(String usageCode) {
        UsageCode = usageCode;
    }

    public String getIsSterile() {
        return IsSterile;
    }

    public void setIsSterile(String isSterile) {
        IsSterile = isSterile;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getCnt() {
        return Cnt;
    }

    public void setCnt(String cnt) {
        Cnt = cnt;
    }

    public boolean isCheckIsSterile() {
        return checkIsSterile;
    }

    public void setCheckIsSterile(boolean checkIsSterile) {
        this.checkIsSterile = checkIsSterile;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getOccuranceID() {
        return OccuranceID;
    }

    public void setOccuranceID(String occuranceID) {
        OccuranceID = occuranceID;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getRound() {
        return Round;
    }

    public void setRound(String round) {
        Round = round;
    }

    public boolean isSinglechk() {
        return singlechk;
    }

    public void setSinglechk(boolean singlechk) {
        this.singlechk = singlechk;
    }

    public Boolean getChk_box_wash() {
        return chk_box_wash;
    }

    public void setChk_box_wash(Boolean chk_box_wash) {
        this.chk_box_wash = chk_box_wash;
    }
}
