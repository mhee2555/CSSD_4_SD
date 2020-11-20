package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.phc.cssd.adapter.ItemDetailAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.master_data.SterileProgramItemActivity;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelItems;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.url.Url;

public class Item extends AppCompatActivity {
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    private String userid;
    private ImageButton tab_1;
    private ImageButton tab_2;
    private ImageButton tab_3;
    private ImageButton tab_4;
    private ImageButton tab_5;
    private ImageButton tab_6;
    private Button tab_detail_1;
    private Button tab_detail_2;
    private Button tab_detail_3;
    private LinearLayout linear_layout_1;
    private LinearLayout linear_layout_2;
    private LinearLayout linear_layout_3;
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
    private EditText edt_life;
    private ListView list_set_item;
    private ImageView img_item;
    private RadioButton radio_type_one;
    private RadioButton radio_type_set;
    private EditText edt_item_code;
    private EditText edt_item_name;
    private EditText edt_item_alternate_name;
    private EditText edt_set_count;
    private RadioButton radio_used;
    private RadioButton radio_unused;
    private RadioButton radio_reused;
    private RadioButton radio_unreused;
    private RadioButton radio_used_department;
    private RadioButton radio_used_multiple_department;
    private CheckBox chkIsSpecial;
    private CheckBox chkNoWash;
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
    private Button btn_delete_item;
    private Button btn_open_item_stock;
    private Button btn_create_item_stock;
    private Button btn_open_sterile_program_item;
    private ImageView imageBack;
    private boolean IsNew = true;
    private String ITEM_ID = null;
    private String ITEM_CODE = null;
    private String ITEM_NAME = null;
    private String IS_SET = null;
    private String ImageName = "image.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byIntent();

        byWidget();

    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        //System.out.println("onRestart = " + ITEM_ID);

        // Display
        if(ITEM_ID != null) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(Item.this);
            backgroundWorker.execute("display", ITEM_ID);
        }


    }

    private void byWidget(){
        // Main Tab
        tab_1 = (ImageButton)findViewById(R.id.tab_1);
        tab_2 = (ImageButton)findViewById(R.id.tab_2);
        tab_3 = (ImageButton)findViewById(R.id.tab_3);
        tab_4 = (ImageButton)findViewById(R.id.tab_4);
        tab_5 = (ImageButton)findViewById(R.id.tab_5);
        tab_6 = (ImageButton)findViewById(R.id.tab_6);

        // Tab Detail
        tab_detail_1 = (Button)findViewById(R.id.tab_detail_1);
        tab_detail_2 = (Button)findViewById(R.id.tab_detail_2);
        tab_detail_3 = (Button)findViewById(R.id.tab_detail_3);

        linear_layout_1 = (LinearLayout)findViewById(R.id.linear_layout_1);
        linear_layout_2 = (LinearLayout)findViewById(R.id.linear_layout_2);
        linear_layout_3 = (LinearLayout)findViewById(R.id.linear_layout_3);

        radio_type_one = (RadioButton)findViewById(R.id.radio_type_one);
        radio_type_set = (RadioButton)findViewById(R.id.radio_type_set);

        radio_used = (RadioButton)findViewById(R.id.radio_used);
        radio_unused = (RadioButton)findViewById(R.id.radio_unused);

        radio_reused = (RadioButton)findViewById(R.id.radio_reused);
        radio_unreused = (RadioButton)findViewById(R.id.radio_unreused);





        radio_used_department = (RadioButton)findViewById(R.id.radio_used_department);
        radio_used_multiple_department = (RadioButton)findViewById(R.id.radio_used_multiple_department);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup_department);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_used_department){

                }else if(checkedId == R.id.radio_used_multiple_department){
                    txt_department.setContentDescription("20");
                    txt_department.setText("à¸«à¸™à¹ˆà¸§à¸¢à¸ˆà¹ˆà¸²à¸¢à¸à¸¥à¸²à¸‡");
                }
            }
        });

        chkIsSpecial = (CheckBox)findViewById(R.id.chkIsSpecial);
        chkNoWash = (CheckBox)findViewById(R.id.chkNoWash);

        txt_special = (TextView)findViewById(R.id.txt_special);
        txt_supplier = (TextView)findViewById(R.id.txt_supplier);
        txt_packingmat = (TextView)findViewById(R.id.txt_packingmat);
        txt_manufact = (TextView)findViewById(R.id.txt_manufact);
        txt_washingprocess = (TextView)findViewById(R.id.txt_washingprocess);
        txt_sterileprocess = (TextView)findViewById(R.id.txt_sterileprocess);
        txt_label = (TextView)findViewById(R.id.txt_label);
        txt_item_type = (TextView)findViewById(R.id.txt_item_type);
        txt_unit = (TextView)findViewById(R.id.txt_unit);
        edt_life = (EditText) findViewById(R.id.edt_life);
        txt_department = (TextView)findViewById(R.id.txt_department);

        list_set_item = (ListView)findViewById(R.id.list_set_item);
        img_item= (ImageView)findViewById(R.id.img_item);
        edt_item_code= (EditText)findViewById(R.id.edt_item_code);
        edt_item_name= (EditText)findViewById(R.id.edt_item_name);
        edt_item_alternate_name= (EditText)findViewById(R.id.edt_item_alternate_name);
        edt_set_count= (EditText)findViewById(R.id.edt_set_count);

        edt_cost_price= (EditText)findViewById(R.id.edt_cost_price);
        edt_sale_price= (EditText)findViewById(R.id.edt_sale_price);
        edt_usage_price= (EditText)findViewById(R.id.edt_usage_price);

        //Detail 3
        edt_minimum= (EditText)findViewById(R.id.edt_minimum);
        edt_maximum= (EditText)findViewById(R.id.edt_maximum);
        edt_on_stock= (EditText)findViewById(R.id.edt_on_stock);
        edt_on_cssd= (EditText)findViewById(R.id.edt_on_cssd);

        txt_on_department= (TextView)findViewById(R.id.txt_on_department);
        txt_all_item_stock= (EditText)findViewById(R.id.txt_all_item_stock);

        btn_save_item = (Button)findViewById(R.id.btn_save_item);
        btn_delete_item = (Button)findViewById(R.id.btn_delete_item);
        btn_open_item_stock = (Button)findViewById(R.id.btn_open_item_stock);
        btn_create_item_stock = (Button)findViewById(R.id.btn_create_item_stock);
        btn_open_sterile_program_item = (Button)findViewById(R.id.btn_open_sterile_program_item);

        radio_type_one.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                chkIsSpecial.setEnabled(radio_type_one.isChecked());
            }
        });

        radio_type_set.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                chkIsSpecial.setEnabled(!radio_type_set.isChecked());
            }
        });

        // --------------------------------------- Event -------------------------------------------
        tab_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTab(1);
                onClearForm();
            }
        });

        tab_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTab(2);

                if(ITEM_ID != null) {
                    if(IS_SET.equals("1")) {
                        openActivity(ITEM_ID, ITEM_CODE, ITEM_NAME);
                    }else{
                        Toast.makeText(Item.this, "à¸£à¸²à¸¢à¸à¸²à¸£à¸™à¸µà¹‰à¹€à¸›à¹‡à¸™à¸£à¸²à¸¢à¸à¸²à¸£à¸Šà¸´à¹‰à¸™à¹€à¸”à¸µà¸¢à¸§!!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Item.this, "à¸¢à¸±à¸‡à¹„à¸¡à¹ˆà¹„à¸”à¹‰à¹€à¸¥à¸·à¸­à¸à¸£à¸²à¸¢à¸à¸²à¸£!", Toast.LENGTH_SHORT).show();
                }

                //openActivity(ITEM_ID);
            }
        });

        tab_3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTab(3);
                openMasterActivity(Cons.ITEM_TYPE);
            }
        });

        tab_4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTab(4);
                openMasterActivity(Cons.UNITS);
            }
        });

        tab_5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTab(5);
                openMaster3FieldActivity(Cons.PACKING);
            }
        });

        tab_6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTab(6);
                openDropDown("item");
            }
        });

        // -----------------------------------------------------------------------------------------



        txt_special.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("special");
            }
        });

        txt_supplier.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("supplier");
            }
        });

        txt_packingmat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("packingmat");
            }
        });

        txt_manufact.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("manufact");
            }
        });

        txt_washingprocess.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("washprocess");
            }
        });

        txt_sterileprocess.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("sterileprocess");
            }
        });

        txt_label.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("LabelGroup");
            }
        });

        txt_on_department.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("department");
            }
        });

        txt_item_type.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("itemtype");
            }
        });

        txt_unit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("units");
            }
        });

        txt_department.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("department");
            }
        });

        // -----------------------------------------------------------------------------------------
        // Detail


        tab_detail_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTabDetail(1);
                showTabDetail(true, false, false);

            }
        });

        tab_detail_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTabDetail(2);
                showTabDetail(false, true, false);
            }
        });

        tab_detail_3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                activeTabDetail(3);
                showTabDetail(false, false, true);
            }
        });

        btn_save_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onSave();
            }
        });

        btn_delete_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onDelete();
            }
        });

        btn_open_item_stock.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onOpenItemStock();
            }
        });

        btn_create_item_stock.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onOpenItemStockDepartment();
            }
        });

        btn_open_sterile_program_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(Item.this, SterileProgramItemActivity.class);
                i.putExtra("Item_Code", ITEM_ID);
                startActivity(i);
            }
        });

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageBack.bringToFront();
    }

    private void activeTab(int tab_index){
        /*
        tab_1.setBackgroundResource(R.drawable.ic_newitem_blue);
        tab_2.setBackgroundResource(R.drawable.ic_newset_blue);
        tab_3.setBackgroundResource(R.drawable.ic_type_blue);
        tab_4.setBackgroundResource(R.drawable.ic_units_blue);
        tab_5.setBackgroundResource(R.drawable.ic_pack_blue);
        tab_6.setBackgroundResource(R.drawable.ic_search_blue);

        switch (tab_index){
            case 1: tab_1.setBackgroundResource(R.drawable.ic_newitem_grey); break;
            case 2: tab_2.setBackgroundResource(R.drawable.ic_newset_grey); break;
            case 3: tab_3.setBackgroundResource(R.drawable.ic_type_grey); break;
            case 4: tab_4.setBackgroundResource(R.drawable.ic_units_grey); break;
            case 5: tab_5.setBackgroundResource(R.drawable.ic_pack_grey); break;
            case 6: tab_6.setBackgroundResource(R.drawable.ic_search_grey); break;
        }
        */
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

    private void onClearForm(){
        IsNew = true;
        ITEM_ID = null;
        ITEM_CODE = null;
        ITEM_NAME = null;
        IS_SET = null;
        ImageName = "image.jpg";

        //Detail 1
        list_set_item.setAdapter(null);
        img_item.setImageResource(R.drawable.ic_preview);

        chkNoWash.setChecked(false);
        radio_type_one.setChecked(true);
        radio_type_set.setChecked(false);
        chkIsSpecial.setEnabled(true);

        edt_item_code.setText("");
        edt_item_name.setText("");
        edt_item_alternate_name.setText("");
        edt_set_count.setText("");

        radio_used.setChecked(true);
        radio_unused.setChecked(false);

        radio_reused.setChecked(true);
        radio_unreused.setChecked(false);

        radio_used_department.setChecked(true);
        radio_used_multiple_department.setChecked(false);

        txt_department = (TextView)findViewById(R.id.txt_department);

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

        edt_life.setText("");

        //Detail 2
        edt_cost_price.setText("");
        edt_sale_price.setText("");
        edt_usage_price.setText("");

        //Detail 3
        edt_minimum.setText("");
        edt_maximum.setText("");

        txt_all_item_stock.setText("");
    }

    private void showTabDetail(boolean t1, boolean t2, boolean t3){
        linear_layout_1.setVisibility(t1 ? View.VISIBLE : View.GONE);
        linear_layout_2.setVisibility(t2 ? View.VISIBLE : View.GONE);
        linear_layout_3.setVisibility(t3 ? View.VISIBLE : View.GONE);
    }

    private void openActivity(String ITEM_ID, String ITEM_CODE, String ITEM_NAME){
        Intent i = new Intent(Item.this, ItemSet.class);
        i.putExtra("ITEM_ID", ITEM_ID);
        i.putExtra("ITEM_CODE", ITEM_CODE);
        i.putExtra("ITEM_NAME", ITEM_NAME);
        this.startActivity(i);
    }

    private void openMasterActivity(String data){
        Intent i = new Intent(Item.this, MasterData_2Field.class);
        i.putExtra("data", data);
        this.startActivity(i);
    }

    private void openMaster3FieldActivity(String data){
        Intent i = new Intent(Item.this, MasterData_3Field.class);
        i.putExtra("data", data);
        this.startActivity(i);
    }

    private void openDropDown(String data){
        Intent i = new Intent(Item.this, MasterDropdown.class);
        i.putExtra("data", data);
        startActivityForResult(i, Master.getResult(data));
    }

    private void onOpenItemStock(){
        if(ITEM_ID != null && ( radio_type_set.isChecked() || chkIsSpecial.isChecked()) ) {
            Intent i = new Intent(Item.this, CssdCreateItemStock.class);
            i.putExtra("Item_Code", ITEM_ID);
            i.putExtra("Item_Name", edt_item_name.getText().toString());
            i.putExtra("Item_Stock", txt_all_item_stock.getText().toString());
            i.putExtra("userid", userid);
            this.startActivity(i);
        }else{
            Toast.makeText(Item.this, "à¸£à¸²à¸¢à¸à¸²à¸£à¸™à¸µà¹‰à¹€à¸›à¹‡à¸™à¸£à¸²à¸¢à¸à¸²à¸£à¸Šà¸´à¹‰à¸™à¹€à¸”à¸µà¸¢à¸§ à¹„à¸¡à¹ˆà¸ªà¸²à¸¡à¸²à¸£à¸–à¹€à¸žà¸´à¹ˆà¸¡à¸‚à¹‰à¸­à¸¡à¸¹à¸¥à¹ƒà¸™à¸ªà¸•à¹‡à¸­à¸„à¹„à¸”à¹‰ !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onOpenItemStockDepartment(){
        if(ITEM_ID != null) {
            Intent i = new Intent(Item.this, CssdCreateItemStockDepartment.class);
            i.putExtra("Item_Code", ITEM_ID);
            i.putExtra("Item_Name", edt_item_name.getText().toString());
            i.putExtra("Item_Stock", txt_all_item_stock.getText().toString());
            i.putExtra("userid", userid);
            this.startActivity(i);
        }else{
            Toast.makeText(Item.this, "à¸¢à¸±à¸‡à¹„à¸¡à¹ˆà¹„à¸”à¹‰à¹€à¸¥à¸·à¸­à¸à¸£à¸²à¸¢à¸à¸²à¸£ !!", Toast.LENGTH_SHORT).show();
        }
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

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void onDisplay(String ID){
        // Display
        BackgroundWorker backgroundWorker = new BackgroundWorker(Item.this);
        backgroundWorker.execute("display", ID);
    }

    private void onSave(){
        if(checkEnterForm()) {
            // Display
            BackgroundWorker backgroundWorker = new BackgroundWorker(Item.this);
            backgroundWorker.execute("save", userid);
        }

    }

    private void onDelete(){

        if(ITEM_ID != null) {
            AlertDialog.Builder quitDialog = new AlertDialog.Builder(Item.this);
            quitDialog.setTitle(Cons.TITLE);
            quitDialog.setMessage(Cons.CONFIRM_DELETE);

            quitDialog.setPositiveButton("à¸•à¸à¸¥à¸‡", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Display
                    BackgroundWorker backgroundWorker = new BackgroundWorker(Item.this);
                    backgroundWorker.execute("delete", ITEM_ID);
                }
            });

            quitDialog.setNegativeButton("à¸¢à¸à¹€à¸¥à¸´à¸", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            quitDialog.show();
        }else{
            Toast.makeText(Item.this, "à¸¢à¸±à¸‡à¹„à¸¡à¹ˆà¹„à¸”à¹‰à¹€à¸¥à¸·à¸­à¸à¸£à¸²à¸¢à¸à¸²à¸£à¸—à¸µà¹ˆà¸ˆà¸°à¸¥à¸š", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    ImageView imgView;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    static String strSDCardPathName = Environment.getExternalStorageDirectory() + "/temp_picture" + "/";
    String imageFileName = null;

    static String strURLUpload = Url.URL+ "uploadFile.php";
    */


    private boolean checkEnterForm(){
        String Massage = "";

        /*
        if(edt_item_code.getText().toString().equals("")){
            Massage += "à¸£à¸«à¸±à¸ª , \n";
        }
        */

        if(edt_item_name.getText().toString().equals("")){
            Massage += "à¸Šà¸·à¹ˆà¸­à¹€à¸„à¸£à¸·à¹ˆà¸­à¸‡à¸¡à¸·à¸­ , \n";
        }

        /*
        if(txt_item_type.getContentDescription() == null || txt_item_type.getContentDescription().toString().equals("")){
            Massage += "à¸›à¸£à¸°à¹€à¸ à¸— , \n";
        }
        */

        if(txt_unit.getContentDescription() == null || txt_unit.getContentDescription().toString().equals("")){
            Massage += "à¸«à¸™à¹ˆà¸§à¸¢à¸™à¸±à¸š , \n";
        }

        /*
        if(txt_special.getContentDescription() == null || txt_special.getContentDescription().toString().equals("")){
            x
            //Massage += "à¹ƒà¸Šà¹‰à¸•à¸²à¸¡à¹à¸œà¸™à¸ , \n";
        }
        */

        /*
        if(txt_on_department.getContentDescription().toString().equals("")){
            Massage += "à¸£à¸«à¸±à¸ª , \n";
        }
        */

        /*
        if(edt_set_count.getText().toString().equals("")){
            Massage += "à¸ˆà¸³à¸™à¸§à¸™ (à¹€à¸‹à¹‡à¸—) , \n";
        }

        if(edt_cost_price.getText().toString().equals("")){
            //Massage += "à¸£à¸²à¸„à¸²à¸•à¹‰à¸™à¸—à¸¸à¸™ , \n";

        }

        if(edt_sale_price.getText().toString().equals("")){
            //Massage += "à¸£à¸²à¸„à¸²à¸‚à¸²à¸¢ , \n";

        }

        if(edt_usage_price.getText().toString().equals("")){
            //Massage += "à¸£à¸²à¸„à¸²à¹ƒà¸Šà¹‰à¸‡à¸²à¸™ , \n";

        }
        */

        if(txt_packingmat.getContentDescription() == null || txt_packingmat.getContentDescription().toString().equals("")){
            Massage += "à¸£à¸¹à¸›à¹à¸šà¸šà¸à¸²à¸£à¸«à¹ˆà¸­ , \n";
        }

        /*
        if(txt_sterileprocess.getContentDescription() == null || txt_sterileprocess.getContentDescription().toString().equals("")){
            Massage += "à¸£à¸¹à¸›à¹à¸šà¸šà¸à¸²à¸£à¸†à¹ˆà¸²à¹€à¸Šà¸·à¹‰à¸­ , \n";
        }
        */

        /*
        if(txt_washingprocess.getContentDescription() == null || txt_washingprocess.getContentDescription().toString().equals("")){
            x
            //Massage += "à¸£à¸¹à¸›à¹à¸šà¸šà¸à¸²à¸£à¸¥à¹‰à¸²à¸‡ , \n";
        }

        if(txt_supplier.getContentDescription() == null || txt_supplier.getContentDescription().toString().equals("")){
            //Massage += "à¸œà¸¹à¹‰à¸‚à¸²à¸¢ , \n";
            x
        }

        if(txt_manufact.getContentDescription() == null || txt_manufact.getContentDescription().toString().equals("")){
            //Massage += "à¸œà¸¹à¹‰à¸œà¸¥à¸´à¸• , \n";
            x
        }
        */

        if(txt_label.getContentDescription() == null || txt_label.getContentDescription().toString().equals("")){
            Massage += "à¸ªà¸•à¸´à¹Šà¸à¹€à¸à¸­à¸£à¹Œ , \n";
        }

        /*
        if(edt_minimum.getText().toString().equals("")){
            Massage += "à¸à¸³à¸«à¸™à¸”à¸ˆà¸³à¸™à¸§à¸™à¸™à¹‰à¸­à¸¢à¸ªà¸¸à¸” , \n";
            x
        }

        if(edt_maximum.getText().toString().equals("")){
            Massage += "à¸à¸³à¸«à¸™à¸”à¸ˆà¸³à¸™à¸§à¸™à¸¡à¸²à¸à¸ªà¸¸à¸” , \n";
            x
        }

        if(txt_all_item_stock.getText().toString().equals("")){
            Massage += "à¸œà¸¥à¸£à¸§à¸¡ , \n";
        }
        */

        if(!Massage.equals("")){
            Toast.makeText(Item.this, "à¹„à¸¡à¹ˆà¸ªà¸²à¸¡à¸²à¸£à¸–à¸šà¸±à¸™à¸—à¸¶à¸à¹„à¸”à¹‰ à¹€à¸™à¸·à¹ˆà¸­à¸‡à¸ˆà¸²à¸à¸—à¹ˆà¸²à¸™à¸¢à¸±à¸‡à¹„à¸¡à¹ˆà¹„à¸”à¹‰à¸›à¹‰à¸­à¸™ à¸«à¸£à¸·à¸­ à¹€à¸¥à¸·à¸­à¸ à¸‚à¹‰à¸­à¸¡à¸¹à¸¥ \n" + Massage, Toast.LENGTH_SHORT).show();
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
    private String getF_edt_item_name() {
        return edt_item_name.getText().toString();}

    private String getF_edt_item_alternate_name() {
        return edt_item_alternate_name.getText().toString();
    }

    private String getF_radio_type_set() {
        return (radio_type_set.isChecked() ? "1" : "0");
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
        return radio_used_department.isChecked() ? "1" : "0";
    }

    private String getF_txt_on_department() {
        return isNull(txt_department.getContentDescription());
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

    // =============================================================================================

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

                post_data += "&p_itemcode="+ getF_edt_item_code();
                post_data += "&p_itemname="+ getF_edt_item_name();
                post_data += "&p_Alternatename="+ getF_edt_item_alternate_name();
                post_data += "&p_Barcode=" + getF_edt_item_code();
                post_data += "&p_IsSet=" + getF_radio_type_set();

                post_data += "&p_IsReuse=" + getF_radio_reused();
                post_data += "&p_IsNormal=" + getF_radio_used();
                post_data += "&p_itemtypeID="+ getF_txt_item_type();
                post_data += "&p_UnitID="+ getF_txt_unit();
                post_data += "&p_IsSpecial="+ getF_txt_special();

                post_data += "&p_DepartmentID="+ getF_txt_on_department();
                post_data += "&p_ShelfLifeID=1";
                post_data += "&p_Picture=" + ImageName;
                post_data += "&p_SetCount="+ getF_edt_set_count();
                post_data += "&p_PackingMatID="+ getF_txt_packingmat();

                post_data += "&p_CostPrice="+ getF_edt_cost_price();
                post_data += "&p_SalePrice="+ getF_edt_sale_price();
                post_data += "&p_UsagePrice="+ getF_edt_usage_price();
                post_data += "&p_SterileProcessID="+ getF_txt_sterileprocess();
                post_data += "&p_WashMachineID=1";

                post_data += "&p_WashProcessID="+ getF_txt_washingprocess();
                post_data += "&p_SupllierID="+ getF_txt_supplier();
                post_data += "&p_FactID="+ getF_txt_manufact();
                post_data += "&p_LabelID="+ getF_txt_label();
                post_data += "&p_Minimum="+ getF_edt_minimum();

                post_data += "&p_Maximum="+ getF_edt_maximum();
                post_data += "&p_weight=1";
                post_data += "&p_itemtypeID="+ getF_txt_item_type();
                post_data += "&p_no_wash=" + ( chkNoWash.isChecked() ? "1" : "0") ;



            }else if(type.equals("display")) {

                url_ = Url.URL + "cssd_display_item.php";
                String ITEM_ID = params[1];
                post_data += "&p_filter_id=" + ITEM_ID;

            }else if(type.equals("display_item_set")) {

                url_ = Url.URL + "cssd_display_item_set.php";
                String ITEM_ID = params[1];
                post_data += "&p_itemcode=" + ITEM_ID;

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
                        IS_SET = list.get(ix).isSet();
                        ImageName = list.get(ix).getPicture();


                        radio_type_one.setChecked(!list.get(ix).isSet().equals("0"));
                        radio_type_set.setChecked(list.get(ix).isSet().equals("0"));


                        chkIsSpecial.setEnabled(!list.get(ix).isSet().equals("0"));
                        chkIsSpecial.setChecked(list.get(ix).isSpecial());

                        chkNoWash.setChecked(list.get(ix).getNoWash().equals("1"));

                        edt_item_code.setText(list.get(ix).getItemcode());
                        edt_item_name.setText(list.get(ix).getItemname());
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
                        txt_supplier.setText(list.get(ix).getSpecialName());
                        txt_packingmat.setText(list.get(ix).getPackingMat());
                        txt_manufact.setText(list.get(ix).getManuFact());
                        txt_washingprocess.setText(list.get(ix).getWashingProcess());
                        txt_label.setText(list.get(ix).getLabelName());
                        txt_sterileprocess.setText(list.get(ix).getSterileName());
                        txt_unit.setText(list.get(ix).getUnitName());
                        txt_department.setText(list.get(ix).getDepName());

                        txt_item_type.setContentDescription(list.get(ix).getItemtypeID());
                        txt_special.setContentDescription(list.get(ix).getSpecialID());
                        txt_supplier.setContentDescription(list.get(ix).getSupllierID());
                        txt_packingmat.setContentDescription(list.get(ix).getPackingMatID());
                        txt_manufact.setContentDescription(list.get(ix).getFactID());
                        txt_washingprocess.setContentDescription(list.get(ix).getWashProcessID());
                        txt_label.setContentDescription(list.get(ix).getLabelID());
                        txt_sterileprocess.setContentDescription(list.get(ix).getSterileProcessID());
                        txt_unit.setContentDescription(list.get(ix).getUnitID());
                        txt_department.setContentDescription(list.get(ix).getDepartmentID());

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



                        //Image
                        try {
                            URL url = new URL(Url.getImageURL() + list.get(ix).getPicture());
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            img_item.setImageBitmap(bmp);
                        }catch(Exception e){
                            e.printStackTrace();
                            //return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        //return;
                    }

                    // Display Item Detail (SET)
                    if(IS_SET.equals("1")) {
                        list_set_item.setAdapter(null);

                        BackgroundWorker backgroundWorker = new BackgroundWorker(Item.this);
                        backgroundWorker.execute("display_item_set", ITEM_ID);
                    }else{
                        list_set_item.setAdapter(null);
                    }

                }else if(TYPE.equals("display_item_set")){
                    try {
                        ArrayAdapter<ModelItemDetail> adapter = new ItemDetailAdapter(Item.this, getItemDetailModel());
                        list_set_item.setAdapter(adapter);
                    } catch (Exception e) {
                        list_set_item.setAdapter(null);
                        e.printStackTrace();
                    }
                }else if(TYPE.equals("delete")){
                    onClearForm();
                }else if(TYPE.equals("save")){
                    onClearForm();

                    Toast.makeText(Item.this, "à¸šà¸±à¸™à¸—à¸¶à¸à¸£à¸²à¸¢à¸à¸²à¸£à¸ªà¸³à¹€à¸£à¹‡à¸ˆ", Toast.LENGTH_SHORT).show();
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

                ////System.out.println("size() = " + size);
                ////System.out.println("data.size() = " + data.size());

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

                // //System.out.println("list = " + list.size());

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
                                    data.get(i + 13),
                                    data.get(i + 14),
                                    data.get(i + 15),
                                    data.get(i + 16)
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

        private ModelItemDetail getItemDegtail(int index,
                                               String ID,
                                               String itemcode,
                                               String itemname,
                                               String alternatename,
                                               String barcode,
                                               String setCount,
                                               String unitName,
                                               String ID_set,
                                               String itemDetailID,
                                               String qty,
                                               String itemcode_set,
                                               String itemname_set,
                                               String alternatename_set,
                                               String barcode_set,
                                               String Picture_set,
                                               String Picture_detail,
                                               String QtyAll) {
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
                    barcode_set,
                    Picture_set,
                    Picture_detail,
                    QtyAll
            );
        }
        // =========================================================================================

    }

}
