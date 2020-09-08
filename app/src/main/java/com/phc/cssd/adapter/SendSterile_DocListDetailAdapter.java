package com.phc.cssd.adapter;

/**
 * Created by HPBO on 1/11/2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.properties.pCustomer;

import java.util.ArrayList;

/**
 * Created by User on 20/7/2560.
 */


public class SendSterile_DocListDetailAdapter extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private AppCompatActivity aActivity;
    String CheckAll = null;
    String CheckAllAd = "1";

    public SendSterile_DocListDetailAdapter(AppCompatActivity aActivity, ArrayList<pCustomer> listData,String CheckAll) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
        this.listData = listData;
        this.CheckAll = CheckAll;
    }

    @Override
    public int getCount() { return listData.size(); }

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
        final View v = inflater.inflate(R.layout.list_getdocdetail_sendsterile, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;
        TextView txtitemname = (TextView) v.findViewById(R.id.itemname);
        TextView txtxqty = (TextView) v.findViewById(R.id.xqty);
        TextView textView49 = (TextView) v.findViewById(R.id.textView49);
        RelativeLayout R1 = (RelativeLayout) v.findViewById(R.id.R1);
        final CheckBox checkBoxsub = (CheckBox ) v.findViewById(R.id.checkBoxsub);

        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!listData.get(position).getRemarkAdmin().equals("0")){
                    ((SendSterile_MainActivity)aActivity).OpenDialog(listData.get(position).getItemname(),"1");
                }
            }
        });

        checkBoxsub.setChecked(true);

//        if (!listData.get(position).getRemarkAdmin().equals("0")){
//            checkBoxsub.setChecked(false);
//            txtitemname.setTextColor(Color.RED);
//            txtxqty.setTextColor(Color.RED);
//            textView49.setTextColor(Color.RED);
//        }else {
//            checkBoxsub.setChecked(true);
//            txtitemname.setTextColor(Color.BLACK);
//            txtxqty.setTextColor(Color.BLACK);
//            textView49.setTextColor(Color.BLACK);
//        }

        if (CheckAll == CheckAllAd) {
            if (!listData.get(position).getRemarkAdmin().equals("0")){
                checkBoxsub.setChecked(false);
                txtitemname.setTextColor(Color.RED);
                txtxqty.setTextColor(Color.RED);
                textView49.setTextColor(Color.RED);
            }else {
                checkBoxsub.setChecked(true);
            }
        } else {
            checkBoxsub.setChecked(false);
        }

        checkBoxsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBoxsub.isChecked()){
                    ((SendSterile_MainActivity)aActivity).OpenDialog(listData.get(position).getItemname(),"0");
                }
            }
        });
        txtitemname.setPaintFlags(txtitemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtitemname.setText(listData.get(position).getItemname());
        txtitemname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SendSterile_MainActivity)aActivity).LoadImg(listData.get(position).getItemcode(),"2",listData.get(position).getUsageCode(),listData.get(position).getItemname());
            }
        });
        txtxqty.setText( listData.get(position).getXqty() );
        return v;
    }

}