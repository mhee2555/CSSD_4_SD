package com.phc.cssd.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.CssdSearchSterile;
import com.phc.cssd.R;
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
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_search_sterile_detail, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_barcode = (TextView) view.findViewById(R.id.txt_barcode);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_sd = (TextView) view.findViewById(R.id.txt_sd);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.chk = (ImageView) view.findViewById(R.id.chk);
            viewHolder.chk_ = (ImageView) view.findViewById(R.id.chk_);


//            viewHolder.edit_print_qty = (EditText) view.findViewById(R.id.edit_print_qty);
            if (type.equals("0")){
                viewHolder.chk_.setVisibility(View.GONE);
            }else {
                viewHolder.chk_.setVisibility(View.VISIBLE);
                viewHolder.chk.setVisibility(View.GONE);
            }

            viewHolder.chk_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        viewHolder.chk.setVisibility(View.VISIBLE);
                        viewHolder.chk_.setVisibility(View.GONE);
                        DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                        viewHolder.chk_.setImageResource( DATA_MODEL.get(position).getCheck() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(viewHolder.index).setCheck(!DATA_MODEL.get(viewHolder.index).isCheck());
                        Log.d("LFHLKD","1");
                        (( CssdSearchSterile ) context).WashID_Row(DATA_MODEL.get(position).getUsageCode());
                        viewHolder.chk.setVisibility(View.GONE);
                        viewHolder.chk_.setVisibility(View.VISIBLE);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
//
//            viewHolder.edit_print_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(final View v, boolean hasFocus) {
//                    try {
//                        DATA_MODEL.get(viewHolder.index).setQty_Print( viewHolder.edit_print_qty.getText().toString() );
//
//                        //System.out.println("SET QTY = " + viewHolder.edit_print_qty.getText().toString());
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.txt_no.setText((DATA_MODEL.get(position).getIndex()+1) + ".");
        holder.txt_barcode.setText(DATA_MODEL.get(position).getItemcode());
        holder.txt_item_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.txt_sd.setText(DATA_MODEL.get(position).getID());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
//        holder.chk_.setImageResource(DATA_MODEL.get(position).getCheck() );
//        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
//        holder.edit_print_qty.setText("");
        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_no;
        TextView txt_barcode;
        TextView txt_item_code;
        TextView txt_sd;
        TextView txt_item_name;
        TextView txt_qty;
        ImageView chk;
        ImageView chk_;
//        EditText edit_print_qty;
    }

}