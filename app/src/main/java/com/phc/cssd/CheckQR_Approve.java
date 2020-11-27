package com.phc.cssd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.UpdateUserQR;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CheckQR_Approve extends Activity {

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private UpdateUserQR Uqr = new UpdateUserQR();
    private Timer timer;

    EditText etxt_qr;
    Button bt_cancel;

    String DocNo;
    String xSel;
    String MacNo;
    String check="false";

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr__approve);
        Intent intent = getIntent();
        DocNo = intent.getStringExtra("DocNo");
        xSel = intent.getStringExtra("xSel");
        MacNo = intent.getStringExtra("MacNo");
        Log.d("DocNo chk: ", DocNo);
        Log.d("xSel chk: ", xSel);
        init();
    }

    public void init(){
        etxt_qr = (EditText) findViewById(R.id.etxt_qr);
        etxt_qr.requestFocus();
        bt_cancel = (Button) findViewById(R.id.bt_cancel);

        etxt_qr.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Checkuser(etxt_qr.getText().toString(),DocNo,xSel);
                            //finish();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        etxt_qr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Checkuser("EM00001", DocNo, xSel);
                return true;
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        setResult(1035, intent);
        intent.putExtra("RETURN_DATA",check);
        intent.putExtra("RETURN_xsel",xSel);
        intent.putExtra("RETURN_DocNo",DocNo);
        intent.putExtra("RETURN_MacNo",MacNo);
        super.finish();
    }

    public void startUserSession() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    public void Checkuser(String qr_code,String docno,String xsel) {
        class Checkuser extends AsyncTask<String, Void, String> {
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
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("check").equals("true")){
                            check = c.getString("check");
                            finish();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(CheckQR_Approve.this);
                            builder.setCancelable(true);
                            builder.setTitle("แจ้งเตือน !!");
                            builder.setMessage("สิทธิ์ผู้ใช้งานไม่สามารถเข้าถึงส่วนนี้ได้ !!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            startUserSession();
                            etxt_qr.setText("");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("qr_code",params[0]);
                data.put("docno",params[1]);
                data.put("xsel",params[2]);
                Log.d("xDocNo: ", data+"");
                String result = httpConnect.sendPostRequest(getUrl.xUrl+"chk_qr/check_qr.php",data);
                Log.d("KFHKCHKD", result);
                Log.d("KFHKCHKD", data+"");
                return  result;
            }
        }
        Checkuser ru = new Checkuser();
        ru.execute( qr_code,docno,xsel );
    }
}
