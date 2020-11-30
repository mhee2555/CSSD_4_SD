package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.PayoutDetailAdapter;
import com.phc.cssd.adapter.PayoutNotfullyAdapter;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.DateThai;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.viewbinder.CustomExpandableListAdapter;
import com.phc.cssd.viewbinder.ExpandableListDataPump;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PayoutActivity extends AppCompatActivity {
    ArrayList<String> UserCode = new ArrayList<>();
    String RefDocNo = "";
    String DocNo = "";
    String DocNoIsStatus = "";
    String DocNoIsCheck = "0";
    String userid;
    int PrintCnt = 0;
    Intent intent;
    int chk_txt_qty = 0;
    int chk_txt_xqty = 0;
    int WithdrawMode = 0;
    ArrayList<Response_Aux> resultspayout = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultspayoutdetail = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultspayout_notfully = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultsDepartment = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> emptyarray = new ArrayList<Response_Aux>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    private static final String TAG_RESULTS = "result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
//    xControl xCtl = new xControl();
    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;
    float x1,x2,y1,y2;

    TextView gDate;
    boolean mode_check = true;
    String refDocNo = "";
    Button bDate;
    Button b_Back;
    ImageView Search;
    ImageView Save;
    ImageView Print;
    SearchableSpinner spinner01;
    SearchableSpinner spinner03;

    CheckBox checkBox;
    String xSearch = "";
    TextView textViewDocNo;
    DateThai Dthai;
    EditText eUsageCode;
    String xIsStatus = "0";
    Button bt_additem;
    Button bt_createdoc;
    TextView bt_notfully;
    TextView txt_notfully;
    Switch bt_switch;
    Switch bt_isWithdraw;
    Button bt_chkbox_delete;
    Spinner spCount;
    TextView textViewDate1;

    public boolean IsAdmin = false;

    private String IsAdmin_T = "";
    private String IsInCharg = "";
    private String IsUser = "";

    private String IsAdmin_Log = "";
    private String IsInCharg_Log = "";
    private String IsUser_Log = "";

    boolean isnewDoc = false;
    String B_ID;
    String B_IDTT;

    String EmpCode = "";
    String user_name = "";
    private String ED_UserCode = "";

    ArrayList<String> xDataUserCode = new ArrayList<String>();
    private String UserSelect;
    boolean dev = false;

    public static int devicemode = 0;
    Boolean showHmenu = false;
    public static int IsT2 = 0;
    public static int IsL2 = 1;
    private LinearLayout rLayout;
    private LinearLayout lLayout;
    private int posDepSelect;


//    private Button b_IPDOPD;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if(width>720){//T2lite
            setContentView(R.layout.activity_payout);
            byIntent();
            devicemode = IsT2;
        }else{//L2
            setContentView(R.layout.activity_payout_l2);
            byIntent();
            devicemode = IsL2;
            Button logout = (Button)  findViewById(R.id.logout);
            Button menu = (Button)  findViewById(R.id.menu);
            final LinearLayout Hmenu = (LinearLayout) findViewById(R.id.Hmenu);
            final LinearLayout titlemenu = (LinearLayout) findViewById(R.id.titlemenu);

            Hmenu.setVisibility(View.GONE);
            showHmenu = false;
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(showHmenu){
                        Hmenu.setVisibility(View.GONE);
                        showHmenu = false;
                    }else{
                        Hmenu.setVisibility(View.VISIBLE);
                        showHmenu = true;
                    }

                }
            });

            Hmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(showHmenu){
                        Hmenu.setVisibility(View.GONE);
                        showHmenu = false;
                    }else{
                        Hmenu.setVisibility(View.VISIBLE);
                        showHmenu = true;
                    }

                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PayoutActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });

            rLayout = (LinearLayout) findViewById(R.id.R);
            lLayout = (LinearLayout) findViewById(R.id.L);

            rLayout.setVisibility(View.GONE);
            lLayout.setVisibility(View.VISIBLE);
        }

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        getSupportActionBar().hide();
        setTitle("บันทึกจ่ายออก");
        textViewDate1 = (TextView) findViewById(R.id.textViewDate1);
        textViewDocNo = (TextView) findViewById(R.id.textViewDocNo);
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("EEE,d,M,yyyy");
        String formattedCurrentDate = simpleDateFormat.format(currentDate);
        Dthai = new DateThai();
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        bt_isWithdraw = (Switch) findViewById(R.id.isWithdraw);
        gDate = (TextView) findViewById(R.id.gDate);
        bDate = (Button) findViewById(R.id.bDate);
        b_Back = (Button) findViewById(R.id.b_Back);
        Search = (ImageView) findViewById(R.id.button01);
        Save = (ImageView) findViewById(R.id.button02);
        Print = (ImageView) findViewById(R.id.button03);
        spinner01 = ( SearchableSpinner ) findViewById(R.id.spinner01);
        spinner01.setTitle("เลือกแผนก");//lang
        spinner01.setPositiveButton("");//lang
        spinner01.requestFocus();

        spinner03 = ( SearchableSpinner ) findViewById(R.id.spinner3);
        spinner03.setTitle("เลือกผู้รับ");//lang
        spinner03.setPositiveButton("");//lang
        spinner03.requestFocus();

        expandableListView = (ExpandableListView) findViewById(R.id.elView);
        bt_additem = (Button) findViewById(R.id.bt_additem);
        bt_createdoc= (Button) findViewById(R.id.bt_createdoc);
        bt_notfully= (TextView) findViewById(R.id.bt_notfully);
        txt_notfully = (TextView) findViewById(R.id.txt_notfully);
        bt_switch = (Switch) findViewById(R.id.bt_switch);
        bt_chkbox_delete= (Button) findViewById(R.id.bt_chkbox_delete);

        eUsageCode = (EditText) findViewById(R.id.eUsageCode);
        eUsageCode.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
//                            if(WithdrawMode==1){
//                                String itemCode = (eUsageCode.getText().toString().toUpperCase()).substring(0,6);
//                                if(textViewDocNo.getText().toString().equals("")){
//                                    CreatePayoutDocument(userid,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),itemCode,"1");
//                                    isnewDoc = true;
//                                    bt_additem.setEnabled(false);
//                                    bt_createdoc.setEnabled(false);
//                                }else{
//                                    if(isnewDoc){
//                                        Insert_PayoutDetail(DocNo,itemCode,"1");
//                                    }else{
//                                        if(mode_check){
//                                            payoutInSertSub( DocNo,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),eUsageCode.getText().toString().toUpperCase());
//                                        }else{
//                                            payoutIsCheckPay(DocNo,eUsageCode.getText().toString());
//                                        }
//                                        eUsageCode.setText("");
//                                        eUsageCode.requestFocus();
//                                        return true;
//                                    }
//                                }
//                            }else{
//                                if(mode_check){
//                                    payoutInSertSub( DocNo,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),eUsageCode.getText().toString().toUpperCase());
//                                }else{
//                                    payoutIsCheckPay(DocNo,eUsageCode.getText().toString());
//                                }
//                                eUsageCode.setText("");
//                                eUsageCode.requestFocus();
//                                return true;
//                            }

                            String itemCode = (eUsageCode.getText().toString().toUpperCase()).substring(0,5);
                            if(textViewDocNo.getText().toString().equals("")){
                                CreatePayoutDocument(userid,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),itemCode,"1");
                                isnewDoc = true;
                                bt_additem.setEnabled(false);
                                bt_createdoc.setEnabled(false);
                            }else{
                                if(isnewDoc){
                                    Insert_PayoutDetail(DocNo,itemCode,"1");
                                }else{
                                    if(mode_check){
                                        payoutInSertSub( DocNo,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),eUsageCode.getText().toString().toUpperCase());
                                    }else{
                                        payoutIsCheckPay(DocNo,eUsageCode.getText().toString());
                                    }
                                    eUsageCode.setText("");
                                    eUsageCode.requestFocus();
                                    return true;
                                }
                            }

                        default:
                            break;
                    }
                }
                return false;
            }
        });

        bt_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // do something when checked is selected
                    eUsageCode.setHint("เช็ครายการ");
                    mode_check = false;
                    Toast.makeText(PayoutActivity.this, "เช็ครายการ", Toast.LENGTH_SHORT).show();
                } else {
                    //do something when unchecked

                    eUsageCode.setHint("นำเข้ารายการ");
                    mode_check = true;
                    Toast.makeText(PayoutActivity.this, "นำเข้ารายการ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_isWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isChecked = bt_isWithdraw.isChecked();
                if(isnewDoc){
                    if (!isChecked) {
                        ArkCancelPayout();
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                builder.setCancelable(true);
                builder.setTitle("ยืนยัน");
                builder.setMessage("คุณต้องการออกจากเอกสารนี้หรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switchMode(isChecked);
                            }
                        });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bt_isWithdraw.setChecked(!isChecked);
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();

                if(DocNo!=""){
                    dialog.show();
                }else{
                    switchMode(isChecked);
                }

                spinner03.setSelection(0);
            }
        });

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            RefDocNo = bd.getString("DocNo");
            userid =  bd.getString("userid");
            IsAdmin = bd.getBoolean("IsAdmin");
            B_ID = bd.getString("B_ID");
            B_IDTT = bd.getString("B_IDTT");

        }
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        String[] partsCollArr;
        String delimiter = ",";
        partsCollArr = formattedCurrentDate.split(delimiter);

        String ft = Dthai.getDayThai( partsCollArr[0] ) + " "
                + partsCollArr[1] + " "
                + Dthai.getMonthThai( Integer.parseInt( partsCollArr[2] ) ) + " "
                + Dthai.getYearThai( Integer.parseInt( partsCollArr[3] ) );
        //วันที่ปัจจุบัน
        //textViewDate1.setText( ft );
        textViewDate1.setText("");
        // textViewDate1.setText( resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields2() );

        gDate.setText(TwoZero(day+"" )+ "-" + TwoZero((month + 1)+"") + "-" + year);

        bDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On button click show datepicker dialog
                if(isnewDoc){
                    ArkCancelPayout();
                }else{
                    showDialog(DATE_PICKER_ID);
                }
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isnewDoc){
                    ArkCancelPayout();
                    ListDepartment(spinner01,TwoZero(day+"" )+ "-" + TwoZero((month + 1)+"") + "-" + year , PayoutActivity.this);
                }else{
                    onSearch();
                    ListDepartment(spinner01,TwoZero(day+"" )+ "-" + TwoZero((month + 1)+"") + "-" + year , PayoutActivity.this);
                }
                //โชว์แผนก
//                textViewDate1.setText( resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields2() );
//                xSearch = gDate.getText() +","+ resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1();
//                Log.d("xSearch: ", gDate.getText() +","+ resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1());
//                if(checkBox.isChecked() == true)
//                    ListPayoutDocument("1",xSearch);
//                else
//                    ListPayoutDocument("0",xSearch);
//                payoutnotfully("");
//                ListPayoutDetail( "" );
//                DocNo="";
//                textViewDocNo.setText("");
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ttest_save_doc","DocNo : "+DocNo);
                if(!DocNo.equals("")){
//                        if(spinner03.getSelectedItemPosition()!=0) {
//                            if (!txt_notfully.getText().toString().equals("0")) {
//                                if(WithdrawMode == 1){
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
//                                    builder.setCancelable(true);
//                                    builder.setTitle("ยืนยัน");
//                                    builder.setMessage("มีรายการค้างจ่ายจำนวน " + txt_notfully.getText().toString() + " ต้องการยกเลิกรายการที่ค้างหรือไม่?");
//                                    builder.setPositiveButton("ใช่",
//                                            new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    deletepayoutdetailfromsub(DocNo);
//                                                }
//                                            });
//                                    builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    AlertDialog dialog = builder.create();
//                                    dialog.show();
//                                }else{
//
//                                    Set<String> itemcode_notfully_set = new HashSet<String>();
//                                    for(int i =0;i<resultspayout_notfully.size();i++){
//                                        itemcode_notfully_set.add(resultspayout_notfully.get(i).getFields4());
//                                        Log.d("payout_notfully","resultspayout_notfully : "+resultspayout_notfully.get(i).getFields4());
//                                    }
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
//                                    builder.setCancelable(true);
//                                    builder.setTitle("จ่ายอุปกรณ์ไม่ครบ");
//                                    builder.setMessage("มีรายการค้างจ่าย " +itemcode_notfully_set.size()+" รายการ ทั้งหมด "+ txt_notfully.getText().toString() + " ชิ้น ต้องการดำเนินการต่อหรือไม่?");
//                                    builder.setPositiveButton("ดำเนินการต่อ",
//                                            new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    getQR(DocNo, "pa", "");
//                                                    isnewDoc = false;
//                                                    bt_additem.setEnabled(true);
//                                                    bt_createdoc.setEnabled(true);
//                                                }
//                                            });
//                                    builder.setNegativeButton("ยกเลิกการจ่าย", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    });
//                                    AlertDialog dialog = builder.create();
//                                    dialog.show();
//                                }
//                            } else {
//                                getQR(DocNo, "pa", "");
//                                isnewDoc = false;
//                                bt_additem.setEnabled(true);
//                                bt_createdoc.setEnabled(true);
//                            }
//                        }else{
//                            Toast.makeText(PayoutActivity.this, "กรุณาเลือกผู้รับ!!", Toast.LENGTH_SHORT).show();
//                        }

                    if (!txt_notfully.getText().toString().equals("0")) {
                        if(WithdrawMode == 1){
                            AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("ยืนยัน");
                            builder.setMessage("มีรายการค้างจ่ายจำนวน " + txt_notfully.getText().toString() + " ต้องการยกเลิกรายการที่ค้างหรือไม่?");
                            builder.setPositiveButton("ใช่",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deletepayoutdetailfromsub(DocNo);
                                        }
                                    });
                            builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{

                            Set<String> itemcode_notfully_set = new HashSet<String>();
                            for(int i =0;i<resultspayout_notfully.size();i++){
                                itemcode_notfully_set.add(resultspayout_notfully.get(i).getFields4());
                                Log.d("payout_notfully","resultspayout_notfully : "+resultspayout_notfully.get(i).getFields4());
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("จ่ายอุปกรณ์ไม่ครบ");
                            builder.setMessage("มีรายการค้างจ่าย " +itemcode_notfully_set.size()+" รายการ ทั้งหมด "+ txt_notfully.getText().toString() + " ชิ้น ต้องการดำเนินการต่อหรือไม่?");
                            builder.setPositiveButton("ดำเนินการต่อ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            getQR(DocNo, "pa", "");
                                            isnewDoc = false;
                                            bt_additem.setEnabled(true);
                                            bt_createdoc.setEnabled(true);
                                        }
                                    });
                            builder.setNegativeButton("ยกเลิกการจ่าย", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } else {
                        getQR(DocNo, "pa", "");
                        isnewDoc = false;
                        bt_additem.setEnabled(true);
                        bt_createdoc.setEnabled(true);
                    }
                }else{
                    Toast.makeText(PayoutActivity.this, "ยังไม่ได้เลือกเอกสาร!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_chkbox_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("isnewBorrow","DocNoIsCheck : "+DocNoIsCheck);
                if(!DocNo.equals("")&&DocNoIsCheck.equals("1")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("คุณต้องการลบรายการที่เลือกใช่หรือไม่?");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    payoutdeletecheckbox(DocNo,checkRowID());
                                    ListPayoutDetail(DocNo);
                                    payoutnotfully(DocNo);
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
                    Toast.makeText(PayoutActivity.this, "ยังไม่ได้เลือกเอกสาร", Toast.LENGTH_SHORT).show();
                }

            }
        });

        b_Back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(PayoutActivity.this, Menu.class);
                //startActivity(intent);
                if(isnewDoc){
                    ArkCancelPayout();
                }else{
                    finish();
                }

            }
        });

        Print.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!DocNo.equals("")){
                    GetReport(DocNo,userid,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),"2", "0" ,(PrintCnt)+"");
                }else{
                    Toast.makeText(PayoutActivity.this, "ยังไม่ได้เลือกเอกสาร", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayoutActivity.this,Payout_additemActivity.class);
                intent.putExtra("userid",userid);
                intent.putExtra("IsAdmin", IsAdmin_T);
                intent.putExtra("IsInCharg", IsInCharg);
                intent.putExtra("IsUser", IsUser);
                intent.putExtra("IsAdmin_Log", IsAdmin_Log);
                intent.putExtra("IsInCharg_Log", IsInCharg_Log);
                intent.putExtra("IsUser_Log", IsUser_Log);
                intent.putExtra("WithdrawMode",WithdrawMode+"");
                intent.putExtra("deptid",resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1());
                intent.putExtra("date",gDate.getText());
                intent.putExtra("B_ID", B_ID);
                intent.putExtra("xSel","9");
                intent.putExtra("devicemode", devicemode);
                startActivity(intent);
            }
        });
        bt_createdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayoutActivity.this,Payout_CreateNewDocActivity.class);
                intent.putExtra("userid",userid);
                intent.putExtra("deptid",resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1());
                intent.putExtra("date",gDate.getText());
                intent.putExtra("mode",WithdrawMode);
                intent.putExtra("B_ID", B_ID);
                intent.putExtra("devicemode", devicemode);
                //intent.putExtra("xSel",xSel);
                startActivity(intent);
            }
        });

        bt_notfully.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DocNo.equals("")){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(PayoutActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View convertView;
                    if(devicemode==PayoutActivity.IsT2){
                        convertView= (View) inflater.inflate(R.layout.list_dialog_payounotfully, null);
                    }else{
                        convertView= (View) inflater.inflate(R.layout.list_dialog_payounotfully_l2, null);
                    }

                    alertDialog.setView(convertView);
                    final AlertDialog p = alertDialog.show();
                    TextView text_refdoc = (TextView) convertView.findViewById(R.id.text_refdoc);
                    TextView txt_remark = (TextView) convertView.findViewById(R.id.txt_remark);
                    TextView txt_headdoc = (TextView) convertView.findViewById(R.id.txt_headdoc);
                    txt_headdoc.setText("เลขที่เอกสาร :"+DocNo);
                    //text_refdoc.setText("เอกสารอ้างอิง : "+refdoc);
                    //txt_remark.setText("หมายเหตุ : "+remark);
                    ListView lv = (ListView) convertView.findViewById(R.id.lv);
                    lv.setAdapter(new PayoutNotfullyAdapter(PayoutActivity.this, resultspayout_notfully,devicemode));
                    TextView txt_back = (TextView) convertView.findViewById(R.id.txt_back);
                    txt_back.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            p.dismiss();
                        }
                    });
                    TextView bt_finish = (TextView) convertView.findViewById(R.id.bt_finish);
                    bt_finish.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if(!DocNoIsStatus.equals("2")&&!DocNoIsStatus.equals("")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle("ยืนยัน");
                                builder.setMessage("ต้องการปิดเอกสารเลยหรือไม่");
                                builder.setPositiveButton("ตกลง",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ImportPayout("1",DocNo,"close");
                                                finishpayout(DocNo,"close");
                                                ListPayoutDetail("");
                                                payoutnotfully("");
                                                ListPayoutDocument("0",xSearch);
                                                textViewDocNo.setText("");
                                                spinner03.setSelection(0);
//                                                xDataUserCode.add("");
                                                p.dismiss();
                                            }
                                        });
                                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        p.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{
                                Toast.makeText(PayoutActivity.this, "เอกสารนี้ถูกปิดแล้ว", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(PayoutActivity.this, "ยังไม่ได้เลือกเอกสาร", Toast.LENGTH_SHORT).show();
                }

            }
        });

        spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(isnewDoc){
                    ArkCancelPayout();
                }else{
                    textViewDate1.setText( resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields16() );
                    xSearch = gDate.getText() +","+ resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1();
                    if(checkBox.isChecked() == true)
                        ListPayoutDocument("1",xSearch);
                    else
                        ListPayoutDocument("0",xSearch);
                    payoutnotfully("");
                    ListPayoutDetail( "" );
                    DocNo="";
                    textViewDocNo.setText("");
                    spinner03.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

//        bt_notfully.bringToFront();
        txt_notfully.bringToFront();
        txt_notfully.setText("0");

//        Log.d("ttest","1111111111111111111");
//        getuserCode();
        getdept_spinner();
//        Log.d("ttest","2222222222222222222");

        spinner03.setSelection(0);
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
        userid = intent.getStringExtra("userid");
        user_name = intent.getStringExtra("user_name");

        IsAdmin_Log = intent.getStringExtra("IsAdmin_Log");
        IsInCharg_Log = intent.getStringExtra("IsInCharg_Log");
        IsUser_Log = intent.getStringExtra("IsUser_Log");
        IsAdmin_T = intent.getStringExtra("IsAdmin");
        IsInCharg = intent.getStringExtra("IsInCharg");
        IsUser = intent.getStringExtra("IsUser");

    }

//    public boolean onTouchEvent(MotionEvent touchEvent){
//        switch (touchEvent.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1 = touchEvent.getX();
//                y1 = touchEvent.getY();
//                Log.d("KFDJDL",x1+"");
//                Log.d("KFDJDL",y1+"");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("KFDJDL",x2+"");
//                Log.d("KFDJDL",y2+"");
//                x2 = touchEvent.getX();
//                y2 = touchEvent.getY();
//                if (x1 < x2){
//                    gotoActivity(SterileSeachActivity.class);
//                }else if (x1 > x2){
//
//                }
//                break;
//        }
//        return false;
//    }

    boolean Resume1st =true;
    @Override
    public void onResume(){
        super.onResume();
        ListDepartment(spinner01,TwoZero(day+"" )+ "-" + TwoZero((month + 1)+"") + "-" + year , PayoutActivity.this);
        if(!Resume1st){
            onSearch();
        }

        Resume1st =false;
    }

    public void switchMode(boolean isChecked) {
        if (isChecked) {
            WithdrawMode = 1;
            bt_isWithdraw.setText("ยืม");
            ListPayoutDocument("0", xSearch);
//                        bt_notfully.setEnabled(false);
            bt_switch.setEnabled(false);
            //                    mode_check=false;
            //                    Toast.makeText(PayoutActivity.this, "เช็ครายการ", Toast.LENGTH_SHORT).show();
        } else {
            WithdrawMode = 0;
            bt_isWithdraw.setText("เบิก");
            ListPayoutDocument("0", xSearch);
            isnewDoc =false;

            bt_additem.setEnabled(true);
            bt_createdoc.setEnabled(true);
            bt_notfully.setEnabled(true);
            bt_switch.setEnabled(true);
            //                    mode_check=true;
            //
        }
        ListPayoutDetail("");
        payoutnotfully("");
        textViewDocNo.setText("");
        spinner03.setSelection(0);
        DocNo="";
    }

    public void ArkCancelPayout() {
        final boolean isChecked = bt_isWithdraw.isChecked();
        Log.d("Tog_ArkCancelPayout_","isChecked = "+isChecked);

        AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
        builder.setCancelable(true);
        builder.setTitle("ยืนยัน");
        builder.setPositiveButton("ใช่",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isnewDoc = false;
                        CancelPayout(DocNo);

                        bt_additem.setEnabled(true);
                        bt_createdoc.setEnabled(true);
                    }
                });
        builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                    bt_createdoc.setEnabled(true);
            }
        });

        if (isChecked) {
            builder.setMessage("ต้องการปิดเอกสารยืมนี้หรือไม่");
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
//            if(resultspayoutdetail.size()<=0){
//                builder.setMessage("ต้องการปิดเอกสารเบิกนี้หรือไม่");
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }else {
//                isnewDoc = false;
//                bt_additem.setEnabled(true);
//                bt_createdoc.setEnabled(true);
//            }

            isnewDoc = false;
            bt_additem.setEnabled(true);
            bt_createdoc.setEnabled(true);
        }

    }

    public void CancelPayout(final String xDocNo) {
        class CancelPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        onSearch();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("Insert : ", data+"");

                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_cancelpayout_and_detail.php",data);
                return  result;
            }
        }
        CancelPayout ru = new CancelPayout();
        ru.execute( xDocNo);
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
//                List<String> list = new ArrayList<String>();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    xDataUserCode.clear();
                    UserCode.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(dev){
                            xDataUserCode.add(c.getString("xName")+"--"+c.getString("xID"));
                        }else{
                            xDataUserCode.add(c.getString("xName"));
                        }
                        UserCode.add(c.getString("xID"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(PayoutActivity.this,android.R.layout.simple_spinner_dropdown_item,xDataUserCode);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner03.setAdapter(adapter);
//                    int spinnerPosition = adapter.getPosition(Depsel);
                    if(dev){
                        spinner03.setSelection(1000);
                    }else{
                        spinner03.setSelection(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                String result = ruc.sendPostRequest(iFt.getUserCode_spinner(),data);
//                Log.d("ttest1",result);
                return  result;
            }
        }
        getuserCode ru = new getuserCode();
        ru.execute();
    }

    public void getdept_spinner() {
        getuserCode();
        spinner03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserSelect = UserCode.get(position);
                Log.d("ttest_reUser","UserSelect = "+UserSelect);
                if(dev){
                    Toast.makeText(PayoutActivity.this, UserSelect, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onSearch(){

        textViewDate1.setText( resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields2() );
        xSearch = gDate.getText() +","+ resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1();
        Log.d("xSearch: ", gDate.getText() +","+ resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1());
        if(checkBox.isChecked() == true)
            ListPayoutDocument("1",xSearch);
        else
            ListPayoutDocument("0",xSearch);
        payoutnotfully("");
        ListPayoutDetail( "" );
        DocNo="";
        textViewDocNo.setText("");
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        //replaces the default 'Back' button action
////        if(keyCode==KeyEvent.KEYCODE_ENTER){
////            Log.d("OOOO","Enter :: " + eUsageCode.getText());
////        }
//        return true;
//    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            Log.d("ttestDate","in DatePickerDialog");
            // Show selected date
            gDate.setText(TwoZero(day+"" )+ "-" + TwoZero((month + 1)+"") + "-" + year);
            xSearch = gDate.getText() +","+ resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1();
            ListDepartment(spinner01,TwoZero(day+"" )+ "-" + TwoZero((month + 1)+"") + "-" + year, PayoutActivity.this);
            ListPayoutDocument("0",xSearch);

        }
    };

    public void ImportPayout(final String Sel,final String xDocNo,final String type) {
        class ImportPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if( c.getString("Finish").equals("true")){
                            ListPayoutDocument("0",xSearch);
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
                data.put("xSel",params[0]);
                data.put("xDocNo",params[1]);
                if (UserSelect == null){
                    data.put("UserCode","");
                }else {
                    data.put("UserCode",UserSelect);
                }
                data.put("type",type);
                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(iFt.getsavepayout(),data);
                Log.d("DLCLDJ",data+"");
                Log.d("DLCLDJ",result);
                return  result;
            }
        }
        ImportPayout ru = new ImportPayout();
        ru.execute( Sel,xDocNo );
    }

    public void ConvertPayout(final String xDocNo,String xDept) {
        class ConvertPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        //if( c.getString("Finish").equals("true")){
//                        savepayout_all( DocNo );
                        ListPayoutDetail(DocNo);
                        //}
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("xDept",params[1]);
//                Log.d("OOOO","Convert : " + params[0]);
                String result = ruc.sendPostRequest(iFt.getconvertpayout(),data);
                Log.d("DKFJDCD",data+"");
                Log.d("DKFJDCD",result);
                return  result;
            }
        }
        ConvertPayout ru = new ConvertPayout();
        ru.execute( xDocNo,xDept );
    }

    public void ListPayoutDocument(String xSel,String xSearch) {
        class ListSterileDocument extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
//                    JSONObject p = setRs.getJSONObject(0);
                    Log.d("B_ID: ", setRs.length()+"");
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);

                        Log.d("B_ID", "DocNo: " + c.getString("xDocNo") + " / Status: " + c.getString("xIsStatus"));

                        newsData.setFields1(c.getString("xDocNo"));
                        newsData.setFields2(c.getString("xDocDate"));
                        newsData.setFields3(c.getString("xDepName2"));
                        newsData.setFields4(c.getString("xQty"));
                        newsData.setFields5(c.getString("xPayQty"));
                        newsData.setFields6(c.getString("xRemark"));
                        newsData.setFields7(c.getString("xIsStatus"));
                        newsData.setFields8(c.getString("xIsPrint"));
                        newsData.setFields9(c.getString("xItemCnt"));
                        newsData.setFields10(c.getString("xIsCheck"));
                        newsData.setFields11(c.getString("xRefDocNo"));
                        newsData.setFields12(c.getString("xIsSpecial"));
                        newsData.setFields13(c.getString("xPrintCount"));
                        newsData.setFields14(c.getString("IsBorrow"));
                        Log.d("ttest",c.getString("xDocNo")+"---"+c.getString("IsBorrow"));
                        if (!c.getString("xDocNo").equals("")) resultspayout.add( newsData );
                    }

                    if(setRs.length()>0) {
                        initData(resultspayout);
                    }else{
                        initData(resultspayout);
                        Toast.makeText(PayoutActivity.this, "ไม่พบรายการ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xSel",params[0]);
                data.put("xSearch",params[1]);
                Log.d("B_ID: ", B_ID+"");
                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(iFt.getpayoutdocument(),data);
                Log.d("FLKVFPK",data+"");
                Log.d("FLKVFPK",result+"");
                return  result;
            }
        }
        ListSterileDocument ru = new ListSterileDocument();
        ru.execute( xSel,xSearch );
    }

    private void initData(ArrayList<Response_Aux> getData) {
        String xDate = gDate.getText().toString().substring(0,2)+"-"+gDate.getText().toString().substring(3,5)+"-"+gDate.getText().toString().substring(6,10);
        Log.d("GFPKVF",xDate);
        expandableListDetail = ExpandableListDataPump.getData(getData,xDate,WithdrawMode);
        List<Map.Entry<String,List<String>>> entries = new ArrayList<Map.Entry<String,List<String>>>(expandableListDetail.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String,List<String>>>() {
            public int compare(Map.Entry<String,List<String>> l1, Map.Entry<String,List<String>> l2) {
                String o1 = l1.getKey().substring(0,1);
                String o2 = l2.getKey().substring(0,1);
                return o1.compareTo(o2);
            }
        });
        expandableListDetail.clear();
        expandableListTitle = new ArrayList<String>();
        for (Map.Entry<String,List<String>> e : entries) {
            expandableListTitle.add(e.getKey());
            expandableListDetail.put(e.getKey(),e.getValue());
        }
//        Log.d( "OOOO","expandableListDetail: "+ expandableListDetail );
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();

//               final ImageView ivArrow = expandableListAdapter.getArrow();
//                ivArrow.animate().rotationBy(90)
//                        .start();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

//                final ImageView ivArrow = dirViewHolder.getIvArrow();
//                ivArrow.animate().rotationBy(-90)
//                        .start();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String Data = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                Log.d( "isnewBorrow","Data : "+ Data );
                DocNo = Data.split(",")[0];
                int IsStatus = Integer.parseInt( Data.split(",")[1] );
                int IsPrint = Integer.parseInt( Data.split(",")[2] );
                int IsCheck = Integer.parseInt( Data.split(",")[3] );
                PrintCnt = Integer.parseInt( Data.split(",")[7] );
                DocNoIsCheck=IsCheck+"";
                refDocNo = Data.split(",")[4] ;
                int IsSpecial = Integer.parseInt( Data.split(",")[5] );

//            Log.d( "OOOO","Status : "+ IsStatus );
                //Toast.makeText( getApplicationContext(),DocNo +" // "+ IsStatus+"//"+IsPrint+"//"+IsCheck+"//"+refDocNo, Toast.LENGTH_SHORT ).show();
                spinner03.setEnabled(true);
                if(isnewDoc){
                    ArkCancelPayout();
                }else {
                    switch (IsStatus) {
                        case 0:
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("ยืนยัน");
                        builder.setMessage("ต้องการจัดเอกสารหรือไม่");
                        builder.setPositiveButton("ตกลง",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConvertPayout(DocNo,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1());
                                        payoutnotfully(DocNo);
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();*/
                            if (IsCheck == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle("ยืนยัน");
                                builder.setMessage("ต้องการให้ระบบเลือกรหัสใช้งานอัตโนมัติหรือไม่");
                                builder.setPositiveButton("ใช่",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ConvertPayout(DocNo, resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1());
//                                            savepayout_all(DocNo);
                                                textViewDocNo.setText(DocNo);
                                                spinner03.setSelection(0);
                                                payoutnotfully(DocNo);
                                                bt_switch.setChecked(true);
                                                get_RecipientCode(DocNo);
                                            }
                                        });
                                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ListPayoutDetail(DocNo);
                                        textViewDocNo.setText(DocNo);
                                        spinner03.setSelection(0);
                                        payoutnotfully(DocNo);
                                        bt_switch.setChecked(false);

                                        get_RecipientCode(DocNo);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();


//                            ListPayoutDetail(DocNo);
//                            textViewDocNo.setText(DocNo);
//                            payoutnotfully(DocNo);
//                            bt_switch.setChecked(false);
                                DocNoIsStatus = "0";
                                isnewDoc = false;
                            } else {
                                Toast.makeText(PayoutActivity.this, "เอกสารยังไม่ได้ตราจสอบแก้ไข", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case 1:

                            if (IsCheck == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle("ยืนยัน");
                                builder.setMessage("ต้องการให้ระบบเลือกรหัสใช้งานอัตโนมัติหรือไม่");
                                builder.setPositiveButton("ใช่",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                ConvertPayout(DocNo, resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1());
//                                            savepayout_all(DocNo);
                                                textViewDocNo.setText(DocNo);
                                                spinner03.setSelection(0);
                                                payoutnotfully(DocNo);
                                                bt_switch.setChecked(true);

                                                get_RecipientCode(DocNo);
                                            }
                                        });
                                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ListPayoutDetail(DocNo);
                                        textViewDocNo.setText(DocNo);
                                        spinner03.setSelection(0);
                                        payoutnotfully(DocNo);
                                        bt_switch.setChecked(false);

                                        get_RecipientCode(DocNo);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();


//                            ListPayoutDetail(DocNo);
//                            textViewDocNo.setText(DocNo);
//                            payoutnotfully(DocNo);
//                            bt_switch.setChecked(false);
                                DocNoIsStatus = "1";
                                isnewDoc = false;

                            } else {
                                Toast.makeText(PayoutActivity.this, "เอกสารยังไม่ได้ตราจสอบแก้ไข", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case 6:

                            if (IsCheck == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle("ยืนยัน");
                                builder.setMessage("ต้องการให้ระบบเลือกรหัสใช้งานอัตโนมัติหรือไม่");
                                builder.setPositiveButton("ใช่",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                ConvertPayout(DocNo, resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1());
//                                            savepayout_all(DocNo);
                                                textViewDocNo.setText(DocNo);
                                                spinner03.setSelection(0);
                                                payoutnotfully(DocNo);
                                                bt_switch.setChecked(true);
                                                get_RecipientCode(DocNo);
                                            }
                                        });
                                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ListPayoutDetail(DocNo);
                                        textViewDocNo.setText(DocNo);
                                        spinner03.setSelection(0);
                                        payoutnotfully(DocNo);
                                        bt_switch.setChecked(false);
                                        get_RecipientCode(DocNo);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

//                            ListPayoutDetail(DocNo);
//                            textViewDocNo.setText(DocNo);
//                            payoutnotfully(DocNo);
//                            bt_switch.setChecked(false);
                                DocNoIsStatus = "6";
                                isnewDoc = false;
                            } else {
                                Toast.makeText(PayoutActivity.this, "เอกสารยังไม่ได้ตราจสอบแก้ไข", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2:

                            ListPayoutDetail(DocNo);
                            textViewDocNo.setText(DocNo);
                            spinner03.setSelection(0);
                            payoutnotfully(DocNo);
                            DocNoIsStatus = "2";
                            get_RecipientCode(DocNo);
                            isnewDoc = false;
                            break;
                        case 9:

                            if (IsCheck == 1) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PayoutActivity.this);
                                builder.setCancelable(true);
                                builder.setTitle("ยืนยัน");
                                builder.setMessage("ต้องการให้ระบบเลือกรหัสใช้งานอัตโนมัติหรือไม่");
                                builder.setPositiveButton("ใช่",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                ConvertPayout(DocNo, resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1());
//                                            savepayout_all(DocNo);
                                                textViewDocNo.setText(DocNo);
                                                spinner03.setSelection(0);
                                                payoutnotfully(DocNo);
                                                bt_switch.setChecked(true);
                                                get_RecipientCode(DocNo);
                                            }
                                        });
                                builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ListPayoutDetail(DocNo);
                                        textViewDocNo.setText(DocNo);
                                        spinner03.setSelection(0);
                                        payoutnotfully(DocNo);
                                        bt_switch.setChecked(false);
                                        get_RecipientCode(DocNo);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                DocNoIsStatus = "6";
                                isnewDoc = false;
                            } else {
                                Toast.makeText(PayoutActivity.this, "เอกสารยังไม่ได้ตราจสอบแก้ไข", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 3:

                            ListPayoutDetail(DocNo);
                            textViewDocNo.setText(DocNo);
                            spinner03.setSelection(0);
                            payoutnotfully(DocNo);
                            DocNoIsStatus = "2";
                            get_RecipientCode(DocNo);
                            isnewDoc = false;
                            break;
                    }

                }
                return false;
            }
        });
    }

    public void IsCheckPayout(final String xDocNo) {
        class IsCheckPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("Insert : ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_updateischeckpayout.php",data);
                return  result;
            }
        }
        IsCheckPayout ru = new IsCheckPayout();
        ru.execute( xDocNo);
    }

    public void ListPayoutDetail(String xDocNo) {
        if(devicemode==IsL2){
            if(xDocNo==""){
                rLayout.setVisibility(View.GONE);
                lLayout.setVisibility(View.VISIBLE);
            }else{
                rLayout.setVisibility(View.VISIBLE);
                lLayout.setVisibility(View.GONE);
            }
        }
        class ListPayoutDetail extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayoutdetail.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
//                        Log.d("OOOO",c.getString("xItemName"));
                        newsData.setFields1(c.getString("xItemName"));
                        newsData.setFields2(c.getString("xExpireDate"));
                        newsData.setFields3(c.getString("xQty"));
                        newsData.setFields4(c.getString("xIsStatus"));
                        newsData.setFields5(c.getString("xID"));
                        newsData.setFields6(c.getString("xIsCheckPay"));
                        newsData.setFields7(c.getString("xpaysubID"));
                        newsData.setFields8(c.getString("xShelflife"));
                        newsData.setIs_Check(false);
                        resultspayoutdetail.add( newsData );
                    }
                    final GridView Gv = (GridView) findViewById(R.id.ListView02);
                    Gv.setAdapter(new PayoutDetailAdapter( PayoutActivity.this, resultspayoutdetail,Gv,DocNo));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(iFt.getpayoutdetail(),data);
                Log.d("doInBackground: ",result );
                return  result;
            }
        }
        ListPayoutDetail ru = new ListPayoutDetail();
        ru.execute( xDocNo );
    }

    public void Insert_PayoutDetail(final String xDocNo,final String xItemCode,String xQTY) {
        class ImportPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if( c.getString("Finish").equals("true")){
                            //ListPayoutDocument("0",xSearch);

                            payoutInSertSub( DocNo,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),eUsageCode.getText().toString().toUpperCase());
                            eUsageCode.setText("");
                            eUsageCode.requestFocus();
                            ListPayoutDetail( DocNo );
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("xUsageCode",params[1]);
                data.put("xQTY",params[2]);
                if(B_ID!=null){data.put("B_ID",B_ID);}
//                data.put("xdept",params[3]);
                Log.d("input insert : ",data+"" );
                String result = ruc.sendPostRequest(iFt.setinsertpayoutdetail(),data);
                Log.d("output result : ",result );
                return  result;
            }
        }
        ImportPayout ru = new ImportPayout();
        ru.execute( xDocNo,xItemCode,xQTY);
    }

    public void CreatePayoutDocument(String UserCode, String xdept, final String RETURN_ItemCode , final String RETURN_QTY) {
        class CreatePayoutDocument extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if (c.getString("DocNo").equals("")){
                            Toast.makeText(PayoutActivity.this,"รายการนี้ไม่ได้ผูกแผนก !!", Toast.LENGTH_SHORT).show();
                            eUsageCode.setText("");
                            eUsageCode.requestFocus();
                        }else {
                            DocNo = c.getString("DocNo");
                            textViewDocNo.setText(DocNo);
                            IsCheckPayout(DocNo);
                            Insert_PayoutDetail(DocNo,RETURN_ItemCode,RETURN_QTY);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UserCode",params[0]);
                data.put("xdept",params[1]);
                data.put("mode",WithdrawMode+"");
                data.put("itemcode",RETURN_ItemCode);
                if(B_ID!=null){
                    data.put("B_ID",B_ID);
                }
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_createpayoutdocno.php",data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return  result;
            }
        }
        CreatePayoutDocument ru = new CreatePayoutDocument();
        ru.execute( UserCode,xdept,RETURN_ItemCode,RETURN_QTY);
    }

    public void get_RecipientCode(final String xDocNo) {
        class get_RecipientCode extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    JSONObject c = setRs.getJSONObject(0);
                    String empCode = c.getString("RecipientCode");
                    Log.d("ttest",empCode.equals("null")+"");
                    if(empCode.equals("null")){
                        spinner03.setEnabled(true);
                    }else{
                        spinner03.setEnabled(true);
                        spinner03.setSelection(0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("BANK11",data+"");

                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/get_recive_userCode_PADocno.php",data);
                Log.d("BANK11","load rePA"+result);
                return  result;
            }
        }
        get_RecipientCode ru = new get_RecipientCode();
        ru.execute( xDocNo);
    }

    private String TwoZero(String data){
        String dd = data;
        if(data.length() < 2 ) dd = "0"+data;
        return dd;
    }

    @Override
    public void onBackPressed() {
        if(devicemode==IsL2){
            rLayout.setVisibility(View.GONE);
            lLayout.setVisibility(View.VISIBLE);
            textViewDocNo.setText("");
        }
    }

/*    public void checkitempayoutdetail(String usagecode){
       String uchk;
       int boo =0;
        for(int i =0; i<resultspayoutdetail.size();i++){
            String ss = resultspayoutdetail.get(i).getFields1();
            int intIndex = ss.indexOf("[");
            uchk=ss.substring(intIndex+2,ss.length()-2);
            usagecode = usagecode.toUpperCase();
            Log.d("checkitempayoutdetail:", uchk);
            Log.d("usagecode:", usagecode);
            if(usagecode.equals(uchk.toUpperCase())){
                resultspayoutdetail.get(i).setFields6("1");
                Toast.makeText(this, "พบรายการ :"+uchk, Toast.LENGTH_SHORT).show();
                final GridView Gv = (GridView) findViewById(R.id.ListView02);
                Gv.setAdapter(new PayoutDetailAdapter( PayoutActivity.this, resultspayoutdetail,Gv,DocNo));
                boo=1;=[-=----------------------------------------------------------[
                //break;
            }
        }
        if(boo==0){
            Toast.makeText(this, "ไม่พบรายการ", Toast.LENGTH_SHORT).show();
        }
        eUsageCode.setText("");
        eUsageCode.requestFocus();

    }*/

    public boolean checkSavepayout(){

        Log.d("ttest_save_doc","checkSavepayout DocNo : "+DocNo);
        int count=0;
        int countx=0;
        chk_txt_qty=resultspayoutdetail.size();
        for(int i =0; i<resultspayoutdetail.size();i++){

            if(resultspayoutdetail.get(i).getFields6().equals("1")){
                count++;
            }
        }
        chk_txt_xqty=count;
        if(count==resultspayoutdetail.size()&&resultspayoutdetail.size()>0&&resultspayout_notfully.size()==0){
            return  true;
        }else{
            return  false;
        }
    }

    public void payoutnotfully(String xDocNo) {
        class payoutnotfully extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout_notfully.clear();
                    String xqtyfinal = "0";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        // Log.d("OOOO",c.getString("xItemName"));
                        newsData.setFields1(c.getString("xDocNo"));
                        newsData.setFields2(c.getString("xQty_sum"));
                        newsData.setFields3(c.getString("xitemname"));
                        newsData.setFields4(c.getString("xItemCode"));
                        xqtyfinal=c.getString("xQty_sumfinal");
//                        Log.d("txt_notfully: ", txt_notfully.getText().toString());
                        if(Integer.parseInt(c.getString("xQty_sum"))>0){
                            resultspayout_notfully.add( newsData );
                        }

                    }
                    txt_notfully.setText(xqtyfinal);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("xDocNo: ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/get_payout_notfully.php",data);
                Log.d("result fully: ", result);
                return  result;
            }
        }
        payoutnotfully ru = new payoutnotfully();
        ru.execute( xDocNo );
    }

   /* private void GetReport(String xDocNo, String xuser,String DeptID) {
        String url = "";
        //String url2 = "";
        Log.d("GetReport: ", refDocNo);
        if(refDocNo.equals("-")){
            url = "http://poseintelligence.com/cssd_dev/report/Report_Payout_no_ss.php?DocNo="+xDocNo+"&UserID="+xuser+"&DeptID="+DeptID;
            //url2 = " http://poseintelligence.com/cssd/report/Report_Payout_Detail.php?DocNo="+xDocNo+"&UserID="+xuser;

            //url = "192.168.1.103:8181/cssd/report/Report_Payout_no_ss.php?DocNo="+xDocNo+"&UserID="+xuser+"&DeptID="+DeptID;
        }else{
            url = "http://poseintelligence.com/cssd_dev/report/Report_Payout.php?DocNo="+xDocNo+"&UserID="+xuser+"&DeptID="+DeptID;
            //url2 = " http://poseintelligence.com/cssd/report/Report_Payout_Detail.php?DocNo="+xDocNo+"&UserID="+xuser;
            //url = "192.168.1.103:8181/cssd/report/Report_Payout.php?DocNo="+xDocNo+"&UserID="+xuser+"&DeptID="+DeptID;
        }

        Log.d("GetReport: ", url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }*/

    private void GetReport(String xDocNo, String xuser,String DeptID,String mode,String SelPrint,String MaxPrint) {
        String url = "";
        Log.d("ttest_GetReport","xuser : "+xuser);
        Log.d("ttestrefDocNo",refDocNo);
        if(refDocNo.equals("-")){
            if(mode.equals("1")){
                url = getUrl.xUrl+"report/Report_Payout.php?DocNo="+xDocNo+"&UserID="+xuser+"&DeptID="+DeptID+"&SelPrint="+SelPrint+"&MaxPrint="+MaxPrint+"&WithdrawMode="+WithdrawMode;
                Log.d("BANK",xDocNo);
            }else{
                url = getUrl.xUrl+"report/Report_Payout_Detail.php?DocNo="+xDocNo+"&UserID="+xuser+"&WithdrawMode="+WithdrawMode;
                Log.d("BANK",xDocNo);
            }
        }else{
            if(mode.equals("1")){
                url = getUrl.xUrl+"report/Report_Payout.php?DocNo="+xDocNo+"&UserID="+xuser+"&DeptID="+DeptID+"&SelPrint="+SelPrint+"&MaxPrint="+MaxPrint+"&WithdrawMode="+WithdrawMode;
                Log.d("BANK",xDocNo);
            }else{
                url = getUrl.xUrl+"report/Report_Payout_Detail.php?DocNo="+xDocNo+"&UserID="+xuser+"&WithdrawMode="+WithdrawMode;
                Log.d("BANK",xDocNo);
            }
        }



        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void payoutInSertSub(String xDocNo,String xDept,final String xUsageCode) {
        class payoutInSertSub extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String boo = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        boo=c.getString("Finish");
                    }
                    if(boo.equals("true")){

                        resultspayout_notfully.clear();
                        Toast.makeText(PayoutActivity.this, "เพิ่มรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                        ListPayoutDetail(DocNo);
                        payoutnotfully(DocNo);
                    }else if(boo.equals("full")){
                        Toast.makeText(PayoutActivity.this, "รายการครบแล้ว", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(PayoutActivity.this, "เพิ่มรายการไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                        int x =resultspayoutdetail.size();
                        if(x==0){
                            isnewDoc = false;
                            textViewDocNo.setText("");
                            bt_additem.setEnabled(true);
                            bt_createdoc.setEnabled(true);
                        }
                    }
                    eUsageCode.setText("");
                    eUsageCode.requestFocus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("xDept",params[1]);
                data.put("xUsageCode",params[2]);
                if(B_ID!=null){data.put("B_ID",B_ID);}else{data.put("B_ID","1");}
                if(WithdrawMode == 1 && isnewDoc){
                    data.put("isnewBorrow","true");
                }
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_check_insert_payoutsub.php",data);
                Log.d("ttest_chk_insert: ", data+"");
                Log.d("ttest_chk_insert: ", result);
                return  result;
            }
        }
        payoutInSertSub ru = new payoutInSertSub();
        ru.execute( xDocNo,xDept,xUsageCode );
    }

    public void payoutIsCheckPay(String xDocNo,String xUsageCode) {
        class payoutIsCheckPay extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout_notfully.clear();
                    String boo = "";
                    String u_code = "";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        boo=c.getString("Finish");
                        u_code=c.getString("xUsageCode");
                    }
                    if(boo.equals("true")){
                        Toast.makeText(PayoutActivity.this, "พบ"+u_code+"ในรายการ", Toast.LENGTH_SHORT).show();
                        ListPayoutDetail(DocNo);
                        eUsageCode.setText("");
                        eUsageCode.requestFocus();
                    }else{
                        Toast.makeText(PayoutActivity.this, "ไม่พบ"+u_code+"ในรายการ", Toast.LENGTH_SHORT).show();
                        eUsageCode.setText("");
                        eUsageCode.requestFocus();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("xUsageCode",params[1]);
                Log.d("ischeckpay: ", data+"");

                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_update_ischeckpay.php",data);
                Log.d("result ischeckpay: ", result);
                return  result;
            }
        }
        payoutIsCheckPay ru = new payoutIsCheckPay();
        ru.execute( xDocNo,xUsageCode );
    }

    public String checkRowID() {
        String RowID_chk = "";

        for (int i = 0; i < resultspayoutdetail.size(); i++) {
            if (resultspayoutdetail.get(i).isIs_Check()) {
                RowID_chk=RowID_chk+resultspayoutdetail.get(i).getFields7()+",";
            }

        }
        if(RowID_chk.length()!=0){
            RowID_chk = RowID_chk.substring(0, RowID_chk.length() - 1);
        }

        return  RowID_chk;
    }
    public void payoutdeletecheckbox(String xDocNo,String RowID_chk) {
        class payoutdeletecheckbox extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout_notfully.clear();
                    String boo = "";
                    String u_code = "";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        boo=c.getString("Finish");
                    }
                    if(boo.equals("true")){
                        Toast.makeText(PayoutActivity.this, "ลบรายการที่เลือกแล้ว", Toast.LENGTH_SHORT).show();
                        ListPayoutDetail(DocNo);
                    }else{
                        Toast.makeText(PayoutActivity.this, "ไม่มีรายการที่เลือก", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("RowID_chk",params[1]);
                if(WithdrawMode==1 && isnewDoc){
                    data.put("isnewBorrow","true");
                }
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_deletecheckbox_payoutdetail.php",data);
                return  result;
            }
        }
        payoutdeletecheckbox ru = new payoutdeletecheckbox();
        ru.execute( xDocNo,RowID_chk );
    }

    public void finishpayout(String xDocNo,final String type) {
        class finishpayout extends AsyncTask<String, Void, String> {
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
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String finish = "";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        finish=c.getString("Finish");
                    }
                    if(finish.equals("true")){
                        Toast.makeText(PayoutActivity.this, "บันทึกแล้ว", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("type",type);
                Log.d("xDocNo: ", data+"");

                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_finishPayout.php",data);
                Log.d("result finish", result);
                return  result;
            }
        }
        finishpayout ru = new finishpayout();
        ru.execute( xDocNo );
    }

    public void savepayout_all(String xDocNo) {
        class savepayout_all extends AsyncTask<String, Void, String> {
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
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String finish = "";
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        finish=c.getString("Finish");
                    }
                    if(finish.equals("true")){
                        Log.d("ttest: ", "ConvertPayout");
                        ListPayoutDetail(DocNo);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("ttest: ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_payout_all.php",data);
                Log.d("ttest", result);
                return  result;
            }
        }
        savepayout_all ru = new savepayout_all();
        ru.execute( xDocNo );
    }

    public void deletepayoutdetailfromsub(String xDocNo) {
        class deletepayoutdetailfromsub extends AsyncTask<String, Void, String> {
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
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String finish = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        finish=c.getString("Finish");
                    }
                    if(finish.equals("true")){
                        Log.d("ttest: ", "ConvertPayout");
                        getQR(DocNo,"pa","");
                    }else{
                        Toast.makeText(PayoutActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_delete_payoutdetail_from_sub.php",data);
                Log.d("ttest", result);
                return  result;
            }
        }
        deletepayoutdetailfromsub ru = new deletepayoutdetailfromsub();
        ru.execute( xDocNo );
    }


    public void getQR(String DocNo,String xsel,String macno){
        Intent i = new Intent(PayoutActivity.this, CheckQR_Approve.class);
        Log.d("ttest_save_doc","getQR DocNo : "+DocNo);
        i.putExtra("DocNo", DocNo);
        i.putExtra("xSel", xsel);
        i.putExtra("MacNo", macno);
        i.putExtra("devicemode", devicemode);
        startActivityForResult(i, 1035);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null)
            return;

        try {
            //System.out.println( "resultCode = " + requestCode + "resultCode = " + resultCode) ;
            if (resultCode == 1035 ){
                String xData = data.getStringExtra("RETURN_DATA");
                String xsel= data.getStringExtra("RETURN_xsel");
                String xDocNo= data.getStringExtra("RETURN_DocNo");
                String MacNo= data.getStringExtra("RETURN_MacNo");
                if(xData.equals("true")){
                    Log.d("ttest_save_doc","1035 DocNo : "+DocNo);
                    savepayout();
                }else{
                    Toast.makeText(this, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void savepayout(){
        final String xDocNo = this.DocNo;
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(PayoutActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View convertView = (View) inflater.inflate(R.layout.list_dialog_printreport, null);
        alertDialog.setView(convertView);
        int cnt=0;
        final AlertDialog p = alertDialog.show();
        LinearLayout f = (LinearLayout) convertView.findViewById(R.id.f);
        TextView textView167 = (TextView) convertView.findViewById(R.id.textView167);
        ImageView bt_back = (ImageView) convertView.findViewById(R.id.bt_back);
        spCount = (Spinner) convertView.findViewById(R.id.spCount);

        if(devicemode==IsL2){
            final float scale = PayoutActivity.this.getResources().getDisplayMetrics().density;
            float pixels = 270 * scale + 0.5f;
            f.getLayoutParams().width = (int)(pixels);
        }

        if(checkSavepayout())
            textView167.setText("ต้องการจ่ายเอกสารหรือไม่");
        else
            textView167.setText("เอกสารยังเช็คไม่ครบต้องการจ่ายเอกสารหรือไม่");

        List<String> list = new ArrayList<String>();
        for(int i=1;i<=(PrintCnt+1);i++){
            list.add(i+"");
            cnt=i;
        }
        ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(getApplication(),
                android.R.layout.simple_dropdown_item_1line, list);
        spCount.setAdapter( SpinnerList );
        spCount.setSelection(cnt-1);
        bt_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                p.dismiss();
            }
        });
        ImageView bt_finish = (ImageView) convertView.findViewById(R.id.bt_finish);
        bt_finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(bt_switch.isChecked())
                    ImportPayout("1",xDocNo,"notype");
                else
                    ImportPayout("2",xDocNo,"notype");
                GetReport(xDocNo,userid,resultsDepartment.get( spinner01.getSelectedItemPosition() ).getFields1(),"1", spCount.getSelectedItem().toString() ,(PrintCnt+1)+"");
                textViewDocNo.setText("");
                spinner03.setSelection(0);
//                    xDataUserCode.add("");
                ListPayoutDetail("");
                payoutnotfully("");
                p.dismiss();
            }
        });
    }

    public void ListDepartment(final Spinner spn,final String date,final Activity context) {

        class ListDepartment extends AsyncTask<String, Void, String> {
            String DepSelectID="";
            int posDepSelect=0;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(!resultsDepartment.isEmpty()){
                    DepSelectID = resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1();
                    Log.d("posDepSelect","posDepSelect : "+posDepSelect);
                }
                List<String> list = new ArrayList<String>();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultsDepartment.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xName"));
                        newsData.setFields16(c.getString("DepName"));
                        resultsDepartment.add( newsData );
                        list.add(c.getString("xNameNew"));
                        if(DepSelectID.equals(c.getString("xId"))){
                            posDepSelect = i;
                        }
                    }

                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner01.setSelection(posDepSelect);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("date",date);
                String result = ruc.sendPostRequest(iFt.getdepartment(),data);
                Log.d("KFJDDJDL",data+"");
                Log.d("KFJDDJDL",result+"");
                return  result;
            }
        }
        ListDepartment ru = new ListDepartment();
        ru.execute();
    }

    private void gotoActivity(Class c){
        Intent intent = new Intent(PayoutActivity.this, c);
        intent.putExtra("userid", userid);
        intent.putExtra("user_name", user_name);
        intent.putExtra("IsAdmin", IsAdmin);
        intent.putExtra("B_ID", B_ID);
        intent.putExtra("EmpCode", EmpCode);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

/*
    public void savepayout(){
            //====================================
            if (checkSavepayout()) {
                final Dialog dialog = new Dialog(PayoutActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_check_pay);
                dialog.setCancelable(true);
                dialog.setTitle("หมายเหตุ...");
                TextView txt_qty = (TextView) dialog.findViewById(R.id.txt_qty);
                TextView txt_xqty = (TextView) dialog.findViewById(R.id.txt_xqty);
                TextView textView168 = (TextView) dialog.findViewById(R.id.textView168);
                TextView textView181 = (TextView) dialog.findViewById(R.id.textView181);
                TextView textView174 = (TextView) dialog.findViewById(R.id.textView174);
                TextView textView182 = (TextView) dialog.findViewById(R.id.textView182);
                TextView textView179 = (TextView) dialog.findViewById(R.id.textView179);

                LinearLayout LL1 = (LinearLayout) dialog.findViewById(R.id.LL1);
                LinearLayout LL2 = (LinearLayout) dialog.findViewById(R.id.LL2);

                Button bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
                txt_qty.setText(chk_txt_qty + "");
                txt_xqty.setText(chk_txt_xqty + "");

                if(!bt_switch.isChecked()) {
                    LL1.setVisibility(View.GONE);

                    textView168.setVisibility(View.GONE);
                    textView181.setVisibility(View.GONE);
                    textView174.setVisibility(View.GONE);
                    textView182.setVisibility(View.GONE);

                    txt_qty.setVisibility(View.GONE);
                    txt_xqty.setVisibility(View.GONE);

                    textView179.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView179.setText("ต้องการจ่ายเอกสารนี้ใช่หรือไม่ ?");
                }

                bt_ok.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(bt_switch.isChecked())
                            ImportPayout("1",DocNo);
                        else
                            ImportPayout("2",DocNo);

                        GetReport(DocNo, userid, resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1(), "1");
                        textViewDocNo.setText("");
                        ListPayoutDetail("");
                        payoutnotfully("");
                        dialog.dismiss();
                    }
                });

                Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            } else {
                final Dialog dialog = new Dialog(PayoutActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_check_pay);
                dialog.setCancelable(true);
                dialog.setTitle("หมายเหตุ...");
                TextView txt_qty = (TextView) dialog.findViewById(R.id.txt_qty);
                TextView txt_xqty = (TextView) dialog.findViewById(R.id.txt_xqty);
                TextView textView168 = (TextView) dialog.findViewById(R.id.textView168);
                TextView textView181 = (TextView) dialog.findViewById(R.id.textView181);
                TextView textView174 = (TextView) dialog.findViewById(R.id.textView174);
                TextView textView182 = (TextView) dialog.findViewById(R.id.textView182);
                TextView textView179 = (TextView) dialog.findViewById(R.id.textView179);

                LinearLayout LL1 = (LinearLayout) dialog.findViewById(R.id.LL1);
                LinearLayout LL2 = (LinearLayout) dialog.findViewById(R.id.LL2);

                Button bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
                txt_qty.setText(chk_txt_qty + "");
                txt_xqty.setText(chk_txt_xqty + "");
                if(!bt_switch.isChecked()) {
                    LL1.setVisibility(View.GONE);

                    textView168.setVisibility(View.GONE);
                    textView181.setVisibility(View.GONE);
                    textView174.setVisibility(View.GONE);
                    textView182.setVisibility(View.GONE);

                    txt_qty.setVisibility(View.GONE);
                    txt_xqty.setVisibility(View.GONE);

                    textView179.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView179.setText("ต้องการจ่ายเอกสารนี้ใช่หรือไม่ ?");
                }
                bt_ok.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(bt_switch.isChecked())
                            ImportPayout("1",DocNo);
                        else
                            ImportPayout("2",DocNo);

                        GetReport(DocNo, userid, resultsDepartment.get(spinner01.getSelectedItemPosition()).getFields1(), "1");
                        textViewDocNo.setText("");
                        ListPayoutDetail("");
                        payoutnotfully("");
                        dialog.dismiss();
                    }
                });

                Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
            //====================================
        }
*/
}
