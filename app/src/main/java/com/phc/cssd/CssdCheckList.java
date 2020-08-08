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
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

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


    // Intent
    private String userid = null;
    private String B_ID = null;
    private boolean IsAdmin = false;
    private boolean Is_ById = false;
    private String ID = null;
    private String UsageCode= null;

    String condition1 = "";
    String condition2 = "";
    String condition3 = "";
    String condition4 = "";

    // Widget
    private boolean DIALOG_ACTIVE = false;
    private ListView list_check;
    private ImageView imv_print;
    private ImageView imageBack;
    private ImageView imv_new;
    private EditText edt_usage_code;
    private TextView txt_packer;
    private TextView txt_item_name;
    private TextView txt_item_detail;

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

        list_check = (ListView) findViewById(R.id.list_check);

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
                            checkInput(edt_usage_code.getText().toString());
                            CheckDialog(edt_usage_code.getText().toString());
                            DIALOG_ACTIVE = true;
                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        imv_print.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPrint();
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
                    //Set All Check
                    for (int i = 0; i < MODEL_CHECK_LIST.size(); i++) {
                        MODEL_CHECK_LIST.get(i).setCheck(checkbox.isChecked());
                    }

                    // Display
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
                    if (DIALOG_ACTIVE == true){
                        if (!condition1.equals("0") || !condition2.equals("0") || !condition3.equals("0") || !condition4.equals("0")){
                            Intent intent = new Intent(CssdCheckList.this, dialog_check_usage_count.class);
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

        ID = null;
        UsageCode= null;

        img_item_all.setImageResource(R.drawable.ic_preview);
        img_item.setImageResource(R.drawable.ic_preview);

        txt_packer.setText("ผู้ห่อ : -");
        txt_packer.setContentDescription("");

        txt_item_name.setText("ชื่อเซ็ท : -");
        txt_item_detail.setText("รายการในเซ็ท 0 รายการ   จำนวนทั้งหมด 0 ชิ้น");

        checkbox.setChecked(false);

        list_check.setAdapter(null);

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

                if (!MODEL_CHECK_LIST.get(i).isCheck()) {
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
                    int sum_qty = 0;

                    List<ModelCheckList> list = new ArrayList<>();

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {

                            usage_item_code = c.getString("usage_item_code");
                            usage_item_name = c.getString("usage_item_name");
                            img_set = c.getString("Picture_set");
                            sum_qty += c.getInt("Qty");

                            ID = c.getString("ID");

                            System.out.println();

                            list.add(
                                    new ModelCheckList(
                                            c.getString("ID"),
                                            c.getString("Send_ID"),
                                            c.getString("itemcode"),
                                            c.getString("itemname"),
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
                        }
                    }

                    // Model
                    MODEL_CHECK_LIST = list;

                    try {
                        ArrayAdapter<ModelCheckList> adapter;

                        adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                        list_check.setAdapter(adapter);

                        txt_item_name.setText("ชื่อเซ็ท : " + usage_item_code + " - " + usage_item_name);
                        txt_item_detail.setText("รายการในเซ็ท " + MODEL_CHECK_LIST.size() + " รายการ   จำนวนทั้งหมด " + sum_qty + " ชิ้น");

                        try {
                            URL url = new URL(Url.getImageURL() + img_set);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            img_item_all.setImageBitmap(bmp);
                        }catch(Exception e){
                            img_item_all.setImageResource(R.drawable.ic_preview);
                        }

                    } catch (Exception e) {
                        list_check.setAdapter(null);
                        e.printStackTrace();
                    }

                    String packer_code = (String) txt_packer.getContentDescription();

                    System.out.println("packer_code = " + packer_code );
                    System.out.println("packer_text = " + txt_packer.getText() );

                    // Check Employee
                    if(ID != null && packer_code != null && !packer_code.equals("")){
                        updatePacker(ID, packer_code);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

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

            displayCheckList();

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

                    // Set
                    MODEL_CHECK_LIST.get(i).setCheck(true);

                    // Display
                    ArrayAdapter<ModelCheckList> adapter;
                    adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                    list_check.setAdapter(adapter);

                    try {
                        /*
                        URL url = new URL(Url.getImageURL() + MODEL_CHECK_LIST.get(i).getPicture_set());
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        img_item_all.setImageBitmap(bmp);
                         */

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
}