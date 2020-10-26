package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.CheckListAdapter;
import com.phc.cssd.model.ModelCheckList;
import com.phc.cssd.model.ModelWashDetailForPrint;
import com.phc.cssd.print_sticker.PrintWash;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CssdCheckList extends Activity {

    // Http
    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    private int COUNT_PROCESS = 0;
    // Intent
    private String userid = null;
    private String B_ID = null;
    private boolean IsAdmin = false;
    private boolean Is_ById = false;
    private String ID = null;
    private String UsageCode = null;

    String condition1 = "";
    String condition2 = "";
    String condition3 = "";
    String condition4 = "";
    String condition5 = "";

    ImageView ch1_un,ch2_un,ch3_un;
    ImageView ch1,ch2,ch3;
    TextView test_ch1,test_ch2,test_ch3;

    // Widget
    private String chk = "0";
    private boolean DIALOG_ACTIVE = false;
    private ListView list_check;
    private ImageView imv_print;
    private ImageView imageBack;
    private ImageView imv_new;
    private EditText edt_usage_code;
    private TextView txt_packer;
    private TextView txt_usagecode_scan;
    private TextView txt_item_name;
    private TextView txt_item_detail;
    private String RETURN_VALUE = "";
    private String RETURN_ADMIN = "";
    private String RETURN_INCHARG = "";
    private String RETURN_ITEMCODE = "";
    private String RETURN_ITEMDETAIL = "";
    private String RETURN_ROWID = "";
    private String RETURN_TYPE = "";
    String IsSave = "0";

    private ImageView img_item;
    private ImageView img_item_all;
    private CheckBox checkbox;
    private LinearLayout west;
    private List<ModelCheckList> MODEL_CHECK_LIST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_check_list);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        byIntent();

        byWidget();

        byEvent();

        if(Is_ById) {
            displayCheckList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //displayCheckList();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
        IsAdmin = intent.getBooleanExtra("IsAdmin", false);
        ID = intent.getStringExtra("ID");
        Is_ById = intent.getBooleanExtra("Is_ById", false);
    }

    private void byWidget(){
        ch1_un = (ImageView ) findViewById(R.id.ch1_un);
        ch2_un = (ImageView ) findViewById(R.id.ch2_un);
        ch3_un = (ImageView ) findViewById(R.id.ch3_un);

        ch1 = (ImageView ) findViewById(R.id.ch1);
        ch2 = (ImageView ) findViewById(R.id.ch2);
        ch3 = (ImageView ) findViewById(R.id.ch3);

        test_ch1 = (TextView ) findViewById(R.id.test_ch1);
        test_ch2 = (TextView ) findViewById(R.id.test_ch2);
        test_ch3 = (TextView ) findViewById(R.id.test_ch3);

        ch1.setVisibility(View.GONE);
        ch2.setVisibility(View.GONE);
        ch3.setVisibility(View.GONE);

        ch1_un.setVisibility(View.GONE);
        ch2_un.setVisibility(View.GONE);
        ch3_un.setVisibility(View.GONE);

        test_ch1.setVisibility(View.GONE);
        test_ch2.setVisibility(View.GONE);
        test_ch3.setVisibility(View.GONE);

        ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch1_un.getVisibility() == View.GONE){
                    ch1_un.setVisibility(View.VISIBLE);
                    ch1.setVisibility(View.GONE);
                    for (int i = 0 ; i < MODEL_CHECK_LIST.size() ; i ++){
                        MODEL_CHECK_LIST.get(i).setInternal("0");
                        ArrayAdapter adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                        list_check.setAdapter(adapter);
                    }
                }
            }
        });

        ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch2_un.getVisibility() == View.GONE){
                    ch2_un.setVisibility(View.VISIBLE);
                    ch2.setVisibility(View.GONE);
                }
            }
        });

        ch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch3_un.getVisibility() == View.GONE){
                    ch3_un.setVisibility(View.VISIBLE);
                    ch3.setVisibility(View.GONE);
                }
            }
        });

        ch1_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch1.getVisibility() == View.GONE){
                    ch1.setVisibility(View.VISIBLE);
                    ch1_un.setVisibility(View.GONE);
                    for (int i = 0 ; i < MODEL_CHECK_LIST.size() ; i ++){
                        MODEL_CHECK_LIST.get(i).setInternal("1");
                        ArrayAdapter adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                        list_check.setAdapter(adapter);
                    }
                }
            }
        });

        ch2_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch2.getVisibility() == View.GONE){
                    ch2.setVisibility(View.VISIBLE);
                    ch2_un.setVisibility(View.GONE);
                }
            }
        });

        ch3_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch3.getVisibility() == View.GONE){
                    ch3.setVisibility(View.VISIBLE);
                    ch3_un.setVisibility(View.GONE);
                }
            }
        });

        list_check = (ListView) findViewById(R.id.list_check);
        list_check.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = list_check.getItemAtPosition(position);
                ModelCheckList ModelCheckList = ( ModelCheckList ) o;
                String img_set = ModelCheckList.getPicture_set();
                String img_detail = ModelCheckList.getPicture_detail();
                String getQtyItemDetail = ModelCheckList.getQtyItemDetail();
                String Itemname = ModelCheckList.getItemname();
                String Qty = ModelCheckList.getQty();
                onListClick(img_set,img_detail);
                if (!Itemname.equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    if (!getQtyItemDetail.equals("0")){
                        OpenDialog(Itemname,"1",Qty,getQtyItemDetail);
                    }
                }
            }
        });

        list_check.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v, final int position, long arg3) {
                Object o = list_check.getItemAtPosition(position);
                ModelCheckList ModelCheckList = ( ModelCheckList ) o;
                String NameType = ModelCheckList.getNameType();
                String Itemname = ModelCheckList.getItemname();
                final String img_set = ModelCheckList.getPicture_set();
                final String img_detail = ModelCheckList.getPicture_detail();
                final String Itemcode = ModelCheckList.getItemcode();
                final String Item_Detail_ID = ModelCheckList.getItem_Detail_ID();
                final String RowID = ModelCheckList.getRowID();

                if (!Itemname.equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    if (NameType.equals("")) {
                        return false;
                    } else {
                        AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdCheckList.this);
                        quitDialog.setTitle("แจ้งเตือน");
                        quitDialog.setMessage("คุณต้องการ Delete Remark / Admin Approve หรือไม่ !!");
                        quitDialog.setPositiveButton("Admin Approve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openQR("admin",Itemcode,Item_Detail_ID,RowID,"approve");
                                onListClick(img_set, img_detail);
                            }
                        });

                        quitDialog.setNegativeButton("Delete Remark", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openQR("admin",Itemcode,Item_Detail_ID,RowID,"delete");
                                onListClick(img_set, img_detail);
                            }
                        });
                        quitDialog.show();
                    }
                }

                return true;
            }
        });

        txt_usagecode_scan = (TextView) findViewById(R.id.txt_usagecode_scan);
        imv_print = (ImageView) findViewById(R.id.imv_print);

        img_item = (ImageView)findViewById(R.id.img_item);
        img_item_all = (ImageView)findViewById(R.id.img_item_all);
        imv_new = (ImageView)findViewById(R.id.imv_new);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        txt_packer = ( TextView ) findViewById(R.id.txt_packer);
        txt_item_name = ( TextView ) findViewById(R.id.txt_item_name);
        txt_item_detail = ( TextView ) findViewById(R.id.txt_item_detail);
        edt_usage_code = ( EditText ) findViewById(R.id.edt_usage_code);
        west = ( LinearLayout ) findViewById(R.id.west);

        checkbox = ( CheckBox ) findViewById(R.id.checkbox);

        // west.setVisibility(Is_ById ? View.GONE : View.VISIBLE);
        west.setVisibility(View.GONE);
        imv_new.setVisibility(Is_ById ? View.GONE : View.VISIBLE);
        //edt_usage_code.setShowSoftInputOnFocus(false);
    }

    private void byEvent(){
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdCheckList.this);
                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setIcon(R.drawable.pose_favicon_2x);
                quitDialog.setMessage("ค้นหา Usage Code เพื่อทำการเช็คอุปกรณ์ใหม่ ?");
                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearAll();
                        ReturnApprove();
                        COUNT_PROCESS = 0;
                    }
                });
                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                quitDialog.show();
            }
        });

        edt_usage_code.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Log.d("FKJDHJKDH",COUNT_PROCESS+"");
                            if (COUNT_PROCESS < 2){
                                checkInput(edt_usage_code.getText().toString());
                                DIALOG_ACTIVE = true;
                            }else {
                                if (edt_usage_code.getText().toString().equals("1") || edt_usage_code.getText().toString().equals("2") || edt_usage_code.getText().toString().equals("3")){
                                    if (edt_usage_code.getText().toString().equals("1")){
                                        if (ch1_un.getVisibility() == View.VISIBLE){
                                            ch1_un.setVisibility(View.GONE);
                                            ch1.setVisibility(View.VISIBLE);
                                            edt_usage_code.setText("");
                                            edt_usage_code.requestFocus();
                                            for (int i = 0 ; i < MODEL_CHECK_LIST.size() ; i ++){
                                                MODEL_CHECK_LIST.get(i).setInternal("1");
                                                ArrayAdapter adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                                                list_check.setAdapter(adapter);
                                            }
                                            return true;
                                        }
                                        edt_usage_code.setText("");
                                        edt_usage_code.requestFocus();
                                    }else if (edt_usage_code.getText().toString().equals("2")){
                                        if (ch2_un.getVisibility() == View.VISIBLE){
                                            ch2_un.setVisibility(View.GONE);
                                            ch2.setVisibility(View.VISIBLE);
                                            edt_usage_code.setText("");
                                            edt_usage_code.requestFocus();
                                            for (int i = 0 ; i < MODEL_CHECK_LIST.size() ; i ++){
                                                MODEL_CHECK_LIST.get(i).setInternal("1");
                                                ArrayAdapter adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                                                list_check.setAdapter(adapter);
                                            }
                                            return true;
                                        }
                                        edt_usage_code.setText("");
                                        edt_usage_code.requestFocus();
                                    }else if (edt_usage_code.getText().toString().equals("3")){
                                        if (ch3_un.getVisibility() == View.VISIBLE){
                                            ch3_un.setVisibility(View.GONE);
                                            ch3.setVisibility(View.VISIBLE);
                                            edt_usage_code.setText("");
                                            edt_usage_code.requestFocus();
                                            for (int i = 0 ; i < MODEL_CHECK_LIST.size() ; i ++){
                                                MODEL_CHECK_LIST.get(i).setInternal("1");
                                                ArrayAdapter adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                                                list_check.setAdapter(adapter);
                                            }
                                            return true;
                                        }
                                        edt_usage_code.setText("");
                                        edt_usage_code.requestFocus();
                                    }
                                    return true;
                                }else {
                                    if (COUNT_PROCESS > 2){
                                        for (int i = 0 ; i < MODEL_CHECK_LIST.size() ; i ++){
                                            MODEL_CHECK_LIST.get(i).getItemcode();
                                            String Itemcode;
                                            Itemcode = edt_usage_code.getText().toString().toUpperCase().substring(0,5);
                                            if (Itemcode.equals(MODEL_CHECK_LIST.get(i).getItemcode())){
                                                if (MODEL_CHECK_LIST.get(i).isCheck() == true){
                                                    chk = "2";
                                                }else {
                                                    MODEL_CHECK_LIST.get(i).setCheck(true);
                                                    ArrayAdapter adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                                                    list_check.setAdapter(adapter);
                                                    try {
                                                        URL url = new URL(Url.getImageURL() + Itemcode+"_pic1.PNG");
                                                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                                        img_item.setImageBitmap(bmp);
                                                    }catch(Exception e){
                                                        e.printStackTrace();
                                                        img_item.setImageResource(R.drawable.ic_preview);
                                                    }
                                                    chk = "1";
                                                }
                                            }
                                        }
                                        if (chk.equals("1")){
                                            Toast.makeText(CssdCheckList.this, "รายการถูกต้อง", Toast.LENGTH_SHORT).show();
                                            chk.equals("0");
                                            edt_usage_code.setText("");
                                            edt_usage_code.requestFocus();
                                        }else if (chk.equals("0")){
                                            Toast.makeText(CssdCheckList.this, "รายการไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                                            edt_usage_code.setText("");
                                            edt_usage_code.requestFocus();
                                        }else if (chk.equals("2")){
                                            Toast.makeText(CssdCheckList.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                                            edt_usage_code.setText("");
                                            edt_usage_code.requestFocus();
                                        }
                                        COUNT_PROCESS ++;
                                    }
                                }
                                return true;
                            }
                            return true;
                        default:
                            edt_usage_code.requestFocus();
                            break;
                    }
                }else {
                    edt_usage_code.requestFocus();
                    return true;
                }
                return false;
            }
        });


        imv_print.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckValidation();
                //onPrint();
            }
        });

        txt_packer.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                checkPacker("EM00002");
                return true;
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for (int i = 0; i < MODEL_CHECK_LIST.size(); i++) {
                        MODEL_CHECK_LIST.get(i).setCheck(checkbox.isChecked());
                    }
                    ArrayAdapter<ModelCheckList> adapter;
                    adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                    list_check.setAdapter(adapter);
                }catch (Exception e){

                }finally {
                    focus_();
                }
            }
        });
    }

    public void CheckDialog(final String Usagecode) {
        class CheckDialog extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(CssdCheckList.this);
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

                    String UsageItem = "";

                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        UsageCode = c.getString("UsageCode");
                        UsageItem = c.getString("UsageItem");
                        condition1 = c.getString("condition1");
                        condition2 = c.getString("condition2");
                        condition3 = c.getString("condition3");
                        condition4 = c.getString("condition4");
                        condition5 = c.getString("condition5");
                    }
                    if (DIALOG_ACTIVE == true){
                        if (!condition1.equals("0") || !condition2.equals("0") || !condition3.equals("0") || !condition4.equals("0") || !condition5.equals("0")){
                            Intent intent = new Intent(CssdCheckList.this, dialog_check_usage_count.class);
                            intent.putExtra("condition1",condition1);
                            intent.putExtra("condition2",condition2);
                            intent.putExtra("condition3",condition3);
                            intent.putExtra("condition4",condition4);
                            intent.putExtra("condition5",condition5);
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
                data.put("Type","1");
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
        CheckDialog obj = new CheckDialog();
        obj.execute();
    }

    private void clearAll(){
        COUNT_PROCESS = 0;
        UsageCode= null;
        img_item_all.setImageResource(R.drawable.ic_preview);
        img_item.setImageResource(R.drawable.ic_preview);
        txt_packer.setText("ผู้ห่อ : -");
        txt_packer.setContentDescription("");
        txt_item_name.setText("ชื่อเซ็ท : -");
        txt_usagecode_scan.setText("");
        txt_item_detail.setText("0 รายการ / 0 ชิ้น");
        checkbox.setChecked(false);
        list_check.setAdapter(null);
        ch1_un.setVisibility(View.GONE);
        ch2_un.setVisibility(View.GONE);
        ch3_un.setVisibility(View.GONE);
        ch1.setVisibility(View.GONE);
        ch2.setVisibility(View.GONE);
        ch3.setVisibility(View.GONE);
        test_ch1.setVisibility(View.GONE);
        test_ch2.setVisibility(View.GONE);
        test_ch3.setVisibility(View.GONE);
    }

    public void openQR(final String admin,final String itemcode,final String itemdetail,final String RowID,final String type){
        Intent i = new Intent(CssdCheckList.this, CssdQrUser.class);
        i.putExtra("data", admin);
        i.putExtra("itemcode", itemcode);
        i.putExtra("itemdetail", itemdetail);
        i.putExtra("RowID", RowID);
        i.putExtra("type", type);
        startActivityForResult(i,1006);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        try {
            RETURN_VALUE = data.getStringExtra("RETURN_VALUE");
            RETURN_ADMIN = data.getStringExtra("RETURN_ADMIN");
            RETURN_INCHARG = data.getStringExtra("RETURN_INCHARG");
            RETURN_ITEMCODE = data.getStringExtra("RETURN_ITEMCODE");
            RETURN_ITEMDETAIL = data.getStringExtra("RETURN_ITEMDETAIL");
            RETURN_ROWID = data.getStringExtra("RETURN_ROWID");
            RETURN_TYPE = data.getStringExtra("RETURN_TYPE");
            if (resultCode == 1006) {
                if (RETURN_ADMIN.equals("1")){
                    CancelRemark(RETURN_ITEMCODE,RETURN_ITEMDETAIL,RETURN_ROWID,RETURN_TYPE);
                }else {
                    if (RETURN_INCHARG.equals("2")){
                        CancelRemark(RETURN_ITEMCODE,RETURN_ITEMDETAIL,RETURN_ROWID,RETURN_TYPE);
                    }else {
                        Toast.makeText(CssdCheckList.this, "ผู้ใช้ทั่วไปไม่สามารถยกเลิก Remark ได้ !!", Toast.LENGTH_SHORT).show();
                    }
                }
            }else if (resultCode == 1005){
                displayCheckList();
                //CheckValidation_Master();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void CancelRemark(final String itemcode,final String itemdetail,final String RowId,final String type) {
        class CancelRemark extends AsyncTask<String, Void, String> {
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
                        if (c.getString("finish").equals("truedelete")){
                            displayCheckList();
                            //CheckValidation_Master();
                            Toast.makeText(CssdCheckList.this, "ยกเลิก Remark สำเร็จ", Toast.LENGTH_SHORT).show();
                            IsSave = "1";
                        }else if (c.getString("finish").equals("falsedelete")){
                            displayCheckList();
                            //CheckValidation_Master();
                            Toast.makeText(CssdCheckList.this, "ยกเลิก Remark ไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            IsSave = "0";
                        }else if (c.getString("finish").equals("trueapprove")){
                            displayCheckList();
                            //CheckValidation_Master();
                            Toast.makeText(CssdCheckList.this, "Approve Remark สำเร็จ", Toast.LENGTH_SHORT).show();
                            IsSave = "1";
                        }else if (c.getString("finish").equals("falseapprove")){
                            displayCheckList();
                            //CheckValidation_Master();
                            Toast.makeText(CssdCheckList.this, "Approve Remark ไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            IsSave = "0";
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
                data.put("itemcode",itemcode);
                data.put("itemdetail",itemdetail);
                data.put("RowId",RowId);
                data.put("page","checklist");
                data.put("type",type);
                data.put("EmpCode",RETURN_VALUE);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_delete_remark_send.php", data);
                    Log.d("DHKDHKD",data+"");
                    Log.d("DHKDHKD",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CancelRemark obj = new CancelRemark();
        obj.execute();
    }

    private void ReturnApprove(){
        class ReturnApprove extends AsyncTask<String, Void, String> {

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
                        ID = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID", ID);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_wash_detail_for_print.php", data);
                    Log.d("DLJLCJL",data+"");
                    Log.d("DLJLCJL",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ReturnApprove obj = new ReturnApprove();
        obj.execute();
    }

    public void onListClick(String img_set, String img_detail){
        try {
            URL url = new URL(Url.getImageURL() + img_set);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            img_item_all.setImageBitmap(bmp);
            URL url_ = new URL(Url.getImageURL() + img_detail);
            Bitmap bmp_ = BitmapFactory.decodeStream(url_.openConnection().getInputStream());
            img_item.setImageBitmap(bmp_);
        }catch(Exception e){
            img_item_all.setImageResource(R.drawable.ic_preview);
            img_item.setImageResource(R.drawable.ic_preview);
        }
    }

    private void onPrint(){

        boolean IsCheck = true;
        String MsgCheck = "";

        try {

            // Check Packer
            if (txt_packer.getContentDescription() == null || txt_packer.getContentDescription().toString().equals("")) {
                Toast.makeText(CssdCheckList.this, "ยังไม่ได้เลือกผู้ห่อ !!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check List
            for(int i=0;i<MODEL_CHECK_LIST.size();i++){

                Log.d("VDLJVLDJ",MODEL_CHECK_LIST.get(i).getCheck()+" : "+MODEL_CHECK_LIST.get(i).getItemname());

                if (!MODEL_CHECK_LIST.get(i).getCheck() == true && !MODEL_CHECK_LIST.get(i).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")) {
                    MsgCheck = "มีบางรายการยังไม่ได้ถูกเช็ค !!";
                    IsCheck = false;
                    break;
                }

                if (!MODEL_CHECK_LIST.get(i).getNameType().equals("")) {
                    MsgCheck = "มีบางรายการถูก Remark ไว้ !!";
                    IsCheck = false;
                    break;
                }
            }

            if(!IsCheck){
                Toast.makeText(CssdCheckList.this, MsgCheck, Toast.LENGTH_SHORT).show();
                return;
            }

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

                                // Print
                                PrintWash p = new PrintWash();
                                String p_data = p.print(CssdCheckList.this, c.getInt("CaseLabel"), c.getString("PrinterIP"), list);

                                // Update Print Status
                                updatePrintStatus(p_data);

                                if(c.getString("IsCheckList").equals("1")){
                                    callCheckListPaper(ID);
                                }

                                if(Is_ById) {
                                    finish();
                                }else{
                                    clearAll();
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }finally {
                        focus();
                    }

                }

                @SuppressLint("WrongThread")
                @Override
                protected String doInBackground(String... params) {
                    HashMap<String, String> data = new HashMap<String,String>();
                    data.put("ID", ID);
                    data.put("usagecode",txt_usagecode_scan.getText().toString());
                    String result = null;

                    try {
                        result = httpConnect.sendPostRequest(Url.URL + "cssd_select_wash_detail_for_print.php", data);
                        Log.d("V:K:DD",data+"");
                        Log.d("V:K:DD",result);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    return result;
                }

                // =========================================================================================
            }

            Print obj = new Print();
            obj.execute();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!IsCheck)
                focus();
        }
    }

    public void updatePrintStatus(final String p_data) {

        class UpdatePrintStatus extends AsyncTask<String, Void, String> {

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

                data.put("p_data", p_data.substring(0, p_data.length()-1));

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_detail_print_status.php", data);

                return result;
            }

            // =========================================================================================
        }

        UpdatePrintStatus obj = new UpdatePrintStatus();
        obj.execute();
    }

    private void callCheckListPaper(String WashID) {
        String url = Url.URL + "report/Report_checklist_sterile.php?WashID=" + WashID;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void displayCheckList() {
        class DisplayWashDetail extends AsyncTask<String, Void, String> {
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
                    String usage_item_code = "";
                    String usage_item_name = "";
                    String img_set = "";
                    String resultdata = "";
                    int sum_qty = 0;
                    int sum_qty_Detail = 0;
                    int Total = 0;
                    int Total1 = 0;
                    int cnt = 0;
                    List<ModelCheckList> list = new ArrayList<>();
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("result").equals("A")) {
                            usage_item_code = c.getString("usage_item_code");
                            usage_item_name = c.getString("usage_item_name");
                            img_set = c.getString("Picture_set");
                            sum_qty += c.getInt("Qty");
                            sum_qty_Detail += c.getInt("QtyItemDetail");
                            resultdata = c.getString("result");
                            ID = c.getString("ID");
                            System.out.println();
                            list.add(
                                    new ModelCheckList(
                                            c.getString("configuration"),
                                            c.getString("IsRemarkRound_RemarlAdmin"),
                                            c.getString("IsRemarkRound"),
                                            c.getString("RemarkDocNo"),
                                            c.getString("IsPicture"),
                                            c.getString("QtyItemDetail"),
                                            c.getString("AdminApprove"),
                                            c.getString("RowID"),
                                            c.getString("ID"),
                                            c.getString("Send_ID"),
                                            c.getString("itemcode"),
                                            c.getString("itemname"),
                                            c.getString("Item_Detail_ID"),
                                            c.getString("Qty"),
                                            c.getString("AdminRemark"),
                                            c.getString("DateRemark"),
                                            c.getString("Remark"),
                                            c.getString("NameType"),
                                            img_set,
                                            c.getString("Picture_detail"),
                                            usage_item_code,
                                            usage_item_name,
                                            c.getString("NameType").equals("-")
                                    )
                            );
                            Total = Integer.parseInt(c.getString("Qty"));
                            Total1 = Integer.parseInt(c.getString("QtyItemDetail"));
                            if (Total == Total1){

                            }else {
                                cnt++;
                            }
                        }
                    }
                    // Model
                    MODEL_CHECK_LIST = list;
                    try {
                        ArrayAdapter<ModelCheckList> adapter;
                        adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                        list_check.setAdapter(adapter);
                        if (resultdata.equals("A")){
                            CheckDialog(edt_usage_code.getText().toString());
                        }
                        Log.d("KDNCVJHD",COUNT_PROCESS+"");
                        if (COUNT_PROCESS == 1){
                            ch1_un.setVisibility(View.VISIBLE);
                            ch2_un.setVisibility(View.VISIBLE);
                            ch3_un.setVisibility(View.VISIBLE);
                            test_ch1.setVisibility(View.VISIBLE);
                            test_ch2.setVisibility(View.VISIBLE);
                            test_ch3.setVisibility(View.VISIBLE);
                            ch1.setVisibility(View.GONE);
                            ch2.setVisibility(View.GONE);
                            ch3.setVisibility(View.GONE);
                        }

                        int Sum = sum_qty - sum_qty_Detail;
                        txt_item_name.setText("ชื่อเซ็ท : " + usage_item_code + " - " + usage_item_name);
                        txt_item_detail.setText("[ "+cnt+" รายการ  /  "+Sum +" ชิ้น ]");
                        try {
                            URL url = new URL(Url.getImageURL() + img_set);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            img_item_all.setImageBitmap(bmp);
                        }catch(Exception e){
                            img_item_all.setImageResource(R.drawable.ic_preview);
                        }
                    } catch (Exception e) {
                        Toast.makeText(CssdCheckList.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
                        COUNT_PROCESS = 1;
                        list_check.setAdapter(null);
                        e.printStackTrace();
                    }
                    String packer_code = (String) txt_packer.getContentDescription();
                    if(ID != null && packer_code != null && !packer_code.equals("")){
                        updatePacker(ID, packer_code);
                    }
                    COUNT_PROCESS = 3;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CssdCheckList.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
                    COUNT_PROCESS = 1;
                    list_check.setAdapter(null);
                    return;
                }finally {
                    focus();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                if(ID != null) {
                    data.put("ID", ID);
                }else if(UsageCode != null) {
                    data.put("p_usage_code", UsageCode);
                }
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_check_list.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        DisplayWashDetail obj = new DisplayWashDetail();
        obj.execute();
    }

    public void checkPacker(final String packer) {
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
                            txt_packer.setText("ผู้ห่อ : " + c.getString("packer"));
                            txt_packer.setContentDescription(c.getString("ID"));
                            COUNT_PROCESS = 1;
                        }else{
                            Toast.makeText(CssdCheckList.this, "ไม่พบรายชื่อพนักงาน โปรด Scan รหัสพนักงานก่อน !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    focus();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                if(ID != null)
                    data.put("ID", ID);
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
                return result;
            }
            // =========================================================================================
        }
        Check obj = new Check();
        obj.execute();
    }

    public void updatePacker(final String ID, final String p_user_id) {
        class Check extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                focus();
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID", ID);
                data.put("p_user_id", p_user_id);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_update_packer.php", data);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }

        Check obj = new Check();
        obj.execute();
    }

    private void checkInput(final String Input){
        String packer_code = (String) txt_packer.getContentDescription();
        // Check Employee
        if(packer_code == null || packer_code.equals("")){
            checkPacker(Input);
            return;
        }
        // Check Usage
        if(ID == null){
            UsageCode = Input;
            txt_usagecode_scan.setText(edt_usage_code.getText().toString().toUpperCase());
            displayCheckList();
            CheckValidation_Master();
            return;
        }
        // Check Item Detail
        boolean IsCheck = false;
        try {
            String code = Input.length() > 6 ? Input.substring(0, 5) : Input;
            for(int i=0;i<MODEL_CHECK_LIST.size();i++){
                //System.out.println(code + " = " + MODEL_CHECK_LIST.get(i).getItemcode());
                if (code.equals(MODEL_CHECK_LIST.get(i).getItemcode())) {
                    IsCheck = true;
                    if(MODEL_CHECK_LIST.get(i).isCheck()){
                        Toast.makeText(CssdCheckList.this, "รายการนี้ได้ทำการยิงเช็คแล้ว !!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    // Setimv_new
                    MODEL_CHECK_LIST.get(i).setCheck(true);
                    // Display
                    ArrayAdapter<ModelCheckList> adapter;
                    adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                    list_check.setAdapter(adapter);
                    try {
                        URL url_ = new URL(Url.getImageURL() + MODEL_CHECK_LIST.get(i).getPicture_detail());
                        Bitmap bmp_ = BitmapFactory.decodeStream(url_.openConnection().getInputStream());
                        img_item.setImageBitmap(bmp_);
                    }catch(Exception e){
                        img_item_all.setImageResource(R.drawable.ic_preview);
                        img_item.setImageResource(R.drawable.ic_preview);
                    }
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(IsCheck) {
                focus();
            }
        }
    }

    public void OpenDialog(final String Itemname ,final String type, final String Qty, final String Qty_save) {
        Intent intent = new Intent(CssdCheckList.this, dialog_remark_sendsterile.class);
        intent.putExtra("Itemname", Itemname);
        intent.putExtra("Usagecode", txt_usagecode_scan.getText().toString());
        intent.putExtra("DepID", "");
        intent.putExtra("DocNoSend", "");
        intent.putExtra("EmpCode",RETURN_VALUE);
        intent.putExtra("IsAdmin",IsAdmin);
        intent.putExtra("IsStatus","0");
        intent.putExtra("DocNo","");
        intent.putExtra("Type",type);
        intent.putExtra("Qty",Qty);
        intent.putExtra("Qty_save",Qty_save);
        intent.putExtra("context", String.valueOf(CssdCheckList.this));
        startActivityForResult(intent,1005);
    }

    private void focus(){
        checkbox.setChecked(false);
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                edt_usage_code.setText("");
                edt_usage_code.requestFocus();
            }
        }, 500);
    }

    private void focus_(){
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                edt_usage_code.setText("");
                edt_usage_code.requestFocus();
            }
        }, 500);
    }

    public void LoadImg(final String itemcode,final String sel,final String itemname,final String type) {
        if (type.equals("remark")){
            Intent intent = new Intent(CssdCheckList.this, dialog_Load_Img_Remark.class);
            intent.putExtra("itemcode", itemcode);
            intent.putExtra("usagecode", txt_usagecode_scan.getText().toString());
            intent.putExtra("itemname", itemname);
            intent.putExtra("sel",sel);
            intent.putExtra("type",type);
            startActivity(intent);
        }else {
            Intent intent = new Intent(CssdCheckList.this, dialog_Load_Img.class);
            intent.putExtra("itemcode", itemcode);
            intent.putExtra("usagecode", txt_usagecode_scan.getText().toString());
            intent.putExtra("itemname", itemname);
            intent.putExtra("sel",sel);
            intent.putExtra("type",type);
            startActivity(intent);
        }
    }

    public void CheckValidation_Master() {
        class CheckValidation_Master extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("flug").equals("true")){
                            if (c.getString("IsInternalIndicatorCheck").equals("1")){

                            }else {
                                test_ch1.setVisibility(View.GONE);
                                ch1_un.setVisibility(View.GONE);
                                ch1.setVisibility(View.GONE);
                            }

                            if (c.getString("IsFillterCheck").equals("1")){

                            }else {
                                test_ch2.setVisibility(View.GONE);
                                ch2_un.setVisibility(View.GONE);
                                ch2.setVisibility(View.GONE);
                            }

                            if (c.getString("IsLabelingCheck").equals("1")){

                            }else {
                                test_ch3.setVisibility(View.GONE);
                                ch3_un.setVisibility(View.GONE);
                                ch3.setVisibility(View.GONE);
                            }
                        }else {
                            test_ch1.setVisibility(View.GONE);
                            ch1_un.setVisibility(View.GONE);
                            ch1.setVisibility(View.GONE);
                            test_ch2.setVisibility(View.GONE);
                            ch2_un.setVisibility(View.GONE);
                            ch2.setVisibility(View.GONE);
                            test_ch3.setVisibility(View.GONE);
                            ch3_un.setVisibility(View.GONE);
                            ch3.setVisibility(View.GONE);
                            txt_usagecode_scan.setText("");
                            Toast.makeText(CssdCheckList.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
                            COUNT_PROCESS = 1;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("usagecode", txt_usagecode_scan.getText().toString());
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_validation.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }

        CheckValidation_Master obj = new CheckValidation_Master();
        obj.execute();
    }

    public void CheckValidation() {
        class CheckValidation extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    boolean Check1 = false;
                    boolean Check2 = false;
                    boolean Check3 = false;
                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("IsInternalIndicatorCheck").equals("1")){
                            if (ch1.getVisibility() == View.VISIBLE){
                                Check1 = true;
                            }else {
                                Check1 = false;
                            }
                        }else {
                            if (ch1.getVisibility() == View.GONE){
                                Check1 = true;
                            }else {
                                Check1 = false;
                            }
                        }

                        if (c.getString("IsFillterCheck").equals("1")){
                            if (ch2.getVisibility() == View.VISIBLE){
                                Check2 = true;
                            }else {
                                Check2 = false;
                            }
                        }else {
                            if (ch2.getVisibility() == View.GONE){
                                Check2 = true;
                            }else {
                                Check2 = false;
                            }
                        }

                        if (c.getString("IsLabelingCheck").equals("1")){
                            if (ch3.getVisibility() == View.VISIBLE){
                                Check3 = true;
                            }else {
                                Check3 = false;
                            }
                        }else {
                            if (ch3.getVisibility() == View.GONE){
                                Check3 = true;
                            }else {
                                Check3 = false;
                            }
                        }

                        if (Check1 == true){
                            if (c.getString("IsInternalIndicatorCheck").equals("1")){
                                Check1 = true;
                            }else {
                                Check1 = false;
                            }
                        }

                        if (Check2 == true){
                            if (c.getString("IsFillterCheck").equals("1")){
                                Check2 = true;
                            }else {
                                Check2 = false;
                            }
                        }

                        if (Check3 == true){
                            if (c.getString("IsLabelingCheck").equals("1")){
                                Check3 = true;
                            }else {
                                Check3 = false;
                            }
                        }

                        if (Check1 == false){
                            if (test_ch1.getVisibility() == View.GONE){
                                Check1 = true;
                            }
                        }

                        if (Check2 == false){
                            if (test_ch2.getVisibility() == View.GONE){
                                Check2 = true;
                            }
                        }

                        if (Check3 == false){
                            if (test_ch3.getVisibility() == View.GONE){
                                Check3 = true;
                            }
                        }
                    }

                    Log.d("FKJDHJKDH",Check1+"");
                    Log.d("FKJDHJKDH",Check2+"");
                    Log.d("FKJDHJKDH",Check3+"");

                    if (Check1 == true && Check2 == true && Check3 == true){
                        onPrint();
                    }else {
                        Toast.makeText(CssdCheckList.this, "กรุณาเช็ค / สแกน VALIDATION SHEET ให้ครบ !!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("usagecode", txt_usagecode_scan.getText().toString());
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_validation.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }

        CheckValidation obj = new CheckValidation();
        obj.execute();
    }
}