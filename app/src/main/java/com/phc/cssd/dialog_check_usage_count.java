package com.phc.cssd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.ListUsageCount;
import com.phc.cssd.model.ModelUsageCount;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dialog_check_usage_count extends Activity {

    String UsageCode;
    String DocNo;
    String B_ID;
    String sel;
    String Cnt;
    String page;

    String condition1;
    String condition2;
    String condition3;
    String condition4;

    TextView cnt_item;
    TextView index1,index2,index3,index4;
    TextView qty1,qty2,qty3,qty4;
    Button back;
    LinearLayout P1,P2,P3,P4;
    ListView rq_listdocdetail;

    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelUsageCount> Model_RQ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_check_usage_count);
        byIntent();
        initialize();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        UsageCode = intent.getStringExtra("UsageCode");
        DocNo = intent.getStringExtra("DocNo");
        B_ID = intent.getStringExtra("B_ID");
        sel = intent.getStringExtra("sel");
        Cnt = intent.getStringExtra("cnt");
        page = intent.getStringExtra("page");
        condition1 = intent.getStringExtra("condition1");
        condition2 = intent.getStringExtra("condition2");
        condition3 = intent.getStringExtra("condition3");
        condition4 = intent.getStringExtra("condition4");
    }

    public void initialize() {
        rq_listdocdetail = (ListView) findViewById(R.id.rq_listdocdetail);
        cnt_item = (TextView) findViewById(R.id.cnt_item);
        qty1 = (TextView) findViewById(R.id.qty1);
        qty2 = (TextView) findViewById(R.id.qty2);
        qty3 = (TextView) findViewById(R.id.qty3);
        qty4 = (TextView) findViewById(R.id.qty4);
        qty1.setPaintFlags(qty1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        qty2.setPaintFlags(qty2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        qty3.setPaintFlags(qty3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        qty4.setPaintFlags(qty4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page.equals("0")){
                    finish();
                }else {
                    SetIsstatus();
                }
            }
        });
        P1 = (LinearLayout) findViewById(R.id.P1);
        P2 = (LinearLayout) findViewById(R.id.P2);
        P3 = (LinearLayout) findViewById(R.id.P3);
        P4 = (LinearLayout) findViewById(R.id.P4);
        index1 = (TextView) findViewById(R.id.index1);
        index2 = (TextView) findViewById(R.id.index2);
        index3 = (TextView) findViewById(R.id.index3);
        index4 = (TextView) findViewById(R.id.index4);
        Log.d("KFHDLD",condition1);
        Log.d("KFHDLD",condition2);
        Log.d("KFHDLD",condition3);
        Log.d("KFHDLD",condition4);
        if (!condition1.equals("0") && !condition2.equals("0") && !condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.VISIBLE);
            index1.setText("1.");
            index2.setText("2.");
            index3.setText("3.");
            index4.setText("4.");
            qty1.setText(condition1);
            qty2.setText(condition2);
            qty3.setText(condition3);
            qty4.setText(condition4);
        }else if (!condition1.equals("0") && !condition2.equals("0") && !condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.GONE);
            index1.setText("1.");
            index2.setText("2.");
            index3.setText("3.");
            qty1.setText(condition1);
            qty2.setText(condition2);
            qty3.setText(condition3);
        }else if (!condition1.equals("0") && !condition2.equals("0") && condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.GONE);
            index1.setText("1.");
            index2.setText("2.");
            qty1.setText(condition1);
            qty2.setText(condition2);
        }else if (!condition1.equals("0") && condition2.equals("0") && condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.GONE);
            index1.setText("1.");
            qty1.setText(condition1);
        }else if (condition1.equals("0") && !condition2.equals("0") && !condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.VISIBLE);
            index2.setText("1.");
            index3.setText("2.");
            index4.setText("3.");
            qty2.setText(condition2);
            qty3.setText(condition3);
            qty4.setText(condition4);
        }else if (condition1.equals("0") && condition2.equals("0") && !condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.VISIBLE);
            index3.setText("1.");
            index4.setText("2.");
            qty3.setText(condition3);
            qty4.setText(condition4);
        }else if (condition1.equals("0") && condition2.equals("0") && condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.VISIBLE);
            index4.setText("1.");
            qty4.setText(condition4);
        }else if (condition1.equals("0") && !condition2.equals("0") && condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.GONE);
            index2.setText("1.");
            qty2.setText(condition2);
        }else if (!condition1.equals("0") && condition2.equals("0") && !condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.VISIBLE);
            index1.setText("1.");
            index3.setText("2.");
            index4.setText("3.");
            qty1.setText(condition1);
            qty3.setText(condition3);
            qty4.setText(condition4);
        }else if (!condition1.equals("0") && condition2.equals("0") && condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.VISIBLE);
            index1.setText("1.");
            index4.setText("2.");
            qty1.setText(condition1);
            qty4.setText(condition4);
        }else if (!condition1.equals("0") && !condition2.equals("0") && condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.VISIBLE);
            index1.setText("1.");
            index2.setText("2.");
            index4.setText("3.");
            qty1.setText(condition1);
            qty2.setText(condition2);
            qty4.setText(condition4);
        }else if (condition1.equals("0") && !condition2.equals("0") && condition3.equals("0") && !condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.GONE);
            P4.setVisibility(View.VISIBLE);
            index2.setText("1.");
            index4.setText("2.");
            qty2.setText(condition2);
            qty4.setText(condition4);
        }else if (!condition1.equals("0") && condition2.equals("0") && !condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.VISIBLE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.GONE);
            index1.setText("1.");
            index3.setText("2.");
            qty1.setText(condition1);
            qty3.setText(condition3);
        }else if (condition1.equals("0") && !condition2.equals("0") && !condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.VISIBLE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.GONE);
            index2.setText("1.");
            index3.setText("2.");
            qty2.setText(condition2);
            qty3.setText(condition3);
        }else if (condition1.equals("0") && condition2.equals("0") && !condition3.equals("0") && condition4.equals("0")){
            P1.setVisibility(View.GONE);
            P2.setVisibility(View.GONE);
            P3.setVisibility(View.VISIBLE);
            P4.setVisibility(View.GONE);
            index3.setText("1.");
            qty3.setText(condition3);
        }

    }

    public void SetIsstatus() {
        class SetIsstatus extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_check_usage_count.this);

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
                        if (c.getString("finish").equals("true")){
                            finish();
                        }else {
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
                data.put("DOC_NO", DocNo);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_set_status_ems.php", data);
                    Log.d("KJDGDK",data+"");
                    Log.d("KJDGDK",result);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }
            // =========================================================================================
        }
        SetIsstatus obj = new SetIsstatus();
        obj.execute();
    }
}
