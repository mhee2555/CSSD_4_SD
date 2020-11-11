package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.search_PayoutItemAdapter;
import com.phc.cssd.function.KeyboardUtils;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchItem_Payout extends Activity {
    ArrayList<Response_Aux_itemstock> ArrayData = new ArrayList<Response_Aux_itemstock>();
    ArrayList<Response_Aux_itemstock> ArrayData1 = new ArrayList<Response_Aux_itemstock>();
    Button button_search;
    Button button_import;
    EditText etxt_searchsendsterile;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    String xSel = "0";
    String SS_DeptID="0";
    String DepID;
    String B_ID;
    ArrayList<Response_Aux> resultsDepartment = new ArrayList<Response_Aux>();
    Button bt_back_searchsendsterile;
    private int devicemode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sendsterile);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            xSel =  bd.getString("xSel");
            SS_DeptID = bd.getString("ED_Dept");
            B_ID = bd.getString("B_ID");
            devicemode = bd.getInt("devicemode");
            if(devicemode==PayoutActivity.IsL2){
                LinearLayout l1 = (LinearLayout) findViewById(R.id.list1);
                LinearLayout H = (LinearLayout) findViewById(R.id.H);
                LinearLayout B = (LinearLayout) findViewById(R.id.B);
                l1.getLayoutParams().width = 700;
                l1.getLayoutParams().height = 1100;
                l1.setBackgroundColor(Color.WHITE);
                H.setVisibility(View.GONE);
                B.setVisibility(View.GONE);
            }
        }

        initialize();

        bt_back_searchsendsterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initialize(){
        etxt_searchsendsterile = (EditText) findViewById(R.id.etxt_searchsendsterile);

        bt_back_searchsendsterile = (Button) findViewById(R.id.bt_back_searchsendsterile);

        ListData("%","");
        etxt_searchsendsterile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListData(etxt_searchsendsterile.getText().toString().replace(' ','%'),DepID);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_search = (Button) findViewById(R.id.bt_searchsendsterile);
        button_search.bringToFront();
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListData(etxt_searchsendsterile.getText().toString(),DepID);
                etxt_searchsendsterile.requestFocus();
            }
        });


        KeyboardUtils.hideKeyboard(SearchItem_Payout.this);
    }

    public void ListData(final String Usage_code,final String xDepID) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    ArrayData.clear();
                    Response_Aux_itemstock newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_itemstock();
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")) {
                            newsData.setFields1(c.getString("xID"));
                            newsData.setFields2(c.getString("xItem_Code"));
                            newsData.setFields3(c.getString("xPackDate"));
                            newsData.setFields4(c.getString("xExpDate"));
                            newsData.setFields5(c.getString("xDept"));
                            newsData.setFields6(c.getString("xQty"));
                            newsData.setFields7(c.getString("xStatus"));
                            newsData.setFields8(c.getString("xUsageID"));
                            newsData.setFields9(c.getString("xItem_Name"));
                            newsData.setxFields10(c.getString("xDeptID"));
                            newsData.setxFields11(xSel);
                            newsData.setxFields12(c.getString("itemqty"));
                            newsData.setxFields13("0");
                            newsData.setxFields14("0");
                            newsData.setxFields15(c.getString("Shelflife"));
                            newsData.setxFields16(c.getString("IsSet"));
                            newsData.setIs_Check(true);
                            ArrayData.add( newsData );
                            if(i==0 && !Usage_code.equals("")){

                                etxt_searchsendsterile.requestFocus();
                            }
                        }else{

                            etxt_searchsendsterile.requestFocus();
                        }
                    }

                    ListView lv = (ListView) findViewById(R.id.list_searsendsterile);
                    lv.setAdapter(new search_PayoutItemAdapter(SearchItem_Payout.this, ArrayData,devicemode));
//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Usage_code",params[0]);
                data.put("xSel",params[1]);
                data.put("B_ID",B_ID);
                data.put("Dept",SS_DeptID);
                String result = ruc.sendPostRequest(iFt.Check_usagecode(),data);
                Log.d("BANKDCDV",data+"");
                Log.d("BANKDCDV",result);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute(Usage_code,xSel,xDepID );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public  void senditemandqty(String getID,String getQty,String getdeptID){
        Intent intent = new Intent();
        Log.d("OOOO", xSel+"--"+getID+"--"+getQty+"--"+getdeptID);
        if(xSel.equals("9")){
            setResult(1975, intent);
            intent.putExtra("ItemCode",getID);
            intent.putExtra("RETURN_Qty",getQty);
            intent.putExtra("RETURN_deptID",getdeptID);
            intent.putExtra("xSel",xSel);
            finish();
        }else if(xSel.equals("10")){
            setResult(1035, intent);
            intent.putExtra("ItemCode",getID);
            intent.putExtra("RETURN_Qty",getQty);
            intent.putExtra("RETURN_deptID",getdeptID);
            intent.putExtra("xSel",xSel);
            finish();
        }else{
            setResult(100, intent);
            intent.putExtra("RowID",getID);
            intent.putExtra("RETURN_Qty",getQty);
            intent.putExtra("xSel",xSel);
            finish();
        }

    }
}
