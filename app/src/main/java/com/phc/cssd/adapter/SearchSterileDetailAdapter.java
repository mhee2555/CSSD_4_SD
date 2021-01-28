package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phc.cssd.CssdSearchSterile;
import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.model.ModelImportWashDetail;
import com.phc.cssd.model.ModelSterileDetail;

import java.util.List;

public class SearchSterileDetailAdapter extends ArrayAdapter<ModelSterileDetail> {

    private final List<ModelSterileDetail> DATA_MODEL;
    private List<ModelImportWashDetail> DATA_MODEL_WASH;
    private final Activity context;
    private String type = "0";

    public SearchSterileDetailAdapter(Activity context, List<ModelSterileDetail> DATA_MODEL,String type) {
        super(context, R.layout.activity_list_search_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.type = type;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.activity_list_search_sterile_detail, parent, false);

        LinearLayout L1 = (LinearLayout) view.findViewById(R.id.L1);
        TextView txt_no = (TextView) view.findViewById(R.id.txt_no);
        TextView txt_barcode = (TextView) view.findViewById(R.id.txt_barcode);
        TextView txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
        TextView txt_sd = (TextView) view.findViewById(R.id.txt_sd);
        TextView txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        TextView txt_qty = (TextView) view.findViewById(R.id.txt_qty);
        final ImageView chk = (ImageView) view.findViewById(R.id.chk);
        final ImageView chk_ = (ImageView) view.findViewById(R.id.chk_);

        L1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (DATA_MODEL.get(position).getIsCheckList().equals("1")){
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_print_checklist);
                    dialog.setCancelable(true);
                    dialog.setTitle("หมายเหตุ...");

                    Button btn_print = (Button) dialog.findViewById(R.id.btn_print);
                    btn_print.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            (( CssdSearchSterile ) context).callCheckListPaper(DATA_MODEL.get(position).getImportID());
                        }
                    });
                    dialog.show();
                }
                return false;
            }
        });
        //            viewHolder.edit_print_qty = (EditText) view.findViewById(R.id.edit_print_qty);
        if (type.equals("0")){
            chk_.setVisibility(View.GONE);
        }else {
            chk_.setVisibility(View.VISIBLE);
            chk.setVisibility(View.GONE);
        }

        Log.d("FKHDKH",DATA_MODEL.get(position).IsCheck+"");

        if (DATA_MODEL.get(position).IsCheck == true){
            chk.setVisibility(View.GONE);
            chk_.setVisibility(View.VISIBLE);
        }else {
            chk.setVisibility(View.VISIBLE);
            chk_.setVisibility(View.GONE);
        }

        chk_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    chk.setVisibility(View.VISIBLE);
                    chk_.setVisibility(View.GONE);
                    (( CssdSearchSterile ) context).WashID_Row(DATA_MODEL.get(position).getUsageCode());
                    DATA_MODEL.get(position).setCheck(false);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    chk.setVisibility(View.GONE);
                    chk_.setVisibility(View.VISIBLE);
                    (( CssdSearchSterile ) context).WashID_Row(DATA_MODEL.get(position).getUsageCode());
                    DATA_MODEL.get(position).setCheck(true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

//        holder.index = (DATA_MODEL.get(position).getIndex());
        txt_no.setText((DATA_MODEL.get(position).getIndex()+1) + ".");
        txt_barcode.setText(DATA_MODEL.get(position).getItemcode());
        txt_item_code.setText(DATA_MODEL.get(position).getUsageCode());
        if(DATA_MODEL.get(position).getID().equals("null") || DATA_MODEL.get(position).getID().equals("") || DATA_MODEL.get(position).getID().equals("0") || DATA_MODEL.get(position).getID().equals("-1")){
            txt_sd.setText("-");
        }else {
            txt_sd.setText(DATA_MODEL.get(position).getID());
        }
        txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        txt_qty.setText(DATA_MODEL.get(position).getQty());

        return view;
    }

}