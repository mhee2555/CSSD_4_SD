package com.phc.cssd;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Create_UserSendActivity extends Activity {

    SearchableSpinner spinner_dept;

    EditText txt_fname;
    EditText txt_lname;

    Button button5;
    Button button1;

    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    ArrayAdapter<String> adapter_spinner;

    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    private ArrayList<String> array_deptsp = new ArrayList<String>();
    private ArrayList<String> list_sp = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_send);
        initialize();
        getdeptcreatesend("x");
    }

    public void initialize() {
        spinner_dept = (SearchableSpinner) findViewById(R.id.spinner_dept);
        txt_fname = (EditText) findViewById(R.id.txt_fname);
        txt_lname = (EditText) findViewById(R.id.txt_lname);
        button5 = (Button) findViewById(R.id.button5);
        button1 = (Button) findViewById(R.id.button1);
        spinner_dept.setTitle("เลือกแผนก");
        spinner_dept.setPositiveButton("ปิด");
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_fname.getText().toString().equals("") && !txt_lname.getText().toString().equals("")) {
                    if(!spinner_dept.getSelectedItem().equals("")){
                        register_insert(txt_fname.getText().toString(), txt_lname.getText().toString(), String.valueOf(spinner_dept.getSelectedItemPosition()));
                    }else{
                        Toast.makeText(Create_UserSendActivity.this,"กรุณาเลือกแผนก", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Create_UserSendActivity.this,"กรุณากรอกข้อมูลให้ถูกต้องและครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register_insert( String fname, String lname, String dept) {
        class register_insert extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            Toast.makeText(Create_UserSendActivity.this,"บันทึกสำเร็จ", Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(Create_UserSendActivity.this,"บันทึกไม่สำเร็จ", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fname",params[0]);
                data.put("lname",params[1]);
                data.put("dept",params[2]);
                String result = null;
                try {
                    result = ruc.sendPostRequest(Url.URL + "cssd_create_user_send.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        register_insert ru = new register_insert();
        ru.execute(fname,lname,dept);
    }

    public void getdeptcreatesend(String x) {
        class getdeptcreatesend extends AsyncTask<String, Void, String> {
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
                    adapter_spinner = new ArrayAdapter<String>(Create_UserSendActivity.this, android.R.layout.simple_spinner_dropdown_item, list_sp);
                    adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_dept.setAdapter(adapter_spinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("x", params[0]);
                String result = ruc.sendPostRequest(iFt.getdepartmentsp(), data);
                return result;
            }
        }
        getdeptcreatesend ru = new getdeptcreatesend();
        ru.execute(x);
    }
}
