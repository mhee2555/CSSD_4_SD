package com.phc.cssd;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.R;
import com.phc.cssd.adapter.CssdPreviewItemSterile_List_ItemSet_Adapter;
import com.phc.cssd.adapter.ListStockLinenDetailAdapter;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelLinenDetail;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dialog_linen_detail extends Activity {

    Button back,save;
    EditText search_name,search_scan;
    CheckBox chk_all;
    ListView item;

    String DelDocNo;
    String EmpCode = "";
    String CheckAll = "0";
    String x = "0";

    Intent intent;

    HashMap<String, String> DelAlldata = new HashMap<String,String>();
    getUrl iFt = new getUrl();

    public ArrayList<String> DelRowId = new ArrayList<String>();
    private JSONArray rs = null;
    private String TAG_RESULTS="result";
    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelLinenDetail> Model_Linen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_linen_detail);

        byIntent();

        initialize();

        getlistdata("","0");

    }

    public void byIntent() {
        intent = getIntent();
        EmpCode = intent.getStringExtra("EmpCode");
    }

    private void initialize(){
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt=0;
                for (String key : DelAlldata.keySet()) {
                    if (DelAlldata.get(key) == "0")
                        cnt++;
                }
                if (cnt != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(dialog_linen_detail.this);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("ต้องการเปลี่ยนรายการจัดเซ็ตใช่หรือไม่ ?");
                    builder.setPositiveButton("ยืนยัน",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (String key : DelAlldata.keySet()) {
                                        if (DelAlldata.get(key) == "0")
                                            DelRowId.add(key);
                                    }
                                    SaveLinen(String.valueOf(DelRowId));
                                    DelRowId.clear();
                                    DelAlldata.clear();
                                }
                            });
                    builder.setNegativeButton("ไม่ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(dialog_linen_detail.this, "กรุณาเลือกอย่างน้อย 1 รายการ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        search_name = (EditText) findViewById(R.id.search_name);
        search_name.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getlistdata(search_name.getText().toString(),"0");
            }
        });
        search_scan = (EditText) findViewById(R.id.search_scan);
        search_scan.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            getlistdata(search_scan.getText().toString(),"1");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        item = (ListView) findViewById(R.id.item);
        chk_all = (CheckBox) findViewById(R.id.chk_all);
        chk_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (int a = 0 ; a < Model_Linen.size() ; a ++){
                        Model_Linen.get(a).setChk("1");
                        ArrayAdapter<ModelLinenDetail> adapter;
                        adapter = new ListStockLinenDetailAdapter(dialog_linen_detail.this, Model_Linen);
                        item.setAdapter(adapter);
                    }
                }else {
                    DelAll1();
                    for (int a = 0 ; a < Model_Linen.size() ; a ++){
                        Model_Linen.get(a).setChk("0");
                        ArrayAdapter<ModelLinenDetail> adapter;
                        adapter = new ListStockLinenDetailAdapter(dialog_linen_detail.this, Model_Linen);
                        item.setAdapter(adapter);
                    }
                }
            }
        });
    }

    public void getlistdata(final String key,final String type) {
        class getlistdata extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_linen_detail.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setTitle(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    List<ModelLinenDetail> list = new ArrayList<>();
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("itemname").equals("")){
                            if (type.equals("0")){
                                list.add(
                                        get(
                                                c.getString("itemname"),
                                                c.getString("UsageCode"),
                                                c.getString("PackDate"),
                                                c.getString("ExpireDate"),
                                                c.getString("date"),
                                                "0"
                                        )
                                );
                            }else {
                                list.add(
                                        get(
                                                c.getString("itemname"),
                                                c.getString("UsageCode"),
                                                c.getString("PackDate"),
                                                c.getString("ExpireDate"),
                                                c.getString("date"),
                                                "1"
                                        )
                                );
                            }
                        }else {
                            search_name.requestFocus();
                            search_scan.setText("");
                            search_scan.setHint("สแกนเช็ครายการ");
                            Toast.makeText(dialog_linen_detail.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Model_Linen = list;
                    ArrayAdapter<ModelLinenDetail> adapter;
                    adapter = new ListStockLinenDetailAdapter(dialog_linen_detail.this, Model_Linen);
                    item.setAdapter(adapter);
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
                data.put("key", key);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_linen_detail.php", data);
                    Log.d("BFGDH",result);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            private ModelLinenDetail get(String Itemname, String UsageCode, String PackDate, String ExpireDate, String Date, String Chk) {
                return new ModelLinenDetail(
                        Itemname,
                        UsageCode,
                        PackDate,
                        ExpireDate,
                        Date,
                        Chk);
            }
            // =========================================================================================
        }

        getlistdata obj = new getlistdata();
        obj.execute();
    }

    public void SaveLinen(final String Usagecode) {
        class SaveLinen extends AsyncTask<String, Void, String> {
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
                        if (c.getString("flag").equals("true")){
                            Toast.makeText(dialog_linen_detail.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                            getlistdata("","0");
                        }else {
                            Toast.makeText(dialog_linen_detail.this, "บันทึกไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            getlistdata("","0");
                        }
                    }
                } catch (JSONException e) {
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("Usagecode",Usagecode.trim());
                data.put("EmpCode",EmpCode);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_save_linen_to_sendsterile.php", data);
                Log.d("LFHLKD",data+"");
                Log.d("LFHLKD",result+"");

                return result;
            }
        }
        SaveLinen ru = new SaveLinen();
        ru.execute();
    }

    public void DelAll1 (){
        DelAlldata.clear();
        DelRowId.clear();
        x = "0";
    }

    public void DelAll (String DelDocno, String DelRowid, String Type){
        if (chk_all.isChecked()){
            if (!Type.equals("1")){
                DelDocNo = DelDocno;
                if(DelAlldata.get(DelRowid)=="0"){
                    x="0";
                }
                DelAlldata.put(DelRowid, x);
                Log.d("LFHLKD",DelAlldata+"");
            }else {
                DelAlldata.clear();
                DelRowId.clear();
                Log.d("LFHLKD",DelAlldata+"");
            }
        }else {
            if (!Type.equals("1")){
                DelDocNo = DelDocno;
                if(DelAlldata.get(DelRowid)=="0"){
                    x="1";
                }
                DelAlldata.put(DelRowid, x);
                Log.d("LFHLKD",DelAlldata+"");
            }else {
                DelAlldata.clear();
                DelRowId.clear();
                Log.d("LFHLKD",DelAlldata+"");
            }
        }
    }
}