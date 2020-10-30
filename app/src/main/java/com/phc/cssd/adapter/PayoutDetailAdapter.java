package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.PayoutActivity;
import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PayoutDetailAdapter extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Activity context;
    ArrayList<String> array_chkbox;
    GridView Gv;
    String xDocNo;
    Object DateThai;

    public PayoutDetailAdapter(Activity aActivity, ArrayList<Response_Aux> listData,GridView Gv,String xDocNo) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.Gv = Gv;
        this.xDocNo = xDocNo;
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
        final View v = inflater.inflate(R.layout.list_payout_detail, parent, false);
        final CheckBox cBox=(CheckBox)v.findViewById(R.id.chk_box);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        ImageView imageView1 = (ImageView) v.findViewById(R.id.imageView1);
        ImageView imageView16 = (ImageView) v.findViewById(R.id.imageView16);
        TextView txt_checkusage = (TextView) v.findViewById(R.id.txt_checkusage);
        tFields1.setText("");
        /*if (listData.get(position).getFields4().equals("0")) {
            String ss = listData.get(position).getFields1();
            int intIndex = ss.indexOf("[");
            tFields2.setText(ss.substring(0,intIndex));
        }else{
            tFields2.setText(listData.get(position).getFields1());
        }*/


        tFields2.setText(listData.get(position).getFields1());
        tFields3.setText(listData.get(position).getFields2()+"[ "+listData.get(position).getFields8()+" วัน ]");
        tFields4.setText(listData.get(position).getFields3());


        if(listData.get(position).getFields6().equals("1")){
            txt_checkusage.setVisibility(View.VISIBLE);
        }else{
            txt_checkusage.setVisibility(View.INVISIBLE);

        }

        if (listData.get(position).getFields4().equals("1")) {
            imageView1.setImageResource(R.drawable.ic_radiobox_fill);
            cBox.setVisibility(View.VISIBLE);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("คุณต้องการลบข้อมูลนี้ "+listData.get(position).getFields1()+" ใช่หรือไม่?");
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(listData.get(position).getFields4().equals("1")){
                                Delete_PayoutDetail(listData.get(position).getFields7(),"1");
                                ((PayoutActivity)context).payoutnotfully(xDocNo);
                                Toast.makeText(v.getContext(), "ลบรายการแล้ว", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(v.getContext(), "ไม่สามารถลบรายการได้", Toast.LENGTH_SHORT).show();
                            }

                            //Gv.setAdapter( new PayoutDetailAdapter( context, listData,Gv,xDocNo) );
                        }
                    });
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dialog.dismiss();
                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }
        /*else if (listData.get(position).getFields4().equals("1")) {
            imageView1.setImageResource(R.drawable.uncheck02_1_32);
        }*/else{
            cBox.setVisibility(View.INVISIBLE);
            imageView1.setImageResource(R.drawable.ic_radiobox_unfill);
        }
        if(listData.get(position).getFields4().equals("2")){
            imageView16.setVisibility(View.INVISIBLE);
        }else{
            imageView16.setVisibility(View.INVISIBLE);
        }

        //cBox.setChecked(array_chkbox.get(position));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listData.get(position).getFields4().equals("1")) {
                    cBox.setChecked(!cBox.isChecked());
                }
            }
        });
        cBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cBox.isChecked()){
                    listData.get(position).setIs_Check(true);
                }else{
                    listData.get(position).setIs_Check(false);
                }
            }
        });

        return v;
    }

    public void Delete_PayoutDetail(final String xID,String mode) {
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
//
                        ListPayoutDetail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xID",params[0]);
                data.put("mode",params[1]);
                Log.d("del data: ", data+"");
                String result = ruc.sendPostRequest(iFt.setdeletepayoutdetail(),data);
                Log.d("del result: ", result+"");
                return  result;
            }
        }
        Delete_PayoutDetail ru = new Delete_PayoutDetail();
        ru.execute( xID,mode );
    }

    public void ListPayoutDetail() {
        class ListSterileDocument extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            final getUrl iFt = new getUrl();
            final HTTPConnect ruc = new HTTPConnect();
            final String TAG_RESULTS="result";
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
                    JSONArray setRs = null;
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    listData.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xItemName"));
                        newsData.setFields2(c.getString("xExpireDate"));
                        newsData.setFields3(c.getString("xQty"));
                        newsData.setFields4(c.getString("xIsStatus"));
                        newsData.setFields5(c.getString("xID"));
                        newsData.setFields6(c.getString("xIsCheckPay"));
                        newsData.setFields7(c.getString("xpaysubID"));
                        newsData.setFields8(c.getString("xShelflife"));
                        newsData.setIs_Check(false);
                        listData.add( newsData );
                    }
                    Gv.setAdapter(new PayoutDetailAdapter( context, listData,Gv,xDocNo));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.getpayoutdetail(),data);
                return  result;
            }
        }
        ListSterileDocument ru = new ListSterileDocument();
        ru.execute( xDocNo );
    }

}
