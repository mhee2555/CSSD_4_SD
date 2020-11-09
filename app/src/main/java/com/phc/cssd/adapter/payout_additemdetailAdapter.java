package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.PayoutActivity;
import com.phc.cssd.Payout_additemActivity;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class payout_additemdetailAdapter extends ArrayAdapter {
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private ArrayList<Response_Aux> listData ;
    //private ArrayList<Response_Aux_itemstock> listData2 ;
    private Activity context;
    ListView Lv2;
    String Usage_code;
    int no;
    String IsAdmin;
    String IsInCharg;
    String IsUser;
    String IsAdmin_Log;
    String IsInCharg_Log;
    String IsUser_Log;
    String DocNoStatus;
    int devicemode;

    public payout_additemdetailAdapter(Activity aActivity,
                                       ArrayList<Response_Aux> listData,
                                       String IsAdmin,
                                       String IsInCharg,
                                       String IsUser,
                                       String IsAdmin_Log,
                                       String IsInCharg_Log,
                                       String IsUser_Log,
                                       String DocNoStatus,
                                       int devicemode) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.Lv2 = Lv2;
        this.IsAdmin = IsAdmin;
        this.IsInCharg = IsInCharg;
        this.IsUser = IsUser;
        this.IsAdmin_Log = IsAdmin_Log;
        this.IsInCharg_Log = IsInCharg_Log;
        this.IsUser_Log = IsUser_Log;
        this.DocNoStatus = DocNoStatus;
        this.Usage_code = Usage_code;
        this.devicemode =devicemode;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        no=position;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_additemdetail_payout, parent, false);

        if(devicemode== PayoutActivity.IsL2){
            RelativeLayout M = (RelativeLayout) v.findViewById(R.id.M);
            M.setVisibility(View.GONE);
        }

        TextView txt_itemcode_po = (TextView) v.findViewById(R.id.txt_itemcode_po);
        final EditText etxt_num_po = (EditText) v.findViewById(R.id.etxt_num_po);
        Button bt_del_po = (Button) v.findViewById(R.id.bt_del_po);
        TextView bt_special = (TextView) v.findViewById(R.id.bt_special);

        etxt_num_po.setText( listData.get(position).getFields3() );
        String ss = listData.get(position).getFields1();
        txt_itemcode_po.setText(ss);
        Log.d("VMFVDLE", IsAdmin);
        Log.d("VMFVDLE", IsInCharg);
        Log.d("VMFVDLE", IsUser);
        Log.d("VMFVDLE", IsAdmin_Log);
        Log.d("VMFVDLE", IsInCharg_Log);
        Log.d("VMFVDLE", IsUser_Log);
        if(listData.get(position).getFields8().equals("1")){
            if (IsUser.equals("1") && IsUser_Log.equals("1")) {
                bt_del_po.setVisibility(View.VISIBLE);
            } else if (IsInCharg.equals("1") && IsInCharg_Log.equals("1")) {
                bt_del_po.setVisibility(View.VISIBLE);
            } else if (IsAdmin.equals("1") && IsAdmin_Log.equals("1")) {
                bt_del_po.setVisibility(View.VISIBLE);
            } else {
                bt_del_po.setVisibility(View.INVISIBLE);
                bt_special.setVisibility(View.GONE);
                etxt_num_po.setEnabled(false);
            }
        }else{
            bt_del_po.setVisibility(View.VISIBLE);
            bt_special.setVisibility(View.VISIBLE);
        }

        etxt_num_po.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus){
                if (hasFocus) ((EditText)v).selectAll();
            }
        });

        etxt_num_po.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                listData.get(position).setFields3( etxt_num_po.getText()+"" );
                Update_PayoutDetail(listData.get(position).getFields6(),etxt_num_po.getText()+"",listData.get(position).getFields7());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        etxt_num_po.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Log.d("onKey: ", "DocNo:"+listData.get(position).getFields6()+" QTY:"+etxt_num_po.getText()+" ItemStock :"+listData.get(position).getFields7());
                            Update_PayoutDetail(listData.get(position).getFields6(),etxt_num_po.getText()+"",listData.get(position).getFields7());
                            //searchbox.requestFocus();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        bt_del_po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("ยืนยัน");
                builder.setMessage("ต้องการลบเอกสารหรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Delete_PayoutDetail(listData.get(position).getFields5(),listData.get(position).getFields6());
                                ( (Payout_additemActivity)context ).ListPayoutDetail(listData.get(position).getFields6());
                                if(listData.get(position).getFields8().equals("1") && IsAdmin.equals("1")){
                                    ( (Payout_additemActivity)context ).addEvenlog("PA",listData.get(position).getFields5()+"","Delete [ "+listData.get(position).getFields3()+" ] "+listData.get(position).getFields7()+" to "+listData.get(position).getFields6());
                                }

                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        bt_special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("ยืนยัน");
                builder.setMessage("ต้องการย้ายรายการไปเอกสารพิเศษหรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               /* newsData.setFields1(c.getString("xItemName"));
                                newsData.setFields2(c.getString("xExpireDate"));
                                newsData.setFields3(c.getString("xQty"));
                                newsData.setFields4(c.getString("xIsStatus"));
                                newsData.setFields5(c.getString("xID"));
                                newsData.setFields6(xDocNo);
                                newsData.setFields7(c.getString("xItemStockID"));
                                newsData.setFields8(c.getString("xIsCheck"));
                                newsData.setFields9(Po_DeptID);*/
                                addSpecialDocNo(listData.get(position).getFields10(),listData.get(position).getFields3(),listData.get(position).getFields7(),listData.get(position).getFields9());
                                Delete_PayoutDetail(listData.get(position).getFields5(),listData.get(position).getFields6());
                                ( (Payout_additemActivity)context ).ListPayoutDetail(listData.get(position).getFields6());
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return v;
    }

    public void Delete_PayoutDetail(final String xID,String xDocNo) {
        class Delete_PayoutDetail extends AsyncTask<String, Void, String> {
            final getUrl iFt = new getUrl();
            final HTTPConnect ruc = new HTTPConnect();
            final String TAG_RESULTS="result";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray setRs = null;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
//                        if( c.getString("xItemName").equals("true") );
//                            Toast.makeText(context,listData.get(position).getFields5(),Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xID",params[0]);
                data.put("xDocNo",params[1]);
                Log.d("ttdel", data+"");
                String result = ruc.sendPostRequest(iFt.setdeletepayoutdetail(),data);
                Log.d("result: ", result+"");
                return  result;
            }
        }
        Delete_PayoutDetail ru = new Delete_PayoutDetail();
        ru.execute( xID,xDocNo );
    }

    public void Update_PayoutDetail(String xDocNo,String xQty,String xItemStockID) {
        class Update_PayoutDetail extends AsyncTask<String, Void, String> {
            final getUrl iFt = new getUrl();
            final HTTPConnect ruc = new HTTPConnect();
            final String TAG_RESULTS="result";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray setRs = null;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if( c.getString("Finish").equals("true") );
                        Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("Qty",params[1]);
                data.put("ItemStockID",params[2]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_updatepayoutadditemdetail.php",data);
                return  result;
            }
        }
        Update_PayoutDetail ru = new Update_PayoutDetail();
        ru.execute( xDocNo,xQty,xItemStockID );
    }
    public void addSpecialDocNo(String UserCode,String xQty,String xItemStockID,String xDept) {
        class addSpecialDocNo extends AsyncTask<String, Void, String> {
            final getUrl iFt = new getUrl();
            final HTTPConnect ruc = new HTTPConnect();
            final String TAG_RESULTS="result";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray setRs = null;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if( c.getString("Finish").equals("true") );
                        //Toast.makeText(context,"อัพเดต: "+listData.get(no).getFields6()+"จำนวน"+listData.get(no).getFields3(),Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UserCode",params[0]);
                data.put("xQty",params[1]);
                data.put("xItemStockID",params[2]);
                data.put("xDept",params[3]);
                Log.d("data: ", data+"");
                String result = ruc.sendPostRequest(getUrl.xUrl+"1/set_addspecialDoc_payout.php",data);
                return  result;
            }
        }
        addSpecialDocNo ru = new addSpecialDocNo();
        ru.execute( UserCode,xQty,xItemStockID,xDept );
    }
}

