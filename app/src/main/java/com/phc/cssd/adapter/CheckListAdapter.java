package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdCheckList;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelCheckList;

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

        final String img_set = listData.get(position).getPicture_set();
        final String img_detail = listData.get(position).getPicture_detail();

        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CssdCheckList)acc).onListClick(img_set, img_detail);
            }
        });


        R1.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (model.getNameType().equals("")) {
                    return false;
                } else {
                    AlertDialog.Builder quitDialog = new AlertDialog.Builder(acc);
                    quitDialog.setTitle("แจ้งเตือน");
                    quitDialog.setMessage("คุณต้องการ Delete Remark / Admin Approve หรือไม่ !!");
                    quitDialog.setPositiveButton("Admin Approve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((CssdCheckList)acc).openQR("admin",listData.get(position).getItemcode(),listData.get(position).getItem_Detail_ID(),listData.get(position).getRowID(),"approve");
                            ((CssdCheckList)acc).onListClick(img_set, img_detail);
                        }
                    });

                    quitDialog.setNegativeButton("Delete Remark", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((CssdCheckList)acc).openQR("admin",listData.get(position).getItemcode(),listData.get(position).getItem_Detail_ID(),listData.get(position).getRowID(),"delete");
                            ((CssdCheckList)acc).onListClick(img_set, img_detail);
                        }
                    });

                    quitDialog.show();
                }

                return false;
            }
        });

        Log.d("FKJDHJKDH",model.getNameType()+"");

        if (!model.getNameType().equals("") && model.getAdminApprove().equals("0")){
            txtitemname.setTextColor(Color.RED);
            txt_caption_qty.setTextColor(Color.RED);
            txt_qty.setTextColor(Color.RED);
            txt_remark.setTextColor(Color.RED);
            txt_remark_admin.setTextColor(Color.RED);
            txt_remark_type.setTextColor(Color.RED);
            txt_remark_date.setTextColor(Color.RED);
        }else if (model.getAdminApprove().equals("1")){
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
            checkbox.setChecked(true);
            listData.get(position).setCheck(true);
        }else {
            listData.get(position).setRemark("-");
            listData.get(position).setAdminRemark("-");
            listData.get(position).setNameType("-");
            listData.get(position).setDateRemark("-");
            txt_remark.setText(listData.get(position).getRemark());
            txt_remark_admin.setText(listData.get(position).getAdminRemark());
            txt_remark_type.setText(listData.get(position).getNameType());
            txt_remark_date.setText(listData.get(position).getDateRemark());
            txtitemname.setTextColor(Color.BLACK);
            txt_caption_qty.setTextColor(Color.BLACK);
            txt_qty.setTextColor(Color.BLACK);
            txt_remark.setTextColor(Color.BLACK);
            txt_remark_admin.setTextColor(Color.BLACK);
            txt_remark_type.setTextColor(Color.BLACK);
            txt_remark_date.setTextColor(Color.BLACK);
            checkbox.setChecked(true);
            listData.get(position).setCheck(true);
        }

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listData.get(position).setCheck(checkbox.isChecked());
                ((CssdCheckList)acc).onListClick(img_set, img_detail);
            }
        });

        txtitemname.setPaintFlags(txtitemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtitemname.setText(listData.get(position).getItemcode() + " - " + listData.get(position).getItemname());
        txt_qty.setText(listData.get(position).getQty());
        checkbox.setChecked(listData.get(position).isCheck());
        txt_remark.setText(listData.get(position).getRemark());
        txt_remark_admin.setText(listData.get(position).getAdminRemark());
        txt_remark_type.setText(listData.get(position).getNameType());
        txt_remark_date.setText(listData.get(position).getDateRemark());

        return v;
    }

}