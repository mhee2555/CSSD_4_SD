package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.phc.core.string.Cons;
import com.phc.cssd.CssdPrintSterile;
import com.phc.cssd.CssdSterile;
import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.model.ModelCheckList;
import com.phc.cssd.properties.pCustomer;

import java.util.ArrayList;
import java.util.List;

public class CheckListAdapter extends ArrayAdapter {

    private List<ModelCheckList> listData;
    private Activity acc;

    public CheckListAdapter(Activity aActivity, List<ModelCheckList> listData) {
        super(aActivity, 0, listData);
        this.acc= aActivity;
        this.listData = listData;
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
        LayoutInflater inflater = (LayoutInflater) acc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_check_list, parent, false);

        final ModelCheckList model = listData.get(position);

        final TextView txtitemname = (TextView) v.findViewById(R.id.itemname);
        final TextView txt_qty = (TextView) v.findViewById(R.id.txt_qty);
        final TextView txt_caption_qty = (TextView) v.findViewById(R.id.txt_caption_qty);
        final TextView txt_remark = (TextView) v.findViewById(R.id.txt_remark);
        final TextView txt_remark_admin = (TextView) v.findViewById(R.id.txt_remark_admin);
        final TextView txt_remark_type = (TextView) v.findViewById(R.id.txt_remark_type);
        final TextView txt_remark_date = (TextView) v.findViewById(R.id.txt_remark_date);
        final RelativeLayout R1 = (RelativeLayout) v.findViewById(R.id.R1);
        final CheckBox checkbox = (CheckBox ) v.findViewById(R.id.checkbox);

        /*
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        */

        R1.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {

                if (model.getNameType().equals("")) {
                    return false;
                } else {
                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(acc);
                    quitDialog.setTitle(Cons.TITLE);
                    quitDialog.setMessage("ยืนยันลบ Remark ออกจากรายการ ?");

                    quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            listData.get(position).setRemark("-");
                            listData.get(position).setAdminRemark("-");
                            listData.get(position).setNameType("-");
                            listData.get(position).setDateRemark("-");

                            txt_remark.setText(listData.get(position).getRemark());
                            txt_remark_admin.setText(listData.get(position).getAdminRemark());
                            txt_remark_type.setText(listData.get(position).getNameType());
                            txt_remark_date.setText(listData.get(position).getDateRemark());

                            txtitemname.setTextColor(Color.GRAY);
                            txt_caption_qty.setTextColor(Color.GRAY);
                            txt_qty.setTextColor(Color.GRAY);
                            txt_remark.setTextColor(Color.GRAY);
                            txt_remark_admin.setTextColor(Color.GRAY);
                            txt_remark_type.setTextColor(Color.GRAY);
                            txt_remark_date.setTextColor(Color.GRAY);
                        }
                    });

                    quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    quitDialog.show();
                }

                return false;
            }
        });

        if (model.getNameType().equals("")) {

        } else {
            txtitemname.setTextColor(Color.RED);
            txt_caption_qty.setTextColor(Color.RED);
            txt_qty.setTextColor(Color.RED);
            txt_remark.setTextColor(Color.RED);
            txt_remark_admin.setTextColor(Color.RED);
            txt_remark_type.setTextColor(Color.RED);
            txt_remark_date.setTextColor(Color.RED);
        }

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listData.get(position).setCheck(checkbox.isChecked());
            }
        });

        txtitemname.setPaintFlags(txtitemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtitemname.setText(listData.get(position).getItemname());
        txt_qty.setText(listData.get(position).getQty());
        checkbox.setChecked(listData.get(position).isCheck());
        txt_remark.setText(listData.get(position).getRemark());
        txt_remark_admin.setText(listData.get(position).getAdminRemark());
        txt_remark_type.setText(listData.get(position).getNameType());
        txt_remark_date.setText(listData.get(position).getDateRemark());

        return v;
    }

}