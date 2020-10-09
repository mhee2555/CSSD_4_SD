package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CheckList extends Activity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    EditText etxt_qr;

    Button bt_cancel;
    Button bt_comfirm;

    RadioButton type1;
    RadioButton type2;

    CheckBox ch1,ch2,ch3;

    EditText num_round;

    String TypeItem;
    String TypeNum;
    String itemcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        byIntent();

        init();

        OnDisplay(itemcode);
    }

    private void byIntent(){
        Intent i = getIntent();
        itemcode = i.getStringExtra("itemcode");
    }

    public void init(){
        etxt_qr = (EditText) findViewById(R.id.etxt_qr);
        num_round = (EditText) findViewById(R.id.num_round);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_comfirm = (Button) findViewById(R.id.bt_comfirm);
        type1 = (RadioButton) findViewById(R.id.type1);
        type2 = (RadioButton) findViewById(R.id.type2);
        ch1 = (CheckBox) findViewById(R.id.ch1);
        ch2 = (CheckBox) findViewById(R.id.ch2);
        ch3 = (CheckBox) findViewById(R.id.ch3);

        type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(type1.isChecked()){
                    TypeNum = "1";
                    type1.setChecked(true);
                    type2.setChecked(false);
                }
            }
        });
        type2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(type2.isChecked()){
                    TypeNum = "0";
                    type2.setChecked(true);
                    type1.setChecked(false);
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpDateChecklist(itemcode);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void UpDateChecklist(final String itemcode) {
        class UpDateChecklist extends AsyncTask<String, Void, String> {
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
                        if (c.getString("finish").equals("true")){
                            Toast.makeText(CheckList.this,"บันทึกสำเร็จ",Toast.LENGTH_SHORT).show();
                            finish();
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
                data.put("TypeNum",TypeNum);
                data.put("num_round",num_round.getText().toString());
                if (ch1.isChecked() == true){
                    data.put("ch1","1");
                }else {
                    data.put("ch1","0");
                }
                if (ch2.isChecked() == true){
                    data.put("ch2","1");
                }else {
                    data.put("ch2","0");
                }
                if (ch3.isChecked() == true){
                    data.put("ch3","1");
                }else {
                    data.put("ch3","0");
                }
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_save_item_checklist.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        UpDateChecklist obj = new UpDateChecklist();
        obj.execute();
    }

    public void OnDisplay(final String itemcode) {
        class OnDisplay extends AsyncTask<String, Void, String> {
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
                        if (c.getString("IsCheckList").equals("1")){
                            type1.setChecked(true);
                            type2.setChecked(false);
                        }else if (c.getString("IsCheckList").equals("0")){
                            type1.setChecked(false);
                            type2.setChecked(true);
                        }else {
                            type1.setChecked(false);
                            type2.setChecked(false);
                        }

                        if (c.getString("IsInternalIndicatorCheck").equals("1")){
                            ch1.setChecked(true);
                        }

                        if (c.getString("IsFillterCheck").equals("1")){
                            ch2.setChecked(true);
                        }

                        if (c.getString("IsLabelingCheck").equals("1")){
                            ch3.setChecked(true);
                        }

                        num_round.setText(c.getString("IsRemarkRound"));
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
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_diaplay_item_checklist.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        OnDisplay obj = new OnDisplay();
        obj.execute();
    }

    @Override
    public void finish() {
        super.finish();
    }

//    public void Checkuser(String qr_code,String docno,String xsel) {
//        class Checkuser extends AsyncTask<String, Void, String> {
//            // ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
//            }
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                // loading.dismiss();
//                try {
//
//                    JSONObject jsonObj = new JSONObject(s);
//                    rs = jsonObj.getJSONArray(TAG_RESULTS);
//                    for(int i=0;i<rs.length();i++){
//                        JSONObject c = rs.getJSONObject(i);
//                        if(c.getString("check").equals("true")){
//                            //Toast.makeText(CheckQR_Approve.this, "บันทึกแล้ว", Toast.LENGTH_SHORT).show();
//                            check=c.getString("check");
//                            finish();
//                        }else{
//                            AlertDialog.Builder builder = new AlertDialog.Builder(CheckNoWashType.this);
//                            builder.setCancelable(true);
//                            builder.setTitle("แจ้งเตือน !!");
//                            builder.setMessage("ไม่พบรหัสผู้ใช้");
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                            startUserSession();
//                            etxt_qr.setText("");
//                        }
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("qr_code",params[0]);
//                data.put("docno",params[1]);
//                data.put("xsel",params[2]);
//                Log.d("xDocNo: ", data+"");
//                String result = httpConnect.sendPostRequest(getUrl.xUrl+"chk_qr/check_qr.php",data);
//                Log.d("result fully: ", result);
//                return  result;
//            }
//        }
//        Checkuser ru = new Checkuser();
//        ru.execute( qr_code,docno,xsel );
//    }
}