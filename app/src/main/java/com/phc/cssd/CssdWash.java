package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.CircularToggle;
import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.date.CountDownTime;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.BasketAdapter;
import com.phc.cssd.adapter.ImportSendSterileDetailAdapter;
import com.phc.cssd.adapter.ImportSendSterileDetailBigSizeAdapter;
import com.phc.cssd.adapter.ImportSendSterileDetailGridViewAdapter;
import com.phc.cssd.adapter.WashDetailAdapter;
import com.phc.cssd.adapter.WashDetailBasketAdapter;
import com.phc.cssd.adapter.WashDetailBigSizeAdapter;
import com.phc.cssd.adapter.WashDetailGridViewAdapter;
import com.phc.cssd.config.CssdSetting;
import com.phc.cssd.config.Setting;
import com.phc.cssd.data.Master;
import com.phc.cssd.loaner.LoanerMainActivity;
import com.phc.cssd.model.Model;
import com.phc.cssd.model.ModelImportWashDetail;
import com.phc.cssd.model.ModelSendSterileDetail;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.model.ModelWashProcess;
import com.phc.cssd.model.ModelWash;
import com.phc.cssd.model.ModelWashDetail;
import com.phc.cssd.model.ModelWashMachine;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdWash extends AppCompatActivity {

    String condition1 = "";
    String condition2 = "";
    String condition3 = "";
    String condition4 = "";
    String condition5 = "";
    String user_name = "";
    int cnt = 0;
    Boolean WASH = true;
    private TextView occupancy_rate;
    private String OccupancyRate_Text_Wash;
    private int countput = 0;
    private String QtyWash = "";
    // =================================================
    // Http
    // =================================================
    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    // =================================================
    // Session Variable
    // =================================================
    private String userid = null;
    private boolean IsAdmin = false;
    private String B_ID = null;
    private Button btn_export;
    private Button bnt_loaner;
    // =================================================
    // Widget Variable
    // =================================================
    private ImageView imageBack;
    private ImageView imv_import_all_send_sterile;
    private ImageView imv_remove_all_wash;
    private ImageView imv_basket;
    private Button btn_open_send_sterile;
    private LinearLayout main;
    private TextView txt_doc_no;
    private TextView txt_doc_date;
    private TextView txt_machine;
    private TextView txt_round;
    private TextView txt_finish;
    private TextView txt_test_program;
    private TextView txt_wash_type;
    private TextView txt_label_wash_type;
    private TextView edit_wash_type;
    private TextView txt_wash_process;
    private TextView txt_caption;
    private SingleSelectToggleGroup group_choices;
    private ListView list_item_detail;
    private ListView list_wash_detail;
    private ListView list_import_send_sterile;
    private GridView grid_import_send_sterile;
    private GridView grid_wash_detail;
    private boolean DIALOG_ACTIVE = false;
    // Process
    private ImageView imv_pc_1;
    private ImageView imv_pc_2;
    private ImageView imv_pc_3;
    private ImageView imv_pc_4;
    private ImageView imv_pc_5;
    private ImageView imv_pc_6;
    private ImageView imv_pc_7;
    private ImageView imv_pc_8;
    private ImageView imv_pc_9;
    private ImageView imv_pc_10;
    // Machine
    private ImageView imv_mc_1;
    private ImageView imv_mc_2;
    private ImageView imv_mc_3;
    private ImageView imv_mc_4;
    private ImageView imv_mc_5;
    private ImageView imv_mc_6;
    private ImageView imv_mc_7;
    private ImageView imv_mc_8;
    private ImageView imv_mc_9;
    private ImageView imv_mc_10;
    // Machine Time
    private TextView txt_mc_1;
    private TextView txt_mc_2;
    private TextView txt_mc_3;
    private TextView txt_mc_4;
    private TextView txt_mc_5;
    private TextView txt_mc_6;
    private TextView txt_mc_7;
    private TextView txt_mc_8;
    private TextView txt_mc_9;
    private TextView txt_mc_10;
    // Machine Name
    private TextView txt_mn_1;
    private TextView txt_mn_2;
    private TextView txt_mn_3;
    private TextView txt_mn_4;
    private TextView txt_mn_5;
    private TextView txt_mn_6;
    private TextView txt_mn_7;
    private TextView txt_mn_8;
    private TextView txt_mn_9;
    private TextView txt_mn_10;
    private Button btn_complete;
    private Button btn_add;
    private ImageView bt_delsterile;

    private EditText scan_basket;
    // =================================================
    // Local Variable
    // =================================================
    private int DISPLAY_MODE = 0;
    private final int i = 0;
    private final int delay = 1000;
    static final int CONNECT_TIME = 3000;
    private final int MACHINE_ACTIVE = 0;
    private final int MACHINE_NORMAL = 50;
    private final int MACHINE_RUN_TIME_ACTIVE = -10;
    private final int MACHINE_RUN_TIME_NORMAL = 40;
    private final int MACHINE_NAME_ACTIVE = 12;
    private final int MACHINE_NAME_NORMAL = 62;
    private final int MACHINE_NAME_RUN_TIME_ACTIVE = 40;
    private final int MACHINE_NAME_RUN_TIME_NORMAL = 90;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private int r = com.phc.cssd.R.color.colorRed;
    private int g = com.phc.cssd.R.color.colorGreen;
    private int b = com.phc.cssd.R.color.colorBlue;
    // Wash Program
    private String WASH_PROCESS_ID_1 = null;
    private String WASH_PROCESS_ID_2 = null;
    private String WASH_PROCESS_ID_3 = null;
    private String WASH_PROCESS_ID_4 = null;
    private String WASH_PROCESS_ID_5 = null;
    private String WASH_PROCESS_ID_6 = null;
    private String WASH_PROCESS_ID_7 = null;
    private String WASH_PROCESS_ID_8 = null;
    private String WASH_PROCESS_ID_9 = null;
    private String WASH_PROCESS_ID_10 = null;
    // Machine Active
    private int WASH_MACHINE_NUMBER_ACTIVE = 0;
    // Mode Program Active
    private int WASH_PROCESS_NUMBER_ACTIVE = 0;
    private List<ModelSendSterileDetail> MODEL_SEND_STERILE_DETAIL = null;
    HashMap<String,List<ModelSendSterileDetail>> MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET = new HashMap<String,List<ModelSendSterileDetail>>();
    private List<ModelWashDetail> MODEL_WASH_DETAIL = null;
    private List<ModelWash> MODEL_WASH = null;
    private List<Model> MODEL;
    // Wash Machine
    private List<ModelWashMachine> MACHINE_1;
    private List<ModelWashMachine> MACHINE_2;
    private List<ModelWashMachine> MACHINE_3;
    private List<ModelWashMachine> MACHINE_4;
    private List<ModelWashMachine> MACHINE_5;
    private List<ModelWashMachine> MACHINE_6;
    private List<ModelWashMachine> MACHINE_7;
    private List<ModelWashMachine> MACHINE_8;
    private List<ModelWashMachine> MACHINE_9;
    private List<ModelWashMachine> MACHINE_10;
    // Mode Wash Program
    private List<ModelWashProcess> MODEL_WASH_PROGRAM = null;
    // Wash Program
    private List<ModelWashProcess> WASH_PROCESS_1;
    private List<ModelWashProcess> WASH_PROCESS_2;
    private List<ModelWashProcess> WASH_PROCESS_3;
    private List<ModelWashProcess> WASH_PROCESS_4;
    private List<ModelWashProcess> WASH_PROCESS_5;
    private List<ModelWashProcess> WASH_PROCESS_6;
    private List<ModelWashProcess> WASH_PROCESS_7;
    private List<ModelWashProcess> WASH_PROCESS_8;
    private List<ModelWashProcess> WASH_PROCESS_9;
    private List<ModelWashProcess> WASH_PROCESS_10;
    // Handler
    private Handler handler_1 = new Handler();
    private Handler handler_2 = new Handler();
    private Handler handler_3 = new Handler();
    private Handler handler_4 = new Handler();
    private Handler handler_5 = new Handler();
    private Handler handler_6 = new Handler();
    private Handler handler_7 = new Handler();
    private Handler handler_8 = new Handler();
    private Handler handler_9 = new Handler();
    private Handler handler_10 = new Handler();
    // Runnable
    private Runnable runnable_1;
    private Runnable runnable_2;
    private Runnable runnable_3;
    private Runnable runnable_4;
    private Runnable runnable_5;
    private Runnable runnable_6;
    private Runnable runnable_7;
    private Runnable runnable_8;
    private Runnable runnable_9;
    private Runnable runnable_10;
    // Config
    private boolean IsQR = true;
    private boolean IsQR_Wash_Type = true;
    private boolean IsUsedProcessTimeByWashType = true;
    private boolean IsShowBasket = false;

    float x1,x2,y1,y2;

    String EmpCode;
    // =============================================================================================
    public void onDestroy() {
        super.onDestroy();
        clearHandler();
    }
    private void clearHandler(){
        handler_1.removeCallbacks(runnable_1);
        handler_2.removeCallbacks(runnable_2);
        handler_3.removeCallbacks(runnable_3);
        handler_4.removeCallbacks(runnable_4);
        handler_5.removeCallbacks(runnable_5);
        handler_6.removeCallbacks(runnable_6);
        handler_7.removeCallbacks(runnable_7);
        handler_8.removeCallbacks(runnable_8);
        handler_9.removeCallbacks(runnable_9);
        handler_10.removeCallbacks(runnable_10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_wash);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byIntent();

        byWidget();

        byConfig();

        //defaultTabMachine();

        //generateMachine();
    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                Log.d("KFDJDL",x1+"");
                Log.d("KFDJDL",y1+"");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("KFDJDL",x2+"");
                Log.d("KFDJDL",y2+"");
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2){
                    Intent i = new Intent(CssdWash.this, SendSterile_MainActivity.class);
                    i.putExtra("userid", userid);
                    i.putExtra("user_name", user_name);
                    i.putExtra("IsAdmin", IsAdmin);
                    i.putExtra("EmpCode", EmpCode);
                    Log.d("FKJDHJKDH",EmpCode+"");
                    i.putExtra("B_ID", B_ID);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    clearForm();
                    clearMachine(1);
                    clearMachine(2);
                    clearMachine(3);
                    clearMachine(4);
                    clearMachine(5);
                    clearMachine(6);
                    clearMachine(7);
                    clearMachine(8);
                    clearMachine(9);
                    clearMachine(10);
                    handler_1 . removeCallbacks(runnable_1);
                    handler_2 . removeCallbacks(runnable_2);
                    handler_3 . removeCallbacks(runnable_3);
                    handler_4 . removeCallbacks(runnable_4);
                    handler_5 . removeCallbacks(runnable_5);
                    handler_6 . removeCallbacks(runnable_6);
                    handler_7 . removeCallbacks(runnable_7);
                    handler_8 . removeCallbacks(runnable_8);
                    handler_9 . removeCallbacks(runnable_9);
                    handler_10 . removeCallbacks(runnable_10);
                    defaultTabMachine();
                    hideMachineAll();
                    list_import_send_sterile.setAdapter(null);
                }else if (x1 > x2){
                }
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayImportSendSterile("0");
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        user_name = intent.getStringExtra("user_name");
        IsAdmin = intent.getBooleanExtra("IsAdmin", false);
        B_ID = intent.getStringExtra("B_ID");
        EmpCode = intent.getStringExtra("EmpCode");
    }

    private void byConfig(){
        Setting s = CssdSetting.getSetting();

        IsShowBasket = s.isShowBasket();

//        imv_basket.setVisibility(IsShowBasket ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        generateProgram();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void byWidget() {

        main = (LinearLayout) findViewById(R.id.main);
        bnt_loaner = (Button) findViewById(R.id.bnt_loaner);
        bnt_loaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CssdWash.this, LoanerMainActivity.class);
                startActivity(it);
                clearForm();
                clearMachine(1);
                clearMachine(2);
                clearMachine(3);
                clearMachine(4);
                clearMachine(5);
                clearMachine(6);
                clearMachine(7);
                clearMachine(8);
                clearMachine(9);
                clearMachine(10);
                handler_1 . removeCallbacks(runnable_1);
                handler_2 . removeCallbacks(runnable_2);
                handler_3 . removeCallbacks(runnable_3);
                handler_4 . removeCallbacks(runnable_4);
                handler_5 . removeCallbacks(runnable_5);
                handler_6 . removeCallbacks(runnable_6);
                handler_7 . removeCallbacks(runnable_7);
                handler_8 . removeCallbacks(runnable_8);
                handler_9 . removeCallbacks(runnable_9);
                handler_10 . removeCallbacks(runnable_10);
                defaultTabMachine();
                hideMachineAll();
                list_import_send_sterile . setAdapter(null);
            }
        });
        imv_import_all_send_sterile = (ImageView) findViewById(R.id.imv_import_all_send_sterile);
        imv_remove_all_wash = (ImageView) findViewById(R.id.imv_remove_all_wash);
        imv_basket = (ImageView) findViewById(R.id.imv_basket);
        btn_open_send_sterile = (Button) findViewById(R.id.btn_open_send_sterile);

        list_item_detail = (ListView) findViewById(R.id.list_item_detail);
        list_wash_detail = (ListView) findViewById(R.id.list_wash_detail);
        list_import_send_sterile = (ListView) findViewById(R.id.list_import_send_sterile);

        grid_wash_detail = (GridView) findViewById(R.id.grid_wash_detail);
        grid_import_send_sterile = (GridView) findViewById(R.id.grid_import_send_sterile);

        txt_doc_no = (TextView) findViewById(R.id.txt_doc_no);
        txt_doc_date = (TextView) findViewById(R.id.txt_doc_date);
        txt_machine = (TextView) findViewById(R.id.txt_machine);
        txt_round = (TextView) findViewById(R.id.txt_round);
        txt_finish = (TextView) findViewById(R.id.txt_finish);
        bt_delsterile = (ImageView) findViewById(R.id.bt_delsterile);

        txt_wash_process = (TextView) findViewById(R.id.txt_wash_process);

        imv_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBasketManagement();
            }
        });

        //basket
        // =========================================================================================

        scan_basket = (EditText) findViewById(R.id.scan_basket);
        scan_basket.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                Log.d("ttest_scan","keyCode = "+keyCode);
                String txt = scan_basket.getText().toString();
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            Log.d("ttest_scan","txt = "+txt);
                            if(MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.containsKey(txt)&&(!txt.equals(""))){//&&chk_mac()

                                final List<ModelSendSterileDetail> MODEL_SUB = MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.get(txt);

                                String UsageCode = MODEL_SUB.get(0).getUsageCode();

                                if(MODEL_SUB.size()>1){
                                    for(int i=1;i<MODEL_SUB.size();i++){
                                        UsageCode += "@"+MODEL_SUB.get(i).getUsageCode();
                                    }
                                }

                                importSendSterileDetail( UsageCode, MODEL_SUB.get(0).getI_program_id(), MODEL_SUB.get(0).getI_program());

                            }else{
                                Toast.makeText(CssdWash.this, "ไม่พบตะกร้า ("+txt+")!!", Toast.LENGTH_SHORT).show();
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

        // =========================================================================================

        btn_open_send_sterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSendSterile();
            }
        });

        imv_import_all_send_sterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countput == 0){
                    countput ++;
                    onImport();
                }
            }
        });

        imv_remove_all_wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMachineActive()) {
                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdWash.this);
                    quitDialog.setTitle(Cons.TITLE);
                    quitDialog.setIcon(R.drawable.pose_favicon_2x);
                    quitDialog.setMessage(Cons.CONFIRM_REMOVE);

                    quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onRemoveAllWashDetail();
                        }
                    });

                    quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    quitDialog.show();

                } else {
                    Toast.makeText(CssdWash.this, "ไม่สามารถลบรายการได้!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // =========================================================================================
        // machine
        btn_complete = (Button) findViewById(R.id.btn_complete);
        btn_add = (Button) findViewById(R.id.btn_add);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateStartMachine();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CssdWash.this, ImportSendSterile.class);
                i.putExtra("data", Master.open_send_sterile);
                i.putExtra("B_ID", B_ID);
                startActivityForResult(i, Master.open_send_sterile);
            }
        });

        txt_test_program = (TextView) findViewById(R.id.txt_test_program);
        txt_test_program.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checkMachineActive()) {
                    openDropDown("wash_test_program", getProcessId());
                } else {
                    Toast.makeText(CssdWash.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        occupancy_rate = (TextView) findViewById(R.id.occupancy_rate);
        occupancy_rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkMachineActive()) {
                    if (!txt_doc_no.getText().toString().equals("")) {
                        openDropDown1("wash_test_program", getProcessId());
                    }else {
                        Toast.makeText(CssdWash.this, "กรุณาเพิ่มรายการก่อนทำรายการ !!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CssdWash.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_label_wash_type = (TextView) findViewById(R.id.txt_label_wash_type);
        txt_wash_type = (TextView) findViewById(R.id.txt_wash_type);
        edit_wash_type = (TextView) findViewById(R.id.edit_wash_type);

        // Check Used QR_Wash_Type
        if (IsQR_Wash_Type){

            txt_wash_type.setVisibility(View.GONE);
            txt_label_wash_type.setVisibility(View.GONE);

            edit_wash_type.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //String docNo = getDocNo();

                    //if(docNo == null || docNo.equals("-") || docNo.equals("")) {

                    if (checkMachineActive()) {
                        openQR("wash_type");
                    } else {
                        Toast.makeText(CssdWash.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }else {
            edit_wash_type.setVisibility(View.GONE);
            txt_wash_type.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (checkMachineActive()) {
                        openDropDown("wash_type", getProcessId());
                    } else {
                        Toast.makeText(CssdWash.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // =========================================================================================
        imv_pc_1 = (ImageView) findViewById(R.id.imv_pc_1);
        imv_pc_2 = (ImageView) findViewById(R.id.imv_pc_2);
        imv_pc_3 = (ImageView) findViewById(R.id.imv_pc_3);
        imv_pc_4 = (ImageView) findViewById(R.id.imv_pc_4);
        imv_pc_5 = (ImageView) findViewById(R.id.imv_pc_5);
        imv_pc_6 = (ImageView) findViewById(R.id.imv_pc_6);
        imv_pc_7 = (ImageView) findViewById(R.id.imv_pc_7);
        imv_pc_8 = (ImageView) findViewById(R.id.imv_pc_8);
        imv_pc_9 = (ImageView) findViewById(R.id.imv_pc_9);
        imv_pc_10 = (ImageView) findViewById(R.id.imv_pc_10);

        imv_pc_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(1, WASH_PROCESS_ID_1);
            }
        });

        imv_pc_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(2, WASH_PROCESS_ID_2);
            }
        });

        imv_pc_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(3, WASH_PROCESS_ID_3);
            }
        });

        imv_pc_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(4, WASH_PROCESS_ID_4);
            }
        });

        imv_pc_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(5, WASH_PROCESS_ID_5);
            }
        });

        imv_pc_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(6, WASH_PROCESS_ID_6);
            }
        });

        imv_pc_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(7, WASH_PROCESS_ID_7);
            }
        });

        imv_pc_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(8, WASH_PROCESS_ID_8);
            }
        });

        imv_pc_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(9, WASH_PROCESS_ID_9);
            }
        });

        imv_pc_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProgram(10, WASH_PROCESS_ID_10);
            }
        });

        // =========================================================================================

        // Text Time Machine
        txt_mc_1 = (TextView)findViewById(R.id.txt_mc_1);
        txt_mc_2 = (TextView)findViewById(R.id.txt_mc_2);
        txt_mc_3 = (TextView)findViewById(R.id.txt_mc_3);
        txt_mc_4 = (TextView)findViewById(R.id.txt_mc_4);
        txt_mc_5 = (TextView)findViewById(R.id.txt_mc_5);
        txt_mc_6 = (TextView)findViewById(R.id.txt_mc_6);
        txt_mc_7 = (TextView)findViewById(R.id.txt_mc_7);
        txt_mc_8 = (TextView)findViewById(R.id.txt_mc_8);
        txt_mc_9 = (TextView)findViewById(R.id.txt_mc_9);
        txt_mc_10 = (TextView)findViewById(R.id.txt_mc_10);

        // Button Machine
        imv_mc_1 = (ImageView) findViewById(R.id.imv_mc_1);
        imv_mc_2 = (ImageView) findViewById(R.id.imv_mc_2);
        imv_mc_3 = (ImageView) findViewById(R.id.imv_mc_3);
        imv_mc_4 = (ImageView) findViewById(R.id.imv_mc_4);
        imv_mc_5 = (ImageView) findViewById(R.id.imv_mc_5);
        imv_mc_6 = (ImageView) findViewById(R.id.imv_mc_6);
        imv_mc_7 = (ImageView) findViewById(R.id.imv_mc_7);
        imv_mc_8 = (ImageView) findViewById(R.id.imv_mc_8);
        imv_mc_9 = (ImageView) findViewById(R.id.imv_mc_9);
        imv_mc_10 = (ImageView) findViewById(R.id.imv_mc_10);

        // Text Menu Name
        txt_mn_1 = (TextView)findViewById(R.id.txt_mn_1);
        txt_mn_2 = (TextView)findViewById(R.id.txt_mn_2);
        txt_mn_3 = (TextView)findViewById(R.id.txt_mn_3);
        txt_mn_4 = (TextView)findViewById(R.id.txt_mn_4);
        txt_mn_5 = (TextView)findViewById(R.id.txt_mn_5);
        txt_mn_6 = (TextView)findViewById(R.id.txt_mn_6);
        txt_mn_7 = (TextView)findViewById(R.id.txt_mn_7);
        txt_mn_8 = (TextView)findViewById(R.id.txt_mn_8);
        txt_mn_9 = (TextView)findViewById(R.id.txt_mn_9);
        txt_mn_10 = (TextView)findViewById(R.id.txt_mn_10);

        // On Click
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

        imv_mc_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(6);
            }
        });

        imv_mc_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(7);
            }
        });

        imv_mc_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(8);
            }
        });

        imv_mc_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(9);
            }
        });

        imv_mc_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMachine(10);
            }
        });

        // On Long Click
        imv_mc_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(1);

                return true;
            }
        });

        imv_mc_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(2);

                return true;
            }
        });

        imv_mc_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(3);
                return true;
            }
        });

        imv_mc_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(4);

                return true;
            }
        });

        imv_mc_5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(5);

                return true;
            }
        });

        imv_mc_6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(6);

                return true;
            }
        });

        imv_mc_7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(7);

                return true;
            }
        });

        imv_mc_8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(8);
                return true;
            }
        });

        imv_mc_9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(9);

                return true;
            }
        });

        imv_mc_10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickMachine(10);

                return true;
            }
        });

        group_choices = (SingleSelectToggleGroup) findViewById(R.id.group_choices);
        group_choices.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {

                //System.out.println(checkedId);

                CircularToggle c = (CircularToggle) findViewById(checkedId);

                String txt = c.getText().toString();

                if(txt.equals("S")){
                    DISPLAY_MODE = 3;
                }else if(txt.equals("M")){
                    DISPLAY_MODE = 2;
                }else if(txt.equals("L")){
                    DISPLAY_MODE = 1;
                }else{
                    DISPLAY_MODE = 0;
                    return;
                }

                displayMode();

            }
        });

        // =========================================================================================
        txt_caption = (TextView)findViewById(R.id.txt_caption);
        txt_caption.bringToFront();
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHandler(); finish();
            }
        });
        imageBack.bringToFront();
        group_choices.bringToFront();
        bt_delsterile.bringToFront();
        btn_export = (Button) findViewById(R.id.btn_export);
        btn_export.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectQtyDocNO();
            }
        });

    }

    private void displayMode(){

        list_wash_detail.setVisibility(!(DISPLAY_MODE == 3) ? View.VISIBLE : View.GONE);
        list_import_send_sterile.setVisibility(!(DISPLAY_MODE == 3) ? View.VISIBLE : View.GONE);
        grid_wash_detail.setVisibility(DISPLAY_MODE == 3 ? View.VISIBLE : View.GONE);
        grid_import_send_sterile.setVisibility(DISPLAY_MODE == 3 ? View.VISIBLE : View.GONE);

        class DisplayMode extends AsyncTask<String, Void, String> {

            //private ProgressDialog dialog = new ProgressDialog(CssdWash.this);

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

//                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
//                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(MODEL_SEND_STERILE_DETAIL != null && MODEL_SEND_STERILE_DETAIL.size() > 0){
                    displayImportSendSterile("1");
                }

                if(MODEL_WASH_DETAIL != null && MODEL_WASH_DETAIL.size() > 0){
                    displayWashDetail(getDocNo());
                }

//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }

            }

            @Override
            protected String doInBackground(String... params) {
                return "result";
            }

        }

        DisplayMode obj = new DisplayMode();
        obj.execute();
    }

    private void onClickProgram(final int process_active, final String sterile_process_id){

        WASH_PROCESS_NUMBER_ACTIVE = process_active;

        // Clear Handler
        clearHandler();

        //defaultTabMachine();

        setProcessBackgroundColor();

        generateMachine(sterile_process_id);

        displayImportSendSterile("1");
    }

    private void newWashProcess(String ID, String WashingProcess, String IsCancel, String ProgramR, String IsProgramTest, int index){

        List<ModelWashProcess> list = new ArrayList<>();

        list.add(
                new ModelWashProcess(
                        ID,
                        WashingProcess,
                        IsCancel,
                        ProgramR,
                        IsProgramTest,
                        index
                )
        );

        switch(index) {
            case 1 :
                WASH_PROCESS_1 = list;
                break;
            case 2 :
                WASH_PROCESS_2 = list;
                break;
            case 3 :
                WASH_PROCESS_3 = list;
                break;
            case 4 :
                WASH_PROCESS_4 = list;
                break;
            case 5 :
                WASH_PROCESS_5 = list;
                break;
            case 6 :
                WASH_PROCESS_6 = list;
                break;
            case 7 :
                WASH_PROCESS_7 = list;
                break;
            case 8 :
                WASH_PROCESS_8 = list;
                break;
            case 9 :
                WASH_PROCESS_9 = list;
                break;
            case 10 :
                WASH_PROCESS_10 = list;
                break;
            default: return;
        }
    }


    private void newProcess(int ProcessNo, String ProcessId , int SterileR ){

        switch(ProcessNo) {
            case 1 :
                WASH_PROCESS_ID_1 = ProcessId;
                imv_pc_1.setImageResource( getImgR( SterileR, false)); break;
            case 2 :
                WASH_PROCESS_ID_2 = ProcessId;
                imv_pc_2.setImageResource( getImgR( SterileR, false)); break;
            case 3 :
                WASH_PROCESS_ID_3 = ProcessId;
                imv_pc_3.setImageResource( getImgR( SterileR, false)); break;
            case 4 :
                WASH_PROCESS_ID_4 = ProcessId;
                imv_pc_4.setImageResource( getImgR( SterileR, false)); break;
            case 5 :
                WASH_PROCESS_ID_5 = ProcessId;
                imv_pc_5.setImageResource( getImgR( SterileR, false)); break;
            case 6 :
                WASH_PROCESS_ID_6 = ProcessId;
                imv_pc_6.setImageResource( getImgR( SterileR, false)); break;
            case 7 :
                WASH_PROCESS_ID_7 = ProcessId;
                imv_pc_7.setImageResource( getImgR( SterileR, false)); break;
            default: return;
        }
    }


    private void setProcessBackgroundColor(){
        if(WASH_PROCESS_NUMBER_ACTIVE > 0 && WASH_PROCESS_NUMBER_ACTIVE < 8) {
            setProcessBackgroundColor(WASH_PROCESS_NUMBER_ACTIVE);
        }
    }

    private void setProcessBackgroundColor(int ProcessNo){
        try {

            imv_pc_1.setImageResource( getImgR( WASH_PROCESS_1.get(i).getProgramR(), false));
            imv_pc_2.setImageResource( getImgR( WASH_PROCESS_2.get(i).getProgramR(), false));
            imv_pc_3.setImageResource( getImgR( WASH_PROCESS_3.get(i).getProgramR(), false));
            imv_pc_4.setImageResource( getImgR( WASH_PROCESS_4.get(i).getProgramR(), false));
            imv_pc_5.setImageResource( getImgR( WASH_PROCESS_5.get(i).getProgramR(), false));
            imv_pc_6.setImageResource( getImgR( WASH_PROCESS_6.get(i).getProgramR(), false));
            imv_pc_7.setImageResource( getImgR( WASH_PROCESS_7.get(i).getProgramR(), false));
            imv_pc_8.setImageResource( getImgR( WASH_PROCESS_8.get(i).getProgramR(), false));
            imv_pc_9.setImageResource( getImgR( WASH_PROCESS_9.get(i).getProgramR(), false));
            imv_pc_10.setImageResource( getImgR( WASH_PROCESS_10.get(i).getProgramR(), false));

            switch(ProcessNo) {
                case 1 : imv_pc_1.setImageResource( getImgR( WASH_PROCESS_1.get(i).getProgramR(), true)); break;
                case 2 : imv_pc_2.setImageResource( getImgR( WASH_PROCESS_2.get(i).getProgramR(), true)); break;
                case 3 : imv_pc_3.setImageResource( getImgR( WASH_PROCESS_3.get(i).getProgramR(), true)); break;
                case 4 : imv_pc_4.setImageResource( getImgR( WASH_PROCESS_4.get(i).getProgramR(), true)); break;
                case 5 : imv_pc_5.setImageResource( getImgR( WASH_PROCESS_5.get(i).getProgramR(), true)); break;
                case 6 : imv_pc_6.setImageResource( getImgR( WASH_PROCESS_6.get(i).getProgramR(), true)); break;
                case 7 : imv_pc_7.setImageResource( getImgR( WASH_PROCESS_7.get(i).getProgramR(), true)); break;
                case 8 : imv_pc_8.setImageResource( getImgR( WASH_PROCESS_8.get(i).getProgramR(), true)); break;
                case 9 : imv_pc_9.setImageResource( getImgR( WASH_PROCESS_9.get(i).getProgramR(), true)); break;
                case 10 : imv_pc_10.setImageResource( getImgR( WASH_PROCESS_10.get(i).getProgramR(), true)); break;
                default: break;
            }

            main.setBackgroundResource(R.drawable.bg_sterile_blue);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void hideProcess(int ProcessNo, boolean IsCancel ){

        switch(ProcessNo) {
            case 1 :    imv_pc_1.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 2 :    imv_pc_2.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 3 :    imv_pc_3.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 4 :    imv_pc_4.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 5 :    imv_pc_5.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 6 :    imv_pc_6.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 7 :    imv_pc_7.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 8 :    imv_pc_8.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 9 :    imv_pc_9.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            case 10 :   imv_pc_10.setVisibility(IsCancel ? View.GONE : View.VISIBLE); break;
            default: return;
        }
    }

    private String getProcessId() {
        switch(WASH_PROCESS_NUMBER_ACTIVE) {
            case 1 : return WASH_PROCESS_ID_1;
            case 2 : return WASH_PROCESS_ID_2;
            case 3 : return WASH_PROCESS_ID_3;
            case 4 : return WASH_PROCESS_ID_4;
            case 5 : return WASH_PROCESS_ID_5;
            case 6 : return WASH_PROCESS_ID_6;
            case 7 : return WASH_PROCESS_ID_7;
            case 8 : return WASH_PROCESS_ID_8;
            case 9 : return WASH_PROCESS_ID_9;
            case 10 : return WASH_PROCESS_ID_10;
            default: return null;
        }
    }

    private int getImgR(int sterileR, boolean p){
        switch(sterileR) {
            case 1 : return p ? R.drawable.ic_p1_blue : R.drawable.ic_p1_grey;
            case 2 : return p ? R.drawable.ic_manual_blue : R.drawable.ic_manual_grey;
            case 3 : return p ? R.drawable.ic_ul_blue : R.drawable.ic_ul_grey;
            case 4 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            case 5 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            case 6 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            case 7 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            case 8 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            case 9 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            case 10 : return p ? R.drawable.ic_program : R.drawable.ic_program;
            default: return 0;
        }
    }

    // =========================================================================================
    //
    // Machine
    //
    // =========================================================================================

    private void hideMachine(){

        hideMachineAll();

        imv_mc_10.setVisibility(MAX_WASH_MACHINE < 10 ? View.GONE : View.VISIBLE);
        imv_mc_9.setVisibility(MAX_WASH_MACHINE < 9 ? View.GONE : View.VISIBLE);
        imv_mc_8.setVisibility(MAX_WASH_MACHINE < 8 ? View.GONE : View.VISIBLE);
        imv_mc_7.setVisibility(MAX_WASH_MACHINE < 7 ? View.GONE : View.VISIBLE);
        imv_mc_6.setVisibility(MAX_WASH_MACHINE < 6 ? View.GONE : View.VISIBLE);
        imv_mc_5.setVisibility(MAX_WASH_MACHINE < 5 ? View.GONE : View.VISIBLE);
        imv_mc_4.setVisibility(MAX_WASH_MACHINE < 4 ? View.GONE : View.VISIBLE);
        imv_mc_3.setVisibility(MAX_WASH_MACHINE < 3 ? View.GONE : View.VISIBLE);
        imv_mc_2.setVisibility(MAX_WASH_MACHINE < 2 ? View.GONE : View.VISIBLE);
        imv_mc_1.setVisibility(MAX_WASH_MACHINE < 1 ? View.GONE : View.VISIBLE);
    }

    private void hideMachineAll(){
        imv_mc_10.setVisibility(View.GONE);
        imv_mc_9.setVisibility(View.GONE);
        imv_mc_8.setVisibility(View.GONE);
        imv_mc_7.setVisibility(View.GONE);
        imv_mc_6.setVisibility(View.GONE);
        imv_mc_5.setVisibility(View.GONE);
        imv_mc_4.setVisibility(View.GONE);
        imv_mc_3.setVisibility(View.GONE);
        imv_mc_2.setVisibility(View.GONE);
        imv_mc_1.setVisibility(View.GONE);

        txt_mn_1.setText("");
        txt_mn_2.setText("");
        txt_mn_3.setText("");
        txt_mn_4.setText("");
        txt_mn_5.setText("");
        txt_mn_6.setText("");
        txt_mn_7.setText("");
        txt_mn_8.setText("");
        txt_mn_9.setText("");
        txt_mn_10.setText("");

        txt_mc_1.setText("");
        txt_mc_2.setText("");
        txt_mc_3.setText("");
        txt_mc_4.setText("");
        txt_mc_5.setText("");
        txt_mc_6.setText("");
        txt_mc_7.setText("");
        txt_mc_8.setText("");
        txt_mc_9.setText("");
        txt_mc_10.setText("");

        MACHINE_1 = null;
        MACHINE_2 = null;
        MACHINE_3 = null;
        MACHINE_4 = null;
        MACHINE_5 = null;
        MACHINE_6 = null;
        MACHINE_7 = null;
        MACHINE_8 = null;
        MACHINE_9 = null;
        MACHINE_10 = null;


    }

    private String getMachineId(int MachineNo){
        switch(MachineNo) {
            case 1 : return MACHINE_1.get(i).getID();
            case 2 : return MACHINE_2.get(i).getID();
            case 3 : return MACHINE_3.get(i).getID();
            case 4 : return MACHINE_4.get(i).getID();
            case 5 : return MACHINE_5.get(i).getID();
            case 6 : return MACHINE_6.get(i).getID();
            case 7 : return MACHINE_7.get(i).getID();
            case 8 : return MACHINE_8.get(i).getID();
            case 9 : return MACHINE_9.get(i).getID();
            case 10 : return MACHINE_10.get(i).getID();
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
            case 6 : return MACHINE_6.get(i).getMachineName();
            case 7 : return MACHINE_7.get(i).getMachineName();
            case 8 : return MACHINE_8.get(i).getMachineName();
            case 9 : return MACHINE_9.get(i).getMachineName();
            case 10 : return MACHINE_10.get(i).getMachineName();
            default: return null;
        }
    }

    private String getMachineActive(int MachineNo){
        switch(MachineNo) {
            case 1 : return MACHINE_1.get(i).getIsActive();
            case 2 : return MACHINE_2.get(i).getIsActive();
            case 3 : return MACHINE_3.get(i).getIsActive();
            case 4 : return MACHINE_4.get(i).getIsActive();
            case 5 : return MACHINE_5.get(i).getIsActive();
            case 6 : return MACHINE_6.get(i).getIsActive();
            case 7 : return MACHINE_7.get(i).getIsActive();
            case 8 : return MACHINE_8.get(i).getIsActive();
            case 9 : return MACHINE_9.get(i).getIsActive();
            case 10 : return MACHINE_10.get(i).getIsActive();
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
            case 6 : return MACHINE_6.get(i).getDocNo();
            case 7 : return MACHINE_7.get(i).getDocNo();
            case 8 : return MACHINE_8.get(i).getDocNo();
            case 9 : return MACHINE_9.get(i).getDocNo();
            case 10 : return MACHINE_10.get(i).getDocNo();
            default: return null;
        }
    }

    private String getDocNo(){

        switch(WASH_MACHINE_NUMBER_ACTIVE) {
            case 1 : return MACHINE_1.get(i).getDocNo();
            case 2 : return MACHINE_2.get(i).getDocNo();
            case 3 : return MACHINE_3.get(i).getDocNo();
            case 4 : return MACHINE_4.get(i).getDocNo();
            case 5 : return MACHINE_5.get(i).getDocNo();
            case 6 : return MACHINE_6.get(i).getDocNo();
            case 7 : return MACHINE_7.get(i).getDocNo();
            case 8 : return MACHINE_8.get(i).getDocNo();
            case 9 : return MACHINE_9.get(i).getDocNo();
            case 10 : return MACHINE_10.get(i).getDocNo();
            default: return null;
        }


    }

    private void setMachine(String DocNo){

        switch(WASH_MACHINE_NUMBER_ACTIVE) {
            case 1 : MACHINE_1.get(i).setDocNo(DocNo); break;
            case 2 : MACHINE_2.get(i).setDocNo(DocNo); break;
            case 3 : MACHINE_3.get(i).setDocNo(DocNo); break;
            case 4 : MACHINE_4.get(i).setDocNo(DocNo); break;
            case 5 : MACHINE_5.get(i).setDocNo(DocNo); break;
            case 6 : MACHINE_6.get(i).setDocNo(DocNo); break;
            case 7 : MACHINE_7.get(i).setDocNo(DocNo); break;
            case 8 : MACHINE_8.get(i).setDocNo(DocNo); break;
            case 9 : MACHINE_9.get(i).setDocNo(DocNo); break;
            case 10 : MACHINE_10.get(i).setDocNo(DocNo); break;
            default: ;
        }

    }

    private boolean checkMachineActive(){

        switch(WASH_MACHINE_NUMBER_ACTIVE) {
            case 1 : return !MACHINE_1.get(i).isActive();
            case 2 : return !MACHINE_2.get(i).isActive();
            case 3 : return !MACHINE_3.get(i).isActive();
            case 4 : return !MACHINE_4.get(i).isActive();
            case 5 : return !MACHINE_5.get(i).isActive();
            case 6 : return !MACHINE_6.get(i).isActive();
            case 7 : return !MACHINE_7.get(i).isActive();
            case 8 : return !MACHINE_8.get(i).isActive();
            case 9 : return !MACHINE_9.get(i).isActive();
            case 10 : return !MACHINE_10.get(i).isActive();
            default: return false;
        }

    }

    private void newMachine(
            String ID,
            String machineName,
            String isActive,
            String docNo,
            String startTime,
            String finishTime,
            String pauseTime,
            String isPause,
            String CountTime,
            String TestProgramID,
            String WashTypeID,
            String TestProgramName,
            String WashTypeName,
            int index
    ){

        List<ModelWashMachine> list = new ArrayList<>();

        list.add(
                getWashMachine(
                        ID,
                        machineName,
                        isActive,
                        docNo,
                        startTime,
                        finishTime,
                        pauseTime,
                        isPause,
                        CountTime,
                        TestProgramID,
                        WashTypeID,
                        TestProgramName,
                        WashTypeName,
                        index
                )
        );

        switch(index) {
            case 1 : MACHINE_1 = list; break;
            case 2 : MACHINE_2 = list; break;
            case 3 : MACHINE_3 = list; break;
            case 4 : MACHINE_4 = list; break;
            case 5 : MACHINE_5 = list; break;
            case 6 : MACHINE_6 = list; break;
            case 7 : MACHINE_7 = list; break;
            case 8 : MACHINE_8 = list; break;
            case 9 : MACHINE_9 = list; break;
            case 10 : MACHINE_10 = list; break;
            default: return;
        }
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

        if(MACHINE_6 != null && MACHINE_6.size() > 0){
            startMachine(6, imv_mc_6, txt_mc_6, txt_mn_6, MACHINE_6);
        }

        if(MACHINE_7 != null && MACHINE_7.size() > 0){
            startMachine(7, imv_mc_7, txt_mc_7, txt_mn_7, MACHINE_7);
        }

        if(MACHINE_8 != null && MACHINE_8.size() > 0){
            startMachine(8, imv_mc_8, txt_mc_8, txt_mn_8, MACHINE_8);
        }

        if(MACHINE_9 != null && MACHINE_9.size() > 0){
            startMachine(9, imv_mc_9, txt_mc_9, txt_mn_9, MACHINE_9);
        }

        if(MACHINE_10 != null && MACHINE_10.size() > 0){
            startMachine(10, imv_mc_10, txt_mc_10, txt_mn_10, MACHINE_10);
        }

    }

    // Machine
    private void startMachine(final int McNo, final ImageView TabMachine, final TextView TextMachine, final TextView TextName, List<ModelWashMachine> MACHINE){

        // Set Machine Name
        try {
            TextName.setText(MACHINE.get(i).getMachineName());
            TextMachine.setText(MACHINE.get(i).getCountTime());
        }catch(Exception e){
            e.printStackTrace();
        }

        // Check Machine Active && Machine --> Finish
        if(MACHINE.get(i).isActive() && MACHINE.get(i).getFinishTime() != null){
            TabMachine.setImageResource(R.drawable.ic_wash_red );
            TextName.setTextColor(getResources().getColor(r));

            TextName.setY( WASH_MACHINE_NUMBER_ACTIVE == McNo ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_RUN_TIME_NORMAL);

            TextMachine.setY( WASH_MACHINE_NUMBER_ACTIVE == McNo ? MACHINE_RUN_TIME_ACTIVE : MACHINE_RUN_TIME_NORMAL);

            // Check Pause Machine
            if(MACHINE.get(i).getIsPause().equals("0")) {
                try {
                    onWash(MACHINE.get(i).getMachineNumber(), MACHINE.get(i).getFinishTime());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }else{
            TabMachine.setImageResource(R.drawable.ic_wash_green );
            TextMachine.setText("");
        }
    }

    private void startMachineActive(){

        // Start Machine Active
        switch(WASH_MACHINE_NUMBER_ACTIVE){
            case 1 : handler_1.removeCallbacks(runnable_1); startMachine(1, imv_mc_1, txt_mc_1, txt_mn_1, MACHINE_1); break;
            case 2 : handler_2.removeCallbacks(runnable_2); startMachine(2, imv_mc_2, txt_mc_2, txt_mn_2, MACHINE_2); break;
            case 3 : handler_3.removeCallbacks(runnable_3); startMachine(3, imv_mc_3, txt_mc_3, txt_mn_3, MACHINE_3); break;
            case 4 : handler_4.removeCallbacks(runnable_4); startMachine(4, imv_mc_4, txt_mc_4, txt_mn_4, MACHINE_4); break;
            case 5 : handler_5.removeCallbacks(runnable_5); startMachine(5, imv_mc_5, txt_mc_5, txt_mn_5, MACHINE_5); break;
            case 6 : handler_6.removeCallbacks(runnable_6); startMachine(6, imv_mc_6, txt_mc_6, txt_mn_6, MACHINE_6); break;
            case 7 : handler_7.removeCallbacks(runnable_7); startMachine(7, imv_mc_7, txt_mc_7, txt_mn_7, MACHINE_7); break;
            case 8 : handler_8.removeCallbacks(runnable_8); startMachine(8, imv_mc_8, txt_mc_8, txt_mn_8, MACHINE_8); break;
            case 9 : handler_9.removeCallbacks(runnable_9); startMachine(9, imv_mc_9, txt_mc_9, txt_mn_9, MACHINE_9); break;
            case 10 : handler_10.removeCallbacks(runnable_10); startMachine(10, imv_mc_10, txt_mc_10, txt_mn_10, MACHINE_10); break;
        }

        if(WASH_MACHINE_NUMBER_ACTIVE > 0) {
            disableButtonAictive();
            main.setBackgroundResource(R.drawable.bg_sterile_red);
        }
    }

    private void onWash(int Machine_Number, final String EndDateTime) throws Exception{
        // Show Machine Status
        switch(Machine_Number){
            case 1 : countSterileMachineNumber(1, EndDateTime); break;
            case 2 : countSterileMachineNumber(2, EndDateTime); break;
            case 3 : countSterileMachineNumber(3, EndDateTime); break;
            case 4 : countSterileMachineNumber(4, EndDateTime); break;
            case 5 : countSterileMachineNumber(5, EndDateTime); break;
            case 6 : countSterileMachineNumber(6, EndDateTime); break;
            case 7 : countSterileMachineNumber(7, EndDateTime); break;
            case 8 : countSterileMachineNumber(8, EndDateTime); break;
            case 9 : countSterileMachineNumber(9, EndDateTime); break;
            case 10 : countSterileMachineNumber( 10, EndDateTime); break;
        }

    }

    private void countSterileMachineNumber(final int Machine_Number, final String EndDateTime) throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final Date end_date = dateFormat.parse(EndDateTime);

        switch(Machine_Number) {
            case 1:
                runnable_1 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_1.postDelayed(this, delay);
                            Date start_date = new Date();

                            if (!start_date.after(end_date)) {
                                txt_mc_1.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_1.removeCallbacks(runnable_1);
                                //getQR(MACHINE_1.get(i).getDocNo(), "w2", "1");
                                updateFinishMachine(1, MACHINE_1.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_1.removeCallbacks(runnable_1);
                        }
                    }
                };

                handler_1.postDelayed(runnable_1, 0); break;

            case 2:
                runnable_2 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_2.postDelayed(this, delay);
                            Date start_date = new Date();

                            if (!start_date.after(end_date)) {
                                txt_mc_2.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_2.removeCallbacks(runnable_2);
                                //getQR(MACHINE_2.get(i).getDocNo(), "w2", "2");
                                updateFinishMachine(2, MACHINE_2.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_2.removeCallbacks(runnable_2);
                        }
                    }
                };

                handler_2.postDelayed(runnable_2, 0); break;

            case 3:
                runnable_3 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_3.postDelayed(this, delay);
                            Date start_date = new Date();

                            if (!start_date.after(end_date)) {
                                txt_mc_3.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_3.removeCallbacks(runnable_3);
                                //getQR(MACHINE_3.get(i).getDocNo(), "w2", "3");
                                updateFinishMachine(3, MACHINE_3.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_3.removeCallbacks(runnable_3);
                        }
                    }
                };

                handler_3.postDelayed(runnable_3, 0); break;

            case 4:
                runnable_4 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_4.postDelayed(this, delay);
                            Date start_date = new Date();

                            if (!start_date.after(end_date)) {
                                txt_mc_4.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_4.removeCallbacks(runnable_4);
                                //getQR(MACHINE_4.get(i).getDocNo(), "w2", "4");
                                updateFinishMachine(4, MACHINE_4.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_4.removeCallbacks(runnable_4);
                        }
                    }
                };

                handler_4.postDelayed(runnable_4, 0); break;

            case 5 :
                runnable_5 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_5.postDelayed(this, delay);
                            Date start_date = new Date();

                            if (!start_date.after(end_date)) {
                                txt_mc_5.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_5.removeCallbacks(runnable_5);
                                //getQR(MACHINE_5.get(i).getDocNo(), "w2", "5");
                                updateFinishMachine(5, MACHINE_5.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_5.removeCallbacks(runnable_5);
                        }
                    }
                };

                handler_5.postDelayed(runnable_5, 0); break;

            case 6:
                runnable_6 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_6.postDelayed(this, delay);
                            Date start_date = new Date();
                            if (!start_date.after(end_date)) {
                                txt_mc_6.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_6.removeCallbacks(runnable_6);
                                //getQR(MACHINE_6.get(i).getDocNo(), "w2", "6");
                                updateFinishMachine(6, MACHINE_6.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_6.removeCallbacks(runnable_6);
                        }
                    }
                };

                handler_6.postDelayed(runnable_6, 0); break;

            case 7:
                runnable_7 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_7.postDelayed(this, delay);
                            Date start_date = new Date();
                            if (!start_date.after(end_date)) {
                                txt_mc_7.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_7.removeCallbacks(runnable_7);
                                //getQR(MACHINE_7.get(i).getDocNo(), "w2", "7");
                                updateFinishMachine(7, MACHINE_7.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_7.removeCallbacks(runnable_7);
                        }
                    }
                };

                handler_7.postDelayed(runnable_7, 0); break;

            case 8:
                runnable_8 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_8.postDelayed(this, delay);
                            Date start_date = new Date();
                            if (!start_date.after(end_date)) {
                                txt_mc_8.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_8.removeCallbacks(runnable_8);
                                //getQR(MACHINE_8.get(i).getDocNo(), "w2", "8");
                                updateFinishMachine(8, MACHINE_8.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_8.removeCallbacks(runnable_8);
                        }
                    }
                };

                handler_8.postDelayed(runnable_8, 0); break;

            case 9:
                runnable_9 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_9.postDelayed(this, delay);
                            Date start_date = new Date();
                            if (!start_date.after(end_date)) {
                                txt_mc_9.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_9.removeCallbacks(runnable_9);
                                //getQR(MACHINE_9.get(i).getDocNo(), "w2", "9");
                                updateFinishMachine(9, MACHINE_9.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_9.removeCallbacks(runnable_9);
                        }
                    }
                };

                handler_1.postDelayed(runnable_1, 0); break;

            case 10:
                runnable_10 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            handler_10.postDelayed(this, delay);
                            Date start_date = new Date();
                            if (!start_date.after(end_date)) {
                                txt_mc_10.setText(CountDownTime.getCountLabel(start_date, end_date));
                            } else {
                                handler_10.removeCallbacks(runnable_10);
                                //getQR(MACHINE_10.get(i).getDocNo(), "w2", "10");
                                updateFinishMachine(10, MACHINE_10.get(i).getDocNo());
                                clearForm();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler_10.removeCallbacks(runnable_10);
                        }
                    }
                };

                handler_10.postDelayed(runnable_10, 0); break;
        }
    }

    // 0n Click
    private void clickMachine(final int Machine_Number) {
        WASH_MACHINE_NUMBER_ACTIVE = Machine_Number;
        try {
            // Display Background
            displayTabMachine(Machine_Number);
            // Display Document
            switch (Machine_Number) {
                case 1: onDisplay(MACHINE_1); break;
                case 2: onDisplay(MACHINE_2); break;
                case 3: onDisplay(MACHINE_3); break;
                case 4: onDisplay(MACHINE_4); break;
                case 5: onDisplay(MACHINE_5); break;
                case 6: onDisplay(MACHINE_6); break;
                case 7: onDisplay(MACHINE_7); break;
                case 8: onDisplay(MACHINE_8); break;
                case 9: onDisplay(MACHINE_9); break;
                case 10: onDisplay(MACHINE_10); break;
                default : break;
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    private void onDisplay(List<ModelWashMachine> MACHINE){
        if (MACHINE.get(i).getDocNo() != null && !MACHINE.get(i).getDocNo().equals("-")) {
            onDisplay(MACHINE.get(i).getDocNo());
            getOccupancyRate(getDocNo());
            if (MACHINE.get(i).isActive()) {
                alertMachineBusy(MACHINE.get(i).getMachineName());
            }
        } else {
            clearForm();
//            edit_wash_type.callOnClick();
        }
    }

    private void displayTabMachine(int m_no){

        defaultTabMachine();

        switch(m_no) {
            case 1:
                if (MACHINE_1 != null && MACHINE_1.size() > 0) {
                    imv_mc_1.bringToFront();
                    imv_mc_1.setY(MACHINE_ACTIVE);

                    txt_mc_1.bringToFront();
                    txt_mc_1.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_1.bringToFront();
                    txt_mn_1.setY(MACHINE_1.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_1.setTextColor(getResources().getColor(MACHINE_1.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_1.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 2:
                if (MACHINE_2 != null && MACHINE_2.size() > 0) {

                    imv_mc_2.bringToFront();
                    imv_mc_2.setY(MACHINE_ACTIVE);

                    txt_mc_2.bringToFront();
                    txt_mc_2.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_2.bringToFront();
                    txt_mn_2.setY(MACHINE_2.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_2.setTextColor(getResources().getColor(MACHINE_2.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_2.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 3:
                if (MACHINE_3 != null && MACHINE_3.size() > 0) {
                    imv_mc_3.bringToFront();
                    imv_mc_3.setY(MACHINE_ACTIVE);

                    txt_mc_3.bringToFront();
                    txt_mc_3.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_3.bringToFront();
                    txt_mn_3.setY(MACHINE_3.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_3.setTextColor(getResources().getColor(MACHINE_3.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_3.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 4:
                if (MACHINE_4 != null && MACHINE_4.size() > 0) {

                    imv_mc_4.bringToFront();
                    imv_mc_4.setY(MACHINE_ACTIVE);

                    txt_mc_4.bringToFront();
                    txt_mc_4.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_4.bringToFront();
                    txt_mn_4.setY(MACHINE_4.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_4.setTextColor(getResources().getColor(MACHINE_4.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_4.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 5:
                if (MACHINE_5 != null && MACHINE_5.size() > 0) {
                    imv_mc_5.bringToFront();
                    imv_mc_5.setY(MACHINE_ACTIVE);

                    txt_mc_5.bringToFront();
                    txt_mc_5.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_5.bringToFront();
                    txt_mn_5.setY(MACHINE_5.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_5.setTextColor(getResources().getColor(MACHINE_5.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_5.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 6:
                if (MACHINE_6 != null && MACHINE_6.size() > 0) {
                    imv_mc_6.bringToFront();
                    imv_mc_6.setY(MACHINE_ACTIVE);

                    txt_mc_6.bringToFront();
                    txt_mc_6.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_6.bringToFront();
                    txt_mn_6.setY(MACHINE_6.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_6.setTextColor(getResources().getColor(MACHINE_6.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_6.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 7:
                if (MACHINE_7 != null && MACHINE_7.size() > 0) {
                    imv_mc_7.bringToFront();
                    imv_mc_7.setY(MACHINE_ACTIVE);

                    txt_mc_7.bringToFront();
                    txt_mc_7.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_7.bringToFront();
                    txt_mn_7.setY(MACHINE_7.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_7.setTextColor(getResources().getColor(MACHINE_7.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_7.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 8:
                if (MACHINE_8 != null && MACHINE_8.size() > 0) {
                    imv_mc_8.bringToFront();
                    imv_mc_8.setY(MACHINE_ACTIVE);

                    txt_mc_8.bringToFront();
                    txt_mc_8.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_8.bringToFront();
                    txt_mn_8.setY(MACHINE_8.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_8.setTextColor(getResources().getColor(MACHINE_8.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_8.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 9:
                if (MACHINE_9 != null && MACHINE_9.size() > 0) {
                    imv_mc_9.bringToFront();
                    imv_mc_9.setY(MACHINE_ACTIVE);

                    txt_mc_9.bringToFront();
                    txt_mc_9.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_9.bringToFront();
                    txt_mn_9.setY(MACHINE_9.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_9.setTextColor(getResources().getColor(MACHINE_9.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_9.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            case 10:
                if (MACHINE_10 != null && MACHINE_10.size() > 0) {
                    imv_mc_10.bringToFront();
                    imv_mc_10.setY(MACHINE_ACTIVE);

                    txt_mc_10.bringToFront();
                    txt_mc_10.setY(MACHINE_RUN_TIME_ACTIVE);

                    txt_mn_10.bringToFront();
                    txt_mn_10.setY(MACHINE_10.get(i).isActive() ? MACHINE_NAME_RUN_TIME_ACTIVE : MACHINE_NAME_ACTIVE);
                    txt_mn_10.setTextColor(getResources().getColor(MACHINE_10.get(i).isActive() ? r : b));

                    main.setBackgroundResource(MACHINE_10.get(i).isActive() ? R.drawable.bg_sterile_red : R.drawable.bg_sterile_blue);
                }
                break;

            default: break;
        }

        try {
            // Background
            imv_mc_1.setImageResource( ( MACHINE_1 != null && MACHINE_1.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 1 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_2.setImageResource( ( MACHINE_2 != null && MACHINE_2.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 2 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_3.setImageResource( ( MACHINE_3 != null && MACHINE_3.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 3 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_4.setImageResource( ( MACHINE_4 != null && MACHINE_4.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 4 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_5.setImageResource( ( MACHINE_5 != null && MACHINE_5.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 5 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_6.setImageResource( ( MACHINE_6 != null && MACHINE_6.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 6 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_7.setImageResource( ( MACHINE_7 != null && MACHINE_7.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 7 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_8.setImageResource( ( MACHINE_8 != null && MACHINE_8.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 8 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_9.setImageResource( ( MACHINE_9 != null && MACHINE_9.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 9 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));
            imv_mc_10.setImageResource( ( MACHINE_10 != null && MACHINE_10.get(i).isActive() ) ? R.drawable.ic_wash_red : (m_no == 10 ? R.drawable.ic_wash_blue : R.drawable.ic_wash_green));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void clearMachine(int MachineNo){
        switch(MachineNo) {
            case 1 : imv_mc_1.setImageResource(R.drawable.ic_wash_green); txt_mc_1.setText(""); txt_mn_1.setTextColor(getResources().getColor(g));break;
            case 2 : imv_mc_2.setImageResource(R.drawable.ic_wash_green); txt_mc_2.setText(""); txt_mn_2.setTextColor(getResources().getColor(g));break;
            case 3 : imv_mc_3.setImageResource(R.drawable.ic_wash_green); txt_mc_3.setText(""); txt_mn_3.setTextColor(getResources().getColor(g));break;
            case 4 : imv_mc_4.setImageResource(R.drawable.ic_wash_green); txt_mc_4.setText(""); txt_mn_4.setTextColor(getResources().getColor(g));break;
            case 5 : imv_mc_5.setImageResource(R.drawable.ic_wash_green); txt_mc_5.setText(""); txt_mn_5.setTextColor(getResources().getColor(g));break;
            case 6 : imv_mc_6.setImageResource(R.drawable.ic_wash_green); txt_mc_6.setText(""); txt_mn_6.setTextColor(getResources().getColor(g));break;
            case 7 : imv_mc_7.setImageResource(R.drawable.ic_wash_green); txt_mc_7.setText(""); txt_mn_7.setTextColor(getResources().getColor(g));break;
            case 8 : imv_mc_8.setImageResource(R.drawable.ic_wash_green); txt_mc_8.setText(""); txt_mn_8.setTextColor(getResources().getColor(g));break;
            case 9 : imv_mc_9.setImageResource(R.drawable.ic_wash_green); txt_mc_9.setText(""); txt_mn_9.setTextColor(getResources().getColor(g));break;
            case 10 : imv_mc_10.setImageResource(R.drawable.ic_wash_green); txt_mc_10.setText(""); txt_mn_10.setTextColor(getResources().getColor(g));break;
            default: return;
        }

        // Set Default
        defaultTabMachine();

        // Default Background
        main.setBackgroundResource(R.drawable.bg_sterile_blue);
    }


    private void disableButtonAictive(){
        btn_complete.setBackgroundResource(R.drawable.ic_sentwash_disable);
        imv_remove_all_wash.setImageResource(R.drawable.bt_select_all_disable);
    }

    private void defaultTabMachine(){

        btn_complete.setBackgroundResource(R.drawable.ic_sentwash);
        imv_remove_all_wash.setImageResource(R.drawable.bt_select_all);

        imv_mc_10.bringToFront();
        imv_mc_9.bringToFront();
        imv_mc_8.bringToFront();
        imv_mc_7.bringToFront();
        imv_mc_6.bringToFront();
        imv_mc_5.bringToFront();
        imv_mc_4.bringToFront();
        imv_mc_3.bringToFront();
        imv_mc_2.bringToFront();
        imv_mc_1.bringToFront();

        txt_mc_10.bringToFront();
        txt_mc_9.bringToFront();
        txt_mc_8.bringToFront();
        txt_mc_7.bringToFront();
        txt_mc_6.bringToFront();
        txt_mc_5.bringToFront();
        txt_mc_4.bringToFront();
        txt_mc_3.bringToFront();
        txt_mc_2.bringToFront();
        txt_mc_1.bringToFront();

        txt_mn_1.bringToFront();
        txt_mn_2.bringToFront();
        txt_mn_3.bringToFront();
        txt_mn_4.bringToFront();
        txt_mn_5.bringToFront();
        txt_mn_6.bringToFront();
        txt_mn_7.bringToFront();
        txt_mn_8.bringToFront();
        txt_mn_9.bringToFront();
        txt_mn_10.bringToFront();

        // Machine Time
        imv_mc_1.setY(MACHINE_NORMAL);
        imv_mc_2.setY(MACHINE_NORMAL);
        imv_mc_3.setY(MACHINE_NORMAL);
        imv_mc_4.setY(MACHINE_NORMAL);
        imv_mc_5.setY(MACHINE_NORMAL);
        imv_mc_6.setY(MACHINE_NORMAL);
        imv_mc_7.setY(MACHINE_NORMAL);
        imv_mc_8.setY(MACHINE_NORMAL);
        imv_mc_9.setY(MACHINE_NORMAL);
        imv_mc_10.setY(MACHINE_NORMAL);

        txt_mc_1.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_2.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_3.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_4.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_5.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_6.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_7.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_8.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_9.setY(MACHINE_RUN_TIME_NORMAL);
        txt_mc_10.setY(MACHINE_RUN_TIME_NORMAL);

        // Machine Name
        txt_mn_1.setY(getY(MACHINE_1));
        txt_mn_2.setY(getY(MACHINE_2));
        txt_mn_3.setY(getY(MACHINE_3));
        txt_mn_4.setY(getY(MACHINE_4));
        txt_mn_5.setY(getY(MACHINE_5));
        txt_mn_6.setY(getY(MACHINE_6));
        txt_mn_7.setY(getY(MACHINE_7));
        txt_mn_8.setY(getY(MACHINE_8));
        txt_mn_9.setY(getY(MACHINE_9));
        txt_mn_10.setY(getY(MACHINE_10));


        /*
        txt_mn_1.setY(MACHINE_NAME_NORMAL);
        txt_mn_2.setY(MACHINE_NAME_NORMAL);
        txt_mn_3.setY(MACHINE_NAME_NORMAL);
        txt_mn_4.setY(MACHINE_NAME_NORMAL);
        txt_mn_5.setY(MACHINE_NAME_NORMAL);
        txt_mn_6.setY(MACHINE_NAME_NORMAL);
        txt_mn_7.setY(MACHINE_NAME_NORMAL);
        txt_mn_8.setY(MACHINE_NAME_NORMAL);
        txt_mn_9.setY(MACHINE_NAME_NORMAL);
        txt_mn_10.setY(MACHINE_NAME_NORMAL);


        txt_mn_1.setTextColor(getResources().getColor(g));
        txt_mn_2.setTextColor(getResources().getColor(g));
        txt_mn_3.setTextColor(getResources().getColor(g));
        txt_mn_4.setTextColor(getResources().getColor(g));
        txt_mn_5.setTextColor(getResources().getColor(g));
        txt_mn_6.setTextColor(getResources().getColor(g));
        txt_mn_7.setTextColor(getResources().getColor(g));
        txt_mn_8.setTextColor(getResources().getColor(g));
        txt_mn_9.setTextColor(getResources().getColor(g));
        txt_mn_10.setTextColor(getResources().getColor(g));
        */


        txt_mn_1.setTextColor(getResources().getColor(MACHINE_1 != null && MACHINE_1.get(i).isActive() ? r : g));
        txt_mn_2.setTextColor(getResources().getColor(MACHINE_2 != null && MACHINE_2.get(i).isActive() ? r : g));
        txt_mn_3.setTextColor(getResources().getColor(MACHINE_3 != null && MACHINE_3.get(i).isActive() ? r : g));
        txt_mn_4.setTextColor(getResources().getColor(MACHINE_4 != null && MACHINE_4.get(i).isActive() ? r : g));
        txt_mn_5.setTextColor(getResources().getColor(MACHINE_5 != null && MACHINE_5.get(i).isActive() ? r : g));
        txt_mn_6.setTextColor(getResources().getColor(MACHINE_6 != null && MACHINE_6.get(i).isActive() ? r : g));
        txt_mn_7.setTextColor(getResources().getColor(MACHINE_7 != null && MACHINE_7.get(i).isActive() ? r : g));
        txt_mn_8.setTextColor(getResources().getColor(MACHINE_8 != null && MACHINE_8.get(i).isActive() ? r : g));
        txt_mn_9.setTextColor(getResources().getColor(MACHINE_9 != null && MACHINE_9.get(i).isActive() ? r : g));
        txt_mn_10.setTextColor(getResources().getColor(MACHINE_10 != null && MACHINE_10.get(i).isActive() ? r : g));

        main.bringToFront();
        imageBack.bringToFront();
        imv_import_all_send_sterile.bringToFront();
        imv_remove_all_wash.bringToFront();
        txt_caption.bringToFront();
        group_choices.bringToFront();
        imv_basket.bringToFront();

    }

    private int getY(List<ModelWashMachine> MACHINE){
        return (MACHINE != null && MACHINE.size() > 0 && MACHINE.get(i).isActive()) ? MACHINE_NAME_RUN_TIME_NORMAL : MACHINE_NAME_NORMAL;
    }

    final String[] option = new String[]{"จบการทำงาน", "เริ่มใหม่", "หยุดการทำงาน", "ออก"};
    final String[] option_2 = new String[]{"จบการทำงาน", "เริ่มใหม่", "เริ่มทำงาน", "ออก"};
    final String[] option_admin = new String[]{"เริ่มใหม่", "หยุดการทำงาน", "ออก"};
    final String[] option_2_admin = new String[]{"เริ่มใหม่", "เริ่มทำงาน", "ออก"};
    final String[] option_3 = new String[]{"เริ่มทำงาน", "ออก"};

    private boolean isPause(final int Machine_Number){
        try {
            switch (Machine_Number) {
                case 1:
                    return MACHINE_1.get(i).getIsPause().equals("0");
                case 2:
                    return MACHINE_2.get(i).getIsPause().equals("0");
                case 3:
                    return MACHINE_3.get(i).getIsPause().equals("0");
                case 4:
                    return MACHINE_4.get(i).getIsPause().equals("0");
                case 5:
                    return MACHINE_5.get(i).getIsPause().equals("0");
                case 6:
                    return MACHINE_6.get(i).getIsPause().equals("0");
                case 7:
                    return MACHINE_7.get(i).getIsPause().equals("0");
                case 8:
                    return MACHINE_8.get(i).getIsPause().equals("0");
                case 9:
                    return MACHINE_9.get(i).getIsPause().equals("0");
                case 10:
                    return MACHINE_10.get(i).getIsPause().equals("0");
                default:
                    return false;
            }
        }catch(Exception e){
            return false;
        }
    }

    private void longClickMachine(final int Machine_Number) {

        clickMachine(Machine_Number);

        //STERILE_MACHINE_NUMBER_ACTIVE = Machine_Number;

        String DocNo = getDocNo(Machine_Number);

        // No Have DocNo.
        if(DocNo == null || DocNo.equals("-") || DocNo.equals("")) {
            return;
        }

        // Doc Not Run
        if(txt_finish.getText().equals("") && getMachineActive(Machine_Number).equals("0")){
            // Not Ever Run
            openDialog();
        }else{
            // Ever Run
            openDialog(Machine_Number);
        }
    }

    private void openDialog(final int Machine_Number) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdWash.this, android.R.layout.select_dialog_item, isPause(Machine_Number) ? option : option_2);
            AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
            builder.setTitle(Cons.TITLE);
            builder.setIcon(R.drawable.pose_favicon_2x);
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // ListView Clicked item value
                    if (which == 0) {
                        // =============================================================================
                        AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdWash.this);
                        quitDialog.setTitle(Cons.TITLE);
                        quitDialog.setIcon(R.drawable.pose_favicon_2x);
                        quitDialog.setMessage(Cons.CONFIRM_STOP);
                        quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (Machine_Number) {
                                    case 1:
                                        if (IsQR) {
                                            //getQR(MACHINE_1.get(i).getDocNo(), "w2", "1");
                                            handler_1.removeCallbacks(runnable_1);
                                            updateFinishMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                                            handler_1.removeCallbacks(runnable_1);
                                            clearForm();
                                        } else {
                                            handler_1.removeCallbacks(runnable_1);
                                            updateFinishMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                                            handler_1.removeCallbacks(runnable_1);
                                            clearForm();
                                        }
                                        break;
                                    case 2:
                                        if (IsQR) {
                                            //getQR(MACHINE_2.get(i).getDocNo(), "w2", "2");
                                            handler_2.removeCallbacks(runnable_2);
                                            updateFinishMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
                                            handler_2.removeCallbacks(runnable_2);
                                            clearForm();
                                        } else {
                                            handler_2.removeCallbacks(runnable_2);
                                            updateFinishMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
                                            handler_2.removeCallbacks(runnable_2);
                                            clearForm();
                                        }
                                        break;

                                    case 3:
                                        if (IsQR) {
                                            //getQR(MACHINE_3.get(i).getDocNo(), "w2", "3");
                                            handler_3.removeCallbacks(runnable_3);
                                            updateFinishMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
                                            handler_3.removeCallbacks(runnable_3);
                                            clearForm();
                                        } else {
                                            handler_3.removeCallbacks(runnable_3);
                                            updateFinishMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
                                            handler_3.removeCallbacks(runnable_3);
                                            clearForm();
                                        }
                                        break;

                                    case 4:
                                        if (IsQR) {
                                            //getQR(MACHINE_4.get(i).getDocNo(), "w2", "4");
                                            handler_4.removeCallbacks(runnable_4);
                                            updateFinishMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
                                            handler_4.removeCallbacks(runnable_4);
                                            clearForm();
                                        } else {
                                            handler_4.removeCallbacks(runnable_4);
                                            updateFinishMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
                                            handler_4.removeCallbacks(runnable_4);
                                            clearForm();
                                        }
                                        break;

                                    case 5:
                                        if (IsQR) {
                                            //getQR(MACHINE_5.get(i).getDocNo(), "w2", "5");
                                            handler_5.removeCallbacks(runnable_5);
                                            updateFinishMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
                                            handler_5.removeCallbacks(runnable_5);
                                            clearForm();
                                        } else {
                                            handler_5.removeCallbacks(runnable_5);
                                            updateFinishMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
                                            handler_5.removeCallbacks(runnable_5);
                                            clearForm();
                                        }
                                        break;

                                    case 6:

                                        if (IsQR) {
                                            //getQR(MACHINE_6.get(i).getDocNo(), "w2", "6");
                                            handler_6.removeCallbacks(runnable_6);
                                            updateFinishMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
                                            handler_6.removeCallbacks(runnable_6);
                                            clearForm();
                                        } else {
                                            handler_6.removeCallbacks(runnable_6);
                                            updateFinishMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
                                            handler_6.removeCallbacks(runnable_6);
                                            clearForm();
                                        }

                                        break;

                                    case 7:
                                        if (IsQR) {
                                            //getQR(MACHINE_7.get(i).getDocNo(), "w2", "7");
                                            handler_7.removeCallbacks(runnable_7);
                                            updateFinishMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
                                            handler_7.removeCallbacks(runnable_7);
                                            clearForm();
                                        } else {
                                            handler_7.removeCallbacks(runnable_7);
                                            updateFinishMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
                                            handler_7.removeCallbacks(runnable_7);
                                            clearForm();
                                        }
                                        break;

                                    case 8:
                                        if (IsQR) {
                                            //getQR(MACHINE_8.get(i).getDocNo(), "w2", "8");
                                            handler_8.removeCallbacks(runnable_8);
                                            updateFinishMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
                                            handler_8.removeCallbacks(runnable_8);
                                            clearForm();
                                        } else {
                                            handler_8.removeCallbacks(runnable_8);
                                            updateFinishMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
                                            handler_8.removeCallbacks(runnable_8);
                                            clearForm();
                                        }
                                        break;

                                    case 9:
                                        if (IsQR) {
                                            //getQR(MACHINE_9.get(i).getDocNo(), "w2", "9");
                                            handler_9.removeCallbacks(runnable_9);
                                            updateFinishMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
                                            handler_9.removeCallbacks(runnable_9);
                                            clearForm();
                                        } else {
                                            handler_9.removeCallbacks(runnable_9);
                                            updateFinishMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
                                            handler_9.removeCallbacks(runnable_9);
                                            clearForm();
                                        }
                                        break;

                                    case 10:
                                        if (IsQR) {
                                            //getQR(MACHINE_10.get(i).getDocNo(), "w2", "10");
                                            handler_10.removeCallbacks(runnable_10);
                                            updateFinishMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
                                            handler_10.removeCallbacks(runnable_10);
                                            clearForm();
                                        } else {
                                            handler_10.removeCallbacks(runnable_10);
                                            updateFinishMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
                                            handler_10.removeCallbacks(runnable_10);
                                            clearForm();
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
                        if (IsQR) {
                            getQR(getDocNo(), "w1", "");
                        } else {
                            startMachine(getDocNo());
                        }
                    } else if (which == 2) {
                        // =============================================================================

                        switch (Machine_Number) {
                            case 1:

                                if (MACHINE_1.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_1.removeCallbacks(runnable_1);

                                    // Finish Update IsStatus
                                    updatePauseMachine(Machine_Number, MACHINE_1.get(i).getDocNo());

                                    handler_1.removeCallbacks(runnable_1);
                                }

                                break;

                            case 2:

                                if (MACHINE_2.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_2.removeCallbacks(runnable_2);

                                    // Finish Update IsStatus
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
                            case 6:

                                if (MACHINE_6.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_6.removeCallbacks(runnable_6);

                                    // Finish Update IsStatus
                                    updatePauseMachine(Machine_Number, MACHINE_6.get(i).getDocNo());

                                    handler_6.removeCallbacks(runnable_6);
                                }

                                break;

                            case 7:

                                if (MACHINE_7.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_7.removeCallbacks(runnable_7);

                                    // Finish Update IsStatus
                                    updatePauseMachine(Machine_Number, MACHINE_7.get(i).getDocNo());

                                    handler_7.removeCallbacks(runnable_7);
                                }

                                break;

                            case 8:

                                if (MACHINE_8.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_8.removeCallbacks(runnable_8);

                                    // Finish Update IsStatus
                                    updatePauseMachine(Machine_Number, MACHINE_8.get(i).getDocNo());

                                    handler_8.removeCallbacks(runnable_8);
                                }

                                break;

                            case 9:

                                if (MACHINE_9.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_9.removeCallbacks(runnable_9);

                                    // Finish Update IsStatus
                                    updatePauseMachine(Machine_Number, MACHINE_9.get(i).getDocNo());

                                    handler_9.removeCallbacks(runnable_9);
                                }

                                break;

                            case 10:

                                if (MACHINE_10.get(i).getIsPause().equals("1")) {

                                    updateRunMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
                                } else {
                                    // Stop handler
                                    handler_10.removeCallbacks(runnable_10);

                                    // Finish Update IsStatus
                                    updatePauseMachine(Machine_Number, MACHINE_10.get(i).getDocNo());

                                    handler_10.removeCallbacks(runnable_10);
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

//        if (IsAdmin == true) {
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdWash.this, android.R.layout.select_dialog_item, isPause(Machine_Number) ? option : option_2);
//            AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
//            builder.setTitle(Cons.TITLE);
//            builder.setIcon(R.drawable.pose_favicon_2x);
//            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    // ListView Clicked item value
//                    if (which == 0) {
//                        // =============================================================================
//                        AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdWash.this);
//                        quitDialog.setTitle(Cons.TITLE);
//                        quitDialog.setIcon(R.drawable.pose_favicon_2x);
//                        quitDialog.setMessage(Cons.CONFIRM_STOP);
//                        quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                switch (Machine_Number) {
//                                    case 1:
//                                        if (IsQR) {
//                                            getQR(MACHINE_1.get(i).getDocNo(), "w2", "1");
//                                        } else {
//                                            handler_1.removeCallbacks(runnable_1);
//                                            updateFinishMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
//                                            handler_1.removeCallbacks(runnable_1);
//                                        }
//                                        break;
//                                    case 2:
//                                        if (IsQR) {
//                                            getQR(MACHINE_2.get(i).getDocNo(), "w2", "2");
//                                        } else {
//                                            handler_2.removeCallbacks(runnable_2);
//                                            updateFinishMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
//                                            handler_2.removeCallbacks(runnable_2);
//                                        }
//                                        break;
//
//                                    case 3:
//                                        if (IsQR) {
//                                            getQR(MACHINE_3.get(i).getDocNo(), "w2", "3");
//                                        } else {
//                                            handler_3.removeCallbacks(runnable_3);
//                                            updateFinishMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
//                                            handler_3.removeCallbacks(runnable_3);
//                                        }
//                                        break;
//
//                                    case 4:
//                                        if (IsQR) {
//                                            getQR(MACHINE_4.get(i).getDocNo(), "w2", "4");
//                                        } else {
//                                            handler_4.removeCallbacks(runnable_4);
//                                            updateFinishMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
//                                            handler_4.removeCallbacks(runnable_4);
//                                        }
//                                        break;
//
//                                    case 5:
//                                        if (IsQR) {
//                                            getQR(MACHINE_5.get(i).getDocNo(), "w2", "5");
//                                        } else {
//                                            handler_5.removeCallbacks(runnable_5);
//                                            updateFinishMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
//                                            handler_5.removeCallbacks(runnable_5);
//                                        }
//                                        break;
//
//                                    case 6:
//
//                                        if (IsQR) {
//                                            getQR(MACHINE_6.get(i).getDocNo(), "w2", "6");
//                                        } else {
//                                            handler_6.removeCallbacks(runnable_6);
//                                            updateFinishMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
//                                            handler_6.removeCallbacks(runnable_6);
//                                        }
//
//                                        break;
//
//                                    case 7:
//                                        if (IsQR) {
//                                            getQR(MACHINE_7.get(i).getDocNo(), "w2", "7");
//                                        } else {
//                                            handler_7.removeCallbacks(runnable_7);
//                                            updateFinishMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
//                                            handler_7.removeCallbacks(runnable_7);
//                                        }
//                                        break;
//
//                                    case 8:
//                                        if (IsQR) {
//                                            getQR(MACHINE_8.get(i).getDocNo(), "w2", "8");
//                                        } else {
//                                            handler_8.removeCallbacks(runnable_8);
//                                            updateFinishMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
//                                            handler_8.removeCallbacks(runnable_8);
//                                        }
//                                        break;
//
//                                    case 9:
//                                        if (IsQR) {
//                                            getQR(MACHINE_9.get(i).getDocNo(), "w2", "9");
//                                        } else {
//                                            handler_9.removeCallbacks(runnable_9);
//                                            updateFinishMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
//                                            handler_9.removeCallbacks(runnable_9);
//                                        }
//                                        break;
//
//                                    case 10:
//                                        if (IsQR) {
//                                            getQR(MACHINE_10.get(i).getDocNo(), "w2", "10");
//                                        } else {
//                                            handler_10.removeCallbacks(runnable_10);
//                                            updateFinishMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
//                                            handler_10.removeCallbacks(runnable_10);
//                                        }
//                                        break;
//
//                                }
//
//                            }
//                        });
//
//                        quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//
//                        quitDialog.show();
//                        // =============================================================================
//                    } else if (which == 1) {
//                        if (IsQR) {
//                            getQR(getDocNo(), "w1", "");
//                        } else {
//                            startMachine(getDocNo());
//                        }
//                    } else if (which == 2) {
//                        // =============================================================================
//
//                        switch (Machine_Number) {
//                            case 1:
//
//                                if (MACHINE_1.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_1.removeCallbacks(runnable_1);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
//
//                                    handler_1.removeCallbacks(runnable_1);
//                                }
//
//                                break;
//
//                            case 2:
//
//                                if (MACHINE_2.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_2.removeCallbacks(runnable_2);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
//
//                                    handler_2.removeCallbacks(runnable_2);
//                                }
//
//                                break;
//
//                            case 3:
//
//                                if (MACHINE_3.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_3.removeCallbacks(runnable_3);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
//
//                                    handler_3.removeCallbacks(runnable_3);
//                                }
//
//                                break;
//
//                            case 4:
//
//                                if (MACHINE_4.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_4.removeCallbacks(runnable_4);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
//
//                                    handler_4.removeCallbacks(runnable_4);
//                                }
//
//                                break;
//
//                            case 5:
//
//                                if (MACHINE_5.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_5.removeCallbacks(runnable_5);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
//
//                                    handler_5.removeCallbacks(runnable_5);
//                                }
//
//                                break;
//                            case 6:
//
//                                if (MACHINE_6.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_6.removeCallbacks(runnable_6);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
//
//                                    handler_6.removeCallbacks(runnable_6);
//                                }
//
//                                break;
//
//                            case 7:
//
//                                if (MACHINE_7.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_7.removeCallbacks(runnable_7);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
//
//                                    handler_7.removeCallbacks(runnable_7);
//                                }
//
//                                break;
//
//                            case 8:
//
//                                if (MACHINE_8.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_8.removeCallbacks(runnable_8);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
//
//                                    handler_8.removeCallbacks(runnable_8);
//                                }
//
//                                break;
//
//                            case 9:
//
//                                if (MACHINE_9.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_9.removeCallbacks(runnable_9);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
//
//                                    handler_9.removeCallbacks(runnable_9);
//                                }
//
//                                break;
//
//                            case 10:
//
//                                if (MACHINE_10.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_10.removeCallbacks(runnable_10);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
//
//                                    handler_10.removeCallbacks(runnable_10);
//                                }
//
//                                break;
//
//                        }
//                        // =============================================================================
//                    } else {
//                        // Exit
//                        return;
//                    }
//                }
//            });
//
//            final AlertDialog dialog = builder.create();
//            dialog.show();
//        }else {
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdWash.this, android.R.layout.select_dialog_item, isPause(Machine_Number) ? option_admin : option_2_admin);
//            AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
//            builder.setTitle(Cons.TITLE);
//            builder.setIcon(R.drawable.pose_favicon_2x);
//            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    // ListView Clicked item value
//                    if (which == 0) {
//                        if (IsQR) {
//                            getQR(getDocNo(), "w1", "");
//                        } else {
//                            startMachine(getDocNo());
//                        }
//                    } else if (which == 1) {
//                        // =============================================================================
//
//                        switch (Machine_Number) {
//                            case 1:
//
//                                if (MACHINE_1.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_1.removeCallbacks(runnable_1);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_1.get(i).getDocNo());
//
//                                    handler_1.removeCallbacks(runnable_1);
//                                }
//
//                                break;
//
//                            case 2:
//
//                                if (MACHINE_2.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_2.removeCallbacks(runnable_2);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_2.get(i).getDocNo());
//
//                                    handler_2.removeCallbacks(runnable_2);
//                                }
//
//                                break;
//
//                            case 3:
//
//                                if (MACHINE_3.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_3.removeCallbacks(runnable_3);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_3.get(i).getDocNo());
//
//                                    handler_3.removeCallbacks(runnable_3);
//                                }
//
//                                break;
//
//                            case 4:
//
//                                if (MACHINE_4.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_4.removeCallbacks(runnable_4);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_4.get(i).getDocNo());
//
//                                    handler_4.removeCallbacks(runnable_4);
//                                }
//
//                                break;
//
//                            case 5:
//
//                                if (MACHINE_5.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_5.removeCallbacks(runnable_5);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_5.get(i).getDocNo());
//
//                                    handler_5.removeCallbacks(runnable_5);
//                                }
//
//                                break;
//                            case 6:
//
//                                if (MACHINE_6.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_6.removeCallbacks(runnable_6);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_6.get(i).getDocNo());
//
//                                    handler_6.removeCallbacks(runnable_6);
//                                }
//
//                                break;
//
//                            case 7:
//
//                                if (MACHINE_7.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_7.removeCallbacks(runnable_7);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_7.get(i).getDocNo());
//
//                                    handler_7.removeCallbacks(runnable_7);
//                                }
//
//                                break;
//
//                            case 8:
//
//                                if (MACHINE_8.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_8.removeCallbacks(runnable_8);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_8.get(i).getDocNo());
//
//                                    handler_8.removeCallbacks(runnable_8);
//                                }
//
//                                break;
//
//                            case 9:
//
//                                if (MACHINE_9.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_9.removeCallbacks(runnable_9);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_9.get(i).getDocNo());
//
//                                    handler_9.removeCallbacks(runnable_9);
//                                }
//
//                                break;
//
//                            case 10:
//
//                                if (MACHINE_10.get(i).getIsPause().equals("1")) {
//
//                                    updateRunMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
//                                } else {
//                                    // Stop handler
//                                    handler_10.removeCallbacks(runnable_10);
//
//                                    // Finish Update IsStatus
//                                    updatePauseMachine(Machine_Number, MACHINE_10.get(i).getDocNo());
//
//                                    handler_10.removeCallbacks(runnable_10);
//                                }
//
//                                break;
//
//                        }
//                        // =============================================================================
//                    } else {
//                        // Exit
//                        return;
//                    }
//                }
//            });
//
//            final AlertDialog dialog = builder.create();
//            dialog.show();
//        }
    }

    private void openDialog() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdWash.this, android.R.layout.select_dialog_item, option_3 );

        AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
        //builder.setIcon(R.drawable.message);
        builder.setTitle(Cons.TITLE);
        builder.setIcon(R.drawable.pose_favicon_2x);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (which == 0) {
                    if (MODEL_WASH_DETAIL == null || MODEL_WASH_DETAIL.size() < 1) {
                        Toast.makeText(CssdWash.this, "ไม่มีรายการล้าง!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (IsQR) {
                        getQR(getDocNo(), "w1", "");
                    } else {
                        startMachine(getDocNo());
                    }
                } else {
                    return;
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void alertMachineBusy(String MachineName){
        disableButtonAictive();
        Toast.makeText(CssdWash.this, "เครื่อง " + MachineName + " กำลังทำงาน !!", Toast.LENGTH_SHORT).show();
    }

    private void updateStartMachine(){
        if( MODEL_WASH_DETAIL == null || MODEL_WASH_DETAIL.size() < 1 ){
            Toast.makeText(CssdWash.this, "ไม่มีรายการล้าง!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkMachineActive()) {
            AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdWash.this);
            quitDialog.setTitle(Cons.TITLE);
            quitDialog.setIcon(R.drawable.pose_favicon_2x);
            quitDialog.setMessage(Cons.CONFIRM_RUNNING);
            quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String DocNo = getDocNo();
                    if (DocNo == null || DocNo.equals("-") || DocNo.equals("")){
                        Toast.makeText(CssdWash.this, "ไม่พบเอกสารการล้าง !!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        if (IsQR) {
                            getQR(DocNo, "w1", "");
//                            if (occupancy_rate.getText().toString().equals("0 %")){
//                                Toast.makeText(CssdWash.this, "กรุณากรอกอัตราเต็มตู้ต่อรอบ !!", Toast.LENGTH_SHORT).show();
//                            }else {
//                                getQR(DocNo, "w1", "");
//                            }
                        } else {
                            startMachine(DocNo);
                        }
                    }catch(Exception e){
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

        }else{
            Toast.makeText(CssdWash.this, "เครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
        }

    }

    // =============================================================================================
    // -- Generate Program ...
    // =============================================================================================

    public void generateProgram() {

        class Program extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    List<ModelWashProcess> list = new ArrayList<>();

                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            list.add(
                                    new ModelWashProcess(
                                            c.getString("ID"),
                                            c.getString("WashingProcess"),
                                            c.getString("IsCancel"),
                                            c.getString("ProgramR"),
                                            c.getString("IsProgramTest"),
                                            i
                                    )
                            );
                        }
                    }

                    MODEL_WASH_PROGRAM = list;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<ModelWashProcess> DATA_MODEL = MODEL_WASH_PROGRAM;

                Iterator i = DATA_MODEL.iterator();

                while(i.hasNext()) {
                    try {
                        ModelWashProcess m = (ModelWashProcess) i.next();

                        // New Data Wash Model
                        newWashProcess(m.getID(), m.getWashingProcess(), (m.isCancel() ? "1" : "0") , Integer.toString(m.getProgramR()), m.getIsProgramTest(),m.getIndex()+1);

                        // New Image Process
                        newProcess(m.getIndex()+1, m.getID(), m.getProgramR());

                        // Hide Button Process
                        hideProcess(m.getIndex()+1, m.isCancel());

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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_wash_program.php", data);

                return result;
            }

            // =========================================================================================
        }

        Program obj = new Program();
        obj.execute();
    }

    // =========================================================================================
    // Form
    private void displayWash(
            String DocNo,
            String DocDate,
            String Machine,
            String Round,
            String wash_process_id,
            String wash_process_name,
            String FinishTime,
            String TestProgramID,
            String TestProgramName,
            String WashTypeID,
            String WashTypeName
    ) {
        txt_doc_no.setText(DocNo);
        txt_doc_date.setText(DocDate);
        txt_machine.setText(Machine);
        txt_round.setText(Round);
        txt_finish.setText(FinishTime);

        txt_wash_process.setContentDescription(wash_process_id);
        txt_wash_process.setText(wash_process_name);

        txt_test_program.setContentDescription(TestProgramID);
        txt_wash_type.setContentDescription(WashTypeID);
        edit_wash_type.setContentDescription(WashTypeID);

        txt_test_program.setText(TestProgramName);
        txt_wash_type.setText(WashTypeName);
        edit_wash_type.setText(WashTypeName);
    }


    private void clearForm(){
        list_item_detail.setAdapter(null);
        list_wash_detail.setAdapter(null);
        grid_wash_detail.setAdapter(null);
        txt_wash_process.setContentDescription("");
        txt_wash_process.setText("");
        txt_doc_no.setText("");
        txt_doc_date.setText("");
        txt_machine.setText("");
        txt_round.setText("");
        txt_finish.setText("");
        txt_test_program.setContentDescription("");
        occupancy_rate.setContentDescription("");
        txt_wash_type.setContentDescription("");
        edit_wash_type.setContentDescription("");
        txt_test_program.setText("");
        txt_wash_type.setText("");
        occupancy_rate.setText("");
        edit_wash_type.setText("");
    }

    public void openDialogWashManagement(final String itemcode,final String itemname) {
        final String[] option_wash_detail = new String[]{"แสดง Usagecode"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdWash.this, android.R.layout.select_dialog_item, option_wash_detail );
        AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
        builder.setIcon(R.drawable.pose_favicon_2x);
        builder.setTitle(Cons.TITLE);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // ListView Clicked item value
                if (which == 0) {

                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
    // =========================================================================================
    // 1 : N
    private void onImport(){
        final  String p_WashTypeID = txt_wash_type.getContentDescription().toString();
        //String DocNo = txt_doc_no.getText().toString();
        String DocNo = getDocNo();
        if(checkWash()) {
            if(checkProgram()) {
                final String DOC_NO = getDocNo();
                if(!p_WashTypeID.equals("")) {
                    if (DocNo.equals(null) || DocNo.equals("-")) {
                        if (WASH == true){
                            WASH = false;
                            createWash();
                        }
                    } else {
                        addWashDetail();
                    }
                }else {
                    Toast.makeText(CssdWash.this, "ยังไม่ได้เลือกประเภทการล้าง !!", Toast.LENGTH_SHORT).show();
                }
            }else{
                AlertDialog.Builder quitDialog  = new AlertDialog.Builder(CssdWash.this);
                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setIcon(R.drawable.pose_favicon_2x);
                quitDialog.setMessage(Cons.CONFIRM_IMPORT_WASH);
                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String DocNo = txt_doc_no.getText().toString();
                        if(DocNo.equals("")){
                            createWash();
                        }else{
                            addWashDetail();
                        }
                    }});

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt_wash_process.setText("");
                        txt_wash_process.setContentDescription("");
                    }
                });
                quitDialog.show();
            }
        }
    }

    public void importSendSterileDetail(String Id, String WashProcessId, String WashProcessName){
        onImport(Id, WashProcessId, WashProcessName,"");
    }

    // 1 : 1
    private void onImport(final String Id, final String WashProcessId, final String WashProcessName,final String gQty){
        // Check Sterile Process
        if (WASH_MACHINE_NUMBER_ACTIVE == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
            builder.setTitle(Cons.TITLE);
            builder.setIcon(R.drawable.pose_favicon_2x);
            builder.setMessage("ยังไม่ได้เลือกเครื่องล้าง!!");
            builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return;
        }
        // Check Machine Active (Run)
        if(!checkMachineActive()) {
            Toast.makeText(CssdWash.this, "ไม่สามารถเพิ่มได้ เนื่องจากเครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkProgram(WashProcessId, WashProcessName)) {
            String DocNo = txt_doc_no.getText().toString();
            final  String p_WashTypeID = txt_wash_type.getContentDescription().toString();
            if(!p_WashTypeID.equals("")) {
                if (!DocNo.equals("")) {
                    onAddWashDetail(DocNo,Id,gQty);
                } else {
                    createWash(Id,gQty);
                }
            }else {
                Toast.makeText(CssdWash.this, "ยังไม่ได้เลือกประเภทการล้าง !!", Toast.LENGTH_SHORT).show();
            }
        }else{
            AlertDialog.Builder quitDialog  = new AlertDialog.Builder(CssdWash.this);
            quitDialog.setTitle(Cons.TITLE);
            quitDialog.setMessage(Cons.CONFIRM_IMPORT_WASH);
            quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String DocNo = txt_doc_no.getText().toString();
                    final  String p_WashTypeID = txt_wash_type.getContentDescription().toString();
                    if(!p_WashTypeID.equals("")) {
                        if (DocNo.equals("")) {
                            createWash(Id,gQty);
                        } else {
                            onAddWashDetail(DocNo, Id,gQty);
                        }
                    }else {
                        Toast.makeText(CssdWash.this, "ยังไม่ได้เลือกประเภทการล้าง !!", Toast.LENGTH_SHORT).show();
                    }
                }});

            quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    txt_wash_process.setText("");
                    txt_wash_process.setContentDescription("");
                }
            });

            quitDialog.show();
        }
    }

    private void addWashDetail(){
        final String DOC_NO = getDocNo();

        if(DOC_NO == null){
            return ;
        }
        if(checkMachineActive()) {
            try {
                String LIST_ID = null;
                String DATA = "";
                // =========================================================================================
                List<ModelSendSterileDetail> DATA_MODEL = MODEL_SEND_STERILE_DETAIL;
                Iterator li = DATA_MODEL.iterator();
                while(li.hasNext()) {
                    try {
                        ModelSendSterileDetail m = (ModelSendSterileDetail) li.next();
                        LIST_ID = m.getI_id();
                        DATA += LIST_ID + "@";
                    }catch(Exception e){
                        continue;
                    }
                }
                onAddWashDetail(getDocNo(), DATA,"");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(CssdWash.this, "ไม่สามารถเพิ่มรายการได้ เนื่องจากเครื่องกำลังทำงานอยู่ !!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWash(){
        if (WASH_MACHINE_NUMBER_ACTIVE == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
            builder.setTitle(Cons.TITLE);
            builder.setIcon(R.drawable.pose_favicon_2x);
            builder.setMessage("ยังไม่ได้เลือกเครื่องล้าง!!");
            builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return false;
        }else{
            return true;
        }
    }

    private boolean checkProgram(String WashProcessId, String WashProcessName){
        try {
            String WashProcessId_ = txt_wash_process.getContentDescription().toString();
            if(WashProcessId_.equals("") || WashProcessId_.equals("-") || WashProcessId_ == null){
                txt_wash_process.setText(WashProcessName);
                txt_wash_process.setContentDescription(WashProcessId);
                return true;
            }
            return WashProcessId_.equals(WashProcessId);
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    public void openDialogBasketManagement(){

        if(MODEL_WASH_DETAIL == null){
            return;
        }

        if(MODEL != null) {
            MODEL.clear();
        }

        if (checkMachineActive()) {

            final Dialog dialog = new Dialog(CssdWash.this, R.style.DialogCustomTheme);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.dialog_item_stock_detail_basket);

            dialog.setCancelable(true);

            dialog.setTitle("");

            final ListView list_basket = (ListView) dialog.findViewById(R.id.list_basket);
            final ListView list = (ListView) dialog.findViewById(R.id.list);

            final Button btn_save = (Button) dialog.findViewById(R.id.btn_save);
            final Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            final Button btn_remove = (Button) dialog.findViewById(R.id.btn_remove);
            final EditText edt_basket_code = (EditText) dialog.findViewById(R.id.edt_basket_code);

            //edt_basket_code.setShowSoftInputOnFocus(false);

            btn_save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    onPairBasket();

                    dialog.dismiss();

                }
            });

            btn_remove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CssdWash.this);
                    builder.setTitle(Cons.TITLE);
                    builder.setIcon(R.drawable.pose_favicon_2x);
                    builder.setMessage(Cons.CONFIRM_DELETE);
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface d, int which) {
                            onRemoveBasket();

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();

                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
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

                                selectBasket(edt_basket_code.getText().toString(), list_basket, edt_basket_code);

                                return true;
                            default:
                                break;
                        }
                    }

                    return false;

                }


            });

            list.setAdapter(new WashDetailBasketAdapter(CssdWash.this, MODEL_WASH_DETAIL));

            dialog.show();

            //edt_basket_code.setShowSoftInputOnFocus(false);

            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    edt_basket_code.requestFocus();
                }

            }, 1000);


        }else{
            Toast.makeText(CssdWash.this, "ไม่สามารถผูกตะกร้าได้ !!", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectBasket(final String BasketCode, final ListView list_basket, final EditText edt_basket_code) {


        class Basket extends AsyncTask<String, Void, String> {

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

                    //List<Model> list = new ArrayList<>();

                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);

                        if(c.getString("result").equals("A")&&c.getString("BasketType").equals("0")) {

                            if(MODEL != null) {
                                Iterator li = MODEL.iterator();

                                while (li.hasNext()) {

                                    Model m = (Model) li.next();

                                    if (c.getString("BasketCode").equals(m.getCode())) {
                                        Toast.makeText(CssdWash.this, "มีตะกร้าอยู่แล้ว !!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }else{
                                MODEL = new ArrayList<>();
                            }

                            MODEL.add(
                                    new Model(
                                            0,
                                            c.getString("ID"),
                                            c.getString("BasketCode"),
                                            c.getString("BasketName")
                                    )
                            );

                            list_basket.setAdapter(new BasketAdapter(CssdWash.this, MODEL));

                        }else{
                            Toast.makeText(CssdWash.this, "ไม่พบตะกร้า !!", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    edt_basket_code.setText("");

                    edt_basket_code.requestFocus();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("BasketCode", BasketCode);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_basket.php", data);

                return result;
            }

            // =========================================================================================
        }

        Basket obj = new Basket();
        obj.execute();
    }

    public void onRemoveBasket(){

        String basket = "";
        String wash_detail_id = "";

        // Get Basket
        if(MODEL != null) {
            Iterator li = MODEL.iterator();

            while (li.hasNext()) {

                Model m = (Model) li.next();

                basket += m.getId() + "@";
            }
        }

        // Get Wash Detail
        if(MODEL_WASH_DETAIL != null) {
            Iterator li = MODEL_WASH_DETAIL.iterator();

            while (li.hasNext()) {

                ModelWashDetail m = (ModelWashDetail) li.next();

                wash_detail_id += m.isCheck() ? ( m.getID() + "@" ) : "";
            }
        }

        if(wash_detail_id.equals("")){
            Toast.makeText(CssdWash.this, "ไม่ได้เลือกรายการ !!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String p_basket = basket;
        final String p_wash_detail_id = wash_detail_id;
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
                            displayWashDetail(getDocNo());
                        }

                        Toast.makeText(CssdWash.this, c.getString("Message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_wash_detail_id", p_wash_detail_id);

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

    public void onPairBasket(){

        String basket = "";
        String wash_detail_id = "";

        // Get Basket
        if(MODEL != null) {
            Iterator li = MODEL.iterator();

            while (li.hasNext()) {

                Model m = (Model) li.next();

                basket += m.getId() + "@";
            }
        }

        if(basket.equals("")){
            Toast.makeText(CssdWash.this, "ไม่มีรายการตะกร้า !!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get Wash Detail
        if(MODEL_WASH_DETAIL != null) {
            Iterator li = MODEL_WASH_DETAIL.iterator();

            while (li.hasNext()) {

                ModelWashDetail m = (ModelWashDetail) li.next();

                wash_detail_id += m.isCheck() ? ( m.getID() + "@" + m.getItemStockID() + "@" ) : "";
            }
        }

        if(wash_detail_id.equals("")){
            Toast.makeText(CssdWash.this, "ไม่ได้เลือกรายการ !!", Toast.LENGTH_SHORT).show();
            return;
        }

        final String p_basket = basket;
        final String p_wash_detail_id = wash_detail_id;

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
                            displayWashDetail(getDocNo());
                        }
                        Toast.makeText(CssdWash.this, c.getString("Message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_basket", p_basket);
                data.put("p_wash_detail_id", p_wash_detail_id);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_pair_basket.php", data);

                Log.d("ttest_pair_basket",data+"");

                return result;
            }

            // =========================================================================================
        }

        PairBasket obj = new PairBasket();
        obj.execute();

    }

    private boolean checkProgram(){
        try {
            List<ModelSendSterileDetail> DATA_MODEL = MODEL_SEND_STERILE_DETAIL;
            Iterator li = DATA_MODEL.iterator();
            String program_id_tmp = null;
            String program_id = null;
            String program_id_name = null;
            while(li.hasNext()) {
                try {
                    ModelSendSterileDetail m = (ModelSendSterileDetail) li.next();
                    program_id = m.getI_program_id();
                    program_id_name = m.getI_program();
                    if(program_id_tmp != null && !program_id_tmp.equals(program_id) ){
                        return false;
                    }
                    program_id_tmp = program_id;
                }catch(Exception e){
                    return false;
                }
            }
            txt_wash_process.setText(program_id_name);
            txt_wash_process.setContentDescription(program_id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    // =============================================================================================
    public void removeWashDetail(
            String LIST_IMPORT_ID,
            String LIST_ID,
            String LIST_ITEM_STOCK_ID
    ){
        if(checkMachineActive()) {
            try {
                String DATA = LIST_ID + "@" + LIST_IMPORT_ID + "@" + LIST_ITEM_STOCK_ID + "@";
                removeWashDetail(DATA);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(CssdWash.this, "ไม่สามารถลบรายการได้!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeWashtagInWashDetail(
            String DATA
    ){
        if(checkMachineActive()) {
            try {
                removeWashDetail(DATA);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(CssdWash.this, "ไม่สามารถลบรายการได้!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onRemoveAllWashDetail(){
        try {
            List<ModelWashDetail> DATA_MODEL = MODEL_WASH_DETAIL;
            Iterator li = DATA_MODEL.iterator();
            String LIST_IMPORT_ID;
            String LIST_ID;
            String LIST_ITEM_STOCK_ID;
            String DATA = "";
            while(li.hasNext()) {
                try {
                    ModelWashDetail m = (ModelWashDetail) li.next();
                    LIST_ID = m.getID();
                    LIST_IMPORT_ID = m.getImportID();
                    LIST_ITEM_STOCK_ID = m.getItemStockID();
                    DATA += LIST_ID + "@" + LIST_IMPORT_ID + "@" + LIST_ITEM_STOCK_ID + "@";
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            removeWashDetail(DATA);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // =============================================================================================
    // ---------------------------------------------------------------------------------------------
    // Dropdown list
    private void openDropDown(String data, String filter){
        Intent i = new Intent(CssdWash.this, MasterDropdown.class);
        i.putExtra("data", data);
        i.putExtra("filter", filter);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, Master.getResult(data));
    }

    private void openDropDown1(String data, String filter){
        Intent i = new Intent(CssdWash.this, MasterDropdown1.class);
        i.putExtra("data", data);
        i.putExtra("filter", filter);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, 28);
    }


    private void openSendSterile(){
        Intent i = new Intent(CssdWash.this, SendSterile_MainActivity.class);
        i.putExtra("userid", userid);
        i.putExtra("user_name", user_name);
        i.putExtra("IsAdmin", IsAdmin);
        i.putExtra("EmpCode", EmpCode);
        Log.d("FKJDHJKDH",EmpCode+"");
        i.putExtra("B_ID", B_ID);
        startActivity(i);
        clearForm();
        clearMachine(1);
        clearMachine(2);
        clearMachine(3);
        clearMachine(4);
        clearMachine(5);
        clearMachine(6);
        clearMachine(7);
        clearMachine(8);
        clearMachine(9);
        clearMachine(10);
        handler_1 . removeCallbacks(runnable_1);
        handler_2 . removeCallbacks(runnable_2);
        handler_3 . removeCallbacks(runnable_3);
        handler_4 . removeCallbacks(runnable_4);
        handler_5 . removeCallbacks(runnable_5);
        handler_6 . removeCallbacks(runnable_6);
        handler_7 . removeCallbacks(runnable_7);
        handler_8 . removeCallbacks(runnable_8);
        handler_9 . removeCallbacks(runnable_9);
        handler_10 . removeCallbacks(runnable_10);
        defaultTabMachine();
        hideMachineAll();
        list_import_send_sterile.setAdapter(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            String RETURN_DATA = null;
            String RETURN_VALUE = null;
            try {
                RETURN_DATA = data.getStringExtra("RETURN_DATA");
                RETURN_VALUE = data.getStringExtra("RETURN_VALUE");
            }catch(Exception e){
                return;
            }
            if (resultCode == Master.washprocess) {
                txt_wash_process.setText(RETURN_DATA);
                txt_wash_process.setContentDescription(RETURN_VALUE);
            }else if (resultCode == 28) {
                occupancy_rate.setText(RETURN_DATA);
//                occupancy_rate.setContentDescription(RETURN_VALUE);

//                updateWash(Master.occupancy_rate, RETURN_VALUE, getDocNo());

            }else if (resultCode == Master.wash_type) {
                txt_wash_type.setText(RETURN_DATA);
                txt_wash_type.setContentDescription(RETURN_VALUE);
                edit_wash_type.setText(RETURN_DATA);
                edit_wash_type.setContentDescription(RETURN_VALUE);
                updateWash(Master.wash_type, RETURN_VALUE, getDocNo());
            }else if (resultCode == Master.wash_test_program) {
                txt_test_program.setText(RETURN_DATA);
                txt_test_program.setContentDescription(RETURN_VALUE);
                updateWash(Master.wash_test_program, RETURN_VALUE, getDocNo());
            }else if (resultCode == Master.open_send_sterile ) {
                list_import_send_sterile.setAdapter(null);
                grid_import_send_sterile.setAdapter(null);
            }else if(resultCode == Master.add_wash_detail){
            }else if (resultCode == 1035 ){
                String Data = data.getStringExtra("RETURN_DATA");
                String Selected = data.getStringExtra("RETURN_xsel");
                String DocNo = data.getStringExtra("RETURN_DocNo");
                String MacNo = data.getStringExtra("RETURN_MacNo");
                if(Data.equals("true")){
                    if(Selected.equals("w1")){
                        Toast.makeText(this, "บันทึก", Toast.LENGTH_SHORT).show();
                        startMachine(DocNo);
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
                            case "6":
                                updateFinishMachine(6, MACHINE_6.get(i).getDocNo());
                                handler_6.removeCallbacks(runnable_6);
                                clearForm();
                                break;
                            case "7":
                                updateFinishMachine(7, MACHINE_7.get(i).getDocNo());
                                handler_7.removeCallbacks(runnable_7);
                                clearForm();
                                break;
                            case "8":
                                updateFinishMachine(8, MACHINE_8.get(i).getDocNo());
                                handler_8.removeCallbacks(runnable_8);
                                clearForm();
                                break;
                            case "9":
                                updateFinishMachine(9, MACHINE_9.get(i).getDocNo());
                                handler_9.removeCallbacks(runnable_9);
                                clearForm();
                                break;
                            case "10":
                                updateFinishMachine(10, MACHINE_10.get(i).getDocNo());
                                handler_10.removeCallbacks(runnable_10);
                                clearForm();
                                break;
                        }
                    }else{
                    }
                }else{
                    Toast.makeText(this, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            list_import_send_sterile.setAdapter(null);
            grid_import_send_sterile.setAdapter(null);
            return;
        }
    }
    // =============================================================================================
    // -- Generate Machine ...
    // =============================================================================================
    private int MAX_WASH_MACHINE = 0;
    private List<ModelWashMachine> MODEL_WASH_MACHINE = null;
    public void generateMachine(final String p_wash_process_id) {
        clearForm();
        WASH_MACHINE_NUMBER_ACTIVE = 0;
        MAX_WASH_MACHINE = 0;
        class GenerateMachine extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                List<ModelWashMachine> list = new ArrayList<>();
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            list.add(
                                    new ModelWashMachine(
                                            c.getString("ID"),
                                            c.getString("MachineName"),
                                            c.getString("IsActive"),
                                            c.getString("DocNo"),
                                            c.getString("StartTime"),
                                            c.getString("FinishTime"),
                                            c.getString("PauseTime"),
                                            c.getString("IsPause"),
                                            c.getString("CountTime"),
                                            c.getString("TestProgramID"),
                                            c.getString("WashTypeID"),
                                            c.getString("TestProgramName"),
                                            c.getString("WashTypeName"),
                                            i
                                    )
                            );
                            MAX_WASH_MACHINE =  i+1;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MODEL_WASH_MACHINE = list;
                hideMachine();
                List<ModelWashMachine> DATA_MODEL = MODEL_WASH_MACHINE;
                Iterator i = DATA_MODEL.iterator();
                while (i.hasNext()) {
                    try {
                        ModelWashMachine m = (ModelWashMachine) i.next();
                        newMachine(
                                m.getID(),
                                m.getMachineName(),
                                m.getIsActive(),
                                m.getDocNo(),
                                m.getStartTime(),
                                m.getFinishTime(),
                                m.getPauseTime(),
                                m.getIsPause(),
                                m.getCountTime(),
                                m.getTestProgramID(),
                                m.getWashTypeID(),
                                m.getTestProgramName(),
                                m.getWashTypeName(),
                                m.getIndex() + 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                defaultTabMachine();
                startMachine();
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_wash_process_id", p_wash_process_id);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_wash_machine_json.php", data);
                return result;
            }
            // =========================================================================================
        }
        GenerateMachine obj = new GenerateMachine();
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

                //if(Success && data != null) {
                int MachineNo = 0;

                try {
                    MachineNo = Integer.valueOf(data.get(0)).intValue();
                }catch(Exception e){
                    e.printStackTrace();
                    return ;
                }

                // New Button Sterile Machine (Set Machine Data)
                newMachine(
                        getMachineId(MachineNo),
                        getMachineName(MachineNo),
                        "0",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        MachineNo);

                // Clear Machine
                clearMachine(MachineNo);

                //}
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_doc_no);
                data.put("p_machine_no", Integer.toString(p_machine_no));

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_finish_time_.php", data);

                return result;
            }

            // =========================================================================================
        }

        UpdateFinishMachine obj = new UpdateFinishMachine();
        obj.execute();
    }

    // =============================================================================================
    // -- Start Machine ...
    // =============================================================================================

    public void startMachine(final String p_doc_no) {
        final String wash_process = txt_wash_process.getContentDescription().toString();

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
                        MODEL_WASH_MACHINE = getModelWashMachine();
                        MAX_WASH_MACHINE = MODEL_WASH_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelWashMachine> DATA_MODEL = MODEL_WASH_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelWashMachine m = (ModelWashMachine) i.next();

                            // New Button Sterile Machine (Set Machine Data)
                            newMachine(
                                    m.getID(),
                                    m.getMachineName(),
                                    m.getIsActive(),
                                    m.getDocNo(),
                                    m.getStartTime(),
                                    m.getFinishTime(),
                                    m.getPauseTime(),
                                    m.getIsPause() ,
                                    m.getCountTime(),
                                    m.getTestProgramID(),
                                    m.getWashTypeID(),
                                    m.getTestProgramName(),
                                    m.getWashTypeName(),
                                    WASH_MACHINE_NUMBER_ACTIVE
                            );

                            // Set Time
                            txt_finish.setText(m.getFinishTime().substring(11, 16));

                            // Refresh Detail
                            displayWashDetail(m.getDocNo());

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }

                    startMachineActive();

                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(IsUsedProcessTimeByWashType) {
                    data.put("p_process_time_by_wash_type", "1");
                }

                data.put("p_docno", p_doc_no);
                data.put("p_WashProcessID", wash_process );

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                data.put("OccupancyRate",occupancy_rate.getText().toString());

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_start_time_.php", data);

                Log.d("BANKM7",data+"");
                Log.d("BANKM7",result);

                return result;
            }

            private List<ModelWashMachine> getModelWashMachine() throws Exception{

                List<ModelWashMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getWashMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        "",
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
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

                    try {
                        MODEL_WASH_MACHINE = getModelWashMachine();
                        MAX_WASH_MACHINE = MODEL_WASH_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelWashMachine> DATA_MODEL = MODEL_WASH_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelWashMachine m = (ModelWashMachine) i.next();

                            newMachine(
                                    m.getID(),
                                    m.getMachineName(),
                                    m.getIsActive(),
                                    m.getDocNo(),
                                    m.getStartTime(),
                                    m.getFinishTime(),
                                    m.getPauseTime(),
                                    m.getIsPause() ,
                                    m.getCountTime(),
                                    m.getTestProgramID(),
                                    m.getWashTypeID(),
                                    m.getTestProgramName(),
                                    m.getWashTypeName(),
                                    WASH_MACHINE_NUMBER_ACTIVE
                            );

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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_pause_time_.php", data);

                //System.out.println("URL = " + result);

                return result;
            }

            private List<ModelWashMachine> getModelWashMachine() throws Exception{

                List<ModelWashMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getWashMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        "",
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
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
                        MODEL_WASH_MACHINE = getModelWashMachine();
                        MAX_WASH_MACHINE = MODEL_WASH_MACHINE.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelWashMachine> DATA_MODEL = MODEL_WASH_MACHINE;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelWashMachine m = (ModelWashMachine) i.next();

                            // New Button Sterile Machine (Set Machine Data)
                            newMachine(
                                    m.getID(),
                                    m.getMachineName(),
                                    m.getIsActive(),
                                    m.getDocNo(),
                                    m.getStartTime(),
                                    m.getFinishTime(),
                                    m.getPauseTime(),
                                    m.getIsPause() ,
                                    m.getCountTime(),
                                    m.getTestProgramID(),
                                    m.getWashTypeID(),
                                    m.getTestProgramName(),
                                    m.getWashTypeName(),
                                    WASH_MACHINE_NUMBER_ACTIVE
                            );

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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_run_time_.php", data);

                return result;
            }

            private List<ModelWashMachine> getModelWashMachine() throws Exception{

                List<ModelWashMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getWashMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        "",
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
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
    // -- Display Wash
    // =============================================================================================

    public void onDisplay(final String p_docno) {

        class DisplayWash extends AsyncTask<String, Void, String> {

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
                        MODEL_WASH = getModelWash();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelWash> DATA_MODEL = MODEL_WASH;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelWash m = (ModelWash) i.next();

                            // Display Wash
                            displayWash(
                                    m.getDocNo(),
                                    m.getDocDate(),
                                    m.getMachineName(),
                                    m.getWashRoundNumber(),
                                    m.getWashProgramID(),
                                    m.getWashingProcess(),
                                    m.getFinishTime(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getWashTypeID(),
                                    m.getWashTypeName()
                            );

                            // Display Sterile Detail
                            displayWashDetail( getDocNo() );

                        }catch(Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                }else{
                    clearForm();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_wash.php", data);


                return result;
            }

            private List<ModelWash> getModelWash() throws Exception{

                List<ModelWash> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getWash(
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
                                        index
                                )
                        );

                        index++;
                    }

                    //System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================

            private ModelWash getWash(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String WashProgramID,
                    String WashMachineID,
                    String WashRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String Remark,
                    String WashingProcess,
                    String MachineName,
                    String TestProgramID,
                    String WashTypeID,
                    String TestProgramName,
                    String WashTypeName,
                    int index
            ){
                return new ModelWash(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        WashProgramID,
                        WashMachineID,
                        WashRoundNumber,
                        StartTime,
                        FinishTime,
                        Remark,
                        WashingProcess,
                        MachineName,
                        TestProgramID,
                        WashTypeID,
                        TestProgramName,
                        WashTypeName,
                        index
                );
            }
        }

        DisplayWash obj = new DisplayWash();
        obj.execute();
    }



    // =============================================================================================
    // -- Display Import Send Sterile
    // =============================================================================================

    public void displayImportSendSterile(final String sel) {
        if(WASH_PROCESS_NUMBER_ACTIVE < 1){
            list_import_send_sterile.setAdapter(null);
            grid_import_send_sterile.setAdapter(null);
            return;
        }
        class DisplayImportSendSterile extends AsyncTask<String, Void, String> {
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
                        MODEL_SEND_STERILE_DETAIL = getModelSendSterileDetail();

                        List<ModelSendSterileDetail> MODEL_SEND_STERILE_DETAIL_GROUP_BASKET = new ArrayList<>();
                        MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.clear();

                        for(int i =0;i<MODEL_SEND_STERILE_DETAIL.size();i++){
                            ModelSendSterileDetail x = MODEL_SEND_STERILE_DETAIL.get(i);
                            String key = x.getBasketCode();
                            if(key.equals("-")){ // ไม่อยู่ในตะกร้า
                                MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.add(x);
                            }else{// อยู่ในตะกร้า
                                if(MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.containsKey(key)){ //มีตะกร้าแล้ว
                                    MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.get(key).add(x);
                                }else{ // ยังไม่มีตะกร้า
                                    List<ModelSendSterileDetail> list = new ArrayList<>();
                                    list.add(x);
                                    MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.put(key,list);
                                    ModelSendSterileDetail clone = (ModelSendSterileDetail) x.clone();
                                    clone.setI_name(x.getBasketName());
                                    clone.setUsageCode(x.getBasketCode());
                                    clone.setBasket(true);
                                    MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.add(0,clone);
                                }
                            }
                        }

                        try {
                            ArrayAdapter<ModelSendSterileDetail> adapter;
                            if(DISPLAY_MODE == 3) {
                                adapter = new ImportSendSterileDetailGridViewAdapter(CssdWash.this, MODEL_SEND_STERILE_DETAIL, !checkMachineActive());
                                grid_import_send_sterile.setAdapter(adapter);
                            }else if(DISPLAY_MODE == 2) {
                                adapter = new ImportSendSterileDetailAdapter(CssdWash.this, MODEL_SEND_STERILE_DETAIL_GROUP_BASKET, !checkMachineActive(),MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET);
                                list_import_send_sterile.setAdapter(adapter);
                            }else if(DISPLAY_MODE == 1) {
                                adapter = new ImportSendSterileDetailBigSizeAdapter(CssdWash.this, MODEL_SEND_STERILE_DETAIL, !checkMachineActive());
                                list_import_send_sterile.setAdapter(adapter);
                            }
                        } catch (Exception e) {
                            list_wash_detail.setAdapter(null);
                            grid_wash_detail.setAdapter(null);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    /*
                    try {
                        MODEL_SEND_STERILE_DETAIL = getModelSendSterileDetail();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    try {
                        ArrayAdapter<ModelSendSterileDetail> adapter = new SendSterileDetailMachineAdapter(CssdWash.this, MODEL_SEND_STERILE_DETAIL);
                        list_import_send_sterile.setAdapter(adapter);
                    } catch (Exception e) {
                        list_import_send_sterile.setAdapter(null);
                        grid_import_send_sterile.setAdapter(null);
                        e.printStackTrace();
                    }
                    */
                }else{
                    list_import_send_sterile.setAdapter(null);
                    grid_import_send_sterile.setAdapter(null);
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();

                Log.d("BANKFH",edit_wash_type.getText().toString()+"");
                Log.d("BANKFH",sel+"");

                if (sel.equals("0") && edit_wash_type.getText().toString().equals("")){
                    data.put("p_id", "0");
                }else if (WASH_PROCESS_NUMBER_ACTIVE == 1 && edit_wash_type.getText().toString().equals("")){
                    data.put("p_id", Integer.toString(WASH_PROCESS_NUMBER_ACTIVE));
                }else if (!edit_wash_type.getText().toString().equals("")){
                    data.put("p_id", Integer.toString(WASH_PROCESS_NUMBER_ACTIVE));
                }else if (sel.equals("0") && !edit_wash_type.getText().toString().equals("")){
                    data.put("p_id", Integer.toString(WASH_PROCESS_NUMBER_ACTIVE));
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_import_send_sterile_detail.php", data);
                Log.d("BANKFH",data+"");
                Log.d("BANKFH",result+"");
                return result;
            }
            // =========================================================================================
            private List<ModelSendSterileDetail> getModelSendSterileDetail() throws Exception{

                List<ModelSendSterileDetail> list = new ArrayList<>();

                try {
                    int index = 0;


                    for(int i=0;i<data.size();i+=size){

                        Log.d("tog_get_Model_SS_D","itemcode = "+data.get(i+2));

                        ModelSendSterileDetail x = get(

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
                                index
                        );

                        x.setUsageCode(data.get(i + 13));
                        x.setBasketCode(data.get(i + 14));
                        x.setBasketName(data.get(i + 15));
                        x.setBasket(false);

                        list.add(x);

                        index++;
                    }

                    //System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelSendSterileDetail get(
                    String i_id,
                    String i_code,
                    String i_name,
                    String i_alt_name,
                    String i_barcode,
                    String i_qty,
                    String i_program,
                    String i_program_id,
                    String packingMat,
                    String shelflife,
                    String packingMatID,
                    String IsWashDept,
                    String IsRemarkExpress,
                    int index
            ){
                return new ModelSendSterileDetail(
                        i_id,
                        i_code,
                        i_name,
                        i_alt_name,
                        i_barcode,
                        i_qty,
                        i_program,
                        i_program_id,
                        packingMat,
                        shelflife,
                        packingMatID,
                        IsWashDept,
                        IsRemarkExpress,
                        index
                );
            }
        }
        DisplayImportSendSterile obj = new DisplayImportSendSterile();
        obj.execute();
    }
    // =============================================================================================
    // -- Create Wash 1 : N
    // =============================================================================================

    public void createWash() {
        final  String p_WashProgramID = txt_wash_process.getContentDescription().toString();
        String TestProgramID = txt_test_program.getContentDescription().toString();
        final  String p_TestProgramID = TestProgramID.equals("") ? "0" : TestProgramID ;
        final  String p_WashTypeID = txt_wash_type.getContentDescription().toString();

        class CreateWash_ extends AsyncTask<String, Void, String> {
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
                        MODEL_WASH = getModelWash();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    List<ModelWash> DATA_MODEL = MODEL_WASH;
                    Iterator i = DATA_MODEL.iterator();
                    if(i.hasNext()) {
                        try {

                            ModelWash m = (ModelWash) i.next();
                            // Display Wash
                            displayWash(
                                    m.getDocNo(),
                                    m.getDocDate(),
                                    m.getMachineName(),
                                    m.getWashRoundNumber(),
                                    m.getWashProgramID(),
                                    m.getWashingProcess(),
                                    m.getFinishTime(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getWashTypeID(),
                                    m.getWashTypeName()
                            );
                            // Set Data Machine
                            setMachine(m.getDocNo());
                            if (m.getDocNo().equals("")){
                                WASH = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    // Add Wash Detail
                    addWashDetail();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String p_WashMachineID = getMachineId(WASH_MACHINE_NUMBER_ACTIVE);
                data.put("p_UserCode", userid );
                data.put("p_Qty", "1");
                data.put("p_WashProgramID", p_WashProgramID);
                data.put("p_WashMachineID", p_WashMachineID);
                data.put("p_TestProgramID", p_TestProgramID);
                data.put("p_WashTypeID", p_WashTypeID);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_wash.php", data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return result;
            }
            private List<ModelWash> getModelWash() throws Exception{
                List<ModelWash> list = new ArrayList<>();
                try {
                    int index = 0;
                    for(int i=0;i<data.size();i+=size){
                        list.add(
                                getWash(
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
            private ModelWash getWash(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String WashProgramID,
                    String WashMachineID,
                    String WashRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String Remark,
                    String WashingProcess,
                    String MachineName,
                    String TestProgramID,
                    String WashTypeID,
                    String TestProgramName,
                    String WashTypeName,
                    int index
            ){
                return new ModelWash(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        WashProgramID,
                        WashMachineID,
                        WashRoundNumber,
                        StartTime,
                        FinishTime,
                        Remark,
                        WashingProcess,
                        MachineName,
                        TestProgramID,
                        WashTypeID,
                        TestProgramName,
                        WashTypeName,
                        index
                );
            }
            // =========================================================================================
        }
        CreateWash_ obj = new CreateWash_();
        obj.execute();
    }
    // =============================================================================================
    // -- Create Wash 1 : 1
    // =============================================================================================
    public void createWash(final String Id,final String gQty) {
        final  String p_WashProgramID = txt_wash_process.getContentDescription().toString();
        String TestProgramID = txt_test_program.getContentDescription().toString();
        final  String p_TestProgramID = TestProgramID.equals("") ? "0" : TestProgramID ;
        final  String p_WashTypeID = txt_wash_type.getContentDescription().toString();
        class CreateWash extends AsyncTask<String, Void, String> {

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
                        MODEL_WASH = getModelWash();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    List<ModelWash> DATA_MODEL = MODEL_WASH;

                    Iterator i = DATA_MODEL.iterator();

                    if(i.hasNext()) {
                        try {
                            ModelWash m = (ModelWash) i.next();

                            // Display Wash
                            displayWash(
                                    m.getDocNo(),
                                    m.getDocDate(),
                                    m.getMachineName(),
                                    m.getWashRoundNumber(),
                                    m.getWashProgramID(),
                                    m.getWashingProcess(),
                                    m.getFinishTime(),
                                    m.getTestProgramID(),
                                    m.getTestProgramName(),
                                    m.getWashTypeID(),
                                    m.getWashTypeName()
                            );

                            // Set Data Machine
                            setMachine(m.getDocNo());

                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    // Add Wash Detail
                    onAddWashDetail(getDocNo(), Id + "@",gQty);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();


                String p_WashMachineID = getMachineId(WASH_MACHINE_NUMBER_ACTIVE);

                data.put("p_UserCode", userid);
                data.put("p_Qty", "1");
                data.put("p_WashProgramID", p_WashProgramID);
                data.put("p_WashMachineID", p_WashMachineID);
                data.put("p_TestProgramID", p_TestProgramID);
                data.put("p_WashTypeID", p_WashTypeID);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_wash.php", data);
                Log.d("BANKTTT",data+"");
                Log.d("BANKTTT",result);
                return result;
            }

            private List<ModelWash> getModelWash() throws Exception{

                List<ModelWash> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getWash(
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
                                        index
                                )
                        );

                        index++;
                    }

                    //System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================

            private ModelWash getWash(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String WashProgramID,
                    String WashMachineID,
                    String WashRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String Remark,
                    String WashingProcess,
                    String MachineName,
                    String TestProgramID,
                    String WashTypeID,
                    String TestProgramName,
                    String WashTypeName,
                    int index
            ){
                return new ModelWash(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        WashProgramID,
                        WashMachineID,
                        WashRoundNumber,
                        StartTime,
                        FinishTime,
                        Remark,
                        WashingProcess,
                        MachineName,
                        TestProgramID,
                        WashTypeID,
                        TestProgramName,
                        WashTypeName,
                        index
                );
            }


            // =========================================================================================

        }

        CreateWash obj = new CreateWash();
        obj.execute();

    }

    // =============================================================================================
    // -- Display Wash Detail
    // =============================================================================================

    public void displayWashDetail(final String p_docno) {
        if(p_docno == null || p_docno.equals("-") || p_docno.equals("")){
            return ;
        }

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

                        MODEL_WASH_DETAIL = getModelWashDetail();
//
                        List<ModelWashDetail> MODEL_WASH_DETAIL_GROUP_BASKET = new ArrayList<>();
                        HashMap<String,List<ModelWashDetail>> MAP_MODEL_WASH_DETAIL_GROUP_BASKET = new HashMap<String,List<ModelWashDetail>>();

                        for(int i =0;i<MODEL_WASH_DETAIL.size();i++){
                            ModelWashDetail x = MODEL_WASH_DETAIL.get(i);
                            String key = x.getBasketCode();
                            if(key.equals("-")){ // ไม่อยู่ในตะกร้า
                                MODEL_WASH_DETAIL_GROUP_BASKET.add(x);
                            }else{// อยู่ในตะกร้า
                                if(MAP_MODEL_WASH_DETAIL_GROUP_BASKET.containsKey(key)){ //มีตะกร้าแล้ว
                                    MAP_MODEL_WASH_DETAIL_GROUP_BASKET.get(key).add(x);
                                }else{ // ยังไม่มีตะกร้า
                                    List<ModelWashDetail> list = new ArrayList<>();
                                    list.add(x);
                                    MAP_MODEL_WASH_DETAIL_GROUP_BASKET.put(key,list);
                                    ModelWashDetail clone = (ModelWashDetail) x.clone();
                                    clone.setItemname(x.getBasketName());
                                    clone.setUsageCode(x.getBasketCode());
                                    clone.setBasket(true);
                                    MODEL_WASH_DETAIL_GROUP_BASKET.add(0,clone);
                                }
                            }
                        }

                        try {
                            ArrayAdapter<ModelWashDetail> adapter;
                            if(DISPLAY_MODE == 3) {
                                adapter = new WashDetailGridViewAdapter(CssdWash.this, MODEL_WASH_DETAIL, !checkMachineActive());
                                grid_wash_detail.setAdapter(adapter);
                            }else if(DISPLAY_MODE == 2) {
                                adapter = new WashDetailAdapter(CssdWash.this, MODEL_WASH_DETAIL_GROUP_BASKET, !checkMachineActive(),MAP_MODEL_WASH_DETAIL_GROUP_BASKET);
                                list_wash_detail.setAdapter(adapter);
                            }else if(DISPLAY_MODE == 1) {
                                adapter = new WashDetailBigSizeAdapter(CssdWash.this, MODEL_WASH_DETAIL, !checkMachineActive());
                                list_wash_detail.setAdapter(adapter);
                            }

                        } catch (Exception e) {
                            list_wash_detail.setAdapter(null);
                            grid_wash_detail.setAdapter(null);
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }else{
                    list_wash_detail.setAdapter(null);
                    grid_wash_detail.setAdapter(null);
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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_wash_detail.php", data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return result;
            }

            private List<ModelWashDetail> getModelWashDetail() throws Exception{

                List<ModelWashDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        ModelWashDetail x = new ModelWashDetail(

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
                                index
                        );

                        x.setBasketCode(data.get(i + 16));
                        x.setBasket(false);

                        list.add(x);

                        index++;
                    }

                    //System.out.println("list = " + list.size());

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
    // -- Add Wash Detail ...
    // =============================================================================================

    public void onAddWashDetail(final String p_docno, final String p_data,final String p_Qty) {

        if (p_docno == null || p_docno.equals("-") || p_docno.equals("")) {
            return;
        }

        final String p_WashProgramID = txt_wash_process.getContentDescription().toString();
        class AddWashDetail extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdWash.this);

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

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    try{
                        // Display Wash & Wash Detail
                        displayWashDetail( getDocNo() );
                        WASH = true;
                        //onDisplay( getDocNo() );
                        // Display Import Wash Detail
                        displayImportSendSterile("1");
                        CheckUsageEms(getDocNo());
                        DIALOG_ACTIVE = true;
                        countput = 0;
                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }finally {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String p_WashMachineID = getMachineId(WASH_MACHINE_NUMBER_ACTIVE);
                data.put("p_docno", p_docno);
                data.put("p_data", p_data);
                data.put("p_WashProgramID", p_WashProgramID);
                data.put("p_WashMachineID", p_WashMachineID);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                data.put("p_Qty", p_Qty);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_add_wash_detail.php", data);
                Log.d("BANK",result);
                Log.d("BANK",data+"");
                return result;
            }

            // =========================================================================================
        }

        AddWashDetail obj = new AddWashDetail();
        obj.execute();
    }

    public void importWashDetail(String Id, String WashProcessId, String WashProcessName, String PackingMatID,String gQty){
        if (edit_wash_type.getText().toString().equals("")){
            Toast.makeText(CssdWash.this,"ยังไม่ได้เลือกประเภทการล้าง !!", Toast.LENGTH_SHORT).show();
        }else {
            onImport(Id, WashProcessId, WashProcessName,gQty);
        }

    }

    // =============================================================================================
    // -- Remove Wash Detail
    // =============================================================================================

    public void removeWashDetail(final String p_data) {

        class RemoveWashDetail extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdWash.this);

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

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {

                    try{
                        // Display Wash & Wash Detail
                        displayWashDetail( getDocNo() );

                        // Display Import Wash Detail
                        displayImportSendSterile("1");

                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }finally {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                //System.out.println("p_data = " + p_data);

                data.put("p_data", p_data);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_remove_wash_detail.php", data);

                //System.out.println("URL = " + result);
                Log.d("tog_remove_wash_detail","output = "+result);

                return result;
            }

            // =========================================================================================
        }

        RemoveWashDetail obj = new RemoveWashDetail();
        obj.execute();
    }


    // =========================================================================================
    // Model
    // =========================================================================================
    private ModelWashMachine getWashMachine(
            String ID,
            String MachineName,
            String IsActive,
            String DocNo,
            String StartTime,
            String FinishTime,
            String PauseTime,
            String IsPause,
            String CountTime,
            String TestProgramID,
            String WashTypeID,
            String TestProgramName,
            String WashTypeName,
            int index
    ){
        return new ModelWashMachine(
                ID,
                MachineName,
                IsActive,
                DocNo,
                StartTime,
                FinishTime,
                PauseTime,
                IsPause,
                CountTime,
                TestProgramID,
                WashTypeID,
                TestProgramName,
                WashTypeName,
                index
        );
    }

    // =============================================================================================
    // -- Update Wash
    // =============================================================================================

    private void updateWash(final int field, final String value, final String doc_no) {

        if(doc_no == null || doc_no.equals("-") || doc_no.equals("")) {
            return;
        }

        class Update extends AsyncTask<String, Void, String> {

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

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        Toast.makeText(CssdWash.this, c.getString("Message") , Toast.LENGTH_SHORT).show();

                        break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash.php", data);

                return result;
            }

            // =========================================================================================
        }

        Update obj = new Update();
        obj.execute();
    }

    public void SelectQtyDocNO() {
        class SelectQtyDocNO extends AsyncTask<String, Void, String> {
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

                        QtyWash = c.getString("QTY");

                        if (QtyWash.equals("0")) {
                            if (getDocNo() == null) {
                                Toast.makeText(CssdWash.this, "ไม่มีเอกสารล้าง !!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (!checkMachineActive()) {
                                Toast.makeText(CssdWash.this, "เครื่องกำลังทำงาน!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdWash.this);
                            quitDialog.setTitle(Cons.TITLE);
                            quitDialog.setIcon(R.drawable.pose_favicon_2x);
                            quitDialog.setMessage(Cons.CONFIRM_EXPORT1);
                            quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // ============================================= Clear ==============================================
                                    updateWashMachineClearDocNo(getMachineId(WASH_MACHINE_NUMBER_ACTIVE), WASH_MACHINE_NUMBER_ACTIVE);
                                    // ============================================= Clear ==============================================
                                }
                            });
                            quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            quitDialog.show();
                        }else {
                            Toast.makeText(CssdWash.this, "ไม่สามารถส่งออกเอกสารได้ เนื่องจากมีรหัสใช้งานในเครื่อง !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",getDocNo());
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_qty_washdocno.php", data);
                    Log.d("KHFDD",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        SelectQtyDocNO obj = new SelectQtyDocNO();
        obj.execute();
    }

    public void updateWashMachineClearDocNo(final String id, final int machine_no) {
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
                    newMachine(getMachineId(machine_no), getMachineName(machine_no), "0", null, null,
                            null, null, null,"",null,null,null,null, machine_no);

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
                data.put("DocNo",getDocNo());

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_machine_clear_docno.php", data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return result;
            }

            private List<ModelWashMachine> getModelWashMachine() throws Exception{

                List<ModelWashMachine> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getWashMachine(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        "",
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
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


    private void getQR(String DocNo,String Select, String Machine){
        Intent i = new Intent(CssdWash.this, CheckQR_Approve.class);
        i.putExtra("DocNo", DocNo);
        i.putExtra("xSel", Select);
        i.putExtra("MacNo", Machine);
        i.putExtra("B_ID", B_ID);
        startActivityForResult(i, 1035);
    }

    // open qr
    private void openQR(String data){
        String ProcessId = getProcessId();

        if(ProcessId == null)
            return;

        Intent i = new Intent(CssdWash.this, CssdQr.class);
        i.putExtra("data", data);
        i.putExtra("filter", ProcessId);
        i.putExtra("B_ID", B_ID);

        startActivityForResult(i, Master.getResult(data));
    }

    public void CheckUsageEms(final String DOC_NO) {
        class CheckUsageEms extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdWash.this);
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
                            Intent intent = new Intent(CssdWash.this, dialog_check_usage_count.class);
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

    public void getOccupancyRate(final String DocNo) {
        class getOccupancyRate extends AsyncTask<String, Void, String> {
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
                        OccupancyRate_Text_Wash = c.getString("OccupancyRate");
                        occupancy_rate.setText(OccupancyRate_Text_Wash + " %");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_occupancyrate_wash.php", data);
                    Log.d("BANKHG",data+"");
                    Log.d("BANKHG",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        getOccupancyRate obj = new getOccupancyRate();
        obj.execute();
    }
}

