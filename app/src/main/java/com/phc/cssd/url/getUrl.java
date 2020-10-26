package com.phc.cssd.url;

import com.phc.cssd.config.Developer;
import com.phc.cssd.config.Rama;
import com.phc.cssd.config.Setting;
import com.phc.cssd.config.Siriraj;
import com.phc.cssd.config.Vichaiyut;

public class getUrl {
    // ----------------------------------------------------
    // Version
    // ----------------------------------------------------
    public static final int appversion = 12;
    public static final String VERSION = "4U" + "20201012 B";
    // ----------------------------------------------------
    // Var URL
    // ----------------------------------------------------
    public static final String xUrl = getURL();
    // ----------------------------------------------------
    // Project
    // ----------------------------------------------------
    public static final String CSSD_VICHAIYUT = "CSSD_VICHAIYUT";
    public static final String CSSD_PRESENT_RAMA = "CSSD_PRESENT_RAMA";
    public static final String CSSD_SIRIRAJ = "CSSD_SIRIRAJ";
    // ------------------------------- --------------------
    // Active Project
    // ----------------------------------------------------
    public static final String PROJECT = "CSSD_SIRIRAJ";
    // ----------------------------------------------------
    // Get Project URL
    // ----------------------------------------------------
    public static String getURL() {
        /*
        String s;
        switch (PROJECT){.
            case getUrl.CSSD_VICHAIYUT : s = "http://poseintelligence.dyndns.biz:8181/cssd_vcy_test/"; break;
            case getUrl.CSSD_PRESENT_RAMA : s = "http://poseintelligence.dyndns.biz:8181/cssd_vcy_test/"; break;
            default: s = "http://poseintelligence.dyndns.biz:8181/cssd_vcy_test/"; break;
        }
        return s;
        */
//        return "http://poseintelligence.dyndns.biz:8181/cssd_test_itemcode/";
//        return "http://poseintelligence.dyndns.biz:8181/cssd_siriraj/";
//        return "http://poseintelligence.dyndns.biz:8181/cssd_siriraj_dev/";
//        return "http://poseintelligence.dyndns.biz:8181/cssd_siriraj_test/";
//        return "http://poseintelligence.dyndns.biz:8181/cssd_vcy_test/";
//        return "http://192.168.14.48/cssd_vch/";
//        return "http://192.168.1.132/cssd_siriraj/";
//        return "http://192.168.1.104/cssd_vcy_test/";
        // PRESENT
        return "http://poseintelligence.dyndns.biz/cssd_siriraj_dev/";
    }
    // ----------------------------------------------------
    // Get Project Setting
    // ----------------------------------------------------
    public static Setting getSetting() {
        Setting s;
        switch (PROJECT){
            case getUrl.CSSD_VICHAIYUT : s = Vichaiyut.get(); break;
            case getUrl.CSSD_PRESENT_RAMA : s = Rama.get(); break;
            case getUrl.CSSD_SIRIRAJ : s = Siriraj.get(); break;
            default: s = Developer.get(); break;
        }
        return s;
    }

    private String TAG_RESULTS = "result";
    public String getTAG_RESULTS() { return TAG_RESULTS; }
    public String getUrlRegis(){ return xUrl + "regis.php"; }
    public String getUrlUser(){ return xUrl + "getUser.php"; }
    public String getUrlOrderMenu() { return xUrl + "menu_order.1.php"; }
    public String getUrlImg() { return xUrl + "img_menu/"; }
    public String getUrlUnit(){ return xUrl + "setting/get_Units.php"; }
    public String setUrlUnit(){ return xUrl + "setting/set_Units.php"; }
    public String getUrlSpecial(){ return xUrl + "setting/get_Special.php"; }
    public String setUrlSpecial(){ return xUrl + "setting/set_Special.php"; }
    public String getUrlItemType(){ return xUrl + "setting/get_itemtype.php"; }
    public String setUrlItemType(){ return xUrl + "setting/set_itemtype.php"; }
    public String getUrlOccurence(){ return xUrl + "setting/get_occurence.php"; }
    public String setUrlOccurence(){ return xUrl + "setting/set_occurence.php"; }
    public String getUrlWashingprocess(){ return xUrl + "setting/get_washingprocess.php"; }
    public String setUrlWashingprocess(){ return xUrl + "setting/set_washingprocess.php"; }
    public String getUrlLabel(){ return xUrl + "setting/get_label.php"; }
    public String setUrlLabel(){ return xUrl + "setting/set_label.php"; }
    public String getUrlOrganization(){ return xUrl + "setting/get_organization.php"; }
    public String setUrlOrganization(){ return xUrl + "setting/set_organization.php"; }
    public String getUrlSupplier(){ return xUrl + "setting/get_supplier.php"; }
    public String setUrlSupplier(){ return xUrl + "setting/set_supplier.php"; }
    public String getUrlPackingmat(){ return xUrl + "setting/get_packingmat.php"; }
    public String setUrlPackingmat(){ return xUrl + "setting/set_packingmat.php"; }
    public String getUrlSterileprocess(){ return xUrl + "setting/get_sterileprocess.php"; }
    public String setUrlSterileprocess(){ return xUrl + "setting/set_sterileprocess.php"; }
    public String getUsers(){ return xUrl + "setting/get_Users.php"; }
    public String setUsers(){ return xUrl + "setting/set_Users.php"; }
    public String getEmployee(){ return xUrl + "setting/get_employee.php"; }
    public String setEmployee(){ return xUrl + "setting/set_employee.php"; }
    public String getsterilemachine(){ return xUrl + "setting/get_sterilemachine.php"; }
    public String setsterilemachine(){ return xUrl + "setting/set_sterilemachine.php"; }
    public String getUserCode_spinner(){ return xUrl + "Itemstock/get_usageCode_spinner_itemstock.php"; }
    public String getsterileprocess(){ return xUrl + "1/get_sterileprocess.php"; }
    public String getsterileprocessmachine(){ return xUrl + "1/get_sterileprocessmachine.php"; }
    public String getsterileprocessround(){ return xUrl + "1/get_sterileprocessround.php"; }
    public String getsteriledocument(){ return xUrl + "1/get_steriledocument.php"; }
    public String getsteriledocdetail(){ return xUrl + "1/get_steriledocdetail.php"; }
    public String setsteriledocdetail(){ return xUrl + "1/set_steriledocdetail.php"; }
    public String createapprovestockdocno(){ return xUrl + "1/set_createapprovestockdocno.php"; }
    public String getapprovestockdetail(){ return xUrl + "1/get_approvestockdetail.php"; }
    public String cancelapprovestock(){ return xUrl + "1/set_cancelapprovestock.php"; }
    public String updatestockforapprovestock(){ return xUrl + "1/set_updatestockforapprovestock.php"; }
    public String getoccurancetype(){ return xUrl + "1/get_occurancetype.php"; }
    public String getresteriletype(){ return xUrl + "1/get_resteriletype.php"; }
    public String createoccurancedocno(){ return xUrl + "1/set_createoccurancedocno.php"; }
    public String getdepartment(){ return xUrl + "1/get_department.php"; }
    public String getpayoutdocument(){ return xUrl + "1/get_payoutdocument.php"; }
    public String getpayoutdetail(){ return xUrl + "1/get_payoutdetail.php"; }
    public String getsavepayout(){ return xUrl + "1/set_savepayout.php"; }
    public String get_department_spinner(){ return xUrl + "Recall/spinner_department_recall.php"; }
    public String get_itemname_spinner(){ return xUrl + "Recall/spinner_itemname_recall.php"; }
    public String get_list_itemstock(){ return xUrl + "Recall/get_listitemstock_recall.php"; }
    public String get_list_recall(){ return xUrl + "Recall/get_listrecall_recall.php"; }
    public String createdoc_recall(){ return xUrl + "Recall/createdoc_recall.php"; }
    public String updatedoc_recall(){ return xUrl + "Recall/updatedoc_recall.php"; }
    public String set_detail_itemset(){ return xUrl + "Recall/set_detail_itemset.php"; }
    public String delete_document_recall(){ return xUrl + "Recall/delete_document_recall.php"; }
    public String dialog_recall(){ return xUrl + "Recall/get_listrecall_dialog_recall.php"; }
    public String submit_document_recall(){ return xUrl + "Recall/submit_document_recall.php"; }
    public String cancel_document_recall(){ return xUrl + "Recall/cancel_document_recall.php"; }
    public String cancel_item(){ return xUrl + "Recall/cancel_item_recall.php"; }
    public String get_datatree_recall(){ return xUrl + "Recall/get_datatree_recall.php"; }
    public String get_datatree_dialog_recall(){ return xUrl + "Recall/get_datatree_dialog_recall.php"; }
    public String updatestatus_recall(){ return xUrl + "Recall/updatestatus_recall.php"; }
    public String remark_recall(){ return xUrl + "Recall/remark_recall.php"; }
    public String get_remark_recall(){ return xUrl + "Recall/cssd_select_remark_recall.php"; }
    public String get_Login(){ return xUrl + "Login/get_login.php"; }
    public String getItemstock(){ return xUrl + "Itemstock/get_itemstock.php"; }
    public String getSearch_Itemstock(){ return xUrl + "Itemstock/get_search_itemstock.php"; }
    public String Check_usagecode(){ return xUrl + "Itemstock/check_usagecode.php"; }
    public String CreatItemstock(){ return xUrl + "Itemstock/create_itemstock.php"; }
    public String getDepartment_spinner() { return  xUrl + "Itemstock/get_department_spinner_itemstock.php"; }
    public String AddSetData() { return  xUrl + "Itemstock/add_setdata_itemstock.php"; }
    public String Changestatus_itemstock() { return  xUrl + "Itemstock/changestatus_itemstock.php"; }
    public String Change_dept() { return  xUrl + "Itemstock/change_dept_itemstock.php"; }
    public String Add_New_Usagecode() { return  xUrl + "Itemstock/add_new_usagecode.php"; }
    public String Add_New_Usagecode_1() { return  xUrl + "Itemstock/add_new_usagecode(1).php"; }
    public String Get_PreviewSticker(){ return xUrl + "PreviewSticker/get_previewsticker.php"; }
    public String UpdatePreviewSticker(){ return xUrl + "PreviewSticker/update_previewsticker.php"; }
    public String getDepartment(){ return xUrl + "setting/get_department.php"; }
    public String setDepartment(){ return xUrl + "setting/set_department.php"; }
    public String getSterlieProgram(){ return xUrl + "setting/get_sterlieprogram.php"; }
    public String setSterlieProgram(){ return xUrl + "setting/set_sterlieprogram.php"; }
    public String getSterlieProcess_spinner(){ return xUrl + "setting/get_sterlieprocess_spinner.php"; }
    public String getSpinner_sterileprocess(){ return xUrl + "setting/get_spinner_sterileprocess.php"; }
    public String getSpinner_machine(){ return xUrl + "setting/get_spinner_machine.php"; }
    public String getSpinner_washmachine(){ return xUrl + "setting/get_spinner_washmachine.php"; }
    public String getListMachine(){ return xUrl + "setting/get_listdata_machine.php"; }
    public String getListProgram(){ return xUrl + "setting/get_listdata_program.php"; }
    public String getListProcess(){ return xUrl + "setting/get_listdata_process.php"; }
    public String reCreateSterileMachine_process() { return xUrl + "setting/recreate_sterilemachine_process.php"; }
    public String reCreateSterileMachine_program() { return xUrl + "setting/recreate_sterilemachine_program.php"; }
    public String reCreateWashMachine_process() { return xUrl + "setting/recreate_washmachine_process.php"; }
    public String Register_User(){ return xUrl + "setting/register_user.php"; }
    public String get_Spinner_dept(){ return xUrl + "setting/get_department_register.php"; }
    public String get_Spinner_leg(){ return xUrl + "setting/get_language_register.php"; }
    public String getUrlWashMachine(){ return xUrl + "setting/get_washmachine.php"; }
    public String setUrlWashMachine(){ return xUrl + "setting/set_washmachine.php"; }
    public String getUrlWashprocess(){ return xUrl + "setting/get_washprocess.php"; }
    public String setUrlWashprocess(){ return xUrl + "setting/set_washprocess.php"; }
    public String get_Spinner_sticker(){ return xUrl + "setting/get_spinner_sticker.php"; }
    public String getOccurenceType(){ return xUrl + "setting/get_occurencetype.php"; }
    public String setOccurenceType(){ return xUrl + "setting/set_occurencetype.php"; }
    public String get_Spinner_Processoc(){ return xUrl + "setting/get_spinner_processoc.php"; }
    public String searchEmployee(){ return xUrl + "setting/searchEmployee.php"; }
    public String searchItem(){ return xUrl + "Recall/searchItem.php"; }
    public String searchItem_risk(){ return xUrl + "Recall/searchItem_risk.php"; }
    public String getTimetable(){ return xUrl + "setting/get_timetable.php"; }
    public String setTimetable(){ return xUrl + "setting/set_timetable.php"; }
    public String getconvertpayout(){ return xUrl + "1/set_convertpayout.php"; }
    public String setinsertpayoutdetail(){ return xUrl + "1/set_insert_payoutdetail.php"; }
    public String setdeletepayoutdetail(){ return xUrl + "1/set_delete_payoutdetail.php"; }
    public String getApdocument(){ return xUrl + "1/get_approvestockdocument.php"; }
    public String get_list_itemstock_risk(){ return xUrl + "Recall/get_list_itemstock_risk.php"; }
    public String createdoc_recall_risk(){ return xUrl + "Recall/createdoc_recall_risk.php"; }
    public String get_ResterileType(){ return xUrl + "setting/get_resterile_type.php"; }
    public String set_ResterileType(){ return xUrl + "setting/set_resterile_type.php"; }
    public String searchItem_Sterileprogram(){ return xUrl + "setting/searchItem_link.php"; }
    public String getListProgram_Item(){ return xUrl + "setting/get_listdata_program_item.php"; }
    public String reCreateSterleProgram_Item() { return xUrl + "setting/recreate_sterile_program_item.php"; }
    public String getdept() { return xUrl + "sendsterile/getdept_sendsterile.php"; }
    public String getlistdoc() { return xUrl + "sendsterile/getlistdoc_sendsterile.php"; }
    public String getlistdetail() { return xUrl + "sendsterile/getlistdetail_sendsterile.php"; }
    public String UpdateIsSterile() { return xUrl + "sendsterile/UpdateCheckIsSterile_sendsterile.php"; }
    public String UpdateSterile() { return xUrl + "sendsterile/UpdateCheckIsSterile_sendsterile2.php"; }
    public String getitemstock() { return xUrl + "sendsterile/getitemstock_sendsterile.php"; }
    public String InsertDetail() { return xUrl + "sendsterile/InsertDetail_sendsterile.php"; }
    public String InsertDetail1() { return xUrl + "sendsterile/InsertDetail_sendsterile1.php"; }
    public String DeleteDetail() { return xUrl + "sendsterile/DeleteDetail_sendsterile.php"; }
    public String DeleteDetailAll() { return xUrl + "sendsterile/DeleteDetail_sendsterile_all.php"; }
    public String geteditdetail() { return xUrl + "sendsterile/geteditdetail_sendsterile.php"; }
    public String UpdateDocDate() { return xUrl + "sendsterile/UpdateDocDate_sendsterile.php"; }
    public String savedoc() { return xUrl + "sendsterile/savedoc_sendsterile.php"; }
    public String checkdubplicate_sendsterile() { return xUrl + "sendsterile/checkdubplicate_sendsterile.php"; }
    public String getresteriletype_sendsterile() { return xUrl + "sendsterile/getresteriletype_sendsterile.php"; }
    public String getitemdetail_sendsterile1() { return xUrl + "sendsterile/getitemdetail_sendsterile1.php"; }
    public String getdepartmentsp() { return xUrl + "sendsterile/get_department_sendsterile.php"; }
    public String updateremark() { return xUrl + "sendsterile/updateremark_sendsterile.php"; }
    public String updateremarkems() { return xUrl + "sendsterile/updateremark_sendsterile_ems.php"; }
    public String getitemdetail_sendsterile() { return xUrl + "sendsterile/getitemdetail_sendsterile.php"; }
    public String create_oc_sendsterile() { return xUrl + "sendsterile/create_oc_sendsterile.php"; }
    public String create_oc_sendsterile1() { return xUrl + "sendsterile/create_oc_sendsterile1.php"; }
    public String getocurancetype() {
        return xUrl + "occurance/getoccurancetype_occurance.php";
    }
    public String getMachine() { return xUrl + "occurance/getmachine_occurance.php"; }
    public String getround() { return xUrl + "occurance/getround_occurance.php"; }
    public String getround_report() { return xUrl + "cssd_select_round_report.php"; }
    public String getmac_report() { return xUrl + "cssd_select_mac_report.php"; }
    public String getdoc() { return xUrl + "occurance/getdoc_occurance.php"; }
    public String getdocdetail() { return xUrl + "occurance/getdocdetail_occurance.php"; }
    public String createoccurance() { return xUrl + "occurance/create_occurance_occurance.php"; }
    public String updateoccurancecreaterecall() { return xUrl + "occurance/updateoccurance&createrecall_occurance.php"; }
    public String getnewdoc_occurance() {
        return xUrl + "occurance/getnewdoc_occurance.php";
    }
    public String Check_usagecode2() { return xUrl + "occurance/oc_check_usagecode.php";    }
    public String oc_array_usagecode() { return xUrl + "occurance/oc_array_usagecode.php";    }
    public String savenewdoc_occurance() { return xUrl + "occurance/savenewdoc_occurance.php";    }
    public String getmachinecount(){ return xUrl + "1/get_machine_count.php"; }
    public String canceloccurance(){ return xUrl + "1/set_cancelccurance.php"; }
    public String setsteriledocdetailisocc(){ return xUrl + "1/set_steriledocdetail_isoccurence.php"; }
    public String get_Spinner_labelgroup(){ return xUrl + "setting/get_spinner_labelgroup.php"; }
    public String retrunoccurance(){ return xUrl + "1/set_returnoccurance.php"; }
    public String reCreateWashProgram_Item() { return xUrl + "setting/recreate_wash_program_item.php"; }
    public String searchItem_Washprogram(){ return xUrl + "setting/searchItem_link.php"; }
    public String getListProgram_Item_Wash(){ return xUrl + "setting/get_listdata_program_item_Wash.php"; }
    public String getreport() { return xUrl + "cssd_display_zone_report.php"; }
    public String getreportdoc() { return xUrl + "cssd_display_report_name.php"; }
    public String getreport_name() { return xUrl + "cssd_get_report_name.php"; }
}
