package com.phc.cssd.model;

public class ModelSterile {

    String ID;
    String DocNo;
    String DocDate;
    String ModifyDate;
    String UserCode;
    String PrepareCode;
    String ApproveCode;
    String DeptID;
    String Qty;
    String IsStatus;
    String sterileProgramID;
    String sterileMachineID;
    String sterileRoundNumber;
    String StartTime;
    String FinishTime;
    String s_time;
    String f_time;
    String Remark;
    String IsOccurance;
    String SterileName;
    String MachineName;
    String usr_sterile;
    String usr_prepare;
    String usr_approve;
    String usr_beforeapprove;
    String PrintAll;
    String PrintCount;
    String TestProgramID;
    String TestProgramName;
    private int index = -1;

    public ModelSterile(//create_sterile
            String ID,
            String docNo,
            String docDate,
            String modifyDate,
            String userCode,

            String prepareCode,
            String approveCode,
            String deptID,
            String qty,
            String isStatus,

            String sterileProgramID,
            String sterileMachineID,
            String sterileRoundNumber,
            String startTime,
            String finishTime,

            String s_time,
            String f_time,
            String remark,
            String isOccurance,
            String sterileName,

            String machineName,
            String usr_sterile,
            String usr_prepare,
            String usr_approve,
            String PrintAll,

            String PrintCount,
            String TestProgramID,
            String TestProgramName,
            int index
    ) {
        this.ID = ID;
        DocNo = docNo;
        DocDate = docDate;
        ModifyDate = modifyDate;
        UserCode = userCode;
        PrepareCode = prepareCode;
        ApproveCode = approveCode;
        DeptID = deptID;
        Qty = qty;
        IsStatus = isStatus;
        this.sterileProgramID = sterileProgramID;
        this.sterileMachineID = sterileMachineID;
        this.sterileRoundNumber = sterileRoundNumber;
        StartTime = startTime;
        FinishTime = finishTime;
        this.s_time = s_time;
        this.f_time = f_time;
        Remark = remark;
        IsOccurance = isOccurance;
        SterileName = sterileName;
        MachineName = machineName;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        this.PrintAll = PrintAll;
        this.PrintCount = PrintCount;
        this.TestProgramID = TestProgramID;
        this.TestProgramName = TestProgramName;
        this.usr_beforeapprove ="-";
        this.index = index;
    }

    public ModelSterile(//display_sterile
            String ID,
            String docNo,
            String docDate,
            String modifyDate,
            String userCode,

            String prepareCode,
            String approveCode,
            String deptID,
            String qty,
            String isStatus,

            String sterileProgramID,
            String sterileMachineID,
            String sterileRoundNumber,
            String startTime,
            String finishTime,

            String s_time,
            String f_time,
            String remark,
            String isOccurance,
            String sterileName,

            String machineName,
            String usr_sterile,
            String usr_prepare,
            String usr_approve,
            String PrintAll,

            String PrintCount,
            String TestProgramID,
            String TestProgramName,
            String usr_beforeapprove,
            int index
    ) {
        this.ID = ID;
        DocNo = docNo;
        DocDate = docDate;
        ModifyDate = modifyDate;
        UserCode = userCode;
        PrepareCode = prepareCode;
        ApproveCode = approveCode;
        DeptID = deptID;
        Qty = qty;
        IsStatus = isStatus;
        this.sterileProgramID = sterileProgramID;
        this.sterileMachineID = sterileMachineID;
        this.sterileRoundNumber = sterileRoundNumber;
        StartTime = startTime;
        FinishTime = finishTime;
        this.s_time = s_time;
        this.f_time = f_time;
        Remark = remark;
        IsOccurance = isOccurance;
        SterileName = sterileName;
        MachineName = machineName;
        this.usr_sterile = usr_sterile;
        this.usr_prepare = usr_prepare;
        this.usr_approve = usr_approve;
        this.PrintAll = PrintAll;
        this.PrintCount = PrintCount;
        this.TestProgramID = TestProgramID;
        this.TestProgramName = TestProgramName;
        this.usr_beforeapprove =usr_beforeapprove;
        this.index = index;
    }

    public String getUsr_beforeapprove() {
        return usr_beforeapprove;
    }

    public void setUsr_beforeapprove(String usr_beforeapprove) {
        this.usr_beforeapprove = usr_beforeapprove;
    }

    public String getPrintBalance() {
        try {
            return Integer.toString(Integer.valueOf(PrintAll).intValue() - Integer.valueOf(PrintCount).intValue() );
        }catch(Exception e){
            return "0";
        }
    }

    public String getTestProgramID() {
        return TestProgramID;
    }

    public void setTestProgramID(String testProgramID) {
        TestProgramID = testProgramID;
    }

    public String getTestProgramName() {
        return TestProgramName;
    }

    public void setTestProgramName(String testProgramName) {
        TestProgramName = testProgramName;
    }

    public String getPrintAll() {
        return PrintAll;
    }

    public void setPrintAll(String printAll) {
        PrintAll = printAll;
    }

    public String getPrintCount() {
        return PrintCount;
    }

    public void setPrintCount(String printCount) {
        PrintCount = printCount;
    }

    public String getID() {
        return ID;
    }

    public String getDocNo() {
        return DocNo;
    }

    public String getDocDate() {
        return DocDate;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public String getUserCode() {
        return UserCode;
    }

    public String getPrepareCode() {
        return PrepareCode;
    }

    public String getApproveCode() {
        return ApproveCode;
    }

    public String getDeptID() {
        return DeptID;
    }

    public String getQty() {
        return Qty;
    }

    public String getIsStatus() {
        return IsStatus;
    }

    public String getSterileProgramID() {
        return sterileProgramID;
    }

    public String getSterileMachineID() {
        return sterileMachineID;
    }

    public String getSterileRoundNumber() {
        return sterileRoundNumber;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public String getS_time() {
        return s_time;
    }

    public String getF_time() {
        return f_time;
    }

    public String getRemark() {
        return Remark;
    }

    public String getIsOccurance() {
        return IsOccurance;
    }

    public String getSterileName() {
        return SterileName;
    }

    public String getMachineName() {
        return MachineName;
    }

    public String getUsr_sterile() {
        return usr_sterile;
    }

    public String getUsr_prepare() {
        return usr_prepare;
    }

    public String getUsr_approve() {
        return usr_approve;
    }

    public int getIndex() {
        return index;
    }
}
