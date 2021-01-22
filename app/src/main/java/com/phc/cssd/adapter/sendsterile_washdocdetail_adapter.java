package com.phc.cssd.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.config.ConfigProgram;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class sendsterile_washdocdetail_adapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    public ArrayList<String> selectedItemDetail = new ArrayList<String>();
    public boolean IsAdmin;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    String B_ID = null;
    String Isdel = null;
    String Isdel1 = "1";
    boolean Isadmin;
    boolean IsEdit;
    boolean IsAlert =true;
    boolean IsCheck = false;
    String IsStatusDoc = "";

    public sendsterile_washdocdetail_adapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData,boolean IsAdmin,String B_ID, String Isdel) {
        super(aActivity, 0, listData);
        this.IsAdmin = IsAdmin;
        this.IsEdit = IsEdit;
        this.aActivity= aActivity;
        this.listData = listData;
        this.selectedItemDetail = selectedItemDetail;
        Isadmin = (( SendSterile_MainActivity )aActivity).IsAdmin;
        this.B_ID = B_ID;
        this.Isdel = Isdel;
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.sendsterile_washdocdetail_adapter, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;

        TextView w_itemname = (TextView) v.findViewById(R.id.w_itemname);
        TextView w_remark_detail = (TextView) v.findViewById(R.id.w_remark_detail);
        ImageView resterile_IV = (ImageView)v.findViewById(R.id.w_resterile);
        TextView txtitemname = (TextView)v.findViewById(R.id.w_itemname);
        TextView txtUcode= (TextView) v.findViewById(R.id.w_ucode);
        TextView ucount= (TextView) v.findViewById(R.id.ucount);
        TextView w_date= (TextView) v.findViewById(R.id.w_date);
        TextView itemqty = (TextView) v.findViewById(R.id.itemqty);
        TextView txtxremark_detail = (TextView) v.findViewById(R.id.w_remark_detail);
        TextView textView53 = (TextView) v.findViewById(R.id.textView53);
        TextView r_name = (TextView) v.findViewById(R.id.w_r_name);
        TextView bt_note = (TextView) v.findViewById(R.id.w_bt_note);
        TextView bt_risk = (TextView) v.findViewById(R.id.w_bt_risk);
        TextView bt_ems = (TextView) v.findViewById(R.id.w_bt_ems);
        TextView basket_name = (TextView) v.findViewById(R.id.basket_name);
        final ImageView del_multi_un = (ImageView) v.findViewById(R.id.del_multi_un);
        final ImageView del_multi = (ImageView) v.findViewById(R.id.del_multi);
        RelativeLayout back = ( RelativeLayout ) v.findViewById(R.id.back);
        ImageView btdel =(ImageView ) v.findViewById(R.id.w_btdel);
        final ImageView chk_box = (ImageView) v.findViewById(R.id.chk_box);
        final ImageView un_chk_box = (ImageView) v.findViewById(R.id.un_chk_box);
        final ImageView throw_item_to_washtag = ( ImageView ) v.findViewById(R.id.throw_item_to_washtag);

        if(!ConfigProgram.wash_tag){
            throw_item_to_washtag.getLayoutParams().width = 0;
        }

        if(!listData.get(position).getBasketname().equals("")){
            throw_item_to_washtag.setVisibility(View.INVISIBLE);
        }else{
            throw_item_to_washtag.setVisibility(View.VISIBLE);
            throw_item_to_washtag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listData.get(position).getResteriletype().equals("1")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(aActivity);
                        builder.setCancelable(true);
                        builder.setTitle("ยืนยัน");
                        builder.setMessage("ต้องการนำรายการหมดอายุ เข้าตะกร้า Wash Tag หรือไม่");
                        builder.setPositiveButton("ใช่",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((SendSterile_MainActivity) aActivity ).insert_item_to_basket_and_re(listData.get(position).getItemID(),listData.get(position).getSs_rowid());

                                    }
                                });
                        builder.setNegativeButton("ไม่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }else{
                        ((SendSterile_MainActivity) aActivity ).insert_item_to_basket(listData.get(position).getItemID(),listData.get(position).getSs_rowid());
                    }
                }
            });
        }


        del_multi.setVisibility(View.INVISIBLE);
        chk_box.setVisibility(View.VISIBLE);
        un_chk_box.setVisibility(View.GONE);

        bt_risk.setEnabled(false);

        if (listData.get(position).getIsDenger().equals("0")){
            bt_risk.setBackgroundResource(R.drawable.ic_risk_icon_gray);
        }else {
            bt_risk.setBackgroundResource(R.drawable.ic_risk_icon);
        }
        basket_name.setText(listData.get(position).getBasketname());
        w_itemname.setPaintFlags(w_itemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        w_itemname.setText(listData.get(position).getItemname());
        w_itemname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (( SendSterile_MainActivity )aActivity).LoadImg( listData.get(position).getItemcode(),"1",listData.get(position).getUsageCode(),listData.get(position).getItemname(),"noremark");
            }
        });
        txtitemname.setPaintFlags(txtitemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtitemname.setText(listData.get(position).getItemname());


        txtUcode.setText("รหัสใช้งาน : "+listData.get(position).getUsageCode());
        itemqty.setText("[ "+listData.get(position).getItemCount()+" ]");
        w_date.setText(" ("+listData.get(position).getPackdate()+" วัน"+" )");
        w_remark_detail.setText(listData.get(position).getXremark());
        txtxremark_detail.setText(listData.get(position).getXremark());
        ArrayList<Response_Aux> resultsR = xCtl.getListResterileType();
        ArrayList<Response_Aux> resultsO = xCtl.getListOccuranceType();

        if (listData.get(position).getUsageCount().equals("0")){
            ucount.setVisibility(View.INVISIBLE);
        }else {
            ucount.setVisibility(View.VISIBLE);
        }

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((SendSterile_MainActivity)aActivity).getlistdetailqty(listData.get(position).getUsageCode());
//                ((SendSterile_MainActivity)aActivity).getlistdetail(listData.get(position).getUsageCode(),listData.get(position).getDocno());
//                ((SendSterile_MainActivity)aActivity).UsageCode(listData.get(position).getUsageCode(),listData.get(position).getDept(),listData.get(position).getDocno());
//            }
//        });

        chk_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_box.getVisibility() == View.INVISIBLE){
                    IsCheck = true;
                    chk_box.setVisibility(View.VISIBLE);
                    un_chk_box.setVisibility(View.INVISIBLE);
                    listData.get(position).setChk_box_wash(false);
                    (( SendSterile_MainActivity ) aActivity).checkRowID(listData.get(position).getItemID(), listData.get(position).getItemID());
                    //(( SendSterile_MainActivity ) aActivity).DelAll(listData.get(position).getDocno(), listData.get(position).getSs_rowid());
                }else {
                    IsCheck = false;
                    chk_box.setVisibility(View.INVISIBLE);
                    un_chk_box.setVisibility(View.VISIBLE);
                    listData.get(position).setChk_box_wash(true);
                    (( SendSterile_MainActivity ) aActivity).checkRowID(listData.get(position).getItemID(), listData.get(position).getItemID());
                    //(( SendSterile_MainActivity ) aActivity).DelAll(listData.get(position).getDocno(), listData.get(position).getSs_rowid());
                }
            }
        });

        un_chk_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (un_chk_box.getVisibility() == View.INVISIBLE){
                    IsCheck = true;
                    chk_box.setVisibility(View.INVISIBLE);
                    un_chk_box.setVisibility(View.VISIBLE);
                    listData.get(position).setChk_box_wash(false);
                    (( SendSterile_MainActivity ) aActivity).checkRowID(listData.get(position).getItemID(), listData.get(position).getItemID());
                    //(( SendSterile_MainActivity ) aActivity).DelAll(listData.get(position).getDocno(), listData.get(position).getSs_rowid());
                }else {
                    IsCheck = false;
                    chk_box.setVisibility(View.VISIBLE);
                    un_chk_box.setVisibility(View.INVISIBLE);
                    listData.get(position).setChk_box_wash(true);
                    (( SendSterile_MainActivity ) aActivity).checkRowID(listData.get(position).getItemID(), listData.get(position).getItemID());
                    //(( SendSterile_MainActivity ) aActivity).DelAll(listData.get(position).getDocno(), listData.get(position).getSs_rowid());
                }
            }
        });

//        chk_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(chk_box.isChecked()){
//                    listData.get(position).setChk_box_wash(true);
//                    (( SendSterile_MainActivity ) aActivity).checkRowID(listData.get(position).getItemID(), listData.get(position).getItemID());
//                }else{
//
//                    (( SendSterile_MainActivity ) aActivity).checkRowID(listData.get(position).getItemID(), listData.get(position).getItemID());
//                }
//            }
//        });

        if(!(listData.get(position).getIsStatus().equals("0"))){
            resterile_IV.setEnabled(false);
            if(!(IsEdit&&Isadmin)){
                btdel.setEnabled(false);
                btdel.setVisibility(View.INVISIBLE);
                if(Isadmin && IsAlert){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setCancelable(true);
                    if(listData.get(position).getPayoutIsStatus().equals("1")){
                        builder.setMessage("ไม่สามารถแก้ไขได้ เนื่องจากมีรายการบางรายการอยู่ในเครื่องล้าง");
                    }else{
                        builder.setMessage("ไม่สามารถแก้ไขได้ เนื่องจากเอกสารจ่ายถูกจ่ายไปแล้ว");
                    }
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    IsAlert =false;
                }
            }else{
                ((SendSterile_MainActivity)aActivity).showAndhideBlueHead(true);
            }
            bt_note.setEnabled(false);
            bt_risk.setEnabled(false);
        }

        if (!listData.get(position).getIsStatus().equals("0")&&!(IsEdit&&Isadmin)) {

            btdel.setEnabled(false);
            btdel.setVisibility(View.INVISIBLE);
            del_multi_un.setVisibility(View.INVISIBLE);
            del_multi_un.setEnabled(false);

//            if (!(IsEdit&&Isadmin)) {
//                btdel.setEnabled(false);
//                btdel.setVisibility(View.INVISIBLE);
//                del_multi_un.setVisibility(View.INVISIBLE);
//                del_multi_un.setEnabled(false);
//            }else {
//                if (Isdel == Isdel1) {
//                    btdel.setVisibility(View.INVISIBLE);
//                    del_multi_un.setVisibility(View.VISIBLE);
//                } else {
//                    btdel.setVisibility(View.VISIBLE);
//                    del_multi_un.setVisibility(View.INVISIBLE);
//                }
//            }
        }else {
            Log.d("KNLDVLD",Isdel);
            Log.d("KNLDVLD",Isdel1);
            if (Isdel == Isdel1) {
                btdel.setVisibility(View.INVISIBLE);
                del_multi_un.setVisibility(View.VISIBLE);
            } else {
                btdel.setVisibility(View.VISIBLE);
                del_multi_un.setVisibility(View.INVISIBLE);
            }
        }

        if(!listData.get(position).getOccuranceID().equals("0")){
            txtitemname.setTextColor(Color.RED);
            txtUcode.setTextColor(Color.RED);
            txtxremark_detail.setTextColor(Color.RED);
            textView53.setTextColor(Color.RED);
            w_date.setTextColor(Color.RED);
            //bt_risk.setBackgroundResource(R.drawable.ic_risk_icon);
        }

        if(listData.get(position).getIsSterile().equals("1")){
            for(int i=0;i<resultsR.size();i++){
                if(listData.get(position).getResteriletype().equals(resultsR.get(i).getFields1())){
                    listData.get(position).setResterilename(resultsR.get(i).getFields2());
                }
            }
            if (listData.get(position).getResterilename().equals("-")){
                r_name.setText(listData.get(position).getResterilename());
                resterile_IV.setImageResource(R.drawable.ic_r_grey);
            }else {
                r_name.setText(listData.get(position).getResterilename());
                resterile_IV.setImageResource(R.drawable.ic_r_red);
            }

        }

        if (!listData.get(position).getIsStatus().equals("0")) {
            ((SendSterile_MainActivity)aActivity).showAndhideBlueHead(false);
            //btdel.setVisibility(View.INVISIBLE);
        }else {
            ((SendSterile_MainActivity)aActivity).showAndhideBlueHead(true);
            //btdel.setVisibility(View.VISIBLE);
        }

        btdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setCancelable(true);
                builder.setTitle("ยืนยัน");
                builder.setMessage("ต้องการลบหรือไม่");
                builder.setPositiveButton("ตกลง",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                (( SendSterile_MainActivity ) aActivity).DeleteDetail(listData.get(position).getDocno(), String.valueOf(listData.get(position).getSs_rowid()));
                                (( SendSterile_MainActivity ) aActivity).addEvenlog("SS", listData.get(position).getItemID(), "Delete " + listData.get(position).getUsageCode() + " from " + listData.get(position).getDocno());
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

        del_multi_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (del_multi.getVisibility() == View.INVISIBLE){
                    IsCheck = true;
                    del_multi.setVisibility(View.VISIBLE);
                    (( SendSterile_MainActivity ) aActivity).DelAll(listData.get(position).getDocno(), listData.get(position).getSs_rowid());
                }else {
                    IsCheck = false;
                    del_multi.setVisibility(View.INVISIBLE);
                    (( SendSterile_MainActivity ) aActivity).DelAll(listData.get(position).getDocno(), listData.get(position).getSs_rowid());
                }
            }
        });

        if (listData.get(position).getRemarkEms().equals("0")){
            bt_ems.setBackgroundResource(R.drawable.ic_stopwatchx64_gray2);
        }else {
            bt_ems.setBackgroundResource(R.drawable.ic_stopwatchx64_red2);
        }

        bt_ems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LFHFKF",listData.get(position).getIsStatus());
                if((listData.get(position).getIsStatus().equals("0"))){
                    IsStatusDoc = "0";
                }else {
                    IsStatusDoc = "1";
                }

                final Dialog dialog = new Dialog(aActivity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.ems_dialog);
                dialog.setCancelable(true);
                dialog.setTitle("หมายเหตุ...");

                final EditText note1 = (EditText) dialog.findViewById(R.id.note1);

                note1.setText(listData.get(position).getRemarkExpress());

                Button button1 = (Button) dialog.findViewById(R.id.button1);
                Button button5 = (Button) dialog.findViewById(R.id.button5);

                if (IsStatusDoc.equals("0")) {
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
//                            bt_ems.setBackgroundResource(R.drawable.ic_stopwatchx64_red2);
                            (( SendSterile_MainActivity ) aActivity).updateremarkems(listData.get(position).getUsageCode(), note1.getText().toString(), "1");
                            (( SendSterile_MainActivity ) aActivity).getlistcreate_l2(listData.get(position).getDocno());
                            dialog.dismiss();
                        }
                    });
                    button5.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
//                            bt_ems.setBackgroundResource(R.drawable.ic_stopwatchx64_gray2);
                            (( SendSterile_MainActivity ) aActivity).updateremarkems(listData.get(position).getUsageCode(), note1.getText().toString(), "0");
                            (( SendSterile_MainActivity ) aActivity).getlistcreate_l2(listData.get(position).getDocno());
                            dialog.dismiss();
                        }
                    });
                }else {
                    button1.setEnabled(false);
                    button5.setEnabled(false);
                    button1.setBackgroundResource(R.drawable.bt_save_grey);
                    button5.setBackgroundResource(R.drawable.bt_cancel_gray);
                    note1.setEnabled(false);
                    note1.setTextColor(Color.BLACK);
                }
                dialog.show();
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
                        listData.get(position).setResteriletype(resultsResterileType.get(pn).getFields1());
                        listData.get(position).setResterilename(ResterileType.getSelectedItem().toString());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                Button button1 = (Button) dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(!ResterileType.getSelectedItem().equals("-")) {
                            listData.get(position).setIsSterile("1");
//                            resterile_IV.setImageResource(R.drawable.ic_r_red);
                        }
                        (( SendSterile_MainActivity ) aActivity).UpIsSterile(listData.get(position).getSs_rowid().toString(),listData.get(position).getIsSterile(),listData.get( position ).getResteriletype());
//                        r_name.setText(listData.get(position).getResterilename());
                        dialog.dismiss();
                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(!ResterileType.getSelectedItem().equals("-")) {
                            listData.get(position).setIsSterile("0");
//                            resterile_IV.setImageResource(R.drawable.ic_r_grey);
                        }
                        (( SendSterile_MainActivity )aActivity).UpSterile(listData.get(position).getSs_rowid().toString(),listData.get(position).getIsSterile(),listData.get( position ).getResteriletype());
//                        resterile_IV.setImageResource(R.drawable.ic_r_grey);
//                        r_name.setText("");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Log.d("VVDVRFV" + "Remark",listData.get(position).getRemarkSend());
        Log.d("VVDVRFV" + "Send",listData.get(position).getSs_rowid());

        if (listData.get(position).getXremark().equals("")){
            if(listData.get(position).getRemarkSend().equals("1")){
                bt_note.setBackgroundResource(R.drawable.ic_list_blue);
            }else {
                bt_note.setBackgroundResource(R.drawable.ic_list_grey);
            }
        }else {
            bt_note.setBackgroundResource(R.drawable.ic_list_blue);
        }

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
                        listData.get(position).setXremark(note1.getText().toString());
                        (( SendSterile_MainActivity ) aActivity).updateremark(listData.get(position).getSs_rowid(),listData.get(position).getXremark(),"2");
                        (( SendSterile_MainActivity ) aActivity).getlistcreate_l2(listData.get(position).getDocno());
//                        txtxremark_detail.setText(listData.get(position).getXremark());
                        dialog.dismiss();
                    }
                });

                Button button5 = (Button) dialog.findViewById(R.id.button5);
                button5.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        txtxremark_detail.setText(listData.get(position).getXremark());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return v;
    }

}
