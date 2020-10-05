package com.phc.cssd.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SendSterile_DocListAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    String B_ID = null;


    public SendSterile_DocListAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData,String B_ID) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
        this.listData = listData;
        this.B_ID = B_ID;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_getdoc_sendsterile, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;

        TextView txtdocno = (TextView) v.findViewById(R.id.docno);
        TextView txtdocdate = (TextView)v.findViewById(R.id.docdate);
        TextView txtdept = (TextView)v.findViewById(R.id.dept);
        TextView txtqty = (TextView) v.findViewById(R.id.qty);
        final TextView txtnote = (TextView) v.findViewById(R.id.note);
        final TextView bt_note = (TextView) v.findViewById(R.id.bt_note);
        TextView txtisstatus = (TextView) v.findViewById(R.id.isstatus);
        ImageView doc_dapt = (ImageView) v.findViewById(R.id.doc_dapt);

        Log.d("BANKDOCNO",listData.get(position).getDocno());
        txtdocno.setText(listData.get(position).getDocno());
        txtdocdate.setText(listData.get(position).getDocdate()+" "+listData.get(position).getTime());
        txtdept.setText( listData.get(position).getDeptname());
        txtqty.setText( listData.get(position).getQty() );
        txtnote.setText( listData.get(position).getNote() );

        if (listData.get(position).getNote().equals("")){
            bt_note.setBackgroundResource(R.drawable.ic_list_grey);
        }else {
            bt_note.setBackgroundResource(R.drawable.ic_list_blue);
        }

        if(listData.get(position).getIsStatus().equals("0")){
            txtisstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radiobox_fill,0,0,0);
        }else{
            txtisstatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_radiobox_unfill,0,0,0);
            bt_note.setEnabled(false);
        }

        if (listData.get(position).getIsWeb().equals("0")){
            doc_dapt.setVisibility(View.INVISIBLE);
        }else {
            doc_dapt.setVisibility(View.VISIBLE);
        }

//        v.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Log.d("adepter : ", listData.get(position).getDocno());
//                Log.d("adepter : ", listData.get(position).getDocdate());
//                Log.d("adepter : ", listData.get(position).getDept());
//
//                ( (SendSterile_MainActivity)aActivity ).SetDate(listData.get(position).getDocno(),listData.get(position).getDocdate(),listData.get(position).getDept(),listData.get(position).getDeptname(),listData.get(position).getIsStatus());
//                ( (SendSterile_MainActivity)aActivity ).getlistdetail(listData.get(position).getDocno());
//
//            }
//        });

        bt_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(aActivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remark_dialog);
                dialog.setCancelable(true);
                dialog.setTitle("หมายเหตุ...");

                final EditText note1 = (EditText) dialog.findViewById(R.id.note1);
                note1.setText(listData.get(position).getNote());
                Button button1 = (Button) dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        txtnote.setText(note1.getText());
                        listData.get(position).setNote(note1.getText().toString());
                        updateremark(listData.get(position).getDocno(),listData.get(position).getNote(),"1");
                        ((SendSterile_MainActivity)aActivity).getlistdata("",listData.get(position).getDocdate(),"");
                      dialog.dismiss();
                    }
                });

                Button button5 = (Button) dialog.findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        txtnote.setText(listData.get(position).getNote());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return v;
    }


    public void updateremark(String DocNo, final String remark, String check) {
        class updateremark extends AsyncTask<String, Void, String> {
            // variable

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    final ArrayList<pCustomer> pCus = new ArrayList<pCustomer>();
                    String bo = "";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bo=c.getString("flag");
                    }
                }catch (JSONException e){
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("remark",params[1]);
                data.put("check",params[2]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.updateremark(),data);
                Log.d("BANK",result);
                Log.d("BANK",data+"");
                return result;
            }
        }
        updateremark ru = new updateremark();
        ru.execute(DocNo,remark,check);
    }
}