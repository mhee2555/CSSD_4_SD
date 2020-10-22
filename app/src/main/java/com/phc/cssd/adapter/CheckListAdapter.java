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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdCheckList;
import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
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
        final ImageView checkbox = (ImageView) v.findViewById(R.id.checkbox);
        final ImageView un_checkbox = (ImageView) v.findViewById(R.id.un_checkbox);
        final ImageView Open_pic = (ImageView) v.findViewById(R.id.Open_pic);
        final TextView Open_pic_Text = (TextView) v.findViewById(R.id.Open_pic_Text);
        final String img_set = listData.get(position).getPicture_set();
        final String img_detail = listData.get(position).getPicture_detail();

        checkbox.setVisibility(View.GONE);

        if (listData.get(position).getIsPicture().equals("0")){
            Open_pic.setVisibility(View.GONE);
            Open_pic_Text.setVisibility(View.GONE);
        }

        Open_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CssdCheckList)acc).LoadImg(listData.get(position).getItemcode(),listData.get(position).getRemarkDocNo(),listData.get(position).getItemname(),"remark");
            }
        });

        Log.d("FKJDHJKDH",listData.get(position).getIsRemarkRound_RemarlAdmin()+" : "+listData.get(position).getIsRemarkRound());


        if (listData.get(position).getConfiguration().equals("0") && !listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
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
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(true);
                }else {
                    listData.get(position).setCheck(false);
                }
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
                //checkbox.setChecked(true);
                checkbox.setVisibility(View.VISIBLE);
                un_checkbox.setVisibility(View.GONE);
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(true);
                }else {
                    listData.get(position).setCheck(false);
                }
            }else {
                listData.get(position).setCheck(false);
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
                //checkbox.setChecked(true);
                checkbox.setVisibility(View.VISIBLE);
                un_checkbox.setVisibility(View.GONE);
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(true);
                }else {
                    listData.get(position).setCheck(false);
                }
            }
        }else {
            if (!model.getNameType().equals("") && model.getAdminApprove().equals("0")){
                int IsRemarkRound_RemarlAdmin = Integer.parseInt(listData.get(position).getIsRemarkRound_RemarlAdmin());
                int IsRemarkRound = Integer.parseInt(listData.get(position).getIsRemarkRound());
                if (IsRemarkRound_RemarlAdmin >= IsRemarkRound){
                    txtitemname.setTextColor(Color.RED);
                    txt_caption_qty.setTextColor(Color.RED);
                    txt_qty.setTextColor(Color.RED);
                    txt_remark.setTextColor(Color.RED);
                    txt_remark_admin.setTextColor(Color.RED);
                    txt_remark_type.setTextColor(Color.RED);
                    txt_remark_date.setTextColor(Color.RED);
                }else {
                    if (IsRemarkRound == 0){
                        txtitemname.setTextColor(Color.RED);
                        txt_caption_qty.setTextColor(Color.RED);
                        txt_qty.setTextColor(Color.RED);
                        txt_remark.setTextColor(Color.RED);
                        txt_remark_admin.setTextColor(Color.RED);
                        txt_remark_type.setTextColor(Color.RED);
                        txt_remark_date.setTextColor(Color.RED);
                    }else {
                        listData.get(position).setRemark("-");
                        listData.get(position).setAdminRemark("-");
                        listData.get(position).setNameType("-");
                        listData.get(position).setDateRemark("-");
                        if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                            listData.get(position).setCheck(true);
                        }else {
                            listData.get(position).setCheck(false);
                        }
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
                        //checkbox.setChecked(true);
                        checkbox.setVisibility(View.VISIBLE);
                        un_checkbox.setVisibility(View.GONE);
                    }
                }
            }else if (model.getAdminApprove().equals("1")){
                listData.get(position).setRemark("-");
                listData.get(position).setAdminRemark("-");
                listData.get(position).setNameType("-");
                listData.get(position).setDateRemark("-");
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(true);
                }else {
                    listData.get(position).setCheck(false);
                }
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
                //checkbox.setChecked(true);
                checkbox.setVisibility(View.VISIBLE);
                un_checkbox.setVisibility(View.GONE);
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(true);
                }else {
                    listData.get(position).setCheck(false);
                }
            }else {
                listData.get(position).setCheck(false);
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
                //checkbox.setChecked(true);
                checkbox.setVisibility(View.VISIBLE);
                un_checkbox.setVisibility(View.GONE);
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(true);
                }else {
                    listData.get(position).setCheck(false);
                }
            }
        }
//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listData.get(position).setCheck(checkbox.isChecked());
//                ((CssdCheckList)acc).onListClick(img_set, img_detail);
//                if (model.getNameType().equals("")){
//                    if (!checkbox.isChecked()){
//                    }
//                }
//            }
//        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                    listData.get(position).setCheck(checkbox.getVisibility() == View.VISIBLE);
                    ((CssdCheckList)acc).onListClick(img_set, img_detail);
                    if (model.getNameType().equals("")){
                        if (un_checkbox.getVisibility() == View.GONE){
                            checkbox.setVisibility(View.GONE);
                            un_checkbox.setVisibility(View.VISIBLE);
                            ((CssdCheckList)acc).OpenDialog(listData.get(position).getItemname(),"0",listData.get(position).getQty(),listData.get(position).getQtyItemDetail());
                        }else {
                            checkbox.setVisibility(View.VISIBLE);
                            un_checkbox.setVisibility(View.GONE);
                        }
                    }
                }else {
                    listData.get(position).setCheck(true);
                    if (un_checkbox.getVisibility() == View.GONE){
                        checkbox.setVisibility(View.GONE);
                        un_checkbox.setVisibility(View.VISIBLE);
                    }else {
                        checkbox.setVisibility(View.VISIBLE);
                        un_checkbox.setVisibility(View.GONE);
                    }
                }
            }
        });

        un_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getNameType().equals("") && model.getAdminApprove().equals("0")){
                    if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
                        if (checkbox.getVisibility() == View.GONE){
                            checkbox.setVisibility(View.VISIBLE);
                            un_checkbox.setVisibility(View.GONE);
                        }else {
                            checkbox.setVisibility(View.GONE);
                            un_checkbox.setVisibility(View.VISIBLE);
                        }
                    }else {
                        listData.get(position).setCheck(true);
                        if (checkbox.getVisibility() == View.GONE){
                            checkbox.setVisibility(View.VISIBLE);
                            un_checkbox.setVisibility(View.GONE);
                        }else {
                            checkbox.setVisibility(View.GONE);
                            un_checkbox.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        txtitemname.setPaintFlags(txtitemname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtitemname.setText(listData.get(position).getItemcode() + " - " + listData.get(position).getItemname());
        int x1 = Integer.parseInt(listData.get(position).getQty());
        int x2 = Integer.parseInt(listData.get(position).getQtyItemDetail());
        if (!listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
            if (x1 == x2){
                txt_qty.setText("(ขาด"+x2+")");
            }else {
                if (x2 != 0){
                    int x1_1 = x1 - x2;
                    txt_qty.setText(x1_1+" (ขาด"+x2+")");
                }else {
                    txt_qty.setText(listData.get(position).getQty());
                }
            }
        }else {
            txt_qty.setVisibility(View.GONE);
            txt_caption_qty.setVisibility(View.GONE);
        }

        if (listData.get(position).getItemname().equals("COMPLY STERIGAGE STEAM (SHORT)")){
            if (listData.get(position).getInternal().equals("0")){
                checkbox.setVisibility(View.GONE);
                un_checkbox.setVisibility(View.VISIBLE);
            }else {
                checkbox.setVisibility(View.VISIBLE);
                un_checkbox.setVisibility(View.GONE);
            }
        }
        txt_remark.setText(listData.get(position).getRemark());
        txt_remark_admin.setText(listData.get(position).getAdminRemark());
        txt_remark_type.setText(listData.get(position).getNameType());
        txt_remark_date.setText(listData.get(position).getDateRemark());
        return v;
    }
}