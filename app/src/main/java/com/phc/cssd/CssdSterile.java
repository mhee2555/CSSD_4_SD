package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;
import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.date.CountDownTime;
import com.phc.core.date.DateTime;
import com.phc.core.string.Cons;
import com.phc.core.string.Text2Digit;
import com.phc.cssd.adapter.ImportWashDetailAdapter;
import com.phc.cssd.adapter.ImportWashDetailBigSizeAdapter;
import com.phc.cssd.adapter.ImportWashDetailGridViewAdapter;
import com.phc.cssd.adapter.ImportWashNotPrintDetailAdapter;
import com.phc.cssd.adapter.SterileDetailAdapter;
import com.phc.cssd.adapter.SterileDetailBigSizeAdapter;
import com.phc.cssd.adapter.SterileDetailGridViewAdapter;
import com.phc.cssd.config.ConfigProgram;
import com.phc.cssd.config.CssdSetting;
import com.phc.cssd.config.Setting;
import com.phc.cssd.data.Master;
import com.phc.cssd.data.PreviewSticker;
import com.phc.cssd.model.Model;
import com.phc.cssd.model.ModelImportWashDetail;
import com.phc.cssd.model.ModelSterile;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.model.ModelSterileMachine;
import com.phc.cssd.model.ModelSterileProcess;
import com.phc.cssd.model.ModelWashDetail;
import com.phc.cssd.model.ModelWashDetailForPrint;
import com.phc.cssd.print_sticker.PrintSticker;
import com.phc.cssd.print_sticker.PrintWash;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdSterile extends AppCompatActivity {

    private boolean IsShowBasket = false;
    private boolean OccupancyRate_Con = ConfigProgram.OccupancyRate;
    private TextView OccupancyRate_Sterile;
    private String OccupancyRate_Text_Sterile;
    private boolean IsAdmin = false;

    private List<Model> MODEL= new ArrayList<>();

    PrintSticker PSK = new PrintSticker();
    String DocNo;
    String Text1;
    String Text2;
    String Program ;
    String Time = null;
    String STERILE_PROGRAM_ID = null;
    String STERILE_PROGRAM_NAME = null;

    int cnt;
    int SterileTest;
    int SterileOnTest;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    private String userid = null;
    private String B_ID = null;
    private boolean isTestMac ;
    private boolean isTestBNT;

    // Widget Variable
    private LinearLayout main;
    private ImageView imv_import_all_wash;
    private ImageView imv_remove_all_sterile;
    private TextView txt_doc_no;
    private TextView txt_doc_date;
    private TextView txt_cap_doc_date;
    private TextView txt_usr_prepare;
    private TextView txt_usr_approve;
    private TextView txt_usr_beforeapprove;
    private TextView txt_usr_sterile;
    private TextView txt_test_program;
    private TextView txt_sterile_program;
    private TextView txt_round;
    private TextView txt_start;
    private TextView txt_finish;
    private TextView txt_machine;
    private TextView txt_print_balance;
    private TextView txt_caption;
    private Switch switch_mode;
    private SingleSelectToggleGroup group_choices;
    private ListView list_wash_detail;
    private ListView list_sterile_detail;
    private GridView grid_wash_detail;
    private GridView grid_sterile_detail;
    private ImageView imv_print;
    private ImageView imv_AddItem;
    private ImageView imv_pc_1;
    private ImageView imv_pc_2;
    private ImageView imv_pc_3;
    private ImageView imv_pc_4;
    private ImageView imv_pc_5;
    private ImageView imv_pc_6;
    private ImageView imv_pc_7;
    private ImageView imv_mc_1;
    private ImageView imv_mc_2;
    private ImageView imv_mc_3;
    private ImageView imv_mc_4;
    private ImageView imv_mc_5;
    private TextView txt_mc_1;
    private TextView txt_mc_2;
    private TextView txt_mc_3;
    private TextView txt_mc_4;
    private TextView txt_mc_5;
    private TextView txt_mn_1;
    private TextView txt_mn_2;
    private TextView txt_mn_3;
    private TextView txt_mn_4;
    private TextView txt_mn_5;
    private Button btn_complete;
    private Button btn_print;
    private Button btn_print_bk;
    private Button btn_import;
    private Button btn_export;
    private Button btn_import_new_item_stock;
    private Button btn_checklist;
    private ImageView imageBack;
    private int DISPLAY_MODE = 2;
    private final int i = 0;
    private int STERILE_PROCESS_NUMBER_ACTIVE = 0;
    private int MAX_STERILE_PROCESS = 0;
    private String STERILE_PROCESS_ID_1 = null;
    private String STERILE_PROCESS_ID_2 = null;
    private String STERILE_PROCESS_ID_3 = null;
    private String STERILE_PROCESS_ID_4 = null;
    private String STERILE_PROCESS_ID_5 = null;
    private String STERILE_PROCESS_ID_6 = null;
    private String STERILE_PROCESS_ID_7 = null;
    private List<ModelSterileProcess> STERILE_PROCESS_1;
    private List<ModelSterileProcess> STERILE_PROCESS_2;
    private List<ModelSterileProcess> STERILE_PROCESS_3;
    private List<ModelSterileProcess> STERILE_PROCESS_4;
    private List<ModelSterileProcess> STERILE_PROCESS_5;
    private List<ModelSterileProcess> STERILE_PROCESS_6;
    private List<ModelSterileProcess> STERILE_PROCESS_7;
    private int STERILE_MACHINE_NUMBER_ACTIVE = 0;
    private int MAX_STERILE_MACHINE = 0;
    private List<ModelSterileMachine> MACHINE_1;
    private List<ModelSterileMachine> MACHINE_2;
    private List<ModelSterileMachine> MACHINE_3;
    private List<ModelSterileMachine> MACHINE_4;
    private List<ModelSterileMachine> MACHINE_5;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private List<ModelSterileProcess> MODEL_STERILE_PROCESS = null;
    private List<ModelSterileMachine> MODEL_STERILE_MACHINE = null;
    private List<ModelSterile> MODEL_STERILE = null;
    private List<ModelSterileDetail> MODEL_STERILE_DETAIL = null;
    private List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL = null;
    private List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET = null;

    private List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR = new ArrayList<>();
    private List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR = new ArrayList<>();

    //    HashMap <String ,ArrayList<String>>model_head_show_Usagecode = new HashMap<String ,ArrayList<String>>();
    HashMap<String,List<ModelImportWashDetail>> MAP_MODEL_IMPORT_WASH_DETAIL_SUB = new HashMap<String,List<ModelImportWashDetail>>();
    private Handler handler_1 = new Handler();
    private Handler handler_2 = new Handler();
    private Handler handler_3 = new Handler();
    private Handler handler_4 = new Handler();
    private Handler handler_5 = new Handler();
    String condition1 = "";
    String condition2 = "";
    String condition3 = "";
    String condition4 = "";
    String condition5 = "";
    private Runnable runnable_1;
    private Runnable runnable_2;
    private Runnable runnable_3;
    private Runnable runnable_4;
    private Runnable runnable_5;
    private final int TAB_ACTIVE = 18;
    private final int TAB_NORMAL = 50;
    private final int TXT_ACTIVE = 8;
    private final int TXT_NORMAL = 40;
    private final int TXT_NAME_ACTIVE = 75;
    private final int TXT_NAME_NORMAL = 105;
    private final int TXT_NAME_RUN_ACTIVE = 103;
    private final int TXT_NAME_RUN_NORMAL = 135;
    private int r = R.color.colorRed;
    private int g = R.color.colorGreen;
    private int b = R.color.colorBlue;
    private int gr = R.color.colorGray;
    private boolean PRINT_ACTIVE = false;
    private boolean DIALOG_ACTIVE = false;
    static final int DATE_PICKER_ID = 6628;
    private int year;
    private int month;
    private int day;
    private boolean IsUsrQR = true;
    private Spinner spinner_report;
    private int selected_itemspinner=0;
    private Button bt_report_print;
    private String ProgramTest;
    boolean Print = true;
    private Button button_basket;
    private String basket_Code="";
    private String basket_ID="";
    private ListView basket_dialog_list_basket;
    private ListView basket_dialog_w_list;
    private TextView PairBasketBox_basket_Code;
    private Button pair_fin;
    private TextView packer;
    private String print_w_id ="";
    boolean add_basket_cnt = true;
    private EditText scan_basket;
    private String packer_id="";

    private boolean createSterile_loop = true;
    private ListView basket_dialog_list_washtag;
    private List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_NOT_PRINT;

    Dialog dialog_item_stock_detail_basket;
    private TextView cnt_basket_list;
    private Button btn_import_new_item_stock_to_basket;

    public void onDestroy() {
        super.onDestroy();
        clearHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_sterile);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byIntent();

        byWidget();

        byConfig();

        declarePrinter();

    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
        IsAdmin = intent.getBooleanExtra("IsAdmin", false);
    }

    private void byConfig(){
        Setting s = CssdSetting.getSetting();
        IsShowBasket = s.isShowBasket();

        btn_import_new_item_stock.setVisibility(View.VISIBLE);

        if(!ConfigProgram.basket_tag){
            button_basket.setVisibility(View.INVISIBLE);
            btn_checklist.setVisibility(View.INVISIBLE);
            txt_test_program.setVisibility(View.INVISIBLE);

            TextView textview_test_program = (TextView) findViewById(R.id.textview_test_program);
            textview_test_program.setVisibility(View.INVISIBLE);
            scan_basket.setVisibility(View.INVISIBLE);

            TextView txt_usr_prepare_h = (TextView) findViewById(R.id.txt_usr_prepare_h);
            TextView txt_usr_approve_h = (TextView) findViewById(R.id.txt_usr_approve_h);
            txt_usr_approve_h.setVisibility(View.VISIBLE);
            txt_usr_prepare_h.setVisibility(View.VISIBLE);
            txt_usr_prepare.setVisibility(View.VISIBLE);
            txt_usr_approve.setVisibility(View.VISIBLE);

            txt_usr_approve_h.setText("ผู้ตรวจ");

            txt_usr_beforeapprove.setVisibility(View.GONE);

            btn_print.setVisibility(View.VISIBLE);
            txt_print_balance.setVisibility(View.VISIBLE);

            btn_import_new_item_stock.setVisibility(View.VISIBLE);
        }

        //rollback pair basket
        if(ConfigProgram.pair_basket_2){
            LinearLayout lin_list_washtag = (LinearLayout) dialog_item_stock_detail_basket.findViewById(R.id.lin_list_washtag);
            lin_list_washtag.setVisibility(View.GONE);
            btn_import_new_item_stock_to_basket.setVisibility(View.GONE);
            btn_import_new_item_stock.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    dp_to_px(114), dp_to_px(50));
            layoutParams.setMargins(dp_to_px(35), dp_to_px(585), dp_to_px(951), dp_to_px(20));
            pair_fin.setLayoutParams(layoutParams);

           layoutParams = new RelativeLayout.LayoutParams(
                    dp_to_px(70), dp_to_px(70));
            layoutParams.setMargins(dp_to_px(990), dp_to_px(572), dp_to_px(40), dp_to_px(13));
            btn_print_bk.setLayoutParams(layoutParams);
        }else{
            pair_fin.setBackgroundResource(R.drawable.btn_completed);
        }
    }

    private void newProcess(int ProcessNo, String ProcessId , int SterileR ){

        switch(ProcessNo) {
            case 1 :    STERILE_PROCESS_ID_1 = ProcessId;
                imv_pc_1.setImageResource( getImgR( SterileR, false)); break;
            case 2 :    STERILE_PROCESS_ID_2 = ProcessId;
                imv_pc_2.setImageResource( getImgR( SterileR, false)); break;
            case 3 :    STERILE_PROCESS_ID_3 = ProcessId;
                imv_pc_3.setImageResource( getImgR( SterileR, false)); break;
            case 4 :    STERILE_PROCESS_ID_4 = ProcessId;
                imv_pc_4.setImageResource( getImgR( SterileR, false)); break;
            case 5 :    STERILE_PROCESS_ID_5 = ProcessId;
                imv_pc_5.setImageResource( getImgR( SterileR, false)); break;
            case 6 :    STERILE_PROCESS_ID_6 = ProcessId;
                imv_pc_6.setImageResource( getImgR( SterileR, false)); break;
            case 7 :    STERILE_PROCESS_ID_7 = ProcessId;
                imv_pc_7.setImageResource( getImgR( SterileR, false)); break;
            default: return;
        }
    }


    private void setProcessBackgroundColor(){
        if(STERILE_PROCESS_NUMBER_ACTIVE > 0 && STERILE_PROCESS_NUMBER_ACTIVE < 8) {
            setProcessBackgroundColor(STERILE_PROCESS_NUMBER_ACTIVE);
        }
    }

    private void setProcessBackgroundColor(int ProcessNo){
        try {

            imv_pc_1.setImageResource( getImgR( STERILE_PROCESS_1.get(i).getSterileR(), false));
            imv_pc_2.setImageResource( getImgR( STERILE_PROCESS_2.get(i).getSterileR(), false));
            imv_pc_3.setImageResource( getImgR( STERILE_PROCESS_3.get(i).getSterileR(), false));
            imv_pc_4.setImageResource( getImgR( STERILE_PROCESS_4.get(i).getSterileR(), false));
            imv_pc_5.setImageResource( getImgR( STERILE_PROCESS_5.get(i).getSterileR(), false));
            imv_pc_6.setImageResource( getImgR( STERILE_PROCESS_6.get(i).getSterileR(), false));
            imv_pc_7.setImageResource( getImgR( STERILE_PROCESS_7.get(i).getSterileR(), false));

            switch(ProcessNo) {
                case 1 : imv_pc_1.setImageResource( getImgR( STERILE_PROCESS_1.get(i).getSterileR(), true)); break;
                case 2 : imv_pc_2.setImageResource( getImgR( STERILE_PROCESS_2.get(i).getSterileR(), true)); break;
                case 3 : imv_pc_3.setImageResource( getImgR( STERILE_PROCESS_3.get(i).getSterileR(), true)); break;
                case 4 : imv_pc_4.setImageResource( getImgR( STERILE_PROCESS_4.get(i).getSterileR(), true)); break;
                case 5 : imv_pc_5.setImageResource( getImgR( STERILE_PROCESS_5.get(i).getSterileR(), true)); break;
                case 6 : imv_pc_6.setImageResource( getImgR( STERILE_PROCESS_6.get(i).getSterileR(), true)); break;
                case 7 : imv_pc_7.setImageResource( getImgR( STERILE_PROCESS_7.get(i).getSterileR(), true)); break;
                default: break;
            }

            main.setBackgroundResource(R.drawable.bg_sterile_blue);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void hideProcess(int ProcessNo, boolean IsCancel ){

        //////System.out.println(ProcessNo + " - " + IsCancel);

        switch(ProcessNo) {
            case 1 :    imv_pc_1.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 2 :    imv_pc_2.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 3 :    imv_pc_3.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 4 :    imv_pc_4.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 5 :    imv_pc_5.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 6 :    imv_pc_6.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 7 :    imv_pc_7.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            default: return;
        }
    }

    private String getSterileProcessId() {
        switch(STERILE_PROCESS_NUMBER_ACTIVE) {
            case 1 : return STERILE_PROCESS_ID_1;
            case 2 : return STERILE_PROCESS_ID_2;
            case 3 : return STERILE_PROCESS_ID_3;
            case 4 : return STERILE_PROCESS_ID_4;
            case 5 : return STERILE_PROCESS_ID_5;
            case 6 : return STERILE_PROCESS_ID_6;
            case 7 : return STERILE_PROCESS_ID_7;
            default: return null;
        }
    }

    private int getImgR(int sterileR, boolean p){
        isTestBNT = false;
        switch(sterileR) {
            case 1 : return p ? R.drawable.icon_p1_blue : R.drawable.icon_p1_grey;
            case 2 : return p ? R.drawable.icon_p2_blue : R.drawable.icon_p2_grey;
            case 3 : return p ? R.drawable.ic_fh_blue : R.drawable.ic_fh_grey;
            case 4 : return p ? R.drawable.ic_plasma_blue : R.drawable.ic_plasma_grey;
            case 5 : return p ? R.drawable.ic_hotair_blue : R.drawable.ic_hotair_grey;
            case 6 : return p ? R.drawable.ic_bowie_blue : R.drawable.ic_bowie_grey;
            case 7 : return p ? R.drawable.ic_eo_blue : R.drawable.ic_eo_grey;
            case 8 : return p ? R.drawable.ic_pre_vac_blue : R.drawable.ic_pre_vac_gray;
            case 9 : return p ? R.drawable.ic_eo_white_blue : R.drawable.ic_eo_white_grey;
            case 11 : return p ? R.drawable.icon_p6_blue : R.drawable.icon_p6_grey;
            case 12 : return p ? R.drawable.icon_p7_blue : R.drawable.icon_p7_grey;
            case 10 :
                isTestBNT = true;
                return p ? R.drawable.ic_test_blue : R.drawable.ic_test_grey;
            default: return 0;
        }
    }

    private boolean isBowiedick() {
        switch(STERILE_PROCESS_NUMBER_ACTIVE) {
            case 1 : return STERILE_PROCESS_1.get(i).isBowiedick();
            case 2 : return STERILE_PROCESS_2.get(i).isBowiedick();
            case 3 : return STERILE_PROCESS_3.get(i).isBowiedick();
            case 4 : return STERILE_PROCESS_4.get(i).isBowiedick();
            case 5 : return STERILE_PROCESS_5.get(i).isBowiedick();
            case 6 : return STERILE_PROCESS_6.get(i).isBowiedick();
            case 7 : return STERILE_PROCESS_7.get(i).isBowiedick();
            default: return false;
        }
    }

    private int getProcessId() {
        switch(STERILE_PROCESS_NUMBER_ACTIVE) {
            case 1 : return STERILE_PROCESS_1.get(i).getId();
            case 2 : return STERILE_PROCESS_2.get(i).getId();
            case 3 : return STERILE_PROCESS_3.get(i).getId();
            case 4 : return STERILE_PROCESS_4.get(i).getId();
            case 5 : return STERILE_PROCESS_5.get(i).getId();
            case 6 : return STERILE_PROCESS_6.get(i).getId();
            case 7 : return STERILE_PROCESS_7.get(i).getId();
            default: return 0;
        }
    }

    private void newSterileProcess(String ID, String sterileName, String IsCancel, String SterileR, String ProcessTest, int index){

        List<ModelSterileProcess> list = new ArrayList<>();

        list.add(
                getSterileProcess(
                        ID,
                        sterileName,
                        IsCancel,
                        SterileR,
                        ProcessTest,
                        index
                )
        );

        switch(index) {
            case 1 :
                STERILE_PROCESS_1 = list;
                break;
            case 2 :
                STERILE_PROCESS_2 = list;
                break;
            case 3 :
                STERILE_PROCESS_3 = list;
                break;
            case 4 :
                STERILE_PROCESS_4 = list;
                break;
            case 5 :
                STERILE_PROCESS_5 = list;
                break;
            case 6 :
                STERILE_PROCESS_6 = list;
                break;
            case 7 :
                STERILE_PROCESS_7 = list;
                break;
            default: return;
        }
    }

    private ModelSterileProcess getSterileProcess(
            String ID,
            String SterileName,
            String IsCancel,
            String SterileR,
            String ProcessTest,
            int index
    ){
        return new ModelSterileProcess(
                ID,
                SterileName,
                IsCancel,
                SterileR,
                ProcessTest,
                index
        );
    }

    // =============================================================================================

    private void newMachine(String ID, String machineName, String isActive, String docNo, String startTime, String finishTime, String pauseTime, String isPause, String CountTime, int index){

        List<ModelSterileMachine> list = new ArrayList<>();

        list.add(
                getSterileMachine(
                        ID,
                        machineName,
                        isActive,
                        docNo,
                        startTime,
                        finishTime,
                        pauseTime,
                        isPause,
                        CountTime,
                        index
                )
        );

        switch(index) {
            case 1 :
                MACHINE_1 = list;
                break;
            case 2 :
                MACHINE_2 = list;
                break;
            case 3 :
                MACHINE_3 = list;
                break;
            case 4 :
                MACHINE_4 = list;
                break;
            case 5 :
                MACHINE_5 = list;
                break;
            default: return;
        }
    }

    private void hideMachine(){
        imv_mc_5.setVisibility(MAX_STERILE_MACHINE < 5 ? View.INVISIBLE : View.VISIBLE);
        imv_mc_4.setVisibility(MAX_STERILE_MACHINE < 4 ? View.INVISIBLE : View.VISIBLE);
        imv_mc_3.setVisibility(MAX_STERILE_MACHINE < 3 ? View.INVISIBLE : View.VISIBLE);
        imv_mc_2.setVisibility(MAX_STERILE_MACHINE < 2 ? View.INVISIBLE : View.VISIBLE);
        imv_mc_1.setVisibility(MAX_STERILE_MACHINE < 1 ? View.INVISIBLE : View.VISIBLE);

        txt_mc_5.setVisibility(MAX_STERILE_MACHINE < 5 ? View.INVISIBLE : View.VISIBLE);
        txt_mc_4.setVisibility(MAX_STERILE_MACHINE < 4 ? View.INVISIBLE : View.VISIBLE);
        txt_mc_3.setVisibility(MAX_STERILE_MACHINE < 3 ? View.INVISIBLE : View.VISIBLE);
        txt_mc_2.setVisibility(MAX_STERILE_MACHINE < 2 ? View.INVISIBLE : View.VISIBLE);
        txt_mc_1.setVisibility(MAX_STERILE_MACHINE < 1 ? View.INVISIBLE : View.VISIBLE);

        txt_mn_5.setVisibility(MAX_STERILE_MACHINE < 5 ? View.INVISIBLE : View.VISIBLE);
        txt_mn_4.setVisibility(MAX_STERILE_MACHINE < 4 ? View.INVISIBLE : View.VISIBLE);
        txt_mn_3.setVisibility(MAX_STERILE_MACHINE < 3 ? View.INVISIBLE : View.VISIBLE);
        txt_mn_2.setVisibility(MAX_STERILE_MACHINE < 2 ? View.INVISIBLE : View.VISIBLE);
        txt_mn_1.setVisibility(MAX_STERILE_MACHINE < 1 ? View.INVISIBLE : View.VISIBLE);

        txt_mc_5.setText("");
        txt_mc_4.setText("");
        txt_mc_3.setText("");
        txt_mc_2.setText("");
        txt_mc_1.setText("");

        txt_mn_5.setText("");
        txt_mn_4.setText("");
        txt_mn_3.setText("");
        txt_mn_2.setText("");
        txt_mn_1.setText("");

    }

    private void hideAllMachine(){
        imv_mc_1.setVisibility(View.INVISIBLE);
        imv_mc_2.setVisibility(View.INVISIBLE);
        imv_mc_3.setVisibility(View.INVISIBLE);
        imv_mc_4.setVisibility(View.INVISIBLE);
        imv_mc_5.setVisibility(View.INVISIBLE);

        txt_mc_1.setVisibility(View.INVISIBLE);
        txt_mc_2.setVisibility(View.INVISIBLE);
        txt_mc_3.setVisibility(View.INVISIBLE);
        txt_mc_4.setVisibility(View.INVISIBLE);
        txt_mc_5.setVisibility(View.INVISIBLE);

        txt_mn_1.setVisibility(View.INVISIBLE);
        txt_mn_2.setVisibility(View.INVISIBLE);
        txt_mn_3.setVisibility(View.INVISIBLE);
        txt_mn_4.setVisibility(View.INVISIBLE);
        txt_mn_5.setVisibility(View.INVISIBLE);

        txt_mc_5.setText("");
        txt_mc_4.setText("");
        txt_mc_3.setText("");
        txt_mc_2.setText("");
        txt_mc_1.setText("");

        txt_mn_5.setText("");
        txt_mn_4.setText("");
        txt_mn_3.setText("");
        txt_mn_2.setText("");
        txt_mn_1.setText("");

    }

    private void startMachine(){

        // Check Size Machine
        if(MACHINE_1 != null && MACHINE_1.size() > 0){
            startMachine(1, imv_mc_1, txt_mc_1, txt_mn_1, MACHINE_1);
        }

        if(MACHINE_2 != null && MACHINE_2.size() > 0){
            startMachine(2, imv_mc_2, txt_mc_2, txt_mn_2, MACHINE_2);
        }

        if(MACHINE_3 != null && MACHINE_3.size() > 0){
            startMachine(3, imv_mc_3, txt_mc_3, txt_mn_3, MACHINE_3);
        }

        if(MACHINE_4 != null && MACHINE_4.size() > 0){
            startMachine(4, imv_mc_4, txt_mc_4, txt_mn_4, MACHINE_4);
        }

        if(MACHINE_5 != null && MACHINE_5.size() > 0){
            startMachine(5, imv_mc_5, txt_mc_5, txt_mn_5, MACHINE_5);
        }

    }

    private void startMachineActive(){
        //////System.out.println("startMachineActive() = " + STERILE_MACHINE_NUMBER_ACTIVE);

        // Start Machine Active
        switch(STERILE_MACHINE_NUMBER_ACTIVE){
            case 1 : handler_1.removeCallbacks(runnable_1); startMachine(1, imv_mc_1, txt_mc_1, txt_mn_1, MACHINE_1); break;
            case 2 : handler_2.removeCallbacks(runnable_2); startMachine(2, imv_mc_2, txt_mc_2, txt_mn_2, MACHINE_2); break;
            case 3 : handler_3.removeCallbacks(runnable_3); startMachine(3, imv_mc_3, txt_mc_3, txt_mn_3, MACHINE_3); break;
            case 4 : handler_4.removeCallbacks(runnable_4); startMachine(4, imv_mc_4, txt_mc_4, txt_mn_4, MACHINE_4); break;
            case 5 : handler_5.removeCallbacks(runnable_5); startMachine(5, imv_mc_5, txt_mc_5, txt_mn_5, MACHINE_5); break;
        }

        if(STERILE_MACHINE_NUMBER_ACTIVE > 0) {
            disableButtonAictive();
            main.setBackgroundResource(R.drawable.bg_sterile_red);
        }
    }

    // Machine
    private void startMachine(final int McNo, final ImageView TabMachine, final TextView TextMachine, final TextView TextName, List<ModelSterileMachine> MACHINE){

        // Set Machine Name
        try {
            //TextName.setText(MACHINE.get(i).getMachineName());
            //TextMachine.setText(CountDownTime.getPauseTime(MACHINE.get(i).getCountTime()));

            TextName.setText(MACHINE.get(i).getMachineName());
            TextMachine.setText(MACHINE.get(i).getCountTime());

        }catch(Exception e){
            e.printStackTrace();
        }


        // Check Machine Active && Machine --> Finish
        if(MACHINE.get(i).isActive() && MACHINE.get(i).getFinishTime() != null){
            TabMachine.setImageResource(R.drawable.ic_sterile_red );
            TextName.setTextColor(getResources().getColor(r));
            TextName.setY( STERILE_MACHINE_NUMBER_ACTIVE == McNo ? TXT_NAME_RUN_ACTIVE : TXT_NAME_RUN_NORMAL);

            // Check Pause Machine
            if(MACHINE.get(i).getIsPause().equals("0")) {
                onSterile(MACHINE.get(i).getMachineNumber(), MACHINE.get(i).getFinishTime());
            }

        }else{
            TabMachine.setImageResource(R.drawable.ic_sterile_green );
            TextMachine.setText("");
        }
    }

    private void onSterile(int Machine_Number, final String EndDateTime){
        // Show Machine Status
        switch(Machine_Number){
            case 1 : countSterileMachineNumber_1(EndDateTime); break;
            case 2 : countSterileMachineNumber_2(EndDateTime); break;
            case 3 : countSterileMachineNumber_3(EndDateTime); break;
            case 4 : countSterileMachineNumber_4(EndDateTime); break;
            case 5 : countSterileMachineNumber_5(EndDateTime); break;
        }
    }

    private void clearHandler(){
        handler_1.removeCallbacks(runnable_1);
        handler_2.removeCallbacks(runnable_2);
        handler_3.removeCallbacks(runnable_3);
        handler_4.removeCallbacks(runnable_4);
        handler_5.removeCallbacks(runnable_5);

        list_wash_detail.setAdapter(null);
        grid_wash_detail.setAdapter(null);
    }

    private void countSterileMachineNumber_1(String EndDateTime) {
        final String eTime = EndDateTime;

        runnable_1 = new Runnable() {

            @Override
            public void run() {
                try {

                    handler_1.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date start_date = new Date();
                    Date end_date = dateFormat.parse(eTime);

                    //////System.out.println("x1 = " + eTime);

                    if (!start_date.after(end_date)) {
                        //machine_1.setText(getCountLabel(start_date, end_date));
                        txt_mc_1.setText(CountDownTime.getCountLabel(start_date, end_date));
                    } else {
                        //machine_1.setText(MACHINE_1.get(i).getMachineName());
                        handler_1.removeCallbacks(runnable_1);
                        //getQR(MACHINE_1.get(i).getDocNo(),"s2","1");
                        // Finish Update Sterile IsStatus
                        updateFinishMachine(1, MACHINE_1.get(i).getDocNo());
                        clearForm();
                    }
                } catch (Exception e) {
                    //machine_1.setText(MACHINE_1.get(i).getMachineName());
                    e.printStackTrace();

                    handler_1.removeCallbacks(runnable_1);
                }
            }
        };

        handler_1.postDelayed(runnable_1, 0);
    }

    private void countSterileMachineNumber_2(String EndDateTime) {
        final String eTime = EndDateTime;

        runnable_2 = new Runnable() {

            @Override
            public void run() {
                try {

                    handler_2.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date start_date = new Date();
                    Date end_date = dateFormat.parse(eTime);

                    //////System.out.println("x2 = " + eTime);

                    if (!start_date.after(end_date)) {
                        //machine_2.setText(getCountLabel(start_date, end_date));
                        txt_mc_2.setText(CountDownTime.getCountLabel(start_date, end_date));
                    } else {
                        //machine_2.setText(MACHINE_2.get(i).getMachineName());
                        handler_2.removeCallbacks(runnable_2);
                        //getQR(MACHINE_2.get(i).getDocNo(),"s2","2");
                        // Finish Update Sterile IsStatus
                        updateFinishMachine(2, MACHINE_2.get(i).getDocNo());
                        clearForm();
                    }
                } catch (Exception e) {
                    //machine_2.setText(MACHINE_2.get(i).getMachineName());
                    e.printStackTrace();
                }
            }
        };

        handler_2.postDelayed(runnable_2, 0);
    }

    private void countSterileMachineNumber_3(String EndDateTime) {
        final String eTime = EndDateTime;

        runnable_3 = new Runnable() {

            @Override
            public void run() {
                try {

                    handler_3.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date start_date = new Date();
                    Date end_date = dateFormat.parse(eTime);

                    if (!start_date.after(end_date)) {
                        //machine_3.setText(getCountLabel(start_date, end_date));
                        txt_mc_3.setText(CountDownTime.getCountLabel(start_date, end_date));
                    } else {
                        //machine_3.setText(MACHINE_3.get(i).getMachineName());
                        handler_3.removeCallbacks(runnable_3);
                        //getQR(MACHINE_3.get(i).getDocNo(),"s2","3");
                        //Finish Update Sterile IsStatus
                        updateFinishMachine(3, MACHINE_3.get(i).getDocNo());
                        clearForm();
                    }
                } catch (Exception e) {
                    //machine_1.setText(MACHINE_3.get(i).getMachineName());
                    e.printStackTrace();
                }
            }
        };

        handler_3.postDelayed(runnable_3, 0);
    }

    private void countSterileMachineNumber_4(String EndDateTime) {
        final String eTime = EndDateTime;

        runnable_4 = new Runnable() {

            @Override
            public void run() {
                try {

                    handler_4.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date start_date = new Date();
                    Date end_date = dateFormat.parse(eTime);

                    if (!start_date.after(end_date)) {
                        // machine_4.setText(getCountLabel(start_date, end_date));
                        txt_mc_4.setText(CountDownTime.getCountLabel(start_date, end_date));
                    } else {
                        //machine_4.setText(MACHINE_4.get(i).getMachineName());
                        handler_4.removeCallbacks(runnable_4);
                        // getQR(MACHINE_4.get(i).getDocNo(),"s2","4");
                        // Finish Update Sterile IsStatus
                        updateFinishMachine(4, MACHINE_4.get(i).getDocNo());
                        clearForm();
                    }
                } catch (Exception e) {
                    //machine_4.setText(MACHINE_4.get(i).getMachineName());
                    e.printStackTrace();
                }
            }
        };

        handler_4.postDelayed(runnable_4, 0);
    }

    private void countSterileMachineNumber_5(String EndDateTime) {
        final String eTime = EndDateTime;

        runnable_5 = new Runnable() {

            @Override
            public void run() {
                try {

                    handler_5.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date start_date = new Date();
                    Date end_date = dateFormat.parse(eTime);

                    if (!start_date.after(end_date)) {
                        // machine_5.setText(getCountLabel(start_date, end_date));
                        txt_mc_5.setText(CountDownTime.getCountLabel(start_date, end_date));
                    } else {
                        //machine_5.setText(MACHINE_5.get(i).getMachineName());
                        handler_5.removeCallbacks(runnable_5);
                        // getQR(MACHINE_5.get(i).getDocNo(),"s2","5");
                        // Finish Update Sterile IsStatus
                        updateFinishMachine(5, MACHINE_5.get(i).getDocNo());
                        clearForm();
                    }
                } catch (Exception e) {
                    //machine_5.setText(MACHINE_5.get(i).getMachineName());
                    e.printStackTrace();
                }
            }
        };

        handler_5.postDelayed(runnable_5, 0);
    }


    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();

        generateProcess();
    }


    @Override
    protected void onResume() {
        super.onResume();

        displayWashDetail(getSterileProcessId());
        /*
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
        updateDeviceList();
        */

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        TimeTest();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_PICKER_ID:return new DatePickerDialog(this, pickerListener, mYear, mMonth, mDay);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            //txt_doc_date.setText(DateTime.getDate());
            txt_doc_date.setText( Text2Digit.twoDigit(Integer.toString(day)) + "-" +  Text2Digit.twoDigit(Integer.toString(month + 1)) + "-" + year );

        }
    };


    private void byWidget(){
        //mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        main = (LinearLayout)findViewById(R.id.main);
        OccupancyRate_Sterile = (TextView ) findViewById(R.id.OccupancyRate_Sterile);
        if (OccupancyRate_Con == true){
            OccupancyRate_Sterile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (checkMachineActive()) {
                        if (!txt_doc_no.getText().toString().equals("")) {
                            openDropDown1("wash_test_program", "");
                        }else {
                            Toast.makeText(CssdSterile.this, "กรุณาเพิ่มรายการก่อนทำรายการ !!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CssdSterile.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            OccupancyRate_Sterile.setVisibility(View.VISIBLE);
//            OccupancyRate_Sterile.setOnKeyListener(new View.OnKeyListener() {
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                        switch (keyCode) {
//                            case KeyEvent.KEYCODE_DPAD_CENTER:
//                            case KeyEvent.KEYCODE_ENTER:
//                                OccupancyRate_Text_Sterile = OccupancyRate_Sterile.getText().toString();
//                                return true;
//                            default:
//                                break;
//                        }
//                    }
//                    return false;
//                }
//            });
        }else {
            OccupancyRate_Sterile.setVisibility(View.INVISIBLE);
        }

        imv_import_all_wash = (ImageView)findViewById(R.id.imv_import_all_wash);
        imv_remove_all_sterile = (ImageView)findViewById(R.id.imv_remove_all_sterile);

//        list_wash_detail = (ListView) findViewById(R.id.list_wash_detail);

        list_wash_detail = (ListView) findViewById(R.id.list_wash_detail);
        list_sterile_detail = (ListView) findViewById(R.id.list_sterile_detail);

        grid_wash_detail = (GridView) findViewById(R.id.grid_wash_detail);
        grid_sterile_detail = (GridView) findViewById(R.id.grid_sterile_detail);

        txt_doc_no = (TextView)findViewById(R.id.txt_doc_no);
        txt_doc_date = (TextView)findViewById(R.id.txt_doc_date);
        txt_cap_doc_date = (TextView)findViewById(R.id.txt_cap_doc_date);
        txt_round = (TextView)findViewById(R.id.txt_round);
        txt_sterile_program = (TextView)findViewById(R.id.txt_sterile_program);
        txt_start = (TextView)findViewById(R.id.txt_start);
        txt_finish = (TextView)findViewById(R.id.txt_finish);

        //spinner_report = (Spinner) findViewById(R.id.spinner_report);
        bt_report_print= (Button) findViewById(R.id.bt_report_print);

        bt_report_print.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!txt_doc_no.getText().toString().equals("")){
                    GetReport(txt_doc_no.getText().toString());
                }else{
                    Toast.makeText(CssdSterile.this, "ยังไม่มีเอกสาร", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_doc_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //////System.out.println(getDocNo() + " , " + STERILE_PROCESS_NUMBER_ACTIVE);

                try {
                    //if ( (getDocNo() == null || getDocNo().equals("-")) && STERILE_PROCESS_NUMBER_ACTIVE != 6 && STERILE_MACHINE_NUMBER_ACTIVE > 0) {
                    if ( (getDocNo() == null || getDocNo().equals("-")) && !isBowiedick()) {
                        showDialog(DATE_PICKER_ID);
                    }
                }catch(Exception e){
                    return;
                }
            }
        });

        txt_cap_doc_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    if ( (getDocNo() == null || getDocNo().equals("-")) && !isBowiedick()) {
                        showDialog(DATE_PICKER_ID);
                    }
                }catch(Exception e){
                    return;
                }
            }
        });

        txt_usr_prepare = (TextView)findViewById(R.id.txt_usr_prepare);
        txt_usr_approve = (TextView)findViewById(R.id.txt_usr_approve);
        txt_usr_beforeapprove = (TextView)findViewById(R.id.txt_usr_beforeapprove);
        txt_usr_sterile = (TextView)findViewById(R.id.txt_usr_sterile);
        txt_test_program = (TextView)findViewById(R.id.txt_test_program);

        txt_print_balance = (TextView)findViewById(R.id.txt_print_balance);
        txt_caption = (TextView)findViewById(R.id.txt_caption);

        txt_machine = (TextView)findViewById(R.id.txt_machine);

        txt_machine.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(getSterileProcessId() != null) {
                    openDropDown("sterile_machine", getSterileProcessId());
                }else{
                    Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือกการฆ่าเชื้อ!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // =========================================================================================

        txt_usr_prepare.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(checkMachineActive()) {
                    if(IsUsrQR)
                        openQR("user_prepare");
                    else
                        openDropDown("user_prepare", null);
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_usr_approve.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(checkMachineActive()) {
                    if(IsUsrQR)
                        openQR("user_approve");
                    else
                        openDropDown("user_approve", null);
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_usr_sterile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(checkMachineActive()) {
                    openDropDown("user_sterile", null);
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_test_program.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(checkMachineActive()) {
                    if (txt_usr_prepare.getText().toString().equals("") && txt_usr_approve.getText().toString().equals("")){
                        Toast.makeText(CssdSterile.this, "กรุณากรอกข้อมูลผู้เตรียมผู้ตรวจให้ครบถ้วน !!", Toast.LENGTH_SHORT).show();
                    }else {
                        openDropDown("sterile_test_program", Integer.toString(getProcessId()));
                    }
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        imv_import_all_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImport();
            }
        });

        imv_remove_all_sterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveSterileDetail();
            }
        });

        // =========================================================================================

        imv_print = (ImageView) findViewById(R.id.imv_print);
        imv_print.bringToFront();
        imv_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CssdSterile.this, CssdSearchSterile.class);
                intent.putExtra("userid", userid);
                intent.putExtra("B_ID", B_ID);
                startActivity(intent);
            }
        });

        imv_pc_1 = (ImageView) findViewById(R.id.imv_pc_1);
        imv_pc_2 = (ImageView) findViewById(R.id.imv_pc_2);
        imv_pc_3 = (ImageView) findViewById(R.id.imv_pc_3);
        imv_pc_4 = (ImageView) findViewById(R.id.imv_pc_4);
        imv_pc_5 = (ImageView) findViewById(R.id.imv_pc_5);
        imv_pc_6 = (ImageView) findViewById(R.id.imv_pc_6);
        imv_pc_7 = (ImageView) findViewById(R.id.imv_pc_7);

        imv_pc_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 1;

                //////System.out.println(STERILE_PROCESS_ID_1 + " = " + STERILE_PROCESS_1.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_1);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());

            }
        });

        imv_pc_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 2;

                //////System.out.println(STERILE_PROCESS_ID_2 + " = " + STERILE_PROCESS_2.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_2);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());
            }
        });

        imv_pc_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 3;

                //////System.out.println(STERILE_PROCESS_ID_3 + " = " + STERILE_PROCESS_3.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_3);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());

            }
        });

        imv_pc_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 4;

                //////System.out.println(STERILE_PROCESS_ID_4 + " = " + STERILE_PROCESS_4.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_4);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());
            }
        });

        imv_pc_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 5;

                //////System.out.println(STERILE_PROCESS_ID_5 + " = " + STERILE_PROCESS_5.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_5);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());
            }
        });

        imv_pc_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 6;

                //////System.out.println(STERILE_PROCESS_ID_6 + " = " + STERILE_PROCESS_6.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_6);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());
            }
        });

        imv_pc_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                STERILE_PROCESS_NUMBER_ACTIVE = 7;

                //////System.out.println(STERILE_PROCESS_ID_7 + " = " + STERILE_PROCESS_7.get(i).getID() + " = " + getSterileProcessId());

                clearHandler();

                defaultTabMachine();

                setProcessBackgroundColor();

                generateMachine(STERILE_PROCESS_ID_7);

                // Display Wash Detail
                displayWashDetail(getSterileProcessId());
            }
        });

        // =========================================================================================
        // machine
        txt_mc_1 = (TextView)findViewById(R.id.txt_mc_1);
        txt_mc_2 = (TextView)findViewById(R.id.txt_mc_2);
        txt_mc_3 = (TextView)findViewById(R.id.txt_mc_3);
        txt_mc_4 = (TextView)findViewById(R.id.txt_mc_4);
        txt_mc_5 = (TextView)findViewById(R.id.txt_mc_5);

        txt_mn_1 = (TextView)findViewById(R.id.txt_mn_1);
        txt_mn_2 = (TextView)findViewById(R.id.txt_mn_2);
        txt_mn_3 = (TextView)findViewById(R.id.txt_mn_3);
        txt_mn_4 = (TextView)findViewById(R.id.txt_mn_4);
        txt_mn_5 = (TextView)findViewById(R.id.txt_mn_5);

        imv_mc_1 = (ImageView) findViewById(R.id.imv_mc_1);
        imv_mc_2 = (ImageView) findViewById(R.id.imv_mc_2);
        imv_mc_3 = (ImageView) findViewById(R.id.imv_mc_3);
        imv_mc_4 = (ImageView) findViewById(R.id.imv_mc_4);
        imv_mc_5 = (ImageView) findViewById(R.id.imv_mc_5);

        imv_mc_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(1);
            }
        });

        imv_mc_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(2);
            }
        });

        imv_mc_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(3);
            }
        });

        imv_mc_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(4);
            }
        });

        imv_mc_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(5);
            }
        });

        imv_mc_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(txt_doc_no.getText().toString(),1);
                return true;
            }
        });

        imv_mc_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(txt_doc_no.getText().toString(),2);

                return true;
            }
        });

        imv_mc_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(txt_doc_no.getText().toString(),3);
                return true;
            }
        });

        imv_mc_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(txt_doc_no.getText().toString(),4);

                return true;
            }
        });

        imv_mc_5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(txt_doc_no.getText().toString(),5);

                return true;
            }
        });

        // =========================================================================================

        btn_complete = (Button) findViewById(R.id.btn_add);
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_import = (Button) findViewById(R.id.btn_import);
        btn_export = (Button) findViewById(R.id.btn_export);
        btn_import_new_item_stock = (Button) findViewById(R.id.btn_import_new_item_stock);
        btn_checklist = (Button) findViewById(R.id.btn_checklist);

        btn_checklist.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                callCheckList();
            }
        });

        btn_import_new_item_stock.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(!chk_mac()){
                    return;
                }

                Intent i = new Intent(CssdSterile.this, CssdNewItemStock.class);
                i.putExtra("p_SterileProcessID", getSterileProcessId());
                i.putExtra("B_ID", B_ID);
                startActivityForResult(i, Master.CssdSterileDraft);

            }
        });

        btn_import.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(!checkMachineActive()) {
                    Toast.makeText(CssdSterile.this, "ไม่สามารถเพิ่มได้ เนื่องจากเครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(STERILE_MACHINE_NUMBER_ACTIVE > 0) {
                    Intent i = new Intent(CssdSterile.this, CssdSterileDraft.class);
                    i.putExtra("STERILE_MACHINE_ID", getMachineId(STERILE_MACHINE_NUMBER_ACTIVE));
                    i.putExtra("B_ID", B_ID);
                    startActivityForResult(i, Master.CssdSterileDraft);
                }else{
                    Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือกเครื่องฆ่าเชื้อ !!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_export.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(getDocNo() == null ) {
                    Toast.makeText(CssdSterile.this, "ไม่มีเอกสารฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!checkMachineActive()) {
                    Toast.makeText(CssdSterile.this, "เครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (txt_doc_no.getText().toString().equals("")){
                    Toast.makeText(CssdSterile.this, "ไม่มีเอกสารฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
                    quitDialog.setTitle(Cons.TITLE);
                    quitDialog.setIcon(R.drawable.pose_favicon_2x);
                    quitDialog.setMessage(Cons.CONFIRM_EXPORT);

                    quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ============================================= Clear ==============================================
                            updateSterileMachineClearDocNo(getMachineId(STERILE_MACHINE_NUMBER_ACTIVE), STERILE_MACHINE_NUMBER_ACTIVE);
                            // ============================================= Clear ==============================================
                        }
                    });

                    quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    quitDialog.show();

                }
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TimeTest();
                updateStartMachine();
            }
        });

        switch_mode = (Switch) findViewById(R.id.switch_mode);
        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String p_SterileProcessID = getSterileProcessId();
                Log.d("tog_displayWashDetail","switch_mode");
                displayWashDetail(p_SterileProcessID);
            }
        });


        group_choices = (SingleSelectToggleGroup) findViewById(R.id.group_choices);
        group_choices.setVisibility(View.INVISIBLE);
//        group_choices.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
//
//                CircularToggle c = (CircularToggle) findViewById(checkedId);
//
//                String txt = c.getText().toString();
//
//                if(txt.equals("S")){
//                    DISPLAY_MODE = 3;
//                }else if(txt.equals("M")){
//                    DISPLAY_MODE = 2;
//                }else if(txt.equals("L")){
//                    DISPLAY_MODE = 1;
//                }else{
//                    DISPLAY_MODE = 0;
//                    return;
//                }
//
//                displayMode();
//
//            }
//        });

        imv_AddItem = (ImageView) findViewById(R.id.imv_AddItem);
        imv_AddItem.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openSearchItemStock();
            }
        });

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHandler(); finish();
            }
        });

        imageBack.bringToFront();
        txt_caption.bringToFront();
        group_choices.bringToFront();
        switch_mode.bringToFront();

        //basket
        scan_basket = (EditText) findViewById(R.id.scan_basket);
        scan_basket.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                Log.d("ttest_scan","keyCode = "+keyCode);
                String txt = scan_basket.getText().toString().toLowerCase();
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            if(txt.length()>6){
                                for(int i=0;i<MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET.size();i++){
                                    if(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET.get(i).getI_UsageCode().toLowerCase().equals(txt)){
                                        ModelImportWashDetail x = MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET.get(i);
                                        onImport(x.getI_code(),
                                                x.getI_program_id(),
                                                x.getPackingMatID(),
                                                x.getI_qty(),
                                                x.getI_UsageCode()
                                        );
                                    }
                                }

                                scan_basket.setText("");
                                return true;
                            }

                            Log.d("ttest_scan","txt = "+txt);
                            if((MAP_MODEL_IMPORT_WASH_DETAIL_SUB.containsKey(txt))&&(!txt.equals(""))){//&&chk_mac()

                                List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_TO_ADD = MAP_MODEL_IMPORT_WASH_DETAIL_SUB.get(txt);
                                importWashDetail(
                                        MODEL_IMPORT_WASH_DETAIL_TO_ADD
                                );
                            }else{
                                Toast.makeText(CssdSterile.this, "ไม่พบตะกร้า ("+txt+")!!", Toast.LENGTH_SHORT).show();
                            }

                            scan_basket.setText("");
                            return true;
                        default:
                            break;
                    }
                }else if(keyCode==KeyEvent.KEYCODE_ENTER){
                    return true;
                }

                return false;

            }
        });
        //DialogCustomTheme pair basket

        dialog_item_stock_detail_basket = new Dialog(CssdSterile.this, R.style.DialogCustomTheme);

        dialog_item_stock_detail_basket.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_item_stock_detail_basket.setContentView(R.layout.dialog_item_stock_detail_basket_sterile);

        dialog_item_stock_detail_basket.setCancelable(false);

        dialog_item_stock_detail_basket.setTitle("");

        basket_dialog_list_basket = (ListView) dialog_item_stock_detail_basket.findViewById(R.id.list_basket);
        basket_dialog_w_list = (ListView) dialog_item_stock_detail_basket.findViewById(R.id.list);
        basket_dialog_list_washtag = (ListView) dialog_item_stock_detail_basket.findViewById(R.id.list_washtag);
        btn_print_bk = (Button) dialog_item_stock_detail_basket.findViewById(R.id.btn_save);
        pair_fin = (Button) dialog_item_stock_detail_basket.findViewById(R.id.btn_cancel);
        final EditText edt_basket_code = (EditText) dialog_item_stock_detail_basket.findViewById(R.id.edt_basket_code);
        PairBasketBox_basket_Code = (TextView) dialog_item_stock_detail_basket.findViewById(R.id.bastek_name);
        cnt_basket_list = (TextView) dialog_item_stock_detail_basket.findViewById(R.id.cnt_basket_list);
        btn_import_new_item_stock_to_basket = (Button) dialog_item_stock_detail_basket.findViewById(R.id.btn_import_new_item_stock_to_basket);

        btn_import_new_item_stock_to_basket.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent i = new Intent(CssdSterile.this, CssdNewItemStockToWash.class);
                i.putExtra("B_ID", B_ID);
                i.putExtra("userid", userid);
                startActivity(i);
            }
        });

        cnt_basket_list.setText("");

        packer = (TextView) dialog_item_stock_detail_basket.findViewById(R.id.packer);
        packer.setText("");
//        packer.setVisibility(View.GONE);

        pair_fin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn_print_bk.setText("0");
                basket_dialog_list_basket.setAdapter(null);
                PairBasketBox_basket_Code.setText("");
                cnt_basket_list.setText("");
                basket_Code = "";
                basket_ID = "";
                packer_id = "";
                edt_basket_code.setText("");
                dialog_item_stock_detail_basket.dismiss();
                displayWashDetail(getSterileProcessId());
            }
        });

        edt_basket_code.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            String edt = edt_basket_code.getText().toString().toUpperCase();
                            boolean b=true;

                            if(!ConfigProgram.pair_basket_2){
                                if(edt.length()>6){
                                    final String basket = basket_ID;

                                    if(!basket.equals("")){
                                        for(int i=0;i<MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.size();i++){
                                            Log.d("tog_scan_usage",edt+" --- "+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.get(i).getI_UsageCode());
                                            if(edt.equals(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.get(i).getI_UsageCode())){
                                                b=false;
                                                if(CheckProgramTypePairBasket(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.get(i).getI_program_type())){
                                                    importWashDetailToBasket(
                                                            MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.get(i).getI_UsageCode()
                                                    );
                                                }else{
                                                    Toast.makeText(CssdSterile.this, "โปรแกรมฆ่าเชื้อไม่ตรงกันกับรายการในตะกร้า !!", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }
                                        }

                                        if(b){
                                            b=true;
                                            for(int i=0;i<MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size();i++){
                                                Log.d("tog_scan_usage",edt+" --- "+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(i).getI_UsageCode());
                                                if(edt.equals(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(i).getI_UsageCode())){
                                                    b=false;
                                                    break;
                                                }
                                            }

                                            if(b){
                                                Toast.makeText(CssdSterile.this, "ไม่พบรหัสรายการ", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(CssdSterile.this, "สแกนรหัสใช้งานซ้ำ", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }else{
                                        Toast.makeText(CssdSterile.this, "Scan QR Code Basket Tag !!", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    selectBasket(edt,edt_basket_code);
                                }

                            }else{
                                selectBasket(edt,edt_basket_code);
                            }
                            
                            edt_basket_code.setText("");

                            return true;
                        default:
                            break;
                    }
                }

                return false;

            }


        });

        Button add_basket_Button = (Button) dialog_item_stock_detail_basket.findViewById(R.id.add_basket_Button);
        add_basket_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_basket_cnt){
                    add_basket_cnt = false;
                    add_basket();
                }
            }
        });

        final Dialog dialog_qr = new Dialog(CssdSterile.this, R.style.DialogCustomTheme);

        dialog_qr.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_qr.setContentView(R.layout.activity_check_qr__approve);

        dialog_qr.setCancelable(false);

        dialog_qr.setTitle("");

        Button bt_cancel = (Button) dialog_qr.findViewById(R.id.bt_cancel);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_qr.dismiss();
            }
        });

        final EditText etxt_qr_pack = (EditText) dialog_qr.findViewById(R.id.etxt_qr);
        etxt_qr_pack.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            Checkuser_packer(etxt_qr_pack.getText().toString(),dialog_qr,dialog_item_stock_detail_basket);
                            etxt_qr_pack.setText("");
                            return true;
                        default:
                            break;
                    }
                }

                return false;

            }


        });
//        DialogCustomTheme

        btn_print_bk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int xn =0;

                print_w_id = "";
                if(ConfigProgram.pair_basket_2){

                    for(int i=0;i<MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size();i++){

                        Log.d("ttest_for_print","w_id"+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(i).getI_id());
                        if(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(i).getPrint_count()<=0){
                            print_w_id=print_w_id+","+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(i).getI_id();
                            xn = xn+1;
                        }
                    }
                }else{
                    for(int i=0;i<MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.size();i++){

                        Log.d("ttest_for_print","w_id"+MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).getI_id());
                        if(MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).isCheck()){
                            print_w_id=print_w_id+","+MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).getI_id();
                            xn = xn+1;
                        }
                    }
                }


                set_num_btn_print_bk(xn);

                if(xn==0){
                    Toast.makeText(CssdSterile.this, "ไม่พบรายการในตะกร้าที่พิมพ์ได้!!", Toast.LENGTH_SHORT).show();
                }else{
                    print_w_id = print_w_id.substring(1,print_w_id.length());

                    Log.d("ttest_W_id","w_id"+print_w_id);

                    checkPacker(print_w_id);

//                    dialog_qr.show();
                }
            }
        });

        btn_print.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    Log.d("SSSS","PRINTER_IP : "+PRINTER_IP);
                    onPrint();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        button_basket = (Button) findViewById(R.id.button_basket);
        button_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialogProgress = new ProgressDialog(CssdSterile.this);
                dialogProgress.setMessage(Cons.WAIT_FOR_PROCESS);
                dialogProgress.show();
                basket_Code = "";
                basket_ID = "";
                //ปิดเลือกโปรแกรมก่อน
//                if (STERILE_PROCESS_NUMBER_ACTIVE == 0){
//                    Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือกวิธีฆ่าเชื้อ!!", Toast.LENGTH_SHORT).show();
//                    dialogProgress.dismiss();
//                    return;
//                }
//                basket_dialog_w_list.setAdapter(new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR,MAP_MODEL_IMPORT_WASH_DETAIL_SUB,4));
                //ปิดเลือกโปรแกรมก่อน จบ
                displayWashDetail(getSterileProcessId());
                dialog_qr.show();
                dialogProgress.dismiss();
                if("TL01193N40267".equals(getSerialNumber())||"L203P85U01743".equals(getSerialNumber())){
                    Checkuser_packer("em00009",dialog_qr,dialog_item_stock_detail_basket);
                }
            }
        });

     /*   spinner_report.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_itemspinner = spinner_report.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    // open qr
    private void openQR(String data){
        Intent i = new Intent(CssdSterile.this, CssdQrUser.class);
        i.putExtra("data", data);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, Master.getResult(data));//14
    }

    private void GetReport(String DocNo) {
        String url = "";
        //DocNo="S1904-0002";
        url = getUrl.xUrl+"report/Report_checklist_sterile.php?DocNo="+DocNo;
        //Log.d("GetReport: ", url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void displayMode(){

        list_wash_detail.setVisibility(!(DISPLAY_MODE == 3) ? View.VISIBLE : View.GONE);
        list_sterile_detail.setVisibility(!(DISPLAY_MODE == 3) ? View.VISIBLE : View.GONE);
        grid_wash_detail.setVisibility(DISPLAY_MODE == 3 ? View.VISIBLE : View.GONE);
        grid_sterile_detail.setVisibility(DISPLAY_MODE == 3 ? View.VISIBLE : View.GONE);

        class DisplayMode extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(MODEL_IMPORT_WASH_DETAIL != null && MODEL_IMPORT_WASH_DETAIL.size() > 0){
                    Log.d("tog_displayWashDetail","displayMode");
                    displayWashDetail(getSterileProcessId());
                }

                if(MODEL_STERILE_DETAIL != null && MODEL_STERILE_DETAIL.size() > 0){
                    displaySterileDetail(getDocNo());
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                return "result";
            }

        }

        DisplayMode obj = new DisplayMode();
        obj.execute();
    }

    private void openSearchItemStock(){
        Intent i = new Intent(this,Barcode_itemstock.class);
        i.putExtra("xSel", "2");
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i,9999);
    }

    // =========================================================================================
    // 0n Click
    private void clickMachine(int Machine_Number) {
        STERILE_MACHINE_NUMBER_ACTIVE = Machine_Number;

        ////System.out.println("Click STERILE_MACHINE_NUMBER_ACTIVE = " + STERILE_MACHINE_NUMBER_ACTIVE);

        clearForm();

        if(Machine_Number == 1) {

            displayTabMachine(true, false, false, false, false);

            if(MACHINE_1.get(i).getDocNo() != null && !MACHINE_1.get(i).getDocNo().equals("-")){
                onDisplay( MACHINE_1.get(i).getDocNo() );
            }

            if(MACHINE_1.get(i).isActive()){
                alertMachineBusy( MACHINE_1.get(i).getMachineName() );
            }

        }else if(Machine_Number == 2) {

            displayTabMachine(false, true, false, false, false);

            if(MACHINE_2.get(i).getDocNo() != null && !MACHINE_2.get(i).getDocNo().equals("-")){
                onDisplay( MACHINE_2.get(i).getDocNo() );
            }

            if(MACHINE_2.get(i).isActive()){
                alertMachineBusy( MACHINE_2.get(i).getMachineName() );
            }
        }else if(Machine_Number == 3) {

            displayTabMachine(false, false, true, false, false);

            if(MACHINE_3.get(i).getDocNo() != null && !MACHINE_3.get(i).getDocNo().equals("-")){
                onDisplay( MACHINE_3.get(i).getDocNo() );
            }

            if(MACHINE_3.get(i).isActive()){
                alertMachineBusy( MACHINE_3.get(i).getMachineName() );
            }
        }else if(Machine_Number == 4) {

            displayTabMachine(false, false, false, true, false);

            if( MACHINE_4.get(i).getDocNo() != null && !MACHINE_4.get(i).getDocNo().equals("-")){
                onDisplay( MACHINE_4.get(i).getDocNo() );
            }

            if(MACHINE_4.get(i).isActive()){
                alertMachineBusy( MACHINE_4.get(i).getMachineName() );
            }
        }else if(Machine_Number == 5) {

            displayTabMachine(false, false, false, false, true);

            if(MACHINE_5.get(i).getDocNo() != null && !MACHINE_5.get(i).getDocNo().equals("-")){
                onDisplay( MACHINE_5.get(i).getDocNo() );
            }

            if(MACHINE_5.get(i).isActive()){
                alertMachineBusy( MACHINE_5.get(i).getMachineName() );
            }
        }

        //clearForm();

    }

    final String[] option = new String[]{"จบการทำงาน", "เริ่มใหม่", "หยุดการทำงาน", "ออก"};
    final String[] option_2 = new String[]{"จบการทำงาน", "เริ่มใหม่", "เริ่มทำงาน", "ออก"};
    final String[] option_admin = new String[]{"เริ่มใหม่", "หยุดการทำงาน", "ออก"};
    final String[] option_2_admin = new String[]{"เริ่มใหม่", "เริ่มทำงาน", "ออก"};
    final String[] option_3 = new String[]{"เริ่มทำงาน", "ออก"};


    private boolean isPause(final int Machine_Number){
        switch (Machine_Number){
            case 1 : return  MACHINE_1.get(i).getIsPause().equals("0");
            case 2 : return  MACHINE_2.get(i).getIsPause().equals("0");
            case 3 : return  MACHINE_3.get(i).getIsPause().equals("0");
            case 4 : return  MACHINE_4.get(i).getIsPause().equals("0");
            case 5 : return  MACHINE_5.get(i).getIsPause().equals("0");
            default: return false;
        }
    }

    private void longClickMachine(final String longdocno, final int Machine_Number) {
        clickMachine(Machine_Number);
        //STERILE_MACHINE_NUMBER_ACTIVE = Machine_Number;
        String DocNo = getDocNo(Machine_Number);
        // No Have DocNo.
        if(DocNo == null || DocNo.equals("-") || DocNo.equals("")) {
            return;
        }
        if(txt_finish.getText().equals("") && getMachineActive(Machine_Number).equals("0")){
            // Not Ever Run
            openDialog();
        }else{
            // Ever Run
            openDialog(longdocno,Machine_Number);
        }
    }

    private void openDialogTest(final int Machine_Number) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdSterile.this, android.R.layout.select_dialog_item, isPause(Machine_Number) ? option : option_2);
        AlertDialog.Builder builder = new AlertDialog.Builder(CssdSterile.this);
        builder.setIcon(R.drawable.pose_favicon_2x);
        builder.setTitle(Cons.TITLE);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
                    quitDialog.setTitle(Cons.TITLE);
                    quitDialog.setMessage(Cons.CONFIRM_STOP);
                    quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (Machine_Number) {
                                case 1:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_1.removeCallbacks(runnable_1);
                                        updateFinishMachine(1, MACHINE_1.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_1.get(i).getDocNo(), "s2", "1");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 2:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_2.removeCallbacks(runnable_2);
                                        updateFinishMachine(2, MACHINE_2.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_2.get(i).getDocNo(), "s2", "2");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 3:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        updateFinishMachine(3, MACHINE_3.get(i).getDocNo());
                                        handler_3.removeCallbacks(runnable_3);
                                        clearForm();
                                        //getQR(MACHINE_3.get(i).getDocNo(), "s2", "3");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 4:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_4.removeCallbacks(runnable_4);
                                        updateFinishMachine(4, MACHINE_4.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_4.get(i).getDocNo(), "s2", "4");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 5:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_5.removeCallbacks(runnable_5);
                                        updateFinishMachine(5, MACHINE_5.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_5.get(i).getDocNo(), "s2", "5");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                        }
                    });
                    quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    quitDialog.show();
                    // =============================================================================
                } else if (which == 1) {
                    String DocNo = getDocNo();
                    getQR(DocNo, "s1", "");
                    //startMachine(DocNo);
                } else if (which == 2) {
                    // =============================================================================
                    switch (Machine_Number) {
                        case 1:
                            if (MACHINE_1.get(i).getIsPause().equals("1")) {
                                updateRunMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                            } else {
                                handler_1.removeCallbacks(runnable_1);
                                updatePauseMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                                handler_1.removeCallbacks(runnable_1);
                            }
                            break;
                        case 2:

                            if (MACHINE_2.get(i).getIsPause().equals("1")) {
                                updateRunMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
                            } else {
                                handler_2.removeCallbacks(runnable_2);
                                updatePauseMachine(Machine_Number, MACHINE_2.get(i).getDocNo());

                                handler_2.removeCallbacks(runnable_2);
                            }

                            break;

                        case 3:

                            if (MACHINE_3.get(i).getIsPause().equals("1")) {

                                updateRunMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
                            } else {
                                // Stop handler
                                handler_3.removeCallbacks(runnable_3);

                                // Finish Update IsStatus
                                updatePauseMachine(Machine_Number, MACHINE_3.get(i).getDocNo());

                                handler_3.removeCallbacks(runnable_3);
                            }

                            break;

                        case 4:

                            if (MACHINE_4.get(i).getIsPause().equals("1")) {

                                updateRunMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
                            } else {
                                // Stop handler
                                handler_4.removeCallbacks(runnable_4);

                                // Finish Update IsStatus
                                updatePauseMachine(Machine_Number, MACHINE_4.get(i).getDocNo());

                                handler_4.removeCallbacks(runnable_4);
                            }

                            break;

                        case 5:

                            if (MACHINE_5.get(i).getIsPause().equals("1")) {

                                updateRunMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
                            } else {
                                // Stop handler
                                handler_5.removeCallbacks(runnable_5);

                                // Finish Update IsStatus
                                updatePauseMachine(Machine_Number, MACHINE_5.get(i).getDocNo());

                                handler_5.removeCallbacks(runnable_5);
                            }

                            break;

                    }
                    // =============================================================================
                } else {
                    // Exit
                    return;
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDialogProgram(final int Machine_Number) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdSterile.this, android.R.layout.select_dialog_item, isPause(Machine_Number) ? option : option_2);
        AlertDialog.Builder builder = new AlertDialog.Builder(CssdSterile.this);
        builder.setIcon(R.drawable.pose_favicon_2x);
        builder.setTitle(Cons.TITLE);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Log.d("JFHFKD",isBowiedick()+"");
                    Log.d("JFHFKD",Program+"");
                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
                    quitDialog.setTitle(Cons.TITLE);
                    quitDialog.setMessage(Cons.CONFIRM_STOP);
                    quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("JFHFKD",isBowiedick()+"");
                            Log.d("JFHFKD",Program+"");
                            switch (Machine_Number) {
                                case 1:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_1.removeCallbacks(runnable_1);
                                        updateFinishMachine(1, MACHINE_1.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_1.get(i).getDocNo(), "s2", "1");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 2:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_2.removeCallbacks(runnable_2);
                                        updateFinishMachine(2, MACHINE_2.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_2.get(i).getDocNo(), "s2", "2");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 3:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        updateFinishMachine(3, MACHINE_3.get(i).getDocNo());
                                        handler_3.removeCallbacks(runnable_3);
                                        clearForm();
                                        //getQR(MACHINE_3.get(i).getDocNo(), "s2", "3");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 4:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_4.removeCallbacks(runnable_4);
                                        updateFinishMachine(4, MACHINE_4.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_4.get(i).getDocNo(), "s2", "4");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 5:
                                    if (isBowiedick() == Program.equals("TEST")) {
                                        handler_5.removeCallbacks(runnable_5);
                                        updateFinishMachine(5, MACHINE_5.get(i).getDocNo());
                                        clearForm();
                                        //getQR(MACHINE_5.get(i).getDocNo(), "s2", "5");
                                    } else {
                                        Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                        }
                    });
                    quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    quitDialog.show();
                    // =============================================================================
                } else if (which == 1) {
                    String DocNo = getDocNo();
                    getQR(DocNo, "s1", "");
                    //startMachine(DocNo);
                } else if (which == 2) {
                    // =============================================================================
                    switch (Machine_Number) {
                        case 1:
                            if (MACHINE_1.get(i).getIsPause().equals("1")) {
                                updateRunMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                            } else {
                                handler_1.removeCallbacks(runnable_1);
                                updatePauseMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                                handler_1.removeCallbacks(runnable_1);
                            }
                            break;
                        case 2:

                            if (MACHINE_2.get(i).getIsPause().equals("1")) {
                                updateRunMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
                            } else {
                                handler_2.removeCallbacks(runnable_2);
                                updatePauseMachine(Machine_Number, MACHINE_2.get(i).getDocNo());

                                handler_2.removeCallbacks(runnable_2);
                            }

                            break;

                        case 3:

                            if (MACHINE_3.get(i).getIsPause().equals("1")) {

                                updateRunMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
                            } else {
                                // Stop handler
                                handler_3.removeCallbacks(runnable_3);

                                // Finish Update IsStatus
                                updatePauseMachine(Machine_Number, MACHINE_3.get(i).getDocNo());

                                handler_3.removeCallbacks(runnable_3);
                            }

                            break;

                        case 4:

                            if (MACHINE_4.get(i).getIsPause().equals("1")) {

                                updateRunMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
                            } else {
                                // Stop handler
                                handler_4.removeCallbacks(runnable_4);

                                // Finish Update IsStatus
                                updatePauseMachine(Machine_Number, MACHINE_4.get(i).getDocNo());

                                handler_4.removeCallbacks(runnable_4);
                            }

                            break;

                        case 5:

                            if (MACHINE_5.get(i).getIsPause().equals("1")) {

                                updateRunMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
                            } else {
                                // Stop handler
                                handler_5.removeCallbacks(runnable_5);

                                // Finish Update IsStatus
                                updatePauseMachine(Machine_Number, MACHINE_5.get(i).getDocNo());

                                handler_5.removeCallbacks(runnable_5);
                            }

                            break;

                    }
                    // =============================================================================
                } else {
                    // Exit
                    return;
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDialog(final String longdocno,final int Machine_Number) {
        ShowProgram(longdocno,Machine_Number);
    }

    private void openDialog() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdSterile.this, android.R.layout.select_dialog_item, option_3 );
        AlertDialog.Builder builder = new AlertDialog.Builder(CssdSterile.this);
        builder.setIcon(R.drawable.pose_favicon_2x);
        builder.setTitle(Cons.TITLE);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if(!isBowiedick()) {
                        if((MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() < 1)){
                            Toast.makeText(CssdSterile.this, "ไม่สามารถเริ่มการฆ่าเชื้อได้ เนื่องจากไม่มีรายการที่จะฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                        }else {
                            getQR(getDocNo(),"s1","");
                        }
                    }else {
                        Toast.makeText(CssdSterile.this, "ไม่สามารถเริ่มการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    return;
                }
                if (isTestBNT){
                    isTestMac = true;
                }else{
                    isTestMac = false;
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void alertMachineBusy(String MachineName){
        disableButtonAictive();
        Toast.makeText(CssdSterile.this, "เครื่อง " + MachineName + " กำลังทำงาน !!", Toast.LENGTH_SHORT).show();
    }


    // =========================================================================================
    // Form
    private void displaySterile(
            String DocNo,
            String DocDate,
            String SterileName,
            String S_time,
            String F_time,
            String SterileRoundNumber,
            String Usr_prepare,
            String Usr_approve,
            String Usr_sterile,
            String PrepareCode,
            String ApproveCode,
            String UserCode,
            String SterileProgramID,
            String print_balance,
            String print_all,
            String TestProgramID,
            String TestProgramName,
            String Usr_beforeapprove
    ) {

        // Display Sterile
        txt_doc_no.setText(DocNo);
        txt_doc_date.setText(DocDate);
        txt_sterile_program.setText(SterileName);
        txt_sterile_program.setContentDescription(SterileProgramID);
        txt_start.setText(S_time);
        txt_finish.setText(F_time);
        txt_round.setText(SterileRoundNumber);

        txt_usr_prepare.setText(Usr_prepare);
        txt_usr_sterile.setText(Usr_sterile);
        txt_usr_approve.setText(Usr_approve);
        txt_usr_beforeapprove.setText(Usr_beforeapprove);
        txt_test_program.setText(TestProgramName);

        ProgramTest = TestProgramName;

        txt_usr_prepare.setContentDescription(PrepareCode);
        txt_usr_sterile.setContentDescription(UserCode);
        txt_usr_approve.setContentDescription(ApproveCode);
        txt_test_program.setContentDescription(TestProgramID);

        txt_print_balance.setText(print_balance);

        if(print_all.equals("0")){
            btn_print.setBackgroundResource( !checkMachineActive() ? R.drawable.ic_print_grey : R.drawable.ic_print_blue);
            txt_print_balance.setTextColor( !checkMachineActive() ? getResources().getColor(gr) : getResources().getColor(b));
        }else{
            btn_print.setBackgroundResource( !checkMachineActive() ? R.drawable.ic_print_grey : ( print_balance.equals("0") ? R.drawable.ic_print_green : R.drawable.ic_print_red ) ) ;
            txt_print_balance.setTextColor( !checkMachineActive() ? getResources().getColor(gr) : ( print_balance.equals("0") ? getResources().getColor(b) : getResources().getColor(r) ) );
        }
    }


    private void clearForm(){
        btn_print.setBackgroundResource(R.drawable.ic_print_blue);
        txt_print_balance.setTextColor( getResources().getColor(b));
        btn_complete.setBackgroundResource(R.drawable.ic_sterile);
        imv_remove_all_sterile.setImageResource(R.drawable.bt_select_all);

        MODEL_STERILE_DETAIL = null;
        list_sterile_detail.setAdapter(null);
        grid_sterile_detail.setAdapter(null);

        txt_doc_no.setText("");
        //txt_doc_date.setText(DateTime.getDate());
        txt_doc_date.setText("");
        txt_sterile_program.setText("");
        txt_sterile_program.setContentDescription("");
        txt_round.setText("");
        txt_start.setText("");
        txt_finish.setText("");

        txt_usr_prepare.setContentDescription("");
        txt_usr_approve.setContentDescription("");
        txt_usr_sterile.setContentDescription("");
        txt_test_program.setContentDescription("");
        txt_usr_prepare.setText("");
        txt_usr_approve.setText("");
        txt_usr_sterile.setText("");
        txt_test_program.setText("");
        txt_print_balance.setText("0");
        txt_usr_beforeapprove.setText("");
    }

    private void disableButtonAictive(){
        btn_print.setBackgroundResource(R.drawable.ic_print_grey);
        txt_print_balance.setTextColor( getResources().getColor(gr) );
        btn_complete.setBackgroundResource(R.drawable.ic_sterile_disable);
        imv_remove_all_sterile.setImageResource(R.drawable.bt_select_all_disable);
    }

    private void defaultTabMachine(){

        imv_mc_5.bringToFront();
        txt_mc_5.bringToFront();
        imv_mc_4.bringToFront();
        txt_mc_4.bringToFront();
        imv_mc_3.bringToFront();
        txt_mc_3.bringToFront();
        imv_mc_2.bringToFront();
        txt_mc_2.bringToFront();
        imv_mc_1.bringToFront();
        txt_mc_1.bringToFront();

        txt_mn_1.bringToFront();
        txt_mn_2.bringToFront();
        txt_mn_3.bringToFront();
        txt_mn_4.bringToFront();
        txt_mn_5.bringToFront();



        main.bringToFront();


        imv_mc_1.setY(TAB_NORMAL);
        imv_mc_2.setY(TAB_NORMAL);
        imv_mc_3.setY(TAB_NORMAL);
        imv_mc_4.setY(TAB_NORMAL);
        imv_mc_5.setY(TAB_NORMAL);

        txt_mc_1.setY(TXT_NORMAL);
        txt_mc_2.setY(TXT_NORMAL);
        txt_mc_3.setY(TXT_NORMAL);
        txt_mc_4.setY(TXT_NORMAL);
        txt_mc_5.setY(TXT_NORMAL);

        txt_mn_1.setY(getY(MACHINE_1));
        txt_mn_2.setY(getY(MACHINE_2));
        txt_mn_3.setY(getY(MACHINE_3));
        txt_mn_4.setY(getY(MACHINE_4));
        txt_mn_5.setY(getY(MACHINE_5));

        txt_mn_1.setTextColor(getResources().getColor(g));
        txt_mn_2.setTextColor(getResources().getColor(g));
        txt_mn_3.setTextColor(getResources().getColor(g));
        txt_mn_4.setTextColor(getResources().getColor(g));
        txt_mn_5.setTextColor(getResources().getColor(g));


        imv_print.bringToFront();
        imv_AddItem.bringToFront();
        imv_import_all_wash.bringToFront();
        imv_remove_all_sterile.bringToFront();
        txt_print_balance.bringToFront();
        imageBack.bringToFront();
        txt_caption.bringToFront();
        group_choices.bringToFront();
        switch_mode.bringToFront();

    }

    private int getY(List<ModelSterileMachine> MACHINE){
        return MACHINE != null && MACHINE.size() > 0 && MACHINE.get(i).isActive() ? TXT_NAME_RUN_NORMAL : TXT_NAME_NORMAL;
    }

    private void displayTabMachine(boolean m1, boolean m2, boolean m3, boolean m4, boolean m5){

        defaultTabMachine();

        if(m1 && MACHINE_1 != null && MACHINE_1.size() > 0) {

            imv_mc_1.bringToFront();
            imv_mc_1.setY(TAB_ACTIVE);

            txt_mc_1.bringToFront();
            txt_mc_1.setY(TXT_ACTIVE);

            txt_mn_1.bringToFront();
            txt_mn_1.setY(MACHINE_1.get(i).isActive() ? TXT_NAME_RUN_ACTIVE : TXT_NAME_ACTIVE);
            txt_mn_1.setTextColor( getResources().getColor( MACHINE_1.get(i).isActive() ? r : b ) );

            main.setBackgroundResource(MACHINE_1.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
            imv_AddItem.bringToFront();
        }

        if(m2 && MACHINE_2 != null && MACHINE_2.size() > 0) {

            imv_mc_2.bringToFront();
            imv_mc_2.setY(TAB_ACTIVE);

            txt_mc_2.bringToFront();
            txt_mc_2.setY(TXT_ACTIVE);

            txt_mn_2.bringToFront();
            txt_mn_2.setY(MACHINE_2.get(i).isActive() ? TXT_NAME_RUN_ACTIVE : TXT_NAME_ACTIVE);
            txt_mn_2.setTextColor( getResources().getColor( MACHINE_2.get(i).isActive() ? r : b ) );


            main.setBackgroundResource(MACHINE_2.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
            imv_AddItem.bringToFront();
        }

        if(m3 && MACHINE_3 != null && MACHINE_3.size() > 0) {
            imv_mc_3.bringToFront();
            imv_mc_3.setY(TAB_ACTIVE);

            txt_mc_3.bringToFront();
            txt_mc_3.setY(TXT_ACTIVE);

            txt_mn_3.bringToFront();
            txt_mn_3.setY(MACHINE_3.get(i).isActive() ? TXT_NAME_RUN_ACTIVE : TXT_NAME_ACTIVE);
            txt_mn_3.setTextColor( getResources().getColor( MACHINE_3.get(i).isActive() ? r : b ) );

            main.setBackgroundResource(MACHINE_3.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
            imv_AddItem.bringToFront();
        }

        if(m4 && MACHINE_4 != null && MACHINE_4.size() > 0) {

            imv_mc_4.bringToFront();
            imv_mc_4.setY(TAB_ACTIVE);

            txt_mc_4.bringToFront();
            txt_mc_4.setY(TXT_ACTIVE);

            txt_mn_4.bringToFront();
            txt_mn_4.setY(MACHINE_4.get(i).isActive() ? TXT_NAME_RUN_ACTIVE : TXT_NAME_ACTIVE);
            txt_mn_4.setTextColor( getResources().getColor( MACHINE_4.get(i).isActive() ? r : b ) );

            main.setBackgroundResource(MACHINE_4.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
            imv_AddItem.bringToFront();
        }

        if(m5 && MACHINE_5 != null && MACHINE_5.size() > 0) {
            imv_mc_5.bringToFront();
            imv_mc_5.setY(TAB_ACTIVE);

            txt_mc_5.bringToFront();
            txt_mc_5.setY(TXT_ACTIVE);

            txt_mn_5.bringToFront();
            txt_mn_5.setY(MACHINE_5.get(i).isActive() ? TXT_NAME_RUN_ACTIVE : TXT_NAME_ACTIVE);
            txt_mn_5.setTextColor( getResources().getColor( MACHINE_5.get(i).isActive() ? r : b ) );

            main.setBackgroundResource(MACHINE_5.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
            imv_AddItem.bringToFront();
        }

        try {
            // Background
            imv_mc_1.setImageResource( ( MACHINE_1 != null && MACHINE_1.get(i).isActive() ) ? R.drawable.ic_sterile_red : (m1 ? R.drawable.ic_sterile_blue : R.drawable.ic_sterile_green));
            imv_mc_2.setImageResource( ( MACHINE_2 != null && MACHINE_2.get(i).isActive() ) ? R.drawable.ic_sterile_red : (m2 ? R.drawable.ic_sterile_blue : R.drawable.ic_sterile_green));
            imv_mc_3.setImageResource( ( MACHINE_3 != null && MACHINE_3.get(i).isActive() ) ? R.drawable.ic_sterile_red : (m3 ? R.drawable.ic_sterile_blue : R.drawable.ic_sterile_green));
            imv_mc_4.setImageResource( ( MACHINE_4 != null && MACHINE_4.get(i).isActive() ) ? R.drawable.ic_sterile_red : (m4 ? R.drawable.ic_sterile_blue : R.drawable.ic_sterile_green));
            imv_mc_5.setImageResource( ( MACHINE_5 != null && MACHINE_5.get(i).isActive() ) ? R.drawable.ic_sterile_red : (m5 ? R.drawable.ic_sterile_blue : R.drawable.ic_sterile_green));

            // Button
            boolean IsActive = true;

            if(m1) {
                IsActive = MACHINE_1 != null && MACHINE_1.get(i).isActive();
            }else if(m2) {
                IsActive = MACHINE_2 != null && MACHINE_2.get(i).isActive();
            }else if(m3) {
                IsActive = MACHINE_3 != null && MACHINE_3.get(i).isActive();
            }else if(m4) {
                IsActive = MACHINE_4 != null && MACHINE_4.get(i).isActive();
            }else if(m5) {
                IsActive = MACHINE_5 != null && MACHINE_5.get(i).isActive();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void clearMachine(int MachineNo){
        switch(MachineNo) {
            case 1 : imv_mc_1.setImageResource(R.drawable.ic_sterile_green); txt_mc_1.setText(""); /*txt_mn_1.setText("");*/txt_mn_1.setTextColor(getResources().getColor(g));break;
            case 2 : imv_mc_2.setImageResource(R.drawable.ic_sterile_green); txt_mc_2.setText(""); /*txt_mn_2.setText("");*/txt_mn_2.setTextColor(getResources().getColor(g));break;
            case 3 : imv_mc_3.setImageResource(R.drawable.ic_sterile_green); txt_mc_3.setText(""); /*txt_mn_3.setText("");*/txt_mn_3.setTextColor(getResources().getColor(g));break;
            case 4 : imv_mc_4.setImageResource(R.drawable.ic_sterile_green); txt_mc_4.setText(""); /*txt_mn_4.setText("");*/txt_mn_4.setTextColor(getResources().getColor(g));break;
            case 5 : imv_mc_5.setImageResource(R.drawable.ic_sterile_green); txt_mc_5.setText(""); /*txt_mn_5.setText("");*/txt_mn_5.setTextColor(getResources().getColor(g));break;
            default: return;
        }
        STERILE_MACHINE_NUMBER_ACTIVE = 0;
        // Set Default
        defaultTabMachine();

        // Default Background
        main.setBackgroundResource(R.drawable.bg_sterile_blue);
    }

    private String getMachineActive(int MachineNo){
        switch(MachineNo) {
            case 1 : return MACHINE_1.get(i).getIsActive();
            case 2 : return MACHINE_2.get(i).getIsActive();
            case 3 : return MACHINE_3.get(i).getIsActive();
            case 4 : return MACHINE_4.get(i).getIsActive();
            case 5 : return MACHINE_5.get(i).getIsActive();
            default: return null;
        }
    }

    private String getMachineId(int MachineNo){
        switch(MachineNo) {
            case 1 : return MACHINE_1.get(i).getID();
            case 2 : return MACHINE_2.get(i).getID();
            case 3 : return MACHINE_3.get(i).getID();
            case 4 : return MACHINE_4.get(i).getID();
            case 5 : return MACHINE_5.get(i).getID();
            default: return null;
        }
    }

    private String getMachineName(int MachineNo){
        switch(MachineNo) {
            case 1 : return MACHINE_1.get(i).getMachineName();
            case 2 : return MACHINE_2.get(i).getMachineName();
            case 3 : return MACHINE_3.get(i).getMachineName();
            case 4 : return MACHINE_4.get(i).getMachineName();
            case 5 : return MACHINE_5.get(i).getMachineName();
            default: return null;
        }
    }

    private String getDocNo(int MachineNo){

        switch(MachineNo) {
            case 1 : return MACHINE_1.get(i).getDocNo();
            case 2 : return MACHINE_2.get(i).getDocNo();
            case 3 : return MACHINE_3.get(i).getDocNo();
            case 4 : return MACHINE_4.get(i).getDocNo();
            case 5 : return MACHINE_5.get(i).getDocNo();
            default: return null;
        }
    }

    private String getDocNo(){

        if(STERILE_MACHINE_NUMBER_ACTIVE == 1){
            return MACHINE_1.get(i).getDocNo();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 2){
            return MACHINE_2.get(i).getDocNo();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 3){
            return MACHINE_3.get(i).getDocNo();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 4){
            return MACHINE_4.get(i).getDocNo();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 5){
            return MACHINE_5.get(i).getDocNo();
        }else{
            return null;
        }

    }



    private void setMachine(String DocNo){

        if(STERILE_MACHINE_NUMBER_ACTIVE == 1){
            MACHINE_1.get(i).setDocNo(DocNo);
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 2){
            MACHINE_2.get(i).setDocNo(DocNo);
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 3){
            MACHINE_3.get(i).setDocNo(DocNo);
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 4){
            MACHINE_4.get(i).setDocNo(DocNo);
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 5){
            MACHINE_5.get(i).setDocNo(DocNo);
        }

    }

    private boolean checkMachineActive(){
        if(STERILE_MACHINE_NUMBER_ACTIVE == 1){
            return !MACHINE_1.get(i).isActive();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 2){
            return !MACHINE_2.get(i).isActive();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 3){
            return !MACHINE_3.get(i).isActive();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 4){
            return !MACHINE_4.get(i).isActive();
        }else if(STERILE_MACHINE_NUMBER_ACTIVE == 5){
            return !MACHINE_5.get(i).isActive();
        }else{
            return false;
        }
    }


    // =========================================================================================
    // Event

    private void addSterileDetail(String DocNo){

        if(DocNo == null){
            return ;
        }

        try {
            String LIST_ID = null;
            String DATA = "";

            // =========================================================================================

            List<ModelImportWashDetail> DATA_MODEL = MODEL_IMPORT_WASH_DETAIL;

            Log.d("tog_isprint","size = "+DATA_MODEL.size());
            Iterator li = DATA_MODEL.iterator();

            while(li.hasNext()) {
                try {
                    ModelImportWashDetail m = (ModelImportWashDetail) li.next();

                    LIST_ID = m.getI_code();

                    //if (m.isCheck()) {
                    DATA += LIST_ID + "@";
                    //}

                }catch(Exception e){
                    continue;
                }
            }

            addSterileDetail(DocNo, DATA, null,"0",null);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void ClearTime(){
        Time = null;
    }

    private void updateStartMachine() {
        if (isBowiedick()) {
            AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
            quitDialog.setTitle(Cons.TITLE);

            Text1 = "คุณได้บันทึกการทดสอบ " + "( " + ProgramTest + " )" + " นี้แล้วเริ่มทำงานเมื่อเวลา " + Time + " ต้องการให้เครื่องทำงานหรือไม่ ?";
            Text2 = "ยืนยันการทำงาน !!";

            if (!(Time == null)){
                quitDialog.setMessage(Text1);
                ClearTime();
            }else {
                quitDialog.setMessage(Text2);
                ClearTime();
            }

            quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String DocNo = getDocNo();

                    // =============================================================================
                    if (DocNo == null || DocNo.equals("-") || DocNo.equals("")) {



                        if (isBowiedick() && (MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() < 1)) {

                            // Check User
                            if (txt_usr_prepare.getContentDescription() == null || txt_usr_prepare.getContentDescription().equals("") ||
                                    //txt_usr_sterile.getContentDescription() == null || txt_usr_sterile.getContentDescription().equals("")||
                                    txt_usr_approve.getContentDescription() == null || txt_usr_approve.getContentDescription().equals("")) {
                                Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือก ผู้เตรียม ผู้ตรวจ !!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } else {
                            Toast.makeText(CssdSterile.this, "ไม่พบเอกสารการฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                    if (OccupancyRate_Con == true) {
//                        if (!OccupancyRate_Sterile.getText().toString().equals("")) {
//                            if (isBowiedick()) {
//                                createSterile(getProcessId());
//                            }
//                        } else {
//                            Toast.makeText(CssdSterile.this, "กรุณากรอกอัตราการเต็มตู้ต่อรอบ", Toast.LENGTH_SHORT).show();
//                        }
                        createSterile(getProcessId());
                    }else {
                        if (isBowiedick()) {
                            createSterile(getProcessId());
                        }
                    }

                }
            });

            quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    txt_usr_prepare.setText("");
                    txt_usr_approve.setText("");
                    txt_usr_beforeapprove.setText("");
                    txt_test_program.setText("");
                    Time = null;
                }
            });

            quitDialog.show();
        }else{
            if (checkMachineActive()) {

                AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setMessage(Cons.CONFIRM_RUNNING);

                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String DocNo = getDocNo();

                        //System.out.println("DocNo = " + DocNo);

                        // =============================================================================

                        if (DocNo == null || DocNo.equals("-") || DocNo.equals("")) {

                            if (isBowiedick() && (MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() < 1)) {

                                // Check User
                                if (txt_usr_prepare.getContentDescription() == null || txt_usr_prepare.getContentDescription().equals("") ||
                                        //txt_usr_sterile.getContentDescription() == null || txt_usr_sterile.getContentDescription().equals("")||
                                        txt_usr_approve.getContentDescription() == null || txt_usr_approve.getContentDescription().equals("")

                                ) {
                                    Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือก ผู้เตรียม ผู้ตรวจ !!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // Create Test Machine
                                createSterile(getProcessId());

                            } else {
                                Toast.makeText(CssdSterile.this, "ไม่พบเอกสารการฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        // =============================================================================

                        //System.out.println("Machine Active = " + STERILE_MACHINE_NUMBER_ACTIVE );
                        //System.out.println("Start DocNo = " + getDocNo());

                        try {

                            //System.out.println("isBowiedick() = " + !isBowiedick());
                            if (OccupancyRate_Con == true) {
//                                if (!OccupancyRate_Sterile.getText().toString().equals("")) {
//                                    if (!isBowiedick()) {
//                                        if ((MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() < 1)) {
//                                            Toast.makeText(CssdSterile.this, "ไม่สามารถเริ่มการฆ่าเชื้อได้ เนื่องจากไม่มีรายการที่จะฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            getQR(getDocNo(), "s1", "");
//                                            //startMachine(getDocNo());
//                                        }
//                                    }
//                                } else {
//                                    Toast.makeText(CssdSterile.this, "กรุณากรอกอัตราการเต็มตู้ต่อรอบ", Toast.LENGTH_SHORT).show();
//                                }

                                if ((MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() < 1)) {
                                    Toast.makeText(CssdSterile.this, "ไม่สามารถเริ่มการฆ่าเชื้อได้ เนื่องจากไม่มีรายการที่จะฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                                } else {
                                    getQR(getDocNo(), "s1", "");
                                    //startMachine(getDocNo());
                                }
                            }else {
                                if ((MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() < 1)) {
                                    Toast.makeText(CssdSterile.this, "ไม่สามารถเริ่มการฆ่าเชื้อได้ เนื่องจากไม่มีรายการที่จะฆ่าเชื้อ !!", Toast.LENGTH_SHORT).show();
                                } else {
                                    getQR(getDocNo(), "s1", "");
                                    //startMachine(getDocNo());
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                quitDialog.show();

            } else {
                Toast.makeText(CssdSterile.this, "เครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void onRemoveSterileDetail(){
        if(checkMachineActive()) {

            AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
            quitDialog.setTitle(Cons.TITLE);
            quitDialog.setMessage(Cons.CONFIRM_REMOVE);

            quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onRemoveAllSterileDetail();
                }
            });

            quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            quitDialog.show();

        }else{
            Toast.makeText(CssdSterile.this, "ไม่สามารถลบรายการได้!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeSterileDetail(
            String LIST_IMPORT_ID,
            String LIST_ID,
            String LIST_ITEM_STOCK_ID
    ){
        if(checkMachineActive()) {
            try {

                String DATA = LIST_ID + "@" + LIST_IMPORT_ID + "@" + LIST_ITEM_STOCK_ID + "@";

                //////System.out.println("DATA = " + DATA);

                // Remove All Sterile Detail
                removeSterileDetail(DATA);

            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(CssdSterile.this, "ไม่สามารถลบรายการได้!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRemoveAllSterileDetail(){
        try {
            List<ModelSterileDetail> DATA_MODEL = MODEL_STERILE_DETAIL;

            Iterator li = DATA_MODEL.iterator();

            String LIST_IMPORT_ID = null;
            String LIST_ID = null;
            String LIST_ITEM_STOCK_ID = null;

            String DATA = "";

            while(li.hasNext()) {
                try {
                    ModelSterileDetail m = (ModelSterileDetail) li.next();

                    LIST_ID = m.getID();
                    LIST_IMPORT_ID = m.getImportID();
                    LIST_ITEM_STOCK_ID = m.getItemStockID();


                    DATA += LIST_ID + "@" + LIST_IMPORT_ID + "@" + LIST_ITEM_STOCK_ID + "@";


                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            //////System.out.println("DATA = " + DATA);

            // Remove All Sterile Detail
            removeSterileDetail(DATA);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void importWashDetail(String Id, String SterileProgramID, String PackingMatID,String gQty,String usageCode){
        onImport(Id, SterileProgramID, PackingMatID, gQty,usageCode);
    }

    Runnable runnable2;
    public void importWashDetail(final List<ModelImportWashDetail> DETAIL_SUB){

        String DocNo = getDocNo();
        if(!chk_mac()){
            return;
        }

        if(!checkSterileDetailProgram()) {
            Toast.makeText(CssdSterile.this, "ท่านเลือกโปรแกรมฆ่าเชื้อไม่ตรงกัน โปรดเลือกรายการฆ่าเชื้อใหม่อีกครั้ง !!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("ttest_create_loop","DocNo = "+DocNo);
        if ((DocNo == null) || DocNo.equals("-")) {
            createSterile_loop = false;
            Log.d("ttest_create_loop","createSterile_loop 1 = "+createSterile_loop);
            ModelImportWashDetail x = DETAIL_SUB.get(0);

            onImport(x.getI_code(),
                    x.getI_program_id(),
                    x.getPackingMatID(),
                    x.getI_qty(),
                    x.getI_UsageCode()
            );

            final List<ModelImportWashDetail> DETAIL_SUB2 = DETAIL_SUB.subList(1,DETAIL_SUB.size());

            final Handler handler = new Handler();
            runnable2 = new Runnable() {
                int iiii=0;
                @Override
                public void run() {
                    Log.d("ttest_create_loop","createSterile_loop 2 = "+createSterile_loop);
                    if (!createSterile_loop) {
                        iiii+=200;
                        Log.d("ttest_create_loop","runnable2 = "+iiii);
                        handler.postDelayed(runnable2, 200);
                    }else{
                        importWashDetail(DETAIL_SUB2);
                    }
                }
            };
            handler.postDelayed(runnable2, 200);
        }else{
            for(int i=0;i<DETAIL_SUB.size();i++){

                Log.d("ttest_create_loop","runnable2 = "+i);

                ModelImportWashDetail x = DETAIL_SUB.get(i);

                onImport(x.getI_code(),
                        x.getI_program_id(),
                        x.getPackingMatID(),
                        x.getI_qty(),
                        x.getI_UsageCode()
                );
            }
        }
    }

    public void removeWashDetail(final String itemcode){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdSterile.this);
        quitDialog.setTitle(Cons.TITLE);
        quitDialog.setMessage(Cons.CONFIRM_DELETE);

        quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onRemoveWashDetail(itemcode);
            }
        });

        quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        quitDialog.show();
    }

    public void openDialogWashManagement(final String itemcode,final String itemname,final String p_usagecode) {
        final String[] option_wash_detail = new String[]{"จัดการรูปแบบการห่อ", "พิมพ์ย้อนหลัง","ออก"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdSterile.this, android.R.layout.select_dialog_item, option_wash_detail );
        AlertDialog.Builder builder = new AlertDialog.Builder(CssdSterile.this);
        builder.setIcon(R.drawable.pose_favicon_2x);
        builder.setTitle(Cons.TITLE);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // ListView Clicked item value
//                if (which == 0) {
//                    Intent i = new Intent(CssdSterile.this, CssdWashDetailManagement.class);
//                    i.putExtra("itemcode", itemcode);
//                    i.putExtra("itemname", itemname);
//                    i.putExtra("B_ID", B_ID);
//                    startActivityForResult(i, Master.CssdWashDetailManagement);
//                }else if (which == 1) {
//                    removeWashDetail(itemcode);
//                }else {
//                    return;
//                }

                if (which == 0) {
                    wash_detail_management_pack(itemcode,itemname,p_usagecode);
                }else if (which == 1) {
                    checkPacker(itemcode);
                }else {
                    return;
                }

            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onImportNewItemStock(String STERILE_PROGRAM_ID, String DATA, boolean IsNew){

        if(!chk_mac()){
            return;
        }

        String DocNo = getDocNo();
        if (DocNo != null && !DocNo.equals("-")) {
            if(MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() == 0){
                // Update Sterile Program
                updateSterile(DocNo, STERILE_PROGRAM_ID);
            }
            if(IsNew) {
                addSterileDetailAdvance(DocNo, DATA, "cssd_add_sterile_detail_by_import_new_item_stock.php");
            }else{
                addSterileDetailAdvance(DocNo, DATA, "cssd_add_sterile_detail_by_import_no_wash.php");
            }
        } else {
            createSterile(STERILE_PROGRAM_ID, DATA, IsNew);
        }
    }
    // 1 : N
    private void onImport(){

        if(!chk_mac()){
            return;
        }

        // Check Wash Program
        if (checkSelectSterileProgram()) {

            // Check Sterile Program
            if(checkSterileDetailProgram()) {

                // Create Document
                String DocNo = getDocNo();

                //////System.out.println("DocNo = " + DocNo );

                if (DocNo != null && !DocNo.equals("-")) {

                    if(MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() == 0){
                        // Update Sterile Program
                        updateSterile(DocNo, STERILE_PROGRAM_ID);
                    }

                    addSterileDetail(DocNo);
                } else {
                    createSterile(STERILE_PROGRAM_ID, null, false);
                }
            }else{
                Toast.makeText(CssdSterile.this, "โปรแกรมฆ่าเชื้อไม่ตรงกัน !!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CssdSterile.this, "โปรแกรมฆ่าเชื้อมีมากกว่า 1 โปรแกรม!!", Toast.LENGTH_SHORT).show();
        }
    }

    // 1 : 1
    private boolean onImport(String Id, String SterileProgramID, String PackingMatID, String gQty,String usageCode){

        Log.d("tog_SterileProgramID","SterileProgramID = "+ SterileProgramID );

        if(!chk_mac()){
            return false;
        }

        // Check Sterile
        if(checkSterileDetailProgram()) {
            // Create Document
            String DocNo = getDocNo();

            if (DocNo != null && !DocNo.equals("-")) {

                if(MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() == 0){
                    // Update Sterile Program

                    //////System.out.println("++++ " + DocNo + " ++++ " + SterileProgramID);

                    updateSterile(DocNo, SterileProgramID);
                }

                addSterileDetail(DocNo, Id, PackingMatID,gQty,usageCode);

            } else {
                createSterile(Id, SterileProgramID, PackingMatID,gQty,usageCode);
            }
        }else{
            Toast.makeText(CssdSterile.this, "ท่านเลือกโปรแกรมฆ่าเชื้อไม่ตรงกัน โปรดเลือกรายการฆ่าเชื้อใหม่อีกครั้ง !!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkSelectSterileProgram(){

        try {
            List<ModelImportWashDetail> DATA_MODEL = MODEL_IMPORT_WASH_DETAIL;

            Iterator li = DATA_MODEL.iterator();

            String program_id_tmp = null;
            STERILE_PROGRAM_ID = null;
            STERILE_PROGRAM_NAME = null;

            while(li.hasNext()) {
                try {
                    ModelImportWashDetail m = (ModelImportWashDetail) li.next();


                    STERILE_PROGRAM_ID = m.getI_program_id();
                    STERILE_PROGRAM_NAME = m.getI_program();

                    if(program_id_tmp != null && !program_id_tmp.equals(STERILE_PROGRAM_ID) ){
                        return false;
                    }

                    program_id_tmp = STERILE_PROGRAM_ID;

                    //////System.out.println("STERILE_PROGRAM_ID = " + STERILE_PROGRAM_ID);

                }catch(Exception e){
                    e.printStackTrace();
                    STERILE_PROGRAM_ID = null;
                    STERILE_PROGRAM_NAME = null;
                    return false;
                }
            }

            return true;

        }catch(Exception e){
            e.printStackTrace();
            STERILE_PROGRAM_ID = null;
            STERILE_PROGRAM_NAME = null;
            return false;
        }
    }

    private boolean checkSterileDetailProgram(){
        String SterileProgramID = txt_sterile_program.getContentDescription().toString();

        //////System.out.println("MODEL_STERILE_DETAIL = " + MODEL_STERILE_DETAIL.size());

        // No Sterile Program
        if(SterileProgramID.equals("")){
            return true;
        }

        // No sterile detail have change sterile program
        if(MODEL_STERILE_DETAIL == null || MODEL_STERILE_DETAIL.size() == 0){
            return true;
        }

        //////System.out.println("MODEL_STERILE_DETAIL.size() == " + MODEL_STERILE_DETAIL.size());

        try {
            List<ModelImportWashDetail> DATA_MODEL = MODEL_IMPORT_WASH_DETAIL;

            Iterator li = DATA_MODEL.iterator();

            while(li.hasNext()) {
                try {
                    ModelImportWashDetail m = (ModelImportWashDetail) li.next();

                    //////System.out.println(SterileProgramID + "" + m.getI_program_id());

                    if(!SterileProgramID.equals(m.getI_program_id()) ){
                        return false;
                    }


                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }
            }

            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Dropdown list
    private void openDropDown(String data, String filter){
        Intent i = new Intent(CssdSterile.this, MasterDropdown.class);
        i.putExtra("data", data);
        i.putExtra("filter", filter);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, Master.getResult(data));
    }

    private void openDropDown1(String data, String filter){
        Intent i = new Intent(CssdSterile.this, MasterDropdown1.class);
        i.putExtra("data", data);
        i.putExtra("filter", filter);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, 28);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null)
            return;

        try {

            Log.d("tog_MD","resultCode --"+resultCode);
            //////System.out.println( "resultCode = " + requestCode + "resultCode = " + resultCode) ;

            String RETURN_DATA = data.getStringExtra("RETURN_DATA");
            String RETURN_VALUE = data.getStringExtra("RETURN_VALUE");

            if (resultCode == Master.user_prepare) {
                txt_usr_prepare.setText(RETURN_DATA);
                txt_usr_prepare.setContentDescription(RETURN_VALUE);


                if(!isBowiedick()) {
                    updateSterile(Master.user_prepare, RETURN_VALUE, getDocNo());
                    if (!txt_doc_no.getText().toString().equals("")) {
                        displaySterileDetail(DocNo);
                    }else {

                    }
                    Log.d("BANK",txt_doc_no.getText().toString());
                }

            }
            else if (resultCode == Master.user_aprrove) {
                txt_usr_approve.setText(RETURN_DATA);
                txt_usr_approve.setContentDescription(RETURN_VALUE);


                if(!isBowiedick()) {
                    updateSterile(Master.user_aprrove, RETURN_VALUE, getDocNo());
                    if (!txt_doc_no.getText().toString().equals("")) {
                        displaySterileDetail(DocNo);
                    }else {

                    }
                    Log.d("BANK",txt_doc_no.getText().toString());
                }

            }
            else if (resultCode == Master.user_sterile) {
                txt_usr_sterile.setText(RETURN_DATA);
                txt_usr_sterile.setContentDescription(RETURN_VALUE);


                if(!isBowiedick()) {
                    updateSterile(Master.user_sterile, RETURN_VALUE, getDocNo());
                    //displaySterileDetail(DocNo);
                }

            }
            else if (resultCode == Master.sterile_test_program) {
                txt_test_program.setText(RETURN_DATA);
                txt_test_program.setContentDescription(RETURN_VALUE);

                ProgramTest = RETURN_DATA;
                TimeTest();

                if(!isBowiedick()) {
                    updateSterile(Master.sterile_test_program, RETURN_VALUE, getDocNo());
                }

            }
            else if (resultCode == Master.CssdWashDetailManagement) {

                displayWashDetail(getSterileProcessId());

            }
            else if (resultCode == Master.CssdSterileDraft) {

                if(STERILE_MACHINE_NUMBER_ACTIVE > 0 && checkMachineActive()) {
                    updateSterileMachineImportDocNo(STERILE_MACHINE_NUMBER_ACTIVE, getMachineId(STERILE_MACHINE_NUMBER_ACTIVE), RETURN_VALUE);
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถนำเอกสารเข้าเครื่องได้ !!" , Toast.LENGTH_SHORT).show();
                }

            }
            else if (resultCode == Master.CssdNewItemStock) {
                onImportNewItemStock(RETURN_DATA, RETURN_VALUE, true);

            }
            else if(resultCode == Master.CssdWashDetailNoWash){
                onImportNewItemStock(RETURN_DATA, RETURN_VALUE, false);

            }
            else if (resultCode == 9999 ){
                String RETURN_Dep = data.getStringExtra("RETURN_Dep");
                String RETURN_Id = data.getStringExtra("RETURN_Id");
                String RETURN_Qty = data.getStringExtra("RETURN_Qty");
                String RETURN_ReSterile = data.getStringExtra("RETURN_ReSterile");
                String RETURN_ReSterileType = data.getStringExtra("RETURN_ReSterileType");
                String RETURN_IsWithdraw = data.getStringExtra("RETURN_IsWithdraw");

                createWash( RETURN_Dep,RETURN_Id,RETURN_Qty,RETURN_ReSterile,RETURN_ReSterileType,RETURN_IsWithdraw );

            }
            else if (resultCode == 501 ){
                String xData = data.getStringExtra("RETURN_DATA");
                updatePrintStatus(xData);

            }
            else if (resultCode == 1035 ){

                String xData = data.getStringExtra("RETURN_DATA");
                String xsel= data.getStringExtra("RETURN_xsel");
                String xDocNo= data.getStringExtra("RETURN_DocNo");
                String MacNo= data.getStringExtra("RETURN_MacNo");

                if(xData.equals("true")){
                    if(xsel.equals("s1")&&!MacNo.equals("bowiedick")){
                        Toast.makeText(this, "บันทึก", Toast.LENGTH_SHORT).show();
                        startMachine(xDocNo);
                    }else if(!MacNo.equals("")){
                        switch (MacNo) {
                            case "1":
                                updateFinishMachine(1, MACHINE_1.get(i).getDocNo());
                                handler_1.removeCallbacks(runnable_1);
                                clearForm();
                                break;
                            case "2":
                                updateFinishMachine(2, MACHINE_2.get(i).getDocNo());
                                handler_2.removeCallbacks(runnable_2);
                                clearForm();
                                break;
                            case "3":
                                updateFinishMachine(3, MACHINE_3.get(i).getDocNo());
                                handler_3.removeCallbacks(runnable_3);
                                clearForm();
                                break;
                            case "4":
                                updateFinishMachine(4, MACHINE_4.get(i).getDocNo());
                                handler_4.removeCallbacks(runnable_4);
                                clearForm();
                                break;
                            case "5":
                                updateFinishMachine(5, MACHINE_5.get(i).getDocNo());
                                handler_5.removeCallbacks(runnable_5);
                                clearForm();
                                break;
                            case "bowiedick":
                                createSterile(getProcessId());
                                break;
                        }
                    }else{
                    }
                }
                else{
                    Toast.makeText(this, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                }
            }
            else if (resultCode == Master.packingmat) {

                txt_packingmat.setText(RETURN_DATA);
                txt_packingmat.setContentDescription(RETURN_VALUE);
//                    DAY = Integer.valueOf( RETURN_DATA.substring(RETURN_DATA.lastIndexOf("[") + 1, RETURN_DATA.length() - 1)).intValue();
                Log.d("tog_MD",RETURN_DATA+"---"+RETURN_VALUE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createWash(String xDep,String xId,String xQty,String xReSterile,String xReSterileType,String RETURN_IsWithdraw){
        String p_SterileProgramID = "0";
        String p_SterileMachineID = "0";
        BackgroundWorker backgroundWorker = new BackgroundWorker(CssdSterile.this);
        backgroundWorker.execute("create_wash", userid, p_SterileProgramID, p_SterileMachineID,xDep,xId,xQty,xReSterile,xReSterileType,RETURN_IsWithdraw);
    }

    public int getMachineNumberActive(String MachineId) {
        if(MAX_STERILE_MACHINE > 0 && MACHINE_1.get(i).getID().equals(MachineId)){
            return MACHINE_1.get(i).getMachineNumber();
        }
        if(MAX_STERILE_MACHINE > 1 && MACHINE_2.get(i).getID().equals(MachineId)){
            return MACHINE_2.get(i).getMachineNumber();
        }

        if(MAX_STERILE_MACHINE > 2 && MACHINE_3.get(i).getID().equals(MachineId)){
            return MACHINE_3.get(i).getMachineNumber();
        }

        if(MAX_STERILE_MACHINE > 3 && MACHINE_4.get(i).getID().equals(MachineId)){
            return MACHINE_4.get(i).getMachineNumber();
        }

        if(MAX_STERILE_MACHINE > 4 && MACHINE_5.get(i).getID().equals(MachineId)){
            return MACHINE_5.get(i).getMachineNumber();
        }

        return 0;

    }

    // =============================================================================================

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }

        String TYPE;

        //private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            String post_data = "";
            String url_ = null;

            TYPE = type;

            if(type.equals("create_wash")) {

                String p_UserCode = params[1];
                String p_SterileProgramID = params[2];
                String p_SterileMachineID = params[3];
                String p_Dept = params[4];
                String p_ID = params[5];
                String p_Qty = params[6];
                String p_ReSterile = params[7];
                String p_ReSterileType = params[8];
                String p_IsWithdraw = params[9];
                url_ = Url.URL + "1/set_create_wash.php";

                post_data += "p_UserCode=" + p_UserCode;
                post_data += "&p_SterileProgramID=" + p_SterileProgramID;
                post_data += "&p_SterileMachineID=" + p_SterileMachineID;
                post_data += "&p_DeptID=" + p_Dept;
                post_data += "&p_ID=" + p_ID;
                post_data += "&p_Qty=" + p_Qty;
                post_data += "&p_ReSterile=" + p_ReSterile;
                post_data += "&p_ReSterileType=" + p_ReSterileType;
                post_data += "&p_IsWithdraw=" + p_IsWithdraw;

                if(B_ID != null){
                    post_data += "&p_bid=" + B_ID;
                }
            }

            else{
                return null;
            }

            try {

                URL url = new URL(url_);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //out.println("URL = " + url_ + post_data);

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // ===========================================================================================

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String result="";
                String line="";

                if((line = bufferedReader.readLine())!= null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
            //this.dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            AsonData ason = new AsonData(result);

            Success = ason.isSuccess();
            size = ason.getSize();
            data = ason.getASONData();

            if(Success && data != null) {
                //if (dialog.isShowing()) {
                //    dialog.dismiss();
                //}
            }else{
                //if (dialog.isShowing()) {
                //    dialog.dismiss();
                //}
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    // =============================================================================================
    // Model
    // =============================================================================================
    private ModelSterileMachine getSterileMachine(
            String ID,
            String MachineName,
            String IsActive,
            String DocNo,
            String StartTime,
            String FinishTime,
            String PauseTime,
            String IsPause,
            String CountTime,
            int index
    ){
        return new ModelSterileMachine(
                ID,
                MachineName,
                IsActive,
                DocNo,
                StartTime,
                FinishTime,
                PauseTime,
                IsPause,
                CountTime,
                index
        );
    }

    private ModelSterileMachine getSterileMachine(
            String ID,
            String MachineName,
            String IsActive,
            String DocNo,
            String StartTime,
            String FinishTime,
            String PauseTime,
            String IsPause,
            int index
    ){
        return new ModelSterileMachine(
                ID,
                MachineName,
                IsActive,
                DocNo,
                StartTime,
                FinishTime,
                PauseTime,
                IsPause,
                index
        );
    }

    private ModelSterileDetail getSterileDetail(
            String ID,
            String DocNo,
            String ItemStockID,
            String Qty,
            String itemname,

            String itemcode,
            String UsageCode,
            String age,
            String ImportID,
            String SterileDate,

            String ExpireDate,
            String IsStatus,
            String OccuranceQty,
            String DepName,
            String DepName2,

            String LabelID,
            String usr_sterile,
            String usr_prepare,
            String usr_approve,
            String SterileRoundNumber,

            String MachineName,
            String Price,
            String Time,
            String SterileProcessID,
            String PrintCount,

            String Printer,
            String UsageCount,
            String ItemSetData,
            int index
    ){
        return new ModelSterileDetail(
                ID,
                DocNo,
                ItemStockID,
                Qty,
                itemname,

                itemcode,
                UsageCode,
                age,
                ImportID,
                SterileDate,

                ExpireDate,
                IsStatus,
                OccuranceQty,
                DepName,
                DepName2,

                LabelID,
                usr_sterile,
                usr_prepare,
                usr_approve,
                SterileRoundNumber,

                MachineName,
                Price,
                Time,
                SterileProcessID,
                PrintCount,

                Printer,
                UsageCount,
                ItemSetData,
                index
        );
    }

    // =============================================================================================

    private List<ModelSterileDetail> getItemLabel(String Label){
        try {
            List<ModelSterileDetail> list = new ArrayList<>();
            List<ModelSterileDetail> DATA_MODEL = MODEL_STERILE_DETAIL;

            Iterator li = DATA_MODEL.iterator();

            String LABEL_ID = null;

            while(li.hasNext()) {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                LABEL_ID = m.getLabelID();

                if (LABEL_ID.equals(Label) && m.getPrintCount() == 0) {
                    list.add(
                            getSterileDetail(
                                    m.getID(),
                                    m.getDocNo(),
                                    m.getItemStockID(),
                                    m.getQty(),
                                    m.getItemname(),

                                    m.getItemcode(),
                                    m.getUsageCode(),
                                    m.getAge(),
                                    m.getImportID(),
                                    m.getSterileDate(),

                                    m.getExpireDate(),
                                    m.getIsStatus(),
                                    m.getOccuranceQty(),
                                    m.getDepName(),
                                    m.getDepName2(),

                                    m.getLabelID(),
                                    m.getUsr_sterile(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getSterileRoundNumber(),

                                    m.getMachineName(),
                                    m.getPrice(),
                                    m.getTime(),
                                    m.getSterileProcessID(),
                                    m.getQty_Print(),

                                    m.getPrinter(),
                                    m.getUsageCount(),
                                    m.getItemSetData(),
                                    m.getIndex()
                            )
                    );
                }
            }

            return list;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // =============================================================================================
    // -- Generate Process ...
    // =============================================================================================

    public void generateProcess() {

        class GenerateProcess extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    try {
                        try {
                            MODEL_STERILE_PROCESS = getModelSterileProcess();
                            MAX_STERILE_PROCESS = MODEL_STERILE_PROCESS.size();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }

                        //hideProcess();

                        List<ModelSterileProcess> DATA_MODEL = MODEL_STERILE_PROCESS;

                        Iterator i = DATA_MODEL.iterator();

                        while(i.hasNext()) {
                            try {
                                ModelSterileProcess m = (ModelSterileProcess) i.next();

                                // Model Sterile Process
                                newSterileProcess(m.getID(), m.getSterileName(), (m.isCancel() ? "1" : "0") , Integer.toString(m.getSterileR()), m.getProcessTest(),m.getIndex()+1);

                                // New Button Sterile Process
                                newProcess(m.getIndex()+1, m.getID(), m.getSterileR());

                                // Hide Button Sterile Process
                                hideProcess(m.getIndex()+1, m.isCancel());

                            }catch(Exception e){
                                e.printStackTrace();
                                return;
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_sterile_process.php", data);

                return result;
            }

            private List<ModelSterileProcess> getModelSterileProcess() throws Exception{

                List<ModelSterileProcess> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileProcess(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelSterileProcess getSterileProcess(
                    String ID,
                    String SterileName,
                    String IsCancel,
                    String SterileR,
                    String ProcessTest,
                    int index
            ){
                return new ModelSterileProcess(
                        ID,
                        SterileName,
                        IsCancel,
                        SterileR,
                        ProcessTest,
                        index
                );
            }

            // =========================================================================================
        }

        GenerateProcess obj = new GenerateProcess();
        obj.execute();
    }

    // =============================================================================================
    // -- Generate Machine ...
    // =============================================================================================

    public void generateMachine(final String p_SterileProcessID) {

        //Clear Form
        clearForm();

        STERILE_MACHINE_NUMBER_ACTIVE = 0;



        class GenerateMachine extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE_MACHINE = getModelSterileMachine();
                        MAX_STERILE_MACHINE = MODEL_STERILE_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    // Hide Machine
                    hideMachine();

                    List<ModelSterileMachine> DATA_MODEL = MODEL_STERILE_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    while(i.hasNext()) {
                        try {
                            ModelSterileMachine m = (ModelSterileMachine) i.next();

                            // New Button Sterile Machine
                            newMachine(m.getID(), m.getMachineName(), m.getIsActive(), m.getDocNo(),m.getStartTime(), m.getFinishTime(), m.getPauseTime(), m.getIsPause() , m.getCountTime(),m.getIndex()+1);

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }

                    // Start Machine
                    startMachine();

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }else{
                    hideAllMachine();

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    finish();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                data.put("p_SterileProcessID", p_SterileProcessID);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_sterile_machine_detail.php", data);

                return result;
            }

            private List<ModelSterileMachine> getModelSterileMachine() throws Exception{

                List<ModelSterileMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }

        GenerateMachine obj = new GenerateMachine();
        obj.execute();
    }

    // =============================================================================================
    // -- Start Machine ...
    // =============================================================================================

    public void startMachine(final String p_doc_no) {


        class StartMachine extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE_MACHINE = getModelSterileMachine();
                        MAX_STERILE_MACHINE = MODEL_STERILE_MACHINE.size();
                        onDisplay(p_doc_no);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterileMachine> DATA_MODEL = MODEL_STERILE_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterileMachine m = (ModelSterileMachine) i.next();

                            // New Button Sterile Machine (Set Machine Data)
                            newMachine(m.getID(), m.getMachineName(), m.getIsActive(), m.getDocNo(),m.getStartTime(), m.getFinishTime(), m.getPauseTime(), m.getIsPause() , "", STERILE_MACHINE_NUMBER_ACTIVE);

                            // Set Time
                            txt_start.setText(m.getStartTime().substring(11, 16));
                            txt_finish.setText(m.getFinishTime().substring(11, 16));

                            // Refresh Detail
                            displaySterileDetail(m.getDocNo());

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }

                    startMachineActive();

                    Update_S_id_IN_Baskettag(p_doc_no,3);

                }else{
                    hideAllMachine();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_doc_no);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                if (OccupancyRate_Con == true) {
                    data.put("OccupancyRate", OccupancyRate_Sterile.getText().toString());
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_start_time.php", data);
                Log.d("BANKM2",data+"");
                Log.d("BANKM2",result);

                return result;
            }

            private List<ModelSterileMachine> getModelSterileMachine() throws Exception{

                List<ModelSterileMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }

        StartMachine obj = new StartMachine();
        obj.execute();
    }

    // =============================================================================================
    // -- Update Finish Machine ...
    // =============================================================================================

    public void updateFinishMachine(final int p_machine_no, final String p_doc_no) {

        class UpdateFinishMachine extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    int MachineNo = 0;

                    try {
                        MachineNo = Integer.valueOf(data.get(0)).intValue();
                    }catch(Exception e){
                        e.printStackTrace();
                        return ;
                    }

                    // New Button Sterile Machine (Set Machine Data)
                    newMachine(getMachineId(MachineNo), getMachineName(MachineNo), "0", null, null, null, null, null,"", MachineNo);

                    // Clear Machine
                    clearMachine(MachineNo);
                    if (OccupancyRate_Con == true) {
                        OccupancyRate_Sterile.setText("");
                        OccupancyRate_Text_Sterile = "";
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_doc_no);
                data.put("p_machine_no", Integer.toString(p_machine_no));

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_finish_time.php", data);

                return result;
            }

            // =========================================================================================
        }

        UpdateFinishMachine obj = new UpdateFinishMachine();
        obj.execute();
    }

    // =============================================================================================
    // -- Update Pause Machine ...
    // =============================================================================================

    public void updatePauseMachine(final int p_machine_no, final String p_doc_no) {

        class UpdatePauseMachine extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    /*
                    ////System.out.println("pause");
                    ////System.out.println("ID" + data.get(0) );
                    ////System.out.println("MachineName" + data.get(1) );
                    ////System.out.println("IsActive" + data.get(2) );
                    ////System.out.println("DocNo" + data.get(3) );
                    ////System.out.println("StartTime" + data.get(4) );
                    ////System.out.println("FinishTime" + data.get(5) );
                    ////System.out.println("PauseTime" + data.get(6) );
                    ////System.out.println("IsPause" + data.get(7) );
                    ////System.out.println("--------------------------------");
                    */

                    try {
                        MODEL_STERILE_MACHINE = getModelSterileMachine();
                        MAX_STERILE_MACHINE = MODEL_STERILE_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterileMachine> DATA_MODEL = MODEL_STERILE_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterileMachine m = (ModelSterileMachine) i.next();

                            newMachine(m.getID(), m.getMachineName(), m.getIsActive(), m.getDocNo(),m.getStartTime(), m.getFinishTime(), m.getPauseTime(), m.getIsPause() ,"", STERILE_MACHINE_NUMBER_ACTIVE);

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_doc_no);
                data.put("p_machine_no", Integer.toString(p_machine_no));

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_pause_time.php", data);

                return result;
            }

            private List<ModelSterileMachine> getModelSterileMachine() throws Exception{

                List<ModelSterileMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }

        UpdatePauseMachine obj = new UpdatePauseMachine();
        obj.execute();
    }

    // =============================================================================================
    // -- Update Run Machine ...
    // =============================================================================================

    public void updateRunMachine(final int p_machine_no, final String p_doc_no) {

        class UpdateRunMachine extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE_MACHINE = getModelSterileMachine();
                        MAX_STERILE_MACHINE = MODEL_STERILE_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterileMachine> DATA_MODEL = MODEL_STERILE_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterileMachine m = (ModelSterileMachine) i.next();

                            // New Button Sterile Machine (Set Machine Data)
                            newMachine(m.getID(), m.getMachineName(), m.getIsActive(), m.getDocNo(),m.getStartTime(), m.getFinishTime(), m.getPauseTime(), m.getIsPause() ,"", STERILE_MACHINE_NUMBER_ACTIVE);

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }

                    startMachineActive();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_doc_no);
                data.put("p_machine_no", Integer.toString(p_machine_no));

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_run_time.php", data);

                return result;
            }

            private List<ModelSterileMachine> getModelSterileMachine() throws Exception{

                List<ModelSterileMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }

        UpdateRunMachine obj = new UpdateRunMachine();
        obj.execute();
    }

    // =============================================================================================
    // -- Update Clear Machine ...
    // =============================================================================================

    public void updateSterileMachineClearDocNo(final String id, final int machine_no) {

        class UpdateClearMachine extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    // New Button Sterile Machine (Set Machine Data)
                    newMachine(getMachineId(machine_no), getMachineName(machine_no), "0", null, null, null, null, null,"", machine_no);

                    // Clear Machine
                    clearMachine(machine_no);

                    // Clear Form
                    clearForm();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_id", id);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_machine_clear_docno.php", data);

                return result;
            }

            private List<ModelSterileMachine> getModelSterileMachine() throws Exception{

                List<ModelSterileMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }

        UpdateClearMachine obj = new UpdateClearMachine();
        obj.execute();
    }


    // =============================================================================================
    // -- Update Sterile Machine Import DocNo ...
    // =============================================================================================

    public void updateSterileMachineImportDocNo(final int p_no, final String p_id, final String p_docno) {

        class UpdateSterileMachineImportDocNo extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    try {
                        MODEL_STERILE_MACHINE = getModelSterileMachine();
                        //MAX_STERILE_MACHINE = MODEL_STERILE_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterileMachine> DATA_MODEL = MODEL_STERILE_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterileMachine m = (ModelSterileMachine) i.next();

                            // Get Data Machine
                            newMachine(m.getID(), m.getMachineName(), m.getIsActive(), m.getDocNo(), m.getStartTime(), m.getFinishTime(), m.getPauseTime(), m.getIsPause(), "", p_no);

                            // Display Machine
                            clickMachine(p_no);

                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }

            private List<ModelSterileMachine> getModelSterileMachine() throws Exception{

                List<ModelSterileMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        index
                                )
                        );

                        index++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_id", p_id);
                data.put("p_docno", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_machine_import_docno.php", data);

                return result;
            }
            // =========================================================================================
        }

        UpdateSterileMachineImportDocNo obj = new UpdateSterileMachineImportDocNo();
        obj.execute();
    }




    // =============================================================================================
    // -- Create Sterile 1 : 1 ...
    // =============================================================================================

    public void createSterile(final String p_id, final String p_SterileProgramID, final String PackingMatID,final String gQty,final String usageCode) {

        final String p_SterileMachineID = getMachineId(STERILE_MACHINE_NUMBER_ACTIVE);
        final String p_doc_date = txt_doc_date.getText().toString();

        class CreateSterile extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);
            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                dialog.dismiss();
                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE = getModelSterile();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterile> DATA_MODEL = MODEL_STERILE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterile m = (ModelSterile) i.next();
                            String p_doc_no = m.getDocNo();

                            if(p_doc_no == null  || p_doc_no.equals("") || p_doc_no.equals("-")){
                                return;
                            }

                            // Display Sterile
                            displaySterile(
                                    p_doc_no,
                                    m.getDocDate(),
                                    m.getSterileName(),
                                    m.getS_time(),
                                    m.getF_time(),
                                    m.getSterileRoundNumber(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getUsr_sterile(),
                                    m.getPrepareCode(),
                                    m.getApproveCode(),
                                    m.getUserCode(),
                                    m.getSterileProgramID(),
                                    m.getPrintBalance(),
                                    m.getPrintAll(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getUsr_beforeapprove()
                            );

                            //////System.out.println("STERILE_MACHINE_NUMBER_ACTIVE = " + STERILE_MACHINE_NUMBER_ACTIVE);

                            // SetData Machine
                            setMachine(p_doc_no);

                            // Add Sterile Detail
                            createSterile_loop =true;
                            addSterileDetail(p_doc_no, p_id, PackingMatID,gQty,usageCode);

                            //////System.out.println("Add Sterile Detail DocNo = " + m.getDocNo());

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }

                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_UserCode", userid);
                data.put("p_SterileProgramID", p_SterileProgramID);
                data.put("p_SterileMachineID", p_SterileMachineID);

                if(!p_doc_date.equals("")) {
                    data.put("p_doc_date", DateTime.convertDate(p_doc_date));
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_sterile.php", data);
                Log.d("tog_create_sterile",data+"");
                Log.d("tog_create_sterile",result);
                return result;
            }

            private List<ModelSterile> getModelSterile() throws Exception{

                List<ModelSterile> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterile(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        data.get(i + 28),
                                        index
                                )
                        );

                        index++;
                    }

                    // //////System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelSterile getSterile(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String PrepareCode,
                    String ApproveCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String sterileProgramID,
                    String sterileMachineID,
                    String sterileRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String s_time,
                    String f_time,
                    String Remark,
                    String IsOccurance,
                    String SterileName,
                    String MachineName,
                    String usr_sterile,
                    String usr_prepare,
                    String usr_approve,
                    String PrintAll,
                    String PrintCount,
                    String TestProgramID,
                    String TestProgramName,
                    String Batch,
                    int index
            ){
                return new ModelSterile(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        PrepareCode,
                        ApproveCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        sterileProgramID,
                        sterileMachineID,
                        sterileRoundNumber,
                        StartTime,
                        FinishTime,
                        s_time,
                        f_time,
                        Remark,
                        IsOccurance,
                        SterileName,
                        MachineName,
                        usr_sterile,
                        usr_prepare,
                        usr_approve,
                        PrintAll,
                        PrintCount,
                        TestProgramID,
                        TestProgramName,
                        Batch,
                        index
                );
            }

            // =========================================================================================
        }

        CreateSterile obj = new CreateSterile();
        obj.execute();
    }

    // =============================================================================================
    // -- Create Sterile 1 : N ...
    // =============================================================================================

    public void createSterile(final String p_SterileProgramID, final String DATA, final boolean IsNew) {

        final String p_SterileMachineID = getMachineId  (STERILE_MACHINE_NUMBER_ACTIVE);
        final String p_doc_date = txt_doc_date.getText().toString();

        class CreateSterile extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);
            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                dialog.dismiss();
                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE = getModelSterile();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterile> DATA_MODEL = MODEL_STERILE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterile m = (ModelSterile) i.next();
                            String p_doc_no = m.getDocNo();

                            if(p_doc_no == null  || p_doc_no.equals("") || p_doc_no.equals("-")){
                                return;
                            }

                            // Display Sterile
                            displaySterile(
                                    p_doc_no,
                                    m.getDocDate(),
                                    m.getSterileName(),
                                    m.getS_time(),
                                    m.getF_time(),
                                    m.getSterileRoundNumber(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getUsr_sterile(),
                                    m.getPrepareCode(),
                                    m.getApproveCode(),
                                    m.getUserCode(),
                                    m.getSterileProgramID(),
                                    m.getPrintBalance(),
                                    m.getPrintAll(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getUsr_beforeapprove()
                            );
                            // SetData Machine
                            setMachine(p_doc_no);
                            if(DATA == null) {
                                // Add Sterile Detail (Import Wash Detail)
                                addSterileDetail(p_doc_no);
                            }else{
                                // Add Sterile Detail (Import New Item)
                                if(IsNew) {
                                    addSterileDetailAdvance(p_doc_no, DATA, "cssd_add_sterile_detail_by_import_new_item_stock.php");
                                }else{
                                    addSterileDetailAdvance(p_doc_no, DATA, "cssd_add_sterile_detail_by_import_no_wash.php");
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถสร้างเอกสารฆ่าเชื้อได้ !!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_UserCode", userid);
                data.put("p_SterileProgramID", p_SterileProgramID);
                data.put("p_SterileMachineID", p_SterileMachineID);
                if(!p_doc_date.equals("")) {
                    data.put("p_doc_date", DateTime.convertDate(p_doc_date));
                }
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_sterile.php", data);
                Log.d("BANK123",data+"");
                Log.d("BANK123",result);
                return result;
            }

            private List<ModelSterile> getModelSterile() throws Exception{
                List<ModelSterile> list = new ArrayList<>();
                try {
                    int index = 0;
                    for(int i=0;i<data.size();i+=size){
                        list.add(
                                getSterile(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        data.get(i + 28),
                                        index
                                )
                        );
                        index++;
                    }
                    // //////System.out.println("list = " + list.size());
                }catch(Exception e){
                    e.printStackTrace();
                }
                return list;
            }
            private ModelSterile getSterile(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String PrepareCode,
                    String ApproveCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String sterileProgramID,
                    String sterileMachineID,
                    String sterileRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String s_time,
                    String f_time,
                    String Remark,
                    String IsOccurance,
                    String SterileName,
                    String MachineName,
                    String usr_sterile,
                    String usr_prepare,
                    String usr_approve,
                    String PrintAll,
                    String PrintCount,
                    String TestProgramID,
                    String TestProgramName,
                    String Batch,
                    int index
            ){
                return new ModelSterile(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        PrepareCode,
                        ApproveCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        sterileProgramID,
                        sterileMachineID,
                        sterileRoundNumber,
                        StartTime,
                        FinishTime,
                        s_time,
                        f_time,
                        Remark,
                        IsOccurance,
                        SterileName,
                        MachineName,
                        usr_sterile,
                        usr_prepare,
                        usr_approve,
                        PrintAll,
                        PrintCount,
                        TestProgramID,
                        TestProgramName,
                        Batch,
                        index
                );
            }
            // =====================================================================================
        }
        CreateSterile obj = new CreateSterile();
        obj.execute();
    }
    // =============================================================================================
    // -- Create Sterile Test Machine ...
    // =============================================================================================
    public void createSterile(final int p_SterileProcessID) {
        final String usr_prepare = txt_usr_prepare.getContentDescription().toString();
        final String usr_approve = txt_usr_approve.getContentDescription().toString();
        final String test_program_id = txt_test_program.getContentDescription().toString();
        final String x = txt_doc_date.getText().toString();
        final String xdt = txt_doc_date.getText().toString();
        final String p_SterileMachineID = getMachineId(STERILE_MACHINE_NUMBER_ACTIVE);
        class CreateSterile extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);
            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                dialog.dismiss();
                AsonData ason = new AsonData(result);
                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();
                if(Success && data != null) {
                    try {
                        MODEL_STERILE = getModelSterile();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    List<ModelSterile> DATA_MODEL = MODEL_STERILE;
                    Iterator i = DATA_MODEL.iterator();
                    if(i.hasNext()) {
                        try {
                            ModelSterile m = (ModelSterile) i.next();
                            String p_doc_no = m.getDocNo();
                            if(p_doc_no == null  || p_doc_no.equals("") || p_doc_no.equals("-")){
                                return;
                            }
                            // Display Sterile
                            displaySterile(
                                    p_doc_no,
                                    m.getDocDate(),
                                    m.getSterileName(),
                                    m.getS_time(),
                                    m.getF_time(),
                                    m.getSterileRoundNumber(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getUsr_sterile(),
                                    m.getPrepareCode(),
                                    m.getApproveCode(),
                                    m.getUserCode(),
                                    m.getSterileProgramID(),
                                    m.getPrintBalance(),
                                    m.getPrintAll(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getUsr_beforeapprove()
                            );
                            setMachine(p_doc_no);
                            startMachine(getDocNo());
                            if (isTestBNT){
                                isTestMac = true;
                            }else{
                                isTestMac = false;
                            }
                            getQR(p_doc_no, "s1", "");
                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_UserCode", userid);
                data.put("p_SterileProcessID", Integer.toString(p_SterileProcessID));
                data.put("p_SterileMachineID", p_SterileMachineID);
                data.put("p_usr_prepare", usr_prepare );
                data.put("p_usr_approve", usr_approve );
                data.put("p_usr_sterile", usr_approve );

                if(test_program_id != null && !test_program_id.equals("")) {
                    data.put("p_test_program_id", test_program_id);
                }

                if(!x.equals("")) {
                    data.put("p_doc_date", DateTime.convertDate(xdt));
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_test_machine_sterile.php", data);
                return result;
            }

            private List<ModelSterile> getModelSterile() throws Exception{
                List<ModelSterile> list = new ArrayList<>();
                try {
                    int index = 0;
                    for(int i=0;i<data.size();i+=size){
                        list.add(
                                getSterile(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        data.get(i + 28),
                                        index
                                )
                        );
                        index++;
                    }
                    // //////System.out.println("list = " + list.size());
                }catch(Exception e){
                    e.printStackTrace();
                }
                return list;
            }
            private ModelSterile getSterile(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String PrepareCode,
                    String ApproveCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String sterileProgramID,
                    String sterileMachineID,
                    String sterileRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String s_time,
                    String f_time,
                    String Remark,
                    String IsOccurance,
                    String SterileName,
                    String MachineName,
                    String usr_sterile,
                    String usr_prepare,
                    String usr_approve,
                    String PrintAll,
                    String PrintCount,
                    String TestProgramID,
                    String TestProgramName,
                    String Batch,
                    int index
            ){
                return new ModelSterile(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        PrepareCode,
                        ApproveCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        sterileProgramID,
                        sterileMachineID,
                        sterileRoundNumber,
                        StartTime,
                        FinishTime,
                        s_time,
                        f_time,
                        Remark,
                        IsOccurance,
                        SterileName,
                        MachineName,
                        usr_sterile,
                        usr_prepare,
                        usr_approve,
                        PrintAll,
                        PrintCount,
                        TestProgramID,
                        TestProgramName,
                        Batch,
                        index
                );
            }
            // =========================================================================================
        }
        CreateSterile obj = new CreateSterile();
        obj.execute();
    }
    // =============================================================================================
    // -- Display Sterile
    // =============================================================================================
    public void onDisplay(final String p_docno) {
        class DisplaySterile extends AsyncTask<String, Void, String> {
            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE = getModelSterile();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelSterile> DATA_MODEL = MODEL_STERILE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelSterile m = (ModelSterile) i.next();

                            // Display Sterile
                            displaySterile(
                                    m.getDocNo(),
                                    m.getDocDate(),
                                    m.getSterileName(),
                                    m.getS_time(),
                                    m.getF_time(),
                                    m.getSterileRoundNumber(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getUsr_sterile(),
                                    m.getPrepareCode(),
                                    m.getApproveCode(),
                                    m.getUserCode(),
                                    m.getSterileProgramID(),
                                    m.getPrintBalance(),
                                    m.getPrintAll(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getUsr_beforeapprove()
                            );

                            // Display Sterile Detail
                            displaySterileDetail( getDocNo() );

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile.php", data);
                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("ttest_display_sterile","result = "+result);

                return result;
            }

            private List<ModelSterile> getModelSterile() throws Exception{

                List<ModelSterile> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterile(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        data.get(i + 28),
                                        index
                                )
                        );

                        index++;
                    }
                    // //////System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelSterile getSterile(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String PrepareCode,
                    String ApproveCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String sterileProgramID,
                    String sterileMachineID,
                    String sterileRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String s_time,
                    String f_time,
                    String Remark,
                    String IsOccurance,
                    String SterileName,
                    String MachineName,
                    String usr_sterile,
                    String usr_prepare,
                    String usr_approve,
                    String PrintAll,
                    String PrintCount,
                    String TestProgramID,
                    String TestProgramName,
                    String usr_beforeapprove,
                    int index
            ){
                return new ModelSterile(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        PrepareCode,
                        ApproveCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        sterileProgramID,
                        sterileMachineID,
                        sterileRoundNumber,
                        StartTime,
                        FinishTime,
                        s_time,
                        f_time,
                        Remark,
                        IsOccurance,
                        SterileName,
                        MachineName,
                        usr_sterile,
                        usr_prepare,
                        usr_approve,
                        PrintAll,
                        PrintCount,
                        TestProgramID,
                        TestProgramName,
                        usr_beforeapprove,
                        index
                );
            }

            // =========================================================================================
        }

        DisplaySterile obj = new DisplaySterile();
        obj.execute();
    }


    // =============================================================================================
    // -- Add Sterile Detail
    // =============================================================================================

    public void addSterileDetail(final String p_docno, final String p_data, final String p_PackingMatID,final String p_Qty,final String usagecode) {

        final boolean mode = switch_mode.isChecked();

        class AddSterileDetail extends AsyncTask<String, Void, String> {


            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCancelable(false);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        Log.d("OOOO","result :: "+c.getString("result"));
                        if(c.getString("result").equals("A")) {
                            // Display Sterile & Sterile Detail
                            onDisplay( getDocNo() );
                            // Display Import Wash Detail
                            displayWashDetail( getSterileProcessId() );
                            CheckUsageEms(getDocNo());
                            DIALOG_ACTIVE = true;

                            Update_S_id_IN_Baskettag(c.getString("sql_update_wash_detail"),1);

                        }else{
                            Toast.makeText(CssdSterile.this, "ไม่พบรายการ !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);
                data.put("p_docno", p_docno);
                data.put("p_Qty", p_Qty);

                if(p_PackingMatID != null) {
                    data.put("p_PackingMatID", p_PackingMatID);
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                data.put("p_is_status", mode ? "-1" : "1");

                if(usagecode != null) {
                    data.put("p_UsageCode", usagecode);
                }

                Log.d("tog_create_sterile","data :: "+data);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_add_sterile_detail_json.php", data);
                Log.d("tog_create_sterile","result :: "+result);
                return result;
            }

            // =========================================================================================
        }

        AddSterileDetail obj = new AddSterileDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Add Sterile Detail (New Item)
    // =============================================================================================

    public void addSterileDetailAdvance(final String p_docno, final String p_data, final String file) {

        class AddSterileDetail extends AsyncTask<String, Void, String> {


            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try{
                        // Display Sterile & Sterile Detail
                        onDisplay( getDocNo() );

                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }
                }else{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);
                data.put("p_docno", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + file, data);
                Log.d("BAKNFD",data+"");
                Log.d("BAKNFD",result);
                return result;
            }

            // =========================================================================================
        }

        AddSterileDetail obj = new AddSterileDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Update Sterile SterileProgramID
    // =============================================================================================

    public void updateSterile(final String p_docno, final String p_sterile_program_id) {

        class UpdateSterile extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_value", p_sterile_program_id);
                data.put("p_doc_no", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_program.php", data);


                return result;
            }

            // =========================================================================================
        }

        UpdateSterile obj = new UpdateSterile();
        obj.execute();
    }

    // =============================================================================================
    // -- Remove Sterile Detail
    // =============================================================================================

    public void removeSterileDetail(final String p_data) {

        class RemoveSterileDetail extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    // Display Sterile & Sterile Detail
                    onDisplay( getDocNo() );

                    // Display Import Wash Detail
                    displayWashDetail( getSterileProcessId() );

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }else{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_remove_sterile_detail.php", data);
                Log.d("LJDLJDL",data+"");
                return result;
            }

            // =========================================================================================
        }

        RemoveSterileDetail obj = new RemoveSterileDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Update Sterile
    // =============================================================================================

    public void updateSterile(final int field, final String value, final String doc_no) {

        class RemoveSterileDetail extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    //
                }else{
                    Toast.makeText(CssdSterile.this, "ไม่สามารถบันทึกข้อมูลได้ !!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_field", Integer.toString(field));
                data.put("p_value", value);
                data.put("p_doc_no", doc_no);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile.php", data);

                return result;
            }

            // =========================================================================================
        }

        RemoveSterileDetail obj = new RemoveSterileDetail();
        obj.execute();
    }


    // =============================================================================================
    // -- Display Sterile Detail
    // =============================================================================================

    public void displaySterileDetail(final String p_docno) {
        if(p_docno == null || p_docno.equals("-") || p_docno.equals("")){
            return ;
        }

        class DisplaySterileDetail extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {

                        MODEL_STERILE_DETAIL = getModelSterileDetail();

                        List<ModelSterileDetail> MODEL_STERILE_DETAIL_GROUP_BASKET = new ArrayList<>();
                        HashMap<String,List<ModelSterileDetail>> MAP_MODEL_STERILE_DETAIL_GROUP_BASKET = new HashMap<String,List<ModelSterileDetail>>();

                        for(int i =0;i<MODEL_STERILE_DETAIL.size();i++){
                            ModelSterileDetail x = MODEL_STERILE_DETAIL.get(i);
                            String key = x.getBasketCode();
                            if(key.equals("-")){
                                MODEL_STERILE_DETAIL_GROUP_BASKET.add(x);
                            }else{
                                if(MAP_MODEL_STERILE_DETAIL_GROUP_BASKET.containsKey(key)){
                                    MAP_MODEL_STERILE_DETAIL_GROUP_BASKET.get(key).add(x);
                                }else{
                                    List<ModelSterileDetail> list = new ArrayList<>();
                                    list.add(x);
                                    MAP_MODEL_STERILE_DETAIL_GROUP_BASKET.put(key,list);
                                    ModelSterileDetail clone = (ModelSterileDetail) x.clone();
                                    clone.setItemname(x.getBasketName());
                                    clone.setUsageCode(x.getBasketCode());
                                    clone.setIsBasket(true);
                                    MODEL_STERILE_DETAIL_GROUP_BASKET.add(0,clone);
                                }
                            }
                        }

                        DocNo = p_docno;

                        try {

                            ArrayAdapter<ModelSterileDetail> adapter;

                            if(DISPLAY_MODE == 3) {
                                adapter = new SterileDetailGridViewAdapter(CssdSterile.this, MODEL_STERILE_DETAIL, !checkMachineActive(),B_ID);
                                grid_sterile_detail.setAdapter(adapter);
                            }else if(DISPLAY_MODE == 2) {
                                adapter = new SterileDetailAdapter(CssdSterile.this, MODEL_STERILE_DETAIL_GROUP_BASKET, !checkMachineActive(),B_ID,Width,Heigth,2,MAP_MODEL_STERILE_DETAIL_GROUP_BASKET);
                                list_sterile_detail.setAdapter(adapter);
                            }else if(DISPLAY_MODE == 1) {
                                adapter = new SterileDetailBigSizeAdapter(CssdSterile.this, MODEL_STERILE_DETAIL, !checkMachineActive(),B_ID);
                                list_sterile_detail.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            list_sterile_detail.setAdapter(null);
                            grid_sterile_detail.setAdapter(null);
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }else{
                    list_sterile_detail.setAdapter(null);
                    grid_sterile_detail.setAdapter(null);
                    MODEL_STERILE_DETAIL = null;
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                if(IsShowBasket){
                    data.put("p_show_basket", "1");
                }

                String result = null;

                try{
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile_detail.php", data);
                    Log.d("BANK",data+"");
                    Log.d("BANK",result);
                }catch(Exception e){
                    e.printStackTrace();

                }
                return result;
            }
            private List<ModelSterileDetail> getModelSterileDetail() {
                List<ModelSterileDetail> list = new ArrayList<>();
                try {
                    int index = 0;
                    for(int i=0;i<data.size();i+=size){
                        list.add(
                                new ModelSterileDetail(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        data.get(i + 28),
                                        data.get(i + 31),
                                        index,
                                        data.get(i + 29)
                                )
                        );
                        index++;
                    }
                    // //////System.out.println("list = " + list.size());
                }catch(Exception e){
                    e.printStackTrace();
                }
                return list;
            }
            // =========================================================================================
        }
        DisplaySterileDetail obj = new DisplaySterileDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Remove WashDetail
    // =============================================================================================

    public void updateWashDetailStatus(final String p_itemcode) {

        final boolean mode = switch_mode.isChecked();

        class UpdateWashDetail extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if (Success && data != null) {
                    // Display Wash Detail
                    displayWashDetail(getSterileProcessId());
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_itemcode", p_itemcode);
                data.put("p_is_status", mode ? "1" : "-1");

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_detail_is_status.php", data);

                return result;
            }
        }

        UpdateWashDetail obj = new UpdateWashDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Remove WashDetail
    // =============================================================================================

    public void onRemoveWashDetail(final String p_itemcode) {

        class RemoveWashDetail extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if (Success && data != null) {
                    // Display Wash Detail
                    displayWashDetail(getSterileProcessId());
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();

                data.put("p_itemcode", p_itemcode);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_remove_wash_details.php", data);

                return result;
            }
        }

        RemoveWashDetail obj = new RemoveWashDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Display Import Wash Detail ...
    // =============================================================================================

    public void displayWashDetail(final String p_SterileProcessID) {
        Log.d("tog_displayWashDetail","displayWashDetail");

        final boolean mode = switch_mode.isChecked();

        class DisplayWashDetail extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    try {

                        MODEL_IMPORT_WASH_DETAIL = getImportWashDetail();

                        List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_SUB = new ArrayList<>();

                        MAP_MODEL_IMPORT_WASH_DETAIL_SUB.clear();
                        MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.clear();
                        MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.clear();

                        for(int i =0;i<MODEL_IMPORT_WASH_DETAIL.size();i++){
                            ModelImportWashDetail x = MODEL_IMPORT_WASH_DETAIL.get(i);
                            String key = x.getBasketCode().toLowerCase();
                            if(key.equals("-")){
                                MODEL_IMPORT_WASH_DETAIL_SUB.add(x);
                                MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.add(x);
                            }else{
                                if(MAP_MODEL_IMPORT_WASH_DETAIL_SUB.containsKey(key)){
                                    MAP_MODEL_IMPORT_WASH_DETAIL_SUB.get(key).add(x);
                                }else{
                                    List<ModelImportWashDetail> list = new ArrayList<>();
                                    list.add(x);
                                    MAP_MODEL_IMPORT_WASH_DETAIL_SUB.put(key,list);
                                    ModelImportWashDetail clone = (ModelImportWashDetail) x.clone();
                                    clone.setI_name(x.getBasketName());
                                    clone.setCheck(true);
                                    MODEL_IMPORT_WASH_DETAIL_SUB.add(0,clone);
                                    if(x.getBasketType().equals("0")){
                                        MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.add(0,clone);
                                    }

                                }
                            }
                        }


                        MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET = MODEL_IMPORT_WASH_DETAIL_SUB;

                        try {
                            ArrayAdapter<ModelImportWashDetail> adapter;

                            if(DISPLAY_MODE == 3) {
                                adapter = new ImportWashDetailGridViewAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET);
                                if(p_SterileProcessID!=null){
                                    grid_wash_detail.setAdapter(adapter);
                                }
                            }else if(DISPLAY_MODE == 2) {

                                Log.d("tog_isprint","cnt = "+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR.size());
                                basket_dialog_w_list.setAdapter(new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR,MAP_MODEL_IMPORT_WASH_DETAIL_SUB,4));

//                                adapter = new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR,MAP_MODEL_IMPORT_WASH_DETAIL_SUB,4);
//                                basket_dialog_w_list.setAdapter(adapter);

                                if(MAP_MODEL_IMPORT_WASH_DETAIL_SUB.containsKey(basket_Code.toLowerCase())){
                                    MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR = MAP_MODEL_IMPORT_WASH_DETAIL_SUB.get(basket_Code.toLowerCase());
                                    Log.d("tog_cnt_basket_list","cnt_basket_list"+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size());
                                    cnt_basket_list.setText(" [ "+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size()+" รายการ ]");
                                    
                                    basket_dialog_list_basket.setAdapter(new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR,5));
                                    
                                    int xn=0;
                                    for(int ii=0;ii<MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size();ii++){
                                        if(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(ii).getPrint_count()<=0){
                                            xn = xn+1;
                                        }
                                    }
                                    set_num_btn_print_bk(xn);
                                }else{
                                    set_num_btn_print_bk(0);
                                    basket_dialog_list_basket.setAdapter(null);
                                }

                                if(p_SterileProcessID!=null){
                                    adapter = new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET,MAP_MODEL_IMPORT_WASH_DETAIL_SUB,3);
                                    list_wash_detail.setAdapter(adapter);
                                }
                            }else if(DISPLAY_MODE == 1) {
                                adapter = new ImportWashDetailBigSizeAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET);
                                if(p_SterileProcessID!=null){
                                    list_wash_detail.setAdapter(adapter);
                                }
                            }

                        } catch (Exception e) {
                            list_wash_detail.setAdapter(null);
                            grid_wash_detail.setAdapter(null);
                            e.printStackTrace();
                        }

                        displayWashDetailNotPrint(p_SterileProcessID);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }else{
                    list_wash_detail.setAdapter(null);
                    grid_wash_detail.setAdapter(null);

                    MODEL_IMPORT_WASH_DETAIL = null;
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(p_SterileProcessID==null){
                    data.put("p_SterileProcessID", "null");
                }else{
                    data.put("p_SterileProcessID", p_SterileProcessID);
                }

                data.put("p_Mode", mode ? "-1" : "1");

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                if(IsShowBasket){
                    data.put("p_show_basket", "1");
                }

                String result = null;

                try {
//                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_import_wash.php", data);//isprint =0
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_import_wash_isprint_detail.php", data);//isprint >0
                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("ttest_import_w_detail",data+"");
                Log.d("ttest_import_w_detail",result+"");

                return result;
            }

            public List<ModelImportWashDetail> getImportWashDetail() throws Exception{

                List<ModelImportWashDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        if(ConfigProgram.basket_tag && !ConfigProgram.pair_basket_2 && data.get(i + 18).equals("0")){
                           continue;
                        }

                        Log.d("tog_isprint","PrintCount = "+data.get(i+14)+"---"+data.get(i+18)+"---"+data.get(i+19));
                        list.add(
                                new ModelImportWashDetail(
                                        index,
                                        false,
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19)
                                )
                        );

                        index++;
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }


            // =========================================================================================
        }

        DisplayWashDetail obj = new DisplayWashDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Display Import Wash Detail not print...
    // =============================================================================================

    public void displayWashDetailNotPrint(final String p_SterileProcessID) {

        final boolean mode = switch_mode.isChecked();

        class displayWashDetailNotPrint extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    try {

                        MODEL_IMPORT_WASH_DETAIL_NOT_PRINT = getImportWashDetail();

                        List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_NOT_PRINT = new ArrayList<>();

                        List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR_NOT_PRINT = new ArrayList<>();

                        HashMap<String,List<ModelImportWashDetail>> MAP_MODEL_IMPORT_WASH_DETAIL_SUB_NOT_PRINT = new HashMap<String,List<ModelImportWashDetail>>();

                        for(int i =0;i<MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.size();i++){
                            ModelImportWashDetail x = MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i);
                            String key = x.getBasketCode().toLowerCase();
                            if(key.equals("-")){
                                MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_NOT_PRINT.add(x);
                                MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR_NOT_PRINT.add(x);
                            }else{
                                if(MAP_MODEL_IMPORT_WASH_DETAIL_SUB_NOT_PRINT.containsKey(key)){
                                    MAP_MODEL_IMPORT_WASH_DETAIL_SUB_NOT_PRINT.get(key).add(x);
                                }else{
                                    List<ModelImportWashDetail> list = new ArrayList<>();
                                    list.add(x);
                                    MAP_MODEL_IMPORT_WASH_DETAIL_SUB_NOT_PRINT.put(key,list);
                                    ModelImportWashDetail clone = (ModelImportWashDetail) x.clone();
                                    clone.setI_name(x.getBasketName());
                                    clone.setBasket(true);
                                    MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_NOT_PRINT.add(0,clone);
                                    if(x.getBasketType().equals("0")){
                                        MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR_NOT_PRINT.add(0,clone);
                                    }

                                }
                            }
                        }

                        try {
                            ImportWashNotPrintDetailAdapter adapter = new ImportWashNotPrintDetailAdapter(CssdSterile.this,MODEL_IMPORT_WASH_DETAIL_NOT_PRINT, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR_NOT_PRINT,MAP_MODEL_IMPORT_WASH_DETAIL_SUB_NOT_PRINT);
                            basket_dialog_list_washtag.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(p_SterileProcessID==null){
                    data.put("p_SterileProcessID", "null");
                }else{
                    data.put("p_SterileProcessID", p_SterileProcessID);
                }

                data.put("p_Mode", mode ? "-1" : "1");

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                if(IsShowBasket){
                    data.put("p_show_basket", "1");
                }

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_import_wash_detail.php", data);
                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("ttest_import_w_detail",data+"");
                Log.d("ttest_import_w_detail",result+"");

                return result;
            }

            public List<ModelImportWashDetail> getImportWashDetail() throws Exception{

                List<ModelImportWashDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){
                        Log.d("ttestdataget","data.get("+i+") = "+data.get(i+2)+"---"+data.get(i+19));
                        list.add(
                                new ModelImportWashDetail(
                                        index,
                                        false,
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19)
                                )
                        );

                        index++;
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }


            // =========================================================================================
        }

        displayWashDetailNotPrint obj = new displayWashDetailNotPrint();
        obj.execute();
    }

    public void TimeTest() {
        final String p_SterileMachineID = getMachineId(STERILE_MACHINE_NUMBER_ACTIVE);
        class TimeTest extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        Time = c.getString("TIME");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_ID",B_ID);
                data.put("Program",ProgramTest);
                data.put("p_SterileMachineID", p_SterileMachineID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_time_test.php", data);
                    Log.d("KDHKDH",data+"");
                    Log.d("KDHKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            // =========================================================================================
        }
        TimeTest obj = new TimeTest();
        obj.execute();
    }

    // =============================================================================================
    // -- Add Sterile Detail
    // =============================================================================================

    public void updatePrintStatus(final String p_data) {

        class UpdatePrintStatus extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    // Display Sterile & Sterile Detail
                    onDisplay( getDocNo() );
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_detail_print_status.php", data);

                return result;
            }

            // =========================================================================================
        }

        UpdatePrintStatus obj = new UpdatePrintStatus();
        obj.execute();
    }

    public void ShowProgram(final String LongDocNo,final int Machine_Number) {

        class ShowProgram extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);

                        Program = c.getString("SterileName");

                        if (Program.equals("TEST")){
                            SterileTest = 1;
                        }else {
                            SterileTest = 0;
                        }

                        if (isBowiedick() == true){
                            if (Program.equals("TEST")){
                                openDialogTest(Machine_Number);
                            }else {
                                Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                            }
                        }else if (isBowiedick() != true){
                            if (!Program.equals("TEST")){
                                openDialogProgram(Machine_Number);
                            }else {
                                Toast.makeText(CssdSterile.this, "ไม่สามารถหยุดการทำงานข้ามโปรแกรมได้ !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",LongDocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_program.php", data);
                    Log.d("JFHFKD",data+"");
                    Log.d("JFHFKD",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            // =========================================================================================
        }

        ShowProgram obj = new ShowProgram();
        obj.execute();
    }

    // =============================================================================================
    // -- Get Printer
    // =============================================================================================
    ArrayList<Integer> PRINTER = new ArrayList<>();
    ArrayList<String> PRINTER_IP = new ArrayList<>();
    int Width = 0;
    int Heigth = 0;
    //    ArrayList<Integer> HEIGHT = new ArrayList<>();
//    ArrayList<Integer> WIDTH = new ArrayList<>();
    int xItemname1 = 0;
    int yItemname1 = 0;
    int xUsagecode1 = 0;
    int yUasgecode1 = 0;
    int xPrice = 0;
    int yPrice = 0;
    int xDept1 = 0;
    int yDept1 = 0;
    int xMachine = 0;
    int yMahcine = 0;
    int xRound = 0;
    int yRound = 0;
    int xPackdate = 0;
    int yPackdate = 0;
    int xExp = 0;
    int yExp = 0;
    int xEmp1 = 0;
    int yEmp1 = 0;
    int xEmp2 = 0;
    int yEmp2 = 0;
    int xItemname2 = 0;
    int yItemname2 = 0;
    int xDept2 = 0;
    int yDept2 = 0;
    int xUsagecode2 = 0;
    int yUsagecode2 = 0;
    int xQrcode1 = 0;
    int yQrcode1 = 0;
    int xQrcode2 = 0;
    int yQrcode2 = 0;
    int xPackdate2 = 0;
    int yPackdate2 = 0;
    int xExp2 = 0;
    int yExp2 = 0;
    int xEmp3 = 0;
    int yEmp3 = 0;

    public void declarePrinter() {
        class DeclarePrinter extends AsyncTask<String, Void, String> {
            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try{
                        for(int i=0;i<data.size();i+=size) {
                            PRINTER.add( Integer.valueOf(data.get(i)).intValue() );
                            PRINTER_IP.add(data.get(i+1));
                            PRINTER_IP.add(data.get(i+2));
                            PRINTER_IP.add(data.get(i+3));
                            /*yItemname1=Integer.parseInt(data.get(i+3));
                            xUsagecode1=Integer.parseInt(data.get(i+4));
                            yUasgecode1=Integer.parseInt(data.get(i+5));
                            xPrice=Integer.parseInt(data.get(i+6));
                            yPrice=Integer.parseInt(data.get(i+7));
                            xDept1=Integer.parseInt(data.get(i+8));
                            yDept1=Integer.parseInt(data.get(i+9));
                            xMachine=Integer.parseInt(data.get(i+10));
                            yMahcine=Integer.parseInt(data.get(i+11));
                            xRound=Integer.parseInt(data.get(i+12));
                            yRound=Integer.parseInt(data.get(i+13));
                            xPackdate=Integer.parseInt(data.get(i+14));
                            yPackdate=Integer.parseInt(data.get(i+15));
                            xExp=Integer.parseInt(data.get(i+16));
                            yExp=Integer.parseInt(data.get(i+17));
                            xEmp1=Integer.parseInt(data.get(i+18));
                            yEmp1=Integer.parseInt(data.get(i+19));
                            xEmp2=Integer.parseInt(data.get(i+20));
                            yEmp2=Integer.parseInt(data.get(i+21));
                            xItemname2=Integer.parseInt(data.get(i+22));
                            yItemname2=Integer.parseInt(data.get(i+23));
                            xDept2=Integer.parseInt(data.get(i+24));
                            yDept2=Integer.parseInt(data.get(i+25));
                            xUsagecode2=Integer.parseInt(data.get(i+26));
                            yUsagecode2=Integer.parseInt(data.get(i+27));
                            xQrcode1=Integer.parseInt(data.get(i+28));
                            yQrcode1=Integer.parseInt(data.get(i+29));
                            xQrcode2=Integer.parseInt(data.get(i+30));
                            yQrcode2=Integer.parseInt(data.get(i+31));
                            xPackdate2=Integer.parseInt(data.get(i+32));
                            yPackdate2=Integer.parseInt(data.get(i+33));
                            xExp2=Integer.parseInt(data.get(i+34));
                            yExp2=Integer.parseInt(data.get(i+35));
                            xEmp3=Integer.parseInt(data.get(i+36));
                            yEmp3=Integer.parseInt(data.get(i+37));*/
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_label.php", data);
                return result;
            }

            // =========================================================================================
        }
        DeclarePrinter obj = new DeclarePrinter();
        obj.execute();
    }
    // =============================================================================================
    // -- Printing...
    // =============================================================================================
    final int DELAY_TIME = 0;
    PreviewSticker p = new PreviewSticker("");

    private void onPrint() throws Exception {

        final List<ModelSterileDetail> Label_1 = getItemLabel("1");
        final List<ModelSterileDetail> Label_2 = getItemLabel("2");
        final List<ModelSterileDetail> Label_3 = getItemLabel("3");
        final List<ModelSterileDetail> Label_4 = getItemLabel("4");

        //=========================================================================================

        if(!PRINT_ACTIVE){
            PRINT_ACTIVE = true;

            btn_print.setBackgroundResource(R.drawable.ic_print_grey);
            btn_print.setEnabled(false);

            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PRINT_ACTIVE = false;
                    btn_print.setBackgroundResource(R.drawable.ic_print_green);
                    btn_print.setEnabled(true);
                }
            }, 5000);
        }

        if(Label_1 != null && Label_1.size() > 0) {
            final Handler handler0 = new Handler();
            handler0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<ModelSterileDetail> DATA_MODEL = Label_1;
                        Iterator li = DATA_MODEL.iterator();
                        String xData = "";
                        while (li.hasNext()) {
                            ModelSterileDetail m = (ModelSterileDetail) li.next();
                            xData+= m.getItemname()+","+
                                    m.getPrice()+","+
                                    m.getDepName2()+","+
                                    m.getItemcode()+","+
                                    m.getUsageCode()+","+
                                    m.getMachineName()+","+
                                    m.getSterileRoundNumber()+","+
                                    m.getSterileDate()+","+
                                    m.getExpireDate()+","+
                                    m.getAgeDay()+","+
                                    m.getUsr_prepare()+","+
                                    m.getUsr_approve()+","+
                                    m.getUsr_sterile()+","+
                                    m.getQty()+","+
                                    m.getID()+";";
                        }
                        PSK.PrintSticker(CssdSterile.this,1,Label_1,PRINTER_IP,B_ID,Print);
                        updatePrintStatus( PSK.getData() );
                        PSK.setData("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0);
        }

        if(Label_2 != null && Label_2.size() > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        List<ModelSterileDetail> DATA_MODEL = Label_2;
                        Iterator li = DATA_MODEL.iterator();
                        String xData = "";
                        while (li.hasNext()) {
                            ModelSterileDetail m = (ModelSterileDetail) li.next();
                            xData+= m.getItemname()+","+
                                    m.getPrice()+","+
                                    m.getDepName2()+","+
                                    m.getItemcode()+","+
                                    m.getUsageCode()+","+
                                    m.getMachineName()+","+
                                    m.getSterileRoundNumber()+","+
                                    m.getSterileDate()+","+
                                    m.getExpireDate()+","+
                                    m.getAgeDay()+","+
                                    m.getUsr_prepare()+","+
                                    m.getUsr_approve()+","+
                                    m.getUsr_sterile()+","+
                                    m.getQty()+","+
                                    m.getID()+";";
                        }
                        PSK.PrintSticker(CssdSterile.this,2,Label_2,PRINTER_IP,B_ID,Print);
                        updatePrintStatus( PSK.getData() );
                        PSK.setData("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, DELAY_TIME);
        }

        if(Label_3 != null && Label_3.size() > 0) {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<ModelSterileDetail> DATA_MODEL = Label_3;
                        Iterator li = DATA_MODEL.iterator();
                        String xData = "";
                        while (li.hasNext()) {
                            ModelSterileDetail m = (ModelSterileDetail) li.next();
                            xData+= m.getItemname()+","+
                                    m.getPrice()+","+
                                    m.getDepName2()+","+
                                    m.getItemcode()+","+
                                    m.getUsageCode()+","+
                                    m.getMachineName()+","+
                                    m.getSterileRoundNumber()+","+
                                    m.getSterileDate()+","+
                                    m.getExpireDate()+","+
                                    m.getAgeDay()+","+
                                    m.getUsr_prepare()+","+
                                    m.getUsr_approve()+","+
                                    m.getUsr_sterile()+","+
                                    m.getQty()+","+
                                    m.getID()+";";
                        }
                        PSK.PrintSticker(CssdSterile.this,3,Label_3,PRINTER_IP,B_ID,Print);
                        updatePrintStatus( PSK.getData() );
                        PSK.setData("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, DELAY_TIME);
        }

        if(Label_4 != null && Label_4.size() > 0) {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<ModelSterileDetail> DATA_MODEL = Label_4;
                        Iterator li = DATA_MODEL.iterator();
                        String xData = "";
                        while (li.hasNext()) {
                            ModelSterileDetail m = (ModelSterileDetail) li.next();
                            xData+= m.getItemname()+","+
                                    m.getPrice()+","+
                                    m.getDepName2()+","+
                                    m.getItemcode()+","+
                                    m.getUsageCode()+","+
                                    m.getMachineName()+","+
                                    m.getSterileRoundNumber()+","+
                                    m.getSterileDate()+","+
                                    m.getExpireDate()+","+
                                    m.getAgeDay()+","+
                                    m.getUsr_prepare()+","+
                                    m.getUsr_approve()+","+
                                    m.getUsr_sterile()+","+
                                    m.getQty()+","+
                                    m.getID()+";";
                        }
                        PSK.PrintSticker(CssdSterile.this,4,Label_4,PRINTER_IP,B_ID,Print);
                        updatePrintStatus( PSK.getData() );
                        PSK.setData("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, DELAY_TIME);
        }
    }

    public void CheckUsageEms(final String DOC_NO) {
        class CheckUsageEms extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    String IsRemarkExpress = "";
                    String itemname = "";
                    String Shelflife = "";
                    String UsageCode = "";
                    String Cnt = "";
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        UsageCode = c.getString("UsageCode");
                        Cnt = c.getString("cnt");
                        condition1 = c.getString("condition1");
                        condition2 = c.getString("condition2");
                        condition3 = c.getString("condition3");
                        condition4 = c.getString("condition4");
                        condition5 = c.getString("condition5");
                    }
                    if (DIALOG_ACTIVE == true){
                        if (!condition1.equals("0") || !condition2.equals("0") || !condition3.equals("0") || !condition4.equals("0") || !condition5.equals("0")){
                            Intent intent = new Intent(CssdSterile.this, dialog_check_usage_count.class);
                            intent.putExtra("UsageCode", UsageCode);
                            intent.putExtra("cnt", Cnt);
                            intent.putExtra("DocNo",getDocNo());
                            intent.putExtra("B_ID",B_ID);
                            intent.putExtra("sel","1");
                            intent.putExtra("page","1");
                            intent.putExtra("condition1",condition1);
                            intent.putExtra("condition2",condition2);
                            intent.putExtra("condition3",condition3);
                            intent.putExtra("condition4",condition4);
                            intent.putExtra("condition5",condition5);
                            startActivity(intent);
                            DIALOG_ACTIVE = false;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DOC_NO",DOC_NO);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_usage_ems.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckUsageEms obj = new CheckUsageEms();
        obj.execute();
    }

    public void getQR(String DocNo,String xsel,String MacNo){
        Intent i = new Intent(CssdSterile.this, CheckQR_Approve.class);
        i.putExtra("DocNo", DocNo);
        i.putExtra("xSel", xsel);
        i.putExtra("MacNo", MacNo);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, 1035);
    }

    public void selectBasket(final String BasketCode, final EditText edt_basketcode) {
        Log.d("tog_isprint","selectBasket");
        class Basket extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                edt_basketcode.requestFocus();
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    //List<Model> list = new ArrayList<>();

                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);

                        if(c.getString("result").equals("A")) {

                            if(!c.getString("Mc_id").equals("-")){
                                Toast.makeText(CssdSterile.this, "ไม่สามารถเลือกตะกร้าได้ Basket Tag นี้อยู่ในเครื่องฆ่าเชื้อ "+c.getString("MachineName"), Toast.LENGTH_LONG).show();
                            }else{
                                basket_Code = c.getString("BasketCode");
                                basket_ID = c.getString("ID");
                                PairBasketBox_basket_Code.setText(c.getString("BasketName"));
                                cnt_basket_list.setText("");

                                Log.d("tog_cnt_basket_list","cnt_basket_list"+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size());
                                basket_dialog_w_list.setAdapter(new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_TO_PAIR,MAP_MODEL_IMPORT_WASH_DETAIL_SUB,4));
                                Log.d("ttest_pair","Key_map = "+MAP_MODEL_IMPORT_WASH_DETAIL_SUB);
                                Log.d("ttest_pair","containsKey_map = "+MAP_MODEL_IMPORT_WASH_DETAIL_SUB.containsKey(basket_Code.toLowerCase()));
                                Log.d("ttest_pair","basket_Code = "+basket_Code);

    //                            if(ConfigProgram.pair_basket_2){
    //
    //                            }

                                if(MAP_MODEL_IMPORT_WASH_DETAIL_SUB.containsKey(basket_Code.toLowerCase())){
                                    MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR = MAP_MODEL_IMPORT_WASH_DETAIL_SUB.get(basket_Code.toLowerCase());

                                    cnt_basket_list.setText(" [ "+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size()+" รายการ ]");
                                    Log.d("tog_cnt_basket_list","cnt_basket_list"+MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size());
                                    basket_dialog_list_basket.setAdapter(new ImportWashDetailAdapter(CssdSterile.this, MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR,5));
                                    int xn=0;
                                    for(int ii=0;ii<MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size();ii++){
                                        if(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(ii).getPrint_count()<=0){
                                            xn = xn+1;
                                        }
                                    }
                                    set_num_btn_print_bk(xn);
                                }else{
                                    set_num_btn_print_bk(0);
                                    basket_dialog_list_basket.setAdapter(null);
                                }
                            }

                        }else{
                            Toast.makeText(CssdSterile.this, "ไม่พบตะกร้า !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("BasketCode", BasketCode);

                String result = httpConnect.sendPostRequest(Url.URL + "sterile_basket/cssd_select_basket.php", data);

                return result;
            }

            // =========================================================================================
        }

        Basket obj = new Basket();
        obj.execute();
    }



    // =============================================================================================
    // -- Display Sterile Detail By Basket
    // =============================================================================================

    public void importWashDetailToBasket(String usageCode){

        onPairBasket(usageCode);

    }

    public boolean CheckProgramTypePairBasket(String programType){
        if(MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.size()>0){
            ModelImportWashDetail x = MODEL_IMPORT_WASH_DETAIL_GROUP_BASKET_IN_PAIR.get(0);
            if(!programType.equals(x.getI_program_type())){
                return false;
            }
        }

        return true;
    }

    public void onPairBasket(final String usageCode){
        onPairBasket(usageCode,1);
    }

    public void onPairBasket(final String item_data, final int isMode){
        //mode
        final int isUsageCode = 1;
        final int isWashDetail_id = 2;
        final int isItemCode = 3;


        final boolean mode = switch_mode.isChecked();
        final String basket = basket_ID;

        if(basket.equals("")){
            Toast.makeText(CssdSterile.this, "Scan QR Code Basket Tag !!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(item_data.equals("")){
            Toast.makeText(CssdSterile.this, "ไม่ได้เลือกรายการ !!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String p_basket = basket;

        class PairBasket extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("result").equals("A")) {

                            Log.d("tog_displayWashDetail","onPairBasket");
                            displayWashDetail(getSterileProcessId());
                        }
                        Toast.makeText(CssdSterile.this, c.getString("Message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_basket", p_basket);

                data.put("p_is_status", mode ? "-1" : "1");

                switch(isMode){
                    case isUsageCode :
                         if(data != null) {
                            data.put("p_UsageCode", item_data);
                         }
                         break;
                    case isWashDetail_id :
                        if(data != null) {
                            data.put("w_id", item_data);
                        }
                        break;
                    case isItemCode :
                        if(data != null) {
                            data.put("p_data", item_data);
                        }
                        break;
                }

                Log.d("ttest_pair_basket","data = "+data);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_pair_basket.php", data);

                Log.d("ttest_pair_basket","result = "+result);
                return result;
            }

            // =========================================================================================
        }

        PairBasket obj = new PairBasket();
        obj.execute();

    }

    public void onRemoveBasket(final String basket_detail_id){

        class RemoveBasket extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);

                        if(c.getString("result").equals("A")) {

                            Log.d("tog_displayWashDetail","onRemoveBasket");
                            displayWashDetail(getSterileProcessId());
                        }

                        Toast.makeText(CssdSterile.this, c.getString("Message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("basket_detail_id", basket_detail_id);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_remove_basket.php", data);
                Log.d("LFHKFS",data+"");
                Log.d("LFHKFS",result+"");

                return result;
            }

            // =========================================================================================
        }

        RemoveBasket obj = new RemoveBasket();
        obj.execute();

    }

    public void callCheckList(final String ID){
        Intent intent = new Intent(CssdSterile.this, CssdCheckList.class);
        intent.putExtra("userid", userid);
        intent.putExtra("B_ID", B_ID);
        intent.putExtra("IsAdmin", IsAdmin);
        intent.putExtra("ID", ID);
        intent.putExtra("Is_ById", true);
        startActivity(intent);
    }

    public void callCheckList(){
        Intent intent = new Intent(CssdSterile.this, CssdCheckList.class);
        intent.putExtra("userid", userid);
        intent.putExtra("B_ID", B_ID);
        intent.putExtra("IsAdmin", IsAdmin);
        intent.putExtra("Is_ById", false);
        startActivity(intent);
    }


    private void onPrintWash(final String W_id){
        Log.d("ttest_for_print","W_id = "+W_id);
//        final String ID = "2945,2946,2947,2948,2949,2950,2951,2952,2953,2954";
        final String ID = W_id;

        try {

            // Print
            class Print extends AsyncTask<String, Void, String> {

                // variable
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    List<ModelWashDetailForPrint> list = new ArrayList<>();

                    try {
                        JSONObject jsonObj = new JSONObject(s);
                        rs = jsonObj.getJSONArray(TAG_RESULTS);

                        for (int i = 0; i < rs.length(); i++) {

                            JSONObject c = rs.getJSONObject(i);

                            if (c.getString("result").equals("A")) {
                                list.add(
                                        new ModelWashDetailForPrint(
                                                c.getString("ID"),
                                                c.getString("itemcode"),
                                                c.getString("itemname"),
                                                c.getString("UsageCode"),
                                                c.getString("Packer"),
                                                c.getString("Age"),
                                                c.getString("MFG"),
                                                c.getString("EXP"),
                                                c.getString("CaseLabel"),
                                                c.getString("PrinterIP"),
                                                c.getString("UsageCount"),
                                                c.getString("IsCheckList")
                                        )
                                );
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }finally {

                    }

                    // -----------------------------------
                    // Print Multiple Id
                    // -----------------------------------
                    List<ModelWashDetailForPrint> list_2 ;
                    List<ModelWashDetailForPrint> list_3 ;

                    list_2 = getWashDetailLabelForPrint(list, "2");
                    list_3 = getWashDetailLabelForPrint(list, "3");

                    System.out.println("list_2.size() = " + list_2.size());
                    System.out.println("list_3.size() = " + list_3.size());

                    // Print
                    final PrintWash p = new PrintWash();
                    String p_data = "";

                    if(list_2 != null && list_2.size() > 0) {
                        String ip = list_2.get(0).getPrinterIP();
                        p_data += p.print(CssdSterile.this, 2, ip, list_2);
                    }

                    if(list_3 != null && list_3.size() > 0) {
                        String ip = list_3.get(0).getPrinterIP();
                        p_data += p.print(CssdSterile.this, 3, ip, list_3);
                    }

                    // Update Print Status
                    if(p_data != null && !p_data.equals("")){
                        updatePrintWashStatus(p_data);

                    }else{
                        displayWashDetail(getSterileProcessId());
                    }


                    // -----------------------------------

                }

                @Override
                protected String doInBackground(String... params) {
                    HashMap<String, String> data = new HashMap<String,String>();
                    data.put("ID", ID);
                    String result = null;

                    try {
                        result = httpConnect.sendPostRequest(Url.URL + "cssd_select_wash_detail_for_print.php", data);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    Log.d("ttest_for_print","data = "+data);
                    Log.d("ttest_for_print","re = "+result);
                    return result;
                }

                // =========================================================================================
            }

            Print obj = new Print();
            obj.execute();


        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    private List<ModelWashDetailForPrint> getWashDetailLabelForPrint(List<ModelWashDetailForPrint> list, String Label){

        List<ModelWashDetailForPrint> list_label = new ArrayList<>();

        // List Label portion
        for(int ix=0;ix<list.size();ix++){
            if(list.get(ix).getCaseLabel().equals(Label)) {
                list_label.add(
                        new ModelWashDetailForPrint(
                                list.get(ix).getID(),
                                list.get(ix).getItemcode(),
                                list.get(ix).getItemname(),
                                list.get(ix).getUsageCode(),
                                list.get(ix).getPacker(),
                                list.get(ix).getAge(),
                                list.get(ix).getMFG(),
                                list.get(ix).getEXP(),
                                list.get(ix).getCaseLabel(),
                                list.get(ix).getPrinterIP(),
                                list.get(ix).getUsageCount(),
                                list.get(ix).getIsCheckList()
                        )
                );
            }
        }

        return list_label;
    }

    public void updatePrintWashStatus(final String p_data) {

        Log.d("ttest_UpdatePrint","p_data = "+p_data );
        class UpdatePrintStatus extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                final String basket = basket_ID;

                if(!basket.equals("")){

                    for(int i=0;i<MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.size();i++){

                        Log.d("ttest_for_print","w_id"+MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).getI_id());
                        if(MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).isCheck()){

                            if(CheckProgramTypePairBasket(MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).getI_program_type())){
                                importWashDetailToBasket(
                                        MODEL_IMPORT_WASH_DETAIL_NOT_PRINT.get(i).getI_UsageCode()
                                );
                            }
                        }
                    }

                }

                displayWashDetail(getSterileProcessId());
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data.substring(0, p_data.length()-1));

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_detail_print_status.php", data);

                Log.d("ttest_UpdatePrint","p_data = "+data );

                return result;
            }

            // =========================================================================================
        }

        UpdatePrintStatus obj = new UpdatePrintStatus();
        obj.execute();
    }

    public void checkPacker(final String W_ID) {

        final String packer = packer_id;
        Log.d("ttest_checkPacker","checkPacker = "+packer);

        class Check extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            onPrintWash(W_ID);
                        }else{
                            Toast.makeText(CssdSterile.this, "ไม่พบรหัสพนักงาน !!", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
//                    focus();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("ID", W_ID);
                data.put("packer", packer);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_packer.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("ttest_packer","check_packer data = "+data);
                Log.d("ttest_packer","check_packer re = "+result);

                return result;
            }

            // =========================================================================================
        }

        Check obj = new Check();
        obj.execute();
    }

    public void Checkuser_packer(final String qr_code, final Dialog dialog_qr, final Dialog dialog) {
        class Checkuser extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {

                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            packer.setText(c.getString("data"));
                            packer_id = qr_code;
                            dialog.show();
                        }else{
                            packer_id = "";
                            Toast.makeText(CssdSterile.this, "สิทธิ์ผู้ใช้งานไม่สามารถเข้าถึงส่วนนี้ได้ !!", Toast.LENGTH_SHORT).show();
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog_qr.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_user_code",params[0]);
                String result = httpConnect.sendPostRequest(getUrl.xUrl+"cssd_check_user.php",data);
                Log.d("tog_Checkuser", result);
                return  result;
            }
        }
        Checkuser ru = new Checkuser();
        ru.execute( qr_code);
    }

    private void add_basket(){

        class add_basket extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {

                            final Dialog dialog_add_bk = new Dialog(CssdSterile.this, R.style.DialogCustomTheme);

                            dialog_add_bk.requestWindowFeature(Window.FEATURE_NO_TITLE);

                            dialog_add_bk.setContentView(R.layout.new_basket);

                            dialog_add_bk.setCancelable(false);

                            dialog_add_bk.setTitle("");

                            dialog_add_bk.show();

                            final TextView textBkcode = (TextView) dialog_add_bk.findViewById(R.id.textBkcode);
                            textBkcode.setText(c.getString("bkcode"));

                            final EditText BkName = (EditText) dialog_add_bk.findViewById(R.id.BkName);

                            Button buttonSave = (Button) dialog_add_bk.findViewById(R.id.saveBk);
                            buttonSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(BkName.getText().toString().length()<=0){

                                    }else{
                                        new_basket(textBkcode.getText().toString(),BkName.getText().toString(),dialog_add_bk);
                                    }
                                }
                            });

                            Button buttonCancel = (Button) dialog_add_bk.findViewById(R.id.cancelBk);
                            buttonCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog_add_bk.dismiss();
                                    add_basket_cnt = true;
                                }
                            });

                        }else{
                            Toast.makeText(CssdSterile.this, "Error !!", Toast.LENGTH_SHORT).show();
                        }
//                        add_basket_cnt = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
//                    focus();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_new_basket.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("ttest_new_bk","new bk result = "+result);
                return result;
            }

            // =========================================================================================
        }

        add_basket obj = new add_basket();
        obj.execute();
    }

    private void new_basket(final String textBkcode,final String BkName, final Dialog dialog_add_bk){
        class new_basket extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            if(c.getString("re").equals("0")){
                                Toast.makeText(CssdSterile.this, "ชื่อซ้ำกับตะกร้าอื่น ลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CssdSterile.this, "เพิ่มตะกร้าใหมสำเร็จ", Toast.LENGTH_SHORT).show();
                                dialog_add_bk.dismiss();
                                add_basket_cnt = true;
                            }
                        }else{
                            Toast.makeText(CssdSterile.this, "Error !!", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
//                    focus();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("BKCODE", textBkcode);
                data.put("BKNAME", BkName);
                data.put("BKTYPE", "1");

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_new_basket.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("ttest_new_bk","new bk result = "+result);
                return result;
            }

            // =========================================================================================
        }

        new_basket obj = new new_basket();
        obj.execute();
    }

    public boolean chk_mac(){

        Log.d("tog_SterileProgramID","STERILE_MACHINE_NUMBER_ACTIVE = "+ STERILE_MACHINE_NUMBER_ACTIVE );

        // Check Sterile Process
        if (userid == null){
            Toast.makeText(CssdSterile.this, "ไม่มีผู้ทำรายการ!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check Sterile Process
        if (STERILE_PROCESS_NUMBER_ACTIVE == 0){
            Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือกวิธีฆ่าเชื้อ!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check Machine Active
        if (STERILE_MACHINE_NUMBER_ACTIVE == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(CssdSterile.this);
            builder.setTitle(Cons.TITLE);
            builder.setIcon(R.drawable.pose_favicon_2x);
            builder.setMessage("ยังไม่ได้เลือกเครื่องฆ่าเชื้อ!!");
            builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();
            //Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือกเครื่องฆ่าเชื้อ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check Machine Active (Run)
        if(!checkMachineActive()) {
            Toast.makeText(CssdSterile.this, "ไม่สามารถเพิ่มได้ เนื่องจากเครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void set_num_btn_print_bk(int n){
        btn_print_bk.setText(n+"");
    }

    Dialog dialog_pack;

    private TextView txt_packingmat;
    private TextView txt_item_code;
    private TextView txt_item_name;

    public void wash_detail_management_pack(final String w_id, String itemname,String usagecode) {

        dialog_pack = new Dialog(CssdSterile.this, R.style.DialogCustomTheme);

        dialog_pack.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_pack.setContentView(R.layout.activity_cssd_wash_detail_management_pack);

        dialog_pack.setCancelable(false);

        dialog_pack.setTitle("");

        txt_item_code = (TextView) dialog_pack.findViewById(R.id.txt_item_code);
        txt_item_name = (TextView) dialog_pack.findViewById(R.id.txt_item_name);
        txt_packingmat = (TextView) dialog_pack.findViewById(R.id.txt_packingmat);
        ImageView image_save = (ImageView) dialog_pack.findViewById(R.id.image_save);
        ImageView imageBack_pack = (ImageView) dialog_pack.findViewById(R.id.imageBack);

        txt_item_code.setText(usagecode);
        txt_item_name.setText(itemname+" : ");

        imageBack_pack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dialog_pack.dismiss();
            }
        });

        image_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(txt_packingmat.getContentDescription() == null){
                    Toast.makeText(CssdSterile.this, "ยังไม่ได้เลือกรูปแบบการห่อ" , Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(CssdSterile.this, txt_item_code.getText().toString()+"-----"+txt_packingmat.getContentDescription().toString() , Toast.LENGTH_SHORT).show();
                onUpdatePacking(w_id, txt_packingmat.getContentDescription().toString());

            }
        });

        txt_packingmat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("packingmat");
            }
        });

        dialog_pack.show();

    }

    public void onUpdatePacking(final String p_data, final String p_PackingMatID) {

        class RemoveSterileDetail extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdSterile.this);

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }else{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
                dialog_pack.dismiss();
                displayWashDetail(getSterileProcessId());
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);
                data.put("p_PackingMatID", p_PackingMatID);


                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_detail_packing.php", data);

                System.out.println("URL = " + result);
                Log.d("tog_packing","data = "+data);
                Log.d("tog_packing","result = "+result);

                return result;
            }

            // =========================================================================================
        }

        RemoveSterileDetail obj = new RemoveSterileDetail();
        obj.execute();
    }

    // Dropdown list
    private void openDropDown(String data){
        Intent i = new Intent(CssdSterile.this, MasterDropdown.class);
        i.putExtra("data", data);

        Log.d("tog_MD",data+"---"+Master.getResult(data));
        startActivityForResult(i, Master.getResult(data));
    }

    public int dp_to_px(int dp){
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public String getSerialNumber() {
        String serialNumber = "";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ro.serialno");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "sys.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = Build.SERIAL;
            if (serialNumber.equals(""))
                serialNumber = null;
        } catch (Exception e) {
            e.printStackTrace();
            serialNumber = null;
        }
        Log.d("ttest_serialNumber","serialNumber--"+serialNumber);
        return serialNumber;
    }

    public void  Update_S_id_IN_Baskettag(final String W_id, final int mode){
        //mode 1=add s_id to basket , 2=remove s_id from basket , 3=set basket is empty

        Log.d("tog_S_id_IN_BKT","W_id = "+W_id+" ---- mode ="+mode);

        class UpdateBaskettag extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("W_id", W_id);
                data.put("mode", mode+"");

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "sterile_basket/Update_Sterile_id_in_Baskettag.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("tog_S_id_IN_BKT","data = "+data);
                Log.d("tog_S_id_IN_BKT","result = "+result);
                return result;
            }

            // =========================================================================================
        }

        UpdateBaskettag obj = new UpdateBaskettag();
        obj.execute();
    }

}

