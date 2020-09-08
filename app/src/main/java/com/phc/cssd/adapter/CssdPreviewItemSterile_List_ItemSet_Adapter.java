package com.phc.cssd.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelItemDetail;

import java.util.List;

public class CssdPreviewItemSterile_List_ItemSet_Adapter extends ArrayAdapter<ModelItemDetail> {

    private final List<ModelItemDetail> DATA_MODEL;
    private final Activity context;

    public CssdPreviewItemSterile_List_ItemSet_Adapter(Activity context, List<ModelItemDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_preview_item_set, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_preview_item_set, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_pic = (TextView) view.findViewById(R.id.txt_pic);


            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();

        if (DATA_MODEL.get(position).getIsChk() == 2){
            holder.txt_name.setTextColor(Color.BLACK);
            holder.txt_no.setTextColor(Color.BLACK);
            holder.txt_qty.setTextColor(Color.BLACK);
            holder.txt_code.setTextColor(Color.BLACK);
        }else {
            holder.txt_name.setTextColor(Color.BLACK);
            holder.txt_no.setTextColor(Color.BLACK);
            holder.txt_qty.setTextColor(Color.BLACK);
            holder.txt_code.setTextColor(Color.BLACK);
        }

        holder.txt_name.setText(DATA_MODEL.get(position).getItemname());
        holder.txt_code.setText(DATA_MODEL.get(position).getItemcode_set());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty() + " " + DATA_MODEL.get(position).getUnitName());
        holder.txt_no.setText((DATA_MODEL.get(position).getIndex()+1) + "." );
        holder.txt_pic.setText(DATA_MODEL.get(position).getPicture_detail());
        holder.index = (DATA_MODEL.get(position).getIndex());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_code;
        TextView txt_name;
        TextView txt_qty;
        TextView txt_no;
        TextView txt_pic;
    }

}