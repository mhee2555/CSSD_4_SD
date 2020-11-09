package com.phc.cssd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.payout_additemAdapter;
import com.phc.cssd.adapter.payout_additemdetailAdapter;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Payout_additemActivity extends Activity {

    ArrayList<Response_Aux_itemstock> ArrayData = new ArrayList<Response_Aux_itemstock>();
    ArrayList<Response_Aux> resultspayout = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultspayoutdetail = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> emptyarray = new ArrayList<Response_Aux>();
    Button button_search;
    Button button_import;
    Button bt_plus_po;
    EditText etxt_searchsendsterile;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    String xSel = "9";
    String userid = "0";
    String Po_DeptID="0";
    String Po_date="0";
    String DocNo="";
    String IsCheck="0";
    String IsStatus="0";
    ArrayList<Response_Aux> resultsDepartment = new ArrayList<Response_Aux>();
    Button bt_back_payoutadditem;
    Button bt_del;
    Button bt_ischeck;
    TextView txt_docdetail;

    private String IsAdmin_T = "";
    private String IsInCharg = "";
    private String IsUser = "";

    private String IsAdmin_Log = "";
    private String IsInCharg_Log = "";
    private String IsUser_Log = "";

    boolean IsAdmin;
    boolean editByAdmin =false;
    String B_ID ;
    String WithdrawMode;
    private ListView lvpayoutdocdetail;

    private int devicemode;
    private LinearLayout fL;
    private LinearLayout fR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            userid =  bd.getString("userid");
            Po_DeptID = bd.getString("deptid");
            Po_date = bd.getString("date");
            IsAdmin = bd.getBoolean("IsAdmin");

            IsAdmin_Log = bd.getString("IsAdmin_Log");
            IsInCharg_Log = bd.getString("IsInCharg_Log");
            IsUser_Log = bd.getString("IsUser_Log");
            IsAdmin_T = bd.getString("IsAdmin");
            IsInCharg = bd.getString("IsInCharg");
            IsUser = bd.getString("IsUser");

            WithdrawMode = bd.getString("WithdrawMode");
            B_ID = bd.getString("B_ID");
            Log.d("ttestgetpayoutdocument", "WithdrawMode:"+WithdrawMode);
//            xSel =  bd.getString("xSel");

            devicemode = bd.getInt("devicemode");
            if(devicemode==PayoutActivity.IsT2){
                setContentView(R.layout.activity_additem_payout);
            }else{
                setContentView(R.layout.activity_additem_payout_l2);
                fL= (LinearLayout) findViewById(R.id.L);
                fR= (LinearLayout) findViewById(R.id.R);
            }
        }

        initialize();

    }

    private void initialize(){
        bt_back_payoutadditem = (Button) findViewById(R.id.bt_back_payoutadditem);
        bt_plus_po = (Button) findViewById(R.id.bt_plus_po);
        bt_del = (Button) findViewById(R.id.bt_del);
        bt_ischeck= (Button) findViewById(R.id.bt_ischeck);
        txt_docdetail= (TextView) findViewById(R.id.txt_docdetail);
        lvpayoutdocdetail = (ListView) findViewById(R.id.list_payoutdocdetail);
        bt_back_payoutadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt_plus_po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DocNo.equals("")){
                    if(IsCheck.equals("0")){
                        openSearchItemStock();
                        editByAdmin =false;
                    }else{
                        if(IsAdmin && IsStatus.equals("1")){
                            openSearchItemStock();
                            editByAdmin =true;
                        }else{
                            Toast.makeText(Payout_additemActivity.this, "ไม่สามารถแก้ไขข้อมูลได้", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(Payout_additemActivity.this, "กรุณาเลือกเอกสารที่จะเพิ่มรายการ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DocNo.equals("")){
                    if(IsCheck.equals("0")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Payout_additemActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("ยืนยัน");
                        builder.setMessage("ต้องการลบเอกสารหรือไม่");
                        builder.setPositiveButton("ตกลง",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CancelPayout(DocNo);
                                        lvpayoutdocdetail.setAdapter(new payout_additemdetailAdapter(Payout_additemActivity.this, emptyarray,IsAdmin_T,IsInCharg,IsUser,IsAdmin_Log,IsInCharg_Log,IsUser_Log,"0",devicemode));

                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else{
                        Toast.makeText(Payout_additemActivity.this, "ไม่สามารถแก้ไขเอกสารได้", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(Payout_additemActivity.this, "กรุณาเลือกเอกสารที่จะยกเลิก", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_ischeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DocNo.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Payout_additemActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("เอกสารนี้ถูกปรับปรุงหรือแก้ไขเสร็จแล้วใช่หรือไม่");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    IsCheckPayout(DocNo);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else{
                    Toast.makeText(Payout_additemActivity.this, "กรุณาเลือกเอกสารที่จะบันทึก", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ListPayoutDocument("9",Po_date+","+Po_DeptID);
    }

    public void ListPayoutDocument(String xSel,String xSearch) {

        DocNo="";
        IsCheck="0";
        IsStatus="0";
        txt_docdetail.setText(DocNo);

        class ListSterileDocument extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xDocNo"));
                        newsData.setFields2(c.getString("xDocDate"));
                        newsData.setFields3(c.getString("xDepName2"));
                        newsData.setFields4(c.getString("xQty"));
                        newsData.setFields5(c.getString("xPayQty"));
                        newsData.setFields6(c.getString("xRemark"));
                        newsData.setFields7(c.getString("xIsStatus"));
                        newsData.setFields8(c.getString("xIsPrint"));
                        newsData.setFields9(c.getString("xItemCnt"));
                        newsData.setFields10(c.getString("xIsCheck"));
                        newsData.setFields12(c.getString("xIsSpecial"));
                        if (!c.getString("xDocNo").equals("")) resultspayout.add( newsData );
                    }
                    final ListView lv = (ListView) findViewById(R.id.list_payoutdoc);
                    //Log.d("resultspayout size: ", resultspayout.size()+"");
                    lv.setAdapter(new payout_additemAdapter(Payout_additemActivity.this,resultspayout));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = lv.getItemAtPosition(position);
                            Response_Aux newsData = (Response_Aux) o;
                            ListPayoutDetail(newsData.getFields1());
                            DocNo=newsData.getFields1();
                            IsCheck=newsData.getFields10();
                            IsStatus=newsData.getFields7();
                            txt_docdetail.setText(DocNo);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xSel",params[0]);
                data.put("xSearch",params[1]);
                data.put("WithdrawMode",WithdrawMode+"");

                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(iFt.getpayoutdocument(),data);
                Log.d("ttestgetpayoutdocument", data+"=-="+result);
                return  result;
            }
        }
        ListSterileDocument ru = new ListSterileDocument();
        ru.execute( xSel,xSearch );
    }

    public void ListPayoutDetail(final String xDocNo) {
        class ListPayoutDetail extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayoutdetail.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
//                        Log.d("OOOO",c.getString("xItemName"));
                        newsData.setFields1(c.getString("xItemName"));
                        newsData.setFields2(c.getString("xExpireDate"));
                        newsData.setFields3(c.getString("xQty"));
                        newsData.setFields4(c.getString("xIsStatus"));
                        newsData.setFields5(c.getString("xID"));
                        newsData.setFields6(xDocNo);
                        newsData.setFields7(c.getString("xItemStockID"));
                        newsData.setFields8(c.getString("xIsCheck"));
                        newsData.setFields9(Po_DeptID);
                        newsData.setFields10(userid);

                        resultspayoutdetail.add( newsData );
                    }

                    lvpayoutdocdetail.setAdapter(new payout_additemdetailAdapter(Payout_additemActivity.this, resultspayoutdetail,IsAdmin_T,IsInCharg,IsUser,IsAdmin_Log,IsInCharg_Log,IsUser_Log,IsStatus,devicemode));
                    if(devicemode==PayoutActivity.IsL2){
                        fR.setVisibility(View.VISIBLE);
                        fL.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("xDocNo: ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/get_payoutadditemdetail.php",data);
                Log.d("result: ", result);
                return  result;
            }
        }
        ListPayoutDetail ru = new ListPayoutDetail();
        ru.execute( xDocNo );
    }

    @Override
    public void onBackPressed() {
        if(devicemode==PayoutActivity.IsL2){
            if(fR.getVisibility()==View.GONE){
                this.finish();
            }else{
                fR.setVisibility(View.GONE);
                fL.setVisibility(View.VISIBLE);
            }
        }else{
            this.finish();
        }
    }


    private void openSearchItemStock(){
        Intent i = new Intent(Payout_additemActivity.this,SearchItem_Payout.class);
        i.putExtra("xSel", "9");
        i.putExtra("ED_Dept",Po_DeptID);
        i.putExtra("B_ID",B_ID);
        i.putExtra("devicemode",devicemode );
        startActivityForResult(i,1975);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            System.out.println( "requestCode = " + requestCode + " resultCode = " + resultCode) ;
            if(!data.equals(null)) {
                String xSel = data.getStringExtra("xSel");
            }

            String RETURN_ItemCode = data.getStringExtra("ItemCode");
            String RETURN_QTY = data.getStringExtra("RETURN_Qty");
            String RETURN_deptID = data.getStringExtra("RETURN_deptID");
//            Log.d( "Insert_PayoutDetail","docno = " +DocNo + " RETURN_UsageCode = " + RETURN_ItemCode + " RETURN_QTY = " + RETURN_QTY ) ;
            Insert_PayoutDetail(DocNo,RETURN_ItemCode,RETURN_QTY,RETURN_deptID);

            if(editByAdmin){
                addEvenlog("PA",RETURN_ItemCode,"Add [ "+RETURN_QTY+" ] "+RETURN_ItemCode+" to "+DocNo);
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void addEvenlog(String Type,String tid,String Descriptions) {
        class addEvenlog extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Type",params[0]);
                data.put("TID",params[1]);
                data.put("UserID",params[2]);
                data.put("Descriptions",params[3]);
                data.put("DocNo",params[4]);
                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(getUrl.xUrl+"cssd_add_eventlog.php",data);
                Log.d( "addEvenlog",data+"");
                Log.d( "addEvenlog",result);
                return  result;
            }
        }
        addEvenlog ru = new addEvenlog();
        ru.execute( Type,tid,userid,Descriptions,DocNo);
    }

    public void Insert_PayoutDetail(final String xDocNo,final String xItemCode,String xQTY,String xdept) {
        class Insert_PayoutDetail extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if( c.getString("Finish").equals("true")){
//                            ListPayoutDocument(xSel,Po_date+","+Po_DeptID);
                            ListPayoutDetail( DocNo );
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                data.put("xUsageCode",params[1]);
                data.put("xQTY",params[2]);
                data.put("xdept",params[3]);
                if(B_ID!=null){data.put("B_ID",B_ID);}
                Log.d("Insert : ", data+"");
                String result = ruc.sendPostRequest(iFt.setinsertpayoutdetail(),data);
                return  result;
            }
        }
        Insert_PayoutDetail ru = new Insert_PayoutDetail();
        ru.execute( xDocNo,xItemCode,xQTY,xdept);
    }
    public void CancelPayout(final String xDocNo) {
        class CancelPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultspayout.clear();
                    ListPayoutDocument(xSel,Po_date+","+Po_DeptID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("Insert : ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_cancelpayout.php",data);
                Log.d("ttest_cancelpayout", data+"");
                Log.d("ttest_cancelpayout", result+"");
                return  result;
            }
        }
        CancelPayout ru = new CancelPayout();
        ru.execute( xDocNo);
    }

    public void IsCheckPayout(final String xDocNo) {
        class IsCheckPayout extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        ListPayoutDocument(xSel,Po_date+","+Po_DeptID);
                        resultspayoutdetail.clear();
                        lvpayoutdocdetail.setAdapter(new payout_additemdetailAdapter(Payout_additemActivity.this, resultspayoutdetail,IsAdmin_T,IsInCharg,IsUser,IsAdmin_Log,IsInCharg_Log,IsUser_Log,"0",devicemode));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
//                Log.d("Insert : ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_updateischeckpayout.php",data);
                return  result;
            }
        }
        IsCheckPayout ru = new IsCheckPayout();
        ru.execute( xDocNo);
    }
}
