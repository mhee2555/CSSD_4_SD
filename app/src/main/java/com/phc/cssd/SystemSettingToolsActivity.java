package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemSettingToolsActivity extends AppCompatActivity {

    ImageView imageBack;

    LinearLayout L1,L2,L3,L4,L5,L6,L7,L8;

    TextView no1,no2,no3,no4,no5,no6,no7,no8;
    TextView text1,text2,text3,text4,text5,text6,text7,text8;
    TextView text1_1,text2_2,text3_3,text4_4,text5_5,text6_6,text7_7,text8_8;

    Switch sw1,sw2,sw3,sw4,sw5,sw6,sw7,sw8;

    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private String TAG_RESULTS = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting_tools);
        getSupportActionBar().hide();
        initialize();
        PutDataCheck1();
        PutDataCheck2();
        PutDataCheck3();
        PutDataCheck4();
        PutDataCheck5();
        PutDataCheck6();
        PutDataCheck7();
        PutDataCheck8();
    }

    public void initialize() {
        imageBack = (ImageView) findViewById(R.id.imageBack);

        L1 = (LinearLayout) findViewById(R.id.L1);
        L2 = (LinearLayout) findViewById(R.id.L2);
        L3 = (LinearLayout) findViewById(R.id.L3);
        L4 = (LinearLayout) findViewById(R.id.L4);
        L5 = (LinearLayout) findViewById(R.id.L5);
        L6 = (LinearLayout) findViewById(R.id.L6);
        L7 = (LinearLayout) findViewById(R.id.L7);
        L8 = (LinearLayout) findViewById(R.id.L8);

        no1 = (TextView) findViewById(R.id.no1);
        no2 = (TextView) findViewById(R.id.no2);
        no3 = (TextView) findViewById(R.id.no3);
        no4 = (TextView) findViewById(R.id.no4);
        no5 = (TextView) findViewById(R.id.no5);
        no6 = (TextView) findViewById(R.id.no6);
        no7 = (TextView) findViewById(R.id.no7);
        no8 = (TextView) findViewById(R.id.no8);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);
        text7 = (TextView) findViewById(R.id.text7);
        text8 = (TextView) findViewById(R.id.text8);

        text1_1 = (TextView) findViewById(R.id.text1_1);
        text2_2 = (TextView) findViewById(R.id.text2_2);
        text3_3 = (TextView) findViewById(R.id.text3_3);
        text4_4 = (TextView) findViewById(R.id.text4_4);
        text5_5 = (TextView) findViewById(R.id.text5_5);
        text6_6 = (TextView) findViewById(R.id.text6_6);
        text7_7 = (TextView) findViewById(R.id.text7_7);
        text8_8 = (TextView) findViewById(R.id.text8_8);

        sw1 = (Switch) findViewById(R.id.sw1);
        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw1.isChecked()){
                    setboolean("1","1");
                }else{
                    setboolean("1","0");
                }
            }
        });
        sw2 = (Switch) findViewById(R.id.sw2);
        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw2.isChecked()){
                    setboolean("2","1");
                }else{
                    setboolean("2","0");
                }
            }
        });
        sw3 = (Switch) findViewById(R.id.sw3);
        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw3.isChecked()){
                    setboolean("3","1");
                }else{
                    setboolean("3","0");
                }
            }
        });
        sw4 = (Switch) findViewById(R.id.sw4);
        sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw4.isChecked()){
                    setboolean("4","1");
                }else{
                    setboolean("4","0");
                }
            }
        });
        sw5 = (Switch) findViewById(R.id.sw5);
        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw5.isChecked()){
                    setboolean("5","1");
                }else{
                    setboolean("5","0");
                }
            }
        });
        sw6 = (Switch) findViewById(R.id.sw6);
        sw6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw6.isChecked()){
                    setboolean("6","1");
                }else{
                    setboolean("6","0");
                }
            }
        });
        sw7 = (Switch) findViewById(R.id.sw7);
        sw7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw7.isChecked()){
                    setboolean("7","1");
                }else{
                    setboolean("7","0");
                }
            }
        });
        sw8 = (Switch) findViewById(R.id.sw8);
        sw8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw8.isChecked()){
                    setboolean("8","1");
                }else{
                    setboolean("8","0");
                }
            }
        });

        sw1.setVisibility(View.GONE);
        sw2.setVisibility(View.GONE);
        sw3.setVisibility(View.GONE);
        sw4.setVisibility(View.GONE);
        sw5.setVisibility(View.GONE);
        sw6.setVisibility(View.GONE);
        sw7.setVisibility(View.GONE);
        sw8.setVisibility(View.GONE);

        text1_1.setVisibility(View.GONE);
        text2_2.setVisibility(View.GONE);
        text3_3.setVisibility(View.GONE);
        text4_4.setVisibility(View.GONE);
        text5_5.setVisibility(View.GONE);
        text6_6.setVisibility(View.GONE);
        text7_7.setVisibility(View.GONE);
        text8_8.setVisibility(View.GONE);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setboolean(final String type,final String Set){
        class setboolean extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SystemSettingToolsActivity.this);
            // variable
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
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("finish").equals("true")){
                            Toast.makeText(SystemSettingToolsActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SystemSettingToolsActivity.this, "โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
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
                data.put("type", type);
                data.put("Set", Set);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "setting_setsetting.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        setboolean obj = new setboolean();
        obj.execute();
    }

    public void PutDataCheck1() {
        class PutDataCheck1 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            no1.setText("1.");
                            text1_1.setVisibility(View.VISIBLE);
                            sw1.setVisibility(View.VISIBLE);
                            text1.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw1.setChecked(false);
                            }else {
                                sw1.setChecked(true);
                            }
                        }else {
                            L1.setVisibility(View.GONE);
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
                data.put("data","1");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck1 obj = new PutDataCheck1();
        obj.execute();
    }

    public void PutDataCheck2() {
        class PutDataCheck2 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                no2.setText("1.");
                            }else {
                                no2.setText("2.");
                            }
                            text2_2.setVisibility(View.VISIBLE);
                            sw2.setVisibility(View.VISIBLE);
                            text2.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw2.setChecked(false);
                            }else {
                                sw2.setChecked(true);
                            }
                        }else {
                            L2.setVisibility(View.GONE);
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
                data.put("data","2");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck2 obj = new PutDataCheck2();
        obj.execute();
    }

    public void PutDataCheck3() {
        class PutDataCheck3 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    no3.setText("1.");
                                }else {
                                    no3.setText("2.");
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    no3.setText("2.");
                                }else {
                                    no3.setText("3.");
                                }
                            }
                            text3_3.setVisibility(View.VISIBLE);
                            sw3.setVisibility(View.VISIBLE);
                            text3.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw3.setChecked(false);
                            }else {
                                sw3.setChecked(true);
                            }
                        }else {
                            L3.setVisibility(View.GONE);
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
                data.put("data","3");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck3 obj = new PutDataCheck3();
        obj.execute();
    }

    public void PutDataCheck4() {
        class PutDataCheck4 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        no4.setText("1.");
                                    }else {
                                        no4.setText("2.");
                                    }
                                }else {
                                    if (L1.getVisibility() == View.GONE){
                                        no4.setText("2.");
                                    }else {
                                        no4.setText("3.");
                                    }
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        no4.setText("2.");
                                    }else {
                                        no4.setText("3.");
                                    }
                                }else {
                                    no4.setText("4.");
                                }
                            }
                            text4_4.setVisibility(View.VISIBLE);
                            sw4.setVisibility(View.VISIBLE);
                            text4.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw4.setChecked(false);
                            }else {
                                sw4.setChecked(true);
                            }
                        }else {
                            L4.setVisibility(View.GONE);
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
                data.put("data","4");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck4 obj = new PutDataCheck4();
        obj.execute();
    }

    public void PutDataCheck5() {
        class PutDataCheck5 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            no5.setText("1.");
                                        }else {
                                            no5.setText("2.");
                                        }
                                    }else {
                                        no5.setText("3.");
                                    }
                                }else {
                                    no5.setText("4.");
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            no5.setText("2.");
                                        }else {
                                            no5.setText("3.");
                                        }
                                    }else {
                                        if (L4.getVisibility() == View.GONE){
                                            no5.setText("3.");
                                        }else {
                                            no5.setText("4.");
                                        }
                                    }
                                }else {
                                    if (L4.getVisibility() == View.GONE){
                                        no5.setText("4.");
                                    }else {
                                        no5.setText("5.");
                                    }
                                }
                            }
                            text5_5.setVisibility(View.VISIBLE);
                            sw5.setVisibility(View.VISIBLE);
                            text5.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw5.setChecked(false);
                            }else {
                                sw5.setChecked(true);
                            }
                        }else {
                            L5.setVisibility(View.GONE);
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
                data.put("data","5");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck5 obj = new PutDataCheck5();
        obj.execute();
    }

    public void PutDataCheck6() {
        class PutDataCheck6 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                no6.setText("1.");
                                            }else {
                                                no6.setText("2.");
                                            }
                                        }else {
                                            no6.setText("3.");
                                        }
                                    }else {
                                        no6.setText("4.");
                                    }
                                }else {
                                    if (L5.getVisibility() == View.GONE){
                                        no6.setText("3.");
                                    }else {
                                        no6.setText("5.");
                                    }
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                no6.setText("2.");
                                            }else {
                                                no6.setText("3.");
                                            }
                                        }else {
                                            no6.setText("4.");
                                        }
                                    }else {
                                        no6.setText("5.");
                                    }
                                }else {
                                    no6.setText("6.");
                                }
                            }
                            text6_6.setVisibility(View.VISIBLE);
                            sw6.setVisibility(View.VISIBLE);
                            text6.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw6.setChecked(false);
                            }else {
                                sw6.setChecked(true);
                            }
                        }else {
                            L6.setVisibility(View.GONE);
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
                data.put("data","6");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck6 obj = new PutDataCheck6();
        obj.execute();
    }

    public void PutDataCheck7() {
        class PutDataCheck7 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                               if (L6.getVisibility() == View.GONE){
                                                   no7.setText("1.");
                                               }else {
                                                   no7.setText("2.");
                                               }
                                            }else {
                                                no7.setText("3.");
                                            }
                                        }else {
                                            no7.setText("4.");
                                        }
                                    }else {
                                        no7.setText("5.");
                                    }
                                }else {
                                    no7.setText("6.");
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                               if (L6.getVisibility() == View.GONE){
                                                   no7.setText("2.");
                                               }else {
                                                   no7.setText("3.");
                                               }
                                            }else {
                                                no7.setText("4.");
                                            }
                                        }else {
                                            no7.setText("5.");
                                        }
                                    }else {
                                        if (L6.getVisibility() == View.GONE){
                                            no7.setText("4.");
                                        }else {
                                            no7.setText("6.");
                                        }
                                    }
                                }else {
                                    no7.setText("7.");
                                }
                            }
                            text7_7.setVisibility(View.VISIBLE);
                            sw7.setVisibility(View.VISIBLE);
                            text7.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw7.setChecked(false);
                            }else {
                                sw7.setChecked(true);
                            }
                        }else {
                            L7.setVisibility(View.GONE);
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
                data.put("data","7");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck7 obj = new PutDataCheck7();
        obj.execute();
    }

    public void PutDataCheck8() {
        class PutDataCheck8 extends AsyncTask<String, Void, String> {
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
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                if (L6.getVisibility() == View.GONE){
                                                    if (L7.getVisibility() == View.GONE){
                                                        no8.setText("1.");
                                                    }else {
                                                        no8.setText("2.");
                                                    }
                                                }else {
                                                    no8.setText("3.");
                                                }
                                            }else {
                                                no8.setText("4.");
                                            }
                                        }else {
                                            no8.setText("5.");
                                        }
                                    }else {
                                        no8.setText("6.");
                                    }
                                }else {
                                    if (L7.getVisibility() == View.GONE){
                                        no8.setText("4.");
                                    }else {
                                        no8.setText("7.");
                                    }
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                if (L6.getVisibility() == View.GONE){
                                                    if (L7.getVisibility() == View.GONE){
                                                        no8.setText("2.");
                                                    }else {
                                                        no8.setText("3.");
                                                    }
                                                }else {
                                                    no8.setText("4.");
                                                }
                                            }else {
                                                if (L7.getVisibility() == View.GONE){
                                                    no8.setText("4.");
                                                }else {
                                                    no8.setText("5.");
                                                }
                                            }
                                        }else {
                                            no8.setText("6.");
                                        }
                                    }else {
                                        no8.setText("7.");
                                    }
                                }else {
                                    no8.setText("8.");
                                }
                            }
                            text8_8.setVisibility(View.VISIBLE);
                            sw8.setVisibility(View.VISIBLE);
                            text8.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw8.setChecked(false);
                            }else {
                                sw8.setChecked(true);
                            }
                        }else {
                            L8.setVisibility(View.GONE);
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
                data.put("data","8");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck8 obj = new PutDataCheck8();
        obj.execute();
    }
}