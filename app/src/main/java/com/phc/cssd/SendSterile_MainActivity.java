package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.SendSterile_DocListAdapter;
import com.phc.cssd.adapter.SendSterile_DocListDetailAdapter;
import com.phc.cssd.adapter.SendSterile_EditDetailAdapter;
import com.phc.cssd.adapter.SendSterile_EditItemAdapter;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.SendSterile_washDoclist_Adapter;
import com.phc.cssd.adapter.sendsterile_washdocdetail_adapte_2;
import com.phc.cssd.adapter.sendsterile_washdocdetail_adapter;
import com.phc.cssd.config.ConfigProgram;
import com.phc.cssd.data.Master;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.xControl;
import com.stargreatsoftware.sgtreeview.TreeViewAdapter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class SendSterile_MainActivity extends AppCompatActivity {

    private int Delete_multiple_items = ConfigProgram.Delete_multiple_items;
    HashMap<String, String> DelAlldata = new HashMap<String,String>();
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    private int summenuset = 0;
    String Qty;
    String Isstatus;
    String bo = "";
    String DepIDScan = "";
    String TelScan = "";
    private HTTPConnect httpConnect = new HTTPConnect();
    private RecyclerView rv;
    private TreeViewAdapter adapter;
    private int index_data;
    private String dept_ID;
    ArrayList<pCustomer> resultsPcus = new ArrayList<pCustomer>();
    private Spinner dept_spinner;
    private ArrayList<String> array_dept = new ArrayList<String>();
    private ArrayList<String> cnt = new ArrayList<String>();
    Calendar myCalendar = Calendar.getInstance();
    TextView edittext;
    ListView list_docno_detail;
    ListView xedit_detail;
    EditText searchbox;
    Button bt_search_sendsteril;
    Button bt_search_sendsteril1;
    public Switch del_multi;
    public Switch del_multi_1;
    boolean ssStatus1 ;
    private String DocNo = "";
    private String DocDate = "";
    private String Dept = "";
    private String IsStatus = "";
    private String UserReceive = "";
    private String UserSend = "";
    private boolean DIALOG_ACTIVE = false;
    String xSel = "";
    String xSel1 = "";
    String B_ID = null;
    String DelRowId_Text;
    String DelDocNo;
    String txt_usr_send_detail = "";
    public ArrayList<String> DelRowId = new ArrayList<String>();
    public boolean IsAdmin;
    TextView txt_setdetail_l2;
    TextView txt_setdetail_l3;
    TextView txt_setdetail_l4;
    TextView textView46;
    TextView textView47;
    TextView textView48;
    Button bin_all;
    Button bin_all_black;
    Button bin_all_1;
    Button bin_all_black_1;
    EditText txt_usr_receive;
    //TextView txt_usr_send;
    TextView etxt_date;
    TextView etxt_docno;
    TextView txt_tel_dep;
    TextView etxt_sumqty;
    SearchableSpinner etxt_dept;
    SearchableSpinner txt_usr_send;
    TextView doctxt_dept;
    Button calendar;
    private String ED_Deptposition = "";
    private String ED_Dept = "";
    private String ED_UserCode = "";
    private String ED_DeptName = "";
    Button newss;
    Button save;
    private boolean switch_layout = false;
    private LinearLayout L1;
    private LinearLayout L2;
    private EditText txt_search;
    private Button bt_search;
    private TextView txt_menu_set;
    private Switch bt_sw;
    Spinner dept_l2;
    TextView date_l2;
    Button bt_calendar_l2;
    Button bt_search_l2;
    ArrayList<Response_Aux> ar_listdept;
    String dept_search_l2 = "";
    ArrayAdapter<String> adapter_spinner;
    ListView list_set_detail_l2;
    ListView list_createdetail_l2;
    ListView list_docno_l2;
    String xIsStatus;
    Button finddoc_l2;
    TextView textView19;
    String usersend;
    Intent intent;
    //new sendstrile
    EditText txt_get_ucode;
    String Qty1;
    private ArrayList<String> selectedArray = new ArrayList<String>();
    private ArrayList<pCustomer> selectedItemDetail = new ArrayList<pCustomer>();
    private ArrayList<String> selectedArray2 = new ArrayList<String>();
    private SendSterile_EditDetailAdapter UsageObject2;
    private ArrayList<String> array_deptsp = new ArrayList<String>();
    private ArrayList<String> list_sp = new ArrayList<String>();
    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
    final ArrayList<pCustomer> pCus1 = new ArrayList<pCustomer>();
    String deptsp_id = "";
    private ArrayList<String> array_emp = new ArrayList<String>();
    private ArrayList<String> list_emp = new ArrayList<String>();
    ListView lv;
    boolean IsItemClick = false;
    String txt_usr_send_old = "";
    boolean txt_usr_send_on_Click = false;
    private HashMap<String, String> xDepSend = new HashMap<String,String>();
    private ArrayList<String> listUSendID = new ArrayList<String>();
    public String IsDel ;
    SearchableSpinner spinner_dept;
    String condition1 = "";
    String condition2 = "";
    String condition3 = "";
    String condition4 = "";
    CheckBox checkBoxall;
    String CheckAll = "0";
    String Usagecode = "";
    String DepID = "";
    String DocNoSend = "";
    String EmpCode = "";
    String Usage = "";
    String Type = "";
    String serialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sterile__main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().hide();
        byIntent();
        initialize();
        bin_all.setVisibility(View.INVISIBLE);
        bin_all_black.setVisibility(View.VISIBLE);
        bin_all_1.setVisibility(View.INVISIBLE);
        bin_all_black_1.setVisibility(View.VISIBLE);
        etxt_dept.setTitle("เลือกแผนก");
        etxt_dept.setPositiveButton("");
        etxt_dept.requestFocus();
    }

    public String getSerialNumber() {
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
        return serialNumber;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getuserCode();
    }

    public void byIntent() {
        intent = getIntent();
        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            ED_UserCode = bd.getString("userid");
            IsAdmin = bd.getBoolean("IsAdmin");
        }
        B_ID = intent.getStringExtra("B_ID");
        EmpCode = intent.getStringExtra("EmpCode");
    }

    public void initialize() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        checkBoxall = (CheckBox) findViewById(R.id.checkBoxall);
        checkBoxall.setChecked(true);
        CheckAll = "1";
        del_multi = (Switch) findViewById(R.id.del_multi);
        del_multi_1 = (Switch) findViewById(R.id.del_multi_1);
        bin_all = (Button) findViewById(R.id.bin_all);
        bin_all_black = (Button) findViewById(R.id.bin_all_black);
        bin_all_1 = (Button) findViewById(R.id.bin_all_1);
        bin_all_black_1 = (Button) findViewById(R.id.bin_all_black_1);
        IsStatus = "0";
        rv = ( RecyclerView ) findViewById(R.id.rv);
        list_docno_detail = ( ListView ) findViewById(R.id.list_docno_detail);
        xedit_detail = ( ListView ) findViewById(R.id.xedit_detail);
        txt_get_ucode = ( EditText ) findViewById(R.id.txt_get_ucode);
        searchbox = ( EditText ) findViewById(R.id.searchbox);
        edittext = ( TextView ) findViewById(R.id.Birthday);
        calendar = ( Button ) findViewById(R.id.calendar);
        doctxt_dept = ( TextView ) findViewById(R.id.doctxt_dept);
        dept_spinner = ( Spinner ) findViewById(R.id.dept_spinner);
        txt_usr_receive = ( EditText ) findViewById(R.id.txt_usr_receive);
        txt_usr_receive.requestFocus();
        txt_usr_send = ( SearchableSpinner ) findViewById(R.id.txt_usr_send);
        txt_usr_send.setTitle("ผู้ส่ง(แผนก)");//lang
        txt_usr_send.setPositiveButton("เพิ่มผู้ส่ง",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SendSterile_MainActivity.this,Create_UserSendActivity.class);
                startActivity(intent);
            }
        });

        etxt_date = ( TextView ) findViewById(R.id.etxt_date);

//        txt_usr_receive.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                if(!etxt_docno.getText().toString().equals("")){
//                    openQR("user_receive");
//                }else{
//                    Toast.makeText(SendSterile_MainActivity.this, "ยังไม่ได้เพิ่มรายการ กรุณาเพิ่มรายการ", Toast.LENGTH_SHORT).show();
//                }
//                openQR("user_receive");
//            }
//        });

        txt_usr_receive.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            ScanUserReceive();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        txt_usr_send.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(etxt_docno.getText().toString().equals("")){
                    Toast.makeText(SendSterile_MainActivity.this, "ยังไม่ได้เพิ่มรายการ กรุณาเพิ่มรายการ", Toast.LENGTH_SHORT).show();

                }else{
                    txt_usr_send_old = xDepSend.get(txt_usr_send.getSelectedItem());
                    if(txt_usr_send_old == null){txt_usr_send_old ="";}
                    txt_usr_send_on_Click = true;
                    txt_usr_send.onTouch(v, event);
                }
                return true;
            }
        });

        txt_usr_send.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){

                    String emp_id = xDepSend.get(txt_usr_send.getSelectedItem());

                    updateSendSterile(Master.user_send, emp_id, etxt_docno.getText().toString());

                    if(IsAdmin && txt_usr_send_on_Click && (!txt_usr_send_old.equals(emp_id))){
                        addEvenlog("SS","","Edit UserSend ID from "+txt_usr_send_old+" to "+emp_id);
                        txt_usr_send_on_Click = false;
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getuserCode();
        etxt_docno = ( TextView ) findViewById(R.id.etxt_docno);
        txt_tel_dep = ( TextView ) findViewById(R.id.txt_tel_dep);
        txt_setdetail_l2 = (TextView) findViewById(R.id.txt_setdetail_l2);
        txt_setdetail_l3 = (TextView) findViewById(R.id.txt_setdetail_l3);
        txt_setdetail_l4 = (TextView) findViewById(R.id.txt_setdetail_l4);
        textView46 = (TextView) findViewById(R.id.textView46);
        textView47 = (TextView) findViewById(R.id.textView47);
        textView48 = (TextView) findViewById(R.id.textView48);
        etxt_sumqty = ( TextView ) findViewById(R.id.etxt_sumqty);
        etxt_dept = ( SearchableSpinner ) findViewById(R.id.etxt_dept);
        bt_search_sendsteril = ( Button ) findViewById(R.id.bt_search_sendsterile);
        bt_search_sendsteril1 = ( Button ) findViewById(R.id.bt_search_sendsteril1);
        lv = ( ListView ) findViewById(R.id.list_docno);
        L1 = ( LinearLayout ) findViewById(R.id.layout_1);
        L2 = ( LinearLayout ) findViewById(R.id.layout_2);
        txt_menu_set = ( TextView ) findViewById(R.id.txt_menu_set);
        bt_sw = ( Switch ) findViewById(R.id.bt_sw);
        newss = ( Button ) findViewById(R.id.newSS);
        save = ( Button ) findViewById(R.id.next);
        date_l2 = ( TextView ) findViewById(R.id.date_l2);
        bt_calendar_l2 = ( Button ) findViewById(R.id.bt_calendar_l2);
        list_set_detail_l2 = ( ListView ) findViewById(R.id.list_set_detail_l2);
        list_createdetail_l2 = ( ListView ) findViewById(R.id.list_createdetail_l2);
        list_docno_l2 = ( ListView ) findViewById(R.id.list_docno_l2);
        dept_l2 = ( Spinner ) findViewById(R.id.dept_spinner_l2);
        finddoc_l2 = ( Button ) findViewById(R.id.finddoc_l2);
        textView19 = ( TextView ) findViewById(R.id.textView19);
        list_docno_detail = ( ListView ) findViewById(R.id.list_docno_detail);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                pCustomer newsData = ( pCustomer ) o;
                CheckStatusDocNo();
                list_docno_detail.setAdapter(null);
                textView48.setText("" + "0" + " ชิ้น" + " ]");
                txt_setdetail_l4.setText("" + "0" + " ชิ้น" + " ]");
                textView47.setText("[ " +"0"+ "  รายการ  /");
                txt_setdetail_l3.setText("[ " +"0"+ "  รายการ  /");
                DelRowId.clear();
                DelAlldata.clear();
                DocNo = newsData.getDocno();
                DocDate = newsData.getDocdate();
                ED_Dept = newsData.getDept();
                etxt_dept.setSelection(findindexspinner(ED_Dept));
                Qty = newsData.getQty();
                IsStatus = newsData.getIsStatus();
                if(IsStatus.equals("0")){
                    showAndhideBlueHead(true);
                    del_multi.setEnabled(true);
                    del_multi_1.setEnabled(true);
                }else{
                    showAndhideBlueHead(false);
                    del_multi.setEnabled(false);
                    del_multi_1.setEnabled(false);
                }
                UserReceive = newsData.getUsr_receive();
                UserSend = newsData.getUsr_send();
                String UserSend_ = newsData.getUserSend();
                etxt_docno.setText(DocNo);
                etxt_date.setText(DocDate);
                etxt_sumqty.setText(Qty);
                getlistcreate(DocNo, ED_Dept);
                //getlistdetail("","");
                //getlistdetailqty("");
                txt_usr_receive.setText(newsData.getUsr_receive());
                txt_usr_receive.setContentDescription(newsData.getUserReceive());
                txt_usr_send.setSelection(listUSendID.indexOf(newsData.getUserSend())+1);
                IsItemClick = true;
                ShowDetail();
                ShowUserSend();
            }
        });

        list_docno_l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                CheckUsageCont();
                DIALOG_ACTIVE = true;
                Object o = list_docno_l2.getItemAtPosition(position);
                list_docno_detail.setAdapter(null);
                textView48.setText("" + "0" + " ชิ้น" + " ]");
                txt_setdetail_l4.setText("" + "0" + " ชิ้น" + " ]");
                textView47.setText("[ " +"0"+ "  รายการ  /");
                txt_setdetail_l3.setText("[ " +"0"+ "  รายการ  /");
                DelRowId.clear();
                DelAlldata.clear();
                pCustomer newsData = ( pCustomer ) o;
                Isstatus = newsData.getPayoutIsStatus();
                DocNo = newsData.getDocno();
                DocDate = newsData.getDocdate();
                ED_Dept = newsData.getDept();
                etxt_dept.setSelection(findindexspinner(ED_Dept));
                Qty = newsData.getQty();
                IsStatus = newsData.getIsStatus();
                if(IsStatus.equals("0")){
                    showAndhideBlueHead(true);
                    del_multi.setEnabled(true);
                    del_multi_1.setEnabled(true);
                }else{
                    showAndhideBlueHead(false);
                    del_multi.setEnabled(false);
                    del_multi_1.setEnabled(false);
                }
                UserReceive = newsData.getUsr_receive();
                UserSend = newsData.getUsr_send();
                String UserSend_ = newsData.getUserSend();
                etxt_docno.setText(DocNo);
                etxt_date.setText(DocDate);
                etxt_sumqty.setText(Qty);
                getlistcreate_l2(DocNo);
                //getlistdetail("","");
                //getlistdetailqty("");
                txt_usr_receive.setText(newsData.getUsr_receive());
                txt_usr_receive.setContentDescription(newsData.getUserReceive());
                txt_usr_send.setSelection(listUSendID.indexOf(newsData.getUserSend())+1);
                IsItemClick = true;
                ShowDetail();
                ShowUserSend();
            }
        });

        checkBoxall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CheckAll = "1";
                    getlistdetail(Usagecode,"");
                }else {
                    CheckAll = "0";
                    getlistdetail(Usagecode,"");
                }
            }
        });

        del_multi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IsDel = "1";
                    getlistcreate(etxt_docno.getText().toString(), ED_Dept);
                    bin_all.setVisibility(View.VISIBLE);
                    bin_all_black.setVisibility(View.INVISIBLE);
                    Toast.makeText(SendSterile_MainActivity.this, "เลือกลบหลายรายการ", Toast.LENGTH_SHORT).show();
                }else if (!isChecked){
                    IsDel = "0";
                    getlistcreate(etxt_docno.getText().toString(), ED_Dept);
                    bin_all.setVisibility(View.INVISIBLE);
                    bin_all_black.setVisibility(View.VISIBLE);
                    Toast.makeText(SendSterile_MainActivity.this, "เลือกลบทีละรายการ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        del_multi_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IsDel = "1";
                    getlistcreate_l2(etxt_docno.getText().toString());
                    bin_all_1.setVisibility(View.VISIBLE);
                    bin_all_black_1.setVisibility(View.INVISIBLE);
                    Toast.makeText(SendSterile_MainActivity.this, "เลือกลบหลายรายการ", Toast.LENGTH_SHORT).show();
                }else if (!isChecked){
                    IsDel = "0";
                    getlistcreate_l2(etxt_docno.getText().toString());
                    bin_all_1.setVisibility(View.INVISIBLE);
                    bin_all_black_1.setVisibility(View.VISIBLE);
                    Toast.makeText(SendSterile_MainActivity.this, "เลือกลบทีละรายการ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bin_all_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsStatus.equals("1")){
                    bin_all_black.setEnabled(true);
                    Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกเมนูลบหลายรายการ", Toast.LENGTH_SHORT).show();
                }else {
                    bin_all_black.setEnabled(false);
                }
            }
        });

        bin_all_black_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsStatus.equals("1")){
                    bin_all_black_1.setEnabled(true);
                    Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกเมนูลบหลายรายการ", Toast.LENGTH_SHORT).show();
                }else {
                    bin_all_black_1.setEnabled(false);
                }
            }
        });

        bin_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt=0;
                for (String key : DelAlldata.keySet()) {
                    if (DelAlldata.get(key) == "0")
                        cnt++;
                }
                if (cnt != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("ต้องการลบรายการหรือไม่");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (String key : DelAlldata.keySet()) {
                                        if (DelAlldata.get(key) == "0")
                                            DelRowId.add(key);
                                    }
                                    DeleteDetail_l2(DelDocNo, String.valueOf(DelRowId));
                                    DelRowId.clear();
                                    DelAlldata.clear();
                                }
                            });
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกอย่างน้อย 1 รายการ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bin_all_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt=0;
                for (String key : DelAlldata.keySet()) {
                    if (DelAlldata.get(key) == "0")
                        cnt++;
                }
                if (cnt != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("ต้องการลบรายการหรือไม่");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (String key : DelAlldata.keySet()) {
                                        if (DelAlldata.get(key) == "0")
                                            DelRowId.add(key);
                                    }
                                    DeleteDetail_l2(DelDocNo, String.valueOf(DelRowId));
                                    DelRowId.clear();
                                    DelAlldata.clear();
                                }
                            });
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกอย่างน้อย 1 รายการ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getdept_spinner();
        updateDate();
        updateDate_l2();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate_l2();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SendSterile_MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SendSterile_MainActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bt_calendar_l2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SendSterile_MainActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button bt_calendar = ( Button ) findViewById(R.id.bt_calendar);
        bt_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SendSterile_MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button bt1 = ( Button ) findViewById(R.id.finddoc);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
                getlistdetail("","");
                getlistdetailqty("");
                cleardoc2();

            }
        });

        Button bt2 = ( Button ) findViewById(R.id.editDetail);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (IsStatus.equals("0")) {
                    getlistcreate(DocNo, ED_Dept);

                } else if (IsStatus.equals("1")) {
                    Toast.makeText(SendSterile_MainActivity.this, "เอกสารนี้ได้บันทึกส่งล้างแล้ว", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SendSterile_MainActivity.this, "ยังไม่ได้เลือกเอกสาร", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //---------------------------------------------------------------

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etxt_date.setText(sdf.format(Calendar.getInstance().getTime()));

        newss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                IsStatus = "0";
                updateDate2();
                updateDate();
                cleardoc();
                clear_l2();
                ED_Dept = "";
                etxt_dept.setSelection(0);
                txt_usr_receive.setText("");
                txt_usr_receive.setContentDescription(null);
                txt_usr_send.setSelection(0);
                showAndhideBlueHead(true);
                bin_all.setVisibility(View.INVISIBLE);
                del_multi.setChecked(false);
                bin_all_1.setVisibility(View.INVISIBLE);
                del_multi_1.setChecked(false);
                del_multi.setEnabled(true);
                del_multi_1.setEnabled(true);
                DelRowId.clear();
                DelAlldata.clear();
                txt_usr_receive.requestFocus();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (switch_layout) {
                    if (!etxt_docno.getText().toString().equals("")) {
                        if (!xIsStatus.equals("1")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SendSterile_MainActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("ยืนยัน");
                            builder.setMessage("ยืนยันการส่งล้างหรือไม่");
                            builder.setPositiveButton("ยืนยัน",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            createNewSendSterile(etxt_docno.getText().toString(), checkRowID());
                                            updateIsclean(etxt_docno.getText().toString(), ED_UserCode);
                                            getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                                            updateSendSterile(Master.user_receive, txt_usr_receive.getContentDescription()+"", etxt_docno.getText()+"");
                                            String emp_id = xDepSend.get(txt_usr_send.getSelectedItem());
                                            updateSendSterile(Master.user_send, emp_id, etxt_docno.getText().toString());
                                            clear_l2();
                                        }
                                    });
                            builder.setNegativeButton("ไม่ยืนยัน", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            Toast.makeText(SendSterile_MainActivity.this, "เอกสารนี้ได้บันทึกแล้ว", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SendSterile_MainActivity.this, "ไม่มีเอกสารให้บันทึก", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!IsStatus.equals("1")){
                        if (!etxt_docno.getText().toString().equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SendSterile_MainActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("ยืนยัน");
                            builder.setMessage("ต้องการสร้างเอกสารหรือไม่");
                            builder.setPositiveButton("ตกลง",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.out.println(txt_usr_receive.getText().toString());
                                            if ((!txt_usr_receive.getText().toString().trim().equals(""))) {
                                                if (!IsStatus.equals("1")) {
                                                    savedoc(etxt_docno.getText().toString());
                                                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                                                    updateSendSterile(Master.user_receive, txt_usr_receive.getContentDescription() + "", etxt_docno.getText() + "");
                                                    String emp_id = xDepSend.get(txt_usr_send.getSelectedItem());
                                                    updateSendSterile(Master.user_send, emp_id, etxt_docno.getText().toString());
                                                    pCus.clear();
                                                    selectedArray.clear();
                                                    ListView lv = ( ListView ) findViewById(R.id.xedit_detail);
                                                    lv.setAdapter(new SendSterile_EditItemAdapter(SendSterile_MainActivity.this, pCus, selectedArray));
                                                    cleardoc();
                                                    updateDate2();
                                                    getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
                                                    ED_Dept = "";
                                                    etxt_dept.setSelection(0);
                                                    Toast.makeText(SendSterile_MainActivity.this, "บันทึกเอกสารแล้ว", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(SendSterile_MainActivity.this, "เอกสารนี้ได้บันทึกส่งล้างแล้ว", Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                Toast.makeText(SendSterile_MainActivity.this, "กรุณาสแกนชื่อผู้รับ(จ่ายกลาง)", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            Toast.makeText(SendSterile_MainActivity.this, "ไม่มีเอกสารให้บันทึก", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SendSterile_MainActivity.this, "เอกสารนี้ได้บันทึกส่งล้างแล้ว", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        Button searchdoc = ( Button ) findViewById(R.id.searchdoc);
        searchdoc.bringToFront();
        searchdoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
            }
        });


        Button bt_back = ( Button ) findViewById(R.id.bt_back);
        bt_back.bringToFront();
        bt_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                onBackPressed();
            }
        });
        searchbox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
                            cleardoc();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        txt_get_ucode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (txt_usr_receive.getText().toString().equals("")){
                                Toast.makeText(SendSterile_MainActivity.this, "กรุณาสแกนชื่อผู้รับ(จ่ายกลาง)", Toast.LENGTH_SHORT).show();
                                txt_get_ucode.setText("");
                                txt_get_ucode.requestFocus();
                            }else {
                                CheckUsageContScan();
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        bt_search_sendsteril.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                if (txt_usr_receive.getText().toString().equals("")){
                    Toast.makeText(SendSterile_MainActivity.this, "กรุณาสแกนชื่อผู้รับ(จ่ายกลาง)", Toast.LENGTH_SHORT).show();
                }else {
                    if (IsStatus.equals("1")){
                        Toast.makeText(SendSterile_MainActivity.this, "ไม่สามารถเพิ่มรายการได้เนื่องจากเอกสารถูกบันทึกแล้ว !!", Toast.LENGTH_SHORT).show();
                    }else {
                        if (!ED_Dept.equals("") && !ED_Dept.equals("0")) {
                            openSearchItemStock("1");
                        } else {
                            Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกแผนก", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        bt_search_sendsteril1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                if (txt_usr_receive.getText().toString().equals("")){
                    Toast.makeText(SendSterile_MainActivity.this, "กรุณาสแกนชื่อผู้รับ(จ่ายกลาง)", Toast.LENGTH_SHORT).show();
                }else {
                    if (IsStatus.equals("1")){
                        Toast.makeText(SendSterile_MainActivity.this, "ไม่สามารถเพิ่มรายการได้เนื่องจากเอกสารถูกบันทึกแล้ว !!", Toast.LENGTH_SHORT).show();
                    }else {
                        if (!ED_Dept.equals("") && !ED_Dept.equals("0")) {
                            openSearchItemStock("2");
                        } else {
                            Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกแผนก", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        bt_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!etxt_docno.getText().toString().equals("")) {
                    if (bt_sw.isChecked()) {
                        L1.setVisibility(View.GONE);
                        L2.setVisibility(View.VISIBLE);
                        txt_menu_set.setVisibility(View.INVISIBLE);
                        newss.setVisibility(View.VISIBLE);
                        bt_search_sendsteril.setVisibility(View.GONE);
                        bt_search_sendsteril1.setVisibility(View.VISIBLE);
                        txt_get_ucode.setVisibility(View.INVISIBLE);
                        clear_l2();
                        cleardoc();
                        xSel = "1";
                        xSel1 = "0";
                        etxt_dept.setSelection(0);
                        etxt_dept.setEnabled(true);
                        txt_setdetail_l3.setVisibility(View.INVISIBLE);
                        txt_setdetail_l4.setVisibility(View.INVISIBLE);
                        textView47.setText("");
                        textView48.setText("");
                        checkBoxall.setVisibility(View.GONE);
                        getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                        switch_layout = true;
                    } else {
                        L1.setVisibility(View.VISIBLE);
                        L2.setVisibility(View.GONE);
                        txt_menu_set.setVisibility(View.VISIBLE);
                        newss.setVisibility(View.VISIBLE);
                        bt_search_sendsteril.setVisibility(View.VISIBLE);
                        bt_search_sendsteril1.setVisibility(View.GONE);
                        txt_get_ucode.setVisibility(View.VISIBLE);
                        clear_l2();
                        xSel1 = "1";
                        xSel = "0";
                        cleardoc();
                        etxt_dept.setSelection(0);
                        txt_get_ucode.requestFocus();
                        etxt_dept.setEnabled(true);
                        textView47.setText("");
                        textView48.setText("");
                        checkBoxall.setVisibility(View.GONE);
                        txt_setdetail_l3.setVisibility(View.INVISIBLE);
                        txt_setdetail_l4.setVisibility(View.INVISIBLE);
                        getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
                        switch_layout = false;
                    }
                }else {
                    bin_all_1.setVisibility(View.INVISIBLE);
                    bin_all_black_1.setVisibility(View.VISIBLE);
                    bin_all.setVisibility(View.INVISIBLE);
                    bin_all_black.setVisibility(View.VISIBLE);
                    del_multi_1.setChecked(false);
                    del_multi.setChecked(false);
                    if (bt_sw.isChecked()) {
                        L1.setVisibility(View.GONE);
                        L2.setVisibility(View.VISIBLE);
                        txt_menu_set.setVisibility(View.INVISIBLE);
                        newss.setVisibility(View.VISIBLE);
                        bt_search_sendsteril.setVisibility(View.GONE);
                        bt_search_sendsteril1.setVisibility(View.VISIBLE);
                        txt_get_ucode.setVisibility(View.INVISIBLE);
                        clear_l2();
                        cleardoc();
                        xSel = "1";
                        xSel1 = "0";
                        etxt_dept.setSelection(0);
                        etxt_dept.setEnabled(true);
                        txt_setdetail_l3.setVisibility(View.INVISIBLE);
                        txt_setdetail_l4.setVisibility(View.INVISIBLE);
                        textView47.setText("");
                        textView48.setText("");
                        checkBoxall.setVisibility(View.GONE);
                        getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                        switch_layout = true;
                    } else {
                        L1.setVisibility(View.VISIBLE);
                        L2.setVisibility(View.GONE);
                        txt_menu_set.setVisibility(View.VISIBLE);
                        newss.setVisibility(View.VISIBLE);
                        bt_search_sendsteril.setVisibility(View.VISIBLE);
                        bt_search_sendsteril1.setVisibility(View.GONE);
                        txt_get_ucode.setVisibility(View.VISIBLE);
                        clear_l2();
                        xSel1 = "1";
                        xSel = "0";
                        cleardoc();
                        etxt_dept.setSelection(0);
                        txt_get_ucode.requestFocus();
                        etxt_dept.setEnabled(true);
                        textView47.setText("");
                        textView48.setText("");
                        checkBoxall.setVisibility(View.GONE);
                        txt_setdetail_l3.setVisibility(View.INVISIBLE);
                        txt_setdetail_l4.setVisibility(View.INVISIBLE);
                        getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
                        switch_layout = false;
                    }
                }
            }
        });

        if (bt_sw.isChecked()) {
            L1.setVisibility(View.GONE);
            L2.setVisibility(View.VISIBLE);
            txt_menu_set.setVisibility(View.INVISIBLE);
            newss.setVisibility(View.VISIBLE);
            bt_search_sendsteril.setVisibility(View.GONE);
            bt_search_sendsteril1.setVisibility(View.VISIBLE);
            txt_get_ucode.setVisibility(View.INVISIBLE);
            IsStatus = "";
            xSel = "1";
            xSel1 = "0";
            xIsStatus = "";
            etxt_dept.setEnabled(true);
            txt_setdetail_l3.setVisibility(View.INVISIBLE);
            textView47.setText("");
            txt_setdetail_l4.setVisibility(View.INVISIBLE);
            textView48.setText("");
            checkBoxall.setVisibility(View.GONE);
            getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
            switch_layout = true;
        } else {
            L1.setVisibility(View.VISIBLE);
            L2.setVisibility(View.GONE);
            txt_menu_set.setVisibility(View.VISIBLE);
            newss.setVisibility(View.VISIBLE);
            bt_search_sendsteril.setVisibility(View.VISIBLE);
            bt_search_sendsteril1.setVisibility(View.GONE);
            txt_get_ucode.setVisibility(View.VISIBLE);
            IsStatus = "";
            xSel1 = "1";
            xSel = "0";
            xIsStatus = "";
            etxt_dept.setEnabled(true);
            txt_setdetail_l3.setVisibility(View.INVISIBLE);
            textView47.setText("");
            txt_setdetail_l4.setVisibility(View.INVISIBLE);
            textView48.setText("");
            checkBoxall.setVisibility(View.GONE);
            getlistdata(deptsp_id, edittext.getText().toString(), searchbox.getText().toString());
            switch_layout = false;
        }
        finddoc_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                clear_l2();
            }
        });
    }

//    public void defineEmp(final String txt) {
//        class getdeptsp extends AsyncTask<String, Void, String> {
//            // variable
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                try {
//
//                    JSONObject jsonObj = new JSONObject(s);
//                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
//
//                    array_emp.clear();
//                    array_emp.add("");
//
//                    list_emp.add("");
//
//                    for (int i = 0; i < setRs.length(); i++) {
//
//                        JSONObject c = setRs.getJSONObject(i);
//
//                        if(c.getString("result").equals("A")) {
//                            list_emp.add(c.getString("data"));
//                            array_emp.add(c.getString("value"));
//                        }
//
//                    }
//
//                    adapter_spinner = new ArrayAdapter<String>(SendSterile_MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list_emp);
//                    adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                    txt_usr_send.setAdapter(adapter_spinner);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String, String>();
//                data.put("p_filter", txt);
//                String result = ruc.sendPostRequest(Url.URL + "cssd_select_master_data_json.php", data);
//                return result;
//            }
//        }
//        getdeptsp ru = new getdeptsp();
//        ru.execute();
//    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void updateDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
        cleardoc();

    }

    private void updateDate_l2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_l2.setText(sdf.format(myCalendar.getTime()));
        getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");

    }

    public void getlistdata(String department_id, String Date, String sreach) {
        class getlistdata extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
//                this.dialog.setCancelable(false);
//                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    String bo = "";
                    resultsPcus.clear();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setDocno(c.getString("DocNo"));
                        xST.setDocdate(c.getString("DocDate"));
                        xST.setDept(c.getString("DeptID"));
                        xST.setDeptname(c.getString("DepName2"));
                        xST.setQty(c.getString("Qty"));
                        xST.setNote(c.getString("Remark"));
                        xST.setIsStatus(c.getString("IsStatus"));
                        xST.setTime(c.getString("xtime"));
                        xST.setUserReceive(c.getString("UserReceive"));
                        xST.setUsr_receive(c.getString("usr_receive"));
                        xST.setUserSend(c.getString("UserSend"));
                        xST.setUsr_send(c.getString("usr_send"));
                        xST.setIsWeb(c.getString("IsWeb"));
                        resultsPcus.add(xST);
                        //dialog.dismiss();
                    }
                    lv.setAdapter(new SendSterile_DocListAdapter(SendSterile_MainActivity.this, resultsPcus,B_ID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("department_id", params[0]);
                data.put("date", params[1]);
                data.put("sreach", params[2]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(Url.URL + "sendsterile/getlistdoc_sendsterile.php", data);
                Log.d("DJKHDK",data+"");
                Log.d("DJKHDK",result+"");
                System.out.println(result);
                return result;
            }
        }
        getlistdata ru = new getlistdata();
        ru.execute(department_id, Date, sreach);
    }

    public void getlistdetail(final String UsageCode, final String DocNo) {
        class getlistdetail extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    int cnt = 0;
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setUsageCode(UsageCode);
                        xST.setRemarkAdmin(c.getString("RemarkAdmin"));
                        xST.setIsPicture(c.getString("IsPicture"));
                        xST.setRemarkItemCode(c.getString("RemarkItemCode"));
                        xST.setRemarkDocNo(c.getString("DocNo"));
                        pCus.add(xST);
                        cnt++;
                    }
                    textView47.setText("[ " +cnt+ "  รายการ  /");
                    txt_setdetail_l3.setText("[ " +cnt+ "  รายการ  /");
                    ListView lv = ( ListView ) findViewById(R.id.list_docno_detail);
                    lv.setAdapter(new SendSterile_DocListDetailAdapter(SendSterile_MainActivity.this, pCus,CheckAll));
                } catch (JSONException e) {
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("UsageCode", UsageCode);
                data.put("DocNo", DocNo);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getitemdetail_sendsterile(), data);
                Log.d("YUYU",data+"");
                Log.d("YUYU",result);
                return result;
            }
        }
        getlistdetail ru = new getlistdetail();
        ru.execute(UsageCode);
    }

    public void getlistdetailqty(final String UsageCode) {
        class getlistdetailqty extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        Qty1 = c.getString("Qty");

                        if (Qty1.equals("null")) {
                            textView48.setText("" + "0" + " ชิ้น" + " ]");
                            txt_setdetail_l4.setText("" + "0" + " ชิ้น" + " ]");
                        }else {
                            checkBoxall.setVisibility(View.VISIBLE);
                            textView48.setText("" + Qty1 + " ชิ้น" + " ]");
                            txt_setdetail_l4.setText("" + Qty1 + " ชิ้น" + " ]");
                        }
                        pCus.add(xST);
                    }

                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("UsageCode", UsageCode);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getitemdetail_sendsterile1(), data);
                Log.d("YUYU",data+"");
                Log.d("YUYU",result);
                return result;
            }
        }
        getlistdetailqty ru = new getlistdetailqty();
        ru.execute(UsageCode);
    }

    //page2 *****************************************************************************************************************************************************************

    private void updateDate2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etxt_date.setText(sdf.format(Calendar.getInstance().getTime()));
    }

    public void getlistcreate(String xDocNo, String dept) {
        class getlistcreate extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    boolean ssStatus = IsAdmin;
                    int cnt = 0;
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        xST.setDocno(c.getString("DocNo"));
                        xST.setIsStatus(c.getString("IsStatus"));
                        xST.setResteriletype(c.getString("ResterileType"));
                        xST.setResterilename(c.getString("Resterilename"));
                        xST.setDept(ED_Dept);
                        xST.setUcode(ED_UserCode);
                        xST.setOccuranceID(c.getString("OccuranceQty"));
                        xST.setItemID(c.getString("RowID"));
                        xST.setSs_rowid(c.getString("SS_RowID"));
                        xST.setPackdate(c.getString("Shelflife"));
                        xST.setItemCount(c.getString("ItemCount"));
                        xST.setDetailIsStatus(c.getString("ssIsStatus"));
                        xST.setPayoutIsStatus(c.getString("payoutIsStatus"));
                        if(c.getString("ssIsStatus").equals("2")){
                            ssStatus = false;
                            ssStatus1 = false;
                        }
                        if(!(c.getString("payoutIsStatus").equals("1"))){
                            ssStatus = false;
                            ssStatus1 = false;
                        }
                        xST.setUsageCount(c.getString("UsageCount"));
                        xST.setRemarkExpress(c.getString("RemarkExpress"));
                        xST.setRemarkEms(c.getString("IsRemarkExpress"));
                        xST.setIsDenger(c.getString("IsDenger"));
                        pCus.add(xST);
                        pCus1.add(xST);
                        cnt++;
                    }
                    etxt_sumqty.setText(cnt + "");
                    final ListView lv = ( ListView ) findViewById(R.id.xedit_detail);
                    lv.setAdapter(new sendsterile_washdocdetail_adapte_2(SendSterile_MainActivity.this, pCus,ssStatus,B_ID,IsDel));
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("dept", params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.geteditdetail(), data);
                Log.d("KJDGDK",data+"");
                Log.d("KJDGDK",result);
                return result;
            }
        }
        getlistcreate ru = new getlistcreate();
        ru.execute(xDocNo, dept);
    }

    public void addEvenlog(String Type,String tid,String Descriptions) {
        class addEvenlog extends AsyncTask<String, Void, String> {
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
                data.put("Type",params[0]);
                data.put("TID",params[1]);
                data.put("UserID",params[2]);
                data.put("Descriptions",params[3]);
                data.put("DocNo",params[4]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(getUrl.xUrl+"cssd_add_eventlog.php",data);
                return  result;
            }
        }
        addEvenlog ru = new addEvenlog();
        ru.execute( Type,tid,ED_UserCode,Descriptions,DocNo);
    }

    public void InsertDetail(String DocNo, String RowID, final String date, String UserCode, String dept, String QTY) {
        class InsertDetail extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    String txtDocno = "";
                    String txtdept = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        txtDocno = c.getString("DocNo");
                        pCus.add(xST);
                    }
                    //updateDate();
                    Log.d("BYRHCY",etxt_docno.getText().toString());
                    if (etxt_docno.getText().toString().equals("")){
                        getlistdata(dept_search_l2, date_l2.getText().toString(), "");
                        etxt_docno.setText(txtDocno);
                    }else {
                        etxt_docno.setText(txtDocno);
                    }
                    getlistcreate(txtDocno, ED_Dept);
                    if (bo.equals("true")){
                        ShowDetailScan(txtDocno);
                        ShowUserSend();
                    }

                    if (bo.equals("true")) {
                        Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์สำเร็จ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์ไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("RowID", params[1]);
                data.put("date", params[2]);
                data.put("UserCode", params[3]);
                data.put("dept", params[4]);
                data.put("QTY", params[5]);
                data.put("xSel", "0 ");
                data.put("B_ID",B_ID);
                data.put("usr_receive",txt_usr_receive.getText().toString());
                String result = ruc.sendPostRequest(iFt.InsertDetail(), data);
                Log.d("YUYU",data+"");
                Log.d("YUYU",result);
                return result;
            }
        }
        InsertDetail ru = new InsertDetail();
        ru.execute(DocNo, RowID, date, UserCode, dept, QTY);
    }

    public void InsertDetail1(String DocNo, String RowID, final String date, String UserCode, String dept, String QTY) {
        class InsertDetail1 extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    String txtDocno = "";
                    String txtdept = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        txtDocno = c.getString("DocNo");
                        pCus.add(xST);
                    }
                    //updateDate();
                    Log.d("BYRHCY",etxt_docno.getText().toString());
                    if (etxt_docno.getText().toString().equals("")){
                        getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                        etxt_docno.setText(txtDocno);
                    }else {
                        etxt_docno.setText(txtDocno);
                    }
                    getlistcreate_l2(txtDocno);
                    if (bo.equals("true")){
                        ShowDetailScan(txtDocno);
                        ShowUserSend();
                    }
                    if (bo.equals("true")) {
                        Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์สำเร็จ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์ไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("RowID", params[1]);
                data.put("date", params[2]);
                data.put("UserCode", params[3]);
                data.put("dept", params[4]);
                data.put("QTY", params[5]);
                data.put("xSel", "1");
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.InsertDetail(), data);
                Log.d("FDHDKJ",data+"");
                Log.d("FDHDKJ",result);
                return result;
            }
        }
        InsertDetail1 ru = new InsertDetail1();
        ru.execute(DocNo, RowID, date, UserCode, dept, QTY);
    }

    public void InsertDetail2(String DocNo, final String RowID, final String date, String UserCode, String dept, String QTY) {
        class InsertDetail2 extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    String txtDocno = "";
                    String txtdept = "";
                    String txttel = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        txtDocno = c.getString("DocNo");
                        txtdept = c.getString("DepID");
                        txttel = c.getString("Tel");
                        pCus.add(xST);
                        if (bo.equals("true")){
                            etxt_dept.setSelection(Integer.parseInt(c.getString("DepID")));
                            if (ED_Dept == null || ED_Dept.equals("")){
                                ED_Dept = c.getString("DepID");
                            }
                        }
                    }
                    //updateDate();
                    Log.d("BYRHCY",etxt_docno.getText().toString());
                    if (etxt_docno.getText().toString().equals("")){
                        getlistdata("", date_l2.getText().toString(), "");
                        etxt_docno.setText(txtDocno);
                    }else {
                        etxt_docno.setText(txtDocno);
                    }
                    if (bo.equals("true")){
                        ShowDetailScan(txtDocno);
                        ShowUserSend();
                    }
                    getlistcreate(txtDocno, "");
                    if (bo.equals("true")) {
                        Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์สำเร็จ", Toast.LENGTH_SHORT).show();
                        CheckUsageContScanToInsert(RowID);
                        DIALOG_ACTIVE = true;
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }

                    if (bo.equals("true1")) {
                        Toast.makeText(SendSterile_MainActivity.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }

                    if (bo.equals("true2")) {
                        Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์ไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }

                    if (bo.equals("true3")) {
                        Toast.makeText(SendSterile_MainActivity.this, "แผนกหรือสถานะรหัสไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }

                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("RowID", params[1]);
                data.put("date", params[2]);
                data.put("UserCode", params[3]);
                data.put("dept", params[4]);
                data.put("QTY", "1");
                data.put("xSel", "1");
                data.put("B_ID",B_ID);
                data.put("usr_receive",txt_usr_receive.getText().toString().trim());
                String result = ruc.sendPostRequest(iFt.InsertDetail1(), data);
                Log.d("FKDHKDSS",data+"");
                Log.d("FKDHKDSS",result);
                return result;
            }
        }
        InsertDetail2 ru = new InsertDetail2();
        ru.execute(DocNo, RowID, date, UserCode, dept, QTY);
    }

    public void DeleteDetail(String DocNo, String RowID) {
        class DeleteDetail extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    String txtDocno = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        xST.setDocno(c.getString("DocNo"));
                        txtDocno = c.getString("DocNo");
                        pCus.add(xST);
                    }

                    getlistcreate(txtDocno, ED_Dept);
                    getlistcreate_l2(txtDocno);
                    ShowDetailScan(txtDocno);
                    ShowUserSend();
//                    getlistdata(deptsp_id, edittext.getText().toString(), "");
//                    getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                    cleardoc();
                    etxt_docno.setText(txtDocno);
                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("RowID", params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.DeleteDetail(), data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);

                return result;
            }
        }
        DeleteDetail ru = new DeleteDetail();
        ru.execute(DocNo, RowID);
    }

    public void DeleteDetail_l2(String DocNo,String RowID) {
        class DeleteDetail_l2 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    String txtDocno = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        xST.setDocno(c.getString("DocNo"));
                        txtDocno = c.getString("DocNo");
                        pCus.add(xST);
                    }

                    getlistcreate(txtDocno, ED_Dept);
                    getlistcreate_l2(txtDocno);
                    getlistdata(deptsp_id, edittext.getText().toString(), "");
                    getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
                    cleardoc();
                    etxt_docno.setText(txtDocno);
                    DelRowId.clear();
                    DelAlldata.clear();
                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo",params[0]);
                data.put("RowID",params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(Url.URL + "sendsterile/DeleteDetail_sendsterile_all.php", data);
                Log.d("BANKUTU1",data+"");
                Log.d("BANKUTU1",result);
                Log.d("BANKUTU1", Url.URL + "sendsterile/DeleteDetail_sendsterile_all.php");

                return result;
            }
        }
        DeleteDetail_l2 ru = new DeleteDetail_l2();
        ru.execute(DocNo,RowID);
    }

    public void UpdateDocDate(String DocNo, String Date) {
        class UpdateDocDate extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        bo = c.getString("flag");
                        pCus.add(xST);
                    }
                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("date", params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(Url.URL + "sendsterile/getlistdoc_sendsterile_l2.php", data);
                return result;
            }
        }
        UpdateDocDate ru = new UpdateDocDate();
        ru.execute(DocNo, Date);
    }

    public void savedoc(String docno) {
        class savedoc extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                    }
                    ListView lv = ( ListView ) findViewById(R.id.xedit_detail);
                    lv.setAdapter(new SendSterile_EditItemAdapter(SendSterile_MainActivity.this, pCus, selectedArray));
                    Toast.makeText(SendSterile_MainActivity.this,"บันทึกเอกสารแล้ว",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.savedoc(), data);
                return result;
            }
        }
        savedoc ru = new savedoc();
        ru.execute(docno);
    }

    public void savedoc1(String docno) {
        class savedoc1 extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                    }
                    ListView lv = ( ListView ) findViewById(R.id.xedit_detail);
                    lv.setAdapter(new SendSterile_EditItemAdapter(SendSterile_MainActivity.this, pCus, selectedArray));
                    Toast.makeText(SendSterile_MainActivity.this, "บันทึกเอกสารแล้ว", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.savedoc(), data);
                return result;
            }
        }
        savedoc1 ru = new savedoc1();
        ru.execute(docno);
    }

    public void cleardoc() {
        txt_usr_receive.setText("");
        txt_usr_send.setSelection(0);
        etxt_docno.setText("");
        txt_tel_dep.setText("");
        etxt_sumqty.setText("");
        final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
        final ListView lv = ( ListView ) findViewById(R.id.xedit_detail);
        lv.setAdapter(new SendSterile_EditDetailAdapter(SendSterile_MainActivity.this, pCus,IsAdmin));
        ListView lv2 = ( ListView ) findViewById(R.id.list_docno_detail);
        lv2.setAdapter(new SendSterile_DocListDetailAdapter(SendSterile_MainActivity.this, pCus, "0"));
    }


    public void cleardoc2() {
        txt_usr_receive.setText("");
        txt_usr_send.setSelection(0);
        final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
        etxt_docno.setText("");
        txt_tel_dep.setText("");
        etxt_sumqty.setText("");
        selectedArray.clear();
        ListView lv = ( ListView ) findViewById(R.id.xedit_detail);
        lv.setAdapter(new SendSterile_EditItemAdapter(SendSterile_MainActivity.this, pCus, selectedArray));
    }

    public void checkdubplicate(final String ucode, String xdept) {
        class checkdubplicate extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    String bool="";
                    String RowID="";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bool=c.getString("flag");
                        RowID=c.getString("RowID");
                        Log.d("bool: ", c.getString("flag"));
                    }
                    if(bool.equals("true")){
                        Toast.makeText(SendSterile_MainActivity.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }else if(bool.equals("false")){
                        if(IsStatus.equals("1")){
                            Toast.makeText(SendSterile_MainActivity.this, "เอกสารสถานะเตรียมล้างเแก้ไขไม่ได้", Toast.LENGTH_SHORT).show();
                            txt_get_ucode.setText("");
                            txt_get_ucode.requestFocus();
                        }else{
                            InsertDetail2(etxt_docno.getText().toString(),ucode, etxt_date.getText().toString(), ED_UserCode, ED_Dept, "0");
                        }
                    }else if(bool.equals("notfound")){
                        Toast.makeText(SendSterile_MainActivity.this, "แผนก หรือ สถานะรหัสไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }else {
                        Toast.makeText(SendSterile_MainActivity.this, "แผนก หรือ สถานะรหัสไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        txt_get_ucode.setText("");
                        txt_get_ucode.requestFocus();
                    }

                }catch (Exception e){

                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ucode",params[0]);
                data.put("xdept",params[1]);
                data.put("DocNo",etxt_docno.getText().toString());
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.checkdubplicate_sendsterile(),data);
                Log.d("FKDKDJD", data+"");
                Log.d("FKDKDJD", result);
                return result;
            }
        }
        checkdubplicate ru = new checkdubplicate();
        ru.execute(ucode,xdept);
    }

    public void chkdel(String xDocNo, String xUsageCode) {
        if (IsStatus.equals("1")) {
            Toast.makeText(this, "เอกสารสถานะเตรียมล้างเแก้ไขไม่ได้", Toast.LENGTH_SHORT).show();
        } else {
            DeleteDetail(xDocNo, xUsageCode);
        }

    }


    public void getdept(String user) {
        class getdept extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xCus = new pCustomer();
                        ED_DeptName = c.getString("ED_DepName");
                        ED_Dept = c.getString("ED_dept");
                    }
                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("user", params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getdept(), data);
                return result;
            }
        }
        getdept ru = new getdept();
        ru.execute(user);
    }

    public void getdept_spinner() {
        getdeptsp("x");
        dept_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                deptsp_id = array_deptsp.get(position);
                getlistdata(deptsp_id, edittext.getText().toString(), "");
                cleardoc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etxt_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!IsItemClick) {
                    ED_Deptposition = position + "";
                    ED_Dept = array_deptsp.get(position);
                    ED_DeptName = list_sp.get(position);
                    //getlistdata(deptsp_id, edittext.getText().toString(), "");
                    //cleardoc();
                }
                IsItemClick = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dept_l2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dept_search_l2 = array_deptsp.get(position);
                clear_l2();
                getlistdata_l2(dept_search_l2, date_l2.getText().toString(), "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getdeptsp(String x) {
        class getdeptsp extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    array_deptsp.clear();
                    array_deptsp.add("");

                    list_sp.add("");
                    for (int i = 0; i < setRs.length(); i++) {

                        JSONObject c = setRs.getJSONObject(i);
                        list_sp.add(c.getString("xName"));
                        array_deptsp.add(c.getString("xID"));

                    }
                    adapter_spinner = new ArrayAdapter<String>(SendSterile_MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list_sp);
                    adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dept_spinner.setAdapter(adapter_spinner);
                    etxt_dept.setAdapter(adapter_spinner);
                    dept_l2.setAdapter(adapter_spinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("x", params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getdepartmentsp(), data);
                return result;
            }
        }
        getdeptsp ru = new getdeptsp();
        ru.execute(x);
    }

    public void getitemdetail(String UsageCode) {
        class getitemdetail extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    ArrayList<String> list_sp = new ArrayList<String>();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        list_sp.add(c.getString("xName"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SendSterile_MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list_sp);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dept_spinner.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("UsageCode", params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getitemdetail_sendsterile(), data);
                return result;
            }
        }
        getitemdetail ru = new getitemdetail();
        ru.execute(UsageCode);
    }

    private void openSearchItemStock(String xSel) {
        Intent i = new Intent(SendSterile_MainActivity.this, SearchItem_SendSterile.class);
        i.putExtra("xSel", xSel);
        i.putExtra("ED_Dept", ED_Dept);
        i.putExtra("B_ID",B_ID);
        startActivityForResult(i, 100);
        Log.d("KDJDHF",xSel);
    }


    public void getlistdata_l2(String department_id, String Date, String sreach) {
        class getlistdata_l2 extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    ArrayList<pCustomer> data_l2 = new ArrayList<pCustomer>();
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        pCustomer data2 = new pCustomer();
                        data2.setDocno(c.getString("DocNo"));
                        data2.setDocdate(c.getString("DocDate"));
                        data2.setDept(c.getString("DeptID"));
                        data2.setDeptname(c.getString("DepName2"));
                        data2.setQty(c.getString("Qty"));
                        data2.setNote(c.getString("Remark"));
                        data2.setIsStatus(c.getString("IsStatus"));
                        data2.setTime(c.getString("xtime"));
                        data2.setUserReceive(c.getString("UserReceive"));
                        data2.setUsr_receive(c.getString("usr_receive"));
                        data2.setUserSend(c.getString("UserSend"));
                        data2.setUsr_send(c.getString("usr_send"));
                        data2.setIsWeb(c.getString("IsWeb"));
                        data_l2.add(data2);
                    }

                    list_docno_l2.setAdapter(new SendSterile_washDoclist_Adapter(SendSterile_MainActivity.this, data_l2,B_ID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("department_id", params[0]);
                data.put("date", params[1]);
                data.put("sreach", params[2]);
                data.put("B_ID",B_ID);
                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "sendsterile/getlistdoc_sendsterile_l2.php", data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }
        }
        getlistdata_l2 ru = new getlistdata_l2();
        ru.execute(department_id, Date, sreach);
    }


    public void getlistcreate_l2(final String xDocNo) {
        class getlistcreate_l2 extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());

                    String bo = "";
                    pCus.clear();
                    int cnt = 0;
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        xST.setUsageCode(c.getString("UsageCode"));
                        xST.setItemcode(c.getString("ItemCode"));
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setIsSterile(c.getString("IsSterile"));
                        xST.setXremark(c.getString("Remark"));
                        xST.setDocno(c.getString("DocNo"));
                        xST.setIsStatus(c.getString("IsStatus"));
                        xST.setResteriletype(c.getString("ResterileType"));
                        xST.setResterilename(c.getString("Resterilename"));
                        xST.setDept("DeptID");
                        xST.setUcode(ED_UserCode);
                        xST.setOccuranceID(c.getString("OccuranceQty"));
                        xST.setItemID(c.getString("RowID"));
                        xST.setSs_rowid(c.getString("SS_RowID"));
                        xST.setPackdate(c.getString("Shelflife"));
                        xST.setItemCount(c.getString("ItemCount"));
                        xST.setUsageCount(c.getString("UsageCount"));
                        pCus.add(xST);
                        cnt++;
                    }
                    etxt_docno.setText(xDocNo);
                    etxt_sumqty.setText(cnt + "");
                    list_createdetail_l2.setAdapter(new sendsterile_washdocdetail_adapter(SendSterile_MainActivity.this, pCus,IsAdmin,B_ID,IsDel));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = ruc.sendPostRequest(iFt.geteditdetail(), data);
                    Log.d("YUYU",data+"");
                    Log.d("YUYU",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
        getlistcreate_l2 ru = new getlistcreate_l2();
        ru.execute(xDocNo);
    }

    public void getlistdetail_l2(final String UsageCode) {
        class getlistdetail_l2 extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    int cnt = 0;
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        pCustomer xST = new pCustomer();
                        xST.setItemname(c.getString("itemname"));
                        xST.setXqty(c.getString("Qty"));
                        xST.setUsageCode(UsageCode);
                        pCus.add(xST);
                        cnt++;
                    }
                    list_set_detail_l2.setAdapter(new SendSterile_DocListDetailAdapter(SendSterile_MainActivity.this, pCus, "0"));
                } catch (JSONException e) {
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("UsageCode", params[0]);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "sendsterile/getitemdetail_sendsterile_l2.php", data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
        getlistdetail_l2 ru = new getlistdetail_l2();
        ru.execute(UsageCode);
    }

    public void updateIsclean(String DocNo, String UserCode) {
        class updateIsclean extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);

                        if (c.getString("flag").equals("true")) {
                            Toast.makeText(SendSterile_MainActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SendSterile_MainActivity.this, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("UserCode", params[1]);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "sendsterile/UpdateClean_SS_l2.php", data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
        updateIsclean ru = new updateIsclean();
        ru.execute(DocNo, UserCode);
    }

    public void updatenotclean(String DocNo, String UserCode) {
        class updatenotclean extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);

                        if (c.getString("flag").equals("true")) {
                            Toast.makeText(SendSterile_MainActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SendSterile_MainActivity.this, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("DocNo", params[0]);
                data.put("UserCode", params[1]);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "sendsterile/UpdateNotclean_SS_l2.php", data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        }
        updatenotclean ru = new updatenotclean();
        ru.execute(DocNo, UserCode);
    }

    public void clear_l2() {
        etxt_docno.setText("");
        txt_tel_dep.setText("");
        etxt_sumqty.setText("");
        etxt_dept.setSelection(0);
        list_set_detail_l2.setAdapter(null);
        list_createdetail_l2.setAdapter(null);

    }

    public int findindexspinner(String deptPos) {
        int index = 0;
        for (int i = 0; array_deptsp.size() > i; i++) {
            if (deptPos.equals(array_deptsp.get(i))) {
                index = i;
            }
        }
        return index;
    }

    public int getIndexEmp(String txt) {
        int index = 0;
        for (int i = 0; array_emp.size() > i; i++) {
            if (txt.equals(array_emp.get(i))) {
                index = i;
            }
        }
        return index;
    }



    public String checkRowID() {
        String RowID_chk = "";

        for (int i = 0; i < pCus.size(); i++) {
            if (!pCus.get(i).getChk_box_wash()) {
                RowID_chk = RowID_chk + pCus.get(i).getItemID() + ",";
            }

        }
        if (RowID_chk.length() != 0) {
            RowID_chk = RowID_chk.substring(0, RowID_chk.length() - 1);
        }

        return RowID_chk;
    }

    public void createNewSendSterile(String xDocNo, String RowID_chk) {
        class createNewSendSterile extends AsyncTask<String, Void, String> {
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
                    JSONArray setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String boo = "";
                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        etxt_docno.setText(c.getString("Finish"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("xDocNo", params[0]);
                data.put("RowID_chk", params[1]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(getUrl.xUrl + "sendsterile/cp_check_ss_notclean.php", data);
                Log.d("DLHJF",data+"");
                Log.d("DLHJF",result+"");
                return result;
            }
        }
        createNewSendSterile ru = new createNewSendSterile();
        ru.execute(xDocNo, RowID_chk);
    }

    // open qr
    private void openQR(String data){
        Intent i = new Intent(SendSterile_MainActivity.this, CssdQrUser.class);
        i.putExtra("data", data);
        i.putExtra("B_ID",B_ID);
        startActivityForResult(i, Master.getResult(data));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        if(requestCode == 100) {
            try {
                String RETURN_DATA = data.getStringExtra("RowID");
                String RETURN_QTY = data.getStringExtra("RETURN_Qty");
                String RETURN_xSel = data.getStringExtra("xSel");
//                if (IsStatus.equals("1")) {
//                    Toast.makeText(SendSterile_MainActivity.this, "เอกสารสถานะเตรียมล้างเแก้ไขไม่ได้", Toast.LENGTH_SHORT).show();
//                    txt_get_ucode.setText("");
//                    txt_get_ucode.requestFocus();
//                }
                if (xSel.equals("1")) {
                    InsertDetail1(etxt_docno.getText().toString(), RETURN_DATA, etxt_date.getText().toString(), ED_UserCode, ED_Dept, RETURN_QTY);
                    txt_get_ucode.setText("");
                    txt_get_ucode.requestFocus();
                    Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์สำเร็จ", Toast.LENGTH_SHORT).show();
                }

                if (xSel1.equals("1")) {
                    InsertDetail(etxt_docno.getText().toString(), RETURN_DATA, etxt_date.getText().toString(), ED_UserCode, ED_Dept, RETURN_QTY);
                    if(IsAdmin){
                        addEvenlog("SS",RETURN_DATA,"Add [ "+RETURN_QTY+" ] "+RETURN_DATA+" to "+etxt_docno.getText().toString());
                    }
                    txt_get_ucode.setText("");
                    txt_get_ucode.requestFocus();
                    Toast.makeText(SendSterile_MainActivity.this, "นำเข้าอุปกรณ์สำเร็จ", Toast.LENGTH_SHORT).show();
                }
                openSearchItemStock(RETURN_xSel);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }else {
            try {
                String RETURN_DATA = data.getStringExtra("RETURN_DATA");
                String RETURN_VALUE = data.getStringExtra("RETURN_VALUE");
                String usagecode = data.getStringExtra("usagecode");
                String DocNoSend = data.getStringExtra("DocNoSend");
                if (resultCode == Master.user_receive) {
                    if(txt_usr_receive.getContentDescription() == null){
                        txt_usr_receive.setText(RETURN_DATA);
                        txt_usr_receive.setContentDescription(RETURN_VALUE);
//                        if(IsAdmin){
//                            addEvenlog("SS","","Add UserReceive ID from "+txt_usr_receive.getContentDescription()+" to "+RETURN_VALUE);
//                        }
                    }else{
                        String txt_old_recrive = txt_usr_receive.getContentDescription().toString();
                        txt_usr_receive.setText(RETURN_DATA);
                        txt_usr_receive.setContentDescription(RETURN_VALUE);
                        if(IsAdmin){
                            addEvenlog("SS","","Edit UserReceive ID from "+txt_old_recrive+" to "+RETURN_VALUE);
                        }
                    }
                    updateSendSterile(Master.user_receive, RETURN_VALUE, etxt_docno.getText().toString());
                } else if (resultCode == Master.user_send) {
                    //txt_usr_send.setText(RETURN_DATA);
                    //txt_usr_send.setContentDescription(RETURN_VALUE);
                    //updateSendSterile(Master.user_send, RETURN_VALUE, DocNo);
                }else if (resultCode == 1005){
                    getlistdetail(usagecode,DocNoSend);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // =============================================================================================
    // -- Update Sterile
    // =============================================================================================
    public void updateSendSterile(final int field, final String value, final String doc_no) {
        class Update extends AsyncTask<String, Void, String> {
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
                    Toast.makeText(SendSterile_MainActivity.this, "ไม่สามารถบันทึกข้อมูลได้ !!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_field", Integer.toString(field));
                data.put("p_value", value);
                data.put("p_doc_no", doc_no);
                data.put("B_ID",B_ID);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_send_sterile.php", data);

                return result;
            }

            // =========================================================================================
        }

        Update obj = new Update();
        obj.execute();
    }

    public void getuserCode() {
        class getuserCode extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                JSONArray setRs = null;
                xDepSend.clear();
                ArrayList<String> listUSend = new ArrayList<String>();
                listUSend.clear();
                listUSend.add("");
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        listUSend.add(c.getString("xName"));
                        listUSendID.add(c.getString("xID"));
                        xDepSend.put(c.getString("xName"),c.getString("xID"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SendSterile_MainActivity.this,android.R.layout.simple_spinner_dropdown_item,listUSend);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    txt_usr_send.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                String result = ruc.sendPostRequest(Url.URL + "sendsterile/get_usageCode_spinner.php",data);
                return  result;
            }
        }
        getuserCode ru = new getuserCode();
        ru.execute();
    }

    public void ShowDetail() {
        class ShowDetail extends AsyncTask<String, Void, String> {
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
                        txt_usr_receive.setText(c.getString("FirstName"));
                        if (c.getString("Tel").equals("")){
                            txt_tel_dep.setText("โทร : ไม่มีข้อมูล");
                        }else {
                            txt_tel_dep.setText("โทร : "+c.getString("Tel"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_detail_send_sterile_docno.php", data);
                    Log.d("LFJDLJD",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ShowDetail obj = new ShowDetail();
        obj.execute();
    }

    public void ShowDetailScan(final String DocNoScan) {
        class ShowDetailScan extends AsyncTask<String, Void, String> {
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
                        //txt_usr_receive.setText(c.getString("FirstName"));
                        etxt_docno.setText(c.getString("DocNo"));
                        if (c.getString("Tel").equals("")){
                            txt_tel_dep.setText("โทร : ไม่มีข้อมูล");
                        }else {
                            txt_tel_dep.setText("โทร : "+c.getString("Tel"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNoScan);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_detail_send_sterile_docno.php", data);
                    Log.d("LFJDLJD",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ShowDetailScan obj = new ShowDetailScan();
        obj.execute();
    }

    public void ShowUserSend() {
        class ShowUserSend extends AsyncTask<String, Void, String> {
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
                        txt_usr_receive.setText(c.getString("FirstName")+" "+c.getString("LastName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",etxt_docno.getText().toString());
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_usersend.php", data);
                    Log.d("LFKFLD",data+"");
                    Log.d("LFKFLD",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ShowUserSend obj = new ShowUserSend();
        obj.execute();
    }

    public void CheckInsertDoc(final String Usagecode) {
        class CheckInsertDoc extends AsyncTask<String, Void, String> {
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
                    String txtDocno = "";
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        bo = c.getString("flag");
                        if (bo.equals("false")) {
                            Toast.makeText(SendSterile_MainActivity.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                            txt_get_ucode.setText("");
                            txt_get_ucode.requestFocus();
                        }else if (bo.equals("false1")){
                            Toast.makeText(SendSterile_MainActivity.this, "แผนกหรือสถานะรหัสไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                            txt_get_ucode.setText("");
                            txt_get_ucode.requestFocus();
                        }else if (bo.equals("true")){
                            InsertDetail2(etxt_docno.getText().toString(), txt_get_ucode.getText().toString().toLowerCase(), etxt_date.getText().toString(), ED_UserCode, ED_Dept, "0");
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
                data.put("DocNo",etxt_docno.getText().toString());
                data.put("B_ID",B_ID);
                data.put("Usagecode",Usagecode);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "check_insert_doc.php", data);
                    Log.d("DKDKJDDK",data+"");
                    Log.d("DKDKJDDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckInsertDoc obj = new CheckInsertDoc();
        obj.execute();
    }

    public void CheckUsageCont() {
        class CheckUsageCont extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SendSterile_MainActivity.this);
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
                    }
                    if (DIALOG_ACTIVE == true){
                        if (!condition1.equals("0") || !condition2.equals("0") || !condition3.equals("0") || !condition4.equals("0")){
                            Intent intent = new Intent(SendSterile_MainActivity.this, dialog_check_usage_count.class);
                            intent.putExtra("UsageCode", UsageCode);
                            intent.putExtra("cnt", Cnt);
                            intent.putExtra("DocNo",DocNo);
                            intent.putExtra("B_ID",B_ID);
                            intent.putExtra("sel","1");
                            intent.putExtra("page","0");
                            intent.putExtra("condition1",condition1);
                            intent.putExtra("condition2",condition2);
                            intent.putExtra("condition3",condition3);
                            intent.putExtra("condition4",condition4);
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

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_usage_count.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckUsageCont obj = new CheckUsageCont();
        obj.execute();
    }

    public void CheckUsageContScan() {
        class CheckUsageContScan extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SendSterile_MainActivity.this);
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
                    String UsageCode = "";
                    String UsageItem = "";

                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        UsageCode = c.getString("UsageCode");
                        UsageItem = c.getString("UsageItem");
                        condition1 = c.getString("condition1");
                        condition2 = c.getString("condition2");
                        condition3 = c.getString("condition3");
                        condition4 = c.getString("condition4");
                    }
                    if (condition1 != "0" || condition2 != "0" || condition3 != "0" || condition4 != "0"){
                        if (!ED_Dept.equals("") && !ED_Dept.equals("0")) {
                            if (txt_get_ucode.getText().toString().length() == 12 && !etxt_docno.getText().toString().equals("")) {
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            } else {
//                                checkdubplicate(txt_get_ucode.getText().toString().toLowerCase(), ED_Dept);
//                                txt_get_ucode.setText("");
//                                txt_get_ucode.requestFocus();
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            }
                        } else {
                            if (txt_get_ucode.getText().toString().length() == 12 && !etxt_docno.getText().toString().equals("")) {
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            } else {
//                                checkdubplicate(txt_get_ucode.getText().toString().toLowerCase(), ED_Dept);
//                                txt_get_ucode.setText("");
//                                txt_get_ucode.requestFocus();
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            }
//                            Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกแผนก", Toast.LENGTH_SHORT).show();
//                            txt_get_ucode.setText("");
//                            txt_get_ucode.requestFocus();
                        }
                    }else {
                        if (!ED_Dept.equals("") && !ED_Dept.equals("0")) {
                            if (txt_get_ucode.getText().toString().length() == 12 && !etxt_docno.getText().toString().equals("")) {
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            } else {
//                                checkdubplicate(txt_get_ucode.getText().toString().toLowerCase(), ED_Dept);
//                                txt_get_ucode.setText("");
//                                txt_get_ucode.requestFocus();
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            }
                        } else {
                            if (txt_get_ucode.getText().toString().length() == 12 && !etxt_docno.getText().toString().equals("")) {
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            } else {
//                                checkdubplicate(txt_get_ucode.getText().toString().toLowerCase(), ED_Dept);
//                                txt_get_ucode.setText("");
//                                txt_get_ucode.requestFocus();
                                CheckInsertDoc(txt_get_ucode.getText().toString().toLowerCase());
                            }
//                            Toast.makeText(SendSterile_MainActivity.this, "กรุณาเลือกแผนก", Toast.LENGTH_SHORT).show();
//                            txt_get_ucode.setText("");
//                            txt_get_ucode.requestFocus();
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
                data.put("Usagecode",txt_get_ucode.getText().toString());
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_usage_count_scan.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckUsageContScan obj = new CheckUsageContScan();
        obj.execute();
    }

    public void CheckUsageContScanToInsert(final String Usagecode) {
        class CheckUsageContScanToInsert extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SendSterile_MainActivity.this);
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
                    String UsageCode = "";
                    String UsageItem = "";

                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        UsageCode = c.getString("UsageCode");
                        UsageItem = c.getString("UsageItem");
                        condition1 = c.getString("condition1");
                        condition2 = c.getString("condition2");
                        condition3 = c.getString("condition3");
                        condition4 = c.getString("condition4");
                    }
                    if (DIALOG_ACTIVE = true){
                        if (!condition1.equals("0") || !condition2.equals("0") || !condition3.equals("0") || !condition4.equals("0")){
                            Intent intent = new Intent(SendSterile_MainActivity.this, dialog_check_usage_count.class);
                            intent.putExtra("condition1",condition1);
                            intent.putExtra("condition2",condition2);
                            intent.putExtra("condition3",condition3);
                            intent.putExtra("condition4",condition4);
                            intent.putExtra("page","0");
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
                data.put("Usagecode",Usagecode);
                data.put("B_ID",B_ID);
                data.put("Type","0");
                data.put("DocNo",etxt_docno.getText().toString());
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_usage_count_scan.php", data);
                    Log.d("DKCKCNKD",data+"");
                    Log.d("DKCKCNKD",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckUsageContScanToInsert obj = new CheckUsageContScanToInsert();
        obj.execute();
    }

    public void DelAll (String DelDocno, String DelRowid){
        DelDocNo = DelDocno;
        String x="0";
        if(DelAlldata.get(DelRowid)=="0"){
            x="1";
        }
        DelAlldata.put(DelRowid, x);
        Log.d("LFHLKD",DelAlldata+"");
    }

    public void CheckStatusDocNo() {
        class CheckStatusDocNo extends AsyncTask<String, Void, String> {
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
                        if (c.getString("IsStatus").equals("0")){
                            CheckUsageCont();
                            DIALOG_ACTIVE = true;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_status_docno.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckStatusDocNo obj = new CheckStatusDocNo();
        obj.execute();
    }

    public void ScanUserReceive() {
        class ScanUserReceive extends AsyncTask<String, Void, String> {
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
                    String UsageCode = "";
                    String UsageItem = "";
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("finish").equals("true")){
                            txt_usr_receive.setText(c.getString("FirstName")+" "+c.getString("LastName"));
                            txt_get_ucode.requestFocus();
                        }else {
                            Toast.makeText(SendSterile_MainActivity.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
                            txt_usr_receive.setText("");
                            txt_usr_receive.requestFocus();
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
                data.put("usr_receive",txt_usr_receive.getText().toString());
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_scan_user_receive.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ScanUserReceive obj = new ScanUserReceive();
        obj.execute();
    }

    public void LoadImg(final String itemcode,final String sel,final String usagecode,final String itemname,final String type) {
        Log.d("FKJDHJKDH",type+"");
        Log.d("FKJDHJKDH",itemcode+"");
        Log.d("FKJDHJKDH",sel+"");
        Log.d("FKJDHJKDH",usagecode+"");
        Log.d("FKJDHJKDH",itemname+"");
        if (type.equals("remark")){
            Intent intent = new Intent(SendSterile_MainActivity.this, dialog_Load_Img.class);
            intent.putExtra("itemcode", itemcode);
            intent.putExtra("usagecode", usagecode);
            intent.putExtra("itemname", itemname);
            intent.putExtra("sel",sel);
            intent.putExtra("type",type);
            startActivity(intent);
        }else {
            Intent intent = new Intent(SendSterile_MainActivity.this, dialog_Load_Img.class);
            intent.putExtra("itemcode", itemcode);
            intent.putExtra("usagecode", usagecode);
            intent.putExtra("itemname", itemname);
            intent.putExtra("sel",sel);
            intent.putExtra("type",type);
            startActivity(intent);
        }
    }

    public void OpenDialog(final String Itemname , final String type) {
        Intent intent = new Intent(SendSterile_MainActivity.this, dialog_remark_sendsterile.class);
        intent.putExtra("Itemname", Itemname);
        intent.putExtra("Usagecode", Usagecode);
        intent.putExtra("DepID", DepID);
        intent.putExtra("DocNoSend", DocNoSend);
        intent.putExtra("EmpCode",EmpCode);
        intent.putExtra("IsAdmin",IsAdmin);
        intent.putExtra("IsStatus",IsStatus);
        intent.putExtra("DocNo",etxt_docno.getText().toString());
        intent.putExtra("Type",type);
        intent.putExtra("context", String.valueOf(SendSterile_MainActivity.this));
        startActivityForResult(intent,1005);
    }

    public void showAndhideBlueHead(boolean x) {
        txt_usr_receive.setEnabled(x);
        txt_usr_send.setEnabled(x);
        etxt_dept.setEnabled(x);
        etxt_sumqty.setEnabled(x);
        txt_get_ucode.setEnabled(x);
        bt_search_sendsteril.setEnabled(x);
        bt_search_sendsteril1.setEnabled(x);
    }

    public void UsageCode(String UsageCode,String Depid,String Docnosend) {
        Usagecode = UsageCode;
        DepID = Depid;
        DocNoSend = Docnosend;
    }

}
