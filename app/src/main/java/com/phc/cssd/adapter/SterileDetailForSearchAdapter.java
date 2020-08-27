package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cssd.R;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.SterileSeachActivity;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SterileDetailForSearchAdapter extends ArrayAdapter {

    int usagecodeMode = 1;
    int itemcodeMode = 2;

    private ArrayList<Response_Aux> listData ;
    private Activity context;
    private int IsStatus;
    private String UserId;
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    ArrayList<Response_Aux> resultssteriledetail = new ArrayList<Response_Aux>();
    String DocNo;
    String Qty;
    int mode;

//    public SterileDetailForSearchAdapter(Activity aActivity, ArrayList<Response_Aux> listData, int IsStatus, String UserId, String DocNo, String Qty) {
//        super(aActivity, 0, listData);
//        this.context = aActivity;
//        this.listData = listData;
//        this.IsStatus = IsStatus;
//        this.UserId = UserId;
//        this.DocNo = DocNo;
//        this.Qty = Qty;
//    }

    public SterileDetailForSearchAdapter(Activity aActivity, ArrayList<Response_Aux> listData, int IsStatus, String UserId, String DocNo, String Qty,int mode) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.IsStatus = IsStatus;
        this.UserId = UserId;
        this.DocNo = DocNo;
        this.Qty = Qty;
        this.mode = mode;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.o_sterile_detail, parent, false);
        final int nPosition=position;
        final TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        final TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        final TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        final ImageView ems = (ImageView) v.findViewById(R.id.ems);
        final ImageView imageView01 = (ImageView) v.findViewById(R.id.imageView1);
        final RelativeLayout Re1 = (RelativeLayout) v.findViewById(R.id.Re1);

        if(mode==usagecodeMode){ //usagecodeMode
            tFields3.setVisibility(View.GONE);
            if (listData.get(position).getFields15().equals("0")){
                ems.setVisibility(View.GONE);
            }else {
                ems.setVisibility(View.VISIBLE);
            }

            ems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SterileSeachActivity)context).DisplayRemarkItem(listData.get(position).getFields6());
                }
            });

            imageView01.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int Status = 0;
                    if( listData.get(nPosition).getFields5().equals("0") ) {
                        Status = 1;
                        imageView01.setImageResource(R.drawable.ic_box_normal_blue);
                        Re1.setBackgroundResource(R.drawable.rectangle_red);
                    }else {
                        Status = 0;
                        imageView01.setImageResource(R.drawable.ic_box_check_blue);
                        Re1.setBackgroundResource(R.drawable.rectangle);
                    }
                    listData.get(nPosition).setFields5(Status+"");
                    UpdateSDIsOccurence(listData.get(nPosition).getFields1(),Status+"");
                }
            });


            if( IsStatus>0 ) {
                tFields1.setText(listData.get(position).getFields2());
                tFields2.setText("รหัสใช้งาน : "+listData.get(position).getFields6());
                if (listData.get(position).getFields5().equals("0")) {
                    imageView01.setImageResource(R.drawable.ic_box_check_blue);
                    Re1.setBackgroundResource(R.drawable.rectangle);
                } else if (listData.get(position).getFields5().equals("1")) {
                    imageView01.setImageResource(R.drawable.ic_box_normal_blue);
                    Re1.setBackgroundResource(R.drawable.rectangle_red);
                    if(!listData.get(position).getFields8().equals("null")){
                        imageView01.setEnabled(false);
                    }
                }else {
                    Re1.setBackgroundResource(R.drawable.rectangle_red);
                    tFields1.setTextColor(Color.RED);
                    tFields2.setTextColor(Color.RED);
                    imageView01.setVisibility(View.GONE);
                }
            }else{
                Re1.setVisibility(View.GONE);
                tFields1.setVisibility(View.GONE);
                tFields2.setVisibility(View.GONE);
                imageView01.setVisibility(View.GONE);
            }
        }else{ //itemcodeMode

            tFields3.setVisibility(View.VISIBLE);
            ems.setVisibility(View.GONE);
            imageView01.setVisibility(View.GONE);

            if( IsStatus>0 ) {
                tFields1.setText(listData.get(position).getFields2());
                tFields2.setText("รหัสรายการ : "+listData.get(position).getFields6());
                tFields3.setText("จำนวน : "+listData.get(position).getFields3());
            }else{
                Re1.setVisibility(View.GONE);
                tFields1.setVisibility(View.GONE);
                tFields2.setVisibility(View.GONE);
                imageView01.setVisibility(View.GONE);
            }
        }


        return v;
    }

    public void UpdateSDIsOccurence(String xId, String xStatus) {
        class ListData extends AsyncTask<String, Void, String> {
            getUrl iFt = new getUrl();
            HTTPConnect ruc = new HTTPConnect();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONArray setRs = null;
                final String TAG_RESULTS="result";
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
//                        Log.d("OOOO",c.getString("Finish"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xId",params[0]);
                data.put("xStatus",params[1]);
                String result = ruc.sendPostRequest(iFt.setsteriledocdetailisocc(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( xId,xStatus);
    }

    public void UpdateSterileDetail(String xId, String xStatus,String xStepNo) {
        class ListData extends AsyncTask<String, Void, String> {
            getUrl iFt = new getUrl();
            HTTPConnect ruc = new HTTPConnect();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSONArray setRs = null;
                final String TAG_RESULTS="result";
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        //Log.d("AAAA",c.getString("Finish"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xId",params[0]);
                data.put("xStatus",params[1]);
                data.put("xStepNo",params[2]);
                String result = ruc.sendPostRequest(iFt.setsteriledocdetail(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( xId,xStatus,xStepNo );
    }

    public void CreateOccuranceDocNo(String Sel,String RefDocNo,String OccuranceId,
                                     String UserID,String StepNo,String Qty,
                                     String xId,String pt,ImageView imageView,
                                     LinearLayout xRe) {
        final int pp = Integer.valueOf(pt);
        final ImageView imageView1 = imageView;
        final LinearLayout Re1 = xRe;
        class CreateOccuranceDocNo extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssteriledetail.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(!c.getString("DocNo").equals("Null")) {
                            listData.get(pp).setFields5("1");
                            imageView1.setImageResource(R.drawable.ic_box_normal_blue);
                            Toast.makeText(context, "บันทึกสำเร็จ...", Toast.LENGTH_LONG).show();
                            Re1.setBackgroundResource(R.drawable.rectangle_red);
                        }else {
                            listData.get(pp).setFields5("0");
                            imageView1.setImageResource(R.drawable.ic_box_check_blue);
                            Toast.makeText(context, "ไม่สามารถสร้างเอกสารได้...", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xSel",params[0]);
                data.put("xRefDocNo",params[1]);
                data.put("xOccuranceId",params[2]);
                data.put("xUserID",params[3]);
                data.put("xStepNo",params[4]);
                data.put("xQty",params[5]);
                data.put("xId",params[6]);
                String result = ruc.sendPostRequest(iFt.createoccurancedocno(),data);
                return  result;
            }
        }
        CreateOccuranceDocNo ru = new CreateOccuranceDocNo();
        ru.execute(Sel,RefDocNo,OccuranceId,UserID,StepNo,Qty,xId,pt);
    }

    public void CancelOccurance(String Sel,String RefDocNo,String OccuranceId,
                                String UserID,String StepNo,String Qty,
                                String xId,String pt,ImageView imageView,
                                LinearLayout xRe) {
        final int pp = Integer.valueOf(pt);
        final ImageView imageView1 = imageView;
        final LinearLayout Re1 = xRe;
        class CancelOccurance extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssteriledetail.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("Finish").equals("true")) {
                            listData.get(pp).setFields5("1");
                            imageView1.setImageResource(R.drawable.ic_box_check_blue);
                            Toast.makeText(context, "ยกเลิกสำเร็จ...", Toast.LENGTH_LONG).show();
                            listData.get(pp).setFields5("0");
                            Re1.setBackgroundResource(R.drawable.rectangle);
                        }else {
                            listData.get(pp).setFields5("0");
                            imageView1.setImageResource(R.drawable.ic_box_normal_blue);
                            Toast.makeText(context, "ไม่สามารถยกเลิกได้...", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xSel",params[0]);
                data.put("xRefDocNo",params[1]);
                data.put("xOccuranceId",params[2]);
                data.put("xUserID",params[3]);
                data.put("xStepNo",params[4]);
                data.put("xQty",params[5]);
                data.put("xId",params[6]);
                String result = ruc.sendPostRequest(iFt.canceloccurance(),data);
                return  result;
            }
        }
        CancelOccurance ru = new CancelOccurance();
        ru.execute(Sel,RefDocNo,OccuranceId,UserID,StepNo,Qty,xId,pt);
    }

}
