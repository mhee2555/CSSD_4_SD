package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.phc.cssd.adapter.ItemDetailAdapter;
import com.phc.cssd.config.ConfigProgram;
import com.phc.cssd.data.Master;
import com.phc.cssd.master_data.MachineAndSterile;
import com.phc.cssd.master_data.MachineAndWash;
import com.phc.cssd.model.ModelDepartment;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelItems;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Item_newlayout extends AppCompatActivity {
    //------------------------------------------------
    // Background Worker Process Variable
    private String ED_Dept = "";
    String DepCheck = "1";
    private boolean Gen_ItemCode1 = ConfigProgram.Gen_ItemCode;
    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    String ItemCode;
    String ItemCode1;
    private Timer timer;
    Button save_im;
    TextView no_wash_type;
    String no_wash_num_item = "0";
    String no_wash_num_item1 = "0";
    String no_wash_num_item2 = "0";
    String no_wash_num_item3 = "0";
    HTTPConnect ruc = new HTTPConnect();
    int IsSaveDoc = 0;
    int IsNewSave = 1;
    int ItemSet = 1;
    int IsPic = 0;
    String ItemDisplay = "0";
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    private int summenuset = 0;
    private String userid;
    Bitmap bitmap1 = null;
    int width = 1000;
    int height = 1000;
    boolean pic = true;
    int statusPic = 0;
    Bitmap bitmap2 = null;
    private ImageButton tab_1;
    private ImageButton tab_2;
    private ImageButton tab_3;
    private ImageButton tab_4;
    private ImageButton tab_5;
    private Button tab_6;
    private Button tab_detail_1;
    private Button tab_detail_2;
    private Button tab_detail_3;
    private LinearLayout linear_layout_1;
    private LinearLayout linear_layout_2;
    private LinearLayout linear_layout_3;
    private LinearLayout layout_listview;
    private TextView txt_special;
    private TextView txt_supplier;
    private TextView txt_packingmat;
    private TextView txt_manufact;
    private TextView txt_washingprocess;
    private TextView txt_sterileprocess;
    private TextView txt_label;
    private TextView txt_item_type;
    private TextView txt_unit;
    private TextView txt_department;
    private TextView edt_life;
    private ListView list_set_item;
    private ImageView img_item;
    private ImageView img_item1;
    private RadioButton radio_type_one;
    private RadioButton radio_type_set;
    private RadioButton radio_type_set2;
    private TextView edt_item_code;
    private EditText edt_item_code1;
    private EditText edt_item_name;
    private EditText limit_count;
    private EditText edt_item_alternate_name;
    private EditText edt_set_count;
    private RadioButton radio_used;
    private RadioButton radio_unused;
    private RadioButton radio_reused;
    private RadioButton radio_unreused;
    private CheckBox radio_used_department;
    private CheckBox radio_used_multiple_department;
    private CheckBox chkIsSpecial;
    private CheckBox chkNoWash;
    private CheckBox chkisWashdept;
    private CheckBox paydep;
    private EditText edt_cost_price;
    private EditText edt_sale_price;
    private EditText edt_usage_price;
    private EditText edt_minimum;
    private EditText edt_maximum;
    private EditText edt_on_stock;
    private EditText edt_on_cssd;
    private TextView txt_on_department;
    private EditText txt_all_item_stock;
    private Button btn_save_item;
    private Button btn_save_item_1;
    private Button btn_checklist;
    private Button btn_checklist1;
    private Button btn_delete_item;
    private Button btn_open_item_stock;
    private Button btn_create_item_stock;
    private Button btn_open_sterile_program_item;
    private Button btn_open_wash_program_item;
    private ImageView imageBack;
    boolean EditText1,EditText2,EditText3,EditText4,EditText5,EditText6,EditText7,EditText8,EditText9,checkstep2;
    private TextView txt_summenuset;
    private ImageView bt_step;
    private ImageView bt_step1;
    private ImageView bt_step2;
    private ImageView bt_step3;
    private TextView txt_manuqty;
    private TextView txt_manuunit;
    private String stockdetail_edt_minimum ="";
    private String stockdetail_edt_maximum="";
    private String stockdetail_txt_on_department ="";
    private String stockdetail_txt_all_item_stock ="";
    private String stockdetail_edt_on_stock ="";
    private String stockdetail_edt_on_cssd ="";
    private Button bt_displaystockdetail;
    public static final int REQUEST_GALLERY = 1;
    private boolean IsNew = true;
    private String ITEM_ID = null;
    private String ITEM_CODE = null;
    private String ITEM_NAME = null;
    private String IS_SET = null;
    private String ImageName = "image.jpg";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    private HTTPConnect httpConnect = new HTTPConnect();
    private String B_ID = null;
    SearchableSpinner etxt_dept;
    ArrayAdapter<String> adapter_spinner;
    private ArrayList<String> array_deptsp = new ArrayList<String>();
    private ArrayList<String> list_sp = new ArrayList<String>();
    CheckBox denger;
    String StatusDenger = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_newlayout);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        byIntent();

        initialize();

        StratPage();

        etxt_dept.setTitle("เลือกแผนก");
        etxt_dept.setPositiveButton("");
        etxt_dept.requestFocus();

    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(ITEM_ID != null) {
            if (!edt_item_code1.getText().toString().equals("")){
                edt_item_code1.setVisibility(View.INVISIBLE);
            }else {
                Item_newlayout.BackgroundWorker1 backgroundWorker = new Item_newlayout.BackgroundWorker1(Item_newlayout.this);
                backgroundWorker.execute("display", ITEM_ID);
            }
        }
        BackgroundWorker1 backgroundWorker = new BackgroundWorker1(Item_newlayout.this);
        backgroundWorker.execute("display_item_set", ITEM_ID);
    }

    public void initialize(){
        // Main Tab
        //----------------------------------------------
        // Config Gen_ItemCode == false
        //----------------------------------------------
        if (!Gen_ItemCode1 == true) {
            denger = ( CheckBox ) findViewById(R.id.denger);
            tab_1 = ( ImageButton ) findViewById(R.id.tab_1);
            tab_2 = ( ImageButton ) findViewById(R.id.tab_2);
            tab_3 = ( ImageButton ) findViewById(R.id.tab_3);
            tab_4 = ( ImageButton ) findViewById(R.id.tab_4);
            tab_5 = ( ImageButton ) findViewById(R.id.tab_5);
            tab_6 = ( Button ) findViewById(R.id.tab_6);
            no_wash_type = (TextView) findViewById(R.id.no_wash_type);
            tab_detail_1 = ( Button ) findViewById(R.id.tab_detail_1);
            tab_detail_2 = ( Button ) findViewById(R.id.tab_detail_2);
            tab_detail_3 = ( Button ) findViewById(R.id.tab_detail_3);
            linear_layout_1 = ( LinearLayout ) findViewById(R.id.linear_layout_1);
            linear_layout_2 = ( LinearLayout ) findViewById(R.id.linear_layout_2);
            linear_layout_3 = ( LinearLayout ) findViewById(R.id.linear_layout_3);
            radio_type_one = ( RadioButton ) findViewById(R.id.radio_type_one);
            radio_type_set = ( RadioButton ) findViewById(R.id.radio_type_set);
            radio_type_set2 = ( RadioButton ) findViewById(R.id.radio_type_set2);
            radio_used = ( RadioButton ) findViewById(R.id.radio_used);
            radio_unused = ( RadioButton ) findViewById(R.id.radio_unused);
            radio_reused = ( RadioButton ) findViewById(R.id.radio_reused);
            radio_unreused = ( RadioButton ) findViewById(R.id.radio_unreused);
            radio_used_department = ( CheckBox ) findViewById(R.id.radio_used_department);
            radio_used_multiple_department = ( CheckBox ) findViewById(R.id.radio_used_multiple_department);
            radio_used_department.setChecked(true);
            radio_used_multiple_department.setChecked(true);
            getDept();
            chkIsSpecial = ( CheckBox ) findViewById(R.id.chkIsSpecial);
            chkNoWash = ( CheckBox ) findViewById(R.id.chkNoWash);
            chkisWashdept = ( CheckBox ) findViewById(R.id.chkisWashdept);
            paydep = ( CheckBox ) findViewById(R.id.paydep);
            txt_special = ( TextView ) findViewById(R.id.txt_special);
            txt_supplier = ( TextView ) findViewById(R.id.txt_supplier);
            txt_packingmat = ( TextView ) findViewById(R.id.txt_packingmat);
            txt_manufact = ( TextView ) findViewById(R.id.txt_manufact);
            txt_washingprocess = ( TextView ) findViewById(R.id.txt_washingprocess);
            txt_sterileprocess = ( TextView ) findViewById(R.id.txt_sterileprocess);
            txt_label = ( TextView ) findViewById(R.id.txt_label);
            txt_item_type = ( TextView ) findViewById(R.id.txt_item_type);
            txt_unit = ( TextView ) findViewById(R.id.txt_unit);
            edt_life = ( TextView ) findViewById(R.id.edt_life);
            txt_department = ( TextView ) findViewById(R.id.txt_department);
            etxt_dept = ( SearchableSpinner ) findViewById(R.id.etxt_dept);
            list_set_item = ( ListView ) findViewById(R.id.list_set_item);
            img_item = ( ImageView ) findViewById(R.id.img_item);
            img_item1 = ( ImageView ) findViewById(R.id.img_item1);

            edt_item_code = ( TextView ) findViewById(R.id.edt_item_code);
            edt_item_code1 = ( EditText ) findViewById(R.id.edt_item_code1);
            save_im = (Button ) findViewById(R.id.save_im);
            save_im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage();
                }
            });

            edt_item_code1.setVisibility(View.INVISIBLE);

            edt_item_name = ( EditText ) findViewById(R.id.edt_item_name);
            limit_count = (EditText) findViewById(R.id.limit_count);
            edt_item_alternate_name = ( EditText ) findViewById(R.id.edt_item_alternate_name);
            edt_set_count = ( EditText ) findViewById(R.id.edt_set_count);

            edt_cost_price = ( EditText ) findViewById(R.id.edt_cost_price);
            edt_sale_price = ( EditText ) findViewById(R.id.edt_sale_price);
            edt_usage_price = ( EditText ) findViewById(R.id.edt_usage_price);

            //Detail 3
            edt_minimum = ( EditText ) findViewById(R.id.edt_minimum);
            edt_maximum = ( EditText ) findViewById(R.id.edt_maximum);
            edt_on_stock = ( EditText ) findViewById(R.id.edt_on_stock);
            edt_on_cssd = ( EditText ) findViewById(R.id.edt_on_cssd);

            txt_on_department = ( TextView ) findViewById(R.id.txt_on_department);
            txt_all_item_stock = ( EditText ) findViewById(R.id.txt_all_item_stock);

            btn_save_item = ( Button ) findViewById(R.id.btn_save_item);
            btn_save_item_1 = ( Button ) findViewById(R.id.btn_save_item_1);

            btn_save_item_1.setVisibility(View.INVISIBLE);

            btn_checklist = ( Button ) findViewById(R.id.btn_checklist);
            btn_checklist1 = ( Button ) findViewById(R.id.btn_checklist1);

            btn_checklist.setVisibility(View.INVISIBLE);
            btn_checklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCheckList();
                }
            });
            btn_checklist1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCheckList();
                }
            });

            btn_delete_item = ( Button ) findViewById(R.id.btn_delete_item);
            btn_open_item_stock = ( Button ) findViewById(R.id.btn_open_item_stock);
            btn_create_item_stock = ( Button ) findViewById(R.id.btn_create_item_stock);
            btn_open_sterile_program_item = ( Button ) findViewById(R.id.btn_open_sterile_program_item);
            btn_open_wash_program_item = ( Button ) findViewById(R.id.btn_open_wash_program_item);

            layout_listview = ( LinearLayout ) findViewById(R.id.layout_listview);
            layout_listview.setVisibility(View.INVISIBLE);

            txt_summenuset = ( TextView ) findViewById(R.id.txt_summenuset);
            txt_manuqty = ( TextView ) findViewById(R.id.txt_manuqty);
            txt_manuunit = ( TextView ) findViewById(R.id.txt_manuunit);
            bt_step = ( ImageView ) findViewById(R.id.bt_step);
            bt_step1 = ( ImageView ) findViewById(R.id.bt_step1);
            bt_step2 = ( ImageView ) findViewById(R.id.bt_step2);
            bt_step3 = ( ImageView ) findViewById(R.id.bt_step3);

            //btn_open_sterile_program_item.setBackgroundResource(R.drawable.button);
            //btn_create_item_stock.setBackgroundResource(R.drawable.button);
            // btn_open_sterile_program_item.setTextColor(Color.BLACK);
            //btn_create_item_stock.setTextColor(Color.BLACK);


            radio_type_one.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkIsSpecial.setEnabled(radio_type_one.isChecked());
                    layout_listview.setVisibility(View.INVISIBLE);
                }
            });

            radio_type_set.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkIsSpecial.setEnabled(!radio_type_set.isChecked());
                    layout_listview.setVisibility(View.VISIBLE);
                }
            });

            radio_type_set2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkIsSpecial.setEnabled(!radio_type_set2.isChecked());
                    layout_listview.setVisibility(View.VISIBLE);
                }
            });

            // --------------------------------------- Event -------------------------------------------
            tab_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(1);

                    onClearForm();
                    tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
                    bt_step.setImageResource(R.drawable.ss_ic_step2_disable);
                    bt_step2.setImageResource(R.drawable.ss_ic_step3_disable);
                    bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
                    layout_listview.setVisibility(View.INVISIBLE);
                    edt_item_code1.setTextColor(Color.BLACK);

                }
            });

            tab_2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(2);

                    if (!edt_item_code1.getText().toString().equals("")) {
                        openActivity(ITEM_ID,edt_item_code1.getText().toString(), ITEM_NAME);
                    } else {
                        Toast.makeText(Item_newlayout.this, "ยังไม่ได้เลือกรายการ!", Toast.LENGTH_SHORT).show();
                    }

                    //openActivity(ITEM_ID);
                }
            });

            tab_3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(3);
                    openMasterActivity(Cons.ITEM_TYPE);
                }
            });

            tab_4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(4);
                    openMasterActivity(Cons.UNITS);
                }
            });

            tab_5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(5);
                    openMaster3FieldActivity(Cons.PACKING);
                }
            });

            tab_6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(6);
                    openDropDown("item");
                }
            });

            // -----------------------------------------------------------------------------------------


            txt_special.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("special");
                }
            });

            txt_supplier.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("supplier");
                }
            });

            txt_packingmat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("packingmat");
                }
            });

            txt_manufact.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("manufact");
                }
            });

            txt_washingprocess.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("washprocess");
                }
            });

            txt_sterileprocess.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("sterileprocess");
                }
            });

            txt_label.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("LabelGroup");
                }
            });

            txt_on_department.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("department");
                }
            });

            txt_item_type.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("itemtype");
                }
            });

            txt_unit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("units");
                }
            });

//            txt_department.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    openDropDown("department");
//                }
//            });

            // -----------------------------------------------------------------------------------------
            // Detail


            tab_detail_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    activeTabDetail(1);
                    showTabDetail(true, false, false);

                }
            });

            tab_detail_2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    activeTabDetail(2);
                    showTabDetail(false, true, false);
                }
            });

            tab_detail_3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    activeTabDetail(3);
                    showTabDetail(false, false, true);
                }
            });

            img_item1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, 1695);
                }
            });

            btn_save_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        if (edt_item_code1.getText().toString().length() < 6) {
                            Toast.makeText(Item_newlayout.this, "รหัสใช้งานต้องมีจำนวนอักษร 6 ตัวอักษรเท่านั้น !!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (checkAllTrue()) {
                                onSave();
                            } else {
                                Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_delete_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onDelete();
                }
            });

            btn_open_item_stock.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onOpenItemStock();
                }
            });

            btn_create_item_stock.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    displayStockdetail();
                    //onOpenItemStockDepartment();
                }
            });

            btn_open_sterile_program_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (ITEM_ID != null) {
                        Intent i = new Intent(Item_newlayout.this, MachineAndSterile.class);
                        i.putExtra("Item_Code", ITEM_ID);
                        startActivity(i);
                    } else {
                        Toast.makeText(Item_newlayout.this, "กรุณาบันทึกรายการก่อน", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_open_wash_program_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (ITEM_ID != null) {
                        Intent i = new Intent(Item_newlayout.this, MachineAndWash.class);
                        i.putExtra("Item_Code", ITEM_ID);
                        startActivity(i);
                    } else {
                        Toast.makeText(Item_newlayout.this, "กรุณาบันทึกรายการก่อน", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imageBack = ( ImageView ) findViewById(R.id.imageBack);
            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            imageBack.bringToFront();


            //btn_save_item.setVisibility(View.INVISIBLE);
            edt_item_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText1 = true;
                        if (checkAllTrue())
                            btn_save_item.setBackgroundResource(R.drawable.bt_save_newitem);
                    } else {
                        EditText1 = false;
                        btn_save_item.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txt_unit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText3 = true;
                        if (checkAllTrue())
                            btn_save_item.setBackgroundResource(R.drawable.bt_save1);
                    } else {
                        EditText3 = false;
                        btn_save_item.setBackgroundResource(R.drawable.bt_save1_grey);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txt_label.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText7 = true;
                        if (checkAllTrue())
                            btn_save_item.setBackgroundResource(R.drawable.bt_save1);
                    } else {
                        EditText7 = false;
                        btn_save_item.setBackgroundResource(R.drawable.bt_save1_grey);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            txt_packingmat.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText8 = true;
                        if (checkAllTrue())
                            btn_save_item.setBackgroundResource(R.drawable.bt_save1);
                    } else {
                        EditText8 = false;
                        btn_save_item.setBackgroundResource(R.drawable.bt_save1_grey);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

//            txt_department.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (s.toString().length() > 0) {
//                        EditText9 = true;
//                        if (checkAllTrue())
//                            btn_save_item.setBackgroundResource(R.drawable.bt_save1);
//                    } else {
//                        EditText9 = false;
//                        btn_save_item.setBackgroundResource(R.drawable.bt_save1_grey);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

            limit_count.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View v, boolean hasFocus){
                    if (hasFocus) ((EditText)v).selectAll();
                }
            });

//            limit_count.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (s.toString().length() > 0) {
//                        EditText10 = true;
//                        if (checkAllTrue())
//                            btn_save_item.setBackgroundResource(R.drawable.bt_save_newitem);
//                    } else {
//                        EditText10 = false;
//                        btn_save_item.setBackgroundResource(R.drawable.bt_save_newitem_disable);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

            edt_item_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        checkstep2 = true;
                        if (checkStep2True())
                            save_im.setBackgroundResource(R.drawable.bt_save_newitem_new);
                            btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash);
                            btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program);
                            btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_enable2);
                            bt_step.setImageResource(R.drawable.ss_ic_step2_enable);
                            bt_step2.setImageResource(R.drawable.ss_ic_step3_enable);
                            bt_step3.setImageResource(R.drawable.ss_ic_step4_enable_new);
                            btn_open_sterile_program_item.setTextColor(Color.WHITE);
                            btn_open_wash_program_item.setTextColor(Color.WHITE);
                            btn_create_item_stock.setTextColor(Color.WHITE);
                        } else {
                            checkstep2 = false;
                            save_im.setBackgroundResource(R.drawable.bt_save_newitem_disable_new);
                            btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash2);
                            btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program_disable);
                            btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_disable2);
                            bt_step.setImageResource(R.drawable.ss_ic_step2_disable);
                            bt_step2.setImageResource(R.drawable.ss_ic_step3_disable);
                            bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
                            btn_open_sterile_program_item.setTextColor(Color.BLACK);
                            btn_open_wash_program_item.setTextColor(Color.BLACK);
                            btn_create_item_stock.setTextColor(Color.BLACK);
                        }
                    }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            bt_displaystockdetail = ( Button ) findViewById(R.id.bt_displaystockdetail);
            bt_displaystockdetail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    displayStockdetail();
                }
            });


            getSupportActionBar().hide();
        }else {

            //----------------------------------------------
            // Config Gen_ItemCode == True
            //----------------------------------------------
            btn_checklist = ( Button ) findViewById(R.id.btn_checklist);
            btn_checklist1 = ( Button ) findViewById(R.id.btn_checklist1);

            btn_checklist.setVisibility(View.INVISIBLE);
            btn_checklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCheckList();
                }
            });
            btn_checklist1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCheckList();
                }
            });
            denger = ( CheckBox ) findViewById(R.id.denger);
            tab_1 = ( ImageButton ) findViewById(R.id.tab_1);
            tab_2 = ( ImageButton ) findViewById(R.id.tab_2);
            tab_3 = ( ImageButton ) findViewById(R.id.tab_3);
            tab_4 = ( ImageButton ) findViewById(R.id.tab_4);
            tab_5 = ( ImageButton ) findViewById(R.id.tab_5);
            tab_6 = ( Button ) findViewById(R.id.tab_6);
            no_wash_type = (TextView) findViewById(R.id.no_wash_type);
            tab_detail_1 = ( Button ) findViewById(R.id.tab_detail_1);
            tab_detail_2 = ( Button ) findViewById(R.id.tab_detail_2);
            tab_detail_3 = ( Button ) findViewById(R.id.tab_detail_3);
            linear_layout_1 = ( LinearLayout ) findViewById(R.id.linear_layout_1);
            linear_layout_2 = ( LinearLayout ) findViewById(R.id.linear_layout_2);
            linear_layout_3 = ( LinearLayout ) findViewById(R.id.linear_layout_3);
            radio_type_one = ( RadioButton ) findViewById(R.id.radio_type_one);
            radio_type_set = ( RadioButton ) findViewById(R.id.radio_type_set);
            radio_type_set2 = ( RadioButton ) findViewById(R.id.radio_type_set2);
            radio_used = ( RadioButton ) findViewById(R.id.radio_used);
            radio_unused = ( RadioButton ) findViewById(R.id.radio_unused);
            radio_reused = ( RadioButton ) findViewById(R.id.radio_reused);
            radio_unreused = ( RadioButton ) findViewById(R.id.radio_unreused);
            radio_used_department = ( CheckBox ) findViewById(R.id.radio_used_department);
            radio_used_multiple_department = ( CheckBox ) findViewById(R.id.radio_used_multiple_department);
            radio_used_department.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (radio_used_department.isChecked()) {
                        DepCheck = "1";
                        radio_used_department.setChecked(true);
                        radio_used_multiple_department.setChecked(false);
                    }
                }
            });
            radio_used_multiple_department.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (radio_used_multiple_department.isChecked()) {
                        DepCheck = "0";
                        radio_used_department.setChecked(false);
                        radio_used_multiple_department.setChecked(true);
                    }
                }
            });
            chkIsSpecial = ( CheckBox ) findViewById(R.id.chkIsSpecial);
            chkNoWash = ( CheckBox ) findViewById(R.id.chkNoWash);
            chkNoWash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getNoWashType();
                }
            });
            chkisWashdept = ( CheckBox ) findViewById(R.id.chkisWashdept);
            paydep = ( CheckBox ) findViewById(R.id.paydep);
            txt_special = ( TextView ) findViewById(R.id.txt_special);
            txt_supplier = ( TextView ) findViewById(R.id.txt_supplier);
            txt_packingmat = ( TextView ) findViewById(R.id.txt_packingmat);
            txt_manufact = ( TextView ) findViewById(R.id.txt_manufact);
            txt_washingprocess = ( TextView ) findViewById(R.id.txt_washingprocess);
            txt_sterileprocess = ( TextView ) findViewById(R.id.txt_sterileprocess);
            txt_label = ( TextView ) findViewById(R.id.txt_label);
            txt_item_type = ( TextView ) findViewById(R.id.txt_item_type);
            txt_unit = ( TextView ) findViewById(R.id.txt_unit);
            edt_life = ( TextView ) findViewById(R.id.edt_life);
            txt_department = ( TextView ) findViewById(R.id.txt_department);
            etxt_dept = ( SearchableSpinner ) findViewById(R.id.etxt_dept);
            list_set_item = ( ListView ) findViewById(R.id.list_set_item);
            img_item = ( ImageView ) findViewById(R.id.img_item);
            img_item1 = ( ImageView ) findViewById(R.id.img_item1);
            img_item1.setImageResource(R.drawable.ic_preview2);
            edt_item_code = ( TextView ) findViewById(R.id.edt_item_code);
            edt_item_code1 = ( EditText ) findViewById(R.id.edt_item_code1);
            if (!Gen_ItemCode1 == true) {
                edt_item_code1.setVisibility(View.VISIBLE);
                edt_item_code1.setVisibility(View.INVISIBLE);
            } else {
                edt_item_code1.setVisibility(View.INVISIBLE);
                edt_item_code1.setVisibility(View.VISIBLE);
            }
            edt_item_name = ( EditText ) findViewById(R.id.edt_item_name);
            limit_count = ( EditText ) findViewById(R.id.limit_count);
            edt_item_alternate_name = ( EditText ) findViewById(R.id.edt_item_alternate_name);
            edt_set_count = ( EditText ) findViewById(R.id.edt_set_count);
            edt_cost_price = ( EditText ) findViewById(R.id.edt_cost_price);
            edt_sale_price = ( EditText ) findViewById(R.id.edt_sale_price);
            edt_usage_price = ( EditText ) findViewById(R.id.edt_usage_price);
            edt_minimum = ( EditText ) findViewById(R.id.edt_minimum);
            edt_maximum = ( EditText ) findViewById(R.id.edt_maximum);
            edt_on_stock = ( EditText ) findViewById(R.id.edt_on_stock);
            edt_on_cssd = ( EditText ) findViewById(R.id.edt_on_cssd);
            txt_on_department = ( TextView ) findViewById(R.id.txt_on_department);
            txt_all_item_stock = ( EditText ) findViewById(R.id.txt_all_item_stock);
            btn_save_item = ( Button ) findViewById(R.id.btn_save_item);
            btn_save_item_1 = ( Button ) findViewById(R.id.btn_save_item_1);
            if (Gen_ItemCode1 = true) {
                btn_save_item.setVisibility(View.INVISIBLE);
                btn_save_item_1.setVisibility(View.VISIBLE);
            } else {
                btn_save_item.setVisibility(View.VISIBLE);
                btn_save_item_1.setVisibility(View.INVISIBLE);
            }
            save_im = (Button ) findViewById(R.id.save_im);
            save_im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        uploadImage();
                        Toast.makeText(Item_newlayout.this, "บันทึกรูปภาพสำเร็จ", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btn_save_item_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        if (edt_item_code1.getText().toString().length() > 6) {
                            Toast.makeText(Item_newlayout.this, "รหัสใช้งานต้องมีจำนวนอักษร 6 ตัวอักษรเท่านั้น !!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (checkAllTrue()) {
                                onSave();
                            } else {
                                Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btn_delete_item = ( Button ) findViewById(R.id.btn_delete_item);
            btn_open_item_stock = ( Button ) findViewById(R.id.btn_open_item_stock);
            btn_create_item_stock = ( Button ) findViewById(R.id.btn_create_item_stock);
            btn_open_sterile_program_item = ( Button ) findViewById(R.id.btn_open_sterile_program_item);
            btn_open_wash_program_item = ( Button ) findViewById(R.id.btn_open_wash_program_item);

            layout_listview = ( LinearLayout ) findViewById(R.id.layout_listview);
            layout_listview.setVisibility(View.INVISIBLE);

            txt_summenuset = ( TextView ) findViewById(R.id.txt_summenuset);
            txt_manuqty = ( TextView ) findViewById(R.id.txt_manuqty);
            txt_manuunit = ( TextView ) findViewById(R.id.txt_manuunit);
            bt_step = ( ImageView ) findViewById(R.id.bt_step);
            bt_step1 = ( ImageView ) findViewById(R.id.bt_step1);
            bt_step2 = ( ImageView ) findViewById(R.id.bt_step2);
            bt_step3 = ( ImageView ) findViewById(R.id.bt_step3);

            //btn_open_sterile_program_item.setBackgroundResource(R.drawable.button);
            //btn_create_item_stock.setBackgroundResource(R.drawable.button);
            //btn_open_sterile_program_item.setTextColor(Color.BLACK);
            //btn_create_item_stock.setTextColor(Color.BLACK);


            radio_type_one.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkIsSpecial.setEnabled(radio_type_one.isChecked());
                    layout_listview.setVisibility(View.INVISIBLE);
                    IsSaveDoc = 0;
                }
            });

            radio_type_set.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkIsSpecial.setEnabled(!radio_type_set.isChecked());
                    layout_listview.setVisibility(View.VISIBLE);
                    IsSaveDoc = 0;
                }
            });

            radio_type_set2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    chkIsSpecial.setEnabled(!radio_type_set2.isChecked());
                    layout_listview.setVisibility(View.VISIBLE);
                    IsSaveDoc = 0;
                }
            });

            // --------------------------------------- Event -------------------------------------------
            tab_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(1);
                    ItemCode = "";
                    ItemCode1 = "";
                    ItemDisplay = "0";
                    edt_item_code1.setVisibility(View.VISIBLE);
                    edt_item_code1.setText("");
                    edt_item_code1.requestFocus();
                    edt_item_code1.setTextColor(Color.BLACK);
                    onClearForm();
                    tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
                    bt_step.setImageResource(R.drawable.ss_ic_step2_disable);
                    btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program_disable);
                    btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash2);
                    btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_disable2);
                    save_im.setBackgroundResource(R.drawable.bt_save_newitem_disable_new);
                    bt_step2.setImageResource(R.drawable.ss_ic_step3_disable);
                    bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
                    layout_listview.setVisibility(View.INVISIBLE);
                    IsSaveDoc = 0;
                    IsNewSave = 1;
                    btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    bt_displaystockdetail.setBackgroundResource(R.drawable.bt_stock_disable_new);
                }
            });

            tab_2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (!edt_item_code1.getText().toString().equals("")) {
                        openActivity(ITEM_ID,edt_item_code1.getText().toString(), ITEM_NAME);
                    } else {
                        Toast.makeText(Item_newlayout.this, "ยังไม่ได้เลือกรายการ!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            tab_3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(3);
                    openMasterActivity(Cons.ITEM_TYPE);
                }
            });

            tab_4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(4);
                    openMasterActivity(Cons.UNITS);
                }
            });

            tab_5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(5);
                    openMaster3FieldActivity(Cons.PACKING);
                }
            });

            tab_6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //activeTab(6);
                    onClearForm();
                    openDropDown("item");
                    IsNewSave = 0;
                    ItemCode = "";
                    ItemCode1 = "";
                    edt_item_code.setVisibility(View.VISIBLE);
                    edt_item_code.setText("");
                    edt_item_code.requestFocus();
                    edt_item_code.setEnabled(true);
                    edt_item_code.setTextColor(Color.BLACK);
                    tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
                    bt_step.setImageResource(R.drawable.ss_ic_step2_disable);
                    btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program_disable);
                    btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash2);
                    btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_disable2);
                    save_im.setBackgroundResource(R.drawable.bt_save_newitem_disable_new);
                    bt_step2.setImageResource(R.drawable.ss_ic_step3_disable);
                    bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
                    layout_listview.setVisibility(View.INVISIBLE);
                    IsSaveDoc = 0;
                    btn_save_item.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    bt_displaystockdetail.setBackgroundResource(R.drawable.bt_stock_disable_new);
                }
            });

            // -----------------------------------------------------------------------------------------


            txt_special.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("special");
                }
            });

            txt_supplier.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("supplier");
                }
            });

            txt_packingmat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("packingmat");
                }
            });

            txt_manufact.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("manufact");
                }
            });

            txt_washingprocess.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("washprocess");
                }
            });

            txt_sterileprocess.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("sterileprocess");
                }
            });

            txt_label.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("LabelGroup");
                }
            });

            txt_on_department.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("department");
                }
            });

            txt_item_type.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("itemtype");
                }
            });

            txt_unit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openDropDown("units");
                }
            });

//            txt_department.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    openDropDown("department");
//                }
//            });

            // -----------------------------------------------------------------------------------------
            // Detail


            tab_detail_1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    activeTabDetail(1);
                    showTabDetail(true, false, false);

                }
            });

            tab_detail_2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    activeTabDetail(2);
                    showTabDetail(false, true, false);
                }
            });

            tab_detail_3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    activeTabDetail(3);
                    showTabDetail(false, false, true);
                }
            });

            img_item1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, 1695);
                }
            });

            btn_save_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (checkAllTrue()) {
                        onSave();
                        //uploadImage();
                    } else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btn_delete_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onDelete();
                }
            });

            btn_open_item_stock.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onOpenItemStock();
                }
            });

            btn_create_item_stock.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        displayStockdetail();
                    }else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_open_sterile_program_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        Intent i = new Intent(Item_newlayout.this, MachineAndSterile.class);
                        i.putExtra("Item_Code", getF_edt_item_code1()+"");
                        startActivity(i);
                    } else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_open_wash_program_item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        Intent i = new Intent(Item_newlayout.this, MachineAndWash.class);
                        i.putExtra("Item_Code", getF_edt_item_code1()+"");
                        startActivity(i);
                    } else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imageBack = ( ImageView ) findViewById(R.id.imageBack);
            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            imageBack.bringToFront();


            //btn_save_item.setVisibility(View.INVISIBLE);
            edt_item_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText1 = true;
                        if (checkAllTrue())
                            btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem);
                    } else {
                        EditText1 = false;
                        btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txt_unit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText3 = true;
                        if (checkAllTrue())
                            btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem);
                    } else {
                        EditText3 = false;
                        btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txt_label.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText7 = true;
                        if (checkAllTrue())
                            btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem);
                    } else {
                        EditText7 = false;
                        btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            txt_packingmat.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        EditText8 = true;
                        if (checkAllTrue())
                            btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem);
                    } else {
                        EditText8 = false;
                        btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

//            txt_department.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (s.toString().length() > 0) {
//                        EditText9 = true;
//                        if (checkAllTrue())
//                            btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem);
//                    } else {
//                        EditText9 = false;
//                        btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

//            limit_count.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    if (s.toString().length() > 0) {
//                        EditText10 = true;
//                        if (checkAllTrue())
//                            btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem);
//                    } else {
//                        EditText10 = false;
//                        btn_save_item_1.setBackgroundResource(R.drawable.bt_save_newitem_disable);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

            limit_count.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View v, boolean hasFocus){
                    if (hasFocus) ((EditText)v).selectAll();
                }
            });

            edt_item_code1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //edt_item_code1.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
                    if (s.toString().length() > 0) {
                        checkstep2 = true;
                    } else {
                        checkstep2 = false;
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            bt_displaystockdetail = ( Button ) findViewById(R.id.bt_displaystockdetail);
            bt_displaystockdetail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                        displayStockdetail();
                    } else {
                        Toast.makeText(Item_newlayout.this, "กรุณากรอกข้อมูลที่สำคัญให้ครบ!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            getSupportActionBar().hide();
        }
        getdept_spinner();
    }

    public void getdept_spinner() {
        getdeptsp("x");
        etxt_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (etxt_dept.toString().length() > 0) {
                    EditText9 = true;
                    if (checkAllTrue())
                        btn_save_item.setBackgroundResource(R.drawable.bt_save1);
                } else {
                    EditText9 = false;
                    btn_save_item.setBackgroundResource(R.drawable.bt_save1_grey);
                }
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
                    adapter_spinner = new ArrayAdapter<String>(Item_newlayout.this, android.R.layout.simple_spinner_dropdown_item, list_sp);
                    adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    etxt_dept.setAdapter(adapter_spinner);
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

    public void startUserSession() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 1000);
        ItemCode = "";
    }

    public void CheckItemCode() {
        class CheckItemCode extends AsyncTask<String, Void, String> {
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
                        ItemCode = c.getString("itemcode");
                        ItemCode1 = edt_item_code1.getText().toString();

                        Log.d("BANKTY",IsNewSave+"");

                        if(checkEnterForm()) {
                            if (!c.getString("itemcode").equals("0")){
                                if (c.getString("Item").equals("null")){
                                    IsNewSave = 1;
                                    if (IsNewSave == 0) {
                                        CreateItem();
                                    }else {
                                        IsNewSave = 0;
                                        CreateItem();
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(Item_newlayout.this);
//                                        builder.setCancelable(true);
//                                        builder.setTitle("แจ้งเตือน !!");
//                                        builder.setMessage("รหัสใช้งานซ้ำ !!");
//                                        AlertDialog dialog = builder.create();
//                                        dialog.show();
//                                        startUserSession();
//                                        edt_item_code1.requestFocus();
//                                        edt_item_code1.setTextColor(Color.RED);
//                                        ItemCode = "";
                                    }
                                }else {
                                    IsNewSave = 1;
                                    if (IsNewSave == 0) {
                                        CreateItem();
                                    }else {
                                        if (ItemDisplay.equals("1")){
                                            IsNewSave = 0;
                                            CreateItem();
                                        }else {
                                            IsNewSave = 0;
                                            CreateItem();
//                                            AlertDialog.Builder builder = new AlertDialog.Builder(Item_newlayout.this);
//                                            builder.setCancelable(true);
//                                            builder.setTitle("แจ้งเตือน !!");
//                                            builder.setMessage("รหัสใช้งานซ้ำ !!");
//                                            AlertDialog dialog = builder.create();
//                                            dialog.show();
//                                            startUserSession();
//                                            edt_item_code1.requestFocus();
//                                            edt_item_code1.setTextColor(Color.RED);
//                                            ItemCode = "";
                                        }
                                    }
                                }
                            }else {
                                if (c.getString("Item").equals("null")) {
                                    IsNewSave = 1;
                                    CreateItem();
                                }else {
                                    IsNewSave = 0;
                                    CreateItem();
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(Item_newlayout.this);
//                                    builder.setCancelable(true);
//                                    builder.setTitle("แจ้งเตือน !!");
//                                    builder.setMessage("รหัสใช้งานซ้ำ !!");
//                                    AlertDialog dialog = builder.create();
//                                    dialog.show();
//                                    startUserSession();
//                                    edt_item_code1.requestFocus();
//                                    edt_item_code1.setTextColor(Color.RED);
//                                    ItemCode = "";
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ItemCode",getF_edt_item_code1());
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_itemstock.php", data);
                    Log.d("BANKTY",data+"");
                    Log.d("BANKTY",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            // =========================================================================================
        }

        CheckItemCode obj = new CheckItemCode();
        obj.execute();
    }

    public boolean checkAllTrue() {
        if (EditText1 && EditText3 && EditText7 && EditText8 && EditText9)
            //if (EditText1 && EditText2 && EditText3 && EditText4 &&EditText5 && EditText6 && EditText7 && EditText8 && EditText9)
            return true;
        return false;
    }

    public boolean checkStep2True() {
        if (checkstep2)
            //if (EditText1 && EditText2 && EditText3 && EditText4 &&EditText5 && EditText6 && EditText7 && EditText8 && EditText9)
            return true;
        return false;
    }

    private void activeTabDetail(int tab_index){
        tab_detail_1.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
        tab_detail_2.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);
        tab_detail_3.setBackgroundResource(R.drawable.tab_master_cyanblue_noselect);

        tab_detail_1.setTextColor(Color.parseColor("#AAAAAA"));
        tab_detail_2.setTextColor(Color.parseColor("#AAAAAA"));
        tab_detail_3.setTextColor(Color.parseColor("#AAAAAA"));

        switch (tab_index){
            case 1: tab_detail_1.setBackgroundResource(R.drawable.tab_master_cyanblue);
                tab_detail_1.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 2: tab_detail_2.setBackgroundResource(R.drawable.tab_master_cyanblue);
                tab_detail_2.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case 3: tab_detail_3.setBackgroundResource(R.drawable.tab_master_cyanblue);
                tab_detail_3.setTextColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }
    private void setsumzero(){
        summenuset=0;
        txt_summenuset.setText("รายการ [ "+summenuset+" ] ชิ้น");
        txt_summenuset.setTextColor(Color.WHITE);
    }

    private void onClearForm(){
        no_wash_type.setText("");
        chkNoWash.setChecked(false);
        IsNew = true;
        ITEM_ID = null;
        ITEM_CODE = null;
        ITEM_NAME = null;
        IS_SET = null;
        list_set_item.setAdapter(null);
        img_item1.setImageResource(R.drawable.ic_preview2);
        chkNoWash.setChecked(false);
        chkisWashdept.setChecked(false);
        paydep.setChecked(false);
        radio_type_one.setChecked(true);
        radio_type_set.setChecked(false);
        radio_type_set2.setChecked(false);
        chkIsSpecial.setEnabled(true);
        edt_item_code.setText("");
        edt_item_name.setText("");
        limit_count.setText("0");
        edt_item_alternate_name.setText("");
        edt_set_count.setText("");
        radio_used.setChecked(true);
        radio_unused.setChecked(false);
        radio_reused.setChecked(true);
        radio_unreused.setChecked(false);
        radio_used_department.setChecked(true);
        radio_used_multiple_department.setChecked(false);
        txt_department = (TextView)findViewById(R.id.txt_department);
        etxt_dept = (SearchableSpinner) findViewById(R.id.etxt_dept);
        txt_item_type.setText("");
        txt_special.setText("");
        txt_supplier.setText("");
        txt_packingmat.setText("");
        txt_manufact.setText("");
        txt_washingprocess.setText("");
        txt_label.setText("");
        txt_sterileprocess.setText("");
        txt_on_department.setText("");
        txt_unit.setText("");
        txt_department.setText("");
        etxt_dept.setSelection(0);
        txt_item_type.setContentDescription("");
        txt_special.setContentDescription("");
        txt_supplier.setContentDescription("");
        txt_packingmat.setContentDescription("");
        txt_manufact.setContentDescription("");
        txt_washingprocess.setContentDescription("");
        txt_label.setContentDescription("");
        txt_sterileprocess.setContentDescription("");
        txt_on_department.setContentDescription("");
        txt_unit.setContentDescription("");
        txt_department.setContentDescription("");
        etxt_dept.setContentDescription("");
        edt_life.setText("");
        edt_cost_price.setText("");
        edt_sale_price.setText("");
        edt_usage_price.setText("");
        edt_minimum.setText("");
        edt_maximum.setText("");
        txt_all_item_stock.setText("");
        denger.setChecked(false);
        setsumzero();
        txt_summenuset.setBackgroundResource(R.drawable.text_view);
        txt_manuqty.setBackgroundResource(R.drawable.text_view);
        txt_manuunit.setBackgroundResource(R.drawable.text_view);
    }

    private void showTabDetail(boolean t1, boolean t2, boolean t3){
        linear_layout_1.setVisibility(t1 ? View.VISIBLE : View.GONE);
        linear_layout_2.setVisibility(t2 ? View.VISIBLE : View.GONE);
        linear_layout_3.setVisibility(t3 ? View.VISIBLE : View.GONE);
    }

    private void openActivity(String ITEM_ID, String ITEM_CODE, String ITEM_NAME){
        if (ItemSet == 0) {
            Intent i = new Intent(Item_newlayout.this, ItemSet.class);
            i.putExtra("ITEM_ID", ITEM_ID);
            i.putExtra("ITEM_CODE", ITEM_CODE);
            i.putExtra("ITEM_NAME", ITEM_NAME);
            this.startActivity(i);
        }else {
            Intent i = new Intent(Item_newlayout.this, ItemSet.class);
            i.putExtra("ITEM_ID", getF_edt_item_code1());
            i.putExtra("ITEM_CODE", getF_edt_item_code1());
            i.putExtra("ITEM_NAME", edt_item_name.getText().toString());
            this.startActivity(i);
        }
    }

    private void openMasterActivity(String data){
        Intent i = new Intent(Item_newlayout.this, MasterData_2Field.class);
        i.putExtra("data", data);
        this.startActivity(i);
    }

    private void openMaster3FieldActivity(String data){
        Intent i = new Intent(Item_newlayout.this, MasterData_3Field.class);
        i.putExtra("data", data);
        this.startActivity(i);
    }

    private void openDropDown(String data){
        Intent i = new Intent(Item_newlayout.this, MasterDropdown.class);
        i.putExtra("data", data);
        startActivityForResult(i, Master.getResult(data));
    }

    private void onOpenItemStock(){
        if (Gen_ItemCode1 == true) {
            Intent i = new Intent(Item_newlayout.this, dialog_itemstock.class);
            i.putExtra("Item_Code", getF_edt_item_code1());
            i.putExtra("Item_Name", edt_item_name.getText().toString());
            i.putExtra("Item_Stock", txt_all_item_stock.getText().toString());
            i.putExtra("userid", userid);
            i.putExtra("B_ID", B_ID);
            this.startActivity(i);
        }else {
            Intent i = new Intent(Item_newlayout.this, dialog_itemstock.class);
            i.putExtra("Item_Code", getF_edt_item_code1());
            i.putExtra("Item_Name", edt_item_name.getText().toString());
            i.putExtra("Item_Stock", txt_all_item_stock.getText().toString());
            i.putExtra("userid", userid);
            i.putExtra("B_ID", B_ID);
            this.startActivity(i);
        }
    }

    private void onOpenItemStockDepartment(){
        if(ITEM_ID != null) {
            Intent i = new Intent(Item_newlayout.this, CssdCreateItemStockDepartment.class);
            i.putExtra("Item_Code", ITEM_ID);
            i.putExtra("Item_Name", edt_item_name.getText().toString());
            i.putExtra("Item_Stock", txt_all_item_stock.getText().toString());
            i.putExtra("userid", userid);
            this.startActivity(i);
        }else{
            Toast.makeText(Item_newlayout.this, "ยังไม่ได้เลือกรายการ !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Item_newlayout.this, "Uploading Image", "Please wait...",true,true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                String result = null;
                try {
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    bitmap1 = (( BitmapDrawable ) img_item1.getDrawable()).getBitmap();
                    bitmap1 = Bitmap.createScaledBitmap(bitmap1,width,height, true);
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,50, stream1); //compress to which format you want.
                    byte[] byte_arr1 = stream1.toByteArray();
                    String image_str1 = Base64.encodeToString(byte_arr1, Base64.DEFAULT);

                    String image_str2 = "null";

                    if (statusPic > 1) {
                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        bitmap2 = (( BitmapDrawable ) img_item1.getDrawable()).getBitmap();
                        bitmap2 = Bitmap.createScaledBitmap(bitmap2, width, height, true);
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 50, stream2); //compress to which format you want.
                        byte[] byte_arr2 = stream2.toByteArray();
                        image_str2 = Base64.encodeToString(byte_arr2, Base64.DEFAULT);
                    }

                    HashMap<String,String> data = new HashMap<>();
                    data.put("image1", image_str1);
                    data.put("name1",edt_item_code1.getText().toString()+"_pic1");
                    data.put("image2", image_str2);
                    data.put("name2",edt_item_code1.getText().toString()+"_pic2");
                    data.put("DocNo",edt_item_code1.getText().toString());
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_image/UploadImage.php",data);
                    Log.d("KLFHD",data+"");
                    Log.d("KLFHD",result);
                    loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loading.dismiss();
                return  result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if(data == null)
                return;

            //System.out.println( "resultCode = " + requestCode + "resultCode = " + resultCode) ;

            String RETURN_DATA = data.getStringExtra("RETURN_DATA");
            String RETURN_VALUE = data.getStringExtra("RETURN_VALUE");

            //System.out.println( "RETURN_DATA = " + RETURN_DATA + ", RETURN_VALUE = " + RETURN_VALUE) ;

            if (requestCode == 0) {
                onDisplay(RETURN_VALUE);
            }else if (resultCode == Master.special) {
                txt_special.setText(RETURN_DATA);
                txt_special.setContentDescription(RETURN_VALUE);
            } else if (resultCode == Master.supplier) {
                txt_supplier.setText(RETURN_DATA);
                txt_supplier.setContentDescription(RETURN_VALUE);
            } else if (resultCode == Master.packingmat) {
                txt_packingmat.setText(RETURN_DATA);
                txt_packingmat.setContentDescription(RETURN_VALUE);
                edt_life.setText(RETURN_DATA.substring(RETURN_DATA.lastIndexOf("[")+1,RETURN_DATA.length()-1));
            } else if (resultCode == Master.manufact) {
                txt_manufact.setText(RETURN_DATA);
                txt_manufact.setContentDescription(RETURN_VALUE);
            } else if (resultCode == Master.washprocess) {
                txt_washingprocess.setText(RETURN_DATA);
                txt_washingprocess.setContentDescription(RETURN_VALUE);
            } else if (resultCode == Master.sterileprocess) {
                txt_sterileprocess.setText(RETURN_DATA);
                txt_sterileprocess.setContentDescription(RETURN_VALUE);
            }else if (resultCode == Master.label_group) {
                Log.d("label_group: ",RETURN_DATA+"//"+RETURN_VALUE);
                txt_label.setText(RETURN_DATA);
                txt_label.setContentDescription(RETURN_VALUE);
            }else if (resultCode == Master.department) {
                txt_department.setText(RETURN_DATA);
                txt_department.setContentDescription(RETURN_VALUE);
            }else if (resultCode == Master.itemtype) {
                txt_item_type.setText(RETURN_DATA);
                txt_item_type.setContentDescription(RETURN_VALUE);
            }else if(resultCode == Master.units){
                txt_unit.setText(RETURN_DATA);
                txt_unit.setContentDescription(RETURN_VALUE);
            }

            if (requestCode == 1695 && null != data) {
                Uri selectedImage = data.getData();

                try {
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    img_item1.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == Master.no_wash_type) {
                no_wash_type.setText(RETURN_DATA);

                if (no_wash_type.getText().toString().equals("(เครื่องมือ)")){
                    chkNoWash.setChecked(true);
                    no_wash_num_item = "1";
                }else if (no_wash_type.getText().toString().equals("(ผ้า)")){
                    chkNoWash.setChecked(true);
                    no_wash_num_item = "2";
                }else if (no_wash_type.getText().toString().equals("")) {
                    chkNoWash.setChecked(false);
                    no_wash_num_item = "0";
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void onDisplay(String ID){
        // Display
        if (!Gen_ItemCode1 == true) {
            Item_newlayout.BackgroundWorker backgroundWorker = new Item_newlayout.BackgroundWorker(Item_newlayout.this);
            backgroundWorker.execute("display", ID);
        }else {
            Item_newlayout.BackgroundWorker1 backgroundWorker = new Item_newlayout.BackgroundWorker1(Item_newlayout.this);
            backgroundWorker.execute("display", ID);
        }
    }

    private void onSave(){
        CheckItemCode();
    }

    private void onDelete(){

        if(ITEM_ID != null) {
            AlertDialog.Builder quitDialog = new AlertDialog.Builder(Item_newlayout.this);
            quitDialog.setTitle(Cons.TITLE);
            quitDialog.setMessage(Cons.CONFIRM_DELETE);

            quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Display
                    if (!Gen_ItemCode1 == true) {
                        Item_newlayout.BackgroundWorker backgroundWorker = new Item_newlayout.BackgroundWorker(Item_newlayout.this);
                        backgroundWorker.execute("delete", ITEM_ID);
                    }else {
                        Item_newlayout.BackgroundWorker1 backgroundWorker = new Item_newlayout.BackgroundWorker1(Item_newlayout.this);
                        backgroundWorker.execute("delete", ITEM_ID);
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
            Toast.makeText(Item_newlayout.this, "ยังไม่ได้เลือกรายการที่จะลบ", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDept(){
        // Display
        if (!Gen_ItemCode1 == true) {
            Item_newlayout.BackgroundWorker backgroundWorker = new Item_newlayout.BackgroundWorker(Item_newlayout.this);
            backgroundWorker.execute("get_ModelDepartment", "");
        }else {
            Item_newlayout.BackgroundWorker1 backgroundWorker = new Item_newlayout.BackgroundWorker1(Item_newlayout.this);
            backgroundWorker.execute("get_ModelDepartment", "");
        }
    }



    private boolean checkEnterForm(){
        String Massage = "";


        if(edt_item_name.getText().toString().equals("")){
            Massage += "ชื่อเครื่องมือ , \n";
        }


        if(txt_unit.getContentDescription() == null || txt_unit.getContentDescription().toString().equals("")){
            Massage += "หน่วยนับ , \n";
        }


        if(txt_packingmat.getContentDescription() == null || txt_packingmat.getContentDescription().toString().equals("")){
            Massage += "รูปแบบการห่อ , \n";
        }



        if(txt_label.getContentDescription() == null || txt_label.getContentDescription().toString().equals("")){
            Massage += "สติ๊กเกอร์ , \n";
        }

        if(!Massage.equals("")){
            Toast.makeText(Item_newlayout.this, "ไม่สามารถบันทึกได้ เนื่องจากท่านยังไม่ได้ป้อน หรือ เลือก ข้อมูล \n" + Massage, Toast.LENGTH_SHORT).show();
        }


        return Massage.equals("");
    }

    private String getIsNew(){
        return IsNew ? "1" : "0";
    }

    private String getITEM_ID() {
        return ITEM_ID;
    }

    private String getF_edt_item_code() {
        return edt_item_code.getText().toString();
    }

    private String getF_edt_item_code1() {
        return edt_item_code1.getText().toString();
    }

    private String getF_edt_item_name() {
        return edt_item_name.getText().toString();}

    private String getF_edt_limit_count() {
        return limit_count.getText().toString();}

    private String getF_edt_item_alternate_name() {
        return edt_item_alternate_name.getText().toString();
    }

    private String getF_radio_type_set() {
        return (radio_type_set.isChecked() ? "1" : "2");
    }

    private String getF_radio_type_set2() {
        return (radio_type_set2.isChecked() ? "1" : "0");
    }

    private String getF_radio_reused() {
        return (radio_reused.isChecked() ? "1" : "0");
    }

    private String getF_radio_used() {
        return (radio_used.isChecked() ? "1" : "0");
    }

    private String getF_txt_item_type() {
        return isNull(txt_item_type.getContentDescription());
    }

    private String getF_txt_unit() {
        return isNull(txt_unit.getContentDescription());
    }

    private String getF_txt_special() {
        return DepCheck;
    }

    private String getF_txt_on_department() {
        return String.valueOf(etxt_dept.getSelectedItemPosition());
    }


    private String getF_ImageName() {
        return ImageName;
    }


    private String getF_edt_set_count() {
        return edt_set_count.getText().toString();
    }


    private String getF_edt_cost_price() {
        return edt_cost_price.getText().toString().equals("") ? "0" : edt_cost_price.getText().toString();
    }


    private String getF_edt_sale_price() {
        return edt_sale_price.getText().toString().equals("") ? "0" : edt_sale_price.getText().toString();
    }


    private String getF_edt_usage_price() {
        return edt_usage_price.getText().toString().equals("") ? "0" : edt_usage_price.getText().toString();
    }


    private String getF_txt_packingmat() {
        return isNull(txt_packingmat.getContentDescription());
    }


    private String getF_txt_sterileprocess() {
        return isNull(txt_sterileprocess.getContentDescription());
    }


    private String getF_txt_washingprocess() {
        return isNull(txt_washingprocess.getContentDescription());
    }


    private String getF_txt_supplier() {
        return isNull(txt_supplier.getContentDescription());
    }


    private String getF_txt_manufact() {
        return isNull(txt_manufact.getContentDescription());
    }


    private String getF_txt_label() {
        return isNull(txt_label.getContentDescription());
    }


    private String getF_edt_minimum() {
        return edt_minimum.getText().toString().equals("") ? "0" : edt_minimum.getText().toString();
    }

    private String getF_edt_maximum() {
        return edt_maximum.getText().toString().equals("") ? "0" : edt_maximum.getText().toString();
    }

    private String isNull(CharSequence str){
        return str == null ? null : str.toString();
    }

    public class BackgroundWorker1 extends AsyncTask<String,Void,String> {
        Context context;
        BackgroundWorker1 (Context ctx) {
            context = ctx;
        }
        String TYPE;

        //private ProgressDialog dialog = new ProgressDialog(Item.this);

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            HashMap<String, String> data = new HashMap<String,String>();
            String type = params[0];

            String post_data = "";
            String url_ = null;

            TYPE = type;

            //System.out.println("TYPE = " + TYPE);

            if(type.equals("delete")) {

                url_ = Url.URL + "cssd_delete_item.php";

                String id = params[1];
                post_data += "&p_id=" + id;

            }else if(type.equals("display")) {

                ItemDisplay = "1";
                url_ = Url.URL + "cssd_display_item.php";
                String ITEM_ID = params[1];
                post_data += "&p_filter_id=" + ITEM_ID;

            }else if(type.equals("display_item_set")) {

                url_ = Url.URL + "cssd_display_item_set.php";
                String ITEM_ID = params[1];
                post_data += "&p_itemcode=" + ITEM_ID;

                Log.d("BANK",post_data += "&p_itemcode=" + ITEM_ID);

            }else if(type.equals("get_ModelDepartment")) {
                url_ = Url.URL + "get_ModelDepartment.php";
                post_data ="";
            }else{
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

                System.out.println("URL = " + post_data);

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
            Log.d("onPostExecute: ", result);

            Success = ason.isSuccess();
            size = ason.getSize();
            data = ason.getASONData();

            if(Success && data != null) {
                // if (dialog.isShowing()) {
                //     dialog.dismiss();
                // }

                if(TYPE.equals("display")){

                    int ix = 0;
                    IsNew = false;

                    try {
                        List<ModelItems> list = getItemModel();

                        ITEM_ID = list.get(ix).getID();
                        //System.out.println("ITEM_ID = " + ITEM_ID);

                        ITEM_CODE = list.get(ix).getItemcode();
                        ITEM_NAME = list.get(ix).getItemname();
                        IS_SET = String.valueOf(list.get(ix).isSet());
                        Log.d("DKHGSD",IS_SET);

                        if (IS_SET.equals("0")){
                            radio_type_one.setChecked(true);
                        }else if (IS_SET.equals("1")){
                            radio_type_set.setChecked(true);
                        }else if (IS_SET.equals("2")){
                            radio_type_set2.setChecked(true);
                        }

                        if (list.get(ix).getIsDenger().equals("0")){
                            denger.setChecked(false);
                        }else {
                            denger.setChecked(true);
                        }

                        ImageName = list.get(ix).getPicture();
                        if(list.get(ix).isSet().equals("1")){
                            bt_step.setImageResource(R.drawable.ss_ic_step2_enable);
                            bt_step2.setImageResource(R.drawable.ss_ic_step3_enable);
                            bt_step3.setImageResource(R.drawable.ss_ic_step4_enable_new);
                            tab_2.setBackgroundResource(R.drawable.ss_bt_addset_enable);
                            tab_2.setEnabled(true);
                            txt_summenuset.setBackgroundResource(R.drawable.textview_column);
                            txt_manuqty.setBackgroundResource(R.drawable.textview_column);
                            txt_manuunit.setBackgroundResource(R.drawable.textview_column);
                            btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program);
                            btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash);
                            btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_enable2);
                            save_im.setBackgroundResource(R.drawable.bt_save_newitem_new);
                            bt_displaystockdetail.setBackgroundResource(R.drawable.bt_stock_new);
                        }else{
                            //bt_step2.setBackgroundResource(R.drawable.ss_ic_step2_disable);
                            bt_step.setImageResource(R.drawable.ss_ic_step2_enable);
                            bt_step2.setImageResource(R.drawable.ss_ic_step3_enable);
                            bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
                            tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
                            tab_2.setEnabled(false);
                            txt_summenuset.setBackgroundResource(R.drawable.text_view);
                            txt_manuqty.setBackgroundResource(R.drawable.text_view);
                            txt_manuunit.setBackgroundResource(R.drawable.text_view);
                            btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program);
                            save_im.setBackgroundResource(R.drawable.bt_save_newitem_new);
                            btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash);
                            btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_enable2);
                            bt_displaystockdetail.setBackgroundResource(R.drawable.bt_stock_new);
                        }

                        chkIsSpecial.setEnabled(!list.get(ix).isSet().equals("0"));
                        chkIsSpecial.setChecked(list.get(ix).isSpecial());

                        chkNoWash.setChecked(list.get(ix).getNoWash().equals("1"));
                        no_wash_num_item1 = list.get(ix).getNoWashType() ;
                        if (no_wash_num_item1.equals("1")){
                            no_wash_num_item2 = "(เครื่องมือ)";
                        }else if (no_wash_num_item1.equals("2")){
                            no_wash_num_item2 = "(ผ้า)";
                        }else if (no_wash_num_item1.equals("0")){
                            no_wash_num_item2 = "";
                        }
                        no_wash_type.setText(no_wash_num_item2);
                        chkisWashdept.setChecked(list.get(ix).getWashDept().equals("1"));
                        paydep.setChecked(list.get(ix).getPayDep().equals("1"));

                        edt_item_code1.setText(list.get(ix).getItemcode());
                        edt_item_code1.setVisibility(View.INVISIBLE);
                        edt_item_code.setText(list.get(ix).getItemcode());
                        edt_item_name.setText(list.get(ix).getItemname());
                        limit_count.setText(list.get(ix).getLimitCount());
                        edt_item_alternate_name.setText(list.get(ix).getAlternatename());
                        edt_set_count.setText(list.get(ix).getSetCount());

                        radio_used.setChecked(list.get(ix).getIsNormal());
                        radio_unused.setChecked(!list.get(ix).getIsNormal());

                        radio_reused.setChecked(list.get(ix).getIsReuse());
                        radio_unreused.setChecked(!list.get(ix).getIsReuse());

                        radio_used_department.setChecked(list.get(ix).getSpecialID().equals("1"));
                        radio_used_multiple_department.setChecked(!list.get(ix).getSpecialID().equals("1"));

                        txt_item_type.setText(list.get(ix).getTyeName());
                        txt_special.setText(list.get(ix).getSpecialName());
                        txt_supplier.setText(list.get(ix).getSuppliername());
                        txt_packingmat.setText(list.get(ix).getPackingMat());
                        txt_manufact.setText(list.get(ix).getManuFact());
                        txt_washingprocess.setText(list.get(ix).getWashingProcess());
                        txt_label.setText(list.get(ix).getLabelName());
                        txt_sterileprocess.setText(list.get(ix).getSterileName());
                        txt_unit.setText(list.get(ix).getUnitName());
                        //txt_department.setText(list.get(ix).getDepName());
                        CheckUsageContScan(list.get(ix).getDepName());

                        txt_item_type.setContentDescription(list.get(ix).getItemtypeID());
                        txt_special.setContentDescription(list.get(ix).getSpecialID());
                        txt_supplier.setContentDescription(list.get(ix).getSupllierID());
                        txt_packingmat.setContentDescription(list.get(ix).getPackingMatID());
                        txt_manufact.setContentDescription(list.get(ix).getFactID());
                        txt_washingprocess.setContentDescription(list.get(ix).getWashProcessID());
                        txt_label.setContentDescription(list.get(ix).getLabelID());
                        txt_sterileprocess.setContentDescription(list.get(ix).getSterileProcessID());
                        txt_unit.setContentDescription(list.get(ix).getUnitID());
                        //txt_department.setContentDescription(list.get(ix).getDepartmentID());
                        etxt_dept.setContentDescription(list.get(ix).getDepartmentID());

                        edt_life.setText(list.get(ix).getShelflife());

                        //Detail 2
                        edt_cost_price.setText(list.get(ix).getCostPrice());
                        edt_sale_price.setText(list.get(ix).getSalePrice());
                        edt_usage_price.setText(list.get(ix).getUsagePrice());

                        //Detail 3
                        edt_minimum.setText(list.get(ix).getMinimum());
                        edt_maximum.setText(list.get(ix).getMaximum());
                        txt_on_department.setText(list.get(ix).getOnDepartment());
                        txt_all_item_stock.setText(list.get(ix).getAll_ItemStock());
                        edt_on_stock.setText(list.get(ix).getOnStock());
                        edt_on_cssd.setText(list.get(ix).getOnCssd());

                        stockdetail_edt_minimum=list.get(ix).getMinimum();
                        stockdetail_edt_maximum=list.get(ix).getMaximum();
                        stockdetail_txt_on_department=list.get(ix).getOnDepartment();
                        stockdetail_txt_all_item_stock=list.get(ix).getAll_ItemStock();
                        stockdetail_edt_on_stock=list.get(ix).getOnStock();
                        stockdetail_edt_on_cssd=list.get(ix).getOnCssd();

                        URL imageUrl = new URL(Url.URL + "cssd_image/"+getF_edt_item_code()+"_pic1"+".PNG");

                        System.out.println(Url.URL + "cssd_image/"+getF_edt_item_code()+"_pic1"+".PNG");

                        //Picasso.get().load(String.valueOf(imageUrl)).into(img_item1);

                        Picasso.get().load(String.valueOf(imageUrl)).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(img_item1);

                    } catch (Exception e) {
                        e.printStackTrace();
                        //return;
                    }

                    // Display Item Detail (SET)
                    if(IS_SET.equals("1")) {
                        list_set_item.setAdapter(null);
                        setsumzero();
                        BackgroundWorker1 backgroundWorker = new BackgroundWorker1(Item_newlayout.this);
                        backgroundWorker.execute("display_item_set", ITEM_ID);
                    }else{
                        list_set_item.setAdapter(null);
                        setsumzero();
                    }

                }else if(TYPE.equals("display_item_set")){
                    try {
                        setsumzero();
                        ArrayAdapter<ModelItemDetail> adapter = new ItemDetailAdapter(Item_newlayout.this, getItemDetailModel());
                        list_set_item.setAdapter(adapter);
                        txt_summenuset.setText("รายการ [ "+summenuset+" ] ชิ้น");
                        txt_summenuset.setTextColor(Color.WHITE);
                    } catch (Exception e) {
                        list_set_item.setAdapter(null);
                        setsumzero();
                        e.printStackTrace();
                    }
                }else if(TYPE.equals("delete")){
                    onClearForm();
                }
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


        // =========================================================================================
        private List<ModelMasterData> getModel() throws Exception{

            List<ModelMasterData> list = new ArrayList<>();

            try {
                int index = 0;

                ////System.out.println("size() = " + size);
                ////System.out.println("data.size() = " + data.size());

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            get(
                                    index,
                                    data.get(i),
                                    data.get(i + 1)
                            )
                    );

                    index++;
                }

                // //System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelMasterData get(int index, String code, String name) {
            return new ModelMasterData(
                    index,
                    code,
                    name
            );
        }

        // =========================================================================================

        private List<ModelItems> getItemModel() throws Exception{

            List<ModelItems> list = new ArrayList<>();

            try {

                System.out.println("size() = " + size);
                System.out.println("data.size() = " + data.size());

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            getItem(
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
                                    data.get(i + 29),
                                    data.get(i + 30),
                                    data.get(i + 31),
                                    data.get(i + 32),
                                    data.get(i + 33),
                                    data.get(i + 34),
                                    data.get(i + 35),
                                    data.get(i + 36),
                                    data.get(i + 37),
                                    data.get(i + 38),
                                    data.get(i + 39),
                                    data.get(i + 40),
                                    data.get(i + 41),
                                    data.get(i + 42),
                                    data.get(i + 43),
                                    data.get(i + 44),
                                    data.get(i + 45),
                                    data.get(i + 46),
                                    data.get(i + 47),
                                    data.get(i + 48),
                                    data.get(i + 49),
                                    data.get(i + 50),
                                    data.get(i + 51),
                                    data.get(i + 52)
                            )
                    );
                }

                System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelItems getItem(String ID, String itemcode, String itemname, String alternatename, String barcode, String isReuse, String isNormal, String itemtypeID, String unitID, String specialID, String departmentID, String shelfLifeID, String picture, String setCount, String packingMatID, String costPrice, String salePrice, String usagePrice, String sterileMachineID, String sterileProcessID, String washMachineID, String washProcessID, String supllierID, String factID, String labelID, String minimum, String maximum, String weight, String depName, String tyeName, String labelName, String manuFact, String packingMat, String specialName, String machine_Name, String sterileName, String suppliername, String unitName, String washingName, String washingProcess, String IsSet, String IsSpecial, String OnDepartment,
                                   String OnStock,
                                   String OnCssd,
                                   String All_ItemStock,
                                   String Shelflife,
                                   String NoWash,
                                   String NoWashType,
                                   String WashDept,
                                   String LimitCount,
                                   String PayDep,
                                   String IsDenger) {
            return new ModelItems(
                    ID, itemcode, itemname, alternatename, barcode, isReuse, isNormal, itemtypeID, unitID, specialID, departmentID, shelfLifeID, picture, setCount, packingMatID, costPrice, salePrice, usagePrice, sterileMachineID, sterileProcessID, washMachineID, washProcessID, supllierID, factID, labelID, minimum, maximum, weight, depName, tyeName, labelName, manuFact, packingMat, specialName, machine_Name, sterileName, suppliername, unitName, washingName, washingProcess, IsSet, IsSpecial, OnDepartment,
                    OnStock,
                    OnCssd,
                    All_ItemStock,
                    Shelflife,
                    NoWash,
                    NoWashType,
                    WashDept,
                    LimitCount,
                    PayDep,
                    IsDenger
            );
        }

        // =========================================================================================

        private List<ModelItemDetail> getItemDetailModel() throws Exception{

            List<ModelItemDetail> list = new ArrayList<>();

            try {
                int index = 0;

                //System.out.println("data.size() = " + data.size());
                //System.out.println("size = " + size);

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            getItemDegtail(
                                    index,
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
                                    data.get(i + 13)
                            )
                    );

                    index++;
                    summenuset++;
                }

                // //System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelItemDetail getItemDegtail(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set) {
            return new ModelItemDetail(
                    index,
                    ID,
                    itemcode,
                    itemname,
                    alternatename,
                    barcode,
                    setCount,
                    unitName,
                    ID_set,
                    itemDetailID,
                    qty,
                    itemcode_set,
                    itemname_set,
                    alternatename_set,
                    barcode_set
            );
        }
        // =========================================================================================

    }

    public class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        BackgroundWorker (Context ctx) {
            context = ctx;
        }
        String TYPE;

        //private ProgressDialog dialog = new ProgressDialog(Item.this);

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];

            String post_data = "";
            String url_ = null;

            TYPE = type;

            //System.out.println("TYPE = " + TYPE);

            if(type.equals("delete")) {

                url_ = Url.URL + "cssd_delete_item.php";

                String id = params[1];
                post_data += "&p_id=" + id;

            }else if(type.equals("save")) {

                url_ = Url.URL + "cssd_save_item.php";

                post_data += "p_IsNew=" + getIsNew();

                post_data += "&p_ID=" + ITEM_ID;

                if (!getF_edt_item_code().equals(null)){
                    post_data += "&p_itemcode=" + getF_edt_item_code();
                }else {
                    post_data += "&p_itemcode=" + getF_edt_item_code1();
                }
                post_data += "&p_FactID="+ getF_txt_manufact();
                post_data += "&p_LabelID="+ getF_txt_label();
                post_data += "&p_Minimum="+ getF_edt_minimum();
                post_data += "&p_Maximum="+ getF_edt_maximum();
                post_data += "&p_weight=1";
                post_data += "&p_itemtypeID="+ getF_txt_item_type();
                post_data += "&p_no_wash=" + ( chkNoWash.isChecked() ? "1" : "0") ;
                post_data += "&p_wash_dept=" + ( chkisWashdept.isChecked() ? "1" : "0") ;
                Log.d("Print :",post_data) ;

            }else if(type.equals("display")) {

                url_ = Url.URL + "cssd_display_item.php";
                Log.d("FJKDH",url_);
                String ITEM_ID = params[1];
                post_data += "&p_filter_id=" + ITEM_ID;

            }else if(type.equals("display_item_set")) {

                url_ = Url.URL + "cssd_display_item_set.php";
                String ITEM_ID = params[1];
                post_data += "&p_itemcode=" + ITEM_ID;

                Log.d("BANK",post_data += "&p_itemcode=" + ITEM_ID);

            }else if(type.equals("get_ModelDepartment")) {
                url_ = Url.URL + "get_ModelDepartment.php";
                post_data ="";
            }else{
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

                System.out.println("URL = " + result);

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
            Log.d("onPostExecute: ", result);
            Success = ason.isSuccess();
            size = ason.getSize();
            data = ason.getASONData();

            if(Success && data != null) {
                // if (dialog.isShowing()) {
                //     dialog.dismiss();
                // }

                if(TYPE.equals("display")){

                    int ix = 0;
                    IsNew = false;


                    try {
                        List<ModelItems> list = getItemModel();



                        ITEM_ID = list.get(ix).getID();
                        //System.out.println("ITEM_ID = " + ITEM_ID);
                        ITEM_CODE = list.get(ix).getItemcode();
                        ITEM_NAME = list.get(ix).getItemname();
                        ImageName = list.get(ix).getPicture();

                        IS_SET = list.get(ix).isSet();

                        if (IS_SET.equals("0")){
                            radio_type_one.setChecked(true);
                        }else if (IS_SET.equals("1")){
                            radio_type_set.setChecked(true);
                        }else if (IS_SET.equals("2")){
                            radio_type_set2.setChecked(true);
                        }

                        if(list.get(ix).isSet().equals("0")){
                            bt_step.setImageResource(R.drawable.ss_ic_step2_enable);
                            bt_step2.setImageResource(R.drawable.ss_ic_step3_enable);
                            bt_step3.setImageResource(R.drawable.ss_ic_step4_enable_new);
                            tab_2.setBackgroundResource(R.drawable.ss_bt_addset_enable);
                            tab_2.setEnabled(true);
                            txt_summenuset.setBackgroundResource(R.drawable.textview_column);
                            txt_manuqty.setBackgroundResource(R.drawable.textview_column);
                            txt_manuunit.setBackgroundResource(R.drawable.textview_column);

                        }else{
                            //bt_step2.setBackgroundResource(R.drawable.ss_ic_step2_disable);
                            bt_step.setImageResource(R.drawable.ss_ic_step2_enable);
                            bt_step2.setImageResource(R.drawable.ss_ic_step3_enable);
                            bt_step3.setImageResource(R.drawable.ss_ic_step4_enable_new);
                            tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
                            tab_2.setEnabled(false);
                            txt_summenuset.setBackgroundResource(R.drawable.text_view);
                            txt_manuqty.setBackgroundResource(R.drawable.text_view);
                            txt_manuunit.setBackgroundResource(R.drawable.text_view);
                        }

                        chkIsSpecial.setEnabled(!list.get(ix).isSet().equals("0"));
                        chkIsSpecial.setChecked(list.get(ix).isSpecial());

                        chkNoWash.setChecked(list.get(ix).getNoWash().equals("1"));

                        chkisWashdept.setChecked(list.get(ix).getWashDept().equals("1"));
                        paydep.setChecked(list.get(ix).getPayDep().equals("1"));

                        edt_item_code1.setVisibility(View.INVISIBLE);
                        edt_item_code.setText(list.get(ix).getItemcode());
                        edt_item_name.setText(list.get(ix).getItemname());
                        limit_count.setText(list.get(ix).getLimitCount());
                        edt_item_alternate_name.setText(list.get(ix).getAlternatename());
                        edt_set_count.setText(list.get(ix).getSetCount());

                        radio_used.setChecked(list.get(ix).getIsNormal());
                        radio_unused.setChecked(!list.get(ix).getIsNormal());

                        radio_reused.setChecked(list.get(ix).getIsReuse());
                        radio_unreused.setChecked(!list.get(ix).getIsReuse());

                        radio_used_department.setChecked(list.get(ix).getSpecialID().equals("1"));
                        radio_used_multiple_department.setChecked(!list.get(ix).getSpecialID().equals("1"));

                        txt_item_type.setText(list.get(ix).getTyeName());
                        txt_special.setText(list.get(ix).getSpecialName());
                        txt_supplier.setText(list.get(ix).getSuppliername());
                        txt_packingmat.setText(list.get(ix).getPackingMat());
                        txt_manufact.setText(list.get(ix).getManuFact());
                        txt_washingprocess.setText(list.get(ix).getWashingProcess());
                        txt_label.setText(list.get(ix).getLabelName());
                        txt_sterileprocess.setText(list.get(ix).getSterileName());
                        txt_unit.setText(list.get(ix).getUnitName());
                        //txt_department.setText(list.get(ix).getDepName());

                        txt_item_type.setContentDescription(list.get(ix).getItemtypeID());
                        txt_special.setContentDescription(list.get(ix).getSpecialID());
                        txt_supplier.setContentDescription(list.get(ix).getSupllierID());
                        txt_packingmat.setContentDescription(list.get(ix).getPackingMatID());
                        txt_manufact.setContentDescription(list.get(ix).getFactID());
                        txt_washingprocess.setContentDescription(list.get(ix).getWashProcessID());
                        txt_label.setContentDescription(list.get(ix).getLabelID());
                        txt_sterileprocess.setContentDescription(list.get(ix).getSterileProcessID());
                        txt_unit.setContentDescription(list.get(ix).getUnitID());
                        //txt_department.setContentDescription(list.get(ix).getDepartmentID());
                        etxt_dept.setContentDescription(list.get(ix).getDepartmentID());

                        edt_life.setText(list.get(ix).getShelflife());

                        //Detail 2
                        edt_cost_price.setText(list.get(ix).getCostPrice());
                        edt_sale_price.setText(list.get(ix).getSalePrice());
                        edt_usage_price.setText(list.get(ix).getUsagePrice());

                        //Detail 3
                        edt_minimum.setText(list.get(ix).getMinimum());
                        edt_maximum.setText(list.get(ix).getMaximum());
                        txt_on_department.setText(list.get(ix).getOnDepartment());
                        txt_all_item_stock.setText(list.get(ix).getAll_ItemStock());
                        edt_on_stock.setText(list.get(ix).getOnStock());
                        edt_on_cssd.setText(list.get(ix).getOnCssd());

                        stockdetail_edt_minimum=list.get(ix).getMinimum();
                        stockdetail_edt_maximum=list.get(ix).getMaximum();
                        stockdetail_txt_on_department=list.get(ix).getOnDepartment();
                        stockdetail_txt_all_item_stock=list.get(ix).getAll_ItemStock();
                        stockdetail_edt_on_stock=list.get(ix).getOnStock();
                        stockdetail_edt_on_cssd=list.get(ix).getOnCssd();

                        URL imageUrl = new URL(Url.URL + "cssd_image/"+getF_edt_item_code()+"_pic1"+".PNG");

                        //Picasso.get().load(String.valueOf(imageUrl)).into(img_item1);

                        Picasso.get().load(String.valueOf(imageUrl)).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(img_item1);

                        //System.out.println(img_item1);

                    } catch (Exception e) {
                        e.printStackTrace();
                        //return;
                    }

                    // Display Item Detail (SET)
                    if(IS_SET.equals("1")) {
                        list_set_item.setAdapter(null);
                        setsumzero();
                        Item_newlayout.BackgroundWorker backgroundWorker = new Item_newlayout.BackgroundWorker(Item_newlayout.this);
                        backgroundWorker.execute("display_item_set", ITEM_ID);
                    }else{
                        list_set_item.setAdapter(null);
                        setsumzero();
                    }

                }else if(TYPE.equals("display_item_set")){
                    try {
                        setsumzero();
                        ArrayAdapter<ModelItemDetail> adapter = new ItemDetailAdapter(Item_newlayout.this, getItemDetailModel());
                        list_set_item.setAdapter(adapter);
                        txt_summenuset.setText("รายการ [ "+summenuset+" ] ชิ้น");
                        txt_summenuset.setTextColor(Color.WHITE);

                    } catch (Exception e) {
                        list_set_item.setAdapter(null);
                        setsumzero();
                        e.printStackTrace();
                    }
                }else if(TYPE.equals("delete")){
                    onClearForm();
                }else if(TYPE.equals("save")){
                    //onClearForm();
                    try {
                        if(ITEM_ID==null){
                            int i = 0;
                            //IsNew = false;
                            setsumzero();
                            List<ModelMasterData> list = getitemcode();
                            ITEM_ID = list.get(i).getCode();
                            onDisplay(ITEM_ID);
                        }else{
                            onDisplay(ITEM_ID);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Item_newlayout.this, "บันทึกรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                }
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


        // =========================================================================================
        private List<ModelMasterData> getModel() throws Exception{

            List<ModelMasterData> list = new ArrayList<>();

            try {
                int index = 0;

                ////System.out.println("size() = " + size);
                ////System.out.println("data.size() = " + data.size());

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            get(
                                    index,
                                    data.get(i),
                                    data.get(i + 1)
                            )
                    );

                    index++;
                }

                // //System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelMasterData get(int index, String code, String name) {
            return new ModelMasterData(
                    index,
                    code,
                    name
            );
        }

        // =========================================================================================

        private List<ModelItems> getItemModel() throws Exception{

            List<ModelItems> list = new ArrayList<>();

            try {

                System.out.println("size() = " + size);
                System.out.println("data.size() = " + data.size());

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            getItem(
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
                                    data.get(i + 29),
                                    data.get(i + 30),
                                    data.get(i + 31),
                                    data.get(i + 32),
                                    data.get(i + 33),
                                    data.get(i + 34),
                                    data.get(i + 35),
                                    data.get(i + 36),
                                    data.get(i + 37),
                                    data.get(i + 38),
                                    data.get(i + 39),
                                    data.get(i + 40),
                                    data.get(i + 41),
                                    data.get(i + 42),
                                    data.get(i + 43),
                                    data.get(i + 44),
                                    data.get(i + 45),
                                    data.get(i + 46),
                                    data.get(i + 47),
                                    data.get(i + 48),
                                    data.get(i + 49),
                                    data.get(i + 50),
                                    data.get(i + 51),
                                    data.get(i + 52)
                            )
                    );
                }

                System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelItems getItem(String ID, String itemcode, String itemname, String alternatename, String barcode, String isReuse, String isNormal, String itemtypeID, String unitID, String specialID, String departmentID, String shelfLifeID, String picture, String setCount, String packingMatID, String costPrice, String salePrice, String usagePrice, String sterileMachineID, String sterileProcessID, String washMachineID, String washProcessID, String supllierID, String factID, String labelID, String minimum, String maximum, String weight, String depName, String tyeName, String labelName, String manuFact, String packingMat, String specialName, String machine_Name, String sterileName, String suppliername, String unitName, String washingName, String washingProcess, String IsSet, String IsSpecial, String OnDepartment,
                                   String OnStock,
                                   String OnCssd,
                                   String All_ItemStock,
                                   String Shelflife,
                                   String NoWash,
                                   String NoWashType,
                                   String WashDept,
                                   String LimitCount,
                                   String PayDep,
                                   String IsDenger) {
            return new ModelItems(
                    ID, itemcode, itemname, alternatename, barcode, isReuse, isNormal, itemtypeID, unitID, specialID, departmentID, shelfLifeID, picture, setCount, packingMatID, costPrice, salePrice, usagePrice, sterileMachineID, sterileProcessID, washMachineID, washProcessID, supllierID, factID, labelID, minimum, maximum, weight, depName, tyeName, labelName, manuFact, packingMat, specialName, machine_Name, sterileName, suppliername, unitName, washingName, washingProcess, IsSet, IsSpecial, OnDepartment,
                    OnStock,
                    OnCssd,
                    All_ItemStock,
                    Shelflife,
                    NoWash,
                    NoWashType,
                    WashDept,
                    LimitCount,
                    PayDep,
                    IsDenger
            );
        }

        // =========================================================================================

        private List<ModelItemDetail> getItemDetailModel() throws Exception{

            List<ModelItemDetail> list = new ArrayList<>();

            try {
                int index = 0;

                //System.out.println("data.size() = " + data.size());
                //System.out.println("size = " + size);

                for(int i=0;i<data.size();i+=size){

                    list.add(
                            getItemDegtail(
                                    index,
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
                                    data.get(i + 13)
                            )
                    );

                    index++;
                    summenuset++;
                }

                // //System.out.println("list = " + list.size());

            }catch(Exception e){
                e.printStackTrace();
            }

            return list;
        }

        private ModelItemDetail getItemDegtail(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set) {
            return new ModelItemDetail(
                    index,
                    ID,
                    itemcode,
                    itemname,
                    alternatename,
                    barcode,
                    setCount,
                    unitName,
                    ID_set,
                    itemDetailID,
                    qty,
                    itemcode_set,
                    itemname_set,
                    alternatename_set,
                    barcode_set
            );
        }
        // =========================================================================================

    }

    private List<ModelMasterData> getitemcode() throws Exception{

        List<ModelMasterData> list = new ArrayList<>();

        try {
            int index = 0;

            ////System.out.println("size() = " + size);
            ////System.out.println("data.size() = " + data.size());

            for(int i=0;i<data.size();i+=size){

                list.add(
                        get(
                                index,
                                data.get(i),
                                data.get(i)
                        )
                );

                index++;
            }

            // //System.out.println("list = " + list.size());

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    private ModelMasterData get(int index, String code, String name) {
        return new ModelMasterData(
                index,
                code,
                name
        );
    }

    private List<ModelDepartment> getModelDept() throws Exception{

        List<ModelDepartment> list = new ArrayList<>();
        try {
            int index = 0;

            ////System.out.println("size() = " + size);
            ////System.out.println("data.size() = " + data.size());

            for(int i=0;i<data.size();i+=size){

                list.add(
                        get(
                                data.get(i),
                                data.get(i+1)
                        )
                );
            }

            // //System.out.println("list = " + list.size());

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private ModelDepartment get(String dept_ID, String dept_Name) {
        return new ModelDepartment(
                dept_ID,
                dept_Name
        );
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public void displayStockdetail(){
        final Dialog dialog = new Dialog(Item_newlayout.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_displaystockdetail);
        dialog.setCancelable(false);
        dialog.setTitle("ข้อมูลสต๊อก");
        EditText edt_minimum = (EditText) dialog.findViewById(R.id.edt_minimum);
        EditText edt_maximum = (EditText) dialog.findViewById(R.id.edt_maximum);
        EditText txt_on_department = (EditText) dialog.findViewById(R.id.txt_on_department);
        EditText txt_all_item_stock = (EditText) dialog.findViewById(R.id.txt_all_item_stock);
        TextView edt_on_stock = (TextView) dialog.findViewById(R.id.edt_on_stock);
        TextView edt_on_cssd = (TextView) dialog.findViewById(R.id.edt_on_cssd);
        final TextView itemcode_stock = (TextView) dialog.findViewById(R.id.itemcode_stock);
        TextView itemname_stock = (TextView) dialog.findViewById(R.id.itemname_stock);

        edt_minimum.setText(stockdetail_edt_minimum);
        edt_maximum.setText(stockdetail_edt_maximum);
        edt_minimum.setEnabled(false);
        edt_maximum.setEnabled(false);
        txt_on_department.setText(stockdetail_txt_on_department);
        txt_all_item_stock.setText(stockdetail_txt_all_item_stock);
        edt_on_stock.setText(stockdetail_edt_on_stock);
        edt_on_cssd.setText(stockdetail_edt_on_cssd);
        itemcode_stock.setText("รหัส : "+edt_item_code1.getText().toString());
        itemname_stock.setText("ชื่ออุปกรณ์ : "+edt_item_name.getText().toString());
        getSupportActionBar().hide();

        Button add_stock = (Button) dialog.findViewById(R.id.add_stock);
        add_stock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Item_newlayout.this,dialog_additemstock.class);
                intent.putExtra("ItemCode",edt_item_code1.getText().toString());
                intent.putExtra("B_ID",B_ID);
                startActivity(intent);
            }
        });

        Button bt_back_stockdetail = (Button) dialog.findViewById(R.id.bt_back_stockdetail);
        bt_back_stockdetail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void getNoWashType(){
        no_wash_num_item = no_wash_type.getText().toString();
        if (no_wash_num_item.equals("(เครื่องมือ)")){
            no_wash_num_item1 = "1";
        }else if (no_wash_num_item.equals("(ผ้า)")){
            no_wash_num_item1 = "2";
        }else if (no_wash_num_item.equals("")){
            no_wash_num_item1 = "0";
        }
        if (chkNoWash.isChecked()) {
            Intent i = new Intent(Item_newlayout.this, CheckNoWashType.class);
            i.putExtra("type",no_wash_num_item1);
            startActivityForResult(i, 205);
        }else {
//            no_wash_type.setText("");
        }
    }

    public void getCheckList(){
        Intent i = new Intent(Item_newlayout.this, CheckList.class);
        startActivity(i);
    }

    public void StratPage(){
        ItemCode = "";
        ItemCode1 = "";
        edt_item_code.setVisibility(View.VISIBLE);
        edt_item_code.setText("");
        edt_item_code.requestFocus();
        edt_item_code.setEnabled(true);
        edt_item_code.setTextColor(Color.BLACK);
        onClearForm();
        tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
        bt_step.setImageResource(R.drawable.ss_ic_step2_disable);
        btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program_disable);
        btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash2);
        btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_disable2);
        save_im.setBackgroundResource(R.drawable.bt_save_newitem_disable_new);
        bt_step2.setImageResource(R.drawable.ss_ic_step3_disable);
        bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
        layout_listview.setVisibility(View.INVISIBLE);
        IsSaveDoc = 0;
        IsNewSave = 1;
        btn_save_item.setBackgroundResource(R.drawable.bt_save_newitem_disable);
        bt_displaystockdetail.setBackgroundResource(R.drawable.bt_stock_disable_new);
    }

    public void CreateItem() {
        class CreateItem extends AsyncTask<String, Void, String> {
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

                        if (c.getString("ItemNew").equals("")){
                            Toast.makeText(Item_newlayout.this, "บันทึกรายการไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            bt_displaystockdetail.setEnabled(false);
                            img_item1.setEnabled(false);
                            save_im.setEnabled(false);
                            btn_open_sterile_program_item.setEnabled(false);
                            btn_open_wash_program_item.setEnabled(false);
                            btn_create_item_stock.setEnabled(false);
                            tab_2.setEnabled(false);
                        }else {
                            bt_displaystockdetail.setEnabled(true);
                            img_item1.setEnabled(true);
                            save_im.setEnabled(true);
                            btn_open_sterile_program_item.setEnabled(true);
                            btn_open_wash_program_item.setEnabled(true);
                            btn_create_item_stock.setEnabled(true);
                            tab_2.setEnabled(true);
                            try {
                                if(ITEM_ID==null){
                                    i = 0;
                                    //IsNew = false;
                                    setsumzero();
                                    List<ModelMasterData> list = getitemcode();
                                    ITEM_ID = list.get(i).getCode();
                                    //onDisplay(getF_edt_item_code1());
                                }else{
                                    //onDisplay(getF_edt_item_code1());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Item_newlayout.this, "บันทึกรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                            edt_item_code1.setText(c.getString("ItemNew"));
                            ItemCode = "";
                            if (IsNewSave == 1){
                                IsNewSave = 0;
                            }
                            edt_item_code1.setEnabled(false);
                            bt_displaystockdetail.setBackgroundResource(R.drawable.bt_stock_new);
                            if (!edt_item_name.getText().toString().equals("")&&!txt_unit.getText().toString().equals("") &&!txt_label.getText().toString().equals("")&&!etxt_dept.getSelectedItem().equals("")) {
                                bt_step.setImageResource(R.drawable.ss_ic_step2_enable);
                                bt_step2.setImageResource(R.drawable.ss_ic_step3_enable);
                                bt_step3.setImageResource(R.drawable.ss_ic_step4_enable_new);
                                btn_open_sterile_program_item.setTextColor(Color.WHITE);
                                btn_open_wash_program_item.setTextColor(Color.WHITE);
                                btn_create_item_stock.setTextColor(Color.WHITE);
                                btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program);
                                save_im.setBackgroundResource(R.drawable.bt_save_newitem_new);
                                btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash);
                                btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_enable2);
                            }else {
                                bt_step.setImageResource(R.drawable.ss_ic_step2_disable);
                                bt_step2.setImageResource(R.drawable.ss_ic_step3_disable);
                                bt_step3.setImageResource(R.drawable.ss_ic_step4_disable_new);
                                btn_open_sterile_program_item.setTextColor(Color.BLACK);
                                btn_open_wash_program_item.setTextColor(Color.BLACK);
                                btn_create_item_stock.setTextColor(Color.BLACK);
                                btn_open_sterile_program_item.setBackgroundResource(R.drawable.ss_bt_program_disable);
                                save_im.setBackgroundResource(R.drawable.bt_save_newitem_disable_new);
                                btn_open_wash_program_item.setBackgroundResource(R.drawable.ss_bt_program_wash2);
                                btn_create_item_stock.setBackgroundResource(R.drawable.bt_stock_disable2);
                            }
                            if (getF_radio_type_set().equals("1")){
                                bt_step3.setImageResource(R.drawable.ss_ic_step4_enable);
                                tab_2.setBackgroundResource(R.drawable.ss_bt_addset_enable);
                                tab_2.setEnabled(true);
                                txt_summenuset.setBackgroundResource(R.drawable.textview_column);
                                txt_manuqty.setBackgroundResource(R.drawable.textview_column);
                                txt_manuunit.setBackgroundResource(R.drawable.textview_column);
                            }else{
                                bt_step3.setImageResource(R.drawable.ss_ic_step4_disable);
                                tab_2.setBackgroundResource(R.drawable.ss_bt_addset_disable);
                                tab_2.setEnabled(false);
                                txt_summenuset.setBackgroundResource(R.drawable.text_view);
                                txt_manuqty.setBackgroundResource(R.drawable.text_view);
                                txt_manuunit.setBackgroundResource(R.drawable.text_view);
                            }
                            edt_item_code1.setTextColor(Color.BLACK);
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
                data.put("p_IsNew",IsNewSave+"");
                data.put("p_ID",ITEM_ID+"");
                data.put("p_itemcode",getF_edt_item_code1());
                data.put("p_itemname",getF_edt_item_name());
                data.put("p_Alternatename",getF_edt_item_alternate_name());
                data.put("p_Barcode",getF_edt_item_code1());
                if (radio_type_one.isChecked()){
                    data.put("p_IsSet","0");
                }else {
                    data.put("p_IsSet",getF_radio_type_set());
                }
                data.put("p_IsReuse",getF_radio_reused());
                data.put("p_IsNormal",getF_radio_used());
                data.put("p_itemtypeID",getF_txt_item_type());
                data.put("p_UnitID",getF_txt_unit());
                data.put("p_SpecialID",getF_txt_special());
                data.put("p_DepartmentID",getF_txt_on_department());
                data.put("p_ShelfLifeID","1");
                data.put("p_Picture",ImageName+"");
                data.put("p_SetCount",getF_edt_set_count());
                data.put("p_PackingMatID",getF_txt_packingmat());
                data.put("p_CostPrice",getF_edt_cost_price());
                data.put("p_SalePrice",getF_edt_sale_price());
                data.put("p_UsagePrice",getF_edt_usage_price());
                data.put("p_SterileProcessID",getF_txt_sterileprocess());
                data.put("p_WashMachineID","1");
                data.put("p_WashProcessID",getF_txt_washingprocess());
                data.put("p_SupllierID",getF_txt_supplier());
                data.put("p_FactID",getF_txt_manufact());
                data.put("p_LabelID",getF_txt_label());
                data.put("p_Minimum",getF_edt_minimum());
                data.put("p_Maximum",getF_edt_maximum());
                data.put("p_weight","1");
                data.put("p_itemtypeID",getF_txt_item_type());
                data.put("p_no_wash", ( chkNoWash.isChecked() ? "1" : "0"));
                data.put("p_wash_dept", (chkisWashdept.isChecked() ? "1" : "0"));
                if (no_wash_num_item.equals("0") || no_wash_num_item.equals("1") || no_wash_num_item.equals("2")){
                    data.put("no_wash_type",no_wash_num_item);
                }else {
                    data.put("no_wash_type","0");
                }
                data.put("limit_count",getF_edt_limit_count());
                data.put("paydep",( paydep.isChecked() ? "1" : "0"));
                data.put("denger",( denger.isChecked() ? "1" : "0"));
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_save_item_new.php", data);
                    Log.d("DKJJDK",data+"");
                    Log.d("DKJJDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CreateItem obj = new CreateItem();
        obj.execute();
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

    public void CheckUsageContScan(final String Dept) {
        class CheckUsageContScan extends AsyncTask<String, Void, String> {
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
                        etxt_dept.setSelection(Integer.parseInt(c.getString("ID")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Detp",Dept);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_dept_id.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
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

}


