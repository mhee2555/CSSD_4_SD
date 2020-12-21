package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.data.Master;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CssdQr extends Activity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    private String data = "";
    private String filter = "";

    private TextView txt_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_qr);

        byIntent();

        byWidget();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        filter = intent.getStringExtra("filter");
    }

    private void byWidget() {
        txt_input = (EditText) findViewById(R.id.txt_input);

        txt_input.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            String input = txt_input.getText().toString();

                            onCheck(input);

                            return true;
                        default:
                            break;
                    }
                }


                return false;

            }


        });
    }

    // =============================================================================================
    // Check User
    // =============================================================================================

    private void onCheck(final String qr){
        class CheckUser extends AsyncTask<String, Void, String> {

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

                        if(c.getString("result").equals("A")) {
                            if (data.equals("Admin")){
                                if (c.getString("data").equals("1")){
                                    Intent intent = new Intent();
                                    intent.putExtra("RETURN_DATA", c.getString("data"));
                                    intent.putExtra("RETURN_VALUE", c.getString("value"));
                                    setResult(1111, intent);
                                    finish();
                                }else {
                                    Toast.makeText(CssdQr.this, "สิทธิ์ผู้ใช้งานไม่สามารถเข้าถึงส่วนนี้ได้ !!", Toast.LENGTH_SHORT).show();
                                    txt_input.setText("");
                                    txt_input.requestFocus();
                                }
                            }else if (data.equals("Admin1")){
                                if (c.getString("data").equals("1")){
                                    Intent intent = new Intent();
                                    intent.putExtra("RETURN_DATA", c.getString("data"));
                                    intent.putExtra("RETURN_VALUE", c.getString("value"));
                                    intent.putExtra("RETURN_EM", qr);
                                    setResult(1112, intent);
                                    finish();
                                }else {
                                    Toast.makeText(CssdQr.this, "สิทธิ์ผู้ใช้งานไม่สามารถเข้าถึงส่วนนี้ได้ !!", Toast.LENGTH_SHORT).show();
                                    txt_input.setText("");
                                    txt_input.requestFocus();
                                }
                            } else {
                                Intent intent = new Intent();
                                intent.putExtra("RETURN_DATA", c.getString("data"));
                                intent.putExtra("RETURN_VALUE", c.getString("value"));
                                setResult(Master.getResult(data), intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(CssdQr.this, "ไม่พบข้อมูล !!", Toast.LENGTH_SHORT).show();
                            txt_input.setText("");
                            txt_input.requestFocus();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> d = new HashMap<String,String>();

                String result = null;

                String file = "";

                if(Master.s_wash_type.equals(data)){
                    file = "cssd_check_data_by_qr.php";

                    d.put("p_data", data);
                    d.put("p_filter", filter);
                    d.put("p_qr", qr);
                }else {
                    file = "cssd_check_admin.php";
                    d.put("p_data", data);
                    d.put("p_qr", qr);
                }

                try {
                    result = httpConnect.sendPostRequest(Url.URL + file, d);
                    Log.d("KDJDLJD",d+"");
                    Log.d("KDJDLJD",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }


            // =========================================================================================
        }

        CheckUser obj = new CheckUser();
        obj.execute();
    }



}
