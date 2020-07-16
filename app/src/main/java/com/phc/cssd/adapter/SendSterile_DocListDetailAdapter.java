package com.phc.cssd.adapter;

/**
 * Created by HPBO on 1/11/2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        final View v = inflater.inflate(R.layout.list_getdocdetail_sendsterile, parent, false);
        final pCustomer pCus = listData.get(position);
        final int xps = position;
        TextView txtitemname = (TextView) v.findViewById(R.id.itemname);
        TextView txtxqty = (TextView) v.findViewById(R.id.xqty);
        final CheckBox checkBoxsub = (CheckBox ) v.findViewById(R.id.checkBoxsub);

        checkBoxsub.setChecked(true);
        if (CheckAll == CheckAllAd) {
            checkBoxsub.setChecked(true);
        } else {
            checkBoxsub.setChecked(false);
        }
        checkBoxsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBoxsub.isChecked()){
                    final Dialog dialog = new Dialog(aActivity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.remark_dialog_sendsterlie);
                    dialog.setCancelable(true);
                    dialog.setTitle("หมายเหตุ...");

//                    final EditText note1 = (EditText) dialog.findViewById(R.id.note1);
//                    note1.setText(listData.get(position).getXremark());
//
//                    Button button1 = (Button) dialog.findViewById(R.id.button1);
//                    button1.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    Button button5 = (Button) dialog.findViewById(R.id.button5);
//                    button5.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
                    dialog.show();
                }
            }
        });
        txtitemname.setPaintFlags(txtitemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtitemname.setText(listData.get(position).getItemname());
        txtitemname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (( SendSterile_MainActivity )aActivity).LoadImg(listData.get(position).getItemcode(),"2",listData.get(position).getUsageCode(),listData.get(position).getItemname());
            }
        });
        txtxqty.setText( listData.get(position).getXqty() );
        return v;
    }

}