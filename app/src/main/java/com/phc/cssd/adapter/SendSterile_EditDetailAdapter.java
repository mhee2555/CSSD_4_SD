package com.phc.cssd.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SendSterile_EditDetailAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    // private ArrayList<Response_Aux_itemstock> listData2 ;
    private AppCompatActivity aActivity;
    public ArrayList<String> selectedItemDetail = new ArrayList<String>();
    public boolean IsAdmin;


    //private Activity context;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();

    //ArrayList<Response_Aux> resultsResterileType = xCtl.getListResterileType();
    //ArrayList<Response_Aux> resultsOccuranceType = xCtl.getListOccuranceType();

    //Spinner ResterileType;
    //Spinner OccuranceType;

    public SendSterile_EditDetailAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData,boolean IsAdmin) {
        super(aActivity, 0, listData);
        this.IsAdmin = IsAdmin;
        this.aActivity= aActivity;
        this.listData = listData;
        this.selectedItemDetail = selectedItemDetail;
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
        final View v = inflater.inflate(R.layout.list_editdetail_sendsterile, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;
        //final Spinner ResterileType = (Spinner) v.findViewById(R.id.resterile_spinner);
        //final Spinner OccuranceType = (Spinner) v.findViewById(R.id.occurance_spinner);
        final ImageView resterile_IV = (ImageView)v.findViewById(R.id.resterile_IV);
        final TextView txtitemname = (TextView)v.findViewById(R.id.itemname);
        final TextView txtUcode= (TextView) v.findViewById(R.id.Ucode);
        final TextView txtxremark_detail = (TextView) v.findViewById(R.id.xremark_detail);
        final TextView textView53 = (TextView) v.findViewById(R.id.textView53);
        final TextView r_name = (TextView) v.findViewById(R.id.r_name);
        final CheckBox ckIsSterile = (CheckBox) v.findViewById((R.id.ckIsSterile)) ;
        final TextView bt_note = (TextView) v.findViewById(R.id.bt_note);
        final TextView bt_risk = (TextView) v.findViewById(R.id.bt_risk);
        //final TextView txt_risk = (TextView) v.findViewById(R.id.txt_risk);

        //CheckBox ckselect = (CheckBox) v.findViewById(R.id.ckselect);
        Button btdel =(Button) v.findViewById(R.id.btdel);
        txtitemname.setText(listData.get(position).getItemname());
        txtUcode.setText(listData.get(position).getUsageCode());
        txtxremark_detail.setText(listData.get(position).getXremark());
        ArrayList<Response_Aux> resultsR = xCtl.getListResterileType();
        ArrayList<Response_Aux> resultsO = xCtl.getListOccuranceType();

        Log.d("ttest","getIsStatus : "+listData.get(position).getIsStatus());
        if(!(listData.get(position).getIsStatus().equals("0"))){
            resterile_IV.setEnabled(false);
            btdel.setEnabled(false);
//            if(!IsAdmin){
            btdel.setVisibility(View.GONE);
//            }
            bt_note.setEnabled(false);
            bt_risk.setEnabled(false);
        }

        if(!listData.get(position).getOccuranceID().equals("0")){
            txtitemname.setTextColor(Color.RED);
            txtUcode.setTextColor(Color.RED);
            txtxremark_detail.setTextColor(Color.RED);
            textView53.setTextColor(Color.RED);
            bt_risk.setEnabled(false);
            bt_risk.setBackgroundResource(R.drawable.ic_risk_icon);
          /*  for(int i=0;i<resultsResterileType.size();i++){
                if(listData.get(position).getOccurancetype().equals(resultsResterileType.get(i).getFields1())){
                    listData.get(position).setOccurancename(resultsResterileType.get(i).getFields2());
                }
                txt_risk.setText(listData.get(position).getOccurancename());

            }*/


        }
        if(listData.get(position).getIsSterile().equals("1")){
            for(int i=0;i<resultsR.size();i++){
                if(listData.get(position).getResteriletype().equals(resultsR.get(i).getFields1())){
                    Log.d("getView r_name: ",listData.get(position).getResterilename() );
                    listData.get(position).setResterilename(resultsR.get(i).getFields2());
                }
            }
            r_name.setText(listData.get(position).getResterilename());
            resterile_IV.setImageResource(R.drawable.ic_r_red);
        }

        btdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setCancelable(true);
                builder.setTitle("Confirm");
                builder.setMessage("ต้องการลบหรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("onClick: ", listData.get(position).getDocno()+"  "+listData.get(position).getUsageCode());
                                System.out.print("xxx"+listData.get(position).getDocno()+listData.get(position).getUsageCode());
                                ( ( SendSterile_MainActivity )aActivity ).DeleteDetail(listData.get(position).getDocno(),listData.get(position).getSs_rowid());

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


        //Log.d("getUcode: ", listData.get(position).getUcode());
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ( (SendSterile_MainActivity)aActivity ).getlistdetail_l2(listData.get(position).getUsageCode(),listData.get(position).getDocno());

            }
        });


        resterile_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(aActivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.resterile_dialog);
                dialog.setCancelable(true);
                dialog.setTitle("ประเภทResterile...");

                final Spinner ResterileType = (Spinner) dialog.findViewById(R.id.spn_list);
                xCtl.ListResterileType(ResterileType, aActivity );
                final ArrayList<Response_Aux> resultsResterileType = xCtl.getListResterileType();
                ResterileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                        //id rester
                        listData.get(position).setResteriletype(resultsResterileType.get(pn).getFields1());
                        Log.d("setResteriletype: ",listData.get(position).getResteriletype());
                        //name rester
                        listData.get(position).setResterilename(ResterileType.getSelectedItem().toString());
                        Log.d("setResterilename: ",listData.get(position).getResterilename());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //Another interface callback
                    }
                });

                Button button1 = (Button) dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(!ResterileType.getSelectedItem().equals("-")) {
                            listData.get(position).setIsSterile("1");
                            resterile_IV.setImageResource(R.drawable.ic_r_red);
                        }
                        UpIsSterile(listData.get(position).getSs_rowid().toString(),listData.get(position).getIsSterile(),listData.get( position ).getResteriletype());
                        r_name.setText(listData.get(position).getResterilename());
                        dialog.dismiss();
                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(!ResterileType.getSelectedItem().equals("-")) {
                            listData.get(position).setIsSterile("0");
                            resterile_IV.setImageResource(R.drawable.ic_r_grey);
                        }
                        UpSterile(listData.get(position).getSs_rowid().toString(),listData.get(position).getIsSterile(),listData.get( position ).getResteriletype());
                        r_name.setText("");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        bt_risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(aActivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.risk_dialog_sendsterile);
                dialog.setCancelable(true);
                dialog.setTitle("ประเภทความเสี่ยง...");

                final Spinner OccuranceType = (Spinner) dialog.findViewById(R.id.spn_list);
                xCtl.ListOccuranceType(OccuranceType, aActivity );
                final ArrayList<Response_Aux> resultsOccuranceType = xCtl.getListOccuranceType();
                OccuranceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pn, long id) {
                        listData.get(position).setOccurancetype(resultsOccuranceType.get(pn).getFields1());
                        Log.d("getOccurancetype: ",listData.get(position).getOccurancetype());
                        listData.get(position).setOccurancename(OccuranceType.getSelectedItem().toString());
                        Log.d("getOccurancename: ",listData.get(position).getOccurancename());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //Another interface callback
                    }
                });
                Button button1 = (Button) dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        listData.get(position).setOccuranceID(listData.get(position).getOccurancetype());
                        createoccurance(listData.get(position).getUcode(),listData.get(position).getDocno(),listData.get(position).getDept(),listData.get(position).getItemID(),listData.get(position).getOccurancetype());
                        bt_risk.setBackgroundResource(R.drawable.ic_risk_icon);
                        txtitemname.setTextColor(Color.RED);
                        txtUcode.setTextColor(Color.RED);
                        txtxremark_detail.setTextColor(Color.RED);
                        textView53.setTextColor(Color.RED);
                        dialog.dismiss();
                    }
                });
                Button button5 = (Button) dialog.findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        bt_risk.setBackgroundResource(R.drawable.ic_risk_icon_gray);
                        txtitemname.setTextColor(Color.BLACK);
                        txtUcode.setTextColor(Color.BLACK);
                        txtxremark_detail.setTextColor(Color.BLACK);
                        textView53.setTextColor(Color.BLACK);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        bt_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(aActivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remark_dialog);
                dialog.setCancelable(true);
                dialog.setTitle("หมายเหตุ...");

                final EditText note1 = (EditText) dialog.findViewById(R.id.note1);
                note1.setText(listData.get(position).getXremark());
                Button button1 = (Button) dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //txtxremark_detail.setText(note1.getText());
                        listData.get(position).setXremark(note1.getText().toString());
                        Log.d("Note Doclist: ", listData.get(position).getXremark());
                        updateremark(listData.get(position).getSs_rowid(),listData.get(position).getXremark(),"2");
                        //notifyDataSetChanged();
                        txtxremark_detail.setText(listData.get(position).getXremark());
                        dialog.dismiss();
                    }
                });

                Button button5 = (Button) dialog.findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        txtxremark_detail.setText(listData.get(position).getXremark());
                        //Log.d("resultNONsave: ",listData.get(position).getUsageCode().toString()+"/"+listData.get(position).getIsSterile()+"/"+listData.get( position ).getResteriletype()+"/"+listData.get(position).getResterilename());
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        //notifyDataSetChanged();
        return v;
    }

    //    public ArrayList<String> getSelectedString(){
//        return selectedItemDetail;
//    }
//
//    public  void clearobject2(){
//        selectedItemDetail.clear();
//    }
//
//
//
    public void UpIsSterile(String UsageCode,String Check,String ss) {
        class UpIsSterile extends AsyncTask<String, Void, String> {
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
                    // //Log.d("BBBB", "Hello" );
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bo=c.getString("flag");
                    }
                }catch (JSONException e){
                    //Log.d("AAAAAA", " errror InsertDetail" );
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("UsageCode",params[0]);
                data.put("Check",params[1]);
                data.put("ss",params[2]);
                Log.d("Data:", data+"");
                String result = ruc.sendPostRequest(iFt.UpdateIsSterile(),data);
                Log.d("resultUpdateIs: ", result);
                return result;
            }
        }
        UpIsSterile ru = new UpIsSterile();
        ru.execute(UsageCode,Check,ss);
    }

    public void UpSterile(String UsageCode,String Check,String ss) {
        class UpSterile extends AsyncTask<String, Void, String> {
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

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID",params[0]);
                data.put("Check",params[1]);
                data.put("ss",params[2]);


                String result = ruc.sendPostRequest(iFt.UpdateSterile(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return result;
            }
        }
        UpSterile ru = new UpSterile();
        ru.execute(UsageCode,Check,ss);
    }


    public void updateremark(String DocNo,String remark,String check) {
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
                    // //Log.d("BBBB", "Hello" );
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bo=c.getString("flag");
                    }
                }catch (JSONException e){
                    Log.d("AAAAAA", " errror remark!!" );
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",params[0]);
                data.put("remark",params[1]);
                data.put("check",params[2]);
                Log.d("Data:", data+"");
                String result = ruc.sendPostRequest(iFt.updateremark(),data);
                Log.d("remarkresult: ", result);
                return result;
            }
        }
        updateremark ru = new updateremark();
        ru.execute(DocNo,remark,check);
    }

    public void createoccurance(String usercode,String docno,String dept,String itemcode,String octype) {
        class UpIsSterile extends AsyncTask<String, Void, String> {
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
                    // //Log.d("BBBB", "Hello" );
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        bo=c.getString("bool");
                    }
                    //if(bo.equals("true"))Toast.makeText(aActivity, "True", Toast.LENGTH_SHORT).show();
                    //else Toast.makeText(aActivity, "False", Toast.LENGTH_SHORT).show();

                }catch (JSONException e){
                    //Log.d("AAAAAA", " errror InsertDetail" );
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("usercode",params[0]);
                data.put("docno",params[1]);
                data.put("dept",params[2]);
                data.put("itemcode",params[3]);
                data.put("octype",params[4]);
                Log.d("Data:", data+"");
                String result = ruc.sendPostRequest(iFt.create_oc_sendsterile(),data);
                Log.d("result octype: ", result);
                return result;
            }
        }
        UpIsSterile ru = new UpIsSterile();
        ru.execute(usercode,docno,dept,itemcode,octype);
    }


}