package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.phc.core.connect.HTTPConnect;

import org.json.JSONArray;

public class CheckList extends Activity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    EditText etxt_qr;

    Button bt_cancel;
    Button bt_comfirm;

    CheckBox type1;
    CheckBox type2;

    String TypeItem;
    String TypeNum;
    String Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        byIntent();

        init();

    }

    private void byIntent(){
        Intent i = getIntent();
        Type = i.getStringExtra("type");
    }

    public void init(){
        etxt_qr = (EditText) findViewById(R.id.etxt_qr);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);
        bt_comfirm = (Button) findViewById(R.id.bt_comfirm);
        type1 = (CheckBox) findViewById(R.id.type1);
        type2 = (CheckBox) findViewById(R.id.type2);


//        if (Type.equals("0")){
//            type1.setChecked(false);
//            type2.setChecked(false);
//        }else if (Type.equals("1")){
//            type1.setChecked(true);
//            type2.setChecked(false);
//        }else if (Type.equals("2")){
//            type1.setChecked(false);
//            type2.setChecked(true);
//        }

        type1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(type1.isChecked()){
                    TypeItem = "(เครื่องมือ)";
                    TypeNum = "1";
                    type1.setChecked(true);
                    type2.setChecked(false);
                }
            }
        });
        type2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(type2.isChecked()){
                    TypeItem = "(ผ้า)";
                    TypeNum = "1";
                    type2.setChecked(true);
                    type1.setChecked(false);
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3();
                finish();
            }
        });
        bt_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void b1() {
        Intent intent = new Intent();
        intent.putExtra("RETURN_DATA",TypeItem);
        setResult(205, intent);
    }

    public void b3() {
        Intent intent = new Intent();
        intent.putExtra("RETURN_DATA","");
        setResult(205, intent);
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