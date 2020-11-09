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

public class CssdQrUser extends Activity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    private String data_text = "";
    private String itemcode = "";
    private String itemdetail = "";
    private String RowID = "";
    private String type = "";
    private String xSel9 = "";
    private String xSel10 = "";

    private TextView txt_user_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_qr_user);

        byIntent();

        byWidget();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        data_text = intent.getStringExtra("data");
        itemcode = intent.getStringExtra("itemcode");
        itemdetail = intent.getStringExtra("itemdetail");
        RowID = intent.getStringExtra("RowID");
        type = intent.getStringExtra("type");
    }

    private void byWidget() {
        txt_user_code = (EditText) findViewById(R.id.txt_user_code);
        txt_user_code.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            String user_code = txt_user_code.getText().toString();
                            onCheckUser(user_code);
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
    private void onCheckUser(final String user_code){
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
                            if (data_text.equals("admin")) {
                                Intent intent = new Intent();
                                intent.putExtra("RETURN_DATA", c.getString("data"));
                                intent.putExtra("RETURN_VALUE", c.getString("value"));
                                intent.putExtra("RETURN_ADMIN", c.getString("admin"));
                                intent.putExtra("RETURN_INCHARG", c.getString("IsInCharg"));
                                intent.putExtra("RETURN_USER", c.getString("IsUser"));
                                intent.putExtra("RETURN_ITEMCODE", itemcode);
                                intent.putExtra("RETURN_ITEMDETAIL", itemdetail);
                                intent.putExtra("RETURN_ROWID", RowID);
                                intent.putExtra("RETURN_TYPE", type);
                                setResult(1006, intent);
                                finish();
                            } else if (data_text.equals("admin_save")){
                                Intent intent = new Intent();
                                intent.putExtra("RETURN_DATA", c.getString("data"));
                                intent.putExtra("RETURN_VALUE", c.getString("value"));
                                intent.putExtra("RETURN_ADMIN", c.getString("admin"));
                                intent.putExtra("RETURN_EMCODE", c.getString("EmpCode"));
                                intent.putExtra("RETURN_INCHARG", c.getString("IsInCharg"));
                                intent.putExtra("RETURN_USER", c.getString("IsUser"));
                                intent.putExtra("RETURN_ITEMCODE", itemcode);
                                intent.putExtra("RETURN_ITEMDETAIL", itemdetail);
                                intent.putExtra("RETURN_ROWID", RowID);
                                intent.putExtra("RETURN_TYPE", type);
                                setResult(1007, intent);
                                finish();
                            }else {
                                Intent intent = new Intent();
                                intent.putExtra("RETURN_DATA", c.getString("data"));
                                intent.putExtra("RETURN_VALUE", c.getString("value"));
                                setResult(Master.getResult(data_text), intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(CssdQrUser.this, "สิทธิ์ผู้ใช้งานไม่สามารถเข้าถึงส่วนนี้ได้ !!", Toast.LENGTH_SHORT).show();
                            txt_user_code.setText("");
                            txt_user_code.requestFocus();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_user_code", user_code);
                if (data != null){
                    data.put("data", String.valueOf(data)+data_text);
                }
                data.put("data_text",data_text);
                if (type != null){
                    data.put("type",type);
                }else {
                    data.put("type","");
                }

                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_user.php", data);
                    Log.d("BANK7",data+"");
                    Log.d("BANK7",result);
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
