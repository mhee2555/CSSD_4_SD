package com.phc.cssd;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.payout_createdocdetailAdapter;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Payout_CreateNewDocActivity extends Activity {
    ArrayList<Response_Aux_itemstock> ArrayData = new ArrayList<Response_Aux_itemstock>();
    ArrayList<Response_Aux> resultspayout = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultspayoutdetail = new ArrayList<Response_Aux>();
    Button button_search;
    Button button_import;
    Button bt_plus_po;
    EditText etxt_searchsendsterile;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    String xSel = "0";
    String userid = "0";
    String Po_DeptID="0";
    String Po_date="0";
    String DocNo="";
    ArrayList<Response_Aux> resultsDepartment = new ArrayList<Response_Aux>();
    Button bt_back_payoutcreate;
    TextView xtxt_xDocNo;
    TextView xtxt_dept;
    Button bt_del;
    Button bt_ischeck;
    int mode;
    String B_ID ;
    int devicemode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            userid =  bd.getString("userid");
            Po_DeptID = bd.getString("deptid");
            Po_date = bd.getString("date");
            mode = bd.getInt("mode");
            B_ID = bd.getString("B_ID");

            Log.d("bundle: ", "xSel:"+xSel+" Po_DeptID"+Po_DeptID+" Po_date"+Po_date);
            devicemode = bd.getInt("devicemode");
            if(devicemode==PayoutActivity.IsT2){
                setContentView(R.layout.activity_createnewdoc_payout);
            }else{
                setContentView(R.layout.activity_createnewdoc_payout_l2);
            }
        }

        initialize();

    }

    private void initialize(){

        bt_back_payoutcreate = (Button) findViewById(R.id.bt_back);
        bt_plus_po = (Button) findViewById(R.id.bt_plus_po);
        xtxt_xDocNo = (TextView) findViewById(R.id.xtxt_xDocNo);
        xtxt_dept = (TextView) findViewById(R.id.xtxt_dept);
        TextView headText = (TextView) findViewById(R.id.textView83);
        bt_del = (Button) findViewById(R.id.bt_del);
        bt_ischeck= (Button) findViewById(R.id.bt_ischeck);
        if(mode==1){
            headText.setText("สร้างใบยืมอุปกรณ์");
        }
        bt_back_payoutcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bt_plus_po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchItemStock();
            }
        });
        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Payout_CreateNewDocActivity.this);
                builder.setCancelable(true);
                builder.setTitle("ยืนยัน");
                builder.setMessage("ต้องการลบเอกสารนี้ใช่หรือไม่?");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CancelPayout(DocNo);
                                onBackPressed();
                            }
                        });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        bt_ischeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DocNo.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Payout_CreateNewDocActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("ยืนยัน");
                    builder.setMessage("บันทึกเอกสารใช่หรือไม่");
                    builder.setPositiveButton("ตกลง",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    IsCheckPayout(DocNo);
                                    onBackPressed();
                                    Toast.makeText(Payout_CreateNewDocActivity.this, "บันทึกเอกสารเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(Payout_CreateNewDocActivity.this, "กรุณาเลือกเอกสารที่จะบันทึก", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void CreatePayoutDocument(String UserCode, String xdept, final String RETURN_ItemCode , final String RETURN_QTY, final String RETURN_deptID) {
        class CreatePayoutDocument extends AsyncTask<String, Void, String> {
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
                        xtxt_xDocNo.setText(c.getString("DocNo"));
                        DocNo=c.getString("DocNo");
                        xtxt_dept.setText(c.getString("DepName2"));
                        Insert_PayoutDetail(DocNo,RETURN_ItemCode,RETURN_QTY,RETURN_deptID);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UserCode",params[0]);
                data.put("xdept",params[1]);
                data.put("mode",mode+"");
                Log.d("ttest","CreatePayoutDocument : "+data);
                data.put("B_ID",B_ID+"");
                data.put("itemcode",RETURN_ItemCode+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_createpayoutdocno.php",data);
                Log.d("ttest: ", result);
                return  result;
            }
        }
        CreatePayoutDocument ru = new CreatePayoutDocument();
        ru.execute( UserCode,xdept,RETURN_ItemCode,RETURN_QTY,RETURN_deptID);
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
                        Log.d("OOOO",c.getString("xItemName"));
                        newsData.setFields1(c.getString("xItemName"));
                        newsData.setFields2(c.getString("xExpireDate"));
                        newsData.setFields3(c.getString("xQty"));
                        newsData.setFields4(c.getString("xIsStatus"));
                        newsData.setFields5(c.getString("xID"));
                        newsData.setFields6(xDocNo);
                        newsData.setFields7(c.getString("xItemStockID"));
                        resultspayoutdetail.add( newsData );
                    }
                    ListView lv = (ListView) findViewById(R.id.list_payoutdocdetail);
                    lv.setAdapter(new payout_createdocdetailAdapter(Payout_CreateNewDocActivity.this, resultspayoutdetail,devicemode));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/get_payoutadditemdetail.php",data);
                Log.d("HHHH", result);
                return  result;
            }
        }
        ListPayoutDetail ru = new ListPayoutDetail();
        ru.execute( xDocNo );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }


    private void openSearchItemStock(){
        Intent i = new Intent(com.phc.cssd.Payout_CreateNewDocActivity.this,SearchItem_Payout.class);
        //Intent i = new Intent(SendSterile_MainActivity.this,Barcode_itemstock.class);
        i.putExtra("xSel", "10");
        i.putExtra("ED_Dept",Po_DeptID);
        i.putExtra("B_ID", B_ID);
        i.putExtra("devicemode",devicemode );
        startActivityForResult(i,1035);

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

            if(!RETURN_ItemCode.equals(null)){
                if(DocNo.equals("")){
                    CreatePayoutDocument(userid,Po_DeptID,RETURN_ItemCode,RETURN_QTY,RETURN_deptID);
                    System.out.println( "docno = " +DocNo + " RETURN_UsageCode = " + RETURN_ItemCode + " RETURN_QTY = " + RETURN_QTY ) ;

                }else{
                    System.out.println( "docno = " +DocNo + " RETURN_UsageCode = " + RETURN_ItemCode + " RETURN_QTY = " + RETURN_QTY ) ;
                    Insert_PayoutDetail(DocNo,RETURN_ItemCode,RETURN_QTY,RETURN_deptID);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void Insert_PayoutDetail(final String xDocNo,final String xItemCode,String xQTY,String xdept) {
        class ImportPayout extends AsyncTask<String, Void, String> {
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
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if( c.getString("Finish").equals("true")){
                            //ListPayoutDocument("0",xSearch);
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
                Log.d("input_insert : ",data+"" );
                String result = ruc.sendPostRequest(iFt.setinsertpayoutdetail(),data);
                Log.d("output_result : ",result );
                return  result;
            }
        }
        ImportPayout ru = new ImportPayout();
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
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                    }
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
                    resultspayout.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                Log.d("Insert : ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_updateischeckpayout.php",data);
                return  result;
            }
        }
        IsCheckPayout ru = new IsCheckPayout();
        ru.execute( xDocNo);
    }

}